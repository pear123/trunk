
<#import "../common/spring.ftl" as s>
<html>
<head>
<head>
    <title>忘记密码</title>
    <link href="${base}/static/css/password.min.css" rel="stylesheet" type="text/css">
    <script type="text/javascript">
        function trim(str) {
            return String(str).replace(/\s+/g, '');
        }
        function validateForm(){
            var form = document.forms['updatePasswordForm'];
            var password = form.userPassword.value;
            var passwordConfirm = form.userPassWordConfirm.value;
            var reg=/^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,16}$/;
            if(reg.test(trim(password))){
            }else{
                alert("密码必须包含大小写字母、数字以及特殊字符且长度为6-16位");
                return false;
            }
            if (trim(password) === '') {
                alert("请输入新密码");
                return false;
            }
            if (trim(passwordConfirm) === '') {
                alert("请输入确认密码");
                return false;
            }
            if(trim(password)!=trim(passwordConfirm)){
                alert("两次密码必须一致");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<#--<div class="form">-->
<#--<form action="/bsh/user/updatePasswordBySendEmail" name="updatePasswordForm" method="post" >-->
    <#--<div class="div-phone">-->
        <#--<label for="phone">新&nbsp密&nbsp码</label><input type="password" id="password"  name="userPassword" class="infos" />-->
    <#--</div>-->
    <#--<div class="div-ranks">-->
        <#--<label for="ranks">确认密码</label><input type="password" id="passwordConfirm" name="userPassWordConfirm" class="infos"   />-->
    <#--</div>-->
    <#--<input   type="hidden" name="token"    value="${token}">-->
    <#--<input   type="hidden" name="email"    value="${email}">-->
    <#--<div class="div-conform">-->
        <#--<a href="javascript:formSubmit();" class="conform" >提交</a>-->
    <#--</div>-->
    <#--</form>-->
<#--</div>-->
 <#--<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">-->
     <#--<p>所有商标与品牌均为其各自拥有者的财产<a href="http://www.arvatosystems.com.cn" target="_blank" title="arvato Systems">www.arvatosystems.com.cn</a>-->
     <#--</p>-->
<#--</div>-->
<div>
    <div style="text-align:right;margin-right: 20px;"><a href="/bsh/login">登录</a></div>
    <div class="title">
        BSH邮件找回密码
    </div>
    <div class="form-mod">
        <div class="step">
            <div class="steper  current"><span>1.</span>重新设置密码</div>
            <div class="steper"><span>2.</span>重新设置密码成功</div>
        </div>
        <form id="updatePasswordForm" class="fm-v clearfix ui-form qt-pt20" action="/bsh/user/updatePasswordBySendEmail" method="post" onsubmit="return validateForm();" >
            <table width="400">
                <tr>
                    <td class="qt-pb10" width="100" style="text-align:right;">设置新密码：</td>
                    <td class="qt-pb10">
                        <input class="ui-input" id="userPassword"  name="userPassword" type="password" maxlength="128" tabindex="1" accesskey="n"  placeholder="请输入您的密码" /></td>
                </tr>
                <tr>
                    <td class="qt-pb32" width="100" style="text-align:right;">再次输入密码：</td>
                    <td class="qt-pb32">
                        <input class="ui-input" id="userPassWordConfirm" name="userPassWordConfirm" type="password" maxlength="128" tabindex="1" accesskey="n" placeholder="请再次输入您的密码"  /></td>
                <input   type="hidden" name="token"    value="${token}">
                <input   type="hidden" name="email"    value="${email}">
                </tr>
                <tr>
                    <td></td>
                    <td><input class="ui-button ui-button-large ui-button-red" value="确认" type="submit"/></td>
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