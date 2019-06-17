/**
 * Copyright (c) 2000-2010 arvato systems (Shanghai)
 *
 * $Id: smartec-cmp.js 2011-5-18 19:11:00 Justin $
 */

Ext.Loader.setConfig({
    enabled:true
});

Ext.Loader.setPath('Ext.ux', path('/static/scripts/extjs/ux'));

Ext.require([
    'Ext.grid.*',
    'Ext.form.*',
    'Ext.data.*',
    'Ext.util.*',
    'Ext.toolbar.Paging',
    'Ext.ModelManager',
    'Ext.tip.QuickTipManager',
    'Ext.selection.CheckboxModel',
    'Ext.ux.grid.FiltersFeature'
]);

Ext.require('Ext.grid.Scroller',
    function () {
        Ext.override(Ext.grid.Scroller, {
            afterRender:function () {
                var me = this;
                me.callParent();
                me.mon(me.scrollEl, 'scroll', me.onElScroll, me);
                Ext.cache[me.el.id].skipGarbageCollection = true;

                Ext.EventManager.addListener(me.scrollEl, 'scroll', me.onElScrollCheck, me);
                Ext.cache[me.scrollEl.id].skipGarbageCollection = true;
            },
            wasScrolled:false,
            onElScroll:function (event, target) {
                this.wasScrolled = true; // change flag -> show that listener is alive
                this.fireEvent('bodyscroll', event, target);
            },
            onElScrollCheck:function (event, target, options) {
                var me = this;
                if (!me.wasScrolled) {
                    me.mon(me.scrollEl, 'scroll', me.onElScroll, me);
                }
                me.wasScrolled = false; // change flag to initial value
            }
        });
    }
);

Ext.define('Smartec.upload.Panel',{
    alias:'widget.smartecUpload',
    extend:'Ext.form.Panel',
//    fileUpload: true,
    frame: true,
    autoHeight: true,
    bodyStyle: 'padding: 10px 10px 0 10px;',
    labelWidth: 50,
    defaults: {
        anchor: '95%',
//        allowBlank: false,
        msgTarget: 'side'
    },
//    layout:'border',
//    padding:'10px',
    initComponent:function () {
        var me = this;
        me.callParent();
    }
});





////自定义一个feature，启用文本选择功能，通过替换取消unselectable样式，同时增加x-selectable样式
///** 
// * define the select feature 
// */ 
//Ext.define('Myext.grid.SelectFeature', {
//  extend : 'Ext.grid.feature.Feature',  
//  alias : 'feature.selectable',  
//    
//  mutateMetaRowTpl : function(metaRowTpl) {  
//	var i, ln = metaRowTpl.length;  
//    
//    for (i = 0; i < ln; i++) {
//      tpl = metaRowTpl[i];  
//      tpl = tpl.replace(/x-grid-row/, 'x-grid-row x-selectable');  
//      tpl = tpl.replace(/x-grid-cell-inner x-unselectable/g, 'x-grid-cell-inner');  
//      tpl = tpl.replace(/x-unselectable="on"/g, '');  
//      metaRowTpl[i] = tpl;  
//    };
//     } 
//});

Ext.define('Smartec.MainMenuBar', {
    alias:'widget.mainmenubar',
    extend:'Ext.toolbar.Toolbar',
    items:[
        {
            text:'Menu1',
            menu:{
                items:[
                    {
                        text:'testButton',
                        action:'testAction'
                    }
                ]
            }
        }
    ]
});


// Smartec.form.Panel
Ext.define('Smartec.form.Panel', {
    alias:'widget.smartecform',
    extend:'Ext.form.Panel',
    border:false,
    padding:'10px',
    bodyBorder:false,
    tab:false,

    constructor:function () {
        this.callParent(arguments);
    },

    initComponent:function () {
        var me = this;
        if (me.tab) {
            me.setTabPanel();
        }
        me.callParent();
    },

    setTabPanel:function () {
        var me = this;
        me.items = {
            xtype:'tabpanel',
            activeTab:0,
            defaults:{
                bodyStyle:'padding:10px'
            },
            plain:true,
            items:me.items
        };
    }
});

var itemsPerPage = 10;

