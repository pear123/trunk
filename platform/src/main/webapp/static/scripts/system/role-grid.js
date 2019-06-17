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
Ext.define('Smartec.role.RoleGrid', {
    alias: 'widget.rolegrid',
    extend: 'Smartec.grid.Panel',
    model: 'Role',
    columns: [{
        text: 'id',
        dataIndex: 'omsRoleSysId',
        hidden: true
    },
    /*{
        text: '客户编号',
        width: 200,
 		sortable: false,
        dataIndex: 'omsCustomerId'
    },*/
    {
        text: field['omsRoleId'],
        width: 400,
 		sortable: false,
        dataIndex: 'omsRoleId'
    },
    {
        text: field['omsRoleName'],
        width: 400,
 		sortable: false,
        dataIndex: 'omsRoleName'
    },
    {
        text: field['omsRoleStatus'],
        width: 200,
 		sortable: false,
        dataIndex: 'omsRoleStatus'
    },
    {
        text: field['omsRoleDescription'],
        width: 400,
 		sortable: false,
        dataIndex: 'omsRoleDescription'
    }
    ],
    
	//添加角色
      addRoleHandle:function(){
		var grid = this.up('gridpanel');
		if(Ext.getCmp('addRoleWindow')){
			Ext.getCmp('addRoleWindow').show();
			var form = Ext.getCmp('allform').getForm();
			var fields = form.getFields();
			form.reset();
			for(var i = 0, size = fields.length; i < size; i++){
				fields.getAt(i).clearInvalid();
			}
		}else{
    	   	 Ext.create('Ext.Window', {
		     title:field['addRole'],
		     width:800,
		     height:300,
//		     layout: 'fit',
		     autoScroll:true,
		     modal:true,
		     constrain:true,
		     resizable:true,
		     maximizable: true, 
		     id:'addRoleWindow',
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
		   		id:'allform',
			    bodyPadding: 20,
			    items: [
			    {
	                fieldLabel: field['ecOrganization'],
                   	xtype: 'combo',
	                id: 'ecOrganization',
	                name: 'ecOrganization',
	                store:omsOrganizationlist,
	                width:300,
	                displayField: 'label',
	                valueField: 'value',
	                queryMode: 'local',
	                editable: false,
	                value:'',
	                padding:'0 0 5 0',
	                allowBlank:false, //不允许为空
					blankText:"不能为空",
					emptyText:field['ecOrganization'],
	            	emptyValue:''
	            	/*listConfig  :  new Ext.view.BoundList({
	            		width : 200,
	            		maxHeight : 200,
	            		store:omsOrganizationlist
	            		
	            	})*/
         		 },
			    {
	                fieldLabel: field['omsRoleId'],  //'角色ID <span style=color:red>*</span>',
	                xtype:'textfield',
	                id:'omsRoleId',
	                name:'omsRoleId',
	                padding:'0 0 5 0',
	                allowBlank:false, //不允许为空
					blankText:"不能为空",
					width:300,
					maxLength : 20,
					maxLengthText : '最大长度不能超过20个字符!',
					regex:/^[0-9|a-z|_|A-Z]+$/,
					regexText:"角色ID定义是无效的"
         		 },
			    {
	                xtype:'displayfield',
	                value: messages['lessThanTW'],
	                padding:'0 0 5 0'
         		 },
			    {
	                fieldLabel: field['omsRoleName'], //'角色名称 <span style=color:red>*</span>',
	                xtype:'textfield',
	                id:'omsRoleName',
	                name:'omsRoleName',
	                padding:'0 0 5 0',
	                allowBlank:false, //不允许为空
					blankText:"不能为空",
					width:300,
					maxLength : 50,
					maxLengthText : messages['lessThanFT']
         		 },
			    {
	                xtype:'displayfield',
	                padding:'0 0 5 0',
	                value: '不超过50个字符'
         		 },
			    {
	                fieldLabel: field['omsRoleDescription'],
                   	xtype: 'textarea',
	                id: 'omsRoleDescription',
	                name: 'omsRoleDescription',
	                width:500,
	                height:80,
	                maxLength:300,
	                maxLengthText : '资源描述的长度不能超过300字符',
	                padding:'0 0 5 0'
         		 },
         		 {
	                fieldLabel: field['omsRoleResource'],
                   	xtype: 'combo',
	                id: 'omsRoleResource',
	                name: 'omsRoleResource',
	                store:roleResource,
	                width:500,
	                maxHeight: 200,
	                displayField : 'omsResourceName',
					valueField : 'omsResourceSysId',
	                editable: false,
	                padding:'0 0 10 0',
	                multiSelect : true,
	                emptyText:field['omsRoleResource'],
	            	emptyValue:''
         		 },
         		    /*someform = new Ext.form.FormPanel({
			    	bodyPadding: 0,
			    	padding: 0,
			    	bodyPadding:0,
			    	border: 0,
	                   	items:[
	                   		{
	                   			xtype: 'fieldcontainer',
	                   			fieldLabel : '资源',
	                   			defaultType: 'checkboxfield',
	                   			items: [
	                   				{
										boxLabel  : '曹锅资源',
										name      : 'topping',
										inputValue: '1',
										id        : 'checkbox1'
									}, {
										boxLabel  : '曹哥物流1',
										name      : 'topping',
										inputValue: '2',
										checked   : true,
										id        : 'checkbox2'
									}, {
										boxLabel  : '曹哥物流2',
										name      : 'topping',
										inputValue: '2',
										checked   : true,
										id        : 'checkbox3'
									}, {
										boxLabel  : '曹哥物流3',
										name      : 'topping',
										inputValue: '2',
										checked   : true,
										id        : 'checkbox4'
									}, {
										boxLabel  : '曹哥物流4',
										name      : 'topping',
										inputValue: '2',
										checked   : true,
										id        : 'checkbox5'
									}
	                   			]
	                   		}
	                   	]
         			}),*/
		          {
		            xtype      : 'fieldcontainer',
		            fieldLabel : field['status'],
		            width:300,
		            defaultType: 'radiofield',
		            padding:'0 0 10 0',
		            defaults: {
		                flex: 1
		            },
		            layout: 'hbox',
		            items: [
		                {
		                    boxLabel  : field['able'],
		                    name: 'size',
		                    inputValue: '1',
		                    id : 'omsRoleStatus',
		                    checked:true
		                }, {
		                    boxLabel  : field['unable'],
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
			    		handler:grid.saveRole
			    }
			        ]
			    }
			   
			    ]
			})
			]}
    	   	 ).show();}
	},
