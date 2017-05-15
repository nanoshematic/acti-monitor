angular.module('activitiMonitor.dashboard', ['ngRoute'])

    .config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/dashboard', {
            templateUrl: 'dashboard/dashboard.html',
            controller: 'DashboardCtrl'
        });
    }])

    .controller('DashboardCtrl', function($scope, $http, $routeParams, $location, $uibModal) {
        $http({
            method: 'GET',
            url: '/monitor/rest/process-definition/all'
        }).then(function successCallback(response) {
            $scope.processes = response.data;
            console.log(response.data)
        }, function errorCallback(response) {
            console.log(response.data);
            // called asynchronously if an error occurs
            // or server returns response with an error status.
        });

        var openModal = function () {
            $scope.overdueTasks = null;
            $scope.atRiskTasks = null;
            $http({
                method: 'GET',
                url: '/monitor/rest/process-definition/overdue-tasks',
                params: {processDef: $scope.selected.key}
            }).then(function successCallback(response) {
                $scope.overdueTasks = response.data;
            }, function errorCallback(response) {
                console.log(response.data);
            });

            $http({
                method: 'GET',
                url: '/monitor/rest/process-definition/atrisk-tasks',
                params: {processDef: $scope.selected.key}
            }).then(function successCallback(response) {
                $scope.atRiskTasks = response.data;
            }, function errorCallback(response) {
                console.log(response.data);
            });

            var modalInstance = $uibModal.open({
                templateUrl: 'processInfo.html',
                scope: $scope, //passed current scope to the modal
                size: 'lg'
            }).rendered.then(function () {
                var ctx = "tasksChart";
                var data = {
                    labels: [
                        "Overdue",
                        "At Risk",
                        "Normal"
                    ],
                    datasets: [
                        {
                            data: [$scope.selected.overdueCount, $scope.selected.atRiskCount, $scope.selected.count - $scope.selected.atRiskCount - $scope.selected.overdueCount],
                            backgroundColor: [
                                "#ff1826",
                                "#ebb22a",
                                "#1ab42c"
                            ]
                        }]
                };

                var myPieChart = new Chart(ctx,{
                    type: 'pie',
                    data: data
                });

            });
        };


        $scope.setSelected = function () {
            $scope.selected = this.process;
            openModal();
            // $location.path('procdef/' + $scope.selected.key);
            // console.log($scope.selected);
            // createDiagram($scope.selected.key);
        };

        $scope.openInstance = function() {
            $location.path('procinst/' + this.task.procDefId + '/' + this.task.procInstId)
        };

        $scope.openDefinition = function () {
            $location.path('procdef/' + $scope.selected.key);

        };

    });