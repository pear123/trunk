
function converterOrderStatus(value, p, record) {
	return messages['com.arvato.smartec.Constants.OrderStatus.' + value + '.message'];
}

function converterPayStatus(value, p, record) {
	return messages['com.arvato.smartec.Constants.PayStatus.' + value + '.message'];
}
var warehouseNameIdStoreNew = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsWarehouse/addWarehouseNameList.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//订单类型 order_type
var orderTypeStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   data : [
    	{
	        'label':'混合订单',
	        'value':'BLEND_ORDER'
    	},
    	{
	        'label':'手工导入订单',
	        'value':'HANDWORK_CHANNEL_ORDER'
    	},
    	{
	        'label':'开票订单',
	        'value':'INVOICE_ORDER'
    	},
    	{
	        'label':'帮宝适订单',
	        'value':'PAMPERS_ORDER'
    	},
    	{
	        'label':'大宗促销订单',
	        'value':'BULK_PROMOTIONAL_ORDER'
    	}
    ]
});

Ext.define('Smartec.system.orderIdentify', {
	alias : 'widget.orderIdentifyID',
	extend : 'Smartec.grid.Panel',
	model : 'OrderIdentifying',
	columns :[
		{text:'id',hidden:true,dataIndex:'omsOrderIdentifyingSysId'},
		{text:'订单标识名称',width:350,sortable:false,dataIndex:'identifyingName'}, 
		{text:'对应仓库',width:350,sortable:false,dataIndex:'wahehouseId'},
		{text:'创建时间',width:400,sortable:false,dataIndex:'createTime'},
		{text:'创建人',width:200,sortable:false,dataIndex:'createOp'}
	],

	// 添加订单标识
	addIdentifyHandle : function() {
		var grid = this.up('gridpanel');
		if (Ext.getCmp('addIdentifyWindow')) {
			Ext.getCmp('addIdentifyWindow').show();
		} else {
			Ext.create('Ext.Window', {
				title : '添加订单标识',
				width : 500,
				height : 200,
				layout : 'fit',
				draggable :false,
				modal : true,
				id : 'addIdentifyWindow',
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
					bodyPadding : 20,
					autoScroll:true,
					items : [
					{
						fieldLabel: '订单标识名称<span style=color:red>*</span>',
	                   	xtype: 'combo',
		                id: 'identifyingNameNew',
		                name: 'identifyingNameNew',
		                store:orderTypeStore,
		                width:400,
		                displayField : 'label',
						valueField : 'value',
		                editable: false,
		                padding:'0 0 10 0',
		                emptyText:'请选择订单标识...',
		                emptyValue:'',
		                allowBlank : false, // 不允许为空
						blankText : "不能为空",
		                autoShow: true
					},
					{
						fieldLabel: '对应仓库<span style=color:red>*</span>',
	                   	xtype: 'combo',
		                id: 'wahehouseIdNew',
		                name: 'wahehouseIdNew',
		                store:warehouseNameIdStoreNew,
		                width:400,
		                displayField : 'label',
						valueField : 'value',
		                editable: false,
		                padding:'0 0 10 0',
		                emptyText:'请选择仓库...',
		                emptyValue:'',
		                allowBlank : false, // 不允许为空
						blankText : "不能为空",
		                autoShow: true
					},
					{
						xtype : 'button',
						text : '保存',
						iconCls : 'icon-ok',
						handler : grid.saveIdentify
					}]
				})]
			}).show();
		}
	},
	// 保存添加订单标识
	saveIdentify : function() {
		if (allform.form.isValid()) {
			var identifyingCode = Ext.getCmp('identifyingNameNew').getValue();
			var wahehouseId = Ext.getCmp('wahehouseIdNew').getValue();
			var identifyingName = Ext.getCmp('identifyingNameNew').getRawValue();
			Ext.Ajax.request({
				url : path('/omsOrderIdentifying/saveOrderIdentify.json'),
				params : {
					identifyingName : identifyingName,
					identifyingCode : identifyingCode,
					wahehouseId : wahehouseId,
					flag : "save"
				},
				success : function(json) {
					var data = Ext.decode(json.responseText);
    				var result = data.result;
    				if(result!=null){
    					Ext.Msg.alert(messages['ext.tips.tip'],result,function(){
						});
    				}else{
						Ext.Msg.alert(messages['ext.tips.tip'],'添加成功！', function(btn) {
							Ext.getCmp('addIdentifyWindow').hide();
							var grid = Ext.getCmp('orderIdentifyID');
							grid.getStore().load();
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

	//修改订单标识
	editIdentifyHandle : function() {
		 var grid = this.up('gridpanel');
		 var sm = grid.getSelectionModel();
		 record = sm.getSelection()[0];// 获取选中的条目的第一条
		 var sysId = record.get("omsOrderIdentifyingSysId"); //得到订单标识sysId
		 if (Ext.getCmp('editIdentifyWindow')) {
			 Ext.getCmp('editIdentifyWindow').show();
		 } else {
			 Ext.create('Ext.Window', {
					title : '修改订单标识',
					width:500,
				    height:200,
					resizable:true,
				    maximizable: true, 
				    autoScroll:true,
				    modal:true,
				    constrain:true,
				    draggable :false,
					id : 'editIdentifyWindow',
					items:[
						orderDetailTabPanel = Ext.create('Ext.panel.Panel', {
						autoScroll : true,
						layout : 'fit',
						items : [Ext.create("Ext.form.Panel", {
							url : path("/omsOrderIdentifying/saveOrderIdentify.json?sysId="+sysId),
							id : 'updateOrderIdentifying',
							padding : '0 0 0 20',
							reader : new Ext.data.JsonReader({
								root : 'result',
								model : 'OrderIdentifying',
								successProperty : 'success'
							}),
							bodyPadding : 20,
							border : 0,
							items : [
								{
									fieldLabel: '订单标识名称',
				                   	xtype: 'displayfield',
					                id: 'identifyingName',
					                name: 'identifyingName',
					                width:400,
					                padding:'0 0 10 0'
								},
								{
				                   	xtype: 'textfield',
				                   	hidden:true,
					                id: 'identifyingNameHide',
					                name: 'identifyingNameHide'			
				         		},
				         		{
				                   	xtype: 'textfield',
				                   	hidden:true,
					                id: 'identifyingCodeHide',
					                name: 'identifyingCodeHide'			
				         		},
								{
									fieldLabel: '对应仓库<span style=color:red>*</span>',
				                   	xtype: 'combo',
					                id: 'wahehouseId',
					                name: 'wahehouseId',
					                store:warehouseNameIdStore,
					                width:400,
					                displayField : 'label',
									valueField : 'value',
					                editable: false,
					                allowBlank : false, // 不允许为空
									blankText : "不能为空",
									emptyText:'请选择仓库...',
		                			emptyValue:'',
					                padding:'0 0 10 0'
								},
								{
									xtype : 'fieldcontainer',
									layout : 'hbox',
									items : [{
										xtype : 'button',
										text : '保存',
										iconCls : 'icon-ok',
										handler : function() {
											if (this.up('panel').getForm().isValid()) {
												this.up('panel').getForm().submit({
								        			success : function(json,action) {
								        				var result = action.result.result;
								        				if(result!=null){
								        					Ext.Msg.alert(messages['ext.tips.tip'],result,function(){
															});
								        				}else{
															Ext.Msg.alert(messages['ext.tips.tip'],'修改成功！',function(){
																grid.getStore().load();
																Ext.getCmp('editIdentifyWindow').hide();
															});
								        				}
													},
													failure : function() {
														Ext.Msg.alert(messages['ext.tips.error'],messages['ext.tips.errorMsg']);
													}
										       	});
											} else {
												Ext.Msg.alert(messages['ext.tips.error'],'填写的内容有误!');
											}
										}
									}]
								}]
						})]
						})
					]
			 }).show();
		
		 	// 进入详细页
			Ext.getCmp('updateOrderIdentifying').getForm().load({
				url : path("/omsOrderIdentifying/getBySysId.json?sysId=" + sysId),
				success : function(form, action) {
					var result = action.result.data;
//					console.info(result.identifyingName);
//					console.info(result.identifyingCode);
					Ext.getCmp("identifyingNameHide").setValue(result.identifyingName);
					Ext.getCmp("identifyingCodeHide").setValue(result.identifyingCode);
//					console.info(result);
				}
			});
		 }
	},

	initComponent : function() {
		orderGridMe = this;
		var me = this;
		me.topDockedItems = [{
			xtype : 'button',
			itemId : 'btnAdd',
			height : 24,
			text : '添加订单标识',
			iconCls : 'icon-new',
			handler : me.addIdentifyHandle
		}, {
			xtype : 'button',
			itemId : 'btnEdit',
			disabled : true,
			height : 24,
			text : '编辑订单标识明细',
			iconCls : 'icon-edit',
			handler : me.editIdentifyHandle
		}];
		me.callParent();
		me.getSelectionModel().on('selectionchange',function(thiz, selections) {
			me.down('#btnEdit').setDisabled(!(selections.length == 1));
		});
	}
});

Ext.onReady(function() {
	Ext.create('Smartec.system.orderIdentify',{
		id:'orderIdentifyID'
	});
});