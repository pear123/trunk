(function (scope) {
    var _$Order = scope.$Order;

    var Initialize = {
        define:function(){

            var uploadFieldTypeCombo = Ext.create('Ext.data.Store', {
                fields:['label', 'value'],
                data:[
                    {'value':'F1', 'label':'Alipay数据上传'},
                    {'value':'F2', 'label':'sku数据上传'},
                    {'value':'F3', 'label':'物料数据上传'},
                    {'value':'F4', 'label':'customerID上传'},
                    {'value':'F5', 'label':'发货单数据上传'},
                    {'value':'F6', 'label':'发票数据上传'},
                    {'value':'F7', 'label':'coupon数据上传'}
                ]
            });

            Ext.define('Upload.form.Panel', {
                extend :'Smartec.form.Panel',
//                bodyStyle: 'border-width:0 0 1px 0;',
                border:'#000000 1px solid',
                width: 500,
                frame: true,
//                bodyPadding: '10 10 0',
                autoHeight: true,
                labelWidth: 100,
                defaults: {
                    anchor: '95%',
                    allowBlank: false,
                    msgTarget: 'side',
                    margin:'10'
                },
                items: [
                {
                    xtype:'combo',
                    id:'uploadFieldType',
                    name:'uploadFieldType',
                    fieldLabel:'上传类型',
                    store:uploadFieldTypeCombo,
//                    allowBlank:false,
                    displayField:'label',
                    valueField:'value',
                    editable:false,
                    value:''
//                    margin:'10'
//                    flex:1,
//                    columnWidth:'60%',
//                    anchor:'100%'
                },
//                {
//                    xtype: 'textfield',
//                    fieldLabel: 'Name'
//                },
                {
                    xtype: 'filefield',
//                    id: 'form-file',
                    emptyText: 'Select a file',
                    fieldLabel: '选择文件',
//                    name: 'photo-path',
                    buttonText: '选择'/*,
                    buttonConfig: {
                        iconCls: 'upload-icon'
                    }*/
                }],

                buttons: [{
                    text: '确定',
                    handler: function(){
                        var form = this.up('form').getForm();
                        if(form.isValid()){
                            form.submit({
                                url: 'file-upload.php',
                                waitMsg: 'Uploading your files...',
                                success: function(fp, o) {
                                    msg('Success', 'Processed file "' + o.result.file + '" on the server');
                                }
                            });
                        }
                    }
                },{
                    text: '取消',
                    handler: function() {
                        this.up('form').getForm().reset();
                    }
                }] ,

                constructor:function () {
                    this.callParent(arguments);
                },

                initComponent:function () {
                    var me = this;
//                    if (me.tab) {
//                        me.setTabPanel();
//                    }
                    me.callParent();
                }
            });
        },

        create_form:function () {
            if (!this.upload_form) {
                this.upload_form = Ext.create('Upload.form.Panel', {
                    topBarHight:30,
                    id:'uploadFormId'
//                    autoHeight:true,
//                    autoWidth:true,
//                    autoAdded:false,
//                    autoSetStore:true
//                    features:[
//                        {
//                            ftype:'filters', encode:true, local:false, menuFilterText:messages['ext.filters.menuFilterText'],
//                            filters:Initialize.init_filters()
//                        }
//                    ],
//                    viewConfig:{
//                        stripeRows:false,
//                        getRowClass:function (record, rowIndex, rowParams, store) {
//                            if (record.data.orderInternalStatus == "EXCEPTION") {
//                                return "x-row-blue";
//                            }
//                        }
//                    }
                });
            }

            return this.upload_form;
        }

    };

    function create_sandbox() {
        Initialize.define();
        var uploadAPI = {
            createForm:function () {
                return  Initialize.create_form();
            }
        }

        return uploadAPI;
    }

    scope.$Order = create_sandbox();
})(this);

Ext.onReady(function () {
    var centerPanel = Ext.create('Ext.panel.Panel', {
        layout:'border',
//        borderStyle:'#000000 1px solid',
        height:'100%',
        defaults:{
            split:true
        },
        items:[
            {
                xtype:'panel',
                region:'north',
//                collapsible:true,
                width:500,
                layout:'fit',
                height:'50%',
//                height:'100%',
                items:$Order.createForm()
            },
            {
                xtype:'panel',
//                region:'south',
                region:'center',
                layout:'fit',
                hidden:true,
                border:0
            }
        ]});

    var mainTabPanel = Ext.create('Ext.tab.Panel', {
        id:'mainTab',
        activeTab:0,
        autoHeight:true,
        items:[
            {
                title:'上传管理',
                layout:'fit',
                items:centerPanel
            }
        ]
    });

    console.info(Ext.getCmp('center'));
    Ext.getCmp('center').add(mainTabPanel);
});