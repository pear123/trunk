<#import "common/spring.ftl" as s>
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" type="text/css" href="${base}/static/css/login.css" media="all"/>
<script type="text/javascript" src="${base}/static/scripts/extjs/ext-all.js"></script>
<script type="text/javascript" src="${base}/static/scripts/common/support_zh_CN.min.js"></script>
<link rel="stylesheet" href="${base}/static/css/extcss/resources/css/ext-all-gray.css" type="text/css"/>
<link rel="stylesheet" type="text/css" href="${base}/static/css/adminSty.min.css" media="all"/>
<link rel="stylesheet" type="text/css" href="${base}/static/css/extcss/resources/css/xtheme-smart.css" media="all"/>
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/extjs/ux/grid/css/GridFilters.css"/>
<link rel="stylesheet" type="text/css" href="${base}/static/scripts/extjs/ux/grid/css/RangeMenu.css"/>

<head>
    <title><@s.message "app.name"/></title>
    <script type="text/javascript">
        function trim(str) {
            return String(str).replace(/\s+/g, '');
        }
        function validateForm() {

            var form = document.forms['loginForm'];
            var name = form.name.value;
            var pass = form.password.value;
            var jcaptcha = form.captcha.value;

            if (trim(name) === '') {
                alert("请输入用户名");
                return false;
            }
            if (trim(pass) === '') {
                alert("请输入密码");
                return false;
            }
            if (trim(jcaptcha) === '') {
                alert("请输入验证码");
                return false;
            }

        <#--if(!nameTel.test(name)){-->
        <#--document.getElementById("msgDiv").innerHTML = "<@s.message code='jquery.validate.message.name' />";-->
        <#--return false;-->
        <#--}-->
        <#--if(!passTel.test(pass)){-->
        <#--document.getElementById("msgDiv").innerHTML = "<@s.message code='jquery.validate.message.password' />";-->
        <#--return false;-->
        <#--}-->


            return true;
        }

        function reloadImg() {
            var img = document.getElementById("vercodeimg");
            img.src = "${base}/captcha.jpg?update=" + Math.random();
        }

        function modifyCredential() {
            var doc = window.parent.document;
            var usernameField = document.getElementById("username");
            if (usernameField) {
                var credentialForm = doc.forms['credentialForm'];
                credentialForm.username.value = usernameField.value;
                credentialForm.action = '${base}/updateCredential';
                credentialForm.submit();
            }
        }

        function forgetPassword(){
                var sendEmailPanel=Ext.create('Ext.form.FormPanel', {
                    id: 'credentialForm1',
                    border : false,
                    defaults: {
                        margin:'45 0 0 40 '
                    },
                    items:[{
                        fieldLabel: '邮&nbsp;&nbsp;箱<span style=color:red>*</span>',
                        xtype: 'textfield',
                        id: 'userEmail',
                        name: 'userEmail',
                        allowBlank: false,
                        blankText: "不能为空",
                       // padding: '0 0 10 10',
                        width: 300,
                        /* maxLength: 16,
                         maxLengthText: '最大长度不能超过16个字符',*/
                        minLength: 7,
                        minLengthText: '最小长度不能小于7个字符',
//                            regex: new RegExp("(?![^a-zA-Z]+$)(?!\D+$)(?![a-zA-Z0-9]+$).{6,16}"),
                        regex: new RegExp("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$"),
                        invalidText:'请使用正确的邮箱格式!'
                    }
                    ]
                    });
                Ext.create('Ext.window.Window', {
                    title: '忘记密码',
                    id: 'winForgetPassword',
                    height: 150,
                    width: 400,
                    layout: 'fit',
                    plain: true,
                    modal: true,
                    maximizable: false,
                    draggable: false,
                   // closable: false,
                    resizable: false,
                    items: sendEmailPanel,
                    buttons: [
                        {
                            text: '发送',
                            margin:'0 5 0 0',
                            handler: function () {
                                var credentialForm1 = Ext.getCmp('credentialForm1').getForm();
                                if (!credentialForm1.isValid()) {
                                    Ext.Msg.alert(messages['ext.tips.tip'], '您的相关信息不符合要求，请重新输入');
                                    return;
                                }
                                var userEmail = credentialForm1.findField('userEmail').getValue();
                                Ext.Ajax.request({
                                    url: path('/user/validateAndSendEmail'),
                                    params: {
                                        userEmail: userEmail
                                    },
                                    success: function (json) {
                                        var data = Ext.decode(json.responseText);
                                        if (data.result == "EmailIsEmpty") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '邮箱不能为空');
                                        } else if (data.result === "IlegalEmail") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '邮箱不存在');
                                        }  else if (data.result == "success") {
                                            Ext.Msg.alert(messages['ext.tips.tip'], '邮件已发送', function (btn) {
                                            <#--window.location.href = "${base}/bsh/j_spring_security_logout";-->
                                                Ext.getCmp('winForgetPassword').close();
                                            });
                                        }
                                    },
                                    failure: function () {
                                        Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
                                    },
                                    waitTitle:Ext.MessageBox.wait('正在发送，请等待...', '提示')
                                });
                            }
                        },
                        {
                            text: '取消',
                            margin:'0 47 0 0',
                            handler: function () {
                                Ext.getCmp('winForgetPassword').close();
                            }
                        }
                    ]
                }).show();
        }
    </script>
