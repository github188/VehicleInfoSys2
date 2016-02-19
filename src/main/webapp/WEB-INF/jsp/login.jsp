<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="zh-cmn-Hans">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="description" content="Metro, a sleek, intuitive, and powerful framework for faster and easier web development for Windows Metro Style.">
    <meta name="keywords" content="HTML, CSS, JS, JavaScript, framework, metro, front-end, frontend, web development">
    <meta name="author" content="Sergey Pimenov and Metro UI CSS contributors">

    <link rel='shortcut icon' type='image/x-icon' href='images/metro_ui_image/favicon.ico' />
    <title>车辆侦查系统登录</title>

    <link href="css/metro_ui_css/metro.css" rel="stylesheet">
    <link href="css/metro_ui_css/metro-icons.css" rel="stylesheet">

    <script src="js/metro_ui_js/jquery-2.1.3.min.js"></script>
    <script src="js/metro_ui_js/metro.js"></script>
    <script src='js/lib/jQuery.md5.js'></script>
    
    <link rel="stylesheet" type="text/css" href="css/easyui.css">
    <script src='js/lib/jquery.easyui.min.js'></script>
     
    <%
	  response.setHeader("Pragma","No-cache");  
	  response.setHeader("Cache-Control","No-cache");  
	  response.setDateHeader("Expires", -1);  
	  response.setHeader("Cache-Control", "No-store"); 
    %>       
    
    <style>
        .tile-area-controls {
            position: fixed;
            right: 40px;
            top: 40px; 
        }

        .tile-group {
            left: 100px;
        }

        .tile, .tile-small, .tile-sqaure, .tile-wide, .tile-large, .tile-big, .tile-super {
            opacity: 0;
            -webkit-transform: scale(.8);
            transform: scale(.8);
        }

        #charmSettings .button {
            margin: 5px;
        }

        .schemeButtons {
            /*width: 300px;*/
        }

        @media screen and (max-width: 640px) {
            .tile-area {
                overflow-y: scroll;
            }
            .tile-area-controls {
                display: none;
            }
        }

        @media screen and (max-width: 320px) {
            .tile-area {
                overflow-y: scroll;
            }

            .tile-area-controls {
                display: none;
            }

        }
    </style>
	<script>
	 function showCharm(id){
         var  charm = $(id).data("charm");
         if (charm.element.data("opened") === true) {
             charm.close();
         } else {
             charm.open();
         }
     }
	 
	 function closeCharm(id){
		 var charm = $(id).data("charm");
		 if(charm.element.data("opened") === true){
			 charm.close(); 
		 }
		 
	 }
	 
	</script>
    <script>
        (function($) {
            $.StartScreen = function(){
                var plugin = this;
                var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;

                plugin.init = function(){
                    setTilesAreaSize();
                    if (width > 640) addMouseWheel();
                };

                var setTilesAreaSize = function(){
                    var groups = $(".tile-group");
                    var tileAreaWidth = 80;
                    $.each(groups, function(i, t){
                        if (width <= 640) {
                            tileAreaWidth = width;
                        } else {
                            tileAreaWidth += $(t).outerWidth() + 80;
                        }
                    });
                    $(".tile-area").css({
                        width: tileAreaWidth
                    });
                };

                var addMouseWheel = function (){
                    $("body").mousewheel(function(event, delta, deltaX, deltaY){
                        var page = $(document);
                        var scroll_value = delta * 50;
                        page.scrollLeft(page.scrollLeft() - scroll_value);
                        return false;
                    });
                };

                plugin.init();
            }
        })(jQuery);

        $(function(){
            $.StartScreen();

            var tiles = $(".tile, .tile-small, .tile-sqaure, .tile-wide, .tile-large, .tile-big, .tile-super");

            $.each(tiles, function(){
                var tile = $(this);
                setTimeout(function(){
                    tile.css({
                        opacity: 1,
                        "-webkit-transform": "scale(1)",
                        "transform": "scale(1)",
                        "-webkit-transition": ".3s",
                        "transition": ".3s"
                    });
                }, Math.floor(Math.random()*500));
            });

            $(".tile-group").animate({
                left: 0
            });
        });

        function showCharms(id){
            var  charm = $(id).data("charm");
            if (charm.element.data("opened") === true) {
                charm.close();
            } else {
                charm.open();
            }
        }

        function setSearchPlace(el){
            var a = $(el);
            var text = a.text();
            var toggle = a.parents('label').children('.dropdown-toggle');

            toggle.text(text);
        }

        $(function(){
            var current_tile_area_scheme = localStorage.getItem('tile-area-scheme') || "tile-area-scheme-dark";
            $(".tile-area").removeClass (function (index, css) {
                return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
            }).addClass(current_tile_area_scheme);

            $(".schemeButtons .button").hover(
                    function(){
                        var b = $(this);
                        var scheme = "tile-area-scheme-" +  b.data('scheme');
                        $(".tile-area").removeClass (function (index, css) {
                            return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                        }).addClass(scheme);
                    },
                    function(){
                        $(".tile-area").removeClass (function (index, css) {
                            return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                        }).addClass(current_tile_area_scheme);
                    }
            );

            $(".schemeButtons .button").on("click", function(){
                var b = $(this);
                var scheme = "tile-area-scheme-" +  b.data('scheme');

                $(".tile-area").removeClass (function (index, css) {
                    return (css.match (/(^|\s)tile-area-scheme-\S+/g) || []).join(' ');
                }).addClass(scheme);

                current_tile_area_scheme = scheme;
                localStorage.setItem('tile-area-scheme', scheme);

                showSettings();
            });
        });

    </script>

