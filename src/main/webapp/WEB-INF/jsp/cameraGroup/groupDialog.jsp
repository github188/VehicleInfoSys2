<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="cameraGroup_dialog">
	<div style="padding: 10px;text-align: left">
		<form id="cameraGroup_form" method="post">
			<table>
				<tr>
					<td><label><span style="color: red">*</span>分组名称:</label></td>
					<td>
			            <input class="easyui-textbox" name="name" style="width:178px"
			            	data-options="required:true,validType:['letterAndNumberAndChinese','length[2,15]']" />
					</td>
				</tr>
				<tr>
					<td><label>&nbsp;分组描述:</label></td>
                    <td>
                        <textarea name="description" rows="5" cols="20"  class="easyui-validatebox" data-options="validType:{length:[0,250],letterAndNumberAndChinese:true}" ></textarea>
                    </td>
				</tr>
			</table>
		</form>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	var cameraGroup={},
		dialog,
		form;
	//解析表单中class定义的easyui的插件
	//$.parser.parse('#area_form');
		
	dialog = cameraGroup.dialog = $('#cameraGroup_dialog').dialog({
		closed:true,
		modal:true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				form.form('submit');
			}
		},{
			text:'重置',
			iconCls:'icon-redo',
			handler:function(){
				form.form('reset');
			}
		}]
	});
	form = cameraGroup.form =$('#cameraGroup_form').form();
	
	cameraGroup.openDialog = function(title, id) {
		var url,flag;
		form.form('reset');
		if(!id) {
			url = "cameraGroup/groupSave.action";
			flag = false;
		} else {
			url = "cameraGroup/saveUpdateGroup.action?id="+id;
			flag = true;
			form.form('load',"cameraGroup/getGroupById.action?id="+id);
		} 
		form.form({url: url});
		dialog.dialog('setTitle',title);
		dialog.dialog('open');
	};

    window.cameraGroup = cameraGroup;
})
</script>
