/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: smartec-cmp.js 2011-5-18 19:11:00 Justin $
 */
var path = function (uri) {
    return '/bsh' + uri
};

Ext.define('OperationHistory', {
    extend:'Ext.data.Model',
    fields:[
        {name:'id', type:'int'},
        {name:'fileName', type:'string'},
        {name:'fileType', type:'string'},
        {name:'operateTime' ,type:'date', dateFormat:'time'},
        {name:'operateOp', type:'string'},
        {name:'model', type:'string'}
    ]/*,
     proxy:{
     type:'ajax',
     url:path('/operationHistory/lastedOperationLog.json'),
     reader:{
     type:'json',
     root:'result',
     successProperty:'success'
     }
     }*/
});

Ext.define('RestFinanceReport', {
    extend:'Ext.data.Model',
    fields:[
        {name:'financialSerialNum', type:'string'},
        {name:'orderNum', type:'string'},
        {name:'goodsName', type:'string'},
        {name:'createTime', type:'Timestamp'},
        {name:'account', type:'string'},
        {name:'inFee', type:'Double'},
        {name:'outFee', type:'Double'},
        {name:'balance', type:'Double'},
        {name:'mode', type:'string'},
        {name:'type', type:'string'},
        {name:'memo', type:'string'}
    ],
    proxy:{
        type:'ajax',
        url:path('/finance/exportFinanceQueryList'),
        reader:{
            type:'json',
            root:'result',
            totalProperty:'recordCount',
            successProperty:'success'
        }
    }
});

Ext.define('Gift', {
    extend:'Ext.data.Model',
    fields:[
        {name:'skuId', type:'int'},
        {name:'version', type:'int'},
        {name:'brand', type:'string'},
        {name:'cid', type:'string'},
        {name:'skuNo', type:'string'},
        {name:'name', type:'string'},
        {name:'memo', type:'string'}   ,
        {name:'status', type:'string'}
    ],
    proxy:{
        type:'ajax',
        url:path('/gift/list.json'),
        reader:{
            type:'json',
            root:'result',
            totalProperty:'recordCount',
            successProperty:'success'
        }
    }
});

Ext.define('Sku', {
    extend:'Ext.data.Model',
    fields:[
        {name:'id', type:'int'},
        {name:'version', type:'int'},
        {name:'brand', type:'string'},
        {name:'cid', type:'string'},
        {name:'matnr', type:'string'},
        {name:'tmallPrice', type:'float'},
        {name:'sapprice', type:'float'},
        {name:'status', type:'string'}
    ],
    proxy:{
        type:'ajax',
        url:path('/sku/list.json'),
        reader:{
            type:'json',
            root:'result',
            totalProperty:'recordCount',
            successProperty:'success'
        }
    }
});

Ext.define('User', {
    extend:'Ext.data.Model',
    fields:[
        {
            name:'userSysId',
            type:'string'
        },
        {
            name:'userId',
            type:'string'
        },
        {
            name:'userEmail',
            type:'string'
        },
        {
            name:'userPassword',
            type:'string'
        },
        {
            name:'version',
            type:'Integer'
        },
        {
            name:'userStatus',
            type:'string'
        },
        {
            name:'createTime',
            type:'Date'
        },
        {
            name:'updateTime',
            type:'Date'
        },
        {
            name:'createOp',
            type:'string'
        },
        {
            name:'updateOp',
            type:'string'
        },
        {
            name:'userName',
            type:'string'
        },
        {
            name:'userRemark',
            type:'string'
        },
        {
            name:'userRoles',
            type:'string'
        },
        {
            name:'userStatus',
            type:'string'
        }
    ],
    proxy:{
        type:'ajax',
        url:path('/user/list.json'),
        reader:{
            type:'json',
            root:'result',
            totalProperty:'recordCount',
            successProperty:'success'
        }
    }
});

