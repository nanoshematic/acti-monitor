angular.module('activitiMonitor.procdef', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/procdef/:ID', {
            templateUrl: 'procdef/procdef.html',
            controller: 'ProcdefCtrl'
        });
    }])

    .controller('ProcdefCtrl', function ($scope, $routeParams, $http, $location) {
        var viewer = new BpmnJS({container: document.querySelector('#js-canvas'), height: 600});

        var eventBus = viewer.get('eventBus');

        $scope.showDiagram = false;
        $scope.processName = 'Process Name';

        $scope.versions = ['1', '2', '3'];
        $http({
            method: 'GET',
            url: '/monitor/rest/process-definition/versions',
            params: {key: $routeParams.ID}
            // params: {key: diagramKey, version: '4'}
        }).then(function successCallback(response) {
            console.log(response.data);
            // angular.forEach(response.data, function(value, key) {
            //     console.log(value.count);
            // });
            // versions.clear();
            $scope.versions = response.data;
            console.log('ver');
            console.log($scope.versions);
            // response.data.forEach(function(entry) {
            // console.log(entry.actId);
            // console.log(entry.count);
            // versions.push(entry);
            // });
        }, function errorCallback(response) {
            console.log(response.data);
        });


        $scope.setVersion = function () {
            updateDiagram(this.def.key, this.def.version);
        };

        $scope.openInstance = function () {
            $location.path('procinst/' + this.instance.procDefId + '/' + this.instance.procInstId);
        };

        var events = [
            'element.hover',
            'element.out',
            'element.click',
            'element.dblclick',
            'element.mousedown',
            'element.mouseup'
        ];

        var canvas = viewer.get('canvas'),
            overlays = viewer.get('overlays'),
            selection = viewer.get('selection'),
            selectionVisuals = viewer.get('selectionVisuals'),
            elementRegistry = viewer.get('elementRegistry');

        var ev = 'diagram.click';
        eventBus.on(ev, function (e) {
            console.log('diagram-click!');
        });

        var event = 'element.click';
        eventBus.on(event, function (e) {
                // e.element = the model element
                // e.gfx = the graphical element
                console.log('click!');
                console.log(e.element.width);
                console.log(e.element.incoming[0]);
                console.log(e.element.outgoing[0]);


                // // var diagramHeight = document.getElementsByTagName('svg')[0].style.height.replace(/px/,'') || 300;
                // var diagramHeight = 300;
                // // var diagramWidth = document.getElementsByTagName('svg')[0].style.width.replace(/px/,'') || 300;
                // var diagramWidth = 300;
                // var diagramId = 'djs-container';
                // var heatmapElement = $('<div id="heatmapArea" style="position: absolute;"/>')
                //     .width(diagramWidth)
                //     .height(diagramHeight);
                // $('div#'+diagramId).parent().prepend(heatmapElement);
                // console.log('element ');
                // console.log(document.getElementById('heatmapArea'));
                // // var config = {
                // //     "radius": 30,
                // //     "visible": true,
                // //     "container":document.getElementById('heatmapArea')
                // // };
                // //
                // // var heatmap = h337.create(config);


                //var bpmnElements = $scope.$parent.processDiagram.bpmnElements;

                // var heatmapElement = $('<div class="djs-heatmap-container" style="position: absolute;"/>')
                // $('.djs-container').append(heatmapElement);
                // console.log('selector');
                // console.log(document.querySelector('.djs-container'));
                // console.log(document.getElementById('djs-container'));


                // var x = elementRegistry.get('usertask1').x;
                // var y = elementRegistry.get('usertask1').y;

                console.log(e.element.x);
                console.log(e.element.y);


                console.log(event + ' on ' + e.element.id);
                console.log(e.element.type);

                if (!~e.element.type.indexOf('Flow')) {
                    console.log(selection);
                    console.log(e.element.id);

                    // canvas.addMarker(e.element.id, 'highlight');
                    selection.select(e.element.id);
                    // selectionVisuals.addMarker(e.element.id)

                    overlays.add(e.element.id, 'note', {
                        show: {
                            minZoom: 0.0,
                            maxZoom: 5.0
                        },
                        position: {
                            top: 5,
                            right: 50
                        },
                        html: '<div class="diagram-note">Mixed up the labels?</div>'
                        // html: '<div class="diagram-note">Mixed up the labels?</div>'
                    })
                }
                else {
                    overlays.add(e.element.id, 'note', {
                        show: {
                            minZoom: 0.0,
                            maxZoom: 5.0
                        },
                        position: {
                            top: 5,
                            left: 0
                        },
                        html: '<div class="diagram-note">Mixed up the labels?</div>'
                        // html: '<canvas class="heatmap-canvas" width="920" height="600" style="position: absolute; left: 0px; top: 0px;">'
                    })
                }

            }
        );

        $scope.heatmap = null;

        $scope.showHeatMap = function () {
            if ($scope.heatmap === null) {
                $scope.heatmap = h337.create({
                    // container: document.querySelector('.panel-body')
                    // container: document.querySelector('#js-canvas')
                    container: document.querySelector('.djs-container')
                    // container: document.querySelector('.viewport')
                });
            }
            var oldWidth = parseFloat(document.querySelector('.heatmap-canvas').width);
            var oldHeight = parseFloat(document.querySelector('.heatmap-canvas').height);
            // document.querySelector('.heatmap-canvas').width = oldWidth * 10;
            // document.querySelector('.heatmap-canvas').height = oldHeight * 10;


            // var x = e.element.x;
            // var y = e.element.y;
            $http({
                method: 'GET',
                url: '/monitor/rest/process-definition/activities-count-all',
                params: {key: $routeParams.ID}
            }).then(function successCallback(response) {
                console.log(elementRegistry);
                console.log(canvas);
                var dataPoints = [];
                var maxCount = 0;
                response.data.forEach(function (entry) {
                    var x = elementRegistry.get(entry.actId).x;
                    var y = elementRegistry.get(entry.actId).y;

                    var x_end = x + elementRegistry.get(entry.actId).width;
                    var y_end = y + elementRegistry.get(entry.actId).height;

                    for (var i = x; i <= x_end; i += 10) {
                        for (var j = y; j <= y_end; j += 10) {
                            dataPoints.push({x: i, y: j, value: entry.count});
                        }
                    }

                    maxCount = Math.max(maxCount, entry.count);


                    // dataPoints.push({x: x, y: y, value: entry.count});

                });
                console.log(dataPoints);
                $scope.heatmap.setData({
                    max: maxCount * 8,
                    data: dataPoints
                });
            }, function errorCallback(response) {
                console.log(response.data);
            });


        };

        $scope.hideHeatMap = function () {
            if ($scope.heatmap) {
                $(".heatmap-canvas").remove();
                $scope.heatmap = null;
                // $scope.heatmap.removeData();
            }
        };

        $scope.transformHeatMap = function () {
            if ($scope.heatmap) {
                var transform = document.querySelector('.djs-overlay-container').style.transform;
                console.log(transform.replace(/[^0-9\-.,]/g, '').split(',')[0]);
                var scale = parseFloat(transform.replace(/[^0-9\-.,]/g, '').split(','));
                document.querySelector('.heatmap-canvas').style.transform = transform;
                var oldWidth = parseFloat(document.querySelector('.heatmap-canvas').width);
                var oldHeight = parseFloat(document.querySelector('.heatmap-canvas').height);
                // document.querySelector('.heatmap-canvas').width = oldWidth * 10;
                // document.querySelector('.heatmap-canvas').height = oldHeight * 10;
                // document.querySelector('.heatmap-canvas').width = oldWidth / scale;
                // document.querySelector('.heatmap-canvas').height = oldHeight / scale;
                // $scope.heatmap.repaint();
                console.log(document.querySelector('.heatmap-canvas'));

                // $(".heatmap-canvas").remove();
                // $scope.heatmap = null;
                // $scope.heatmap.removeData();
            }
        };

        $scope.cli = function () {
            console.log('click!');
            overlays.clear();
            overlays.hide();
        };

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
                    } else {
                        console.log('something went wrong:', err);
                    }
                });
                ;

                $http({
                    method: 'GET',
                    url: '/monitor/rest/process-definition/activities-count',
                    params: {key: diagramKey, version: version}
                    // params: {key: diagramKey, version: version}
                }).then(function successCallback(response) {
                    console.log(response.data);
                    // angular.forEach(response.data, function(value, key) {
                    //     console.log(value.count);
                    // });
                    response.data.forEach(function (entry) {
                        overlays.add(entry.actId, 'note', {
                            show: {
                                minZoom: 0.0,
                                maxZoom: 5.0
                            },
                            position: {
                                top: -10,
                                right: 10
                            },
                            html: '<div class="task-count">' + entry.count + '</div>'
                        })
                    });
                }, function errorCallback(response) {
                    console.log(response.data);
                });
            }, function errorCallback(response) {
                console.log(response.data);
            });

        };

        var currentKey = null;
        var currentVersion = null;

        var updateDiagram = function (diagramKey, version) {
            currentKey = diagramKey;
            currentVersion = version;
            createDiagram(diagramKey, version);
            fillInstances(diagramKey, version)
        };

        var fillInstances = function (diagramKey, version) {
            $http({
                method: 'GET',
                url: '/monitor/rest/process-definition/instances',
                params: {key: diagramKey, version: version}
            }).then(function successCallback(response) {
                console.log('insta');
                console.log(response.data);
                $scope.instances = response.data;
            }, function errorCallback(response) {
                console.log(response.data);
            });


        };

        $scope.showDiagramFunc = function () {
            $scope.showDiagram = true;
            updateDiagram(currentKey, currentVersion);
            // $("#definitionContextTabs").tabs({active: 1});
            $("#diagramTab").attr('class', 'active');
            $("#overviewTab").attr('class', null);

        };

        $scope.showOverviewFunc = function () {
            $scope.showDiagram = false;
            $("#overviewTab").attr('class', 'active');
            $("#diagramTab").attr('class', null);
            // updateDiagram(currentKey, currentVersion);
        };


        updateDiagram($routeParams.ID, null);
    });