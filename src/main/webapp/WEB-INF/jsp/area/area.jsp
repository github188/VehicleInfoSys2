<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class='easyui-layout' fit=true>
	<div region='west' style="width:200px;" split=true>
		<ul id="area_tree"></ul>
	</div>
	<div region='center' border=false>
		<div id='area_datagrid'></div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	var _area=window.area,
		_tree,
		_datagrid;
		
   	_tree = $("#area_tree").tree({
	    url:'area/areaTreeJson.action',
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
	_datagrid = $('#area_datagrid').datagrid({
		url:'area/findAreaList.action',
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
			{field:'name',title:'行政区划名称',styler:function(){
				return 'height:27px';
			}},
			{field:'dsc',title:'描述'},
			{field:'areaCode',title:'行政区划编码'},
			{field:'parentName',title:'上级行政区划',formatter:function(v,row){
                     return row.parentName==row.name?'':v;
            }},
			{field:'isEnable',title:'是否启用',width:60,formatter:function(v,row,i){
							switch (v) {
							case 0:
								return "<span class='red'>停用</span>";
							default:
								return "启用";	
							}
						}},
			{field:'operate',title:'操作',width:120,formatter:function(v,row){
                var opre;
                row.isEnable == 0 ? opre="启用":opre="禁用";
				var html='<a href="#" onclick="area.openDialog(\'修改行政区划\','+row.id+')" data-options="iconCls:\'icon-edit\'" title="修改"></a>';
			 	html+='<a href="#" onclick="area.isEnableArea('+row.id+','+row.isEnable+')" data-options="iconCls:\'icon-tip\'" title="'+opre+'"></a>';
				return html;
			}}
		]],
		toolbar: [{
			iconCls: 'icon-add',
			text: "添加",
			handler: function() {
				_area.openDialog('添加行政区划');
			}
		}]
	});
	_area.form.form({
		success: function(data) {
			if(data=="success") {
				$.messager.show({
					title:'消息',
					msg:'提交成功！',
					timeout:2000
				});
    		 	_area.dialog.dialog('close');
	    		_datagrid.datagrid("reload");
	    		_tree.tree("reload");
			}else{
				$.messager.alert({title:'错误',msg:'提交失败'});
				console.info(data);
            }
    	}
	});
	
	_area.isEnableArea = function(rowId,isEnable) {
        var opre,msg;
        isEnable == 0 ? opre="启用":opre="禁用";

        msg = '确定要<span style="color:red">'+opre+'</span>吗？';

		$.messager.confirm('确认', msg, function(y) {
			if (y) {
				$.ajax({
					type: 'post',
					url: 'area/enableOrDisable.action',
                       data:{id:rowId,enableOrDisable:isEnable^1},
					dataType: 'text',
                    success:function(data) {
                        if(data != '') {
                            $.messager.show({
                                title:'提示',
                                msg:data,
                                timeout:5000,
                                showType:'slide'
                            });
                        } else {
                            _datagrid.datagrid("reload");
                        }
                    },
					error: function(data) {
						_datagrid.datagrid("reload");
					}
				});
			}
		});
	}
})
</script>
