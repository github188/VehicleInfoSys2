<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title></title>
	</head>
	<body>
		<div id='user_list'></div>
		<div id='user_dialog'></div>
		<script id='tmpl_user_add' type='tmpl'>
			<form action="">
				<table>
					<tr>
						<td>用&ensp;户&ensp;名:</td>
						<td><input id="loginName" name='loginName' type="text" class="easyui-textbox" data-options="required:true,validType:{letterAndNumberAndChinese:true,length:[5,20],username_remote:['/user/isExistUser.action','loginName']}" >&nbsp;<span style="color:red">*&nbsp;必填项</span></td>
					</tr>
					<tr>
						<td>密&emsp;&emsp;码:</td>
						<td><input id="user_pwd" name='pwd' type="password" class="easyui-textbox" data-options="required:true,validType:{length:[5,20]}">&nbsp;<span style="color:red">*&nbsp;必填项</span></td>
					</tr>
					<tr>
						<td>确认密码:</td>
						<td><input name='' type="password" class="easyui-textbox" data-options="required:true,validType:{equals:'#user_pwd'}">&nbsp;<span style="color:red">*&nbsp;必填项</span></td>
					</tr>
					<tr>
						<td>所属部门:</td>
						<td>
						<input class="easyui-combotree" name="departmentId"
								data-options="required:true,multiple:false,panelHeight:'200',editable:false,
									url:'dept/deptTree.action',
									onShowPanel:function() {
										$(this).combotree('reload');
									}" />&nbsp;<span style="color:red">*&nbsp;必选项</span>
					    </td>
					</tr>
					<tr>
						<td>是否启用:</td>
						<td>
						<input class="easyui-combobox" name="isValid"
							data-options="required:true,panelHeight:'auto',value:'1',
							data:[{value:'1',text:'启用'},{value:'2',text:'停用'}]" />&nbsp;<span style="color:red">*&nbsp;必选项</span>
					</td>
					</tr>
					<tr>
						<td>是否为部门管理员:</td>
						<td>
						<input class="easyui-combobox" name="departmentAdministratorId"
							data-options="required:true,panelHeight:'auto',value:'2',
							data:[{value:'1',text:'是'},{value:'2',text:'否'}]" />&nbsp;<span style="color:red">*&nbsp;必选项</span>
					</td>
					</tr>
					<tr>
						<td>真实姓名:</td>
						<td><input name='realName' type="text" class="easyui-textbox" data-options="validType:{length:[0,20]}"></td>
					</tr>
					<tr>
						<td>电&emsp;&emsp;话:</td>
						<td><input name='tel' type="text" class="easyui-textbox" data-options="validType:{num:true,length:[1,20]}"></td>
					</tr>
					<tr>
						<td>描&emsp;&emsp;述:</td>
						<td><textarea id="" name="dsc" cols="30" rows="10" class="easyui-validatebox" data-options="validType:{length:[0,250]}"> </textarea></td>
					</tr>
				</table>
			</form>
		</script>
		<script id='tmpl_user_edit' type='tmpl'>
			<form action="">
				<input type="hidden" name='id' value='{#id}'>
				<input type="hidden" name='loginName' value='{#loginName}'>
				<table>
					<tr>
						<td>用&ensp;户&ensp;名:</td>
						<td>{#loginName}</td>
					</tr>
					<tr>
						<td>所属部门:</td>
						<td>
						<input class="easyui-combotree" name="departmentId" value='{#departmentId}'
								data-options="required:true,multiple:false,panelHeight:'200',editable:false,
									url:'dept/deptTree.action',
									onShowPanel:function() {
										$(this).combotree('reload');
									}" />&nbsp;<span style="color:red">*&nbsp;必选项</span>
					    </td>
					</tr>
					<tr>
						<td>是否启用:</td>
						<td>
						<input class="easyui-combobox" name="isValid"
							data-options="required:true,panelHeight:'auto',value:'{#isValid}',
							data:[{value:'1',text:'启用'},{value:'2',text:'停用'}]" />&nbsp;<span style="color:red">*&nbsp;必选项</span>
					</td>
					</tr>
					<tr>
						<td>是否为部门管理员:</td>
						<td>
						<input class="easyui-combobox" name="departmentAdministratorId"
							data-options="required:true,panelHeight:'auto',value:'{#departmentAdministratorId}',
							data:[{value:'1',text:'是'},{value:'2',text:'否'}]" />&nbsp;<span style="color:red">*&nbsp;必选项</span>
					</td>
					</tr>
					<tr>
						<td>真实姓名:</td>
						<td><input name='realName' type="text" value="{#name}" class="easyui-textbox" data-options="validType:{length:[0,20]}" ></td>
					</tr>
					<tr>
						<td>电&emsp;&emsp;话:</td>
						<td><input name='tel' type="text" value="{#tel}" class="easyui-textbox"  data-options="validType:{num:true,length:[1,20]}"></td>
					</tr>
					<tr>
						<td>描&emsp;&emsp;述:</td>
						<td><textarea id="" name="dsc" cols="30" rows="10" class="easyui-validatebox" data-options="validType:{length:[0,250]}" >{#dsc}</textarea></td>
					</tr>
				</table>
			</form>
		</script>
		<script>
			(function($){
                var _user=window.user,
                        _datagrid,
                        _open;

				var $user_list=$('#user_list'),
				$user_dialog=$('#user_dialog'),
				tmpl_user_add=$('#tmpl_user_add').html(),
				tmpl_user_edit=$('#tmpl_user_edit').html();

				//添加用户
				var addUserHandler=function(){
					var html=util.parser(tmpl_user_add);
					$user_dialog.html(html).dialog({
						onOpen:function(){
							$.parser.parse($user_dialog);
							//禁止密码输入框的复制 粘贴 剪切
							$("input:password").bind("copy cut paste",function(e){
								return false;
							})
						},
						modal:true,
						width:380,
						title:'添加用户',
						buttons:[{
							text:'提交',
							iconCls:'icon-ok',
							handler:function() {
								var $form=$user_dialog.find('form'),
								data=$form.serialize(),
								msg='添加失败';
								if ($form.form("validate")) {
									$.ajax({type:'post',url:'/user/add.action',data:data,success:function(d){
											if(d=='success'){
												msg='添加成功!';
												$user_list.datagrid('reload');
												$user_dialog.dialog('close');
											}
											$.messager.show({ title:'提示', msg:msg });
											},error:function(){
											$.messager.show({ title:'提示', msg:msg });
										}
									});
								}
							}
						},
						{
							text:'取消',
							iconCls:'icon-cancel',
							handler:function() {
								$user_dialog.dialog('close');
							}
						}]

					});
				}

				//编辑用户
				var editUserHandler=function(){
					var rows=$user_list.datagrid('getChecked')
					if(rows.length!=1){
						$.messager.alert('提示','请选中一条记录!');
						}else{
						var row=rows[0];
						var html=util.parser(tmpl_user_edit,row);
						$user_dialog.html(html).dialog({
							onOpen:function(){
								$.parser.parse($user_dialog);
							},
							modal:true,
							width:380,
							title:'修改用户',
							buttons:[{
								text:'提交',
								iconCls:'icon-ok',
								handler:function() {
									var $form=$user_dialog.find('form'),
									data=$form.serialize(),
									msg='修改失败!';
									if ($form.form("validate")) {
										$.ajax({type:'post',url:'/user/edit.action',data:data,success:function(d){
												if(d=='success'){
													msg='修改成功!';
													$user_list.datagrid('reload');
													$user_dialog.dialog('close');
												}
												$.messager.show({ title:'提示', msg:msg });
												},error:function(){
												$.messager.show({ title:'提示', msg:msg });
											}
										});
									}
								}
							},
							{
								text:'取消',
								iconCls:'icon-cancel',
								handler:function() {
									$user_dialog.dialog('close');
								}
							}]
						});
					}
				}

				//删除用户
				var delUserHandler=function(){
					var rows=$user_list.datagrid('getChecked')
					if(rows.length==0){
						$.messager.alert('提示','请选中至少一条记录!');
						return;
					}
					$.messager.confirm('提示','确认删除选中记录?',function(r){
						if(r){
							var ids=rows.map(function(row){
								return row.id;
							}).join(',');
							var msg='删除失败!';
							$.ajax({type:'post',url:'/user/del.action',data:{ids:ids},success:function(d){
									if(d=='success'){
										msg='删除成功!';
										$user_list.datagrid('reload');
										$user_dialog.dialog('close');
									}else{
										msg=msg+d;
                                    }
									$.messager.show({ title:'提示', msg:msg });
									},error:function(){
									$.messager.show({ title:'提示', msg:msg });
								}
							});
						}
					});
				}

				//数据表格初始化
                _datagrid = $user_list.datagrid({
					url:'/user/list.action',
					loadMsg:'数据载入中',
					pagination:true,
					rownumbers:true,
					fit: true,
					striped:true,
                    checkOnSelect: false,
                    selectOnCheck:false,
                    singleSelect: true,
					pageSize:20,
					columns:[[
						{field:'checkbox',checkbox:true},
						{field:'loginName',title:'用户名'},
                        {field:'name',title:'真实姓名'},
                        {field:'depName',title:'所属部门'},
                        {field:'isValid',title:'账户状态',
                            formatter: function(value){
                                if (value==1){
                                    return "启用";
                                } else {
                                    return "停用";
                                }
                            }
                        },
                        {field:'departmentAdministratorId',title:'是否是部门管理员',
                            formatter: function(value){
                                if (value==1){
                                    return "是";
                                } else if (value==0) {
                                    return "系统管理员";
                                } else {
                                    return "否";
                                }
                            }
                        },
						{field:'tel',title:'电话'},
						{field:'dsc',title:'描述'},
						{field:'operation',title:'操作',formatter:function(v,row,index){
                            var s='';
                            s+='<a onclick="user.open(\'账号权限\','+index+')" data-options="iconCls:\'icon-jurisdiction\'"><span style="color:#0e13ff">操作权限</span>'
                            s+='<a userId="'+row.id+'" class="changePwd" href="javascript:void(0)" title="修改密码"><span style="color:#ff090e">&nbsp;&nbsp;&nbsp;修改密码</span></a>'
                            return s;
						}}
					]],
					toolbar:[{
						text:'添加',
						iconCls: 'icon-add',
						handler: addUserHandler
						},'-',{
						text:'编辑',
						iconCls: 'icon-edit',
						handler: editUserHandler
						},'-',{
						text:'删除',
						iconCls: 'icon-remove',
						handler: delUserHandler
						}]
				});

                _open = _user.open = function(title, index) {
                    if(title =="账号权限") {
                        _user.openPrvDialog(title,_datagrid.datagrid('getRows')[index]);
                    }
                }
			})(jQuery);
		</script>
	</body>
</html>
