﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="mytask_scar_tabs" class="easyui-tabs" data-options="fit:true,border:false,tabWidth:100" style="margin-top:-15px">
<div title="任务列表">
	<div id="searchCarByImage_condition_tollbar" style="background-color:#F4F4F4;padding:5px 35px 5px 10px;border-bottom:1px solid #95B8E7;width:100%">
	<div>
	&nbsp;&nbsp;&nbsp;车牌号码：<input id="sercar_task_licens_id" class="easyui-textbox" style="width:180px;">
	&nbsp;&nbsp;&nbsp;车牌种类：<select id="sercar_task_palate_type_id" class="easyui-combobox" style="width:180px;"></select>
	&nbsp;&nbsp;&nbsp;车辆类型：<select id="sercar_task_vehicle_kind_id" class="easyui-combobox" style="width:180px;"></select>
	&nbsp;&nbsp;&nbsp;车身颜色：<select id="sercar_task_car_color_id" class="easyui-combobox" style="width:180px;"></select>
	</div>
	<div style="margin-top:10px">
	&nbsp;&nbsp;&nbsp;品牌：<input id="sercar_task_brand_id" style="width:180px;">
	&nbsp;&nbsp;&nbsp;车系：<input id="sercar_task_series_id" style="width:180px;">
	&nbsp;&nbsp;&nbsp;款型：<input id="sercar_task_style_id" style="width:180px;">
	&nbsp;&nbsp;&nbsp;监控点：<input id="sercar_task_camer_id" type="text"  style="width:180px;"><div href="javascript:void(0)" id="sercar_task_pub_map" class="easyui-linkbutton" data-options="iconCls:'icon-search'">地图</div>
	&nbsp;&nbsp;&nbsp;<a id="sercar_task_serarch_btn_id" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
	&nbsp;&nbsp;&nbsp;<a id="sercar_task_clear_btn_id" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px">重置</a>
	</div>	
	</div>
	<div id="sercar_task_dg"></div>
