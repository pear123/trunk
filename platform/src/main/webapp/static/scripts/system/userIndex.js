(function (scope) {
    var _$User = scope.$User;
    var Initialize = {
        define: function () {
            Ext.define('Oms.User.Index', {
                id: 'userGridPanel',
                alias: 'widget.usergrid',
                extend: 'Smartec.grid.Panel',
                model: 'User',
                columns: [
                    {xtype: 'rownumberer', text: '序号', width: 40, sortable: false},
                    {
                        text: 'id',
                        dataIndex: 'userSysId',
                        hidden: true
                    },
                    {
                        text: '用户名',
                        width: 100,
                        sortable: false,
                        dataIndex: 'userId'
                    },
                    {
                        text: '电子邮件',
                        width: 200,
                        sortable: false,
                        dataIndex: 'userEmail'
                    },
                    {
                        text: '昵称',
                        width: 200,
                        sortable: false,
                        dataIndex: 'userName'
                    },
                    {
                        text: '角色',
                        width: 200,
                        sortable: false,
                        dataIndex: 'userRoles'
                    },
                    {
                        text: '状态',
                        width: 100,
                        sortable: false,
                        dataIndex: 'userStatus',
                        renderer: function (v) {
                            return v == "1" ? "启用" : "禁用";
                        }
                    }
                ],

                // 添加用户
                addUserHandle: function (scope) {
                    return function () {
                        var grid = scope.up('gridpanel');
                        if (Ext.getCmp('addUserWindow')) {
                            Ext.getCmp('addUserWindow').show(this);
                        } else {
                            Ext.create('Ext.Window', {
                                title: '添加用户',
                                width: 700,
                                height: 400,
                                layout: 'fit',
                                draggable: false,
                                maximizable: true,
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
                                                id: 'userId',
                                                name: 'userId',
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
                                                id: 'userEmail',
                                                name: 'userEmail',
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
                                                id: 'userPassword',
                                                name: 'userPassword',
                                                inputType: 'password',
                                                allowBlank: false,
                                                blankText: "不能为空",
                                                width: 300,
                                                maxLength: 16,
                                                maxLengthText: '最大长度不能超过16个字符',
                                                minLength: 6,
                                                minLengthText: '最小长度不能小于6个字符',
//                                              regex:new RegExp("(?![^a-zA-Z]+$)(?!\D+$)(?![a-zA-Z0-9]+$).{6,16}"),
                                                regex: new RegExp("^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,16}$"),
                                                invalidText: '密码必须包含大小写字母、数字以及特殊字符且长度为6-16位'
                                            },
                                            {
                                                fieldLabel: '确认密码<span style=color:red>*</span>',
                                                xtype: 'textfield',
                                                id: 'userPassWordConfirm',
                                                name: 'userPassWordConfirm',
                                                inputType: 'password',
                                                allowBlank: false,
                                                width: 300,
                                                vtype: "password", //自定义的验证类型
                                                vtypeText: "两次密码不一致",
                                                confirmTo: "userPassword"//要比较的另外一个的组件的id
                                            },
                                            {
                                                fieldLabel: '角色(可多选)',
                                                xtype: 'combo',
                                                id: 'userRole',
                                                name: 'userRole',
                                                store: Ext.create("Role"),
                                                width: 500,
                                                displayField: 'ccRoleName',
                                                valueField: 'ccRoleSysId',
                                                editable: false,
                                                padding: '0 0 10 0',
                                                multiSelect: true,
                                                autoShow: true

                                            },
                                            {
                                                fieldLabel: '昵称',
                                                xtype: 'textfield',
                                                id: 'userName',
                                                name: 'userName',
                                                padding: '0 0 10 0',
                                                width: 300,
                                                maxLength: 50,
                                                maxLengthText: '最大长度不能超过50个字符!'
                                            },
                                            {
                                                fieldLabel: '备注',
                                                xtype: 'textarea',
                                                id: 'userRemark',
                                                name: 'userRemark',
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
                                                name: 'userStatus'
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
                                                handler: scope.saveUser
                                            }
                                        ]
                                    })]
                            }).show(this);
                        }
                    }
                },
                // 保存添加用户
                saveUser: function () {
                    if (allform.form.isValid()) {
                        var grid = this.up('gridpanel');
                        var userId = Ext.getCmp('userId').getValue();
                        var userEmail = Ext.getCmp('userEmail').getValue();
                        var userPassword = Ext.getCmp('userPassword').getValue();
                        //var userStore = Ext.getCmp('omsUserStore').getValue();
                        var userRole = Ext.getCmp('userRole').getValue();
                        var userName = Ext.getCmp('userName').getValue();
                        var userRemark = Ext.getCmp('userRemark').getValue();
                        var userStatus = '';
                        if (Ext.getCmp('userStatusRadio1').getValue())
                            userStatus = Ext.getCmp('userStatusRadio1').inputValue;
                        else
                            userStatus = Ext.getCmp('userStatusRadio2').inputValue;
                        Ext.Ajax.request({
                            url: path('/user/saveUser.do'),
                            params: {
                                userId: userId,
                                userEmail: userEmail,
                                userPassword: userPassword,
//                                userStore: '',
                                userRole: userRole,
                                userName: userName,
                                userRemark: userRemark,
                                size: userStatus
                            },
                            success: function (json) {
                                var data = Ext.decode(json.responseText);
                                if (data.result == "UserIdError") {
                                    Ext.Msg.alert(messages['ext.tips.error'], '用户id已存在！');
                                    return;
                                } else if (data.result == "UserEmailError") {
                                    Ext.Msg.alert(messages['ext.tips.error'], '邮箱已存在！');
                                    return;
                                } else if (data.success) {
                                    Ext.Msg.alert(messages['ext.tips.tip'], '添加成功！', function (btn) {
                                        Ext.getCmp('addUserWindow').hide();
                                        Ext.getCmp('userGridPanel').store.loadPage(1);
//                                      var grid = Ext.getCmp('UserGridId');
//                                      grid.getStore().load();
//                                      grid.getStore().load();
//                                      Ext.getCmp('editUserWindow').close();
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

                refreshUser: function () {
                    return function () {
                        var self = this;
                        self.setDisabled(true);
                        Ext.Ajax.request({
                            url: path('/userRole/refresh'),
                            success: function () {
                                Ext.Msg.alert(messages['ext.tips.error'], '操作成功');
                                self.setDisabled(false);
                            },
                            failure: function () {
                                Ext.Msg.alert(messages['ext.tips.error'], '服务器繁忙，请稍候再试');
                                self.setDisabled(false);
                            }
                        });
                    }
                },

                //修改用户
                editUserHandle: function () {
                    var grid = this.up('gridpanel');
                    var sm = grid.getSelectionModel();
                    var record = sm.getSelection()[0];// 获取选中的条目的第一条
                    var sysId = record.get("userSysId");
                    var code = record.get("userSysId");
                    var userPasswordOld = record.get("userPassword");
                    var modifyCheckBox = false;

                    if (Ext.getCmp('editUserWindow')) {
                        Ext.getCmp('editUserWindow').show(this);
                    } else {
                        Ext.create('Ext.Window', {
                            title: '修改用户',
                            width: 700,
                            height: 400,
                            layout: 'fit',
                            draggable: false,
                            maximizable: true,
                            modal: true,
//                          resizable: true,
//                          autoScroll: true,
//                          constrain: true,
                            //closeAction : 'hide',
                            id: 'editUserWindow',
                            items: [
                                orderDetailTabPanel = Ext.create('Ext.panel.Panel', {
                                    autoScroll: true,
                                    border: 0,
                                    items: [Ext.create('Ext.panel.Panel', {
                                        layout: 'fit',
                                        items: [Ext.create("Ext.form.Panel", {
//                                            url: path("/user/saveUser.do?cbModifyPass=" + modifyCheckBox + "&userSysId=" + sysId),
                                            id: 'updateUser',
//                                            padding: '0 0 0 20',
                                            reader: new Ext.data.JsonReader({
                                                root: 'result',
                                                model: 'User',
                                                successProperty: 'success'
                                            }),
                                            bodyPadding: 20,
                                            border: 0,
                                            items: [
                                                {
                                                    fieldLabel: '用户名',
                                                    xtype: 'displayfield',
                                                    id: 'userId' + sysId,
                                                    name: 'userId',
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
                                                    fieldLabel: '电子邮件',
                                                    xtype: 'textfield',
                                                    id: 'userEmail' + sysId,
                                                    name: 'userEmail',
                                                    padding: '0 0 10 0',
                                                    allowBlank: false, // 不允许为空
                                                    blankText: "不能为空",
//                                                  readOnly:true,
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
                                                    boxLabel: '同时修改密码',
                                                    listeners: {
                                                        change: function () {
                                                            if (this.checked) {
                                                                Ext.getCmp('userPassword' + sysId).show();
                                                                Ext.getCmp('userPassWordConfirm' + sysId).show();
                                                                Ext.getCmp('userPassword' + sysId).allowBlank = false;
                                                                Ext.getCmp('userPassWordConfirm' + sysId).allowBlank = false;
                                                                modifyCheckBox = true;
                                                            } else {
                                                                Ext.getCmp('userPassword' + sysId).setValue("");
                                                                Ext.getCmp('userPassWordConfirm' + sysId).setValue("");
                                                                Ext.getCmp('userPassword' + sysId).hide();
                                                                Ext.getCmp('userPassWordConfirm' + sysId).hide();
                                                                Ext.getCmp('userPassword' + sysId).allowBlank = true;
                                                                Ext.getCmp('userPassWordConfirm' + sysId).allowBlank = true;
                                                                modifyCheckBox = false;
                                                            }
                                                        }
                                                    }
                                                },
                                                {
                                                    fieldLabel: '用户密码<span style=color:red>*</span>',
                                                    xtype: 'textfield',
                                                    id: 'userPassword' + sysId,
                                                    name: 'userPassword',
                                                    hidden: true,
                                                    allowBlank: true,
                                                    inputType: 'password',
                                                    width: 300,
                                                    maxLength: 16,
                                                    maxLengthText: '最大长度不能超过16个字符',
                                                    minLength: 6,
                                                    minLengthText: '最小长度不能小于6个字符',
                                                    regex: new RegExp("^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,16}$"),
                                                    invalidText: '密码必须包含大小写字母、数字以及特殊字符且长度为6-16位'
//                                                    blankText:"不能为空",
//                                                    width:300,
//                                                    maxLength:8,
//                                                    maxLengthText:'密码长度为8位!',
//                                                    minLength:8,
//                                                    minLengthText:'密码长度为8位!'
                                                },
                                                {
                                                    fieldLabel: '确认密码<span style=color:red>*</span>',
                                                    xtype: 'textfield',
                                                    id: 'userPassWordConfirm' + sysId,
                                                    name: 'userPassWordConfirm',
                                                    inputType: 'password',
                                                    hidden: true,
                                                    allowBlank: true,
                                                    width: 300,
                                                    vtype: "password", //自定义的验证类型
                                                    vtypeText: "两次密码不一致",
                                                    confirmTo: "userPassword" + sysId//要比较的另外一个的组件的id
                                                },
                                                {
                                                    xtype: 'displayfield',
                                                    hidden: true,
                                                    id: 'userRoleValue' + sysId,
                                                    name: 'userRoles'
                                                },
                                                {
                                                    fieldLabel: '角色(可多选)',
                                                    xtype: 'combo',
                                                    id: 'userRole' + sysId,
                                                    name: 'userRole',
                                                    store: Ext.create("Role"),
                                                    width: 500,
                                                    displayField: 'ccRoleName',
                                                    valueField: 'ccRoleSysId',
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
                                                    id: 'userName' + sysId,
                                                    name: 'userName',
                                                    padding: '0 0 10 0',
                                                    width: 300,
                                                    maxLength: 50,
                                                    maxLengthText: '最大长度不能超过50个字符!'
                                                },
                                                {
                                                    fieldLabel: '备注',
                                                    xtype: 'textarea',
                                                    id: 'userRemark' + sysId,
                                                    name: 'userRemark',
                                                    width: 600,
                                                    height: 100,
                                                    maxLength: 300,
                                                    maxLengthText: '客户描述的长度不能超过300字符',
                                                    padding: '0 0 10 0',
                                                    name: 'userRemark'
                                                },
                                                {
                                                    xtype: 'displayfield',
                                                    hidden: true,
                                                    id: 'statusValue' + sysId,
                                                    name: 'customerStatus'
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
                                                                var form = Ext.getCmp('updateUser').getForm();
                                                                if (this.up('panel').getForm().isValid()) {

                                                                    this.up('panel').getForm().submit({
//                                                                        url: path("/user/saveUser.do?userSysId=" + sysId),
                                                                        url: path("/user/saveUser.do?cbModifyPass=" + modifyCheckBox + "&userSysId=" + sysId),
                                                                        success: function (json, action) {
                                                                            var data = action.result;
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
                        var form = Ext.getCmp('updateUser').getForm();
                        form.load({
                            url: path("/user/form.json?userSysId=" + sysId),
                            success: function (form, action) {
                                form.url = path("/user/saveUser.do?userSysId=" + sysId);
                                var userRole = Ext.getCmp('userRoleValue' + sysId).getValue();
                                var our = userRole.split(",");
                                var userRoleDisplay = Ext.getCmp('userRole' + sysId);
                                userRoleDisplay.setValue(our);
                                //给单选框赋值
                                var status = action.result.data.userStatus;
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
                removeUserHandle: function () {
                    var grid = this.up('gridpanel');
                    var sm = grid.getSelectionModel();
//                  var record = sm.getSelection()[0];
                    var record = sm.getSelection();
                    if (record.length == 0) {
                        Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                        return;
                    }
                    var sysids = "";
                    for (var i = 0; i < record.length; i++) {
                        sysids += record[i].data.userSysId + ";";
                    }
//                  var sysId = record.get("userSysId");
                    Ext.MessageBox.confirm('警告', '确定删除？', function (btn) {
                        if (btn == 'yes') {
                            Ext.Ajax.request({
                                url: path('/user/remove.json?id=' + sysids),
                                method: "post",
                                success: function (action, data) {
                                    Ext.Msg.alert('提示信息', '删除成功');
                                    Ext.getCmp('userGridPanel').store.loadPage(1);
                                },
                                failure: function (action, data) {
                                    Ext.Msg.alert('提示信息', '删除失败');
                                },
                                waitMsg: Ext.MessageBox.wait('正在删除数据，请等待...', '提示')
                            });
                        }
                    });
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
                            handler: me.addUserHandle(this)
                        },
                        {
                            xtype: 'button',
                            itemId: 'btnEdit',
                            disabled: true,
                            height: 24,
                            text: '修改用户',
                            iconCls: 'icon-edit',
                            handler: me.editUserHandle
                        }
                        ,
                        {
                            xtype: 'button',
                            itemId: 'btnDelete',
                            height: 24,
                            text: '删除用户',
                            iconCls: 'icon-delete',
                            handler: me.removeUserHandle
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

        },

        init_filters: function () {
            return   [
                {type: 'string', dataIndex: 'userId'}
            ]
        },
        create_index: function () {
            var scope = this;
            return Ext.create('Oms.User.Index', {
                topBarHight: 30,
                autoSetStore: true,
                features: [
                    {
                        ftype: 'filters', encode: true, local: false, menuFilterText: messages['ext.filters.menuFilterText'],
                        filters: scope.init_filters()
                    }
                ]
            });
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

    scope.$User = sand_box();
})(this);

Ext.onReady(function () {
    var index = $User.createIndex();
    Ext.getCmp('center').add(index);
})