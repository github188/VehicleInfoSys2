<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@    taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://caselib.com/caselibtaglib" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=9">
    <link rel="stylesheet" type="text/css" href="../css/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="../css/icon.css"/>
    <link rel="stylesheet" type="text/css" href="../css/common.css"/>
    <link rel="stylesheet" type="text/css" href="../css/multizoom.css"/>

    <script type="text/javascript" src="../js/vendor/jquery.min.js"></script>
    <script type="text/javascript" src="../js/lib/multizoom.js"></script>
    <script type="text/javascript" src="../js/vendor/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/lib/jquery.mousewheel.min.js"></script>
    <script type="text/javascript" src="../js/vendor/jquery.form.js"></script>
    <script type="text/javascript" src="../js/vendor/easyui-lang-zh_CN.js"></script>

    <!-- webuploader start -->
    <link rel="stylesheet" type="text/css" href="../css/webuploader.css">
    <script type="text/javascript" src="../js/lib/webuploader.js"></script>

    <!-- webuploader end -->
    <script type="text/javascript" src="../js/lib/raphael-min.js"></script>

    <!-- gis start -->
    <script type="text/javascript"> var url = '/Gis_0001/'; </script>
    <script src="/Gis_0001/maps_my.js" type="text/javascript" charset="UTF-8"></script>
    <script src="/Gis_0001/function1.js" type="text/javascript" charset="UTF-8"></script>
    <script src="/Gis_0001/interface.js" type="text/javascript" charset="UTF-8"></script>
    <script src="../js/GMap2-Config.js" type="text/javascript" charset="UTF-8"></script>
    <script src="../js/GMap2-Graphic.js" type="text/javascript" charset="UTF-8"></script>
    <script src="../js/GMap2-CommonFunction.js" type="text/javascript" charset="UTF-8"></script>
    <!-- gis end -->
    <script type="text/javascript" src="../js/util.js"></script>
    <script type="text/javascript" src="../js/lib/avalon.js"></script>
    <script type="text/javascript">
        var comboTreeData = {};
    </script>
    <style type="text/css"></style>
</head>

<body class="easyui-layout" fit=true id="mainBody">
<!-- 页头  -->
<div id='loading' style="position: absolute; z-index: 1000; top: 0px; left: 0px;
    width: 100%; height: 100%; background: #E0ECFF; text-align: center;">
    <h1 style="top: 48%; position: relative;">
        <img src="../images/loading1.GIF" alt=""/>
    </h1>
    <p style="top: 50%;position: relative;">页面加载中......</p>
