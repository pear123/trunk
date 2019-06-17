Ext.onReady(function () {
    Ext.QuickTips.init();
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());

    //northPannel
    var headerPanel = Ext.create('Ext.panel.Panel', {
        border:0,
        autoLoad:{
            url:path('/common/header'),
            scripts:true
        }
    });
    /*    //westPanel
     var leftPanel = Ext.create('Ext.panel.Panel', {
     border: 0,
     autoLoad: {
     url: path('/common/lmenu')
     }
     });*/
    //southPanel
    var bottomPanel = Ext.create('Ext.panel.Panel', {
        border:0,
        autoLoad:{
            url:path('/common/footer')
        }
    });

    var leftAcc = Ext.create('Ext.panel.Panel', {
//        title:'dsdfsdf',
        height:'100%',
        id:'leftAcc',
        width:160,
//        height:300,
        border:0,
//        pack:'center',
        layout:'accordion',
        defaults:{
            // applied to each contained panel
//        bodyStyle: 'padding:15px'
        },
        items:[
            {
                title:'订单管理',
                //html:'1333'
                html:'\
                <dd class="cur"><a hidefocus="true" href="javascript:;">订单列表</a></dd>\
                <dd><a hidefocus="true" href="javascript:;">淘宝订单<a></dd>'

            },
            {
                title:'单据管理',
                //html:'1333'
                html:'\
                <dd><a hidefocus="true" href="javascript:;">收款单</a></dd>\
                <dd><a hidefocus="true" href="javascript:;">退款单</a></dd>\
                <dd><a hidefocus="true" href="javascript:;">收货单</a></dd>\
                <dd><a hidefocus="true" href="javascript:;">退货单<a></dd>'

            },
            {
                title:'售后服务管理',
                //html:'1333'
                html:'\
                <dd><a hidefocus="true" href="javascript:;">功能配置</a></dd>\
                <dd><a hidefocus="true" href="javascript:;">售后申请<a></dd>'

            },
            {
                title:'快递单管理',
                //html:'1333'
                html:'<dd><a hidefocus="true" href="javascript:;">快递单模板</a></dd>'

            }
        ]

    });


    var viewPort = Ext.create('Ext.container.Viewport', {
        layout:'border',
        id:'viewPortPanel',
        style:{
            border:0,
            background:'#d7d7d7'
        },
        defaults:{
//            split:true
        },
        items:[
            {
                region:'north',
                height:95,
                minWidth:800,
                cls:'north-header',
                border:0,

//                split:true,
//                collapsible:true,
                items:headerPanel
            }/*,
             {
             region:'west',
             width:160,
             border:0,
             id:'leftPanel',
             margin:'5 0 5 5',
             //                xtype:'container',
             cls:'bd999',
             layout:'fit',
             items:leftAcc,
             split:true,
             collapsible:true,
             hideCollapseTool:true,
             collapseMode:'mini'
             }*/,
            {
                region:'center',
                border:0,
                style:{
                    bakcgournd:'none'
                },
                layout:'fit',
                height:'100%',
                cls:'rmain bd999',
                margin:'5 5 5 0',
                id:'center'
            },
            {
                region:'south',
                height:85,
                border:0,
                split:true,
                collapsible:true,
                items:bottomPanel
            }
        ]
    });

    var hideMask = function () {
        Ext.get('loading').remove();
        Ext.fly('loading-mask').animate({
            opacity:0,
            remove:true
        });
    };

    Ext.defer(hideMask);
});//end_Ext.onReady
