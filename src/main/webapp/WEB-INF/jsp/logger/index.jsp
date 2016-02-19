<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div style="margin:5px 0 5px 0;"></div>
<div id="log_datagrid">
    <div class='log_toolbar' style="font-size:14px; margin:0px 0;padding-top:5px; padding-bottom:5px; color:#06C;">
        <div style="padding:10px;font-size:14px; margin:0px 0;padding-top:5px; padding-bottom:5px; color:#06C;">
            <form action="#">
                账号:
                <input id="account" type="text" class="easyui-textbox" style="height:28px;width:8%">
                操作类型:
                <input id="opreType" type="text" class="easyui-textbox" style="height:28px; width:8%">
                操作时间:
                <input id="opre_startTime" type="text" name="startTime" style="width:11%;height:28px;">
                ---
                <input id="opre_endTime" type="text" name="startTime" style="width:11%;height:28px;">
                &nbsp;&nbsp;
                <a href="javascript:void(0)" id="log_submit"
                   class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                <a href="javascript:void(0)" id="reset"
                   class="easyui-linkbutton" data-options="iconCls:'icon-clear'">重置</a>
            </form>
        </div>
    </div>
</div>
<script>
    (function($,w){
        var tab = util.getSelectedTab();
        var $log_grid = tab.find("#log_datagrid"),
                account = tab.find("#account"),
        opre_startTime = tab.find("#opre_startTime"),
        opre_endTime = tab.find("#opre_endTime"),
        opreType = tab.find("#opreType");

        var startTime = opre_startTime.datetimebox({
            editable: false
        });
        var endTime = opre_endTime.datetimebox({
            editable: false
        });

        var getParamData = function () {
            var data = {
                account: account.val(),
                operation: opreType.val(),
                startTime: startTime.combo('getValue'),
                endTime: endTime.combo('getValue')
            };

            $.each(data, function (k, v) {
                data[k] = $.trim(v);
            });

            return data;
        };

        tab.find('#log_submit').click(function (e) {
            e.preventDefault();
            datagrid.datagrid('options').onBeforeLoad=function(p){
                $.extend(p,getParamData());
            };
            datagrid.datagrid("reload");
        });

        tab.find('#reset').click(function (e) {
            var $form = tab.find('form');
            $form.form('reset');
        });

        var datagrid = $log_grid.datagrid({
            url: 'logger/query.action',
            loadMsg: '数据载入中',
            pagination: true,
            rownumbers: true,
            fit: true,
            pageSize: 20,//每页显示的记录条数，默认为10
            columns: [[
                {field:'id',title:'id',hidden:true},
                {field: 'account', title: '账号', width: 100},
                {field: 'operation', title: '操作类型', width: 100},
                {field: 'operObj', title: '操作对象', width: 70},
                {field: 'logInfo', title: '日志内容', width: 80},
                {field: 'input', title: '输入', width: 70},
                {field: 'output', title: '输出', width: 70},
                {field: 'operResult', title: '操作结果', width: 70},
                {field: 'guessIP', title: '客户端ip', width: 100},
                {field: 'createdate', title: '操作时间',formatter: util.formateTime, width: 150}
            ]],
            onBeforeLoad: function (p) {
                $.extend(p, getParamData());
            },
            toolbar: tab.find('.log_toolbar', $log_grid)
        });

        // IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readOnly]").keydown(function (e) {
            e.preventDefault();
        });
    })(jQuery,window)
</script>
