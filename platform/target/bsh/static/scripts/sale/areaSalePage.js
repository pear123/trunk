(function (scope) {
    var _$AreaSalePage = scope.$AreaSalePage;
    var Initialize = {
        define:function () {
            Ext.require([ 'Ext.form.*', 'Ext.Img', 'Ext.tip.QuickTipManager', 'Ext.ux.FieldReplicator' ]);

            Ext.define('Bsh.report.areaSale.IndexView', {
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
                            if(Ext.getCmp("areaSalePageId")){
                                var gridStore = Ext.getCmp("areaSalePageId").getStore() ;
                                var summaryGridStore = Ext.getCmp("areaSalePageSummaryId").getStore() ;

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
                                Ext.apply(summaryGridStore.proxy.extraParams, new_params);
                                summaryGridStore.load();

                                mainTab.setActiveTab(1);
                                mainTab.show();
                            }else{
                                var gridpanel = Initialize.create_gridBoth();
                                var gridStore = Ext.getCmp("areaSalePageId").getStore() ;
                                gridStore.on('load', function (store,records, options) {
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
//                                    text:'导出',
                                    handler:me.addExportTab(this)
//                                    handler:me.exportExcel(this)
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

            Ext.define('Smartec.page.AreaSale', {
                extend:'Smartec.reportGrid.Panel',
                model:AreaSalePage,
                columns:[
                    {xtype:'rownumberer', text:'序号', width:40, sortable:false},
                    {text:'品类', width:45, sortable:true, dataIndex:'cid'},
                    {text:'型号', width:90, sortable:false, dataIndex:'matrn'},
                    { text:'SAP价', width:45, sortable:false,hidden:true, dataIndex:'price'},
                    { text:' ', sortable:false,hidden:true, dataIndex:'quantity'},
                    { text:'<span style=color:red>浙江</span>', width:46, sortable:true, dataIndex:'pzj',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    { text:'<span style=color:red>北京</span>', width:46, sortable:true, dataIndex:'pbj',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'<span style=color:red>广东</span>', width:46, sortable:true, dataIndex:'pgd',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'<span style=color:red>湖北</span>', width:46, sortable:true, dataIndex:'phb',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'<span style=color:red>四川</span>', width:46, sortable:true, dataIndex:'psc',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'成都', width:30, sortable:true, dataIndex:'cd',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'西安', width:30, sortable:true, dataIndex:'xa',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    { text:'兰州', width:30, sortable:true, dataIndex:'lz',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'昆明', width:30, sortable:true, dataIndex:'km',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'贵阳', width:30, sortable:true, dataIndex:'gy',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'重庆', width:30, sortable:true, dataIndex:'cq',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'武汉', width:30, sortable:true, dataIndex:'wh',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'南昌', width:30, sortable:true, dataIndex:'nc',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'郑州', width:30, sortable:true, dataIndex:'zz',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    {text:'长沙', width:30, sortable:true, dataIndex:'cs',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    {text:'北京', width:30, sortable:true, dataIndex:'bj',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    {text:'太原', width:30, sortable:true, dataIndex:'ty',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    { text:'济南', width:30, sortable:true, dataIndex:'jn',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'石家庄', width:30, sortable:true, dataIndex:'sjz',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    { text:'沈阳', width:30, sortable:true, dataIndex:'sy',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    { text:'长春', width:30, sortable:true, dataIndex:'cc',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'哈尔滨', width:30, sortable:true, dataIndex:'heb',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'杭州', width:30, sortable:true, dataIndex:'hz',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'合肥', width:30, sortable:true, dataIndex:'hf',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    { text:'上海', width:30, sortable:true, dataIndex:'sh',renderer:function(v){if(0 ==v){return ''}else{return v}}},
                    { text:'南京', width:30, sortable:true, dataIndex:'nj',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'无锡', width:30, sortable:true, dataIndex:'wx',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'宁波', width:30, sortable:true, dataIndex:'nb',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'佛山', width:30, sortable:true, dataIndex:'fs',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'南宁', width:30, sortable:true, dataIndex:'nn',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'福州', width:30, sortable:true, dataIndex:'fz',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'深圳', width:30, sortable:true, dataIndex:'sz',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'TTL', width:30, sortable:true, dataIndex:'ttl',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'总金额', width:60, sortable:true, dataIndex:'priceTotal',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'品类数量', width:60, sortable:true, dataIndex:'cidQuantity',renderer:function(v){if(0 ==v){return ''}else{return v}} },
                    { text:'品类金额', width:60, sortable:true, dataIndex:'cidPrice',renderer:function(v){if(0 ==v){return ''}else{return v}} } ,
                    { text:'数量占比', width:70, sortable:false, dataIndex:'quantityPercent'},
                    { text:'金额占比', width:70, sortable:false, dataIndex:'pricePercent'}
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
                        url: path('/download/generateAreaSale.json'),
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
                                window.location.href = path('/download/downAreaSale.json?filePath='+Ext.decode(response.responseText).result);
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
                    me.setStore();
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
                    me.on('afterrender', function(grid,eOpts) {
                        grid.view.getEl().on('scroll', function(e, t) {
                            //console.log("scroll", t.scrollLeft);
                            var el1 = Ext.getCmp('areaSalePageId').body.dom;
                            var el2 = Ext.getCmp('areaSalePageSummaryId').body.dom;
                            el1.scrollLeft = el2.scrollLeft= t.scrollLeft;
                            //Ext.getCmp('areaSalePageSummaryId').scrollBy(t.scrollLeft, 0, true);
                        });
                    }, this);
                },
                setStore:function () {
                    var me = this;
                    if (me.autoSetStore) {
                        me.store = Ext.create('Ext.data.Store', {
                            model:me.model,
                            autoLoad:this.autoLoadStore,
                            remoteSort:false
                        });
                    }
                },
                listeners: {
                    //'scroll':function(e){
                    //    var el1 = Ext.getCmp('areaSalePageId').body.dom;
                    //    var el2 = Ext.getCmp('areaSalePageSummaryId').body.dom;
                    //            el2.scrollLeft = el1.scrollLeft;
                    //}
                    render: function (p) {
                        p.body.on('scroll', function (e) {
                            Ext.Msg.alert("tips","====");
                            var el1 = Ext.getCmp('areaSalePageId').body.dom;
                            var el2 = Ext.getCmp('areaSalePageSummaryId').body.dom;
                            el2.scrollLeft = el1.scrollLeft;
                            //el1.scrollTop = el2.scrollTop;
                        },this,{buffer :100});

            //    //render:function(component){
                    //    //    component.body.on('scroll', function(e){
                    //    //        console.log("##--------scroll");
                    //    //    });
                    //    //}
                    }
                }
            });

            Ext.define('Smartec.page.AreaSaleSummary', {
                extend:'Smartec.reportGrid.Panel',
                model:AreaSalePageSummary,
                hideHeaders :true,
                columns:[
                    {xtype:'rownumberer', text:'序号', width:40, sortable:false},
                    {text:'品类', width:45, sortable:true, dataIndex:'cid'},
                    {text:'型号', width:50, sortable:true, dataIndex:'matrn'},
//                    { text:'SAP价', width:45, sortable:false, dataIndex:'price'},
                    { text:' ', width:40, sortable:true, dataIndex:'quantity'},
                    { text:'浙江', width:46, sortable:true, dataIndex:'pzj', renderer:function(v){if(null ==v ||''==v ){return 0}else{return v}}},
                    { text:'北京', width:46, sortable:true, dataIndex:'pbj', renderer:function(v){if(null ==v ||''==v ){return 0}else{return v}} },
                    { text:'广东', width:46, sortable:false, dataIndex:'pgd', renderer:function(v){if(null ==v ||''==v ){return 0}else{return v}} },
                    { text:'湖北', width:46, sortable:false, dataIndex:'phb', renderer:function(v){if(null ==v ||''==v ){return 0}else{return v}} },
                    { text:'四川', width:46, sortable:false, dataIndex:'psc', renderer:function(v){if(null ==v ||''==v ){return 0}else{return v}} },
                    { text:'成都', width:30, sortable:false, dataIndex:'cd', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'西安', width:30, sortable:false, dataIndex:'xa', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'兰州', width:30, sortable:false, dataIndex:'lz', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'昆明', width:30, sortable:false, dataIndex:'km', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'贵阳', width:30, sortable:false, dataIndex:'gy', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'重庆', width:30, sortable:false, dataIndex:'cq', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'武汉', width:30, sortable:false, dataIndex:'wh', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'南昌', width:30, sortable:false, dataIndex:'nc', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'郑州', width:30, sortable:true, dataIndex:'zz', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    {text:'长沙', width:30, sortable:true, dataIndex:'cs', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    {text:'北京', width:30, sortable:true, dataIndex:'bj', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    {text:'太原', width:30, sortable:true, dataIndex:'ty', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'济南', width:30, sortable:true, dataIndex:'jn', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'石家庄', width:30, sortable:true, dataIndex:'sjz', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'沈阳', width:30, sortable:true, dataIndex:'sy', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'长春', width:30, sortable:true, dataIndex:'cc', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'哈尔滨', width:30, sortable:false, dataIndex:'heb', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'杭州', width:30, sortable:false, dataIndex:'hz', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'合肥', width:30, sortable:false, dataIndex:'hf', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'上海', width:30, sortable:true, dataIndex:'sh', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'南京', width:30, sortable:true, dataIndex:'nj', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'无锡', width:30, sortable:true, dataIndex:'wx', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'宁波', width:30, sortable:true, dataIndex:'nb', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'佛山', width:30, sortable:true, dataIndex:'fs', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'南宁', width:30, sortable:true, dataIndex:'nn', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'福州', width:30, sortable:true, dataIndex:'fz', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'深圳', width:30, sortable:true, dataIndex:'sz', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'TTL', width:60, sortable:true, dataIndex:'ttl', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'总金额', width:60, sortable:true, dataIndex:'priceTotal', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'品类数量', width:60, sortable:true, dataIndex:'cidQuantity', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'品类金额', width:60, sortable:true, dataIndex:'cidPrice', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'数量占比', width:70, sortable:true, dataIndex:'quantityPercent', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}},
                    { text:'金额占比', width:70, sortable:true, dataIndex:'pricePercent', renderer:function(v,p,record){if(record.get('quantity')=='数量'){return v}else{return ''}}}
                ],
                initComponent:function () {
                    var me = this;
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
//        create_grid:function () {
//            return Ext.create('Smartec.page.AreaSale', {
//                    id:'areaSalePageId' ,
//                    topBarHight:30,
//                    autoHeight:true,
//                    autoWidth:true,
//                    autoAdded:false,
//                    autoSetStore:true
//                });
//        },

//        create_summaryGrid:function () {
//            return Ext.create('Smartec.page.AreaSaleSummary', {
//                    id:'areaSalePageSummaryId',
//                    topBarHight:30,
//                    autoHeight:true,
//                    autoWidth:true,
//                    autoAdded:false,
//                    autoSetStore:true
//                })
//
//        },

        create_gridBoth :function() {
//            if(!this.bothGridPanel) {
                var areaSalePanel = Ext.create('Smartec.page.AreaSale', {
                    id:'areaSalePageId' ,
                    topBarHight:30,
                    autoHeight:true,
                    autoWidth:true,
                    autoAdded:false,
                    autoSetStore:true,
                    autoScroll:true
                });

                var areaSaleSummaryPanel = Ext.create('Smartec.page.AreaSaleSummary', {
                    id:'areaSalePageSummaryId',
                    topBarHight:0,
                    autoHeight:true,
                    autoWidth:true,
                    autoAdded:false,
                    autoSetStore:true,
                    autoScroll:true
                });
                return Ext.create('Ext.panel.Panel', {
                    title: '结果展示',
                    layout:'border',
                    id:'bothGridId',
                    defaults:{
                        split:true
                    },
                    closable:true,
                    closeAction:'hide',
                    items:[
                        {
                            xtype:'panel',
                            region:'north',
                            layout:'fit',
                            height:'75%',
                            items:areaSalePanel
                        },
                        {
                            xtype:'panel',
                            region:'center',
                            layout:'fit',
                            height:'25%',
                            split:true,
                            items:areaSaleSummaryPanel
                        }
                ]
                });
//            }
            return this.bothGridPanel;
        },

        create_index: function () {
            if (!this.mainTabPanel) {
                var searchForm = Ext.create('Bsh.report.areaSale.IndexView', {
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
                            title: '27仓销售统计(3W)表查询'
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


    function create_sandbox() {
        Initialize.define();
        var areaSaleAPI = {
            createIndex:function () {
                return  Initialize.create_index();
            }
        }

        return areaSaleAPI;
    }
    scope.$AreaSalePage = create_sandbox();

})(this);

Ext.onReady(function () {
    Ext.tip.QuickTipManager.init();
    var index = $AreaSalePage.createIndex();
    Ext.getCmp('center').add(index);
    Ext.getBody().insertHtml('beforeEnd', '<form action="/bsh/download/downAreaSale.json" id="queryForm" name = "queryForm" method="post" >' +
        '<input type="hidden" name="category1" id="category1" >' +
        '<input type="hidden" name="storeId1" id="storeId1">' +
        '<input type="hidden" name="startTime1" id="startTime1">' +
        '<input type="hidden" name="endTime1" id="endTime1">' +
        '<input type="hidden" name="is_trade_close1" id="is_trade_close1">' +
        '<input type="hidden" name="sku1" id="sku1">' +
        '</form>')
});