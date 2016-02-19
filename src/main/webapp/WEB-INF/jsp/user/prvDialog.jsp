<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="user_prv_dialog">
	<div style="padding: 10px;">
		<form id="user_prv_form" method="post">
			<table>
				<tr>
					<td><label>账号：</label></td>
					<td><div id='user_prv_form_name' name='name' style="text-align: left"></div></td>
					<td><label>账号角色：</label></td>
					<td><div id='user_prv_form_roleList' name='roleList'></div></td>
				</tr>
				<tr>
					<td valign="top"><label>账号权限：</label></td>
					<td colspan="3">
						<div ms-controller="user_prv" style="width: 930px;height:160px;">
							<table style="border: 1px black solid;">
								<tr ms-repeat-r='resources' style="border: 1px black solid;">
									<td style="border: 1px black solid;">
										<input style="width: 20px" type="checkbox" ms-checked='r.all' ms-click='selectRow(r,$event)'>
										<label>{{r.name}}</label>
									</td>
									<td style="border: 1px black solid;">
										<label ms-repeat-p='r.privileges'>
											<input style="width: 20px" type="checkbox" ms-duplex='r.ownPrivileges' ms-value='p.id'>
											<label name="prv_txt">{{p.name}}</label>
										</label>
									</td>
								</tr>
							</table>
							<input style="width: 20px" type="checkbox" ms-click='selectAll' ms-checked='all'>
							<label>全选</label>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>	
</div>
<script type="text/javascript">
$(function(){
	var _user={},
            _dialog,
		_form,
		_roleCombotree;
    window.user = _user;

	var resourceCache;
	var up = avalon.define("user_prv", function(vm) {
        vm.resources=[]
		vm.all=true;
		vm.selectRow=function(r,e,checked){
			//e && e.preventDefault();
			var check=checked===undefined?(r.all=!r.all):checked,
			p=r.privileges,
			op=r.ownPrivileges=[];
			if(check){
				p.forEach(function(o){
					r.ownPrivileges.push(o.id+'');
				});
			}
		}
		vm.selectAll=function(){
			var checked=!vm.all;
			vm.resources.forEach(function(r){
				vm.selectRow(r,null,checked);
			});
		}
		vm.reset=function(){
			initResources(resourceCache);
		}
     });
	//初始化数据
	var initResources=function(rs){
		up.resources=formateResources(rs);
		up.resources.forEach(function(r){
			var o=r.ownPrivileges,
			p=r.privileges;
			r.all=o.length==p.length;
			o.$watch('length',function(v){
				r.all=v==p.length;
				up.all=!up.resources.some(function(val){
					return !val.all;
				})
			});
		});
		//初始化全选
		up.all=!up.resources.some(function(val){
			return !val.all;
		})
	}
	//格式化成需要的格式
	var formateResources=function(rs){
		var a=rs.allPrivileges,
		r,r_tmp={},arr=[];
           a.sort(function(a,b){
            return a.id-b.id;
           });
		a.forEach(function(v){
			var p={name:v.actionName,id:v.actionId}
			if(v.resourceId in r_tmp){
				r_tmp[v.resourceId].privileges.push(p)
				}else{
				arr.push(r_tmp[v.resourceId]={
                       id:v.resourceId,
					name:v.resourceName,
					privileges:[p],
					ownPrivileges:[],
					all:false
				})
			}
		});

		rs.userPrivileges.forEach(function(v){
			r_tmp[v.resourceId].ownPrivileges.push(v.actionId+'');
		});

		return arr;
	}
	_roleCombotree = $('#user_prv_form_roleList').combotree({
		checkbox: true,
		multiple: true,
		panelHeight: "auto",
		panelMaxHeight: 300,
		loadFilter:function(data) {
			resourceCache=data;
			initResources(data);
			var values = data.ownRoles.map(function(e){
				return e.id;
			});
			return data.roles.map(function(e){
				var o = {
					id:e.id,
					text:e.name
				}
				if(values.indexOf(e.id)>=0) {
					o.checked = true;
				}
				return o;
			});
		},
		onChange:function(nVal,oVal) {
			changeRolePrivilege(nVal);
		}
	});

	//显示选择角色对应的权限
	changeRolePrivilege = function(ids) {
		if(ids == ''){
			clearRolePrivilege();
		}else{
			$.post('/role/findRoleActionprivilege.action', {roleIds: ids.join()}, function (data) {
				showRolePrivilege(data);
			})
		}
	}

	//情况角色对应的权限标记
	clearRolePrivilege = function(){
		var txt_all = $("label[name='prv_txt']");
		for(var i = 0;i<txt_all.length;i++){
			txt_all[i].style.color = 'black';
		}
	}

	//显示角色对应的权限标记
	showRolePrivilege = function(data){
		clearRolePrivilege();
		var txt_all = $("label[name='prv_txt']");
		for(var o = 0; o<data.length; o++){
			var actionName = data[o].actionName;
			for(var i = 0;i<txt_all.length;i++){
				var labelName = txt_all[i].innerHTML;
				if(actionName == labelName){
					txt_all[i].style.color = '#ff6600';
					break;
				}
			}
		}

	}

	_dialog = $('#user_prv_dialog').dialog({
		closed:true,
		modal:true,
		buttons:[{
			text:'提交',
			iconCls:'icon-ok',
			handler:function(){
				_form.form('submit');
			}
		},{
			text:'重置',
			iconCls:'icon-redo',
			handler:function(){
				_form.form('reset');
			}
		}]
	});
	_form =$('#user_prv_form').form({
		onSubmit:function(params) {
			var ids=[];
			up.resources.forEach(function(r){
				ids=ids.concat(r.ownPrivileges);
			});
			params.prvList = ids;
			return true;
		},
		success: function(data) {
			if(data=="success") {
				$.messager.show({
					title:'消息',
					msg:'提交成功！',
					timeout:2000
				});
    		 	_dialog.dialog('close');
			}else{
				$.messager.alert({title:'错误',msg:'提交失败'});
               }
    	}
	});
	
	window.user.openPrvDialog = function(title, row) {
		var url;
		_form.form('reset');
		
		url = "user/updatePrivilege.action?userId="+row.id;
		$("#user_prv_form_name").html(row.loginName);
		_roleCombotree.combotree('reload', '/user/privileges.action?id=' + row.id);
				
		_form.form({url: url});
		_dialog.dialog('setTitle',title);
		_dialog.dialog('open');
	}
})
</script>
