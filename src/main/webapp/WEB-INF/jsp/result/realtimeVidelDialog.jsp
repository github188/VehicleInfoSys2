<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="realtimeVideo">
</div>
<script type="text/javascript">
$(function(){
	var cassid='<OBJECT ID="ocx" CLASSID="CLSID:19A3589E-25DD-4924-9380-5EA92642B6A4" width=800 height=600></OBJECT>';
	var realtimePlayer = {
			isSuccessOpenFlag:true,
			play:function(url,cameraName){				
				$('#realtimeVideo').dialog({
					title:cameraName+'-实时视频',
					width:820,
					height:640,
					closed:true,
					modal:true,				
				    maximizable:true,
				    resizable:true,
					collapsible:true,
					draggable:true,
					onBeforeOpen:function() {
						$(this).html(cassid);
					},
					onBeforeClose:function(){
						if(realtimePlayer.isSuccessOpenFlag){
							ocx.PlayBackControl("stop");
						}
						$(this).dialog('clear');
					}
				});
				
				$('#realtimeVideo').dialog("open");							
				try{
					ocx.PlayHikDVRRealVideo(url);
					realtimePlayer.isSuccessOpenFlag=true;
				}catch(e){
					$.messager.alert('提示','控件未安装或浏览器不支持播放,请先下载控件后尝试！');	
					realtimePlayer.isSuccessOpenFlag=false;
					$('#realtimeVideo').dialog("close");
				}
			}
		};
	window.relatime_videoPlayer=realtimePlayer;
});
</script>