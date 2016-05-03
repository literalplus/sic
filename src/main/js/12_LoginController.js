var LoginController = ['$stateParams', '$rootScope', '$location',
    function ($stateParams, $rootScope, $location) {
        this.back = function () {
            if ($rootScope.returnto !== null) {
                $location.path($rootScope.returnto);
                $rootScope.returnto = null;
            } else {
                $location.path('/');
            }
        };
    }];