//保存添加用户
 saveRole:function(){
		if(allform.form.isValid()){
			var ecOrganization = Ext.getCmp('ecOrganization').getValue();
			var omsRoleId = Ext.getCmp('omsRoleId').getValue();
			var omsRoleName = Ext.getCmp('omsRoleName').getValue();
			var omsRoleDescription = Ext.getCmp('omsRoleDescription').getValue();
			var omsRoleResource = Ext.getCmp('omsRoleResource').getValue();
			var omsRoleStatus = Ext.getCmp('omsRoleStatus').getGroupValue();
			        Ext.Ajax.request({
                    url: path('/role/saveRole.do'),
                    params: {
                        ecOrganization:ecOrganization,
                        omsRoleId:omsRoleId,
                        omsRoleName:omsRoleName,
                        omsRoleDescription:omsRoleDescription,
                        omsRoleResource:omsRoleResource,
                        omsRoleStatus:omsRoleStatus
                    },
                    success: function(json) {
                        var data = Ext.decode(json.responseText);
                        if(data.result == "idError"){
                        	Ext.Msg.alert(messages['ext.tips.error'],'角色id已存在！');
                        	return;
                        }else if(data.result == "nameError"){
                        	Ext.Msg.alert(messages['ext.tips.error'],'角色名称已存在！');
                        	return;
                        }else if(data.result == "success"){
                   	 			Ext.Msg.alert(messages['ext.tips.tip'], '添加成功！', function(btn) {
                   	 			Ext.getCmp('addRoleWindow').hide();
                   	 			var grid = Ext.getCmp('RoleGridId');
								grid.getStore().load();
                            });
                        }else{
                            Ext.Msg.alert(messages['ext.tips.error'], '添加失败，请重试！');
                        }
                    },
                    failure: function() {
                        Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
                    },
                    waitMsg:'正在保存，请稍后....'
                });
			
		}else{
			Ext.Msg.alert(messages['ext.tips.error'], '填写的内容有误！');
		}
 },

 
 editRoleHandle: function() {
	 var grid = this.up('gridpanel');
     var sm = grid.getSelectionModel();

     record = sm.getSelection()[0];//获取选中的条目的第一条
     var sysId = record.get("omsRoleSysId");
     if (Ext.getCmp('editRoleWindow')) {
		Ext.getCmp('editRoleWindow').show();
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
//				closeAction : 'hide',
				id : 'editRoleWindow',
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
						   	  	url:path("/role/updateRole.do"),
						   		id:'updateRole',
						   		padding:'0 0 0 20',
						   		reader:new Ext.data.JsonReader({ root:'result',model:'Role',successProperty :'success'}),
							    bodyPadding: 20,
							    border:0,
							    items: [
							     {
					                xtype:'textfield',
					                hidden:true,
					                name:'omsRoleSysId',
					                id:'omsRoleSysId'+sysId
					     		 },{
					               	xtype: 'displayfield',
					               	hidden:true,
					                id: 'ecOrganizationValue'+sysId,
					                name: 'ecOrganization'			
					     		 },
//                                 {
//					                fieldLabel: '组织<span style=color:red>*</span>',
//					               	xtype: 'combo',
//					                id: 'ecOrganization'+sysId,
//					                name: 'ecOrganization',
//					                store:omsOrganizationlist,
//					                width:300,
//					                displayField: 'label',
//					                valueField: 'value',
//					                queryMode:'local',
//					                editable: false,
//					                readOnly: true,
//					                autoShow: true,
//					                padding:'0 0 5 0'
//					     		 },
							    {
					                fieldLabel: '角色ID <span style=color:red>*</span>',
					                xtype:'textfield',
					                id:'omsRoleId'+sysId,
					                name:'omsRoleId',
					                padding:'0 0 5 0',
					                allowBlank:false, //不允许为空
					                readOnly:true,
									blankText:"不能为空",
									width:300,
									maxLength : 20,
									maxLengthText : '最大长度不能超过20个字符!',
									regex:/^[0-9|a-z|_|A-Z]+$/,
									regexText:"资源定义ID是无效的"
					     		 },
							    {
					                xtype:'displayfield',
					                value: '不超过20个字节（数字，字母和下划线组成 ）',
					                padding:'0 0 5 0'
					     		 },
							    {
					                fieldLabel: '角色名称 <span style=color:red>*</span>',
					                xtype:'textfield',
					                id:'omsRoleName'+sysId,
					                name:'omsRoleName',
					                padding:'0 0 5 0',
					                allowBlank:false, //不允许为空
									blankText:"不能为空",
									width:300,
									maxLength : 50,
									maxLengthText : '最大长度不能超过50个字符!'
					     		 },
							    {
					                xtype:'displayfield',
					                padding:'0 0 5 0',
					                value: '不超过50个字符'
					     		 },{
					               	xtype: 'displayfield',
					               	hidden:true,
					                id: 'omsRoleResourceValue'+sysId,
					                name: 'omsRoleResources'			
					     		 },{
					                fieldLabel: '资源(可多选)',
					               	xtype: 'combo',
					                id: 'omsRoleResource'+sysId,
					                name: 'omsRoleResource',
					                store:roleResource,
					                width:500,
					                displayField : 'omsResourceName',
									valueField : 'omsResourceSysId',
					                editable: false,
					                padding:'0 0 10 0',
					//                pageSize:10,
					//                pickerAlign:'tl-bl?',
					                multiSelect: true,
					                queryMode:'local',
					                autoShow: true,
					                listeners: {
								        /*click: {
								            element: 'el', //bind to the underlying el property on the panel
								            fn: function(){
					//			            	console.log('click el'); 
					//			            	alert('exe');
								            	var ecOrganization = Ext.getCmp('ecOrganizationValue'+sysId).getValue();	
												var ecOrganizationDisplay = Ext.getCmp('ecOrganization'+sysId);
												ecOrganizationDisplay.setValue(ecOrganization);
												var omsRoleResource = Ext.getCmp('omsRoleResourceValue'+sysId).getValue();
												var orr = omsRoleResource.split(",");
												var omsRoleResourceDisplay = Ext.getCmp('omsRoleResource'+sysId);
												omsRoleResourceDisplay.setValue(orr);
								            }
								        }
								        ,*/
								        dblclick: {
								            element: 'body', //bind to the underlying body property on the panel
								            fn: function(){ 
								            	var omsRoleResource = Ext.getCmp('omsRoleResourceValue'+sysId).getValue();
												var orr = omsRoleResource.split(",");
												var omsRoleResourceDisplay = Ext.getCmp('omsRoleResource'+sysId);
												omsRoleResourceDisplay.setValue(orr);
								            }
								        }
								    }
					     		 },
							    {
					                fieldLabel: '角色描述',
					               	xtype: 'textarea',
					                id: 'omsRoleDescription'+sysId,
					                name: 'omsRoleDescription',
					                width:500,
					                height:80,
					                maxLength:300,
					                maxLengthText : '角色描述的长度不能超过300字符',
					                padding:'0 0 5 0'
					     		 },/*{
									xtype : 'fieldcontainer',
									fieldLabel : '&nbsp;&nbsp;资源(可多选)',
									width : 500,
									defaultType : 'combo',
									padding : '0 0 10 0',
									defaults : {
										flex : 1
									},
									layout : 'hbox',
									items : [{
										id : 'omsUserRole',
										store : roleResource,
										width : 300,
										displayField : 'omsResourceName',
										valueField : 'omsResourceSysId',
										editable : false,
										padding : '0 0 10 0',
										name : 'omsUserRole',
										multiSelect : true
									}]
									},*/{
					               	xtype: 'displayfield',
					               	hidden:true,
					                id: 'statusValue'+sysId,
					                name: 'omsRoleStatus'			
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
						                    id : 'omsRoleStatusRadio1'+sysId,
						                    checked:true
						                }, {
						                    boxLabel  : '禁用',
						                    name : 'size'+sysId,
						                    inputValue: '0',
						                    id : 'omsRoleStatusRadio2'+sysId
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
													Ext.getCmp('editRoleWindow').close();
//													Ext.getCmp('updateRole').getForm().load({url:path("/omsRole/roleById.json?sysId="+sysId),
//													success: function(form, action) {
//													var status="";
//													//给下拉框赋值
//													var ecOrganization = Ext.getCmp('ecOrganizationValue'+sysId).getValue();	
//													var ecOrganizationDisplay = Ext.getCmp('ecOrganization'+sysId);
//													ecOrganizationDisplay.setValue(ecOrganization);
//													var omsRoleResource = Ext.getCmp('omsRoleResourceValue'+sysId).getValue();
//													var orr = omsRoleResource.split(",");
//													var omsRoleResourceDisplay = Ext.getCmp('omsRoleResource'+sysId);
//													omsRoleResourceDisplay.setValue(orr);
//													//给单选框赋值
//													status = Ext.getCmp('statusValue'+sysId).getValue();
//													var radio1 = Ext.getCmp('omsRoleStatusRadio1'+sysId);
//													var radio2 = Ext.getCmp('omsRoleStatusRadio2'+sysId);
//													if(status == '1'){
//														radio1.setValue(true);
//														return;
//													}
//													if(status == '0'){
//														radio2.setValue(true);
//														return;
//													}
//													}
//													})
					                        	});
							        		}else if(data.result == "nameError"){
							        			Ext.Msg.alert(messages['ext.tips.error'],'角色名称已存在！');
												return;
							        		}else if(data.result == "idError"){
							        			Ext.Msg.alert(messages['ext.tips.error'],'角色id已存在！');
							        			return;
							        		}		
					                	},
					                failure: function() {
					                	 Ext.Msg.alert(messages['ext.tips.error'], '修改失败，请重试！');
					                },
					                waitMsg:'正在保存，请稍后....'
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
					})
				]
	 }).show();
	 }
	 
     	//进入角色详细页
		Ext.getCmp('updateRole').getForm().load({url:path("/role/roleById.json?sysId="+sysId),
			success: function(form, action) {
			var resourceStatus="";
			//给下拉框赋值
			var ecOrganization = Ext.getCmp('ecOrganizationValue'+sysId).getValue();	
			var ecOrganizationDisplay = Ext.getCmp('ecOrganization'+sysId);
			ecOrganizationDisplay.setValue(ecOrganization);
			var omsRoleResource = Ext.getCmp('omsRoleResourceValue'+sysId).getValue();
			var orr = omsRoleResource.split(",");
			var omsRoleResourceDisplay = Ext.getCmp('omsRoleResource'+sysId);
			omsRoleResourceDisplay.setValue(orr);
			//给单选框赋值
			var roleStatus = Ext.getCmp('statusValue'+sysId).getValue();
			var radio1 = Ext.getCmp('omsRoleStatusRadio1'+sysId);
			var radio2 = Ext.getCmp('omsRoleStatusRadio2'+sysId);
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
            text: field['addRole'],
            iconCls: 'icon-new',
            handler: me.addRoleHandle
        },
        {
            xtype: 'button',
            itemId: 'btnEdit',
            disabled: true,
            height: 24,
            text: field['viewRoleDetail'],
            iconCls: 'icon-edit',
            handler: me.editRoleHandle
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
    Ext.create('Smartec.role.RoleGrid',{
    	id:'RoleGridId'
    });
});
