<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ include file="../header.jsp" %>
<div region="center">
<div id="task_tab" class="easyui-tabs" fit=true>
    <div title="任务管理">
        <div id="task_datagrid">
            <div class='toolbar'>
                <form action="#">
                    <label>任务名称:
                        <input id="name" type="text" class="easyui-textbox"></label>
                    <label>所属监控点:
                        <input id="cameraIds"></label>
                    <label>任务状态:
                        <input id="status" type="text"></label>
                    <label>离线视频名称:
                        <input id="offlinevideo"></label><br/>

                    <label>任务类型:
                        <input id="type" type="text"></label>
                    <label>开&nbsp;始&nbsp;时&thinsp;&nbsp;间:
                        <input id="startTime" type="text" name="startTime"></label>
                    <label>结束时间:
                        <input id="endTime" type="text" name="startTime"></label>

                    <a href="javascript:void(0)" id="query" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
<script>
    (function ($) {
        var tab = $("#task_tab").tabs();
        var datagrid = $("#task_datagrid");
        var name = $('#task_datagrid #name');
        var offlinevideo = $('#task_datagrid #offlinevideo');

        //存放被选择的监控点
        var camIdArr = [], camNameArr = [], $query_camera = tab.find("#cameraIds");
        //树形下拉框 监控点
        var cameraIds = $query_camera.combotree({
            url: '/camera/camLstTree.action',
            animate: true,
            multiple: true,
            onCheck: function (node, checked) {
                util.checkCam(cameraIds, node, camIdArr, camNameArr, checked);
            },
            onClick: function (node) {
                util.checkCam(cameraIds, node, camIdArr, camNameArr, node.checked);
            },
            onChange: function (nVal, oVal) {
                util.checkCamWithMap(cameraIds);
            }
        });
        var type = $('#task_datagrid #type').combobox({
            editable: false,
            panelHeight: 'auto',
            data: [{
                value: '',
                text: '全部'
            }, {
                value: '1',
                text: '实时任务'
            }, {
                value: '2',
                text: '离线任务'
            }, {
                value: '3',
                text: '批量任务'
            }, {
                value: '4',
                text: '实时图片任务'
            }
            ]
        });
        var startTime = $('#task_datagrid #startTime').datetimebox({
            editable: false
        });
        var endTime = $('#task_datagrid #endTime').datetimebox({
            editable: false
        });
        var status = $('#task_datagrid #status').combobox({
            editable: false,
            panelHeight: 'auto',
            data: [{
                value: '',
                text: '全部'
            }, {
                value: '1',
                text: '识别中'
            }, {
                value: '2',
                text: '已完成'
            }, {
                value: '3',
                text: '已失败'
            }]
        });

        var offlineVideo = offlinevideo.combogrid({
            delay: 500,
            panelWidth: 400,
            singleSelect: true,
            idField: 'name',
            textField: 'name',
            url: '/resource/list.action?type=2',
            multiple: true,
            fitColumns: true,
            striped: true,
            editable: false,
            pagination: true,//是否分页
            rownumbers: true,//序号
            collapsible: false,//是否可折叠的
            pageSize: 10,//每页显示的记录条数，默认为10
            pageList: [10, 20, 50],//可以设置每页记录条数的列表
            method: 'post',
            columns: [[
                {field: 'id', title: 'id', hidden: true},
                {field: 'checkbox', checkbox: true},
                {field: 'name', title: '离线视频名称', width: 150, sortable: true}
            ]],
            keyHandler: {
                up: function () {

                },
                down: function () {

                },
                left: function () {

                },
                right: function () {

                },
                enter: function () {

                },
                query: function (q) {
                    //修得原来将输入的字符串当成id向后台传递的bug.
                    $.post('/resource/list.action?type=2', {'name': q}, function (data) {
                        offlinevideo.combogrid("grid").datagrid("loadData", data);
                        offlinevideo.combogrid("setText", q);
                    })
                }
            }
        });
        var getParamData = function () {
            var data = {
                name: name.val(),
                ids: cameraIds.combo('getValues').join(),
                type: type.combo('getValue'),
                startTime: startTime.combo('getValue'),
                endTime: endTime.combo('getValue'),
                status: status.combo('getValue'),
                offlineVideoName: offlineVideo.combo('getText')
            }
            $.each(data, function (k, v) {
                data[k] = $.trim(v);
            });
            return data;
        }

        $('#task_datagrid #query').click(function (e) {
            e.preventDefault();
            datagrid.datagrid("reload");
        });
        datagrid.datagrid({
            url: '/task/query.action',
            loadMsg: '数据载入中',
            pagination: true,
            rownumbers: true,
            fit: true,
            singleSelect: true,
            columns: [[
                {field: 'name', title: '任务名', width: 300},
                {field: 'cameraName', title: '所属监控点', width: 150},
                {field: 'type', title: '任务类型', formatter: util.formateTaskType, width: 150},
                {field: 'startTime', title: '开始时间', formatter: util.formateTime, width: 150},
                {field: 'endTime', title: '结束时间', formatter: util.formateTime, width: 150},
                {field: 'status', title: '状态', formatter: util.formateTaskStatus, width: 150},
                {
                    field: 'showResult', title: '已识别车辆', formatter: function (v, row) {
                    var rowId = '任务管理' + row.id
                    var html = '<a id="' + rowId + '" class="showResult" href="javascript:void(0);">' + row.count + '</a>';
                    tab.off('click', '#' + rowId).on('click', '#' + rowId, function () {
                        newTab(row)
                    })
                    return html;
                }
                },
                {
                    field: 'stop', title: '操作', formatter: function (v, row, index) {
                    return row.status == 1 ? '<a href="javascript:void(0)" onclick="stopTask(' + row.id + ')">停止</a>' : '';
                }, width: 150
                },
                {
                    field: 'xxxx', title: '下  载', formatter: function (v, row) {
                    var html = '<a id="' + "download" + row.id + '" class="showResult" href="javascript:void(0);">' + '下载' + '</a>';
                    tab.off('click', '#download' + row.id).on('click', '#download' + row.id, function () {
                        //判断条目数
                        $.ajax({
                            url: '/task/downloadItemCount.action',
                            data: {taskId: row.id},
                            type: 'post',
                            success: function (item) {
                                if (item > 2000) {
                                    $.messager.show({
                                        title: "提示消息",
                                        msg: "数据条目数大于2000，无法下载。",
                                        timeout: 3000,
                                        showType: 'slide'
                                    });
                                } else {
                                    var form = $("<form>");
                                    form.attr('style', 'display:none');
                                    form.attr('target', '');
                                    form.attr('method', 'post');
                                    form.attr('action', '/task/download.action');

                                    var input = $('<input>');
                                    input.attr('type', 'hidden');
                                    input.attr('name', 'taskId');
                                    input.attr('value', row.id);
                                    form.append(input);

                                    $('body').append(form);
                                    form.submit();
                                    form.remove();
                                }
                            }
                        });
                    })
                    return html;
                }, width: 50
                }
            ]],
            toolbar: $('#task_datagrid .toolbar'),
            onBeforeLoad: function (p) {
                $.extend(p, getParamData());
            },
            onDblClickRow: function (i, r) {
                newTab(r);
            }
        });

        datagrid.datagrid('getPanel').on("keyup", 'input:text', function (e) {
            if (e.keyCode == 13) {
                datagrid.datagrid('reload');
            }
        });

        var refresh = function () {
            setTimeout(function () {
                if (datagrid.data('datagrid')) {
                    datagrid.datagrid('reload');
                    refresh();
                }
            }, 5000)
        }
        refresh();

        var newTab = function (row) {
            var title = '任务结果-' + row.name;
            if (tab.tabs('exists', title)) {
                tab.tabs('select', title)
            } else {
                tab.tabs('add', {
                    title: title,
                    closable: true,
                    href: '/result/results.action?taskId=' + row.id
                });
            }
        }
// IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readOnly]").keydown(function (e) {
            e.preventDefault();
        });
        window.stopTask = function (taskId) {
            $.post('task/stop.action', {'taskId': taskId}, function (msg) {
                if (null != msg && '' != msg) {
                    $.messager.show({
                        title: '提示',
                        msg: '任务已于' + util.formateTime(msg) + '停止，无需停止！',
                        timeout: 2000
                    });
                }
            });
            datagrid.datagrid("reload");
        }

        window.document.title= tab.tabs("getSelected").panel("options").title;

    })(jQuery);
</script>
<jsp:include page="../footer.jsp"></jsp:include>
