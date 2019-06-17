/*
 * Copyright (c) 2000-2011 arvato systems (Shanghai)
 * $Id: order-grid.js 2011-06-01 14:15:04 by Allen $
 */

function converterOrderStatus(value, p, record) {
    return messages['com.arvato.smartec.Constants.OrderStatus.'+value+'.message'];
}

function converterPayStatus(value, p, record) {
    return messages['com.arvato.smartec.Constants.PayStatus.'+value+'.message'];
}
//var allform=null;
//var code = "";
Ext.define('Smartec.resource.ResourceGrid', {
    alias: 'widget.resourcegrid',
    extend: 'Smartec.grid.Panel',
    model: 'Resource',
    columns: [{
        text: 'id',
        dataIndex: 'resourceSysId',
        hidden: true
    },
    /*{
        text: '客户编号',
        width: 200,
 		sortable: false,
        dataIndex: 'omsCustomerId'
    },*/
    {
        text: '资源名称',
        width: 400,
 		sortable: false,
        dataIndex: 'resourceName'
    },
    {
        text: '资源定义',
        width: 400,
 		sortable: false,
        dataIndex: 'resourcePattern'
    },
    {
        text: '资源状态',
        width: 120,
 		sortable: false,
        dataIndex: 'resourceStatus'
    },
    {
        text: '资源描述',
        width: 400,
 		sortable: false,
        dataIndex: 'resourceDescription'
    }
    ],
    
	//添加资源
      addResourceHandle:function(){
		var grid = this.up('gridpanel');
		if(Ext.getCmp('addresourceWindow')){
			Ext.getCmp('addresourceWindow').show();
			var form = Ext.getCmp('allform').getForm();
			var fields = form.getFields();
			form.reset();
			for(var i = 0, size = fields.length; i < size; i++){
				fields.getAt(i).clearInvalid();
			}
		}else{
    	   	 Ext.create('Ext.Window', {
		     title:'添加资源',
		     width:600,
		     height:300,
//		     layout: 'fit',
		     autoScroll:true,
		     modal:true,
		     constrain:true,
		     resizable:true,
		     maximizable: true, 
		     id:'addresourceWindow',
		     /*listeners : {
		     	hide : function(win)
		     	{
		     		var form = Ext.getCmp('allform').getForm();
		     		form.clearInvalid();
		     		form.reset();
		     	}
		     },*/
		     closeAction:'hide',
		   	 items: [
		   		allform= new Ext.form.FormPanel({
			    bodyPadding: 20,
			    id:'allform',
			    items: [
//			    {
//	                fieldLabel: '子系统<span style=color:red>*</span>',
//                   	xtype: 'combo',
//	                id: 'omsSubSystem',
//	                name: 'omsSubSystem',
//	                store:resourceSubSystemList,
//	                width:300,
//	                displayField: 'label',
//	                valueField: 'value',
//	                queryMode: 'local',
//	                editable: false,
//	                value:'',
//	                padding:'0 0 5 0',
//	                emptyText:'子系统',
//	            	emptyValue:''
//         		 },{
//	                fieldLabel: '模块<span style=color:red>*</span>',
//                   	xtype: 'combo',
//	                id: 'omsModule',
//	                name: 'omsModule',
//	                store:resourceModelList,
//	                width:300,
//	                displayField: 'label',
//	                valueField: 'value',
//	                queryMode: 'local',
//	                editable: false,
//	                value:'',
//	                padding:'0 0 5 0',
//	                emptyText:'模块',
//	            	emptyValue:''
//
//         		 },{
//	                fieldLabel: '子模块<span style=color:red>*</span>',
//                   	xtype: 'combo',
//	                id: 'omsSubModule',
//	                name: 'omsSubModule',
//	                store:resourceSubModelList,
//	                width:300,
//	                displayField: 'label',
//	                valueField: 'value',
//	                queryMode: 'local',
//	                editable: false,
//	                value:'',
//	                padding:'0 0 5 0',
//	                emptyText:'子模块',
//	            	emptyValue:''
//         		 },
			    {
	                fieldLabel: '资源名称 <span style=color:red>*</span>',
	                xtype:'textfield',
	                id:'resourceName',
	                name:'resourceName',
	                padding:'0 0 5 0',
	                allowBlank:false, //不允许为空
					blankText:"不能为空",
					width:300,
					maxLength : 50,
					maxLengthText : '最大长度不能超过50个字符!'
					/*regex:/^[0-9|a-z|_|A-Z]+$/,
					regexText:"资源定义ID是无效的"*/
         		 },
			    {
	                xtype:'displayfield',
	                value: '不超过50个字节（数字，字母和下划线组成 ）',
	                padding:'0 0 5 0'
         		 },
			    {
	                fieldLabel: '资源定义 <span style=color:red>*</span>',
	                xtype:'textfield',
	                id:'resourcePattern',
	                name:'resourcePattern',
	                padding:'0 0 5 0',
	                allowBlank:false, //不允许为空
					blankText:"不能为空",
					width:300,
					maxLength : 1000,
					maxLengthText : '最大长度不能超过1000个字符!'
         		 },
			    {
	                xtype:'displayfield',
	                padding:'0 0 5 0',
	                value: '不超过1000个字符'
         		 },
			    {
	                fieldLabel: '资源描述',
                   	xtype: 'textarea',
	                id: 'resourceDescription',
	                name: 'resourceDescription',
	                width:500,
	                height:80,
	                maxLength:300,
	                maxLengthText : '资源描述的长度不能超过300字符',
	                padding:'0 0 5 0'
         		 },
		          {
		            xtype      : 'fieldcontainer',
		            fieldLabel : '状态',
		            width:300,
		            defaultType: 'radiofield',
		            padding:'0 0 10 0',
		            defaults: {
		                flex: 1
		            },
		            layout: 'hbox',
		            items: [
		                {
		                    boxLabel  : '启用',
		                    name: 'size',
		                    inputValue: '1',
		                    id : 'resourceStatus',
		                    checked:true
		                }, {
		                    boxLabel  : '禁用',
		                    name : 'size',
		                    inputValue: '0'
		                }
		            ]
		        },
			    {
			        xtype: 'fieldcontainer',
			        layout: 'hbox',
			        items: [{			     
			    		xtype:'button',
			    		text:'保存',
			    		iconCls: 'icon-ok',
			    		handler:grid.saveResource
			    }
			        ]
			    }
			   
			    ]
			})
			]}
    	   	 ).show();}
	},
//保存添加用户
 saveResource:function(){
		if(allform.form.isValid()){
//			var omsSubSystem = Ext.getCmp('omsSubSystem').getValue();
//			var omsModule = Ext.getCmp('omsModule').getValue();
//			var omsSubModule = Ext.getCmp('omsSubModule').getValue();
			var resourceName = Ext.getCmp('resourceName').getValue();
			var resourcePattern = Ext.getCmp('resourcePattern').getValue();
			var resourceDescription = Ext.getCmp('resourceDescription').getValue();
			var resourceStatus = Ext.getCmp('resourceStatus').getGroupValue();
//			if(omsSubSystem != "0" && omsModule != "0" && omsSubModule != "0"){
				Ext.Ajax.request({
                    url: path('/resource/saveResource.do'),
                    params: {
//                        omsSubSystem:omsSubSystem,
//                        omsModule:omsModule,
//                        omsSubModule:omsSubModule,
                        resourceName:resourceName,
                        resourcePattern:resourcePattern,
                        resourceDescription:resourceDescription,
                        resourceStatus:resourceStatus
                    },
                    success: function(json) {
                        var data = Ext.decode(json.responseText);
                        if(data.result == "resourceNameError"){
                        	Ext.Msg.alert(messages['ext.tips.error'],'资源名称已存在！');
							return;
                        }else if(data.result == "resourcePatternAndSystemError"){
                        	Ext.Msg.alert(messages['ext.tips.error'],'资源定义已存在！');
							return;
                        }else if(data.result == "success"){
                   	 			Ext.Msg.alert(messages['ext.tips.tip'], '添加成功！', function(btn) {
                   	 			Ext.getCmp('addresourceWindow').hide();
                   	 			var grid = Ext.getCmp('ResourceGridId');
								grid.getStore().load();
                            });
                        }else{
                            Ext.Msg.alert(messages['ext.tips.error'], '添加失败，请重试！');
                        }
                    },
                    failure: function() {
                        Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
                    }
                });
//			}else {
//				Ext.Msg.alert(messages['ext.tips.error'], '子系统，模块，及子模块不能为空！');
//			}
		}else{
			Ext.Msg.alert(messages['ext.tips.error'], '填写的内容有误！');
		}
 },

 
 editResourceHandle: function() {
	 var grid = this.up('gridpanel');
     var sm = grid.getSelectionModel();

     record = sm.getSelection()[0];//获取选中的条目的第一条
     var sysId = record.get("resourceSysId");
     if (Ext.getCmp('editResourceWindow')) {
 		Ext.getCmp('editResourceWindow').show();
 	 } else {
 		Ext.create('Ext.Window', {
			title : '修改角色',
			width:800,
		    height:300,
			resizable:true,
		    maximizable: true, 
		    autoScroll:true,
		    modal:true,
		    constrain:true,
		    draggable :false,
//			closeAction : 'hide',
			id : 'editResourceWindow',
			items:[
		 		orderDetailTabPanel = Ext.create('Ext.panel.Panel', {
		   	        autoScroll : true,
		   	        border:0,
		   	        items: [
		   	     	Ext.create('Ext.panel.Panel', {
		   	     	border:0,
		   	        layout:'fit',
		   			   	 items: [
		   			   		Ext.create("Ext.form.FormPanel",{
		   			   	  	url:path("/resource/updateResource.do"),
		   			   		id:'updateResource',
		   			   		padding:'0 0 0 20',
		   			   		reader:new Ext.data.JsonReader({ root:'result',model:'Resource',successProperty :'success'}),
		   				    bodyPadding: 20,
		   				    border:0,
		   				    items: [
		   				     {
		   		                xtype:'textfield',
		   		                hidden:true,
		   		                name:'resourceSysId',
		   		                id:'resourceSysId'+sysId
		   	         		 },{
		   	                   	xtype: 'displayfield',
		   	                   	hidden:true,
		   		                id: 'subSystemValue'+sysId,
		   		                name: 'subSystem'
		   	         		 },
//                             {
//		   		                fieldLabel: '子系统<span style=color:red>*</span>',
//		   	                   	xtype: 'combo',
//		   		                id: 'omsSubSystem'+sysId,
//		   		                name: 'subSystem',
//		   		                store:resourceSubSystemList,
//		   		                width:300,
//		   		                displayField: 'label',
//		   		                valueField: 'value',
//		   		                queryMode: 'local',
//		   		                editable: false,
//		   		                readOnly: true,
//		   		                padding:'0 0 5 0'
//		   	         		 },{
//		   	                   	xtype: 'displayfield',
//		   	                   	hidden:true,
//		   		                id: 'omsModuleValue'+sysId,
//		   		                name: 'omsModule'
//		   	         		 },{
//		   		                fieldLabel: '模块<span style=color:red>*</span>',
//		   	                   	xtype: 'combo',
//		   		                id: 'omsModule'+sysId,
//		   		                name: 'module',
//		   		                store:resourceModelList,
//		   		                width:300,
//		   		                displayField: 'label',
//		   		                valueField: 'value',
//		   		                queryMode: 'local',
//		   		                editable: false,
//		   		                readOnly: true,
//		   		                padding:'0 0 5 0'
//		   	         		 },{
//		   	                   	xtype: 'displayfield',
//		   	                   	hidden:true,
//		   		                id: 'omsSubModuleValue'+sysId,
//		   		                name: 'omsSubModule'
//		   	         		 },{
//		   		                fieldLabel: '子模块<span style=color:red>*</span>',
//		   	                   	xtype: 'combo',
//		   		                id: 'omsSubModule'+sysId,
//		   		                name: 'subModule',
//		   		                store:resourceSubModelList,
//		   		                width:300,
//		   		                displayField: 'label',
//		   		                valueField: 'value',
//		   		                queryMode: 'local',
//		   		                editable: false,
//		   		                readOnly: true,
//		   		                padding:'0 0 5 0'
//		   	         		 },
		   				    {
		   		                fieldLabel: '资源名称 <span style=color:red>*</span>',
		   		                xtype:'textfield',
		   		                id:'resourceName'+sysId,
		   		                name:'resourceName',
		   		                padding:'0 0 5 0',
		   		                allowBlank:false, //不允许为空
		   						blankText:"不能为空",
		   						width:300,
		   						maxLength : 50,
		   						maxLengthText : '最大长度不能超过50个字符!'
		   						/*regex:/^[0-9|a-z|_|A-Z]+$/,
		   						regexText:"资源定义ID是无效的"*/
		   	         		 },
		   				    {
		   		                xtype:'displayfield',
		   		                value: '不超过50个字节（数字，字母和下划线组成 ）',
		   		                padding:'0 0 5 0'
		   	         		 },
		   				    {
		   		                fieldLabel: '资源定义 <span style=color:red>*</span>',
		   		                xtype:'textfield',
		   		                id:'resourcePattern'+sysId,
		   		                name:'resourcePattern',
		   		                padding:'0 0 5 0',
		   		                allowBlank:false, //不允许为空
		   						blankText:"不能为空",
		   						width:300,
		   						maxLength : 1000,
		   						maxLengthText : '最大长度不能超过1000个字符!'
		   	         		 },
		   				    {
		   		                xtype:'displayfield',
		   		                padding:'0 0 5 0',
		   		                value: '不超过1000个字符'
		   	         		 },
		   				    {
		   		                fieldLabel: '资源描述',
		   	                   	xtype: 'textarea',
		   		                id: 'resourceDescription'+sysId,
		   		                name: 'resourceDescription',
		   		                width:500,
		   		                height:80,
		   		                maxLength:300,
		   		                maxLengthText : '资源描述的长度不能超过300字符',
		   		                padding:'0 0 5 0'
		   	         		 },{
		   	                   	xtype: 'displayfield',
		   	                   	hidden:true,
		   		                id: 'statusValue'+sysId,
		   		                name: 'resourceStatus'
		   	         		 },
		   			          {
		   			            xtype      : 'fieldcontainer',
		   			            fieldLabel : '状态',
		   			            width:300,
		   			            defaultType: 'radiofield',
		   			            padding:'0 0 10 0',
		   			            defaults: {
		   			                flex: 1
		   			            },
		   			            layout: 'hbox',
		   			            items: [
		   			                {
		   			                    boxLabel  : '启用',
		   			                    name: 'size'+sysId,
		   			                    inputValue: '1',
		   			                    id : 'resourceStatusRadio1'+sysId,
		   			                    checked:true
		   			                }, {
		   			                    boxLabel  : '禁用',
		   			                    name : 'size'+sysId,
		   			                    inputValue: '0',
		   			                    id : 'resourceStatusRadio2'+sysId
		   			                }
		   			            ]
		   			        },
		   			        {
		   				        xtype: 'fieldcontainer',
		   				        layout: 'hbox',
		   				        items: [{			     
		   				    		xtype:'button',
		   				    		text:'保存',
		   				    		iconCls: 'icon-ok',
		   				    		handler:function(){
		   				        	if (this.up('panel').getForm().isValid()) {
		   				        	   	this.up('panel').getForm().submit({
		   				        	success: function(json,action) {
		   				        		var data = action.result;
		   				        		if(data.result == "success"){
		   				        			Ext.Msg.alert(messages['ext.tips.tip'], '修改成功！', function(btn) {
		   	    								grid.getStore().load();
		   	    								Ext.getCmp('editResourceWindow').close();
//		   										Ext.getCmp('updateResource').getForm().load({url:path("/omsResource/resourceById.json?sysId="+sysId),
//		   										success: function(form, action) {
//		   										var status="";
//		   										//给单选框赋值
//		   										status = Ext.getCmp('statusValue'+sysId).getValue();
//		   											var radio1 = Ext.getCmp('omsResourceStatusRadio1'+sysId);
//		   											var radio2 = Ext.getCmp('omsResourceStatusRadio2'+sysId);
//		   											if(status == '1'){
//		   												radio1.setValue(true);
//		   												return;
//		   											}
//		   											if(status == '0'){
//		   												radio2.setValue(true);
//		   												return;
//		   											}
//		   										}
//		   										});
		   	                            	});
		   				        		}else if(data.result == "resourceNameError"){
		   				        			Ext.Msg.alert(messages['ext.tips.error'],'资源名称已存在！');
		   									return;
		   				        		}else if(data.result == "resourcePatternAndSystemError"){
		   				        			Ext.Msg.alert(messages['ext.tips.error'],'该子系统下资源定义已存在！');
		   				        			return;
		   				        		}		
		   	                    	},
		   						failure: function() {
		   	                    	 Ext.Msg.alert(messages['ext.tips.error'], '修改失败，请重试！');
		   	                    }
		   				       	});			        		
		   							}else{
		   								Ext.Msg.alert(messages['ext.tips.error'],'填写的内容有误!');
		   							}
		   				    		}
		   				    }
		   				        ]
		   				    }
		   				   
		   				    ]
		   				})
		   				]
		   	     	})
		   	     	]
		 		})]
		 	 }).show();
 	 		}
     
	     	//进入资源详细页
			Ext.getCmp('updateResource').getForm().load({url:path("/resource/resourceById.json?sysId="+sysId),
				success: function(form, action) {
				var resourceStatus="";
				//给下拉框赋值
//				var omsSubSystem = Ext.getCmp('omsSubSystemValue'+sysId).getValue();
//				var omsModule = Ext.getCmp('omsModuleValue'+sysId).getValue();
//				var omsSubModule = Ext.getCmp('omsSubModuleValue'+sysId).getValue();
//
//				var omsSubSystemDisplay = Ext.getCmp('omsSubSystem'+sysId);
//				omsSubSystemDisplay.setValue(omsSubSystem);
//				var omsModuleDisplay = Ext.getCmp('omsModule'+sysId);
//				omsModuleDisplay.setValue(omsModule);
//				var omsSubModuleDisplay = Ext.getCmp('omsSubModule'+sysId);
//				omsSubModuleDisplay.setValue(omsSubModule);
				//给单选框赋值
				resourceStatus = Ext.getCmp('statusValue'+sysId).getValue();
				var radio1 = Ext.getCmp('resourceStatusRadio1'+sysId);
				var radio2 = Ext.getCmp('resourceStatusRadio2'+sysId);
				if(resourceStatus == '1'){
					radio1.setValue(true);
					return;
				}
				if(resourceStatus == '0'){
					radio2.setValue(true);
					return;
				}
			}
			});
    	},

    initComponent: function() {
		orderGridMe = this;
        var me = this;

       /*
		 * me.filterItems = [{ type: 'string', dataIndex: 'omsOrderNo' },{ type:
		 * 'string', dataIndex: 'omsOrderConsumer' }];
		 */

        me.topDockedItems = [
        {
            xtype: 'button',
            itemId: 'btnAdd',
            height: 24,
            text: '添加资源',
            iconCls: 'icon-new',
            handler: me.addResourceHandle
        },
        {
            xtype: 'button',
            itemId: 'btnEdit',
            disabled: true,
            height: 24,
            text: '编辑资源明细',
            iconCls: 'icon-edit',
            handler: me.editResourceHandle
        }
        ];

        me.callParent();


        me.getSelectionModel().on('selectionchange', function(thiz, selections){
            me.down('#btnEdit').setDisabled(!(selections.length == 1));
            //me.down('#btnCancelOrder').setDisabled(selections.length == 0);
        });
//		//分页加载
//        me.store.on('beforeload', function (store, options) {
//	    	new_params = {"a":"a"};
//	       	Ext.apply(store.proxy.extraParams, new_params);
//    });
    }
});

Ext.onReady(function() {
    Ext.create('Smartec.resource.ResourceGrid',{
    	id:'ResourceGridId'
    });
});
