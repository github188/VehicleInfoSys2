function showtext() {
    if($("#password").val()=="") {
	$("#text_input").html("<input type=\"text\" value=\"请输入您的密码\" id=\"pwd\" onfocus=\"showpassword();\" ");
    }
}

function showpassword() {
    $("#text_input").html("<input type=\"password\" value=\"\" id=\"pwd\" onblur=\"showtext();\" ");
    /**
    这里为什么要用setTimeout，因为ie比较傻，刚创建完对象，你直接设置焦点
    在ie下是不会响应的，你必须留出时间给ie缓冲下，所以加上了这个定时器
    **/
    setTimeout(function(){
	$("#password").focus();
    },20);
}

$(function(){
	var usernameDefStr = $("#username").val();
	$("#username").focus(function(){
	    if($(this).val()==usernameDefStr)
	        $(this).val("");
	});
	$("#username").blur(function(){
	    if($(this).val()=="")
	        $(this).val(usernameDefStr);
	});
	
});
