<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="bk_examine_task_datagrid"></div>
<script type="text/javascript">
$(function(){
	var tab = util.getSelectedTab();
    var datagrid=$("#bk_examine_task_datagrid").datagrid({
        multiple: true,
        url: '/surveillance/list.action',
        pagination: true,
        pageSize: 20,
        rownumbers: true,
        singleSelect: true,
        checkOnSelect: false,
        selectOnCheck: false,
        fit: true,
        onClickRow: function (i, row) {
            selectRow = row;
            //loadResultDatas('','',selectRow.id)
        },
        columns: [[            
            {
                field: 'id', title: '任务ID',hidden: true, width: 120
            },
            {
                field: 'name', title: '任务名称', width: 180
            },
            {
                field: 'peopleName', title: '布控申请人', width: 80
            },
            {
                field: 'unitName', title: '布控单位', width: 150
            },
            {
                field: 'camera', title: '监控点名称', width: 120
            },
            {
                field: 'plate', title: '车牌号', width: 80
            },
            {
                field: 'plateType', title: '车牌类型', width: 70, formatter: function (value, row, index) {
                row = row || value;
                var plateValue = '';
                var plate_type_arr = {
                    0: '未知车牌',
                    1: '蓝牌',
                    2: '黑牌',
                    3: '单排黄牌',
                    4: '双排黄牌',
                    5: '警车车牌',
                    6: '武警车牌',
                    7: '个性化车牌',
                    8: '单排军车',
                    9: '双排军车',
                    10: '使馆牌',
                    11: '香港牌',
                    12: '拖拉机',
                    13: '澳门牌',
                    14: '厂内牌'
                }

                $.each(plate_type_arr, function (key, val) {
                    if (value == key) {
                        plateValue = val;
                    }
                })
                return plateValue;
            }
            },
            {
                field: 'carcolor', title: '车身颜色', width: 70
            },
            {
                field: 'vehicleKind', title: '车型', width: 70
            },
            {field: 'startTime', title: '申请布控时间', width: 150, formatter: util.formateTime},
            {
                field: 'status', title: '状态', width: 100	, formatter: function (v) {
                	if(v == 0){
                		return "布控待审核";
                	}
                	if(v == 1){
                		return "布控中（布控审核通过）";
                	}
                	if(v == 2){
                		return "布控结束（撤控审核通过）";
                	}
                	if(v == 3){
                		return "布控审核未通过";
                	}
                	if(v == 4){
                		return "撤控待审核";
                	}
                	if(v == 5){
                		return "撤控审核未通过";
                	}            
            	}
             },
             {
                field: 'xxx', title: '操作', width: 180, formatter: function (v, row) {
                var rowId = 'examine' + row.id
                var html = '<a href="javascript:void(0)" id="' + rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >审核(布控)</a>&nbsp;&nbsp;';
                tab.off('click', '#' + rowId).on('click', '#' + rowId, function () {
                    //弹出审核框                                     
                	examineSurveillanceTask(row.id,row.name,datagrid);
                });
                var html2 = '<a href="javascript:void(0)" id="' +'dt'+rowId + '" class="easyui-linkbutton" data-options="iconCls:\'icon-ok\'" >详情</a>';
                tab.off('click', '#dt' + rowId).on('click', '#dt' + rowId, function () {
                    //弹出详情框                                     
                	showDeatailSurveillanceTask(row,true,examineSurveillanceTask(row.id,row.name,datagrid));
                })
                return html+html2;
            }
            } 
        ]],
        onBeforeLoad:function(p){
	          $.extend(p,{status:0});
	    }
        /* toolbar: $('.bk_task_datagrid_toolbar', tab[0]).get(0) */
    });
    
    //自动刷新表格
    function refreshDataGrid(){
    	datagrid.datagrid("reload");
    	setTimeout(function(){refreshDataGrid()},5000);
    }   
    refreshDataGrid();
})
</script>