tingoApp.factory('PersonDetailService', ['$http', '$location', '$uibModal', 'QuoteListService',
    function ($http, $location, $uibModal, QuoteListService) {
        var detailService = {};
        detailService.person = {name: 'Loading..'};

        detailService.new = function () {
            QuoteListService.new(detailService.person);
        };

        detailService.fetch = function (id) {
            QuoteListService.setFetched(false);

            if (id == 'new') {
                $uibModal.open({
                    controller: 'PersonNameModalController',
                    controllerAs: 'modalCtrl',
                    templateUrl: 'partials/person-name-modal.html'
                }).result.then(
                    function (newName) {
                        $http.post('/api/person/new', {name: newName})
                            .then(function (response) {
                                detailService.person = response.data;
                                QuoteListService.quotes = [];
                                QuoteListService.setFetched(true);
                            }, function (response) {
                                alert('Fehler beim Erstellen der Person!');
                                console.error(response);
                            });
                    }, function () {
                        $location.go('/'); //can't be in controller since we need to handle ESC too
                    }
                );
            } else {
                $http.get('/api/quote/by/person/' + id)
                    .success(function (data) {
                        detailService.person = data.person;
                        QuoteListService.quotes = data.quotes;
                        QuoteListService.setFetched(true);
                    });
            }
        };

        // Getters and Setters
        detailService.getPerson = function () {
            return detailService.person;
        };

        return detailService;
    }]);
