<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class='easyui-layout' fit=true>
	<div region='center' border=false>
		<div id="role_datagrid">
			<div class="toolbar">
				<form method='post' id='query_role_form'>
					<table>
						<tbody>
							<tr>
								<td>
									<label>&nbsp;状态: </label>
									<select name="status" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'">
										<option value="-1" selected>全部</option>
										<option value="1" >启用</option>
										<option value="2" >停用</option>
									</select>
								</td>
								<td>
									<label>&nbsp;角色名称: </label><input id="name" class="easyui-textbox" name='name' data-options="width:100">
								</td>
								<td>
									<label>&nbsp;备注:</label><input id="remark" class="easyui-textbox" name='remark' data-options="width:100">
								</td>
								<td>
									<a id="query_role_submit">查询</a>
									<a id="query_role_reset">重置</a>
								</td>
								<td><div class="datagrid-btn-separator"></div></td>
								<td>
									<a onclick="role.open('新建角色')" class="easyui-linkbutton" iconCls="icon-add" plain=true>新建</a>
								</td>
							</tr>
						</tbody>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	var _role=window.role,
		_datagrid,
		_open,
		_queryForm = $('#query_role_form');
		
	$('#query_role_submit').linkbutton({
		iconCls:'icon-search',
		plain:true,
		onClick:function(){
			_datagrid.datagrid('reload');
		}
	});
	$('#query_role_reset').linkbutton({
		iconCls:'icon-redo' ,
		plain:true,
		onClick:function(){
			_queryForm.form('reset');
		}
	});
	_datagrid = $('#role_datagrid').datagrid({
		url:'role/list.action',
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
		onBeforeLoad:function(p){
			var o,v,arr=_queryForm.serializeArray();
			for(var i=arr.length;i--;){
				o=arr[i];
				v=$.trim(o.value);
				if(v!=='' && v!==undefined){
					p[o.name]=v;
				}
			}
			if(p.status==-1){
				delete p.status;
			}
		},
		idField:'id',
		columns:[[
			{field:'id',hidden:true},
			{field:'name',title:'名称',styler:function(){
					return 'height:27px';
			}},
			{field:'remark',title:'备注',width:300},
			{field:'createName',title:'创建人'},
			{field:'status',title:'状态',formatter: function(value){
                if (value==1){
                    return "启用";
                } else {
                    return "停用";
                }
            }},
			{field:'createTime',title:'创建时间',formatter: util.formateTime, width: 150},
			{field:'do',title:'操作',width:250,formatter:function(value,row,index){
					var s='';
					s+='<a onclick="role.open(\'角色操作权限\','+index+')" data-options="iconCls:\'icon-jurisdiction\'" style="width:100px;height:24px;">操作权限</a>'
					s+='<a onclick="role.open(\'修改角色\','+index+')" data-options="iconCls:\'icon-edit\'" style="width:70px;height:24px;">修改</a>'
					s+='<a onclick="role.deleteRole('+index+')" data-options="iconCls:\'icon-cancel\'" style="width:70px;height:24px;">删除</a>'
					return s;
			}}
		]],
		toolbar:'#role_datagrid .toolbar'
	});
	_role.form.form({
		success: function(data) {
			if(data=="success") {
				$.messager.show({
					title:'消息',
					msg:'提交成功！',
					timeout:2000
				});
    		 	_role.dialog.dialog('close');
	    		_datagrid.datagrid("reload");
			}else{
				$.messager.alert({title:'错误',msg:'提交失败'});
				console.info(data);
               }
    	}
	});
	_open = _role.open = function(title, index) {
		if(index==null) {
			_role.openDialog(title);
		} else if(title=="修改角色") {
			_role.openDialog(title,_datagrid.datagrid('getRows')[index]);
		} else {
			_role.openPrvDialog(title,_datagrid.datagrid('getRows')[index]);
		}
	}
	_role.deleteRole = function(index) {
		var row=getRow(_datagrid,index)
		$.messager.confirm('删除角色','您确定要删除"'+row.name+'"?',function(flag){
			if(!flag)return;
			$.ajax({
				url:'role/del.action',
				type:'post',
				datatype:'json',
				data:row,
				success:function(d){
					if(d=='success'){
						$.messager.show({
							title:'消息',
							msg:'删除成功！',
							timeout:2000
						});
						_datagrid.datagrid("reload");
					} else {
						$.messager.alert('错误','删除失败');
						console.info(d);
					}
				}
			});
		})
	}
    var getRow=function(grid,index){
        return grid.datagrid('selectRow',index).datagrid('getSelected');
    }
})
</script>
