/*
 * Copyright (c) 2000-2011 arvato systems (Shanghai)
 * $Id: smartec-store.js 2011-05-31 18:23:46 by Sawyer $
 */
var path=function(uri){return '/bsh'+uri};
var getEnumStore = function() {
    
    
    
}

var booleanStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [{
        'label':messages['ext.catalog.productattr.text.yes'],
        'value':1
    },{
        'label':messages['ext.catalog.productattr.text.no'],
        'value':0
    }]
});
var states = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data : [
     	{"label":"所有库存", "value":"所有库存"},
        {"label":"退货", "value":"退货"},
        {"label":"换货", "value":"换货"},
        {"label":"取消", "value":"取消"},
        {"label":"库存更新", "value":"库存更新"},
        {"label":"产品库存内部调整", "value":"产品库存内部调整"}
    ]
});


Ext.define('Role', {
    extend:'Ext.data.Model',
    fields:[
        {
            name:'roleSysId', //系统ID
            type:'string'
        },
        {
            name:'roleName', //角色名
            type:'string'
        },
        {
            name:'roleId', //角色ID
            type:'string'
        },
        {
            name:'roleStatus', //角色状态
            type:'string'
        },
        {
            name:'roleDescription', //描述
            type:'string'
        },
        {
            name:'roleResources', //资源
            type:'string'
        }
    ],
    proxy:{
        type:'ajax',
        url:path('/userRole/list.json'),
        reader:{
            type:'json',
            root:'result',
            totalProperty:'recordCount',
            successProperty:'success'
        }
    }
});


var productTypeStore  = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    proxy: {
        type: 'ajax',
        url: path('/catalog/products/getProductType'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var frontendInputStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    proxy: {
        type: 'ajax',
        url: path('/catalog/productattrs/getInput'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var productAttributeSetStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    proxy: {
        type: 'ajax',
        url: path('/catalog/products/getProductAttrSet'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var isEnableStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [{
        'label':messages['customer.enable.Enable.message'],
        'value':'Enable'
    },{
        'label':messages['customer.enable.NotEnable.message'],
        'value':'NotEnable'
    }]
});

var queryConditionStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':field['orderNO'],
	        'value':'omsOrderNo'
    	}
//    ,{
//        'label':messages['customer.enable.NotEnable.message'],
//        'value':'NotEnable'
//    }
    ]
});

var sapcodeStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':"SAP Code",
	        'value':'sapCode'
    	}
    	//    ,{
//        'label':messages['customer.enable.NotEnable.message'],
//        'value':'NotEnable'
//    }
    ]
});
var barcodeStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':"BAR Code",
	        'value':'barCode'
    	}
    	//    ,{
//        'label':messages['customer.enable.NotEnable.message'],
//        'value':'NotEnable'
//    }
    ]
});
var pronameStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':"产品名称",
	        'value':'proName'
    	}
//    ,{
//        'label':messages['customer.enable.NotEnable.message'],
//        'value':'NotEnable'
//    }
    ]
});


var customerTypeStore = Ext.create('Ext.data.Store',{
    fields:['label', 'value'],
    data: [{
        'label':messages['customer.type.Normal.message'],
        'value':'Normal'
    },{
        'label':messages['customer.type.Anonymous.message'],
        'value':'Anonymous'
    }]
});

var orderStatusStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    proxy: {
        type: 'ajax',
        url: path('/orders/getOrderStatus'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

//var orderTypeStore = Ext.create('Ext.data.Store', {
//    fields: ['label', 'value'],
//    proxy: {
//        type: 'ajax',
//        url: path('/orders/getOrderType'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    }
//});

var payStatusStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    proxy: {
        type: 'ajax',
        url: path('/orders/getPayStatus'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var shippingStatusStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    proxy: {
        type: 'ajax',
        url: path('/orders/getShippingStatus'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var shippingMethodStore = Ext.create('Ext.data.Store', {
    fields: [
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'name',
        type: 'string'
    }
    ],
    proxy: {
        type: 'ajax',
        url: path('/orders/getShippingMethod'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var paymentMethodStore = Ext.create('Ext.data.Store', {
    fields: [
    {
        name: 'id',
        type: 'int'
    },
    {
        name: 'name',
        type: 'string'
    }
    ],
    proxy: {
        type: 'ajax',
        url: path('/orders/getPaymentMethod'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});


var invoiceTypeStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    proxy: {
        type: 'ajax',
        url: path('/orders/getInvoiceType'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});




//var categoryTreeStore = Ext.create('Ext.data.TreeStore', {
//    proxy: {
//        type: 'ajax',
//        url: path('/catalog/products/getCategoryTreeNodes'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    },
//    root: {
//        text: 'Root',
//        id: '0',
//        expanded: true
//    }
//});

//var chartDateTypeStore = Ext.create('Ext.data.Store', {
//    fields: ['label', 'value'],
//    proxy: {
//        type: 'ajax',
//        url: path('/charts/getChartDateType'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    }
//});

var orderAmountChartStore = Ext.create('Ext.data.Store', {
    fields: [
    {
        name: 'orderDate',
        type: 'string'
    },
    {
        name: 'sumOrderAmount',
        type: 'float'
    }
    ],
    proxy: {
        type: 'ajax',
        url: path('/charts/getSumOrderAmount'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});
//订单状态Robin
var selectOrderType = Ext.create('Ext.data.Store', {
	/*fields: ['label', 'value'],
	   proxy: {
	        type: 'ajax',
	        url: path('/order/systemParam.json?param=ORDER_STATUS'),
	        reader: {
	            type: 'json',
	            root: 'result'
	        }
	    },
    autoLoad: true*/
    fields: ['label', 'value'],
    data: [
    {
    	'label':field['allStatus'],
    	'value':""
    },
    {
        'label':field['unfilledOrder'],
        'value':"90"
    }
    ,{
        'label':field['deliveryOrder'],
        'value':"100"
    }
    ,{
        'label':field['deleteOrder'],
        'value':"DELETED"
    }
    ,{
        'label':field['cancelledOrder'],
        'value':"CANCELLED"
    }
    ,{
        'label':field['finishedOrder'],
        'value':"FINISHED"
    }
    ]
});
//订单状态Robin

var orderCountChartStore = Ext.create('Ext.data.Store', {
    fields: [
    {
        name: 'orderDate',
        type: 'string'
    },
    {
        name: 'orderCount',
        type: 'int'
    }
    ],
    proxy: {
        type: 'ajax',
        url: path('/charts/getSumOrderAmount'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});


var orderAmountInfoStore = Ext.create('Ext.data.Store', {
    fields: [
    {
        name: 'totalOrderAmount',
        type: 'float'
    },
    {
        name: 'avgOrderAmount',
        type: 'int'
    }
    ],
    proxy: {
        type: 'ajax',
        url: path('/charts/getOrderAmountInfo'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});



//******************  storeId start cao
//var orderStoreIdStore = Ext.create('Ext.data.Store', {
//   fields: ['label', 'value'],
//   proxy: {
//        type: 'ajax',
//        url: path('/omsOrder/store.json'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    }
//});
//********排序
var sortStore=Ext.create('Ext.data.Store',{
    fields:['label','value'],
   data: [{
        'label':'热销',
        'value':'desc'
    },{
        'label':'滞销',
        'value':'ase'
    }]
});
//var storeIdStore = Ext.create('Ext.data.Store', {
//   fields: ['label', 'value'],
//   proxy: {
//        type: 'ajax',
//        url: path('/omsOrder/store.json'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    },
//	autoLoad:true
//});


var orderExceptionTypeStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=ORDER_EXCEPTION_TYPE'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var orderCancelReasonStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   data : [
   		{
	   		'label':'买家退款',
	   		'value':'买家退款'
   		},{
   			'label':'缺货',
	   		'value':'缺货'
   		},{
   			'label':'修改订单',
	   		'value':'修改订单'
   		},{
   			'label':'误操作',
	   		'value':'误操作'
   		},{
   			'label':'其他',
	   		'value':'其他'
   		}
   
   
   ]
//   proxy: {
//        type: 'ajax',
//        url: path('/order/systemParam.json?param=CANCEL_REASON'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    }
});

/***用户角色***/
var userRole = Ext.create('Ext.data.Store',{
	fields: [{
        name: 'omsRoleSysId',
        type: 'string'
    },
    {
        name: 'omsRoleName',
        type: 'string'
    }],
    proxy: {
        type: 'ajax',
        url: path('/user/getUserRoles.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

/***用户商店***/
var userStore = Ext.create('Ext.data.Store',{
	fields: [{
        name: 'omsStoreSysId',
        type: 'string'
    },
    {
        name: 'omsStoreName',
        type: 'string'
    }],
    proxy: {
        type: 'ajax',
        url: path('/omsUser/getUserStores.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//******************  storeId end cao

//******************  storeId start neal
//退货单状态
var orderReturnStatusStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=RETURN_ORDER_STATUS'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

//退换货类型
var orderReturnChangeTypeStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=RETURN_CHANGE_TYPE'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

//退货阶段
var orderReturnTypeStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=RETURN_TYPE'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//退货原因
var orderReturnReasonStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=RETURN_REASON'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});
//******************  storeId end neal

//lk
var orderRefundStatusStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=REFUND_STATUS'),
        reader: {
            type: 'json',
            root: 'result'
        }
    }
});

var shippingMwthodStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=SHIP_METHOD&isnotAll=y'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});


var warehouseNameStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsVirtualWarehouse/addWarehouseList.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

var warehouseNameStoreSearch = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsVirtualWarehouse/addWarehouseList.json?param=search'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

var warehouseNameIdStore = Ext.create('Ext.data.Store', {
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
/***仓库物流***/
var delivartVendorChoose = Ext.create('Ext.data.Store',{
	fields: [{
        name: 'omsDeliveryVendorSysId',
        type: 'string'
    },
    {
        name: 'omsShippingMethodName',
        type: 'string'
    }],
    proxy: {
        type: 'ajax',
        url: path('/omsWarehouse/getDeliveryVendors.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end

//订单内部状态Robin
//var orderSeniorStoreId = Ext.create('Ext.data.Store', {
//   fields: ['label', 'value'],
//   proxy: {
//        type: 'ajax',
//        url: path('/omsOrder/store.json'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    },
//    autoLoad: true
//});
var orderProdcingStatus = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=ORDER_STATUS'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//退货单基本状态
var orderReturnBasicStatusStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=ORDER_RETURN_BASIC_STATUS'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//订单基本状态Robin
var orderReturnBasicStatus = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=ORDER_BASIC_STATUS'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//地区Robin
var regionCode = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=REGION_CODE'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//客户Robin
var customerList = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsStore/addStoreCustomerList.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//资源模块 Qin
var resourceModelList = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=RESOURCE_MODEL'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//资源子系统 Qin
var resourceSubSystemList = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=RESOURCE_SUBSYSTEM'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//资源子模块 Qin
var resourceSubModelList = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=RESOURCE_SUBMODEL'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end

//所有用到商店ID下拉框的(添加信息)
//var addAllStoreId = Ext.create('Ext.data.Store', {
//   fields: ['label', 'value'],
//   proxy: {
//        type: 'ajax',
//        url: path('/omsOrder/store.json?v=1'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    },
//    autoLoad: true
//});

//品牌
var addBrand = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsProduct/param.json?param=OmsBrandAll'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

////所有系列下拉框，修改
//var allSeries = Ext.create('Ext.data.Store', {
//   fields: ['label', 'value'],
//   proxy: {
//        type: 'ajax',
//        url: path('/omsProduct/param.json?param=OmsSeriesAll'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    },
//    autoLoad: true
//});

//所有系列品牌下拉框的(添加信息)
var addSeries = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsProduct/param.json?param=OmsSeriesAll'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//商品类型 修改
var updateProductType = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsProduct/addProductList.json?update=1'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//供应商
var productVendor = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsProduct/addProductList.json?v=1'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//资源子模块 Qin
var omsOrganizationlist = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=EC_ORGANIZATION'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//角色资源 kobs
var roleResource = Ext.create('RoleResource',{
	fields: [{
		        name: 'omsResourceSysId',
		        type: 'string'
		    },
		    {
		        name: 'omsResourceName',
		        type: 'string'
		    }],
    proxy: {
        type: 'ajax',
        url: path('/role/getRoleResources.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//tbtradestatus robin
var tbTradeStatus = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"所有状态",
    	'value':""
    },
    {
        'label':"待淘宝导入",
        'value':"10"
    }
    ,{
        'label':"待验证",
        'value':"20"
    } ,
    {
        'label':"待导入OMS",
        'value':"30"
    },
    {
        'label':"已导入OMS",
        'value':"40"
    },
    {
        'label':"异常订单",
        'value':"50"
    }
    ]
});
//end
//tbtradeWttstatus caoliping
var tbTradeWttStatus = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"所有状态",
    	'value':""
    },
    {
        'label':"未付款",
        'value':"WAIT_BUYER_PAY"
    }
    ,{
        'label':"未付款取消",
        'value':"TRADE_CLOSED_BY_TAOBAO"
    } ,
    {
        'label':"交易成功",
        'value':"TRADE_FINISHED"
    }
    ]
});
//end

//tbtradeType robin
var tbTradeType = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"原始订单号",
    	'value':"tid"
    }
    ]
});
//end

//tbtradeWttType caoliping
var tbTradeWttType = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"原始订单号",
    	'value':"tid"
    }
    ]
});
//end


//商店模块 cao
var yesOrNo = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [{
    	'label':"是",
    	'value':"1"
    },
    {
    	'label':"否",
    	'value':"0"
    }]
});
//商店模块 

//调度器的时间间隔的时
var hours = Ext.create('Ext.data.Store',{
	fields: ['label', 'value'],
	proxy: {
        type: 'ajax',
        url: path('/tbCrontab/getDelayTime.json?count=24'),
        reader: {
            type: 'json',
            root: 'result',
            totalProperty: 'recordCount',
            successProperty: 'success'
        }
    },
    autoLoad: true
});

//调度器的时间间隔的分/秒
var min_sec = Ext.create('Ext.data.Store',{
	fields: ['label', 'value'],
	proxy: {
        type: 'ajax',
        url: path('/tbCrontab/getDelayTime.json?count=60'),
        reader: {
            type: 'json',
            root: 'result',
            totalProperty: 'recordCount',
            successProperty: 'success'
        }
    },
    autoLoad: true
});

var flagsStore = Ext.create('Ext.data.Store',{
	fields: ['label', 'value'],
	proxy: {
        type: 'ajax',
        url: path('/TbCrontab/getFlags.json'),
        reader: {
            type: 'json',
            root: 'result',
            totalProperty: 'recordCount',
            successProperty: 'success'
        }
    },
    autoLoad: true
});


//添加赠品的商品列表
//var giftStore = Ext.create('Ext.data.Store',{
//	fields: [ 'omsProductSysId','omsProductName','omsProductSapcode','omsProductBarcode','omsProductStatus'],
//	proxy: {
//        type: 'ajax',
//        url: path('/omsProduct/productList.json?type=omsProductType&value=NORMAL'),
//        reader: {
//            type: 'json',
//            root: 'result',
//            totalProperty: 'recordCount',
//            successProperty: 'success'
//        }
//    },
//    autoLoad: true
//});
//是否通过
var isPassStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [{
        'label':messages['order.ordersConfirm.pass.message'],
        'value':'Enable'
    },{
        'label':messages['order.ordersConfirm.notThrough.message'],
        'value':'NotEnable'
    }]
});
//发票内容
var orderInvoiceStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=INVOICE_CONTENT&isnotAll=y'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//所属品牌 qin
var seriesBrand = Ext.create('Ext.data.Store',{
	fields: [{
		        name: 'omsBrandSysId',
		        type: 'string'
		    },
		    {
		        name: 'brandName',
		        type: 'string'
		    }],
    proxy: {
        type: 'ajax',
        url: path('/omsSeries/getSeriesBrand.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//end
//促销活动类型 zhong
var activityTypeStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=PROMOTION_ACTIVITY_TYPE&isnotAll=y'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//促销活动状态 zhong
var activityStatusStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=PROMOTION_ACTIVITY_STATUS&isnotAll=y'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//促销活动范围 zhong
var activityScopeStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=PROMOTION_ACTIVITY_SCOPE&isnotAll=y'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});
//促销活动下拉框Neal
var addPromotionActivitys = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsPromotionInfo/selectPromotionActivitys.json?param=PromotionActivitys'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//促销赠品状态 Neal
var largessStatus = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/order/systemParam.json?param=PROMOTION_LARGESS_STATUS&isnotAll=y'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//促销赠品下拉 Neal
var largessInfo = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsPromotionLargess/selectPromotionLargess.json?param=LARGESSINFO'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//地区维护中，虚拟仓库下拉框
var virtualWarehouseStore = Ext.create('Ext.data.Store',{
	fields: [{
		        name: 'omsVirtualWhSysId',
		        type: 'string'
		    },
		    {
		        name: 'omsVirtualWarehouseName',
		        type: 'string'
		    }],
    proxy: {
        type: 'ajax',
        url: path('/omsVirtualWarehouse/list.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});




var selecStatus = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
        'label':'启用',
        'value':'1'
    },
    {
        'label':'禁用',
        'value':'0'
    }
    ]
});
//国家维护中 大洲的list
var continentStore = Ext.create('Ext.data.Store',{
	fields: [ 'omsAddressSysId','text'],
	proxy: {
        type: 'ajax',
        url: path('/omsAddress/countryList.json'),
        reader: {
            type: 'json',
            root: 'result',
            totalProperty: 'recordCount',
            successProperty: 'success'
        }
    },
    autoLoad: true
});

var allProvince = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsAddress/param.json?param=OmsProvince'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//级联市 
var cascadeCity = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsAddress/param.json?param=OmsCity'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//礼品券列表
var selectGiftCard = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsPromotionCardInfo/selectPromotionGiftCardList.do'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

var remarkSelectStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data : [
     	{"label":"创建人", "value":"createOp"},
        {"label":"备注内容", "value":"commentContent"},
        {"label":"订单号", "value":"omsOrder.omsOrderSysId"},
        {"label":"备注标识", "value":"commentTitle"}
    ]
});

//品类查询属性
var selectCategory = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"品类CODE",
    	'value':"categoryCode"
    },
    {
        'label':"品类名称",
        'value':"categoryName"
    }
    ]
});

//品牌查询属性
var selectBrand = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"品牌CODE",
    	'value':"brandCode"
    },
    {
        'label':"品牌名称",
        'value':"brandName"
    }
    ]
});

////所属品类
//var brandCategory = Ext.create('Ext.data.Store',{
//	fields: [{
//		        name: 'omsCategorySysId',
//		        type: 'string'
//		    },
//		    {
//		        name: 'categoryName',
//		        type: 'string'
//		    }],
//    proxy: {
//        type: 'ajax',
//        url: path('/omsBrand/getBrandCategory.json'),
//        reader: {
//            type: 'json',
//            root: 'result'
//        }
//    },
//    autoLoad: true
//});

//所有用到品类下拉框的(添加信息)
var addAllCategory = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsProduct/param.json?param=OmsCategory'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//系列查询属性
var selectSeries = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"系列CODE",
    	'value':"seriesCode"
    },
    {
        'label':"系列名称",
        'value':"seriesName"
    }
    ]
});

