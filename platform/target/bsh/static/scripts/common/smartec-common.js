Ext.BLANK_IMAGE_URL = path('/static/images/s.gif');

function renderBoolean(value) {
    return value == 1 ? messages['ext.catalog.productattr.text.yes'] : messages['ext.catalog.productattr.text.no'];
}

function multiStrValueConverter(value) {
    if (!value)  return ;
    return value.split(',');
}

function multiIntValueConverter(value) {
    if (!value || Ext.isArray(value))  return ;
    var i = 0, strs = value.split(','), values = [];

    for (; i < strs.length; i++) {
        values.push(parseInt(strs[i], 10));
    }

    return values;
}

function converterCustomerType(value, p, record) {
    return messages['customer.type.' + value + '.message'];
}

function converterCustomerEnable(value, p, record) {
    return messages['customer.enable.' + value + '.message'];
}

function createOptionStore(optionValueMap) {
    var data = [];
    for(var key in optionValueMap){
        var dataItem = {
            'label': optionValueMap[key], 
            'value': parseInt(key, 10)
        };
        data.push(dataItem);
    }
        
    return Ext.create('Ext.data.Store', {
        fields: ['label', 'value'],
        data: data
    });
}

function genProductGroupFields(groupInfos, otherTabs, formPanel) {
    var i = 0, tabs = [];  
    
    for (; i < groupInfos.length; i++) {
        var fields = genProductFields(groupInfos[i].attributes, formPanel);
        if (i == 0) {
            fields.push({
                xtype: 'textfield',
                fieldLabel: messages['ext.catalog.product.form.sku'],
                name: 'sku',
                allowBlank: false
            });
        }
        tabs.push({
            title: groupInfos[i].attributeGroupName,
            layout:'column',
            defaults: {
//                labelWidth: 60,
                margin: '0 15 10 15',
                columnWidth: 1
            },
            items: fields
        });
    }

    for (var j = 0; j < otherTabs.length; j++) {
        tabs.push(otherTabs[j]);
    }
    
    return {
        xtype: 'tabpanel',
        id: 'productTabPanel',
        plain: true,
        defaults:{
            bodyStyle: 'padding:10px'
        },
        items: tabs
    };
}

function genCustomerFields(attrInfos, formPanel)  {
    return genDynamicFields(attrInfos, formPanel, 'Customer');    
}

function genProductFields(attrInfos, formPanel)  {
    return genDynamicFields(attrInfos, formPanel, 'Product');    
}
    
function genDynamicFields(attrInfos, formPanel, moduleType) {
    var fields = [], i = 0, record = formPanel.record;
    for (; i < attrInfos.length; i++) {
        var attr = attrInfos[i]
        var item = {
            fieldLabel: attr.frontendLabel,
            name: attr.code
        };
                    
        // frontend input type
        switch(attr.frontendInput) {
            case 'text':
                Ext.apply(item, {
                    xtype: 'textfield'
                });
                break;
            case 'textarea':
                Ext.apply(item, {
                    xtype: 'textareafield'
                });
                break;
            case 'bool':
                Ext.apply(item, {
                    xtype: 'combo',
                    queryMode: 'local',
                    store: booleanStore,
                    displayField: 'label',
                    valueField: 'value'
                });
                break;
            case 'multiselect':
                Ext.apply(item, {
                    xtype: 'combo',
                    queryMode: 'local',
                    displayField: 'label',
                    valueField: 'value',
                    multiSelect: true,
                    store: createOptionStore(attr.optionValueMap)
                });
                break;
            case 'price':
                Ext.apply(item, {
                    xtype: 'numberfield'
                });
                break;
            case 'date':
                Ext.apply(item, {
                    xtype: 'datefield'
                });
                if (!Ext.isEmpty(attr.attrValue)) {
                    attr.attrValue = new Date(attr.attrValue);
                }                            
                break;
            case 'select':
                Ext.apply(item, {
                    xtype: 'combo',
                    queryMode: 'local',
                    displayField: 'label',
                    valueField: 'value',
                    store: createOptionStore(attr.optionValueMap)
                });
                break;
            case 'media_image': // TODO
                break;
        }
                    
        // required
        if (attr.isRequired) {                        
            Ext.apply(item,{
                allowBlank: false
            });
        }
                    
        // unique
        if (attr.isUnique) {
            if (moduleType == 'Product') {
                Ext.apply(item, {
                    validator : validProductAttrUnique,
                    validateOnChange: false
                });
            } else if (moduleType == 'Customer') {
                Ext.apply(item, {
                    validator : validCustomerAttrUnique,
                    validateOnChange: false
                });
            }
        }
                    
        // 如果存在默认值设定默认值
        if (!Ext.isEmpty(attr.defaultValue)) {
            record.set(attr.code, attr.defaultValue);                    
        }
                
        if (!Ext.isEmpty(attr.attrValue) && Ext.isEmpty(record.get(attr.code))) {
            //TODO
            var val = attr.attrValue;
            if (attr.frontendInput == "select") {
                val = parseInt(val, 10);
            } else if (attr.frontendInput == "multiselect") {
                var k = 0, strs = val.split(','), nums = [];

                for (; k < strs.length; k++) {
                    nums.push(parseInt(strs[k], 10));
                }
                val = nums;
            }
            record.set(attr.code, val);
        }
                    
        fields.push(item);
    }
    // EndFor
    return fields;
}

////复写extjs的table类，阻止鼠标选择文本的就是这个unselectable///** 
// * override the table class 
// */ 
//Ext.override(Ext.view.Table, {
//  afterRender : function() {
//    var me = this;  
//    
//    me.callParent();  
//    me.mon(me.el, {  
//      scroll : me.fireBodyScroll,  
//      scope : me  
//    });  
//    if (!me.featuresMC && (me.featuresMC.findIndex('ftype', 'unselectable') >= 0)) {  
//      me.el.unselectable();  
//    }  
//    
//    me.attachEventsForFeatures();
//    alert(3333333333);
//  }  
//});

//Ext.core.Element.prototype.unselectable = function() {
//    var me = this;
//    if (me.dom.className.match(/(x-grid-table|x-grid-view)/)) {
//        return me;
//    }
//    me.dom.unselectable = "on";
//    me.swallowEvent("selectstart", true);
//    me.applyStyles("-moz-user-select:none;-khtml-user-select:none;");
//    me.addCls(Ext.baseCSSPrefix + 'unselectable');
//    return me;
//};