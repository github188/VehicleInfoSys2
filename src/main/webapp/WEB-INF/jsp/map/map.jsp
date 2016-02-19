<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<script src="/js/lib/markerclusterer.js"></script>
<div title="监控点管理">
    <div class="easyui-layout" fit="true">
        <div region="center" oncontextmenu="self.event.returnValue=false">
            <div>
                <div id='main_map' style="height: 100%; width: 100%; position: absolute;"></div>
            </div>
            <!--
            <div id="map_tool" style="position: absolute; left: 300px; top: 5px;">
             <a href="javascript:void(0)" class="easyui-linkbutton">矩形</a>
             <a href="javascript:void(0)" class="easyui-linkbutton">多边形</a>
             <a href="javascript:void(0)" class="easyui-linkbutton">圆形</a>
             <a href="javascript:void(0)" class="easyui-linkbutton">线缓冲</a>
             <a href="javascript:void(0)" class="easyui-linkbutton" style="width: 100px;">保存为防御圈</a>
             <a href="javascript:void(0)" class="easyui-linkbutton">清空</a>
            </div>
             -->
        </div>
        <div title="列表" region="east" style="width: 380px;" split=true>
            <div id="map_list" class="easyui-tabs" fit=true border=false>
                <div title="监控点">
                    <div id="map_camera_datagrid">
                        <div class="toolbar">
                            <form>
                                <label>
                                    监控点:
                                    <input id="text" class="easyui-textbox" type="text" name="location" style="width: 80px;"/>
                                </label>
                                <label>
                                    行政区划:
                                    <%--<input id="camReg" type="text" name="camReg" style="width: 80px;"/>--%>
                                    <input class="easyui-combotree" id="camReg" name="region" value='' style="width: 100px;"
                                           data-options="url:'area/areaTreeJson.action',
                                           editable:true,
                                           //multiple:true,
							    panelHeight:'300',
							    keyHandler:{
							        query: function(q){
							            var t = $(this).combotree('tree');
                                        var nodes = t.tree('getChildren');
                                        if($(this).combo('getText')=='') {
                                            $(this).combo('clear');
                                        }
                                        for(var i=0; i<nodes.length; i++){
                                            var node = nodes[i];
                                            if (node.text.indexOf(q) >= 0){
                                                $(node.target).show();
                                            } else {
                                                $(node.target).hide();
                                            }
                                        }
                                        var opts = $(this).combotree('options');
                                        if (!opts.hasSetEvents){
                                            opts.hasSetEvents = true;
                                            var onShowPanel = opts.onShowPanel;
                                            opts.onShowPanel = function(){
                                                var nodes = t.tree('getChildren');
                                                for(var i=0; i<nodes.length; i++){
                                                    $(nodes[i].target).show();
                                                }
                                                onShowPanel.call(this);
                                            }
                                            $(this).combo('options').onShowPanel = opts.onShowPanel;
                                        }
							        }
							    },
							    loadFilter: function(data){
							        return util.getNodes(data);
							    },onShowPanel:function(){
							    	$(this).combotree('reload');
							    }" />
                                </label>
                                <a id="query" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </form>
                        </div>
                    </div>
                </div>
                <div title="视频">
                    <div id="map_video_datagrid">
                        <div class="toolbar">
                            <form>
                                <label>
                                    地点:
                                    <input type="text" name="location"/>
                                </label>
                                <a id="query" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search">查询</a>
                            </form>
                        </div>
                    </div>
                </div>
                <!-- <div title="防御圈">
                 <div id="map_defensiveRing_datagrid"></div>
                </div> -->
            </div>
        </div>
    </div>
    <div id="map_camera_contextmenu">
        <div data-options="iconCls:'icon-add'">
            此处添加监控点
        </div>
        <div data-options="iconCls:'icon-edit'">
            编辑监控点详情
        </div>
        <div data-options="iconCls:'icon-edit'">
            设置此处为中心点
        </div>
        <div data-options="iconCls:'icon-add'">
            批量导入监控点
        </div>
    </div>
    <div id="map_camera_window"></div>
</div>
<!-- 添加防御圈模板 -->
<script type="tmpl" id="map_defensiveRing_add_tmpl">
	<div>
		<table>
			<tr>
				<td>防御圈名称：</td>
				<td>
					<input class="easyui-textbox" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[2,50]}" name="name" />
				</td>
			</tr>
			<tr>
				<td>
				</td>
				<td>
					<a href="javascript:void(0)" class="btn_ok" >确定</a>
					<a href="javascript:void(0)" class="btn_cancel" >取消</a>
				</td>
			</tr>
		</table>
	</div>
