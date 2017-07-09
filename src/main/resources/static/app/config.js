var mainApp = angular.module('smetnerdemo', ['ngRoute', 'ngResource']);

mainApp.config(function ($routeProvider, $httpProvider) {
    $routeProvider
        .when('/', {
            controller: 'MainController',
            templateUrl: 'views/main/Main.html'
        })
        .otherwise({redirectTo: '/'});

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
});