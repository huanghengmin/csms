Ext.onReady(function(){
    var record = new Ext.data.Record.create([
        {name:'name',mapping:'name'},
        {name:'date',mapping:'date'},
        {name:'version',mapping:'version'},
        {name:'team',	mapping:'team'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../VersionAction.action"
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    },record);
    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy : proxy,
        reader : reader
    });
    store.load();
    var boxM = new Ext.grid.CheckboxSelectionModel();   //复选框
    var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:'应用名称',	    dataIndex:'name',  align:'center',sortable:true,menuDisabled:true},
        {header:'出版时间',		dataIndex:'date',        align:'center',sortable:true,menuDisabled:true},
        {header:'当前版本',		dataIndex:'version',   align:'center',sortable:true,menuDisabled:true},
        {header:'出版团体',		dataIndex:'team',   align:'center',sortable:true,menuDisabled:true}
    ]);
    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:setHeight(),
        width:setWidth(),
        animCollapse:true,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        collapsible:false,
        cm:colM,
        sm:boxM,
        store:store,
        stripeRows:true,
        autoExpandColumn:'Position',
        disableSelection:true,
        bodyStyle:'width:100%',
        enableDragDrop: true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        viewConfig:{
            forceFit:true,
            enableRowBody:true,
            getRowClass:function(record,rowIndex,p,store){
                return 'x-grid3-row-collapsed';
            }
        },
        tbar:[
            {
                xtype: 'tbseparator'
            },
            {
                pressed:false,
                id:'updateVersion',
                text:"上传",
                iconCls: 'add',
                handler:function(){
                    upload();
                    store.reload();
                }
            } ,
            {
                xtype: 'tbseparator'
            }

        ] ,
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
        })
    });
    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[grid_panel]
    });
    function setHeight(){
        var h = document.body.clientHeight-8;
        return h;
    }

    function setWidth(){
        return document.body.clientWidth-8;
    }
    function upload(){
        var internal_formPanel = new Ext.form.FormPanel({
            fileUpload : true,
            plain:true,
            labelWidth:100,
            height:150,
            border:false,
            loadMask : { msg : '正在加载数据，请稍后.....' },
            labelAlign:'right',
            buttonAlign:'left',
            bodyStyle:"padding:50px 0px 0px 5px",
            defaults : {
                width : 280,
                allowBlank : false,
                blankText : '该项不能为空！'
            },
            items:[{
                xtype : 'textfield',
                fieldLabel : '上传软件',
                id:'uploadFile',
                name:'uploadFile',
                allowBlank:false,
                blankText:'文件不能为空',
                width:180,
                inputType:'file',
                fileUpload : true
            }]
        });
        var win = new Ext.Window({
            width:380,
            height:200,
            modal:true,
            title:'android客户端版本上传',
            items:internal_formPanel,
            buttons:[
                {
                    text:'上传',
                    id:'submit',
                    handler: function(){
                        if (internal_formPanel.form.isValid()){
                            internal_formPanel.form.doAction('submit',{
                                url:'../../UploadAction.action',
                                success:function(form,action){
                                    Ext.MessageBox.alert("提示信息","上传成功");
                                    store.reload();
                                    win.close();
                                },
                                failure:function(){
                                    Ext.MessageBox.alert("提示信息","上传失败");
                                    win.close();
                                }
                            });
                        }
                    }
                }
            ]
        }).show();
    }
});
