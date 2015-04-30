<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
 <head>
  <title>用户登录</title>
  <%
  response.setHeader("Pragma","No-cache");  
  response.setHeader("Cache-Control","No-cache");  
  response.setDateHeader("Expires", -1);  
  response.setHeader("Cache-Control", "No-store"); 
  %>
  <link rel="stylesheet" type="text/css" href="css/easyui.css">
<script src='js/lib/jquery.min.js'></script>
<script src='js/lib/jquery.easyui.min.js'></script>
<script src='js/lib/jQuery.md5.js'></script>
  <style type="text/css">
html {
	height: 100%;
	margin: 0;
	padding: 0;
}

body {
	height: 100%;
	margin: 0;
	padding: 0;
}

.floater {
	height: 50%;
	margin-bottom: -234px;
}

.login_center {
	width: 100%;
	height: 468px;
	position: relative;
}

.main {
	background-image: url('images/panel.jpg');
	background-repeat: no-repeat;
	width: 808px;
	height: 468px;
}

table {
	height: 70px;
	width: 70%;
	float: left;
	margin-top: 400px;
	margin-left: 100px;
}

.text_input {
	background-color: #fff;
	border: 1px solid #b5b8c8;
}

.clear {
	clear: both;
}
</style>
 </head>
 <body>
  <div class="floater"></div>
  <div align="center" class="login_center">
   <div class="login_div">
    <div class="main">
     <form action="login.action" method="post">
     <input type="hidden" name="rndStr" id="rndStr" value="${rndStr}">
      <table>
       <tr>
        <td>
        账号:
        </td>
        <td>
         <input type="text" id="username" name="loginName" class="text_input"/>
        </td>
        <td>
         密码:
        </td>
        <td>
         <input type="password" id="pwd" name="pwd" class="text_input"/>
        </td>
        <td>
         <input type="submit" value="登录" class="button" />
        </td>
       </tr>
      </table>
     </form>
    </div>
    <div class="clear"></div>
   </div>
  </div>
  <script type="text/javascript">
  (function () {
		//process login timeout problem
		var loginPage = '/login.action';
		if (top.location.pathname != loginPage) {
			top.location.href = loginPage;
		}

		//禁止密码输入框的复制 粘贴 剪切
		$("input:password").bind("copy cut paste",function(e){
            return false;
         })
		
		var form = $('form');
		
		$.messager.defaults = {ok : '确定', cancel : '取消'};
		form.submit(function (e) {
			e.preventDefault();
			if (form.form("validate")) {
		var loginName = $("#username").val(),
			pwd = $("#pwd").val(),
			rndStr=$("#rndStr").val();
		 pwd=$.md5(pwd+rndStr)
				$.post("login.action", {loginName:loginName,pwd:pwd,rndStr:rndStr}, function (data) {
					if (data == "notExist") {
							location.href = '/'
					} else if(data == "exist") {
						$.messager.confirm('用户登录','该用户已在其它终端上登录,是否强行将之退出?',function(param) {
							if(param == true) {
							    var username = $("#username").val();
								$.post("forceLogin.action",{param:param,loginName:username},function(result) {
									if(result == true) {
										location.href = '/'
									}
								})
							}
						})      
					} else if((/!DOCTYPE/).test(data)){
      //加一个判断,如果发回来的是html代码,就跳到首页去
						location.href = '/'
					} else {                         
							$.messager.alert('登录失败', '登录失败!','info',function(){
									location.href='/login.action'
								})
					}
				})
			}
		})
	})();
 </script>
 </body>
</html>