<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<script type="tmpl" id="result_tmpl">
<div style="position:relative;">
<div class="box" style="top: 5px;left: 5px;width:60%;height:auto;position:absolute;">
    <div class="box-small">
		<img id="pic" width="100%" height="100%" src="{#imageUrl}" alt="" />
    	<div class="rect" id="rect">&nbsp;</div>
		<div class="rect" id="mytagRect0" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect1" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect2" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect3" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect4" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect5" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect6" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect7" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect8" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mytagRect9" style="border:solid 1px Yellow;">&nbsp;</div>
		<div class="rect" id="mypaperRect0" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="mypaperRect1" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="mypaperRect2" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="mypaperRect3" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="mypaperRect4" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="mypaperRect5" style="border:solid 1px Blue;">&nbsp;</div>
		<div class="rect" id="mysunRect0" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="mysunRect1" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="mysunRect2" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="mysunRect3" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="mysunRect4" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="mysunRect5" style="border:solid 1px White;">&nbsp;</div>
		<div class="rect" id="mydropRect0" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="mydropRect1" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="mydropRect2" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="mydropRect3" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="mydropRect4" style="border:solid 1px Orange;">&nbsp;</div>
		<div class="rect" id="mydropRect5" style="border:solid 1px Orange;">&nbsp;</div>
	</div>
    <div style="text-align:center;">
        <button id="previous" disabled="disabled" data-turn-index={#index} data-turn-str="previous">上一张</button>
        &ensp;&ensp;&ensp;&ensp;
        <button id="next" disabled="disabled" data-turn-index={#index} data-turn-str="next">下一张</button>
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
            <td>监控点</td>
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
			<td id="tagNumber">{#tag==1?'有':'无'}</td>
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
</div>
</script>
<div id="detail_win" style=" width: 800px; height: 520px;"></div>
<script>
    (function($){
        var $result_tmpl = $('#result_tmpl').html();
        var $detail_win = $('#detail_win');
        var $detailgrid,picServerHost,loadData;
        var dataRows, pageNum,pageSize;
        var opts, total;
        var detailWindow = {
            /**
             * 调用此方法请传入以下四个参数
             * model 当前双击的行
             * pictrueServerHost 图片服务器 请务必在controller的index.action中返回,否则无法显示图片
             * datagrid 当前页面调用 .datagrid({}) 返回的数据列表. 请在onLoadSuccess方法中设置每一行的index，翻页的时候需要用到，若不设置，模板将无法解析、
             * func 当前页加载数据的函数
             */
            open:function (model,pictureServerHost,datagrid,func) {
                $detailgrid = datagrid;
                picServerHost = pictureServerHost;
                loadData = func;
                dataRows = $detailgrid.datagrid("getRows");
                if($detailgrid.datagrid('getPager').length) {
                    pageNum =  $detailgrid.datagrid('getPager').pagination('options').pageNumber;
                    pageSize = $detailgrid.datagrid('getPager').pagination('options').pageSize;
                    opts = $detailgrid.datagrid('getPager').pagination('options');
                    total = opts.total;
                } else {
                    //没有分页时
                    pageNum = 1;
                }
                var $win=$detail_win;
                var cloneRow = $.extend({}, model);
                cloneRow.imageUrl = picServerHost + encodeURI(model.imageUrl);
                var html = util.parser($result_tmpl,cloneRow);
                $win.html(html);
                if(!$win.data('window')){
                    $win.window({
                        width: 900,
                        height: 600,
                        closed:true,
                        shadow:false
                    })
                }
                return $win.window({
                    title:cloneRow.license,
                    onOpen: function () {
                        var $detail_win=$(this).parent().parent();
                        detailWindow.drawPlate($detail_win,cloneRow)
                    },
                    onResize:function(){
                        //控制按钮操作频繁
                        setTimeout(function(){
                            $("#previous").removeAttr("disabled");
                            $("#next").removeAttr("disabled");
                        },1000);

                        var $detail_win=$(this).parent().parent();
                        //最大最小化窗口后图片没有改变尺寸的bug
                        $win.html(html);

                        detailWindow.drawPlate($detail_win,cloneRow)
                    },
                    onBeforeClose:function(){
                        $win.html("")
                        var $tracker = $(".zoomtracker");
                        if($tracker) {
                            $tracker.remove();
                        }
                    }
                }).window("open");
            },
            //翻页
            turnPage:function (index, str) {
                var row;
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
                        loadData(--pageNum, function () {
                            var dataRows_1 = $detailgrid.datagrid("getRows");
                            detailWindow.open(dataRows_1[pageSize - 1],picServerHost,$detailgrid,loadData)
                        },pageSize);
                    } else {
                        row = dataRows[--index];
                        detailWindow.open(row,picServerHost,$detailgrid,loadData)
                    }
                }

                if (str == 'next') {
                    if (pageNum == detailWindow.getPageCount() && index == dataRows.length - 1) {
                        $.messager.show({
                            title: '提示消息',
                            msg: '已经是最后一张',
                            timeout: 5000,
                            showType: 'slide'
                        });
                        return false;
                    }
                    if (index == dataRows.length - 1) {
                        loadData(++pageNum, function () {
                            var dataRows_1 = $detailgrid.datagrid("getRows");
                            detailWindow.open(dataRows_1[0],picServerHost,$detailgrid,loadData)
                        },pageSize);
                    } else {
                        row = dataRows[++index];
                        detailWindow.open(row,picServerHost,$detailgrid,loadData)
                    }
                }
            },
            drawPlate:function($win,model){
                var $pic = $detail_win.find('#pic'),
                        $rect = $detail_win.find('#rect'),
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
                    $rect.css({
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
                            var $dropRect = $detail_win.find('#mydropRect'+i);
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
                            var $paperRect = $detail_win.find('#mypaperRect'+i);
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
                            var $sunRect = $detail_win.find('#mysunRect'+i);
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
                            var $tagRect = $detail_win.find('#mytagRect'+i);
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
                            $("#tagNumber").text(tagArray.length);
                        }else{
                            $("#tagNumber").text('无');
                        }
                    }
                })
            },
            getPageCount:function(){
                //没有分页框架
                if(!$detailgrid.datagrid('getPager').length) {
                    return  1;
                }
                return (total + pageSize - 1) / pageSize | 0;
            }
        };

        //放大
        $detail_win.on('mouseover', '.box-small', function () {
            var findPic = $detail_win.find("#pic");
            findPic.addimagezoom({ // single image zoom
                zoomablefade:false,
                zoomrange: [2, 15],
                magnifiersize: [500,500],
                magnifierpos: 'left',
                cursorshade: true,
                largeimage:findPic[0].src //<-- No comma after last option!
            })
        });

        //翻页
        $detail_win.on('click', '#previous,#next', function () {
            var self = this;
            var turnIndex = $(self).data('turn-index');
            var turnStr = $(self).data('turn-str');

            //避免按钮在数据未加载完成时连续点击，总页数读取出现错误,引发提示框弹出提示
            self.disabled = true;

            detailWindow.turnPage(turnIndex, turnStr)
        });

        window.detail_window = detailWindow;
    })(jQuery)
</script>