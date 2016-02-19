<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="bk_task_detail_window"></div>

<!-- 详情信息模板 -->
<script type="tmpl" id="bk_task_detail_tmpl">
	<div>
        <fieldset style="margin:10px 10px">
            <legend>布控基本信息</legend>
            <div>
            	&ensp;任务名称&nbsp;:&ensp;<input value="{#name}" id="bk_detail_surveillance_task_name"  type="text" readonly="true" class="easyui-textbox" style="height:20px;width:35%">
                &ensp;监控点&nbsp;:&ensp;<input value="{#camera}" id="bk_detail_surveillance_cameraIds" type="text" readonly="true" class="easyui-textbox" style="height:20px;width:40%"><br/>
                &ensp;&ensp;&ensp;车牌号&nbsp;:&ensp;<input value="{#plate}" id="bk_detail_plate_number" type="text" readonly="true" class="easyui-textbox" style="height:20px;width:129px;">
                &ensp;特征物&nbsp;:&ensp;<input value="{#characteristics}" id="bk_detail_characteristics" type="text" readonly="true" class="easyui-textbox"  style="height:20px;width:129px;">
                &ensp;车身颜色&nbsp;:&ensp;<input value="{#carcolor}" id="bk_detail_body_color" type="text" readonly="true" class="easyui-textbox" style="height:20px;width:129px;"><br/>
                &ensp;&ensp;&ensp;&ensp;&ensp;车型&nbsp;:&ensp;<input value="{#vehicleKind}" id="bk_detail_motorcycle_type" class="easyui-textbox" type="text" readonly="true" style="height:20px;width:129px;">
                &ensp;车牌类型&nbsp;:&ensp;<input value="{#plateType}" id="bk_detail_plate_type" type="text" readonly="true" class="easyui-textbox" style="height:20px;width:129px;"><br/>
                &ensp;&ensp;&ensp;&ensp;&ensp;品牌&nbsp;:&ensp;<input value="{#vehicleBrand}" readonly="true" id="bk_detail_brand" class="easyui-textbox" style="height:20px;width:129px;">
                &nbsp;&nbsp;车系&nbsp;:&nbsp;<input value="{#vehicleSeries}" id="bk_detail_series" readonly="true" class="easyui-textbox" style="height:20px;width:100px;">
                &nbsp;车款&nbsp;:&nbsp;<input value="{#vehicleStyle}" readonly="true" id="bk_detail_model" class="easyui-textbox" style="height:20px;width:100px;"><br/>
				&ensp;&ensp;&ensp;布控人&nbsp;:&ensp;<input value="{#peopleName}" readonly="true" id="bk_detail_surveillance_people_name" type="text" class="easyui-textbox" style="height:20px;width:129px" data-options="required:true">                              
                &ensp;布控人联系电话&nbsp;:&ensp;<input value="{#peopleTel}" readonly="true" id="bk_detail_surveillance_people_tel" type="text" class="easyui-textbox" style="height:20px;width:180px;" data-options="required:true"><br/>
                &ensp;布控单位&nbsp;:&ensp;<input value="{#unitName}" readonly="true" id="bk_detail_surveillance_unit_name" type="text" class="easyui-textbox" style="height:20px;width:445px;" data-options="required:true"><br/>
                &ensp;布控简介&nbsp;:&ensp;<input value="{#detail}" readonly="true" id="bk_detail_surveillance_detail" class="easyui-textbox" data-options="multiline:true" style="height:65px;width:445px;"><br/>
             </div>
        </fieldset>
		<fieldset style="margin:10px 10px 2px 10px;padding:5px 5px">
			<legend>审批流程</legend>
			<div id="bk_approval_process_datagrid"></div>
		</fieldset>
		<div id="div_buttons_group" style="position:absolute;bottom:10px;right:20px;display:none">
			<a href="javascript:void(0)" class="btn_ok" >审核</a>
			<a href="javascript:void(0)" class="btn_cancel" >关闭</a>
        </div>
</div>
</script>

