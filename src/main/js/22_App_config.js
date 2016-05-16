tingoApp.config(['$stateProvider', '$urlRouterProvider', '$urlMatcherFactoryProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $urlMatcherFactoryProvider, $httpProvider) {
        $urlRouterProvider.otherwise('/');
        $urlRouterProvider.when('/person', '/person/list');

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/home.html',
                controller: 'HomeController',
                controllerAs: 'homeCtrl',
                data: {
                    no_auth: true
                }
            })
            .state('login', {
                url: '/login?errRedirect',
                templateUrl: 'partials/login.html',
                controller: 'LoginController',
                controllerAs: 'loginCtrl',
                data: {
                    no_auth: true
                }
            })
            .state('register', {
                url: '/register',
                templateUrl: 'partials/register.html',
                controller: 'RegisterController',
                controllerAs: 'regCtrl',
                data: {
                    no_auth: true
                }
            })
            .state('person', {
                url: '/people',
                abstract: true,
                template: '<ui-view/>'
            })
            .state('person.detail', {
                url: '/detail/:id',
                templateUrl: 'partials/person-detail.html',
                controller: 'PersonDetailController',
                controllerAs: 'detailCtrl'
            })
            .state('newquotes', {
                url: '/quotes/latest',
                templateUrl: 'partials/new-quotes.html',
                controller: 'NewQuotesController',
                controllerAs: 'detailCtrl'
            });

        //Prevent Spring Security from displaying auth dialog, we control authentication ourselves!
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $httpProvider.interceptors.push('AuthInterceptor');
    }]);
