/*
 * Copyright (c) 2000-2011 arvato systems (Shanghai) $Id: order-grid.js
 * 2011-06-01 14:15:04 by Allen $
 */

function converterOrderStatus(value, p, record) {
	return messages['com.arvato.smartec.Constants.OrderStatus.' + value + '.message'];
}

function converterPayStatus(value, p, record) {
	return messages['com.arvato.smartec.Constants.PayStatus.' + value + '.message'];
}

var sysId = "";
var status = "";


Ext.define('Smartec.system.OrderSwitch', {
	alias : 'widget.orderSwitchgrid',
	extend : 'Smartec.grid.Panel',
	model : 'OrderSwitch',
	columns :[
	{
		text : 'id',
		dataIndex : 'omsOrderSwitchSysId',
		hidden : true
	},
	{
		text : '所属商店',
		width : 200,
		sortable : false,
		dataIndex : 'storeId'
	}, 
	{
		text : '开关类型',
		width : 300,
		sortable : false,
		dataIndex : 'switchType'
	},
	{
		text : '开关状态',
		width : 150,
		sortable : false,
		dataIndex : 'status'
	},
	{
		text : '创建人',
		width : 100,
		sortable : false,
		dataIndex : 'createOp'
	},
	{
		text : '修改人',
		width : 100,
		sortable : false,
		dataIndex : 'updateOp'
	},
	{
		text : '创建时间',
		width : 300,
		sortable : false,
		dataIndex : 'createTime'
	},
	{
		text : '修改时间',
		width : 300,
		sortable : false,
		dataIndex : 'updateTime'
	}],

	//查询开关
	searchOrderSwitchHandle : function() {
		var status = Ext.getCmp("switchStatus").getValue();
    	var type = Ext.getCmp("switchType").getValue();
    	var storeId = Ext.getCmp("omsStoreId").getValue();
    	var grid = this.up('gridpanel');
    		grid.getStore().load();
	},
	
	// 添加开关
	addOrderSwtichHandle : function() {
		var grid = this.up('gridpanel');
		if (Ext.getCmp('addOmsOrderSwtichWindow')) {
			Ext.getCmp('addOmsOrderSwtichWindow').show();
		} else {
			Ext.create('Ext.Window', {
				title : '添加开关',
				width : 500,
				height : 200,
				layout : 'fit',
				draggable :false,
				modal : true,
				id : 'addOmsOrderSwtichWindow',
				listeners : {
			     	hide : function(win)
			     	{
			     		var form = Ext.getCmp('allform').getForm();
			     		form.clearInvalid();
			     		form.reset();
			     	}
			     },
				closeAction:'hide',
				items : [
					allform = new Ext.form.FormPanel({
					id:'allform',
					bodyPadding : 10,
					autoScroll:true,
					items : [
					{
						fieldLabel: '所属商店',
				        xtype: 'combo',
				        id: 'storeId',
				        name: 'storeId',
				        store:orderSeniorStoreId,
				        displayField: 'label',
				        valueField: 'value',
				        emptyText:'请选择商店...',
				       	emptyValue:'',
				       	allowBlank : false, // 不允许为空
						blankText : "请选择商店",
				        editable: false,
				        width:350,
				        value:''
			        },
					{
						fieldLabel: '开关类型',
				        xtype: 'combo',
				        id: 'switchType',
				        name: 'switchType',
				        store:orderSwitchType,
				        displayField: 'label',
				        valueField: 'value',
				        emptyText:'请选择类型(可多选)',
				       	emptyValue:'',
				       	allowBlank : false, // 不允许为空
						blankText : "不能为空",
				        editable: false,
				        multiSelect: true,
				        autoShow: true,
				        width:400,
				        value:''
			        },
					{
					xtype : 'button',
					text : '保存',
					iconCls : 'icon-orderCancel',
					handler : grid.saveOmsOrderSwitch
					}]
				})]
			}).show();
		}
	},
	// 保存添加开关
	saveOmsOrderSwitch : function() {
		if (allform.form.isValid()) {
			var storeId = Ext.getCmp('storeId').getValue();
			var switchType = Ext.getCmp('switchType').getValue();
			Ext.Ajax.request({
				url : path('/omsOrderSwitch/saveOmsOrderSwitch.json'),
				params : {
					storeId : storeId,
					switchType : switchType
				},
				success : function(json) {
					var data = Ext.decode(json.responseText);
    				var result = data.result;
    				if(result!=null&&result!=""){
    					Ext.Msg.alert(messages['ext.tips.tip'],result,function(){
						});
    				}else{
						Ext.Msg.alert(messages['ext.tips.tip'],'添加成功！', function(btn) {
							var grid = Ext.getCmp('OrderSwitchId');
							grid.getStore().load();
							Ext.getCmp('addOmsOrderSwtichWindow').close();
						});
					}
				},
				failure : function() {
					Ext.Msg.alert(messages['ext.tips.error'],messages['ext.tips.errorMsg']);
				}
			});
		} else {
			Ext.Msg.alert(messages['ext.tips.error'], '填写的内容有误！');
		}
	},

//	//修改开关
//	editOrderSwtichHandle : function() {
//		if(Ext.getCmp('updateUser')){
//			 alert('请关闭原页再打开！');
//			 return;
//		 }else{
//			 var grid = this.up('gridpanel');
//			 var sm = grid.getSelectionModel();
//			 record = sm.getSelection()[0];// 获取选中的条目的第一条
//			 var sysId = record.get("omsOrderSwitchSysId");
//				orderDetailTabPanel = Ext.create('Ext.panel.Panel', {
//					autoScroll : true,
//					items : [Ext.create('Ext.panel.Panel', {
//						layout : 'fit',
//						items : [Ext.create("Ext.form.Panel", {
//							url : path("/omsOrderSwitch/saveOmsOrderSwitch.json"),
//							id : 'updateOrderSwitch',
//							padding : '0 0 0 10',
//							reader : new Ext.data.JsonReader({
//								root : 'result',
//								model : 'OrderSwitch',
//								successProperty : 'success'
//							}),
//							bodyPadding : 10,
//							border : 0,
//							items : [
//								{
//									fieldLabel: '所属商店',
//							        xtype: 'combo',
//							        id: 'storeId',
//							        name: 'storeId',
//							        store:orderStoreIdStore,
//							        displayField: 'label',
//							        valueField: 'value',
//							        emptyText:'请选择商店...',
//							       	emptyValue:'',
//							        editable: false,
//							        width:350,
//							        value:''
//						        },
//								{
//									fieldLabel: '开关类型',
//							        xtype: 'combo',
//							        id: 'switchType',
//							        name: 'switchType',
//							        store:orderSwitchType,
//							        displayField: 'label',
//							        valueField: 'value',
//							        emptyText:'请选择类型(可多选)',
//							       	emptyValue:'',
//							        editable: false,
//							        width:350,
//							        value:''
//						        },
//								{
//									xtype : 'fieldcontainer',
//									layout : 'hbox',
//									items : [{
//										xtype : 'button',
//										text : '保存',
//										iconCls : 'icon-orderCancel',
//										handler : function() {
//											if (this.up('panel').getForm().isValid()) {
//												this.up('panel').getForm().submit({
//								        			success : function(json,action) {
//														Ext.Msg.alert(messages['ext.tips.tip'],'修改成功！');
//													},
//													failure : function() {
//														Ext.Msg.alert(messages['ext.tips.error'],messages['ext.tips.errorMsg']);
//													}
//										       	});
//											} else {
//												Ext.Msg.alert(messages['ext.tips.error'],'填写的内容有误!');
//											}
//										}
//									}]
//								}]
//						})]
//					})]
//				});
//
//				// 进入用户详细页
//				Ext.getCmp('updateOrderSwitch').getForm().load({
//					url : path("/omsOrderSwitch/form.json?omsOrderSwitchSysId=" + sysId),
//					success : function(form, action) {
//						//给下拉框赋值
//						var omsUserStore = Ext.getCmp('omsUserStoreValue'+sysId).getValue();
//						var ous = omsUserStore.split(",");
//						var omsUserStoreDisplay = Ext.getCmp('omsUserStore'+sysId);
//						omsUserStoreDisplay.setValue(ous);
//						
//						var omsUserRole = Ext.getCmp('omsUserRoleValue'+sysId).getValue();
//						var our = omsUserRole.split(",");
//						var omsUserRoleDisplay = Ext.getCmp('omsUserRole'+sysId);
//						omsUserRoleDisplay.setValue(our);
//						 //给单选框赋值
//						 var status = action.result.data.omsUserStatus;
//						 var radio1 =Ext.getCmp('userStatusRadio1'+sysId);
//						 var radio2 =Ext.getCmp('userStatusRadio2'+sysId);
//						 if(status == '1')
//							 radio1.setValue(true);
//						 else
//						 	radio2.setValue(true);
//					}
//				});
//
//				mainTabPanel.add({
//					title : '开关详细',
//					iconCls : 'tabs',
//					layout : "fit",
//					items : orderDetailTabPanel,
//					closable : true
//				}).show();
//		 }
//	},
	
	//设为手动/自动
	updateOrderSwitch:function(){
        Ext.MessageBox.show({
		     title:messages['ext.system.omsOrderSwitch.confirm.message'],
		     msg:messages['ext.system.omsOrderSwitch.description.message'],
		     width:300,
		     buttons:Ext.MessageBox.OKCANCEL,
		     icon:Ext.MessageBox.INFO,
		     fn:function(btn,comments){   //comments得到文本框输入的值
    			if (btn == 'ok') {
//                	var sysId = record.get("omsOrderSwitchSysId");    //得到当前操作的订单的主键
//                	var status = record.get("status"); //当前开关状态
                	var newStatus = "";
                	if(status=="自动") { // 0
                		newStatus = "1";
                	}else if(status=="手动"){ //1
                		newStatus = "0";
                	}
	                Ext.Ajax.request({
	                    url: path('/omsOrderSwitch/updateSwitchStatus.json'),
	                    params: {
	                        omsOrderSwitchSysId: sysId,
	                        status:newStatus
	                    },
	                    success: function(json) {
	                        var data = Ext.decode(json.responseText);
	                        if(data.success){
	                            Ext.Msg.alert(messages['ext.tips.tip'], messages['ext.system.omsOrderSwitch.success.message'], function(btn) {
	                               var grid = Ext.getCmp('OrderSwitchId');
									grid.getStore().load();
	                            });
	                        }else{
	                            Ext.Msg.alert(messages['ext.tips.error'], messages['ext.system.omsOrderSwitch.failure.message']);
	                        }
	                    },
	                    failure: function() {
	                        Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
	                    }
	                });
    			}
          	}
    	});
    },
	initComponent : function() {
		orderGridMe = this;
		var me = this;
		me.topDockedItems = [{
			xtype : 'button',
			itemId : 'btnAdd',
			height : 24,
			text : '新建开关',
			iconCls : 'icon-new',
			handler : me.addOrderSwtichHandle
		},
		/*{
			xtype : 'button',
			itemId : 'btnEdit',
			disabled : true,
			height : 24,
			text : '修改开关',
			iconCls : 'icon-edit',
			handler : me.editOrderSwtichHandle
		},*/
		{
			xtype : 'button',
			itemId : 'btnHand',
			disabled : true,
			height : 24,
			text : '设为手动',
			iconCls : 'icon-edit',
			handler : me.updateOrderSwitch
		},{
			xtype : 'button',
			itemId : 'btnAuto',
			disabled : true,
			height : 24,
			text : '设为自动',
			iconCls : 'icon-edit',
			handler : me.updateOrderSwitch
		},'->',{
	        xtype: 'combo',
	        id: 'omsStoreId',
	        name: 'omsStoreId',
	        readOnly: me.isReadOnly,
	        store:orderStoreIdStore,
	        displayField: 'label',
	        valueField: 'value',
	        emptyText:'所属商店',
	       	emptyValue:'',
	        editable: false,
	        width:130,
	        value:''
        },{
	        xtype: 'combo',
	        id: 'switchType',
	        name: 'switchType',
	        readOnly: me.isReadOnly,
	        store:orderSwitchTypeAll,
	        displayField: 'label',
	        valueField: 'value',
	        emptyText:'开关类型',
	       	emptyValue:'',
	        editable: false,
	        width:130,
	        value:''
        },{
	        xtype: 'combo',
	        id: 'switchStatus',
	        name: 'switchStatus',
	        readOnly: me.isReadOnly,
	        store:orderSwitchStatus,
	        displayField: 'label',
	        valueField: 'value',
	        emptyText:'开关状态',
	       	emptyValue:'',
	        editable: false,
	        width:130,
	        value:''
        },{
			xtype : 'button',
			itemId : 'btnSearch',
			height : 24,
			text : '查询',
			iconCls : 'icon-select',
			handler : me.searchOrderSwitchHandle
		}];
		me.callParent();
		me.getSelectionModel().on('selectionchange',function(thiz, selections) {
			if(selections.length==1){
				sysId = selections[0].data.omsOrderSwitchSysId;
				status = selections[0].data.status;
				
				if(selections[0].data.status=="自动"){
					me.down('#btnHand').setDisabled(false);
				}else if(selections[0].data.status=="手动"){
					me.down('#btnAuto').setDisabled(false);	
				}
//				me.down('#btnEdit').setDisabled(false);
			}else{
				sysId = "";
				status = "";
				me.down('#btnHand').setDisabled(true);
				me.down('#btnAuto').setDisabled(true);	
//				me.down('#btnEdit').setDisabled(true);
			}
		});
		
		 //分页加载
        me.store.on('beforeload', function (store, options) {
	        var status = Ext.getCmp("switchStatus").getValue();
	    	var type = Ext.getCmp("switchType").getValue();
	    	var storeId = Ext.getCmp("omsStoreId").getValue();
	        var new_params = {"status":status,"type":type,"storeId":storeId};
	        Ext.apply(store.proxy.extraParams, new_params);
    	});
	}
});

Ext.onReady(function() {
	Ext.create('Smartec.system.OrderSwitch',{
		id:'OrderSwitchId'
	});
});