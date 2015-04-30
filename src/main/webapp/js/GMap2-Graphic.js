var Rc = 6378137; // 赤道半径
var Rj = 6356725; // 极半径

// 矩形
var Rectangle = function(map,point1,point2) {
	this.point1 = point1;
	this.point2 = new GLatLng(point2.lat(),point1.lng());
	this.point3 = point2;
	this.point4 = new GLatLng(point1.lat(),point2.lng());
	this.strokeColor = "blue";
	this.strokeOpacity = 0.7;
	this.fillColor = "blue";
	this.fillOpacity = 0.2;
	this.borderWeight = 4;
	this.map = map;
	this.polygon = null;
}
// 画矩形
Rectangle.prototype.draw = function() {
	this.polygon = new GPolygon([this.point1,this.point2,this.point3,this.point4,this.point1], this.strokeColor , this.borderWeight, this.strokeOpacity, this.fillColor, this.fillOpacity);
	this.map.addOverlay(this.polygon);
}
// 刷新矩形
Rectangle.prototype.refresh = function() {
	if (this.polygon != null) {
		this.map.addOverlay(this.polygon);
	}
}
// 清除矩形
Rectangle.prototype.clear = function() {
	if (this.polygon != null) {
		this.map.removeOverlay(this.polygon);
		this.polygon = null;
	}	
}
// 圆
function Circle(cmap, radius, latlng) {
	this.cx = latlng.lng();// 经度
	this.cy = latlng.lat();
	this.radius = radius;
	this.clickable = true;
	this.map = cmap;
	this.strokeColor = "blue";
	this.strokeOpacity = 0.7;
	this.fillColor = "blue";
	this.fillOpacity = 0.2;
	this.borderWeight = 4;
	// this.dotNum = Math.ceil(radius * 1000000);
	this.dotNum = Math.ceil(radius * 10000);
	// alert('dotNum:' + this.dotNum + 'radius:' + radius);
	this.polygon = null;
}
// 画圆
Circle.prototype.draw = function() {
	var i = 0;
	var x = 0;
	var y = 0;
	var points = [];

	for (; i < this.dotNum; i = i + 1) {
		var angle = Math.PI * i * 2 / (this.dotNum);
		x = this.cx + this.radius * Math.cos(angle);
		y = this.cy + this.radius * Math.sin(angle);
		points.push(new GLatLng(y, x));	
	}
	this.polygon = new GPolygon(points, this.strokeColor , this.borderWeight, this.strokeOpacity, this.fillColor, this.fillOpacity);
	var mapCenter = this.map.getCenter();
	this.map.addOverlay(this.polygon);
}

// 刷新圆
Circle.prototype.refresh = function() {
	if (this.polygon != null) {
		this.map.addOverlay(this.polygon);
	}
}

// 清除圆
Circle.prototype.clear = function() {
	if (this.polygon != null) {
		this.map.removeOverlay(this.polygon);
		this.polygon = null;
	}	
}

// 多边形
function Polygon(vmap, shape, points, callbackFn) {
	this.map = vmap;
	this.polygon = null;
	this.status = 0;
	this.shape = shape;
	this.radius = 1000;
	this.points = points;
	this.circle = null;
	this.rectangle = null;
	this.strokeColor = "red";
	this.strokeOpacity = 0.7;
	this.fillColor = "blue";
	this.fillOpacity = 0.2;
	this.borderWeight = 2;
	this.event = null;
	if (typeof callbackFn == "function") {
		this.callbackFn = callbackFn;
	}
	else {
		this.callbackFn = null;
	}
}
// 是否结束画线
Polygon.prototype.isEndline=function(){
	// 如果只有一个点,任何图形都不算结束画线
	if(this.polygon.getVertexCount() < 2){
		return false;
	}
	// 如果是线缓冲,没有状态,永远都做为结束处理
	if(this.shape=="linebuffer"){
		return true;
	}else{
		// 如果status为2则表示结束画线
		return this.status == 2;
	}
}

