var PersonDetailController = ['PersonDetailService', '$stateParams',
    function (PersonDetailService, $stateParams) {
        var ctrl = this;

        this.getQuotes = PersonDetailService.getQuotes;
        this.getPerson = PersonDetailService.getPerson;

        this.save = PersonDetailService.save;
        this.unedit = PersonDetailService.unedit;
        this.edit = PersonDetailService.edit;
        this.new = PersonDetailService.new;
        this.delete = PersonDetailService.delete;
        this.isFetched = PersonDetailService.isFetched;

        this.handleDblClick = function (quote) {
            ctrl.edit(quote);
        };
        
        this.getFriendlyVoteCount = function (quote) {
            if (quote.voteCount === 0) {
                return '±0';
            } else if (quote.voteCount > 0) {
                return '+' + quote.voteCount;
            } else {
                return quote.voteCount;
            }
        };

        PersonDetailService.fetch($stateParams.id);
    }];
