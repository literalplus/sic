var BestQuotesController = ['QuoteListService', '$http',
    function (QuoteListService, $http) {
        var ctrl = this;
        var currentPage = 0;
        var previousLength = -1;
        this.disableScroll = true;
        this.quotes = [];
        this.minRating = 0;

        angular.extend(ctrl, QuoteListService);

        this.fetchPage = function (pageId) {
            ctrl.disableScroll = true;
            $http.get('/api/quote/best/gt/' + ctrl.minRating + '/page/' + pageId)
                .then(function (response) {
                    QuoteListService.quotes = _.concat(
                        QuoteListService.quotes, response.data
                    );
                    previousLength = response.data.length;
                    ctrl.disableScroll = false;
                }, function (response) {
                    console.warn('Could not fetch best quotes:');
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
        
        this.resetQuotes = function () {
            QuoteListService.quotes = [];
            currentPage = 0;
            ctrl.fetchPage(0);
        };

        this.fetchPage(0);
    }];
