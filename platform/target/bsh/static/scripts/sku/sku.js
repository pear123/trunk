(function (scope) {
    var _$Sku = scope.$Sku;

    var Initialize = {
        define:function () {
            //define brand store
            var brandStore =  Ext.create('Ext.data.Store', {
                fields: ['value', 'label'],
                proxy: {
                    type: 'ajax',
                    url: path('/sku/findBrand.json'),
                    reader: {
                        type: 'json',
                        root: 'result'
                    }
                },
                autoLoad: true
            });

            //define category store
            var categoryStore =  Ext.create('Ext.data.Store', {
                fields: ['value', 'label'],
                proxy: {
                    type: 'ajax',
                    url: path('/sku/findCategory.json'),
                    reader: {
                        type: 'json',
                        root: 'result'
                    }
                },
                autoLoad: true
            });

            //define sku grid
            Ext.define('Smartec.sku.SkuGrid', {
                extend:'Smartec.grid.Panel',
                model:'Sku',
                id:'skuGridId',
                height:'100%',
                width:'100%',
                columns:[
                    {xtype:'rownumberer', text:'序号', width:40, sortable:false},
                    {text:'id', dataIndex:'id', hidden:true},
                    {text:'version', dataIndex:'version', hidden:true},
                    {text:'品牌', width:160, sortable:true, dataIndex:'brand'},
                    {text:'品类', width:160, sortable:true, dataIndex:'cid'},
                    {text:'SKU号', width:150, sortable:true, dataIndex:'matnr'},
                    { text:'Tmall价格', width:130, sortable:true, dataIndex:'tmallPrice'},
                    { text:'Sap价格', width:130, sortable:true, dataIndex:'sapprice'}      ,
                    { text:'状态', width:130, sortable:true, dataIndex:'status'}
                ],
                addSku:function(scope){
                    return function () {
                        Ext.Ajax.request({
                            url: path("/sku/doCurrentOperation.json?operationType=save"),
                            method: "post",
                            dataType: "json",
                            success:function (data,act) {
                                var res = Ext.decode(data.responseText);
                                if(res.success){
                                    var grid = scope.up('gridpanel');
                                    if (Ext.getCmp('addSkuWindow')) {
                                        Ext.getCmp('addSkuWindow').show(this);
                                    } else {
                                        Ext.create('Ext.Window', {
                                            title: '添加SKU',
                                            width: 450,
                                            height: 300,
                                            layout: 'fit',
                                            draggable: true,
                                            maximizable: true,
                                            modal: true,
                                            id: 'addSkuWindow',
                                            listeners: {
                                                hide: function (win) {
                                                    var form = Ext.getCmp('addSkuForm').getForm();
                                                    form.clearInvalid();
                                                    form.reset();
                                                }
                                            },
                                            items: [
                                                new Ext.form.FormPanel({
                                                    id: 'addSkuForm',
                                                    bodyPadding: 20,
                                                    autoScroll: true,
                                                    items: [
                                                        {
                                                            xtype: 'combo',
                                                            fieldLabel: '品牌',
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
                                                            xtype: 'combo',
                                                            fieldLabel: '品类',
                                                            typeAhead : true,
                                                            name: 'category',
                                                            store: categoryStore,
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
                                                            fieldLabel: '型号',
                                                            name: 'matnr',
                                                            id:  'matnr' ,
                                                            padding: '0 0 10 0',
                                                            allowBlank: false, // 不允许为空
                                                            blankText: "不能为空",
                                                            maxLength: 50,
                                                            maxLengthText: '最大长度不能超过50个字符!',
                                                            width: 300 ,
                                                            regex:/^[A-Za-z0-9]+$/ ,
                                                            regexText:"错误,SKU号只能由数字和字母组成",
                                                            listeners : {
                                                                blur:function(){
                                                                    var inputMatnr = Ext.getCmp("matnr").getValue();
                                                                    Ext.Ajax.request({
                                                                        url: path("/sku/findMatnr.json?inputMatnr="+inputMatnr),
                                                                        method: "post",
                                                                        dataType: "json",
                                                                        success:function (data,act) {
                                                                            var res = Ext.decode(data.responseText);
                                                                            if(res.success){
                                                                            }else{
                                                                                Ext.Msg.alert('提示信息', 'SKU号错误:'+res.result);
                                                                                Ext.getCmp("matnr").setValue()=="";
                                                                            }
                                                                        } ,
                                                                        failure:function (action, data) {
                                                                            Ext.Msg.alert('提示信息', '请求失败,请稍后再试');
                                                                        }
                                                                    })  ;
                                                                }
                                                            }
                                                        },
                                                        {
                                                            xtype: 'numberfield',
                                                            fieldLabel: 'TMALL 价格',
                                                            name: 'tmallprice',
                                                            allowDecimals: true,
                                                            decimalPrecision: 2,
                                                            allowBlank: false, // 不允许为空
                                                            blankText: "不能为空",
                                                            minValue: 0 ,
                                                            padding: '0 0 10 0'
                                                        },
                                                        {
                                                            fieldLabel: 'SAP 价格',
                                                            xtype: 'numberfield',
                                                            name: 'sapprice',
                                                            allowBlank: false, // 不允许为空
                                                            blankText: "不能为空",
                                                            allowDecimals: true,
                                                            decimalPrecision: 2,
                                                            minValue: 0,
                                                            padding: '0 0 10 0'
                                                        },
                                                        {
                                                            xtype: 'button',
                                                            text: '保存',
                                                            iconCls: 'icon-ok',
                                                            handler: scope.saveSku
                                                        }
                                                    ]
                                                })]
                                        }).show();
                                    }
                                    Ext.getCmp('addSkuWindow').on("close",function(){
                                        Ext.Ajax.request({
                                            url:path("/sku/doDeleteCurrentOperation.json?operationType=save"),
                                            method:"post",
                                            dataType: "json",
                                            success:function (data,act) {
                                            }
                                        });
                                    }) ;
                                }else{
                                    Ext.Msg.alert('提示信息', res.result);
                                }
                            }
                        });
                    }

                },
                saveSku:function(){
                    var form = Ext.getCmp("addSkuForm").getForm();
                    if (form.isValid()) {
                        form.submit({
                            url:path('/sku/saveSku.json?'),
                            method:'POST',
                            success:function (action, data) {
                                Ext.Msg.alert('提示信息', '保存成功');
                                Ext.getCmp('addSkuWindow').close();
                                var grid = Ext.getCmp('skuGridId');
                                grid.getStore().load();
                            },
                            failure:function (action, data) {
                                var res = Ext.decode(data.response.responseText);
                                Ext.Msg.alert('提示信息', '保存失败:'+res.result);
                            },
                            waitTitle:Ext.MessageBox.wait('正在保存，请等待...', '提示')
                        });
                    } else {
                        Ext.Msg.alert('提示信息', '请输入正确的值！');
                    }
                },

                deleteSku:function (scope) {
                    var grid = this.up('gridpanel');
                    var sm = grid.getSelectionModel();
                    var record=sm.getSelection();
                    if(!(record.length>=1)){
                        Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                        return;
                    }
                    var sysids = "";
                    for(var i=0;i<record.length;i++){
                        sysids=sysids+record[i].data.id+";";
                    }
                    Ext.Ajax.request({
                        url: path("/sku/doCurrentOperation.json?operationType=delete&id="+sysids),
                        method: "post",
                        dataType: "json",
                        success:function (data,act) {
                            var res = Ext.decode(data.responseText);
                            if(res.success){
                                Ext.MessageBox.confirm({title:'警告',msg:'确定要删除吗?',closable:false,buttons:Ext.Msg.YESNO,fn:callBack,icon:Ext.Msg.INFO});
                                function callBack(yesOrNo){
                                    if(yesOrNo=='yes'){
                                        Ext.Ajax.request({
                                            url:path("/sku/deleteSku.json?id="+sysids),
                                            method:"post",
                                            success:function (action, data){
                                                Ext.Msg.alert('提示信息', '删除成功');
                                                var grid = Ext.getCmp('skuGridId');
                                                // grid.getStore().remove(record);
                                                grid.getStore().load();
                                            } ,
                                            failure:function (action, data) {
                                                Ext.Msg.alert('提示信息', '删除失败');
                                            },
                                            waitTitle:Ext.MessageBox.wait('正在保存，请等待...', '提示')
                                        });
                                    }else if(yesOrNo=='no'){
                                        Ext.Ajax.request({
                                            url:path("/sku/doDeleteCurrentOperation.json?operationType=delete"),
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
                },
                modifySku:function(scope){
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
                    var sysId = record.get("id");//获取Id

                    Ext.Ajax.request({
                        url: path("/sku/doCurrentOperation.json?operationType=modify&sysId="+sysId),
                        method: "post",
                        dataType: "json",
                        success:function (data,act) {
                            var res = Ext.decode(data.responseText);
                            if(res.success){
                                if (Ext.getCmp('editSkuWindow')) {
                                    Ext.getCmp('editSkuWindow').show();
                                } else {
                                    Ext.create('Ext.Window', {
                                        title: '修改SKU',
                                        width: 450,
                                        height: 300,
                                        layout:'fit',
                                        draggable: true,
                                        maximizable: true,
                                        modal: true,
                                        id: 'editSkuWindow',
                                        items: [
                                            Ext.create("Ext.form.Panel", {
                                                url: path("/sku/findById.json?id=" + sysId),
                                                id: 'editSkuForm',
                                                reader: new Ext.data.JsonReader({
                                                    root: 'result',
                                                    model: 'Sku',
                                                    successProperty: 'success'
                                                }),
                                                bodyPadding: 20,
                                                //autoScroll: true,
                                                items: [
                                                    {
                                                        xtype: 'combo',
                                                        fieldLabel: '品牌',
                                                        id:'brandCom',
                                                        name: 'brand',
                                                        store: brandStore,
                                                        displayField: 'label',
                                                        valueField: 'value',
                                                        padding: '0 0 10 0',
                                                        allowBlank: false, // 不允许为空
                                                        blankText: "不能为空",
                                                        width:300,
                                                        value:record.get("brand"),
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
                                                        xtype: 'combo',
                                                        fieldLabel: '品类',
                                                        typeAhead : true,
                                                        id: 'category',
                                                        name: 'category',
                                                        store: categoryStore,
                                                        displayField: 'label',
                                                        valueField: 'value',
                                                        value:record.get("cid"),
                                                        queryMode: 'local',
                                                        forceSelection:true,//要求输入值必须在列表中存在
                                                        typeAhead:true,//允许自动选择匹配的剩余部分文本
                                                        padding: '0 0 10 0',
                                                        allowBlank: false, // 不允许为空
                                                        blankText: "不能为空",
                                                        width: 300,
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
                                                        fieldLabel: '型号',
                                                        id: 'matnr',
                                                        name: 'matnr',
                                                        padding: '0 0 10 0',
                                                        allowBlank: false, // 不允许为空
                                                        blankText: "不能为空",
                                                        value:record.get("matnr"),
                                                        maxLength: 50,
                                                        maxLengthText: '最大长度不能超过50个字符!',
                                                        width: 300,
                                                        regex:/^[A-Za-z0-9]+$/ ,
                                                        regexText:"错误,SKU号只能由数字和字母组成",
                                                        listeners : {
                                                            blur:function(){
                                                                var inputMatnr = Ext.getCmp("matnr").getValue();
                                                                var recordMantr=record.get("matnr");
                                                                Ext.Ajax.request({
                                                                    url: path("/sku/findMatnrRemoveMy.json?inputMatnr="+inputMatnr+"&recordMantr="+recordMantr),
                                                                    method: "post",
                                                                    dataType: "json",
                                                                    success:function (data,act) {
                                                                        var res = Ext.decode(data.responseText);
                                                                        if(res.success){
                                                                        }else{
                                                                            Ext.Msg.alert('提示信息', 'SKU号错误:'+res.result);
                                                                            // Ext.getCmp("matnr").setValue(recordMantr);
                                                                        }
                                                                    } ,
                                                                    failure:function (action, data) {
                                                                        Ext.Msg.alert('提示信息', '请求失败,请稍后再试');
                                                                    }
                                                                })  ;
                                                            }
                                                        }
                                                    },
                                                    {
                                                        xtype: 'numberfield',
                                                        fieldLabel: 'TMALL 价格',
                                                        name: 'tmallprice',
                                                        value:record.get("tmallPrice"),
                                                        allowDecimals: true,
                                                        decimalPrecision: 2,
                                                        minValue: 0,
                                                        padding: '0 0 10 0',
                                                        filter:{
                                                            type: 'numeric'
                                                        }
                                                    },
                                                    {
                                                        fieldLabel: 'SAP 价格',
                                                        xtype: 'numberfield',
                                                        name: 'sapprice',
                                                        value:record.get("sapprice"),
                                                        allowDecimals: true,
                                                        decimalPrecision: 2,
                                                        minValue: 0,
                                                        padding: '0 0 10 0',
                                                        filter:{
                                                            type: 'numeric'
                                                        }
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
                                                                    var form = Ext.getCmp('editSkuForm').getForm();
                                                                    var recordMantr=record.get("matnr");
                                                                    if (this.up('panel').getForm().isValid()) {
                                                                        this.up('panel').getForm().submit({
                                                                            url: path("/sku/saveSku.json?id=" + sysId+"&recordMantr="+recordMantr),
                                                                            success:function (action, data) {
                                                                                Ext.Msg.alert('提示信息', '保存成功');
                                                                                Ext.getCmp('editSkuWindow').close();
                                                                                var grid = Ext.getCmp('skuGridId');
                                                                                grid.getStore().load();
                                                                            },
                                                                            failure:function (action, data) {
                                                                                var res = Ext.decode(data.response.responseText);
                                                                                Ext.Msg.alert('提示信息', '保存失败:'+res.result);
                                                                                if("该记录已被删除"==res.result){
                                                                                    Ext.getCmp('editSkuWindow').close();
                                                                                    var grid = Ext.getCmp('skuGridId');
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
                                Ext.getCmp('editSkuWindow').on("close",function(){
                                    Ext.Ajax.request({
                                        url:path("/sku/doDeleteCurrentOperation.json?operationType=modify&sysId="+sysId),
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

                unlockSku:function(scope){
                    Ext.Ajax.request({
                        url:path("/sku/doDeleteSkuCurrentOperationByUser.json"),
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
                            handler:me.addSku(this)
                        },
                        {
                            xtype:'button',
                            itemId:'btnEdit',
                            height:24,
                            text:'修改',
                            disable:true,
                            iconCls:'icon-edit',
                            handler:me.modifySku
                        },
                        {
                            xtype:'button',
                            itemId:'btnDelete',
                            height:24,
                            text:'删除',
                            disable:true,
                            iconCls:'icon-edit',
                            handler:me.deleteSku
                        },
                        {
                            xtype:'button',
                            itemId:'btnUnlocek',
                            height:24,
                            text:'解锁',
                            disable:true,
                            iconCls:'icon-edit',
                            handler:me.unlockSku
                        }
                    ]
                    me.callParent();
                    me.getSelectionModel().on('selectionchange', function (thiz, selections) {
                        //me.down('#btnEdit').setDisabled((selections.length != 1));
                        //me.down('#btnDelete').setDisabled((selections.length != 1));
                    })
                }
            });
        },

        clearFilters:function () {
            var me = this;
            return function () {
                if (me.sku_grid) {
                    me.sku_grid.filters.clearFilters();
                }
            }
        },


        init_filters:function () {
            return   [
                {type:'string', dataIndex:'brand'} ,
                {type:'string', dataIndex:'cid'},
                {type:'string', dataIndex:'matnr'},
                {type:'numeric', dataIndex:'tmallPrice'} ,
                {type:'numeric', dataIndex:'sapprice'} ,
                {type:'string', dataIndex:'status'},
                {type:'numeric', dataIndex:'version'}
            ];
        },

        create_grid:function () {
            if (!this.sku_grid) {
                this.sku_grid = Ext.create('Smartec.sku.SkuGrid', {
                    topBarHight:30,
                    id:'skuGridId',
                    autoHeight:true,
                    autoWidth:true,
                    autoAdded:false,
                    autoSetStore:true,
                    features:[
                        {
                            ftype:'filters', encode:true, local:false,menuFilterText:messages['ext.filters.menuFilterText'],
                            filters:Initialize.init_filters()
                        }
                    ]
                });
            }

            return this.sku_grid;
        }
    }

    function create_sandbox() {
        Initialize.define();
        var skuAPI = {
            createGrid:function () {
                return  Initialize.create_grid();
            }
        }
        return skuAPI;
    }

    scope.$Sku = create_sandbox();
})(this);

Ext.onReady(function () {
    var index = $Sku.createGrid();
    Ext.getCmp('center').add(index);
});