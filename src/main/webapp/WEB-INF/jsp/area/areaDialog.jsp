<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="area_dialog">
	<div style="padding: 10px;text-align: right">
		<form id="area_form" method="post">
			<table>
				<tr>
					<td><label><span class="red">*</span>行政区划名称:</label></td>
					<td>
			            <input class="easyui-textbox" name="name"
			            	data-options="required:true,validType:['letterAndNumberAndChinese','length[2,15]']" />
					</td>
				</tr>
				<tr>
					<td><label>行政区划编码:</label></td>
					<td>
			            <input class="easyui-textbox" name="areaCode"
			            	data-options="validType:['letterAndNumber','length[0,20]']" />
					</td>
				</tr>
				<tr>
					<td><label>行政区划描述:</label></td>
					<td>
						<input class="easyui-textbox" name="dsc" 
							data-options="validType:['letterAndNumberAndChinese','length[0,25]']" />
					</td>
				</tr>
				<tr>
					<td><label>上级行政区划:</label></td>
					<td>
						<input id="area_form_parentId" name="parentId">
					</td>
				</tr>
			</table>
		</form>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	var _area={},
		_combotree,
		_dialog,
		_form,
            _title;
		window.area = _area;
		
	//解析表单中class定义的easyui的插件
	//$.parser.parse('#area_form');
		
	_combotree = $("#area_form_parentId").combotree({
		url:"area/areaTreeJson.action",
		valueField:"id",
		textField:"name",
		multiple: false,
		panelHeight: "200",
        loadFilter: function(data){
            return util.getNodes(data);
        }
	});
	_dialog = _area.dialog = $('#area_dialog').dialog({
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
                if(_title == "添加行政区划") {
                    _combotree.combotree("clear");
                }
			}
		}]
	});
	_form = _area.form =$('#area_form').form();
	
	_area.openDialog = function(title, id) {
        _title = title;
		var url,flag;
		_form.form('reset');
        _combotree.combotree("clear");
        if(!id) {
			url = "area/areaSave.action";
			flag = false;
		} else {
			url = "area/saveUpdateArea.action?id="+id;
			flag = true;
			_form.form('load',"area/getAreaById.action?id="+id);
		} 
		_combotree.combotree('readonly',flag);
		_combotree.combotree("reload");
		_form.form({url: url});
		_dialog.dialog('setTitle',title);
		_dialog.dialog('open');
	};

    $("input[readOnly]").keydown(function (e) {
        e.preventDefault();
    });
})
</script>