Ext.define('Resource', {
    extend:'Ext.data.Model',
    fields:[
        {
            name:'resourceSysId', //系统ID
            type:'string'
        },
        {
            name:'resourceName', //资源名称
            type:'string'
        },
        {
            name:'resourcePattern', //资源定义
            type:'string'
        },
        {
            name:'resourceStatus', //资源状态
            type:'string'
        },
        {
            name:'resourceDescription', //资源描述
            type:'string'
        },
        {
            name:'createTime',
            type:'Date'
        },
        {
            name:'updateTime',
            type:'Date'
        },
        {
            name:'createOp',
            type:'string'
        } ,
        {
            name:'updateOp',
            type:'string'
        }  ,
        {
            name:'resourceType',
            type:'string'
        } ,
        {
            name:'parentResourceSysId',
            type:'string'
        } ,
        {
            name:'resourceCode',
            type:'string'
        }
    ],
    proxy:{
        type:'ajax',
        url:path('/userRoleResource/list.json'),
        reader:{
            type:'json',
            root:'result',
            totalProperty:'recordCount',
            successProperty:'success'
        }
    }
});

Ext.define('TbTradeComment', {
    extend:'Ext.data.Model',
    fields:[
        {
            name:'tbTradeCommentSysId',
            type:'string'
        },
        {
            name:'omsTradeSysId',
            type:'string'
        },
        {
            name:'commentTitle',
            type:'double'
        },
        {
            name:'commentContent',
            type:'double'
        },
        {
            name:'createTime', type:'date', dateFormat:'time'
        },
        {
            name:'upadteTime',
            type:'string'
        },
        {
            name:'createOp',
            type:'string'
        },
        {
            name:'updateOp',
            type:'string'
        },
        {
            name:'status',
            type:'string'
        }
    ],
    proxy:{
        type:'ajax',
        url:path('/tbTrade/tradeRemark.json'),
        reader:{
            type:'json',
            root:'result',
            idProperty:'tbTradeCommentSysId',
            totalProperty:'recordCount',
            successProperty:'success'
        }
    }
});

Ext.define('RoleResource', {
    extend:'Ext.data.Store',
    fields:[
        {
            name:'omsResourceSysId',
            type:'string'
        },
        {
            name:'omsResourceName',
            type:'string'
        }
    ],
    proxy:{
        type:'ajax',
        url:path('/role/getRoleResources.json'),
        reader:{
            type:'json',
            root:'result'
        }
    },
    autoLoad:true
});

Ext.define('Role', {
    extend:'Ext.data.Store',
    fields:[
        {
            name:'ccRoleSysId',
            type:'string'
        },
        {
            name:'ccRoleName',
            type:'string'
        }
    ],
    proxy:{
        type:'ajax',
        url:path('/user/getUserRoles.json'),
        reader:{
            type:'json',
            root:'result'
        }
    },
    autoLoad:true
});


Ext.define('AreaSalePage', {
    extend:'Ext.data.Model',
    fields:[
        {name:'cid',type:'string'},
        {name:'matrn',type:'string'},
        {name:'price',type:'string'},
        {name:'quantity',type:'string'},
        {name:'pzj',type:'number'},
        {name:'pbj',type:'number'},
        {name:'pgd',type:'number'},
        {name:'phb',type:'number'},
        {name:'psc',type:'number'} ,
        {name:'cd',type:'number'},
        {name:'xa',type:'number'},
        {name:'lz',type:'number'},
        {name:'km',type:'number'},
        {name:'gy',type:'number'},
        {name:'cq',type:'number'} ,
        {name:'wh',type:'number'},
        {name:'nc',type:'number'},
        {name:'zz',type:'number'},
        {name:'cs',type:'number'},
        {name:'bj',type:'number'},
        {name:'ty',type:'number'},
        {name:'jn',type:'number'},
        {name:'sjz',type:'number'},
        {name:'sy',type:'number'},
        {name:'cc',type:'number'},
        {name:'heb',type:'number'},
        {name:'hz',type:'number'},
        {name:'hf',type:'number'} ,
        {name:'sh',type:'number'},
        {name:'nj',type:'number'},
        {name:'wx',type:'number'} ,
        {name:'nb',type:'number'},
        {name:'fs',type:'number'},
        {name:'nn',type:'number'},
        {name:'fz',type:'number'},
        {name:'sz',type:'number'} ,
        {name:'ttl',type:'number'},
        {name:'priceTotal',type:'number'},
        {name:'cidQuantity',type:'number'},
        {name:'cidPrice',type:'number'},
        {name:'quantityPercent',type:'string'},
        {name:'pricePercent',type:'string'}
    ],
    proxy: {
        type: 'ajax',
        url: path('/sale/showAreaSale.json'),
        reader:{
            type:'json',
            root:'result'
        }
    }
});

