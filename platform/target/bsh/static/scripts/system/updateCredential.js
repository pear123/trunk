/**
 * Created with IntelliJ IDEA.
 * User: pang008
 * Date: 4/3/14
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */

(function (scope) {
    var _$Credential = scope.$Credential;

    var Initialize = {
        define: function () {
            Ext.apply(Ext.form.VTypes, {
                password: function (val, field) {
                    if (field.confirmTo) {
                        var pwd = Ext.getCmp(field.confirmTo);
                        return (val == pwd.getValue());
                    }
                    return true;
                },
                checkDuplicated: function (val, field) {
                    if (field.confirmTo) {
                        var pwd = Ext.getCmp(field.confirmTo);
                        return !(val == pwd.getValue());
                    }

                    return false;
                }
            });
        },
        create_index: function () {
            return Ext.create('Ext.panel.Panel', {
                title: '修改密码',
                width: 400,
                height: 500,
                modal: true,
                closable: false,
                border: true,
                frame: true,
                layout: 'fit',
                items: {
                    xtype: 'form',
                    id: 'credentialForm',
                    tbar: [
                        {
                            xtype: 'button',
                            text: '修改',
                            handler: function () {
                                var credentialForm = Ext.getCmp('credentialForm').getForm();
                                if (!credentialForm.isValid()) {
                                    Ext.Msg.alert(messages['ext.tips.tip'], '您的相关信息不符合要求，请重新输入');
                                    return;
                                }
                                var me = this;
                                me.setDisabled(true);
                                var userId = credentialForm.findField('username').getValue();
                                var oldPasswordWord = credentialForm.findField('oldPasswordWord').getValue();
                                var omsUserPassWord = credentialForm.findField('omsUserPassword').getValue();
                                var omsUserPassWordConfirm = credentialForm.findField('omsUserPassWordConfirm').getValue();

                                Ext.Ajax.request({
                                    url: path('/user/updatePassword'),
                                    params: {
                                        omsUserId: userId,
                                        oldPasswordWord: oldPasswordWord,
                                        omsUserPassWord: omsUserPassWord,
                                        omsUserPassWordConfirm: omsUserPassWordConfirm
                                    },
                                    success: function (json) {
                                        me.setDisabled(false);
                                        var data = Ext.decode(json.responseText);
                                        if (data.result === "NotEqualsOriginalPasscode") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '原密码输入错误');
                                            return;
                                        } else if (data.result === "NotEqualsNewPasscode") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '您输入的新旧密码不一致');
                                        } else if (data.result === "ExistInRecentRecord") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '您的新密码最近使用过，请更换其他密码');
                                        } else if (data.result == "success") {
                                            Ext.Msg.alert(messages['ext.tips.tip'], '修改密码成功，请重新登录', function (btn) {
                                                window.location.href = "${base}/bsh/j_spring_security_logout";
                                            });
                                        }
                                    },
                                    failure: function () {
                                        me.setDisabled(false);
                                        Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
                                    }
                                });


                            }
                        },
                        {
                            xtype: 'button',
                            text: '返回',
                            handler: function () {
                                window.location.href = path("/j_spring_security_logout");
                            }
                        }
                    ],
                    items: [
                        {
                            xtype: 'displayfield',
                            name: 'username',
                            id: 'username',
                            fieldLabel: '用&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名',
                            labelAlign: 'right',
                            value: '<font color="red">' + document.getElementById('username') ? document.getElementById('username').value : '' + '</font>'
                        },
                        {
                            xtype: 'password',
                            fieldLabel: '原&nbsp;&nbsp;密&nbsp;&nbsp;码<span style=color:red>*</span>',
                            xtype: 'textfield',
                            id: 'oldPasswordWord',
                            name: 'oldPasswordWord',
                            readOnlyCls: 'disable',
                            inputType: 'password',
                            allowBlank: false, // 不允许为空
                            blankText: "不能为空",
                            width: 300,
                            labelAlign: 'right',
                            listeners: {
                                blur: function (scope) {
                                    var val = scope.getValue();
                                    if (val != '') {
                                        var username = Ext.getCmp('username').getValue();
                                        Ext.Ajax.request({
                                            url: './validateOriginalPasscode',
                                            params: {
                                                username: username,
                                                passcode: val
                                            },
                                            success: function (response) {
                                                var text = response.responseText;
                                                var json = Ext.decode(text);
                                                if (json.success === false) {
                                                    scope.markInvalid('密码与用户名不匹配');
                                                } else {
                                                    scope.clearInvalid();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        }
                        ,
                        {
                            fieldLabel: '新密码<span style=color:red>*</span>',
                            xtype: 'textfield',
                            id: 'omsUserPassword',
                            name: 'omsUserPassword',
                            inputType: 'password',
                            allowBlank: false,
                            blankText: "不能为空",
                            labelAlign: 'right',
                            width: 300,
//                            regex: new RegExp("(?![^a-zA-Z]+$)(?!\D+$)(?![a-zA-Z0-9]+$).{6,16}"),
                            regex: new RegExp("^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,16}$"),
                            invalidText: '密码必须包含大小写字母、数字以及特殊字符且长度为6-16位',
                            maxLength: 16,
                            maxLengthText: '最大长度为16位',
                            minLength: 6,
                            minLengthText: '最小长度为6位'
//                            ,
//                            vtype: "checkDuplicated", //自定义的验证类型
//                            vtypeText: "新旧密码不可一致",
//                            confirmTo: "oldPasswordWord"//要比较的另外一个的组件的id
                        }
                        ,
                        {
                            fieldLabel: '确认密码<span style=color:red>*</span>',
                            xtype: 'textfield',
                            id: 'omsUserPassWordConfirm',
                            name: 'omsUserPassWordConfirm',
                            inputType: 'password',
                            labelAlign: 'right',
                            width: 300,
                            allowBlank: false,
                            blankText: "不能为空",
                            vtype: "password", //自定义的验证类型
                            vtypeText: "两次密码不一致",
                            confirmTo: "omsUserPassword"//要比较的另外一个的组件的id
                        }
                        ,
                        {
                            xtype: 'form',
                            layout: 'column',
                            width: 300,
                            border: false,
                            items: [
                                {
                                    columnWidth: .6,
                                    fieldLabel: '验证码',
                                    xtype: 'textfield',
                                    labelAlign: 'right',
                                    id: 'code',
                                    name: 'code',
                                    allowBlank: false,
                                    blankText: "不能为空",
                                    listeners: {
                                        blur: function (scope) {
                                            var val = scope.getValue();
                                            if (val !== '') {
                                                var field = this;
                                                Ext.Ajax.request({
                                                    url: './validateCode',
                                                    params: {
                                                        code: val
                                                    },
                                                    success: function (response) {
                                                        var text = response.responseText;
                                                        var json = Ext.decode(text);
                                                        if (json.success === false) {
                                                            scope.markInvalid('验证码有误');
                                                        } else {
                                                            scope.clearInvalid();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                },
                                {
                                    columnWidth: .4,
                                    xtype: 'label',
                                    html: '<img name="checkImg" id="vercodeimg" src="./captcha.jpg" style="cursor: pointer;margin-left: 10px" onclick="this.src=\'./captcha.jpg?update=new Date()\'" align="absmiddle">'
                                }
                            ]
                        }
                    ]
                }
            })
                ;
        }
    }

    function sand_box() {
        Initialize.define();

        var api = {
            createIndex: function () {
                return Initialize.create_index();
            }
        }
        return api;
    }

    scope.$Credential = sand_box();
})
    (this);

Ext.onReady(function () {
    Ext.getBody().setStyle({backgroundColor: 'lightgray'});
    var index = $Credential.createIndex();
    index.setHeight(Ext.getBody().getHeight() - 20);
    index.render(Ext.getBody());
    index.showAt((Ext.getBody().getWidth() - index.getWidth()) / 2, 0, true);
    window.onresize = function () {
        index.setWidth(400);
        index.setHeight(Ext.getBody().getHeight());
        index.doLayout();
    }
})
