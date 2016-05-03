tingoApp.factory('TokenService', ['$window', '$cookies',
    function ($window, $cookies) {
        var tokenService = {};
        var token = null;
        var cookieOpts = {
            path: '/',
            //secure: true, //doesn't work with local HTTP-only debug; TODO
            expires: new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 15)) /* 15 days */
        };

        tokenService.setToken = function (newToken) {
            token = newToken;

            if (!!token) {
                $window.localStorage.setItem('sic-token', token);
                $cookies.put('sic-token-cookie', token, cookieOpts);

            } else {
                $window.localStorage.removeItem('sic-token');
                $cookies.remove('sic-token-cookie', cookieOpts);
            }
        };

        tokenService.getToken = function () {
            return token;
        };

        tokenService.hasToken = function () {
            return !!token;
        };

        var storedToken = $window.localStorage.getItem('sic-token');
        if (!!storedToken) {
            token = storedToken;
        }

        return tokenService;
    }]);
