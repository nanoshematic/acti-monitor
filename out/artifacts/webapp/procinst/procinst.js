/**
 * Created by aleksejbatrakov on 19/04/17.
 */
angular.module('activitiMonitor.procinst', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/procinst/:defId/:ID', {
            templateUrl: 'procinst/procinst.html',
            controller: 'ProcinstCtrl'
        });
    }])

    .controller('ProcinstCtrl', function ($scope, $routeParams, $http, $uibModal) {
        // var viewer = new BpmnJS({container: document.querySelector('#js-canvas'), height: '100%'});
        var viewer = new BpmnJS({container: '#diagram', height: '100%'});
        var eventBus = viewer.get('eventBus');

        var canvas = viewer.get('canvas'),
            overlays = viewer.get('overlays'),
            selection = viewer.get('selection'),
            selectionVisuals = viewer.get('selectionVisuals');


        var event = 'element.click';
        eventBus.on(event, function (e) {
                console.log(e.element);
                if (e.element.type === 'bpmn:UserTask') {
                    $scope.open(e.element.id);
                }
            }
        );

        function getRandomColor() {
            var letters = '0123456789ABCDEF'.split('');
            var color = '#';
            for (var i = 0; i < 6; i++) {
                color += letters[Math.floor(Math.random() * 16)];
            }
            return color;
        }

        $scope.open = function (taskId) {

            $scope.task = taskId;
            $http({
                method: 'GET',
                url: '/monitor/rest/process-instance/activity',
                params: {instance: $routeParams.ID, activity: taskId}
            }).then(function successCallback(response) {
                $scope.tasks = response.data;
                console.log('!!!$scope.tasks');
                console.log($scope.tasks);

                $scope.tasks.forEach(function (entry) {
                    entry.actName = entry.actName.replace('${docLabel} ', '');
                    entry.actName = entry.actName.replace(' ${d}', '');
                    entry.startTime = new Date(entry.startTime).toString();//.split(' GMT+0300 (MSK)').join('');
                    if (entry.endTime !== null) {
                        entry.endTime = new Date(entry.endTime).toString();//.split(' GMT+0300 (MSK)').join('');
                    } else {
                        entry.endTime = 'Task is in progress';
                    }
                    if (entry.dueDate !== null) {
                        entry.dueDate = new Date(entry.dueDate).toString();//.split(' GMT+0300 (MSK)').join('');
                    } else {
                        entry.dueDate = 'Not specified';
                    }

                })
            }, function errorCallback(response) {
                console.log(response.data);
            });

            var modalInstance = $uibModal.open({
                templateUrl: 'taskinfo.html',
                scope: $scope, //passed current scope to the modal
                size: 'lg'
            }).rendered.then(function () {
                var ctx = "myChart";
                $http({
                    method: 'GET',
                    url: '/monitor/rest/process-definition/tasks/duration',
                    params: {key: $routeParams.defId.split(':')[0], activity: taskId}
                }).then(function successCallback(response) {
                    var assignees = [];
                    var durations = [];
                    var colors = [];
                    response.data.forEach(function (entry) {
                        assignees.push(entry.assignee);
                        durations.push(entry.duration / 3600);
                        colors.push(getRandomColor());
                    });
                    var data = {
                        labels: assignees,
                        datasets: [
                            {
                                label: "Average task duration in munutes",
                                backgroundColor: "rgba(255,153,0,1)",
                                // backgroundColor: colors,
                                borderWidth: 1,
                                data: durations
                            }
                        ]
                    };

                    var myChart = new Chart(ctx, {
                        type: 'horizontalBar',
                        data: data
                        // options: {
                        //     scales: {
                        //         xAxes: [{
                        //             ticks: {
                        //                 userCallback: function (label, index, labels) {
                        //                     return moment(label).format("DD/MM/YY");
                        //                 }
                        //             }
                        //         }]
                        //     }
                        // }
                    });
                }, function errorCallback(response) {
                    console.log(response.data);
                });

            });

        };

        // $scope.toggleAnimation = function () {
        //     $scope.animationsEnabled = !$scope.animationsEnabled;
        // };

        var createDiagram = function (diagramKey, version) {
            $http({
                method: 'GET',
                url: '/monitor/rest/process-diagram',
                params: {key: diagramKey, version: version}
                // params: {key: diagramKey, version: '4'}
            }).then(function successCallback(response) {
                viewer.importXML(response.data, function (err) {
                    if (!err) {
                        var canvas = viewer.get('canvas'),
                            overlays = viewer.get('overlays');

                        var auth = window.btoa("user:pass");
                        var headers = {"Authorization": "Basic " + auth};
                        $http({
                            method: 'GET',
                            url: '/activiti-rest/service/process-instance/' + $routeParams.ID + '/highlights',
                            headers: headers

                        }).then(function successCallback(response) {
                            console.log(response.data);
                            response.data.flows.forEach(function (entry) {
                                canvas.addMarker(entry, 'highlight')
                            });
                            response.data.activities.forEach(function (entry) {
                                canvas.addMarker(entry, 'highlight')
                            });

                        }, function errorCallback(response) {
                            console.log(response.data);
                        });

                        $http({
                            method: 'GET',
                            url: '/monitor/rest/process-instance/activities',
                            params: {instance: $routeParams.ID}
                        }).then(function successCallback(response) {
                            var formated_date = '';
                            // $scope.tasks = response.data;
                            response.data.forEach(function (entry) {
                                var finDate = '';
                                if (entry.endTime !== null) {
                                    var taskDate = new Date(entry.endTime);
                                    finDate = taskDate.toString().split(' GMT+0300 (MSK)').join('');
                                }
                                var overlay = 'task-info';
                                var marker = 'highlight-normal-task';
                                if (entry.overDue === true) {
                                    overlay += '-warning';
                                    marker = 'highlight-overdue-task';
                                }

                                // overlays.add(entry.actId, 'note', {
                                //     show: {
                                //         minZoom: 0.0,
                                //         maxZoom: 5.0
                                //     },
                                //     position: {
                                //         bottom: 0,
                                //         left: 0
                                //     },
                                //     html: '<div class="' + overlay + '">'
                                //     + '<div>' + entry.assignee + '</div>'
                                //     + '<div>' + finDate + '</div>'
                                //     + '</div>'
                                // });

                                canvas.addMarker(entry.actId, marker);
                            });

                        }, function errorCallback(response) {
                            console.log(response.data);
                        });

                    } else {
                        console.log('something went wrong:', err);
                    }
                });


            }, function errorCallback(response) {
                console.log(response.data);
            });
        };

        var params = $routeParams.defId.split(':');
        createDiagram(params[0], params[1]);

    });
