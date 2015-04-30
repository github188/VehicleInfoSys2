//定义常量
var mapSettings = $.extend({},window.mapSettings,{
		min:4,
		max:15,
		noData:'没有数据',
		normal:'普通',
		satellite:'卫星',
		centerLat: 23.030056239124402,
		centerLng: 113.77346992492676,
		 
		initialZoom: 12,
		focusZoom: 15
});
var initLongitude = 121.1518;
var initLatitude = 31;
var initZoomLeval = 12;
//add new init
ZTE = window.ZTE || {};
ZTE.markerArray = [];
ZTE.trackArray = [];		
ZTE.markerCount = 0;
ZTE.playStep = 0;
	
ZTE.subIsShow = false;
ZTE.subMouseIn = false;
	
	
ZTE.lineArray = [];
ZTE.polygonArray = [];
	
//	if (GBrowserIsCompatible()) 
//	{
	var copyright = new GCopyright(1,new GLatLngBounds(new GLatLng(-90, -180),
														new GLatLng(90, 180)),0);
	var copyrightCollection = new GCopyrightCollection("&copy 2013");
	copyrightCollection.addCopyright(copyright);

	var norLayer = [new GTileLayer(copyrightCollection, mapSettings.min, mapSettings.max)];
	norLayer[0].getTileUrl = getMapTileUrl;
	norLayer[0].isPng = function() {
		return true;
	};
	norLayer[0].getOpacity = function() {
		return 1.0;
	};
			
	var arrowIcon = null;
		
	//create a custom picture layer
	var norLayer = [ new GTileLayer(copyrightCollection , mapSettings.min, mapSettings.max)];
	norLayer[0].getTileUrl = getMapTileUrl;
	norLayer[0].isPng = function() { return true; };
	norLayer[0].getOpacity = function() { return 1.0; };
		
	var normalMap = new GMapType(norLayer, G_NORMAL_MAP.getProjection(), mapSettings.normal,
	{maxResolution:mapSettings.max, minRes:mapSettings.min, errorMessage:mapSettings.noData});
		
	var satLayer = [ new GTileLayer(copyrightCollection , mapSettings.min, mapSettings.max),new GTileLayer(copyrightCollection , mapSettings.min, mapSettings.max)];
	satLayer[0].getTileUrl = getSatTileUrl;
	satLayer[0].isPng = function() { return true; };
	satLayer[0].getOpacity = function() { return 1.0; };
	satLayer[1].getTileUrl = getSatTileUrl;
	satLayer[1].isPng = function() { return true; };
	satLayer[1].getOpacity = function() { return 1.0; };
		
	var satTileMap = new GMapType(satLayer, G_SATELLITE_MAP.getProjection(), mapSettings.satellite,
	{maxResolution:mapSettings.max, minResolution:mapSettings.min, errorMessage:mapSettings.noData});
	