angular.module('smetnerdemo')
    .controller('MainController', function ($scope, $rootScope, User) {

        $scope.editAll = false;

        $scope.getAllUsers = function () {
            var users = User.query({}, function () {
                $scope.allUsers = users;
            });
        };
        $scope.saveAll = function (users) {
            var users = User.updateAll($scope.allUsers, function () {
                $scope.allUsers = users;
                $scope.editAll = false;
                $scope.error = false;
            }, function (error) {
                if (error.status === 400) {
                    $scope.error = "Please amend phone numbers. The number of characters in the phone number must be 13 and choose type";
                }
            });
        };

        $scope.update = function (user) {
            return $scope.user = User.update({id: user.id}, user, function () {
                $scope.error = false;
            }, function (error) {
                $scope.error = "Please amend phone number and choose type";
                User.get({id: user.id}, function (success) {
                    angular.copy(success, user);

                });
            });
        };

        $scope.cancel = function () {
            $scope.editAll = false;
            $scope.error = false;
            $scope.getAllUsers();
        };

        $scope.editPhone = function (user) {
            user.editPhone = true;
        };
        $scope.donePhone = function (user) {
            user.editPhone = false;
            user = $scope.update(user);
        };
        $scope.editType = function (user) {
            user.editType = true;
        };
        $scope.doneType = function (user) {
            user.editType = false;
            user = $scope.update(user);
        };

        $scope.editComment = function (user) {
            user.editComment = true;
        };
        $scope.doneComment = function (user) {
            user.editComment = false;
            user = $scope.update(user);
        };

        $scope.getReport=function () {

        }

    });