<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="tmpl" id="browse_window_tmpl">
<li style="list-style-type:none;">
	<div style="margin: 0 0 30px 30px;text-align: center;height: 250px;width: 220px;float: left;border: 2px solid #ddd;
				padding: 4px;border-radius: 10px;
				word-wrap:break-word;word-break:break-all;">
	<img style="height: 70%;width: 100%;" onerror="util.noFind()" src="${PictureServerHost}/{#bigUrl}" />
	<br/>
	<br/>
	<span>{#name}</span>
	</div>
</li>
</script>
<div class="easyui-layout" fit=true style="min-width: 800px;">
	<div title="视频图片列表" onselectstart="return(event.srcElement.type=='text')" data-options="region:'west',width:380,split:true">
		<div id="resource_grid" border=false>
		</div>
	</div>
	<div region="center">
		<div id="resource_task_tabs" class="easyui-tabs result_details_tabs" fit=true border=false>
			<div title="任务列表" onselectstart="return(event.srcElement.type=='text')">
				<div id="resource_task_grid" border=false>
					<div class='toolbar'>
						<a id="listRemove" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain=true>删除</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class='resource_grid_toolbar'>
    <table>
     <tr>
      <td>
       <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain=true>添加</a>
      </td>
      <td><div class="datagrid-btn-separator"></div></td>
      <td>
       <a id="remove" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain=true>删除</a>
      </td>
      <td><div class="datagrid-btn-separator"></div></td>
      <td>
       <a id="start_task" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain=true>批量任务</a>
      </td>
      <td><div class="datagrid-btn-separator"></div></td>
      <td>
       <div class="resourceType easyui-combobox" data-options="
        width:'80px',
        panelHeight:'100px',
        onSelect:function(){
           var tab = window.mainTabs.tabs('getSelected');
           $('#resource_grid',tab).datagrid('load');
        },
        value:-1,
  data: [{
            value:-1,
   text: '全部类型'
  },{
            value:1,
   text: '图片'
  },{
            value:2,
   text: '视频'
  },{
            value:3,
   text: '目录'
  }]">视频类型</div>
      </td>
     </tr>
    </table>
</div>
<div id="browse_window" style="position: relative; width: 100%; height: 100%;">
</div>
<script type="text/javascript">
$(function(){
	//获取最外层tab对象，用以定位具体tab页
	var tab = window.mainTabs.tabs('getSelected');
	
	var cameraId = '${camera.id}';
	var cameraName = '${camera.name}';
	
	var upload = window.upload;    //获取上传页面的窗口对象
	var uploadDialog = upload.dialog; //获取上传页面的dialog对象
	var form = upload.form;   //获取上传页面的表单
	var resource_grid = $('#resource_grid',tab[0]);  //离线资源列表
	var resource_task_grid = $('#resource_task_grid',tab[0]); //离线资源任务列表
	
	//绑定添加资源按钮的事件
    tab.on('click','#add',function(){
		$('#cameraId',form).attr('value',cameraId);
		upload.grid = resource_grid;
		upload.open({
            url:'resource/addFile.action',
			title:'上传资源，请选择 <span style="color:red">'+cameraName+'</span> 监控点下的文件'
	    })
	})
	//绑定批量开始识别任务的事件
	.on('click','#start_task',function(){
		$('#cameraId',form).attr('value',cameraId);
		upload.grid = resource_grid;
        upload.open({
            url:'/task/bat.action',
            selectVideo:false,
            createMenu:true,//后台是否要建一个目录           
            title:'批量添加，请选择 <span style="color:red">'+cameraName+'</span> 监控点下的文件'
         })
		  
  /*
		var selections = resource_grid.datagrid("getChecked");
		if(selections.length<=0) {
			$.messager.alert("提示!","还未选择任何记录!");
			return;
		}
		$.messager.confirm('提示!','是否确认批量启动识别任务？',function(r){
			if (r){
				var ids = selections.map(function(e){
					return e.id+''
				});
				$.ajax({
					url:'task/startOffLineTask.action',
					type:"post",
					data:{
						"id":ids.toString()
					},
					success:function(data) {
						if(data == "success") {
							$.messager.show({
								title:'消息',
								msg:'任务提交成功！',
								timeout:2000
							});
							resource_task_grid.datagrid('load');
						} else {
							$.messager.show({
								title:'任务提交失败！',
								msg:data,
								timeout:2000
							});
						}
					}
				});
			}
		});
	*/})
	//绑定表单的删除事件
    .on('click','#remove',function(){
    	var grid=resource_grid;
		var selections = grid.datagrid("getChecked");
		if(selections.length<=0) {
			$.messager.alert("提示!","请选择要删除的记录!");
			return;
		}
		$.messager.confirm('提示!','是否确认删除？',function(r){
			if (r){
				$.messager.defaults.ok = '是';
				$.messager.defaults.cancel = '否';
				$.messager.confirm('选择','是否删除该资源下的任务和结果？',function(r) {
					var flag = false;
					if(r) {
						flag = true;
					}
					var ids = selections.map(function(e){
						return e.id+''
					});
					$.ajax({
						url:'resource/remove.action',
						type:"post",
						data:{
							ids:ids.toString(),
							isAllDelete:flag
						},
						success:function(data) {
							if(data == "success") {
								$.messager.show({
									title:'消息',
									msg:'删除成功！',
									timeout:2000
								});
								resource_grid.datagrid("load");
							} else {
								$.messager.show({
									title:'删除失败！',
									msg:data,
									timeout:2000
								});
							}
						}
					});
					$.messager.defaults.ok = '确定';
					$.messager.defaults.cancel = '取消';
				});
			}
		});
	});
	util.createRemoveEvent('#listRemove', resource_task_grid, 'task/delete.action');
	
	//该变量用于判断离线视频源标签页中的列表是否已经初始化
	var isResourceInit = false;
	$('#resource_tabs',tab[0]).tabs({
		onSelect:function(title,i) { //在标签页选中时初始化而不是在页面加载时全部一起初始化，这样在加载页面时不会卡很久
			//判断标签页以及是否已经初始化，避免重复初始化
			if(i == 1 && !isResourceInit) {
				isResourceInit = true;
				//初始化离线资源列表
				resource_grid.datagrid({
					url:'resource/list.action',
					queryParams:{'cameraId':cameraId},
					loadMsg:'数据载入中',
					pagination:true,
					rownumbers:true,
					fit: true,
					fitColumns:true,
					singleSelect:true,
					selectOnCheck:false,
					checkOnSelect:false,
					onBeforeLoad:function(p) {
                        var tab = window.mainTabs.tabs('getSelected'),
                        type=tab.find('.resourceType').combo('getValue')
                        type>0&&(p.type=type)
					},
					frozenColumns:[[
						{field:'ck',checkbox:true},
						{field:'operation',title:'操作',width:60,formatter:function(v,row,i){
							var html = '<a href="javascript:void(0)" onclick="drawLine('+row.id+',\''+row.bigUrl+'\',2,\''+row.followarea+'\')" data-options="iconCls:\'icon-tip\'" title="开始识别">识别</a>'
if(row.type==3){
 //如果是目录则可以看这个目录中的文件
html += '<br><a href="javascript:void(0)" onclick="viewMenu('+row.id+',\''+row.name+'\')" data-options="iconCls:\'icon-search\'" title="浏览目录中的文件">浏览</a>'
}
							return html;
						}}
					]],
					columns:[[
						{field:'thumbnail',title:'缩略图',formatter:function(v,i,r) {
							return '<img height="50px" width="50px" src="'+v+'" />';
						}},
						{field:'type',title:'类型',formatter:util.formateResourceType},
						{field:'name',title:'名称'}
					]],
					onLoadSuccess:function(){
						var self=$(this).parent();
						setTimeout(function(){
							var a=self.find('a').linkbutton({plain:true})
						})
					},
					onClickRow:function(index,row) {
						resource_task_grid.datagrid('load','task/list.action?type=2&dataSourceId='+row.id);
					},
					toolbar:$('.resource_grid_toolbar',tab[0]).get(0)
				});
				//初始化离线任务列表
				resource_task_grid.datagrid({
					loadMsg:'数据载入中',
					pagination:true,
					rownumbers:true,
					fit: true,
					singleSelect:false,
					columns:[[
						{field:'ck',checkbox:true},
						{field:'name',title:'任务名称'},
						{field:'startTime',title:'开始时间',formatter:util.formateTime},
						{field:'endTime',title:'结束时间',formatter:util.formateTime},
						{field:'status',title:'状态',formatter:util.formateTaskStatus}
					]],
					onDblClickRow:function(index,row) {
						$('#resource_task_tabs',tab[0]).tabs('add',{
							title:'任务结果-'+row.name,
							closable:true,
							href:'result/results.action?taskId='+row.id
						});
					},
					toolbar:$('#resource_task_grid .toolbar',tab[0]).get(0)
				});
			}
		}
	});

	var browse_window_tmpl=$('#browse_window_tmpl').html();

	//浏览目录中的文件
	window.viewMenu=function(id,name){
		var parentId=id;
		$.post('resource/browseList.action',{'parentId':parentId,'page':1,'rows':10},function(data){
			var pager = data;
			var html = '';
			$.each(pager.rows,function(i,o) {
				o[i]
				html += util.parser(browse_window_tmpl,o);
			});
			$('#browse_window').window({
					title:name+'中的文件',
		      	    width:960,
		      	    height:640,
		      	    content:'<div id="browse_pp" class="easyui-pagination" data-options="total:'+pager.total+',pageSize:10,onSelectPage:function(page,rows){clearContent();loadChildList('+parentId+',page,rows);}" style="background:#efefef;border:1px solid #ccc;"></div><div id="content">'+html+'</div>'
				})
			})
		}
	window.loadChildList = function(id, page, rows) {
		$.post('resource/browseList.action',{'parentId':id,'page':page,'rows':rows},function(data){
			var pager = data;
			$('#browse_pp').pagination('refresh',{
				total: pager.total,
				pageNumber:page
			});
			var html = '';
			$.each(pager.rows,function(i,o) {
				o[i]
				html += util.parser(browse_window_tmpl,o);
			});
			$('#content').html(html)
		});
	}
	
	window.clearContent = function() {
		$('#content').html('')
	}
	
});
</script>