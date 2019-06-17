//alpha 
//alphanum 
//email 
//url 

Ext.apply(Ext.form.field.VTypes, {
    //  vtype validation function
    attrCode: function(codeValue, field) {
        var bFlag = false;
        if (!Ext.isEmpty(codeValue)) {
            Ext.Ajax.request({
                url: path('/catalog/productattrs/checkCode/' + codeValue),
                async: false,
                success: function(json) {
                    data = Ext.decode(json.responseText);
                    bFlag = data.result == "false";
                }
            });
        }
        return bFlag ? true : (Ext.isEmpty(codeValue) ? messages['ext.tips.empty'] : messages['ext.catalog.productattr.tips.codeExist']);
    },
    // vtype Text property: The error text to display when the validation function returns false
    attrCodeText: 'Not a valid time.  Must be in the format "12:34 PM".',
    // vtype Mask property: The keystroke filter mask
    attrCodeMask: /[\d\s]/i
});


Ext.apply(Ext.form.field.VTypes, {
    //  vtype validation function
    productAttrUnique: function(codeValue, field) {
        var attrCode = field.getName();
        var id = field.up('form').record.get('id');
        var setId = field.up('formpanel').record.get('setId');
        
        if (!Ext.isEmpty(codeValue)) {
            Ext.Ajax.request({
                url: path('/valid/attrUnique'),
                async: false,
                params: {
                    moduleType: 'Product',
                    attrCode: attrCode,
                    attrValue: codeValue, 
                    setId: setId,
                    id: id
                },
                success: function(json) {
                    data = Ext.decode(json.responseText);
                    return data.success;
                }
            });
        }
        return false;
    },
    // vtype Text property: The error text to display when the validation function returns false
    productAttrUniqueText: 'Not a valid time.  Must be in the format "12:34 PM".'
});

Ext.apply(Ext.form.field.VTypes, {
    //  vtype validation function
    customerAttrUnique: function(codeValue, field) {
        var attrCode = field.getName();
        var id = field.up('form').record.get('id');
        
        if (!Ext.isEmpty(codeValue)) {
            Ext.Ajax.request({
                url: path('/valid/attrUnique'),
                params: {
                    moduleType: 'Customer',
                    attrCode: attrCode,
                    attrValue: codeValue,                   
                    id: id
                },
                async: false,
                success: function(json) {
                    data = Ext.decode(json.responseText);
                    return data.success;
                }
            });
        }
        return false;
    },
    // vtype Text property: The error text to display when the validation function returns false
    customerAttrUniqueText: 'Not a valid time.  Must be in the format "12:34 PM".'
});