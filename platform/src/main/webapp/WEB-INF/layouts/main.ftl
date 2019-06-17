<#import "../views/common/spring.ftl" as s>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><@s.message "app.name"/> - ${title}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <style type="text/css">

        #loading-mask {
            background-color: white;
            height: 100%;
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            z-index: 20000;
        }

        #loading {
            height: auto;
            position: absolute;
            left: 45%;
            top: 40%;
            padding: 2px;
            z-index: 20001;
            width: 500px;
        }

        #loading a {
            color: #225588;
        }

        #loading .loading-indicator {
            background: white;
            color: #444;
            font: bold 13px Helvetica, Arial, sans-serif;
            height: auto;
            margin: 0;
            padding: 10px;
        }

        #loading-msg {
            font-size: 10px;
            font-weight: normal;
        }

    </style>
</head>
<body class="wrap">
</body>
<div id="loading-mask"></div>
<div id="loading">
    <div class="loading-indicator">
        <img src="${base}/static/images/loading.gif" width="32" height="32"
             style="margin-right:8px;float:left;vertical-align:top;"/>BSH channel connector
        <br/><span id="loading-msg">页面资源加载中...</span>
    </div>
</div>
<link rel="stylesheet" href="${base}/static/css/extcss/resources/css/ext-all-gray.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${base}/static/css/adminSty.min.css" media="all"/>
<link rel="stylesheet" type="text/css" href="${base}/static/css/extcss/resources/css/xtheme-smart.css" media="all"/>
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/extjs/ux/grid/css/GridFilters.css"/>
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/extjs/ux/grid/css/RangeMenu.css"/>

<script type="text/javascript" src="${base}/static/i18n/messages_zh_CN.min.js"></script>
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = '页面UI组件加载中...';</script>
<script type="text/javascript" src="${base}/static/scripts/extjs/ext-all.js"></script>
<script type="text/javascript" src="${base}/static/scripts/extjs/lang.js"></script>
<script type="text/javascript" src="${base}/static/scripts/common/support_zh_CN.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/common/smartec-component.js"></script>
<script type="text/javascript" src="${base}/static/scripts/common/smartec-common.js"></script>
<script type="text/javascript" src="${base}/static/scripts/common/smartec-model.js"></script>
<script type="text/javascript">document.getElementById('loading-msg').innerHTML = '页面初始化中...';</script>
<script type="text/javascript" src="${base}/static/scripts/layout.min.js"></script>
${head}
<script type="text/javascript" src="${base}/static/scripts/common.min.js"></script>

<script type="text/javascript">
    Ext.Ajax.on('requestcomplete', function (conn, response, options) {
        if (options.url.indexOf(".") >= 0) {
            var responseTxt = Ext.decode(response.responseText)
            if (responseTxt) {
                var result = responseTxt.result;
                if ("session-invalidate" == result) {
                    console.info("session-invalidate")
                    window.location.href = path("/login");
                    return false;
                }
            }
        }
    });
</script>

</html>

