<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class='easyui-layout' fit=true>
	<div region='west' style='width:200px;' split=true>
		<ul id="dept_tree"></ul>
	</div>
	<div region='center' border=false>
		<div id="dept_datagrid"></div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	var _dept=window.dept,
		_tree,
		_datagrid;
		
   	_tree = $("#dept_tree").tree({
        url:'dept/deptTreeJson.action',
        loadFilter: function(data){
            return util.getNodes(data);
        },
	    onClick:function(node) {
	    	var queryParams = {
	    		id:node.id,
	    		name:node.text
			};
	    	_datagrid.datagrid("load", queryParams);
	    }
	});
	_datagrid = $('#dept_datagrid').datagrid({
		url:'dept/findDeptList.action',
		loadMsg:'数据载入中',
		pagination:true,
		rownumbers:true,
		fit: true,
		singleSelect:true,	
		onLoadSuccess:function(){
			var self=$(this).parent();
			setTimeout(function(){
				var a=self.find('a').linkbutton({plain:true})
			})
		},
		idField:'id',
		columns:[[
			{field:'name',title:'部门名称',styler:function(){
				return 'height:27px';
			}},
			{field:'dsc',title:'部门描述'},
			{field:'parentName',title:'上级部门',formatter:function(v,row){
                     return row.parentName==row.name?'':v;
            }},
			{field:'isEnable',title:'是否启用',width:60,formatter:function(v,row,i){
							switch (v) {
							case 0:
								return "<span class='red'>停用</span>";
							default:
								return "启用";		
							}
						}
			},
			{field:'operate',title:'操作',width:100,formatter:function(v,row){
							var html='<a onclick="dept.openDialog(\'修改部门\','+row.id+')" data-options="iconCls:\'icon-edit\'" style="width:38px;height:24px;" title="修改"></a>'
						 	html+='<a onclick="dept.isEnableDept('+row.id+','+row.isEnable+')" data-options="iconCls:\'icon-tip\'" style="width:38px;height:24px;" title="启用/禁用"></a>'
							return html;
						}
			}
		]],
		toolbar: [{
			iconCls: 'icon-add',
			text: "添加",
			handler: function() {
				_dept.openDialog('添加部门');
			}
		}]
	});
	_dept.form.form({
		success: function(data) {
			if(data=="success") {
				$.messager.show({
					title:'消息',
					msg:'提交成功！',
					timeout:2000
				});
    		 	_dept.dialog.dialog('close');
	    		_datagrid.datagrid("reload");
	    		_tree.tree("reload");
			}else{
				$.messager.alert("提示", "添加失败！请检查部门是否重名！");
				console.info(data);
               }
    	}
	});
	
	_dept.isEnableDept = function(rowId,isEnable) {
		$.messager.confirm('确认', '确定要启用/禁用吗？', function(y) {
			if (y) {
				$.ajax({
					type: 'post',
					url: 'dept/enableOrDisable.action',
                       data:{id:rowId,enableOrDisable:isEnable^1},
					dataType: 'json',
					error: function(data) {
						_datagrid.datagrid("reload");
					}
				});
			}
		});
	}
})
</script>
