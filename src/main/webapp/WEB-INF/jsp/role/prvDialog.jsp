<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="role_prv_dialog">
	<div style="padding: 10px;">
		<form id="role_prv_form" method="post">
			<table>
				<tr>
					<td><label>角色名称：</label></td>
					<td><div id='role_prv_form_name' name='name' style="text-align: left"></div></td>
				</tr>
				<tr>
					<td valign="top"><label>角色权限：</label></td>
					<td colspan="3">
						<div ms-controller="role_prv" style="width: 930px;height:160px;">
							<table style="border: 1px black solid;">
								<tr ms-repeat-r='resources' style="border: 1px black solid;">
									<td style="border: 1px black solid;">
										<input style="width: 20px" type="checkbox" ms-checked='r.all' ms-click='selectRow(r,$event)'>
										<label>{{r.name}}</label>
									</td>
									<td style="border: 1px black solid;">
										<label ms-repeat-p='r.privileges'>
											<input style="width: 20px" type="checkbox" ms-duplex='r.ownPrivileges' ms-value='p.id'>
											{{p.name}}
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
	var _dialog,
		_form,
		_roleCombotree;
		
	var resourceCache;
	var up = avalon.define("role_prv", function(vm) {
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

		rs.rolePrivileges.forEach(function(v){
			r_tmp[v.resourceId].ownPrivileges.push(v.actionId+'');
		});

		return arr;
	}
	_dialog = $('#role_prv_dialog').dialog({
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
	_form =$('#role_prv_form').form({
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
				console.info(data);
               }
    	}
	});
	
	window.role.openPrvDialog = function(title, row) {
		var url;
		_form.form('reset');
		
		url = "role/updatePrivileges.action?roleId="+row.id;
		$("#role_prv_form_name").html(row.name);
		$.ajax({
			url:'role/findPrivileges.action?roleId=' + row.id,
			dataType:'json',
			type:'post',
			success:function(rs){
				resourceCache=rs;
				initResources(rs);
			}
		});
				
		_form.form({url: url});
		_dialog.dialog('setTitle',title);
		_dialog.dialog('open');
	}
})
</script>
