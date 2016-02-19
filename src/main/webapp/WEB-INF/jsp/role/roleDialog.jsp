<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="role_dialog">
	<div style="padding: 10px;text-align: right">
		<form id="role_form" method="post">
			<table>
				<tr>
					<td><label><span class="red">*</span>角色名称:</label></td>
					<td>
			            <input class="easyui-textbox" name="name" id="role_form_name"
			            	data-options="required:true,validType:['letterAndNumberAndChinese','length[4,12]']" />
					</td>
				</tr>
				<tr>
					<td><label>备&emsp;&emsp;注:</label></td>
					<td>
			            <input class="easyui-textbox" name="remark"
			            	data-options="validType:['letterAndNumberAndChinese','length[0,20]']" />
					</td>
				</tr>
				<tr>
					<td><label>状&emsp;&emsp;态:</label></td>
					<td>
						<input name="status" class="easyui-combobox" 
							data-options="panelHeight:'auto',value:'1',
							data:[{value:'1',text:'启用'},{value:'2',text:'停用'}]" />
					</td>
				</tr>
			</table>
		</form>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	var _role={},
		_dialog,
		_form;
		window.role = _role;
	
	_dialog = _role.dialog = $('#role_dialog').dialog({
		closed:true,
		modal:true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				_form.form('submit');
			}
		},{
			text:'重置',
			iconCls:'icon-redo',
			handler:function(){
				_form.form('reset');
			}
		}]
	});
	_form = _role.form =$('#role_form').form();
	
	_role.openDialog = function(title, row) {
		var url;
		_form.form('reset');
		if(!row) {
			url = "role/add.action";
		} else {
			url = "role/update.action?id="+row.id;
			_form.form('load',row);
		} 
		_form.form({url: url});
		_dialog.dialog('setTitle',title);
		_dialog.dialog('open');
	}
})
</script>
