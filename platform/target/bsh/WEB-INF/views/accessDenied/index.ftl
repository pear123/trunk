<#import "../common/spring.ftl" as s>
<html>
    <head>
        <title>拒绝访问</title>
        <link rel="stylesheet" type="text/css" href="${base}/static/scripts/extjs/ux/grid/css/GridFilters.css"/>
        <link rel="stylesheet" type="text/css" href="${base}/static/scripts/extjs/ux/grid/css/RangeMenu.css"/>
        <script type="text/javascript" src="${base}/static/scripts/layout.min.js"></script>
        <script type="text/javascript">
            Ext.require([
                'Ext.panel.*'
            ]);

            Ext.onReady(function(){
               Ext.create('Ext.Window', {
                   id:'accessDeniedWindow',
                   title:'系统提示',
                   width:300,
                   height:200,
                   modal:true,
                   closeAction:'close',
                   html:'<div style="color:red;align:center;">无权限访问</div>'
               }).show();
            });
        </script>
    </head>
    <body>
        <div id="accessDenied"></div>
    </body>
</html>
