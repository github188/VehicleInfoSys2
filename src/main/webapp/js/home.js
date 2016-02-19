(function ($) {
    //改密码事件
    $("body").on('click', '.changePwd', function (e) {
        e.preventDefault();
        var $win = $("#win_changePwd"),
            $form = $win.find('form'),
            userId = $(this).attr('userId');
        //清空表单数据
        $form.form('reset');
        if (userId) {
            $form.find('#changePwd_userId').val(userId);
        }
        $win.show().dialog({
            title: "修改密码",
            modal: true,
            buttons: [{
                text: '修改',
                handler: function () {
                    if ($form.form("validate")) {
                        $.post("changePwd.action", $form.serialize(), function (data) {
                            var msg = data ? "修改成功" : "修改失败";
                            $.messager.alert('修改密码', msg);
                            if (data)$win.dialog("close");
                        })
                    }
                }
            }, {
                text: '取消',
                handler: function () {
                    $win.dialog("close")
                }
            }]
        });
    })
        //绑定结果详情页面图片的右键事件
        .on('mousedown', '.result_img', function (e) {
            if (e.which == 3) {
                var $this = $(this),
                    src = $this.attr('src').replace(/[\u4e00-\u9fa5]/g, function (str) {
                        return encodeURI(str);
                    });
                $this.attr('src', src);
            }
        })
        //查看版本号
        .on("click","#showVersion",function(e) {
            $.ajax({
                url: 'info.action',
                type: 'post',
                success: function (d) {
                    $.messager.alert("版本号",d);
                }
            });
        });
    //创建全局tabs对象（以下所有页面元素的获取都是基于这个tab对象下的，避免多tab页混淆导致的定位错误问题）
    var mainTabs = window.mainTabs = $('#main_tabs').tabs(),
        navigators = [
            {
                id: 'serverstatus_control',
                href: '/serverstatus/index.action'
            }, {
                id: 'navigate_result',
                href: '/result/index.action'
            }, {
                id: 'task_management',
                href: '/task/index.action'
            }, {
                id: 'surveillance_management',
                href: '/surveillance/index.action'
            }, {
                id: 'user_management',
                href: '/user/index.action'
            }, {
                id: 'fakeLicensedCar_management',
                href: '/fakeLicensedCar/index.action'
            },{
                id: 'unlicensedCar_management',
                href: '/unlicensedcar/index.action'
            }, {
                id: 'serachCarByImage_management',
                href: '/serachCarByImage/index.action'
            },{
                id: 'travelTogetherVehicle',
                href: '/travelTogetherVehicle/index.action'
            },{
                id: 'firstIntoCityAnalyse',
                href: '/firstIntoCityAnalyse/index.action'
            },{
                id: 'frequentlyPassVehicle',
                href: '/frequentlyPassVehicle/index.action'
            },{
                id: 'frequentNocturnalVehicle',
                href: '/frequentNocturnalVehicle/index.action'
            },{
                id: 'aerialMammalVehicle',
                href: '/aerialMammalVehicle/index.action'
            },{
                id:"area_management",
                href:"/area/area.action"
            },{
                id:"log_management",
                href:"/logger/index.action"
            },/*{
                id:"cam_management",
                href:"/camera/manage.action?pageType=1"
            },*/{
                id:"collectPictures_management",
                href:"/collectPictures/index.action"
            },{
                id:"department_management",
                href:"/dept/dept.action"
            },{
                id:"role_management",
                href:"/role/role.action"
            }
        ];
    navigators.forEach(function (n) {
        var $el=$('#' + n.id).click(function (e) {
            e.preventDefault();
            var title = $.trim($el.text());
            if (mainTabs.tabs('exists', title)) {
                mainTabs.tabs('select', title)
            } else {
                mainTabs.tabs('add', {
                    title: title,
                    closable: true,
                    href: n.href
                });
            }
        })
    });

    checkFailTask();

    //设置定时器，作用是定时（60秒）检查失败的任务，若有，则报警提示用户！
    setInterval(checkFailTask, 60000);
    //每隔1秒检查一下是否还是登录状态，不是就提示用户
    var checkLogin = function () {
        var timestamp = new Date().getTime();
        setTimeout(function () {
            $.post('/api/isLogin.action', {timestamp: timestamp}, function (data) {
                if (!data) {
                    //如果有服务器本地文件上传,则中断
                    $.post('/api/stopUpload.action',{uuid:util.uuid.get()});
                    //提示信息
                    $.messager.alert('提示消息', '您可能长时间没有操作或帐号在其它地方登录,请重新登录!', 'info', function() {
                        location.href = '/';
                    }).window({
                    	onClose:function(){
                    		location.href = '/';
                    	}
                    })
                } else {
                    checkLogin()
                }
                //服务器重启或关闭自动跳转到登录界面
            }).error(function () {
                location.href = '/'
            })
        }, 5000)
    }
    checkLogin();
    
    setInterval(checkSurveillanceResult,5000)
    
  //检查正在布控的任务的结果
    function checkSurveillanceResult(){
    	$.post('/surveillance/checkResult.action',{interval:5000},function(data){
            var length = data.length;
            if($.isArray(data) && length > 0){
                $.messager.show({
                    title:'提示消息',
                    msg:' 有<span style="color:red">'+ length+'</span>个新的布控结果,' + '<a class="checkFailTask" onclick="$(\'#surveillance_management\').click()">请注意查看</a>！',
                    height:'auto',
                    timeout:5000
                })
			}
		});
    }

    //检查失败任务个数
    function checkFailTask() {
        var id = $.parseJSON(getCookie('lastFailTask'));
        $.post('/faillog/task.action', {'id': id}, function (d) {
            var list = d.rows;
            if (!list || list.length <= 0) {
                return;
            }
            if (list.length > 3) {
                $.messager.show({
                    title: '警告',
                    height: 'auto',
                    msg: '有包括 <span style="color:red">' + list[0].taskName + '</span> 等多个任务失败了，请注意<a class="checkFailTask" onclick="$(\'#task_management\').click()">查看</a>！',
                    timeout: 5000
                });
            } else {
                var tempH = 0;
                for (var i = 0; i < list.length; i++) {
                    var task = list[i];
                    $.messager.show({
                        title: '警告',
                        height: 'auto',
                        style: {
                            left: '',
                            top: '',
                            right: 0,
                            bottom: tempH
                        },
                        msg: '任务 <span style="color:red">' + task.taskName + '</span> 失败了，失败原因：<span style="color:red">' + task.description + '</span>，请注意<a class="checkFailTask" onclick="$(\'#task_management\').click()">查看</a>！',
                        timeout: 5000
                    });
                    tempH += 118;
                }
            }

            setCookie('lastFailTask', list[0].id);
        });
    }

    //设置cookie
    function setCookie(name, val) {
        var day = 30,//保存30天
            date = new Date();
        date.setTime(date.getTime() + day * 24 * 60 * 60 * 1000);
        document.cookie = name + '=' + val + ';expires=' + date.toString();
    }

    //通过key获取cookie
    function getCookie(name) {
        var resultVal = null,
            cookie = document.cookie,
            array = cookie.split(';');
        for (var i in array) {
            if(typeof array[i] == "string") {
                if (array[i].indexOf('=') > 0) {
                    var kv = array[i].split('=');
                    if ($.trim(kv[0]) == name) {
                        var val = kv[1];
                        if (!isNaN(val)) {
                            resultVal = kv[1];
                        }
                    }
                }
            }else {
                resultVal = -1;
            }
        }
        return resultVal;
    }

    //检测浏览器是否安装flash插件以及版本
    function checkFlash(){
        var hasFlash = true;
        var flashVersion = 0;
        if(window.ActiveXObject){
            try {
                var swf = new ActiveXObject('ShockwaveFlash.ShockwaveFlash');
                if(swf){
                    hasFlash = true;
                    VSwf=swf.GetVariable("$version");
                    flashVersion=parseInt(VSwf.split(" ")[1].split(",")[0]);
                }
            } catch(e) {
                hasFlash = false;
            }

        }else{
            try {
                var swf = navigator.plugins["Shockwave Flash"];
            } catch(e) {
                //console.log(e)
            }
        }
        return {f:hasFlash,v:flashVersion};
    }

    var hasFlash = checkFlash();
    if(!hasFlash.f){
        $.messager.alert("提示", '您没有安装flash,请先安装flash插件!');
        return;
    }

})(jQuery);
