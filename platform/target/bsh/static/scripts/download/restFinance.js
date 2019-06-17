(function (scope) {
    var _$AlipayQueryReport = scope.$AlipayQueryReport;
    var initialize = {
        define:function () {
            Ext.require([ 'Ext.form.*', 'Ext.Img', 'Ext.tip.QuickTipManager', 'Ext.ux.FieldReplicator' ]);
            Ext.define('Oms.Finance.AlipayQuery.IndexView', {
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
//                        document.getElementById('brand1').value = Ext.getCmp("brand").getValue();
//                        document.getElementById('startTime1').value = Ext.getCmp("startTime").getRawValue();
//                        document.getElementById('endTime1').value = Ext.getCmp("endTime").getRawValue();
//                        document.forms['queryForm'].submit();
//                    }

                    var loadMarsk = new Ext.LoadMask(document.body, {
                        msg : '正在导出，请稍候',
                        removeMask : true// 完成后移除
                    });
                    loadMarsk .show(); //显示
                    Ext.Ajax.request({
                        disableCaching : true,
                        url: path('/finance/generateRestFinance.json'),
                        method: 'POST',
                        params:{
                            brand1 : Ext.getCmp("brand").getValue(),
                            startTime1 : Ext.getCmp("startTime").getRawValue(),
                            endTime1 : Ext.getCmp("endTime").getRawValue()
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
                                window.location.href = path('/finance/exportRestFinance.json?filePath='+ result);
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
                    me.getForm().load({url:path("/operationHistory/lastedOperationLog.json?operationType=DOWNLOAD_REST")});
                }

            });

            Ext.define('Ext.alipayQuery.GridPanel',{
                extend:'Smartec.reportGrid.Panel',
                title: '结果展示',
                model:RestFinanceReport,
                columns: [
                    {xtype: 'rownumberer',text:'序号',width:40,sortable: false},
                    {header: '账务流水号', dataIndex: 'financialSerialNum',sortable: false},
                    {header: '商户订单号', dataIndex: 'orderNum',width:160,sortable: false},
                    {header: '商品名称', dataIndex: 'goodsName',sortable: false},
//                    {header: '发生时间', dataIndex: 'createTime',width : 150,sortable: false, renderer:function (v) {
//                        if(v){
//                            return Ext.Date.format(v, 'Y/m/d H:i:s')
//                        }
//                        return "";
//                    }},
                    {header: '发生时间', dataIndex: 'createTime',sortable: false},
                    {header: '对方账号', dataIndex: 'account',sortable: false},
                    {header: '收入金额', dataIndex: 'inFee',sortable: false},
                    {header: '支出金额', dataIndex: 'outFee',sortable: false},
                    {header: '账户余额',  dataIndex: 'balance',sortable: false},
                    {header: '交易渠道', dataIndex: 'mode',sortable: false},
                    {header: '业务类型', dataIndex: 'type',sortable: false},
                    {header: '备注', dataIndex: 'memo',sortable: false}
                ],
//                exportExcel:function (scope) {
//                    return function () {
//                        document.getElementById('brand1').value = Ext.getCmp("brand").getValue();
//                        document.getElementById('startTime1').value = Ext.getCmp("startTime").getRawValue();
//                        document.getElementById('endTime1').value = Ext.getCmp("endTime").getRawValue();
//                        document.forms['queryForm'].submit();
//                    }
//                },
                initComponent:function () {
                    var me = this;
                    me.topDockedItems = [
                        {
                            xtype:'button',
                            itemId:'btnEdit',
                            height:24,
                            text:'导出',
                            iconCls:'icon-edit',
                            handler:me.exportExcel(this)
                        }
                    ];
                    me.callParent();
                    me.store.on('beforeload', function (store, options) {
                        var brand =  Ext.getCmp("brand").getValue();
                        var startTime = Ext.getCmp("startTime").getRawValue();
                        var endTime = Ext.getCmp("endTime").getRawValue();

                        new_params = {"brand":brand,"startTime":startTime,"endTime":endTime};
                        Ext.apply(store.proxy.extraParams, new_params);
                    });
                }
            });
        },

        create_grid:function () {

            return Ext.create('Ext.alipayQuery.GridPanel', {
                id:'alipayGridId',
                topBarHight:30,
                autoHeight:true,
                autoWidth:true,
                autoAdded:false,
                autoSetStore:true ,
                closable : true ,
                closeAction: 'hide'
            });
        },

        create_index:function () {

            if (!this.mainTabPanel1) {
                var searchForm = Ext.create('Oms.Finance.AlipayQuery.IndexView', {
                    id:'indexView1',
                    plain:true,
                    border:0,
                    bodyPadding:'20px 50px 100px 20px',
                    fieldDefaults:{
                        labelWidth:120,
                        padding:'10px 0 0 0'
                    },
                    reader:new Ext.data.JsonReader({ root:'result', model:'OperationHistory', successProperty:'success'}),
                    items:[
                        {
                            xtype:'combo', name:'brand', id:'brand', anchor:'30%', fieldLabel:'商家店铺', store: allBrands,
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
                                }
                            }

                        },
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
                    ]
                });
                this.mainTabPanel1 = Ext.create('Ext.tab.Panel', {
                    id:'mainTab1',
                    activeTab:0,
                    autoHeight:true,
                    layout:'fit',
                    items:[
                        {
                            items:searchForm,
                            title:'财务Alipay反查(营业外收入)表查询'
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

    function sand_box() {
        initialize.define();
        var api = {
            createIndex:function () {
                return initialize.create_index();
            }
        }

        return api;
    }

    scope.$AlipayQueryReport = sand_box();

})(this);

Ext.onReady(function () {
    Ext.tip.QuickTipManager.init();
    var index = $AlipayQueryReport.createIndex();
    Ext.getCmp('center').add(index);
    Ext.getBody().insertHtml('beforeEnd', '<form action="/bsh/finance/exportRestFinance.json" id="queryForm" name = "queryForm" method="post" >' +
        '<input type="hidden" name="brand1" id="brand1" >' +
        '<input type="hidden" name="startTime1" id="startTime1">' +
        '<input type="hidden" name="endTime1" id="endTime1">' +
        '</form>')
});

