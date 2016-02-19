<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div class="easyui-layout" fit="true" style="width: 100%; height: 100%;">
    <div id="camera_group" data-options="split:true,region:'center',title:'摄像头分组管理'" style="width:100%">
        <div id="camera_group_datagrid"></div>
    </div>
</div>
<script type="text/javascript">
    (function ($, w) {
        var cameraGroup = w.cameraGroup;
        var camera_group_datagrid = $('#camera_group_datagrid').datagrid({
                    url:'cameraGroup/findCameraGroupList.action',
                    loadMsg:'数据载入中',
                    pagination:true,
                    rownumbers:true,
                    fit: true,
                    singleSelect:true,
                    onLoadSuccess:function(){
                        var self=$(this).parent();
                        setTimeout(function(){
                            var a=self.find('a').linkbutton({plain:true})
                        },200)
                    },
                    idField:'id',
                    columns:[[
                        {field:'name',title:'组名称',styler:function(){
                            return 'height:27px';
                        }},
                        {field:'description',title:'组描述'}/*,
                        {field:'operate',title:'操作',width:60,formatter:function(v,row){
                            var html='<a href="#" onclick="area.openDialog(\'修改组\','+row.id+')" data-options="iconCls:\'icon-edit\'" title="修改"></a>';
                            return html;
                        }}*/
                    ]],
                    toolbar: [{
                        iconCls: 'icon-add',
                        text: "添加",
                        handler: function() {
                            cameraGroup.openDialog('添加分组');
                        }
                    }]
                });

        cameraGroup.form.form({
            success: function(data) {
                if(data=="success") {
                    $.messager.show({
                        title:'消息',
                        msg:'提交成功！',
                        timeout:2000
                    });
                    cameraGroup.dialog.dialog('close');
                    camera_group_datagrid.datagrid("reload");
                }else{
                    $.messager.alert({title:'错误',msg:'提交失败'});
                }
            }
        });
    })(jQuery,window);
</script>
