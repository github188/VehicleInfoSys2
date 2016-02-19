<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<script type="text/javascript">var serverIp = '${serverIp}';</script>
<script type="text/javascript" src="../js/home.js"></script>
<script type="text/javascript">
    function loginOut(){
        $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function (r) {
            if (r) {
                location.href = '/logout.action';
            }
        });
    }
    (function ($) {
        //检查识别数信息，达到上限则提示警告信息
        var checkWarning = function () {
            var warningLabel = $('#warningLabel');
            if(warningLabel.length == 1){
                return;
            }
            setTimeout(function () {
                //查看识别模块已达上限，
                $.post('/recognum/checkRecognum.action', function (msg) {
                    if('success' == msg){ //没有达上限
                        checkWarning();
                    }else if ('failed' == msg) { //已达上限
                        var txt1="<div style='position: absolute;top: 60px;right: 10px;'><label id='warningLabel' style='margin-left: 100px;font-size:14px;color: #CC2222'>今日识别量已达上限.</label></div>";
                        $('#mainBody').append(txt1);
                    }
                });
            }, 10000)
        }
        checkWarning();

        //禁止密码输入框的复制 粘贴 剪切
        $("input:password").bind("copy cut paste", function (e) {
            return false;
        });
        // IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readOnly]").keydown(function (e) {
            e.preventDefault();
        });
        //处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
        // http://volunteer521.iteye.com/blog/830522/
        function banBackSpace(e) {
            var ev = e || window.event;//获取event对象
            var obj = ev.target || ev.srcElement;//获取事件源

            var t = obj.type || obj.getAttribute('type');//获取事件源类型

            //获取作为判断条件的事件类型
            var vReadOnly = obj.getAttribute('readonly');
            var vEnabled = obj.getAttribute('enabled');
            //处理null值情况
            vReadOnly = (vReadOnly == null) ? false : vReadOnly;
            vEnabled = (vEnabled == null) ? true : vEnabled;

            //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
            //并且readonly属性为true或enabled属性为false的，则退格键失效
            var flag1 = (ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vEnabled != true)) ? true : false;

            //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
            var flag2 = (ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") ? true : false;

            //判断
            if (flag2) {
                return false;
            }
            if (flag1) {
                return false;
            }
        }

//禁止后退键 作用于Firefox、Opera
        document.onkeypress = banBackSpace;
//禁止后退键  作用于IE、Chrome
        document.onkeydown = banBackSpace;
        function show(){
            $("#loading").fadeOut("normal", function(){
                $(this).remove();
            });
        }
        var delayTime;
        $.parser.onComplete = function(){
            if(delayTime)
                clearTimeout(delayTime);
            delayTime = setTimeout(show,500);
        }
        window.monitorFlage = false;
    })(jQuery)
</script>
</body>
</html>