//预售商品，赠品状态
var specialProductStatus = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"有效",
    	'value':"1"
    },
    {
        'label':"无效",
        'value':"0"
    }
    ]
});

//预售商品，赠品状态
var productVirtualStatus = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"启用",
    	'value':"1"
    },
    {
        'label':"禁用",
        'value':"0"
    }
    ]
});

//预售商品,赠品查询条件
var selectPreProProduct = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"商品名称",
    	'value':"productName"
    },
    {
        'label':"商品编码",
        'value':"productSapcode"
    },
    {
        'label':"商品条形码",
        'value':"productBarcode"
    }
    ]
});

//开关类型 cao
var orderSwitchType = Ext.create('Ext.data.Store', {
	fields: ['label', 'value'],
	   proxy: {
	        type: 'ajax',
	        url: path('/order/systemParam.json?param=ORDER_SWITCH&isnotAll=y'),
	        reader: {
	            type: 'json',
	            root: 'result'
	        }
	    },
    autoLoad:true
});

var orderSwitchTypeAll = Ext.create('Ext.data.Store', {
	fields: ['label', 'value'],
	   proxy: {
	        type: 'ajax',
	        url: path('/order/systemParam.json?param=ORDER_SWITCH'),
	        reader: {
	            type: 'json',
	            root: 'result'
	        }
	    },
    autoLoad:true
});
//开关状态 cao
var orderSwitchStatus = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"全部",
    	'value':""
    },
    {
    	'label':"自动",
    	'value':"0"
    },
    {
        'label':"手动",
        'value':"1"
    }
    ]
});


