<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="/js/lib/markerclusterer.js"></script>
<div id="map_window">
    <div>
        <div id='pub_main_map' style="height: 100%; width: 100%;"></div>
    </div>
    <div id="pub_map_tool" style="position:absolute;left: 300px; top: 30px;">
        <a href="javascript:void(0)" class="easyui-linkbutton">多边形</a>
        <a href="javascript:void(0)" class="easyui-linkbutton">清空</a>
    </div>
</div>
<!-- camera maker信息窗模板 -->
<script type="tmpl" id="pub_map_camera_info_tmpl">
		<table>
			<tr>
				<td>监控点名：</td>
				<td>{#name}</td>
			</tr>
			<tr>
				<td>经度：</td>
				<td>{#longitude}</td>
			</tr>
			<tr>
				<td>纬度：</td>
				<td>{#latitude}</td>
			</tr>
			<tr>
				<td>状态：</td>
				<td>{#status?"激活":"禁用"}</td>
			</tr>
		</table>
<br>
</script>
<script type="text/javascript">
    (function ($, window) {
        var $win = $('#map_window');
        ${mapSettings}
        var pub_map_window = {};
        /**
         * 不能没有你
         * @Title: open
         * @Description: 画图选择监控点区域
         * @param $query_camera(为tab.find('#xxxxx')找到的值) 监控点下拉框
         */
         pub_map_window.open = function($query_camera,camIdArr,camNameArr){
             $("#pub_map_tool").show(); //显示（多边形、清空）按钮
            $win.window({
                title:'画图选择监控点区域(<span style="color:red">选择图形，单击开始，双击结束</span>):',
                width:880,
                height:660,
                modal:true
            });
            var $map = $win.find("#pub_main_map"),
                    map = initMap($map[0], new GLatLng(mapSettings.centerLat, mapSettings.centerLng), mapSettings.initialZoom),
                    mc = new MarkerClusterer(map),
            //用于右键时保存当前坐标对象
                    gClickLatLng,
            //保存最近点击的摄像头对象
                    gOnClickMarker,
                    tool = $win.find('#pub_map_tool'),
                    toolItems = $('a', tool),
                    gDrawingPolygon = null;
            $map.data('map', map);
            $(toolItems[0]).click(function () {
                addPolygon('polygon');
            });
            $(toolItems[1]).click(function () {
                clearPolygon();
            });

             var addPolygon = function (shape, points) {
                clearPolygon();
                var r = [];
                points = points || [];
                gDrawingPolygon = new Polygon(map, shape, points);
                gDrawingPolygon.draw();
                var reloadDatagrid = function (force) {
                    //如果强制刷新视频列表的话就跳过此判断
                    if (!force && !gDrawingPolygon.isEndline()) {
                        return;
                    }
                    setTimeout(function () {
                        var dtos = camera.toArr().map(function (e) {
                            return e.dto;
                        });
                        for (var i = 0; i < dtos.length; i++) {
                            var dto = dtos[i];
                            if (gDrawingPolygon != null && gDrawingPolygon.containsLatLng(new GLatLng(dto.latitude, dto.longitude))) {
                                r.push(dto.id);
                            }
                        }
                        //如果有框中监控点就重载数据
                        if(r.length > 0){
                            $.each(r,function(i,o) {
                                camIdArr.jiuLingAdd(o);
                            });
                            //关闭窗口
                            $win.window('close');
                            //选中监控点
                            var t = $query_camera.combotree("tree");
                            var nodes = t.tree('getChildren');
                            //存放监控点树id
                            var ckCamTree = [];
                            for(var i= 0,len=nodes.length;i<len;i++) {
                                var cnode = nodes[i];
                                if(t.tree("isLeaf",cnode.target) && !!cnode.attributes) {
                                    for(var ii= 0,iLen= camIdArr.length;ii<iLen;ii++) {
                                        if(cnode.attributes.cameraId == camIdArr[ii]) {
                                            ckCamTree.jiuLingAdd(cnode.id);
                                            camNameArr.jiuLingAdd(cnode.text);
                                        }
                                    }
                                }
                            }
                            $query_camera.combotree('setValues',ckCamTree);
                            $query_camera.combotree('setText',camNameArr);
                        } else {
                            $.messager.alert("提示", "没有选中监控点!");
                        }
                    }, 200);
                };
                if (points.length > 0) {
                    reloadDatagrid(true);
                } else {
                    GEvent.addListener(gDrawingPolygon.polygon, "lineupdated", reloadDatagrid);
                }
            };

            //清空
            var clearPolygon = function () {
                if (gDrawingPolygon != null) {
                    if (gDrawingPolygon.polygon) {
                        gDrawingPolygon.polygon.disableEditing();
                    }
                    gDrawingPolygon.clear();
                    gDrawingPolygon = null;
                }
            };

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
                            //datagrid.datagrid("reload");
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
                    pub_map_camera_info_tmpl = $("#pub_map_camera_info_tmpl").html(),
                    openInfoWindowHtml = function (marker) {
                        var infoWindowHtml = util.parser(pub_map_camera_info_tmpl, marker.dto);
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
            });
            //载入页面时加载监控点
            camera.showInBounds(map.getBounds());
        };

        /**
         * 显示轨迹
         */
        pub_map_window.openTrailMap = function(trails,strokeColor,weight){

            $("#pub_map_tool").hide(); //隐藏（多边形、清空）按钮

            $win.window({
                title:'行车轨迹',
                width:880,
                height:660,
                modal:true
            });
            var $map = $win.find("#pub_main_map"),
                    map = initMap($map[0], new GLatLng(trails[0].latitude, trails[0].longitude), mapSettings.initialZoom),
                    mc = new MarkerClusterer(map),
                    gDrawingPolygon = null;


            /**
             * 移动到第一个监控点为地图中心
             */
            var LatLng = new GLatLng(+trails[0].latitude, +trails[0].longitude);
            setTimeout(function () {
                map.panTo(LatLng);
            });

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
                            //datagrid.datagrid("reload");
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
                    pub_map_camera_info_tmpl = $("#pub_map_camera_info_tmpl").html(),
                    openInfoWindowHtml = function (marker) {
                        var infoWindowHtml = util.parser(pub_map_camera_info_tmpl, marker.dto);
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
                                        camera.aferShowInBounds();
                                    }
                                });
                            }
                        };
                    })();

            //载入页面时加载监控点
            camera.showInBounds(map.getBounds());

            var markers = {};
            var rows = trails;
            var markerOptions = {icon: gIconCar, draggable: false};
            var invalidData = [], data = [];
            var degreesPerRadian = 180.0 / Math.PI;
            //箭头路径
            var imagePath;

            function checkLatLng(c) {
                //检测监控点坐标是否合法
                return c.latitude && c.latitude > 0 && c.longitude && c.longitude > 0;
            }

            function addLabelMarker(latLng, labelText) {
                var label = new GLabelMarker(latLng,{labelText:labelText});
                map.addOverlay(label);
                return label;
            }

            rows.forEach(function (r) {
                if (checkLatLng(r)) {
                    //添加文字标注
                    var labelText = '&emsp;&emsp;车牌:'+ r.license + '<br/>' + '&emsp;&emsp;地点:'+ (r.location==null?'无地点':r.location) + '<br/>' + '&emsp;&emsp;过车时间:' + util.formateTime(r.resultTime);
                    addLabelMarker(new GLatLng(r.latitude, r.longitude), labelText);

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
                if(!strokeColor) {
                    strokeColor='red';
                }
                imagePath = "/Gis_0001/images/"+strokeColor+"/img_arrow";
                //画箭头
                for (var i = 0, len = data.length - 1; i < len; i++) {
                    var p1 = data[i], p2 = data[i + 1];
                    //
                    var line = new GPolyline([p1,p2], strokeColor, weight, 1);
                    map.addOverlay(line);
                    //
                    var dir = bearing(p1,p2);
                    var offsetBase = 16;
                    //alert(dir + ":" + Math.sin(dir*2*Math.PI/360) + ":" + Math.cos(dir*2*Math.PI/360));
                    var hOffset = Math.round(offsetBase * Math.sin(dir*2*Math.PI/360));
                    var vOffset = Math.round(offsetBase * Math.cos(dir*2*Math.PI/360));
                    //alert(dir*2*Math.PI/360 + ":" + hOffset + ":" + vOffset);
                    var dir = Math.round(dir/3) * 3;
                    while (dir >= 120) {dir -= 120;}
                    var arrowIcon = new GIcon();
                    arrowIcon.iconSize = new GSize(24,24);
                    arrowIcon.iconAnchor = new GPoint(12+hOffset, 12-vOffset);
                    arrowIcon.image = imagePath + "/dir_"+dir+".png";
                    map.addOverlay(new GMarker(data[i + 1], arrowIcon));
                }
            }

            function bearing( from, to ) {
                var lat1 = from.latRadians();
                var lon1 = from.lngRadians();
                var lat2 = to.latRadians();
                var lon2 = to.lngRadians();
                var angle = - Math.atan2( Math.sin( lon1 - lon2 ) * Math.cos( lat2 ), Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( lon1 - lon2 ) );
                if ( angle < 0.0 ) {
                    angle += Math.PI * 2.0;
                }
                angle = angle * degreesPerRadian;
                angle = angle.toFixed(1);
                return angle;
            }

        };

        //公用地图窗口
        window.pubMapWindow = pub_map_window;
    })(jQuery, window);
</script>

