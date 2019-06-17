/*
 * Copyright (c) 2000-2011 arvato systems (Shanghai) $Id: order-grid.js
 * 2011-06-01 14:15:04 by Allen $
 */

function converterOrderStatus(value, p, record) {
    return messages['com.arvato.smartec.Constants.OrderStatus.' + value + '.message'];
}

function converterPayStatus(value, p, record) {
    return messages['com.arvato.smartec.Constants.PayStatus.' + value + '.message'];
}


Ext.define('Smartec.user.UserGrid', {
    alias: 'widget.usergrid',
    extend: 'Smartec.grid.Panel',
    model: 'User',
    columns: [
        {
            text: 'id',
            dataIndex: 'omsUserSysId',
            hidden: true
        },
        {
            text: '用户名',
            width: 440,
            sortable: false,
            dataIndex: 'omsUserId'
        },
///*	{
//		text : '用户密码',
//		width : 440,
//		sortable : false,
//		dataIndex : 'omsUserPassword'
//	},*/
        {
            text: '用户状态',
            width: 430,
            sortable: false,
            dataIndex: 'omsUserStatus'
        }
    ],

    // 添加用户
    addUserHandle: function () {
        var grid = this.up('gridpanel');
        if (Ext.getCmp('addUserWindow')) {
            Ext.getCmp('addUserWindow').show();
        } else {
            Ext.create('Ext.Window', {
                title: '添加用户',
                width: 700,
                height: 400,
                layout: 'fit',
                draggable: false,
                modal: true,
                id: 'addUserWindow',
                listeners: {
                    hide: function (win) {
                        var form = Ext.getCmp('allform').getForm();
                        form.clearInvalid();
                        form.reset();
                    }
                },
                closeAction: 'hide',
                items: [
                    allform = new Ext.form.FormPanel({
                        id: 'allform',
                        bodyPadding: 20,
                        autoScroll: true,
                        items: [
                            {
                                fieldLabel: '用户名<span style=color:red>*</span>',
                                xtype: 'textfield',
                                id: 'omsUserId',
                                name: 'omsUserId',
                                readOnlyCls: 'disable',
                                padding: '0 0 10 0',
                                allowBlank: false, // 不允许为空
                                blankText: "不能为空",
                                width: 300,
                                maxLength: 50,
                                maxLengthText: '最大长度不能超过50个字符!'
                            },
                            {
                                fieldLabel: '电子邮件<span style=color:red>*</span>',
                                xtype: 'textfield',
                                id: 'omsUserEmail',
                                name: 'omsUserEmail',
                                padding: '0 0 10 0',
                                allowBlank: false, // 不允许为空
                                blankText: "不能为空",
                                width: 300,
                                maxLength: 50,
                                maxLengthText: '最大长度不能超过50个字符!',
                                regex: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                                regexText: "邮箱格式错误"
                            },
                            {
                                fieldLabel: '用户密码<span style=color:red>*</span>',
                                xtype: 'textfield',
                                id: 'omsUserPassword',
                                name: 'omsUserPassword',
                                inputType: 'password',
                                allowBlank: false,
                                blankText: "不能为空",
                                width: 300,
                                maxLength: 8,
                                maxLengthText: '最大长度不能超过8个字符!',
                                minLength: 8,
                                minLengthText: '最小长度不能小于8个字符!'
                            },
                            {
                                fieldLabel: '确认密码<span style=color:red>*</span>',
                                xtype: 'textfield',
                                id: 'omsUserPassWordConfirm',
                                name: 'omsUserPassWordConfirm',
                                inputType: 'password',
                                width: 300,
                                vtype: "password",//自定义的验证类型
                                vtypeText: "两次密码不一致！",
                                confirmTo: "omsUserPassword"//要比较的另外一个的组件的id
                            },
                            {
                                fieldLabel: '角色(可多选)',
                                xtype: 'combo',
                                id: 'omsUserRole',
                                name: 'omsUserRole',
                                store: userRole,
                                width: 500,
                                displayField: 'omsRoleName',
                                valueField: 'omsRoleSysId',
                                editable: false,
                                padding: '0 0 10 0',
                                multiSelect: true,
                                autoShow: true

                            },
//					{
//						fieldLabel: '商店(可多选)',
//	                   	xtype: 'combo',
//		                id: 'omsUserStore',
//		                name: 'omsUserStore',
//		                store:userStore,
//		                width:500,
//		                displayField : 'omsStoreName',
//						valueField : 'omsStoreSysId',
//		                editable: false,
//		                padding:'0 0 10 0',
//		                multiSelect: true,
//		                autoShow: true
//					},
                            {
                                fieldLabel: '昵称',
                                xtype: 'textfield',
                                id: 'omsUserName',
                                name: 'omsUserName',
                                padding: '0 0 10 0',
                                width: 300,
                                maxLength: 50,
                                maxLengthText: '最大长度不能超过50个字符!'
                            },
                            {
                                fieldLabel: '备注',
                                xtype: 'textarea',
                                id: 'omsUserRemark',
                                name: 'omsUserRemark',
                                width: 600,
                                height: 100,
                                maxLength: 300,
                                maxLengthText: '客户描述的长度不能超过300字符',
                                padding: '0 0 10 0'
                            },
                            {
                                xtype: 'displayfield',
                                hidden: true,
                                id: 'statusValue',
                                name: 'omsUserStatus'
                            },
                            {
                                xtype: 'fieldcontainer',
                                fieldLabel: '状态',
                                width: 300,
                                defaultType: 'radiofield',
                                padding: '0 0 10 0',
                                defaults: {
                                    flex: 1
                                },
                                layout: 'hbox',
                                items: [
                                    {
                                        boxLabel: '启用',
                                        name: 'size',
                                        inputValue: '1',
                                        id: 'userStatusRadio1',
                                        checked: true
                                    },
                                    {
                                        boxLabel: '禁用',
                                        name: 'size',
                                        inputValue: '0',
                                        id: 'userStatusRadio2'
                                    }
                                ]
                            },
                            {
                                xtype: 'button',
                                text: '保存',
                                iconCls: 'icon-ok',
                                handler: grid.saveUser
                            }
                        ]
                    })]
            }).show();
        }
    },
    // 保存添加用户
    saveUser: function () {
        if (allform.form.isValid()) {
            var userId = Ext.getCmp('omsUserId').getValue();
            var userEmail = Ext.getCmp('omsUserEmail').getValue();
            var userPassword = Ext.getCmp('omsUserPassword').getValue();
//			var userStore = Ext.getCmp('omsUserStore').getValue();
            var userRole = Ext.getCmp('omsUserRole').getValue();
            var userName = Ext.getCmp('omsUserName').getValue();
            var userRemark = Ext.getCmp('omsUserRemark').getValue();
            var userStatus = '';
            if (Ext.getCmp('userStatusRadio1').getValue())
                userStatus = Ext.getCmp('userStatusRadio1').inputValue;
            else
                userStatus = Ext.getCmp('userStatusRadio2').inputValue;
            Ext.Ajax.request({
                url: path('/user/saveUser.do'),
                params: {
                    omsUserId: userId,
                    omsUserEmail: userEmail,
                    omsUserPassword: userPassword,
                    omsUserStore: '',
                    omsUserRole: userRole,
                    userName: userName,
                    omsUserName: userRemark,
                    size: userStatus
                },
                success: function (json) {
                    var data = Ext.decode(json.responseText);
                    if (data.result == "UserIdError") {
                        Ext.Msg.alert(messages['ext.tips.error'], '用户id已存在！');
                        return;
                    } else if (data.result == "UserEmailError") {
                        Ext.Msg.alert(messages['ext.tips.error'], '邮箱已存在已存在！');
                        return;
                    } else if (data.success) {
                        Ext.Msg.alert(messages['ext.tips.tip'], '添加成功！', function (btn) {
                            Ext.getCmp('addUserWindow').hide();
                            var grid = Ext.getCmp('UserGridId');
                            grid.getStore().load();
                        });
                    }
                },
                failure: function () {
                    Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
                }
            });
        } else {
            Ext.Msg.alert(messages['ext.tips.error'], '填写的内容有误！');
        }
    },

    //修改用户
    editUserHandle: function () {
        var grid = this.up('gridpanel');
        var sm = grid.getSelectionModel();
        record = sm.getSelection()[0];// 获取选中的条目的第一条
        var sysId = record.get("omsUserSysId");
        var code = record.get("omsUserSysId");
        var modifyCheckBox = false;

        if (Ext.getCmp('editUserWindow')) {
            Ext.getCmp('editUserWindow').show();
        } else {
            Ext.create('Ext.Window', {
                title: '修改用户',
                width: 800,
                height: 300,
                resizable: true,
                maximizable: true,
                autoScroll: true,
                modal: true,
                constrain: true,
                draggable: false,
//					closeAction : 'hide',
                id: 'editUserWindow',
                items: [
                    orderDetailTabPanel = Ext.create('Ext.panel.Panel', {
                        autoScroll: true,
                        items: [Ext.create('Ext.panel.Panel', {
                            layout: 'fit',
                            items: [Ext.create("Ext.form.Panel", {
                                url: path("/user/saveUser.do?cbModifyPass=" + modifyCheckBox + "&omsUserSysId=" + sysId),
                                id: 'updateUser',
                                padding: '0 0 0 20',
                                reader: new Ext.data.JsonReader({
                                    root: 'result',
                                    model: 'User',
                                    successProperty: 'success'
                                }),
                                bodyPadding: 20,
                                border: 0,
                                items: [
                                    {
                                        fieldLabel: '用户名<span style=color:red>*</span>',
                                        xtype: 'textfield',
                                        id: 'omsUserId' + sysId,
                                        name: 'omsUserId',
                                        readOnly: true,
                                        readOnlyCls: 'disable',
                                        padding: '0 0 10 0',
                                        allowBlank: false, // 不允许为空
                                        blankText: "不能为空",
                                        width: 300,
                                        maxLength: 50,
                                        maxLengthText: '最大长度不能超过50个字符!'
                                    },
                                    {
                                        fieldLabel: '电子邮件<span style=color:red>*</span>',
                                        xtype: 'textfield',
                                        id: 'omsUserEmail' + sysId,
                                        name: 'omsUserEmail',
                                        padding: '0 0 10 0',
                                        allowBlank: false, // 不允许为空
                                        blankText: "不能为空",
                                        width: 300,
                                        maxLength: 50,
                                        maxLengthText: '最大长度不能超过50个字符!',
                                        regex: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
                                        regexText: "邮箱格式错误"
                                    },
                                    {
                                        fieldLabel: '操作',
                                        xtype: 'checkbox',
                                        hideLable: true,
                                        name: 'operatPwd',
                                        id: 'operatPwd',
                                        boxLabel: '同时修改密码',
                                        listeners: {
                                            change: function () {
                                                if (this.checked) {
                                                    Ext.getCmp('omsUserPassword' + sysId).show();
                                                    Ext.getCmp('omsUserPassWordConfirm' + sysId).show();
                                                    modifyCheckBox = true;
                                                } else {
                                                    Ext.getCmp('omsUserPassword' + sysId).hide();
                                                    Ext.getCmp('omsUserPassWordConfirm' + sysId).hide();
                                                    modifyCheckBox = false;
                                                }
                                            }
                                        }
                                    },
                                    {
                                        fieldLabel: '用户密码<span style=color:red>*</span>',
                                        xtype: 'textfield',
                                        id: 'omsUserPassword' + sysId,
                                        name: 'omsUserPassword',
                                        inputType: 'password',
                                        hidden: true,
                                        allowBlank: false,
                                        blankText: "不能为空",
                                        width: 300,
                                        maxLength: 8,
                                        maxLengthText: '密码长度为8位!',
                                        minLength: 8,
                                        minLengthText: '密码长度为8位!'
                                    },
                                    {
                                        fieldLabel: '确认密码<span style=color:red>*</span>',
                                        xtype: 'textfield',
                                        id: 'omsUserPassWordConfirm' + sysId,
                                        name: 'omsUserPassWordConfirm',
                                        inputType: 'password',
                                        hidden: true,
                                        width: 300,
                                        vtype: "password",//自定义的验证类型
                                        vtypeText: "两次密码不一致！",
                                        confirmTo: "omsUserPassword" + sysId//要比较的另外一个的组件的id
                                    },
                                    {
                                        xtype: 'displayfield',
                                        hidden: true,
                                        id: 'omsUserRoleValue' + sysId,
                                        name: 'omsUserRoles'
                                    },
                                    {
                                        fieldLabel: '角色(可多选)',
                                        xtype: 'combo',
                                        id: 'omsUserRole' + sysId,
                                        name: 'omsUserRole',
                                        store: userRole,
                                        width: 500,
                                        displayField: 'omsRoleName',
                                        valueField: 'omsRoleSysId',
                                        editable: false,
                                        padding: '0 0 10 0',
                                        multiSelect: true,
                                        queryMode: 'local'
                                    },
//										{
//						                   	xtype: 'displayfield',
//						                   	hidden:true,
//							                id: 'omsUserStoreValue'+sysId,
//							                name: 'omsUserStores'
//						         		},
//										{
//											fieldLabel: '商店(可多选)',
//						                   	xtype: 'combo',
//							                id: 'omsUserStore'+sysId,
//							                name: 'omsUserStore',
//							                store:userStore,
//							                width:500,
//							                displayField : 'omsStoreName',
//											valueField : 'omsStoreSysId',
//							                editable: false,
//							                padding:'0 0 10 0',
//							                multiSelect: true,
//							                queryMode: 'local',
//							                autoShow: true
//										},
                                    {
                                        fieldLabel: '昵称',
                                        xtype: 'textfield',
                                        id: 'omsUserName' + sysId,
                                        name: 'omsUserName',
                                        padding: '0 0 10 0',
                                        width: 300,
                                        maxLength: 50,
                                        maxLengthText: '最大长度不能超过50个字符!'
                                    },
                                    {
                                        fieldLabel: '备注',
                                        xtype: 'textarea',
                                        id: 'omsUserRemark' + sysId,
                                        name: 'omsUserRemark',
                                        width: 600,
                                        height: 100,
                                        maxLength: 300,
                                        maxLengthText: '客户描述的长度不能超过300字符',
                                        padding: '0 0 10 0',
                                        name: 'omsUserRemark'
                                    },
                                    {
                                        xtype: 'displayfield',
                                        hidden: true,
                                        id: 'statusValue' + sysId,
                                        name: 'omsCustomerStatus'
                                    },
                                    {
                                        xtype: 'fieldcontainer',
                                        fieldLabel: '状态',
                                        width: 300,
                                        defaultType: 'radiofield',
                                        padding: '0 0 10 0',
                                        defaults: {
                                            flex: 1
                                        },
                                        layout: 'hbox',
                                        items: [
                                            {
                                                boxLabel: '启用',
                                                name: 'size' + sysId,
                                                inputValue: '1',
                                                id: 'userStatusRadio1' + sysId,
                                                checked: true
                                            },
                                            {
                                                boxLabel: '禁用',
                                                name: 'size' + sysId,
                                                inputValue: '0',
                                                id: 'userStatusRadio2' + sysId
                                            }
                                        ]
                                    },
                                    {
                                        xtype: 'fieldcontainer',
                                        layout: 'hbox',
                                        items: [
                                            {
                                                xtype: 'button',
                                                text: '保存',
                                                iconCls: 'icon-ok',
                                                handler: function () {
                                                    if (this.up('panel').getForm().isValid()) {
                                                        this.up('panel').getForm().submit({
                                                            success: function (json, action) {
                                                                var data = action.result.result;
                                                                if (data.result == "UserIdError") {
                                                                    Ext.Msg.alert(messages['ext.tips.error'], '用户id已存在！');
                                                                    return;
                                                                } else if (data.result == "UserEmailError") {
                                                                    Ext.Msg.alert(messages['ext.tips.error'], '邮箱已存在！');
                                                                    return;
                                                                } else {
                                                                    Ext.Msg.alert(messages['ext.tips.tip'], '修改成功！');
                                                                    grid.getStore().load();
                                                                    Ext.getCmp('editUserWindow').close();
                                                                }
                                                            },
                                                            failure: function () {
                                                                Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
                                                            }
                                                        });
                                                    } else {
                                                        Ext.Msg.alert(messages['ext.tips.error'], '填写的内容有误!');
                                                    }
                                                }
                                            }
                                        ]
                                    }
                                ]
                            })]
                        })]
                    })
                ]
            }).show();

            // 进入用户详细页
            Ext.getCmp('updateUser').getForm().load({
                url: path("/user/form.json?omsUserSysId=" + sysId),
                success: function (form, action) {
                    //给下拉框赋值
//						var omsUserStore = Ext.getCmp('omsUserStoreValue'+sysId).getValue();
//						var ous = omsUserStore.split(",");
//						var omsUserStoreDisplay = Ext.getCmp('omsUserStore'+sysId);
//						omsUserStoreDisplay.setValue(ous);

                    var omsUserRole = Ext.getCmp('omsUserRoleValue' + sysId).getValue();
                    var our = omsUserRole.split(",");
                    var omsUserRoleDisplay = Ext.getCmp('omsUserRole' + sysId);
                    omsUserRoleDisplay.setValue(our);
                    //给单选框赋值
                    var status = action.result.data.omsUserStatus;
                    var radio1 = Ext.getCmp('userStatusRadio1' + sysId);
                    var radio2 = Ext.getCmp('userStatusRadio2' + sysId);
                    if (status == '1')
                        radio1.setValue(true);
                    else
                        radio2.setValue(true);
                }
            });
        }
    },

    initComponent: function () {
        orderGridMe = this;
        var me = this;
        me.topDockedItems = [
            {
                xtype: 'button',
                itemId: 'btnAdd',
                height: 24,
                text: '添加用户',
                iconCls: 'icon-new',
                handler: me.addUserHandle
            },
            {
                xtype: 'button',
                itemId: 'btnEdit',
                disabled: true,
                height: 24,
                text: '查看用户明细',
                iconCls: 'icon-edit',
                handler: me.editUserHandle
            }
        ];
        me.callParent();
        me.getSelectionModel().on('selectionchange', function (thiz, selections) {
            me.down('#btnEdit').setDisabled(!(selections.length == 1));
        });

        //自定义验证两个密码是否一致
        Ext.apply(Ext.form.VTypes, {
            password: function (val, field) {//val指这里的文本框值，field指这个文本框组件
                if (field.confirmTo) {//confirmTo是自定义的配置参数，一般用来保存另外的组件的id值
                    var pwd = Ext.getCmp(field.confirmTo);//取得confirmTo的那个id的值
                    return (val == pwd.getValue());
                }
                return true;
            }
        });
    }
});

Ext.onReady(function () {
    Ext.create('Smartec.user.UserGrid', {
        id: 'UserGridId'
    });
});