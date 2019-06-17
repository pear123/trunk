
<#import "../common/spring.ftl" as s>
<html>
<head>
<head>
    <title>忘记密码</title>
    <link href="${base}/static/css/password.min.css" rel="stylesheet" type="text/css">
    <script language="javascript" type="text/javascript">
        var i = 10;
        var intervalid;
        intervalid = setInterval("fun()", 1000);
        function fun() {
            if (i == 0) {
                window.location.href = "/bsh/login";
                clearInterval(intervalid);
            }
            document.getElementById("mes").innerHTML = i;
            i--;
        }
    </script>
</head>
<body>
<#--<div class="form">-->
    <#--<p>${info},将在 <span id="mes">5</span> 秒钟后返回首页！点击<a href="/bsh/login">这里</a>直接跳回。</p>-->
<#--</div>-->
<div>
    <div class="title">
        BSH邮件找回密码
    </div>
    <div class="form-mod">
        <div class="step">
            <div class="steper"><span>1.</span>重新设置密码</div>
            <div class="steper current"><span>2.</span>重新设置密码成功</div>
        </div>
        <form class="fm-v clearfix ui-form qt-pt20" >
            <table width="400">
                <tr>
                    <td></td>
                    <td class="qt-pb10">
                    </td>
                </tr>
                <tr>
                    <td ></td>
                    <td class="qt-pb32">
                    ${info},将在 <span id="mes">10</span> 秒钟后返回首页！点击<a href="/bsh/login">这里</a>直接跳回。
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </form>
    </div>
</div>
</div>
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
    <p>所有商标与品牌均为其各自拥有者的财产<a href="http://www.arvatosystems.com.cn" target="_blank" title="arvato Systems">www.arvatosystems.com.cn</a>
    </p>
</div>
</body>
</html>
