<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div id="collectPictures">
    <div class='toolbar'>
        <form action="#" style="padding: 10px">
            <label>监控点:<input id="cameras" style="width: 200px;"><a href="javascript:void(0)"><span id="map_button" class="l-btn-left l-btn-icon-left"><span
                    class="l-btn-text">地图</span><span class="l-btn-icon icon-search">&nbsp;</span></span></a></label>&nbsp;
            <label>采集状态:<input id="status" type="text"></label>&nbsp;
            <a href="javascript:void(0)" id="query" class="easyui-linkbutton"
               data-options="iconCls:'icon-search'">查询</a>
            <a href="javascript:void(0)" id="empty_query" class="easyui-linkbutton"
               data-options="iconCls:'icon-clear'">重置</a>
        </form>
        <br>
        <a id="addBtn" href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加</a>&nbsp;
        <a id="delBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain=true>删除</a>&nbsp;&nbsp;
        <a id="startBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain=true>批量启动</a>&nbsp;
        <a id="pauseBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain=true>批量暂停</a>&nbsp;
        <a id="stopBtn" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-tip" plain=true>批量终止</a>
    </div>
</div>
<script>
    (function ($, w) {
        //存放被选择的id
        var checkboxItemArray = new Array();
        //自建函数
        Array.prototype.jiuLingIndexOf = function(val){
            for(var i=0;i<this.length;i++){
                if(this[i] == val){
                    return i;
                }
            }
            return -1;
        }
        Array.prototype.jiuLingRemove = function(val){
            var index = this.jiuLingIndexOf(val);
            if(index > -1){
                this.splice(index,1);
            }
        }
        Array.prototype.jiuLingAdd = function(val){
            var isRepeated = false;
            for(var i=0;i<this.length;i++){
                if(this[i] == val){
                    isRepeated = true;
                }
            }
            if(!isRepeated){
                this.push(val);
            }
        }

        var collectPicture = w.collectPicture;
        var datagrid = $("#collectPictures");


        //存放被选择的监控点
        var camIdArr = [],camNameArr = [];
        //树形下拉框 监控点
        var cameras = $('#collectPictures #cameras').combotree({
            url: '/camera/camLstTree.action',
            animate:true,
            multiple:true,
            onCheck:function(node,checked) {
                util.checkCam(cameras,node,camIdArr,camNameArr,checked);
            },
            onClick:function(node) {
                util.checkCam(cameras,node,camIdArr,camNameArr,node.checked);
            },
            onChange:function(nVal,oVal) {
                util.checkCamWithMap(cameras);
            }
        });

        //地图按钮点击事件
        $('#collectPictures #map_button').click(function (e) {
            e.preventDefault();
            pubMapWindow.open(cameras, camIdArr,camNameArr);
        });

        //状态（查询条件）
        var status = $('#collectPictures #status').combobox({
            editable: false,
            panelHeight: 'auto',
            data: [{
                value: '',
                text: '全部'
            }, {
                value: '0',
                text: '等待中'
            }, {
                value: '1',
                text: '下载中'
            }, {
                value: '3',
                text: '暂停中'
            }, {
                value: '2',
                text: '已完成'
            }, {
                value: '4',
                text: '已终止'
            }, { //进行中（等待中、下载中、暂停中）
                value: '5',
                text: '进行中'
            }, { //已结束（已完成、已终止）
                value: '6',
                text: '已结束'
            }]
        });

        //获取查询条件参数信息
        var getParamData = function () {
            var data = {
                status: status.combo('getValue'),
                cameraNames: cameras.combo('getText')
            }
            $.each(data, function (k, v) {
                data[k] = $.trim(v);
            });
            return data;
        }

        //--------查询--------
        $('#collectPictures #query').click(function (e) {
            e.preventDefault();
            checkboxItemArray.length = 0;
            datagrid.datagrid("reload");
        });

        //--------重置（查询条件信息）--------
        $('#collectPictures #empty_query').click(function (e) {
            var $form = datagrid.find('form');
            $form.form('reset');
            camIdArr = [];
            camNameArr = [];
            cameras.combotree("clear");
            status.combobox("setValue", '');
        });

        //-----------添加---------
        $('#collectPictures #addBtn').click(function (e) {
            collectPicture.dialogAdd('添加采集任务');
        });

        //--------删除--------
        $('#collectPictures #delBtn').click(function (e) {
            if(checkboxItemArray.length == 0){
                $.messager.alert("提示!", "请选择要删除的记录!");
                return;
            }
            $.messager.confirm('提示!', '是否确认删除？', function (r) {
                if (r) {
                    $.ajax({
                        url: 'collectPictures/delete.action',
                        type: "post",
                        data: {
                            ids: checkboxItemArray.join(),
                            isAllDelete: true
                        },
                        success: function (data) {
                            if (data == "success") {
                                checkboxItemArray.length = 0;
                                datagrid.datagrid('reload');
                            } else {
                                $.messager.show({
                                    title: '删除失败！',
                                    msg: data,
                                    timeout: 1000
                                });
                            }
                        }
                    });
                }
            });
        });

        //--------批量启动--------
        $('#collectPictures #startBtn').click(function (e) {
            if(checkboxItemArray.length == 0){
                $.messager.alert("提示!", "请选择要启动的记录!");
                return;
            }
            $.messager.confirm('提示!', '是否确认批量启动？', function (r) {
                if (r) {
                    window.startCollectPictureTask(checkboxItemArray.join());
                }
            });
        });

        //--------批量暂停--------
        $('#collectPictures #pauseBtn').click(function (e) {
            if(checkboxItemArray.length == 0){
                $.messager.alert("提示!", "请选择要暂停的记录!");
                return;
            }
            $.messager.confirm('提示!', '是否确认批量暂停？', function (r) {
                if (r) {
                    window.stopOrPause(checkboxItemArray.join(),3);
                }
            });
        });

        //--------批量终止--------
        $('#collectPictures #stopBtn').click(function (e) {
            if(checkboxItemArray.length == 0){
                $.messager.alert("提示!", "请选择要终止的记录!");
                return;
            }
            $.messager.confirm('提示!', '是否确认批量终止？', function (r) {
                if (r) {
                    window.stopOrPause(checkboxItemArray.join(),4);
                }
            });
        });

        datagrid.datagrid({
            url: '/collectPictures/list.action',
            loadMsg: '数据载入中',
            pagination: true,
            rownumbers: true,
            fit: true,
            singleSelect: false,
            columns: [[
                {field: 'id', checkbox: true},
                {field: 'cameraName', title: '监控点', width: 200},
                {field: 'startTime', title: '采集时段(起始时间)', formatter: util.formateTime, width: 160},
                {field: 'endTime', title: '采集时段(截止时间)', formatter: util.formateTime, width: 160},
                {field: 'status', title: '状态', formatter: util.formateCollectPictrureStatus, width: 80},
                {field: 'passTime', title: '采集进程时间', formatter: util.formateTime, width: 160},
                {field: 'downloadCount', title: '已采集数量', width: 100},
                {
                    field: 'stop', title: '操作', formatter: function (v, row, index) {
                    return row.status == 0 ? '<a href="javascript:void(0)" onclick="stopOrPause(' + row.id + ',3)">暂停</a>'+' | '+'<a href="javascript:void(0)" onclick="stopOrPause(' + row.id + ',4)">终止</a>'
                            : row.status == 1 ? '<a href="javascript:void(0)" onclick="stopOrPause(' + row.id + ',3)">暂停</a>'+' | '+'<a href="javascript:void(0)" onclick="stopOrPause(' + row.id + ',4)">终止</a>'
                            : row.status == 3 ? "<a href='javascript:void(0)' " + "onclick=startCollectPictureTask(" + row.id + ")>启动</a>" +' | '+'<a href="javascript:void(0)" onclick="stopOrPause(' + row.id + ',4)">终止</a>': '';
                }, width: 80
                }
            ]],
            toolbar: $('#collectPictures .toolbar'),
            onBeforeLoad: function (p) {
                $.extend(p, getParamData());
            },onLoadSuccess: function (pager) {
                //勾选多选框
                //获取当前页的所有行
                var myrows = datagrid.datagrid('getRows');
                for(var i=0;i<myrows.length;i++){
                    if(checkboxItemArray.jiuLingIndexOf(myrows[i].id)>-1){
                        var myindex = datagrid.datagrid('getRowIndex',myrows[i]);
                        datagrid.datagrid('checkRow',myindex);
                    }
                }

            },
            onCheck:function(rowIndex,rowData){
                checkboxItemArray.jiuLingAdd(rowData.id);
            },
            onUncheck:function(rowIndex,rowData){
                checkboxItemArray.jiuLingRemove(rowData.id);
            },
            onCheckAll:function(rows){
                for(var i=0;i<rows.length;i++){
                    checkboxItemArray.jiuLingAdd(rows[i].id);
                }
            },
            onUncheckAll:function(rows){
                for(var i=0;i<rows.length;i++){
                    checkboxItemArray.jiuLingRemove(rows[i].id);
                }
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
            }, 10000)
        }
        refresh();

        collectPicture.form.form({
            success: function (data) {
                if (data == "success") {
                    $.messager.show({
                        title: '消息',
                        msg: '提交成功！',
                        timeout: 2000
                    });
                    collectPicture.dialog.dialog('close');
                    datagrid.datagrid("reload");
                } else {
                    $.messager.alert({title: '错误', msg: '提交失败'});
                }
            }
        });

        // IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readOnly]").keydown(function (e) {
            e.preventDefault();
        });

        //--------终止/暂停 采集图片任务 oper：3、暂停 4、终止--------
        window.stopOrPause = function (cpIds,oper) {
            $.post('collectPictures/stopOrPause.action', {'ids': cpIds,'oper':oper}, function (msg) {
                if ('success' == msg) {
                    checkboxItemArray.length = 0;
                    datagrid.datagrid("reload");
                }else{
                    $.messager.show({
                        title: '提示',
                        msg: '操作失败'
                    });
                }
            });
        }

        //--------开启采集图片任务--------
        window.startCollectPictureTask = function (cpIds) {

            $.post('collectPictures/start.action', {'ids': cpIds}, function (msg) {
                if ('success' == msg) {
                    checkboxItemArray.length = 0;
                    datagrid.datagrid("reload");
                }else{
                    $.messager.show({
                        title: '提示',
                        msg: '启动失败！'
                    });
                }
            });
        }

    })(jQuery, window);
</script>
