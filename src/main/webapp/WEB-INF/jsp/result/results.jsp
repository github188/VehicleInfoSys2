<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="tmpl" id="result_tmpl_page">
<li>
	<div class="vehicle_panel round_border">
	<input name="id" value="{#serialNumber}" style="display: none;" />
	<input name="license" value="{#license}" style="display: none;" />
	<img class="result_img" onerror="util.noFind()" src="${PictureServerHost}/{#imageUrl}" />
	<br/>
	<br/>
	{#license}
	<a href="javascript:void(0)" class="result_details" data-result-index={#index}>详情</a>
	<a href="javascript:void(0)" class="play_video" style="display:'inline-'+{#isResourceVideo}">播放片段</a>
	<br/>	
	{#util.formateTime(resultTime)}
	</div>
</li>
</script>

<script type="tmpl" id="detail_result_tmpl">
<div class="box1" style="position:absolute;top:0;left:0;width:60%;height:63%;">
    <div class="box1-small">
		<img id="pic1" width="100%" height="100%" src="{#imageUrl}" alt="" />
		<div class="zoomMark" id="zoomMark1">&nbsp;</div>
    </div>
    <div style="text-align:center;">
        <button id="previous" data-pre-index={#index} data-pre-str="_previous">上一张</button>
        &ensp;&ensp;&ensp;&ensp;
        <button id="next" data-pre-index={#index} data-pre-str="_next">下一张</button>
    </div>
    <div class="box1big">
        <div class="box1-big"></div>
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
<div class="results_area_div" style="position:relative;">
	<div id="content">
	<c:choose>
	<c:when test="${empty resultList || empty resultList.rows || resultList.total == 0}">
		<div  style="margin:0 auto;margin-top: 50px;width:100px;">
			<span style="font-size: x-large;">无结果</span>
		</div>
	</c:when>
	<c:otherwise>
	<ul class="results_area">
		<c:forEach items="${resultList.rows}" var="e" varStatus="st">
			<li>
				<div class="vehicle_panel round_border">
					<input name="id" value="${e.serialNumber}" style="display: none;" />
					<input name="license" value="${e.license}" style="display: none;" />
					<img class="result_img" onerror="util.noFind()" src="${PictureServerHost}/${e.imageUrl}" />
					<br/>
					<br/>
					${e.license} 
					<a href="javascript:void(0)" class="result_details" data-result-index="${st.index}">详情</a>
					<c:if test="${e.taskType == 2 && e.resourceType == 2}">
						<a href="javascript:void(0)" class="play_video">播放片段</a>
					</c:if>
					<br/>
					<fmt:formatDate value="${e.resultTime}" type="both"/>
				</div>
			</li>
		</c:forEach>
	</ul>
	</c:otherwise>
	</c:choose>
	</div>
	<div id="task_result_pg" class="easyui-pagination" style="position:fixed;bottom:0;z-index:1;width:100%;background:#efefef;border:1px solid #ccc;"
		data-options="total:${resultList.total},pageSize:20,displayMsg:'共{total}条记录'"></div>
</div>
<div id="query_win_result" style="position: relative; width: 800px; height: 520px;"></div>
<script type="text/javascript">
(function(){
   var tab=window.mainTabs.tabs('getSelected'),
   $task_result_tabs=tab.find('.result_details_tabs:visible'),
   $task_result_tab=$task_result_tabs.length==0?tab:$task_result_tabs.tabs('getSelected'),   
   $task_result_pg=$task_result_tab.find('#task_result_pg'),
	taskId = '${taskId}',
	$query_win_result=$task_result_tab.find('#query_win_result');

	
	$query_win_result.on('mouseover','.box1-small',function(){
			_zoomImg(this)
		})
	
	//翻页
	$query_win_result.on('click','#previous,#next',function(){
			var preIndex=$(this).data('pre-index')
			var preStr=$(this).data('pre-str')
			_turnPage(preIndex,preStr)
		})

	 $task_result_pg.pagination({
		 	onSelectPage:function(page,rows){
		 		clearResults()
		 		loadResults(this,page,rows)
		 		
		 	}
		 })
	//图片放大
	  var _zoomImg=function(self) {
          var $query_win_result=$(self).parent().parent();
		  var $pic1=$query_win_result.find('#pic1');
		  var $box1big=$query_win_result.find('.box1big');
		  var $zoomMark1=$query_win_result.find('#zoomMark1');
		  var $bigimg1=$query_win_result.find('#bigimg1');
		  var $bg1=$query_win_result.find('.box1-big'),
		  	  src=$pic1[0].src,
		  	  p=$query_win_result.mousemove(function(e){
		  		e=e.originalEvent;
		  		var imageWidth=$pic1.width();
		  		var imageHeight=$pic1.height();
		  		var imageTop=$pic1.offset().top;
		  		var imageLeft=$pic1.offset().left;
		  		var bigWidth=$box1big.width();
		  		var bigHeight=$box1big.height();
				var scaley='x';
				var scalex='y';

				if(isNaN(scalex)|isNaN(scaley)){
					var scalex=(bigWidth/$zoomMark1.width());
					var scaley=(bigHeight/$zoomMark1.height());
				}
			  	var x=e.pageX||e.offsetX,
					y=e.pageY||e.offsetY,
					o=p.offset()
					
				if(x<=imageLeft||x>=imageLeft+imageWidth||y<=imageTop||y>=imageTop+imageHeight){
					mouseOutImage()
					return;
			  	}
				  	
				$zoomMark1[0].style.display='block'
				var xpos=x-imageLeft<=($zoomMark1.width()/2)?0:((x-imageLeft-40+80)>imageWidth?(imageWidth-$zoomMark1.width()):(x-imageLeft-40))
				var ypos=y-imageTop<=($zoomMark1.height()/2)?0:((y-imageTop-40+80)>imageHeight?(imageHeight-$zoomMark1.height()):(y-imageTop-40))
				
				$zoomMark1.css({
						opacity:0.5,
						background: 'gray',
						top:ypos,
						left:xpos
					})
				var bigimgWidth=$pic1.width()*scalex,
					bigimgHeight=$pic1.height()*scaley
						
				if($query_win_result.find('#box1-big').get().length==0){
					$box1big.append('<div class="box1-big" id="box1-big"><img src="'+src+'" id="bigimg1" width="'+bigimgWidth+'" height="'+bigimgHeight+'"></div>')
				}

				var scalexx=$bigimg1.width()/$pic1.width();	
				var scaleyy=$bigimg1.height()/$pic1.height();
				
				var xposs=xpos*scalexx;
				var yposs=ypos*scaleyy;	
				
				$bg1.scrollTop(yposs)
				$bg1.scrollLeft(xposs)
				$bg1.css({
					width:'300px',
					height:'300px',
				}).show()
	  	  })
	  	  
	  	  var mouseOutImage=function(){
			  	$query_win_result.unbind('mousemove');
			  	$bg1.remove()
				$zoomMark1.hide()
		  	  }
	  };

	  

	//翻页
	  var _turnPage=function(index,str){

		  var pageNum=$task_result_pg.pagination('options').pageNumber,
		  	  pageSize=$task_result_pg.pagination('options').pageSize,
	    	  key=getKey();
    	  		
		  	if(str=='_previous'){
		  		if(pageNum==1&&index == 0){
					  $.messager.show({
							title:'提示消息',
							msg:'已经是第一张',
							timeout:5000,
							showType:'slide',
					  });
					  return false;
				}
			  	if(index==0){
				  loadResults($task_result_pg,--pageNum,pageSize,function(){
					_showResultWindow(pageSize-1);
				  })
				}else{
					_showResultWindow(--index)
				}
			}

			if(str=='_next'){
				if(pageNum==getPageCount() && index==_taskPageResults[key].length-1){
				  	$.messager.show({
						title:'提示消息',
						msg:'已经是最后一张',
						timeout:5000,
						showType:'slide'
					});
				  return false;
				}
				if(index==taskPageResults[key].length-1){
					loadResults($task_result_pg,++pageNum,pageSize,function(){
						_showResultWindow(0);
					})
				}else{
					_showResultWindow(++index)
				}
			}
		  }

	  //总页数
	  var getPageCount=function(){
			  var total=$task_result_pg.pagination('options').total,
			      rows=$task_result_pg.pagination('options').pageSize,
			      pageCount;

		      return pageCount=(total+rows-1)/rows | 0;
		  }
	
	//var _taskPageResults=window.taskPageResults=window.taskPageResults||{};

	if(!window.taskPageResults){
		window.taskPageResults={}
	}
	var _taskPageResults=window.taskPageResults;
	
	var detail_result_tmpl=$('#detail_result_tmpl').html();
	
	var getKey=function() {
        if($task_result_pg.length==0){
            return taskId+'_1';
        }
		var pageNum=$task_result_pg.pagination('options').pageNumber,
			key=taskId+'_'+pageNum;
			return key;
		}

	$task_result_tab.on('click','.result_details',function(){
		var index=$(this).data('result-index')
		_showResultWindow(index)
	})
	  
	 //显示详情窗口 
	 var _showResultWindow=function(index){
		var key=getKey();
		if(!_taskPageResults[key]){
			$.ajax({
				url:'result/findByTaskId.action',
				type:'post',
				data:{'taskId':taskId},
				async:false,
	            success:function(page){
							_taskPageResults[key]=page.rows;
						}
				})
	    }
		var resultVo=getResultVo(index,key);
		if(resultVo.imageUrl.indexOf('${PictureServerHost}/')<0){
			resultVo.imageUrl = '${PictureServerHost}/' + encodeURI(resultVo.imageUrl);
		}
		resultVo.index=index;
		var html=util.parser(detail_result_tmpl,resultVo);
		cacheWindow(html,resultVo).window('open')
	}

	//详情窗口缓存
	var cacheWindow=function(html,resultVo){
		    if(!$query_win_result.hasInit){
       		    $query_win_result.hasInit=true;			
				$query_win_result.window({
					title:resultVo.license,
		      	    width:800,
		      	    height:520,
		      	    content:html
				})			
			}
			return $query_win_result.window('setTitle',resultVo.license).html(html); 
		}

//拿到结果对象
	var getResultVo=function(index,key){
		var resultRows= _taskPageResults[key];
		return resultRows[index];
	}
//播放
$task_result_tab.on('click','.play_video',function(){
		playResultVideo(this)
	})
	
	var playResultVideo = function(t) {
		var that = t;
		var tabs = $(that).closest('.result_details_tabs');
		var parent = $(that).parent();
		var id = parent.find('input[name="id"]').val();
		$.post('result/findById.action',{resultId:id},function(d) {
			video_player.play(d.path,d.frame_index);
		});
	}
	
	var loadResults = function(t, page, rows,callback) {
		var that = t;
		var div = $(that).closest('.results_area_div');
		var ul = div.find('.results_area');
		var tmpl = $('#result_tmpl_page',$task_result_tab).html();
		$.post('result/findByTaskId.action',{'taskId':taskId,'page':page,'rows':rows},function(d) {
			var pager = d;
			_taskPageResults[taskId+'_'+page]=pager.rows;
			$(that).pagination('refresh',{
				total: pager.total,
				pageNumber:page,
				displayMsg:'共{total}条记录'
			});
			var html = '';
			$.each(pager.rows,function(i,o) {
				o.isResourceVideo = o.taskType == 2 && o.resourceType == 2 ? 'block':'none';
				o.index=i;
				o[i]
				html += util.parser(tmpl,o);
			});
			$('#content',$task_result_tab).html('<ul class="results_area">'+html+'</ul>');
			if(typeof callback=='function'){
				callback();
			}
		});
	}
	
	var clearResults = function() {
		$('#content',$task_result_tab).html('');
	}
	
	$('.results_area img').each(function(i,e) {
		$(e).attr('src',e.src);
	});
})()
</script>