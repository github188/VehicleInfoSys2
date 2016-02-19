<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@    taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
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
	<a href="javascript:void(0)" class="play_video" style="display:{#isResourceVideo}">播放片段</a>
	<br/>	
	{#util.formateTime(resultTime)}
	</div>
</li>

</script>

<script type="tmpl" id="detail_result_tmpl">
<div class="box1" style="top: 5px;left: 5px;width:60%;height:auto;position:absolute;">
    <div class="box1-small">
		<img id="pic1" width="100%" height="100%" src="{#imageUrl}" alt="" />
		<div class="rect_1" id="rect_1">&nbsp;</div>
		<div class="rect_1" id="tagrect0" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect1" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect2" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect3" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect4" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect5" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect6" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect7" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect8" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="tagrect9" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect_1" id="paperrect0" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect_1" id="paperrect1" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect_1" id="paperrect2" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect_1" id="paperrect3" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect_1" id="paperrect4" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect_1" id="paperrect5" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect_1" id="sunrect0" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect_1" id="sunrect1" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect_1" id="sunrect2" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect_1" id="sunrect3" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect_1" id="sunrect4" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect_1" id="sunrect5" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect_1" id="droprect0" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect_1" id="droprect1" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect_1" id="droprect2" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect_1" id="droprect3" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect_1" id="droprect4" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect_1" id="droprect5" style="border:solid 1px Orange;">&nbsp;</div>
    </div>
    <div style="text-align:center;">
        <button id="previous" disabled="disabled" data-pre-index={#index} data-pre-str="_previous">上一张</button>
        &ensp;&ensp;&ensp;&ensp;
        <button id="next" disabled="disabled" data-pre-index={#index} data-pre-str="_next">下一张</button>
    </div>
</div>
<div style="top: 5px;right: 5px;position:absolute;width:320px;">
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
            <td>地点</td>
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
			<td>车系</td>
			<td>{#vehicleSeries}</td>
		</tr>
		<tr>
			<td>款型</td>
			<td>{#vehicleStyle}</td>
		</tr>
		<tr>
			<td>年检标</td>
			<td id="tagNumbersb">{#tag==1?'有':'无'}</td>
		</tr>
		<tr>
			<td>纸巾盒</td>
			<td>{#paper==1?'有':'无'}</td>
		</tr>
		<tr>
			<td>遮阳板</td>
			<td>{#sun==1?'放下':'未放下'}</td>
		</tr>
		<tr>
			<td>挂饰</td>
			<td>{#drop==1?'有':'无'}</td>
		</tr>
    </table>
</div>

</script>
<div class="results_area_div" style="position:relative;">
    <div id="content">
        <ul class="results_area">
        </ul>
    </div>
    <div id="task_result_pg" style="position:fixed;bottom:0;z-index:1;width:100%;background:#efefef;border:1px solid #ccc;"></div>
</div>
<div id="query_win_result" style="position: relative; width: 800px; height: 520px;"></div>
<script type="text/javascript">
    (function () {
        var tab =  $("#task_tab").tabs("getSelected")
                task_unfinished=true,
                $task_result_tabs = tab.find('.result_details_tabs:visible'),
                $task_result_tab = $task_result_tabs.length == 0 ? tab : $task_result_tabs.tabs('getSelected'),
                $task_result_pg = $task_result_tab.find('#task_result_pg'),
                taskId = '${taskId}',
                $query_win_result = $task_result_tab.find('#query_win_result');


        $query_win_result.off('mouseover','.box1-small').on('mouseover', '.box1-small', function () {
            $("#pic1").addimagezoom({ // single image zoom
                zoomablefade:false,
                zoomrange: [2, 15],
                magnifiersize: [500,500],
                magnifierpos: 'left',
                cursorshade: true,
                largeimage: $("#pic1")[0].src //<-- No comma after last option!
            })
        })

        //翻页
        $query_win_result.on('click', '#previous,#next', function () {
            var self = this
            var preIndex = $(self).data('pre-index')
            var preStr = $(self).data('pre-str')
            
            //避免按钮在数据未加载完成时连续点击，总页数读取出现错误,引发提示框弹出提示
            self.disabled = true;
            
            _turnPage(preIndex, preStr)
        })

        $task_result_pg.pagination({
            onSelectPage: function (page, rows) {
                clearResults()
                loadResults(this, page, rows)

            }
        })

        //翻页
        var _turnPage = function (index, str) {

            var pageNum = $task_result_pg.pagination('options').pageNumber,
                    pageSize = $task_result_pg.pagination('options').pageSize,
                    key = getKey();

            if (str == '_previous') {
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
                    loadResults($task_result_pg, --pageNum, pageSize, function () {
                        _showResultWindow(pageSize - 1);
                    })
                } else {
                    _showResultWindow(--index)
                }
            }

            if (str == '_next') {
                if (pageNum == getPageCount() && index == _taskPageResults[key].length - 1) {
                    $.messager.show({
                        title: '提示消息',
                        msg: '已经是最后一张',
                        timeout: 5000,
                        showType: 'slide'
                    });
                    return false;
                }
                if (index == taskPageResults[key].length - 1) {
                    loadResults($task_result_pg, ++pageNum, pageSize, function () {
                        _showResultWindow(0);
                    })
                } else {
                    _showResultWindow(++index)
                }
            }
        }

        //总页数
        var getPageCount = function () {
            var total = $task_result_pg.pagination('options').total,
                    rows = $task_result_pg.pagination('options').pageSize;
            return (total + rows - 1) / rows | 0;
        }

        //var _taskPageResults=window.taskPageResults=window.taskPageResults||{};

        if (!window.taskPageResults) {
            window.taskPageResults = {}
        }
        var _taskPageResults = window.taskPageResults;

        var detail_result_tmpl = $('#detail_result_tmpl').html();

        var getKey = function () {
            if ($task_result_pg.length == 0) {
                return taskId + '_1';
            }
            var pageNum = $task_result_pg.pagination('options').pageNumber;
            return taskId + '_' + pageNum;
        }

        $task_result_tab.on('click', '.result_details', function () {
            var index = $(this).data('result-index')
            _showResultWindow(index)
        })

        //显示详情窗口
        var _showResultWindow = function (index) {
            var key = getKey();
            if (!_taskPageResults[key]) {
                $.ajax({
                    url: '/result/findByTaskId.action',
                    type: 'post',
                    data: {'taskId': taskId},
                    async: false,
                    success: function (page) {
                        _taskPageResults[key] = page.rows;
                    }
                })
            }
            var resultVo = getResultVo(index, key);
            if (resultVo.imageUrl.indexOf('${PictureServerHost}/') < 0) {
                resultVo.imageUrl = '${PictureServerHost}/' + encodeURI(resultVo.imageUrl);
            }
            resultVo.index = index;
            resultVo.characteristic=util.formateCharacteristic(resultVo);
            var html = util.parser(detail_result_tmpl, resultVo);
            showWindow(html, resultVo);
        }

        //详情窗口缓存
        var showWindow = function (html, model) {
            var $win=$query_win_result;
            if(!$win.data('window')){
                $win.window({
                    width: 900,
                    height: 600,
                    closed:true,
                    shadow:false
                })
            }
            return $win.window({
                content:html,
                title:model.license,
                onOpen: function () {
            		var $query_win_result = $(this).parent().parent();
            		drawPlate($query_win_result,model)
                },
                onResize:function(){
                    //控制按钮操作频繁
                    setTimeout(function(){
                        $("#previous").removeAttr("disabled");
                        $("#next").removeAttr("disabled");
                    },1000);

                	var $query_win_result = $(this).parent().parent();
                    //最大最小化窗口后图片没有改变尺寸的bug
                    $win.html(html);

            		drawPlate($query_win_result,model)
                },
                onBeforeClose:function(){
                    $win.html("")
                    var $tracker = $(".zoomtracker");
                    if($tracker) {
                        $tracker.remove();
                    }
                }
            }).window("open");
        }

        var drawPlate=function($win,model){
        	var $pic = $query_win_result.find('#pic1'),
	        	$rect_1 = $query_win_result.find('#rect_1'),
	            src = model.imageUrl;
        	var orginalImage = new Image();
            orginalImage.src = src;
            $(orginalImage).bind('load',function(){
                var orginalWidth=orginalImage.width;
                var orginalHeight=orginalImage.height;
        
                var picWidth=$pic.width(); 
                var picHeight=$pic.height();
                var scalex=picWidth/orginalWidth,
                	scaley=picHeight/orginalHeight,
                	xposs=model.locationLeft*scalex,
	            	yposs=model.locationTop*scaley,
	            	rectWidth=model.locationRight*scalex-xposs,
	            	rectHeight=model.locationBottom*scaley-yposs;
                /*车牌结果框已经实现,但是目前框的位置可能不准确,暂时注释*/
                 $rect_1.css({
                     display:'block',
                     top:yposs,
                     left:xposs,
                     width:rectWidth,
                     height:rectHeight
                 });

                
                 if(model.dropRectAndScore != null){
                	 //挂饰
                     var dropArray = JSON.parse(model.dropRectAndScore).rect;
                	for(var i=0;i<dropArray.length;i++){
                		var dropInfo = dropArray[i];
                		 if(i>5){
                			 return;
                		 }
                		 var $dropRect = $('#droprect'+i)
    	                 var dropx = dropInfo.x1*scalex;
    	                 var dropy = dropInfo.y1*scaley;
    	                 var dropWidth = dropInfo.x2*scalex-dropx;
    	                 var dropHeight = dropInfo.y2*scaley-dropy;                                 
    	                 $dropRect.css({
    	                     display:'block',
    	                     top:dropy,
    	                     left:dropx,
    	                     width:dropWidth,
    	                     height:dropHeight
    	                 });
                	 }
	     
                 }
                                	 
               	 if(model.paperRectAndScore != null){
               		 //纸巾盒
                     var paperArray = JSON.parse(model.paperRectAndScore).rect;
	                 for(var i=0;i<paperArray.length;i++){
	                	 var paperInfo = paperArray[i];
                		 if(i>5){
                			 return;
                		 }
		                 var $paperRect = $('#paperrect'+i)
		                 var paperx = paperInfo.x1*scalex;
		                 var papery = paperInfo.y1*scaley;
		                 var paperWidth = paperInfo.x2*scalex-paperx;
		                 var paperHeight = paperInfo.y2*scaley-papery;
		                 $paperRect.css({
		                     display:'block',
		                     top:papery,
		                     left:paperx,
		                     width:paperWidth,
		                     height:paperHeight
		                 });
                     }
               	 }
                                  
                 if(model.sunRectAndScore != null){
                	 //遮阳板
                     var sunArray = JSON.parse(model.sunRectAndScore).rect;
                     for(var i=0;i<sunArray.length;i++){
                    	 var sunInfo = sunArray[i];
                    	 if(i>5){
                			 return;
                		 }
		                 var $sunRect = $('#sunrect'+i)
		                 var sunx = sunInfo.x1*scalex;
		                 var suny = sunInfo.y1*scaley;
		                 var sunWidth = sunInfo.x2*scalex-sunx;
		                 var sunHeight = sunInfo.y2*scaley-suny;
		                 $sunRect.css({
		                     display:'block',
		                     top:suny,
		                     left:sunx,
		                     width:sunWidth,
		                     height:sunHeight
		                 });
                    }
                 }
	             if(model.tagRectAndScore != null){
	                 //年检标签
                     var tagArray = JSON.parse(model.tagRectAndScore).rect;
                     for(var i=0;i<tagArray.length;i++){
                         var tagInfo = tagArray[i];
                         if(i>9){
                             return;
                         }
		                 var $tagRect = $('#tagrect'+i);
		                 var tagx = tagInfo.x1*scalex;
		                 var tagy = tagInfo.y1*scaley;
		                 var tagWidth = tagInfo.x2*scalex-tagx;
		                 var tagHeight = tagInfo.y2*scaley-tagy;
		                 $tagRect.css({
		                     display:'block',
		                     top:tagy,
		                     left:tagx,
		                     width:tagWidth,
		                     height:tagHeight
		                 });

	                }
                     if(tagArray.length > 0){
                         $("#tagNumbersb").text(tagArray.length);
                     }else{
                         $("#tagNumbersb").text('无');
                     }
              	 }
	             
	                  
            })
        }

//拿到结果对象
        var getResultVo = function (index, key) {
            var resultRows = _taskPageResults[key];
            return resultRows[index];
        }
//播放
        $task_result_tab.on('click', '.play_video', function () {
            playResultVideo(this)
        })

        var playResultVideo = function (t) {
            var that = t;
            var parent = $(that).parent();
            var id = parent.find('input[name="id"]').val();
            $.post('/result/findById.action', {resultId: id}, function (d) {
                video_player.play(d.path, d.frame_index);
            });
        }

        var loadResults = function (t, page, rows, callback) {
            var that = t;
            var div = $(that).closest('.results_area_div');
            var ul = div.find('.results_area');
            var tmpl = $('#result_tmpl_page', $task_result_tab).html();
            $.post('/result/findByTaskId.action', {'taskId': taskId, 'page': page, 'rows': rows}, function (d) {
                var pager = d;
                //任务完成或者失败时 停止刷新识别结果。
                if( pager.status == 2 || pager.status == 3){
                    task_unfinished = false;
                }
                _taskPageResults[taskId + '_' + page] = pager.rows;
                $(that).pagination('refresh', {
                    total: pager.total,
                    pageNumber: page,
                    displayMsg: '共{total}条记录'
                });
                var html = '';
                $.each(pager.rows, function (i, o) {
                    o.isResourceVideo = o.taskType == 2 && o.resourceType == 2 ? 'inline-block' : 'none';
                    o.index = i;
                    html += util.parser(tmpl, o);
                });
                $task_result_pg.pagination({
                    total:pager.total,
                    displayMsg:'共{total}条记录'
                });
                if(pager.rows.length == 0) {
                    $('#content', $task_result_tab).html('<div style="margin:0 auto;margin-top: 50px;width:100px;"><span style="font-size: x-large;">无结果</span></div>');
                } else {
                    $('#content', $task_result_tab).html('<ul class="results_area">' + html + '</ul>');
                }
                if (typeof callback == 'function') {
                    callback();
                    //这个callback用于在查看结果详情的时候,翻下一张,翻页时弹出一个window显示结果详情
                    //若不设为null,在上面的翻页后datagrid loadSuccess(包括查询,刷新等)后依然会弹出一个window
                    callback=null;
                }
            });
        }
        loadResults();

        //定时刷新
		var refresh=function(){
			setTimeout(function(){
				if($task_result_pg.data("pagination") && task_unfinished){
					$task_result_pg.pagination('select')
					refresh();
				}
			},20000)
		}
        refresh();

        setTimeout(function(){
            if($task_result_pg.data("pagination") && task_unfinished){
                $task_result_pg.pagination('select')
            }
        },0)

        var clearResults = function () {
            $('#content', $task_result_tab).html('');
        }

        $('.results_area img').each(function (i, e) {
            $(e).attr('src', e.src);
        });
    })()
</script>