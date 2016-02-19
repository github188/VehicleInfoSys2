<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div id="upload">
    <div class="easyui-tabs" fit="true">
        <div title="客户端上传">
            <div id="uploader" class="wu-example" style="height: 100%;width: 100%;">
                <form id="upload_form" enctype="multipart/form-data" method="post" style="height: 100%;width: 100%;">
                    <input type="text" id="cameraId" name="cameraId" style="display: none;"/>

                    <div class="easyui-layout" fit=true>
                        <div region="north" style="height: 50px;" border=false>
                            <label class="selectVideo"><input type="radio" name="type" value="2" checked>视频</label>
                            <label class="selectPic"><input type="radio" name="type" value="1">图片</label>
                            <label id="videoTypeLabel" style="display: none">视频类型:<input name="videoType" id="videoType"></label>
                            <div><span style="color:red">注意：批量任务上传图片单次最大上传300张！</span></div>
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
        <div title="服务器本地目录上传">
            <br/>
            <label>
                请选择需要上传目录下的一个文件: <br/>
                <span id="fileupload">
                    <input type="file" style="width: 500px;height: 30px;" id="browsefiles">
                </span>
            </label>
            <div id="message"></div>
        </div>
    </div>

</div>
<script type="text/javascript">
    (function () {
        var upload = {},
                $form,
                dialog,
                uuid,
                progressbar,
                CLIENT_UPLOAD_STR = '客户端上传',
                SERVER_UPLOAD_STR = '服务器本地目录上传',
                $fileListScroll = $('#fileListScroll');

        window.upload = upload;
        upload.form = $form = $('#upload_form');
        progressbar = $('#progressbar');
        $fileupload = $('#fileupload');
        browsefiles = $('#browsefiles');
        var videoType = $('#videoType');

        //加载视频类型列表
        var loadVideoType = function(){
            var videoTypeCombo = videoType.combobox({
                valueField: 'typeID',
                textField: 'typeName',
                value:'0'
            });
            $.ajax({
                url:'/tssupportVideoType/list.action',
                dataType : 'json',
                type : 'POST',
                success: function (data){
                    videoTypeCombo.combobox('loadData',data);
                }
            });
        };

        //是否显示视频类型
        function showVideoTypeCombo(){
            $form.on("click","input[name=type]",function(e){
                if(this.value == 2) {
                    $form.find('#videoTypeLabel').show();
                    loadVideoType();
                } else {
                    $form.find('#videoTypeLabel').hide();
                }
            });
        }

        //FX获取文件路径方法
        var readFileFirefox = function(fileBrowser) {
            try {
                netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
            }
            catch (e) {
                alert('无法访问本地文件，由于浏览器安全设置。为了克服这一点，请按照下列步骤操作：(1)在地址栏输入"about:config";(2) 右键点击并选择 New->Boolean; (3) 输入"signed.applets.codebase_principal_support" （不含引号）作为一个新的首选项的名称;(4) 点击OK并试着重新加载文件');
                return;
            }
            var fileName=fileBrowser.value; //这一步就能得到客户端完整路径。下面的是否判断的太复杂，还有下面得到ie的也很复杂。
            var file = Components.classes["@mozilla.org/file/local;1"]
                    .createInstance(Components.interfaces.nsILocalFile);
            try {
                // Back slashes for windows
                file.initWithPath( fileName.replace(/\//g, "\\\\") );
            }
            catch(e) {
                if (e.result!=Components.results.NS_ERROR_FILE_UNRECOGNIZED_PATH) throw e;
                alert("File '" + fileName + "' cannot be loaded: relative paths are not allowed. Please provide an absolute path to this file.");
                return;
            }
            if ( file.exists() == false ) {
                alert("File '" + fileName + "' not found.");
                return;
            }


            return file.path;
        };

        var getpath = function(obj){
//判断浏览器
            var Sys = {};
            var ua = navigator.userAgent.toLowerCase();
            var s;
            (s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] :
                    (s = ua.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] :
                            (s = ua.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] :
                                    (s = ua.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] :
                                            (s = ua.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
            var file_url="";
            if(Sys.ie<="6.0"){
                //ie5.5,ie6.0
                file_url = obj.value;
            }else if(Sys.ie>="7.0"){
                //ie7,ie8
                obj.select();
                obj.blur();
                file_url = document.selection.createRange().text;
            }else if(Sys.firefox){
                //fx
                //file_url = document.getElementById("file").files[0].getAsDataURL();//获取的路径为FF识别的加密字符串
                file_url = readFileFirefox(obj);
            }else if(Sys.chrome){
                file_url = obj.value;
            }
            return file_url;
        };
        setTimeout(function () {
            var $list = $('#fileList'), uploader = WebUploader.create({
                //不压缩image
                resize:false,
                // swf文件路径
                swf: '/js/lib/Uploader.swf',
                // 文件接收服务端。
                //server: 'resource/addFile.action',
                // 选择文件的按钮。可选。
                pick: '#filePicker',
                compress: false,
                threads:1,
                fileVal: 'files'
            });

            //显示视频类型列表
            showVideoTypeCombo();

            $list.on("click", ".remove", function (e) {
                e.preventDefault();
                var $fileItem = $(this).parent().parent().parent().parent().parent();
                $fileItem.remove();
                uploader.removeFile($fileItem[0].id, true);
            });

            // 当有文件被添加进队列的时候
            uploader.on('fileQueued', function (file) {
                var $li = $(
                        '<div id="' + file.id + '" class="item">' +
                        '<table style="width:100%"><tbody><tr>' +
                        '<td  width="243px" style="word-wrap:break-word;word-break:break-all;">' +
                        '<span class="info">' + file.name + '</span>' +
                        '</td>' +
                        '<td width="50px" style="word-wrap:break-word;word-break:break-all;">' +
                        '<span class="state" ></span>' +
                        '</td>' +
                        '<td style="width:150px">' +
                        '<div class="progressbar" ></div>' +
                        '</td>' +
                        '<td style="width:40px">' +
                        '<a href="javascript:void(0)" class="remove">删除</a>' +
                        '</td>' +
                        '</tr></tbody></table>' +
                        '</div>'
                );
                $list.append($li);

                var scrollHeight = $fileListScroll[0].scrollHeight;

                //滚动条跟随
                if ($fileListScroll.height() < scrollHeight) {
                    $fileListScroll.scrollTop(scrollHeight);
                }
            });
            uploader.on('beforeFileQueued', function (file) {
                var selectType = $form.find("[name=type]:checked").val();
                //1图片,2视频
                if (selectType == 1 && (file.type.indexOf("image") > -1 || file.type.indexOf("stream") > -1)){
                    return true;
                }

                if (selectType == 2 && (file.type.indexOf("video") > -1 || file.type.indexOf("stream") > -1)) {
                    return true;
                }

                $.messager.show({
                    title: '选择文件失败',
                    msg: '文件类型不对！',
                    timeout: 2000
                });

                return false;
            });
            uploader.on('uploadProgress', function (file, percentage) {
                percentage = percentage * 100 | 0;
                $('#' + file.id).find('.progressbar').fadeIn().progressbar({value: percentage});
            });
            var _totalHeight = 0;
            uploader.on('uploadSuccess', function (file) {
                //文件名<td>的高度
                var $fileId = $('#' + file.id),
                        tb = $fileId.find('table')[0],
                        divHeight = $fileListScroll.height(),
                        tbHeight = $(tb).height();
                //已上传文件的高度
                _totalHeight += tbHeight;

                $fileId.find('span.state').text(' 已上传');

                //滚动条跟随
                if (_totalHeight > divHeight) {
                    $fileListScroll.scrollTop(_totalHeight);
                }
            });

            uploader.on('uploadError', function (file,reason) {
                $('#' + file.id).find('span.state').text(' 上传出错,出错原因:'+reason);
            });

            uploader.on('uploadFinished', function (file) {
                upload.dialog.dialog("close");
                upload.grid.datagrid('reload');
                uploader.clean();

            });
            uploader.on('uploadAccept', function (file,ret) {
                var info = ret._raw;
                var msg = '';
                if(info == "fail") {
                    msg = "上传失败!";
                } else if (info == "typeError") {
                    msg = "文件类型错误!";
                } else if (info == "exists") {
                    msg = "文件已存在，请稍候再进行操作!";
                } else if(info == "success"){
                    msg = "上传成功!";
                }else {
                    msg = "上传出错!";
                }
                $.messager.show({
                    title: '提示',
                    msg: msg,
                    timeout: 5000
                });
            });
            uploader.clean = function () {
                uploader.reset();
                $list.html("");
            };
            upload.open = function (opt) {
                var d = upload.dialog,
                        defaultOpt = {
                            title: '文件上传',
                            selectVideo: true,
                            createMenu: false,
                            selectPic: true,
                            submitText: '上传',
                            cleanText: '清空'
                        },
                        opt = $.extend(d.dialog("options"), defaultOpt, opt);
                if (opt.selectVideo) {
                    d.find('.selectVideo').show().click()
                } else {
                    d.find('.selectVideo').hide()
                }
                if (opt.selectPic) {
                    d.find('.selectPic').show().click()
                } else {
                    d.find('.selectPic').hide()
                }
                if (opt.submitText) {
                    opt.buttons[0].text = opt.submitText
                }
                if (opt.cleanText) {
                    opt.buttons[1].text = opt.cleanText
                }
                if (opt.url) {
                    uploader.options.server = opt.url
                }
                uploader.options.createMenu = opt.createMenu;
                uploader.options.formData.uuid = util.uuid();
                d.dialog(opt).dialog('open');
            };
            // 清空file控件
            var fileclear = function () {
                dialog.find('#browsefiles').remove();
                dialog.find('#fileupload').html('<input type="file" style="width: 500px;height: 30px;" id="browsefiles">');
            };

            dialog = upload.dialog = $('#upload').dialog({
                closed: true,
                modal: true,
                width: 550,
                height: 350,
                onBeforeClose:function() {
                    var flag = true;
                    var queueFileNum = uploader.getFiles().length,successNum = uploader.getStats().successNum;
                    if(queueFileNum > 0) {
                        if(successNum > 0 && successNum < queueFileNum) {
                            flag = false;
                            uploader.stop();
                            $.messager.confirm('确认','文件还未上传完成,您确定要关闭吗?',function(r){
                                if (r){
                                    $form.find('#videoTypeLabel').hide();
                                    uploader.clean();
                                    _totalHeight = 0;
                                    fileclear();
                                    dialog.find('#message').html("");
                                    $.post('/api/stopUpload.action',{uuid:uuid});

                                    upload.dialog.dialog("close");
                                    upload.grid.datagrid('reload');
                                }
                            });
                        }
                    }
                    return flag;
                },
                onClose: function () {
                    $form.find('#videoTypeLabel').hide();
                    uploader.clean();
                    _totalHeight = 0;
                    fileclear();
                    dialog.find('#message').html("");
                    $.post('/api/stopUpload.action',{uuid:uuid});
                },
                onOpen: function () {
                    uuid=util.uuid.init();
                    if(!browsefiles) {
                        dialog.find('#fileupload').html('<input type="file" style="width: 500px;height: 30px;" id="browsefiles">');
                    }
                    var divs = $(this).find('#filePicker').children(),
                            button = $(divs[0]),
                            label = $(divs[1]);
                    //将label高宽写死，避免从服务器tab切换过来后label变自动高宽30x20
                    label.css({
                        width: '86px',
                        height:'38px'
                    })
                },
                buttons: [{
                    id:'submitText',
                    text: '上传',
                    iconCls: 'icon-ok',
                    handler: function () {
                        var tab = dialog.find('.easyui-tabs').tabs('getSelected'),
                                title = tab.panel('options').title;
                        if (title == CLIENT_UPLOAD_STR) {
                            var files = uploader.getFiles();
                            if (files.length >300) {
                                $.messager.alert("文件上传", "图片上传超过限制！最大单次上传数300！");
                                return;
                            }
                            if (files.length == 0) {
                                $.messager.alert("文件上传", "请选择文件!!");
                                return;
                            }
                            $list.find('.remove').hide();

                            var videoTypeVal='';
                            if(videoType.data('combobox')) {
                                videoTypeVal = videoType.combobox('getValue');
                            }
                            var param = {
                                cId: $form.find("#cameraId").val(),
                                dataType: $form.find("[name=type]:checked").val(),
                                videoType:videoTypeVal
                            };
                            if (uploader.options.createMenu) {
                            	var state = uploader.getStats();
                            	//文件上传完成时，不再添加目录
                            	if(uploader.getFiles().length > (state.successNum + state.uploadFailNum)){
                            		
                            		 $.post('/resource/createMenu.action', {cameraId: param.cId}, function (d) {
                                         param.parentId = d.id;
                                         uploader.option("formData", param);
                                         uploader.upload();
                                     })
                            	}                              
                            } else {
                                uploader.option("formData", param);
                                uploader.upload();
                            }
                        } else if (title == SERVER_UPLOAD_STR) {
                            var relpath = getpath($('#browsefiles'));
                            relpath = relpath.substring(0,relpath.lastIndexOf("\\"));

                            var data = {
                                menuPath: relpath.trim(),
                                cId: dialog.find('#cameraId').val(),
                                uuid:uuid
                            };

                            $.ajax({
                                type: 'post',
                                url: '/resource/addServerFile.action',
                                data: data,
                                success: function (data) {
                                    var msg = "";
                                    if (data == "success") {
                                        msg = "上传成功"
                                        upload.grid.datagrid('reload');
                                        dialog.dialog("close");
                                    } else {
                                        msg = data;
                                    }
                                    $.messager.show({
                                        title: '提示信息',
                                        msg: msg
                                    });
                                }

                            });
                            $('#submitText').linkbutton('disable');
                            $('#cleanText').linkbutton('disable');
                            serverCopy();
                        }
                    }
                },
                    {
                        id:'cleanText',
                        text: '清空',
                        iconCls: 'icon-ok',
                        handler: function () {
                            var tab = dialog.find('.easyui-tabs').tabs('getSelected'),
                                    title = tab.panel('options').title;
                            if (title == CLIENT_UPLOAD_STR) {
                                uploader.clean();
                                _totalHeight = 0;
                            } else if (title == SERVER_UPLOAD_STR) {
                                fileclear();
                                dialog.find('#message').html("");
                            }
                        }
                    }]
            });
        }, 500);

        var serverCopy = function () {
            setTimeout(function () {
                $.ajax({
                    type:'post',
                    url:'/resource/uploadFileNum.action',
                    data:{uuid:uuid},
                    success: function (num) {
                        var data = num|0;
                        if(data>0){
                            dialog.find('#message').html('后台正在处理第'+data+'个文件');
                        }
                        if(dialog.is(':visible')){
                            serverCopy();
                        }else{
                            dialog.find('#message').html("");
                        }
                    }
                });
            },250)
        }
    })()
</script>
