<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class='easyui-layout' fit=true>
	<div data-options="region:'west',title:'批量任务',split:true" style="width:400px;">
           <div class='datagrid_batTasks'>
           
           </div>
    </div>
    <div data-options="region:'center',split:true">
    
    
    
    </div>
</div>
<div class='window_batTasks'/>
<script>
	;(function(){
		//为当前打开的tab做一个局部引用,此tab内部的元素均用tab.find()来查找
		var tab = util.getSelectedTab(),
        $win=tab.find('.window_batTasks'),
		cameraId=${camera.id},
		cameraName = '${camera.name}',
        TASK_TYPE_BAT=3,
        $batTasks=tab.find('.datagrid_batTasks')
        $batTasks.datagrid({
			url:'/task/list.action',
			queryParams:{cameraId:cameraId,type:TASK_TYPE_BAT},
			loadMsg:'数据载入中',
			pagination:true,
			rownumbers:true,
			fit: true,
			fitColumns:true,
			singleSelect:true,
			selectOnCheck:false,
			checkOnSelect:false,			
			columns:[[
				{field:'name',title:'任务'}
			]],
			onLoadSuccess:function(){
				var self=$(this).parent();
				setTimeout(function(){
					var a=self.find('a').linkbutton({plain:true})
				})
			},
			onClickRow:function(index,row) {
				//
			},
			toolbar:[{
                   text:'添加',iconCls:'icon-add',
                   handler:function(){
				    var upload=window.upload
				    upload.form.find('#cameraId').val(cameraId)
                    upload.grid=$batTasks
				    upload.open({
                        url:'/task/bat.action',
				    	selectVideo:false,
                        submitText:'上传并提交任务', 
                        title:'批量添加，请选择 <span style="color:red">'+cameraName+'</span> 监控点下的文件'
                     })
                   }
			    }]
		})
  
		
	})()
</script>
