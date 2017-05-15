/**
 * Created by aleksejbatrakov on 25/02/17.
 */
'use strict';

var appModule = angular.module('activitiMonitor',
['ui.bootstrap',
'ngRoute',
'activitiMonitor.procdef',
'activitiMonitor.procinst',
'activitiMonitor.dashboard']);

// angular.module('activitiMonitor', ['ngRoute'])
appModule.config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
    $locationProvider.hashPrefix('!');

    $routeProvider.otherwise({redirectTo: '/dashboard'});
}]);

appModule.controller('AppController', function ($scope, $http) {
    $scope.singleModel = 1;

    $scope.radioModel = 'Middle';

    $scope.checkModel = {
        left: false,
        middle: true,
        right: false
    };

    $scope.checkResults = [];

    $scope.$watchCollection('checkModel', function () {
        $scope.checkResults = [];
        angular.forEach($scope.checkModel, function (value, key) {
            if (value) {
                $scope.checkResults.push(key);
            }
        });
    });

    // var BpmnViewer = window.BpmnJS;
    // var viewer = new BpmnViewer({container: '#canvas'});

});