Ext.define('AreaSalePageSummary', {
    extend:'Ext.data.Model',
    fields:[
        {name:'cid',type:'string'},
        {name:'matrn',type:'string'},
        {name:'price',type:'string'},
        {name:'quantity',type:'string'},
        {name:'pzj',type:'string'},
        {name:'pbj',type:'string'},
        {name:'pgd',type:'string'},
        {name:'phb',type:'string'},
        {name:'psc',type:'string'} ,
        {name:'cd',type:'string'},
        {name:'xa',type:'string'},
        {name:'lz',type:'string'},
        {name:'km',type:'string'},
        {name:'gy',type:'string'},
        {name:'cq',type:'string'} ,
        {name:'wh',type:'string'},
        {name:'nc',type:'string'},
        {name:'zz',type:'string'},
        {name:'cs',type:'string'},
        {name:'bj',type:'string'},
        {name:'ty',type:'string'},
        {name:'jn',type:'string'},
        {name:'sjz',type:'string'},
        {name:'sy',type:'string'},
        {name:'cc',type:'string'},
        {name:'heb',type:'string'},
        {name:'hz',type:'string'},
        {name:'hf',type:'string'} ,
        {name:'sh',type:'string'},
        {name:'nj',type:'string'},
        {name:'wx',type:'string'} ,
        {name:'nb',type:'string'},
        {name:'fs',type:'string'},
        {name:'nn',type:'string'},
        {name:'fz',type:'string'},
        {name:'sz',type:'string'} ,
        {name:'ttl',type:'string'},
        {name:'priceTotal',type:'string'},
        {name:'cidQuantity',type:'string'},
        {name:'cidPrice',type:'string'},
        {name:'quantityPercent',type:'string'},
        {name:'pricePercent',type:'string'}
    ],
    proxy: {
        type: 'ajax',
        url: path('/sale/showAreaSaleSummary.json'),
        reader:{
            type:'json',
            root:'result'
        }
    }
});



//Ext.define('SaleStructurePageModel', {
//    extend:'Ext.data.Model',
//    fields:[
//        {name:'num',type:'string'},
//        {name:'matrn',type:'string'},
//        {name:'saleQuantity',type:'string'},
//        {name:'saleAmount',type:'string'},
//        {name:'saleAvg',type:'string'},
//        {name:'saleQuantityPercentage',type:'string'},
//        {name:'saleAmountPercentage',type:'string'},
//        {name:'sapPrice',type:'string'},
//        {name:'sapAmount',type:'string'} ,
//        {name:'sapDiscount',type:'string'}
//    ]
//});

Ext.define('SaleStructurePage', {
    extend:'Ext.data.Model',
    fields:[
        {name:'num',type:'string'},
        {name:'matnr',type:'string'},
        {name:'saleQuantity',type:'string'},
        {name:'saleAmount',type:'string'},
        {name:'saleAvg',type:'string'},
        {name:'saleQuantityPercentage',type:'string'},
        {name:'saleAmountPercentage',type:'string'},
        {name:'sapPrice',type:'string'},
        {name:'sapAmount',type:'string'} ,
        {name:'sapDiscount',type:'string'}
    ],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: path('/sale/showSaleStructure.json'),
        reader:{
            type:'json',
            root:'result'
        }
    }
});