</head>
<body style="overflow-y: hidden;">
    <div data-role="charm" id="charmSearch">
         <form action="login.action" method="post">
            <input type="hidden" name="rndStr" id="rndStr" value="${rndStr}">
            <h1 class="text-light">Login to service</h1>
            <hr class="thin"/>
            <br/>
            <div class="input-control text full-size" data-role="input">
                <label for="user_login">&nbsp;用户名:</label>
                <input type="text" name="loginName" id="username">
                <button class="button helper-button clear"><span class="mif-cross"></span></button>
            </div>
            <br/>
            <br/>
            <div class="input-control password full-size" data-role="input">
                <label for="user_password">&nbsp;密&nbsp;&nbsp;码:</label>
                <input type="password" name="pwd" id="pwd">
                <button class="button helper-button reveal"><span class="mif-looks"></span></button>
            </div>
            <br/>
            <br/>
            <div class="form-actions">
                <button type="submit" class="button primary">登录</button>
                <button type="button" class="button link" onclick="closeCharm('#charmSearch')">取消</button>
            </div>
        </form>
    </div>
    
<!--     <div data-role="dialog" id="dialog1" class="padding20" data-close-button="true" data-type="alert">
            <h1>登录失败</h1>
            <p>
                	该用户未启用！
            </p>
    </div> -->
    
    <div data-role="charm" id="charmSettings" data-position="top">
        <h1 class="text-light">Settings</h1>
        <hr class="thin"/>
        <br />
        <div class="schemeButtons">
            <div class="button square-button tile-area-scheme-dark" data-scheme="dark"></div>
            <div class="button square-button tile-area-scheme-darkBrown" data-scheme="darkBrown"></div>
            <div class="button square-button tile-area-scheme-darkCrimson" data-scheme="darkCrimson"></div>
            <div class="button square-button tile-area-scheme-darkViolet" data-scheme="darkViolet"></div>
            <div class="button square-button tile-area-scheme-darkMagenta" data-scheme="darkMagenta"></div>
            <div class="button square-button tile-area-scheme-darkCyan" data-scheme="darkCyan"></div>
            <div class="button square-button tile-area-scheme-darkCobalt" data-scheme="darkCobalt"></div>
            <div class="button square-button tile-area-scheme-darkTeal" data-scheme="darkTeal"></div>
            <div class="button square-button tile-area-scheme-darkEmerald" data-scheme="darkEmerald"></div>
            <div class="button square-button tile-area-scheme-darkGreen" data-scheme="darkGreen"></div>
            <div class="button square-button tile-area-scheme-darkOrange" data-scheme="darkOrange"></div>
            <div class="button square-button tile-area-scheme-darkRed" data-scheme="darkRed"></div>
            <div class="button square-button tile-area-scheme-darkPink" data-scheme="darkPink"></div>
            <div class="button square-button tile-area-scheme-darkIndigo" data-scheme="darkIndigo"></div>
            <div class="button square-button tile-area-scheme-darkBlue" data-scheme="darkBlue"></div>
            <div class="button square-button tile-area-scheme-lightBlue" data-scheme="lightBlue"></div>
            <div class="button square-button tile-area-scheme-lightTeal" data-scheme="lightTeal"></div>
            <div class="button square-button tile-area-scheme-lightOlive" data-scheme="lightOlive"></div>
            <div class="button square-button tile-area-scheme-lightOrange" data-scheme="lightOrange"></div>
            <div class="button square-button tile-area-scheme-lightPink" data-scheme="lightPink"></div>
            <div class="button square-button tile-area-scheme-grayed" data-scheme="grayed"></div>
        </div>
    </div>

    <div class="tile-area tile-area-scheme-dark fg-white" style="height: 100%; max-height: 100% !important;">
        <h1 class="tile-area-title">Start</h1>
        <div class="tile-area-controls">                  
            <button class="square-button bg-transparent fg-white bg-hover-dark no-border" onclick="showCharms('#charmSettings')"><span class="mif-cog"></span></button>          
        </div>

        <div class="tile-group double">
            <span class="tile-group-title">通用</span>

            <div class="tile-container">

                <div class="tile bg-indigo fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                       <span class="icon mif-windows"></span>
                    </div>
                    <span class="tile-label">登录</span>
                </div>

                <div class="tile bg-darkBlue fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <span class="icon mif-envelop"></span>
                    </div>
                    <span class="tile-label">Inbox</span>
                </div>

                <div class="tile-large bg-steel fg-white" data-role="tile">                                   
                    <div class="tile-content slide-up">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/2.jpg" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over op-cyan text-big padding10">
	                        	深圳久凌软件技术有限公司是专门从事智能监控产品研发、生产、销售为一体的高新技术企业，位于深圳市福田国际电子商务产业园2栋202，在香港设有研发中心，在北京设有办事处。
	                    </div>
	                    <div class="tile-label">介绍</div>
                    </div>                
                </div>
            </div>
        </div>

        <div class="tile-group double">
            <span class="tile-group-title">以图搜车</span>
            <div class="tile-container">
                <div class="tile-wide" data-role="tile" data-effect="slideLeft">
                    <div class="tile-content">
                        <a href="javascript:void(0);" class="live-slide"><img src="images/metro_ui_image/1.jpg" data-role="fitImage" data-format="fill"></a>
                        <a href="javascript:void(0);" class="live-slide"><img src="images/metro_ui_image/2.jpg" data-role="fitImage" data-format="fill"></a>
                        <a href="javascript:void(0);" class="live-slide"><img src="images/metro_ui_image/3.jpg" data-role="fitImage" data-format="fill"></a>
                        <a href="javascript:void(0);" class="live-slide"><img src="images/metro_ui_image/4.jpg" data-role="fitImage" data-format="fill"></a>
                        <a href="javascript:void(0);" class="live-slide"><img src="images/metro_ui_image/5.jpg" data-role="fitImage" data-format="fill"></a>
                    </div>
                    <div class="tile-label">画廊</div>
                </div>
                <div class="tile-wide fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down-2">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/5.jpg" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10">
	                        	只需添加一张图片,系统自动识别出车牌、车型、车辆品牌等,根据特征区域搜车.
	                    </div>
	                    <div class="tile-label">自动搜车</div>
	                </div>
               </div>
               <div class="tile fg-white bg-transparent " data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-left-2 ">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_shoudongsouche_14.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10 ">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">手动搜车</div>
	                </div>
            	</div>               
                <div class="tile fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_soucherenwuguanli_13.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over op-green text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">搜车任务管理</div>
	                </div>
            	</div>                        
            </div>
        </div>

        <div class="tile-group double">
            <span class="tile-group-title">布控缉查</span>
            <div class="tile-wide fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                <div class="tile-content slide-up-2">
                    <div class="slide">
                        <img src="images/metro_ui_image/bukongshenqing.png" data-role="fitImage" data-format="fill">
                    </div>
                    <div class="slide-over bg-orange text-big padding10">
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                    </div>
                    <div class="tile-label">布控申请</div>
            </div>
            </div>                            
                <div class="tile fg-white bg-transparent " data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down-2 ">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_bukongguanli_20.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10 ">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">布控管理</div>
	                </div>
            	</div>               
                <div class="tile fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_bukongshenhe.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over op-green text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">布控审核</div>
	                </div>
            	</div>
            	<div class="tile fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-left-2">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_chekongshenhe.png" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over op-red text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">撤控审核</div>
	                </div>
            	</div>                            
                <div class="tile fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_bukongchaxun.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over op-green text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">布控查询</div>
	                </div>
            	</div>               
        </div> 
        
        <div class="tile-group double">
            <span class="tile-group-title">大数据研判</span>
            <div class="tile-container">
               
               <div class="tile-wide fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-left">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/5.jpg" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over op-cyan text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">假套牌车分析</div>
	                </div>
            	</div>
                                                          
                <div class="tile fg-white bg-transparent " data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down-2 ">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_wupaichefenxi.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10 ">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">无牌车分析</div>
	                </div>
            	</div>
                
               <div class="tile fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-left-2">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_shouchirenchenfenxi_35.png" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over op-cyan text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">首次入城分析</div>
	                </div>
               </div>
               
            	<div class="tile-wide fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-right">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/5.jpg" data-role="fitImage" data-format="hd">
	                    </div>
	                    <div class="slide-over op-green text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">同行车分析</div>
	                </div>
            	</div>

            </div>
        </div>      

        <div class="tile-group double">
            <span class="tile-group-title"> </span>
            <div class="tile-container">
                <div class="tile-wide fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')"> 
	                <div class="tile-content slide-down-2">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/5.jpg" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10">
	                        	只需添加一张图片,系统自动识别出车牌、车型、车辆品牌等,根据特征区域搜车.
	                    </div>
	                    <div class="tile-label">频繁过车分析</div>
	                </div>
               </div>
               <div class="tile-wide fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down-2">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/5.jpg" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10">
	                       	 只需添加一张图片,系统自动识别出车牌、车型、车辆品牌等,根据特征区域搜车.
	                    </div>
	                    <div class="tile-label">频繁夜出车辆分析</div>
	                </div>
               </div>
               <div class="tile-wide fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down-2">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/5.jpg" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10">
	                        	只需添加一张图片,系统自动识别出车牌、车型、车辆品牌等,根据特征区域搜车.
	                    </div>
	                    <div class="tile-label">昼伏夜出车辆分析</div>
	                </div>
               </div>
                
                
            </div>
        </div>
        
               <div class="tile-group double">
            <span class="tile-group-title">综合应用</span>
            <div class="tile-container">               
                <div class="tile fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-left-2">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_zonghe.png" data-role="fitImage" data-format="fill">
	                    </div>
	                    <div class="slide-over op-red text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">综合查询</div>
	                </div>
            	</div>                            
                <div class="tile fg-white bg-transparent " data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down-2 ">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_jiankongdian_03.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over bg-green text-big padding10 ">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">监控点管理</div>
	                </div>
            	</div>               
                <div class="tile fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
	                <div class="tile-content slide-down">
	                    <div class="slide">
	                        <img src="images/metro_ui_image/icon_renwu_09.png" data-role="fitImage" data-format="square">
	                    </div>
	                    <div class="slide-over op-green text-big padding10">
	                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
	                    </div>
	                    <div class="tile-label">任务管理</div>
	                </div>
            	</div>
                
                <div class="tile bg-darkBlue fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <img src="images/metro_ui_image/halo.jpg" data-role="fitImage" data-overlay="实时视频流无间断识别"  data-type="handing-ani">
                    <div class="tile-label">实时视频源识别</div>
                </div>
                <div class="tile bg-darkBlue fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <img src="images/metro_ui_image/clear-night.jpg" data-role="fitImage" data-overlay="实时图片流无间断识别"  data-type="handing-ani">
                    <div class="tile-label">实时图片源识别</div>
                </div>
                <div class="tile bg-darkBlue fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <img src="images/metro_ui_image/rain.jpg" data-role="fitImage" data-overlay="离线视频或图片二次识别"  data-type="handing-ani">
                    <div class="tile-label">离线源识别</div>
                </div>
            </div>
        </div>
        
        <div class="tile-group double">
            <span class="tile-group-title">系统管理</span>
            <div class="tile-container">
                <div class="tile bg-teal fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <img src="images/metro_ui_image/icon_bumen_54.png" data-role="fitImage" data-format="square">
                    </div>
                    <span class="tile-label">部门管理</span>
                </div>
                <div class="tile bg-darkGreen fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <img src="images/metro_ui_image/icon_juese_61.png" data-role="fitImage" data-format="square">
                    </div>
                    <span class="tile-label">角色管理</span>
                </div>
                <div class="tile bg-cyan fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <img src="images/metro_ui_image/icon_yonghu_62.png" data-role="fitImage" data-format="square">
                    </div>
                    <div class="tile-label">用户管理</div>
                </div>
                <div class="tile bg-darkBlue fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <img src="images/metro_ui_image/icon_xingzhengqu_56.png" data-role="fitImage" data-format="square">
                    </div>
                    <span class="tile-label">行政区划管理</span>
                </div>
                  <div class="tile bg-teal fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <img src="images/metro_ui_image/icon_shishitupian_65.png" data-role="fitImage" data-format="square">
                    </div>
                    <span class="tile-label">实时图片采集管理</span>
                </div>
                <div class="tile bg-darkGreen fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <img src="images/metro_ui_image/icon_fuwuqi_63.png" data-role="fitImage" data-format="square">
                    </div>
                    <span class="tile-label">服务器监控</span>
                </div>
            </div>
        </div>

        <div class="tile-group one">
            <span class="tile-group-title"></span>
            <div class="tile-container">            
                <div class="tile bg-cyan fg-white" data-role="tile" data-on-click="showCharms('#charmSearch')">
                    <div class="tile-content iconic">
                        <img src="images/metro_ui_image/icon_rizhi_59.png" data-role="fitImage" data-format="square">
                    </div>
                    <div class="tile-label">日志管理</div>
                </div>
            </div>
        </div>
        
    </div>
</body>
<script>
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
	    var loginName = $.trim($("#username").val()),
		pwd = $("#pwd").val(),
		rndStr=$("#rndStr").val();
	 	pwd=$.md5(pwd+rndStr)
	 	
			$.post("login.action", {loginName:loginName,pwd:pwd,rndStr:rndStr}, function (data) {
				if (data == "notExist") {
						location.href = '/'
				} else if(data == "notStartUsing") {
					$.messager.alert("登录失败", '该用户未启用!');
				}else if(data == "exist") {
					$.messager.confirm('用户登录','该用户已在其它终端上登录,是否强行将之退出?',function(param) {
						if(param == true) {
						    var username = $.trim($("#username").val());
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
</html>