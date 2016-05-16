var PersonDetailController = ['PersonDetailService', 'QuoteListService', '$stateParams',
    function (PersonDetailService, QuoteListService, $stateParams) {
        var ctrl = this;

        angular.extend(ctrl, QuoteListService, PersonDetailService);
        
        PersonDetailService.fetch($stateParams.id);
    }];
