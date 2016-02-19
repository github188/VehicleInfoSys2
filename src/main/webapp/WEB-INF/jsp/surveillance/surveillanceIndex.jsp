<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://caselib.com/caselibtaglib" prefix="p"%>
<div id="surveillance_index_tabs" class="easyui-tabs" data-options="tabPosition:'left',headerWidth:105,tabHeight:40" fit=true border=false>

    <p:privilege privilege="apply">
        <div title="布控申请" collapsible="false">
            <jsp:include page="surveillanceApplicant.jsp"></jsp:include>
        </div>
    </p:privilege>

    <p:privilege privilege="mange">
        <div title="布控管理" collapsible="false">
            <jsp:include page="surveillanceManger.jsp"></jsp:include>
        </div>
    </p:privilege>

    <p:privilege privilege="surveillance_audit">
        <div title="布控审核" collapsible="false">
            <jsp:include page="examineSurveillanceTask.jsp"></jsp:include>
        </div>
    </p:privilege>

    <p:privilege privilege="audit_control">
        <div title="撤控审核" collapsible="false">
            <jsp:include page="revokeSurveillanceTask.jsp"></jsp:include>
        </div>
    </p:privilege>

    <p:privilege privilege="alarm_query">
        <div title="报警查询">
            <jsp:include page="surveillanceResults.jsp"></jsp:include>
        </div>
    </p:privilege>

</div>
