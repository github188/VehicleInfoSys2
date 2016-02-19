<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div id="add_surveillance_dialog">
    <form action="" id="surveillance_form">
        <fieldset style="width: 99%; display: inline">
            <legend>布控基本信息</legend>
            <br/>
                &ensp;&ensp;监&ensp;控&ensp;点&nbsp:&ensp;<input id="surveillance_cameraIds" type="text" style="width:290px;">
                <div style="float:right;margin:23px 28px 0px 0px;"><button href="javascript:void(0)" id="map_button" style="margin-top: -30px"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">地图</span><span class="l-btn-icon icon-search">&nbsp;</span></span></button></div><br/><br/>
                &ensp;&ensp;车&ensp;牌&ensp;号&nbsp:&ensp;<input id="plate_number" type="text" style="width:355px;"><br/><br/>
                &ensp;&ensp;特&ensp;征&ensp;物&nbsp:&ensp;<input id="characteristics" type="text" style="width:129px;">
                &ensp;&ensp;&ensp;&nbsp车身颜色&nbsp:&ensp;<input id="body_color" type="text" style="width:129px;"><br/><br/>
                &ensp;&ensp;车&ensp;&ensp;&ensp;&ensp;型&nbsp:&ensp;<input id="motorcycle_type" type="text" style="width:129px;">
                &ensp;&ensp;&ensp;&nbsp车牌类型&nbsp:&ensp;<input id="plate_type" type="text" style="width:129px;"><br/><br/>
                <%--&ensp;&ensp;品&ensp;&ensp;&ensp;&ensp;牌&nbsp:&ensp;<input id="brand" type="text" style="width:125px;">
                &ensp;&ensp;&ensp;&ensp;款&ensp;&ensp;&ensp;&ensp;型&nbsp:&ensp;<input id="model" type="text" style="width:125px;"><br/><br/>--%>
                &ensp;&ensp;品牌&nbsp:&nbsp<input id="brand" style="width:100px;">
                &nbsp车系&nbsp:&nbsp<input id="series" style="width:100px;">
                &nbsp车款&nbsp:&nbsp<input  id="model" style="width:100px;"><br/><br/>
        </fieldset>
	</form>
