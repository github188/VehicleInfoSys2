//设置自定义图标
var gIconCase = customIcon("images/IconCase.png");//案件图标
var	gIconCaseInvalid = customIcon("images/IconCaseInvalid.png");//失效案件图标
var gIconCamera = customIcon("images/IconCamera.png");//监控点图标
var	gIconCameraInvalid = customIcon("images/IconCameraInvalid.png");//失效监控点图标
var gIconPerson = customIcon("images/person.png");
var gIconCar = customIcon("images/car.png");


var str_Confirm_MoveConfirm = "是否确认移动到该位置？";

/** ----------Commonfunction--------- **/
//去除字符串前后空格的代码，写了这个之后就可以用trim（），不写的话许多浏览器将无法解析trim（）函数
String.prototype.trim = function () {
	return this .replace(/^\s\s*/, '' ).replace(/\s\s*$/, '' );
}


//初始化地图
function initMap(element, center, zoom) {
	var map = new GMap2(element);
	
	/**
	 * 加载离线地图需要的操作
	 */
	map.addMapType(normalMap);
	//map.addMapType(satTileMap);
	map.removeMapType(G_PHYSICAL_MAP);
	map.removeMapType(G_SATELLITE_MAP);
	map.removeMapType(G_HYBRID_MAP);
	map.removeMapType(G_NORMAL_MAP);
	map.setMapType(normalMap);
	
	
	
	map.disableDoubleClickZoom();
	map.enableScrollWheelZoom();
	map.addControl(new GLargeMapControl());
	map.addControl(new GMapTypeControl());
	map.setCenter(center, zoom);
	map.getPane(G_MAP_MARKER_SHADOW_PANE).style.display = "none"; 	
	map.getPane(G_MAP_FLOAT_SHADOW_PANE).style.display = "none";
	
	/**解决窗口大小改变后，地图部分缺失的bug，每次改变窗体大小时都刷新地图尺寸
	*  checkResize():地图api中用于刷新地图尺寸的函数
	*/
	$(element).parent().panel({
		fit:true,
		onResize:function(){map.checkResize();}
	});
	
	return map;
}
function refreshMap(map) {
	map.checkResize();
	map.setCenter(new GLatLng(mapSettings.centerLat, mapSettings.centerLng));
	map.setZoom(mapSettings.initialZoom);
}
//自定义图标
function customIcon(imageURL) {
	var vIcon = new GIcon(G_DEFAULT_ICON);
	vIcon.iconSize = new GSize(20, 20);
	vIcon.iconAnchor = new GPoint(10, 10);
	vIcon.infoWindowAnchor = new GPoint(10, 0);

	
	vIcon.image = imageURL;
	
	return vIcon;
}

//显示单个对象标记
function showObject(map, object, markerOptions, updateAction,dragendCallback) {
	var marker = new GMarker(new GLatLng(object.latitude, object.longitude), markerOptions); 
	map.addOverlay(marker); 
	
	var tempLatLng = null;
	//添加标记事件

	GEvent.addListener(marker, "dragstart", function(){
		marker.closeInfoWindow();
		tempLatLng = marker.getLatLng();
	});
	GEvent.addListener(marker, "dragend", function(newLatLng){
		$.messager.confirm('移动','是否确认移动？',function(r){
			if(r) {
				object.longitude = newLatLng.lng();
				object.latitude = newLatLng.lat();
				$.ajax( {
					type : "POST",
					url : updateAction,
					data: object,
					success:dragendCallback||$.noop,
					error:function(){
					   marker.setLatLng(tempLatLng);
				    }
				});
			} else {
				marker.setLatLng(tempLatLng);
			}
		});
	});
	return marker;
}

/********   右侧列表栏   ********/

function createRightBar(map) {
	var rightBar = document.createElement("div");
	//rightBar.style.visibility = "hidden";
	map.getContainer().appendChild(rightBar);
	
	var pos = new GControlPosition(G_ANCHOR_TOP_RIGHT, new GSize(0, 0));
	pos.apply(rightBar);
	
	rightBar.innerHTML ="" +
	"<div id='rightBar' class='rightBar ui-widget-content ui-corner-all'>" +
		"<table id='caseList'></table>" +
		"<div id='pager'></div>" +
	"</div>";
	
	return rightBar;
}


/**
 * 已废弃的代码，作用是通过地址栏传参将对象添加到地图，即从客户端添加到地图
 * @param map
 * @param addAction
 * @param objectType
 * @return
 */
function addObjectOnMap(map, addAction, objectType) {
	var xPos = getParameter("xPos");
	var yPos = getParameter("yPos");
	var objectId = getParameter("objectId");
	var objectDesc = getParameter("objectDesc");
	var screenPoint = getOriginScreenPoint(xPos, yPos);
	var objectLatLng = map.fromDivPixelToLatLng(screenPoint);
	//var objectLatLng = new GLatLng(22.5, 114);
	
	var object = null;
	
	if(objectType == 1) {
		object = {"caseId":objectId,"longitude":objectLatLng.lng(),"latitude":objectLatLng.lat(),"description":objectDesc};
	}else {
		object = {"cameraId":objectId,"longitude":objectLatLng.lng(),"latitude":objectLatLng.lat(),"description":objectDesc,"status":1};
	}
	
	$.ajax( {
		type : "POST",
		url : addAction,
		data: object,
		async: false,
		dataType : 'json',
		success : function(msg) {
			alert("添加成功！");
		},
		error: function () {
            alert("已存在，无需添加！");
        }
	});
}

/**
 * 已废弃不用的代码，原作用是对屏幕坐标做处理和获取地址栏参数
 * @param xPos
 * @param yPos
 * @return
 */

function getOriginScreenPoint(xPos, yPos) {
    var ix = xPos - window.screenLeft;
    var iy = yPos - window.screenTop;

    return new GPoint(ix,iy);
}


function getParameter(name) {  
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r!=null) 
		return unescape(r[2]); 
	return null; 
}

/********   右键菜单代码(v2 实现有难度)   ********/
/**
function createContextMenu(map) {
    var contextmenu = document.createElement("div");
    contextmenu.style.visibility = "hidden";
    map.getContainer().appendChild(contextmenu);
    GEvent.addListener(map, "singlerightclick", function(pixel, tile, marker) {
    	if(marker != null && marker.getTitle() == "CaseMarker") {
    		marker.closeInfoWindow();
    		var x = pixel.x;
    		var y = pixel.y;
    		if (x > map.getSize().width - 120) {
    			x = map.getSize().width - 120
    		}
    		if (y > map.getSize().height - 100) {
    			y = map.getSize().height - 100
    		}
    		var pos = new GControlPosition(G_ANCHOR_TOP_LEFT, new GSize(x, y));
    		pos.apply(contextmenu);
    		contextmenu.style.visibility = "visible";
		 } else {
			 contextmenu.style.visibility = "hidden";
		 }
    });
    
    GEvent.addListener(map, "click", function() {
        contextmenu.style.visibility = "hidden";
    });
    GEvent.addListener(map, "movestart", function() {
        contextmenu.style.visibility = "hidden";
    });
    GEvent.addListener(map, "zoom_changed", function() {
        contextmenu.style.visibility = "hidden";
    });
    GEvent.addListener(map, "dragstart", function() {
        contextmenu.style.visibility = "hidden";
    });
    
    return contextmenu;
}
*/
