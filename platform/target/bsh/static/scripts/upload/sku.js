(function (scope) {
    var _$AlipayPage = scope.$AlipayPage;

    var Initialize = {

        define:function () {

            Ext.define('Upload.sku.Panel', {
                extend :'Smartec.upload.Panel',
//                labelWidth: 100,
//                width:500,
//                layout:'fit',
//                autoHeight: true,
                model:'OperationHistory',
                id:'upform2',
//                fileUpload:true,
                frame:true,
                autoHeight:true,
                border:0,
                bodyStyle:'padding: 10px 10px 0 10px;',
                labelWidth:40,
//                defaults:{
//                    anchor:'95%',
//                    allowBlank:false,
//                    msgTarget:'side'
//                },
                items:[
                    {
                        xtype: 'fileuploadfield',
                        id: 'form-file',
                        name:'form-file',
                        height:30,
//                        width:'300px',
                        emptyText: '请选择文件...',
                        fieldLabel: '选择文件',
                        name: 'upload', 	//from字段，对应后台java的bean属性，上传的文件字段
                        buttonCfg: {
                            text: '',  // 上传文件时的本地查找按钮
                            iconCls: 'icon-upload'  // 按钮上的图片，定义在CSS中
                        }
                    },
//                    {
//                        xtype : 'label',
//                        id: 'template',
//                        html : '<a href="../omsProduct/productTemplateDownload.do">点此模板下载</a>'
//                    },
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
                                                url: path("/upload/validateSkuUploadCurrentOperation.json"),
                                                method: "post",
                                                dataType: "json",
                                                success:function (data,act) {
                                                    var res = Ext.decode(data.responseText);
                                                    if(res.success){
                                                            form.submit({
                                                                url:path('/upload/doUploadSku.json'),
                                                                method:'POST',
//                                                    contentType:'application/json;charset=utf-8',
//                                                    dataType:'json',
                                                                success:function (action, data) {
                                                                    Ext.Msg.alert('提示信息', '文件成功上传');
                                                                    form.load({url:path("/operationHistory/lastedOperationLog.json?operationType=UPLOAD_SKU")});
//                                                                    form.findField("upload").setRawValue(url);
                                                                },
                                                                failure:function (action, data) {
                                                                    var res = Ext.decode(data.response.responseText);
                                                                    Ext.Msg.alert('提示信息', '文件导入失败:'+res.result);
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
                                                url:path("/sku/doDeleteSkuCurrentOperationByUser.json"),
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
//                buttons: [{
//                    text: '确定',
//                    handler: function(){
//                        var form = this.up('form').getForm();
//                        console.info(form.findField('fileName'));
//                        if(form.isValid()){
//                            form.submit({
//                                url: path('/upload/doUploadAlipay.json'),
////                                waitMsg: 'Uploading files...',
////                                success: function(fp, o) {
////                                    msg('Success', 'Processed file "' + o.result.file + '" on the server');
////                                }
//                                waitMsg : '正在保存文件...',
//                                success : function(json, action) {
//
//                                    Ext.Msg.alert('提示信息', '文件成功上传');
////                                    form.findField('fileName').reset();
//                                },
//                                failure : function() {
//                                    Ext.Msg.alert('警告', '对不起,文件上传无法成功.');
//                                }
//                            });
//                        }
//                    }
//                },{
//                    text: '取消',
//                    handler: function() {
//                        this.up('form').getForm().reset();
//                    }
//                }]
               initComponent:function () {
                    var me = this;
                    me.callParent();
                    me.getForm().load({url:path("/operationHistory/lastedOperationLog.json?operationType=UPLOAD_SKU")});
                }
            });


            /***
             * Title:define upload alipay history grid
             * Author tracy
             * Date:2015/08/03
             */
//            Ext.define('Upload.alipay.history.Panel', {
//                extend :'Smartec.grid.Panel',
//                labelWidth: 100,
//                layout:'fit',
//                model:'OperationHistory',
//                columns:[
//                    {xtype:'rownumberer', text:'序号', width:40, sortable:false},
//                    {text:'id', dataIndex:'id', hidden:true},
//                    {text:'上传文件名', width:160, sortable:true, dataIndex:'fileName'},
//                    {text:'上传时间', width:150, sortable:true, dataIndex:'operateTime', renderer:function (v) {
//                        if (v) {
//                            return Ext.Date.format(v, 'Y-m-d H:i:s')
//                        } else {
//                            return ""
//                        }
//                    }},
//                    {text:'上传用户', width:130, sortable:false, dataIndex:'operateOp'}
//                ]
//
//            });


        }
        ,
        /***
         * create upload form
         * @return {*}
         */
        create_form:function(){
            if (!this.upload_form) {
                this.upload_form = Ext.create('Upload.sku.Panel', {
                    id:'uploadForm'
                });
            }
            return this.upload_form;
        },
//        ,
        /***
         * create history form
         * @return {*}
         */
//        create_history_form:function(){
//            if (!this.history_form) {
//                this.history_form = Ext.create('Upload.alipay.history.Panel', {
//                    autoScroll:true,
//                    id:'uploadHistoryForm',
//                    autoHeight:true,
//                    autoWidth:true,
//                    autoAdded:false,
//                    topBarAutoShow:false,
//                    autoLoadStore:false
//                });
//            }
//            return this.history_form;
//        }               /***

        create_form:function(){
            if (!this.upload_form) {

                this.upload_form = Ext.create('Upload.sku.Panel', {
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
//            ,
//            createUploadHistoryForm:function(){
//                return Initialize.create_history_form();
//            }
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
//                layout:'fit',
//                width:'75%',
                items:$AlipayPage.createForm()
//            },
//            {
//                xtype:'panel',
//                region:'center',
//                layout:'fit',
//                width:'25%',
//                split:true,
//                collapsible:true,
//                items:$AlipayPage.createUploadHistoryForm()
//            },
//            {
//                xtype:'panel',
//                region:'south',
//                layout:'fit',
//                hidden:true,
//                border:0
            }
        ]});
    //add By tracy start
    var mainTabPanel = Ext.create('Ext.tab.Panel', {
        id:'mainTab',
        activeTab:0,
        autoHeight:true,
        items:[
            {
                title:'SKU数据管理',
                layout:'fit',
                items:centerPanel
            }
        ]
    });
    //add by tracy end
    Ext.getCmp('center').add(mainTabPanel);
});