</div>
<script type="text/javascript">
    (function ($,w) {
        var dialog = $('#add_surveillance_dialog'),
                dialog_html = dialog.html();
        dialog.empty();

        window.addSurveillance = function ($bk_task_datagrid) {
            dialog.html(dialog_html);
            dialog.dialog({
                title: '添加布控任务信息',
                resizable:true,
                width: 500,
                height: 330,
                closed: false,
                cache: false,
                modal: true,
                buttons: [{
                    text: '立即布控',
                    iconCls: 'icon-ok',
                    handler: function () {
                        var rows = characteristics.combogrid('grid').datagrid('getRows'),
                                selections = characteristics.combogrid('grid').datagrid('getSelections');
                        var tag, paper, sun, drop;
                        $.each(rows, function (index, row) {
                            $.each(selections, function (i, o) {
                                if(row == o) {
                                    if(o.value == 'tag') {
                                        tag = 1;
                                    }else if(o.value == 'paper') {
                                        paper = 1;
                                    }else if(o.value == 'sun') {
                                        sun = 1;
                                    }else{
                                        drop = 1;
                                    }
                                }
                            })
                        });
                        var $form =$('#surveillance_form'),
                                data = {
                                    plate:$.trim($form.find('#plate_number').val()),
                                    plateType:$.trim($form.find('#plate_type').combo('getValue')),
                                    carcolor:$.trim($form.find('#body_color').combo('getValues').join()),
                                    cameraIds:$.trim($form.find('#surveillance_cameraIds').combo('getValues').join()),
                                    vehicleKind:$.trim($form.find('#motorcycle_type').combo('getValue')),
                                    /*vehicleBrand:$.trim($form.find('#brand').val()),
                                    vehicleStyle:$.trim($form.find('#model').val()),*/
                                    vehicleBrand: $.trim(brand.combo('getValue')),
                                    vehicleSeries: $.trim(series.combo('getValue')),
                                    vehicleStyle: $.trim(model.combo('getValue')),
                                    tag:tag,
                                    paper:paper,
                                    sun:sun,
                                    drop:drop
                                }
                        if ($form.form("validate")) {
                            $.ajax({
                                url: '/surveillance/add.action',
                                type: 'post',
                                data: data,
                                success: function (d) {
                                    var msg = '';
                                    if (d == "surveillancing") {
                                        msg = '当前任务正在布控中';
                                    } else {
                                        $bk_task_datagrid.datagrid('reload');
                                        msg = '添加布控成功'
                                    }
                                    $.messager.show({
                                        title: '提示信息',
                                        msg: msg
                                    });
                                }
                            });
                        }
                    }
                },
                    {
                        text: '清空布控条件',
                        handler: function () {
                            var $form = dialog.find('form');
                            //清空表单数据
                            $form.form('reset');
                        }
                    },
                    {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            dialog.dialog('close');
                        }
                    }
                ],
                onBeforeClose: function () {
                    $(this).dialog('clear');
                }
            });
            //存放被选择的监控点
            var camIdArr = [],camNameArr = [],$query_camera=$('#surveillance_cameraIds');
            //树形下拉框 监控点
            var cameraIds = $query_camera.combotree({
                url: '/camera/camLstTree.action',
                animate:true,
                multiple:true,
                onCheck:function(node,checked) {
                    util.checkCam(cameraIds,node,camIdArr,camNameArr,checked);
                },
                onClick:function(node) {
                    util.checkCam(cameraIds,node,camIdArr,camNameArr,node.checked);
                },
                onChange:function(nVal,oVal) {
                    util.checkCamWithMap(cameraIds);
                }
            });
            var plate_type = $("#plate_type").combobox({
                data: [{
                    value: '0',
                    text: '未知车牌'
                }, {
                    value: '1',
                    text: '蓝牌'
                }, {
                    value: '2',
                    text: '黑牌'
                }, {
                    value: '3',
                    text: '单排黄牌'
                }, {
                    value: '4',
                    text: '双排黄牌'
                }, {
                    value: '5',
                    text: '警车车牌'
                }, {
                    value: '6',
                    text: '武警车牌'
                }, {
                    value: '7',
                    text: '个性化车牌'
                }, {
                    value: '8',
                    text: '单排军车'
                }, {
                    value: '9',
                    text: '双排军车'
                }, {
                    value: '10',
                    text: '使馆牌'
                }, {
                    value: '11',
                    text: '香港牌'
                }, {
                    value: '12',
                    text: '拖拉机'
                }, {
                    value: '13',
                    text: '澳门牌'
                }, {
                    value: '14',
                    text: '厂内牌'
                }]
            });

            var body_color = $("#body_color").combogrid({
                multiple: true,
                idField: 'value',
                textField: 'text',
                editable:false,
                columns: [[
                    {field: 'value', title: 'value', hidden: true},
                    {field: 'checkbox', checkbox: true},
                    {field: 'text', title: '颜色', width: 95}
                ]],
                data: [{
                    value: '黑',
                    text: '黑'
                }, {
                    value: '蓝',
                    text: '蓝'
                }, {
                    value: '青',
                    text: '青'
                }, {
                    value: '灰(银)',
                    text: '灰（银）'
                }, {
                    value: '绿',
                    text: '绿'
                },{
                    value: '红',
                    text: '红'
                },{
                    value: '白',
                    text: '白'
                },{
                    value: '黄',
                    text: '黄'
                },{
                    value: '棕',
                    text: '棕'
                }, {
                    value: '粉',
                    text: '粉'
                }]
            });
            var motorcycle_type = $("#motorcycle_type").combobox({
                url:"js/json/vehicleKind.json",
                valueField:"value",
                textField:"text"
            });
            var characteristics = $("#characteristics").combogrid({
                multiple: true,
                idField: 'value',
                textField: 'text',
                editable:false,
                columns: [[
                    {field: 'value', title: 'value', hidden: true},
                    {field: 'checkbox', checkbox: true},
                    {field: 'text', title: '特征物名称', width: 95}
                ]],
                data: [{
                    value: 'tag',
                    text: '年检标'
                }, {
                    value: 'paper',
                    text: '纸巾盒'
                }, {
                    value: 'sun',
                    text: '遮阳板'
                }, {
                    value: 'drop',
                    text: '挂饰'
                }]
            });
            // 一层Combo
            var brand = $("#brand").combobox({
                //editable:false, //不可编辑状态  品牌太多 支持匹配
                        url: '/brandmodel/listbrand.action',
                        dataType: 'json',
                        type:'post',
                onChange:function(record){  //onSelect 用户点击时触发的事件  在此的意义在于，用户点击一级后自动二级combobox
                    $.ajax({
                        type:'post',
                        url: '/brandmodel/listsubbrand.action',
                        data:{brandName : $("#brand").combobox("getValue")},
                        success:function(d){
                            var data=[];
                            $.each(d,function(i,o) {
                                var obj={value: o.carSeries,text: o.carSeries};
                                data.push(obj);
                            })
                            series.combobox({
                                onLoadSuccess:function(){  //清空三级下拉框 就是成功加载完触发的事件 当一级combobox改变时，二级和三级就需要清空
                                    model.combobox("clear");
                                    series.combobox("clear");
                                },
                                data:data,
                                onChange:function(record) {

                                    $.ajax({
                                        type:'post',
                                        url: '/brandmodel/listcar.action',
                                        data:{brandName : $("#brand").combobox("getValue"),carSeries : $("#series").combobox("getValue")},
                                        success:function(d){
                                            var data=[];
                                            $.each(d,function(i,o) {
                                                var obj={value: o.modelsName,text: o.modelsName};
                                                data.push(obj);
                                            })
                                            model.combobox({
                                                data:data,
                                                onChange:function(record) {

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
                value:''
            });
            /******************************************************************************************************/
            //下面的俩个是组件，

            //  二层Combo
            var series = $("#series").combobox({
                editable:false, //不可编辑状态
                value:''
            });

            //三层Combo
            var model=$("#model").combobox({
                editable:false, //不可编辑状态
                value:''
            });
            dialog.on('click', '#map_button', function (e) {
                e.preventDefault();
                pubMapWindow.open(cameraIds,camIdArr,camNameArr);
            });
        }
    })(jQuery,window)
</script>
