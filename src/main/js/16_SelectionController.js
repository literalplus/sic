var SelectionController = [
    function () {
        var selectedQuotes = [];

        this.select = function (quote) {
            var index = _.indexOf(selectedQuotes, quote.id);

            if (index === -1) {
                selectedQuotes.push(quote.id);
            } else {
                selectedQuotes.splice(index);
            }
        };
        
        this.isSelected = function (quote) {
            return _.indexOf(selectedQuotes, quote.id) !== -1;
        };

        this.getQuoteString = function () {
            return _.join(selectedQuotes);
        };

        this.getSelectedQuoteIds = function () {
            return selectedQuotes;
        };

        this.resetSelection = function () {
            selectedQuotes = [];
        };
        
        this.hasSelection = function () {
            return selectedQuotes.length !== 0;
        };
    }];
