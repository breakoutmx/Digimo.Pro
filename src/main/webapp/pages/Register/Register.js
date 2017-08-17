/**
 * Registration
 * Step by step onboarding wizard for new premium or free users
 *
 * Related Links
 * --------------
 * https://www.gettoby.com/e/5adcqr0dkyz8
 *
 * Services
 * ----------
 * ServiceStripeListCustomers - Web/REST service for Stripe API :: List Customers
 *  @param(*) limit (int) the number of results, default 100, max 100
 *  @param(-) starting_after (string) the stripe customer ID used as forward pagination anchor
 *
 * Variables
 * ----------
 * $s.V.StripeListCustomersVariable - service variable binded to the ServiceStripeListCustomers service
 *  @method getData - get the current value of it's data accessed via the data prop (methodResult.data)
 *  @method invoke - async method to call the service (stripe api) and retrieve the customer list based on params
 *
 * StripeListCustomers - alias of $scope.Variables.StripeListCustomersVariable
 *  @method setInput {dataValue: value} - sets the input field binded to the service params (limit, starting_with)
 */

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
        /*
         * Setup scoped shorthand variables
         */
        $scope.onboardingUserData = $scope.Variables.onboardingUser.dataSet.dataValue;
    };

    //StripeServiceRetrieveCustomers
    $scope.onboardWizardStepAccountValidateNext = function($isolateScope, currentStep, stepIndex) {


    };

    // On wizard step load, disable the next button
    $scope.onboardWizardStepAccountValidateLoad = function($isolateScope, stepIndex) {
        if (typeof $scope.onboardingUserData === 'undefined' || !$scope.onboardingUserData.userid)
            $scope.Widgets.onboardWizardStepAccountValidate.disablenext = true;
        else
            $scope.Widgets.onboardWizardStepAccountValidate.disablenext = false;
    };


    $scope.onboardingForm1Success = function($event, $operation, $data) {
        console.log("onboardingForm1Success data", $data);
        $scope.onboardingUserData.userid = $data.id;
        $scope.onboardingUserData.userdata = $data;
        console.log("$scope.onboardingUserData Data", $scope.onboardingUserData);

        //Add new user object to current customer form / create relationship
        // $scope.Widgets.onboardingFormCustomer.formWidgets.id.datavalue = $scope.onboardingUserData.customerid;
        $scope.Widgets
            .onboardingFormCustomer.formWidgets.customerid.datavalue = $scope.Variables.onboardingUser.dataSet.dataValue.customerid;
        $scope.Widgets
            .onboardingFormCustomer.formWidgets.created.datavalue = $scope.Variables.onboardingUser.dataSet.dataValue.customerdata.created * 1000;
        $scope.Widgets
            .onboardingFormCustomer.formWidgets.email.datavalue = $scope.Variables.onboardingUser.dataSet.dataValue.customerdata.email;
        $scope.Widgets
            .onboardingFormCustomer.formWidgets.users.datavalue = $data;
        $scope.Widgets
            .onboardingFormCustomer.formWidgets.users.displayvalue = $data;
        //Submit form to create customer record
        $scope.Widgets.onboardingFormCustomer.save();

        //Add new user object to profile form / create relationship
        // $scope.Widgets.users.datavalue = $data;
        // $scope.Widgets.users.displayvalue = $data;
        $scope.Widgets.onboardingFormProfile.formWidgets.users.datavalue = $data;
        $scope.Widgets.onboardingFormProfile.formWidgets.users.displayvalue = $data;
        //Submit form to create profile record
        $scope.Widgets.onboardingFormProfile.save();
    };

    $scope.onboardingFormCustomerSuccess = function($event, $operation, $data) {
        console.log("onboardingFormCustomerSuccess Data", $data);
        $scope.onboardingUserData.customerid = $data.id;
        $scope.onboardingUserData.customerdata = $data;
        console.log("$scope.onboardingUserData Data", $scope.onboardingUserData);
    };

    $scope.onboardingFormProfileSuccess = function($event, $operation, $data) {
        console.log("onboardingFormProfileSuccess Data", $data);
        $scope.onboardingUserData.profileid = $data.id;
        $scope.onboardingUserData.profiledata = $data;
        console.log("$scope.onboardingUserData Data", $scope.onboardingUserData);
        // Update onboarding form 2 in wizard user link with data object
        $scope.Widgets.onboardingForm2.formWidgets.users.datavalue = $scope.onboardingUserData.userdata;
        $scope.Widgets.onboardingForm2.formWidgets.users.displayvalue = $scope.onboardingUserData.userid;
    };

}]);

