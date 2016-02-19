<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div id="manual_scar_tabs" class="easyui-tabs" data-options="fit:true,border:false,tabWidth:100">
<div title="搜车条件">	
<div class="easyui-layout" style="width:100%;height:100%;">
<div data-options="region:'west'" style="padding:1px;width:50%;height:100%">
<div class="easyui-layout" data-options="fit:true" style="width:100%;height:100%;">
<div data-options="region:'center'">
<div align="center">
<div id="manual_scar_draw_line"></div>
</div>
</div>
<div data-options="region:'south',minHeight:'186px'">
<table cellspacing="2" cellpadding="0" style="text-align:left;margin-top:5px;" border="0px">
<tbody>
<tr><td colspan="2" align="left">
<form id="manual_sendImageForm" enctype="multipart/form-data">
&nbsp;&nbsp; 选择图片：<input id="manual_chooseImageButton" name="files"  type="file" style="width: 80%;height: 25px;">
</form> 
</td></tr>
<tr><td width="60%">
<fieldset style="padding:5px">
       <legend>注意：</legend>
       <div style="color:red;margin-top:3px;">1,点击“画车牌”按钮开始画车牌区域（必须紧扣车牌框），点击“画特征区域”画出特征区。</div>
       <div style="color:red;margin-top:3px;">2,必须对目标车辆画出特征区域，否则无法搜索到车辆，支持多个特征区域。</div>
       <div style="color:red;margin-top:3px;">3,特征区域：点击图片开始，双击结束。</div>
</fieldset>
</td>
<td width="40%" align="center">
<div>
<div style="margin-bottom:5px;float:left;position:relative;left:10px">
<a id="scar_drawPlate_btn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-pen'" style="">画车牌&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
</div> 
<div style="margin-bottom:5px;float:left;position:relative;left:20px">
<a id="clear_drawPlate_btn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-clear'" style="">清除车牌&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
</div>
</div>
<div>
<div style="margin-top:5px;float:left;position:relative;left:10px">
<a id="scar_drawFeature_btn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-pen'" style="">画特征区域</a>
</div> 
<div style="margin-top:5px;float:left;position:relative;left:15px">
<a id="clear_Feature_btn" href="javascript:void(0)" class="easyui-linkbutton"  data-options="iconCls:'icon-clear'" style="">清除特征区域</a>
</div>
</div>  
</td>
</tr>
<tr  style="text-align:right">
<td>
</td>
</tr>
</tbody>
</table>
</div>
</div>
</div>

<div data-options="region:'center'" style="padding:5px;width:50%;height:100%">
<table height="80%" width="100%" cellspacing="2" cellpadding="0" style="text-align:left;" border="0px">
<tr>
<td>车牌号码：<input id="manual_sercar_licens_id" class="easyui-textbox" style="width:180px;"></td>
<td><input type="checkbox" id="manual_sercar_licens_checkbox" value="1"/>&nbsp;选用</td>
</tr>
<tr>
<td> 车牌种类：<select id="manual_sercar_palate_type_id" class="easyui-combobox" style="width:180px;"></select></td>
<td><input type="checkbox" id="manual_sercar_palate_type_checkbox" value="2" checked="true" />&nbsp;选用</td>
</tr>
<tr>
<td> 车辆类型：<select id="manual_sercar_vehicle_kind_id" class="easyui-combobox" style="width:180px;"></select></td>
<td><input type="checkbox" id="manual_sercar_vehicle_kind_checkbox" value="3" checked="true" />&nbsp;选用</td>
</tr>
<tr>
<td>车辆颜色：<select id="manal_sercar_car_color_id" class="easyui-combobox" style="width:180px;"></select></td>
<td><input type="checkbox" id="manual_sercar_car_color_checkbox" value="4" checked="true" />&nbsp;选用</td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;品牌：<input id="manual_sercar_brand_id" style="width:180px;"></td>
<td><input type="checkbox" id="manual_sercar_brand_checkbox" value="5" checked="true" />&nbsp;选用</td>
</tr>
<tr>
<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;车系：<input id="manual_sercar_series_id" style="width:180px;"></td>
<td><input type="checkbox" id="manual_sercar_series_checkbox" value="6" checked="true" />&nbsp;选用</td>
</tr>
<tr>
<td >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款型：<input id="manual_sercar_style_id" style="width:180px;"></td>
<td><input type="checkbox" id="manual_sercar_style_checkbox" value="7" checked="true" />&nbsp;选用</td>
</tr>
<tr>
<td >&nbsp;&nbsp;&nbsp;监控点：<input id="manual_sercar_camer_id" type="text"  style="width:180px;"></td>
<td><input type="checkbox" id="manual_sercar_camer_checkbox" value="8" checked="true" />&nbsp;选用</td>
</tr>
<tr>
<td >过车时间：<input id="manual_sercar_startime_id" type="text" style="width:180px;">
-- <input id="manual_sercar_endtime_id" type="text"  style="width:180px;">
</td>
<td><input type="checkbox" id="manual_sercar_time_checkbox" value="9" checked="true" />&nbsp;选用</td>
</tr>
</table>
<div style="text-align:center;margin-top:50px"><a id="manual_sercar_serarch_btn_id" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="">搜索车辆</a></div>	
</div>
</div>
</div>
</div>

