tingoApp.factory('PersonDetailService', ['$http', '$location', '$uibModal',
    function ($http, $location, $uibModal) {
        var detailService = {};
        detailService.person = {name: 'Loading..'};
        detailService.quotes = [];
        var fetched = false;

        detailService.save = function (quote) {
            var myIndex = _.indexOf(detailService.quotes, quote);
            if (quote.text.length === 0 || !quote.text.trim()) {
                detailService.quotes.splice(myIndex);
                return;
            }

            $http.post('/api/quote/save', quote)
                .then(function (response) {
                    detailService.quotes[myIndex] = response.data;
                }, function (response) {
                    console.warn('Couldn\'t save fields: ');
                    console.warn(response);
                    alert('Fehler beim Speichern: ' + response.data.errorMessage);
                });
        };

        detailService.unedit = function (quote) {
            var myIndex = _.indexOf(detailService.quotes, quote);
            detailService.quotes[myIndex] = quote.backup;
        };

        detailService.edit = function (quote) {
            quote.backup = _.clone(quote);
            quote.editing = true;
        };
        
        detailService.isUpVote = function (quote) {
            return quote.ownVote === 1;
        };

        detailService.isDownVote = function (quote) {
            return quote.ownVote === -1;
        };

        detailService.setVote = function (quote, newVote) {
            var voteType;
            if (newVote === -1) {
                voteType = 'down';
            } else if (newVote === 1) {
                voteType = 'up';
            } else {
                voteType = 'reset';
            }
            
            if (quote.ownVote === newVote) {
                voteType = 'reset';
                quote.ownVote = 0;
            } else {
                quote.ownVote = newVote;
            }
            
            $http.get('/api/quote/by/id/' + quote.id + '/vote/' + voteType)
                .then(function (response) {
                    quote.voteCount = response.data;
                }, function (response) {
                    console.warn('Couldn\'t change quote vote: ');
                    console.warn(response);
                    alert('Fehler beim Voten: ' + response.data.errorMessage);
                });
        };

        detailService.new = function () {
            detailService.quotes.push({
                text: '',
                personId: detailService.person.id,
                editing: true,
                authorName: 'du',
                subText: '',
                voteCount: 0
            });
        };

        detailService.delete = function (quote) {
            $http.post('/api/quote/delete', quote)
                .then(function (response) {
                    detailService.quotes = _.without(detailService.quotes, quote);
                }, function (response) {
                    console.warn('Couldn\'t delete quote: ');
                    console.warn(response);
                    alert('Fehler beim Löschen: ' + response.data.errorMessage);
                });
        };

        detailService.fetch = function (id) {
            fetched = false;

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
                                detailService.quotes = [];
                                fetched = true;
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
                        detailService.quotes = data.quotes;
                        fetched = true;
                    });
            }
        };

        // Getters and Setters
        detailService.isFetched = function () {
            return fetched;
        };

        detailService.getQuotes = function () {
            return detailService.quotes;
        };

        detailService.getPerson = function () {
            return detailService.person;
        };

        return detailService;
    }]);
