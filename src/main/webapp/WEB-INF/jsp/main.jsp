<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页</title>
    <script src="js/metro_ui_js/jquery-2.1.3.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="css/common.css"/>
    <script type="text/javascript" src="js/vendor/jquery.easyui.min.js"></script>
    <style type="text/css">
        .box {
            width: 120px;
            text-align: center;
            font-size: 14px;
            font-weight: bold;
        }

        .box img {
            width: 90px;
        }

        .headfont {
            font-size: 15px;
            font-weight: bold;
        }
    </style>
</head>
<body class="easyui-layout" fit=true style="background-color:#EDF6FA;">
<div region="north" class="head" style="height:56px">
    <div>
        <div class="topleft"><a href="" target="_parent"><img src="images/logo5.png" title="系统首页"/></a></div>
        <div class="topright">
            <ul>
                <li style="line-height:22px; color:#e1edf4; font-family:微软雅黑; font-size:12px;background:url(../images/user.png) no-repeat 2px 5px; padding-left:20px;"> ${user.loginName} </li>
                <li><a id="changePwd" class="changePwd l-btn-pwd" href="javascript:void(0)" title="修改密码"></a></li>
                <li><a href="/flash/install_flash_player_18_active_x.exe" class="l-btn-b" title="flash插件下载"></a></li>
                <li><a href="/3nd/VSOCX_Setup.exe" class="l-btn-b" title="离线源视频控件下载"></a></li>
                <li><a href="/3nd/PlayHikDvrVideoActiveX_Setup_v1.0.0.6.exe" class="l-btn-b" title="实时源视频控件下载"></a></li>
                <li><a id="showVersion" href="javascript:void(0);" class="l-btn-ver" title="查看版本号"></a></li>
                <li><a href="javaScript:loginOut();" id="loginOut" class="l-btn-c" title="退出登录"></a></li>
            </ul>
        </div>
    </div>
    <div id="win_changePwd" style="display:none;padding:10px">
        <form>
            <input id="changePwd_userId" type="hidden" name='userId'/>
            <label>旧&ensp;密&ensp;码:
                <input class="easyui-textbox"
                       data-options="required:true,validType:{length:[5,20],checkPwd_remote:['/validPwd.action','pwd','#changePwd_userId']}"
                       style="width:100px" type="password" name="pwd">
            </label>
            <br>
            <label>新&ensp;密&ensp;码:
                <input id="newPwd" class="easyui-textbox" data-options="required:true,validType:['length[5,20]']" style="width:100px" type="password"
                       name="newPwd">
            </label>
            <br>
            <label>重复密码:
                <input id="newAgain" class="easyui-textbox" data-options="required:true,validType:{equals:'#newPwd'}" style="width:100px"
                       type="password" name="newAgain">
            </label>
        </form>
    </div>
</div>

<div region="center">
    <table style="position:relative;top:20px;left:30px;background-color:#EDF6FA;" cellspacing="15px">
        <tr>
            <td>
                <div class="headfont">综合应用</div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_03.png" id="navigate_result" />

                    <div>综合查询</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_05.png" onclick="toCameraManger()" />

                    <div>监控点管理</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_08.png" id="task_management" />

                    <div>任务管理</div>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <div class="headfont">以图搜车</div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_14.png" />

                    <div>自动搜车</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_15.png" />

                    <div>手动搜车</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_13.png" />

                    <div>搜车任务管理</div>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <div class="headfont">布控缉查</div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_23.png" />

                    <div>布控申请</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_25.png" />

                    <div>布控管理</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_26.png" />

                    <div>布控审核</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_28.png" />

                    <div>撤控审核</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_20.png" />

                    <div>布控查询</div>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <div class="headfont">大数据研判</div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_38.png" />

                    <div>假套牌车分析</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_39.png" />

                    <div>无牌车分析</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_41.png" />

                    <div>同行车分析</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_43.png" />

                    <div>首次入城分析</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_45.png" />

                    <div>频繁过车分析</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_36.png" />

                    <div>频繁夜出车辆分析</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_47.png" />

                    <div>昼伏夜出车辆分析</div>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <div class="headfont">系统管理</div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_56.png" />

                    <div>部门管理</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_58.png" />

                    <div>角色管理</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_61.png" />

                    <div>用户管理</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_63.png" />

                    <div>行政区划管理</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_66.png" />

                    <div>实时图片采集管理</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_68.png" />

                    <div>服务器监控</div>
                </div>
            </td>
            <td>
                <div class="box">
                    <img src="images/metro_ui_image/icon1_59.png" />

                    <div>日志管理</div>
                </div>
            </td>
        </tr>
    </table>
</div>
</body>
<script type="text/javascript">
    $.messager.defaults = {ok: '确定', cancel: '取消'};
    function loginOut() {
        $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function (r) {
            if (r) {
                location.href = '/logout.action';
            }
        });
    }

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
        //查看版本号
            .on("click", "#showVersion", function (e) {
                $.ajax({
                    url: 'info.action',
                    type: 'post',
                    success: function (d) {
                        $.messager.alert("版本号", d);
                    }
                });
            });


    //监控点管理
    function toCameraManger() {
        window.open('/home.action');
    }

    var navigators = [
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
        }, {
            id: 'unlicensedCar_management',
            href: '/unlicensedcar/index.action'
        }, {
            id: 'serachCarByImage_management',
            href: '/serachCarByImage/index.action'
        }, {
            id: 'travelTogetherVehicle',
            href: '/travelTogetherVehicle/index.action'
        }, {
            id: 'firstIntoCityAnalyse',
            href: '/firstIntoCityAnalyse/index.action'
        }, {
            id: 'frequentlyPassVehicle',
            href: '/frequentlyPassVehicle/index.action'
        }, {
            id: 'frequentNocturnalVehicle',
            href: '/frequentNocturnalVehicle/index.action'
        }, {
            id: 'aerialMammalVehicle',
            href: '/aerialMammalVehicle/index.action'
        }, {
            id: "area_management",
            href: "/area/area.action"
        }, {
            id: "log_management",
            href: "/logger/index.action"
        }, {
            id: "collectPictures_management",
            href: "/collectPictures/index.action"
        }, {
            id: "department_management",
            href: "/dept/dept.action"
        }, {
            id: "role_management",
            href: "/role/role.action"
        }
    ];
    navigators.forEach(function (n) {
        var $el = $('#' + n.id).click(function (e) {
            e.preventDefault();
            window.open(n.href);
        })
    });


</script>
</html>