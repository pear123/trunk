<#import "common/spring.ftl" as s>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <#--<script type="text/javascript">-->
        <#--var viewPort =Ext.create('Ext.panel.Panel', {-->
            <#--width: 200,-->
            <#--html: '<p style="text-align:center;padding-top:200px;font-weight:bolder;font-size:50px;color:blue;">WELCOME TO BSH CHANNEL CONNECTOR</p>'-->
        <#--});-->
        <#--Ext.onReady(function () {-->
            <#--Ext.getCmp('center').add(viewPort);-->
        <#--});-->
    <#--</script>-->
        <script type="text/javascript">
            var viewPort =Ext.create('Ext.panel.Panel', {
                // width: 200,
                //cls:'loginbgimage'
                //html: '<p style="text-align:center;padding-top:200px;font-weight:bolder;font-size:50px;color:blue;">WELCOME TO BSH CHANNEL CONNECTOR</p>',
              //  html:'<img src="${base}/static/images/BSH_Zentrale_2.jpg" style="width:100%,height:auto"></img>'
                //html:'<img src="${base}/static/images/BSH_Zentrale_2.jpg"></img>'
                <#--bodyStyle: {-->
                    <#--//background: '#ffc',-->
                    <#--background: 'url(${base}/static/images/BSH_Zentrale_2.jpg)  no-repeat ',-->
                   <#--// background: 'url(${base}/static/images/BSH_Zentrale_2.jpg) no-repeat center',-->
                    <#--//repeat:'repeat'-->
                    <#--padding: '10px'-->
                <#--},-->
                autoEl: {
                    tag: 'img',    //指定为img标签
                    src: '${base}/static/images/BSH_Zentrale_2.jpg'    //指定url路径 ,一般为相对路径
                }
            });
            Ext.onReady(function () {
                Ext.getCmp('center').add(viewPort);
            });
        </script>

</head>
<body>

</body>
</html>