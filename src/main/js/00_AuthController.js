var AuthController = ['$rootScope', 'AuthService', 'AUTH_EVENTS',
    function ($rootScope, AuthService, AUTH_EVENTS) {
        var auth = this;
        this.credentials = {};
        this.loginError = null;
        this.registerError = null;

        this.login = function () {
            AuthService.login(auth.credentials);
        };

        this.logout = function () {
            AuthService.logout();
        };

        this.register = function () {
            AuthService.register(auth.credentials);
        };

        this.getUserName = function () {
            return AuthService.getUserName();
        };

        $rootScope.$on(AUTH_EVENTS.login_failure, function () {
            auth.loginError = 'Falscher Benutzername oder falsches Passwort!';
        });

        $rootScope.$on(AUTH_EVENTS.register_failure, function (evt, data) {
            auth.registerError = !!data ? data.errorMessage : 'Fehler beim Registrieren!';
        });

        $rootScope.$on(AUTH_EVENTS.login_success, function () {
            auth.registerError = null;
            auth.loginError = null;
        });
    }];
