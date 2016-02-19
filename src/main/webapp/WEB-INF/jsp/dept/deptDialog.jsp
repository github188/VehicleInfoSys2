<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="dept_dialog">
	<div style="padding: 10px;text-align: right">
		<form id="dept_form" method="post">
			<table>
				<tr>
					<td><label><span class="red">*</span>部门名称:</label></td>
					<td>
			            <input class="easyui-textbox" name="name"
			            	data-options="required:true,validType:['letterAndNumberAndChinese','length[2,15]']" />
					</td>
				</tr>
				<tr>
					<td><label>部门描述:</label></td>
					<td>
						<input class="easyui-textbox" name="dsc" 
							data-options="validType:['letterAndNumberAndChinese','length[0,25]']" />
					</td>
				</tr>
				<tr>
					<td><label>上级部门:</label></td>
					<td>
						<input id="dept_form_parentId" name="parentId">
					</td>
				</tr>
			</table>
		</form>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	var _dept={},
		_combotree,
		_dialog,
		_form;
		window.dept = _dept;
		
	//解析表单中class定义的easyui的插件
	//$.parser.parse('#dept_form');
		
	_combotree = $("#dept_form_parentId").combotree({
		url:"dept/deptTree.action",
		multiple: false,
		panelHeight: "200"
	});
	_dialog = _dept.dialog = $('#dept_dialog').dialog({
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
	_form = _dept.form =$('#dept_form').form();
	
	_dept.openDialog = function(title, id) {
		var url,flag;
		_form.form('reset');
		if(!id) {
			url = "dept/deptSave.action";
			flag = false;
		} else {
			url = "dept/saveUpdateDept.action?id="+id;
			flag = true;
            _form.form('load',"dept/getDeptById.action?id="+id +"&d="+new Date());
		}
        _combotree.combotree("clear");
        _combotree.combotree("reload");
		_combotree.combotree('readonly',flag);
		_form.form({url: url});
		_dialog.dialog('setTitle',title);
		_dialog.dialog('open');
	}
})
</script>
