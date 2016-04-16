Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'processName', mapping:'processName'} ,
        {name:'processId', mapping:'processId'}/*,
         {name:'accessStatus', mapping:'accessStatus'}*/
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../StopListAction_findProcess.action",
        timeout:10 * 1000
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load({
        params:{
            start:start, limit:pageSize
        }
    });

  var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:true});   //复选框单选
//    var boxM = new Ext.grid.RadioboxSelectionModel();

    var rowNumber = new Ext.grid.RowNumberer();         //自动编号

    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"进程名称", dataIndex:"processName", align:'center', sortable:true, menuDisabled:true, sort:true} ,
        {header:"进程标识", dataIndex:"processId", align:'center', sortable:true, menuDisabled:true},
//        {header:"授权状态", dataIndex:"accessStatus", align:'center',sortable:true, menuDisabled:true,renderer:show_accessStatus},
//        {header:"运行状态", dataIndex:"processStatus", align:'center',sortable:true, menuDisabled:true,renderer:show_processStatus},
        {header:'操作标记', dataIndex:'flag', align:'center', sortable:true, menuDisabled:true, renderer:show_flag, width:300}
    ]);

    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });

    function setHeight() {
        var h = document.body.clientHeight - 8;
        return h;
    }

    ;

//    var tb = new Ext.Toolbar({
//        autoWidth :true,
//        autoHeight:true,
//        items:[
//            {
//                id:'addBlackAddress.info',
//                xtype:'button',
//                text:'新增黑名单地址',
//                iconCls:'add',
//                handler:function () {
//                    addBlackAddress(grid_panel, store);
//                }
//            }]
//    });
//        var runData = [
//        [0,'运行'],
//        [1,'未启动']
//    ];
//
//    var processData = [
//        [0,'允许'],
//        [1,'禁止']
//    ];

    var tbar = new Ext.Toolbar({
        autoWidth:true,
        autoHeight:true,
        items:[
            '进程名称',
            new Ext.form.TextField({
                name:'processName',
                id:'hzih.tbar.processName'
            }),
            '进程标识',
            new Ext.form.TextField({
                name:'processId',
                id:'hzih.tbar.processId'
            }),
//            '进程运行状态',
//            new Ext.form.ComboBox({
//                emptyText:"选择进程运行状态",
//                forceSelection: true,
//                mode: 'local',
//                hiddenName:"processStatus",
//                triggerAction: 'all',
//                store: runData,
//                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
//                displayField: 'name',//下拉框显示内容
//                name : 'processStatus',
//                id:'hzih.tbar.processStatus'
//            }),
//            '进程授权访问状态',
//            new Ext.form.ComboBox({
//                emptyText:"选择进程授权访问状态",
//                forceSelection: true,
//                mode: 'local',
//                hiddenName:"processStatus",
//                triggerAction: 'all',
//                store: processData,
//                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
//                displayField: 'name',//下拉框显示内容
//                name : 'accessStatus',
//                id:'hzih.tbar.accessStatus'
//
//            }),
            {
                id:'tbar.tbar.processName.info',
                xtype:'button',
                iconCls:'select',
                text:'查询',
                handler:function () {
                    var processName = Ext.getCmp('hzih.tbar.processName').getValue();
                    var processId = Ext.getCmp('hzih.tbar.processId').getValue();
//                  var accessStatus = Ext.getCmp('hzih.tbar.accessStatus').getValue();
                    store.setBaseParam('processName', processName);
                    store.setBaseParam('processId', processId);
//                  store.setBaseParam('accessStatus', accessStatus);
                    store.load({
                        params:{
                            start:start,
                            limit:pageSize
                        }
                    });
//                    Ext.getCmp('hzih.tbar.processName').setValue('');
//                    Ext.getCmp('hzih.tbar.processId').setValue('');
//                    Ext.getCmp('hzih.tbar.accessStatus').setValue('');
                }
            }]
    });

    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:setHeight(),
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        bodyStyle:'width:100%',
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:true,
        cm:colM,
        sm:boxM,
        store:store,
        tbar:tbar,
