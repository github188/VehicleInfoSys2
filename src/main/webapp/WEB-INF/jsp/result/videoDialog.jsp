<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="video_play">
	<OBJECT ID="OrgVideo" CLASSID="CLSID:319A6E02-E7BF-48F4-92DD-575B805D3092"></OBJECT>
</div>
<script type="text/javascript">
(function(){
	var temp = '<OBJECT ID="OrgVideo" CLASSID="CLSID:319A6E02-E7BF-48F4-92DD-575B805D3092"></OBJECT>';
	
	var player = {
		isInitFail:false,
		serverIp:'${serverIp}',
		init:function() {
			OrgVideo.Ocx_Init(this.serverIp,1);
		},
		play:function(path,frame_index) {
			this.dialog.dialog('open');
			if(this.isInitFail) {
				$.messager.alert('提示','控件未安装或浏览器不支持播放,请先下载控件后尝试！');
				return;
			}
			
			var start,end;
			if(frame_index != null) {
				//开始帧数取出现结果帧数的前四秒
				start = frame_index - 100;
				if(start<0) {start=0}
				//结束帧数取结果帧数的后四秒
				end = frame_index + 100;
			} else {
				start = 0;
				end = 0;
			}
			if(path.indexOf('file')>=0) {
				path = path.substring(7);
			}
			var param = '<?xml version="1.0"encoding="UTF-8"?<video><path>'+path+'</path><startframe>'+start+'</startframe><endframe>'+end+'</endframe></video>';
			var result = OrgVideo.Ocx_PlaySrvVideo(param); 
		},
		dialog:$('#video_play').dialog({
			title:'播放结果片段',
			closed:true,
			modal:true,
			onBeforeOpen:function() {
				var that = this;
				if(player.isInitFail) {
					window.open('3nd/VSOCX_Setup.exe');
					return false;
				}
				$(this).html(temp);
				try {
					player.init();
				} catch (e) {
					player.isInitFail = true;
				}
			},
			onBeforeClose:function(){
				$(this).dialog('clear');
				player.isInitFail = false;
			}
		})
	};
	
	try {
		player.init();
	} catch (e) {
		player.isInitFail = true;
	}
	
	window.video_player = player;
	
})()
</script>