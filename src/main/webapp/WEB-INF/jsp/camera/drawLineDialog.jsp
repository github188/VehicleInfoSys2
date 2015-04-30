<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="draw_line">
</div>
<script type="text/javascript">
(function() {
	window.drawLine = function(id,path,type,paramStr,callback) {
		//临时插入,如果是目录进来就不画感兴趣区了
  if(path=="null"){
        $.post('task/startOffLineTask.action',{id:id},function(msg) {
        var tab = window.mainTabs.tabs('getSelected');
           $('#resource_task_grid',tab[0]).datagrid('load','task/list.action?type=2&dataSourceId='+id);
           $('#resource_grid',tab[0]).datagrid('reload');
          });
        return
   }


		  
		var param = null;
		if(paramStr) {
			param = eval('('+paramStr+')');
		}
		
		var x0 = 0,
		y0 = 0,
		x1 = 0,
		y1 = 0,
		cx = 0,
		cy = 0;
		
		var img = new Image();
		img.src = path;
		
		img.onload = function(){
			var pointCount = 0,
			fx = 0,
			fy = 0,
			drawRec = null,
			task = {
				id:id,
				x0:0,
				y0:0,
				x1:0,
				y1:0,
				width:0,
				height:0
			};
			
			cx = img.width;
			cy = img.height;
			
			var paper = Raphael('draw_line',cx, cy);
			var image = paper.image(path, 0, 0, cx, cy);
			
			if(param) {
				drawRec=paper.rect(param.x0, param.y0, param.x1-param.x0, param.y1-param.y0);
				drawRec.attr('stroke','red');
				$.extend(task,param);
			}	
			
			var dialog = $('#draw_line').dialog({
				title:'画感兴趣区域',
				//closed:true,
				modal:true,
				buttons:[{
					text:'确定',
					iconCls:'icon-ok',
					handler:function() {
						if(pointCount == 1) {
							$.messager.alert('提示','请画完感兴趣区域！');
							return;
						}
						
			    		if(type == 2) {
			    			$.post('task/startOffLineTask.action',task,function(msg) {
								var tab = window.mainTabs.tabs('getSelected');
				    			$('#resource_task_grid',tab[0]).datagrid('load','task/list.action?type=2&dataSourceId='+id);
				    			$('#resource_grid',tab[0]).datagrid('reload');
				    			dialog.dialog('close');
				    		});
			    		} else if(type == 1) {
			    			$.post('task/start.action',task,function(msg) {
				    			callback(msg);
				    			dialog.dialog('close');
			    			});
			    		}
					}
				},{
					text:'重画',
					iconCls:'icon-reload',
					handler:function() {
						pointCount = 0;
						drawRec.remove();
						drawRec = null;
						$.extend(task,{
									x0:0,
									x1:0,
									y0:0,
									y1:0,
									width:0,
									height:0
								});
					}
				},{
					text:'取消',
					iconCls:'icon-no',
					handler:function() {
						dialog.dialog('close');
					}
				}],
				onBeforeClose:function() {
					$(this).dialog('clear');
				}
			});
			
			image.click(function(e) {
				if(pointCount>1) {
					return;
				}
				param = null;
				var x = e.offsetX || e.layerX;
				var y = e.offsetY || e.layerY;
				if(pointCount == 0) {
					fx = x;
					fy = y;
				} else if (pointCount == 1) {
					fx = 0;
					fy = 0;
					x -= 1;
					y -= 1;
					task = {'id':id,'x0':x0,'y0':y0,'x1':x1,'y1':y1,'width':cx,'height':cy};
				}
				pointCount+=1;
			});
			image.mousemove(function(e) {
				if(pointCount == 1) {
					var offsetX = e.offsetX || e.layerX,
					offsetY = e.offsetY || e.layerY;
					x0 = Math.min(offsetX,fx);
					y0 = Math.min(offsetY,fy);
					x1 = Math.max(offsetX,fx);
					y1 = Math.max(offsetY,fy);
					var width = x1-x0-1;
					var height = y1-y0-1;
					
					if(width < 1 || height < 1) {
						return;
					}
					
					if(offsetX < fx && offsetY < fy) {
						x0 += 1;
						y0 += 1;
					}
					
					if(drawRec) {
						drawRec.attr('x',x0);
						drawRec.attr('y',y0);
						drawRec.attr('width',width);
						drawRec.attr('height',height);
						return;
					}
					
					drawRec=paper.rect(x0, y0, width, height);
					drawRec.attr('stroke','red');
				}
			});
		};
	}
})()
</script>