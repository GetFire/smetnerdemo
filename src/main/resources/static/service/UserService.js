angular.module('smetnerdemo')
    .factory('User', ['$resource', function ($resource) {
        return $resource('/api/users/:id', {id: '@id'}, {
            update: {
                method: 'PUT'
            },
            delete: {
                method: 'DELETE'
            },
            updateAll: {
                method: 'PUT',
                url: '/api/users/all',
                isArray: true
            }
        });
    }]);