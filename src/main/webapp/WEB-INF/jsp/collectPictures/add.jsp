<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="collectPicture_dialog">
	<div style="padding: 10px;text-align: left">
		<form id="collectPicture_form" method="post">
			<input type="hidden" id="cameraNames" name="cameraNames">
			<table>
				<tr style="vertical-align: middle;">
					<td><label><span style="color: red">*</span>监控点:</label></td>
					<td>
						<input id="collectPicture_cameraIds" type="text" style="width:200px;"><a href="javascript:void(0)"><span id="map_button" class="l-btn-left l-btn-icon-left"><span class="l-btn-text">地图</span><span class="l-btn-icon icon-search">&nbsp;</span></span></a>
					</td>
				</tr>
				<tr style="vertical-align: middle;">
					<td><label>&nbsp;采集时段(起始时间):</label></td>
                    <td>
						<input id="startTime" type="text" name="startTime" style="width:160px;">
                    </td>
				</tr>
				<tr style="vertical-align: middle;">
					<td><label>&nbsp;采集时段(截止时间):</label></td>
					<td>
						<input id="endTime" type="text" name="endTime" style="width:160px;">
					</td>
				</tr>
			</table>
		</form>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	var collectPicture={},
		dialog,
		form;

	//存放被选择的监控点
	var camIdArr = [],camNameArr = [];
	//树形下拉框 监控点
	var collectPicture_cameraIds = $('#collectPicture_dialog #collectPicture_cameraIds').combotree({
		url: '/camera/camLstTree.action',
		animate:true,
		multiple:true,
		onCheck:function(node,checked) {
			util.checkCam(collectPicture_cameraIds,node,camIdArr,camNameArr,checked);
		},
		onClick:function(node) {
			util.checkCam(collectPicture_cameraIds,node,camIdArr,camNameArr,node.checked);
		},
		onChange:function(nVal,oVal) {
			util.checkCamWithMap(collectPicture_cameraIds);
		}
	});

	var startTime = $('#collectPicture_dialog #startTime').datetimebox({
		editable: false
	});

	var endTime = $('#collectPicture_dialog #endTime').datetimebox({
		editable: false
	});

	dialog = collectPicture.dialog = $('#collectPicture_dialog').dialog({
		closed:true,
		modal:true,
        onClose:function() {
            camIdArr = [];
            camNameArr = [];
        },
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				var startTime = $('#collectPicture_dialog #startTime').combo('getText');
				var endTime = $('#collectPicture_dialog #endTime').combo('getText');
				if(startTime=='' && endTime!=''){
					$.messager.show({
						title: '提示',
						msg: "请选择起始时间",
						timeout: 2000
					});
					return;
				}
				if(startTime!='' && endTime!='' && startTime>endTime){
					$.messager.show({
						title: '提示',
						msg: "起始时间不能大于结束时间",
						timeout: 2000
					});
					return;
				}

				var cameras = camNameArr.join();
				if(cameras==undefined || cameras==''){
					$.messager.show({
						title: '提示',
						msg: "请选择监控点",
						timeout: 2000
					});
					return;
				}
				$('#collectPicture_dialog #cameraNames').val(cameras); //获取监控点名称

				$.post('collectPictures/checkAdd.action',{'cameraNames':cameras},function(msg) {
					if(null != msg && '' != msg) {
						form.form('submit');
						camIdArr.length = 0;
						camNameArr.length = 0;
					}else{
						$.messager.show({
							title:'提示',
							msg:'您选择监控点中包含未终止的任务！',
							timeout:2000
						});
					}
				});
			}
		},{
			text:'重置',
			iconCls:'icon-redo',
			handler:function(){
				form.form('reset');
                camIdArr = [];
                camNameArr = [];
			}
		}]
	});


	form = collectPicture.form =$('#collectPicture_form').form();

	collectPicture.dialogAdd = function(title) {
		form.form('reset');
		form.form({url: "/collectPictures/save.action"});
		dialog.dialog('setTitle',title);
		dialog.dialog('open');

		dialog.on('click', '#map_button', function (e) {
			e.preventDefault();
			pubMapWindow.open(collectPicture_cameraIds,camIdArr,camNameArr);
		});
	};

    window.collectPicture = collectPicture;
})
</script>
