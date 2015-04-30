﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="task_datagrid">
	<div class='toolbar'>
		<form action="#">
			<label>任务名称:
			<input id="name" type="text" class="easyui-textbox"></label>
			<label>所属监控点:
			<input id="cameraIds"></label>
            <label>任务状态:
            <input id="status" type="text"></label><br/>
            
			<label>任务类型:
			<input id="type" type="text"></label>
			<label>&emsp;开始时间:
			<input id="startTime" type="text" name="startTime"></label>
			<label>结束时间:
			<input id="endTime" type="text" name="startTime"></label>
			
			<a href="javascript:void(0)" id="query" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</form>
	</div>
</div>
<script>
  (function(){
  	  var datagrid=$("#task_datagrid");
  	  var name = $('#task_datagrid #name');

	  //监控点自动完成多选框
      var $query_camera=$('#task_datagrid').find('#cameraIds'),
      queryUrl='/camera/list.action',
	  cameraIds=$query_camera.combogrid({
		  	delay:500,
		 	panelWidth:400,
		 	idField:'id',
		 	textField:'name',
		    url:queryUrl,
		    multiple:true,  
		    fitColumns: true,  
		    striped: true,  
		    editable:true,  
		    pagination : true,//是否分页  
		    rownumbers:true,//序号  
		    collapsible:false,//是否可折叠的  
		    pageSize: 10,//每页显示的记录条数，默认为10  
		    pageList: [10,20,50],//可以设置每页记录条数的列表  
		    method:'post',  
		    columns:[[
		        {field:'id',title:'id',hidden:true},  
		        {field:'checkbox',checkbox:true},  
		        {field:'name',title:'监控点名称',width:150,sortable:true} 
		    ]], 
		    keyHandler: {
				up: function () {
				
				},
				down: function () {
				
				},
				left: function () {
				 	
				},
				right: function () {
				
				},
				enter: function () {
				
				},
				query: function (q) {
					$query_camera.combogrid("grid").datagrid("reload", { 'name': q });
					$query_camera.combogrid("setValue", q);
				}
		 	}  
	  });
   
  	  var type = $('#task_datagrid #type').combobox({
  	  			editable:false,
  	  			panelHeight:'auto',
			    data:[{
				  value:'',
				  text:'全部'
				},{
				  value:'1',
				  text:'实时任务'
				},{
				  value:'2',
				  text:'离线任务'
				},{
				  value:'3',
				  text:'批量任务'
				}]
		  });
	  var startTime=$('#task_datagrid #startTime').datetimebox({
		  editable:false
	  });
	  var endTime=$('#task_datagrid #endTime').datetimebox({
		  editable:false
	  });
  	  var status = $('#task_datagrid #status').combobox({
			    editable:false,
			    panelHeight:'auto',
			    data:[{
				  value:'',
				  text:'全部'
				},{
				  value:'1',
				  text:'识别中'
				},{
				  value:'2',
				  text:'已完成'
				},{
				  value:'3',
				  text:'已失败'
				}]
		  });
	  var getParamData=function(){
		  return  {
		  		name:name.val(),
				ids:cameraIds.combo('getValues').join(),
				type:type.combo('getValue'),
				startTime:startTime.combo('getValue'),
				endTime:endTime.combo('getValue'),
				status:status.combo('getValue')
			  }
	  }
	  
	  $('#task_datagrid #query').click(function(e){
		  e.preventDefault();
		  datagrid.datagrid("reload");
	  });
	  datagrid.datagrid({
		  url:'/task/query.action',
			loadMsg:'数据载入中',
			pagination:true,
			rownumbers:true,
			fit: true,
			singleSelect:true,
			columns:[[
				{field:'name',title:'任务名',width:300},
				{field:'cameraName',title:'所属监控点',width:150},
				{field:'type',title:'任务类型',formatter:util.formateTaskType,width:150},
				{field:'startTime',title:'开始时间' ,formatter:util.formateTime,width:150},
				{field:'endTime',title:'结束时间' ,formatter:util.formateTime,width:150},
				{field:'status',title:'状态',formatter:util.formateTaskStatus,width:150},
				{field:'stop',title:'操作',formatter:function(v,row,index){ 
                          return row.status==1?'<a href="javascript:void(0)" onclick="stopTask('+row.id+')">停止</a>':'';
	             },width:150}
			]],
			onDblClickRow:function(index,row) {
				  	var title='任务结果-'+row.name;
					if(mainTabs.tabs('exists',title)){
						mainTabs.tabs('select',title)
					}else{
						mainTabs.tabs('add',{
							title:title,
							closable:true,
							href:'result/results.action?taskId='+row.id
						});
					}
		          
			},
			toolbar:$('#task_datagrid .toolbar'),
			onBeforeLoad:function(p){
				$.extend(p,getParamData());
			}
	  });
	  datagrid.datagrid('getPanel').on("keyup",'input:text',function(e){
			if(e.keyCode == 13) {
				datagrid.datagrid('reload');
			}
    });
     
  window.stopTask=function(taskId){
   $.post('task/stop.action',{'taskId':taskId},function(msg) {
   if(null != msg && '' != msg) {
    $.messager.show({
     title:'提示',
     msg:'任务已于'+util.formateTime(msg)+'停止，无需停止！',
     timeout:2000
    });
   }
  });
   datagrid.datagrid("reload");
	   }
  })();
 </script>
