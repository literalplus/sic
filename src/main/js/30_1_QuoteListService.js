tingoApp.factory('QuoteListService', ['$http',
    function ($http) {
        var listService = {};
        listService.quotes = [];
        var fetched = false;

        listService.save = function (quote) {
            var myIndex = _.indexOf(listService.quotes, quote);
            if (quote.text.length === 0 || !quote.text.trim()) {
                listService.quotes.splice(myIndex);
                return;
            }

            $http.post('/api/quote/save', quote)
                .then(function (response) {
                    listService.quotes[myIndex] = response.data;
                }, function (response) {
                    console.warn('Couldn\'t save fields: ');
                    console.warn(response);
                    alert('Fehler beim Speichern: ' + response.data.errorMessage);
                });
        };

        listService.unedit = function (quote) {
            var myIndex = _.indexOf(listService.quotes, quote);
            listService.quotes[myIndex] = quote.backup;
        };

        listService.edit = function (quote) {
            quote.backup = _.clone(quote);
            quote.editing = true;
        };

        listService.isUpVote = function (quote) {
            return quote.ownVote === 1;
        };

        listService.isDownVote = function (quote) {
            return quote.ownVote === -1;
        };

        listService.setVote = function (quote, newVote) {
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

        listService.delete = function (quote) {
            $http.post('/api/quote/delete', quote)
                .then(function (response) {
                    listService.quotes = _.without(listService.quotes, quote);
                }, function (response) {
                    console.warn('Couldn\'t delete quote: ');
                    console.warn(response);
                    alert('Fehler beim Löschen: ' + response.data.errorMessage);
                });
        };

        listService.new = function (person) {
            listService.quotes.unshift({
                text: '',
                person: person,
                editing: true,
                authorName: 'du',
                subText: '',
                voteCount: 0
            });
        };

        listService.getFriendlyVoteCount = function (quote) {
            if (quote.voteCount === 0) {
                return '±0';
            } else if (quote.voteCount > 0) {
                return '+' + quote.voteCount;
            } else {
                return quote.voteCount;
            }
        };

        // Getters and Setters
        listService.isFetched = function () {
            return fetched;
        };
        
        listService.setFetched = function (newFetched) {
            fetched = newFetched;
        };

        listService.getQuotes = function () {
            return listService.quotes;
        };

        return listService;
    }]);