//        listeners:{
//            render:function(){
//                tbar.render(this.tbar);
//            }
//        },
        bbar:page_toolbar
    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[grid_panel]
    });
});

function show_flag(value, p, r) {
//    if(r.get("processStatus")=="0"){
    return String.format(
        '<a id="deleteProcess.info" href="javascript:void(0);" onclick="deleteProcess();return false;" style="color: green;">取消控制</a>&nbsp;&nbsp;&nbsp;'
//            + '<a id="stopProcess.info" href="javascript:void(0);" onclick="stopProcess();return false;" style="color: green;">结束进程</a>&nbsp;&nbsp;&nbsp;'
    );
//    }else if(r.get("processStatus")=="1"){
//        return String.format(
//            '<a id="deleteProcess.info" href="javascript:void(0);" onclick="deleteProcess();return false;" style="color: green;">取消控制</a>&nbsp;&nbsp;&nbsp;'
//        );
//    }
}
;

//function show_accessStatus(value, p, r){
//    if(r.get("accessStatus")=="0"){
//        return String.format('<font color="#4169e1" >允许访问</font> ');
//    }else if(r.get("accessStatus")=="1"){
//        return String.format('<font color="red" >禁止访问</font>');
//    }
//};
//
//function show_processStatus(value, p, r){
//    if(r.get("processStatus")=="0"){
//        return String.format('<img src="../../img/icon/ok.png" alt="远行中" title="远行中" />');
//    }else if(r.get("processStatus")=="1"){
//        return String.format('<img src="../../img/icon/off.gif" alt="未启动" title="非运行中" />');
//    }
//};

function deleteProcess() {
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if (!recode) {
        Ext.Msg.alert("提示", "请选择一条记录!");
    } else {
        Ext.Msg.confirm("提示", "确认从进程黑名单删除吗？", function (sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url:"../../StopListAction_delete.action",
                    timeout:20 * 60 * 1000,
                    method:"POST",
                    params:{processId:recode.get("processId")},
                    success:function (form, action) {
                        Ext.Msg.alert("提示", "删除成功!");
                        grid_panel.getStore().reload();
                    },
                    failure:function (result) {
                        Ext.Msg.alert("提示", "删除失败!");
                    }
                });
            }
        });
    }
}
;

//function stopProcess(){
//    var grid_panel = Ext.getCmp("grid.info");
//    var recode = grid_panel.getSelectionModel().getSelected();
//    if(!recode){
//        Ext.Msg.alert("提示", "请选择一条记录!");
//    }else{
//        Ext.Msg.confirm("提示", "确认终止进程访问吗？", function(sid) {
//            if (sid == "yes") {
//                Ext.Ajax.request({
//                    url : "../../TerminalAppAction_stop.action",
//                    timeout: 20*60*1000,
//                    method : "POST",
//                    params:{processName:recode.get("processName")},
//                    success : function(form, action) {
//                        Ext.Msg.alert("提示", "终止成功!");
//                        grid_panel.getStore().reload();
//                    },
//                    failure : function(result) {
//                        Ext.Msg.alert("提示", "终止失败!");
//                    }
//                });
//            }
//        });
//    }
//};

