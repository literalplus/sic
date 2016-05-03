tingoApp.factory('AuthInterceptor', ['$q', 'TokenService', '$location', '$rootScope',
    function ($q, TokenService, $location, $rootScope) {
        var authInterceptor = {};

        authInterceptor.request = function (config) {
            if (TokenService.hasToken()) {
                config.headers.Authorization = 'Bearer ' + TokenService.getToken();
            }
            return config;
        };

        authInterceptor.responseError = function (response) {
            if (response.status === 401) {
                console.info("401 encountered");
                TokenService.setToken(null);
                $rootScope.returnto = $location.path(); //save current path for login back
                $location.path('/login');
            }
            return $q.reject(response);
        };

        return authInterceptor;
    }]);
