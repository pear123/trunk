(function (scope) {
    var _$SaleStructureReport = scope.$SaleStructureReport;
    var initialize = {
        define:function () {
            Ext.require([ 'Ext.form.*', 'Ext.Img', 'Ext.tip.QuickTipManager', 'Ext.ux.FieldReplicator' ]);
            Ext.define('Bsh.report.saleStructure.IndexView', {
                extend:'Ext.form.Panel',
                model:OperationHistory ,
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
                        xtype:'datefield', name:'startTime', id:'startTime', fieldLabel:'开始时间', anchor:'30%', style:'margin-top:15px', format:'Y-m-d H:i:s'
                    },
                    {
                        xtype:'datefield', name:'endTime', id:'endTime', fieldLabel:'结束时间', labelSeparator:'', anchor:'30%', format:'Y-m-d H:i:s',
                        listeners: {
                            'select':function(field,value,eOpts ){
                                var temp = Ext.Date.add(new Date(this.value),Ext.Date.HOUR,23);
                                temp = Ext.Date.add(new Date(temp),Ext.Date.MINUTE,59);
                                temp = Ext.Date.add(new Date(temp),Ext.Date.SECOND,59);
                                this.setValue(temp);
                            }
                        }

                    },
                    {
                        fieldLabel: '交易是否关闭',
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
                    } ,
                    {
                        xtype:'fieldset',
                        title:'最新下载',
                        margin:'5 5 5 5',
                        defaultType:'displayfield',
                        defaults:{
                            layout:'vbox',
                            border:false,
                            bodyStyle:'padding:4px margin:4px',
                            anchor:'-20'
                        },
                        items:[
                            {
                                fieldLabel:'下载时间',
                                name:'operateTime',
                                setValue:function (v) {
                                    this.value = v;
                                    this.setRawValue(this.renderer(v));
                                    return this;
                                },
                                renderer:function (v) {
                                    if (v == null)
                                        return "";
                                    else
                                        return Ext.Date.format(v, 'Y-m-d H:i:s')
                                }
                            },
                            {
                                fieldLabel:'下载用户',
                                name:'operateOp'
                            }
                        ]
                    }

                ],
                clearAll:function (scope) {
                    return function () {
                        scope.getForm().reset();
                    }
                },
                addExportTab:function (scope) {
                    return function () {
                        var mainTab = Ext.getCmp('mainTab1');

                        if (mainTab) {
                            if(Ext.getCmp("alipayGridId")){
                                var gridStore = Ext.getCmp("alipayGridId").getStore() ;
                                var brand = Ext.getCmp("brand").getValue();
                                var startTime = Ext.getCmp("startTime").getRawValue();
                                var endTime = Ext.getCmp("endTime").getRawValue();

                                new_params = {"store":store,"startTime":startTime,"endTime":endTime};
                                Ext.apply(gridStore.proxy.extraParams, new_params);
                                gridStore.load();

                                mainTab.setActiveTab(1);
                                mainTab.show();
                            }else{
                                mainTab.add(initialize.create_grid()).show();
                            }
                        }
                    }
                },
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
                                var result = Ext.decode(response.responseText).result;
                                if("session-invalidate" == result ){
                                    console.info("session-invalidate")
                                    window.location.href = path("/login");
                                    return false;
                                }
                                window.location.href = path('/sale/downSaleStructure.json?filePath='+result);
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
                    this.dockedItems = [
                        {
                            xtype:'toolbar',
                            dock:'top',
                            items:[
                                {
                                    xtype:'button',
                                    itemId:'btnEdit',
//                                    text:'查询',
                                    text:'导出',
//                                    handler:me.addExportTab(this)
                                    handler:me.exportExcel
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
                    me.getForm().load({url:path("/operationHistory/lastedOperationLog.json?operationType=DOWNLOAD_SALE_STRUCTURE")});

                }

            });


        },

        create_index:function () {

            if (!this.mainTabPanel1) {
                var searchForm = Ext.create('Bsh.report.saleStructure.IndexView', {
                    id:'indexView1',
                    reader:new Ext.data.JsonReader({ root:'result', model:'OperationHistory', successProperty:'success'})
                });
                this.mainTabPanel1 = Ext.create('Ext.tab.Panel', {
                    id:'mainTab1',
                    activeTab:0,
                    autoHeight:true,
                    layout:'fit',
                    items:[
                        {
                            items:searchForm,
                            title:'销售结构分析查询'
                        }
                    ]
                });
            }

            return this.mainTabPanel1;
        }
    }

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
            createIndex:function () {
                return initialize.create_index();
            }
        }

        return api;
    }

    scope.$SaleStructureReport = sand_box();

})(this);

Ext.onReady(function () {
    Ext.tip.QuickTipManager.init();
    var index = $SaleStructureReport.createIndex();
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

