<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="easyui-layout" style="width:100%;height:100%">
	<div data-options="region:'center',title:'',split:true" style="width:70%;">
		<form id="surveillance_form_id" action="#">
        <fieldset style="margin:10px 10px">
            <legend>布控基本信息</legend>
            <br/>
            <div style="font-size:15px;color:">
            	&ensp;<font color="red">任务名称</font>&nbsp;:&ensp;<input id="surveillance_task_name" type="text" class="easyui-textbox" style="height:25px;width:50%" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[1,50],remote:['/surveillance/valideName.action','taskName']}"><br/><br/>
                &ensp;&ensp;&ensp;监控点&nbsp;:&ensp;<input id="surveillance_cameraIds" type="text" class="easyui-textbox" style="height:25px;width:50%">
                <div style="float:right;margin:23px 200px 0px 0px;"><button href="javascript:void(0)" id="map_button" style="margin-top: -30px;"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">地图</span><span class="l-btn-icon icon-search">&nbsp;</span></span></button></div><br/><br/>
                &ensp;&ensp;&ensp;车牌号&nbsp;:&ensp;<input id="plate_number" type="text" class="easyui-textbox" style="height:25px;width:129px;"><br/><br/>
                &ensp;&ensp;&ensp;特征物&nbsp;:&ensp;<input id="characteristics" type="text" class="easyui-textbox" style="height:25px;width:129px;"><br/><br/>
                &ensp;车身颜色&nbsp;:&ensp;<input id="body_color" type="text" class="easyui-textbox" style="height:25px;width:129px;"><br/><br/>
                &ensp;&ensp;&ensp;&ensp;&ensp;车型&nbsp;:&ensp;<input id="motorcycle_type" class="easyui-textbox" type="text" style="height:25px;width:129px;"><br/><br/>
                &ensp;车牌类型&nbsp;:&ensp;<input id="plate_type" type="text" class="easyui-textbox" style="height:25px;width:129px;"><br/><br/>
                &ensp;&ensp;&ensp;&ensp;&ensp;品牌&nbsp;:&nbsp;<input id="brand" class="easyui-textbox" style="height:25px;width:129px;">
                &nbsp;车系&nbsp;:&nbsp;<input id="series" class="easyui-textbox" style="height:25px;width:100px;">
                &nbsp;车款&nbsp;:&nbsp;<input id="model" class="easyui-textbox" style="height:25px;width:100px;"><br/><br/>
             </div>
        </fieldset>
        <fieldset style="margin:10px 10px">
        	<legend>布控人信息</legend>
        	<br/>
        	<div style="font-size:15px;color:">
                &ensp;&ensp;&ensp;<font color="red">申请人</font>&nbsp;:&ensp;<input id="surveillance_people_name" type="text" class="easyui-textbox" style="height:25px;width:129px" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[1,50]}" value="${empty userInfo.name ? userInfo.loginName : userInfo.name}">                              
                &ensp;<font color="red">布控人联系电话</font>&nbsp;:&ensp;<input id="surveillance_people_tel" type="text" class="easyui-textbox" style="height:25px;width:180px;" data-options="required:true,validType:{num:true,length:[1,20]}" value="${userInfo.tel}"><br/><br/>
                &ensp;<font color="red">布控部门</font>&nbsp;:&ensp;<input id="surveillance_unit_name" type="text" class="easyui-textbox" style="height:25px;width:445px;" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[1,256]}" value="${userInfo.depName}"><br/><br/>
                &ensp;布控简介&nbsp;:&ensp;<input id="surveillance_detail" class="easyui-textbox" data-options="multiline:true" style="height:70px;width:445px;"><br/><br/>
             </div>
        </fieldset>
        </form>
        <div style="float:right;margin-right:20px">
         <a href="javascript:void(0)" id="surveillance_deploy_button" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">布控</a>
         &ensp;
         <a href="javascript:void(0)" id="surveillance_clear_button" class="easyui-linkbutton" data-options="iconCls:'icon-clear'">重置</a>
        </div>
	</div> 
	<div data-options="region:'east',title:'审批流程',split:true" style="width:30%;">
	    
		<img src="images/floatChart.png" alt="流程图" width="370px"/>
	</div>
