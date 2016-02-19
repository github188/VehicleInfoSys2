<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'north',title:'布控任务列表',split:true" style="width:560px;height:50%">
        <div id="bk_task_datagrid"></div>
    </div>

    <div data-options="region:'center',title:'布控结果'" style="padding:5px;background:#eee;">
        <div id="bk_result_datagrid"></div>
    </div>

</div>
<div class="bk_task_datagrid_toolbar">
    <table>
        <tr>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
            <td>
                <a href="javascript:void(0)" id="btn_list_surveillance" class="easyui-linkbutton" data-options="iconCls:'icon-search'" selected=true plain=true>查找所有布控结果</a>
            </td>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
            <td>
                <a href="javascript:void(0)" id="btn_add_surveillance" class="easyui-linkbutton" data-options="iconCls:'icon-add'" plain=true>添加布控</a>
            </td>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
            <td>
                <a id="remove" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain=true>删除</a>
            </td>
            <td>
                <div class="datagrid-btn-separator"></div>
            </td>
        </tr>
    </table>
</div>
<div id="surveillance_win"></div>
<script type="tmpl" id="surveillance_tmpl">
<div style="position:relative;">
<div class="box" style="top: 5px;left: 5px;width:60%;height:auto;position:absolute;">
    <div class="box-small">
		<img id="pic2" width="100%" height="100%" src="{#imageUrl}" alt="" />
    	<div class="rect" id="rect">&nbsp;</div>
		<div class="rect" id="tagRect0" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect1" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect2" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect3" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect4" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect5" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect6" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect7" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect8" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="tagRect9" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="paperRect0" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="paperRect1" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="paperRect2" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="paperRect3" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="paperRect4" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="paperRect5" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="sunRect0" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="sunRect1" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="sunRect2" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="sunRect3" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="sunRect4" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="sunRect5" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="dropRect0" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="dropRect1" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="dropRect2" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="dropRect3" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="dropRect4" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="dropRect5" style="border:solid 1px Orange;">&nbsp;</div>
	</div>
    <div style="text-align:center;">
        <button id="previous" disabled="disabled" data-turn-index={#index} data-turn-str="previous">上一张</button>
        &ensp;&ensp;&ensp;&ensp;
        <button id="next" disabled="disabled" data-turn-index={#index} data-turn-str="next">下一张</button>
    </div>
</div>
<div style="top: 5px;right: 5px;position:absolute;width:320px;">
     <table class="info_table">
        <tr>
            <td>车牌</td>
            <td id="license">{#license}</td>
        </tr>
        <tr>
            <td>车牌颜色</td>
            <td id="plateColor">{#plateColor}</td>
        </tr>
        <tr>
            <td>车牌类型</td>
            <td id="plateType">{#plateType}</td>
        </tr>
        <tr>
            <td>车牌可信度</td>
            <td id="confidence">{#confidence}</td>
        </tr>
        <tr>
            <td>车牌归属地</td>
            <td id="licenseAttribution">{#licenseAttribution}</td>
        </tr>
        <tr>
            <td>车身颜色</td>
            <td id="carColor">{#carColor}</td>
        </tr>
        <tr>
            <td>过车时间</td>
            <td id="resultTime">{#util.formateTime(resultTime)}</td>
        </tr>
        <tr>
            <td>地点</td>
            <td id="location">{#location}</td>
        </tr>
		<tr>
			<td>车型</td>
			<td>{#vehicleKind}</td>
		</tr>
		<tr>
			<td>品牌</td>
			<td>{#vehicleBrand}</td>
		</tr>
		<tr>
			<td>车系</td>
			<td>{#vehicleSeries}</td>
		</tr>
		<tr>
			<td>款型</td>
			<td>{#vehicleStyle}</td>
		</tr>
		<%--<tr>
			<td>特征物</td>
			<td>{#characteristic}</td>
		</tr>--%>
		<tr>
			<td>年检标</td>
			<td id="tagNumbersa">{#tag==1?'有':'无'}</td>
		</tr>
		<tr>
			<td>纸巾盒</td>
			<td>{#paper==1?'有':'无'}</td>
		</tr>
		<tr>
			<td>遮阳板</td>
			<td>{#sun==1?'放下':'未放下'}</td>
		</tr>
		<tr>
			<td>挂饰</td>
			<td>{#drop==1?'有':'无'}</td>
		</tr>
    </table>
</div>
</div>

</script>
<script>
    (function ($) {
        var $query_win = $('#surveillance_win');
        var tab = util.getSelectedTab();
        var add_surveillance_task = $('#add_surveillance_task');
        var $surveillance_win = $('#surveillance_win');
        var add_surveillance_tmpl = $('#add_surveillance_tmpl').html();
        var surveillance_tmpl = $('#surveillance_tmpl').html();
        var $bk_task_datagrid = $('#bk_task_datagrid'),
                $bk_result_datagrid = $('#bk_result_datagrid');
        $('#btn_add_surveillance').on('click', function (e) {
            addSurveillance($bk_task_datagrid);
        });
        $('#btn_list_surveillance').on('click', function (e) {
            selectRow = 1;
            loadResultDatas()
        });
        //放大
        $surveillance_win.on('mouseover', '.box-small', function () {
            var findPic = $surveillance_win.find("#pic2");
            findPic.addimagezoom({ // single image zoom
                zoomablefade:false,
                zoomrange: [2, 15],
                magnifiersize: [500,500],
                magnifierpos: 'left',
                cursorshade: true,
                largeimage: findPic[0].src //<-- No comma after last option!
            })
        });

        //翻页
        $surveillance_win.on('click', '#previous,#next', function () {
            var self = this
            var turnIndex = $(self).data('turn-index')
            var turnStr = $(self).data('turn-str')

            //避免按钮在数据未加载完成时连续点击，总页数读取出现错误,引发提示框弹出提示
            self.disabled = true;

            turnPage(turnIndex, turnStr)
        })

        //翻页
        var turnPage = function (index, str) {
            var dataRows = $bk_result_datagrid.datagrid("getRows"),
                    pageNum = $bk_result_datagrid.datagrid('getPager').pagination('options').pageNumber,
                    pageSize = $bk_result_datagrid.datagrid('getPager').pagination('options').pageSize,
                    row;
            if (str == 'previous') {
                if (pageNum == 1 && index == 0) {
                    $.messager.show({
                        title: '提示消息',
                        msg: '已经是第一张',
                        timeout: 5000,
                        showType: 'slide'
                    });
                    return false;
                }
                if (index == 0) {
                    loadResultDatas(--pageNum, function () {
                        var dataRows_1 = $bk_result_datagrid.datagrid("getRows");
                        showSurveillanceWindow(dataRows_1[pageSize - 1])
                    });
                } else {
                    row = dataRows[--index]
                    showSurveillanceWindow(row)
                }
            }

            if (str == 'next') {
                if (pageNum == getPageCount() && index == dataRows.length - 1) {
                    $.messager.show({
                        title: '提示消息',
                        msg: '已经是最后一张',
                        timeout: 5000,
                        showType: 'slide'
                    });
                    return false;
                }
                if (index == dataRows.length - 1) {
                    loadResultDatas(++pageNum, function () {
                        var dataRows_1 = $bk_result_datagrid.datagrid("getRows");
                        showSurveillanceWindow(dataRows_1[0])
                    });
                } else {
                    row = dataRows[++index];
                    showSurveillanceWindow(row)
                }
            }
        }
        //总页数
        var getPageCount = function () {
            var opts = $bk_result_datagrid.datagrid('getPager').pagination('options'),
                    pageSize = opts.pageSize,
                    total = opts.total;
            return (total + pageSize - 1) / pageSize | 0;
        }
        tab.on('click', '#remove', function (e) {
            e.preventDefault();
            var grid = $bk_task_datagrid;
            var selections = grid.datagrid("getChecked");
            var rows = grid.datagrid('getRows');
            var allow = selections.map(function (e) {
                if (e.status == 1) {
                    return false;
                }
            })
            if (rows.length <= 0) {
                $.messager.alert("提示!", "当前还没有布控任务!");
                return;
            }
            if (selections.length <= 0) {
                $.messager.alert("提示!", "请选择要删除的记录!");
                return;
            }
            var falseIndex = $.inArray(false, allow);
            if (falseIndex != -1) {
                $.messager.alert("提示!", "要删除的记录正在布控中,请先停止布控!");
                return;
            }
            $.messager.confirm('提示!', '是否确认删除？', function (r) {
                if (r) {
                    $.messager.defaults.ok = '是';
                    $.messager.defaults.cancel = '否';
                    $.messager.confirm('选择', '是否删除该任务下的结果？', function (r) {
                        var flag = false;
                        if (r) {
                            flag = true;
                        }
                        var ids = selections.map(function (e) {
                            return e.id + ''
                        });
                        $.ajax({
                            url: '/surveillance/remove.action',
                            type: "post",
                            data: {
                                ids: ids.toString(),
                                isAllDelete: flag
                            },
                            success: function (data) {
                                if (data == "success") {
                                    $.messager.show({
                                        title: '消息',
                                        msg: '删除成功！',
                                        timeout: 2000
                                    });
                                    grid.datagrid("load");
                                } else {
                                    $.messager.show({
                                        title: '删除失败！',
                                        msg: data,
                                        timeout: 2000
                                    });
                                }
                            }
                        });
                        $.messager.defaults.ok = '确定';
                        $.messager.defaults.cancel = '取消';
                    });
                }
            });
        })

        var getParamData=function(){
            var data = {
                plate: selectRow.plate,
                startTime: util.formateTime(selectRow.startTime),
                endTime: util.formateTime(selectRow.endTime),
                cameraIds: selectRow.camera,
                carColor:selectRow.carcolor,
                drop: selectRow.drop,
                paper: selectRow.paper,
                plateType: selectRow.plateType,
                status: selectRow.status,
                sun: selectRow.sun,
                tag: selectRow.tag,
                vehicleBrand: selectRow.vehicleBrand,
                vehicleKind: selectRow.vehicleKind,
                vehicleStyle: selectRow.vehicleStyle
            }

            $.each(data,function(k,v){
                data[k]= $.trim(v);
            })
            return data;
        }
        var loadResultDatas = function ( pageNumber, callback, id) {
            $bk_result_datagrid.datagrid({
                queryParams:{surveillanceTaskId : id},
                url: '/surveillanceresult/list.action',
                loadMsg: '数据载入中',
                pagination: true,
                pageNumber: pageNumber,
                rownumbers: true,
                fit: true,
                singleSelect: true,
                columns: [[
                    {
                        field: 'license', title: '车牌号', width: 80
                    },
                    {
                        field: 'plateType', title: '车牌类型', width: 70
                    },
                    {
                        field: 'carColor', title: '车身颜色', width: 70
                    },
                    {
                        field: 'vehicleKind', title: '车型', width: 70
                    },
                    {
                        field: 'vehicleBrand', title: '品牌', width: 70
                    },
                    {
                        field: 'vehicleSeries', title: '车系', width: 70
                    },
                    {
                        field: 'vehicleStyle', title: '款型', width: 70
                    },
                    {field: 'tag', title: '年检标', width: 50, formatter: util.formateCharacteristic},

                    {field: 'paper', title: '纸巾盒', width: 50, formatter: util.formateCharacteristic},

                    {field: 'sun', title: '遮阳板', width: 50, formatter: util.formateSun},

                    {field: 'drop', title: '挂饰', width: 50, formatter: util.formateCharacteristic},
                    {
                        field: 'location', title: '过车地点', width: 100
                    },
                    {field: 'resultTime', title: '过车时间', width: 150, formatter: util.formateTime}
                ]],
                onBeforeLoad:function(p){
                    $.extend(p,getParamData());
                },
                onDblClickRow: function (index, row) {
                    showSurveillanceWindow(row)
                },
                onLoadSuccess: function (pager) {
                    $.each(pager.rows, function (i, o) {
                        o.index = i;
                    });
                    if (typeof callback == 'function') {
                        callback();
                        //这个callback用于在查看结果详情的时候,翻下一张,翻页时弹出一个window显示结果详情
                        //若不设为null,在上面的翻页后datagrid loadSuccess(包括查询,刷新等)后依然会弹出一个window
                        callback = null;
                    }
                }
            });
        }
        //详情窗口缓存
        var showWindows = function (html, model) {
            var $win = $surveillance_win;
            $win.html(html);
            if (!$win.data('window')) {
                $win.window({
                    width: 800,
                    height: 600,
                    closed: true
                })
            }
            return $win.window({
                content: html,
                title: model.license,
                onOpen: function () {
                    var $surveillance_win = $(this).parent().parent();
                    drawPlate($surveillance_win, model)
                },
                onResize: function () {
                    //控制按钮操作频繁
                    setTimeout(function(){
                        $("#previous").removeAttr("disabled");
                        $("#next").removeAttr("disabled");
                    },1000);

                    var $surveillance_win = $(this).parent().parent();
                    $win.html(html);//
                    drawPlate($surveillance_win, model)
                },
                onBeforeClose:function(){
                    $win.html("")
                    var $tracker = $(".zoomtracker");
                    if($tracker) {
                        $tracker.remove();
                    }
                }
            }).window("open");
        }
        var drawPlate = function ($win, model) {
            var $pic = $surveillance_win.find('#pic2'),
                    $rect = $surveillance_win.find('#rect'),
                    src = model.imageUrl;

            var orginalImage = new Image();
            orginalImage.src = src;
            $(orginalImage).bind('load', function () {
                var orginalWidth = orginalImage.width;
                var orginalHeight = orginalImage.height;

                var picWidth = $pic.width();
                var picHeight = $pic.height();
                var scalex = picWidth / orginalWidth,
                        scaley = picHeight / orginalHeight,
                        xposs = model.locationLeft * scalex,
                        yposs = model.locationTop * scaley,
                        rectWidth = model.locationRight * scalex - xposs,
                        rectHeight = model.locationBottom * scaley - yposs;
                /*车牌结果框已经实现,但是目前框的位置可能不准确,暂时注释*/
                 $rect.css({
                 display:'block',
                 top:yposs,
                 left:xposs,
                 width:rectWidth,
                 height:rectHeight
                 })
                 
                 
                 if(model.dropRectAndScore != null){
                	 //挂饰
                     var dropArray = JSON.parse(model.dropRectAndScore).rect;
                	for(var i=0;i<dropArray.length;i++){
                		var dropInfo = dropArray[i];
                		 if(i>5){
                			 return;
                		 }
                		 var $dropRect = $query_win.find('#dropRect'+i)
    	                 var dropx = dropInfo.x1*scalex;
    	                 var dropy = dropInfo.y1*scaley;
    	                 var dropWidth = dropInfo.x2*scalex-dropx;
    	                 var dropHeight = dropInfo.y2*scaley-dropy;                                 
    	                 $dropRect.css({
    	                     display:'block',
    	                     top:dropy,
    	                     left:dropx,
    	                     width:dropWidth,
    	                     height:dropHeight
    	                 });
                	 }
	     
                 }
                                	 
               	 if(model.paperRectAndScore != null){
               		 //纸巾盒
                     var paperArray = JSON.parse(model.paperRectAndScore).rect;
	                 for(var i=0;i<paperArray.length;i++){
	                	 var paperInfo = paperArray[i];
                		 if(i>5){
                			 return;
                		 }
		                 var $paperRect = $query_win.find('#paperRect'+i)
		                 var paperx = paperInfo.x1*scalex;
		                 var papery = paperInfo.y1*scaley;
		                 var paperWidth = paperInfo.x2*scalex-paperx;
		                 var paperHeight = paperInfo.y2*scaley-papery;
		                 $paperRect.css({
		                     display:'block',
		                     top:papery,
		                     left:paperx,
		                     width:paperWidth,
		                     height:paperHeight
		                 });
                     }
               	 }
                                  
                 if(model.sunRectAndScore != null){
                	 //遮阳板
                     var sunArray = JSON.parse(model.sunRectAndScore).rect;
                     for(var i=0;i<sunArray.length;i++){
                    	 var sunInfo = sunArray[i];
                    	 if(i>5){
                			 return;
                		 }
		                 var $sunRect = $query_win.find('#sunRect'+i)
		                 var sunx = sunInfo.x1*scalex;
		                 var suny = sunInfo.y1*scaley;
		                 var sunWidth = sunInfo.x2*scalex-sunx;
		                 var sunHeight = sunInfo.y2*scaley-suny;
		                 $sunRect.css({
		                     display:'block',
		                     top:suny,
		                     left:sunx,
		                     width:sunWidth,
		                     height:sunHeight
		                 });
                    }
                 }
	             if(model.tagRectAndScore != null){
	                 //年检标签
	                 var tagArray = JSON.parse(model.tagRectAndScore).rect;
		             for(var i=0;i<tagArray.length;i++){
		            	 var tagInfo = tagArray[i];
                		 if(i>9){
                			 return;
                		 }
		                 var $tagRect = $('#tagRect'+i)
		                 var tagx = tagInfo.x1*scalex;
		                 var tagy = tagInfo.y1*scaley;
		                 var tagWidth = tagInfo.x2*scalex-tagx;
		                 var tagHeight = tagInfo.y2*scaley-tagy;
		                 $tagRect.css({
		                     display:'block',
		                     top:tagy,
		                     left:tagx,
		                     width:tagWidth,
		                     height:tagHeight
		                 });
	                }
                     if(tagArray.length > 0){
                         $("#tagNumbersa").text(tagArray.length);
                     }else{
                         $("#tagNumbersa").text('无');
                     }
              	 }
                 
                 
            })
        }
        var showSurveillanceWindow = function (row) {
            var cloneRow = $.extend({}, row);
            cloneRow.imageUrl = '${PictureServerHost}/' + encodeURI(row.imageUrl);
            var html = util.parser(surveillance_tmpl, cloneRow);
            showWindows(html, cloneRow);
        }

        //选中的布控任务
        var selectRow;
        $bk_task_datagrid.datagrid({
            multiple: true,
            url: '/surveillance/list.action',
            pagination: true,
            rownumbers: true,
            singleSelect: true,
            checkOnSelect: false,
            selectOnCheck: false,
            fit: true,
            onClickRow: function (i, row) {
                selectRow = row;
                loadResultDatas('','',selectRow.id)
            },
            columns: [[
                {
                    field: 'ck', checkbox: true
                },
                {
                    field: 'id', title: '任务ID',hidden: true, width: 120
                },
                {
                    field: 'cameraName', title: '监控点名称', width: 120
                },
                {
                    field: 'plate', title: '车牌号', width: 80
                },
                /*{
                 field: 'name', title: '任务名称', width: 70
                 },*/
                {
                    field: 'plateType', title: '车牌类型', width: 70, formatter: function (value, row, index) {
                    row = row || value;
                    var plateValue = '';
                    var plate_type_arr = {
                        0: '未知车牌',
                        1: '蓝牌',
                        2: '黑牌',
                        3: '单排黄牌',
                        4: '双排黄牌',
                        5: '警车车牌',
                        6: '武警车牌',
                        7: '个性化车牌',
                        8: '单排军车',
                        9: '双排军车',
                        10: '使馆牌',
                        11: '香港牌',
                        12: '拖拉机',
                        13: '澳门牌',
                        14: '厂内牌'
                    }

                    $.each(plate_type_arr, function (key, val) {
                        if (value == key) {
                            plateValue = val;
                        }
                    })
                    return plateValue;
                }
                },
                {
                    field: 'carcolor', title: '车身颜色', width: 70
                },
                /*{
                 field: 'location', title: '监控点路段', width: 70
                 },*/
                {
                    field: 'vehicleKind', title: '车型', width: 70
                },
                {
                    field: 'vehicleBrand', title: '品牌', width: 70
                },
                {
                    field: 'vehicleSeries', title: '车系', width: 70
                },
                {
                    field: 'vehicleStyle', title: '款型', width: 70
                },
                {field: 'tag', title: '年检标', width: 50, formatter: util.formateCharacteristic},

                {field: 'paper', title: '纸巾盒', width: 50, formatter: util.formateCharacteristic},

                {field: 'sun', title: '遮阳板', width: 50, formatter: util.formateSun},

                {field: 'drop', title: '挂饰', width: 50, formatter: util.formateCharacteristic},

                {field: 'startTime', title: '开始时间', width: 150, formatter: util.formateTime},
                {field: 'endTime', title: '结束时间', width: 150, formatter: util.formateTime},
                {
                    field: 'status', title: '状态', width: 70, formatter: function (v) {
                    return v == 1 ? '布控中' : '结束布控';
                }
                },
                {
                    field: 'xxx', title: '操作', width: 50, formatter: function (v, row) {
                    var rowId = '布控任务' + row.id
                    var html = row.status == 1 ? '<a href="javascript:void(0)" id="' + rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-cancel\'" >停止</a>' : '';
                    tab.off('click', '#' + rowId).on('click', '#' + rowId, function () {
                        //停止布控任务
                        stopTask(row.id);
                    })
                    return html;
                }
                }
            ]],
            toolbar: $('.bk_task_datagrid_toolbar', tab[0]).get(0)
        });


        var stopTask = function (taskId) {
            $.post('/surveillance/stop.action', {taskId: taskId}, function (msg) {
                if (null != msg && '' != msg) {
                    $.messager.show({
                        title: '提示',
                        msg: '任务已于' + util.formateTime(msg) + '停止，无需停止！',
                        timeout: 2000
                    })
                }
            })
            $bk_task_datagrid.datagrid('reload')
        }

        var refresh = function () {
            setTimeout(function () {
                if ($bk_result_datagrid.data('datagrid')) {
                    $bk_result_datagrid.datagrid('reload');
                    refresh();
                }
            }, 5000);
        };
        refresh();
        selectRow = 1;
        loadResultDatas();
    })(jQuery)
</script>