//虚拟产品查询条件
var selectProductVirtual = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"虚拟产品CODE",
    	'value':"productVirtualCode"
    },
    {
        'label':"虚拟产品名称",
        'value':"productVirtualName"
    }
    ]
});

//虚拟仓库搜索
var selectVirtualWarehouse = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"虚拟仓库ID",
    	'value':"omsVirtualWhId"
    },
    {
        'label':"虚拟仓库名称",
        'value':"omsVirtualWarehouseName"
    }
    ]
});

//出库单搜索
var selectStkout = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"原订单号",
    	'value':"osh.stkoutOrders"
    },
    {
        'label':"出库申请单号",
        'value':"osh.omsStkoutNo"
    },
    {
        'label':"订单号",
        'value':"oo.omsOrderNo"
    }
    ]
});

//物流公司
var delivaryIdStore = Ext.create('Ext.data.Store', {
	   fields: ['label', 'value'],
	   proxy: {
	        type: 'ajax',
	        url: path('/omsDelivary/delivary.json'),
	        reader: {
	            type: 'json',
	            root: 'result'
	        }
	    },
	    autoLoad:true
	});

//物流公司
var omsDelivaryInclueAll = Ext.create('Ext.data.Store', {
	   fields: ['label', 'value'],
	   proxy: {
	        type: 'ajax',
	        url: path('/omsDelivaryInclueAll/delivary.json'),
	        reader: {
	            type: 'json',
	            root: 'result'
	        }
	    },
	    autoLoad:true
	});

