<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<jsp:include page="../header.jsp"></jsp:include>
<jsp:include page="detailDialog.jsp"></jsp:include>
<div style="margin:5px 0 5px 0;"></div>
<div region="center">
    <div id="search_results_tab" class="easyui-tabs" fit=true>
        <div title="综合查询">
            <div id="query_datagrid">
                <div class='query_toolbar' style="font-size:14px; margin:0px 0;padding-top:5px; padding-bottom:5px; color:#06C;">
                    <div class="easyui-accordion" data-options="multiple:true" style="width:100%;height1:300px;">
                        <div style="padding:10px;font-size:14px; margin:0px 0;padding-top:5px; padding-bottom:5px; color:#06C;">
                            <p>

                            <form action="#">
                                车&nbsp;&nbsp;&nbsp;牌
                                <input id="plate" type="text" style="height:25px;width:8%">&nbsp;
                                <input id="accurate_checkbox" type="checkbox" value="1" checked="true"/>&nbsp;(精确匹配)&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;品&nbsp;&nbsp;&nbsp;牌
                                <input id="vehicleBrand" type="text" class="easyui-textbox" style="height:28px; width:8%">
                                &nbsp;车&nbsp;&nbsp;系
                                <input id="vehicleSeries" type="text" class="easyui-textbox" style="height:28px; width:8%">
                                &nbsp;款&nbsp;&nbsp;型
                                <input id="vehicleStyle" type="text" class="easyui-textbox" style="height:28px; width:8%">
                                &nbsp;车身颜色
                                <input id="carColor" type="text" class="easyui-textbox" style="height:28px;width:8%;"><br/>

                                <div style="height:5px;"></div>
                                监控点
                                <input id="query_camera" type="text" style="width:20%;height:28px;">

                                <div href="javascript:void(0)" id="pub_map" class="easyui-linkbutton" data-options="iconCls:'icon-search'">地图</div>

                                <span style="color:red">*</span>过车时间段(<span style="color:red">必填项</span>)
                                <input id="query_startTime" type="text" name="startTime" style="width:11%;height:28px;">
                                ---
                                <input id="query_endTime" type="text" name="startTime" style="width:11%;height:28px;">
                                &nbsp;&nbsp;
                                <a href="javascript:void(0)" id="query_submit"
                                   class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
                                <a href="javascript:void(0)" id="empty_submit"
                                   class="easyui-linkbutton" data-options="iconCls:'icon-clear'">重置</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                                <a href="javascript:void(0)" id="show_trail_btn"
                                   class="easyui-linkbutton" data-options="iconCls:'icon-search'">显示轨迹</a>
                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <a href="javascript:void(0)" id="download_select_submit"
                                   class="easyui-linkbutton" data-options="iconCls:'icon-down'">下载</a>
                                <a href="javascript:void(0)" id="download_submit"
                                   class="easyui-linkbutton" data-options="iconCls:'icon-down'">下载全部</a>
                            </form>
                            </p>
                        </div>
                        <div title="更多选项&ensp;▼" style="padding:10px;font-size:14px; margin:0px 0;padding-top:5px; padding-bottom:5px; color:#06C; ">
                            <p>

                            <form>
                                地&nbsp;&nbsp;&nbsp;点
                                <input id="location" type="text" class="easyui-textbox" style="height:28px; width:8%;">
                                车道方向
                                <input id="direction" type="text" style="width:8%;height:28px;">
                                车&nbsp;&nbsp;&nbsp;型
                                <input id="vehicleKind" type="text" style="width:8%;height:28px;">

                                特征物
                                <input id="characteristic" type="text" style="width:8%;height:28px;">
                                车牌颜色
                                <input id="plateColor" type="text" class="easyui-textbox" style="height:28px;width:8%;">

                                <div style="display:none;"><input id="trailcombo" type="text"></div>
                                <%--<a href="javascript:void(0)" id="show_trail_combo"
                                   class="easyui-linkbutton" data-options="iconCls:'icon-search'">显示轨迹</a>--%>
                            </form>

                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../footer.jsp" %>
