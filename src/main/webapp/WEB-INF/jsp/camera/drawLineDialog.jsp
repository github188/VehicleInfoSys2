<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div id="draw_line_dialog">
    <div id="draw_line">
    </div>
    <fieldset style="width: 50%;display: inline">
        <legend>识别配置</legend>
        <label>宽度放大:<input class="zoomWidth" style="width: 50px" value=1 type="text">倍</label>
        <label>&ensp;&ensp;高度放大:<input class="zoomHeight" style="width: 50px" value=1 type="text">倍</label><br>
        <label>最小宽度:<input class="minimum_width" style="width: 50px" value=400 type="text">像素</label>
        <label>最大宽度:<input class="maximum_height" style="width: 50px" value=800 type="text">像素</label><br>

        <label id="dropFrame" style="display: none">抽&ensp;&ensp;&ensp;&ensp;帧:<input class="dropFrame" style="width: 50px" value=0 type="text">帧</label>
        <div  id="detectmode" style="display: none">
            侦测模式:&nbsp
           <!--  <label><input name="detectMode" value=-1 checked type="radio">不指定&nbsp</label> -->
            <label><input name="detectMode" value=0 checked type="radio">车头&nbsp</label>
            <label><input name="detectMode" value=1 type="radio">车尾&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <label id="video_detectMode_id" style="display: none"><input name="video_detectMode" value=1 checked type="checkbox">自动判定&nbsp</label>
        </div>
        <label>配置文件名模板:<input name="dateFormatTemp" style="width: 180px" type="text" maxlength="50"></label>
    </fieldset>
    <fieldset style="width: 45%;display: inline">
        <legend>画图模式</legend>
        <label><input name="areaType" value=1 checked type="radio">感兴趣区</label>
        <label><input name="areaType" value=2 type="radio">非感兴趣区</label>
    </fieldset>
</div>

