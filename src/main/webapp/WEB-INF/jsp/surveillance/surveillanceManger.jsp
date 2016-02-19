<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="bk_manger_datagrid"></div>
<div id="bk_operter_info_window"></div>

<!-- 编辑模板-->
<script type="tmpl" id="bk_edit_ifno_tmpl">
<div>
	<form id="bk_edit_surveillance_form_id" action="#">
        <fieldset style="margin:10px 10px">
            <legend>布控基本信息</legend>
            <br/>
            <div>
            	&ensp;<font color="red">任务名称</font>&nbsp;:&ensp;<input value="{#name}" id="bk_edit_surveillance_task_name"  type="text" class="easyui-textbox" style="height:20px;width:50%" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[1,50]}"><br/><br/>
                &ensp;&ensp;&ensp;监控点&nbsp;:&ensp;<input value="{#camera}" id="bk_edit_surveillance_cameraIds" type="text" class="easyui-textbox" style="height:20px;width:50%">
                <div style="float:right;margin:23px 200px 0px 0px;"><button href="javascript:void(0)" id="bk_edit_map_button" style="margin-top: -30px;"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">地图</span><span class="l-btn-icon icon-search">&nbsp;</span></span></button></div><br/><br/>
                &ensp;&ensp;&ensp;车牌号&nbsp;:&ensp;<input value="{#plate}" id="bk_edit_plate_number" type="text" class="easyui-textbox" style="height:20px;width:129px;"><br/><br/>
                &ensp;&ensp;&ensp;特征物&nbsp;:&ensp;<input  id="bk_edit_characteristics" type="text" class="easyui-textbox" style="height:20px;width:129px;"><br/><br/>
                &ensp;车身颜色&nbsp;:&ensp;<input value="{#carcolor}" id="bk_edit_body_color" type="text" class="easyui-textbox" style="height:20px;width:129px;"><br/><br/>
                &ensp;&ensp;&ensp;&ensp;&ensp;车型&nbsp;:&ensp;<input value="{#vehicleKind}" id="bk_edit_motorcycle_type" class="easyui-textbox" type="text" style="height:20px;width:129px;"><br/><br/>
                &ensp;车牌类型&nbsp;:&ensp;<input value="{#plateType}" id="bk_edit_plate_type" type="text" class="easyui-textbox" style="height:20px;width:129px;"><br/><br/>
                &ensp;&ensp;&ensp;&ensp;&ensp;品牌&nbsp;:&nbsp;<input value="{#vehicleBrand}" id="bk_edit_brand" class="easyui-textbox" style="height:20px;width:129px;">
                &nbsp;车系&nbsp;:&nbsp;<input value="{#vehicleSeries}" id="bk_edit_series" class="easyui-textbox" style="height:20px;width:100px;">
                &nbsp;车款&nbsp;:&nbsp;<input value="{#vehicleStyle}" id="bk_edit_model" class="easyui-textbox" style="height:20px;width:100px;"><br/><br/>
             </div>
        </fieldset>
        <fieldset style="margin:10px 10px">
        	<legend>布控人信息</legend>
        	<br/>
        	<div style="">
                &ensp;&ensp;&ensp;<font color="red">布控人</font>&nbsp;:&ensp;<input value="{#peopleName}" id="bk_edit_surveillance_people_name" type="text" class="easyui-textbox" style="height:20px;width:129px" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[1,50]}">                              
                &ensp;<font color="red">布控人联系电话</font>&nbsp;:&ensp;<input value="{#peopleTel}" id="bk_edit_surveillance_people_tel" type="text" class="easyui-textbox" style="height:20px;width:180px;" data-options="required:true,validType:{num:true,length:[1,20]}"><br/><br/>
                &ensp;<font color="red">布控单位</font>&nbsp;:&ensp;<input value="{#unitName}" id="bk_edit_surveillance_unit_name" type="text" class="easyui-textbox" style="height:20px;width:445px;" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[1,256]}"><br/><br/>
                &ensp;布控简介&nbsp;:&ensp;<input value="{#detail}" id="bk_edit_surveillance_detail" class="easyui-textbox" data-options="multiline:true" style="height:65px;width:445px;"><br/><br/>
             </div>
        </fieldset>
        </form>
        <div style="float:right;margin-right:20px">
			<a href="javascript:void(0)" class="btn_ok_survices" >布控并保存</a>
        	<a href="javascript:void(0)" class="btn_ok" >保存</a>
			<a href="javascript:void(0)" class="btn_cancel" >取消</a>
		    <br/><br/>
        </div>