</script>
<!-- camera maker信息窗模板 -->
<script type="tmpl" id="map_camera_info_tmpl">
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
<!-- 添加监控点模板-->
<script type="tmpl" id="map_camera_add_tmpl">
	<div>
		<form>
			<table >
				<tr>
					<td><span style="color:red">*</span>监控点：</td>
					<td>
						<input class="name easyui-textbox" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[2,50],remote:['/camera/valideName.action','name']}" name="name" />
					</td>
				</tr>
				<tr>
					<td>&ensp;辖&emsp;区：</td>
					<td>
						<input class="easyui-combotree" name="region" value=''
                                   data-options="url:'area/areaTree.action',
							    panelHeight:'150',
							    onClick:function(node) {
                                var queryParams = {
                                    id:node.id,
                                    name:node.text
                                };
                                },onShowPanel:function(){
							    	$(this).combotree('reload');
							    }" />
					</td>
				</tr>
				<tr>
					<td>&ensp;经&emsp;度：</td>
					<td>
						<input class="easyui-textbox" data-options="required:true,validType:{range:[-180,180,14],length:[1,20]}" name="longitude" value="{#longitude}" />
					</td>
				</tr>
				<tr>
					<td>&ensp;纬&emsp;度：</td>
					<td>
						<input class="easyui-textbox" data-options="required:true,validType:{range:[-90,90,16],length:[1,20]}" name="latitude" value="{#latitude}" />
					</td>
				</tr>

				<tr>
					<td>
					</td>
					<td>
						<a href="javascript:void(0)" class="btn_ok" >确定</a>
						<a href="javascript:void(0)" class="btn_cancel" >取消</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</script>
<script type="tmpl" id="map_camera_import_tmpl">
        <div style='font-size:16px;'>
            <form enctype="multipart/form-data" method='post' action="/camera/import.action">
                <table>
                <tr>
                    <td>txt文件:</td>
                    <td>
                        <input type="file" name="cameraInfo" class="easyui-validatebox" data-options="required:true">
                        <br/>格式:监控点名,经度,纬度(每一个监控点一行)
                        <br/>监控点名不能重复
                        <br/>使用utf8编码
                    </td>
                </tr>
                        <tr>
                        <td></td>
                        <td>
                            <a href="javascript:void(0)" class="btn_ok" >确定</a>
                            <a href="javascript:void(0)" class="btn_cancel" >取消</a>
                        </td>
                    </tr>
                </table>
        </form>
        </div>
