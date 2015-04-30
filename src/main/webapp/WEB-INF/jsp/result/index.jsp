<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="query_datagrid">
	<div class='query_toolbar'>
		<form action="#">
			监&ensp;控&ensp;点:
			<input id="query_camera" type="text">
			车&emsp;&emsp;牌:
			<input id="plate" type="text">
			行&ensp;政&ensp;区:
			<input id="location" type="text">
			车牌颜色:
			<input id="plateColor" type="text">
			车身颜色:
			<input id="carColor" type="text">
			车道方向:
			<input id="direction" type="text">
			<br />
			车&emsp;&emsp;型:
			<input id="vehicleKind" type="text">
			品&emsp;&emsp;牌:
			<input id="vehicleBrand" type="text">
			款&emsp;&emsp;型:
			<input id="vehicleStyle" type="text">
			开始时间:
			<input id="query_startTime" type="text" name="startTime">
			结束时间:
			<input id="query_endTime" type="text" name="startTime">
			<a href="javascript:void(0)" id="query_submit"
				class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		</form>
	</div>
</div>
<div id="query_win"
	style="position: relative; width: 100%; height: 100%;"></div>
<script type="tmpl" id="result_tmpl">
<div class="box" style="position:absolute;top:0;left:0;width:60%;height:63%;">
    <div class="box-small">
		<img id="pic" width="100%" height="100%" src="{#imageUrl}" alt="" />
    	<div class="zoomMark" id="zoomMark">&nbsp;</div>
	</div>
    <div style="text-align:center;">
        <button id="previous" data-turn-index={#index} data-turn-str="previous">上一张</button>
        &ensp;&ensp;&ensp;&ensp;
        <button id="next" data-turn-index={#index} data-turn-str="next">下一张</button>
    </div>
    <div class="boxbig" id="boxbig">
    </div>
</div>
<div style="position:absolute;top:0;right:0;width:40%;height:70%;">
    <table class="info_table">
        <tr>
            <td>车牌</td>
            <td id="license">{#license}</td>
        </tr>
        <tr>
            <td>车牌颜色</td>
            <td id="plateColor">{#plateColor}</td>
        </tr>
        <tr>
            <td>车牌类型</td>
            <td id="plateType">{#plateType}</td>
        </tr>
        <tr>
            <td>车牌可信度</td>
            <td id="confidence">{#confidence}</td>
        </tr>
        <tr>
            <td>车牌归属地</td>
            <td id="licenseAttribution">{#licenseAttribution}</td>
        </tr>
        <tr>
            <td>车身颜色</td>
            <td id="carColor">{#carColor}</td>
        </tr>
        <tr>
            <td>过车时间</td>
            <td id="resultTime">{#util.formateTime(resultTime)}</td>
        </tr>
        <tr>
            <td>过车行政区</td>
            <td id="location">{#location}</td>
        </tr>
		<tr>
			<td>车型</td>
			<td>{#vehicleKind}</td>
		</tr>
		<tr>
			<td>品牌</td>
			<td>{#vehicleBrand}</td>
		</tr>
		<tr>
			<td>款型</td>
			<td>{#vehicleStyle}</td>
		</tr>
    </table>
</div>
</script>
<script>
  (function(){
      //监控点自动完成多选框
		var $query_camera=$('#query_camera'),
			$query_datagrid = $('#query_datagrid'),
			$query_win=$('#query_win'),
		queryUrl='/camera/list.action',
		cameraIds=$query_camera.combogrid({
			delay:500,
		 	panelWidth:400,
		 	idField:'id',
		 	textField:'name',
		    url:queryUrl,
		    multiple:true,  
		    fitColumns: true,  
		    striped: true,  
		    editable:true,  
		    pagination : true,//是否分页  
		    rownumbers:true,//序号  
		    collapsible:false,//是否可折叠的  
		    pageSize: 10,//每页显示的记录条数，默认为10  
		    pageList: [10,20,50],//可以设置每页记录条数的列表  
		    method:'post',  
		    columns:[[
		        {field:'id',title:'id',hidden:true},  
		        {field:'checkbox',checkbox:true},  
		        {field:'name',title:'监控点名称',width:150} 
		    ]], 
		    keyHandler: {
				up: function () {
				
				},
				down: function () {
				
				},
				left: function () {
				 	
				},
				right: function () {
				
				},
				enter: function () {
				
				},
				query: function (q) {
						$query_camera.combogrid("grid").datagrid("reload", { 'name': q });
						$query_camera.combogrid("setValue", q);
				}
			}
	  });
	  var startTime=$('#query_startTime').datetimebox({
		  editable:false
	  });
	  var endTime=$('#query_endTime').datetimebox({
		  editable:false
	  });
	  var plate=$("#plate");
	  var location=$("#location");
	  var plateColor=$("#plateColor");
	  var carColor=$("#carColor");
	  var vehicleBrand=$("#vehicleBrand");
	  var vehicleStyle=$("#vehicleStyle");
	  var direction=$("#direction").combobox({
			    data:[{
			      value:'0',
			      text:'未知'
				},{
				  value:'1',
				  text:'向上'
				},{
				  value:'2',
				  text:'向下'
				},{
				  value:'3',
				  text:'向左'
				},{
				  value:'4',
				  text:'向右'
				}]
		  });
	  var vehicleKind=$("#vehicleKind").combobox({
		    data:[{
		      value:'car',
		      text:'小轿车'
			},{
			  value:'freight',
			  text:'货车'
			},{
			  value:'passenger',
			  text:'客车'
			},{
			  value:'motorcycle',
			  text:'摩托车'
			},{
			  value:'van',
			  text:'面包车'
			},{
			  value:'unknown',
			  text:'未识别'
			}]
	  });
	  var getParamData=function(){
		  return  {
				  cameraIds:cameraIds.combo('getValues').join(),
				  startTime:startTime.combo('getValue'),
				  endTime:endTime.combo('getValue'),
				  plate:plate.val(),
				  location:location.val(),
				  plateColor:plateColor.val(),
				  carColor:carColor.val(),
				  direction:direction.combo('getValue'),
				  vehicleKind:vehicleKind.combo('getValue'),
				  vehicleBrand:vehicleBrand.val(),
				  vehicleStyle:vehicleStyle.val()
			  }
	  }

	  //放大
	  $query_win.on('mouseover','.box-small',function(){
		  zoomImg(this)
	  })
	  
	  //翻页
	  $query_win.on('click','#previous,#next',function(){
			var turnIndex=$(this).data('turn-index')
			var turnStr=$(this).data('turn-str')
			turnPage(turnIndex,turnStr)
		})

	  //图片放大
	  var zoomImg=function(self) {
          var $query_win=$(self).parent().parent();
		  var $pic=$query_win.find('#pic');
		  var $boxbig=$query_win.find('.boxbig');
		  var $zoomMark=$query_win.find('#zoomMark');
		  var $bigimg=$query_win.find('#bigimg');
		  var $bg=$query_win.find('.box-big'),
		  	  src=$pic[0].src,
		  	  p=$query_win.mousemove(function(e){
			  	  
		  		e=e.originalEvent;
		  		
		  		var imageWidth=$pic.width();
		  		var imageHeight=$pic.height();
		  		var imageTop=$pic.offset().top;
		  		var imageLeft=$pic.offset().left;
		  		var bigWidth=$boxbig.width();
		  		var bigHeight=$boxbig.height();
				var scaley='x';
				var scalex='y';

/*/图片原始尺寸
var orginalImage = new Image();
orginalImage.src = src;
var orginalWidth=orginalImage.width;
var orginalHeight=orginalImage.height;
*/
				if(isNaN(scalex)|isNaN(scaley)){
					var scalex=(bigWidth/$zoomMark.width());
					var scaley=(bigHeight/$zoomMark.height());
				}
		  		
			  	var x=e.pageX||e.offsetX,
					y=e.pageY||e.offsetY,
					o=p.offset()
					
				if(x<=imageLeft||x>=imageLeft+imageWidth||y<=imageTop||y>=imageTop+imageHeight){
					mouseOutImage()
					return;
			  	}
				  	
				$zoomMark[0].style.display='block'
				var xpos=x-imageLeft<=($zoomMark.width()/2)?0:((x-imageLeft-40+80)>imageWidth?(imageWidth-$zoomMark.width()):(x-imageLeft-40))
				var ypos=y-imageTop<=($zoomMark.height()/2)?0:((y-imageTop-40+80)>imageHeight?(imageHeight-$zoomMark.height()):(y-imageTop-40))
				
				$zoomMark.css({
						opacity:0.5,
						background: 'gray',
						top:ypos,
						left:xpos
					})
				var bigimgWidth=$pic.width()*scalex,
					bigimgHeight=$pic.height()*scaley
						
				if($query_win.find('#box-big').get().length==0){
					$boxbig.append('<div class="box-big" id="box-big"><img src="'+src+'" id="bigimg" width="'+bigimgWidth+'" height="'+bigimgHeight+'"></div>')
				}
				$(self).css('cursor','move')
				var scalexx=$bigimg.width()/$pic.width();	
				var scaleyy=$bigimg.height()/$pic.height();
				
				var xposs=xpos*scalexx;
				var yposs=ypos*scaleyy;	
				
				$bg.scrollTop(yposs)
				$bg.scrollLeft(xposs)
				$bg.css({
					width:'300px',
					height:'300px',
				}).show()
	  	  })
	  	  
	  	  var mouseOutImage=function(){
			  	$query_win.unbind('mousemove');
			  	$bg.remove()
				$zoomMark.hide()
		  	  }
	  };

	//翻页
	  var turnPage=function(index,str){
			var dataRows = $query_datagrid.datagrid("getRows"),
	  	  	  	pageNum = $query_datagrid.datagrid('getPager').pagination('options').pageNumber,
	  	  	  	pageSize = $query_datagrid.datagrid('getPager').pagination('options').pageSize
		  	if(str=='previous'){
		  		if(pageNum==1&&index == 0) {
			  		$.messager.show({
						title:'提示消息',
						msg:'已经是第一张',
						timeout:5000,
						showType:'slide'
					});
				 return false;
			  } 
			  if(index==0) {
				  loadResultDatas(--pageNum,function(){
					  var dataRows_1 = $query_datagrid.datagrid("getRows");
					  showResultWindow(dataRows_1[pageSize-1])
					  });
			  }else{
				  var row=dataRows[--index]
				  showResultWindow(row)
			  }
			}
			
		  	if(str=='next'){
		  		if(pageNum==getPageCount()&&index == dataRows.length-1) {
					  $.messager.show({
							title:'提示消息',
							msg:'已经是最后一张',
							timeout:5000,
							showType:'slide'
						});
					  return false;
			    } 
			    if(index==dataRows.length-1) {
				  loadResultDatas(++pageNum,function(){
					  var dataRows_1 = $query_datagrid.datagrid("getRows");
					  showResultWindow(dataRows_1[0])
					  });
			    }else {
				  var row=dataRows[++index];
				   showResultWindow(row)			  
			  }
			}
		  }

	//总页数
	  var getPageCount=function(){
			  var opts = $query_datagrid.datagrid('getPager').pagination('options'),
		  	  	  pageSize = opts.pageSize,
		  	  	  total = opts.total,
				  pageCount;
			  
			  return pageCount=(total+pageSize-1)/pageSize|0;
		  }

	  $('#query_submit').click(function(e){
		  e.preventDefault();
		  $query_datagrid.datagrid("reload");
	  });
	  
	  var result_tmpl=$('#result_tmpl').html();
	  
	  var loadResultDatas=function(pageNumber,callback){
		  
		  var datagrid=$query_datagrid.datagrid({
			  	url:'/result/query.action',
				loadMsg:'数据载入中',
				pagination:true,
				pageNumber:pageNumber,
				rownumbers:true,
				fit: true,
				singleSelect:true,
	   //*过车时间（绝对时间）、*方向、*地点、*经度、*纬度、
				columns:[[
					{field:'operation',title:'操作',formatter:function(v,row,i){
						var html = '';
						if(row.taskType == 2 && row.resourceType == 2) {
							html = '<a href="javascript:void(0)" onclick=video_player.play(\"'+row.path.replace(/\\/gm,'\\\\')+'\",'+row.frame_index+') >播放片段</a>';
						}
						return html;
					}},
					{field:'license',title:'车牌',width:150},
					{field:'plateType',title:'车牌类型' ,width:150},
					{field:'plateColor',title:'车牌颜色' ,width:70},
					{field:'licenseAttribution',title:'车牌归属地',width:150},
					{field:'vehicleKind',title:'车型',width:70},
					{field:'vehicleBrand',title:'品牌',width:70},
					{field:'vehicleStyle',title:'款型',width:120},
					{field:'confidence',title:'可信度',width:50},
					{field:'carColor',title:'车身颜色',width:150},
					{field:'direction',title:'方向' ,width:50},
					{field:'location',title:'过车行政区' ,width:150},
					//{field:'longitude',title:'经度' ,width:150},
					//{field:'latitude',title:'纬度' ,width:150},
					{field:'resultTime',title:'过车时间',formatter:util.formateTime,width:150}
				]],
				toolbar:$('.query_toolbar',datagrid),
				onDblClickRow:function(index,row){
					 showResultWindow(row)
	            },
				onBeforeLoad:function(p){
					$.extend(p,getParamData());	
				},
				onLoadSuccess:function(pager){
					$('.pagination-num').attr('disabled','disabled')
					$.each(pager.rows,function(i,o) {
						o.index=i;
						o[i]
					});
				  	if(typeof callback=='function'){
					  callback();
					}
				}
		  })
	  }
	  loadResultDatas();


	//详情窗口缓存
		var cacheWindow=function(html,row){
			    if(!$query_win.hasInit){
	       		    $query_win.hasInit=true;			
					$query_win.window({
						title:row.license,
			      	    width:800,
			      	    height:520,
			      	    content:html
					})			
				}
				return $query_win.window('setTitle',row.license).html(html); 
			}
var showResultWindow=function(row){
	var cloneRow=$.extend({},row);
	cloneRow.imageUrl='${PictureServerHost}/'+encodeURI(row.imageUrl);
	var html=util.parser(result_tmpl,cloneRow);
	cacheWindow(html,cloneRow).window('open')
	
	var pager = $query_datagrid.datagrid('getPager');
	pager.pagination({
		onSelectPage:function(pageNumber){
			loadResultDatas(pageNumber)
		}	   	
	}) 
}
	$query_datagrid.datagrid('getPanel').on("keyup",'input:text',function(e){
		if(e.keyCode == 13) {
			$query_datagrid.datagrid('reload');
		}
	});
  })();
 </script>