</div>
</script>

<script type="text/javascript">
$(function(){
	var $win = $("#bk_operter_info_window");
	var edit_tmpl_html=$("#bk_edit_ifno_tmpl").html();
	var tab = util.getSelectedTab();
    var datagrid=$("#bk_manger_datagrid").datagrid({
        multiple: true,
        url: '/surveillance/list.action',
        pagination: true,
        pageSize: 20,
        rownumbers: true,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        fit: true,
        onClickRow: function (i, row) {
            selectRow = row;
            //loadResultDatas('','',selectRow.id)
        },
        columns: [[            
            {
                field: 'id', title: '任务ID',hidden: true, width: 120
            },
            {
                field: 'name', title: '任务名称', width: 180
            },
            {
                field: 'peopleName', title: '布控申请人', width: 80
            },
            {
                field: 'unitName', title: '布控单位', width: 150
            },
            {
                field: 'camera', title: '监控点名称', width: 120
            },
            {
                field: 'plate', title: '车牌号', width: 80
            },
            {
                field: 'plateType', title: '车牌类型', width: 70, formatter: function (value, row, index) {
                row = row || value;
                var plateValue = '';
                var plate_type_arr = {
                    0: '未知车牌',
                    1: '蓝牌',
                    2: '黑牌',
                    3: '单排黄牌',
                    4: '双排黄牌',
                    5: '警车车牌',
                    6: '武警车牌',
                    7: '个性化车牌',
                    8: '单排军车',
                    9: '双排军车',
                    10: '使馆牌',
                    11: '香港牌',
                    12: '拖拉机',
                    13: '澳门牌',
                    14: '厂内牌'
                }

                $.each(plate_type_arr, function (key, val) {
                    if (value == key) {
                        plateValue = val;
                    }
                })
                return plateValue;
            }
            },
            {
                field: 'carcolor', title: '车身颜色', width: 70
            },
            {
                field: 'vehicleKind', title: '车型', width: 70
            },
            {field: 'startTime', title: '申请布控时间', width: 150, formatter: util.formateTime},
            {
                field: 'status', title: '状态', width: 120, formatter: function (v) {
                	if(v == 0){
                		return "布控待审核";
                	}
                	if(v == 1){
                		return "布控中";
                	}
                	if(v == 2){
                		return "布控结束";
                	}
                	if(v == 3){
                		return "布控审核未通过";
                	}
                	if(v == 4){
                		return "撤控待审核";
                	}
                	if(v == 5){
                		return "撤控审核未通过";
                	}            
            	}
             },
             {
                field: 'xxx', title: '操作', width: 200, formatter: function (v, row) {
                var rowId = row.id;
                var status = row.status;
                var html="";
                //重新布控
                var ahtml = '<a href="javascript:void(0)" id="' +'a'+rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >重新布控</a>&nbsp;&nbsp;';
                //重新撤控
                var bhtml = '<a href="javascript:void(0)" id="' +'b'+rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >重新撤控</a>&nbsp;&nbsp;';
                //编辑
                var chtml = '<a href="javascript:void(0)" id="' +'c'+rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >编辑</a>&nbsp;&nbsp;';
                //查看
                var dhtml = '<a href="javascript:void(0)" id="' +'d'+rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >详情</a>&nbsp;&nbsp;';
                //删除
                var ehtml = '<a href="javascript:void(0)" id="' +'e'+rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >删除</a>&nbsp;&nbsp;';
                //撤销布控
                var fhtml = '<a href="javascript:void(0)" id="' +'f'+rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >撤销布控</a>&nbsp;&nbsp;';
                tab.off('click', '#' + 'a'+rowId).on('click', '#' + 'a'+rowId, function () {
                	$.messager.confirm("操作提示", "您确定要重新布控吗？", function (data) {
                        if (data) {
                        	//重新布控                                 
                        	reconfirmApplicant(rowId);
                        	datagrid.datagrid('reload');
                        }
                    }); 
                	
                });
                tab.off('click', '#' + 'b'+rowId).on('click', '#' + 'b'+rowId, function () {
                	$.messager.confirm("操作提示", "您确定要重新撤控吗？", function (data) {
                        if (data) {
                        	//重新撤控
                        	reconfirmRevoke(rowId);
                        	datagrid.datagrid('reload');
                        }
                    });
                	
                });
				tab.off('click', '#' + 'c'+rowId).on('click', '#' + 'c'+rowId, function () {
				    //编辑任务
					editSurveillance(row,datagrid);
					datagrid.datagrid('reload');
					
				});
				tab.off('click', '#' + 'd'+rowId).on('click', '#' + 'd'+rowId, function () {
				    //查看任务详情
					showDeatailSurveillanceTask(row);
				});
				tab.off('click', '#' + 'e'+rowId).on('click', '#' + 'e'+rowId, function () {
					$.messager.confirm("操作提示", "您确定要删除\""+row.name+"\"任务吗？", function (data) {
                        if (data) {
        					//撤销布控
        					deleteSurveillance(rowId);
        					datagrid.datagrid('reload');
                        }
                    });
					
				});
				tab.off('click', '#' + 'f'+rowId).on('click', '#' + 'f'+rowId, function () {
					$.messager.confirm("操作提示", "您确定要撤销布控吗？", function (data) {
                        if (data) {
        					//撤销布控
        					revokeSurveillance(rowId); 
        					datagrid.datagrid('reload');
                        }
                    });
					
				});
				if(status == 0){
					html=dhtml+ehtml;
				}
				if(status == 1){
					html=fhtml+dhtml;
				}
				if(status == 2){
					html=dhtml+ehtml;
				}
				if(status == 3){
					html=ahtml+chtml+dhtml+ehtml;
				}
				if(status == 4){
					html=dhtml;
				}
				if(status == 5){
					html=bhtml+dhtml;
				}
                return html;
            }
            } 
        ]],
        onBeforeLoad:function(p){
	          //$.extend(p,{status:0});
	    }
        /* toolbar: $('.bk_task_datagrid_toolbar', tab[0]).get(0) */
    });
    
    //重新布控
    function reconfirmApplicant(id){
    	$.ajax({
            url: '/surveillance/updateSurveillanceTaskStatus.action',
            type: 'post',
            data: {taskId:id,status:0},
            success: function (msg) { 
            	if(msg=='success'){
            		$.messager.show({
                        title: '提示信息',
                        msg: '重新布控成功！',
                        timeout: 5000,
                        showType: 'slide'
                    });
            	}
            }
        });
    }
    //重新撤控
	function reconfirmRevoke(id){
		$.ajax({
            url: '/surveillance/updateSurveillanceTaskStatus.action',
            type: 'post',
            data: {taskId:id,status:4},
            success: function (msg) { 
            	if(msg=='success'){
            		$.messager.show({
                        title: '提示信息',
                        msg: '重新撤控成功！',
                        timeout: 5000,
                        showType: 'slide'
                    });
            	}
            }
        }); 	
	}
    //撤销布控
	function revokeSurveillance(id){
		$.ajax({
            url: '/surveillance/updateSurveillanceTaskStatus.action',
            type: 'post',
            data: {taskId:id,status:4},
            success: function (msg) { 
            	if(msg=='success'){
            		$.messager.show({
                        title: '提示信息',
                        msg: '撤销布控申请成功！',
                        timeout: 5000,
                        showType: 'slide'
                    });
            	}
            }
        });
	}
    //删除
	function deleteSurveillance(id){
		$.ajax({
            url: '/surveillance/remove.action',
            type: 'post',
            data: {ids:id,isAllDelete:false},
            success: function (msg) { 
            	if(msg=='success'){
            		$.messager.show({
                        title: '提示信息',
                        msg: '删除成功！',
                        timeout: 5000,
                        showType: 'slide'
                    });
            	}
            }
        });
	}
    
    //编辑
    function editSurveillance(row,datagrid){
    	var html = util.parser(edit_tmpl_html, row);
    	$win.html(html);
    	
    	var $form = $win.find('form');
    	
        $win.dialog({
            title: "布控任务编辑",
            width: 750,
            modal: true,
            onOpen: function () {
            	//重新解析dom
                $.parser.parse($win);
            	//初始化控件
            	initWidget(row);
            	
				//品牌赋值
				$("#bk_edit_brand").combobox("setValue",row.vehicleBrand);
            	
				//特征物设置
				var characters=[];
				if(row.tag==1){
					characters.push('tag');
				}
				if(row.paper==1){
					characters.push('paper');				
				}
				if(row.sun==1){
					characters.push('sun');
				}
				if(row.drop==1){
					characters.push('drop');
				}
				$('#bk_edit_characteristics').combogrid('setValues', characters);
				
                $win.find('a.btn_ok_survices').linkbutton({               	
                    iconCls: 'icon-ok',
                    onClick: function () {
                        if ($form.form('validate')) {
                        	$.ajax({
                                url: '/surveillance/update.action',
                                type: 'post',
                                data: getEditSurveillanceValues(row.id,row.status),
                                success: function (msg) {
                                	//重新布控
                                	if(msg='success'){
                                		$.ajax({
                                            url: '/surveillance/updateSurveillanceTaskStatus.action',
                                            type: 'post',
                                            data: {taskId:row.id,status:0},
                                            success: function (msg) { 
                                            	if(msg=='success'){
                                            		$.messager.show({
                                                        title: '提示信息',
                                                        msg: '布控且保存成功！',
                                                        timeout: 5000,
                                                        showType: 'slide'
                                                    });
                                            	}
                                            }
                                        });
                                	 }
                                	datagrid.datagrid('reload');
                                	$win.dialog('close');
                                }
                            });
 						                        	
                        }
                    }
                });
							             
                $win.find('a.btn_ok').linkbutton({               	
                    iconCls: 'icon-ok',
                    onClick: function () {
                        if ($form.form('validate')) {
                        	$.ajax({
                                url: '/surveillance/update.action',
                                type: 'post',
                                data: getEditSurveillanceValues(row.id,row.status),
                                success: function (msg) {
                                	if(msg='success'){
	                                    $.messager.show({
	                                        title: '提示信息',
	                                        msg: '修改成功！',
	                                        timeout: 5000,
	                                        showType: 'slide'
	                                    });
                                	 }
                                	datagrid.datagrid('reload');
                                	$win.dialog('close');
                                }
                            });
 						                        	
                        }
                    }
                });
                
				$win.find('a.btn_cancel').linkbutton({
                    iconCls: 'icon-cancel',
                    onClick: function () {
                        $win.dialog('close');
                    }
                });
            },
            onBeforeClose: function () {
                $(this).dialog('clear');
            }
        });
    }
    
    //控件初始化
    function initWidget(row){    	
        //存放被选择的监控点
        var camIdArr = [],camNameArr = [],$query_camera=$('#bk_edit_surveillance_cameraIds');
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
            },
            onLoadSuccess:function(node,data) {
                cameraIds.combo("setText",row.camera);
            }
         });
   	
        var plate_type = $("#bk_edit_plate_type").combobox({
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

        var body_color = $("#bk_edit_body_color").combogrid({
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
        var motorcycle_type = $("#bk_edit_motorcycle_type").combobox({
            url:"js/json/vehicleKind.json",
            valueField:"value",
            textField:"text"
        });
        var characteristics = $("#bk_edit_characteristics").combogrid({
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
        var i=0;
        // 一层Combo
       var brand = $("#bk_edit_brand").combobox({
                    url: '/brandmodel/listbrand.action',
                    dataType: 'json',
                    type:'post',
            onChange:function(record){ 
                $.ajax({
                    type:'post',
                    url: '/brandmodel/listsubbrand.action',
                    data:{brandName : $("#bk_edit_brand").combobox("getValue")},
                    success:function(d){
                    	++i;
                        var data=[];
                        $.each(d,function(i,o) {
                            var obj={value: o.carSeries,text: o.carSeries};
                            data.push(obj);
                        })
                        series.combobox({
                            onLoadSuccess:function(){                                                            	
                            	if(i>1){
                            		model.combobox("clear");
                                    series.combobox("clear");
                            	}else{
                            		//车系赋值
                    				$("#bk_edit_series").combobox("setValue",row.vehicleSeries);
                            	}
                            },
                            data:data,
                            onChange:function(record) {

                                $.ajax({
                                    type:'post',
                                    url: '/brandmodel/listcar.action',
                                    data:{brandName : $("#bk_edit_brand").combobox("getValue"),carSeries : $("#bk_edit_series").combobox("getValue")},
                                    success:function(d){
                                        var data=[];
                                        $.each(d,function(i,o) {
                                            var obj={value: o.modelsName,text: o.modelsName};
                                            data.push(obj);
                                        })
                                        model.combobox({
                                            data:data,
                                            onChange:function(record) {

                                            },
                                            onLoadSuccess:function(){
                                            	if(i<=1){
                                    				//款型赋值
                                    				$("#bk_edit_model").combobox("setValue",row.vehicleStyle);
                                            	}
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
        var series = $("#bk_edit_series").combobox({
            editable:false, 
            value:''
        });       
        var model=$("#bk_edit_model").combobox({
            editable:false, 
            value:''
        });
        
        //地图选择监控点按钮
        $('#bk_edit_map_button').click(function (e) {
            e.preventDefault();
            pubMapWindow.open(cameraIds,camIdArr,camNameArr);
        });
    }
    
    
    //取编辑后的值
    function getEditSurveillanceValues(id,status){
    	var tag=0, paper=0, sun=0, drop=0;
    	var rows = $("#bk_edit_characteristics").combogrid('grid').datagrid('getRows'),
        selections = $("#bk_edit_characteristics").combogrid('grid').datagrid('getSelections');
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
    			id:id,
    			status:status,
    			name:$.trim($('#bk_edit_surveillance_task_name').val()),
                plate:$.trim($('#bk_edit_plate_number').val()),
                plateType:$.trim($('#bk_edit_plate_type').combo('getValue')),
                carcolor:$.trim($('#bk_edit_body_color').combo('getValues').join()),
                camera:$.trim($('#bk_edit_surveillance_cameraIds').combo('getText')),
                vehicleKind:$.trim($('#bk_edit_motorcycle_type').combo('getValue')),
                vehicleBrand: $.trim($("#bk_edit_brand").combobox("getValue")),
                vehicleSeries: $.trim($("#bk_edit_series").combobox("getValue")),
                vehicleStyle: $.trim($("#bk_edit_model").combobox("getValue")),
                tag:tag,
                paper:paper,
                sun:sun,
                drop:drop,
                peopleName:$.trim($('#bk_edit_surveillance_people_name').val()),
                peopleTel:$.trim($('#bk_edit_surveillance_people_tel').val()),
                unitName:$.trim($('#bk_edit_surveillance_unit_name').val()),
                detail:$.trim($('#bk_edit_surveillance_detail').val())
            };
    	return data;   	
    }
    
    //自动刷新表格
    function refreshDataGrid(){
    	datagrid.datagrid("reload");
    	setTimeout(function(){refreshDataGrid()},5000);
    }   
    refreshDataGrid();
    
})
</script>