<#import "common/spring.ftl" as s>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title><@s.message "app.name"/></title>
</head>
<body style="padding: 0;margin: 0;border: 0">
<iframe width="100%" height="100%" src="${base}/j_spring_security_check"
        style="display: block;padding: 0;margin: 0;border: 0"
        onscroll="no">
</iframe>
<form name="credentialForm" method="post">
    <input type="hidden" name="username">
</form>
</body>
</html>