<script type="text/javascript">
(function () {
	var drawPlageFlage;
	var drawFeatureFlage;
	var paper;
	var vlprTaskId;
	var serialNumber;
	var task;
	var plate_polygons = [], feature_polygons = [];
	//选择图片触发函数
	$('#manual_chooseImageButton').change(function(){
		var FileListType = "bmp|png|jpg|jpeg|gif|tiff|tif";
		var fileSrc = $("#manual_chooseImageButton").val();
	
		var ldot = fileSrc.lastIndexOf(".");
		var suffix = fileSrc.substring(ldot+1);
		if(suffix){
			suffix=suffix.toLowerCase();
		}
		if(FileListType.indexOf(suffix) == -1){
			$.messager.show({ 
			title:'文件错误:', 
			msg:'图片类型不正确，请重新选择! 目前仅支持bmp png jpg jpeg gif tiff tif图片', 
			timeout:5000, 
			showType:'slide',
			height:'20%'
			}); 
		}else{
			//处理图片
			operateImageProcess(fileSrc);
		}	
	});
		
	//处理图片函数
	function operateImageProcess(path){
        if(path.trim() == ""){
        	return;
        }
        
        plate_polygons=[];
        feature_polygons = [];
        
        if(paper){
        	paper.remove();
        	$("#scar_drawPlate_btn").unbind("click");
        	$("#scar_drawFeature_btn").unbind("click");
        }
        $("#manual_sendImageForm").ajaxSubmit({
        	type:"post",
        	url:"/serachCarByImage/addImageFile.action",
        	data:{'cId':0,'dataType':1,'type':'image'},
        	success:function(data){
        		sercarFlag = false;
        		serCarClear();
        		task = null;
        		data = JSON.parse(data);
        		path=data.bigUrl;
        		var img = new Image();
                img.src = path;
                img.onload = function () {
                    paper = Raphael('manual_scar_draw_line', img.width,img.height),
                    image = paper.image(path, 0, 0, img.width, img.height);
                    
                    var plate_drawing = false, feature_drawing = false;
                                      
                    //参数对象
                 	task = {
                    	id: data.id,
                    	width: img.width,
                    	height: img.height
                    };
                  
                 
                 //画车牌
                 $("#scar_drawPlate_btn").click(function (){              	                	 
                	 drawPlageFlage = true;
                	 drawFeatureFlage = false;
                	 
                	 image.unclick();
                	 drawPlateArea(image,plate_drawing,plate_polygons);
                 });
                 
                 //画特征区域
                 $("#scar_drawFeature_btn").click(function (){
                	 drawPlageFlage = false;
                	 drawFeatureFlage = true;
                	 
                	 image.unclick();
                	 drawFeatureArea(image,feature_drawing,feature_polygons)
                 });
                 
               	 //重画车牌
                 $("#clear_drawPlate_btn").click(function (){
	                image.unclick();
	                	 
	                plate_drawing = false;
	                plate_polygons.forEach(function (e) {
	                	e.el.remove();
	                });
	                plate_polygons = [];	      
                 });
                 
                 //重画特征区域
                 $("#clear_Feature_btn").click(function (){
	                image.unclick();	                
	                	 
	                feature_drawing = false;
	                feature_polygons.forEach(function (e) {
	                	e.el.remove();
	                });
	                feature_polygons = [];
                 });                 
                                                                                  
                }              
        		
        		return false;
         }    
        });
        
	}
	
	//画车牌
	function drawPlateArea(image,drawing,polygons){
		  //单击开始画多边形，双击结束
        image.click(function (e) {
            if (!drawing) {
                //如果开始画多边形，就用一个数组存一下
                var el = paper.path(), points = [];
                polygons.push({el: el, points: points});
                drawing = true;
                //开始画线后线处于最上层，所以得在线上绑定事件
                el.click(function (e) {
                    if (drawing) {
                        var coordinate = getCoordinate(e);
                        points.push(coordinate);
                    }
                }).mousemove(function (e) {
                    if (!drawing)return
                    var polygon = polygons[polygons.length - 1],
                            coordinate = getCoordinate(e),
                            points = polygon.points.concat([coordinate]);
                    el.attr({
                        stroke: "red",
                        fill: 'red',
                        'fill-opacity': 0.2,
                        path: generatePath(points)
                    })
                }).dblclick(function () {            	
                    drawing = false;
                    points.pop();
                    el.unclick().undblclick().unmousemove();
                })
            }
            var coordinate = getCoordinate(e);
            polygons[polygons.length - 1].points.push(coordinate);
        }).mousemove(function (e) {
            if (!drawing)return
            var polygon = polygons[polygons.length - 1],
                    coordinate = getCoordinate(e),
                    points = polygon.points.concat([coordinate]);
            polygon.el.attr({
                stroke: "red",
                fill: 'red',
                'fill-opacity': 0.2,
                path: generatePath(points)
            })
        });
	}
	
	//画特征区域
	function drawFeatureArea(image,drawing,polygons){
		  //单击开始画多边形，双击结束
      image.click(function (e) {
          if (!drawing) {
              //如果开始画多边形，就用一个数组存一下
              var el = paper.path(), points = [];
              polygons.push({el: el, points: points});
              drawing = true;
              //开始画线后线处于最上层，所以得在线上绑定事件
              el.click(function (e) {
                  if (drawing) {
                      var coordinate = getCoordinate(e);
                      points.push(coordinate);
                  }
              }).mousemove(function (e) {
                  if (!drawing)return
                  var polygon = polygons[polygons.length - 1],
                          coordinate = getCoordinate(e),
                          points = polygon.points.concat([coordinate]);
                  el.attr({
                      stroke: "yellow",
                      fill: 'red',
                      'fill-opacity': 0.2,
                      path: generatePath(points)
                  })
              }).dblclick(function () {            	
                  drawing = false;
                  points.pop();
                  el.unclick().undblclick().unmousemove();
              })
          }
          var coordinate = getCoordinate(e);
          polygons[polygons.length - 1].points.push(coordinate);
      }).mousemove(function (e) {
          if (!drawing)return
          var polygon = polygons[polygons.length - 1],
                  coordinate = getCoordinate(e),
                  points = polygon.points.concat([coordinate]);
          polygon.el.attr({
              stroke: "yellow",
              fill: 'red',
              'fill-opacity': 0.2,
              path: generatePath(points)
          })
      });
	}
   
   //取坐标
   var getCoordinate = function (e) {
        return {
            x: e.offsetX || e.layerX,
            y: e.offsetY || e.layerY
        }
    }
    
   //生成路径
    var generatePath = function (arr) {
        return arr.map(function (p, i) {
                    return (i == 0 ? 'M' : "L") + p.x + ',' + p.y
                }).join('') + 'Z';
    };
		
	//下拉框填值	
	$('#manual_sercar_palate_type_id').combobox({
			valueFiled:'value',
			textField:'text',
			panelHeight:350, 
			value:'',
			data:[
			{
                value: '0',
                text: '未知车牌'
            },{
                value: '1',
                text: '蓝牌'
            },{
                value: '2',
                text: '黑牌'
            },{
                value: '3',
                text: '单排黄牌'
            },{
                value: '4',
                text: '双排黄牌（大车尾牌，农用车）'
            },{
                value: '5',
                text: '警车车牌'
            },{
                value: '6',
                text: '武警车牌'
            },{
                value: '7',
                text: '个性化车牌'
            },{
                value: '8',
                text: '单排军车'
            },{
                value: '9',
                text: '双排军车'
            },{
                value: '10',
                text: '使馆牌'
            },{
                value: '11',
                text: '香港牌'
            },{
                value: '12',
                text: '拖拉机牌'
            },{
                value: '13',
                text: '澳门牌'
            },{
                value: '14',
                text: '厂内牌'
            }] 		
	});
	
 	function changePlageValue(tx){
		if(tx =='未知车牌'){
			return 0;
		}else if(tx=='蓝牌'){
			return 1;
		}else if(tx=='黑牌'){
			return 2;
		}
		else if(tx=='单排黄牌'){
			return 3;		
		}
		else if(tx=='双排黄牌（大车尾牌，农用车）'){
			return 4;
		}
		else if(tx=='警车车牌'){
			return 5;
		}
		else if(tx=='武警车牌'){
			return 6;
		}
		else if(tx=='个性化车牌'){
			return 7;
		}
		else if(tx=='单排军车'){
			return 8;
		}
		else if(tx=='双排军车'){
			return 9;
		}
		else if(tx=='使馆牌'){
			return 10;
		}
		else if(tx=='香港牌'){
			return 11;
		}
		else if(tx=='拖拉机牌'){
			return 12;
		}
		else if(tx=='澳门牌'){
			return 13;
		}else{
			return 14;
		}
	}
	
	$('#manual_sercar_vehicle_kind_id').combobox({
        url:"js/json/vehicleKind.json",
        valueField:"value",
        textField:"text"
    });
	
	$('#manal_sercar_car_color_id').combobox({
		valueFiled:'value',
		textField:'text',
		panelHeight:250,
		value:'',
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
	
	
    // 一层Combo
    var vehicleBrand = $("#manual_sercar_brand_id").combobox({
        //editable:false, //不可编辑状态  品牌太多 支持匹配
        url: '/brandmodel/listbrand.action',
        dataType: 'json',
        type:'post',
        onChange:function(record){  //onSelect 用户点击时触发的事件  在此的意义在于，用户点击一级后自动二级combobox
            $.ajax({
                type:'post',
                url: '/brandmodel/listsubbrand.action',
                data:{brandName : $("#manual_sercar_brand_id").combobox("getValue")},
                success:function(d){
                    var data=[];
                    $.each(d,function(i,o) {
                        var obj={value: o.carSeries,text: o.carSeries};
                        data.push(obj);
                    })
                    vehicleSeries.combobox({
                        onLoadSuccess:function(){  //清空三级下拉框 就是成功加载完触发的事件 当一级combobox改变时，二级和三级就需要清空
                            vehicleStyle.combobox("clear");
                            vehicleSeries.combobox("clear");
                        },
                        data:data,
                        onChange:function(record) {

                            $.ajax({
                                type:'post',
                                url: '/brandmodel/listcar.action',
                                data:{brandName : $("#manual_sercar_brand_id").combobox("getValue"),carSeries : $("#manual_sercar_series_id").combobox("getValue")},
                                success:function(d){
                                    var data=[];
                                    $.each(d,function(i,o) {
                                        var obj={value: o.modelsName,text: o.modelsName};
                                        data.push(obj);
                                    })
                                    vehicleStyle.combobox({
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
    var vehicleSeries = $("#manual_sercar_series_id").combobox({
        //editable:false,
        value:''
    });

    //三层Combo
    var vehicleStyle=$("#manual_sercar_style_id").combobox({
        //editable:false,
        value:''
    });

    var $query_camera = $('#manual_sercar_camer_id');
    //存放被选择的监控点
    var camIdArr = [],camNameArr = [];
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
    //过车时间
    $('#manual_sercar_startime_id').datetimebox({
        editable: false
    });
    $('#manual_sercar_endtime_id').datetimebox({
        editable: false
    });
    
    //获取搜索条件参数
    function getParamater(){
    	var data = {};
     	//车牌号码
    	if($("#manual_sercar_licens_checkbox").prop("checked")==true){    		
    		data.license = $("#manual_sercar_licens_id").textbox("getValue");
    	}
    	//车牌种类
		if($("#manual_sercar_palate_type_checkbox").prop("checked")==true){
			data.plateType=$("#manual_sercar_palate_type_id").combobox("getValue");
    	}
    	//车辆类型
		if($("#manual_sercar_vehicle_kind_checkbox").prop("checked")==true){
			data.vehicleKind=$("#manual_sercar_vehicle_kind_id").combobox("getValue");
    	}
    	//车辆颜色
		if($("#manual_sercar_car_color_checkbox").prop("checked")==true){
			data.vehicleColor=$("#manal_sercar_car_color_id").combobox("getValue");
    	}
    	//品牌
		if($("#manual_sercar_brand_checkbox").prop("checked")==true){
			data.vehicleBrand=$("#manual_sercar_brand_id").combobox("getValue");
    	}
    	//车系
		if($("#manual_sercar_series_checkbox").prop("checked")==true){
			data.vehicleSeries=$("#manual_sercar_series_id").combobox("getValue");
    	}
    	//款型
		if($("#manual_sercar_style_checkbox").prop("checked")==true){
			data.vehicleStyle=$("#manual_sercar_style_id").combobox("getValue");
    	}
    	//监控点
		if($("#manual_sercar_camer_checkbox").prop("checked")==true){
			data.cameraId=$("#manual_sercar_camer_id").combobox("getValues").join();
    	}
    	//过车时间
		if($("#manual_sercar_time_checkbox").prop("checked")==true){
			data.passStartTime=$("#manual_sercar_startime_id").combo("getValue");
			data.passEndTime=$("#manual_sercar_endtime_id").combo("getValue");
    	}
    	
    	return data;
    }
    
	//搜车事件
	$("#manual_sercar_serarch_btn_id").click(function (){
		if(plate_polygons.length==0){
			//弹出提示框
	    	$.messager.show({
	             title: '提示消息',
	             msg: '请画车牌位置！',
	             timeout: 5000,
	             showType: 'slide'
	         });
			return;
		}
		
		if(plate_polygons.length>1){
			//弹出提示框
	    	$.messager.show({
	             title: '提示消息',
	             msg: '只能画一个车牌位置！',
	             timeout: 5000,
	             showType: 'slide'
	         });
			return;
		}
		
		if(feature_polygons.length==0){
			//弹出提示框
	    	$.messager.show({
	             title: '提示消息',
	             msg: '请画特征区！',
	             timeout: 5000,
	             showType: 'slide'
	         });
			return;
		}
		
		var para = getParamater();
		if(!para.plateType){
			para.plateType = -1;
		}
		if(!para.passStartTime){
			para.passStartTime = '0000-00-00 00:00:00';
		}
		if(!para.passEndTime){
			para.passEndTime = '0000-00-00 00:00:00';
		}
		
		//车牌位置
		var plateFollowarea = plate_polygons.map(function (polygon) {
            	return polygon.points.map(function (p) {
                			return p.x + ',' + p.y;
            		   }).join('|');
        	}).join('#');
		//特征区域位置
		var featurFollowarea = feature_polygons.map(function (polygon) {
            	return polygon.points.map(function (p) {
                	return p.x + ',' + p.y;
            	}).join('|');
        	}).join('#'); 
		
		$.post("/serachCarByImage/saveManualVlprVfmTask.action",
				{'vlprTaskId':vlprTaskId,
				 'serialNumber':serialNumber,
			     'license':para.license,
			     'plateType':para.plateType,
			     'vehicleKind':para.vehicleKind,
			     'vehicleColor':para.vehicleColor,
			     'vehicleBrand':para.vehicleBrand,
			     'vehicleSeries':para.vehicleSeries,
			     'vehicleStyle':para.vehicleStyle,
			     'cameraId':para.cameraId,
			     'passStartTime':para.passStartTime,
			     'passEndTime':para.passEndTime,
			     'width':task.width,
			     'height':task.height,
			     'plateFollowarea':plateFollowarea,
			     'featurFollowarea':featurFollowarea,
			     'dsId':task.id
			     },function (data){
			    
			//以图搜车任务id
			var vlprVfmTaskId = data;
			//显示搜车结果tab
			if($("#manual_scar_tabs").tabs("exists","搜车结果")){
				$("#manual_scar_tabs").tabs("close","搜车结果");
			}
			$("#manual_scar_tabs").tabs("add",{
				title:"搜车结果",
				closable:true,
				href:"/serachCarByImage/serachManualIndex.action",
				onLoad:function (){
					scar_loadResultDatas(10,vlprVfmTaskId,vlprTaskId,serialNumber);
				}
			});			
			
		});
	});
	
	//清理函数
	function serCarClear(){
		//车牌号码
    	$("#manual_sercar_licens_id").textbox("clear");
    	//车牌种类
		$("#manual_sercar_palate_type_id").combobox("clear");
    	//车辆类型
		$("#manual_sercar_vehicle_kind_id").combobox("clear");
    	//车辆颜色
		$("#manal_sercar_car_color_id").combobox("clear");
    	//品牌
		$("#manual_sercar_brand_id").combobox("clear");
    	//车系
		$("#manual_sercar_series_id").combobox("clear");
    	//款型
		$("#manual_sercar_style_id").combobox("clear");
    	//监控点
		$("#manual_sercar_camer_id").combobox("clear");
    	//过车时间
		$("#manual_sercar_startime_id").combo("clear");
		$("#manual_sercar_endtime_id").combo("clear");
	}
		
})()
</script>
