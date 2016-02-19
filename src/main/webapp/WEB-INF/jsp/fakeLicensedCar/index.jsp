<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
.j-bj {
	width:120px;
	height:30px;
	background:url(../../../css/icons/baojing1.png) no-repeat 5px center;
	background-color:#f60;
	color:#ffffff;
	-webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
	border:0;
	font-size:12px;
	font-family:'微软雅黑';
	padding-left:15px;
	
}
.j-bj:hover {
	background-color:#F30;
	
}

.j-cx {
	width:120px;
	height:30px;
	background:url(../../../css/icons/49.png) no-repeat 5px center;
	background-color:#04aeda;
	color:#ffffff;
	-webkit-border-radius: 4px;
    -moz-border-radius: 4px;
    border-radius: 4px;
	border:0;
	font-size:12px;
	font-family:'微软雅黑';
	padding-left:15px;
}
.j-cx:hover {
	background-color:#06F;
}
</style>
<div id="fakeLicenseedCarLayout" class="easyui-layout" style="width:100%;height:100%;">
  <div data-options="region:'west',title:'导航',split:true" style="width:10%;height:100%">
    <div style="margin:10px" >
      <button id="fakeLicenseedCarMonitor" class="j-bj" >假套牌实时报警</button>
    </div>
    <div style="margin:10px">
      <button id="fakeLicenseedCarSearch" class="j-cx">假套牌信息查询</button>
    </div>
  </div>
  <div data-options="region:'center'" style="padding:1px;background:#eee;width:90%;height:100%">
    <div id="fackLicensedCar_tabs" class="easyui-tabs result_details_tabs" fit=true data-options="selected:0"> </div>
  </div>
</div>
<script type="text/javascript">
    	
    	$(function(){
    		
    		var fackLicensedCarTabs = $("#fackLicensedCar_tabs").tabs();
        	var fackLicensedCarNavigators =  [
    			{
    			    id: 'fakeLicenseedCarMonitor',
    			    href: '/fakeLicensedCar/fakeLicenseedCarMonitor.action'
    			}, {
    			    id: 'fakeLicenseedCarSearch',
    			    href: '/fakeLicensedCar/fakeLicenseedCarSearch.action'
    			}                              	                                  
        	];
        	
        	//按钮注册，tab中引入对应页面
        	fackLicensedCarNavigators.forEach(function (n) {
        		
                var $el=$('#' + n.id).click(function (e) {               	
                	//阻止时间默认动作
                    e.preventDefault();
                    var title = $.trim($el.text());
                    if (fackLicensedCarTabs.tabs('exists', title)) {
                    	fackLicensedCarTabs.tabs('select', title)
                    } else {
                    	fackLicensedCarTabs.tabs('add', {
                            title: title,
                            closable: true,
                            href: n.href
                        });
                    }
                });               
            });
    		
    	});
    	  	   	
    </script> 
