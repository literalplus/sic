var AuthController = ['$rootScope', 'AuthService', 'AUTH_EVENTS',
    function ($rootScope, AuthService, AUTH_EVENTS) {
        var auth = this;
        this.credentials = {};
        this.guestCredentials = {};
        this.loginError = null;
        this.registerError = null;

        this.login = function () {
            AuthService.login(auth.credentials);
        };
        
        this.guestLogin = function () {
            AuthService.login(auth.guestCredentials);
        };

        this.register = function () {
            AuthService.register(auth.credentials);
        };

        this.logout = AuthService.logout;
        this.getUserName = AuthService.getUserName;
        this.guest = AuthService.isGuest;

        $rootScope.$on(AUTH_EVENTS.login_failure, function (evt, data) {
            auth.loginError = !!data ? data.message : 'Fehler beim Einloggen!';
        });

        $rootScope.$on(AUTH_EVENTS.register_failure, function (evt, data) {
            auth.registerError = !!data ? data.message : 'Fehler beim Registrieren!';
        });

        $rootScope.$on(AUTH_EVENTS.login_success, function () {
            auth.registerError = null;
            auth.loginError = null;
        });
    }];