Application.$controller("onboardingForm1Controller", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;

        $scope.saveAction = function($event) {
            // debugger;
            console.log('onboard Wizard Acct Valid Submit', {
                wm: wm,
                scope: $scope
            });
            // $scope.Variables.UserServiceCreateTempUsername.setOperation('invokeOperation');
            // var tempusername = $scope.Variables.UserServiceCreateTempUsername.dataSet.value;
            // $scope.Widgets.onboardingForm1.formWidgets.username.datavalue = tempusername;

            var StripeListCustomers = $scope.Variables.StripeListCustomersVariable;

            function handleCustomersError(error) {
                alert('There was an error while validating the email, please try refreshing and submitting the form again.  If the problem persists, contact support.')
                console.error('StripeServiceListCustomers invoke error', error);
            }

            /**
             * Success callback function for async Service invocation
             * Get service var dataSet value: https://www.wavemaker.com/learn/app-development/variables/static-variable/#getData
             */
            function handleCustomersSuccess(data) {
                console.log('handleCustomersSuccess invoked');
                // Customers Success Data Object {object: "list", data: Array(10), has_more: true, url: "/v1/customers"}
                // Customers (10) [Object, Object, Object, Object, Object, Object, Object, Object, Object, Object]
                // Form widgets Object {username: n, gridcolumn11_1: n, gridrow7: n, password: n, gridcolumn12: n…}
                // Check customer for email match Object {id: "cus_BBrZV5nHoHeOiM", object: "customer", account_balance: 0, created: 1502422218, currency: null…}

                function findCustomerEmail() {
                    console.log('findCustomerEmail invoked');
                    // var Customers = $scope.Variables.StripeListCustomersVariable.dataSet.data;  //or...
                    var Customers = StripeListCustomers.getData().data;
                    console.log('Customers', Customers);
                    console.log('Form widgets', $scope.Widgets.onboardingForm1.formWidgets);

                    for (var i = 0; i < Customers.length; i++) {
                        var customer = Customers[i];
                        console.log('Check customer for email match', customer);
                        if (customer.email === $scope.Widgets.onboardingForm1.formWidgets.email.datavalue) {
                            console.log('Found Valid Customer', customer);
                            // Set Result Dialog Partial Param binded to it's content image to valid account success
                            $scope.Variables.onboardingResultDialogImage.setData({
                                'dataValue': 'resources/images/swaydialog/OB_Wizard_Account_Valid_Premium.png'
                            });

                            //Update onboardingUser variable with customer data from stripe for now
                            $scope.Variables.onboardingUser.dataSet.dataValue.customerid = customer.id;
                            $scope.Variables.onboardingUser.dataSet.dataValue.customerdata = {
                                id: customer.id,
                                created: customer.created,
                                email: customer.email
                            };
                            console.log("$scope.onboardingUserData Data - customer", $scope.Variables.onboardingUser.dataSet.dataValue);

                            // Set user name to Stripe customer ID
                            // $scope.Widgets.onboardingForm1.formWidgets.username.datavalue = customer.id;

                            // Set the role to 'premiumuser'
                            $scope.Widgets.onboardingForm1.formWidgets.role.datavalue = 'premiumuser';
                            // Submit the form to create the new temporary user record
                            $scope.Widgets.onboardingForm1.save();

                            //Update Customer Live Form
                            // $scope.Widgets.onboardingFormCustomer.formWidgets.customerid.datavalue = customer.id;
                            // $scope.Widgets.onboardingFormCustomer.formWidgets.created.datavalue = customer.created * 1000;
                            // $scope.Widgets.onboardingFormCustomer.formWidgets.email.datavalue = customer.email;
                            //Submit form to create customer record
                            // $scope.Widgets.onboardingFormCustomer.save();



                            // Open the dialog & enable next
                            $scope.Widgets.onboardingResultDialogContent.open();
                            $scope.Widgets.onboardWizardStepAccountValidate.disablenext = false;

                            // Stop iterating here...
                            break;
                        } else {
                            if (i === (Customers.length - 1)) {
                                if (data.has_more === true) {
                                    console.log('Did not find customer, but additional records exist');
                                    // Run check again with current ID as startingAfterId param
                                    checkCustomerResults(customer.id);
                                } else {
                                    console.log('No customer found with email: ' + $scope.Widgets.onboardingForm1.formWidgets.email.datavalue);
                                }
                            }
                        }
                    }
                }

                findCustomerEmail();
            }

            function checkCustomerResults(startingAfterId) {
                // startingAfterId = null;
                // startingAfterId = 'cus_BBrZV5nHoHeOiM';

                // TODO: Create Global Function to set variable data and then set that
                // value to a service variable input field (the following 6 lines)
                $scope.Variables.ServiceParamLimit.setData({
                    dataValue: 10
                });
                StripeListCustomers.setInput({
                    "limit": $scope.Variables.ServiceParamLimit.getValue('dataValue')
                });

                if (!startingAfterId) {
                    console.log('No Starting Id');
                    // $scope.Variables.ServiceParamLimit.setValue('dataValue', 50);
                    $scope.Variables.StripeListCustomersVariable.invoke(null, handleCustomersSuccess, handleCustomersError);
                } else {
                    console.log('Starting Id: ', startingAfterId);
                    //Set value of service param 'starting_after' to startingAfterId
                    $scope.Variables.ServiceParamStartingAfter.setData({
                        dataValue: startingAfterId
                    });
                    //Assign value of service param 'starting_after' to Stripe Service variable's matching input field value
                    StripeListCustomers.setInput({
                        "starting_after": $scope.Variables.ServiceParamStartingAfter.getValue('dataValue')
                    });
                    StripeListCustomers.invoke(null, handleCustomersSuccess, handleCustomersError);
                }
            }

            checkCustomerResults();
        };

    }
]);

Application.$controller("onboardingWizardDialogContentController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("swaySystemMessageDialogController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("onboardingResultDialogContentController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("onboardingFormCustomerController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);





Application.$controller("onboardingFormProfileController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("onboardingFormChargeController", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);

Application.$controller("onboardingForm2Controller", ["$scope",
    function($scope) {
        "use strict";
        $scope.ctrlScope = $scope;
    }
]);