<script type="text/javascript">
    (function () {
        var REALTIME_TASK = 1,
                OFFLINE_TASK = 2,
                BAT_TASK = 3,
                REALIMAGE_TASK = 4,
                OFFILINE_IMAGE = 1,
                OFFLINE_VIDEO = 2,
                OFFLINE_DIR = 3,
                dialog = $('#draw_line_dialog'),
                dialog_html = dialog.html();
        dialog.empty();

        window.drawLine = function (id, path, type, paramStr, resourceType,callback) {
            //临时插入,如果是目录进来就取第一张图
            if (path == "null") {
                $.ajax({
                    url: '/resource/startBatTask.action?parentId=' + id,
                    type: 'post',
                    data: {"id": id},
                    async: false,
                    success: function (p) {
                        var row = p.rows[0];
                        path = row.bigUrl;
                    }
                })
            }
            var followarea = null;

            if (paramStr && (/\d/).test(paramStr)) {
                followarea = paramStr;
            }
            var img = new Image();

            var index = path.lastIndexOf("/"),uploadPath = path.substring(0,index+1),fileName = path.substring(index+1);
            path = uploadPath + encodeURIComponent(fileName);

            img.src = path;

            img.onload = function () {
                var task = {
                            id: id,
                            width: img.width,
                            height: img.height,
                            followarea: ''
                        },
                        drawing = false,
                        polygons = [],
                        getCoordinate = function (e) {
                            return {
                                x: e.offsetX || e.layerX,
                                y: e.offsetY || e.layerY
                            }
                        },
                        generatePath = function (arr) {
                            return arr.map(function (p, i) {
                                        return (i == 0 ? 'M' : "L") + p.x + ',' + p.y
                                    }).join('') + 'Z';
                        };
                dialog.html(dialog_html);

                if (resourceType == OFFLINE_VIDEO || type == REALTIME_TASK) {
                    dialog.find('#dropFrame').show();
                    dialog.find('#detectmode').show();
                    dialog.find('#video_detectMode_id').show();                    
                }
                if (resourceType == OFFILINE_IMAGE) {
                  dialog.find('#detectmode').show();
                }
                if (resourceType == BAT_TASK) {
                    dialog.find('#detectmode').show();
                }
                if (type == REALIMAGE_TASK) {
                    dialog.find('#detectmode').show();
                }

                var paper = Raphael('draw_line', task.width, task.height),
                        image = paper.image(path, 0, 0, task.width, task.height);

                if (followarea) {
                    task.followarea = followarea;
                    polygons = followarea.split('#').map(function (e) {
                        var _points = e.split('|').map(function (xy) {
                                    var arr=xy.split(',');
                                    return {x:arr[0],y:arr[1]}
                                }),
                                _path = generatePath(_points);
                        return {
                            el: paper.path(_path).attr({
                                stroke: "yellow",
                                fill: 'red',
                                'fill-opacity': 0.2
                            }),
                            points: _points
                        }
                    });
                }

                dialog.dialog({
                    title: '画感兴趣区域(单击开始,双击结束,支持多个)',
                    buttons: [{
                        text: '确定',
                        iconCls: 'icon-ok',
                        disabled:false,
                        handler: function () {
                            if (drawing) {
                                $.messager.alert('提示', '请画完感兴趣区域！');
                                return;
                            }
                            //点击后禁用
                            $(this).linkbutton("disable");
                            //取识别参数
                            //#是多边形分隔符,|是点分隔符
                            //126,84|331,76|296,270|164,257#410,94|535,109|520,293|349,294
                            task.dropFrame = dialog.find('.dropFrame').val();
                            task.zoomWidth = dialog.find('.zoomWidth').val() * 100 | 0;
                            task.zoomHeight = dialog.find('.zoomHeight').val() * 100 | 0;
                            task.minimumWidth = dialog.find('.minimum_width').val() * 1 | 0;
                            task.maximumHeight = dialog.find('.maximum_height').val() * 1 | 0;
                            task.detectMode = dialog.find('input[name=detectMode]:checked').val();
                            task.dateFormatTemp = dialog.find('input[name=dateFormatTemp]').val();
                            task.vedioDetectMode = dialog.find('input[name=video_detectMode]:checked').val() || 0;
                            task.areaType = dialog.find('input[name=areaType]:checked').val();
                            task.followarea = polygons.map(function (polygon) {
                                return polygon.points.map(function (p) {
                                    return p.x + ',' + p.y;
                                }).join('|');
                            }).join('#');

                            task.type=type;
                            if(resourceType != OFFLINE_VIDEO){
                            	task.vedioDetectMode = 0;
                            }
                            if (type == REALTIME_TASK){
                            	task.vedioDetectMode = dialog.find('input[name=video_detectMode]:checked').val() || 0;
                            }
                            if (type == OFFLINE_TASK || type == BAT_TASK) {
                                $.post('task/startOffLineTask.action', task, function () {
                                    var tab = util.getSelectedTab();
                                    var taskType;
                                    if(resourceType == 1 || resourceType == 2){
                                        taskType = 2;
                                    } else {
                                        taskType = 3;
                                    }
                                    $('#resource_task_grid', tab[0]).datagrid('load', 'task/query.action?type='+taskType+'&dataSourceId=' + id);
                                    $('#resource_grid', tab[0]).datagrid('reload');
                                    //选中任务列表tab
                                    $('#resource_task_tabs', tab[0]).tabs('select',1);
                                    dialog.dialog('close');
                                });
                            } else if (type == REALTIME_TASK) {
                                $.post('task/start.action', task, function (msg) {
                                    callback(msg);
                                    dialog.dialog('close');
                                });
                            } else if (type == REALIMAGE_TASK) {
                                $.post('task/startRealImage.action', task, function (msg) {
                                    callback(msg);
                                    dialog.dialog('close');
                                });
                            }
                        }
                    }, {
                        text: '重画',
                        iconCls: 'icon-reload',
                        handler: function () {
                            drawing = false;
                            polygons.forEach(function (e) {
                                e.el.remove();
                            });
                            polygons = [];
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-no',
                        handler: function () {
                            dialog.dialog('close');
                        }
                    }],
                    onBeforeClose: function () {
                        $(this).dialog('clear');
                    }
                });

                //单击开始画多边形，双击结束
                image.click(function (e) {
                    if (!drawing) {
                        //如果开始画多边形，就用一个数组存一下
                        var el = paper.path(), points = [];
                        polygons.push({el: el, points: points});
                        drawing = true;
                        //开始画线后线处于最上层，所以得在线上绑定事件
                        el.click(function (e) {
                            if (drawing) {
                                var coordinate = getCoordinate(e);
                                points.push(coordinate);
                            }
                        }).mousemove(function (e) {
                            if (!drawing)return
                            var polygon = polygons[polygons.length - 1],
                                    coordinate = getCoordinate(e),
                                    points = polygon.points.concat([coordinate]);
                            el.attr({
                                stroke: "yellow",
                                fill: 'red',
                                'fill-opacity': 0.2,
                                path: generatePath(points)
                            })
                        }).dblclick(function () {
                            drawing = false;
                            points.pop();
                            el.unclick().undblclick().unmousemove();
                        })
                    }
                    var coordinate = getCoordinate(e);
                    polygons[polygons.length - 1].points.push(coordinate);
                }).mousemove(function (e) {
                    if (!drawing)return
                    var polygon = polygons[polygons.length - 1],
                            coordinate = getCoordinate(e),
                            points = polygon.points.concat([coordinate]);
                    polygon.el.attr({
                        stroke: "yellow",
                        fill: 'red',
                        'fill-opacity': 0.2,
                        path: generatePath(points)
                    })
                });
            };
        }
    })()
</script>