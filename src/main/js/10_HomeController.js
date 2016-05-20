var HomeController = ['$http', '$rootScope', 'AuthService', '$sce', '$stateParams', 'AUTH_EVENTS', '$window',
    function ($http, $rootScope, AuthService, $sce, $stateParams, AUTH_EVENTS, $window) {
        var ctrl = this;
        this.people = {};
        this.videoUrl = null;
        this.quotesCount = null;

        if (AuthService.isAuthenticated()) {
            $http.get('/api/quotes/count')
                .then(function (response) {
                    ctrl.quotesCount = response.data.count;
                });
            $http.get('/api/person/list')
                .success(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        var split = data[i].name.split(' ', 2);
                        data[i].firstName = split[0];
                        data[i].lastName = split[1];
                    }

                    ctrl.people = data;
                });
            $http.get('/api/prank/video/url')
                .then(function (response) {
                    if (!!response.data.showVideo) {
                        ctrl.videoUrl = $sce.trustAsResourceUrl(response.data.url);
                    }
                });
        }

        if (!!$stateParams.guestCode) {
            console.info("Authenticating using guest code...");
            $rootScope.$on(AUTH_EVENTS.login_success, function () {
                if (AuthService.isGuest()) {
                    console.info("Authenticated using guest code!");
                    $window.location = '/'; //force redirect, to drop guestCode from URL 
                }
            });
            AuthService.login({password: $stateParams.guestCode});
        }
    }];
