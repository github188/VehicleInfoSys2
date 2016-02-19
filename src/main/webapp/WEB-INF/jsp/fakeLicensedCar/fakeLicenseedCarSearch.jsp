<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" style="width:100%;height:100%;">

<div data-options="region:'center'" style="padding:1px;background:#eee;width:90%;height:100%">

<div id="fakeLicenseedCar_serach_toolbar">
<div  class="easyui-panel" data-options="border:false" style="background-color:#F4F4F4;padding:5px 35px 5px 10px;border-bottom:1px solid #95B8E7;width:100%;color:#06C; font-size:12px;">
  <div>
	   车牌号码：
	 <input id="licens_id" class="easyui-textbox" style="width:160px" name="plate" id="warn_plate">
	 &nbsp;&nbsp;&nbsp;&nbsp;
	    车牌种类：
	 <input id="palate_type_id" type="text" style="width:10%">
	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    车辆类型：
	 <input id="vehicle_kind_id" type="text" style="width:10%">
	 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    车辆颜色：
	 <input id="car_color_id" type="text" style="width:8%">
  </div>
  <div style="margin-top:10px;margin-bottom:5px">
	 过车时间段：
	<input id="fakeLicens_start_time" type="text" style="width:13%">
	--
	<input id="fakeLicens_end_time" type="text" value="" style="width:13%">
	&nbsp;&nbsp;
	报警类型：
	<input id="warn_type_id" type="text" style="width:10%">
	&nbsp;&nbsp;
	人工审核:
	<input id="manul_audit_id" type="text" style="width:9%">
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a id="warn_serarch_btn_id" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
	&nbsp;&nbsp;
    <a id="warn_reset_btn_id" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-clear'" style="width:80px">重置</a> 
  </div>

</div>
</div>

<div id="fakeLicenseedCar_serach_datagrid"></div>
</div>



<div data-options="region:'east',title:'监控点列表'" style="padding:5px;width:10%;height:100%">      
	<ul id="flc_camera_tree_serach" class="easyui-tree" data-options="animate:true,checkbox:true,lines:true"></ul>       
</div>


