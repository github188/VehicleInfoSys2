<%@ page contentType="text/html;charset=UTF-8" language="java" %>
	
	<div class="easyui-layout" style="width:100%;height:100%;">
	
	<div data-options="region:'center'" style="padding:1px;background:#eee;width:90%;height:100%">
	
	<div id="fakeLicenseedCar_monitor_toolbar">
		<div  class="easyui-panel" data-options="border:false" style="background-color:#F4F4F4;padding:5px 35px 5px 10px;border-bottom:1px solid #95B8E7;width:100%">
			 <a href="javascript:void(0)" id="menu_warn_time" class="easyui-menubutton" data-options="iconCls:''" style="float:left">报警时间</a>
		     <div class="datagrid-btn-separator"></div>
		     <a href="javascript:void(0)" id="menu_process_state" class="easyui-menubutton" data-options="iconCls:''" style="float:left">处理状态</a>
		     <div class="datagrid-btn-separator"></div>
		     <a href="javascript:void(0)" id="menu_warn_type" class="easyui-menubutton" data-options="iconCls:''" style="float:left">报警类型</a>
		     <div class="datagrid-btn-separator"></div>
		     <a href="javascript:void(0)" id="menu_warn_confidence" class="easyui-menubutton" data-options="iconCls:''">可信度(>)</a>
		     <div style="float:right;width:30%">			 	
			 	<button id="startbtn" style="width:80px;height:30px">开启报警</button>
				<label style="margin-left:20px"><font size="3">报警状态：</font></label>
				<label style="margin-right:30px"><font id="monitorState" size="2" color="red">未开启</font></label>
			 </div>
		</div>
		
		<div id="menuTime" style="width:100px;">
		     <div data-options="iconCls:'icon-ok',name:'today'">今天</div>
		     <div data-options="iconCls:'icon-ok',name:'yesterday'">昨天</div>
		     <div data-options="iconCls:'icon-ok',name:'before'">更早以前</div>
		</div>
		<div id="menuState" style="width:100px;">
		     <div data-options="iconCls:'icon-ok',name:'deal'">已处理</div>
		     <div data-options="iconCls:'icon-ok',name:'notDeal'">未处理</div>
		</div>
		<div id="menuType"  style="widtz`h:100px;" >
		     <div data-options="iconCls:'icon-ok',name:'tp'">套牌车</div>
		     <div data-options="iconCls:'icon-ok',name:'jp'">假牌车</div>
		     <div data-options="iconCls:'icon-ok',name:'wp'">无牌车</div>
		</div>
		<div id="menuConfidence"  style="width:100px;" >
		     <div id="100" data-options="iconCls:'',name:'100'">100</div>
		     <div id="80" data-options="iconCls:'',name:'80'">80</div>
		     <div id="60" data-options="iconCls:'',name:'60'">60</div>
		     <div id="40" data-options="iconCls:'',name:'40'">40</div>
		     <div id="20" data-options="iconCls:'',name:'20'">20</div>
		     <div id="0" data-options="iconCls:'icon-ok',name:'0'">0</div>
		</div>
	</div>
	
	<div id="fakeLicenseedCar_monitor_datagrid"></div>
	
	<div id="monitor_popWindow" style="position:relative; width:100%; height:100%;"></div>
	
	</div>
	
	<div data-options="region:'east',title:'监控点列表'" style="padding:5px;width:10%;height:100%">      
        <ul id="flc_camera_tree" class="easyui-tree" data-options="animate:true,checkbox:true,lines:true"></ul>       
    </div>
	
	</div>

