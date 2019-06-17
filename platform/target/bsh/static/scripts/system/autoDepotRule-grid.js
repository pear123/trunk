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

Ext.define('Smartec.system.autoDepotRule', {
	alias : 'widget.autoDepotRule',
	extend : 'Smartec.grid.Panel',
	model : 'AutoDepotRule',
	columns :[
		{text:'id',hidden:true,dataIndex:'omsAutoDepotRuleSysId'},
		{text:'code',hidden:true,dataIndex:'omsAutoDepotRuleCode'}, 
		{text:'statusCode',hidden:true,dataIndex:'statusCode'}, 
		{text:'规则名称',width:450,sortable:false,dataIndex:'omsAutoDepotRuleName'}, 
		{text:'规则状态',width:650,sortable:false,dataIndex:'status'}
	],

	// 自动拆单规则
	defaultHandle : function() {
        Ext.MessageBox.show({
		     title:messages['ext.system.defaultRule.message'],
		     msg:messages['ext.system.defaultRule.tips'],
		     width:300,
		     buttons:Ext.MessageBox.OKCANCEL,
		     icon:Ext.MessageBox.INFO,
		     fn:function(btn,comments){
    			if (btn == 'ok') {
    				var grid = Ext.getCmp('autoDepotRuleID');
			        var sm = grid.getSelectionModel();
			        record = sm.getSelection()[0];//获取选中的条目的第一条
			        var sysId = record.get("omsAutoDepotRuleSysId");
	                Ext.Ajax.request({
	                    url: path('/autoDepotRule/updateOmsAutoDepotRule.json'),
	                    params: {
	                        sysId: sysId,
	                        tag:'DEFAULT'
	                    },
	                    success: function(json) {
	                        var data = Ext.decode(json.responseText);
	                        if(data.success){
	                            Ext.Msg.alert(messages['ext.tips.tip'], messages['ext.system.success.message'], function(btn) {
	                            	Ext.getCmp('autoDepotRuleID').getStore().load();
	                            });
	                        }else{
	                            Ext.Msg.alert(messages['ext.tips.error'], messages['ext.system.failure.message']);
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
	
	//初始化数据
	addDefaultHandle : function() {
        Ext.Ajax.request({
            url: path('/autoDepotRule/initDate.json')
        });
	},

	//
	groupHandle : function() {
        Ext.MessageBox.show({
		     title:messages['ext.system.groupRule.message'],
		     msg:messages['ext.system.groupRule.tips'],
		     width:300,
		     buttons:Ext.MessageBox.OKCANCEL,
		     icon:Ext.MessageBox.INFO,
		     fn:function(btn,comments){
    			if (btn == 'ok') {
    				var grid = Ext.getCmp('autoDepotRuleID');
			        var sm = grid.getSelectionModel();
			        record = sm.getSelection()[0];//获取选中的条目的第一条
			        var sysId = record.get("omsAutoDepotRuleSysId");
	                Ext.Ajax.request({
	                    url: path('/autoDepotRule/updateOmsAutoDepotRule.json'),
	                    params: {
	                        sysId: sysId,
	                        tag:'GROUP'
	                    },
	                    success: function(json) {
	                        var data = Ext.decode(json.responseText);
	                        if(data.success){
	                            Ext.Msg.alert(messages['ext.tips.tip'], messages['ext.system.success.message'], function(btn) {
	                            	Ext.getCmp('autoDepotRuleID').getStore().load();
	                            });
	                        }else{
	                            Ext.Msg.alert(messages['ext.tips.error'], messages['ext.system.failure.message']);
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
			itemId : 'btnAddDefault',
			hidden:true,
			height : 24,
			text : '添加默认值',
			iconCls : 'icon-new',
			handler : me.addDefaultHandle
		},{
			xtype : 'button',
			itemId : 'btnDefault',
			disabled : true,
			height : 24,
			text : '设为默认规则',
			iconCls : 'icon-edit',
			handler : me.defaultHandle
		}, {
			xtype : 'button',
			itemId : 'btnGroup',
			disabled : true,
			height : 24,
			text : '设为组合规则',
			iconCls : 'icon-edit',
			handler : me.groupHandle
		}];
		me.callParent();
		me.getSelectionModel().on('selectionchange',function(thiz, selections) {
			me.down('#btnGroup').setDisabled(true);
			me.down('#btnDefault').setDisabled(true);
			if(selections.length == 1){
					if(selections[0].data.omsAutoDepotRuleCode=='PRODUCT'){
						me.down('#btnGroup').setDisabled(true);
						if(selections[0].data.statusCode=='DEFAULT'){
							me.down('#btnDefault').setDisabled(true);
						}else{
							me.down('#btnDefault').setDisabled(false);
						}
					}else{
						if(selections[0].data.statusCode=='DEFAULT'){
							me.down('#btnGroup').setDisabled(false);
							me.down('#btnDefault').setDisabled(true);
						}else if(selections[0].data.statusCode=='GROUP'){
							me.down('#btnDefault').setDisabled(false);
							me.down('#btnGroup').setDisabled(true);
						}else{
							me.down('#btnDefault').setDisabled(false);
							me.down('#btnGroup').setDisabled(false);
						}
					}
			}
		});
	}
});

Ext.onReady(function() {
	Ext.create('Smartec.system.autoDepotRule',{
		id:'autoDepotRuleID'
	});
});