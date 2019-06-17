(function (scope) {
    var _$UserRole = scope.$UserRole;
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
                    {name:'url', type:'string'},
                    {name:'resourceIds', type:'string'}
                ],
                proxy:{
                    type:'memory',
                    reader:{type:'json'}
                }
            });

            this.resourceFields = [
                {name:'parentId', type:'string', defaultValue:null},
                {name:'index', type:'int', defaultValue:null, persist:false},
                {name:'depth', type:'int', defaultValue:0, persist:false},
                {name:'expanded', type:'bool', defaultValue:false, persist:false},
                {name:'expandable', type:'bool', defaultValue:true, persist:false},
                {name:'checked', type:'auto', defaultValue:null, persist:false},
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
            ];
        },

        /**
         * create the item context menu.
         * @param node
         * @return {*}
         */
        create_menu:function (node) {
            this.context_menu = Ext.create('Ext.menu.Menu', {
                items:[
                    {
                        iconCls:'buy-button',
                        text:'删除',
                        id:'addMenu'
                    }
                ]
            });
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
//                      {name:'checked', type:'auto', defaultValue:null, persist:false},
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
                        {name:'url', type:'string'},
                        {name:'resourceIds', type:'string'}
                    ],
                    proxy:{
                        type:'ajax',
                        url:path('/userRole/tree.json')
                    },
                    root:{text:'角色列表', id:'0', expanded:true}
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
            var operationRegion = Ext.getCmp('operationRegion');
            var editPanel = Ext.getCmp('edit');
            var displayPanel;
            if (editPanel && editPanel.isVisible()) {
                displayPanel = this.create_display();
                operationRegion.removeAll(false);
                operationRegion.add(displayPanel);
                operationRegion.doLayout();
            } else {
                displayPanel = Ext.getCmp('display');
            }
            var displayForm = displayPanel.getForm();