<script>
    (function () {
        //存放被选择的serialNumber
        var checkboxItemArray = new Array();
        //存放被选择的监控点
        var camIdArr = [], camNameArr = [];

        var tab = $("#search_results_tab").tabs(), $form = tab.find("form");
        var pictureServerHost = '${PictureServerHost}/';

        var $query_camera = tab.find('#query_camera'),
                $query_datagrid = tab.find('#query_datagrid');

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

        var startTime = tab.find('#query_startTime').datetimebox({
            editable: false,
            required: true
        });
        var endTime = tab.find('#query_endTime').datetimebox({
            editable: false,
            required: true
        });

        setDeftValOfOneWeek();

        function setDeftValOfOneWeek() {
            //默认查询一个星期内的结果
            var nowDate = new Date,
                    nowTime = nowDate.getTime(),
                    sevDaysBefTime = nowDate.setDate(nowDate.getDate() - 7),
                    formatNowTime = util.formateTime(nowTime),
                    formatSevDaysBefTime = util.formateTime(sevDaysBefTime);

            startTime.datetimebox("setValue", formatSevDaysBefTime);
            endTime.datetimebox("setValue", formatNowTime);
        }

        var plate = tab.find("#plate");
        var location = tab.find("#location");
        //var vehicleBrand = tab.find("#vehicleBrand");
        var vehicleStyle = tab.find("#vehicleStyle");
        var plateColor = tab.find("#plateColor").combobox({
            url: "/js/json/plateColor.json",
            valueField: "value",
            textField: "text"
        });
        var direction = tab.find("#direction").combobox({
            url: "/js/json/direction.json",
            valueField: "value",
            textField: "text"
        });
        var carColor = tab.find("#carColor").combogrid({
            url: "/js/json/carColor.json",
            multiple: true,
            idField: 'value',
            textField: 'text',
            editable: false,
            columns: [[
                {field: 'value', title: 'value', hidden: true},
                {field: 'checkbox', checkbox: true},
                {field: 'text', title: '颜色', width: 95}
            ]]
        });
        var vehicleKind = tab.find("#vehicleKind").combobox({
            url: "/js/json/vehicleKind.json",
            valueField: "value",
            textField: "text"
        });
        var characteristic = tab.find("#characteristic").combogrid({
            url: "/js/json/characteristic.json",
            multiple: true,
            idField: 'value',
            textField: 'text',
            editable: false,
            columns: [[
                {field: 'value', title: 'value', hidden: true},
                {field: 'checkbox', checkbox: true},
                {field: 'text', title: '特征物名称', width: 150}
            ]]
        });
        // 一层Combo
        var vehicleBrand = tab.find("#vehicleBrand").combobox({
            //editable:false, //不可编辑状态  品牌太多 支持匹配
            url: '/brandmodel/listbrand.action',
            dataType: 'json',
            type: 'post',
            onChange: function (record) {  //onSelect 用户点击时触发的事件  在此的意义在于，用户点击一级后自动二级combobox
                $.ajax({
                    type: 'post',
                    url: '/brandmodel/listsubbrand.action',
                    data: {brandName: tab.find("#vehicleBrand").combobox("getValue")},
                    success: function (d) {
                        var data = [];
                        $.each(d, function (i, o) {
                            var obj = {value: o.carSeries, text: o.carSeries};
                            data.push(obj);
                        })
                        vehicleSeries.combobox({
                            onLoadSuccess: function () {  //清空三级下拉框 就是成功加载完触发的事件 当一级combobox改变时，二级和三级就需要清空
                                vehicleStyle.combobox("clear");
                                vehicleSeries.combobox("clear");
                            },
                            data: data,
                            onChange: function (record) {

                                $.ajax({
                                    type: 'post',
                                    url: '/brandmodel/listcar.action',
                                    data: {
                                        brandName: tab.find("#vehicleBrand").combobox("getValue"),
                                        carSeries: tab.find("#vehicleSeries").combobox("getValue")
                                    },
                                    success: function (d) {
                                        var data = [];
                                        $.each(d, function (i, o) {
                                            var obj = {value: o.modelsName, text: o.modelsName};
                                            data.push(obj);
                                        })
                                        vehicleStyle.combobox({
                                            data: data,
                                            onChange: function (record) {

                                            }
                                        });
                                    }
                                });

                            }
                        });
                    }
                });

            },
            valueField: 'brandName',
            textField: 'brandName',
            value: ''
        });
        /******************************************************************************************************/
        //下面的俩个是组件，

        //  二层Combo
        var vehicleSeries = tab.find("#vehicleSeries").combobox({
            editable: false, //不可编辑状态
            value: ''
        });

        //三层Combo
        var vehicleStyle = tab.find("#vehicleStyle").combobox({
            editable: false, //不可编辑状态
            value: ''
        });
        var getParamData = function () {
            var data = {
                cameraIds: cameraIds.combo('getValues').join(),
                startTime: startTime.combo('getValue'),
                endTime: endTime.combo('getValue'),
                plate: plate.val(),
                location: location.val(),
                plateColor: plateColor.combo('getValue'),
                carColor: carColor.combo('getValues').join(),
                direction: direction.combo('getValue'),
                vehicleKind: vehicleKind.combo('getValue'),
                vehicleBrand: vehicleBrand.combo('getValue'),
                vehicleSeries: vehicleSeries.combo('getValue'),
                vehicleStyle: vehicleStyle.combo('getValue'),
                characteristic: characteristic.combo('getValues').join()
            };

            if ($("#accurate_checkbox").is(":checked")) {
                data.accurate = true;
            } else {
                data.accurate = false;
            }

            $.each(data, function (k, v) {
                data[k] = $.trim(v);
            });

            return data;
        };

        tab.find('#query_submit').click(function (e) {
            e.preventDefault();
            checkboxItemArray.length = 0;

            $("#query_datagrid").datagrid({url: '/result/query.action'});

            //$query_datagrid.datagrid("reload");
        });

        function isMoreThanOneMonth(paramObj) {
            var startTimeStr = paramObj.startTime.replace(/-/g, '/'),
                    endTimeStr = paramObj.endTime.replace(/-/g, '/');

            //开始时间
            var startTimeMils = new Date(startTimeStr),
            //允许查询的范围
                    allowRange = startTimeMils.setMonth(startTimeMils.getMonth() + 1),
                    endTimeMils = new Date(endTimeStr);

            //大于一个月不允许查询
            if (endTimeMils > allowRange) {
                $.messager.alert("提示", "只允许查询一个月内的结果,请选择正确的时间段！");
                return false;
            }
            return true;
        }

        $('#empty_submit').click(function (e) {
            //清空表单数据
            $form.form('reset');
            $query_camera.combotree("clear");
            camIdArr = [];
            camNameArr = [];
            setDeftValOfOneWeek();
        });

        tab.find('#download_submit').click(function (e) {
            e.preventDefault();
            var data = getParamData();
            //判断条目数
            $.ajax({
                url: '/result/downloadItemCount.action',
                data: data,
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
                        form.attr('action', '/result/download.action');

                        for (var pname in data) {
                            var input = $('<input>');
                            if (data.hasOwnProperty(pname)) {
                                input.attr('type', 'hidden');
                                input.attr('name', pname);
                                input.attr('value', data[pname]);
                                form.append(input);
                            }
                        }

                        $('body').append(form);
                        form.submit();
                        form.remove();
                    }
                }
            });

        });

        tab.find('#download_select_submit').click(function (e) {
            e.preventDefault();

            if (checkboxItemArray.length < 1) {
                $.messager.show({
                    title: "提示消息",
                    msg: "请选择下载数据。",
                    timeout: 3000,
                    showType: 'slide'
                });
                return;
            }

            if (checkboxItemArray.length > 2000) {
                $.messager.show({
                    title: "提示消息",
                    msg: "数据条目数大于2000，无法下载。",
                    timeout: 3000,
                    showType: 'slide'
                });
                return;
            }

            var form = $("<form>");
            form.attr('style', 'display:none');
            form.attr('target', '');
            form.attr('method', 'post');
            form.attr('action', '/result/downloadChoose.action');

            var input = $('<input>');
            input.attr('type', 'hidden');
            input.attr('name', 'ids');
            input.attr('value', checkboxItemArray.join());
            form.append(input);

            $('body').append(form);
            form.submit();
            form.remove();

        });

        var loadResultDatas = function (pageNumber, callback, pageSize) {
            if (!pageSize) {
                pageSize = 20;
            }
            var datagrid = $query_datagrid.datagrid({
                loadMsg: '数据载入中',
                pagination: true,
                pageNumber: pageNumber,
                rownumbers: true,
                fit: true,
                singleSelect: true,
                checkOnSelect: false,
                selectOnCheck: false,
                pageSize: pageSize,//每页显示的记录条数，默认为10
                //*过车时间（绝对时间）、*方向、*地点、*经度、*纬度、
                columns: [[
                    {field: 'checkbox', checkbox: true},
                    {field: 'serialNumber', title: 'id', hidden: true},
                    {
                        field: 'operation', title: '操作', formatter: function (v, row) {
                        var html = '';
                        if (row.taskType == 2 && row.resourceType == 2) {
                            html = '<a href="javascript:void(0)" onclick=video_player.play(\"' + row.path.replace(/\\/gm, '\\\\') + '\",' + row.frame_index + ') >播放片段</a>';
                        }
                        return html;
                    }
                    },
                    {field: 'license', title: '车牌', width: 100},
                    {field: 'plateType', title: '车牌类型', width: 100},
                    {field: 'carColor', title: '车身颜色', width: 80},
                    {field: 'vehicleKind', title: '车型', width: 70},
                    {field: 'vehicleBrand', title: '品牌', width: 70},
                    {field: 'vehicleSeries', title: '车系', width: 70},
                    {field: 'vehicleStyle', title: '款型', width: 70},
                    {field: 'licenseAttribution', title: '车牌归属地', width: 80},
                    {field: 'plateColor', title: '车牌颜色', width: 70},
                    /*{field: 'characteristic', title: '特征物', formatter:util.formateCharacteristic,width:120},*/
                    {
                        field: 'tag', title: '年检标', width: 50,
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return '有';
                            } else if (value == 0) {
                                return '无';
                            }
                        }
                    },
                    {
                        field: 'paper', title: '纸巾盒', width: 50,
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return '有';
                            } else if (value == 0) {
                                return '无';
                            }
                        }
                    },
                    {
                        field: 'sun', title: '遮阳板', width: 50,
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return '放下';
                            } else if (value == 0) {
                                return '未放下';
                            }
                        }
                    },
                    {
                        field: 'drop', title: '挂饰', width: 50,
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return '有';
                            } else if (value == 0) {
                                return '无';
                            }
                        }
                    },
                    {field: 'confidence', title: '可信度', width: 50},
                    {field: 'direction', title: '方向', width: 50},
                    {field: 'location', title: '监控点', width: 150},
                    {field: 'longitude', title: '经度', width: 150, hidden: true},
                    {field: 'latitude', title: '纬度', width: 150, hidden: true},
                    {field: 'resultTime', title: '过车时间', formatter: util.formateTime, width: 150}/*,
                     //暂时不需要
                     {field: 'showResult', title: '查看结果', formatter: function(v,row){
                     var rowId=row.serialNumber
                     var html='<button id="'+rowId+'" class="showResult">查看结果</button>';
                     tab.off('click','#'+rowId).on('click','#'+rowId,function(){
                     showResultWindow(row)
                     })
                     return html;
                     }
                     }*/
                ]],
                toolbar: tab.find('.query_toolbar', $query_datagrid),
                onDblClickRow: function (index, row) {
                    detail_window.open(row, pictureServerHost, datagrid, loadResultDatas);
                },
                onBeforeLoad: function (p) {
                    var paramObj = getParamData();
                    //强制使用时间查询
                    if (!!!paramObj.startTime || !!!paramObj.endTime) {
                        $.messager.alert("提示", "时间段不能为空！");
                        return false;
                    }
                    if (!isMoreThanOneMonth(paramObj)) {
                        return false;
                    }
                    $.extend(p, paramObj);
                },
                onLoadSuccess: function (pager) {
                    licenseBox(pager.rows);
                    $.each(pager.rows, function (i, o) {
                        o.index = i;
                    });
                    if (typeof callback == 'function') {
                        callback();
                        //这个callback用于在查看结果详情的时候,翻下一张,翻页时弹出一个window显示结果详情
                        //若不设为null,在上面的翻页后datagrid loadSuccess(包括查询,刷新等)后依然会弹出一个window
                        callback = null;
                    }
                    //勾选多选框
                    //获取当前页的所有行
                    var myrows = datagrid.datagrid('getRows');
                    for (var i = 0; i < myrows.length; i++) {
                        if (checkboxItemArray.jiuLingIndexOf(myrows[i].serialNumber) > -1) {
                            var myindex = datagrid.datagrid('getRowIndex', myrows[i]);
                            datagrid.datagrid('checkRow', myindex);
                        }
                    }

                },
                onCheck: function (rowIndex, rowData) {
                    checkboxItemArray.jiuLingAdd(rowData.serialNumber);
                },
                onUncheck: function (rowIndex, rowData) {
                    checkboxItemArray.jiuLingRemove(rowData.serialNumber);
                },
                onCheckAll: function (rows) {
                    for (var i = 0; i < rows.length; i++) {
                        checkboxItemArray.jiuLingAdd(rows[i].serialNumber);
                    }
                },
                onUncheckAll: function (rows) {
                    for (var i = 0; i < rows.length; i++) {
                        checkboxItemArray.jiuLingRemove(rows[i].serialNumber);
                    }
                }
            })
        }
        loadResultDatas();