//虚拟仓库列表
var selectVirtualWarehouseValues = Ext.create('Ext.data.Store', {
	   fields: ['label', 'value'],
	   proxy: {
	        type: 'ajax',
	        url: path('/omsVirtualWarehouse/addVirtualWarehouseList.json'),
	        reader: {
	            type: 'json',
	            root: 'result'
	        }
	    },
	    autoLoad:true
	});
//地址所有的省份 cao
var provinceStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsAddress/getProvinceList.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad:true
});
//退货入库单搜索
var selectStkin = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"退货单号",
    	'value':"osh.stkin_Rtn_Orders"
    },
    {
    	'label':"原订单号",
    	'value':"oo.source_order_no"
    },
    {
    	'label':"订单号",
    	'value':"osh.order_No"
    },
    {
        'label':"退货入库单号",
        'value':"osh.oms_Stkin_No"
    }
    ]
});

var addressWarehouseStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':'地区CODE',
	        'value':'omsAddressCode'
    	}
	    ,{
	        'label':'地区名称',
	        'value':'omsAddressName'
	    }
    ]
});

var IsPresellStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':'正常',
	        'value':'normal'
    	}
	    ,{
	        'label':'预售',
	        'value':'presell'
	    }
    ]
});

var IsPickUp = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':'自提',
	        'value':'pick'
    	}
	    ,{
	        'label':'发货',
	        'value':'delivery'
	    }
    ]
});

