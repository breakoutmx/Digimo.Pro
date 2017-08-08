Application.$controller("LoginPageController", ["$scope", function($scope) {
    "use strict";

    /* perform any action on widgets within this block */
    $scope.onPageReady = function() {
        /*
         * widgets can be accessed through '$scope.Widgets' property here
         * e.g. to get value of text widget named 'username' use following script
         * '$scope.Widgets.username.datavalue'
         
        var progress = 0;
        var updateLoginCheckProgress = setInterval(function() {
            progress = $scope.Widgets.progress1.datavalue !== 'undefined' ? parseInt($scope.Widgets.progress1.datavalue) + 10 : 0;
            $scope.Widgets.progress1.datavalue = progress;
            if (progress === 100) {
                clearInterval(updateLoginCheckProgress);
            }
            console.log('Update Login Check Progress: ' + $scope.Widgets.progress1.datavalue);
        }, 150);*/
    };

}]);


Application.$controller("login1Controller", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("loginFormController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);