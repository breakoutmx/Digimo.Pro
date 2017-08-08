Application.$controller("DMP_Login_ScreenController", ["$scope", function($scope) {
    "use strict";

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

    $scope.onInitPrefab = function() {
        // this method will be triggered post initialization of the prefab.
    };

    /**
     * Reset the alert dialog module to default when the OK button is clicked.
     */
    $scope.resetModuleAlertDialog = function() {
        var result = {};
        result.title = $scope.Variables.moduleAlertDialogTitle.setValue('dataValue', "Important Notice!");
        result.subtitle = $scope.Variables.moduleAlertDialogText.setValue('dataValue', "Some unknown error occurred.  Please try your operation again and if this persists, contact support for additional help.");
        console.log("Set Module Alert Dialog Content: ", result);
    }

    $scope.start_helpbutton_userClick = function($event, $isolateScope) {
        // $scope.Variables.loginHelpTitle.dataSet.dataValue = 'Login with Secure Access Key';
        var result = {};
        result.title = $scope.Variables.loginHelpTitle.setValue('dataValue', "User Credentials");
        result.subtitle = $scope.Variables.loginHelpSubTitle.setValue('dataValue', "Login with your username and password");
        result.text = $scope.Variables.loginHelpText.setValue('dataValue', "When you create your account, you set a username and password.  Click on the 'User Credentials' option and enter it there to login.");
        console.log('Set help items: ', result);
    };

    $scope.start_helpbutton_accesskeyClick = function($event, $isolateScope) {
        // $scope.Variables.loginHelpTitle.dataSet.dataValue = 'Login with Secure Access Key';
        var result = {};
        result.title = $scope.Variables.loginHelpTitle.setValue('dataValue', "Secure Access Key");
        result.subtitle = $scope.Variables.loginHelpSubTitle.setValue('dataValue', "Login with your secure access key");
        result.text = $scope.Variables.loginHelpText.setValue('dataValue', "First, login once with your username and password and then in your profile, generate and download your secure access key.  Click on the 'Secure Access Key' option and enter it to login.");
        console.log('Set help items: ', result);
    };

    $scope.start_helpbutton_accessfileClick = function($event, $isolateScope) {
        var result = {};
        result.title = $scope.Variables.loginHelpTitle.setValue('dataValue', "Secure Access File");
        result.subtitle = $scope.Variables.loginHelpSubTitle.setValue('dataValue', "Login with your secure access file");
        result.text = $scope.Variables.loginHelpText.setValue('dataValue', "First, login once with your username and password and then in your profile, generate and download your secure access file.  Save it somewhere safe on your computer or other device.  Click on the 'Secure Access File' option and upload it to login.");
        console.log('Set help items: ', result);
    };


    $scope.login_options_menuSelect = function($event, $isolateScope, $item) {
        console.log("Login options item selected: ", $item);
        switch ($item) {
            case 'User Credentials':
                console.log('Open usercred_login_dialog');
                $scope.Widgets.usercred_login_dialog.open();
                break;
            case 'Secure Access Key':
                // console.log('Open accesskey_login_dialog');
                // $scope.Widgets.accesskey_login_dialog.open();
                var result = {};
                result.title = $scope.Variables.moduleAlertDialogTitle.setValue('dataValue', "Secure Access Key Support Coming!");
                result.subtitle = $scope.Variables.moduleAlertDialogText.setValue('dataValue', "This feature is not yet supported, but will be in the very near future.  Please keep an eye out for future app updates regarding this.");
                console.log("Set Module Alert Dialog Content: ", result);
                $scope.Widgets.module_alert_dialog_content.open();
                break;
            case 'Secure Access File':
                // console.log('Open accessfile_login_dialog');
                // $scope.Widgets.accessfile_login_dialog.open();
                var result = {};
                result.title = $scope.Variables.moduleAlertDialogTitle.setValue('dataValue', "Secure Access File Support Coming!");
                result.subtitle = $scope.Variables.moduleAlertDialogText.setValue('dataValue', "This feature is not yet supported, but will be in the very near future.  Please keep an eye out for future app updates regarding this.");
                console.log("Set Module Alert Dialog Content: ", result);
                $scope.Widgets.module_alert_dialog_content.open();
                break;
            default:
                // code
        }
    };

}]);

Application.$controller("usercred_login_dialogController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("accesskey_login_dialogController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;

        $scope.login_accesskey_formSubmit = function($event, $isolateScope, $formData) {
            console.log("Submit Login Access Key Form Data: ", $formData);
        };

    }
]);

Application.$controller("accessfile_login_dialogController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;

        $scope.fileupload1Select = function($event, $isolateScope) {
            var result = {};
            result.title = $scope.Variables.moduleAlertDialogTitle.setValue('dataValue', "Secure Access File Support Coming!");
            result.subtitle = $scope.Variables.moduleAlertDialogText.setValue('dataValue', "This feature is not yet supported, but will be in the very near future.  Please keep an eye out for future app updates regarding this.");
            console.log("Set Module Alert Dialog Content: ", result);
            $scope.Widgets.module_alert_dialog_content.open();
        };

    }
]);

Application.$controller("module_alert_dialog_contentController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;

        $scope.module_alert_dialog_contentOk = function($event, $isolateScope) {
            $scope.resetModuleAlertDialog();
            $scope.Widgets.module_alert_dialog_content.close();
        };

    }
]);