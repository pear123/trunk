(function (scope) {
    var _$UserRoleResource = scope.$UserRoleResource;
    var Initialize = {

        define:function () {
            Ext.define('TreeForm', {
                extend:'Ext.data.Model',
                fields:[
                    {name:'description', type:'string'},
                    {name:'text', type:'string'},
                    {name:'code', type:'string'},
                    {name:'type', type:'string'},
                    {name:'url', type:'string'},
                    {name:'parentId', type:'string'},
                    {name:'id', type:'string'},
                    {name:'url', type:'string'}
                ],
                proxy:{
                    type:'memory',
                    reader:{type:'json'}
                }
            });

            this.typeStore = Ext.create('Ext.data.Store', {
                fields:['val', 'name'],
                data:[
                    {"val":"ajax", "name":"异步资源"},
                    {"val":"sync", "name":"同步资源"}
                ]
            })
        },

        /**
         * Show add panel  and fill the parent field with the specified value.
         * @param parentId
         * @return {Function}
         */
        addMenuByParentId:function (parentId) {
            var treeStore = this.tree_store;
            return function () {
                var displayPanel = Ext.getCmp('display');
                var editPanel = Ext.getCmp('edit');
                var operationRegion = Ext.getCmp('operationRegion');
                editPanel = !editPanel ? Initialize.create_edit() : editPanel;
                if (displayPanel.isVisible()) {
                    operationRegion.remove(displayPanel, false);
                }
                operationRegion.add(editPanel);
                operationRegion.doLayout();
                var form = editPanel.getForm();
                if (form) {
                    form.reset();
                    var pName = '';
                    if (parentId !== 0) {
                        pName = treeStore.getNodeById(parentId).data.text;
                    }
                    form.setValues({
                        parentId_name:pName,
                        parentId:parentId
                    })
                }
            }
        },

        /**
         * create the item context menu.
         * @param node
         * @return {*}
         */
        create_menu:function (node) {
            var me = this;
            var data = node.data;
            var root = data.root;
            var subMenus = [
                {text:'子级', handler:me.addMenuByParentId(data.id)}
            ];
            if (!root) {
                subMenus.push({text:'同级', handler:me.addMenuByParentId(data.parentId)});
            }
            this.context_menu = Ext.create('Ext.menu.Menu', {
                items:[
                    {
                        iconCls:'buy-button',
                        text:'添加',
                        id:'addMenu',
                        menu:{items:subMenus}
                    }
                ]
            });

            this.context_menu.node = node;
            return this.context_menu;
        },

        create_left:function () {
            var me = this;
            if (!this.tree_store) {
                this.tree_store = Ext.create('Ext.data.TreeStore', {
                    fields:[
                        {name:'parentId', type:'string', defaultValue:null},
                        {name:'index', type:'int', defaultValue:null, persist:false},
                        {name:'depth', type:'int', defaultValue:0, persist:false},
                        {name:'expanded', type:'bool', defaultValue:false, persist:false},
                        {name:'expandable', type:'bool', defaultValue:true, persist:false},
//                        {name:'checked', type:'auto', defaultValue:null, persist:false},
                        {name:'leaf', type:'bool', defaultValue:false},
                        {name:'cls', type:'string', defaultValue:null, persist:false},
                        {name:'iconCls', type:'string', defaultValue:null, persist:false},
                        {name:'icon', type:'string', defaultValue:null, persist:false},
                        {name:'root', type:'boolean', defaultValue:false, persist:false},
                        {name:'isLast', type:'boolean', defaultValue:false, persist:false},
                        {name:'isFirst', type:'boolean', defaultValue:false, persist:false},
                        {name:'allowDrop', type:'boolean', defaultValue:true, persist:false},
                        {name:'allowDrag', type:'boolean', defaultValue:true, persist:false},
                        {name:'loaded', type:'boolean', defaultValue:false, persist:false},
                        {name:'loading', type:'boolean', defaultValue:false, persist:false},
                        {name:'href', type:'string', defaultValue:null, persist:false},
                        {name:'hrefTarget', type:'string', defaultValue:null, persist:false},
                        {name:'qtip', type:'string', defaultValue:null, persist:false},
                        {name:'qtitle', type:'string', defaultValue:null, persist:false},
                        {name:'children', type:'auto', defaultValue:null, persist:false},
                        {name:'description', type:'string'},
                        {name:'text', type:'string'},
                        {name:'code', type:'string'},
                        {name:'type', type:'string'},
                        {name:'id', type:'string'},
                        {name:'url', type:'string'}
                    ],
                    proxy:{
                        type:'ajax',
                        url:path('/userRoleResource/tree.json')
                    },
                    root:{text:'资源列表', id:'0', expanded:true}
                });
            }

            if (!this.tree_panel) {
                function loadAction() {
                    return function (store, node, successfully, eOpts) {
                        var data;
                        if (!Initialize.currentNode) {
                            var childNodes = node.childNodes;
                            if (childNodes.length == 0) {
                                return;
                            } else {
                                data = childNodes[0].data;
                            }
                        } else {
                            data = Initialize.currentNode;
                        }

                        me.setEditForm_Values(data);
                    }
                }

                this.tree_panel = Ext.create('Ext.tree.Panel', {
                    store:this.tree_store,
                    rootVisible:false,
                    useArrows:true,
                    frame:true,
                    rootVisible:true,
                    autoWidth:true,
                    autoHeight:true,
                    lines:true,
                    animate:true,
                    listeners:{
                        itemclick:me.showItemAction(),
                        itemcontextmenu:me.showMenusAction(),
                        load:loadAction()
                    }
                });
            }

            return  this.tree_panel;
        },

        showMenusAction:function () {
            return function (thiz, record, item, index, e, eOpts) {
                e.stopEvent();
//                Initialize.create_menu(record).showAt(e.getXY());
                return false;
            }
        },

        setEditForm_Values:function (data) {
            var treeStore = this.tree_store;
            var typeStore = this.typeStore;
            var editPanel = Ext.getCmp('edit');
            var displayPanel = Ext.getCmp('display');
            var operationRegion = Ext.getCmp('operationRegion');
            var displayPanel;
            if (editPanel && editPanel.isVisible()) {
                displayPanel = this.create_display();
                operationRegion.remove(editPanel, false);
                operationRegion.add(displayPanel);
            } else {
                displayPanel = Ext.getCmp('display');
            }
            var displayForm = displayPanel.getForm();
            if (data) {
                var parentId = data.parentId;
                var pName = '';
                if (parentId != 0) {
                    var node = treeStore.getNodeById(parentId).data;
                    pName = node.text;
                }
                data.parentId_name = pName;

                var type = data.type;
                var record = typeStore.findRecord('val', type);
                data.type_name = record.get('name');

                displayForm.setValues(data);
            }

        },

        showItemAction:function () {
            var me = this;
            return function (thiz, record, item, index, e, eOpts) {
                var data = record.data;
                if (data.root) {
                    return;
                }

                me.setEditForm_Values(data);
            }
        },

        /**
         * the panel is used to display simple info.
         * @return {*}
         */
        create_display:function () {
            var me = this;
            if (!this.display_panel) {
                function saveAction(scope) {
                    return function () {
                        var operationPanel = Ext.getCmp('operationRegion');
                        var dispay = Ext.getCmp('display');
                        operationPanel.remove(dispay, false);
                        operationPanel.add(scope.create_edit());
                        operationPanel.doLayout();
                    }
                }

                function editAction(scope) {
                    return function () {
                        var operationPanel = Ext.getCmp('operationRegion');
                        var display = Ext.getCmp('display');
                        var form = display.getForm();
                        var values = form.getFieldValues();
                        operationPanel.remove(display, false);
                        var editPanel = scope.create_edit();
                        operationPanel.add(editPanel);
                        operationPanel.doLayout();
                        if (editPanel) {
                            editPanel.getForm().setValues(values);
                        }
                    }
                }

                var removeAction = function () {
                    var successAction = function (id) {
                        return function (r, o) {
                            var text = r.responseText;
                            var json = Ext.JSON.decode(text);
                            var result = json.result;
                            if (result && result !== 'SUCCESS') {
                                msg = '该资源由角色<br/>' + result + '引用，确定删除？';
                            } else {
                                msg = '确定删除？';
                            }
                            Ext.MessageBox.confirm('警告', msg, function (btn) {
                                if (btn == 'yes') {
                                    confirmDelAction(id)
                                }
                            });

                        }
                    }

                    function confirmDelAction(id) {
                        function _successAtion() {
                            return function (r) {
                                var text = r.responseText;
                                var json = Ext.JSON.decode(text);
                                var result = json.result;
                                if (result === true) {
                                    Initialize.tree_store.load();
                                } else {
                                    Ext.MessageBox.alert('提示', result);
                                }
                            }
                        }

                        Ext.Ajax.request({
                            url:path('/userRoleResource/remove'),
                            params:{id:id},
                            method:'GET',
                            success:_successAtion()
                        });
                    }

                    return function () {
                        var display = Ext.getCmp('display');
                        var form = display.getForm();
                        if (form) {
                            var id = form.findField('id').getValue();
                            Ext.Ajax.request({
                                url:path('/userRoleResource/checkResource'),
                                params:{id:id},
                                method:'GET',
                                success:successAction(id)
                            });
                        }
                    }
                }

                this.display_panel = Ext.create('Ext.form.Panel', {
                    id:'display',
                    cls:'exception-right',
                    frame:true,
                    border:true,
                    fieldDefaults:{
                        anchor:'100%'
                    },
                    autoScroll:true,
                    model:Ext.create('TreeForm'),
//                    dockedItems:[
//                        {xtype:'toolbar',
//                            height:40,
//                            dock:'top'
//                            ,
//                            items:[
//                                {
//                                    text:'新增',
//                                    handler:saveAction(me)
//                                },
//                                {
//                                    text:'编辑',
//                                    handler:editAction(me)
//                                },
//                                {
//                                    text:'删除',
//                                    handler:removeAction(me)
//                                }
//                            ]
//                        }
//                    ],
                    items:[

                        {xtype:'hiddenfield', name:'id'},
                        {xtype:'hiddenfield', name:'parentId'},
                        {xtype:'displayfield', fieldLabel:'所属资源', name:'parentId_name'},
                        {xtype:'hiddenfield', name:'type'},
                        {xtype:'hiddenfield', fieldLabel:'资源类型', name:'type_name'},
                        {xtype:'displayfield', fieldLabel:'资源名称', name:'text'},
                        {xtype:'hiddenfield', fieldLabel:'资源编码', name:'code'},
                        {xtype:'displayfield', fieldLabel:'资源URL', name:'url'},
                        {xtype:'displayfield', fieldLabel:'描述', name:'description'}
                    ]
                });
            }
            return this.display_panel;
        },

        /**
         * the panel is used to edit simple info.
         * @return {*}
         */
        create_edit:function () {
            var me = this;
            if (!this.form_panel) {
                var saveOrUpdateAction = function () {
                    return function () {
                        var form = me.form_panel.getForm();
                        if (!form.isValid()) {
                            Ext.Msg.alert('提示', '请检查您输入的数据');
                            return;
                        }
                        Initialize.currentNode = null;
                        form.submit({
                            clientValidation:true,
                            waitMsgTarget:'正在提交...',
                            waitTitle:'请稍候',
                            url:path('/user/addNewResource'),
                            success:function (form, action) {
                                var result = action.result.result;
                                if (result.success) {
                                    form.reset();
                                    Initialize.currentNode = result.resource;
                                    me.tree_store.load();
                                } else {
                                    Ext.Msg.alert('提示', '提交有误');
                                }
                            },
                            failure:function (form, action) {

                            }
                        });
                    }
                };
                var backAction = function (scope) {
                    return function () {
                        var operationPanel = Ext.getCmp('operationRegion');
                        var editPanel = Ext.getCmp('edit');
                        operationPanel.remove(editPanel, false);
                        operationPanel.add(scope.create_display());
                        operationPanel.doLayout();
                    }
                };

                this.form_panel = Ext.create('Ext.form.Panel', {
                    id:'edit',
                    cls:'exception-right',
                    frame:true,
                    border:true,
                    fieldDefaults:{
                        anchor:'100%'
                    },
                    autoScroll:true,
                    model:Ext.create('TreeForm'),
                    dockedItems:[
                        {xtype:'toolbar',
                            height:40,
                            dock:'top',
                            items:[
                                {
                                    text:'确定',
                                    handler:saveOrUpdateAction()
                                },
                                {
                                    text:'取消',
                                    handler:backAction(me)
                                }
                            ]}
                    ],
                    items:[
                        {xtype:'hiddenfield', name:'id'},
                        {xtype:'hiddenfield', name:'parentId'},
                        {xtype:'textfield', fieldLabel:'所属资源', name:'parentId_name', readOnly:true},
//                        {xtype:'hiddenfield', fieldLabel:'资源类型', name:'type', queryMode:'local', displayField:'name', valueField:'val', store:me.typeStore},
                        {xtype:'hiddenfield', fieldLabel:'资源类型', name:'type',value:'sync'},
                        {xtype:'textfield', fieldLabel:'资源名称', name:'text', allowBlank:false},
                        {xtype:'textfield', fieldLabel:'资源编码', name:'code', allowBlank:false},
                        {xtype:'textfield', fieldLabel:'资源URL', name:'url', allowBlank:false, defaultValue:'#'},
                        {xtype:'textareafield', fieldLabel:'描述', name:'description'}
                    ]
                });
            }

            return this.form_panel;
        },

        create_right:function () {
            return this.create_display();
        },

        create_index:function () {
            var scope = this;
            var centerPanel = Ext.getCmp('center');
            var height = centerPanel.getHeight();

            return Ext.create('Ext.panel.Panel', {
                border:false,
                layout:'column',
                defaults:{
                    layout:'anchor',
                    defaults:{
                        anchor:'100%'
                    }
                },
                items:[
                    {xtype:'panel', columnWidth:1 / 2, layout:'fit', height:height, items:scope.create_left()},
                    {xtype:'panel', id:'operationRegion', columnWidth:1 / 2, layout:'fit', height:height, items:scope.create_right()}
                ]
            });
        }
    }

    function sand_box() {
        Initialize.define();
        var api = {
            createIndex:function () {
                return  Initialize.create_index();
            }
        }
        return api;
    }

    $UserRoleResource = sand_box();
})
    (this);
Ext.onReady(function () {
    var indexView = $UserRoleResource.createIndex();
    Ext.getCmp('center').add(indexView);
});