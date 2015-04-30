<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title><spring:message code="main.title" /></title>
		<meta http-equiv="X-UA-Compatible" content="IE=9">
		<link rel="stylesheet" type="text/css" href="css/easyui.css" />
		<link rel="stylesheet" type="text/css" href="css/icon.css" />
		<link rel="stylesheet" type="text/css" href="css/common.css" />

		<script type="text/javascript" src="js/vendor/jquery.min.js"></script>
		<script type="text/javascript" src="js/vendor/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="js/vendor/easyui-lang-zh_CN.js"></script>
  
		<!-- webuploader start -->
        <link rel="stylesheet" type="text/css" href="/css/webuploader.css">
        <script type="text/javascript" src="/js/lib/webuploader.js"></script>
		<!-- webuploader end -->
        <script type="text/javascript" src="/js/lib/raphael-min.js"></script>
  
		<!-- gis start -->
		<script type="text/javascript"> var url = '/Gis_0001/'; </script>
		<script src="/Gis_0001/maps_my.js"  type="text/javascript" charset="UTF-8"></script>
		<script src="/Gis_0001/function1.js"  type="text/javascript" charset="UTF-8"></script>
		<script src="/Gis_0001/interface.js"  type="text/javascript" charset="UTF-8"></script>

		<script src="js/GMap2-Config.js" type="text/javascript" charset="UTF-8"></script>
		<script src="js/GMap2-Graphic.js" type="text/javascript" charset="UTF-8"></script>
		<script src="js/GMap2-CommonFunction.js" type="text/javascript" charset="UTF-8"></script>
		<!-- gis end -->
		<script type="text/javascript" src="js/util.js"></script>
	</head>
	<body class="easyui-layout" fit=true>
		<!-- 页头  -->
		<div region="north" class="head">
			<div class="title">
				<img src="${pageContext.request.contextPath}images/logo.png"
				width="65px" height="65px" alt="公司logo">
				<div class="text">
					<span><spring:message code="main.title" /> </span>
				</div>
				<div class="text_en">
					<span><spring:message code="main.title.small" /> </span>
				</div>
			</div>
			<div class="head-ocx">
				<a href="/3nd/VSOCX_Setup.exe">控件下载</a>
			</div>
			<div class="head-navigate">
				<a href="javascript:void(0)" id="serverstatus_control" class="easyui-linkbutton" data-options="iconCls:'icon-search'">服务器状态监控</a>
				<a href="javascript:void(0)" id="navigate_result" class="easyui-linkbutton" data-options="iconCls:'icon-search'">识别结果查询</a>
				<a href="javascript:void(0)" id="task_management" class="easyui-linkbutton" data-options="iconCls:'icon-search'">任务管理</a>
				<c:if test="${user.id==1}">
					<a href="javascript:void(0)" id="user_management" class="easyui-linkbutton" data-options="iconCls:'icon-man'">用户管理</a>
				</c:if>
                <span>${user.loginName}</span>
                <a id="changePwd" class="changePwd" href="javascript:void(0)">修改密码</a>
                <a href="/logout.action">退出</a>
			</div>
             <div id="win_changePwd" style="display:none" style="padding:10px">
	             <form>
	             	 <input id="changePwd_userId" type="hidden" name='userId' />
		             <label>旧&ensp;密&ensp;码:<input class="easyui-textbox" data-options="required:true,validType:{length:[5,20],checkPwd_remote:['/validPwd.action','pwd','#changePwd_userId']}" style="width:100px" type="password" name="pwd"></label><br>
		             <label>新&ensp;密&ensp;码:<input id="newPwd" class="easyui-textbox" data-options="required:true,validType:['length[5,20]']" style="width:100px" type="password" name="newPwd"></label><br>
		             <label>重复密码:<input id="newAgain" class="easyui-textbox" data-options="required:true,validType:{equals:'#newPwd'}"   style="width:100px" type="password" name="newAgain"></label>
	             </form>
             </div>
		</div>
		<!-- 主要部分 -->
		<div region="center">
			<div id="main_tabs" class="easyui-tabs result_details_tabs" fit=true data-options="selected:0">
				<jsp:include page="map/map.jsp"></jsp:include>
			</div>
		</div>
		<jsp:include page="camera/drawLineDialog.jsp"></jsp:include>
		<jsp:include page="resource/uploadDialog.jsp"></jsp:include>
		<jsp:include page="result/videoDialog.jsp"></jsp:include>
		<script type="text/javascript" >var serverIp='${serverIp}';</script>
		<script type="text/javascript" src="/js/home.js"></script>
		<script type="text/javascript">
		
        (function(){
			//禁止密码输入框的复制 粘贴 剪切
			$("input:password").bind("copy cut paste",function(e){
	            return false;
	         })
        })()
		</script>
	</body>
</html>