// Smartec.grid.Panel
Ext.define('Smartec.grid.Panel', {
    alias:'widget.smartecgrid',
    extend:'Ext.grid.Panel',
    requires:['Ext.selection.CheckboxModel'],
    border:0,
    scroll:true,
    columnLines:true,
//    selType:'checkboxModel',
    selModel:Ext.create('Ext.selection.CheckboxModel', {
        checkOnly:false,
        injectCheckbox:1
    }),
    pageSize:itemsPerPage,
    displayMsg:'显示第 {0} - {1} 条记录，共 {2} 条',
    emptyMsg:'没有记录',
    clearFilterMsg:'清除查询条件',
    autoAdded:true, //是否添加到id为center的面板中
    topBarAutoShow:true, //是否显示头尾
    topBarHight:10, //头显示的高度
    autoLoadStore:true, //是否自动加载列表
    autoSetStore:true,
    initComponent:function () {
        var me = this;

        me.setStore();
        me.setCombo();
        me.setSelect(me.combo,me.store);
        me.setDockItems(me.topDockedItems, me.store,me.combo);
        me.setFilter(me.filterItems);
//        me.setSelectFeatures();
        me.callParent();
        if (me.autoAdded) {
            Ext.getCmp('center').add(this);
        }

    },
    setStore:function () {
        var me = this;
        if (me.autoSetStore) {
            me.store = Ext.create('Ext.data.Store', {
                model:me.model,
                pageSize:me.pageSize,
                groupField:me.groupField,
                autoLoad:this.autoLoadStore,
                remoteSort:true
            });
        }
    },
    setCombo:function(){
        var me=this;
        me.combo= Ext.create('Ext.form.ComboBox',{
            name: 'pagesize',
            hiddenName: 'pagesize',
            store: new Ext.data.ArrayStore({
                fields: ['text', 'value'],
                data: [['10', 10],['50', 50], ['100', 100],['150', 150], ['200', 200]]
            }),
            valueField: 'value',
            displayField: 'text',
            emptyText:10,
            width: 50
        });
    },
    setDockItems:function (topDockedItems, store,combo) {
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
            id:'pagingbar',
            dock:'bottom',
            height:23, //30
            xtype:'pagingtoolbar',
//            padding: '0 10px',
            store:store,
            displayInfo:true,
            displayMsg:me.displayMsg,
            emptyMsg:me.emptyMsg,
            items: ['-', '每页显示',combo,'条']
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

    setSelect:function(combo,store){
        combo.on("select", function (comboBox) {
            var pagingToolbar=Ext.getCmp('pagingbar');
            pagingToolbar.pageSize = parseInt(comboBox.getValue());
            itemsPerPage = parseInt(comboBox.getValue());//更改全局变量itemsPerPage
            store.pageSize = itemsPerPage;//设置store的pageSize，可以将工具栏与查询的数据同步。
            store.load({
                params:{
                    start:0,
                    limit: itemsPerPage
                }
            });//数据源重新加载
            store.loadPage(1);//显示第一页

        });
    },
    setFilter:function (filterItems) {
        var me = this;


        if (me.features) {
            var tmp = [];

            if (!Ext.isArray(me.features)) {
                tmp.push(me.features);
            } else {
                tmp = me.features;
            }
            if (filterItems) {
                var filter = {
                    ftype:'filters',
                    encode:true,
                    local:false,
                    menuFilterText:messages['ext.filters.menuFilterText'],
                    filters:filterItems
                };
                tmp.push(filter);
            }

            me.features = tmp;
        }
    }

//    setSelectFeatures: function(){
//    	var me = this;
//    	var selectFeature = Ext.create('Myext.grid.SelectFeature');
//    	me.features = [selectFeature];
//    }

});

// Smartec.grid.Panel
Ext.define('Smartec.grid.PanelTest', {
    alias:'widget.smartecgridTest',
    extend:'Ext.grid.Panel',
    border:0,
    scroll:true,
    autoAdded:true, //是否添加到id为center的面板中
    topBarAutoShow:true, //是否显示头尾
    topBarHeight:10, //头显示的高度
    autoLoadStore:true, //是否自动加载列表
    autoSetStore:true,
    initComponent:function () {
        var me = this;

//        me.setStore();

        me.callParent();
        if (me.autoAdded) {
            Ext.getCmp('center').add(this);
        }

    }
//    setStore:function () {
//        var me = this;
//        if (me.autoSetStore) {
//            me.store = Ext.create('Ext.data.Store', {
//                model:me.model,
//                autoLoad:this.autoLoadStore,
//                remoteSort:true
//            });
//        }
//    }

});





Ext.define('Second.grid.Panel', {
    alias:'widget.second.grid',
    extend:'Ext.grid.Panel',
    requires:['Ext.selection.CheckboxModel'],
    selModel:Ext.create('Ext.selection.CheckboxModel', {
        checkOnly:false
    }),
    border:0,
    scroll:true,
    columnLines:true,
    pageSize:100,
    displayMsg:messages['ext.pagingtoolbar.displayMsg'],
    emptyMsg:messages['ext.pagingtoolbar.emptyMsg'],
    clearFilterMsg:messages['ext.pagingtoolbar.clearFilter'],

    initComponent:function () {
        var me = this;
        me.setStore();
        me.setDockItems(me.topDockedItems, me.store);
        me.setFilter(me.filterItems);
        me.callParent();
    },

    onHide:function () {
        console.log('grid was hide...');
    },

    setStore:function () {
        var me = this;
        me.store = Ext.create('Ext.data.Store', {
            model:me.model,
            pageSize:me.pageSize,
            autoLoad:true,
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

        var topDock = {
            dock:'top',
            height:36,
            padding:'0 10px',
            xtype:'toolbar',
            items:topDockedItems
        };

        var bottomDock = {
            dock:'bottom',
            height:30,
            xtype:'pagingtoolbar',
            padding:'0 10px',
            store:store,
            displayInfo:true,
            displayMsg:me.displayMsg,
            emptyMsg:me.emptyMsg
        };

        me.dockedItems = [topDock, bottomDock];
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
});

Ext.define('Three.grid.Panel', {
    alias:'widget.three.grid',
    extend:'Ext.grid.Panel',
    border:0,
    scroll:true,
    columnLines:true,
    pageSize:1000,
    displayMsg:messages['ext.pagingtoolbar.displayMsg'],
    emptyMsg:messages['ext.pagingtoolbar.emptyMsg'],
    clearFilterMsg:messages['ext.pagingtoolbar.clearFilter'],
    autoAdded:true, //是否添加到id为center的面板中
    topBarAutoShow:true, //是否显示头尾

    initComponent:function () {
        var me = this;

        me.setStore();
        me.setDockItems(me.topDockedItems, me.store);
        me.setFilter(me.filterItems);

        me.callParent();
        if (me.autoAdded) {
            Ext.getCmp('center').add(this);
        }

    },

    onHide:function () {
        console.log('grid was hide...');
    },

    setStore:function () {
        var me = this;

        me.store = Ext.create('Ext.data.Store', {
            model:me.model,
            pageSize:me.pageSize,

//            autoLoad: true,
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
            height:25, //36
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
});


Ext.define('Four.grid.Panel', {
    alias:'widget.smartecgrid',
    extend:'Ext.grid.Panel',
    border:0,
    scroll:true,
    columnLines:true,
    pageSize:1000,
    displayMsg:messages['ext.pagingtoolbar.displayMsg'],
    emptyMsg:messages['ext.pagingtoolbar.emptyMsg'],
    clearFilterMsg:messages['ext.pagingtoolbar.clearFilter'],
    autoAdded:true, //是否添加到id为center的面板中
    topBarAutoShow:true, //是否显示头尾

    initComponent:function () {
        var me = this;

        me.setStore();
        me.setDockItems(me.topDockedItems, me.store);
        me.setFilter(me.filterItems);

        me.callParent();
        if (me.autoAdded) {
            Ext.getCmp('center').add(this);
        }

    },

    onHide:function () {
        console.log('grid was hide...');
    },

    setStore:function () {
        var me = this;

        me.store = Ext.create('Ext.data.Store', {
            model:me.model,
            pageSize:me.pageSize,

//          remoteSort: true,
//          sorters: ['warehouseId'],
            groupField:'warehouseId',
            autoLoad:false
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
            height:25, //36
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
});

Ext.define('Smartec.reportGrid.Panel', {
    extend:'Ext.grid.Panel',
    requires:['Ext.selection.CheckboxModel'],
    border:0,
    scroll:true,
    columnLines:true,
    emptyMsg:'没有记录',
    clearFilterMsg:'清除查询条件',
    autoAdded:true, //是否添加到id为center的面板中
    topBarAutoShow:true, //是否显示头尾
    topBarHight:10, //头显示的高度
    autoLoadStore:true, //是否自动加载列表
    autoSetStore:true,
    initComponent:function () {
        var me = this;

        me.setStore();
        me.setDockItems(me.topDockedItems, me.store);
        me.callParent();
        if (me.autoAdded) {
            Ext.getCmp('center').add(this);
        }

    },
    setStore:function () {
        var me = this;
        if (me.autoSetStore) {
            me.store = Ext.create('Ext.data.Store', {
                model:me.model,
//                pageSize:me.pageSize,
//                groupField:me.groupField,
                autoLoad:this.autoLoadStore,
                remoteSort:true
            });
        }
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


        var topDock = {
            dock:'top',
            height:me.topBarHight, //36
            xtype:'toolbar',
            items:topDockedItems
        };

        //是否显示grid的头和尾
        if (me.topBarAutoShow) {
            me.dockedItems = [topDock];
        }
    }


});