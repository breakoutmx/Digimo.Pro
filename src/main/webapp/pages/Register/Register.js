Application.$controller("RegisterPageController", ["$scope", function($scope) {
    "use strict";

    /* perform any action on widgets within this block */
    $scope.onPageReady = function() {
        /*
         * widgets can be accessed through '$scope.Widgets' property here
         * e.g. to get value of text widget named 'username' use following script
         * '$scope.Widgets.username.datavalue'
         */
        $scope.Widgets.signupFormMessage.show = false;
    };


    $scope.regPage1SubmitButtonClick = function($event, $isolateScope) {

        var emailValidationMatch = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i;

        if (!$scope.Widgets.signupForm.formWidgets.wmSignupEmail.datavalue)
        // Check the email for validation errors...
        {
            $scope.Widgets.signupFormMessage.type = "error";
            $scope.Widgets.signupFormMessage.caption = "Error: Please enter your TDM email address (the one you used to purchase).";
            $scope.Widgets.signupFormMessage.show = true;
        } else if (!$scope.Widgets.signupForm.formWidgets.wmSignupEmail.datavalue.match(emailValidationMatch))
        // Check email validation via regex match
        {
            $scope.Widgets.signupFormMessage.type = "error";
            $scope.Widgets.signupFormMessage.caption = "Error: Please enter a valid email address, in a valid format (eg: address@domain.com).";
            $scope.Widgets.signupFormMessage.show = true;
        } else if (
            $scope.Widgets.signupForm.formWidgets.wmSignupPwd.datavalue === '' ||
            $scope.Widgets.signupForm.formWidgets.wmSignupEmail.datavalue === null ||
            !$scope.Widgets.signupForm.formWidgets.wmSignupPwd.datavalue)
        // Check the email for validation errors...
        {
            $scope.Widgets.signupFormMessage.type = "error";
            $scope.Widgets.signupFormMessage.caption = "Error: Please enter a password for your account.";
            $scope.Widgets.signupFormMessage.show = true;
        } else if (!$scope.Widgets.signupForm.formWidgets.wmSignupUsername.datavalue)
        // Check the username for validation errors...
        {
            $scope.Widgets.signupFormMessage.type = "error";
            $scope.Widgets.signupFormMessage.caption = "Error: Please enter a username for your account.";
            $scope.Widgets.signupFormMessage.show = true;
        } else if ($scope.Widgets.signupForm.formWidgets.wmSignupPwd.datavalue === $scope.Widgets.signupForm.formWidgets.wmSignupConPwd.datavalue) {
            $scope.Variables.regPage1Variable.insertRecord();
            $scope.Widgets.signupFormMessage.type = "success";
            $scope.Widgets.signupFormMessage.caption = "Registration Successful. Redirecting to login...";
            $scope.Widgets.signupFormMessage.show = true;
            var tscope = $scope;
            setTimeout(function() {
                tscope.Variables.goToPage_Access.invoke();
            }, 2500)
        } else {
            $scope.Widgets.signupFormMessage.type = "error";
            $scope.Widgets.signupFormMessage.caption = "Error: The password and confirmation passwords do not match.  Please check and try again.";
            $scope.Widgets.signupFormMessage.show = true;
        }
    };
}]);

Application.$controller("onboardingForm1Controller", ["$scope",
	function($scope) {
		"use strict";
		$scope.ctrlScope = $scope;
	}
]);