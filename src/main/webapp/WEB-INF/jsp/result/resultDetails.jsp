<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<div class="result_details_info" style="width:100%;height:100%;padding: 10px;">
	<div class="pic_play_area round_border" style="width:65%;height:100%;float:left;">
		<img class="result_img" onerror="util.noFind()" width="100%" height="100%" src="${PictureServerHost}/${result.imageUrl}" style="float:left;"/>
	</div>
			<table class="info_table">
				<tr>
					<td>车牌</td>
					<td>${result.license}</td>
				</tr>
				<tr>
					<td>车牌颜色</td>
					<td>${result.plateColor}</td>
				</tr>
				<tr>
					<td>车牌类型</td>
					<td>${result.plateType}</td>
				</tr>
				<tr>
					<td>车牌可信度</td>
					<td>${result.confidence}</td>
				</tr>
				<tr>
					<td>车牌归属地</td>
					<td>${result.licenseAttribution}</td>
				</tr>
				<tr>
					<td>车身颜色</td>
					<td>${result.carColor}</td>
				</tr>
				<tr>
					<td>过车时间</td>
					<td><fmt:formatDate value="${result.resultTime}" type="both"/></td>
				</tr>
				<tr>
					<td>过车行政区</td>
					<td>${result.location}</td>
				</tr>
				<tr>
					<td>方向</td>
					<td>${result.direction}</td>
				</tr>
				<tr>
					<td>经度</td>
					<td>${result.longitude}</td>
				</tr>
				<tr>
					<td>纬度</td>
					<td>${result.latitude}</td>
				</tr>
			</table>
			<div class="clear"></div>
</div>
<script type="text/javascript">
(function() {
	$('.pic_play_area img').each(function(i,e) {
		$(e).attr('src',e.src);
	});
})()
</script>