/**
 * Created by XUSO002 on 2015/8/14 0014.
 */
(function (scope) {
    var _$Gift = scope.$Gift;

    var Initialize = {
        define:function () {
            //define brand store
            var brandStore =  Ext.create('Ext.data.Store', {
                fields: ['value', 'label'],
                proxy: {
                    type: 'ajax',
                    url: path('/gift/findBrand.json'),
                    reader: {
                        type: 'json',
                        root: 'result'
                    }
                },
                autoLoad: true
            });
            //define sku grid
            Ext.define('Smartec.gift.GiftGrid', {
                extend:'Smartec.grid.Panel',
                model:'Gift',
                id:'giftGridId',
                columns:[
                    {xtype:'rownumberer', text:'序号', width:40, sortable:false},
                    {text:'id', dataIndex:'skuId', hidden:true},
                    {text:'版本', dataIndex:'version', hidden:true},
                    {text:'品牌', width:150, sortable:true, dataIndex:'brand'},
                    {text:'SAP物料号', width:150, sortable:true, dataIndex:'skuNo'},
                    { text:'商品名称', width:250, sortable:true, dataIndex:'name'},
                    { text:'客服备注简写', width:150, sortable:true, dataIndex:'memo'}      ,
                    { text:'状态', width:130, sortable:true, dataIndex:'status'}
                ],

                addGift:function(scope){
                    return function () {
                        Ext.Ajax.request({
                            url: path("/gift/doCurrentOperation.json?operationType=save"),
                            method: "post",
                            dataType: "json",
                            success:function (data,act) {
                                var res = Ext.decode(data.responseText);
                                if(res.success){
                                    var grid = scope.up('gridpanel');
                                    if (Ext.getCmp('addGiftWindow')) {
                                        Ext.getCmp('addGiftWindow').show(this);
                                    } else {
                                        Ext.create('Ext.Window', {
                                            title: '添加物料',
                                            width: 450,
                                            height: 300,
                                            layout: 'fit',
                                            draggable: true,
                                            maximizable: true,
                                            modal: true,
                                            id: 'addGiftWindow',
                                            listeners: {
                                                hide: function (win) {
                                                    var form = Ext.getCmp('addGiftForm').getForm();
                                                    form.clearInvalid();
                                                    form.reset();
                                                }
                                            },
                                            items: [
                                                new Ext.form.FormPanel({
                                                    id: 'addGiftForm',
                                                    bodyPadding: 20,
                                                    autoScroll: true,
                                                    items: [
                                                        {
                                                            xtype: 'combo',
                                                            fieldLabel: '品牌',
                                                            typeAhead : true,
                                                            name: 'brand',
                                                            store: brandStore,
                                                            displayField: 'label',
                                                            valueField: 'value',
                                                            padding: '0 0 10 0',
                                                            allowBlank: false, // 不允许为空
                                                            blankText: "不能为空",
                                                            width: 300,
                                                            queryMode: 'local',
                                                            forceSelection:true,//要求输入值必须在列表中存在
                                                            typeAhead:true,//允许自动选择匹配的剩余部分文本
                                                            listeners : {
                                                                'beforequery':function(e){
                                                                    var combo = e.combo;
                                                                    if(!e.forceAll){
                                                                        var input = e.query;
                                                                        // 检索的正则
                                                                        var regExp = new RegExp(".*" + input + ".*");
                                                                        // 执行检索
                                                                        combo.store.filterBy(function(record,id){
                                                                            // 得到每个record的项目名称值
                                                                            var text = record.get(combo.displayField);
                                                                            return regExp.test(text);
                                                                        });
                                                                        combo.expand();
                                                                        return false;
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        {
                                                            xtype: 'textfield',
                                                            fieldLabel: 'SAP物料号',
                                                            name: 'skuNo',
                                                            id:'skuNo' ,
                                                            padding: '0 0 15 0',
                                                            allowBlank: false, // 不允许为空
                                                            blankText: "不能为空",
                                                            maxLength: 50,
                                                            maxLengthText: '最大长度不能超过50个字符!',
                                                            width: 300,
                                                            regex:/^[A-Za-z0-9]+$/ ,
                                                            regexText:"错误,SAP物料号只能由数字和字母组成",
                                                            listeners : {
                                                                blur: function () {
                                                                    var inputSkuNo = Ext.getCmp("skuNo").getValue();
                                                                    Ext.Ajax.request({
                                                                        url: path("/gift/findSkuNo.json?inputSkuNo=" + inputSkuNo),
                                                                        method: "post",
                                                                        dataType: "json",
                                                                        success: function (data, act) {
                                                                            var res = Ext.decode(data.responseText);
                                                                            if (res.success) {
                                                                            }  else{
                                                                                Ext.Msg.alert('提示信息', 'SKU号错误:' + res.result);
                                                                                Ext.getCmp("skuNo").setValue() == "";
                                                                            }
                                                                        } ,
                                                                        failure:function (action, data) {
                                                                            Ext.Msg.alert('提示信息', '请求失败,请稍后再试');
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                        },
                                                        {
                                                            xtype: 'textfield',
                                                            fieldLabel: '商品名称',
                                                            name: 'name',
                                                            allowDecimals: true,
                                                            decimalPrecision: 2,
                                                            padding: '0 0 15 0',
                                                            allowBlank: false, // 不允许为空
                                                            blankText: "不能为空",
                                                            minValue: 0,
                                                            width: 300
                                                        },
                                                        {
                                                            fieldLabel: '客服备注简写',
                                                            xtype: 'textfield',
                                                            name: 'memo',
                                                            allowBlank: false, // 不允许为空
                                                            blankText: "不能为空",
                                                            padding: '0 0 15 0',
                                                            allowDecimals: true,
                                                            decimalPrecision: 2,
                                                            minValue: 0,
                                                            width: 300
                                                        },
                                                        {
                                                            xtype: 'button',
                                                            text: '保存',
                                                            iconCls: 'icon-ok',
                                                            handler: scope.saveGift
                                                        }
                                                    ]
                                                })]
                                        }).show();
                                    }
                                    Ext.getCmp('addGiftWindow').on("close",function(){
                                        Ext.Ajax.request({
                                            url:path("/gift/doDeleteCurrentOperation.json?operationType=save"),
                                            method:"post",
                                            dataType: "json",
                                            success:function (data,act) {
                                            }
                                        });
                                    }) ;
                                }else{
                                    Ext.Msg.alert('提示信息',res.result);
                                }
                            }
                        });


                    }

                },

                saveGift:function(){
                    var form = Ext.getCmp("addGiftForm").getForm();
                    if (form.isValid()) {
                        form.submit({
                            url:path('/gift/saveGift.json?operationType=save'),
                            // waitMsg: '正在提交表单数据,请稍候...',
                            method:'POST',
                            success:function (action, data) {
                                Ext.Msg.alert('提示信息', '保存成功');
                                Ext.getCmp('addGiftWindow').close();
                                var grid = Ext.getCmp('giftGridId');
                                grid.getStore().load();
                            },
                            failure:function (action, data) {
                                var res = Ext.decode(data.response.responseText);
                                console.info(res);

                                Ext.Msg.alert('提示信息', '保存失败:'+res.result);
                            },
                            waitTitle:Ext.MessageBox.wait('正在保存，请等待...', '提示')
                        });
                    } else {
                        Ext.Msg.alert('提示信息', '请输入正确的值！');
                    }
                },

                deleteGift:function (scope) {
                    var grid = this.up('gridpanel');
                    var sm = grid.getSelectionModel();
                    var record=sm.getSelection();
                    if(!(record.length>=1)){
                        Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                        return;
                    } else{
                        var sysids = "";
                        for (var i = 0; i < record.length; i++) {
                            console.info(record[i].data.skuId)
                            sysids = sysids + record[i].data.skuId + ";";
                        }
                        Ext.Ajax.request({
                            url: path("/gift/doCurrentOperation.json?operationType=delete&id="+sysids),
                            method: "post",
                            dataType: "json",
                            success:function (data,act) {
                                var res = Ext.decode(data.responseText);
                                if(res.success){
                                    Ext.MessageBox.confirm({title:'警告',msg:'确定要删除吗?',closable:false,buttons:Ext.Msg.YESNO,fn: callBack,icon:Ext.Msg.INFO});
                                    function callBack(yesOrNo) {
                                        if (yesOrNo == 'yes') {
                                            Ext.Ajax.request({
                                                url: path("/gift/deleteGift.json?id=" + sysids),
                                                method: "post",
                                                success: function (action, data) {
                                                    Ext.Msg.alert('提示信息', '删除成功');
                                                    var grid = Ext.getCmp('giftGridId');
                                                    //grid.getStore().remove(record);
                                                    grid.getStore().load();
                                                },
                                                failure: function (action, data) {
                                                    Ext.Msg.alert('提示信息', '删除失败');
                                                },
                                                waitTitle: Ext.MessageBox.wait('正在保存，请等待...', '提示')
                                            });
                                        }else if(yesOrNo=='no'){
                                            Ext.Ajax.request({
                                                url:path("/gift/doDeleteCurrentOperation.json?operationType=delete"),
                                                method:"post",
                                                dataType: "json",
                                                success:function (data,act) {
                                                }
                                            });
                                        }
                                    }
                                }else{
                                    Ext.Msg.alert('提示信息', res.result);
                                }
                            },
                            failure:function (action, data) {
                                Ext.Msg.alert('提示信息', '请求失败,请稍后再试');
                            }
                        });
                    }
                },

                modifyGift:function(scope){
                    var grid = this.up('gridpanel');
                    var sm = grid.getSelectionModel();
                    var record1=sm.getSelection();
                    if(record1.length==0){
                        Ext.Msg.alert("提示", "请选择一条记录进行修改！");
                        return;
                    }
                    if(record1.length!=1){
                        Ext.Msg.alert("提示", "只能选择一条记录进行修改！");
                        return;
                    }
                    var record = sm.getSelection()[0];// 获取选中的条目的第一条
                    var sysId = record.get("skuId");//获取Id
                    Ext.Ajax.request({
                        url: path("/gift/doCurrentOperation.json?operationType=modify&sysId="+sysId),
                        method: "post",
                        dataType: "json",
                        success:function (data,act) {
                            var res = Ext.decode(data.responseText);
                            if(res.success){
                                //var version=record.get("version");
                                if (Ext.getCmp('editGiftWindow')) {
                                    Ext.getCmp('editGiftWindow').show();
                                } else {
                                    Ext.create('Ext.Window', {
                                        title: '修改物料',
                                        width: 450,
                                        height: 300,
                                        layout:'fit',
                                        draggable: false,
                                        maximizable: true,
                                        modal: true,
                                        id: 'editGiftWindow',
                                        items: [
                                            Ext.create("Ext.form.Panel", {
                                                url: path("/gift/findById.json?id=" + sysId),
                                                id: 'editGiftForm',
                                                // padding: '0 0 0 20',
                                                //bodyPadding: 20,
                                                reader: new Ext.data.JsonReader({
                                                    root: 'result',
                                                    model: 'Gift',
                                                    successProperty: 'success'
                                                }),
                                                bodyPadding: 20,
                                                autoScroll: true,
                                                items: [
                                                    {
                                                        xtype: 'combo',
                                                        fieldLabel: '品牌',
                                                        typeAhead : true,
                                                        id: 'brandCom',
                                                        name: 'brand',
                                                        store: brandStore,
                                                        displayField: 'label',
                                                        valueField: 'value',
                                                        padding: '0 0 10 0',
                                                        allowBlank: false, // 不允许为空
                                                        blankText: "不能为空",
                                                        value:record.get("brand"),
                                                        width: 300,
                                                        queryMode: 'local',
                                                        forceSelection:true,//要求输入值必须在列表中存在
                                                        typeAhead:true,//允许自动选择匹配的剩余部分文本
                                                        listeners : {
                                                            'beforequery':function(e){
                                                                var combo = e.combo;
                                                                if(!e.forceAll){
                                                                    var input = e.query;
                                                                    // 检索的正则
                                                                    var regExp = new RegExp(".*" + input + ".*");
                                                                    // 执行检索
                                                                    combo.store.filterBy(function(record,id){
                                                                        // 得到每个record的项目名称值
                                                                        var text = record.get(combo.displayField);
                                                                        return regExp.test(text);
                                                                    });
                                                                    combo.expand();
                                                                    return false;
                                                                }
                                                            }
                                                        }
                                                    },
                                                    {
                                                        xtype: 'textfield',
                                                        fieldLabel: 'SAP物料号',
                                                        id: 'skuNo',
                                                        name: 'skuNo',
                                                        padding: '0 0 15 0',
                                                        allowBlank: false, // 不允许为空
                                                        blankText: "不能为空",
                                                        value:record.get("skuNo"),
                                                        maxLength: 50,
                                                        maxLengthText: '最大长度不能超过50个字符!',
                                                        width: 300 ,
                                                        regex:/^[A-Za-z0-9]+$/ ,
                                                        regexText:"错误,SAP物料号只能由数字和字母组成",
                                                        listeners : {
                                                            blur: function () {
                                                                var inputSkuNo = Ext.getCmp("skuNo").getValue();
                                                                var recordSkuNo=record.get("skuNo") ;
                                                                Ext.Ajax.request({
                                                                    url: path("/gift/findSkuNoRemoveMy.json?inputSkuNo=" + inputSkuNo+"&recordSkuNo="+recordSkuNo),
                                                                    method: "post",
                                                                    dataType: "json",
                                                                    success: function (data, act) {
                                                                        var res = Ext.decode(data.responseText);
                                                                        if (res.success) {
                                                                        }else{
                                                                            Ext.Msg.alert('提示信息', 'SKU号错误:' + res.result);
                                                                            //  Ext.getCmp("skuNo").setValue(recordSkuNo);
                                                                        }
                                                                    } ,
                                                                    failure:function (action, data) {
                                                                        Ext.Msg.alert('提示信息', '请求失败,请稍后再试');
                                                                    }
                                                                });
                                                            }
                                                        }
                                                    },
                                                    {
                                                        xtype: 'textfield',
                                                        fieldLabel: '商品名称',
                                                        name: 'name',
                                                        value:record.get("name"),
                                                        allowDecimals: true,
                                                        padding: '0 0 15 0',
                                                        maxLength: 100,
                                                        decimalPrecision: 2,
                                                        minValue: 0,
                                                        width: 300
                                                    },
                                                    {
                                                        fieldLabel: '客服备注简写',
                                                        xtype: 'textfield',
                                                        name: 'memo',
                                                        value:record.get("memo"),
                                                        allowDecimals: true,
                                                        padding: '0 0 15 0',
                                                        decimalPrecision: 2,
                                                        minValue: 0 ,
                                                        width: 300
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
                                                                    var form = Ext.getCmp('editGiftForm').getForm();
                                                                    var recordSkuNo=record.get("skuNo") ;
                                                                    if (this.up('panel').getForm().isValid()) {
                                                                        this.up('panel').getForm().submit({
                                                                            url: path("/gift/saveGift.json?operationType=modify&skuId=" + sysId+"&recordSkuNo="+recordSkuNo),
                                                                            success:function (action, data) {
                                                                                Ext.Msg.alert('提示信息', '保存成功');
                                                                                Ext.getCmp('editGiftWindow').close();
                                                                                var grid = Ext.getCmp('giftGridId');
                                                                                grid.getStore().load();
                                                                            },
                                                                            failure:function (action, data) {
                                                                                var res = Ext.decode(data.response.responseText);
                                                                                Ext.Msg.alert('提示信息', '保存失败:'+res.result);
                                                                                if("该记录已被删除"==res.result){
                                                                                    Ext.getCmp('editGiftWindow').close();
                                                                                    var grid = Ext.getCmp('giftGridId');
                                                                                    grid.getStore().load();
                                                                                }
                                                                            },
                                                                            waitTitle:Ext.MessageBox.wait('正在保存，请等待...', '提示')
                                                                        });
                                                                    } else {
                                                                        Ext.Msg.alert(messages['ext.tips.error'], '填写的内容有误!');
                                                                    }
                                                                }
                                                            }
                                                        ]
                                                    }
                                                ]
                                            })
                                        ]
                                    }).show();
                                }
                                Ext.getCmp('editGiftWindow').on("close",function(){
                                    Ext.Ajax.request({
                                        url:path("/gift/doDeleteCurrentOperation.json?operationType=modify&sysId="+sysId),
                                        method:"post",
                                        dataType: "json",
                                        success:function (data,act) {
                                        }
                                    });
                                }) ;
                            }else{
                                Ext.Msg.alert('提示信息', res.result);
                            }
                        },
                        failure:function (action, data) {
                            Ext.Msg.alert('提示信息', '请求失败,请稍后再试');
                        }
                    });
                },

                unlockGift:function(scope){
                    Ext.Ajax.request({
                        url:path("/gift/doDeleteGiftCurrentOperationByUser.json"),
                        method:"post",
                        dataType: "json",
                        success:function (data,act) {
                            var res = Ext.decode(data.responseText);
                            Ext.Msg.alert('提示信息', res.result);
                        }
                    });
                },
                initComponent:function () {
                    var me = this;
                    me.topDockedItems = [
                        {
                            xtype:'button',
                            itemId:'btnSearch',
                            height:24,
                            text:'重置查询',
                            iconCls:'icon-edit',
                            handler:Initialize.clearFilters()
                        },
                        {
                            xtype:'button',
                            itemId:'btnAdd',
                            height:24,
                            text:'添加',
                            iconCls:'icon-edit',
                            handler:me.addGift(this)
                        },
                        {
                            xtype:'button',
                            itemId:'btnEdit',
                            height:24,
                            text:'修改',
                            disable:true,
                            iconCls:'icon-edit',
                            handler:me.modifyGift
                        },
                        {
                            xtype:'button',
                            itemId:'btnDelete',
                            height:24,
                            text:'删除',
                            disable:true,
                            iconCls:'icon-edit',
                            handler:me.deleteGift
                        },
                        {
                            xtype:'button',
                            itemId:'btnUnlocek',
                            height:24,
                            text:'解锁',
                            disable:true,
                            iconCls:'icon-edit',
                            handler:me.unlockGift
                        }
                    ]
                    me.callParent();
                    me.getSelectionModel().on('selectionchange', function (thiz, selections) {
                        //console.info(me.down('#btnEdit'));
                        //me.down('#btnEdit').setDisabled((selections.length != 1));
                        //me.down('#btnDelete').setDisabled((selections.length != 1));
                    })
                }
            });
        },

        clearFilters:function () {
            var me = this;
            return function () {
                if (me.gift_grid) {
                    me.gift_grid.filters.clearFilters();
                }
            }
        },

        init_filters:function () {
            return   [
                {type:'string', dataIndex:'brand'} ,
                {type:'string', dataIndex:'skuNo'} ,
                {type:'string', dataIndex:'name'} ,
                {type:'string', dataIndex:'memo'},
                {type:'string', dataIndex:'status'},
                {type:'numeric', dataIndex:'version'}
            ];
        },

        create_grid:function () {
            if (!this.gift_grid) {
                this.gift_grid = Ext.create('Smartec.gift.GiftGrid', {
                    topBarHight:30,
                    id:'giftGridId',
                    autoHeight:true,
                    autoWidth:true,
                    autoAdded:false,
                    autoSetStore:true,
                    features:[
                        {
                            ftype:'filters', encode:true, local:false, menuFilterText:messages['ext.filters.menuFilterText'],
                            filters:Initialize.init_filters()
                        }
                    ]
                });
            }

            return this.gift_grid;
        }
    }

    function create_sandbox() {
        Initialize.define();
        var giftAPI = {
            createGrid:function () {
                return  Initialize.create_grid();
            }
        }
        return giftAPI;
    }
    scope.$Gift = create_sandbox();
})(this);

Ext.onReady(function () {
    var index = $Gift.createGrid();
    Ext.getCmp('center').add(index);
});