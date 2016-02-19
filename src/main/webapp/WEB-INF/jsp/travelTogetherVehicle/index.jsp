<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="easyui-layout" fit="true" style="width:100%;height:100%;">
    <div data-options="region:'north',split:false" style="width:100%;height:5%;background: rgb(238, 238, 238);">
        <div id="travelTogetherVehicle_toolbar">
            <div class='toolbar' style="background: rgb(238, 238, 238);">
                <form action="#">
                    &nbsp;&nbsp;<label>车牌号:
                    <input id="plate" class="easyui-textbox" type="text" class="easyui-textbox" data-options="required:true"></label>
                    &nbsp;&nbsp;&nbsp;&nbsp;<label>同行路口数(≥):
                    <input id="locations" class="easyui-textbox" type="text" data-options="required:true" style="width:80px"></label>
                    &nbsp;&nbsp;&nbsp;&nbsp;<label>时差(秒):
                    <input id="timeInterval" class="easyui-textbox" type="text" data-options="required:true" style="width:80px"></label>
                    &nbsp;&nbsp;&nbsp;&nbsp;<label>开始时间:
                    <input id="startTime" type="text" name="startTime"></label>
                    &nbsp;&nbsp;&nbsp;&nbsp;<label>结束时间:
                    <input id="endTime" type="text" name="startTime"></label>

                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" id="query" class="easyui-linkbutton"
                                               data-options="iconCls:'icon-search'">查询</a>
                </form>
            </div>
        </div>
    </div>
    <div data-options="region:'center',split:false,border:false" style="width:100%;height:95%;">
        <div class="easyui-layout" fit="true" style="width:100%;height:100%;">
            <div data-options="region:'west',title:'同行车分析'" style="width:50%;height:100%;background:#eee;">
                <div class="easyui-layout" style="width:100%;height:100%;">
                    <div region="west" split="true" title="同行车列表" style="width:30%;background:#eee;">
                        <div id="travelTogetherVehicle_datagrid"></div>
                    </div>
                    <div data-options="region:'center',border:false" style="width:35%;height:100%;background:#eee;">
                        <div class="easyui-layout" data-options="fit:true">
                            <div data-options="region:'center',title:'原车详细信息'" style="width:40%;height:50%;background:#eee;">
                                <div id="orginalVehicle_detail_datagrid"></div>
                            </div>
                            <div data-options="region:'south',title:'同行车详细信息',collapsible:false" style="width:40%;height:50%;background:#eee;">
                                <div id="travelTogetherVehicle_detail_datagrid"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div data-options="region:'center'" style="width:50%;height:100%;">
                <div>
                    <div id='together_map' style="height: 100%; width: 100%; position: absolute;">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    (function ($) {
        var firstInitialZoom = true;
        var initialZoom = 12; //自适应地图级别（默认12）
        var tab = util.getSelectedTab();
        var datagrid = tab.find("#travelTogetherVehicle_datagrid");
        var toolbar = tab.find("#travelTogetherVehicle_toolbar");
        var orginal_datagrid = tab.find("#orginalVehicle_detail_datagrid");
        var travelTogetherVehicle_detail_datagrid = tab.find("#travelTogetherVehicle_detail_datagrid");
        var $form = toolbar.find("form");
        var pictureServerHost = '${PictureServerHost}/';
        ${mapSettings}
        //全局map对象
        var together_map = tab.find('#together_map'), map = initMap(together_map[0], new GLatLng(mapSettings.centerLat, mapSettings.centerLng), mapSettings.initialZoom),
                mc = new MarkerClusterer(map),
        //用于右键时保存当前坐标对象
                gClickLatLng,
        //保存最近点击的摄像头对象
                gOnClickMarker,
                menu = $("#map_camera_contextmenu").menu(),
                items = menu.children("[data-options]"),
                item = {
                    add: items[0],
                    edit: items[1],
                    setCenter: items[2],
                    import: items[3]
                },
                map_defensiveRing_add_tmpl = $('#map_defensiveRing_add_tmpl').html(),
                map_camera_add_tmpl = $('#map_camera_add_tmpl').html(),
                map_camera_update_tmpl = $('#map_camera_update_tmpl').html(),
                map_camera_import_tmpl = $('#map_camera_import_tmpl').html(),
                gDrawingPolygon = null;
        together_map.data('map', map);

        //显示单个监控点
        var showSingleCamera = function (dto) {
                    if (camera.hasCached(dto))return;
                    if (!camera.checkLatLng(dto))return;
                    var updateAction = "/camera/update.action";
                    var markerOptions = {icon: gIconCamera, draggable: true};
                    if (!dto.status) {
                        markerOptions = {icon: gIconCameraInvalid, draggable: true};
                    }
                    var marker = showObject(map, dto, markerOptions, updateAction, function () {
                        datagrid.datagrid("reload");
                    });
                    //将添加到地图上的marker移除,由聚合框架来控制marker的显示
                    map.removeOverlay(marker);
                    camera.cache(dto.id, {dto: dto, marker: marker});
                    marker.dto = dto;
                    GEvent.addListener(marker, "click", function () {
                        openInfoWindowHtml(marker);
                    });
                    mc.addMarker(marker);
                    // TODO 聚合框架控制监控点显示会导致公共方法中的右键点击事件失效，需要重新设计屏蔽marker右键菜单
                    //给marker的界面标签加上禁止右键点击的属性，防止IE下点击marker时弹出浏览器的contextmenu
                    //$("img.gmnoprint").attr("oncontextmenu","self.event.returnValue=false");
                    return marker;
                },
                map_camera_info_tmpl = $("#map_camera_info_tmpl").html(),
                openInfoWindowHtml = function (marker) {
                    var infoWindowHtml = util.parser(map_camera_info_tmpl, marker.dto);
                    marker.openInfoWindowHtml(infoWindowHtml);
                },
                camera = (function () {
                    var datas = {};
                    return {
                        toArr: function () {
                            var arr = [];
                            for (var k in datas)arr.push(datas[k]);
                            return arr;
                        },
                        hasCached: function (dto) {
                            return !!datas[dto.id];
                        },
                        cache: function (id, obj) {
                            datas[id] = obj;
                        },
                        getMarker: function (id) {
                            return datas[id] && datas[id].marker;
                        },
                        getDto: function (id) {
                            return datas[id] && datas[id].dto;
                        },
                        setDto: function (dto) {
                            return datas[dto.id].dto = dto;
                        },
                        aferShowInBounds: $.noop,
                        checkLatLng: function (c) {
                            //检测监控点坐标是否合法
                            return c.latitude > -90 && c.latitude < 90 && c.longitude > -180 && c.longitude < 180;
                        },
                        showInBounds: function (b, hasLoad) {
                            if (hasLoad) {
                                //camera.aferShowInBounds();
                                //return;
                            }
                            //最多取多少个监控点
                            var maxSize = 1000,
                                    s = b.getSouthWest(),
                                    n = b.getNorthEast();
                            $.ajax({
                                url: '/camera/list.action',
                                type: 'post',
                                data: {
                                    minLat: s.lat(), minLng: s.lng(),
                                    maxLat: n.lat(), maxLng: n.lng(),
                                    page: 1,
                                    rows: maxSize
                                },
                                success: function (d) {
                                    var cameras = d.p.rows;
                                    //cameras.forEach(showSingleCamera);
                                    util.bat(cameras.length, function (i) {
                                        showSingleCamera(cameras[i - 1]);
                                    });

                                    if(firstInitialZoom){
                                        /*-----如果有两个监控点以上，则自适应的地图缩放级别----*/
                                        if(cameras.length>1){
                                            //获取监控点直间的最小距离
                                            var minDistance =0.0; //监控点之间的最小距离
                                            for(var i=0;i<cameras.length-1;i++){
                                                var lon1 = cameras[i].longitude;  //经度1
                                                var lat1 = cameras[i].latitude;   //纬度1

                                                //查看每个坐标与其他坐标的距离，并获取所以监控点之间的最小距离
                                                for(var j=i+1;j<cameras.length;j++){
                                                    var lon2 = cameras[j].longitude; //经度2
                                                    var lat2 = cameras[j].latitude;   //纬度2
                                                    var newDistance = getDistance(lon1, lat1, lon2, lat2);  //获取位置之间的距离
                                                    //获取最短距离
                                                    if(j==1){
                                                        minDistance = newDistance
                                                    }else if(minDistance > newDistance){
                                                        minDistance = newDistance;
                                                    }
                                                }
                                            }
                                            //通过监控点的最短距离获取自适应地图级别
                                            initialZoom = getMapLevelByDistance(minDistance);
                                            if(initialZoom < 12){ //如果获取的级别小于12，则使用默认12
                                                map.setZoom(12);
                                            }else{
                                                map.setZoom(initialZoom);
                                            }
                                        }
                                        //移动到第一个监控点
                                        var LatLng = new GLatLng(+cameras[0].latitude, +cameras[0].longitude);
                                        setTimeout(function () {
                                            map.panTo(LatLng);
                                        });
                                        firstInitialZoom = false;  //标识首次自适应地图完成
                                    }
                                    camera.aferShowInBounds();
                                }
                            });
                        }
                    };
                })();

        //当移动地图结束事件
        GEvent.addListener(map, "moveend", function () {
            var bounds = map.getBounds();
            camera.showInBounds(bounds, true);
        });
        //右键单击弹出菜单
        GEvent.addListener(map, "singlerightclick", function (pixel, tile, marker) {
            if (gDrawingPolygon != null) {
                clearPolygon();
                return;
            }
            gClickLatLng = map.fromContainerPixelToLatLng(pixel);//将屏幕坐标转化为地图坐标并缓存
            if (marker.dto) {
                gOnClickMarker = marker;
            }
            var mapDOM = map.getContainer(),//获取地图DOM元素
                    offset = $(mapDOM).offset(),
                    x = pixel.x + offset.left,
                    y = pixel.y + offset.top;
            if (marker.nodeType) {
                menu.menu("showItem", item.add);
                menu.menu("hideItem", item.edit);
                menu.menu("showItem", item.setCenter);
            } else {
                menu.menu("showItem", item.edit);
                menu.menu("hideItem", item.add);
                menu.menu("hideItem", item.setCenter);
            }
            menu.menu("showItem", item.import).menu('show', {
                left: x,
                top: y
            });
        });
        //载入页面时加载监控点
        camera.showInBounds(map.getBounds());

        var startTime = $('#travelTogetherVehicle_toolbar #startTime').datetimebox({
            required: true,
            editable: false
        });
        var endTime = $('#travelTogetherVehicle_toolbar #endTime').datetimebox({
            required: true,
            editable: false
        });

        var getParamData = function () {
            var data = {
                plate: tab.find("#plate").val(),
                startTime: startTime.combo('getValue'),
                endTime: endTime.combo('getValue')
            };
            $.each(data, function (k, v) {
                data[k] = $.trim(v);
            });
            return data;
        };

        $('#travelTogetherVehicle_toolbar #query').click(function (e) {

            var locations = $("#locations").val();
            var timeInterval = $("#timeInterval").val();
            e.preventDefault();
            if ($form.form("validate")) {
                //原车详细列表
                var loadOrginalData = function () {
                    var org_datagrid = orginal_datagrid.datagrid({
                        url: '/travelTogetherVehicle/queryOrginalPlate.action',
                        loadMsg: '数据载入中',
                        rownumbers: true,
                        fit: true,
                        singleSelect: true,
                        columns: [[
                            {field: 'license', title: '车牌', width: 100},
                            {field: 'location', title: '地点', width: 120},
                            {field: 'direction', title: '方向', width: 50},
                            {field: 'resultTime', title: '过车时间', formatter: util.formateTime, width: 150}
                        ]],
                        onDblClickRow: function (index, row) {
                            detail_window.open(row, pictureServerHost, org_datagrid, loadOrginalData);
                        },
                        onBeforeLoad: function (p) {
                            $.extend(p, getParamData());
                        },
                        onLoadSuccess: function (pager) {
                            //深拷贝原始对象，作为参数传递给后台
                            var datas = pager.rows.map(function(item, index, arr){
                                 return deepCopy(item);
                            });

                            var dtos = pager.rows;
                            //为了翻页增加index 属性
                            $.each(dtos, function (i, o) {
                                o.index = i;
                            });

                            //同行车列表
                            datagrid.datagrid({
                                url: '/travelTogetherVehicle/queryTogetherVehicle.action',
                                loadMsg: '数据载入中',
                                rownumbers: true,
                                fit: true,
                                singleSelect: true,
                                columns: [[
                                    {field: 'plate', title: '车牌号', width: 80},
                                    {field: 'count', title: '同行路口数', width: 100},
                                ]],
                                toolbar: $('#travelTogetherVehicle_datagrid .toolbar'),
                                onBeforeLoad: function (p) {
                                    //把原车list作为参数传递给后台
                                    $.extend(p, {resultVos:JSON.stringify(datas),locations: locations,timeInterval: timeInterval});
                                },
                                //需要优化
                                onDblClickRow: function (index, row) {
                                    var data = getParamData();
                                    data.plate = row.plate;
                                    var lm = {};
                                    //同行车详细列表
                                    var loadTogetherData = function (callback) {
                                        var together_datagrid = travelTogetherVehicle_detail_datagrid.datagrid({
                                            url: '/travelTogetherVehicle/queryTogetherVehicleDetail.action',
                                            loadMsg: '数据载入中',
                                            rownumbers: true,
                                            fit: true,
                                            singleSelect: true,
                                            columns: [[
                                                {field: 'license', title: '车牌', width: 100},
                                                {field: 'location', title: '地点', width: 120},
                                                {field: 'direction', title: '方向', width: 50},
                                                {field: 'resultTime', title: '过车时间', formatter: util.formateTime, width: 150},
                                            ]],
                                            rowStyler: function (index, row) {
                                                for (var i = 0, len = dtos.length; i < len; i++) {
                                                    if (row.location == dtos[i].location && row.resultTime >= (dtos[i].resultTime - timeInterval * 1000) && row.resultTime <= (dtos[i].resultTime + timeInterval * 1000)) {
                                                        return 'background-color:#87ceeb;';
                                                    }
                                                }
                                            },
                                            onDblClickRow: function (index, row) {
                                                detail_window.open(row, pictureServerHost, together_datagrid, loadTogetherData);
                                            },
                                            onBeforeLoad: function (p) {
                                                $.extend(p, data);
                                            },
                                            onLoadSuccess: function (pager) {
                                                $.each(pager.rows, function (i, o) {
                                                    o.index = i;
                                                });
                                                if (typeof callback == 'function') {
                                                    callback();
                                                    callback = null;
                                                }
                                            }
                                        });
                                    }
                                    loadTogetherData(function () {
                                        var togetherRows = travelTogetherVehicle_detail_datagrid.datagrid('getRows');
                                        orginal_datagrid.datagrid({
                                            rowStyler: function (index, row) {
                                                for (var i = 0, len = togetherRows.length; i < len; i++) {
                                                    if (row.location == togetherRows[i].location && togetherRows[i].resultTime >= (row.resultTime - timeInterval * 1000) && togetherRows[i].resultTime <= (row.resultTime + timeInterval * 1000)) {
                                                        return 'background-color:#87ceeb;';
                                                    }
                                                }
                                            }
                                        })

                                        var $together_map = $("#together_map");
                                        var together_map = $together_map.data('map');

                                        //先清除上一次显示的轨迹
                                        together_map.clearOverlays();
                                        //原车轨迹
                                        showTogetherTrail(dtos.sort(function (a, b) {
                                            return a.resultTime > b.resultTime ? 1 : -1;
                                        }), 'red', 7, lm);
                                        //目标车轨迹
                                        lm = showTogetherTrail(togetherRows.sort(function (a, b) {
                                            return a.resultTime > b.resultTime ? 1 : -1;
                                        }), 'blue', 3, lm);
                                        addLabelMarkers(lm);
                                    });
                                }
                            });
                        }
                    });
                };
                loadOrginalData();
            }
        });

        var showTogetherTrail = function (trails, strokeColor, weight, lm) {
            var markers = {};
            var rows = trails;
            var markerOptions = {icon: gIconCar, draggable: false};
            var invalidData = [], data = [];
            var together_map = $("#together_map");
            var map = together_map.data('map');
            var degreesPerRadian = 180.0 / Math.PI;
            //箭头路径
            var imagePath;

            /**
             * 移动到第一个监控点
             */
            var c = rows[0];
            var LatLng = new GLatLng(+c.latitude, +c.longitude);
            //显示轨迹
            map.setZoom(initialZoom); //设置地图初始级别

            setTimeout(function () {
                map.panTo(LatLng);
            });

            function checkLatLng(c) {
                //检测监控点坐标是否合法
                return c.latitude && c.latitude > 0 && c.longitude && c.longitude > 0;
            }

            rows.forEach(function (r) {
                if (checkLatLng(r)) {
                    if (r.cameraID in lm) {
                        lm[r.cameraID].push(r);
                    } else {
                        lm[r.cameraID] = [r];
                    }
                    markers[r.serialNumber] = showObject(map, r, markerOptions);
                    data.push(new GLatLng(r.latitude, r.longitude));
                } else {
                    invalidData.push(r)
                }
            });
            if (invalidData.length > 0) {
                invalidData = invalidData.map(function (r) {
                    return r.license;
                }).join();
                $.messager.show({title: "显示轨迹", msg: invalidData + "缺失经纬度信息!!"});
            }
            if (data.length > 0) {
                if (!strokeColor) {
                    strokeColor = 'red';
                }
                imagePath = "/Gis_0001/images/" + strokeColor + "/img_arrow";
                //画箭头
                for (var i = 0, len = data.length - 1; i < len; i++) {
                    var p1 = data[i], p2 = data[i + 1];
                    //
                    var line = new GPolyline([p1, p2], strokeColor, weight, 1);
                    map.addOverlay(line);
                    //
                    var dir = bearing(p1, p2);
                    var offsetBase = 16;
                    //alert(dir + ":" + Math.sin(dir*2*Math.PI/360) + ":" + Math.cos(dir*2*Math.PI/360));
                    var hOffset = Math.round(offsetBase * Math.sin(dir * 2 * Math.PI / 360));
                    var vOffset = Math.round(offsetBase * Math.cos(dir * 2 * Math.PI / 360));
                    //alert(dir*2*Math.PI/360 + ":" + hOffset + ":" + vOffset);
                    var dir = Math.round(dir / 3) * 3;
                    while (dir >= 120) {
                        dir -= 120;
                    }
                    var arrowIcon = new GIcon();
                    arrowIcon.iconSize = new GSize(24, 24);
                    arrowIcon.iconAnchor = new GPoint(12 + hOffset, 12 - vOffset);
                    arrowIcon.image = imagePath + "/dir_" + dir + ".png";
                    map.addOverlay(new GMarker(data[i + 1], arrowIcon));
                }
            }

            function bearing(from, to) {
                var lat1 = from.latRadians();
                var lon1 = from.lngRadians();
                var lat2 = to.latRadians();
                var lon2 = to.lngRadians();
                var angle = -Math.atan2(Math.sin(lon1 - lon2) * Math.cos(lat2), Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
                if (angle < 0.0) {
                    angle += Math.PI * 2.0;
                }
                angle = angle * degreesPerRadian;
                angle = angle.toFixed(1);
                return angle;
            }

            return lm;
        }

        var addLabelMarker = function (latLng, labelText) {
			var gsize = new GSize(15, 15);
        	
        	var icon = new GIcon();
            icon.image = "";
            icon.dragCrossImage = null;
            icon.iconSize = new GSize(120, 19);
            icon.iconAnchor = new GPoint(-15, -15);
            icon.infoWindowAnchor = new GPoint(5, 1);
        	
            var label = new GLabelMarker(latLng,{labelText:labelText,labelOffset:gsize,draggable:true,icon:icon});
            map.addOverlay(label);
            return label;
        }

        var addLabelMarkers = function (obj) {
            $.each(obj, function (cameraID, resultVOArr) {
                var labelText = "";
                var latLng;
                var passInfo = "";
                for (var i = 0, len = resultVOArr.length; i < len; i++) {
                    if(resultVOArr[i].location) {
                        passInfo = resultVOArr[i].location;
                    } else {
                        passInfo = "无地点";
                    }
                    labelText += '&emsp;&emsp;<font size="1">' + resultVOArr[i].license + '&emsp;' + util.formateTime(resultVOArr[i].resultTime) + '</font><br/><hr>';
                    latLng = new GLatLng(resultVOArr[i].latitude, resultVOArr[i].longitude)
                }
                passInfo = passInfo + '<br/>' + labelText;
                addLabelMarker(latLng, passInfo);
            });
        }

        //对象深拷贝
        var deepCopy= function(source) {
            var result={};
            for (var key in source) {
                result[key] = source[key] instanceof Object ? deepCopy(source[key]): source[key];
            }
            return result;
        };

//enter键监听
        tab.on("keyup", 'input:text', function (e) {
            if (e.keyCode == 13) {
                datagrid.datagrid('reload');
            }
        });
// IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readOnly]").keydown(function (e) {
            e.preventDefault();
        });
    })(jQuery);
</script>
