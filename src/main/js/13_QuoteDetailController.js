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

        PersonDetailService.fetch($stateParams.id);
    }];
