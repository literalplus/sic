var NavDataController = ['$http', 'AuthService',
    function ($http, AuthService) {
        var ctrl = this;
        this.people = {};
        this.peopleLoaded = false;

        AuthService.onAuthChange(function () {
            $http.get('/api/person/list')
                .success(function (data) {
                    ctrl.people = data;
                    ctrl.peopleLoaded = true;
                });
        }, function () {
            ctrl.peopleLoaded = false;
            ctrl.people = {};
        });
    }];
