angular.module('smetnerdemo')
    .controller('UserController', function ($scope, $rootScope, User, $location, $window) {

        $scope.createUser = function () {
            User.addUser($scope.user, function (success) {
                $rootScope.newUserId = success.id;
                $location.path("/")
            }, function (error) {
                $scope.error = true;
                console.log(error);
                $scope.errorMessage = error.status;
            });
        };

        $scope.cancel = function () {
            $rootScope.newUserId = null;
            $window.history.back()
        };

        $scope.submit = function () {
            $scope.createUser($scope.user);
        };


    });