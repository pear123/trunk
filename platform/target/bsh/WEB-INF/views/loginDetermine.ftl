<#import "common/spring.ftl" as s>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>跳转页面</title>
    <script type="text/javascript">
       function forward() {
            window.parent.location.href = "${base}/welcome";
        }
        forward();

    </script>
</head>
<body>

</body>
</html>