</div>
<script type="text/javascript">
$(function (){     
    //存放被选择的监控点
    var camIdArr = [],camNameArr = [],$query_camera=$('#surveillance_cameraIds');
    //树形下拉框 监控点
    var cameraIds = $query_camera.combotree({
        url: '/camera/camLstTree.action',
        animate:true,
        multiple:true,
        onCheck:function(node,checked) {
        	util.checkCam(cameraIds,node,camIdArr,camNameArr,checked);
        },
        onClick:function(node) {
        	util.checkCam(cameraIds,node,camIdArr,camNameArr,node.checked);
        },
        onChange:function(nVal,oVal) {
            util.checkCamWithMap(cameraIds);
        }
     });
	
    var plate_type = $("#plate_type").combobox({
        data: [{
            value: '0',
            text: '未知车牌'
        }, {
            value: '1',
            text: '蓝牌'
        }, {
            value: '2',
            text: '黑牌'
        }, {
            value: '3',
            text: '单排黄牌'
        }, {
            value: '4',
            text: '双排黄牌'
        }, {
            value: '5',
            text: '警车车牌'
        }, {
            value: '6',
            text: '武警车牌'
        }, {
            value: '7',
            text: '个性化车牌'
        }, {
            value: '8',
            text: '单排军车'
        }, {
            value: '9',
            text: '双排军车'
        }, {
            value: '10',
            text: '使馆牌'
        }, {
            value: '11',
            text: '香港牌'
        }, {
            value: '12',
            text: '拖拉机'
        }, {
            value: '13',
            text: '澳门牌'
        }, {
            value: '14',
            text: '厂内牌'
        }]
    });

    var body_color = $("#body_color").combogrid({
        multiple: true,
        idField: 'value',
        textField: 'text',
        editable:false,
        columns: [[
            {field: 'value', title: 'value', hidden: true},
            {field: 'checkbox', checkbox: true},
            {field: 'text', title: '颜色', width: 95}
        ]],
        data: [{
            value: '黑',
            text: '黑'
        }, {
            value: '蓝',
            text: '蓝'
        }, {
            value: '青',
            text: '青'
        }, {
            value: '灰(银)',
            text: '灰（银）'
        }, {
            value: '绿',
            text: '绿'
        },{
            value: '红',
            text: '红'
        },{
            value: '白',
            text: '白'
        },{
            value: '黄',
            text: '黄'
        },{
            value: '棕',
            text: '棕'
        }, {
            value: '粉',
            text: '粉'
        }]
    });
    var motorcycle_type = $("#motorcycle_type").combobox({
        url:"js/json/vehicleKind.json",
        valueField:"value",
        textField:"text"
    });
    var characteristics = $("#characteristics").combogrid({
        multiple: true,
        idField: 'value',
        textField: 'text',
        editable:false,
        columns: [[
            {field: 'value', title: 'value', hidden: true},
            {field: 'checkbox', checkbox: true},
            {field: 'text', title: '特征物名称', width: 95}
        ]],
        data: [{
            value: 'tag',
            text: '年检标'
        }, {
            value: 'paper',
            text: '纸巾盒'
        }, {
            value: 'sun',
            text: '遮阳板'
        }, {
            value: 'drop',
            text: '挂饰'
        }]
    });
    // 一层Combo
    var brand = $("#brand").combobox({
        //editable:false, //不可编辑状态  品牌太多 支持匹配
                url: '/brandmodel/listbrand.action',
                dataType: 'json',
                type:'post',
        onChange:function(record){  //onSelect 用户点击时触发的事件  在此的意义在于，用户点击一级后自动二级combobox
            $.ajax({
                type:'post',
                url: '/brandmodel/listsubbrand.action',
                data:{brandName : $("#brand").combobox("getValue")},
                success:function(d){
                    var data=[];
                    $.each(d,function(i,o) {
                        var obj={value: o.carSeries,text: o.carSeries};
                        data.push(obj);
                    })
                    series.combobox({
                        onLoadSuccess:function(){  //清空三级下拉框 就是成功加载完触发的事件 当一级combobox改变时，二级和三级就需要清空
                            model.combobox("clear");
                            series.combobox("clear");
                        },
                        data:data,
                        onChange:function(record) {

                            $.ajax({
                                type:'post',
                                url: '/brandmodel/listcar.action',
                                data:{brandName : $("#brand").combobox("getValue"),carSeries : $("#series").combobox("getValue")},
                                success:function(d){
                                    var data=[];
                                    $.each(d,function(i,o) {
                                        var obj={value: o.modelsName,text: o.modelsName};
                                        data.push(obj);
                                    })
                                    model.combobox({
                                        data:data,
                                        onChange:function(record) {

                                        }
                                    });
                                }
                            });

                        }
                    });
                }
            });

        },
        valueField: 'brandName',
        textField: 'brandName',
        value:''
    });
    //  二层Combo
    var series = $("#series").combobox({
        editable:false, //不可编辑状态
        value:''
    });
    //三层Combo
    var model=$("#model").combobox({
        editable:false, //不可编辑状态
        value:''
    });
    //地图选择监控点按钮
    $('#map_button').click(function (e) {
        e.preventDefault();
        pubMapWindow.open(cameraIds,camIdArr,camNameArr);
    });
    
    //布控按钮
    $("#surveillance_deploy_button").click(function(){
    	if ($('#surveillance_form_id').form("validate")) {
    		
    		var data = getSurveillanceValues();  	 		
    		if(data.plate=="" && (data.tag==null &&data.paper==null &&data.sun==null &&data.drop==null) && data.carcolor=="" && data.vehicleKind=="" &&data.plateType=="" &&data.vehicleBrand==""){
    			$.messager.show({
                    title: '提示信息',
                    msg: '布控失败，请至少选择一个布控条件！',
                    timeout: 5000,
                    showType: 'slide'
                });
    			
    			return;
    		}
    		   		
            $.ajax({
                url: '/surveillance/add.action',
                type: 'post',
                data: getSurveillanceValues(),
                success: function (d) {
                    var msg = '';
                    if (d == "surveillancing") {
                        msg = '当前任务正在布控中';
                    } else {
                        msg = '添加布控成功';
                        clearSurveillanceInfo();
                    }
                    $.messager.show({
                        title: '提示信息',
                        msg: msg,
                        timeout: 5000,
                        showType: 'slide'
                    });
                }
            });
        }
    });
    //清空按钮
    $("#surveillance_clear_button").click(function(){
    	clearSurveillanceInfo();
    });
    
    
    //清空函数
    function clearSurveillanceInfo(){
    	camIdArr = [];
    	camNameArr = [];
    	$('#surveillance_form_id').form('reset');
    	
    }
    
    //取布控值
    function getSurveillanceValues(){
    	var tag=null, paper=null, sun=null, drop=null;
    	var rows = characteristics.combogrid('grid').datagrid('getRows'),
        selections = characteristics.combogrid('grid').datagrid('getSelections');
		$.each(rows, function (index, row) {
		    $.each(selections, function (i, o) {
		        if(row == o) {
		            if(o.value == 'tag') {
		                tag = 1;
		            }else if(o.value == 'paper') {
		                paper = 1;
		            }else if(o.value == 'sun') {
		                sun = 1;
		            }else{
		                drop = 1;
		            }
		        }
		    })
		});
    	var data = { 			
    			name:$.trim($('#surveillance_task_name').val()),
                plate:$.trim($('#plate_number').val()),
                plateType:$.trim($('#plate_type').combo('getValue')),
                carcolor:$.trim($('#body_color').combo('getValues').join()),
                camera:$.trim($('#surveillance_cameraIds').combo('getText')),
                vehicleKind:$.trim($('#motorcycle_type').combo('getValue')),
                vehicleBrand: $.trim($("#brand").combo('getValue')),
                vehicleSeries: $.trim($("#series").combo('getValue')),
                vehicleStyle: $.trim($("#model").combo('getValue')),
                tag:tag,
                paper:paper,
                sun:sun,
                drop:drop,
                peopleName:$.trim($('#surveillance_people_name').val()),
                peopleTel:$.trim($('#surveillance_people_tel').val()),
                unitName:$.trim($('#surveillance_unit_name').val()),
                detail:$.trim($('#surveillance_detail').val())
            };
    	return data;   	
    }
    

})
</script>