//function updateBlackAddress() {
//    var allData = [
//        [1,'服务下所有地址皆不允许访问'],
//        [0,'仅当前地址不允许访问']
//    ];
//    var allDataStore = new Ext.data.SimpleStore({
//        fields:['id','name'],
//        data:allData
//    });
//    var grid_panel = Ext.getCmp("grid.info");
//    var recode = grid_panel.getSelectionModel().getSelected();
//    var formPanel = new Ext.form.FormPanel({
//        frame:true,
//        autoScroll:true,
//        labelWidth:150,
//        labelAlign:'right',
//        defaultWidth:300,
//        autoWidth:true,
//        layout:'form',
//        border:false,
//        defaults : {
//            width : 250,
//            allowBlank : false,
//            blankText : '该项不能为空！'
//        },
//        items:[
//            new Ext.form.TextField({
//                fieldLabel : '授权访问地址',
//                name : 'blackList.url',
//                value: recode.get('url'),
//                id:  'accessAddress.update.adAccessAddress.url',
//                allowBlank : false,
//                emptyText:"请输授权访问的URL地址",
//                blankText : "不能为空，请正确填写" ,
//                listeners:{
//                    blur:function(){
//                        var url = this.getValue();
//                        Ext.Ajax.request({
//                            url : '../../BlackListAction_checkUrl.action',
//                            timeout: 20*60*1000,
//                            method : 'post',
//                            params : {
//                                url : url
//                            },
//                            success : function(r,o) {
//                                var respText = Ext.util.JSON.decode(r.responseText);
//                                var msg = respText.msg;
//                                if (msg == 'false') {
//                                    Ext.MessageBox.show({
//                                        title:'信息',
//                                        width:250,
//                                        msg:'目标地址已存在,请更换！',
//                                        buttons:Ext.MessageBox.OK,
//                                        buttons:{'ok':'确定'},
//                                        icon:Ext.MessageBox.INFO,
//                                        closable:false,
//                                        fn:function(e){
//                                            if(e=='ok'){
//                                                Ext.getCmp("accessAddress.update.adAccessAddress.url").setValue('');
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        });
//                    }
//                }
//            }),
//            new Ext.form.ComboBox({
//                typeAhead: true,
//                emptyText:"选择授权范围:",
//                triggerAction: 'all',
//                forceSelection: true,
//                mode: 'local',
//                value:recode.get("status"),
//                hiddenName:"blackList.status",
//                id:'hzih.update.blackList.status',
//                store: allDataStore,
//                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
//                displayField: 'name'//下拉框显示内容
//            })
//        ]
//    });
//    var win = new Ext.Window({
//        title:"更新授权地址",
//        width:500,
//        layout:'fit',
//        height:340,
//        modal:true,
//        items:formPanel,
//        bbar:[
//            '->',
//            {
//                id:'update_win.info',
//                text:'更新',
//                width:50,
//                handler:function(){
//                    if (formPanel.form.isValid()) {
//                        formPanel.getForm().submit({
//                            url :'../../BlackListAction_modify.action',
//                            timeout: 20*60*1000,
//                            method :'POST',
//                            waitTitle :'系统提示',
//                            params:{
//                                oldUrl:recode.get("url")
//                            },
//                            waitMsg :'正在连接...',
//                            success : function() {
//                                Ext.MessageBox.show({
//                                    title:'信息',
//                                    width:250,
//                                    msg:'更新成功,点击返回页面!',
//                                    buttons:Ext.MessageBox.OK,
//                                    buttons:{'ok':'确定'},
//                                    icon:Ext.MessageBox.INFO,
//                                    closable:false,
//                                    fn:function(e){
//                                        grid_panel.getStore().reload();
//                                        win.close();
//                                    }
//                                });
//                            },
//                            failure : function() {
//                                Ext.MessageBox.show({
//                                    title:'信息',
//                                    width:250,
//                                    msg:'更新失败，请与管理员联系!',
//                                    buttons:Ext.MessageBox.OK,
//                                    buttons:{'ok':'确定'},
//                                    icon:Ext.MessageBox.ERROR,
//                                    closable:false
//                                });
//                            }
//                        });
//                    } else {
//                        Ext.MessageBox.show({
//                            title:'信息',
//                            width:200,
//                            msg:'请填写完成再提交!',
//                            buttons:Ext.MessageBox.OK,
//                            buttons:{'ok':'确定'},
//                            icon:Ext.MessageBox.ERROR,
//                            closable:false
//                        });
//                    }
//                }
//            },{
//                text:'重置',
//                width:50,
//                handler:function(){
//                    formPanel.getForm().reset();
//                }
//            }
//        ]
//    }).show();
//};
//
//function addBlackAddress(grid_panel, store){
//    var allData = [
//        [1,'服务下所有地址皆不允许访问'],
//        [0,'仅当前地址不允许访问']
//    ];
//    var allDataStore = new Ext.data.SimpleStore({
//        fields:['id','name'],
//        data:allData
//    });
//    var formPanel = new Ext.form.FormPanel({
//        frame:true,
//        autoScroll:true,
//        labelWidth:150,
//        labelAlign:'right',
//        defaultWidth:300,
//        autoWidth:true,
//        layout:'form',
//        border:false,
//        defaults : {
//            width : 250,
//            allowBlank : false,
//            blankText : '该项不能为空！'
//        },
//        items:[
//            new Ext.form.TextField({
//                fieldLabel : '黑名单URL地址',
//                name : 'blackList.url',
//                id:  'accessAddress.add.adAccessAddress.url',
//                allowBlank : false,
//                emptyText:"请输入要添加的黑名单URL地址",
//                blankText : "不能为空，请正确填写",
//                listeners:{
//                    blur:function(){
//                        var url = this.getValue();
//                        Ext.Ajax.request({
//                            url : '../../BlackListAction_checkUrl.action',
//                            timeout: 20*60*1000,
//                            method : 'post',
//                            params : {
//                                url : url
//                            },
//                            success : function(r,o) {
//                                var respText = Ext.util.JSON.decode(r.responseText);
//                                var msg = respText.msg;
//                                if (msg == 'false') {
//                                    Ext.MessageBox.show({
//                                        title:'信息',
//                                        width:250,
//                                        msg:'目标地址已存在,请更换！',
//                                        buttons:Ext.MessageBox.OK,
//                                        buttons:{'ok':'确定'},
//                                        icon:Ext.MessageBox.INFO,
//                                        closable:false,
//                                        fn:function(e){
//                                            if(e=='ok'){
//                                                Ext.getCmp("accessAddress.add.adAccessAddress.url").setValue('');
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        });
//                    }
//                }
//            }),
//            new Ext.form.ComboBox({
//                typeAhead: true,
//                emptyText:"选择授权范围",
//                triggerAction: 'all',
//                forceSelection: true,
//                mode: 'local',
//                hiddenName:"blackList.status",
//                id:'hzih.update.blackList.status',
//                store: allDataStore,
//                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
//                displayField: 'name'//下拉框显示内容
//            })
//        ]
//    });
//    var win = new Ext.Window({
//        title:"新增黑名单地址",
//        width:500,
//        layout:'fit',
//        height:340,
//        modal:true,
//        items:formPanel,
//        bbar:[
//            '->',
//            {
//                id:'add_win.info',
//                text:'新增',
//                width:50,
//                handler:function(){
//                    if (formPanel.form.isValid()) {
//                        formPanel.getForm().submit({
//                            url :'../../BlackListAction_add.action',
//                            timeout: 20*60*1000,
//                            method :'POST',
//                            waitTitle :'系统提示',
//                            waitMsg :'正在连接...',
//                            success : function() {
//                                Ext.MessageBox.show({
//                                    title:'信息',
//                                    width:250,
//                                    msg:'新增成功,点击返回页面!',
//                                    buttons:Ext.MessageBox.OK,
//                                    buttons:{'ok':'确定'},
//                                    icon:Ext.MessageBox.INFO,
//                                    closable:false,
//                                    fn:function(e){
//                                        store.reload();
//                                        win.close();
//                                    }
//                                });
//                            },
//                            failure : function() {
//                                Ext.MessageBox.show({
//                                    title:'信息',
//                                    width:250,
//                                    msg:'保存失败，请与管理员联系!',
//                                    buttons:Ext.MessageBox.OK,
//                                    buttons:{'ok':'确定'},
//                                    icon:Ext.MessageBox.ERROR,
//                                    closable:false
//                                });
//                            }
//                        });
//                    } else {
//                        Ext.MessageBox.show({
//                            title:'信息',
//                            width:200,
//                            msg:'请填写完成再提交!',
//                            buttons:Ext.MessageBox.OK,
//                            buttons:{'ok':'确定'},
//                            icon:Ext.MessageBox.ERROR,
//                            closable:false
//                        });
//                    }
//                }
//            },{
//                text:'重置',
//                width:50,
//                handler:function(){
//                    formPanel.getForm().reset();
//                }
//            }
//        ]
//    }).show();
//};







