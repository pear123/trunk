Ext.core.Element.prototype.unselectable = function(){
    var me = this;
    if(me.dom.className.match(/(x-grid-table|x-grid-view)/)) {
        me.dom.unselectable = "";
        console.log('xuxinran');
        me.swallowEvent("selectstart", false);
        me.applyStyles("-moz-user-select:-moz-none;-khtml-user-select:none;");
        me.addCls(Ext.baseCSSPrefix + 'unselectable');
        return me;
    }
    me.dom.unselectable = "on";
    me.swallowEvent("selectstart", true);
    me.applyStyles("-moz-user-select:none;-khtml-user-select:none;");
    me.addCls(Ext.baseCSSPrefix + 'unselectable');
    return me;
};
Ext.apply(Ext.view.TableChunker, {
    metaRowTpl:[
        '<tr xuxinran=\'1\' class="' + Ext.baseCSSPrefix + 'grid-row {addlSelector} {[this.embedRowCls()]}" {[this.embedRowAttr()]}>',
        '<tpl for="columns">',
        '<td class="{cls} ' + Ext.baseCSSPrefix + 'grid-cell ' + Ext.baseCSSPrefix + 'grid-cell-{columnId} {{id}-modified} {{id}-tdCls} {[this.firstOrLastCls(xindex, xcount)]}" {{id}-tdAttr}><div unselectable="" class="' + Ext.baseCSSPrefix + 'grid-cell-inner ' + Ext.baseCSSPrefix + 'unselectable" style="{{id}-style}; text-align: {align};">{{id}}</div></td>',
        '</tpl>',
        '</tr>'
    ]
});
Ext.define('Oms.grid.Panel', {
    alias:'widget.omsGrid',
    extend:'Ext.grid.Panel',
    requires:['Ext.selection.CheckboxModel'],
    border:0,
    scroll:true,
    columnLines:true,
    selModel:Ext.create('Ext.selection.CheckboxModel', {
        checkOnly:false
    }),
    pageSize:25,
    displayMsg:'显示第 {0} - {1} 条记录，共 {2} 条',
    emptyMsg:'没有记录',
    clearFilterMsg:'清除查询条件',
    autoAdded:true, //是否添加到id为center的面板中
    topBarAutoShow:true, //是否显示头尾
    topBarHight:25, //头显示的高度
    autoLoadStore:true, //是否自动加载列表
    initComponent:function () {
        var me = this;
        me.setStore();
        me.setDockItems(me.topDockedItems, me.store);
        me.setFilter(me.filterItems);
//        me.setSelectFeatures();
        Ext.apply(me,{
            viewConfig: {
                stripeRows: true,
                enableTextSelection: true//启用文字选择
            }
        });

        me.callParent();
//        if (me.autoAdded) {
//            Ext.getCmp('center').add(this);
//        }

    },

    onHide:function () {
        console.log('grid was hide...');
    },

    setStore:function () {
        var me = this;

        me.store = Ext.create('Ext.data.Store', {
            model:me.model,
            pageSize:me.pageSize,

            autoLoad:this.autoLoadStore,
            remoteSort:true
        });

    },

    setDockItems:function (topDockedItems, store) {
        var me = this;
        var clearFilterBtn = {
            text:me.clearFilterMsg,
            height:24,
            iconCls:'icon-clearAll',
            handler:function () {
                me.filters.clearFilters();
            }
        };

        // topDockedItems.push(clearFilterBtn);

        var topDock = {
            dock:'top',
            height:me.topBarHight, //36
//            padding: '0 10px',
            xtype:'toolbar',
            items:topDockedItems
        };

        //页面分页相关信息的显示
        var bottomDock = {
            dock:'bottom',
            height:23, //30
            xtype:'pagingtoolbar',
//            padding: '0 10px',
            store:store,
            displayInfo:true,
            displayMsg:me.displayMsg,
            emptyMsg:me.emptyMsg
        };

        //是否显示grid的头和尾
        if (me.topBarAutoShow) {
            me.dockedItems = [topDock, bottomDock];
        }
//        else
//        {
//        	me.dockedItems = [bottomDock];
//        }
    },

    setFilter:function (filterItems) {
        var me = this;

        if (filterItems) {
            var filter = {
                ftype:'filters',
                encode:true,
                local:false,
                menuFilterText:messages['ext.filters.menuFilterText'],
                filters:filterItems
            };

            me.features = [filter];
        }
    }

//    setSelectFeatures: function(){
//    	var me = this;
//    	var selectFeature = Ext.create('Myext.grid.SelectFeature');
//    	me.features = [selectFeature];
//    }

});

// Smartec.form.Panel
Ext.define('Oms.form.Panel', {
    alias: 'widget.omsform',
    extend: 'Ext.form.Panel',
    border: false,
    padding:'10px',
    bodyBorder: false,
    tab: false,

    constructor: function() {
        this.callParent(arguments);
    },

    initComponent: function() {
        var me = this;
        if (me.tab) {
            me.setTabPanel();
        }
        me.callParent();
    },

    setTabPanel: function() {
        var me = this;
        me.items = {
            xtype: 'tabpanel',
            activeTab: 0,
            defaults:{
                bodyStyle: 'padding:10px'
            },
            plain: true,
            items: me.items
        };
    }
});
