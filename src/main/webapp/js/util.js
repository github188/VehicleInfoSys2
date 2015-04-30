(function($, window) {
	/**
	* 重置和完善部分国际化
	*/
	if ($.fn.pagination) {
		$.fn.pagination.defaults.beforePageText = '第';
		$.fn.pagination.defaults.afterPageText = '页  共{pages}页';
		$.fn.pagination.defaults.displayMsg = '共{total}条记录';
	}
	if ($.fn.datetimebox) {
		var buttons=$.fn.datetimebox.defaults.buttons;
		buttons.pop();//把关闭按钮给去掉
		buttons.push( {//加入清空按钮
			text : '清空',
			handler : function(box) {
				$(box).datetimebox('setValue','').datetimebox("hidePanel");
			}
		})
	}
	if ($.fn.panel) {
		$.fn.panel.defaults.loadingMessage = '加载中';
	}
	//不能让窗口移出边界
	var panelMoveCallback=function(left,top){
		var $self=$(this),
		opts=$self.panel("options"),
		$panel=$self.closest('.panel'),
		win_width=isNaN(opts.width)?$panel.outerWidth():opts.width,
		win_height=isNaN(opts.height)?$panel.outerHeight():opts.height,
		_left=Math.min(left,document.body.clientWidth-win_width),
		_top=Math.min(top,document.body.clientHeight-win_height);
		_left=Math.max(0,_left);
		_top=Math.max(0,_top);
        
		if(_left!=left||_top!=top){
			  $self.panel("move",{
				left:_left,top:_top
			  })
		}
	}
	
	if ($.fn.dialog) {
		$.extend($.fn.dialog.defaults,{
			modal : true,//模态
			onMove : panelMoveCallback
		})
	}
	//设置默认的window属性
	if ($.fn.window) {
		$.extend($.fn.window.defaults,{
			minimizable: false,//关闭最小化
			modal : true,//模态
			onMove : panelMoveCallback
		})
	}
	// 扩展equals检验方法
	$.extend(
		$.fn.validatebox.defaults.rules,
		{
			username_remote : {
				validator : function(value, param) {
					var postdata = {};
					postdata[param[1]] = value;
					var m_result = $.ajax( {
						type : "POST",// http请求方式
						url : param[0], // 服务器段url地址
						data : postdata, // 发送给服务器段的数据
						dataType : "type", // 告诉JQuery返回的数据格式
						async : false
					}).responseText;
					return m_result == "true";
				},
				message : '用户名已存在,请重新输入!'
			},
			checkPwd_remote : {
				validator : function(value, param) {
					var postdata = {};
					postdata[param[1]] = value;
					postdata[$(param[2]).attr('name')] = $(param[2])
					.val();
					var m_result = $.ajax( {
						type : "POST",// http请求方式
						url : param[0], // 服务器段url地址
						data : postdata, // 发送给服务器段的数据
						dataType : "type", // 告诉JQuery返回的数据格式
						async : false
					}).responseText;
					return m_result == "true";
				},
				message : '密码错误,请重新输入!'
			},
			range : {
				validator : function(value, param) {
					var reg = new RegExp("^-?\\d+(\\.\\d{1,"
						+ param[2] + "})?$");
						var r = reg.test(value);
						if (!r) {
							return false;
						}
						return value >= param[0] && value <= param[1];
				},
				message : '请输入{0}~{1}内的数字，小数点后最多保留{2}位'
			},
			number : {
				validator : function(value, param) {
					var reg = /^\d+$/;
					var r = reg.test(value);
					if (!r) {
						return false;
					}
					return value >= param[0] && value <= param[1];
				},
				message : '必须是{0}~{1}内的数字'
			},
			num : {
				validator : function(value) {
					return (/^\d+$/).test(value);
				},
				message : '请输入数字'
			},
			checkIp : {// 验证IP地址
				validator : function(value) {
					var reg = /^((1?\d?\d|(2([0-4]\d|5[0-5])))\.){3}(1?\d?\d|(2([0-4]\d|5[0-5])))$/;
					return reg.test(value);
				},
				message : 'IP地址格式不正确'
			},
			_email : {
				validator : function(value) {
					return /^([a-zA-Z0-9_-])+\.?[a-zA-Z0-9_-]+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
					.test(value);
				},
				message : '请输入有效的邮箱地址'
			},
			letterAndNumber : {
				validator : function(value) {
					var reg = /^[a-zA-Z0-9]+$/;
					var r = reg.test(value);
					return r;
				},
				message : '请输入英文字母或数字，不包含中文汉字和特殊字符!'
			},
			letterAndChinese : {
				validator : function(value) {
					var reg = /^[a-zA-Z\u4e00-\u9fa5]+$/;
					var r = reg.test(value);
					return r;
				},
				message : '请输入英文字母或汉字，不包含数字和特殊字符!'
			},
			letterAndNumberAndChinese : {
				validator : function(value) {
					var reg = /^[a-zA-Z0-9\u4e00-\u9fa5()_]+$/;
					var r = reg.test(value);
					return r;
				},
				message : '请输入英文字母、数字或汉字，不包含特殊字符!'
			},
			equals : {
				validator : function(value, param) {
					return value == $(param).val();
				},
				message : '密码不一致.'
			},
			// 图片和视频格式验证
			img_upload : {
				validator : function(value, id) {
					var fileList = null;
					// 检查浏览器是否支持file api
					if (window.File || window.FileReader
					|| window.FileList || window.Blob) {
						fileList = $(this).prop('files');
					} else {
						fileList = [ {
							name : value
						} ];
					}
					var options = $(this).validatebox('options');
					var fileType = $('input:radio:checked').val();
					for ( var i = 0; i < fileList.length; i++) {
						var value = fileList[i].name;
						var ext = value.substring(value
							.lastIndexOf(".") + 1);
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
				message : ''
			}
		});
		/**
		* 在layout的panle全局配置中,增加一个onCollapse处理title,使之能够在面板收起时也能显示title
		*/
		$.extend($.fn.layout.paneldefaults, {
			onCollapse : function() {
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
			/**
			* 用于当img标签获取图片失败后显示指定图片
			*/
			noFind : function() {
				var img = event.srcElement;
				img.src = "images/error.jpg";
				img.onerror = null;
			},
			/**
			* 开始离线识别任务的按钮事件
			* 
			* id 离线资源的id
			*/
			startTask : function(id) {
				var tab = window.mainTabs.tabs('getSelected');
				var grid = $('#resource_task_grid', tab[0]);
				$.post('task/startOffLineTask.action', {
					'ids' : id
				}, function() {
					grid.datagrid('load',
					'task/list.action?type=2&dataSourceId=' + id);
				});
			},
			/**
			* 创建grid中删除按钮的事件
			* 
			* bntId 按钮的id标识 grid grid列表的所在元素 url 删除的url
			*/
			createRemoveEvent : function(bntId, grid, url) {
				$(bntId, grid).unbind("click").bind('click', function() {
					var selections = grid.datagrid("getChecked");
					if (selections.length <= 0) {
						$.messager.alert("提示!", "请选择要删除的记录!");
						return;
					}
					$.messager.confirm('提示!', '是否确认删除？', function(r) {
						if (r) {
							var ids = selections.map(function(e) {
								return e.id + ''
							});
							$.ajax( {
								url : url,
								type : "post",
								data : {
									"ids" : ids.toString()
								},
								success : function(data) {
									if (data == "success") {
										$.messager.show( {
											title : '消息',
											msg : '删除成功！',
											timeout : 2000
										});
										grid.datagrid("load");
									} else {
										$.messager.show( {
											title : '删除失败！',
											msg : data,
											timeout : 2000
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
			* @return 格式化后的时间
			*/
			formateTime : function(t) {
				if (!t)
					return '';
				var formateStr = 'YYYY-MM-dd HH:mm:ss', d = new Date(t), time = {
					YYYY : d.getFullYear(),
					MM : d.getMonth() + 1,
					dd : d.getDate(),
					HH : d.getHours(),
					mm : d.getMinutes(),
					ss : d.getSeconds()
				};
				return formateStr.replace(/\w+/g, function(str) {
					// 小于10补0
					var val = time[str];
					return val < 10 ? ('0' + val) : val;
				});
			},
			/**
			* 格式化任务状态
			*/
			formateTaskStatus : function(t) {
				var fmt = {
					1 : '识别中',
					2 : '已完成',
					3 : '已失败'
				};
				return fmt[t] || '';
			},
			/**
			* 格式化任务类型
			*/
			formateTaskType : function(t) {
				var fmt = {
					1 : '实时任务',
					2 : '离线任务',
					3 : '批量任务'
				};
				return fmt[t] || '';
			},
			/**
			* 格式化资源类型
			*/
			formateResourceType : function(t) {
				var fmt = {
					1 : '图片',
					2 : '视频',
					3 : '目录'
				};
				return fmt[t] || '';
			},
			/**
			* 模板解析工具方法
			* 
			* @param html
			*            等处理的模板字符串
			* @param model
			*            要填充的数据对象
			* @return 替换好的html字符串
			* @author phq
			*/
			parser : function(html, model) {
				model = model || {};
				return html.replace(/{#(.*?)}/igm, function(s, s1) {
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
			bat : function(n, fun) {
				// 无视浏览器性能差异的批处理
				var _n = n;
				var t = new Date, callee = arguments.callee;
				do {
					if (!n) { // 退出任务条件
						return;
					}
					fun(n--);// n递减做为参数传入fun调用
				} while (new Date - t < 100); // 100ms休息一下
				setTimeout(function() {
					callee(n, fun);
				}, 20); // 这里是休息时间
			},
			uuid : function() {// 生成uuid
				return (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-"
				+ S4() + S4() + S4());
			}
		}
		// Generate four random hex digits.
		function S4() {
			return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
		}

		window.util = util;
})(jQuery, window);
