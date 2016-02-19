<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div id="realTime_page" class="easyui-layout" fit=true style="min-width: 800px;">
	<div region="north" style="height: 60px;">
		<table class="info_area">
			<tr>
				<td width="70%">
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
				<td width="30%" align="right">
					<a href="javascript:void(0)" id="monior${camera.id}" class="realtime-monior" title="实时视频源"></a><label style="color: #0B14C2;">（查看实时视频流）</label>
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
	var tab = util.getSelectedTab();
	
	var cameraId = '${camera.id}';
	var cameraName = '${camera.name}';
	var taskId = '${taskId}';
	var url = '${camera.url}';
	var followarea = '${camera.followarea}';
	var isRefresh = false; //false启动/true关闭 自动刷新
	
	var realTime_page = $('#realTime_page',tab[0]); 
	var realTime_tabs = $('#realTime_tabs',tab[0]);  //实时tab页
	var realTime_task_grid = $('#realTime_task_grid',tab[0]); //实时任务列表

    if(${camera.status==1}) {
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
					drawLine(cameraId,bigPicUrl,1,followarea,null,startTaskCallBack);
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

        //查看实时视频流
        $("#monior${camera.id}").click(function(){
            //var url="hikdvr://ip=192.168.2.27/port=8000/user=admin/pwd=12345/channel=33"; //海康本地测试平台url
            if('' == url) {
                $.messager.alert('启动失败','连接监控点失败，请检查监控点设置！');
                return;
            }
            relatime_videoPlayer.play(url,cameraName);
        });
    } else {
        $('#task_switch',realTime_page).attr("disabled",true);
        $('.realtime-monior',realTime_page).attr("disabled",true);
    }

	function refresh()
	{
		if(isRefresh) return;
		realTime_task_grid.datagrid('reload');
		setTimeout(refresh,5000);
	}
	setTimeout(refresh,5000);

var newTab=function(row){
	var title='任务结果-'+row.name;
	if(realTime_tabs.tabs('exists',title)){
		realTime_tabs.tabs('select',title)
	}else{
		realTime_tabs.tabs('add',{
			title:title,
			closable:true,
			href:'result/results.action?taskId='+row.id
		});
	}
}	
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
				{field:'status',title:'状态',formatter:util.formateTaskStatus},
				{field: 'showResult', title: '已识别车辆', formatter: function(v,row){
	             	 var rowId='实时任务列表'+row.id
                    var html='<a id="'+rowId+'" class="showResult" href="javascript:void(0)">'+row.count+'</a>';
                    tab.off('click','#'+rowId).on('click','#'+rowId,function(){
	        			newTab(row);	
                    })
                    return html;
                }
        	},
        	{field: 'xxxx', title: '下  载', formatter: function(v,row){
                var html='<a id="'+"download"+row.id+'" class="showResult" href="javascript:void(0);">'+'下载'+'</a>';
                tab.off('click','#download'+row.id).on('click','#download'+row.id,function(){
                   //判断条目数
                   $.ajax({
                   	url:'/task/downloadItemCount.action',
                   	data:{taskId:row.id},
                   	type:'post',
                   	success:function (item){
                   		if(item>2000){
                   			$.messager.show({
                   				title:"提示消息",
                   				msg:"数据条目数大于2000，无法下载。",
                   				timeout:3000,
                   				showType:'slide'
                   			});
                   		}else{
                   			var form = $("<form>");           
                               form.attr('style', 'display:none');     
                               form.attr('target', '');    
                               form.attr('method', 'post');    
                               form.attr('action', '/task/download.action');  
                                                                  
                               var input = $('<input>');                                  	
                               input.attr('type', 'hidden');     
                               input.attr('name', 'taskId');    
                               input.attr('value', row.id);
                               form.append(input);                                
                                                        
                              $('body').append(form);                      
                               form.submit();    
                               form.remove();
                   		}
                   	}
                   });
                })
                return html;
            },width:50}
			]],
			onDblClickRow:function(index,row) {//列表每条记录的双击事件
				newTab(row);
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