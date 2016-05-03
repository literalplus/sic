var HomeController = ['$http', '$rootScope', 'AuthService',
    function ($http, $rootScope, AuthService) {
        var ctrl = this;
        this.people = {};

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
        });
    }];
