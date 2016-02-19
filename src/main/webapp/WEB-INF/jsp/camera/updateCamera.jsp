<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<style type="text/css">
    #updateCameraForm table label {
        float: right;
    }

    fieldset {
        width: 60%;
        margin: 50px;
        font-size: 12px;
        border: 1px dotted #95B8E7;
        -moz-border-radius: 5px;
        -webkit-border-radius: 5px;
    }

    legend {
        font-size: 14px;
    }
</style>
<div id="updateCamera" class="easyui-layout" fit=true>
    <div region="center">
        <form id="updateCameraForm" style="width:100%" method="post">
            <input type="hidden" class="brandName" name="brandName" value="${cameraVO.brandName}">
            <fieldset style="">
                <legend>基本信息</legend>
                <table class="table_tabs">
                    <tr>
                        <td style="display:none"><input type="text" id="id" name="id" value='${cameraVO.id}' /></td>
                        <td style="display:none"><input type="text" id="url" name="url" value='${cameraVO.url}' /></td>
                        <td><label>监控点ID:</label></td>
                        <td><input type="text" id="cameraId" name="cameraId" class="easyui-textbox" disabled
                                   data-options="required:true,validType:'length[0,100]'" value='${cameraVO.id}'/></td>
                        <td><span style="color:red">*</span><label>监控点名称:</label></td>
                        <td><input type="text" id="name" name="name" class="easyui-textbox" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[0,50],remote:['/camera/valideName.action?id=${cameraVO.id}','name']}" value='${cameraVO.name}'/></td>
                        <td><label>监控点类型:</label></td>
                        <td><label><input name="cameraType" type="radio" value=1 ${cameraVO.cameraType==1?"checked":""}/>卡口</label>
                            <label><input name="cameraType" type="radio" value=2 ${cameraVO.cameraType==2?"checked":""}/>电警</label>
                            <label><input name="cameraType" type="radio" value=0 ${cameraVO.cameraType==0?"checked":""}/>其他</label></td>
                    </tr>
                    <tr>
                        <td><label>机型:</label></td>
                        <td><label><input name="type" type="radio" value=1 ${cameraVO.type==1?"checked":""}/>球机</label>
                            <label><input name="type" type="radio" value=2 ${cameraVO.type==2?"checked":""}/>枪机</label>
                            <label><input name="type" type="radio" value=0 ${cameraVO.type==0?"checked":""}/>其他</label></td>
                        <td><label>行政区划:</label></td>
                        <td>
                            <input class="easyui-combotree" name="region" value='${cameraVO.region}'
                                   data-options="url:'area/areaTreeJson.action',
                                   editable:false,
							    panelHeight:'200',
							    onClick:function(node) {
                                    var queryParams = {
                                        id:node.id,
                                        name:node.text
                                    };
                                },loadFilter: function(data){
							        return util.getNodes(data);
							    },onShowPanel:function(){
							    	$(this).combotree('reload');
							    }" />
                        </td>
                    </tr>
                    <tr>
                        <td><label>经度:</label></td>
                        <td><input type="text" id="longitude" name="longitude" class="easyui-textbox" data-options="required:true,validType:{range:[-180,180,14],length:[1,20]}" value='${cameraVO.longitude}'/></td>
                        <td><label>纬度:</label></td>
                        <td><input type="text" id="latitude" name="latitude" class="easyui-textbox" data-options="required:true,validType:{range:[-90,90,16],length:[1,20]}" value='${cameraVO.latitude}'/></td>
                    </tr>
                    <tr>
                        <td><label>方向:</label></td>
                        <td><input type="text" id="direction" name="direction" class="easyui-textbox" value='${cameraVO.direction}'/></td>
                        <td><label>地点:</label></td>
                        <td><input type="text" id="location" name="location" class="easyui-textbox" data-options="validType:{length:[0,100]}" value='${cameraVO.location}'/></td>
                    </tr>
                    <tr>
                        <td valign="top"><label>状态:</label></td>
                        <td valign="top">
                            <label><input name="status" type="radio" value=1 ${cameraVO.status==1?"checked":""}/>激活</label>
                            <label><input name="status" type="radio" value=0 ${cameraVO.status==0?"checked":""}/>禁用</label>
                        </td>
                        <td valign="top"><label>描述:</label></td>
                        <td><textarea name="dsc" rows="5" cols="1"  class="easyui-validatebox" data-options="validType:{length:[0,250],letterAndNumberAndChinese:true}" >${cameraVO.dsc}</textarea></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset>
                <legend>物理信息</legend>
                <table class="table_tabs">
                    <tr>
                        <td><label>品牌:</label></td>
                        <td>
                            <div style="display: none">
                            <input id="tempurl" class="easyui-combobox" name="tempurl" value="${cameraVO.brandId}"
                                   data-options="valueField:'value',textField:'url',url:'js/json/vendertempurl.json'"/>
                            </div>
                            <input id="brandId" class="easyui-combobox" name="brandId" value="${cameraVO.brandId}"
                                   data-options="editable:false,valueField:'value',textField:'text',url:'js/json/vendertempurl.json',
                                   onSelect:function(rec){
                                        $('#tempurl').combobox('setValue',rec.value);
                                   }"/>
                        </td>
                        <td><label>型号:</label></td>
                        <td><input type="text" id="model" name="model" class="easyui-textbox" data-options="validType:{length:[0,50],letterAndNumberAndChinese:true}" value='${cameraVO.model}'/></td>
                    </tr>
                    <tr>
                        <td><label>IP:</label></td>
                        <td><input type="text" id="ip" name="ip" class="easyui-textbox" data-options="validType:{length:[0,50],checkIp:[this]}" value='${cameraVO.ip}'/></td>
                        <td><label>端口1:</label></td>
                        <td><input type="text" id="port1" name="port1" class="easyui-textbox" data-options="validType:{length:[0,50],num:[this]}" value='${cameraVO.port1}'/></td>
                        <td><label>端口2:</label></td>
                        <td><input type="text" id="port2" name="port2" class="easyui-textbox" data-options="validType:{length:[0,50],num:[this]}" value='${cameraVO.port2}'/></td>
                    </tr>
                    <tr>
                        <td><label>账号:</label></td>
                        <td><input type="text" id="account" name="account" class="easyui-textbox" data-options="validType:{length:[0,50],letterAndNumberAndChinese:true}" value='${cameraVO.account}'/></td>
                        <td><label>密码:</label></td>
                        <td><input type="text" id="password" name="password" class="easyui-textbox" data-options="validType:{length:[0,50],letterAndNumberAndChinese:true}" value='${cameraVO.password}'/></td>
                    </tr>
                    <tr>
                        <td><label>通道:</label></td>
                        <td><input type="text" id="channel" name="channel" class="easyui-textbox" data-options="validType:{length:[0,50],num:[this]}" value='${cameraVO.channel}'/></td>
                        <td><label>设备编号:</label></td>
                        <td><input type="text" id="extCameraId" name="extCameraId" class="easyui-textbox" data-options="validType:{length:[0,50],num:[this]}" value='${cameraVO.extCameraId}'/></td>
                    </tr>
                </table>
            </fieldset>
            <fieldset>
                <legend>维护信息</legend>
                <table class="table_tabs">
                    <tr>
                        <td><label>管理责任单位:</label></td>
                        <td><input type="text" id="adminDept" name="adminDept" class="easyui-textbox" value='${cameraVO.adminDept}'/></td>
                        <td><label>责任人:</label></td>
                        <td><input type="text" id="admin" name="admin" class="easyui-textbox" value='${cameraVO.admin}'/></td>
                    </tr>
                    <tr>
                        <td><label>联系电话:</label></td>
                        <td><input type="text" id="telephone" name="telephone" class="easyui-textbox" value='${cameraVO.telephone}'/></td>
                        <td><label>联系地址:</label></td>
                        <td><input type="text" id="address" name="address" class="easyui-textbox" value='${cameraVO.address}'/></td>
                    </tr>
                </table>
            </fieldset>
        </form>
    </div>
    <div align="center" class="button_area" region='south'>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="javascript:submitUpdateCamera()"> 确认</a>
    </div>