/**
* 地图上单位像素对应实际长度
* @return
*/
GMap2.prototype.getDistencePerPixel=function(){
	var m=this.fromContainerPixelToLatLng(new GPoint(1,0)).distanceFrom(this.fromContainerPixelToLatLng(GPoint.ORIGIN));
	return m;
}
//画多边形

Polygon.prototype.draw = function() {
	if (this.shape == 'circle') {
		this.polygon = new GPolyline([], this.strokeColor, this.borderWeight, this.strokeOpacity);
	} else if (this.shape == 'rectangle'){
		this.polygon = new GPolyline([], this.strokeColor , this.borderWeight, this.strokeOpacity, this.fillColor, this.fillOpacity);
	} else if (this.shape == 'linebuffer'){
		var bufferWidth=this.bufferWidth=1000;//缓冲区宽度,单位是米
		var m=this.map.getDistencePerPixel();
		this.borderWeight=bufferWidth/m|0;
		this.polygon = new GPolyline([], this.strokeColor , this.borderWeight, this.strokeOpacity, this.fillColor, this.fillOpacity);
		//绑定地图缩放事件
		if(window.GGGendlineHandller){
			GEvent.removeListener(GGGendlineHandller);
		}
		var that=this;
		GGGendlineHandller= GEvent.addListener(this.map, "zoomend", function() {
			if(that.polygon && that.shape == 'linebuffer'){
				var m=that.map.getDistencePerPixel();
				that.borderWeight=bufferWidth/m|0;
				that.polygon.setStrokeStyle({weight:that.borderWeight});
			}
		});
	} else {
		this.polygon = new GPolygon([], this.strokeColor , this.borderWeight, this.strokeOpacity, this.fillColor, this.fillOpacity);
	}
	this.map.addOverlay(this.polygon);
	var that = this;

	this.polygon.enableEditing({onEvent: "mouseover"});
	this.polygon.disableEditing({onEvent: "mouseout"});

	GEvent.addListener(this.polygon, "endline", function() {
		that.status = 2;
	});

	GEvent.addListener(this.polygon, "cancelline", function() {
		that.status = -1;
		that.clear();
	});

	GEvent.addListener(this.polygon, "lineupdated", function() {
		that.refresh();
	});

	if (this.shape == 'circle') {
		this.polygon.enableDrawing({maxVertices: 2});
	} else if (this.shape == 'rectangle') {
		this.polygon.enableDrawing({maxVertices: 2});
	} else {
		this.polygon.enableDrawing();
	}
	if(this.points) {
		for ( var i = 0; i < this.points.length; i++) {
			this.polygon.insertVertex(i, this.points[i]);
		}
	}
	this.status = 1;
}

// 清除多边形
Polygon.prototype.clear = function() {
	that = this;
	if (that.polygon != null) {
		that.polygon.disableEditing();
		that.map.removeOverlay(that.polygon);
		that.polygon = null;
		that.status = 0;
	}
	if (that.circle != null) {
		that.circle.clear();
		that.circle = null;
	}
	if (that.rectangle != null) {
		that.rectangle.clear();
		that.rectangle = null;
	}
	if(that.event != null) {
		GEvent.removeListener(that.event);
		that.event = null;
	}
}

// 重画多边形
Polygon.prototype.refresh = function() {
	var that = this;

	if (that.shape == 'circle') {
		if (that.circle != null) {
			that.circle.clear();
			that.circle = null;
		}
		if (that.polygon.getVertexCount() == 2) {
			var latLng1 = that.polygon.getVertex(0);
			var latLng2 = that.polygon.getVertex(1);
			var radius = Math.sqrt((latLng1.lat() - latLng2.lat()) * (latLng1.lat() - latLng2.lat()) 
			+ (latLng1.lng() - latLng2.lng()) * (latLng1.lng() - latLng2.lng()) );

			if (that.callbackFn) {
				that.callbackFn(2, latLng1.lat(), latLng1.lng(), that.polygon.getLength());
			}
			that.circle = new Circle(that.map, radius, latLng1);
			that.circle.strokeColor = this.strokeColor;
			that.circle.strokeOpacity = this.strokeOpacity;
			that.circle.fillColor = this.fillColor;
			that.circle.fillOpacity = this.fillOpacity;
			that.circle.borderWeight = this.borderWeight;
			that.circle.draw();
		}
	} else if (that.shape == 'rectangle') {
		if (that.rectangle != null) {
			that.rectangle.clear();
			that.rectangle = null;
		}
		if (that.polygon.getVertexCount() == 2) {
			var latLng1 = that.polygon.getVertex(0);
			var latLng2 = that.polygon.getVertex(1);
			that.rectangle = new Rectangle(that.map, latLng1, latLng2);
			that.rectangle.strokeColor = this.strokeColor;
			that.rectangle.strokeOpacity = this.strokeOpacity;
			that.rectangle.fillColor = this.fillColor;
			that.rectangle.fillOpacity = this.fillOpacity;
			that.rectangle.borderWeight = this.borderWeight;
			that.rectangle.draw();
		}
	}
	/**
	* 多边形不需要手动刷新 else { if (that.polygon != null) {
	* that.map.addOverlay(that.polygon); } }
	*/
}

