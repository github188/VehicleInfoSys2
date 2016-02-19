<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div id="revoke_dialog"></div>
<script type="tmpl" id="revoke_dialog_tmpl">
	<from id="revoke_dialog_form" action="#">
		<div style="font-size:15px;">
		<br/>
        &ensp;&ensp;&ensp;审核人&nbsp;:&ensp;<input id="revoke_people_name" type="text"  style="height:25px;width:180px" class="easyui-textbox" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[1,50]}" value="${empty userInfo.name ? userInfo.loginName : userInfo.name}"><br/><br/>                              
        &ensp;联系电话&nbsp;:&ensp;<input id="revoke_people_tel" type="text"  style="height:25px;width:180px;" class="easyui-textbox" data-options="validType:{num:true,length:[1,20]}" value="${userInfo.tel}"><br/><br/>
        &ensp;审核部门&nbsp;:&ensp;<input id="revoke_unit_name" type="text"  style="height:25px;width:445px;" class="easyui-textbox" value="${userInfo.depName}"><br/><br/>
        &ensp;原因描述&nbsp;:&ensp;<input id="revoke_cause"  style="height:120px;width:445px;" class="easyui-textbox" data-options="multiline:true">
        </div>
    </from>
</script>
<script type="text/javascript">
$(function() {
	var dialog = $('#revoke_dialog');
    var htmlStr = $('#revoke_dialog_tmpl').html();
    window.revokeSurveillanceTask = function (id,name,$datagrid) {
    	dialog.html(htmlStr);
        dialog.dialog({
            title: '审核撤控',
            resizable:false,
            width: 600,
            height: 400,
            closed: false,
            cache: false,
            modal: true,
            buttons: [{
                text: '审核通过',
                iconCls: 'icon-ok',
                handler: function () {               	
                    data = {
                    		surveillanceTaskId:id,
                    		name:name,
                    		recordType:1,
                    		peopleName:$.trim($('#revoke_people_name').val()),
                    		peopleTel:$.trim($('#revoke_people_tel').val()),
                    		unitName:$.trim($('#revoke_unit_name').val()),
                    		cause:$.trim($('#revoke_cause').val()),
                    		result:'撤控审核通过'
                        };
                    if ($("#revoke_dialog_form").form('validate')) { 	
                        $.ajax({
                            url: '/surveillance/addSurveillanceApplicationRecord.action',
                            type: 'post',
                            data: data,
                            success: function (d) {
                                var msg = '';
                                if (d != "success") {
                                    msg = '';
                                } else {
                                    msg = '提交成功'
                                }
                                $.messager.show({
                                    title: '提示信息',
                                    msg: msg
                                });
                                $.ajax({
                                    url: '/surveillance/updateSurveillanceTaskStatus.action',
                                    type: 'post',
                                    data: {taskId:id,status:2},
                                    success: function (d) {                                                              
                                    }
                                });
                                $.ajax({
                                    url: '/surveillance/updateEndTime.action',
                                    type: 'post',
                                    data: {taskId:id},
                                    success: function (d) {                                                              
                                    }
                                });
                                $datagrid.datagrid('reload');
                                dialog.dialog('close');
                            }
                        });
                    } 
                   }
                },{
                    text: '审核不通过',
                    iconCls: 'icon-ok',
                    handler: function () {
                        data = {
                        	    surveillanceTaskId:id,
                    		    name:name,
                    		    recordType:1,
                        		peopleName:$.trim($('#revoke_people_name').val()),
                        		peopleTel:$.trim($('#revoke_people_tel').val()),
                        		unitName:$.trim($('#revoke_unit_name').val()),
                        		cause:$.trim($('#revoke_cause').val()),
                        		result:'撤控审核未通过'
                            };
                        if ($("#revoke_dialog_form").form('validate')) {
                            $.ajax({
                                url: '/surveillance/addSurveillanceApplicationRecord.action',
                                type: 'post',
                                data: data,
                                success: function (d) {
                                    var msg = '';
                                    if (d != "success") {
                                        msg = '  ';
                                    } else {
                                        msg = '提交成功'
                                    }
                                    $.messager.show({
                                        title: '提示信息',
                                        msg: msg
                                    });
                                    $.ajax({
                                        url: '/surveillance/updateSurveillanceTaskStatus.action',
                                        type: 'post',
                                        data: {taskId:id,status:5},
                                        success: function (d) {                                                              
                                        }
                                    });
                                    $datagrid.datagrid('reload');
                                    dialog.dialog('close');
                                }
                            });
                        }
                       }
               },
               {
                    text: '清空',
                    iconCls: 'icon-clear',
                    handler: function () {
                    	$('#revoke_dialog_form').form("clear");              	
                    }
                }
            ],
            onBeforeClose: function () {
                $(this).dialog('clear');
            },
            onOpen:function(){
            	//重新解析dom
                $.parser.parse(dialog);
            }
        });
    }
		
});
</script>