</div>
</div>
<script>
  (function(){
	  
	  var tab = util.getSelectedTab();
	  
	  $("#sercar_task_licens_id").textbox({});
	  
	  $('#sercar_task_palate_type_id').combobox({
			valueFiled:'value',
			textField:'text',
			panelHeight:350, 
			value:'',
			data:[
			{
              value: '0',
              text: '未知车牌'
          },{
              value: '1',
              text: '蓝牌'
          },{
              value: '2',
              text: '黑牌'
          },{
              value: '3',
              text: '单排黄牌'
          },{
              value: '4',
              text: '双排黄牌（大车尾牌，农用车）'
          },{
              value: '5',
              text: '警车车牌'
          },{
              value: '6',
              text: '武警车牌'
          },{
              value: '7',
              text: '个性化车牌'
          },{
              value: '8',
              text: '单排军车'
          },{
              value: '9',
              text: '双排军车'
          },{
              value: '10',
              text: '使馆牌'
          },{
              value: '11',
              text: '香港牌'
          },{
              value: '12',
              text: '拖拉机牌'
          },{
              value: '13',
              text: '澳门牌'
          },{
              value: '14',
              text: '厂内牌'
          }] 		
	});
	
	function changePlageValue(tx){
		if(tx =='未知车牌'){
			return 0;
		}else if(tx=='蓝牌'){
			return 1;
		}else if(tx=='黑牌'){
			return 2;
		}
		else if(tx=='单排黄牌'){
			return 3;		
		}
		else if(tx=='双排黄牌（大车尾牌，农用车）'){
			return 4;
		}
		else if(tx=='警车车牌'){
			return 5;
		}
		else if(tx=='武警车牌'){
			return 6;
		}
		else if(tx=='个性化车牌'){
			return 7;
		}
		else if(tx=='单排军车'){
			return 8;
		}
		else if(tx=='双排军车'){
			return 9;
		}
		else if(tx=='使馆牌'){
			return 10;
		}
		else if(tx=='香港牌'){
			return 11;
		}
		else if(tx=='拖拉机牌'){
			return 12;
		}
		else if(tx=='澳门牌'){
			return 13;
		}else{
			return 14;
		}
	}
	
	$('#sercar_task_vehicle_kind_id').combobox({
		url:"js/json/vehicleKind.json",
		valueField:"value",
		textField:"text"
	});
	
	$('#sercar_task_car_color_id').combobox({
		valueFiled:'value',
		textField:'text',
		panelHeight:250,
		value:'',
      data:[{
              value: '黑',
              text: '黑色'
          },{
              value: '白',
              text: '白色'
          },{
              value: '蓝',
              text: '蓝色'
          },{
              value: '棕',
              text: '棕色'
          },{
              value: '红',
              text: '红色'
          },{
              value: '灰',
              text: '灰色'
          },{
              value: '黄',
              text: '黄色'
          },{
              value: '粉',
              text: '粉色'
          },{
              value: '紫',
              text: '紫色'
          },{
              value: '绿',
              text: '绿色'
          }]               
	});
	
	
  // 一层Combo
  var vehicleBrand = $("#sercar_task_brand_id").combobox({
      //editable:false, //不可编辑状态  品牌太多 支持匹配
      url: '/brandmodel/listbrand.action',
      dataType: 'json',
      type:'post',
      onChange:function(record){  //onSelect 用户点击时触发的事件  在此的意义在于，用户点击一级后自动二级combobox
          $.ajax({
              type:'post',
              url: '/brandmodel/listsubbrand.action',
              data:{brandName : $("#sercar_task_brand_id").combobox("getValue")},
              success:function(d){
                  var data=[];
                  $.each(d,function(i,o) {
                      var obj={value: o.carSeries,text: o.carSeries};
                      data.push(obj);
                  })
                  vehicleSeries.combobox({
                      onLoadSuccess:function(){  //清空三级下拉框 就是成功加载完触发的事件 当一级combobox改变时，二级和三级就需要清空
                          vehicleStyle.combobox("clear");
                          vehicleSeries.combobox("clear");
                      },
                      data:data,
                      onChange:function(record) {

                          $.ajax({
                              type:'post',
                              url: '/brandmodel/listcar.action',
                              data:{brandName : $("#sercar_task_brand_id").combobox("getValue"),carSeries : $("#sercar_task_series_id").combobox("getValue")},
                              success:function(d){
                                  var data=[];
                                  $.each(d,function(i,o) {
                                      var obj={value: o.modelsName,text: o.modelsName};
                                      data.push(obj);
                                  })
                                  vehicleStyle.combobox({
                                      data:data,
                                      onChange:function(record) {

                                      }
                                  });
                              }
                          });

                      }
                  });
              }
          });

      },
      valueField: 'brandName',
      textField: 'brandName',
      value:''
  });
  
  //  二层Combo
  var vehicleSeries = $("#sercar_task_series_id").combobox({
      //editable:false,
      value:''
  });

  //三层Combo
  var vehicleStyle=$("#sercar_task_style_id").combobox({
      //editable:false,
      value:''
  });
      //存放被选择的监控点
      var camIdArr = [],camNameArr = [],$query_camera = tab.find("#sercar_task_camer_id");
      //树形下拉框 监控点
      var cameraIds = $query_camera.combotree({
          url: '/camera/camLstTree.action',
          animate:true,
          multiple:true,
          onCheck:function(node,checked) {
              util.checkCam(cameraIds,node,camIdArr,camNameArr,checked);
          },
          onClick:function(node) {
              util.checkCam(cameraIds,node,camIdArr,camNameArr,node.checked);
          },
          onChange:function(nVal,oVal) {
              util.checkCamWithMap(cameraIds);
          }
      });
  			
             //获取搜索条件参数
            function getParamater(){
             	var data = {};
              	//车牌号码
             	data.license = $.trim($("#sercar_task_licens_id").textbox("getValue"));
             	//车牌种类
         		data.plateType=$("#sercar_task_palate_type_id").combobox("getValue");
             	//车辆类型
         		data.vehicleKind=$("#sercar_task_vehicle_kind_id").combobox("getValue");
             	//车辆颜色
         		data.vehicleColor=$("#sercar_task_car_color_id").combobox("getValue");
             	//品牌
         		data.vehicleBrand=$("#sercar_task_brand_id").combobox("getValue");
             	//车系
         		data.vehicleSeries=$("#sercar_task_series_id").combobox("getValue");
             	//款型
         		data.vehicleStyle=$("#sercar_task_style_id").combobox("getValue");
             	//监控点
         		data.cameraId=$("#sercar_task_camer_id").combobox("getValues").join();
             	
             	return data;
             }
  			
           //查询事件
          	$("#sercar_task_serarch_btn_id").click(function (){
         		$('#sercar_task_dg').datagrid("reload");		
         	}) 
         	
         	$("#sercar_task_clear_btn_id").click(function (){
         		//车牌号码
             	$("#sercar_task_licens_id").textbox("clear");
             	//车牌种类
         		$("#sercar_task_palate_type_id").combobox("clear");
             	//车辆类型
         		$("#sercar_task_vehicle_kind_id").combobox("clear");
             	//车辆颜色
         		$("#sercar_task_car_color_id").combobox("clear");
             	//品牌
         		$("#sercar_task_brand_id").combobox("clear");
             	//车系
         		$("#sercar_task_series_id").combobox("clear");
             	//款型
         		$("#sercar_task_style_id").combobox("clear");
             	//监控点
         		$("#sercar_task_camer_id").combotree("clear");
         	});

         	 $('#sercar_task_dg').datagrid({
       		  url:'/serachCarByImage/getVfmTaskList.action',
       		  checkOnSelect: false,
       		  selectOnCheck: false,
       		  multiple:true,
       		  fitColumns: false,
       		  striped: true,
       		  editable:true,
       		  pagination : true,//是否分页
       		  rownumbers:true,//序号
       		  collapsible:true,//是否可折叠的
       		  pageSize: 20,//每页显示的记录条数，默认为10
       		  pageList: [10,20,50],//可以设置每页记录条数的列表
       		  singleSelect:true,
       		  fit: true,
       		  method:'post',
       		  columns:[[
       			  {field:'taskID',title:'以图搜车任务ID',width:'0%',hidden:true},
       			  {field:'vlprTaskID',title:'识别任务ID',width:'0%',hidden:true},
       			  {field:'serialNumber',title:'识别结果ID',width:'0%',hidden:true},
       			  {field:'license',title:'车牌名称',width:'8%'},
       			  {field:'vehicleKind',title:'车辆类型',width:'7%'},
       			  {field:'vehicleColor',title:'车身颜色',width:'5%'},
       			  {
       				  field: 'plateType', title: '车牌类型', width: '5%', formatter: function (value, row, index) {
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
       			  {field:'vehicleBrand',title:'品牌',width:'8%'},
       			  {field:'vehicleSeries',title:'车系',width:'8%'},
       			  {field:'vehicleStyle',title:'车款',width:'8%'},
       			  {field:'passStartTime',title:'过车开始时间',formatter:util.formateTime,width:'12%'},
       			  {field:'passEndTime',title:'过车结束时间',formatter:util.formateTime,width:'12%'},
       			  {field:'insertTime',title:'任务开始时间',formatter:util.formateTime,width:'12%'},
       			  {field:'progress',title:'任务进度',width:'6%',formatter:function(value, row, index){ return value+"%";}},
       			  {field:'noFild',title:'操作',width:'5%',formatter:function(value,row,index){
       				var stopButtonId = "scar_stop_btn_" + row.taskID;
     				var html = '';
     				if(row.progress < 100 && row.flag == 1){
     					html = '<a href="javascript:void(0)" id="' + stopButtonId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-cancel\'" >停止</a>';
     					tab.off('click', '#' + stopButtonId).on('click', '#' + stopButtonId, function (){
       						stopVlprVfmTask(row.taskID);
         				});
     				}
     				
     				return html;       				  
     			  }},
 	             {field: 'xxxx', title: '下  载', formatter: function(v,row){
                     var html='<a id="'+"download"+row.taskID+'" class="showResult" href="javascript:void(0);">'+'下载'+'</a>';
                     tab.off('click','#download'+row.taskID).on('click','#download'+row.taskID,function(){
                        //判断条目数
                        $.ajax({
                        	url:'/serachCarByImage/downloadItemCount.action',
                        	data:{vfmTaskId:row.taskID},
                        	type:'post',
                        	success:function (item){
                        		if(item>2000){
                        			$.messager.show({
                        				title:"提示消息",
                        				msg:"数据条目数大于2000，无法下载。",
                        				timeout:3000,
                        				showType:'slide'
                        			});
                        		}else{
                        			var form = $("<form>");           
                                    form.attr('style', 'display:none');     
                                    form.attr('target', '');    
                                    form.attr('method', 'post');    
                                    form.attr('action', '/serachCarByImage/download.action');  
                                                                       
                                    var input = $('<input>');                                  	
                                    input.attr('type', 'hidden');     
                                    input.attr('name', 'vfmTaskId');    
                                    input.attr('value', row.taskID);
                                    form.append(input);                                
                                                             
                                   $('body').append(form);                      
                                    form.submit();    
                                    form.remove();
                        		}
                        	}
                        });
                     })
                     return html;
                 },width:'3%'}
       		  ]],
       		  onBeforeLoad:function(p){
       			  var para = getParamater();
       			  if(!para.plateType){
              		  	para.plateType = -1;
              		  }
       	          $.extend(p,para);
       	      },
	       	  onDblClickRow: function (index, row) {
	       		showScarDeatalInfo(row);
	          },
       	      toolbar:$("#searchCarByImage_condition_tollbar")
       	  });
      tab.on('click', '#sercar_task_pub_map', function (e) {
          e.preventDefault();
          pubMapWindow.open(cameraIds,camIdArr,camNameArr);
      });
	function showScarDeatalInfo(row){
		if($("#mytask_scar_tabs").tabs("exists","任务详情")){
			$("#mytask_scar_tabs").tabs("close","任务详情");
		}
		  
      $("#mytask_scar_tabs").tabs("add",{
    	  title:"任务详情",
    	  closable:true,
    	  href:"/serachCarByImage/taskSerachIndex.action",
		  onLoad:function (){
		  	scar_loadResultDatas(10,row.taskID,row.vlprTaskID,row.serialNumber);
		  }
      });
	}
	
	//停止以图搜车任务
	function stopVlprVfmTask(taskId){
		 $.post('/serachCarByImage/stopVlprVfmTask.action', {taskId: taskId}, function (msg) {
             if (msg == 1) {
                 $.messager.show({
                     title: '提示',
                     msg: '任务停止成功！',
                     timeout: 2000
                 })
             }
         })
        $('#sercar_task_dg').datagrid("reload");
	}
	  
  })();
 </script>