<script type="text/javascript">
$(function(){
    //详情信息公共方法
    window.showDeatailSurveillanceTask = function editSurveillance(row,showFlage,callback){
    	
    	var $win=$("#bk_task_detail_window");
    	var detail_tmpl_html = $("#bk_task_detail_tmpl").html();
    	
    	//特征物设置
		var characters=[];
		if(row.tag==1){
			characters.push('年检标');
		}
		if(row.paper==1){
			characters.push('纸巾盒');				
		}
		if(row.sun==1){
			characters.push('遮阳板');
		}
		if(row.drop==1){
			characters.push('挂饰');
		}
		row.characteristics=characters.join();
		
		//车牌类型设置
    	var plateTypes = [];
		if(row.plateType==0){
			plateTypes.push("未知车牌");
		}
		if(row.plateType==1){
			plateTypes.push("蓝牌");
		}
		if(row.plateType==2){
			plateTypes.push("黑牌");
		}
		if(row.plateType==3){
			plateTypes.push("单排黄牌");
		}
		if(row.plateType==4){
			plateTypes.push("双排黄牌'");
		}
		if(row.plateType==5){
			plateTypes.push("警车车牌");
		}
		if(row.plateType==6){
			plateTypes.push("武警车牌");
		}
		if(row.plateType==7){
			plateTypes.push("个性化车牌");
		}
		if(row.plateType==8){
			plateTypes.push("单排军车");
		}
		if(row.plateType==9){
			plateTypes.push("双排军车");
		}
		if(row.plateType==10){
			plateTypes.push("使馆牌");
		}
		if(row.plateType==11){
			plateTypes.push("香港牌");
		}
		if(row.plateType==12){
			plateTypes.push("拖拉机");
		}
		if(row.plateType==13){
			plateTypes.push("澳门牌");
		}
		if(row.plateType==14){
			plateTypes.push("厂内牌");
		}
    	row.plateType=plateTypes.join();
    	
    	var html = util.parser(detail_tmpl_html, row);
    	$win.html(html);
      	
        $win.dialog({
            title: "任务详情",
            width: 750,
            height:655,
            modal: true,
            resizable:true,
            onOpen: function () {
            	//重新解析dom
                $.parser.parse($win);
            	//初始化控件
            	initProcessWidget(row.id);
            	
            	if(showFlage){
            		$("#div_buttons_group").show();
            	}
            	
            	$win.find('a.btn_ok').linkbutton({
                    iconCls: 'icon-ok',
                    onClick: function () {
                    	if(typeof callback == 'function'){
                    		callback();
                    	}
                        $win.dialog('close');
                    }
                });
            	
            	$win.find('a.btn_cancel').linkbutton({
                    iconCls: 'icon-cancel',
                    onClick: function () {
                        $win.dialog('close');
                    }
                });
										                           
            },
            onBeforeClose: function () {
                $(this).dialog('clear');
            }
        });
    };
    
    function initProcessWidget(surveillanceTaskId){
    	 $("#bk_approval_process_datagrid").datagrid({
    	        url: '/surveillance/querySurveillanceApplicationRecord.action',
    	        loadMsg: '数据正在加载,请耐心的等待...',
    	        pagination: true,
    	        pageNumber: 1,
    	        pageSize: 10,
    	        rownumbers: true,
    	        height:325,
    	        fit: false,
    	        singleSelect: true,
    	        columns: [[
    				{
    				    field: 'id', title: '主键id', width: '1%',hidden:true
    				},
    				{
    				    field: 'surveillanceTaskId', title: '关联布控任务id', width: '1%',hidden:true
    				},
    	            {
    	                field: 'peopleName', title: '审核人', width: '10%'
    	            },
    	            {
    	                field: 'recordTime', title: '审核时间', width: '25%',formatter: util.formateTime
    	            },
    	            {
    	                field: 'result', title: '审核结果', width: '16%'
    	            },
    	            {
    	                field: 'cause', title: '原因描述', width:'40%'
    	            },
    	            {
    	                field: 'peopleTel', title: '审核人电话', width: '15%'
    	            },
    	            {
    	                field: 'unitName', title: '审核单位', width: '15%'
    	            }
    	        ]],
    	        onBeforeLoad:function(p){
    	           $.extend(p,{surveillanceTaskId:surveillanceTaskId});
    	        }
    	    });
    	   	 
    }
    
    
});
</script>