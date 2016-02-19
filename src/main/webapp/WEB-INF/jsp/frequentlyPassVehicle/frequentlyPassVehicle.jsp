<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout"  fit="true" style="width:100%;height:100%;">
	<div data-options="region:'north',split:false" style="width:100%;height:6%;background: rgb(238, 238, 238);">
		<div style="margin-top:2px">
		<form id="frequentlyPV_form_id" action="#">
			&nbsp;&nbsp;&nbsp;&nbsp;<label>过车时间：</label>
			<input class="easyui-datetimebox" id="frequentlyPV_startTime_id" name="startTime" data-options="required:true" style="width:150px">
			<label>--</label>
			<input class="easyui-datetimebox" id="frequentlyPV_endTime_id" name="endTime" data-options="required:true" style="width:150px">
			&nbsp;&nbsp;&nbsp;&nbsp;<label>监控点：</label>
			<input id="frequentlyPV_camera_id" type="text"  data-options="required:true" style="width:180px;">
			<div href="javascript:void(0)" id="pfgc_map" class="easyui-linkbutton" data-options="iconCls:'icon-search'">地图</div>
			&nbsp;&nbsp;&nbsp;&nbsp;<label>过车频率(≥)：</label>
			<input class="easyui-numberbox" type="text" id="frequentlyPV_frequentlyRate_id" name="frequentlyRate" data-options="required:true,min:0"/>
			&nbsp;&nbsp;<a href="javascript:void(0)" id="frequentlyPV_queryButton_id" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</form>
		</div>
	</div>
	<div data-options="region:'center',split:false,border:false" style="width:100%;height:94%;">
		<div class="easyui-layout" fit="true" style="width:100%;height:100%;">
			<div region="west" split="true" title="频繁过车分析" style="width:50%;height:100%;background:#eee;">
				<div class="easyui-layout" style="width:100%;height:100%;">
					<div data-options="region:'west',title:'过车列表',split:true" style="width:30%;height:100%;">
						<div id="frequentlyPV_passvehicle_datagrid"></div>
					</div>
					<div  data-options="region:'center',split:true,border:false" style="width:35%;height:100%;">
						<div class="easyui-layout" data-options="" style="width:100%;height:100%;">
							<div data-options="region:'center',title:'监控点过车信息',split:false" style="width:100%;height:45%;">
								<div id="frequentlyPV_camera_passvehicle_datagrid"></div>
							</div>
							<div data-options="region:'south',title:'过车详细信息',split:false,collapsible:false" style="width:100%;height:55%;">
								<div id="frequentlyPV_detail_passvehicle_datagrid"></div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div data-options="region:'center',split:true" style="width:50%;height:100%;">
				<div>
					<div id='frequentlyPassVehicle_map' style="height: 100%; width: 100%; position: absolute;"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	(function(){
        var tab = util.getSelectedTab();
		//获取地图对象
		var $frequentlyPassVehicle_map=tab.find("#frequentlyPassVehicle_map"),$query_camera=tab.find("#frequentlyPV_camera_id"),
		//初始化地图
		frePass_map = initMap($frequentlyPassVehicle_map[0], new GLatLng(mapSettings.centerLat, mapSettings.centerLng), mapSettings.initialZoom),
		mc = new MarkerClusterer(frePass_map);
		var lm = {};
		
		var data = {};
		var detailFlage = false;
        var pictureServerHost = '${PictureServerHost}/';


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
		
		//查询按钮事件
		$("#frequentlyPV_queryButton_id").click(function(e){
			e.preventDefault();
			if($("#frequentlyPV_form_id").form("validate")){
				
			    //过车列表信息查询
			    $("#frequentlyPV_passvehicle_datagrid").datagrid({
			        url: '/frequentlyPassVehicle/queryPassVehicleList.action',
			        loadMsg: '数据正在加载,请耐心的等待...',
			        pagination: false,
			        pageNumber: 1,
			        pageSize: 20,
			        rownumbers: true,
			        fit: true,
			        singleSelect: true,
			        columns: [[
						{
						    field: 'license', title: '车牌', width: '50%'
						},
			            {
			                field: 'totalNumber', title: '过车次数', width: '40%'
			            }
			        ]],
			        onBeforeLoad:function(p){
			           $.extend(p,getParamData());
			        },
					onClickRow: function (index, row) {
				           if(detailFlage){
					            $("#frequentlyPV_detail_passvehicle_datagrid").datagrid("loadData",{total:0,rows:[]});
				           }
						data.license = row.license;
						queryPassVehicleByCameraList(data);
						
						//地图
						//清除地图
						frePass_map.clearOverlays();
						
						//显示轨迹
						$.post('/frequentlyPassVehicle/queryMapDataList.action',data,function(datas){
							lm = {};
	                    	//目标车轨迹
	                        lm = showTogetherTrail(datas.rows.sort(function (a,b) {
	                        	return a.resultTime>b.resultTime?1:-1;
	                        }),'blue',3,lm);
	                        //addLabelMarkers(lm);
	                     });
			        },
			        onDblClickRow: function (index, row) {
			            
			        }		        
			    });
			}
		});
		
		//查询监控点过车信息
		function queryPassVehicleByCameraList(data){
		    $("#frequentlyPV_camera_passvehicle_datagrid").datagrid({
		        url: '/frequentlyPassVehicle/queryPassVehicleByCameraList.action',
		        loadMsg: '数据正在加载,请耐心的等待...',
		        pagination: true,
		        pageNumber: 1,
		        pageSize: 10,
		        rownumbers: true,
		        fit: true,
		        singleSelect: true,
		        columns: [[
					{
					    field: 'license', title: '车牌', width: '30%'
					},
					{
		                field: 'cameraName', title: '监控点', width: '30%'
		            },
		            {
		                field: 'totalNumber', title: '过车次数', width: '30%'
		            },
		            {
		                field: 'cameraId', title: '监控点id', width: '0%',hidden:true
		            }
		        ]],
		        onBeforeLoad:function(p){
		           $.extend(p,data);
		        },
				onClickRow: function (index, row) {
					data.cameraId = row.cameraId;
					queryPassVehicleByDatilList(1,null);
		        },
		        onDblClickRow: function (index, row) {
		            
		        }		        
		    });
		}
		
		//查询过车详细信息
		function queryPassVehicleByDatilList(pageNumber,callback){
			var passDetailDatagrid = $("#frequentlyPV_detail_passvehicle_datagrid").datagrid({
		        url: '/frequentlyPassVehicle/queryPassVehicleByDatilList.action',
		        loadMsg: '数据正在加载,请耐心的等待...',
		        pagination: true,
		        pageNumber: pageNumber,
		        pageSize: 10,
		        rownumbers: true,
		        fit: true,
		        singleSelect: true,
		        columns: [[
					{
					    field: 'license', title: '车牌', width: '25%'
					},
					{
		                field: 'cameraID', title: '监控点', width: '25%',
		                formatter:function(value,row,index){                   
		                    return data.cameraId;
		                }
		            },
		            {
		                field: 'direction', title: '方向', width: '10%'
		            },
		            {
		                field: 'resultTime', title: '过车时间', width: '30%',formatter: util.formateTime
		            }
		        ]],
		        onBeforeLoad:function(p){
		           $.extend(p,data);
		           detailFlage = true;
		        },
				onClickRow: function (index, row) {
		            
		        },
		        onDblClickRow: function (index, row) {
		        	detail_window.open(row,pictureServerHost,passDetailDatagrid,queryPassVehicleByDatilList)
		        },
		        onLoadSuccess: function (pager) {
                    $.each(pager.rows, function (i, o) {
                        o.index = i;
                    });  
                    
                    if (typeof callback == 'function') {
                        callback();
                        //这个callback用于在查看结果详情的时候,翻下一张,翻页时弹出一个window显示结果详情
                        //若不设为null,在上面的翻页后datagrid loadSuccess(包括查询,刷新等)后依然会弹出一个window
                        callback = null;
                    }
                }
		    });
		}
		
		//获取参数
		function getParamData(){
			data.startTime = $("#frequentlyPV_startTime_id").combo("getValue");
			data.endTime = $("#frequentlyPV_endTime_id").combo("getValue");
			data.cameraIds = cameraIds.combo("getValues").join();
			data.frequentlyRate = $("#frequentlyPV_frequentlyRate_id").numberbox("getValue");
			return data;
		}

      //翻页
      var turnPage=function (index, str) {
          var dataRows = $("#frequentlyPV_detail_passvehicle_datagrid").datagrid("getRows"),
                  pageNum =  $("#frequentlyPV_detail_passvehicle_datagrid").datagrid('getPager').pagination('options').pageNumber,
                  pageSize = $("#frequentlyPV_detail_passvehicle_datagrid").datagrid('getPager').pagination('options').pageSize,
                  row;
          if (str == 'previous') {
              if (pageNum == 1 && index == 0) {
                  $.messager.show({
                      title: '提示消息',
                      msg: '已经是第一张',
                      timeout: 5000,
                      showType: 'slide'
                  });
                  return false;
              }
              if (index == 0) {
            	  loadResultDatas(--pageNum, function () {
                      var dataRows_1 = $("#frequentlyPV_detail_passvehicle_datagrid").datagrid("getRows");
                      showResultWindow(dataRows_1[pageSize - 1])
                  });
              } else {
            	  row = dataRows[--index]
                  showResultWindow(row)
              }
          }

          if (str == 'next') {
              if (pageNum == getPageCount() && index == dataRows.length - 1) {
                  $.messager.show({
                      title: '提示消息',
                      msg: '已经是最后一张',
                      timeout: 5000,
                      showType: 'slide'
                  });
                  return false;
              }
              if (index == dataRows.length - 1) {
            	  loadResultDatas(++pageNum, function () {
                      var dataRows_1 = $("#frequentlyPV_detail_passvehicle_datagrid").datagrid("getRows");
                      showResultWindow(dataRows_1[pageSize - 1])
                  });
              } else {
            	  row = dataRows[++index]
                  showResultWindow(row)
              }
          }
      	}
      


        /**
         *地图
         */
         //显示轨迹
        var showTogetherTrail=function (trails,strokeColor,weight,lm) {
            var markers = {};
            var rows = trails;
            var markerOptions = {icon: gIconCar, draggable: false};
            var invalidData = [], data = [], validDataRows = [];
            var degreesPerRadian = 180.0 / Math.PI;
            //箭头路径
            var imagePath;

            /**
             * 移动到第一个监控点
             */
            var c=rows[0];
            var LatLng = new GLatLng(+c.latitude, +c.longitude);

            //显示轨迹，自适应地图级别
			/*-----如果有两个监控点以上，则自适应的地图缩放级别----*/
			if(trails.length>1){
				//获取监控点直间的最小距离
				var minDistance =0.0; //监控点之间的最小距离
				for(var i=0;i<trails.length-1;i++){
					var lon1 = trails[i].longitude;  //经度1
					var lat1 = trails[i].latitude;   //纬度1

					//查看每个坐标与其他坐标的距离，并获取所以监控点之间的最小距离
					for(var j=i+1;j<trails.length;j++){
						var lon2 = trails[j].longitude; //经度2
						var lat2 = trails[j].latitude;   //纬度2
						if(trails[i].cameraID == trails[j].cameraID){
							continue;
						}
						var newDistance = getDistance(lon1, lat1, lon2, lat2);  //获取位置之间的距离
						//获取最短距离
						if(j==1){
							minDistance = newDistance
						}else if(newDistance!=0 &&minDistance > newDistance){
							minDistance = newDistance;
						}
					}
				}
				//通过监控点的最短距离获取自适应地图级别
				var initialZoom = getMapLevelByDistance(minDistance);
				if(initialZoom < 12){ //如果获取的级别小于12，则使用默认12
					frePass_map.setZoom(12);
				}else{
					frePass_map.setZoom(initialZoom);
				}
			}else{
				frePass_map.setZoom(12); //默认级别12
			}

            setTimeout(function () {
            	frePass_map.panTo(LatLng);
            });

            function checkLatLng(c) {
                //检测监控点坐标是否合法
                return c.latitude && c.latitude > 0 && c.longitude && c.longitude > 0;
            }
            rows.forEach(function (r) {
                if (checkLatLng(r)) {
                    if(r.cameraID in lm) {
                        lm[r.cameraID].push(r);
                    }else {
                        lm[r.cameraID] = [r];
                    }
                    markers[r.serialNumber] = showObject(frePass_map, r, markerOptions);
                    data.push(new GLatLng(r.latitude, r.longitude));
                    validDataRows.push(r);
                } else {
                    invalidData.push(r)
                }
            });
            if (invalidData.length > 0) {
                invalidData = invalidData.map(function (r) {
                    return r.license;
                }).join();
                $.messager.show({title: "显示轨迹", msg: invalidData + "缺失经纬度信息!!"});
            }
            
            if (data.length > 0) {
                if(!strokeColor) {
                    strokeColor='red';
                }
                imagePath = "/Gis_0001/images/"+strokeColor+"/img_arrow";
                
                //消除前一个定时器
                if(window.frequently_time_out){
                	clearTimeout(window.frequently_time_out);
                }
                //折点集合
                var spinodalPointList = new Array();
                //图标集合
                var gmarkerList = new Array();
                //label集合
                var labelMarkerList = new Array();
                //初始索引
                var gmkIndex = 0;
                             
                for(var i=0;i<validDataRows.length;i++){
                	//小车图标信息
                	var arrowIcon = new GIcon();
                    arrowIcon.iconSize = new GSize(24,60);
                    arrowIcon.iconAnchor = new GPoint(10, 20);
                    arrowIcon.image = "/Gis_0001/images/mycar.png";
                    var gmarker = new GMarker(new GLatLng(validDataRows[i].latitude, validDataRows[i].longitude), arrowIcon);
                    gmarkerList.push(gmarker);
                    
                    //label标签信息
                    var gsize = new GSize(15, -60);               	
                	var icon = new GIcon();
                    icon.image = "";
                    icon.dragCrossImage = null;
                    icon.iconSize = new GSize(120, 19);
                    icon.iconAnchor = new GPoint(-15, 60);
                    icon.infoWindowAnchor = new GPoint(5, 1);
                    var timeSign = "";
                    if(i == 0){
                    	timeSign = '&emsp;&emsp;<font size="2" color="red"> 开始 </font>';
                    }
                    if(i == validDataRows.length-1){
                    	timeSign = '&emsp;&emsp;<font size="2" color="red"> 结束 </font>';
                    }
                    var labelText = (validDataRows[i].location ? validDataRows[i].location : "无地点") +timeSign+'<br/>&emsp;&emsp;<font size="1">'+ validDataRows[i].license + '&emsp;' + util.formateTime(validDataRows[i].resultTime) + '</font>';
                    var label = new GLabelMarker(new GLatLng(validDataRows[i].latitude, validDataRows[i].longitude),{labelText:labelText,labelOffset:gsize,draggable:true,icon:icon});
                    labelMarkerList.push(label);
                                        
                }
                
                
                //画箭头
                for (var i = 0, len = data.length - 1; i < len; i++) {
                    var p1 = data[i], p2 = data[i + 1];
                    //
                    var line = new GPolyline([p1,p2], strokeColor, weight, 1);
                    frePass_map.addOverlay(line);
                    //
                    var dir = bearing(p1,p2); 
                    
                    var offsetBase = 16;
                    //alert(dir + ":" + Math.sin(dir*2*Math.PI/360) + ":" + Math.cos(dir*2*Math.PI/360));
                    var hOffset = Math.round(offsetBase * Math.sin(dir*2*Math.PI/360));
                    var vOffset = Math.round(offsetBase * Math.cos(dir*2*Math.PI/360));
                    //alert(dir*2*Math.PI/360 + ":" + hOffset + ":" + vOffset);
                    var dir = Math.round(dir/3) * 3;
                    while (dir >= 120) {dir -= 120;}
                    var arrowIcon = new GIcon();
                    arrowIcon.iconSize = new GSize(24,24);
                    arrowIcon.iconAnchor = new GPoint(12+hOffset, 12-vOffset);
                    arrowIcon.image = imagePath + "/dir_"+dir+".png";
                    var gmarker = new GMarker(data[i + 1], arrowIcon);
                    //frePass_map.addOverlay(gmarker);
                }
                
                
                function myTimer(){
                	if(gmkIndex >= gmarkerList.length){
                		gmkIndex = 0;
                		frePass_map.removeOverlay(gmarkerList[gmarkerList.length-1]);
                		frePass_map.removeOverlay(labelMarkerList[gmarkerList.length-1]);
                	}
                	
                	frePass_map.addOverlay(gmarkerList[gmkIndex]);
                	frePass_map.addOverlay(labelMarkerList[gmkIndex]);
                	if(gmkIndex>0){
                		frePass_map.removeOverlay(gmarkerList[gmkIndex-1]);
                		frePass_map.removeOverlay(labelMarkerList[gmkIndex-1]);
                	}
                	               	
                	gmkIndex++;
            		window.frequently_time_out = setTimeout(function(){myTimer()},3000);
	
            	}
                
                myTimer();
            }
            
            //判断是否有重复值
            function judgeRepeatePoint(array,point){
            	var flage = false;
            	for(var i=0;i<array.length;i++){
            		var item = array[i];
            		if(item.lat() == point.lat() && item.lng() == point.lng()){
            			flage = true;
            			break;
            		}
            	}
            	
            	return flage;
            }

            function bearing( from, to ) {
                var lat1 = from.latRadians();
                var lon1 = from.lngRadians();
                var lat2 = to.latRadians();
                var lon2 = to.lngRadians();
                var angle = - Math.atan2( Math.sin( lon1 - lon2 ) * Math.cos( lat2 ), Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( lon1 - lon2 ) );
                if ( angle < 0.0 ) {
                    angle += Math.PI * 2.0;
                }
                angle = angle * degreesPerRadian;
                angle = angle.toFixed(1);
                return angle;
            }
            return lm;
        }
        
        var addLabelMarkers = function(obj){
            $.each(obj,function(cameraID,resultVOArr){
                var labelText="";
                var latLng;
                var passInfo="";
                for(var i= 0,len= resultVOArr.length;i < len;i++) {
                    passInfo = resultVOArr[i].location;
                    labelText += '&emsp;&emsp;<font size="1">'+ resultVOArr[i].license + '&emsp;' + util.formateTime(resultVOArr[i].resultTime) + '</font><br/><hr>';
                    latLng = new GLatLng(resultVOArr[i].latitude, resultVOArr[i].longitude)
                }
                passInfo = passInfo+'<br/>'+labelText;
                addLabelMarker(latLng,passInfo);
            });
        }
        
        var addLabelMarker = function(latLng, labelText) {
        	var gsize = new GSize(15, 15);
        	
        	var icon = new GIcon();
            icon.image = "";
            icon.dragCrossImage = null;
            icon.iconSize = new GSize(120, 19);
            icon.iconAnchor = new GPoint(-15, -15);
            icon.infoWindowAnchor = new GPoint(5, 1);
            
            var label = new GLabelMarker(latLng,{labelText:labelText,labelOffset:gsize,draggable:true,icon:icon});
            frePass_map.addOverlay(label);
            return label;
        }
        
      
      
      camera = (function () {
          var datas = {};
          return {
              toArr: function () {
                  var arr = [];
                  for (var k in datas)arr.push(datas[k]);
                  return arr;
              },
              hasCached: function (dto) {
                  return !!datas[dto.id];
              },
              cache: function (id, obj) {
                  datas[id] = obj;
              },
              getMarker: function (id) {
                  return datas[id] && datas[id].marker;
              },
              getDto: function (id) {
                  return datas[id] && datas[id].dto;
              },
              setDto: function (dto) {
                  return datas[dto.id].dto = dto;
              },
              aferShowInBounds: $.noop,
              checkLatLng: function (c) {
                  //检测监控点坐标是否合法
                  return c.latitude > -90 && c.latitude < 90 && c.longitude > -180 && c.longitude < 180;
              },
              showInBounds: function (b, hasLoad) {
                  if (hasLoad) {
                      //camera.aferShowInBounds();
                      //return;
                  }
                  //最多取多少个监控点
                  var maxSize = 1000,
                          s = b.getSouthWest(),
                          n = b.getNorthEast();
                  $.ajax({
                      url: '/camera/list.action',
                      type: 'post',
                      data: {
                          minLat: s.lat(), minLng: s.lng(),
                          maxLat: n.lat(), maxLng: n.lng(),
                          page: 1,
                          rows: maxSize
                      },
                      success: function (d) {
                          var cameras = d.p.rows;
                          //cameras.forEach(showSingleCamera);
                          util.bat(cameras.length, function (i) {
                              showSingleCamera(cameras[i - 1]);
                          });

						  /*-----如果有两个监控点以上，则自适应的地图缩放级别----*/
						  if(cameras.length>1){
							  //获取监控点直间的最小距离
							  var minDistance =0.0; //监控点之间的最小距离
							  for(var i=0;i<cameras.length-1;i++){
								  var lon1 = cameras[i].longitude;  //经度1
								  var lat1 = cameras[i].latitude;   //纬度1

								  //查看每个坐标与其他坐标的距离，并获取所以监控点之间的最小距离
								  for(var j=i+1;j<cameras.length;j++){
									  var lon2 = cameras[j].longitude; //经度2
									  var lat2 = cameras[j].latitude;   //纬度2
									  var newDistance = getDistance(lon1, lat1, lon2, lat2);  //获取位置之间的距离
									  //获取最短距离
									  if(j==1){
										  minDistance = newDistance
									  }else if(minDistance > newDistance){
										  minDistance = newDistance;
									  }
								  }
							  }
							  //通过监控点的最短距离获取自适应地图级别
							  var initialZoom = getMapLevelByDistance(minDistance);
							  if(initialZoom < 12){ //如果获取的级别小于12，则使用默认12
								  frePass_map.setZoom(12);
							  }else{
								  frePass_map.setZoom(initialZoom);
							  }
							  //移动到第一个监控点
							  var LatLng = new GLatLng(+cameras[0].latitude, +cameras[0].longitude);
							  setTimeout(function () {
								  frePass_map.panTo(LatLng);
							  });
						  }

                          camera.aferShowInBounds();
                      }
                  });
              }
          };
      })();

    //显示单个监控点
      var showSingleCamera = function (dto) {
                  if (camera.hasCached(dto))return;
                  if (!camera.checkLatLng(dto))return;
                  var updateAction = "/camera/update.action";
                  var markerOptions = {icon: gIconCamera, draggable: true};
                  if (!dto.status) {
                      markerOptions = {icon: gIconCameraInvalid, draggable: true};
                  }
                  var marker = showObject(frePass_map, dto, markerOptions, updateAction, function () {
                      datagrid.datagrid("reload");
                  });
                  //将添加到地图上的marker移除,由聚合框架来控制marker的显示
                  frePass_map.removeOverlay(marker);
                  camera.cache(dto.id, {dto: dto, marker: marker});
                  marker.dto = dto;
                  GEvent.addListener(marker, "click", function () {
                      openInfoWindowHtml(marker);
                  });
                  mc.addMarker(marker);
                  // TODO 聚合框架控制监控点显示会导致公共方法中的右键点击事件失效，需要重新设计屏蔽marker右键菜单
                  //给marker的界面标签加上禁止右键点击的属性，防止IE下点击marker时弹出浏览器的contextmenu
                  //$("img.gmnoprint").attr("oncontextmenu","self.event.returnValue=false");
                  return marker;
              }
    
      		map_camera_info_tmpl = $("#map_camera_info_tmpl").html();
      		openInfoWindowHtml = function (marker) {
          		var infoWindowHtml = util.parser(map_camera_info_tmpl, marker.dto);
          		marker.openInfoWindowHtml(infoWindowHtml);
      		}

      //载入页面时加载监控点
      camera.showInBounds(frePass_map.getBounds());

		$("#frequentlyPV_form_id").on('click', '#pfgc_map', function (e) {
			e.preventDefault();
			pubMapWindow.open(cameraIds,camIdArr,camNameArr);
		});
	})();
</script>