</script>
<script type="text/javascript">
    (function ($, window) {
        ${mapSettings}
        //全局map对象
        var $map = $("#main_map"), map = initMap($map[0], new GLatLng(mapSettings.centerLat, mapSettings.centerLng), mapSettings.initialZoom),
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
                $win = $('#map_camera_window'),
                map_defensiveRing_add_tmpl = $('#map_defensiveRing_add_tmpl').html(),
                map_camera_add_tmpl = $('#map_camera_add_tmpl').html(),
                map_camera_import_tmpl=$('#map_camera_import_tmpl').html(),
                datagrid = $("#map_camera_datagrid"),
                videoGrid = $("#map_video_datagrid"),
                defensiveGrid = $('#map_defensiveRing_datagrid'),
                tool = $('#map_tool'),
                toolItems = $('a', tool),
                gDrawingPolygon = null;
        $map.data('map', map);
        $(toolItems[0]).click(function () {
            addPolygon('rectangle');
        });
        $(toolItems[1]).click(function () {
            addPolygon('polygon');
        });
        $(toolItems[2]).click(function () {
            addPolygon('circle');
        });
        $(toolItems[3]).click(function () {
            addPolygon('linebuffer');
        });
        $(toolItems[4]).click(function () {
            if (gDrawingPolygon) {
                if (!gDrawingPolygon.isEndline()) {
                    $.messager.alert("提示", "请先完成画图!");
                    return;
                }
                $win.html(map_defensiveRing_add_tmpl);
                var $form = $win.find('form'),
                        $name = $win.find('input[name]');

                $form.submit(function () {
                    $form.find('a.btn_ok').click();
                    return false;
                });

                $win.dialog({
                    title: "添加防御圈",
                    width: 300,
                    modal: true,
                    onOpen: function () {
                        $.parser.parse($win);
                        $name.textbox('textbox').focus();
                        $win.find('a.btn_ok').linkbutton({
                            iconCls: 'icon-ok',
                            onClick: function () {
                                if ($form.form('validate')) {
                                    var obj = {name: $name.val()},
                                            polygon = gDrawingPolygon.polygon,
                                            shape = gDrawingPolygon.shape,
                                            count = polygon.getVertexCount(),
                                            points = '';
                                    for (var i = 0; i < count; i++) {
                                        var point = polygon.getVertex(i);
                                        if (i != 0) points += ',';
                                        points += point.lng() + ',' + point.lat();
                                    }

                                    obj['shape'] = shape;
                                    obj['points'] = points;
                                    $.post("/map/saveDefenceRange.action", obj, function () {
                                        $('#map_list').tabs('select', 2);
                                        defensiveGrid.datagrid('load');
                                        btn_cancel.click();
                                        $.messager.show({title: "添加防御圈", msg: "添加防御圈成功"});
                                    })
                                }
                            }
                        });
                        var btn_cancel = $win.find('a.btn_cancel').linkbutton({
                            iconCls: 'icon-cancel',
                            onClick: function () {
                                $win.dialog('close');
                            }
                        });
                    }
                });
            } else {
                $.messager.alert("提示", "请先选择图形画出防御圈范围!");
            }
        });
        $(toolItems[5]).click(function () {
            clearPolygon();
        });

        var addPolygon = function (shape, points) {
            clearPolygon();
            points = points || [];
            gDrawingPolygon = new Polygon(map, shape, points);
            gDrawingPolygon.draw();
            var updateVideo = function (force) {
                //如果强制刷新视频列表的话就跳过此判断
                if (!force && !gDrawingPolygon.isEndline()) {
                    return;
                }
                setTimeout(function () {
                    var dtos = camera.toArr().map(function (e) {
                        return e.dto;
                    });
                    var r = [];
                    for (var i = 0; i < dtos.length; i++) {
                        var dto = dtos[i];
                        if (gDrawingPolygon != null && gDrawingPolygon.containsLatLng(new GLatLng(dto.latitude, dto.longitude))) {
                            r.push(dto.id);
                        }
                    }

                    $('#map_list').tabs('select', 1);
                    //如果有框中监控点就加载数据
                    if (r.length > 0) {
                        var opts = videoGrid.datagrid('options');
                        opts.url = 'resource/list.action';
                        videoGrid.datagrid('load', {ids: r.join(',')});
                    } else {
                        videoGrid.datagrid('loadData', {rows: [], total: 0});

                    }
                }, 200);
            };
            if (points.length > 0) {
                updateVideo(true);
            } else {
                GEvent.addListener(gDrawingPolygon.polygon, "lineupdated", updateVideo);
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
                if ($.data(videoGrid[0], "datagrid")) {
                    videoGrid.datagrid('options').queryParams = {ids: ""};
                }
            }
        };

        $('#map_list').tabs({
            onSelect: function (title, i) {
                if (i == 1 && !$.data(videoGrid[0], "datagrid")) {
                    videoGrid.datagrid({
                        url: 'resource/list.action',
                        loadMsg: '数据载入中',
                        pagination: true,
                        rownumbers: true,
                        fit: true,
                        singleSelect: true,
                        queryParams: {'type': 2},
                        toolbar: $('.toolbar', videoGrid),
                        onBeforeLoad: function (p) {
                            p.location = $('input[name="location"]', videoGrid.datagrid('getPanel')).val();
                        },
                        frozenColumns: [[
                            {
                                field: 'operation', title: '操作', width: 40, formatter: function (v, row) {
                                var filePath = row.filePath.replace(/\\/g, '\\\\');
                                var html = '<a href="javascript:void(0)" onclick=video_player.play(\'' + filePath + '\') title="播放">播放</a>';
                                return html;
                            }
                            }
                        ]],
                        columns: [[
                            {
                                field: 'thumbnail', title: '缩略图', width: 55, formatter: function (v) {
                                return '<img height="50px" width="50px" src="' + v + '" />';
                            }
                            },
                            {field: 'type', title: '类型', width: 40, formatter: util.formateResourceType},
                            {field: 'name', title: '名称', width: 170}
                        ]]
                    });

                    $(videoGrid.datagrid("getPager")).pagination({
                        beforePageText: "",
                        afterPageText: "共{pages}页"
                    });
                    var panel = videoGrid.datagrid('getPanel');

                    $('input[name="location"]', panel).textbox();
                    panel.on("keydown", "input:text", function (e) {
                        if (e.keyCode == 13) {
                            videoGrid.datagrid('reload');
                        }
                    }).on("click", "#query", function () {
                        videoGrid.datagrid('reload');
                    })
                }
                if (i == 2 && !$.data(defensiveGrid[0], "datagrid")) {
                    defensiveGrid.datagrid({
                        url: '/map/listDefenceRange.action',
                        data: [],
                        loadMsg: '数据载入中',
                        pagination: true,
                        rownumbers: true,
                        fit: true,
                        singleSelect: true,
                        columns: [[
                            {field: 'name', title: '名称', width: 150},
                            {
                                field: 'opera', title: '操作', formatter: function (v, model) {
                                var html = "";
                                html += '<a href="javascript:void(0)" onClick="removeDefensive(' + model.id + ')" data-options="iconCls:\'icon-cancel\'" title="删除">删除</a>';
                                return html;
                            }
                            }
                        ]],
                        onClickRow: function (index, row) {
                            var arr = row.points.split(',');
                            var points = [];
                            for (var i = 0; i < arr.length; i += 2) {
                                points.push(new GLatLng(parseFloat(arr[i + 1]), parseFloat(arr[i])));
                            }
                            addPolygon(row.shape, points);
                        }
                    });
                    $(defensiveGrid.datagrid("getPager")).pagination({
                        beforePageText: "",
                        afterPageText: "共{pages}页"
                    });
                }
            }
        });

        datagrid.datagrid({
            url: '/camera/list.action',
            loadMsg: '数据载入中',
            pagination: true,
            rownumbers: true,
            pageSize: 20,
            fit: true,
            singleSelect: true,
            toolbar: $('.toolbar', datagrid),
            onBeforeLoad: function (p) {
                p.name = $.trim($('input[name="location"]', datagrid.datagrid('getPanel')).val());
                p.region = $('input[name="region"]', datagrid.datagrid('getPanel')).val();
            },
            loadFilter: function(data){
                comboTreeData['region'] = data.areaTree;
                return data.p||data;
            },
            columns: [[
                {
                    field: 'name', title: '监控点', width: 170
                },
                {
                    field: 'status', title: '状态', width: 30, formatter: function (status) {
                    return status ? "激活" : "禁用";
                }
                }, {
                    field: 'opera', title: '操作', width: 80, formatter: function (v, model) {
                        var html = "",str;
                        if(model.status==1) {
                            str =  '<a href="javascript:void(0)" onClick="recognitionCamera(' + model.id + ',\'' + model.name + '\')" data-options="iconCls:\'icon-cancel\'" title="识别">识别</a>';
                        } else {
                            str = '&emsp;&emsp;';
                        }
                        html += str;
                        html += '&emsp;';
                        html += '<a href="javascript:void(0)" onClick="updateCamera(' + model.id + ',\'' + model.name + '\')" data-options="iconCls:\'icon-edit\'" title="管理">管理</a>';
                        //html += '<a href="javascript:void(0)" onClick="removeCamera(' + model.id + ',\'' + model.name + '\')" data-options="iconCls:\'icon-cancel\'" title="删除">删除</a>';
                        return html;
                    }
                },
                {field: 'region', title: '行政区划', width: 150,formatter:util.formateRegion}
            ]],
            onClickCell: function (index, field) {
                if (field == "opera")return;//若是操作列,则返回,操作列有自己的事件
                var c = datagrid.datagrid('getRows')[index];
                if (!camera.checkLatLng(c)) {
                    $.messager.show({title: "添加监控点", msg: "监控点[" + c.name + "]经度或纬度缺失"});
                    return;
                }
                //移动结束后调用
                camera.aferShowInBounds = function () {
                    openInfoWindowHtml(camera.getMarker(c.id));
                    camera.aferShowInBounds = $.noop;
                }
                var LatLng = new GLatLng(+c.latitude, +c.longitude);
                map.setZoom(mapSettings.focusZoom);
                setTimeout(function () {
                    map.panTo(LatLng);
                }, 200);
            }
        });
        datagrid.datagrid('getPanel').on("keyup", 'input:text', function (e) {
            if (e.keyCode == 13) {
                datagrid.datagrid('reload');
            }
        })
        $(datagrid.datagrid("getPager")).pagination({
            beforePageText: "",
            afterPageText: "共{pages}页"
        });
        $('#query', datagrid.datagrid('getPanel')).click(function () {
            datagrid.datagrid('reload');
        });

        //添加监控点事件
        $(item.add).click(function (e) {
            e.preventDefault();
            var vLatLng = gClickLatLng,
                    model = {
                        latitude: vLatLng.lat(),
                        longitude: vLatLng.lng()
                    },
                    html = util.parser(map_camera_add_tmpl, model);
            $win.html(html);

            var $form = $win.find('form'),
                    $name = $win.find('input[name]');

            $form.submit(function () {
                $form.find('a.btn_ok').click();
                return false;
            });

            $win.dialog({
                title: "添加监控点",
                width: 300,
                modal: true,
                onOpen: function () {
                    $.parser.parse($win);

                    $name.textbox('textbox').focus();
                    $win.find('a.btn_ok').linkbutton({
                        iconCls: 'icon-ok',
                        onClick: function () {
                            if ($form.form('validate')) {
                                var obj = {}, arr = $form.serializeArray();
                                $.each(arr, function (i, o) {
                                    if (o.value != "") {
                                        obj[o.name] = o.value;
                                    }
                                });
                                $.post("/camera/add.action", obj, function (d) {
                                    showSingleCamera(d);
                                    datagrid.datagrid("reload");
                                    btn_cancel.click();
                                    if (d.id) {
                                        $.messager.show({title: "添加监控点", msg: "添加监控点成功!"});
                                    }else{
                                        $.messager.show({title: "添加监控点", msg: "添加失败！您没有添加监控点权限！"});
                                    }
                                });
                            }
                        }
                    });
                    var btn_cancel = $win.find('a.btn_cancel').linkbutton({
                        iconCls: 'icon-cancel',
                        onClick: function () {
                            $win.dialog('close');
                        }
                    });
                }
            });
        });
        //管理监控点事件
        $(item.edit).click(function (e) {
            e.preventDefault();
            var model = gOnClickMarker.dto,
                    title ="监控点管理" +  model.name;
            if (mainTabs.tabs('exists', title)) {
                mainTabs.tabs('close', title)
            }
            mainTabs.tabs('add', {
                title: title,
                closable: true,
                href: 'camera/update.action',
                queryParams:{id:model.id}
            });
        });
        //设置中心点
        $(item.setCenter).click(function (e) {
            e.preventDefault();
            $.messager.confirm('确认', '确定要设置此处为中心点吗?', function (r) {
                if (r) {
                    var vLatLng = gClickLatLng,
                            model = {
                                latitude: vLatLng.lat(),
                                longitude: vLatLng.lng()
                            };
                    $.post('/map/setCenter.action', model, function (d) {
                        if (d == "success") {
                            $.messager.show({title: "提示信息", msg: "设置中心点成功,请刷新页面!"});
                        }else{
                            $.messager.show({title: "提示信息", msg: "设置失败！您没有设置中心点权限！"});
                        }
                    });
                }
            })
        })
        //批量导入
        $(item.import).click(function (e) {
            e.preventDefault();
            $win.html(map_camera_import_tmpl);
            var $form = $win.find('form');
            $win.dialog({
                title: "批量导入监控点",
                width: 400,
                onOpen: function () {
                    $.parser.parse($win);
                    $win.find('a.btn_ok').linkbutton({
                        iconCls: 'icon-ok',
                        onClick: function () {
                            if ($form.form('validate')) {
                                $form.form('submit', {
                                    success: function (data) {
                                        if (data == 'success') {
                                            msg = '导入监控点成功!'
                                            datagrid.datagrid("reload");
                                            $win.dialog('close');
                                        } else {
                                            msg = '导入失败！请检查导入文件，或者您没有批量导入监控点权限！';
                                        }
                                        $.messager.show({
                                            title: '导入监控点',
                                            msg: msg
                                        });
                                    }
                                });
                            }
                            return false;
                        }
                    });
                    $win.find('a.btn_cancel').linkbutton({
                        iconCls: 'icon-cancel',
                        onClick: function () {
                            $win.dialog('close');
                        }
                    });
                }
            });
        })

        //删除监控点
        window.removeCamera = function (id, name) {
            $.messager.confirm('删除监控点', '是否确认删除？', function (r) {
                if (!r)return;
                $.ajax({
                    url: '/camera/remove.action',
                    data: {id: id},
                    type: "post",
                    success: function (d) {
                        var msg;
                        if (d == 'success') {
                            msg = '删除监控点成功!';
                            datagrid.datagrid("reload");
                            var marker = camera.getMarker(id);
                            mc.removeMarker(marker);
                            mainTabs.tabs("close", name);
                        } else {
                            msg = '删除监控点失败!'
                        }
                        $.messager.show({
                            title: '删除监控点',
                            msg: msg
                        });
                    }
                });
            })
        }
        window.recognitionCamera = function (id, name) {
            var title = name;
            if (mainTabs.tabs('exists', title)) {
                mainTabs.tabs('close', title)
            }
            mainTabs.tabs('add', {
                    title: title,
                    closable: true,
                    href: 'camera/camera.action?id=' + id
                });

        }
        window.updateCamera = function (id, name) {
            var title ="监控点管理" +  name;
            if (mainTabs.tabs('exists', title)) {
                mainTabs.tabs('close', title)
            }
            mainTabs.tabs('add', {
                    title: title,
                    closable: true,
                    href: 'camera/update.action',
                    queryParams:{id:id}
                });
        }
        //删除防御圈
        window.removeDefensive = function (id) {
            $.ajax({
                url: '/map/delDefenceRange.action',
                data: {id: id},
                type: "post",
                success: function (d) {
                    var msg;
                    if (d == 'success') {
                        msg = '删除防御圈成功!';
                        defensiveGrid.datagrid("reload");
                        //clearPolygon();
                        if (gDrawingPolygon != null) {
                            gDrawingPolygon.polygon.disableEditing();
                            gDrawingPolygon.clear();
                            gDrawingPolygon = null;
                        }
                    } else {
                        msg = '删除防御圈失败!'
                    }
                    $.messager.show({
                        title: '删除防御圈',
                        msg: msg
                    });
                }
            });
        }

        //显示单个监控点

        var showSingleCamera = function (dto) {
                    if (camera.hasCached(dto))return;
                    if (!camera.checkLatLng(dto))return;
                    var updateAction = "/camera/saveUpdatedCamera.action";
                    var markerOptions = {icon: gIconCamera, draggable: true};
                    if (!dto.status) {
                        markerOptions = {icon: gIconCameraInvalid, draggable: true};
                    }
                    var marker = showObject(map, dto, markerOptions, updateAction, function () {
                        datagrid.datagrid("reload");
                    });
                    //将添加到地图上的marker移除,由聚合框架来控制marker的显示
                    map.removeOverlay(marker);
                    marker.dto = dto;
                    GEvent.addListener(marker, "click", function () {
                        openInfoWindowHtml(marker);
                    });
                    GEvent.addListener(marker, "dblclick", function () {
                        if(marker.dto.status == 1) {
                            window.recognitionCamera(marker.dto.id, marker.dto.name);
                        } else {
                            $.messager.show({
                                title:'消息',
                                msg:'该监控点已失效！',
                                timeout:2000
                            });
                        }
                    });
                    mc.addMarker(marker);
                    camera.cache(dto.id, {dto: dto, marker: marker});
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
                            if(!!datas[dto.id]){
                                camera.setDto(dto);
                                //图标也要和状态一起也要跟着改变
                                var icon = [gIconCameraInvalid, gIconCamera][dto.status];
                                var oMarker = camera.getMarker(dto.id);
                                if(!!oMarker.Yv) {
                                    oMarker.setImage(icon.image);
                                    oMarker.setLatLng(new GLatLng(dto.latitude, dto.longitude));
                                }
                            }
                            return !!datas[dto.id];
                        },
                        cache: function (id, obj) {
                            return datas[id] = obj;
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
                        showInBounds: function (b, marker,hasLoad) {
                            /*if (hasLoad && !!marker) {
                                camera.aferShowInBounds();
                                return;
                            }*/
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
                                }
                            });
                        }
                    };
                })();

        $map.data("camera",camera);

        //当移动地图结束事件
        GEvent.addListener(map, "moveend", function () {
            var bounds = map.getBounds();
            camera.showInBounds(bounds,true,true);
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
        //隐藏“视频选项卡”
        $('#map_list').tabs('close',1);

        // IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readOnly]").keydown(function (e) {
            e.preventDefault();
        });
        window.document.title= $("#main_tabs").tabs().tabs("getSelected").panel("options").title;
    })(jQuery, window);
</script>