// 判断多边形中是否包含给定的点
Polygon.prototype.containsLatLng = function(point) {
	var p=this.polygon,
	latLng,
	count=p.getVertexCount();
	if(this.shape == 'circle') {
		var circle = this.circle;
		var dltX = point.lng() - circle.cx;
		var dltY = point.lat() - circle.cy;
		return dltX * dltX + dltY * dltY < circle.radius * circle.radius;
	} else if (this.shape == 'rectangle') {
		var rectangle = this.rectangle;
		if(!rectangle) {
			return;
		}
		var bounds = new GBounds([rectangle.point1, rectangle.point3]);
		return bounds.containsPoint(new GPoint(point.lng(),point.lat()));
	} 
	/**
	else if (this.shape == 'linebuffer'){
	var polyX=[],
	polyY=[],
	x = point.lat(),
	y = point.lng(),
	i=0,r=this.radius;

	for(i=0;i<count;i++){
	latLng=p.getVertex(i);
	polyX.push(latLng.lat());
	polyY.push(latLng.lng());
	}
	for(i=0;i<count-1;i++){
	var j = i+1;
	var x1 = polyX[i],x2 = polyX[j],y1 = polyY[i],y2 = polyY[j];

	//判断点是否在线段端点的圆形范围里
	var len1 = new GLatLng(x,y).distanceFrom(new GLatLng(x1,y1));
	var len2 = new GLatLng(x,y).distanceFrom(new GLatLng(x2,y2));
	if(len1 <= r || len2 <= r) {
	return true;
	}
	//求直线方程y=kx+b中的k和b，换成ax+by+c=0形式的公式，则a为k,b为-1,c为b
	var k1 = (y2 - y1)/(x2 - x1);
	var b1 = (x2*y1 - x1*y2)/(x2 - x1);
	var k2 = -1/k1;
	var b2 = y - k2*x;
	//pointX和pointY是y=k1x+b1和y=k2x+b2两条直线的交点坐标
	var pointX = (b2 - b1)/(k1-k2);
	var pointY = k1*pointX + b1;

	//得到线段长度
	var polyLen = new GLatLng(x1,y1).distanceFrom(new GLatLng(x2,y2));
	//判断交点到是否在线段上
	if(new GLatLng(pointX,pointY).distanceFrom(new GLatLng(x1,y1)) <= polyLen 
	&& new GLatLng(pointX,pointY).distanceFrom(new GLatLng(x2,y2)) <= polyLen) {
	var len = new GLatLng(x,y).distanceFrom(new GLatLng(pointX,pointY));

	if(len <= r) {
	return true;
	};
	}
	}
	return false;
	}
	*/
	/**
	* 
	* 点是否在一个粗线中,线条的缓冲区w为boderWidth/2,单位为像素
	* http://www.cnblogs.com/hxsyl/p/3249763.html
	*/
	else if (this.shape == 'linebuffer') {
		var w=this.borderWeight/2;
		var distance,a,b,c,p1,p2,l,s;
		for(i=1;i<count;i++){
			p1=p.getVertex(i-1);
			p2=p.getVertex(i);

			a=p1.distanceFrom(p2);//线段的距离,单位是米
			b=p1.distanceFrom(point);//点到p1距离,单位是米
			c=p2.distanceFrom(point);//点到p2距离,单位是米

			if (c+b==a) {//点在线段上
				distance= 0;
			}else if (c*c>= a*a + b*b) { //组成直角三角形或钝角三角形，p1为直角或钝角
				distance= b;
			}else if (b*b>= a*a + c*c) {// 组成直角三角形或钝角三角形，p2为直角或钝角
				distance= c;
			}else{
				// 组成锐角三角形，则求三角形的高
				l = (a + b + c) / 2;// 半周长
				s = Math.sqrt(l * (l - a) * (l - b) * (l - c));// 海伦公式求面积
				distance = 2*s / a;// 点到直线的距离（利用三角形面积公式求高）
			}
			if(this.bufferWidth/2>=distance){
				return true;
			}
		}
	} 
	else {
		/*
		* 点是否在多边形中算法 摘自http://blog.csdn.net/hjh2005/article/details/9246967
		*/
		var polyX=[],
		polyY=[],
		x = point.lat(),
		y = point.lng(),
		i=0,j=count-1,flag=false;

		for(i=0;i<count;i++){
			latLng=p.getVertex(i);
			polyX.push(latLng.lat());
			polyY.push(latLng.lng());
		}
		for (i=0;i<count; i++) {			
			if((polyY[i]< y && polyY[j]>=y
			||   polyY[j]<y && polyY[i]>=y)
			&& (polyX[i]<=x || polyX[j]<=x)) {
				flag^=(polyX[i]+(y-polyY[i])/(polyY[j]-polyY[i])*(polyX[j]-polyX[i])<x);}
				j=i;
		}
		return flag; 		
	}
}

