//alpha 
//alphanum 
//email 
//url 

function validAttrCode(codeValue, field) {
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
}

function validProductAttrUnique(codeValue) {
    var field = this;
    var attrCode = field.getName();
    var id = field.up('form').record.get('id');
    var setId = field.up('form').record.get('attributeSetId');

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
                var data = Ext.decode(json.responseText);
                return data.success ? true : data.message;
            }
        });
    }
    //return false;
}

function validCustomerAttrUnique(codeValue) {
    var field = this;
    var attrCode = field.getName();
    var id = field.up('form').record.get('id');
    var result = false;
        
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
                var data = Ext.decode(json.responseText);
                result = data.success ? true : data.message;
            }
        });
    }
    return result;
}