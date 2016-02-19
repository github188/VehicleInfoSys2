<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<div class="easyui-layout" fit="true">
    <div id="camListRegion" title='监控点列表' region='center'>
        <div id="cam_grid" fit=true></div>
    </div>
</div>

<script type="text/javascript">
    (function($){
        var tab = util.getSelectedTab();
        var grid = tab.find("#cam_grid"), gPageType = ${pageType},
                _datagrid;


        _datagrid = grid.datagrid({
            url:'camera/list.action',
            loadMsg:'数据载入中',
            pagination:true,
            rownumbers:true,
            fit: true,
            singleSelect:true,
            onLoadSuccess:function(){
                var self=$(this).parent();
                setTimeout(function(){
                    var a=self.find('a').linkbutton({plain:true})
                })
            },
            idField:'id',
            columns:[[
                {field:'name',title:'监控点名称',styler:function(){
                    return 'width: 170px;height:27px';
                }},
                {field: 'type', title: '类型', width: 40},
                {field:'desc',title:'描述'},
                {field:'operate',title:'操作',width:120,formatter:function(v,row){
                    var  html='<a href="#" onclick="javascript:showCameraDetails('+row.id+ ', \'' + row.name + '\')" data-options="iconCls:\'icon-details\'" title="详情"></a>'
                    if (gPageType == 1) {
                        html+='<a href="#" onclick="javascript:editCamera('+row.id+')" data-options="iconCls:\'icon-edit\'" title="修改"></a>'
                        html+='<a href="#" onclick="javascript:manageCamera('+row.id+')" data-options="iconCls:\'icon-manage\'" title="管理"></a>'
                    }
                    return html;
                }}
            ]],
            toolbar: [{
                iconCls: 'icon-add',
                text: "添加"
            }]
        });
    })(jQuery)
</script>