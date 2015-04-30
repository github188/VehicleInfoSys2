<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-layout" fit="true" style="width: 100%; height: 100%;">
	<div id="serverstatus" data-options="split:true,region:'center',title:'服务器状态'" style="width:75%">
		<div id="server_list"></div>
		<div id="server_dialog"></div>
	</div>
	<div id="onlineuser" region="west" title="当前在线用户" style="width:25%" split="true">
		<div id="onlineuser_list"></div>
	</div>
</div>
<script type="text/javascript">
(function($,w) {
	$('#onlineuser_list').datagrid( {
		url : '/serverstatus/list.action',
		loadMsg : '数据载入中...',
		pagination : true,
		rownumbers : true,
		fit : true,
		striped : true,
		pageSize : 20,
		columns : [ [ {
			field : 'loginName',
			title : '用户名'
		} ] ]
	});

	//移除服务器
	var removeServer=function(id){
		$.messager.confirm('提示','确认移除服务器?',function(r){
			if(r){
				var msg='移除失败!';
				$.ajax({type:'post',url:'/server/remove.action',data:{id:id},success:function(d){
						if(d=='success'){
							msg='移除成功!';
							$('#server_list').datagrid('reload');
							$('#server_dialog').dialog('close');
						}
						$.messager.show({ title:'提示', msg:msg });
						},error:function(){
						$.messager.show({ title:'提示', msg:msg });
					}
				});
			}
		});
	}
	w.removeServer=removeServer;
	var aa=$('#server_list').datagrid( {
		url : '/server/list.action',
		loadMsg : '',
		pagination : true,
		rownumbers : true,
		fit : true,
		striped : true,
		pageSize : 20,
		sortName:'ip',
		remoteSort:true,
		columns : [ [ {
			field : 'ip',
			title : '服务器ip',
			align:'right',
			sortable:true
		}, 
		{
			field : 'cpu',
			sortable:true,
			title : 'cpu占用率(%)',
			formatter: function(value,row,index){
				return row.cpu*100|0;
			},
			align:'right'
		},
		{
			field : 'ram',
			sortable:true,
			title : '内存占用率(%)',
			align:'right'
		},
		{
			field : 'totalSpace',
			sortable:true,
			title : '磁盘总空间(GB)',
			formatter: function(value,row,index){
				var totalSpace = row.totalSpace/1024;
				return totalSpace.toFixed(2);
			},
			align:'right'
		},
		{
			field : 'usedSpace',
			sortable:true,
			title : '磁盘占用空间(%)',
			formatter: function(value,row,index){
				return row.usedSpace*100|0;
			},
			align:'right'
		},
		{
			field : 'freeSpace',
			sortable:true,
			title : '磁盘剩余空间',
			formatter: function(value,row,index){
				var freeSpace = row.freeSpace/1024;
				 	return freeSpace < 1 ? row.freeSpace + " MB" : freeSpace.toFixed(2) + " GB";
			},
			align:'right'
		},
		{
			field : 'updateTime',
			title : '状态',
			align:'right',
			sortable:true,
			order:'asc',
			formatter: function(value,row,index){
				return (row.queryTime - row.updateTime) > 10*1000 ? '无响应':'正常';
			}
		},
		{
			field:'operation',
			title:'操作',
			align:'right',
			formatter: function(value,row,index){
				return (row.queryTime - row.updateTime) > 10*1000 ? '<a class="removeServer" onclick="removeServer('+ row.id +')" href="javascript:void(0)">移除</a>':'';
			}
		}
		]]
	});

       var refresh=function(){ 
	     setTimeout(function(){
                var $server_list=$('#server_list');
               if($server_list.length){
               	 $server_list.datagrid('reload');
               	 refresh();
               }
         },3000) 
       }
       refresh();
})(jQuery,window);
</script>
