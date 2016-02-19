<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<div id="camera_tree">
</div>
<script>
    (function ($) {
        var tab = util.getSelectedTab();
        var opts = tab.panel("options");
        var tabTitle = opts.title;
        var $camera_tree = $("#camera_tree", tab[0]);

        $camera_tree.tree({
            url: '/camera/camLstTree.action',
            onDblClick: function (node) {
                var title = node.text;
                var children = node.children;

                if(!children && !!node.attributes) {
                    if(node.attributes.status == 0) {
                        $.messager.show({
                            title:'消息',
                            msg:'该监控点已失效！',
                            timeout:2000
                        });
                        return false;
                    }
                    if (mainTabs.tabs('exists', title)) {
                        mainTabs.tabs('select', title)
                    } else {
                        mainTabs.tabs('add', {
                            title: title,
                            closable: true,
                            href: 'camera/camera.action?id=' + node.attributes.cameraId
                        });
                    }
                } else {
                    $.messager.show({
                        title:'消息',
                        msg:'该节点不是监控点！',
                        timeout:2000
                    });
                }
            },
            onLoadSuccess:function(node, data){
                showTips();
            }
        });

        function showTips() {
            var $tree_node_arr = $(".tree-title", tab[0]);
            var $arr = $.makeArray($tree_node_arr);
            $arr.map(function(item){
                if(item.innerHTML == tabTitle) {
                    $(item).css("background-color","#95B8E7")
                }
                $(item).tooltip({
                    position: 'right',
                    content: '<span style="color:#fff">双击监控点打开新标签</span>',
                    onShow: function () {
                        $(this).tooltip('tip').css({backgroundColor: '#666', borderColor: '#666'});
                    }
                });
            });
        }
    })(jQuery)
</script>