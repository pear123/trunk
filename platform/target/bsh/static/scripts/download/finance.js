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
                        scope.getForm().findField("alipayStartTime").reset();
                        scope.getForm().findField("alipayEndTime").reset();
                    }
                },
                addExportTab:function (scope) {

                },
                exportExcel:function () {
//                    return function () {
//                        document.getElementById('brandParam').value = Ext.getCmp("brand").getValue();
//                        document.getElementById('orderNumParam').value = Ext.getCmp("orderNum").getValue();
//                        document.getElementById('startTimeParam').value = Ext.getCmp("startTime").getRawValue();
//                        document.getElementById('endTimeParam').value = Ext.getCmp("endTime").getRawValue();
//                        document.forms['queryForm'].submit();
//                    }

                    var loadMarsk = new Ext.LoadMask(document.body, {
                        msg : '正在导出，请稍候',
                        removeMask : true// 完成后移除
                    });
                    loadMarsk .show(); //显示
                    Ext.Ajax.request({
                        disableCaching : true,
                        url: path('/order/generateFinanceReport.json'),
                        method: 'POST',
                        params:{
                            brandParam : Ext.getCmp("brand").getValue(),
                            orderNumParam : Ext.getCmp("orderNum").getValue(),
                            startTimeParam : Ext.getCmp("startTime").getRawValue(),
                            endTimeParam : Ext.getCmp("endTime").getRawValue()  ,
                            alipayStartTime1 : Ext.getCmp("alipayStartTime").getRawValue(),
                            alipayEndTime1 : Ext.getCmp("alipayEndTime").getRawValue()
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
                                window.location.href = path('/order/exportFinanceReport.json?filePath='+result);
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
                    me.getForm().load({url:path("/operationHistory/lastedOperationLog.json?operationType=DOWNLOAD_FINANCE")});
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
                            xtype:'textfield', name:'orderNum', id:'orderNum', anchor:'30%', fieldLabel:'订单号',regex:/^\d*$/, regexText:"必须为数字"
                        }
                        ,
                        {
                            xtype:'datefield', name:'startTime',id:'startTime', fieldLabel:'订单的创建时间', anchor:'30%', style:'margin-top:15px', format:'Y-m-d H:i:s',
                            listeners:{
                                'select': function(v){
                                    Ext.getCmp('endTime').setMinValue(v.getValue());
                                }
                            }
                        },
                        {
                            xtype:'datefield', name:'endTime',id:'endTime', fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;', labelSeparator:'', anchor:'30%', format:'Y-m-d H:i:s',
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
                            xtype:'datefield', name:'alipayStartTime',id:'alipayStartTime', fieldLabel:'支付到账时间', anchor:'30%', style:'margin-top:15px', format:'Y-m-d H:i:s',
                            listeners:{
                                'select': function(v){
                                    Ext.getCmp('alipayEndTime').setMinValue(v.getValue());
                                }
                            }
                        },
                        {
                            xtype:'datefield', name:'alipayEndTime',id:'alipayEndTime', fieldLabel:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;', labelSeparator:'', anchor:'30%', format:'Y-m-d H:i:s',
                            listeners: {
                                'select':function(field,value,eOpts ){
                                    var temp = Ext.Date.add(new Date(this.value),Ext.Date.HOUR,23);
                                    temp = Ext.Date.add(new Date(temp),Ext.Date.MINUTE,59);
                                    temp = Ext.Date.add(new Date(temp),Ext.Date.SECOND,59);
                                    this.setValue(temp);

                                    Ext.getCmp('alipayStartTime').setMaxValue(temp);
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
                            title:'财务入账(销售款)表查询'
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
});

