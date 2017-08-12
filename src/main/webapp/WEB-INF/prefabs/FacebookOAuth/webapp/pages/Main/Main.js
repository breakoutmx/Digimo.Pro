Application.$controller("FacebookOAuthController", ["$scope", function ($scope) {
    "use strict";
    var LOGIN_MODE = {
        'AUTOMATIC': "auto"
    };

    /* 
     * This function will be invoked when any of this prefab's property is changed
     * @key: property name
     * @newVal: new value of the property
     * @oldVal: old value of the property
     */
    function propertyChangeHandler(key, newVal, oldVal) {
        /*
         switch (key) {
         case "prop1":
         // do something with newVal for property 'prop1'
         break;
         case "prop2":
         // do something with newVal for property 'prop2'
         break;
         }
         */
    }
        /* register the property change handler */
    $scope.propertyManager.add($scope.propertyManager.ACTIONS.CHANGE, propertyChangeHandler);


    $scope.OAuthHandlerServiceGetLoginURLonSuccess = function (variable, data) {
        window.top.location = data;
    };

    $scope.onInitPrefab = function () {
        $scope.login = $scope.Variables.OAuthHandlerServiceGetLoginURL.invoke;
    };

    $scope.OAuthHandlerServiceGetAccessTokenonSuccess = function(variable, data) {
        if (data) {
            $scope.accesstoken = data;
            $scope.Widgets.signInButton.disabled = true;
            if ($scope.loginsuccessmessage) {
                $scope.Variables.loginSuccess.invoke();
            }
            Utils.triggerFn($scope.onLoginsuccess);
        } else if ($scope.loginmode === LOGIN_MODE.AUTOMATIC) {
            $scope.Variables.OAuthHandlerServiceGetLoginURL.update();
        }
    };

}]);