angular.module('smetnerdemo')
    .controller('UserController', function ($scope, $rootScope, User, $location) {

        $scope.createUser = function () {
            User.addUser($scope.user, function (success) {
                $location.path("/")
            }, function (error) {
                $scope.error = true;
                console.log(error);
                $scope.errorMessage = error.status;
            });
        };

        $scope.submit = function () {
            $scope.createUser($scope.user);
        };


    });