//车牌下拉框
        var licenseBox = function (rows) {
            var datas = rows,
                    oo = {},
                    licenseArray = [];

            $.each(datas, function (i, e) {
                if (e.license in oo) {
                    oo[e.license].push(e)
                } else {
                    oo[e.license] = [e]
                }
            })
            //若当前页相同车牌数量>1，则把它添加到数组中
            var i = 0;
            $.each(oo, function (license, num) {
                if (num.length > 1) {
                    licenseArray[i] = {value: oo[license], text: license}
                    ++i;
                }
            })
            trailcombo(licenseArray)
        }

        tab.find('.query_toolbar').on('click', '#show_trail_btn', function (e) {
            e.preventDefault();
            var checkedRows = $query_datagrid.datagrid('getChecked');
            if (checkedRows.length == 0) {
                $.messager.show({
                    title: '提示消息',
                    msg: '没有可以显示的轨迹',
                    timeout: 3000,
                    showType: 'slide'
                })
                return;
            }
            //显示轨迹
            pubMapWindow.openTrailMap(checkedRows, 'red', 3);
        })

//        tab.find('.query_toolbar').on('click', '#show_trail_combo', function (e) {
//            e.preventDefault();
//            var datas = tab.find('#trailcombo').combobox('getData');
//
//            if (datas.length == 0) {
//                $.messager.show({
//                    title: '提示消息',
//                    msg: '没有可以显示的轨迹',
//                    timeout: 3000,
//                    showType: 'slide'
//                })
//            }
//            if (datas.length != 0) {
//                var text = tab.find('#trailcombo').combobox('getText');
//                var values = datas.filter(function (d) {
//                    return d.text == text;
//                });
//
//                var checkedRows = $query_datagrid.datagrid('getChecked');
//                if (values.length == 0 && checkedRows.length == 0) {
//                    $.messager.show({
//                        title: '提示消息',
//                        msg: '请选择需要查看轨迹的车牌号',
//                        timeout: 3000,
//                        showType: 'slide'
//                    })
//                } else {
//                    var $map = $("#main_map");
//                    var map = $map.data('map');
//                    var checkedRows = $query_datagrid.datagrid('getChecked');
//                    if(values.length == 0){
//                        values = [{}];
//                        values[0].value = [];
//                    }
//                    for(var i=0;i<checkedRows.length;i++){
//                        values[0].value.push(checkedRows[i]);
//                    }
//                    //显示轨迹
//                    pubMapWindow.openTrailMap(checkedRows, 'red', 3);
//                }
//            }
//        })
        tab.on('click', '#pub_map', function (e) {
            e.preventDefault();
            pubMapWindow.open(cameraIds, camIdArr, camNameArr);
        });
//初始化显示轨迹下拉列表,显示相同车牌的数量大于1的车牌

        var trailcombo = function (data) {
            var trailcombo1 = tab.find('#trailcombo').combobox({
                editable: false,
                data: data,
                onShowPanel: function () {
                    trailcombo1.combo('setValue', undefined)
                }
            })
        };

        $query_datagrid.datagrid('getPanel').on("keyup", 'input:text', function (e) {
            if (e.keyCode == 13) {
                $query_datagrid.datagrid('reload');
            }
        });
        // IE下只读INPUT键入BACKSPACE 后退问题
        $("input[readonly]").keydown(function (e) {
            e.preventDefault();
        });

        $('#plate').validatebox({
            novalidate: false,
            validType: {letterAndNumberAndChinese: true}
        });
        //多选框事件
        $("#accurate_checkbox").change(function () {
            if ($("#accurate_checkbox").prop("checked")) {
                $("#plate").validatebox({novalidate: false});
                $.parser.parse("#plate");
            } else {
                $("#plate").validatebox({novalidate: true});
                $.parser.parse("#plate");
            }
        });

        window.document.title = tab.tabs("getSelected").panel("options").title;
    })();
</script>
