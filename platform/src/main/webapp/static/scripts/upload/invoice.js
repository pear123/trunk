(function (scope) {
    var _$AlipayPage = scope.$AlipayPage;

    var Initialize = {

         define:function () {

            Ext.define('Upload.invoice.Panel', {
                extend :'Smartec.upload.Panel',
//                id:'upform2',
//                frame:true,
//                autoHeight:true,
//                border:0,
                bodyStyle:'padding: 10px 10px 0 10px;',
                labelWidth:40,
                model:OperationHistory,
//                defaults:{
//                    anchor:'95%',
//                    allowBlank:false,
//                    msgTarget:'side',
//                    width:'300px'
//                },
                items:[
                    {
                        xtype: 'fileuploadfield',
                        id: 'form-file',
                        height:30,
//                        width:'300px',
                        emptyText: '请选择文件...',
                        fieldLabel: '选择文件',
                        name: 'upload',
                        buttonCfg: {
                            text: '',
                            iconCls: 'icon-upload'
                        }
                    },
                    {
                        xtype:'fieldset',
                        title:'最新上传',
                        margin:'5 5 5 5',
                        width:350,
                        height:150,
                        defaultType:'displayfield',
                        defaults:{
                            layout:'vbox',
                            border:false,
                            bodyStyle:'padding:4px margin:4px',
                            anchor:'-20'
                        },
                        items:[
                            {
                                fieldLabel:'上传文件',
                                name:'fileName'
                            },
                            {
                                fieldLabel:'上传时间',
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
                                fieldLabel:'上传用户',
                                name:'operateOp'
                            }
                        ]
                    }
                    ,
                    {
                        xtype:'fieldcontainer',
                        items:[
                            {
                                border:0,
                                buttons:[
                                    {
                                        text:'导入',
                                        handler:function () {
                                            var form = this.up('form').getForm();
//                                            if (form.isValid()) {
                                            var url = form.findField("upload").getRawValue();
                                            if(""!=url){
                                                Ext.Ajax.request({
                                                        url: path("/upload/validateInvoiceUploadCurrentOperation.json"),
                                                        method: "post",
                                                        dataType: "json",
                                                        success:function (data,act) {
                                                            var res = Ext.decode(data.responseText);
                                                            if(res.success){
                                                                form.submit({
                                                                    url:path('/upload/doUploadInvoice.json'),
                                                                    method:'POST',
//                                                    contentType:'application/json;charset=utf-8',
//                                                    dataType:'json',
                                                                    success:function (action, data) {
                                                                        Ext.Msg.alert('提示信息', '文件成功上传');
                                                                        form.load({url:path("/operationHistory/lastedOperationLog.json?operationType=UPLOAD_INVOICE")});
//                                                                        form.findField("upload").setRawValue(url);
                                                                    },
                                                                    failure:function (action, data) {
                                                                        var res = Ext.decode(data.response.responseText);
                                                                        Ext.Msg.alert('提示信息',  '文件导入失败:'+res.result);
                                                                    },
                                                                    waitTitle:Ext.MessageBox.wait('正在导入，请等待...', '提示')
                                                                });
                                                            }else{
                                                                var res = Ext.decode(data.responseText);
                                                                Ext.Msg.alert('提示信息', res.result);
                                                            }
                                                        },
                                                    failure:function (action, data) {
                                                        Ext.Msg.alert('提示信息', '请求失败,请稍后再试');
                                                    }
                                                });
                                            } else {
                                                Ext.Msg.alert('提示信息', '请选择导入文件！');
                                            }
                                        }
                                    },
                                    {
                                        text:'重置',
                                        handler:function () {
                                           this.up('form').getForm().findField('form-file').reset();
                                        }
                                    },
                                    {
                                        text:'解锁',
                                        handler:function () {
                                            Ext.Ajax.request({
                                                url:path("/upload/doDeleteInvoiceCurrentOperationByUser.json"),
                                                method:"post",
                                                dataType: "json",
                                                success:function (data,act) {
                                                    var res = Ext.decode(data.responseText);
                                                    Ext.Msg.alert('提示信息', res.result);
                                                }
                                            });
                                        }
                                    }
                                ]
                            }
                        ]
                    }
                ],
                initComponent:function () {
                    var me = this;
                    me.callParent();
                    me.getForm().load({url:path("/operationHistory/lastedOperationLog.json?operationType=UPLOAD_INVOICE")});
                }
            });
        }
        ,
        /***
         * create upload form
         * @return {*}
         */
        create_form:function(){
            if (!this.upload_form) {
                this.upload_form = Ext.create('Upload.invoice.Panel', {
                    id:'uploadForm',
                    reader:new Ext.data.JsonReader({ root:'result', model:'OperationHistory', successProperty:'success'})
                });
            }
            return this.upload_form;
        }

    }

    function create_sandbox() {
        Initialize.define();
        var uploadAPI = {
            createForm:function () {
                return  Initialize.create_form();
            }
        }
        return uploadAPI;
    }

    scope.$AlipayPage = create_sandbox();
})(this);

Ext.onReady(function () {
    var centerPanel = Ext.create('Ext.panel.Panel', {
//        layout:'border',
        layout:'fit',
        height:'100%',
        defaults:{
            split:true
        },
        items:[
            {
                xtype:'panel',
                region:'center',
                //collapsible:true,
                items:$AlipayPage.createForm()
            }
        ]});
    //add By tracy start
    var mainTabPanel = Ext.create('Ext.tab.Panel', {
        id:'mainTab',
        activeTab:0,
        autoHeight:true,
        items:[
            {
                title:'发票数据管理',
                layout:'fit',
                items:centerPanel
            }
        ]
    });
    //add by tracy end
    Ext.getCmp('center').add(mainTabPanel);
});