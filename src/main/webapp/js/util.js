(function ($, window) {

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
    /**
     * 重置和完善部分国际化
     */
    if ($.fn.pagination) {
        $.fn.pagination.defaults.beforePageText = '第';
        $.fn.pagination.defaults.afterPageText = '页  共{pages}页';
        $.fn.pagination.defaults.displayMsg = '共{total}条记录';
    }
    if ($.fn.datetimebox) {
        var buttons = $.fn.datetimebox.defaults.buttons;
        buttons.pop();//把关闭按钮给去掉
        buttons.push({//加入清空按钮
            text: '清空',
            handler: function (box) {
                $(box).datetimebox('setValue', '').datetimebox("hidePanel");
            }
        })
    }
    if ($.fn.panel) {
        $.fn.panel.defaults.loadingMessage = '加载中';
    }
    //重写combo的editable默认值为false
    if ($.fn.combo||$.fn.combobox||$.fn.combotree) {
        $.extend($.fn.combo.defaults, {
            editable:false
        });
        if($.fn.combobox.defaults.editable) {
            $.fn.combobox.defaults.editable = false;
        }
    }
    //不能让窗口移出边界
    var panelMoveCallback = function (left, top) {
        var $self = $(this),
            opts = $self.panel("options"),
            $panel = $self.closest('.panel'),
            win_width = isNaN(opts.width) ? $panel.outerWidth() : opts.width,
            win_height = isNaN(opts.height) ? $panel.outerHeight() : opts.height,
            _left = Math.min(left, document.body.clientWidth - win_width),
            _top = Math.min(top, document.body.clientHeight - win_height);
        _left = Math.max(0, _left);
        _top = Math.max(0, _top);

        if (_left != left || _top != top) {
            $self.panel("move", {
                left: _left, top: _top
            })
        }
    }

    var fixWindow = function(width,height){
        var $self = $(this),
            opts = $self.panel("options"),
            $panel = $self.closest('.panel'),
            win_width = isNaN(opts.width) ? $panel.outerWidth() : opts.width,
            win_height = isNaN(opts.height) ? $panel.outerHeight() : opts.height;

            if(win_width > document.body.clientWidth ||  win_height > document.body.clientHeight){
                win_width = win_width > document.body.clientWidth ? document.body.clientWidth : win_width
                win_height = win_height > document.body.clientHeight ? document.body.clientHeight : win_height

                $self.panel('resize',{
                    width:win_width,
                    height:win_height
                })
            }
    }
    if ($.fn.dialog) {
        $.extend($.fn.dialog.defaults, {
            modal: true,//模态
            onMove: panelMoveCallback,
            onOpen:fixWindow
        })
    }



    //设置默认的window属性
    if ($.fn.window) {
        $.extend($.fn.window.defaults, {
            minimizable: false,//关闭最小化
            modal: true,//模态
            onMove: panelMoveCallback
        })
    }
    // 扩展equals检验方法
    $.extend($.fn.validatebox.defaults.rules, {
        username_remote: {
            validator: function (value, param) {
                var postdata = {};
                postdata[param[1]] = value;
                var m_result = $.ajax({
                    type: "POST",// http请求方式
                    url: param[0], // 服务器段url地址
                    data: postdata, // 发送给服务器段的数据
                    dataType: "type", // 告诉JQuery返回的数据格式
                    async: false
                }).responseText;
                return m_result == "true";
            },
            message: '用户名已存在,请重新输入!'
        },
        checkPwd_remote: {
            validator: function (value, param) {
                var postdata = {};
                postdata[param[1]] = value;
                postdata[$(param[2]).attr('name')] = $(param[2])
                    .val();
                var m_result = $.ajax({
                    type: "POST",// http请求方式
                    url: param[0], // 服务器段url地址
                    data: postdata, // 发送给服务器段的数据
                    dataType: "type", // 告诉JQuery返回的数据格式
                    async: false
                }).responseText;
                return m_result == "true";
            },
            message: '密码错误,请重新输入!'
        },
        range: {
            validator: function (value, param) {
                var reg = new RegExp("^-?\\d+(\\.\\d{1,"
                + param[2] + "})?$");
                var r = reg.test(value);
                if (!r) {
                    return false;
                }
                return value >= param[0] && value <= param[1];
            },
            message: '请输入{0}~{1}内的数字，小数点后最多保留{2}位'
        },
        number: {
            validator: function (value, param) {
                var reg = /^\d+$/;
                var r = reg.test(value);
                if (!r) {
                    return false;
                }
                return value >= param[0] && value <= param[1];
            },
            message: '必须是{0}~{1}内的数字'
        },
        num: {
            validator: function (value) {
                return (/^\d+$/).test(value);
            },
            message: '请输入数字'
        },
        checkIp: {// 验证IP地址
            validator: function (value) {
                var reg = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/;
                return reg.test(value);
            },
            message: 'IP地址格式不正确'
        },
        _email: {
            validator: function (value) {
                return /^([a-zA-Z0-9_-])+\.?[a-zA-Z0-9_-]+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
                    .test(value);
            },
            message: '请输入有效的邮箱地址'
        },
        letterAndNumber: {
            validator: function (value) {
                var reg = /^[a-zA-Z0-9]+$/;
                return reg.test(value);
            },
            message: '请输入英文字母或数字，不包含中文汉字和特殊字符!'
        },
        letterAndChinese: {
            validator: function (value) {
                var reg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
                return reg.test(value);
            },
            message: '请输入英文字母或汉字，不包含数字和特殊字符!'
        },
        letterAndNumberAndChinese: {
            validator: function (value) {
                var reg = /^[a-zA-Z0-9\u4e00-\u9fa5()_]+$/;
                return reg.test(value);
            },
            message: '请输入英文字母、数字或汉字，不包含特殊字符!'
        },
        equals: {
            validator: function (value, param) {
                return value == $(param).val();
            },
            message: '密码不一致.'
        },
        // 图片和视频格式验证
        img_upload: {
            validator: function (value) {
                var fileList = null;
                // 检查浏览器是否支持file api
                if (window.File || window.FileReader
                    || window.FileList || window.Blob) {
                    fileList = $(this).prop('files');
                } else {
                    fileList = [{
                        name: value
                    }];
                }
                var options = $(this).validatebox('options');
                var fileType = $('input:radio:checked').val();
                for (var i = 0; i < fileList.length; i++) {
                    value = fileList[i].name;
                    var ext = value.substring(value.lastIndexOf(".") + 1);
                    if (fileType == 1) {
                        if (!/(jpg|png|bmp)/i.test(ext)) {
                            options.invalidMessage = '只允许上传jpg、png、bmp格式的图片.';
                            return false;
                        }
                    } else if (fileType == 2) {
                        if (!/(3g2|3gp|asf|avi|dvd|flv|lvf|m4v|cdxl|swf|mp4|mtv|mv|vcd|vplayer|wv|wav)/i
                                .test(ext)) {
                            options.invalidMessage = '所选文件' + value + '的格式错误.';
                            return false;
                        }
                    }
                }
                return true;
            },
            message: ''
        }
    });
    /**
     * 在layout的panle全局配置中,增加一个onCollapse处理title,使之能够在面板收起时也能显示title
     */
    $.extend($.fn.layout.paneldefaults, {
        onCollapse: function () {
            // 获取layout容器
            var layout = $(this).parents("div.layout");
            // 获取当前region的配置属性
            var opts = $(this).panel("options");
            // 获取key
            var expandKey = "expand"
                + opts.region.substring(0, 1).toUpperCase()
                + opts.region.substring(1);
            // 从layout的缓存对象中取得对应的收缩对象
            var expandPanel = layout.data("layout").panels[expandKey];
            // 针对横向和竖向的不同处理方式
            if (opts.region == "west" || opts.region == "east") {
                // 竖向的文字打竖,其实就是切割文字加br
                // var split = [];
                // for (var i = 0; i < opts.title.length; i++) {
                // split.push(opts.title.substring(i, i + 1));
                // }
                expandPanel.panel("body").addClass("panel-title").css(
                    "text-align", "center").html(opts.title);
            } else {
                expandPanel.panel("setTitle", opts.title);
            }
        }
    });
    /**
     * 创建工具类
     */
    var util = {
        getSelectedTab: function () {
            return window.mainTabs.tabs('getSelected')
        },
        /**
         * 用于当img标签获取图片失败后显示指定图片
         */
        noFind: function () {
            var img = event.srcElement;
            img.src = "images/error.jpg";
            img.onerror = null;
        },
        /**
         * 开始离线识别任务的按钮事件
         *
         * id 离线资源的id
         */
        startTask: function (id) {
            var tab = util.getSelectedTab();
            var grid = $('#resource_task_grid', tab[0]);
            $.post('task/startOffLineTask.action', {
                'ids': id
            }, function () {
                grid.datagrid('load',
                    'task/query.action?type=2&dataSourceId=' + id);
            });
        },
        /**
         * 创建grid中删除按钮的事件
         *
         * bntId 按钮的id标识 grid grid列表的所在元素 url 删除的url
         */
        createRemoveEvent: function (bntId, grid, url) {
            $(bntId, grid).unbind("click").bind('click', function () {
                var selections = grid.datagrid("getChecked");
                if (selections.length <= 0) {
                    $.messager.alert("提示!", "请选择要删除的记录!");
                    return;
                }
                $.messager.confirm('提示!', '是否确认删除？', function (r) {
                    if (r) {
                        var ids = selections.map(function (e) {
                            return e.id + ''
                        });
                        $.ajax({
                            url: url,
                            type: "post",
                            data: {
                                "ids": ids.toString()
                            },
                            success: function (data) {
                                if (data == "success") {
                                    $.messager.show({
                                        title: '消息',
                                        msg: '删除成功！',
                                        timeout: 2000
                                    });
                                    grid.datagrid("load");
                                } else {
                                    $.messager.show({
                                        title: '删除失败！',
                                        msg: data,
                                        timeout: 2000
                                    });
                                }
                            }
                        });
                    }
                });
            });
        },
        /**
         * 时间格式化函数.
         *
         * @param t
         *            时间戳
         * @return String 格式化后的时间
         */
        formateTime: function (t) {
            if (!t)
                return '';
            var formateStr = 'YYYY-MM-dd HH:mm:ss', d = new Date(t), time = {
                YYYY: d.getFullYear(),
                MM: d.getMonth() + 1,
                dd: d.getDate(),
                HH: d.getHours(),
                mm: d.getMinutes(),
                ss: d.getSeconds()
            };
            return formateStr.replace(/\w+/g, function (str) {
                // 小于10补0
                var val = time[str];
                return val < 10 ? ('0' + val) : val;
            });
        },
        /**
         * 时间格式化函数.
         *
         * @param t
         *            时间戳
         * @return String 格式化后的时间 时分
         */
        formateTimeReturnHour: function (t) {
            if (!t)
                return '';
            var  d = new Date(t), val = d.getHours() + ":" + d.getMinutes();
            return val;
        },
        /**
         * 时分比较
         * @param t1
         * @param t2
         * @returns {boolean}
         */
        compareDate:function(t1,t2) {
            var date = new Date();
            var a = t1.split(":");
            var b = t2.split(":");
            return date.setHours(a[0],a[1]) >= date.setHours(b[0],b[1]);
        },
        /**
         * 格式化任务状态
         * t:任务状态
         */
        formateTaskStatus: function (t,row) {
            var status='识别中';
            if(row.type==2){
                status=row.progress+'%'
            }
            var fmt = {
                1: status,
                2: '已完成',
                3: '已失败'
            };
            return fmt[t] || '';
        },
        /**
         * 格式化采集图片任务状态
         * t:状态
         */
        formateCollectPictrureStatus: function (t,row) {
            var status='等待中';
            var fmt = {
                0: '等待中',
                1: '下载中',
                2: '已完成',
                3: '暂停中',
                4: '已终止'
            };
            return fmt[t] || '';
        },
        /**
         * 格式化视频转码状态
         * t:视频转码状态
         */
        formateTranscodingStatus: function (t,row) {
            var status='未完成';
            var fmt = {
                0: status,
                1: '已完成',
                2: '进行中',
                3: '已失败',
                4: '已删除',
                5: '等待中',
                6: '重试中'
            };
            return fmt[t] || '';
        },
        /**
         * 格式化转码进度
         */
        formateTranscodingProgress: function (t,row) {
            return row.progress+'%';
        },
        /**
         * 格式化定时任务状态
         * t:任务状态
         */
        formateTimerTaskStatus: function (t,row) {
            var fmt = {
                0: '未开始',
                1: '正在运行'
            };
            return fmt[t] || '';
        },
        /**
         * 格式化任务类型
         */
        formateTaskType: function (t) {
            var fmt = {
                1: '实时任务',
                2: '离线任务',
                3: '批量任务',
                4:'实时图片任务'
            };
            return fmt[t] || '';
        },
        /**
         * 格式化资源类型
         */
        formateResourceType: function (t) {
            var fmt = {
                1: '图片',
                2: '视频',
                3: '目录'
            };
            return fmt[t] || '';
        },
        /**
         * 格式化特征物
         */
        formateCharacteristic:function(v){
            var fmt = {
                0: '无',
                1: '有'
            };
            return fmt[v] || ''
        },
        /**
         * 格式化遮阳板
         */
        formateSun:function(v){
            var fmt = {
                0: '未放下',
                1: '放下'
            };
            return fmt[v] || ''
        },
        // 单位树json格式转换
        getNodes:function(data){
        function exists(data, parentId){
            for(var i=0; i<data.length; i++){
                if (data[i].id == parentId) return true;
            }
            return false;
        }

        var nodes = [];
        // 得到顶层节点
        for(var i=0; i<data.length; i++){
            var obj = data[i];
            if (!exists(data, obj.pId)){
                nodes.push({
                    id:obj.id,
                    text:obj.name
                });
            }
        }
        var toDo = [];
        for(var i=0; i<nodes.length; i++){
            toDo.push(nodes[i]);
        }
        while(toDo.length){
            var node = toDo.shift();    // 父节点
            // 得到子节点
            for(var i=0; i<data.length; i++){
                var obj = data[i];
                if (obj.pId == node.id){
                    var child = {id:obj.id,text:obj.name};
                    if (node.children){
                        node.children.push(child);
                    } else {
                        node.children = [child];
                    }
                    toDo.push(child);
                }
            }
        }
        return nodes;
    },


    formateRegion:function(v,row,i) {
        if (row.region) {
            return util.getTreeText(comboTreeData['region'], v);
        }
    },

     getTreeText:function(treeData, nodeIds) {
        if (treeData== null || nodeIds == null) {
            return "";
        }
        var ids = [];

        if (typeof nodeIds == 'string') {
            ids = nodeIds.split(',');
//		alert(1);
        }
        else {
            ids.push(nodeIds);
        }
        // var ids = nodeIds.split(',');
        var i = 0;
        var texts = '';
        var text = null;
        var curNode = null;
        var ancestorIds = null;


        for (i = 0; i < ids.length; i = i + 1) {
            ids[i] = parseInt(ids[i]);
            curNode = util.getTreeNodeById(treeData, ids[i]);
            if (curNode != null) {
                ancestorIds = curNode.ancestorIds;
                if (ancestorIds != null && ancestorIds.length > 2) {
                    ancestorIds = ancestorIds.substring(1, ancestorIds.length - 1);
                    text = util.getTreeTextEx(treeData, ancestorIds);
                    if (i > 0 && text.length > 0) {
                        texts = texts + ';';
                    }
                    texts = texts + text;
                }
            }
        }

        return texts;

    },

     getTreeNodeById:function(treeData, id) {
        var curLevel = [];
        var subLevel = [];
        var i = 0;
        var j = 0;

        for(i = 0; i < treeData.length; i = i + 1) {
            curLevel.push(treeData[i]);
        }

        while (curLevel.length > 0) {
            subLevel = [];
            for (i = 0; i < curLevel.length; i = i + 1) {
                if (curLevel[i].id == id) {
                    return curLevel[i];
                }
                if (curLevel[i].children != null) {
                    for (j = 0; j < curLevel[i].children.length; j = j + 1) {
                        subLevel.push(curLevel[i].children[j]);
                    }
                }
            }
            curLevel = subLevel;
        }
        return null;
    },

     getTreeTextEx:function(treeData, value) {
        var v = [];

        if (typeof(value) == 'number') {
            v.push(value);
        }
        else {
            v = value;
        }

        if (treeData == null || v == null) {
            return "";
        }

        if (treeData.length < 1) {
            return v;
        }

        var values = v.split(',');
        var valueText = '';
        var children = treeData;
        var i = 0;
        var j = 0;
        var curNode = null;
        var parents = null;

        for (i = 0; i < values.length; i = i + 1) {
            /*
             * if (children == null || children.length < 1) { valueText = valueText +
             * ";"; children = treeData; //break; }
             */
            curNode = null;
            for (j = 0; j < children.length; j = j + 1) {
                if (children[j].id == parseInt(values[i])) {
                    valueText = valueText + (children[j].text || '');
                    curNode = children[j];
                    break;
                }
            }

            children = 	curNode.children;
            if (children != null && children.length > 0) {
                valueText = valueText + ',';
            }
        }

        return valueText;
    },
        /**
         * 格式化车牌
         */
        formatePlateType: function (value,row,index) {
            row=row||value;
            var plateValue='';
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
            };

            $.each(plate_type_arr,function(key,val){
                if(value == key){
                    plateValue =  val;
                }
            });
            return plateValue;
        },
        /**
         * 模板解析工具方法
         *
         * @param html
         *            等处理的模板字符串
         * @param model
         *            要填充的数据对象
         * @return String 替换好的html字符串
         * @author phq
         */
        parser: function (html, model) {
            model = model || {};
            return html.replace(/{#(.*?)}/igm, function (s, s1) {
                var val = model[s1];
                if (val == null) {
                    with (model) {
                        val = eval(s1) || "";
                    }
                }
                return val;
            });
        },

        /** 以100毫秒为单位分批调用n次fun ,fun会把n递减做为参数传入 */
        bat: function (n, fun) {
            // 无视浏览器性能差异的批处理
            var t = new Date, callee = arguments.callee;
            do {
                if (!n) { // 退出任务条件
                    return;
                }
                fun(n--);// n递减做为参数传入fun调用
            } while (new Date - t < 100); // 100ms休息一下
            setTimeout(function () {
                callee(n, fun);
            }, 20); // 这里是休息时间
        },
        uuid: function () {// 生成uuid
            return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-"
            + S4() + S4() + S4());
        },
        showTrail: function (trails,strokeColor,weight) {
            window.mainTabs.tabs('select',0);
            var markers = {};
            var rows = trails;
            var markerOptions = {icon: gIconCar, draggable: false};
            var invalidData = [], data = [];
            var $map = $("#main_map");
            var map = $map.data('map');
            var degreesPerRadian = 180.0 / Math.PI;
            //箭头路径
            var imagePath;

            /**
             * 移动到第一个监控点
             */
            var c=rows[0];
            var LatLng = new GLatLng(+c.latitude, +c.longitude);
            //显示轨迹使用11级地图
            map.setZoom(11);
            setTimeout(function () {
                map.panTo(LatLng);
            });

            function checkLatLng(c) {
                //检测监控点坐标是否合法
                return c.latitude && c.latitude > 0 && c.longitude && c.longitude > 0;
            }

            function addLabelMarker(latLng, labelText) {
                var label = new GLabelMarker(latLng,{labelText:labelText});
                map.addOverlay(label);
                return label;
            }

            rows.forEach(function (r) {
                if (checkLatLng(r)) {
                    //添加文字标注
                    var labelText = '&emsp;&emsp;车牌:'+ r.license + '<br/>' + '&emsp;&emsp;监控点:'+ r.location + '<br/>' + '&emsp;&emsp;过车时间:' + util.formateTime(r.resultTime);
                    addLabelMarker(new GLatLng(r.latitude, r.longitude), labelText);

                    markers[r.serialNumber] = showObject(map, r, markerOptions);
                    data.push(new GLatLng(r.latitude, r.longitude));
                } else {
                    invalidData.push(r)
                }
            });
            if (invalidData.length > 0) {
                invalidData = invalidData.map(function (r) {
                    return r.license;
                }).join();
                $.messager.show({title: "显示轨迹", msg: invalidData + "缺失经纬度信息!!"});
            }
            if (data.length > 0) {
                if(!strokeColor) {
                    strokeColor='red';
                }
                imagePath = "/Gis_0001/images/"+strokeColor+"/img_arrow";
                //画箭头
                for (var i = 0, len = data.length - 1; i < len; i++) {
                    var p1 = data[i], p2 = data[i + 1];
                    //
                    var line = new GPolyline([p1,p2], strokeColor, weight, 1);
                    map.addOverlay(line);
                    //
                    var dir = bearing(p1,p2);
                    var offsetBase = 16;
                    //alert(dir + ":" + Math.sin(dir*2*Math.PI/360) + ":" + Math.cos(dir*2*Math.PI/360));
                    var hOffset = Math.round(offsetBase * Math.sin(dir*2*Math.PI/360));
                    var vOffset = Math.round(offsetBase * Math.cos(dir*2*Math.PI/360));
                    //alert(dir*2*Math.PI/360 + ":" + hOffset + ":" + vOffset);
                    var dir = Math.round(dir/3) * 3;
                    while (dir >= 120) {dir -= 120;}
                    var arrowIcon = new GIcon();
                    arrowIcon.iconSize = new GSize(24,24);
                    arrowIcon.iconAnchor = new GPoint(12+hOffset, 12-vOffset);
                    arrowIcon.image = imagePath + "/dir_"+dir+".png";
                    map.addOverlay(new GMarker(data[i + 1], arrowIcon));
                }
            }

            function bearing( from, to ) {
                var lat1 = from.latRadians();
                var lon1 = from.lngRadians();
                var lat2 = to.latRadians();
                var lon2 = to.lngRadians();
                var angle = - Math.atan2( Math.sin( lon1 - lon2 ) * Math.cos( lat2 ), Math.cos( lat1 ) * Math.sin( lat2 ) - Math.sin( lat1 ) * Math.cos( lat2 ) * Math.cos( lon1 - lon2 ) );
                if ( angle < 0.0 ) {
                    angle += Math.PI * 2.0;
                }
                angle = angle * degreesPerRadian;
                angle = angle.toFixed(1);
                return angle;
            }
        },
        /**
         * 选择监控点，
         * 事件顺序：在复选框选中不会触发select和click事件，在复选框外点击节点触发事件的顺序为 = =: 1--onSelect; 2--onCheck; 3--onClick.
         *           因此需要在onCheck和onClick事件中进行处理
         */
        checkCam:function(cameraIds,node,camIdArr,camNameArr,checked) {
            var t = cameraIds.combotree("tree");
            //如果是叶子结点并为监控点
            if(isLeaf(node.target) && !!node.attributes) {
                if(checked) {
                    node.checked = checked;
                    camIdArr.jiuLingAdd(node.attributes.cameraId);
                    camNameArr.jiuLingAdd(node.text);
                    cameraIds.combo("setValues",camIdArr);
                    cameraIds.combo("setText",camNameArr);
                    return;
                } else {
                    node.checked = checked;
                    camIdArr.jiuLingRemove(node.attributes.cameraId);
                    camNameArr.jiuLingRemove(node.text);
                    cameraIds.combo("setValues",camIdArr);
                    cameraIds.combo("setText",camNameArr);
                    return;
                }
            }

            var nodes = t.tree("getChildren",node.target);
            var len = nodes.length,i=0;
            for(;i<len;i++) {
                var cnode = nodes[i];
                cnode.checked=checked;
                if(isLeaf(cnode.target) && !!cnode.attributes) {
                    if(checked) {
                        camIdArr.jiuLingAdd(cnode.attributes.cameraId);
                        camNameArr.jiuLingAdd(cnode.text);
                    } else {
                        camIdArr.jiuLingRemove(cnode.attributes.cameraId);
                        camNameArr.jiuLingRemove(cnode.text);
                    }
                }
            }
            cameraIds.combo("setValues",camIdArr);
            cameraIds.combo("setText",camNameArr);

            function isLeaf(target){
                return t.tree("isLeaf",target);
            }
        },
        /**
         * 通过地图选择监控点触发onChange事件
         * @param cameraIds
         * @param node
         * @param camIdArr
         * @param camNameArr
         * @param checked
         */
        checkCamWithMap:function(cameraIds) {
            //存放被选择的监控点
            var t =cameraIds.combotree("tree");
            var nodes = t.tree("getChecked");
            //存放被选择的监控点
            var ncamIdArr = [],ncamNameArr = [];
            for(var i= 0,len=nodes.length;i<len;i++) {
                if(!!nodes[i].attributes) {
                    ncamIdArr.jiuLingAdd(nodes[i].attributes.cameraId);
                    ncamNameArr.jiuLingAdd(nodes[i].text);
                }
            }
            cameraIds.combo("setValues",ncamIdArr);
            cameraIds.combo("setText",ncamNameArr);
            ncamIdArr = null;
            ncamNameArr = null;
        }
    };
    // Generate four random hex digits.
    function S4() {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    }
    var uuidTmp=0;
    util.uuid.init= function () {
        return uuidTmp=util.uuid();
    }
    util.uuid.get= function () {
        return uuidTmp;
    }

    window.util = util;
})(jQuery, window);
