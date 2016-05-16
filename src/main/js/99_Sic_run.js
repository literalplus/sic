//TODO: Split into multiple files
tingoApp.run(['$rootScope', '$state', '$location', 'AuthService',
    function ($rootScope, $state, $location, AuthService) {
        $rootScope.authenticated = false;
        $rootScope.returnto = null;
        $rootScope.$on('$stateChangeStart', function (evt, to) {
            if (!to.data || !to.data.no_auth) {
                $rootScope.returnto = $location.path();
                //There must be a better solution to this
                //This event is called also when the first state is loaded, so
                //we might not know whether we're authenticated yet. If we don't, we can't really
                //do anything except wait for the auth reply - this happens primarily when a user
                //reloads an authed page or enters the URL directly
                //
                //This might flicker the new state for a fraction of a second, but they can't access
                //sensitive data anyways since the server checks auth itself
                //
                //The proper way to do this would be to somehow get Angular to wait for the login state
                //before loading any page, or use an intent, which Angular's event system doesn't support.
                AuthService.isAuthenticatedSafe(function (authenticated) {
                    console.log('auth callback: ' + authenticated);
                    if (!authenticated) {
                        $state.go('login');
                    }
                });
            }
        });
    }]);

tingoApp.controller('HomeController', HomeController);
tingoApp.controller('AuthController', AuthController);
tingoApp.controller('PersonDetailController', PersonDetailController);
tingoApp.controller('LoginController', LoginController);
tingoApp.controller('RegisterController', RegisterController);
tingoApp.controller('NavDataController', NavDataController);
tingoApp.controller('PersonNameModalController', PersonNameModalController);
tingoApp.controller('SelectionController', SelectionController);
tingoApp.controller('NewQuotesController', NewQuotesController);
tingoApp.controller('BestQuotesController', BestQuotesController);
tingoApp.constant('AUTH_EVENTS', {
    login_success: 'auth-login-success',
    login_failure: 'auth-login-failure',
    register_failure: 'auth-register-failure',
    auth_status: 'auth-status-change',
    logout: 'auth-logout'
});
tingoApp.value('THROTTLE_MILLISECONDS', 500); //infinite-scroll
