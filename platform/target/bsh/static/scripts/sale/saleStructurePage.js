(function (scope) {
    var _$SaleStructurePage = scope.$SaleStructurePage;
    var initialize = {
        define:function () {
            Ext.require([ 'Ext.form.*', 'Ext.Img', 'Ext.tip.QuickTipManager', 'Ext.ux.FieldReplicator' ]);

            Ext.define('Bsh.report.saleStructure.IndexView', {
                extend:'Ext.form.Panel',
                clearAll:function (scope) {
                    return function () {
                        scope.getForm().reset();
                    }
                },
                addExportTab:function (scope) {
                    return function () {
                        var mainTab = Ext.getCmp('mainTab1');

                        if (mainTab) {
                            if(Ext.getCmp("saleStructurePageId")){
                                var gridStore = Ext.getCmp("saleStructurePageId").getStore() ;
                                var category = Ext.getCmp("category").getValue();
                                var storeId = Ext.getCmp("storeId").getValue();
                                var startTime = Ext.getCmp("startTime").getRawValue();
                                var endTime = Ext.getCmp("endTime").getRawValue();
                                var isTradeClose = Ext.getCmp("is_trade_close").checked;
                                var sku = Ext.getCmp("sku").getRawValue();

                                new_params = {"category1":category,"storeId1":storeId,"startTime1":startTime,"endTime1":endTime,
                                    "is_trade_close1":isTradeClose,"sku1":sku};
                                Ext.apply(gridStore.proxy.extraParams, new_params);
                                gridStore.load();
                                gridStore.on('load', function (store,records, options) {
                                    if (records.length == 0) {
                                        Ext.Msg.alert("提示信息", "无记录!");
                                    } else {
                                        mainTab.setActiveTab(1);
                                        mainTab.show();
                                    }
                                });
                            }else{
                                var gridpanel = initialize.create_grid()
                                gridpanel.store.on('load', function (store,records, options) {
                                    if (records.length == 0) {
                                        Ext.Msg.alert("提示信息", "无记录!");
                                    } else {
                                        mainTab.add(gridpanel).show();
                                    }
                                })
                            }
                        }
                    }
                },

                initComponent:function () {
                    var me = this;
                    this.dockedItems = [
                        {
                            xtype:'toolbar',
                            dock:'top',
                            items:[
                                {
                                    xtype:'button',
                                    itemId:'btnEdit',
                                    text:'查询',
                                    handler:me.addExportTab(this)
                                },
                                {
                                    xtype:'button',
                                    iconCls:'icon-clearAll',
                                    text:'清空',
                                    handler:me.clearAll(this)
                                }
                            ]
                        }
                    ]
                    me.callParent();
                }

            });

            Ext.define('Smartec.page.SaleStructure', {
                extend:'Smartec.reportGrid.Panel',
                title: '结果展示',
                model:SaleStructurePage,
                columns:[
//                    {xtype:'rownumberer', text:'序号', width:40, sortable:false},
                    {text:'No', width:100, sortable:false, dataIndex:'num'},
                    {text:'VIB', width:100, sortable:false, dataIndex:'matnr'},
                    { text:'QTY', width:100, sortable:false, dataIndex:'saleQuantity'},
                    { text:'Value', width:100, sortable:false, dataIndex:'saleAmount'},
                    { text:'AVP', width:100, sortable:false, dataIndex:'saleAvg' },
                    { text:'Qty Share', width:100, sortable:false, dataIndex:'saleQuantityPercentage' },
                    { text:'Value Share', width:100, sortable:false, dataIndex:'saleAmountPercentage' },
                    { text:'Supply Price', width:100, sortable:false, dataIndex:'sapPrice' },
                    { text:'Orignal Value', width:100, sortable:false, dataIndex:'sapAmount' },
                    { text:'Discount Rate', width:100, sortable:false, dataIndex:'sapDiscount' }
                ],
                exportExcel:function () {
//                    return function () {
//                        document.getElementById('category1').value = Ext.getCmp("category").getValue();
//                        document.getElementById('storeId1').value = Ext.getCmp("storeId").getValue();
//                        document.getElementById('startTime1').value = Ext.getCmp("startTime").getRawValue();
//                        document.getElementById('endTime1').value = Ext.getCmp("endTime").getRawValue();
//                        document.getElementById('is_trade_close1').value = Ext.getCmp("is_trade_close").checked;
//                        document.getElementById('sku1').value = Ext.getCmp("sku").getRawValue();
//                        document.forms['queryForm'].submit();
//                    }
                    var loadMarsk = new Ext.LoadMask(document.body, {
                        msg : '正在导出，请稍候',
                        removeMask : true// 完成后移除
                    });
                    loadMarsk .show(); //显示
                    Ext.Ajax.request({
                        disableCaching : true,
                        url: path('/sale/generateSaleStructure.json'),
                        method: 'POST',
//                        isUpload: true,
//                        form: Ext.fly('frmDummy'),
                        params:{
                            category1 : Ext.getCmp("category").getValue(),
                            storeId1 : Ext.getCmp("storeId").getValue(),
                            startTime1 : Ext.getCmp("startTime").getRawValue(),
                            endTime1 : Ext.getCmp("endTime").getRawValue(),
                            is_trade_close1 : Ext.getCmp("is_trade_close").checked,
                            sku1 : Ext.getCmp("sku").getRawValue()
                        },
                        success : function(response) {
                            if('' == Ext.decode(response.responseText).result) {
                                Ext.Msg.alert('提示信息', '无纪录！');
                            }
                            if('none' == Ext.decode(response.responseText).result) {
                                Ext.Msg.alert('提示信息', '无纪录！');
                            } else{
                                window.location.href = path('/sale/downSaleStructure.json?filePath='+Ext.decode(response.responseText).result);
                            }
                            loadMarsk.hide();
                        },
                        failure : function(response) {
                            loadMarsk.hide();
                        },
                        timeout:600000
                    });


                },
                initComponent:function () {
                    var me = this;
                    me.topDockedItems = [
                        {
                            xtype:'button',
                            itemId:'btnEdit',
                            height:24,
                            text:'导出',
                            iconCls:'icon-edit',
                            handler:me.exportExcel
                        }
                    ];
                    me.callParent();
                    me.store.on('beforeload', function (store, options) {
                        var category = Ext.getCmp("category").getValue();
                        var storeId = Ext.getCmp("storeId").getValue();
                        var startTime = Ext.getCmp("startTime").getRawValue();
                        var endTime = Ext.getCmp("endTime").getRawValue();
                        var isTradeClose = Ext.getCmp("is_trade_close").checked;
                        var sku = Ext.getCmp("sku").getRawValue();

                        new_params = {"category1":category,"storeId1":storeId,"startTime1":startTime,"endTime1":endTime,
                            "is_trade_close1":isTradeClose,"sku1":sku};
                        Ext.apply(store.proxy.extraParams, new_params);
                    });

                }
            });
        },

        create_grid:function () {
            return Ext.create('Smartec.page.SaleStructure', {
                id:'saleStructurePageId',
                topBarHight:30,
                autoHeight:true,
                autoWidth:true,
                autoAdded:false,
                autoSetStore:true ,
                closable : true ,
                closeAction: 'hide'
            });

        },

        create_index: function () {
            if (!this.mainTabPanel) {
                var searchForm = Ext.create('Bsh.report.saleStructure.IndexView', {
                    id: 'indexView',
                    plain:true,
                    border:0,
                    bodyPadding:'20px 50px 100px 20px',
                    fieldDefaults:{
                        labelWidth:120,
                        padding:'10px 0 0 0'
                    },
                    items:[
                        {
                            xtype: 'combo',
                            fieldLabel: '品类',
                            emptyText:'请选择...',
                            emptyValue:'',
                            typeAhead : true,
                            name: 'category',
                            id:'category',
                            store: categoryStore,
                            displayField: 'label',
                            valueField: 'value',
                            padding: '0 0 10 0',
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
                            xtype:'combo', name:'storeId', id:'storeId', anchor:'30%', fieldLabel:'商家店铺', store: allBrands,
                            displayField: 'label',
                            valueField: 'value'
                        },
                        {
                            xtype:'datefield', name:'startTime', id:'startTime', fieldLabel:'开始时间', anchor:'30%', style:'margin-top:15px', format:'Y-m-d H:i:s',
                            listeners:{
                                'select': function(v){
                                    Ext.getCmp('endTime').setMinValue(v.getValue());
                                }
                            }
                        },
                        {
                            xtype:'datefield', name:'endTime', id:'endTime', fieldLabel:'结束时间', labelSeparator:'', anchor:'30%', format:'Y-m-d H:i:s',
                            listeners: {
                                'select':function(field,value,eOpts ){
                                    var temp = Ext.Date.add(new Date(this.value),Ext.Date.HOUR,23);
                                    temp = Ext.Date.add(new Date(temp),Ext.Date.MINUTE,59);
                                    temp = Ext.Date.add(new Date(temp),Ext.Date.SECOND,59);
                                    this.setValue(temp);

                                    Ext.getCmp('startTime').setMaxValue(temp);
                                }
                            }

                        },
                        {
                            fieldLabel: '不包含交易关闭',
                            xtype: 'checkbox',
                            name: 'is_trade_close',
                            id: 'is_trade_close'
                        }
                        ,
                        {
                            fieldLabel: 'SKU',
                            xtype: 'textfield',
                            name: 'sku',
                            id: 'sku'
                        }

                    ]
                });
                this.mainTabPanel = Ext.create('Ext.tab.Panel', {
                    id: 'mainTab1',
                    activeTab: 0,
                    autoHeight: true,
                    layout: 'fit',
                    items: [
                        {
                            items: searchForm,
                            title: '销售结构分析查询'
                        }
                    ]
                });
            }

            return this.mainTabPanel;
        }
    };

    var allBrands = Ext.create('Ext.data.Store', {
        fields: ['label', 'value'],
        data: [
            {
                'label': '西门子',
                'value': '22511900'
            },
            {
                'label': '博世',
                'value': '22511920'
            }
        ]
    });

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


    function sand_box() {
        initialize.define();
        var api = {
            createIndex: function () {
                return initialize.create_index();
            }
        }

        return api;
    }

    scope.$SaleStructurePage = sand_box();

})(this);

Ext.onReady(function () {
    Ext.tip.QuickTipManager.init();
    var index = $SaleStructurePage.createIndex();
    Ext.getCmp('center').add(index);
    Ext.getBody().insertHtml('beforeEnd', '<form action="/bsh/sale/downSaleStructure.json" id="queryForm" name = "queryForm" method="post" >' +
        '<input type="hidden" name="category1" id="category1" >' +
        '<input type="hidden" name="storeId1" id="storeId1">' +
        '<input type="hidden" name="startTime1" id="startTime1">' +
        '<input type="hidden" name="endTime1" id="endTime1">' +
        '<input type="hidden" name="is_trade_close1" id="is_trade_close1">' +
        '<input type="hidden" name="sku1" id="sku1">' +
        '</form>')
});