var exceptionQueryStore = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':'订单号',
	        'value':'oo.omsOrderNo'
    	},{
	        'label':'原订单号',
	        'value':'oo.sourceOrderNo'
	    },{
	        'label':'异常原因明细',
	        'value':'ex.exceptionDescription'
    	}
    ]
});

//产品查询属性-1
var selectProduct = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"商品名称",
    	'value':"omsProductName"
    },
    {
        'label':"商品编码",
        'value':"omsProductSapcode"
    }
    ,
    {
        'label':"商品条形码",
        'value':"omsProductBarcode"
    }
//    ,{
//        'label':"商品SKU",
//        'value':"omsProductSku"
//    }
    ]
});

//产品查询属性-2
var selectProductSpecial = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    {
    	'label':"商品名称",
    	'value':"oms_Product_Name"
    },
    {
        'label':"商品编码",
        'value':"oms_Product_Sapcode"
    }
    ,{
        'label':"商品条形码",
        'value':"oms_Product_Barcode"
    }
//    ,{
//        'label':"商品SKU",
//        'value':"omsProductSku"
//    }
    ]
});

//商品类型 增加
var omsProductType = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsProduct/addProductList.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//搜索列表
var omsProductTypeSearch = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsProduct/addProductList.json?all=1'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//商店列表
var addOmsStore = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsStore/ComboStoreList.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//商店下虚拟仓库列表
var storeVWhouse = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsVirtualWarehouse/storeWarehouseListStore.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//虚拟仓库表
var allStoreVwarehouse = Ext.create('Ext.data.Store', {
   fields: ['label', 'value'],
   proxy: {
        type: 'ajax',
        url: path('/omsVirtualWarehouse/getAllVirtualWarehouse.json'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});

//退货条件
var queryReturnCondition = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
    	{
	        'label':field['orderNO'],
	        'value':'oo.omsOrderNo'
    	},
    	{
	        'label':'退货单号',
	        'value':'orr.returnBillNo'
    	},
    	{
	        'label':'原订单号',
	        'value':'oo.sourceOrderNo'
    	},
    	{
	        'label':'收货人姓名',
	        'value':'ora.returnContact'
    	},
//    	{
//	        'label':'物流单号',
//	        'value':'orr.returnTrackingNo'
//    	},
//    	{
//	        'label':'物流公司',
//	        'value':'orr.returnShippingMtd'
//    	},
    	{
	        'label':'地址',
	        'value':'ora.returnAddress'
    	},
//    	{
//	        'label':'入库时间',
//	        'value':'orr.receiveTime'
//    	},
//    	{
//	        'label':'仓库',
//	        'value':'orr.returnVirtualWarehouse'
//    	},
    	{
	        'label':'买家ID',
	        'value':'oo.orderConsumer'
    	},
    	{
	        'label':'联系方式',
	        'value':'orr.returnMobile'
    	}
    	
    ]
});

//WTCCNID-1197 WTCCNID-1198 增加   oracleReportType  staticMonth
var oracleReportType = Ext.create('Ext.data.Store', {
    fields: ['label', 'value'],
    data: [
        {
            'label':'佣金报表',
            'value':'brokerage'
        }
        ,{
            'label':'运费报表',
            'value':'shipping'
        }
    ]
});


var staticMonth = Ext.create('Ext.data.Store', {
    fields: ['staicMonth', 'omsPeriodSysId'],
    proxy: {
        type: 'ajax',
        url: path('/finance/queryCompanyDateList'),
        reader: {
            type: 'json',
            root: 'result'
        }
    },
    autoLoad: true
});




