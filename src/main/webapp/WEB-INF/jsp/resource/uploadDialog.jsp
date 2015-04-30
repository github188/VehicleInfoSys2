<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="upload">
	<div id="uploader" class="wu-example" style="height: 100%;width: 100%;">
		<form id="upload_form" enctype="multipart/form-data" method="post" style="height: 100%;width: 100%;">
			<input type="text" id="cameraId" name="cameraId" style="display: none;" />
			<div class="easyui-layout" fit=true>
				<div region="north" style="height: 30px;" border=false>
					<label class="selectVideo"><input type="radio" name="type" value="2" checked>视频</label>
					<label class="selectPic"><input type="radio" name="type" value="1">图片</label>
				</div>
				<div id="fileListScroll" region="center" style="height:100px;max-height: 300px;" border=false>
					<!--用来存放文件信息-->
					<div id="fileList" class="uploader-list"></div>
				</div>
				<div region="south" style="height: 45px;" border=false>
					<div id="filePicker" style="float:right">选择文件</div>
				</div>
			</div>
		</form>
	</div>
</div>
<script type="text/javascript">
	(function(){
		var upload={},
		$form,
		dialog,
		progressbar,
		interValId,
		$fileListScroll=$('#fileListScroll');

		window.upload = upload;
		upload.form=$form=$('#upload_form');
		progressbar = $('#progressbar');

		setTimeout(function() {
			var $list=$('#fileList'),uploader = WebUploader.create({
				// swf文件路径
				swf:  '/js/lib/Uploader.swf',
				// 文件接收服务端。
				//server: 'resource/addFile.action',
				// 选择文件的按钮。可选。
				pick: '#filePicker',
				fileVal :'files'
			});
			$list.on("click",".remove",function(e){
				e.preventDefault();
				var $fileItem=$(this).parent().parent().parent().parent().parent();
				$fileItem.remove();
				uploader.removeFile($fileItem[0].id ,true);
			});
			
			// 当有文件被添加进队列的时候
			uploader.on( 'fileQueued', function( file ) {
				$list.append( '<div id="' + file.id + '" class="item">' +
					'<table style="width:100%"><tbody><tr>'+
					'<td  width="243px" style="word-wrap:break-word;word-break:break-all;">'+
					'<span class="info">' + file.name + '</span>'+
					'</td>'+
					'<td width="50px" style="word-wrap:break-word;word-break:break-all;">'+
					'<span class="state" ></span>'+
					'</td>'+
					'<td style="width:150px">'+
					'<div class="progressbar" ></div>'+
					'</td>'+
					'<td style="width:40px">'+
					'<a href="javascript:void(0)" class="remove">删除</a>'+
					'</td>'+
					'</tr></tbody></table>'+
				'</div>' );
				
				var scrollHeight=$fileListScroll[0].scrollHeight;
				
				//滚动条跟随
				if($fileListScroll.height()<scrollHeight){
				 	$fileListScroll.scrollTop(scrollHeight);
				}
			});
			uploader.on( 'beforeFileQueued', function( file) {
				var selectType=$form.find("[name=type]:checked").val();
				//1图片,2视频
				if(selectType==1&& file.type.indexOf("image")>-1){
					return true;
				}
				if(selectType==2&& (file.type.indexOf("video")>-1||file.type.indexOf("stream")>-1)){
					return true;
				}  
				$.messager.show({
					title:'选择文件失败',
					msg:'文件类型不对！',
					timeout:2000
				});
			 	
				return false;
			});
			uploader.on( 'uploadProgress', function( file, percentage ) {
				percentage=percentage*100|0;
				$('#'+file.id).find('.progressbar').fadeIn().progressbar({value:percentage});
			});
			var _totalHeight=0;
			uploader.on( 'uploadSuccess', function( file ) {
				//文件名<td>的高度
				var $fileId=$('#'+file.id),
					tb=$fileId.find('table')[0],
					divHeight=$fileListScroll.height(),
					tbHeight=$(tb).height();
				//已上传文件的高度
				_totalHeight+=tbHeight;
				
				$fileId.find('span.state').text(' 已上传');
				
				//滚动条跟随
				if(_totalHeight>divHeight){
					$fileListScroll.scrollTop(_totalHeight);
				}
			});

			uploader.on( 'uploadError', function( file ) {
				$( '#'+file.id ).find('span.state').text(' 上传出错');
			});

			uploader.on( 'uploadFinished', function( file ) {
				//upload.dialog.dialog("close");
				upload.grid.datagrid('reload');
				//uploader.clean();

			});
			uploader.clean=function(){
				uploader.reset();
				$list.html("");
			}
			upload.open=function(opt){
				var d= upload.dialog,
				defaultOpt={title:'文件上传',selectVideo:true,createMenu:false,selectPic:true,submitText:'上传',cleanText:'清空'},
				opt=$.extend(d.dialog("options"),defaultOpt,opt)
				if(opt.selectVideo){
					d.find('.selectVideo').show().click()
				}else{
					d.find('.selectVideo').hide()
				}
				if(opt.selectPic){
					d.find('.selectPic').show().click()
				}else{
					d.find('.selectPic').hide()
				}
				if(opt.submitText){
					opt.buttons[0].text=opt.submitText
				}
				if(opt.cleanText){
					opt.buttons[1].text=opt.cleanText
				}
				if(opt.url){
					uploader.options.server=opt.url
				}
				uploader.options.createMenu=opt.createMenu
				uploader.options.formData.uuid=util.uuid()
				d.dialog(opt).dialog('open');
			}  

			upload.dialog = $('#upload').dialog({
				closed:true,
				modal:true,
				width:500,
				height:300,
				onClose:function(){
					uploader.clean();
					_totalHeight=0;
				},
				onOpen:function() {
					var divs = $(this).find('#filePicker').children(),
					button = $(divs[0]),
					label = $(divs[1]),
					width = button.outerWidth ?
					button.outerWidth() : button.width(),
					height = button.outerHeight ?
					button.outerHeight() : button.height();
					label.css({
						width: width + 'px',
						height: height + 'px'
					})
				},
				buttons:[{
					text:'上传',
					iconCls:'icon-ok',
					handler:function() {
						var files=uploader.getFiles();
						if(files.length==0){
							$.messager.alert("文件上传","请选择文件!!");
							return;
						}
						$list.find('.remove').hide()
						var param={
							cId:$form.find("#cameraId").val(),
							dataType:$form.find("[name=type]:checked").val()
						}
						if(uploader.options.createMenu){
							$.post('/resource/createMenu.action',{cameraId:param.cId},function(d){
								param.parentId=d.id;
								uploader.option("formData",param);
								uploader.upload();
							})
						}else{
							uploader.option("formData",param);
							uploader.upload();
						}
					}
				},
				{
					text:'清空',
					iconCls:'icon-ok',
					handler:function() {
						uploader.clean();
						_totalHeight=0;
					}
				}]
			});
		},500);
	})()
</script>
