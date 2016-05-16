var NewQuotesController = ['QuoteListService', '$http',
    function (QuoteListService, $http) {
        var ctrl = this;
        var currentPage = 0;
        var previousLength = -1;
        this.disableScroll = true;
        this.quotes = [];

        angular.extend(ctrl, QuoteListService);

        this.getFriendlyVoteCount = function (quote) {
            if (quote.voteCount === 0) {
                return '±0';
            } else if (quote.voteCount > 0) {
                return '+' + quote.voteCount;
            } else {
                return quote.voteCount;
            }
        };

        this.fetchPage = function (pageId) {
            ctrl.disableScroll = true;
            $http.get('/api/quote/latest/page/' + pageId)
                .then(function (response) {
                    QuoteListService.quotes = _.concat(
                        QuoteListService.quotes, response.data
                    );
                    previousLength = response.data.length;
                    ctrl.disableScroll = false;
                }, function (response) {
                    console.warn('Could not fetch latest quotes:');
                    console.warn(response);
                });
        };

        this.nextPage = function () {
            if (previousLength !== 0) {
                ctrl.fetchPage(++currentPage);
            } else {
                console.info('Probably at the end!');
                ctrl.disableScroll = true;
            }
        };
        
        this.fetchPage(0);
    }];