</div>
<script type="text/javascript">
(function () {
	//树值
	var camerNamePara_serach;
	//页面右边监控点树操作
    //存放被选择的监控点
    var $query_camera=$("#flc_camera_tree_serach");
    //树形下拉框 监控点
    var cameraIds = $query_camera.tree({
        url: '/camera/camLstTree.action',
        animate:true,
        multiple:true,
        onCheck:function(node,checked) {
            var nodes = $('#flc_camera_tree_serach').tree('getChecked');
            var s = '';
            for(var i=0; i<nodes.length; i++){
                var leaf = $('#flc_camera_tree_serach').tree('isLeaf',nodes[i].target);
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
            camerNamePara_serach = s;
            $("#fakeLicenseedCar_serach_datagrid").datagrid("reload");
        }
    });
	
	//下拉框填值
	$('#palate_type_id').combogrid({
  		multiple:true,
  		idField:'value',
		textField:'text',
		panelHeight:440,
        editable:false,
		columns:[[
	        	  {field: 'value', title: 'value', hidden: true},
                  {field: 'checkbox', checkbox: true},
                  {field: 'text', title: '全选'}
               ]],
        data:[{
                value: 0,
                text: '未知车牌'
            },{
                value: 1,
                text: '蓝牌'
            },{
                value: 2,
                text: '黑牌'
            },{
                value: 3,
                text: '单排黄牌'
            },{
                value: 4,
                text: '双排黄牌（大车尾牌，农用车）'
            },{
                value: 5,
                text: '警车车牌'
            },{
                value: 6,
                text: '武警车牌'
            },{
                value: 7,
                text: '个性化车牌'
            },{
                value: 8,
                text: '单排军车'
            },{
                value: 9,
                text: '双排军车'
            },{
                value: 10,
                text: '使馆牌'
            },{
                value: 11,
                text: '香港牌'
            },{
                value: 12,
                text: '拖拉机牌'
            },{
                value: 13,
                text: '澳门牌'
            },{
                value: 14,
                text: '厂内牌'
            }]               
  	});
	
  	$('#vehicle_kind_id').combogrid({
  		multiple:true,
  		idField:'value',
		textField:'text',
		panelHeight:370,
        editable:false,
		columns:[[
	        	  {field: 'value', title: 'value', hidden: true},
                  {field: 'checkbox', checkbox: true},
                  {field: 'text', title: '全选'}
               ]],
        data:[{
            value: "轿车",
            text: "轿车"
        }, {
            value: "越野车",
            text: "越野车"
        }, {
            value: "商务车",
            text: "商务车"
        }, {
            value: "小型货车",
            text: "小型货车"
        }, {
            value: "大型货车",
            text: "大型货车"
        }, {
            value: "轻客",
            text: "轻客"
        }, {
            value: "小型客车",
            text: "小型客车"
        }, {
            value: "大型客车",
            text: "大型客车"
        }, {
            value: "三轮车",
            text: "三轮车"
        }, {
            value: "微面",
            text: "微面"
        }, {
            value: "皮卡",
            text: "皮卡"
        }, {
            value: "挂车",
            text: "挂车"
        }, {
            value: "混凝土搅拌车",
            text: "混凝土搅拌车"
        }, {
            value: "罐车",
            text: "罐车"
        }, {
            value: "随车吊",
            text: "随车吊"
        }, {
            value: "消防车",
            text: "消防车"
        }, {
            value: "渣土车",
            text: "渣土车"
        }, {
            value: "押运车",
            text: "押运车"
        }, {
            value: "工程抢修车",
            text: "工程抢修车"
        }, {
            value: "救援车",
            text: "救援车"
        }, {
            value: "栏板卡车",
            text: "栏板卡车"
        }, {
            value: "未识别",
            text: "未识别"
        }]
  	});
  	
  	$('#car_color_id').combogrid({
  		multiple:true,
  		idField:'value',
		textField:'text',
		panelHeight:300,
        editable:false,
		columns:[[
	        	  {field: 'value', title: 'value', hidden: true},
                  {field: 'checkbox', checkbox: true},
                  {field: 'text', title: '全选'}
               ]],
        data:[{
                value: '黑',
                text: '黑色'
            },{
                value: '白',
                text: '白色'
            },{
                value: '蓝',
                text: '蓝色'
            },{
                value: '棕',
                text: '棕色'
            },{
                value: '红',
                text: '红色'
            },{
                value: '灰',
                text: '灰色'
            },{
                value: '黄',
                text: '黄色'
            },{
                value: '粉',
                text: '粉色'
            },{
                value: '紫',
                text: '紫色'
            },{
                value: '绿',
                text: '绿色'
            }]               
  	});
  	
  	$('#warn_type_id').combogrid({
  		multiple:true,
  		idField:'value',
		textField:'text',
		panelHeight:120,
        editable:false,
		columns:[[
	        	  {field: 'value', title: 'value', hidden: true},
                  {field: 'checkbox', checkbox: true},
                  {field: 'text', title: '全选'}
               ]],
        data:[{
                value: '无牌车',
                text: '无牌车'
            },{
                value: '假牌车',
                text: '假牌车'
            },{
                value: '套牌车',
                text: '套牌车'
            }]               
  	});
  	
  	$('#manul_audit_id').combogrid({
  		multiple:true,
  		idField:'value',
		textField:'text',
		panelHeight:180,
        editable:false,
		columns:[[
	        	  {field: 'value', title: 'value', hidden: true},
                  {field: 'checkbox', checkbox: true},
                  {field: 'text', title: '全选'}
               ]],
        data:[{
                value: '正常车',
                text: '正常车'
            },{
                value: '无牌车',
                text: '无牌车'
            },{
                value: '假牌车',
                text: '假牌车'
            },{
                value: '套牌车',
                text: '套牌车'
            },{
                value: '无法确定',
                text: '无法确定'
            }]               
  	});
  	
  	
  	var fakeLicens_start_time = $('#fakeLicens_start_time').datetimebox({
        editable: false
    });
  	var fakeLicens_end_time = $('#fakeLicens_end_time').datetimebox({
        editable: false
    });
  	
  	
    //创建假套牌车信息表
    $("#fakeLicenseedCar_serach_datagrid").datagrid({
        url: '/fakeLicensedCar/querylist.action',
        loadMsg: '数据正在加载,请耐心的等待...',
        pagination: true,
        pageNumber: 1,
        pageSize: 20,
        rownumbers: true,
        fit: true,
        singleSelect: true,
        columns: [[
			{
			    field: 'id', title: '主键id', width: '1%',hidden:true
			},
			{
			    field: 'serialNumber', title: '序列号', width: '1%',hidden:true
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
                field: 'license', title: '车牌', width: '10%'
            },
            {
                field: 'plateType', title: '车牌种类', width: '10%',
                formatter:function(value,row,index){                   
                   return util.formatePlateType(value);
                }
            },
            {
                field: 'viehicleKind', title: '车型', width: '10%'
            },
            {
            	field: 'carColor', title: '车身颜色', width: '10%'
            },
            {
            	field: 'vehicleBrand', title: '车品牌', width: '13%',
            	formatter:function(value,row,index){ 
            		var brand="";
            		if(row.vehicleBrand != null){
            			brand = brand + row.vehicleBrand+"  ";
            		}
            		if(row.vehicleSeries != null){
            			brand = brand + row.vehicleSeries+"  ";
            		}
            		if(row.vehicleStyle != null){
            			brand = brand + row.vehicleStyle;
            		}
                    return brand;
                 }
            },
            {
                field: 'manulAudit', title: '人工审核', width:'8%'
            }
        ]],
        onBeforeLoad:function(p){
           $.extend(p,getParamData());
        },
        onDblClickRow: function (index, row) {
            
        },
        onLoadSuccess: function (pager) {
            
        },
        toolbar:$("#fakeLicenseedCar_serach_toolbar")
    });
    
    function getParamData(){
    	var data = {
        	license:$("#licens_id").val(),
        	plateType:$("#palate_type_id").combo('getValues'),
        	viehicleKind:$("#vehicle_kind_id").combo('getValues'),
        	carColor:$("#car_color_id").combo('getValues'),
        	startTime:fakeLicens_start_time.combo('getValue'),
        	endTime:fakeLicens_end_time.combo('getValue'),
        	warnType:$("#warn_type_id").combo('getValues'),
        	manulAudit:$("#manul_audit_id").combo('getValues'),
        	camerName:camerNamePara_serach
    	};
    	
    	 $.each(data,function(k,v){
             data[k]= $.trim(v);
         })
         
         return data;
    }
    
    $("#warn_serarch_btn_id").click(function (){
    	$("#fakeLicenseedCar_serach_datagrid").datagrid("reload");
    });
    
 	$("#warn_reset_btn_id").click(function (){
 		$("#licens_id").textbox("clear");
 		$('#palate_type_id').combogrid('clear');
 		$('#vehicle_kind_id').combogrid('clear');
 		$('#car_color_id').combogrid('clear');
 		$('#fakeLicens_start_time').datetimebox('clear');
 		$('#fakeLicens_end_time').datetimebox('clear');
 		$('#warn_type_id').combogrid('clear');
 		$('#manul_audit_id').combogrid('clear');	
 		
 		var root = $('#flc_camera_tree_serach').tree('getRoot');
 		$("#flc_camera_tree_serach").tree('uncheck',root.target);
    });
  	
	
	
})();
</script>