</div>
<script type="text/javascript">

    function submitUpdateCamera(){
        var tab = util.getSelectedTab(),
                model = ${jsonObj}
        $.messager.confirm('提示', '确定要修改吗？', function(y) {
            if (y) {
                //同步品牌名称
                var $form = tab.find('form'),
                     $brandId = $form.find("#brandId"),
                        $url = $form.find("#url"),
                        val = $brandId.combobox('getValue');
                if (val !='' && val > 0) {
                    var brandName = $brandId.combobox('getText');
                    $form.find(".brandName").val(brandName);
                } else {
                    $form.find(".brandName").val("");
                }

                var obj = $.extend({}, model),
                        arr = $form.serializeArray();
                $.each(arr, function (i, o) {
                    if (o.value != "" || obj[o.name] != "") {
                        obj[o.name] = o.value;
                    }
                });

                //获取(实时流)厂家地址
                var tempUrl = $form.find("#tempurl").combobox('getText');
                if (tempUrl) {
                    $url.val(util.parser(tempUrl, obj));
                }
                $('#updateCameraForm').form('submit', {
                    url: "camera/saveUpdatedCamera.action",
                    success: function(data) {
                        if (data != 'success') {
                            $.messager.alert("提示!","请检查表单是否存在错误信息！"+data);
                            return;
                        }
                        tab.panel("refresh","camera/update.action");

                        mainTabs.find("#map_camera_datagrid").datagrid("reload");
                        $.messager.show({
                            title:'消息',
                            msg:'更新监控点成功！',
                            timeout:2000
                        });
                    }
                });
            }
        });
    }
    (function($){
        var $map = mainTabs.find("#main_map").data("map"),
                camera = mainTabs.find("#main_map").data("camera"),
                d = camera.getMarker(${cameraVO.id}).dto;
        var camJSON = ${jsonObj},marker = camera.getMarker(camJSON.id);
        camera.cache(camJSON.id,{dto:camJSON,marker:marker});
        marker.dto = camJSON;
        var bounds = $map.getBounds();
        camera.showInBounds(bounds,marker,true);
        // IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readOnly]").keydown(function (e) {
            e.preventDefault();
        });
    })(jQuery)
</script>