<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="easyui-layout" fit="true" style="width:100%;height:100%;">
    <div data-options="region:'north',split:false" style="width:100%;height:5%;background: rgb(238, 238, 238);">
        <div id="nocturnalVehicle_toolbar">
            <div class='toolbar' style="background: rgb(238, 238, 238);">
                <form action="#">
                    &nbsp;&nbsp;<label>夜间时段:
                    <input id="nightStartTime" class="easyui-textbox" type="text" class="easyui-textbox" data-options="required:true" style="width:80px"></label>
                    -
                    <input id="nightEndTime" class="easyui-textbox" type="text" class="easyui-textbox" data-options="required:true" style="width:80px">
                    &nbsp;&nbsp;&nbsp;&nbsp;<label>夜间出现次数(≥):
                    <input id="appearNum" class="easyui-textbox" type="text" data-options="required:true" style="width:80px"></label>
                    &nbsp;&nbsp;&nbsp;&nbsp;<label>开始时间:
                    <input id="startTime" type="text" name="startTime"></label>
                    &nbsp;&nbsp;&nbsp;&nbsp;<label>结束时间:
                    <input id="endTime" type="text" name="endTime"></label>

                    &nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0)" id="query" class="easyui-linkbutton"
                                               data-options="iconCls:'icon-search'">查询</a>
                </form>
            </div>
        </div>
    </div>
    <div data-options="region:'center',split:false,border:false" style="width:100%;height:95%;">
        <div class="easyui-layout" fit="true" style="width:100%;height:100%;">
            <div data-options="region:'west',title:'过车列表'" style="width:20%;height:100%;background:#eee;">
                <div id="nocturnalVehicle_datagrid"></div>
            </div>
            <div data-options="region:'center',title:'过车详细信息',border:false" style="width:80%;height:100%;background:#eee;">
                <div id="nocturnalVehicle_detail_datagrid">
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    (function($){
        var tab = util.getSelectedTab();
        var nocturnalVehicle_datagrid = tab.find("#nocturnalVehicle_datagrid");
        var toolbar = tab.find("#nocturnalVehicle_toolbar");
        var nocturnalVehicle_detail_datagrid = tab.find("#nocturnalVehicle_detail_datagrid");
        var $form = toolbar.find("form");
        var pictureServerHost = '${PictureServerHost}/';

        var startTime = $('#nocturnalVehicle_toolbar #startTime').datetimebox({
            required: true,
            editable: false
        });
        var endTime = $('#nocturnalVehicle_toolbar #endTime').datetimebox({
            required: true,
            editable: false
        });

        var getParamData = function () {
            var data = {
                nightStartTime: tab.find("#nightStartTime").val(),
                nightEndTime: tab.find("#nightEndTime").val(),
                appearNum:tab.find("#appearNum").val(),
                startTime: startTime.combo('getValue'),
                endTime: endTime.combo('getValue')
            };
            $.each(data, function (k, v) {
                data[k] = $.trim(v);
            });
            return data;
        };
        tab.on('click','#query',function(e){
            e.preventDefault();
            if($form.form('validate')) {

                //次数不允许超出选择时间之间的天数（优化）
                var etime = endTime.combo('getValue');
                var stime = startTime.combo('getValue');
                var date3 = new Date(Date.parse(etime.replace(/-/g, "/"))) - new Date(Date.parse(stime.replace(/-/g, "/")));
                var days=Math.floor(date3/(24*3600*1000)); //选择时间之间的天数
                var anum = tab.find("#appearNum").val(); //夜间出现次数
                if(anum< days){
                    $.messager.alert('提示', '夜间出现次数不能小于时间段之间的天数！');
                    return;
                }

                var loadData = function(callback){
                    var nocturnal_datagrid = nocturnalVehicle_datagrid.datagrid({
                        url: '/frequentNocturnalVehicle/queryNocturnalVehicle.action',
                        loadMsg: '数据载入中',
                        rownumbers: true,
                        fit: true,
                        singleSelect: true,
                        columns: [[
                            {field: 'license', title: '车牌', width: 100},
                            {field: 'nightAppearNum', title: '夜出次数', width: 55}
                        ]],
                        onDblClickRow: function (index, row) {
                            var data = getParamData();
                            data.plate = row.license;
                            var loadVehiclePassInfo = function(pageNumber, callback){
                                var passInfoGrid = nocturnalVehicle_detail_datagrid.datagrid({
                                    url: '/aerialMammalVehicle/queryAerialMammalVehiclePassInfo.action',
                                    loadMsg: '数据载入中',
                                    rownumbers: true,
                                    pageNumber: pageNumber,
                                    fit: true,
                                    singleSelect: true,
                                    pagination:true,
                                    columns: [[
                                        {field: 'license', title: '车牌', width: 100},
                                        {field: 'resultTime', title: '过车时间', formatter:util.formateTime, width: 150},
                                        {field: 'location', title: '监控点', width: 150},
                                        {field: 'direction', title: '方向', width: 50}
                                    ]],
                                    onDblClickRow: function (index, row) {
                                        detail_window.open(row, pictureServerHost, passInfoGrid, loadVehiclePassInfo);
                                    },
                                    onBeforeLoad: function (p) {
                                        $.extend(p, data);
                                    },
                                    rowStyler: function (index, row) {
                                        var hourOfResultTime = util.formateTimeReturnHour(row.resultTime);

                                        var nightStartTime = data.nightStartTime,nightEndTime = data.nightEndTime;
                                        if(nightStartTime.indexOf(":") < 0) {
                                            nightStartTime = nightStartTime + ":00";
                                        }
                                        if(nightEndTime.indexOf(":") < 0) {
                                            nightEndTime = nightEndTime + ":00";
                                        }
                                        var flag1 = util.compareDate(nightStartTime,nightEndTime),
                                                flag2 = util.compareDate(hourOfResultTime,nightStartTime),
                                                flag3 = util.compareDate(hourOfResultTime,nightEndTime);
                                        if(!flag1) {
                                            if(flag2 && !flag3){
                                                return 'background-color:#87ceeb;';
                                            } else {
                                                return 'background-color:yellow;';
                                            }
                                        } else {
                                            if(flag2 || !flag3){
                                                return 'background-color:#87ceeb;';
                                            } else {
                                                return 'background-color:yellow;';
                                            }
                                        }
                                    },
                                    onLoadSuccess: function (pager) {
                                        //为了翻页增加index 属性
                                        $.each(pager.rows, function (i, o) {
                                            o.index = i;
                                        });

                                        if (typeof callback == 'function') {
                                            callback();
                                            callback = null;
                                        }
                                    }
                                })
                            };
                            loadVehiclePassInfo();
                        },
                        onBeforeLoad: function (p) {
                            $.extend(p, getParamData());
                        },
                        onLoadSuccess: function () {
                            if (typeof callback == 'function') {
                                callback();
                                callback = null;
                            }
                        }
                    })
                };
                loadData();
            }
        })
    })(jQuery)
</script>
