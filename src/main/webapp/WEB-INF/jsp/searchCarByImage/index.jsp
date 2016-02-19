<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" fit=true>
<div region="north" data-options="border:false" style="height:0px;width:100%;" split=false>
</div>
<div region="center">
<div id="resource_tabs" class="easyui-tabs" data-options="tabPosition:'left',headerWidth:105,tabHeight:40" fit=true border=false>	
	<div title="以图搜车(自动)" collapsible="false">
		<jsp:include page="searchCar.jsp"></jsp:include>	
	</div>
	<div title="以图搜车(手动)" collapsible="false">
		<jsp:include page="searchCarManual.jsp"></jsp:include>	
	</div>
	<div title="任务管理">
		<jsp:include page="task.jsp"></jsp:include>		
	</div>	
</div>
</div>
</div>