</head>
<body>
<!--loginOuter start-->
<div class="rel loginOuter">
    <div class="topBlk"></div>
    <!--/topBlk-->

    <!--loginWrap start-->
    <div class="rel loginWrap">
        <b class="abs topLock"></b>
        <div class="abs oz_ bd">
            <dl class="l">
                <dd class="blank20"></dd>
                <#--<dt><a href="login.ftl#" class="ti_">smart admin</a></dt>-->
                <dt><span style="font-size: 25px;color: #0056b1">B/S/H </span><span style="font-size: 19px;color: #B2B2B2">CHANNEL CONNECTOR</span></dt>
                <dd class="blank5"></dd>
                <#--<dd>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore-->
                    <#--et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut-->
                    <#--aliq-->
                <#--</dd>-->
            </dl>
            <div class="l midLine"></div>
            <div class="r">
                <p>Please use your login ID and Password.</p>

                <p class="blank20"></p>

                <form action='${base}/j_spring_security_check' name="loginForm" method='post' class="tvcell farial"
                      onsubmit="return validateForm();">
				<span class="w220">
				<p><input type="text" name="j_username" class="c9 userName" id="name" TABINDEX="1"/></p>
				<p class="blank10"></p>
				<p><input type="password" name="j_password" class="c9 password" id="password" TABINDEX="2"/></p>
                <p class="blank10"></p>

				</span>
                    <span class="bracket">}</span>
                    <span class="cWhite"><button type="submit">登录</button></span>
                <#--增加验证码       start    -->
                    <span style="w220">
                    <p>
                        <input class="" id="captcha" TABINDEX="3" name="j_captcha" size="8" maxlength="4"
                              style="width: 40px;"/>
                        <img name="checkImg" id="vercodeimg" src="${base}/captcha.jpg"
                             style="cursor: pointer;margin-left: 10px" align="absmiddle"
                             onclick="this.src='${base}/captcha.jpg?update=' + Math.random()">
                        <a href="javascript:reloadImg()">换个图片?</a>
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><a href="javascript:forgetPassword()">忘记密码</a>
                    </p>
                     </span>
                    <#--增加验证码       end    -->
                    <p class="blank20"></p>

                    <#--<p class="vm_">-->
                        <#--<input type="checkbox" id="chk_remember" name="_spring_security_remember_me"/>-->
                        <#--<label for="chk_remember">记住密码</label>-->
                    <#--&lt;#&ndash;<em><a href="login.ftl#" class="tdu forgetPw">忘记密码</a></em>&ndash;&gt;-->
                    <#--</p>-->

                    <p class="blank5"></p>

                    <div id="msgDiv">
                    <#if msg?exists>
                        <p class="icn infoarea errors">
                        <span>
                            <#if isNeedTranslate?exists>
                            ${msg}
                            <#else>
                                <@s.message code="${msg}" />
                                <#if username?exists>
                                    <input type="hidden" id="username" name="username" value="${username}">
                                </#if>
                            </#if>
                        </span>
                        </p>
                    </#if>
                    </div>
                </form>
            </div>
            <!--/r-->
        </div>
        <!--/hd-->
    </div>
    <!--loginWrap end-->

    <p class="blank10"></p>

    <!--loginFooter start-->
    <div class="abs loginFooter">
        <#--<h1 class="l mr10"><a href="login.ftl#" class="ti_">Smart login</a></h1>-->
        <h1 class="l mr10" style="width: 131px;height: 43px;background-image: url(${base}/static/images/BSH_Logo_HR%20.png)"></h1>
        <p>BSH Copyright © 2015.</p>

        <p>所有商标与品牌均为其各自拥有者的财产
        </p>
            <#--<a href="http://www.bosch.com.cn" target="_blank" title="BSH Systems">www.bosch.com.cn</a>-->
    </div>
    <!--loginFooter end-->
</div>
<!--loginOuter end-->
</body>
</html>