var HomeController = ['$http', '$rootScope', 'AuthService', '$sce',
    function ($http, $rootScope, AuthService, $sce) {
        var ctrl = this;
        this.people = {};
        this.videoUrl = null;

        AuthService.runWhenAuthenticated(function () {
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
        });
    }];
