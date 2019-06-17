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


var spDefCode = "";
var spDefName = "";
var spDefSysId = "";

Ext.define('Smartec.systemParamDefinition.SystemParamDefinitionGrid', {
	alias : 'widget.systemParamDefinitiongrid',
	extend : 'Smartec.grid.Panel',
	model : 'SystemParamDefinition',
	columns :[
	{
		text : 'id',
		dataIndex : 'systemParamDefSysId',
		hidden : true
	},
	{
		text : '系统参数的Code',
		width : 440,
		sortable : false,
		dataIndex : 'systemParamCode'
	}, 
	{
		text : '系统参数的名称',
		width : 440,
		sortable : false,
		dataIndex : 'systemParamName'
	},
	{
		text : '状态',
		width : 430,
		sortable : false,
		dataIndex : 'sysParamDefStatus'
	}],

	// 添加系统参数
	addSystemParamDefHandle : function() {
		var grid = this.up('gridpanel');
		if (Ext.getCmp('addSystemParamDefinitionWindow')) {
			Ext.getCmp('addSystemParamDefinitionWindow').show();
		} else {
			Ext.create('Ext.Window', {
				title : '添加系统参数',
				width : 700,
				height : 400,
				layout : 'fit',
				draggable :false,
				modal : true,
				id : 'addSystemParamDefinitionWindow',
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
						hideEmptyLabel :false,
						xtype : 'displayfield',
						value : '<span style=color:red>*</span>为必填选项'
					}, 
					{
						fieldLabel : '系统参数的Code<span style=color:red>*</span>',
						xtype : 'textfield',
						id : 'systemParamCode',
						name : 'systemParamCode',
						allowBlank : false, // 不允许为空
						blankText : "不能为空",
						width : 300,
						maxLength : 50,
						maxLengthText : '最大长度不能超过50个字符!',
						regex:/^[0-9|a-z|_|A-Z]+$/,
						regexText:"系统参数的Code是无效的"
					},
					{
						hideEmptyLabel :false,
						xtype : 'displayfield',
						value : '不超过50个字节（数字，字母和下划线组成）'
					},
					{
						fieldLabel : '系统参数名称<span style=color:red>*</span>',
						xtype : 'textfield',
						id : 'systemParamName',
						name : 'systemParamName',
						allowBlank : false, // 不允许为空
						blankText : "不能为空",
						width : 300,
						maxLength : 50,
						maxLengthText : '最大长度不能超过50个字符!'
					}, 
					{
						hideEmptyLabel :false,
						xtype : 'displayfield',
						value : '不超过50个字节'
					},
					{
						fieldLabel : '长描述',
						xtype : 'textarea',
						id : 'systemParamDescription',
						name : 'systemParamDescription',
						width : 600,
						height : 100,
						maxLength : 300,
						maxLengthText : '客户描述的长度不能超过300字符',
						padding : '0 0 10 0'
					}, 
					{
						xtype : 'fieldcontainer',
						fieldLabel : '&nbsp;&nbsp;状态',
						width : 300,
						defaultType : 'radiofield',
						padding : '0 0 10 0',
						defaults : {
							flex : 1
						},
						layout : 'hbox',
						items : [{
							boxLabel : '启用',
							name : 'size',
							inputValue : '1',
							id : 'systemParamDefinitionStatusRadio1',
							checked : true
						}, {
							boxLabel : '禁用',
							name : 'size',
							inputValue : '0',
							id : 'systemParamDefinitionStatusRadio2'
						}]
					}, {
					xtype : 'button',
					text : '保存',
					iconCls : 'icon-ok',
					handler : grid.saveSystemParamDefinition
					}]
				})]
			}).show();
		}
	},
	// 添加系统参数
	saveSystemParamDefinition : function() {
		if (allform.form.isValid()) {
			var systemParamCode = Ext.getCmp('systemParamCode').getValue();
			var systemParamName = Ext.getCmp('systemParamName').getValue();
			var systemParamDescription = Ext.getCmp('systemParamDescription').getValue();
			var systemParamStatus = '';
			if(Ext.getCmp('systemParamDefinitionStatusRadio1').getValue())
				systemParamStatus = Ext.getCmp('systemParamDefinitionStatusRadio1').inputValue;
			else
				systemParamStatus = Ext.getCmp('systemParamDefinitionStatusRadio2').inputValue;
			Ext.Ajax.request({
				url : path('/systemParamDefinition/form.json'),
				params : {
					systemParamCode : systemParamCode,
					systemParamName : systemParamName,
					systemParamDescription : systemParamDescription,
					systemParamStatus : systemParamStatus,
					operation : 'create'
				},
				success : function(json) {
					var data = Ext.decode(json.responseText);
					if(data.result=="codeError"){
						Ext.Msg.alert(messages['ext.tips.error'],'系统参数Code已存在！');
						return;
					}else if(data.result=="nameError"){
						Ext.Msg.alert(messages['ext.tips.error'],'系统参数名称已存在！');
						return;
					}else if (data.result=="success") {
						Ext.Msg.alert(messages['ext.tips.tip'],'添加成功！', function(btn) {
							allform.form.reset();
							Ext.getCmp('addSystemParamDefinitionWindow').hide();
							var grid = Ext.getCmp('SystemParamDefinitionGridId');
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

	//修改系统参数
	editSystemParamDefHandle : function() {
		var grid = this.up('gridpanel');
		var sm = grid.getSelectionModel();

		record = sm.getSelection()[0];// 获取选中的条目的第一条
		var sysId = record.get("systemParamDefSysId");
		var code = record.get("systemParamDefSysId");
		
		if (Ext.getCmp('editSystemParamDetailWindow')) {
	 		Ext.getCmp('editSystemParamDetailWindow').show();
	 	 } else {
	 		 Ext.create('Ext.Window', {
					title : '查看系统参数详细',
					width:800,
				    height:300,
					resizable:true,
				    maximizable: true, 
				    autoScroll:true,
				    modal:true,
				    constrain:true,
				    draggable :false,
//					closeAction : 'hide',
					id : 'editSystemParamDetailWindow',
					items:[
						orderDetailTabPanel = Ext.create('Ext.panel.Panel', {
							autoScroll : true,
							items : [Ext.create('Ext.panel.Panel', {
								layout : 'fit',
								items : [Ext.create("Ext.form.Panel", {
									url : path("/systemParamDefinition/form.json?systemParamDefSysId="+sysId),
									id : 'updateSystemParam',
									padding : '0 0 0 20',
									reader : new Ext.data.JsonReader({
										root : 'result',
										model : 'SystemParamDefinition',
										successProperty : 'success'
									}),
									bodyPadding : 20,
									border : 0,
									items : [
									{
										hideEmptyLabel :false,
										xtype : 'displayfield',
										value : '<span style=color:red>*</span>为必填选项'
									}, 
									{
										fieldLabel : '系统参数的Code<span style=color:red>*</span>',
										xtype : 'displayfield',
										id : 'systemParamCode',
										name : 'systemParamCode',
										allowBlank : false, // 不允许为空
										blankText : "不能为空",
										readOnly :true,
										width : 300,
										maxLength : 50,
										maxLengthText : '最大长度不能超过50个字符!',
										regex:/^[0-9|a-z|_|A-Z]+$/,
										regexText:"系统参数的Code是无效的"
									},
									{
										hideEmptyLabel :false,
										xtype : 'displayfield',
										value : '不超过50个字节（数字，字母和下划线组成）'
									},
									{
										fieldLabel : '系统参数名称<span style=color:red>*</span>',
										xtype : 'textfield',
										id : 'systemParamName'+sysId,
										name : 'systemParamName',
										allowBlank : false, // 不允许为空
										blankText : "不能为空",
										width : 300,
										maxLength : 50,
										maxLengthText : '最大长度不能超过50个字符!'
									}, 
									{
										hideEmptyLabel :false,
										xtype : 'displayfield',
										value : '不超过50个字节'
									},
									{
										fieldLabel : '长描述',
										xtype : 'textarea',
										id : 'systemParamDescription'+sysId,
										name : 'systemParamDescription',
										width : 600,
										height : 100,
										maxLength : 300,
										maxLengthText : '客户描述的长度不能超过300字符',
										padding : '0 0 10 0'
									}, 
									{
										xtype : 'fieldcontainer',
										fieldLabel : '&nbsp;&nbsp;状态',
										width : 300,
										defaultType : 'radiofield',
										padding : '0 0 10 0',
										defaults : {
											flex : 1
										},
										layout : 'hbox',
										items : [{
											boxLabel : '启用',
											name : 'size'+sysId,
											inputValue : '1',
											id : 'systemParamStatusRadio1'+sysId,
											checked : true
										}, {
											boxLabel : '禁用',
											name : 'size'+sysId,
											inputValue : '0',
											id : 'systemParamStatusRadio2'+sysId
										}]
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
															var data = action.result;
															if(data.result=="codeError"){
																Ext.Msg.alert(messages['ext.tips.error'],'系统参数Code已存在！');
																return;
															}else if(data.result=="nameError"){
																Ext.Msg.alert(messages['ext.tips.error'],'系统参数名称已存在！');
																return;
															}else if (data.result=="success") {
																Ext.Msg.alert(messages['ext.tips.tip'],'修改成功!');
																grid.getStore().load();
																Ext.getCmp('editSystemParamDetailWindow').close();
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
							})]
						})]
	 		 }).show();
	 	 }
		
		// 进入系统参数详细页
		Ext.getCmp('updateSystemParam').getForm().load({
			url : path("/systemParamDefinition/form.do?systemParamDefSysId=" + sysId),
			success : function(form, action) {
				 //给单选框赋值
				 var status = action.result.data.sysParamDefStatus;
				 var radio1 =Ext.getCmp('systemParamStatusRadio1'+sysId);
				 var radio2 =Ext.getCmp('systemParamStatusRadio2'+sysId);
				 if(status == '1')
					 radio1.setValue(true);
				 else
				 	radio2.setValue(true);
			}
		});
	},

	//系统参数值维护
	modifySystemParamHandle : function(){
		if(Ext.getCmp('seeSystemParamDetail')){
			 alert('请关闭原页再打开！');
			 return;
		 }else{
			 var grid = this.up('gridpanel');
			 var sm = grid.getSelectionModel();
			 record = sm.getSelection()[0];// 获取选中的条目的第一条
			 var sysId = record.get("systemParamDefSysId");
			 spDefCode = record.get("systemParamCode");
			 spDefName = record.get("systemParamName");
			 spDefSysId = sysId;
			 Ext.define('SystemParamValue', {
				    extend: 'Ext.data.Model',
				    fields: [{
				        name: 'sysParamValueSysId',
				        type: 'string'
				    },
				    {
				        name: 'systemParamName',
				        type: 'string'
				    },
				    {
				        name: 'systemParamValueOrder',
				        type: 'int'
				    },
				    {
				        name: 'sysParamValueStatus',
				        type: 'string'
				    },
				    {
				        name: 'systemParamDescription',
				        type: 'string'
				    },
				    {
				        name: 'systemParamValue',
				        type: 'string'
				    },
				    {
				        name: 'systemParamDefSysId',
				        type: 'string'
				    },
				    {
				        name: 'systemParamDefName',
				        type: 'string'
				    },
				    {
				        name: 'systemParamDefCode',
				        type: 'string'
				    }],
				    proxy: {
					        type: 'ajax',
					        url: path('/systemParamValue/form.json?systemParamDefSysId='+sysId),
					        reader: {
					            type: 'json',
					            root: 'result',
					            totalProperty: 'recordCount',
					            successProperty: 'success'
					        }
					    }
				});
				Ext.define('Smartec.grid.PanelSon', {
					alias : 'widget.systemParamValuegrid',
					extend : 'Smartec.grid.Panel',	
					selModel: Ext.create('Ext.selection.CheckboxModel', {
				        checkOnly: false
				    }),
				    plugins: [
				        Ext.create('Ext.grid.plugin.CellEditing', {
				            clicksToEdit: 1
				        })
				    ],
					columns: [
					{
				        text: 'sysParamValueSysId', 
				        dataIndex: 'sysParamValueSysId',
				        hidden : true
			        },
					{
				        text: '系统参数值Code', 
				        width : 400,
						sortable : false,
				        dataIndex: 'systemParamValue'
			        },
			        {
				        text: '系统参数的名称', 
				        width : 400,
						sortable : false,
				        dataIndex: 'systemParamName'
			        },
			        {
				        text: '状态', 
				        width : 400,
						sortable : false,
				        dataIndex: 'sysParamValueStatus'
			        },
			        {
				        text: '排序', 
				        width : 400,
						sortable : false,
				        dataIndex: 'systemParamValueOrder'/*,
				        editor: {
			                xtype:'textfield',
			                allowBlank:false
		            	},
		            	listeners: {
					        change: {
					            element: 'body',
					            fn: function(){ console.info('click el'); }
					        }
		                }*/
			        },{
			            text: '系统参数主键',
			            dataIndex: 'systemParamDefSysId',
			            hidden:true
		            },{
			            text: '系统参数定义名',
			            name: 'systemParamDefName',
			            hidden:true
		            },{
			            text: '系统参数定义code',
			            name: 'systemParamDefCode',
			            hidden:true
		            }],
			        
			        addSystemParamValHandle : function(){
			        	var grid = this.up('gridpanel');
			        	
							if (Ext.getCmp('addSystemParamValWindow')) {
								Ext.getCmp('addSystemParamValWindow').show();
							} else {
								Ext.create('Ext.Window', {
									title : '添加系统参数值',
									width : 700,
									height : 400,
									layout : 'fit',
									draggable :false,
									modal : true,
									id : 'addSystemParamValWindow',
//									listeners : {
//								     	hide : function(win)
//								     	{
//								     		var form = Ext.getCmp('systemParamValform').getForm();
//								     		form.clearInvalid();
//								     		form.reset();
//								     	}
//								     },
//								     closeAction:'hide',
									items : [
										systemParamValform = new Ext.form.FormPanel({
										id:'systemParamValform',
										bodyPadding : 20,
										autoScroll:true,
										items : [
										{
											hideEmptyLabel :false,
											labelWidth:125,
											xtype : 'displayfield',
											value : '<span style=color:red>*</span>为必填选项'
										},
										{
								            xtype: 'displayfield',
								            labelWidth:125,
								            fieldLabel: '系统参数的Code',
								            name: 'systemParamDefCode',
								            value: spDefCode
								        },
								        {
								            xtype: 'displayfield',
								            labelWidth:125,
								            fieldLabel: '系统参数的名称',
								            name: 'systemParamDefName',
								            value: spDefName,
								            width:500
								        },
										{
											xtype : 'displayfield',
											labelWidth:125,
											hidden : true,
											id : 'systemParamDefSysId',
											name : 'systemParamDefSysId',
											value : spDefSysId
										}, 
										{
											fieldLabel : '系统参数值的Code<span style=color:red>*</span>',
											labelWidth:125,
											xtype : 'textfield',
											id : 'systemParamValue',
											name : 'systemParamValue',
											allowBlank : false, // 不允许为空
											blankText : "不能为空",
											width : 400,
											maxLength : 50,
											maxLengthText : '最大长度不能超过50个字符!',
											regex:/^[0-9|a-z|_|A-Z]+$/,
											regexText:"系统参数值的Code是无效的"
										},
										{
											hideEmptyLabel :false,
											labelWidth:125,
											xtype : 'displayfield',
											value : '不超过50个字节（数字，字母和下划线组成）'
										},
										{
											fieldLabel : '系统参数值的名称<span style=color:red>*</span>',
											labelWidth:125,
											xtype : 'textfield',
											id : 'systemParamName',
											name : 'systemParamName',
											allowBlank : false, // 不允许为空
											blankText : "不能为空",
											width : 300,
											maxLength : 50,
											maxLengthText : '最大长度不能超过50个字符!'
										}, 
										{
											hideEmptyLabel :false,
											labelWidth:125,
											xtype : 'displayfield',
											value : '不超过50个字节'
										},
										{
											fieldLabel : '排序',
											labelWidth:125,
											xtype : 'textfield',
											id : 'systemParamValueOrder',
											name : 'systemParamValueOrder',
											padding : '0 0 10 0',
											width : 300,
											regex:/^[0-9]+$/,
											regexText:"排序为数字"
										}, 
										{
											fieldLabel : '长描述',
											labelWidth:125,
											xtype : 'textarea',
											id : 'systemParamDescription',
											name : 'systemParamDescription',
											width : 600,
											height : 100,
											maxLength : 300,
											maxLengthText : '客户描述的长度不能超过300字符',
											padding : '0 0 10 0'
										}, 
										{
											xtype : 'fieldcontainer',
											fieldLabel : '&nbsp;&nbsp;状态',
											labelWidth:125,
											width : 300,
											defaultType : 'radiofield',
											padding : '0 0 10 0',
											defaults : {
												flex : 1
											},
											layout : 'hbox',
											items : [{
												boxLabel : '启用',
												name : 'size',
												inputValue : '1',
												id : 'systemParamValStatusRadio1',
												checked : true
											}, {
												boxLabel : '禁用',
												name : 'size',
												inputValue : '0',
												id : 'systemParamValStatusRadio2'
											}]
										}, 
										{
										xtype : 'button',
										text : '保存',
										iconCls : 'icon-ok',
										handler : grid.saveSystemParamVal
										}]
									})]
								}).show();
							}
			        },
			        
			        saveSystemParamVal :function(){
			        	if (systemParamValform.form.isValid()) {
			        		var systemParamDefSysId = Ext.getCmp('systemParamDefSysId').getValue();
							var systemParamValCode = Ext.getCmp('systemParamValue').getValue();
							var systemParamValName = Ext.getCmp('systemParamName').getValue();
							var systemParamValDescription = Ext.getCmp('systemParamDescription').getValue();
							var systemParamValueOrder = Ext.getCmp('systemParamValueOrder').getValue();
							var systemParamStatus = '';
							if(Ext.getCmp('systemParamValStatusRadio1').getValue())
								systemParamStatus = Ext.getCmp('systemParamValStatusRadio1').inputValue;
							else
								systemParamStatus = Ext.getCmp('systemParamValStatusRadio2').inputValue;
							Ext.Ajax.request({
								url : path('/systemParamValue/operation.json'),
								params : {
									systemParamDefSysId : systemParamDefSysId,
									systemParamValue : systemParamValCode,
									systemParamName : systemParamValName,
									systemParamDescription : systemParamValDescription,
									sysParamValueStatus : systemParamStatus,
									systemParamValueOrder : systemParamValueOrder,
									operation : 'create'
								},
								success : function(json) {
									var data = Ext.decode(json.responseText);
									if(data.result=="codeError"){
										Ext.Msg.alert(messages['ext.tips.error'],'系统参数值Code已存在！');
										return;
									}else if(data.result=="nameError"){
										Ext.Msg.alert(messages['ext.tips.error'],'系统参数值名称已存在！');
										return;
									}else if (data.result=="success") {
										Ext.Msg.alert(messages['ext.tips.tip'],'添加成功！', function(btn) {
											systemParamValform.form.reset();
											Ext.getCmp('addSystemParamValWindow').hide();
											var grid = Ext.getCmp('updateSystemParam');
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
			        
			        editSystemParamValHandle : function(){
			        	var grid = this.up('gridpanel');  
						var sm = grid.getSelectionModel();
						 
						record = sm.getSelection()[0];// 获取选中的条目的第一条
						var sysId = record.get("sysParamValueSysId");
						var code = record.get("sysParamValueSysId");
						if (Ext.getCmp('editSeeSystemParamDetailWindow')) {
							 Ext.getCmp('editSeeSystemParamDetailWindow').show();
						 } else {
							 Ext.create('Ext.Window', {
									title : '查看系统参数值明细',
									width:800,
								    height:300,
									resizable:true,
								    maximizable: true, 
								    autoScroll:true,
								    modal:true,
								    constrain:true,
								    draggable :false,
//									closeAction : 'hide',
									id : 'editSeeSystemParamDetailWindow',
									items:[
										orderDetailTabPanel = Ext.create('Ext.panel.Panel', {
											id:'detailTab',
											autoScroll : true,
											items : [Ext.create('Ext.panel.Panel', {
												layout : 'fit',
												items : [Ext.create("Ext.form.Panel", {
													url : path("/systemParamValue/operation.json?sysParamValueSysId="+sysId+"&systemParamDefSysId="+spDefSysId),
													id : 'seeSystemParamDetail',
													padding : '0 0 0 20',
													reader : new Ext.data.JsonReader({
														root : 'result',
														model : 'SystemParamValue',
														successProperty : 'success'
													}),
													bodyPadding : 20,
													border : 0,
													items : [
														{
															hideEmptyLabel :false,
															labelWidth:125,
															xtype : 'displayfield',
															value : '<span style=color:red>*</span>为必填选项'
														},
														{
												            xtype: 'displayfield',
												            labelWidth:125,
												            fieldLabel: '系统参数的Code',
												            name: 'systemParamDefCode',
												            id : 'systemParamDefCode'+sysId
												        },
												        {
												            xtype: 'displayfield',
												            labelWidth:125,
												            fieldLabel: '系统参数的名称',
												            name: 'systemParamDefName'+sysId,
												            id: 'systemParamDefName'+sysId,
												            width:500
												        },
														{
															xtype : 'displayfield',
															hidden : true,
															id : 'systemParamDefSysId'+sysId,
															name : 'systemParamDefSysId'
														}, 
														{
															fieldLabel : '系统参数值的Code<span style=color:red>*</span>',
															labelWidth:125,
															xtype : 'textfield',
															id : 'systemParamValue'+sysId,
															name : 'systemParamValue',
															allowBlank : false, // 不允许为空
															blankText : "不能为空",
															width : 400,
															maxLength : 50,
															maxLengthText : '最大长度不能超过50个字符!',
															regex:/^[0-9|a-z|_|A-Z]+$/,
															regexText:"系统参数值的Code是无效的"
														},
														{
															hideEmptyLabel :false,
															labelWidth:125,
															xtype : 'displayfield',
															value : '不超过50个字节（数字，字母和下划线组成）'
														},
														{
															fieldLabel : '系统参数值的名称<span style=color:red>*</span>',
															labelWidth:125,
															xtype : 'textfield',
															id : 'systemParamName'+sysId,
															name : 'systemParamName',
															allowBlank : false, // 不允许为空
															blankText : "不能为空",
															width : 300,
															maxLength : 50,
															maxLengthText : '最大长度不能超过50个字符!'
														}, 
														{
															hideEmptyLabel :false,
															labelWidth:125,
															xtype : 'displayfield',
															value : '不超过50个字节'
														},
														{
															fieldLabel : '排序',
															labelWidth:125,
															xtype : 'textfield',
															id : 'systemParamValueOrder'+sysId,
															name : 'systemParamValueOrder',
															padding : '0 0 10 0',
															width : 300,
															regex:/^[0-9]+$/,
															regexText:"排序为数字"
														}, 
														{
															fieldLabel : '长描述',
															labelWidth:125,
															xtype : 'textarea',
															id : 'systemParamDescription'+sysId,
															name : 'systemParamDescription',
															width : 600,
															height : 100,
															maxLength : 300,
															maxLengthText : '客户描述的长度不能超过300字符',
															padding : '0 0 10 0'
														}, 
														{
															xtype : 'fieldcontainer',
															fieldLabel : '&nbsp;&nbsp;状态',
															labelWidth:125,
															width : 300,
															defaultType : 'radiofield',
															padding : '0 0 10 0',
															defaults : {
																flex : 1
															},
															layout : 'hbox',
															items : [{
																boxLabel : '启用',
																name : 'size'+sysId,
																inputValue : '1',
																id : 'systemParamValStatusRadio1'+sysId,
																checked : true
															}, {
																boxLabel : '禁用',
																name : 'size'+sysId,
																inputValue : '0',
																id : 'systemParamValStatusRadio2'+sysId
															}]
														}, 
														{
														xtype : 'button',
														text : '保存',
														iconCls : 'icon-ok',
														handler : function(){
																if (this.up('panel').getForm().isValid()) {
																	this.up('panel').getForm().submit({
													        			success : function(json,action) {
																			var data = action.result.result;
																			if(data.result=="codeError"){
																				Ext.Msg.alert(messages['ext.tips.error'],'系统参数值的Code已存在！');
																				return;
																			}else if(data.result=="nameError"){
																				Ext.Msg.alert(messages['ext.tips.error'],'系统参数值的名称已存在！');
																				return;
																			}else {
																				Ext.Msg.alert(messages['ext.tips.tip'],'修改成功!');
																				grid.getStore().load();
																				Ext.getCmp('editSeeSystemParamDetailWindow').close();
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
												})]
											})]
										})
									]}).show();
						 		}
						// 进入系统参数详细页
						Ext.getCmp('seeSystemParamDetail').getForm().load({
							url : path("/systemParamValue/getSystemVal.json?systemParamValSysId=" + sysId),
							success : function(form, action) {
								 //给单选框赋值
								Ext.getCmp('systemParamDefCode'+sysId).setValue(spDefCode);
								Ext.getCmp('systemParamDefName'+sysId).setValue(spDefName);
								Ext.getCmp('systemParamDefSysId'+sysId).setValue(spDefSysId);
								
								 var status = action.result.data.sysParamValueStatus;
								 var radio1 =Ext.getCmp('systemParamValStatusRadio1'+sysId);
								 var radio2 =Ext.getCmp('systemParamValStatusRadio2'+sysId);
								 if(status == '1')
									 radio1.setValue(true);
								 else
								 	radio2.setValue(true);
							}
						});
			        },
			        
					initComponent : function() {
						var obj = this;
						obj.topDockedItems = [
						{
							xtype : 'button',
							itemId : 'btnSystemParamValAdd',
							height : 24,
							text : '添加系统参数值',
							iconCls : 'icon-new',
							handler : obj.addSystemParamValHandle
						}, 
						{
							xtype : 'button',
							itemId : 'btnSystemParamValEdit',
							disabled : true,
							height : 24,
							text : '查看系统参数值明细',
							iconCls : 'icon-edit',
							handler : obj.editSystemParamValHandle
						},
						'->'/*, 
						{
				            xtype: 'button',
				            itemId: 'btnOrderSystemParam',
				            height: 24,
				            text:'查询'
				        }*/];
						obj.callParent();
						obj.getSelectionModel().on('selectionchange',function(thiz, selections) {
							obj.down('#btnSystemParamValEdit').setDisabled(!(selections.length == 1));
						});
					}
				});
				
				if(Ext.getCmp('updateSystemParam')){
					 alert('请关闭原页再打开！');
					 return;
				 }else{
					 systemParamTabPanel =Ext.create('Ext.panel.Panel', {
							autoScroll : true,
							items : [
								Ext.create('Smartec.grid.PanelSon',{
									model:'SystemParamValue',
									id:'updateSystemParam'
								})
							]
						});
				 }
				mainTabPanel.add({
					title : '系统参数维护',
					iconCls : 'tabs',
					layout : "fit",
					items : systemParamTabPanel,
					closable : true
				}).show();
		 }
	},
	
	//按条件进行查询
	selectSystemParam : function(){
		var systemParamName = Ext.getCmp("systemParamNameCondition").getValue();
    	var systemParamCode = Ext.getCmp("systemParamCodeCondition").getValue();
    	 var grid = this.up('gridpanel');
    		grid.getStore().load();
	},
	initComponent : function() {
		orderGridMe = this;
		var me = this;
		me.topDockedItems = [
		{
			xtype : 'button',
			itemId : 'btnAdd',
			height : 24,
			text : '添加系统参数',
			iconCls : 'icon-new',
			handler : me.addSystemParamDefHandle
		}, 
		{
			xtype : 'button',
			itemId : 'btnEdit',
			disabled : true,
			height : 24,
			text : '查看系统参数明细',
			iconCls : 'icon-edit',
			handler : me.editSystemParamDefHandle
		},
		{
			xtype : 'button',
			itemId : 'btnModify',
			disabled : true,
			height : 24,
			text : '参数信息值维护',
			iconCls : 'icon-edit',
			handler : me.modifySystemParamHandle
		},
		'->', 
		{
			fieldLabel : "系统参数的名称",
			xtype : 'textfield',
			id : 'systemParamNameCondition',
			name : 'systemParamNameCondition'
		}, 
		{
			fieldLabel : "系统参数的code",
			xtype : 'textfield',
			id : 'systemParamCodeCondition',
			name : 'systemParamCodeCondition'
		},
		{
            xtype: 'button',
            itemId: 'selectSystemParam',
            height: 24,
            iconCls : 'icon-select',
            text:'查询',
            handler:me.selectSystemParam
        }];
		me.callParent();
		me.getSelectionModel().on('selectionchange',function(thiz, selections) {
			me.down('#btnEdit').setDisabled(!(selections.length == 1));
			me.down('#btnModify').setDisabled(!(selections.length == 1));
		});
		
		 //分页加载
        me.store.on('beforeload', function (store, options) {
	        var systemParamName = Ext.getCmp("systemParamNameCondition").getValue();
    		var systemParamCode = Ext.getCmp("systemParamCodeCondition").getValue();
	        var new_params = {"systemParamName":systemParamName,"systemParamCode":systemParamCode};
	        Ext.apply(store.proxy.extraParams, new_params);
    	});
	}
});

Ext.onReady(function() {
	Ext.create('Smartec.systemParamDefinition.SystemParamDefinitionGrid',{
		id:'SystemParamDefinitionGridId'
	});
});