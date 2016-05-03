tingoApp.factory('AuthService', ['$http', '$rootScope', '$location', 'AUTH_EVENTS', '$window', 'TokenService',
    function ($http, $rootScope, $location, AUTH_EVENTS, $window, TokenService) {
        var authService = {};

        authService.credentials = {};
        authService.username = null;
        authService.error = false;
        authService.errorMessage = "Falscher Benutzername oder falsches Passwort!";
        authService.authChecked = false;
        authService.authRequested = false;

        var setAuth = function (token) {
            authService.setToken(token);
            $rootScope.$broadcast(AUTH_EVENTS.auth_status, authService.authenticated);
        };

        var resetAuth = function () {
            setAuth(null);
        };

        var authenticate = function (credentials, callback) {
            $http.post('auth/login', credentials).then(function (response) {
                authService.authChecked = true;
                if (response.data.token) {
                    setAuth(response.data.token);
                } else {
                    resetAuth();
                }

                if (callback) {
                    callback(authService.authenticated, response.data);
                }
            }, function () {
                resetAuth();
                if (callback) {
                    callback(false, {});
                }
            });
        };

        authService.login = function (credentials) {
            authenticate(credentials,
                function (authSuccess, data) {
                    authService.error = !authSuccess;
                    if (authSuccess) {
                        $rootScope.$broadcast(AUTH_EVENTS.login_success, data);
                        if ($rootScope.returnto !== null) {
                            $location.path($rootScope.returnto);
                            $rootScope.returnto = null;
                        } else {
                            $location.path('/');
                        }
                    } else {
                        $rootScope.$broadcast(AUTH_EVENTS.login_failure, data);
                        $location.path('/login');
                    }
                }
            );
        };

        authService.logout = function () {
            resetAuth();
            $location.path('/');
            $rootScope.$broadcast(AUTH_EVENTS.logout);
        };

        authService.register = function (credentials) {
            resetAuth();
            $http.post('auth/register', credentials).then(function () {
                authService.login(credentials);
            }, function (response) {
                authService.error = true;
                authService.errorMessage = response.data.errorMessage;
                $rootScope.$broadcast(AUTH_EVENTS.register_failure, response.data);
            });
        };

        authService.isAuthenticated = function () {
            return TokenService.hasToken() && authService.authenticated;
        };

        authService.runWhenAuthenticated = function (callback) { //this is kind of deprecated, use #onAuthChange(...)
            authService.onAuthChange(callback, null);
        };

        authService.onAuthChange = function (onLogin, onLogout) {
            if (onLogin) {
                if (authService.isAuthenticated()) {
                    onLogin();
                }
                //Call again on re-authentication
                $rootScope.$on(AUTH_EVENTS.login_success, onLogin);
            }

            if (onLogout) {
                if(!authService.isAuthenticated()) {
                    onLogout();
                }
                $rootScope.$on(AUTH_EVENTS.logout, onLogout);
            }
        };

        authService.isAuthenticatedSafe = function (callback) {
            callback(authService.isAuthenticated());
        };

        authService.getUserName = function () {
            return !!authService.username ? authService.username : 'Anon';
        };

        authService.setToken = function (token) {
            authService.token = token;
            if (token === null) {
                authService.username = null;
                TokenService.setToken(null);
                $rootScope.authenticated = authService.authenticated = false;
            } else {
                var base64 = token.split('.')[1].replace('-', '+').replace('_', '/'); //2nd string is payload, undo URL encoding
                var claimsObj = JSON.parse($window.atob(base64)); //convert base64 to text and parse as JSON
                TokenService.setToken(token);
                authService.username = claimsObj.sub; //subject is the username
                $rootScope.authenticated = authService.authenticated = true;
            }
        };

        $rootScope.isAuthenticated = authService.isAuthenticated;

        setAuth(TokenService.getToken());

        return authService;
    }]);
