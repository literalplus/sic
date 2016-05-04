var PersonNameModalController = ['$uibModalInstance',
    function ($uibModalInstance) {
        var ctrl = this;
        this.name = '';
        
        this.ok = function () {
            $uibModalInstance.close(ctrl.name);
        };
        
        this.cancel = function () {
            $uibModalInstance.dismiss('cancelled by user');
        };
    }];