function shouldShow(camera, status, range, shape, polygon) {
	if ((status == "valid" && camera.status != 1 )|| (status == "invalid" && camera.status != 0)) {
		return false;
	}
	if (range == "specified") {
		var point = new GLatLng(camera.latitude, camera.longitude);
		if (shape == "circle") {
			return polygon.circle.containsLatLng(point);
		}
		else if (shape == "polygon") {
			return polygon.containsLatLng(point);
		}
	}
	return true;
}

function LatLngExt(lon, lat) {
	this.loDeg = Math.floor(lon);
	this.loMin = Math.floor((lon - this.loDeg) * 60);
	this.loSec =  (lon - this.loDeg - this.loMin/60) * 3600;
	this.laDeg = Math.floor(lat);
	this.laMin = Math.floor((lat - this.laDeg) * 60);
	this.laSec = (lat - this.laDeg - this.laMin/60) * 3600;
	this.lo = lon;
	this.la = lat;
	this.radLo = lon * Math.PI / 180;
	this.radLa = lat * Math.PI / 180;
	this.Ec = Rj + (Rc - Rj) * (90 - this.la) / 90;
	this.Ed = this.Ec * Math.cos(this.radLa);
}

function getLatLngFromDistance(latA, lngA, distance, angle) {
	var pointA = new LatLngExt(lngA, latA);
	var dx = distance * 1000 * Math.sin(angle * Math.PI / 180);
	var dy = distance * 1000 * Math.cos(angle * Math.PI / 180);
	var lngB = (dx / pointA.Ed + pointA.radLo) * 180 / Math.PI;
	var latB = (dy / pointA.Ec + pointA.radLa) * 180 / Math.PI;
	var latLng = new GLatLng(latB, lngB);
	return latLng;
}

function getRadiusFromDistance(latA, lngA, distance) {
	var latLngB = getLatLngFromDistance(latA, lngA, distance, 90);
	var radius = Math.sqrt((latA - latLngB.lat()) * (latA - latLngB.lat()) + (lngA - latLngB.lng()) * (lngA - latLngB.lng()));
	return radius;
}

function showCircle(map, radiusDistance, lat, lng) {
	var radius = getRadiusFromDistance(lat, lng, radiusDistance);
	var latLng = new GLatLng(lat, lng);
	var clc = new Circle(map, radius, latLng);
	clc.draw();
	return clc;
}