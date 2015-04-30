<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div class="easyui-layout" fit=true>
	<div region="north" style="height:100px;min-width: 1000px;" split=true>
		<table class="info_area">
			<tr>
				<td><span>名 称:</span> ${camera.name}</td>
				<td><span>品 牌:</span> ${camera.brandName}</td>
				<td><span>型&ensp;&thinsp;号:</span> ${camera.model}</td>
				<td>
					<span>机&ensp;&thinsp;型:</span> 
					<c:choose>
					    <c:when test="${camera.type == 1}">球机</c:when>
					    <c:otherwise>枪机</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td><span>IPv4:</span> ${camera.ip}</td>
				<td><span>通 道:</span> ${camera.channel}</td>
				<td><span>端口1:</span> ${camera.port1}</td>
				<td><span>端口2:</span> ${camera.port2}</td>
			</tr>
			<tr>
				<td><span>经 度:</span> ${camera.longitude}</td>
				<td><span>纬 度:</span> ${camera.latitude}</td>
				<td>
					<span>状&ensp;&thinsp;态:</span> 
					<c:choose>
					    <c:when test="${camera.status == 1}">激活</c:when>
					    <c:otherwise>失效</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
	</div>
	<div title="视频源管理" region="center">
		<div id="resource_tabs" class="easyui-tabs" data-options="tabPosition:'left',headerWidth:100" fit=true border=false>
			<div title="实时源">
				<jsp:include page="../resource/realTime.jsp"></jsp:include>
			</div>
			<div title="离线源">
				<jsp:include page="../resource/resource.jsp"></jsp:include>
			</div>
			<%--<div title="批量识别">
				<jsp:include page="../task/batchTask.jsp"></jsp:include>
			</div>
		--%></div>
	</div>
</div>