<script type="tmpl" id="fakeLicenseCar_tmpl">
<div id="warnInfodetailInfo"  style="height:650px;width:100%;">
            <table height="620px" width="100%" cellspacing="0" cellpadding="0"  style="text-align:center;padding:0px;margin:0px;">
                <tbody >
                    <tr height="50%"><td colspan=3><img id="myImage"  src="{#imageUrl}" border="none" height="400px" width="100%"/></td></tr>
                    <tr><td width="20%" ></td><td width="40%"><font color="green">识别结果</font></td><td width="40%"><font color="green">车管所登记信息 </font></td></tr>
                    <tr><td width="20%" ><font color="green">号牌号码：</font></td><td id="SFPlateDetail" width="40%">{#license}</td><td id="policePlateDetail" width="40%">{#governmentLicense}</td></tr>
                    <tr><td width="20%" ><font color="green">车牌种类：</font></td><td id="SFPlateTypeDetail" width="40%">{#plateType}</td><td id="policePlateTypeDetail" width="40%">{#governmentPlateType}</td></tr>
                    <tr><td width="20%" ><font color="green">车辆颜色：</font></td><td id="SFCarColorDetail" width="40%"> {#carColor}</td><td id="policeCarColorDetail" width="40%">{#governmentCarColor}</td></tr>
                    <tr><td width="20%" ><font color="green">车辆类型：</font></td><td id="SFCarKindDetail" width="40%"> {#viehicleKind}</td><td id="policeCarKindDetail" width="40%">{#governmentViehicleKind}</td></tr>
                    <tr><td width="20%" ><font color="green">品牌型号：</font></td><td id="SFCarStyleDetail" width="40%"> {#brand}</td><td id="policeCarStyleDetail" width="40%">{#governmentBrand}</td></tr>
                    <tr><td width="20%" ><font color="green">过车时间：</font></td><td id="SFCarSnapshotTime" width="40%">{#util.formateTime(vehicelTime)}</td><td id="policeCarSnapshotTime" width="40%">--</td></tr>
                    <tr><td width="20%" ><font color="green">报警类型：</font></td><td id="SFWarnType" width="40%"> {#warnType}</td><td id="policeWarnType" width="40%">--</td></tr>
                    <tr><td width="20%" ><font color="green">人工审核：</font></td><td id="manualRecognizeResult" class="SFProcessState" width="40%">{#manulAudit}</td><td id="manualRecognizeResult" class="policeProcessState" width="40%">--</td></tr>
                </tbody>
            </table>
            <div style="width:100%;text-align:center; margin-top:10px;">
				<span><font color="green">审核：</font></span> 
                <select id="warnResultCombobox" class="easyui-combobox" data-options="panelHeight:'auto',width:80,editable:false" name="warnResult">   
                    <option value='正常车'>正常车</option>  
                    <option value='假牌车'>假牌车</option>  
                    <option value='套牌车'>套牌车</option>  
                    <option value='无牌车'>无牌车</option> 
                    <option value='无法确定'>无法确定</option> 
                  </select> 
				<span>&nbsp;&nbsp;&nbsp; </span>
                <a id="warnVeryfyBtn" href="javascript:void(0)" class="easyui-linkbutton" style="width:80px;">审核提交</a> 
            </div>   
        </div>
</script>

<script type="text/javascript">
(function () {
	
	//监控点id
	var camerNamePara = "";
	
	//套牌车辆id
	var fakeCarId = 1;
	
	//报警时间，处理状态，报警类型参数数据
	var menu_condition={"menu_warn_time":{"today":true,"yesterday":true,"before":true},
						"menu_process_state":{"deal":true,"notDeal":true},
						"menu_warn_type":{"jp":true,"tp":true,"wp":true},"menu_warn_confidence":0};
	
	//初始化
	var warnTimeMenu = $('#menu_warn_time').menubutton({menu:'#menuTime'});
	var processStateMenu = $('#menu_process_state').menubutton({menu:'#menuState'});
	var warnTypeMenu = $('#menu_warn_type').menubutton({menu:'#menuType'});
	var warnConfidenceMenu = $('#menu_warn_confidence').menubutton({menu:'#menuConfidence'});	
	
	
	//按钮菜单绑定单击事件
	$(warnTimeMenu.menubutton('options').menu).menu({
		onClick: function(item){
			menuButtonClick('menu_warn_time',warnTimeMenu,item);
		}
	});
	$(processStateMenu.menubutton('options').menu).menu({
		onClick: function(item){
			menuButtonClick('menu_process_state',processStateMenu,item);
		}
	});
	$(warnTypeMenu.menubutton('options').menu).menu({
		onClick: function(item){
			menuButtonClick('menu_warn_type',warnTypeMenu,item);
		}
	});
	$(warnConfidenceMenu.menubutton('options').menu).menu({
		onClick: function(item){
			confidenceButtonClick('menu_warn_confidence',warnConfidenceMenu,item);
		}
	}); 
	
	//单击事件触发的函数
	function menuButtonClick(id,fatherItme,item){
		var menu_icon_class = '';
		//赋值
		menu_condition[id][item.name] = !menu_condition[id][item.name];
		if ( typeof(item.iconCls) == "undefined" ){
			menu_icon_class = 'icon-ok';
		}
		$(fatherItme.menubutton('options').menu).menu('setIcon',{
			target: item.target,
			iconCls: menu_icon_class
		});
		
		$("#fakeLicenseedCar_monitor_datagrid").datagrid("reload");
	}
	
	//可信度处理函数
	function confidenceButtonClick(id,fatherItme,item){
		
		//赋值
		menu_condition[id] = item.name;
		
		clearMenuConfidenceSign();		
		$(fatherItme.menubutton('options').menu).menu('setIcon',{
			target: item.target,
			iconCls: 'icon-ok'
		});
		
		$("#fakeLicenseedCar_monitor_datagrid").datagrid("reload");
	};
		
	//清除loge
	function clearMenuConfidenceSign(){
		
		var itemEl = $('#0')[0];  
		var item = $('#menuConfidence').menu('getItem', itemEl);	
		$('#menuConfidence').menu('setIcon',{
			target: item.target,
			iconCls:''
		}); 
		
		itemEl = $('#20')[0];  
		item = $('#menuConfidence').menu('getItem', itemEl);	
		$('#menuConfidence').menu('setIcon',{
			target: item.target,
			iconCls:''
		});
		
		itemEl = $('#40')[0];  
		item = $('#menuConfidence').menu('getItem', itemEl);	
		$('#menuConfidence').menu('setIcon',{
			target: item.target,
			iconCls:''
		});
		
		itemEl = $('#60')[0];  
		item = $('#menuConfidence').menu('getItem', itemEl);	
		$('#menuConfidence').menu('setIcon',{
			target: item.target,
			iconCls:''
		});
		
		itemEl = $('#80')[0];  
		item = $('#menuConfidence').menu('getItem', itemEl);	
		$('#menuConfidence').menu('setIcon',{
			target: item.target,
			iconCls:''
		});
		
		itemEl = $('#100')[0];  
		item = $('#menuConfidence').menu('getItem', itemEl);	
		$('#menuConfidence').menu('setIcon',{
			target: item.target,
			iconCls:''
		});			
	}
	
	
	//创建假套牌车实时报警信息表
    $("#fakeLicenseedCar_monitor_datagrid").datagrid({
        url: '/fakeLicensedCar/realTimelist.action',
        loadMsg: '数据正在加载,请耐心的等待...',
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        rownumbers: true,
        fit: true,
        singleSelect: true,
        columns: [[
			{
			    field: 'id', title: '主键id', width: '0%',hidden:true
			},
			{
			    field: 'serialNumber', title: '序列号', width: '0%',hidden:true
			},
            {
                field: 'warnTime', title: '报警时间', width: '15%',formatter: util.formateTime
            },
            {
                field: 'vehicelTime', title: '过车时间', width: '15%',formatter: util.formateTime
            },
            {
                field: 'camerName', title: '监控点', width: '10%'
            },
            {
                field: 'warnType', title: '报警类型', width: '7%'
            },
            {
                field: 'manulAudit', title: '人工审核', width:'10%',
                formatter:function(value,row,index){
                	if(value == '未审核'){
                		return '<font color="red">'+value+'</font>';
                	}else {
                		return value;
                	}
                	
                }
            },
            {
                field: 'license', title: '车牌', width: '10%'
            },
            {
                field: 'viehicleKind', title: '车型', width: '10%'
            },
            {
            	field: 'carColor', title: '车身颜色', width: '10%'
            },
            {
            	field: 'vehicleBrand', title: '车品牌', width: '13%'
            }
        ]],
        onBeforeLoad:function(p){
            $.extend(p,getParamData());
        },
        onDblClickRow: function (index, row) {
            showFakeLicenseedCarWindow(row)
        },
        onLoadSuccess: function (pager) {
            
        },
        toolbar:$("#fakeLicenseedCar_monitor_toolbar")
    });
	
    
	
	//获取查询参数
	function getParamData(){
		
		//告警时间
		var queryWarnTimePara = "";
		if(menu_condition["menu_warn_time"]["today"]){
			queryWarnTimePara = queryWarnTimePara+"today,"
		}
		if(menu_condition["menu_warn_time"]["yesterday"]){
			queryWarnTimePara = queryWarnTimePara+"yesterday,"
		}
		if(menu_condition["menu_warn_time"]["before"]){
			queryWarnTimePara = queryWarnTimePara+"before,"
		}
		if(queryWarnTimePara.length>0){
			queryWarnTimePara = queryWarnTimePara.substring(0,queryWarnTimePara.length-1);
		}
				
		//处理状态
		var optionStatePara = "";
		if(menu_condition["menu_process_state"]["deal"]){
			optionStatePara = optionStatePara+"已处理,"
		}
		if(menu_condition["menu_process_state"]["notDeal"]){
			optionStatePara = optionStatePara+"未处理,"
		}
		if(optionStatePara.length>0){
			optionStatePara = optionStatePara.substring(0,optionStatePara.length-1);
		}
				
		//报警类型
		var warnTypePara = "";
		if(menu_condition["menu_warn_type"]["jp"]){
			warnTypePara = warnTypePara+"假牌车,"
		}
		if(menu_condition["menu_warn_type"]["tp"]){
			warnTypePara = warnTypePara+"套牌车,"
		}
		if(menu_condition["menu_warn_type"]["wp"]){
			warnTypePara = warnTypePara+"无牌车,"
		}
		if(warnTypePara.length>0){
			warnTypePara = warnTypePara.substring(0,warnTypePara.length-1);
		}
		
		//可信度
		var confidencePara = menu_condition["menu_warn_confidence"];
				
		var data = {
				queryWarnTime:queryWarnTimePara,
				optionState:optionStatePara,
				warnType:warnTypePara,
				confidence:confidencePara,
				camerName:camerNamePara
            }

            $.each(data,function(k,v){
                data[k]= $.trim(v);
            })
            return data;
	}
	
	
	//页面右边监控点树操作
    //存放被选择的监控点
    var $query_camera=$("#flc_camera_tree");
    //树形下拉框 监控点
    var cameraIds = $query_camera.tree({
        url: '/camera/camLstTree.action',
        animate:true,
        multiple:true,
        onCheck:function(node,checked) {
            var nodes = $('#flc_camera_tree').tree('getChecked');
            var s = '';
            for(var i=0; i<nodes.length; i++){
                var leaf = $('#flc_camera_tree').tree('isLeaf',nodes[i].target);
                if(!leaf){
                    continue;
                }
                if(!!nodes[i].attributes) {
                    if (s != '') {
                        s += ',';
                    }
                    s += nodes[i].text;
                }
            }
            camerNamePara = s;
            $("#fakeLicenseedCar_monitor_datagrid").datagrid("reload");
        }
    });
	
	//弹出审核窗口
  	function showFakeLicenseedCarWindow(row){
		
		//弹框信信息
		var winInfoBeans = {imageUrl:'',license:'',plateType:'',carColor:'',viehicleKind:'',brand:'',
				vehicelTime:'',warnType:'',manulAudit:'',governmentLicense:'',governmentPlateType:'',governmentCarColor:'',
				governmentViehicleKind:'',governmentBrand:''};
		var html = '';
		
		
		$("#monitor_popWindow").window({
			width: 800,
            height: 700,
            closed:true,
            title:row.license,
            onBeforeOpen:function(){
            	
            	//假套牌车辆信息表主键id
        		fakeCarId= row.id;
        		//车牌号
        		var licenseNbm = row.license;		
        		//识别结果表主机id
        		var serialNumber = row.serialNumber;
        		
        		//查询车辆信息表
        		$.ajax({
        			url:'/fakeLicensedCar/fakeLicenseCarInfoById.action',
        			type:'get',
        			async:false,
        			data:{'id':fakeCarId},
        			success:function (data){
        				if(data.total>0){
        					var dt = data.rows[0];
        					winInfoBeans.license = dt.license;
        					winInfoBeans.plateType = util.formatePlateType(dt.plateType);
        					winInfoBeans.carColor = dt.carColor;
        					winInfoBeans.viehicleKind = dt.viehicleKind;
        					if(dt.vehicleSeries == null || dt.vehicleSeries == 'null'){
        						dt.vehicleSeries = '未知';
        					}	
        					if(dt.vehicleStyle == null || dt.vehicleStyle == 'null'){
        						dt.vehicleStyle = '未知';
        					}
        					winInfoBeans.brand = dt.vehicleBrand+'-'+dt.vehicleSeries+'-'+dt.vehicleStyle;
        					winInfoBeans.vehicelTime = dt.vehicelTime;
        					winInfoBeans.warnType = dt.warnType;
        					winInfoBeans.manulAudit = dt.manulAudit;        					
        				}
        				
        				//查询车管所车辆登记信息	
    	         		$.ajax({
    	        			url:'/fakeLicensedCar/vehicleRegistrationGovernment.action',
    	        			type:'post',
    	        			async:false,
    	        			data:{'license':licenseNbm},
    	        			success:function (data){
    	        				if(data.total>0){
    	        					var dt = data.rows[0];
    	        					winInfoBeans.governmentLicense = dt.license
    	        					winInfoBeans.governmentPlateType = util.formatePlateType(dt.plateType);
    	        					winInfoBeans.governmentCarColor = dt.carColor;
    	        					winInfoBeans.governmentViehicleKind = dt.vehicleKind;
    	        					winInfoBeans.governmentBrand = dt.vehicleBrand+'-'+dt.vehicleSeries+'-'+dt.vehicleStyle;
    	        				}else{
    	        					winInfoBeans.governmentLicense ='无此车牌';
    	        					winInfoBeans.governmentPlateType = '无';
    	        					winInfoBeans.governmentCarColor = '无';
    	        					winInfoBeans.governmentViehicleKind = '无';
    	        					winInfoBeans.governmentBrand = '无';
    	        				}
    	        				
    	        				//查询识别结果信息表
    	                	 	$.ajax({
    	                			url:'/fakeLicensedCar/vlprResultlist.action',
    	                			type:'get',
    	                			async:false,
    	                			data:{'serialNumber':serialNumber},
    	                			success:function (data){
    	                				if(data.total>0){
    	                					winInfoBeans.imageUrl = '${PictureServerHost}/' + encodeURI(data.rows[0].imageUrl);
    	                					
    	                				}
    	                 	                				
    	                				html = util.parser($('#fakeLicenseCar_tmpl').html(), winInfoBeans);
    	                				
    	                				$("#monitor_popWindow").window({
    	                					 content:html
    	                				});
    	                				  	                				
    	                			}			
    	                		});    	        				
    	        			}			
    	        		});
        			}			
        		});
            }
		});
		$("#monitor_popWindow").window('open');	
	}
	
	//审核提交按钮触发函数
	$("#monitor_popWindow").on('click','#warnVeryfyBtn',function (){
		//获取审核结果
		var auditResult = $("#monitor_popWindow").find("#warnResultCombobox").combo("getValue");		
		//修改审核结果表
		$.ajax({
    	         url:'/fakeLicensedCar/updateFakeLicenseCarInfoById.action',
    	         type:'get',
    	         async:false,
    	         data:{'id':fakeCarId,'auditResult':auditResult},
    	         success:function (data){
    	        	 //刷新表格
    	        	 $("#fakeLicenseedCar_monitor_datagrid").datagrid("reload");
    	        	 //弹出提示框
    	        	 $.messager.show({
                         title: '提示消息',
                         msg: '修改成功！',
                         timeout: 2000,
                         showType: 'slide'
                     });
    	         }			
    	     }); 
		
		$("#monitor_popWindow").window('close');	
	});
	
	if(monitorFlage){
		$('#startbtn').text("关闭报警");
		$('#monitorState').text("已开启    ");
	}else{
		$('#startbtn').text("开启报警");
		$('#monitorState').text("未开启");
	}
	
	//开启监控按钮
	 $('#startbtn').bind('click', function(){		 
		 monitorFlage = !monitorFlage
		 
		 if(monitorFlage){
			 $('#startbtn').text("关闭报警");
			 $('#monitorState').text("已开启    ");
			 //开启后台监视
			 timerFlage=true;
			 myTimer();
			 //弹出提示框
	    	 $.messager.show({
	             title: '提示消息',
	             msg: '报警已开启！',
	             timeout: 2000,
	             showType: 'slide'
	         });
		 }else{
			 $('#startbtn').text("开启报警");
			 $('#monitorState').text("未开启");
			 //关闭监视
			 timerFlage=false;
			 //弹出提示框
	    	 $.messager.show({
	             title: '提示消息',
	             msg: '报警已关闭！',
	             timeout: 2000,
	             showType: 'slide'
	         });    	 
		 }
		 
	 });
		
	//前台循环处理
	function startShowPro(){
		$.ajax({
	    	url:'/fakeLicensedCar/queryDifferenceNumber.action',
	         type:'post',
	         async:true,
	         success:function (data){
	        	 if(data>0){
	        		 $("#fakeLicenseedCar_monitor_datagrid").datagrid("reload");
	        		//弹出提示框
	    	    	 $.messager.show({
	    	             title: '提示消息',
	    	             msg: '发现假套牌车辆，请处理！',
	    	             timeout: 5000,
	    	             showType: 'slide'
	    	         });  
	        	 }
	         }			
	     });	     
	}
	
	//定时器
	function myTimer(){
		startShowPro();
		if(timerFlage){
			setTimeout(function(){myTimer()},5000);
		}		
	}		
	
	
})()

</script>