//            console.info(data);
            if (data) {
                displayForm.setValues(data);
            }

            this.setSelectedResourceTree(displayPanel);
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

        setSelectedResourceTree:function (panel, resetTree) {
            var form = panel.getForm();
            /*var edit = Ext.getCmp('edit');
             var display = Ext.getCmp('display');
             if (edit && edit.isVisible()) {
             form = edit.getForm();
             } else if (display && display.isVisible()) {
             form = display.getForm();
             }*/

            if (form.findField('resourceIds')) {
                var resourceIds = form.findField('resourceIds').getValue();
                if (resourceIds) {
                    this.ids = resourceIds.split(',');
                }else{
                    this.ids = "";
                }
            }

            var self = this;
            this.resource_tree.getRootNode().cascadeBy(function () {
                var me = this;
                var id = me.data.id;
                me.set('checked', false);
                if (self.ids && self.ids.length > 0 && !resetTree) {
                    Ext.each(self.ids, function (v, i) {
                        if (id === v) {
                            me.set('checked', true);
                        }
                    });
                }
            });
        },

        loadAction:function () {
            var me = this;
            return function () {
                me.setSelectedResourceTree(Ext.getCmp('display'));
            }
        },

        create_resource_tree:function () {
            var me = this;
            if (!this.resource_tree) {
                this.resource_tree_store = Ext.create('Ext.data.TreeStore', {
                    fields:me.resourceFields,
                    proxy:{
                        type:'ajax',
                        url:path('/userRoleResource/tree.json')
                    },
                    autoLoad:false,
                    root:{text:'角色权限', id:'0', expanded:true}
                });

                this.resource_tree = Ext.create('Ext.tree.Panel', {
                    id:'edit_tree',
                    store:this.resource_tree_store,
                    border:false,
                    rootVisible:false,
                    useArrows:true,
                    frame:true,
                    rootVisible:true,
                    autoWidth:true,
                    autoHeight:true,
                    lines:true,
                    animate:true,
                    listeners:{
                        checkchange:me.selectAction(),
                        load:me.loadAction()
                    }
                });
            }

            return this.resource_tree;
        },

        selectAction:function () {
            return function (node, checked) {
                node.cascadeBy(function () {
                    this.set('checked', checked);
                });
                node.bubble(function () {
                    var isChecked = false;
                    if(node.raw.parentId != '0' && this.raw.parentId == '0'){
                        var selectChildNodes = node.parentNode.childNodes;
                        Ext.each(selectChildNodes,function(itemNode){
                            if(itemNode.data.checked){
                                isChecked = true;
                                return false;
                            }
                        });
                        this.set('checked', isChecked || checked);
                        return false;
                    }
                });
            }
        },

        resetResourceTree:function () {
            var treeRegion = Ext.getCmp('treeRegion');
            if (treeRegion) {
                if (treeRegion.items.length > 0) {
                    treeRegion.removeAll(false);
                }
                treeRegion.add(this.create_resource_tree());
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
                        operationPanel.removeAll(false);
                        var edit = scope.create_edit();
                        operationPanel.add(edit);
                        operationPanel.doLayout();
                        scope.setSelectedResourceTree(edit, true);
                    }
                }

                function editAction(scope) {
                    return function () {
                        var operationPanel = Ext.getCmp('operationRegion');
                        var display = Ext.getCmp('display');
                        var values = display.getForm().getFieldValues();

                        operationPanel.removeAll(false);
                        var edit = scope.create_edit(values);
                        operationPanel.add(edit);
                        operationPanel.doLayout();
                        scope.setSelectedResourceTree(edit);
                    }
                }

                function removeAction(scope) {
                    var successAction = function (id) {
                        return function (r, o) {
                            var text = r.responseText;
                            var json = Ext.JSON.decode(text);
                            var result = json.result;
                            if (result && result !== 'SUCCESS') {
                                msg = '该角色由用户' + result + '引用，如需删除该角色，请先删除该用户!';
                                Ext.MessageBox.alert('提示', msg);
                            } else {
                                Ext.MessageBox.confirm('警告', '确定删除?', function (btn) {
                                    if (btn == 'yes') {
                                        confirmDelAction(id)
                                    }
                                });
                            }
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
                            url:path('/userRole/remove'),
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
                                url:path('/userRole/checkResource'),
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
                    dockedItems:[
                        {xtype:'toolbar',
                            height:40,
                            dock:'top',
                            items:[
                                {
                                    text:'新增',
                                    handler:saveAction(me)
                                },
                                {
                                    text:'编辑',
                                    handler:editAction(me)
                                },
                                {
                                    text:'删除',
                                    handler:removeAction(me)
                                }
                            ]}
                    ],
                    items:[
                        {xtype:'hiddenfield', name:'id'},
                        {xtype:'hiddenfield', name:'resourceIds'},
                        {xtype:'hiddenfield', fieldLabel:'角色编码', name:'code', allowBlank:false},
                        {xtype:'displayfield', fieldLabel:'角色名称', name:'text', allowBlank:false},
                        {xtype:'displayfield', fieldLabel:'角色描述', name:'description'},
                        {xtype:'fieldset', id:'treeRegion', layout:'fit', height:'100%', border:false/*, items:me.create_resource_tree()*/}
                    ]
                });
            }

            this.resetResourceTree();
            return this.display_panel;
        },


        /**
         * the panel is used to edit simple info.
         * @return {*}
         */
        create_edit:function (defaultValues) {
            var me = this;
            if (!this.form_panel) {
                var saveOrUpdateAction = function () {
                    return function () {
                        var form = me.form_panel.getForm();
                        var checkedNodes = me.resource_tree.getChecked();
                        var resources = '';
                        if (checkedNodes.length > 0) {
                            var node;
                            for (var i = 0, size = checkedNodes.length; i < size; i++) {
                                node = checkedNodes[i];
                                if (!node || !node.data) {
                                    continue;
                                }

                                resources += node.data.id;
                                if (i != size - 1) {
                                    resources += ',';
                                }
                            }
                        }
                        form.setValues({
                            resourceIds:resources
                        });
                        if (!form.isValid()) {
                            Ext.Msg.alert('提示', '请检查您输入的数据');
                            return;
                        }
                        Initialize.currentNode = null;
                        form.submit({
                            clientValidation:true,
                            waitMsgTarget:'正在提交...',
                            waitTitle:'请稍候',
                            url:path('/user/mergeRole'),
                            success:function (form, action) {
                                var result = action.result.result;
                                if(result.error == "RoleNameError"){
                                    Ext.Msg.alert(messages['ext.tips.error'], '角色名称已存在！');
                                    return;
                                }
                                else if (result.success) {
                                    form.reset();
                                    Initialize.currentNode = result.role;
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
                        operationPanel.removeAll(false);
                        var display = scope.create_display();
                        operationPanel.add(display);
                        operationPanel.doLayout();
                        me.setSelectedResourceTree(display);
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
                        {xtype:'hiddenfield', name:'resourceIds'},
                        {xtype:'hiddenfield', fieldLabel:'角色编码', name:'code', allowBlank:false},
                        {xtype:'textfield', fieldLabel:'角色名称', name:'text', allowBlank:false},
                        {xtype:'textfield', fieldLabel:'角色描述', name:'description'},
                        {xtype:'fieldset', id:'treeRegion', layout:'fit', height:'100%'/*, items:me.create_resource_tree()*/}
                    ]
                });
            } else {
                this.form_panel.getForm().reset();
            }

            this.resetResourceTree();

            var form = this.form_panel.getForm();
            if (defaultValues) {
                form.setValues(defaultValues);
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
                    {xtype:'panel', columnWidth:1 / 3, layout:'fit', height:height, items:scope.create_left()},
                    {xtype:'panel', id:'operationRegion', columnWidth:2 / 3, layout:'fit', height:height, items:scope.create_right()}
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

    $UserRole = sand_box();
})(this);
Ext.onReady(function () {
    var indexView = $UserRole.createIndex();
    Ext.getCmp('center').add(indexView);
});