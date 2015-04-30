<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div id="realTime_page" class="easyui-layout" fit=true style="min-width: 800px;">
	<div region="north" style="height: 60px;">
		<table class="info_area">
			<tr>
				<td>
					<span>实时任务状态:</span> 
					<c:choose>
					    <c:when test="${!empty taskId}">
					    	<label>识别中</label>&nbsp;&nbsp;
					    	<input id="task_switch" type="button" value="停止识别" />
					    </c:when>
					    <c:otherwise>
					    	<label>未识别</label>&nbsp;&nbsp;
					    	<input id="task_switch" type="button" value="开始识别" />
					    </c:otherwise>
					</c:choose>
					<label style="color: red;">(若需画感兴趣区域，请先在离线源中上传一张该监控点的画面截图！)</label>
				</td>
			</tr>
		</table>
	</div>
	<div region="center" border=false>
		<div id="realTime_tabs" class="easyui-tabs result_details_tabs" fit=true>
			<div title="实时任务列表">
				<div id="realTime_task_grid" border=false>
					<div class='toolbar'>
						<a id="realremove" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain=true>删除</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(function(){
	
	//获取最外层tab对象，用以定位具体tab页
	var tab = window.mainTabs.tabs('getSelected');
	
	var cameraId = '${camera.id}';
	var taskId = '${taskId}';
	var url = '${camera.url}';
	var followarea = '${camera.followarea}';
	
	var realTime_page = $('#realTime_page',tab[0]); 
	var realTime_tabs = $('#realTime_tabs',tab[0]);  //实时tab页
	var realTime_task_grid = $('#realTime_task_grid',tab[0]); //实时任务列表
	
	//创建表单删除事件
	util.createRemoveEvent('#realremove', realTime_task_grid, 'task/delete.action');
	
	//绑定开启/结束实时任务按钮的事件
	$('#task_switch',realTime_page).click(function() {
		var that = $(this);
		var label = that.prev();
		var flagStr = label.html();
		
		var startTaskCallBack = function(msg) {
			if(!msg || msg == '') {
				$.messager.show({
					title:'提示',
					msg:'监控点正在识别中，无需启动！',
					timeout:2000
				});
			}
			label.html('识别中');	
			that.val('停止识别');	
			realTime_task_grid.datagrid('load');
		}
		
		if(flagStr == '未识别') {
			if('' == url) {
				$.messager.alert('启动失败','连接监控点失败，请检查监控点设置！');
				return;
			}
			$.post('camera/getBigUrl.action',{id:cameraId},function(d) {
				var bigPicUrl = d;
				if(bigPicUrl) {
					drawLine(cameraId,bigPicUrl,1,followarea,startTaskCallBack);
				} else {
					$.post('task/start.action',{'id':cameraId},startTaskCallBack);
				}
			});
		} else {
			$.post('task/stop.action',{'taskId':taskId},function(msg) {
				if(null != msg && '' != msg) {
					$.messager.show({
						title:'提示',
						msg:'监控点已于'+util.formateTime(msg)+'停止，无需停止！',
						timeout:2000
					});
				}
				label.html('未识别');		
				that.val('开始识别');	
				realTime_task_grid.datagrid('load');
			});
		}
	});
	
	//设置延迟加载页面中的列表
	setTimeout(function() {
		//定义实时流历史任务列表
		realTime_task_grid.datagrid({
			url:'task/list.action?type=1&cameraId='+cameraId,
			loadMsg:'数据载入中',
			pagination:true,
			rownumbers:true,
			fit: true,
			singleSelect:false,
			columns:[[
				{field:'ck',checkbox:true},
				{field:'name',title:'任务名称'},
				{field:'startTime',title:'开始时间',formatter:util.formateTime},
				{field:'endTime',title:'结束时间',formatter:util.formateTime},
				{field:'status',title:'状态',formatter:util.formateTaskStatus}
			]],
			onDblClickRow:function(index,row) {//列表每条记录的双击事件
				realTime_tabs.tabs('add',{
					title:'任务结果-'+row.name,
					closable:true,
					href:'result/results.action?taskId='+row.id
				});
			},
			onLoadSuccess:function(data) {//列表加载数据成功时的回调函数
				var list = data.rows;
				if(list.length>0) {
					var status = list[0].status;
					var that = $('#task_switch',tab[0]);
					var label = that.prev();
					if(status == 1) {
						label.html('识别中');	
						that.val('停止识别');
						taskId = list[0].id;
					} else {
						label.html('未识别');		
						that.val('开始识别');	
					}
				}
			},
			toolbar:$('#realTime_task_grid .toolbar',tab[0]).get(0)
		});
	},200);
});
</script>