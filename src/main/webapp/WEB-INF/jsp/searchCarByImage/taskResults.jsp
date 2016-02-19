<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dd" class="easyui-layout" style="width:100%;height:100%;">
    <div id="task_query_win"
         style="position: relative; width: 100%; height: 100%;"></div>

    <div id="retrieval" data-options="region:'south',title:'检索结果&ensp;&ensp;&ensp;当前检索进度：0%  ',split:true" style="height:250px;">
        <div id="task_retrieval_results"></div>
    </div>

    <%--<div data-options="region:'east',title:'详细信息对比',split:true" style="width:45%;">
        <div id="task_detailed_information_original" style="float: left;margin-left: 65px"></div>
        <div id="task_detailed_information" style="float: right;"></div>
    </div>--%>

    <div data-options="region:'center',title:'原始图片（左）与目标图片（右）'" style="background:#eee;">
        <div id="task_img_original" style="width: 50%;height: 100%;float: left;"></div>
        <div id="task_img" style="width: 50%;height: 100%;float: right;"></div>
    </div>
</div>

<%-- 没有任何结果时用 --%>
<script type="tmpl" id="wu_task_vlpr_vfm_result_original_tmpl">
<div style="position:relative;left:40px;float: left;">
     <table class="info_table" style="margin:0 auto;">
        <tr>
            <td colspan="3">原始图片详情</td>
        </tr>
        <tr>
            <td>车牌</td>
            <td id="license">无&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</td>
        </tr>
        <tr>
            <td>车牌颜色</td>
            <td id="plateColor">无</td>
        </tr>
        <tr>
            <td>车牌类型</td>
            <td id="plateType">无</td>
        </tr>
        <tr>
            <td>车牌可信度</td>
            <td id="confidence">无</td>
        </tr>
        <tr>
            <td>车牌归属地</td>
            <td id="licenseAttribution">无</td>
        </tr>
        <tr>
            <td>车身颜色</td>
            <td id="carColor">无</td>
        </tr>
        <tr>
            <td>过车时间</td>
            <td id="resultTime">0000-00-00&ensp;00:00:00</td>
        </tr>
        <tr>
            <td>监控点</td>
            <td id="location">无</td>
            <%--<td id="location">{#location}</td>--%>
        </tr>
		<tr>
			<td>车型</td>
			<td>无</td>
		</tr>
		<tr>
			<td>品牌</td>
			<td>无</td>
		</tr>
		<tr>
			<td>车系</td>
			<td>无</td>
		</tr>
		<tr>
			<td>款型</td>
			<td>无</td>
		</tr>
		<%--<tr>
			<td>特征物</td>
			<td>{#characteristic}</td>
		</tr>--%>
		<tr>
			<td>年检标</td>
			<td>无</td>
		</tr>
		<tr>
			<td>纸巾盒</td>
			<td>无</td>
		</tr>
		<tr>
			<td>遮阳板</td>
			<td>无</td>
		</tr>
		<tr>
			<td>挂饰</td>
			<td>无</td>
		</tr>
    </table>
</div>
</script>


<script type="tmpl" id="task_vlpr_vfm_result_original_tmpl">
<div style="position:relative;left:40px;float: left;">
     <table class="info_table" style="margin:0 auto;">
        <tr>
            <td colspan="3">原始图片详情</td>
        </tr>
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
            <td id="plateType">{#util.formatePlateType(plateType)}</td>
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
            <td>监控点</td>
            <td id="location"></td>
            <%--<td id="location">{#location}</td>--%>
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
			<td>{#tag==1?'有':'无'}</td>
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
</script>
<script type="tmpl" id="task_vlpr_vfm_result_originals_tmpl">
<div>
    <div class="box-small">
		<img width="100%" height="100%" src="{#imageUrl}" alt="" />
    	<div class="zoomMark" id="zoomMark">&nbsp;</div>
    	<div class="rect" id="rect">&nbsp;</div>
		<div class="rect" id="tagRect0">&nbsp;</div>
		<div class="rect" id="tagRect1">&nbsp;</div>
		<div class="rect" id="tagRect2">&nbsp;</div>
		<div class="rect" id="tagRect3">&nbsp;</div>
		<div class="rect" id="tagRect4">&nbsp;</div>
		<div class="rect" id="tagRect5">&nbsp;</div>
		<div class="rect" id="tagRect6">&nbsp;</div>
		<div class="rect" id="tagRect7">&nbsp;</div>
		<div class="rect" id="tagRect8">&nbsp;</div>
		<div class="rect" id="tagRect9">&nbsp;</div>
		<div class="rect" id="paperRect0">&nbsp;</div>
		<div class="rect" id="paperRect1">&nbsp;</div>
		<div class="rect" id="paperRect2">&nbsp;</div>
		<div class="rect" id="paperRect3">&nbsp;</div>
		<div class="rect" id="paperRect4">&nbsp;</div>
		<div class="rect" id="paperRect5">&nbsp;</div>
		<div class="rect" id="sunRect0">&nbsp;</div>
		<div class="rect" id="sunRect1">&nbsp;</div>
		<div class="rect" id="sunRect2">&nbsp;</div>
		<div class="rect" id="sunRect3">&nbsp;</div>
		<div class="rect" id="sunRect4">&nbsp;</div>
		<div class="rect" id="sunRect5">&nbsp;</div>
		<div class="rect" id="dropRect0">&nbsp;</div>
		<div class="rect" id="dropRect1">&nbsp;</div>
		<div class="rect" id="dropRect2">&nbsp;</div>
		<div class="rect" id="dropRect3">&nbsp;</div>
		<div class="rect" id="dropRect4">&nbsp;</div>
		<div class="rect" id="dropRect5">&nbsp;</div>
	</div>
    <div class="boxbig" id="boxbig">
    </div>
</div>
</script>

<script type="tmpl" id="vlpr_vfm_result_tmpl">
<div style="float: left;position:relative;left:90px">
     <table class="info_table" style="margin:0 auto;">
        <tr>
            <td colspan="3">目标图片详情</td>
        </tr>
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
            <td>监控点</td>
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
		<tr>
			<td>年检标</td>
			<td>{#tag==1?'有':'无'}</td>
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
</script>
<script type="tmpl" id="vlpr_vfm_results_tmpl">
<div>
    <div class="box-small">
		<img width="100%" height="100%" src="{#imageUrl}" alt="" />
    	<div class="zoomMark" id="zoomMark">&nbsp;</div>
    	<div class="rect" id="rect">&nbsp;</div>
		<div class="rect" id="tagRect0">&nbsp;</div>
		<div class="rect" id="tagRect1">&nbsp;</div>
		<div class="rect" id="tagRect2">&nbsp;</div>
		<div class="rect" id="tagRect3">&nbsp;</div>
		<div class="rect" id="tagRect4">&nbsp;</div>
		<div class="rect" id="tagRect5">&nbsp;</div>
		<div class="rect" id="tagRect6">&nbsp;</div>
		<div class="rect" id="tagRect7">&nbsp;</div>
		<div class="rect" id="tagRect8">&nbsp;</div>
		<div class="rect" id="tagRect9">&nbsp;</div>
		<div class="rect" id="paperRect0">&nbsp;</div>
		<div class="rect" id="paperRect1">&nbsp;</div>
		<div class="rect" id="paperRect2">&nbsp;</div>
		<div class="rect" id="paperRect3">&nbsp;</div>
		<div class="rect" id="paperRect4">&nbsp;</div>
		<div class="rect" id="paperRect5">&nbsp;</div>
		<div class="rect" id="sunRect0">&nbsp;</div>
		<div class="rect" id="sunRect1">&nbsp;</div>
		<div class="rect" id="sunRect2">&nbsp;</div>
		<div class="rect" id="sunRect3">&nbsp;</div>
		<div class="rect" id="sunRect4">&nbsp;</div>
		<div class="rect" id="sunRect5">&nbsp;</div>
		<div class="rect" id="dropRect0">&nbsp;</div>
		<div class="rect" id="dropRect1">&nbsp;</div>
		<div class="rect" id="dropRect2">&nbsp;</div>
		<div class="rect" id="dropRect3">&nbsp;</div>
		<div class="rect" id="dropRect4">&nbsp;</div>
		<div class="rect" id="dropRect5">&nbsp;</div>
	</div>
    <div class="boxbig" id="boxbig">
    </div>
</div>
</script>

<script>
    (function ($) {
        var wu_vlpr_vfm_result_original_tmpl = $('#wu_task_vlpr_vfm_result_original_tmpl').html();

        var vlpr_vfm_result_original_tmpl = $('#task_vlpr_vfm_result_original_tmpl').html();
        var vlpr_vfm_result_originals_tmpl = $('#task_vlpr_vfm_result_originals_tmpl').html();

        var vlpr_vfm_result_tmpl = $('#vlpr_vfm_result_tmpl').html();
        var vlpr_vfm_results_tmpl = $('#vlpr_vfm_results_tmpl').html();

        var $bk_task_datagrid = $('#bk_task_datagrid'),
                $bk_result_datagrid = $('#task_retrieval_results');
        $('#btn_add_surveillance').on('click', function (e) {
            addSurveillance($bk_task_datagrid);
        });

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
        var html11 = null;
        var html1 = null;
        var soucheid = null;
        scar_loadResultDatas = function ( pageNumber, vlprVfmTaskId, vlprTaskId, serialNumber) {
            soucheid = vlprVfmTaskId;
            if(serialNumber){
                $.get("/serachCarByImage/originallistdetails.action",{'taskID':serialNumber},function (data){
                    $("#task_detailed_information_original").empty();
                    $("#task_img_original").empty();
                    var cloneRowses = $.extend({}, data.rows[0]);
                    cloneRowses.imageUrl = '${PictureServerHost}/' + encodeURI(data.rows[0].imageUrl);
                    html11 = util.parser(vlpr_vfm_result_original_tmpl, cloneRowses);
                    var html22 = util.parser(vlpr_vfm_result_originals_tmpl, cloneRowses);
                    $("#task_detailed_information_original").append(html11);
                    $("#task_img_original").append(html22);
                });
            }else if(vlprTaskId){
                $.get("/serachCarByImage/originallisttask.action",{'taskID':vlprTaskId},function (data){
                    $("#task_detailed_information_original").empty();
                    $("#task_img_original").empty();
                    var cloneRowses = $.extend({}, data.rows[0]);
                    var aa = data.rows[0].filePath;
                    var bb = aa.replace("file://D:/","").replace("\\","/");
                    cloneRowses.imageUrl = '${PictureServerHost}/' + encodeURI(bb);
                    html11 = util.parser(wu_vlpr_vfm_result_original_tmpl, cloneRowses);
                    var html22 = util.parser(vlpr_vfm_result_originals_tmpl, cloneRowses);
                    $("#task_detailed_information_original").append(html11);
                    $("#task_img_original").append(html22);
                });
            }else if(vlprVfmTaskId){
                $.get("/serachCarByImage/originallisttaskid.action",{'taskID':vlprVfmTaskId},function (data){
                    $("#task_detailed_information_original").empty();
                    $("#task_img_original").empty();
                    var cloneRowses = $.extend({}, data.rows[0]);
                    var aa = data.rows[0].desImagePath;
                    var bb = aa.replace("D:/","").replace("\\","/");
                    cloneRowses.imageUrl = '${PictureServerHost}/' + encodeURI(bb);
                    html11 = util.parser(wu_vlpr_vfm_result_original_tmpl, cloneRowses);
                    var html22 = util.parser(vlpr_vfm_result_originals_tmpl, cloneRowses);
                    $("#task_detailed_information_original").append(html11);
                    $("#task_img_original").append(html22);
                });
            }

            var clickTimer = null;
            $bk_result_datagrid.datagrid({
                queryParams:{taskID:vlprVfmTaskId},
                url: '/serachCarByImage/list.action',
                loadMsg: '数据载入中',
                pagination: true,
                pageNumber: 1,
                rownumbers: true,
                fit: true,
                singleSelect: true,
                columns: [[
                    {
                        field: 'license', title: '车牌号', width: 80
                    },
                    {
                        field: 'plateType', title: '车牌类型', width: 80
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
                        field: 'location', title: '监控点', width: 100
                    },
                    {field: 'resultTime', title: '过车时间', width: 150, formatter: util.formateTime},
                    {
                        field: 'vfmScore', title: '匹配度', width: 100
                    }
                ]],
                onBeforeLoad:function(p){
                    $.extend(p,getParamData());
                },
                onClickRow: function (index, row) {
                    //清除延时触发的事件
                    clearTimeout(clickTimer);
                    //单击事件延时300ms触发
                    clickTimer = setTimeout(function(){
                        $("#task_detailed_information").empty();
                        $("#task_img").empty();
                        showSurveillanceWindow(row);
                    },300);

                },
                onDblClickRow: function (index, row) {
                    //清除延时触发的事件
                    clearTimeout(clickTimer);
                    $("#task_detailed_information").empty();
                    $("#task_img").empty();
                    //双击事件的具体操作
                    showResultWindow(row);
                },
                onLoadSuccess:function (pager) {

                }
            });
        }
        var html2 = null;
        var showSurveillanceWindow = function (row) {
            var cloneRow = $.extend({}, row);
            cloneRow.imageUrl = '${PictureServerHost}/' + encodeURI(row.imageURL);
            //html1 = util.parser(vlpr_vfm_result_tmpl, cloneRow);
            html2 = util.parser(vlpr_vfm_results_tmpl, cloneRow);
            //$("#task_detailed_information").append(html1);
            $("#task_img").append(html2);
        }

        var showResultWindow = function (row) {
            var cloneRow = $.extend({}, row);
            cloneRow.imageUrl = '${PictureServerHost}/' + encodeURI(row.imageURL);
            html1 = util.parser(vlpr_vfm_result_tmpl, cloneRow);
            html2 = util.parser(vlpr_vfm_results_tmpl, cloneRow);
            $("#task_img").append(html2);
            var totalHtml = "<div>"+html11+html1+"</div>";
            showWindow(totalHtml);
        }

        //详情窗口缓存
        var showWindow = function (html) {
            var $win=$("#task_query_win");
            if(!$win.data('window')){
                $win.window({
                    width: 630,
                    height: 630,
                    closed:true
                })
            }
            return $win.window({
                content:html,
                title:"对比信息",
                onOpen: function () {

                },
                onResize:function(){

                }
            }).window("open");
        }
        //选中的布控任务
        var selectRow;

        var refresh = function () {
            setTimeout(function () {
                if ($bk_result_datagrid.data('datagrid')) {
                    $bk_result_datagrid.datagrid('reload');
                    $.post("/serachCarByImage/originallisttaskid.action",{'taskID':soucheid},function (data){
                        var title = "检索结果&ensp;&ensp;&ensp;当前检索进度：" +  data.rows[0].progress + "% "
                        $('#retrieval').panel('setTitle', title);
                        if(data.rows[0].progress<100){
                            refresh();
                        }
                    });
                }
            }, 5000);
        };
        refresh();
        selectRow = 1;
    })(jQuery)
</script>