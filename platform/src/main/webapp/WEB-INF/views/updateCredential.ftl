<#import "common/spring.ftl" as s>
<html>
<head>
    <title><@s.message "app.name"/> - 密码修改</title>
</head>

<body>
<input type="hidden" id="username" name="username" value="${username}">
<link rel="stylesheet" href="${base}/static/css/extcss/resources/css/ext-all-gray.css" type="text/css"/>
<script type="text/javascript" src="${base}/static/scripts/common/support_zh_CN.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/extjs/ext-all.js"></script>
<script type="text/javascript" src="${base}/static/scripts/common/smartec-model.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/system/updateCredential.min.js"></script>
</body>
</html>