</div>
<div region="north" class="head">
    <div style="background:url(../images/topbg.gif) repeat-x;">
        <div class="topleft"><a href="" target="_parent"><img src="../images/logo5.png" title="系统首页"/></a></div>
        <%--<div class="head-navigate" style="left:240px; float:left; margin-bottom:4px;max-width:535px; ">

            <p:privilege privilege="result">
                <c:if test="${!menu.menu1}">
                    <a href="javascript:void(0)" id="navigate_result" class="easyui-menubutton" data-options="iconCls:'icon-zonghe'">综合查询</a>
                </c:if>
            </p:privilege>

            <c:if test="${!menu.menu2}">
                <a href="javascript:void(0)" id="surveillance_management" class="easyui-menubutton" data-options="iconCls:'icon-bk'">实时布控</a>
            </c:if>

            <p:privilege privilege="serachCarByImage">
                <c:if test="${!menu.menu3}">
                    <a href="javascript:void(0)" id="serachCarByImage_management" class="easyui-menubutton" data-options="iconCls:'icon-sc'">以图搜车</a>
                </c:if>
            </p:privilege>

            <c:if test="${!menu.menu4}">
                <a href="javascript:void(0)" data-options="menu:'#mm1',iconCls:'icon-jzf'" class="easyui-menubutton">技战法</a>
            </c:if>
            <c:if test="${!menu.menu5}">
            <a href="javascript:void(0)" data-options="menu:'#mm2',iconCls:'icon-xt'" class="easyui-menubutton">系统管理</a></div>
        </c:if>


        <c:if test="${!menu.menu4}">
            <div id="mm1" style="width:150px;">

                <p:privilege privilege="fakeLicensedCar">
                    <c:if test="${!menu.menu401}">
                        <div data-options="iconCls:'icon-jtp'"><a href="javascript:void(0)" id="fakeLicensedCar_management">假套牌车分析</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="unlicensedcar">
                    <c:if test="${!menu.menu402}">
                        <div data-options="iconCls:'icon-wpc'"><a href="javascript:void(0)" id="unlicensedCar_management">无牌车分析</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="travelTogetherVehicle">
                    <c:if test="${!menu.menu403}">
                        <div data-options="iconCls:'icon-txc'"><a href="javascript:void(0)" id="travelTogetherVehicle">同行车分析</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="firstIntoCityAnalyse">
                    <c:if test="${!menu.menu404}">
                        <div data-options="iconCls:'icon-shouci'"><a href="javascript:void(0)" id="firstIntoCityAnalyse">首次入城分析</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="frequentlyPassVehicle">
                    <c:if test="${!menu.menu405}">
                        <div data-options="iconCls:'icon-pf'"><a href="javascript:void(0)" id="frequentlyPassVehicle">频繁过车分析</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="frequentNocturnalVehicle">
                    <c:if test="${!menu.menu406}">
                        <div data-options="iconCls:'icon-yechu'"><a href="javascript:void(0)" id="frequentNocturnalVehicle">频繁夜出车辆分析</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="aerialMammalVehicle">
                    <c:if test="${!menu.menu407}">
                        <div data-options="iconCls:'icon-zhouf'"><a href="javascript:void(0)" id="aerialMammalVehicle">昼伏夜出车辆分析</a></div>
                    </c:if>
                </p:privilege>

            </div>
        </c:if>
        <c:if test="${!menu.menu5}">
            <div id="mm2" style="width:150px;">

                <p:privilege privilege="task">
                    <c:if test="${!menu.menu501}">
                        <div data-options="iconCls:'icon-rw'"><a href="javascript:void(0)" id="task_management">任务管理</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="serverstatus">
                    <c:if test="${!menu.menu502}">
                        <div data-options="iconCls:'icon-jk'"><a href="javascript:void(0)" id="serverstatus_control">服务器监控</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="user">
                    <c:if test="${!menu.menu503}">
                        <div data-options="iconCls:'icon-man1'"><a href="javascript:void(0)" id="user_management">用户管理</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="area">
                    <c:if test="${!menu.menu504}">
                        <div data-options="iconCls:'icon-camargroup'"><a href="javascript:void(0)" id="area_management">行政区划管理</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="collectPictures">
                    <c:if test="${!menu.menu505}">
                        <div data-options="iconCls:'icon-collectpictrue'"><a href="javascript:void(0)" id="collectPictures_management">实时图片源采集管理</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="dept">
                    <c:if test="${!menu.menu506}">
                        <div data-options="iconCls:'icon-camargroup'"><a href="javascript:void(0)" id="department_management">部门管理</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="role">
                    <c:if test="${!menu.menu507}">
                        <div data-options="iconCls:'icon-camargroup'"><a href="javascript:void(0)" id="role_management">角色管理</a></div>
                    </c:if>
                </p:privilege>

                <p:privilege privilege="logger">
                    <c:if test="${!menu.menu508}">
                        <div data-options="iconCls:'icon-logmanage'"><a href="javascript:void(0)" id="log_management">日志管理</a></div>
                    </c:if>
                </p:privilege>

            </div>
        </c:if>
        <SCRIPT>
            $('#mm1').menu({
                onClick: function (item) {
                    //...
                }
            });
            $('#mm2').menu({
                onClick: function (item) {
                    //...
                }
            });

        </SCRIPT>--%>
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