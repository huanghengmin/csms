Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'id', mapping:'id'} ,
        {name:'cn', mapping:'cn'} ,
        {name:'hzihpassword', mapping:'hzihpassword'} ,
        {name:'hzihid', mapping:'hzihid'} ,
        {name:'hzihphone', mapping:'hzihphone'} ,
        {name:'hzihaddress', mapping:'hzihaddress'},
        {name:'hzihemail', mapping:'hzihemail'},
        {name:'hzihjobnumber', mapping:'hzihjobnumber'},
        {name:'phonenetid', mapping:'phonenetid'},
        {name:'hzihcaserialNumber', mapping:'hzihcaserialNumber'},
        {name:'terminalid', mapping:'terminalid'} ,
        {name:'terminal_pwd', mapping:'terminal_pwd'},
        {name:'terminal_pwd_audit', mapping:'terminal_pwd_audit'},
        {name:'hzihdn', mapping:'hzihdn'},
        {name:'hzihprovince', mapping:'hzihprovince'},
        {name:'hzihcity', mapping:'hzihcity'},
        {name:'hzihorganization', mapping:'hzihorganization'},
        {name:'hzihinstitutions', mapping:'hzihinstitutions'},
        {name:'hzihcastatus', mapping:'hzihcastatus'},
        {name:'hzihparentca', mapping:'hzihparentca'},
        {name:'hzihcavalidity', mapping:'hzihcavalidity'},
        {name:'status', mapping:'status'},
        {name:'hzihcertificatetype', mapping:'hzihcertificatetype'}

    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../CaUserAction_findByPages.action" ,
        timeout: 10*1000
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
        {header:"用户名", dataIndex:"cn",align:'center', sortable:true, menuDisabled:true,sort:true,renderer:show_username} ,
        {header:"身份证", dataIndex:"hzihid",align:'center', sortable:true, menuDisabled:true} ,
        {header:"联系电话", dataIndex:"hzihphone",align:'center', sortable:true, menuDisabled:true} ,
        {header:"联系地址", dataIndex:"hzihaddress",align:'center', sortable:true, menuDisabled:true} ,
        {header:"电子邮件", dataIndex:"hzihemail",align:'center', sortable:true, menuDisabled:true} ,
        {header:'操作标记', dataIndex:'flag', align:'center',sortable:true, menuDisabled:true, renderer:show_flag, width:300}
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
    } ;

    var tb = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            {
                id:'add_resource.info',
                xtype:'button',
                text:'新增用户',
                iconCls:'add',
                handler:function () {
                    addRaUser(grid_panel, store);
                }
            }, {
                id:'importCARaUser.info',
                xtype:'button',
                text:'导入CA用户',
                iconCls:'replace',
                handler:function () {
                    importCaUser(grid_panel, store);
                }
            }]
    });

    var typeData = [
        [0,'正常'],[1,'暂停授权'],[2,'停止授权']
    ];
    var typeDataStore = new Ext.data.SimpleStore({
        fields:['id','name'],
        data:typeData
    });

    var tbar = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            '用户名', new Ext.form.TextField({
            name : 'username',
            id:'hzih.tbar.raUser.username'
            }),
            '身份证号码',
            new Ext.form.TextField({
                id:'hzih.tbar.raUser.userid',
                regex:/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([0-9xX])$/,
                regexText:'请输入有效的身份证号',
                name : 'userid'
            }),
            '联系电话',
             new Ext.form.TextField({
             id:'hzih.tbar.raUser.phone',
             name : 'phone'
             }),
            '电子邮件',
             new Ext.form.TextField({
             id:'hzih.tbar.raUser.email' ,
             name : 'email'
             }),
            '授权状态',
            new Ext.form.ComboBox({
                typeAhead: true,
                triggerAction: 'all',
                forceSelection: true,
                mode: 'local',
                hiddenName:"status",
                id:'hzih.tbar.raUser.status',
                store: typeDataStore,
                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
                displayField: 'name'//下拉框显示内容
            }) ,
            {
                id:'tbar.tbar.raUser.info',
                xtype:'button',
                iconCls:'select',
                text:'查询',
                handler:function () {
                    var username = Ext.getCmp('hzih.tbar.raUser.username').getValue();
                    var userid = Ext.getCmp('hzih.tbar.raUser.userid').getValue();
                    var phone = Ext.getCmp('hzih.tbar.raUser.phone').getValue();
                    var email = Ext.getCmp('hzih.tbar.raUser.email').getValue();
                    var status = Ext.getCmp('hzih.tbar.raUser.status').getValue();
                    store.setBaseParam('username', username);
                    store.setBaseParam('userid', userid);
                    store.setBaseParam('phone', phone);
                    store.setBaseParam('email', email);
                    store.setBaseParam('status', status);
                    store.load({
                        params : {
                            start : start,
                            limit : pageSize
                        }
                    });
//                    Ext.getCmp('hzih.tbar.raUser.username').reset();
//                    Ext.getCmp('hzih.tbar.raUser.userid').reset();
//                    Ext.getCmp('hzih.tbar.raUser.phone').reset();
//                    Ext.getCmp('hzih.tbar.raUser.email').reset();
//                    Ext.getCmp('hzih.tbar.raUser.status').reset();
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
        tbar : tb,
        listeners:{
            render:function(){
                tbar.render(this.tbar);
            }
        },
        bbar:page_toolbar
    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[grid_panel]
    });
});

function show_flag(value, p, r){
    if(parseInt(r.get("hzihcastatus"))==2){
        return String.format(
            '<a id="certificateIssued.info" href="javascript:void(0);" onclick="certificateIssued();return false;"style="color: green;">发布证书</a>&nbsp;&nbsp;&nbsp;' +
            '<a id="viewRaUser.info" href="javascript:void(0);" onclick="viewRaUser();return false;" style="color: green;">查看详细</a> &nbsp;&nbsp;&nbsp;'
        );
    }else if(parseInt(r.get("hzihcastatus"))==3&&r.get("status")=="0")  {
        return String.format(
           '<a id="revokeCa.info" href="javascript:void(0);" onclick="revokeCa();return false;"style="color: green;">吊销</a>&nbsp;&nbsp;&nbsp;'  +
           '<a id="deleteRaUser.info" href="javascript:void(0);" onclick="deleteRaUser();return false;" style="color: green;">删除</a>&nbsp;&nbsp;&nbsp;'  +
           '<a id="updateRaUser.info" href="javascript:void(0);" onclick="updateRaUser();return false;" style="color: green;">更新</a>&nbsp;&nbsp;&nbsp;'  +
           '<a id="sleepRaUser.info" href="javascript:void(0);" onclick="sleepRaUser();return false;" style="color: green;">暂停授权</a>&nbsp;&nbsp;&nbsp;'  +
           '<a id="stopRaUser.info" href="javascript:void(0);" onclick="stopRaUser();return false;" style="color: green;">停止授权</a>&nbsp;&nbsp;&nbsp;'  +
           '<a id="viewRaUser.info" href="javascript:void(0);" onclick="viewRaUser();return false;" style="color: green;">查看详细</a> &nbsp;&nbsp;&nbsp;'
        );
    }  else if(parseInt(r.get("hzihcastatus"))==3&&r.get("status")=="1"){
        return String.format(
            '<a id="revokeCa.info" href="javascript:void(0);" onclick="revokeCa();return false;"style="color: green;">吊销</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="deleteRaUser.info" href="javascript:void(0);" onclick="deleteRaUser();return false;" style="color: green;">删除</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="updateRaUser.info" href="javascript:void(0);" onclick="updateRaUser();return false;" style="color: green;">更新</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="reCastRaUser.info" href="javascript:void(0);" onclick="reCastRaUser();return false;" style="color: green;">重新授权</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="stopRaUser.info" href="javascript:void(0);" onclick="stopRaUser();return false;" style="color: green;">停止授权</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="viewRaUser.info" href="javascript:void(0);" onclick="viewRaUser();return false;" style="color: green;">查看详细</a> &nbsp;&nbsp;&nbsp;'
        );
    } else if(parseInt(r.get("hzihcastatus"))==3&&r.get("status")=="2"){
        return String.format(
            '<a id="revokeCa.info" href="javascript:void(0);" onclick="revokeCa();return false;"style="color: green;">吊销</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="deleteRaUser.info" href="javascript:void(0);" onclick="deleteRaUser();return false;" style="color: green;">删除</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="updateRaUser.info" href="javascript:void(0);" onclick="updateRaUser();return false;" style="color: green;">更新</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="reCastRaUser.info" href="javascript:void(0);" onclick="reCastRaUser();return false;" style="color: green;">重新授权</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="viewRaUser.info" href="javascript:void(0);" onclick="viewRaUser();return false;" style="color: green;">查看详细</a> &nbsp;&nbsp;&nbsp;'
        );
    } else{
        return String.format(
            '<a id="viewRaUser.info" href="javascript:void(0);" onclick="viewDetailed();return false;" style="color: green;">查看详细</a> &nbsp;&nbsp;&nbsp;'
        );
    }
};

function show_username(value,p,r){
    if(value.contains("_")){
        return value.substring(0,value.indexOf("_"));
    }else{
        return value;
    }
}

var viewDetailed = function(){
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    var status =recode.get("hzihcastatus")
    var statusValue;
    if(status=="0"){
        statusValue= "新增"
    } else if(status=="1"){
        statusValue= "待批准"
    } else if(status=="2"){
        statusValue= "已批准"
    } else if(status=="3"){
        statusValue= "已发证"
    }  else if(status=="4"){
        statusValue= "已吊销"
    } else if(status=="5"){
        statusValue= "已废除"
    }
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        width:800,
        autoScroll:true,
        baseCls : 'x-plain',
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        layout:'form',
        border:false,
        defaults:{
            width:250
        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel:'用户名称',
                value:recode.get("cn")
            }),
            /*  new Ext.form.DisplayField({
             fieldLabel:'DN',
             value:recode.get("hzihdatadn")
             }),*/
            new Ext.form.DisplayField({
                fieldLabel:'身份证',
                value:recode.get("hzihid")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'联系电话',
                value:recode.get("hzihphone")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'联系地址',
                value:recode.get("hzihaddress")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'电子邮件',
                value:recode.get("hzihemail")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'工号',
                value:recode.get("hzihjobnumber")
            }),
//            new Ext.form.DisplayField({
//                fieldLabel:'省',
//                value:recode.get("hzihprovince")
//            }),
//            new Ext.form.DisplayField({
//                fieldLabel:'市',
//                value:recode.get("hzihcity")
//            }),
            new Ext.form.DisplayField({
                fieldLabel:'组织',
                value:recode.get("hzihorganization")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'机构',
                value:recode.get("hzihinstitutions")
            })  ,
            new Ext.form.DisplayField({
                fieldLabel:'状态',
                value:statusValue
            })/*,*/
            /*    new Ext.form.DisplayField({
             fieldLabel:'父CA',
             value:recode.get("hzihparentcadn")
             }),*/
//            new Ext.form.DisplayField({
//                fieldLabel:'截止日期',
//                value:recode.get("hzihcavalidity")
//            })  ,
//            new Ext.form.DisplayField({
//                fieldLabel:'创建日期',
//                value:recode.get("hzihcacreatedate")
//            })   ,
//            new Ext.form.DisplayField({
//                fieldLabel:'结束日期',
//                value:recode.get("hzihcaenddate")
//            })
        ]
    });

    var select_Win = new Ext.Window({
        title:"用户证书详细",
        width:800,
        layout:'fit',
        height:380,
        modal:true,
        items:formPanel
    });
    select_Win.show();
};         //查看详细

var revokeCa = function(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var CN =recode.get("cn");
    var DN  = recode.get("hzihdn");
    Ext.Msg.confirm("警告", "确认吊销证书,吊销后证书不可用!", function (sid) {
        if (sid == "yes") {
            Ext.Ajax.request({
                url: '../../CaUserAction_revokeCa.action',
                timeout: 20*60*1000,
                params:{DN:DN,CN:CN},
                method: 'POST',
                success : function(form, action) {
                    Ext.Msg.alert("提示", "吊销证书成功!");
                    grid.getStore().reload();
                },
                failure : function(result) {
                    Ext.Msg.alert("提示", "吊销证书失败!");
                    grid.getStore().reload();
                }
            });
        }
    });
};             //吊销证书

var certificateIssued = function(){
    var typeData = [
        ['1024','1024 bits'],['2048','2048 bits'],['4096','4096 bits']
    ];
    var typeDataStore = new Ext.data.SimpleStore({
        fields:['id','name'],
        data:typeData
    });
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var CN =recode.get("cn");
    var DN =   recode.get("hzihdn");
    var validate = recode.get("hzihcavalidity");
    alert(validate);
    var hzihprovince =recode.get("hzihprovince");
    var hzihcity =recode.get("hzihcity");
    var hzihid =recode.get("hzihid");
    var password = recode.get("hzihpassword");
    var hzihorganization =recode.get("hzihorganization");
    var hzihinstitutions =recode.get("hzihinstitutions");
    var type = recode.get("hzihcertificatetype");
    var formPanel = new Ext.form.FormPanel({
        border:false,
        plain:true,
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        padding:0,
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.ComboBox({
                fieldLabel : '密钥长度',
                typeAhead: true,
                triggerAction: 'all',
                forceSelection: true,
                mode: 'local',
                hiddenName:"keyLength",
                store: typeDataStore,
                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
                displayField: 'name'//下拉框显示内容
            })]
    });
    var win = new Ext.Window({
        title:"发证",
        width:500,
        layout:'fit',
        height:120,
        modal:true,
        items: formPanel,
        bbar:[
            '->',
            {
                id:'certificateIssued.win',
                text:'发布证书',
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url : '../../CaUserAction_certificateIssued.action',
                            timeout: 20*60*1000,
                            params :{type:type,hzihid:hzihid,DN:DN,CN:CN,validate:validate,password:password,hzihprovince:hzihprovince,
                                hzihcity:hzihcity,hzihorganization:hzihorganization,hzihinstitutions:hzihinstitutions},
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'请求成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        grid.getStore().reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'请求失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            },{
                text:'重置',
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
};   //发布证书

function viewRaUser(){
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    var status =recode.get("status")
    var statusValue;
    if(status=="0"){
        statusValue= "正常"
    } else if(status=="1"){
        statusValue= "暂停授权"
    } else if(status=="2"){
        statusValue= "停止授权"
    }
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        width:800,
        autoScroll:true,
        baseCls : 'x-plain',
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        layout:'form',
        border:false,
        defaults:{
            width:250
        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel:'用户名',
                value:recode.get("cn")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'身份证',
                value:recode.get("hzihid")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'联系电话',
                value:recode.get("hzihphone")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'联系地址',
                value:recode.get("hzihaddress")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'电子邮件',
                value:recode.get("hzihemail")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'工号',
                value:recode.get("hzihjobnumber")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'手机入网ID',
                value:recode.get("phonenetid")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'证书序列号',
                value:recode.get("hzihcaserialNumber")
            }),
            new Ext.form.DisplayField({
                fieldLabel:'授权状态',
                value:statusValue
            })
        ]
    });

    var select_Win = new Ext.Window({
        title:"用户详细",
        width:800,
        layout:'fit',
        height:380,
        modal:true,
        items:formPanel
    });
    select_Win.show();
};

function importCaUser(grid_panel,store){
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.TextField({
                fieldLabel : 'ldap连接ip',
                name : 'ip',
                regex:/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
                regexText:'请输入正确的ip地址',
                id:"ra.ldap.ip",
                emptyText:"请输入CA ldap数据库所在主机IP",
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : 'ldap连接端口',
                id:"ra.ldap.port",
                emptyText:"请输入CA ldap数据库所在主机端口",
                regex:/^[1-9]$|(^[1-9][0-9]$)|(^[1-9][0-9][0-9]$)|(^[1-9][0-9][0-9][0-9]$)|(^[1-6][0-5][0-5][0-3][0-5]$)/,
                regexText:'请输入正确的端口',
                name : 'port',
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel:'ldap BaseDN',
                emptyText:"请输入ldap操作结点BaseDN",
                name:'baseDN',
                id:"ra.ldap.ldapBaseDN",
                allowBlank:false,
                blankText:"登陆DN"
            }),
            new Ext.form.TextField({
                fieldLabel:'ldap 管理用户',
                emptyText:"请输入ldap登陆用户名",
                name:'admin',
                allowBlank:false,
                id:"ra.ldap.ldapAdmin",
                blankText:"ldap 管理用户"
            }) ,
            new Ext.form.TextField({
                fieldLabel:'ldap 管理用户密码',
                emptyText:"请输入ldap用户密码",
                name:'password',
                id:"ra.ldap.ldapAdminPass",
                inputType:'password',
                allowBlank:false,
                blankText:"ldap 管理用户密码"
            })
        ]
    });
    var win = new Ext.Window({
        title:"导入CA用户",
        width:500,
        layout:'fit',
        height:230,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'importeCaUser_win.info',
                text:'导入',
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../RaImportLdapUserAction_importRaUser.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'导入成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        store.reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'导入失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            },{
                text:'重置',
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
};

function deleteRaUser() {
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if(!recode){
        Ext.Msg.alert("提示", "请选择一条记录!");
    }else{
        Ext.Msg.confirm("提示", "确认删除吗？", function(sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url : "../../CaUserAction_delete.action",
                    timeout: 20*60*1000,
                    method : "POST",
                    params:{id:recode.get("id")},
                    success : function(form, action) {
                        Ext.Msg.alert("提示", "删除成功!");
                        grid_panel.getStore().reload();
                    },
                    failure : function(result) {
                        Ext.Msg.alert("提示", "删除失败!");
                    }
                });
            }
        });
    }
}  ;

function updateRaUser() {
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.TextField({
                fieldLabel : '用户名',
                name : 'caUser.cn',
                readOnly:true,
                value: recode.get('cn'),
                renderer:show_username,
                regex:/^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                regexText:'只能输入数字,字母,中文,下划线,不能以下划线开头和结尾!',
                id:  'raUser.adRaUser.username',
                allowBlank : false,
                emptyText:"请输入您的姓名",
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '用户密码',
                readOnly:true,
                value: recode.get('hzihpassword'),
                regex : /^[a-zA-Z0-9]+$/,
                regexText:'不能含有特殊字符,只能输入字母数字',
                name : 'caUser.hzihpassword',
                minLength:4,
                maxLength:20,
                emptyText:"请输入4-20个字符",
                inputType:'password',
                id:"raUser.adRaUser.password",
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '身份证号码',
                readOnly:true,
                value: recode.get('hzihid'),
                regex:/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([0-9xX])$/,
                regexText:'请输入有效的身份证号',
                allowBlank : false,
                emptyText:"请输入您的身份证号码",
                blankText : "请填写数字 ，不能为空，请正确填写",
                name : 'caUser.hzihid'
            }),
            new Ext.form.TextField({
                fieldLabel : '联系电话',
                name : 'caUser.hzihphone',
                value: recode.get('hzihphone'),
                regex:/^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/,
                regexText:'请输入正确的电话号或手机号',
                allowBlank : false,
                emptyText:"请输入您的手机号码",
                blankText : "联系电话"
            }),
            new Ext.form.TextField({
                fieldLabel : '联系地址',
                value: recode.get('hzihaddress'),
                emptyText:"请输入您具体联系地址",
                name : 'caUser.hzihaddress',
                allowBlank : false,
                blankText : "联系地址"
            }),
            new Ext.form.TextField({
                fieldLabel : '电子邮件',
                regex:/^[0-9a-zA-Z_\-\.]+@[0-9a-zA-Z_\-]+(\.[0-9a-zA-Z_\-]+)*$/,
                regexText:'请输入您有效的邮件地址',
                name : 'caUser.hzihemail',
                emptyText:"请输入电子邮件地址",
                value: recode.get('hzihemail'),
                allowBlank : false,
                blankText : "电子邮件"
            }),
            new Ext.form.TextField({
                fieldLabel : '工号',
                name : 'caUser.hzihjobnumber',
                emptyText:"请输入您的工号",
                readOnly:true,
                value: recode.get('hzihjobnumber'),
                allowBlank : false,
                blankText : "工号"
            }),
            new Ext.form.TextField({
                fieldLabel : '终端编号',
                emptyText:"请输入您手机终端编号",
                name : 'caUser.terminalid',
                value: recode.get('terminalid'),
                allowBlank : false,
                blankText : "终端编号"
            }),
            new Ext.form.TextField({
                fieldLabel : '手机入网ID',
                name : 'caUser.phonenetid',
                emptyText:"请输入您手机入网号",
                value: recode.get('phonenetid'),
                allowBlank : false,
                blankText : "手机入网ID"
            }),
            new Ext.form.TextField({
                fieldLabel : '证书序列号',
                readOnly:true,
                name : 'caUser.hzihcaserialNumber',
                emptyText:"请输入您所有证书序列号",
                value: recode.get('hzihcaserialNumber'),
                allowBlank : false,
                blankText : "证书序列号"
            })
        ]
    });
    var win = new Ext.Window({
        title:"更新授权用户",
        width:500,
        layout:'fit',
        height:340,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'insert_win.info',
                text:'更新',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../CaUserAction_modify.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            params:{
                              id:recode.get("id")
                            },
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'更新成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        grid_panel.getStore().reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'更新失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            },{
                text:'重置',
                width:50,
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
};

function sleepRaUser(){
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if(!recode){
        Ext.Msg.alert("提示", "请选择一条记录!");
    }else{
        Ext.Msg.confirm("提示", "确认暂停授权吗？", function(sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url : "../../CaUserAction_sleepRaUser.action",
                    timeout: 20*60*1000,
                    method : "POST",
                    params:{id:recode.get("id")},
                    success : function(form, action) {
                        Ext.Msg.alert("提示", "暂停授权成功!");
                        grid_panel.getStore().reload();
                    },
                    failure : function(result) {
                        Ext.Msg.alert("提示", "暂停授权失败!");
                    }
                });
            }
        });
    }
};

function reCastRaUser(){
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if(!recode){
        Ext.Msg.alert("提示", "请选择一条记录!");
    }else{
        Ext.Msg.confirm("提示", "确认重新授权吗？", function(sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url : "../../CaUserAction_reCastRaUser.action",
                    timeout: 20*60*1000,
                    method : "POST",
                    params:{id:recode.get("id")},
                    success : function(form, action) {
                        Ext.Msg.alert("提示", "重新授权成功!");
                        grid_panel.getStore().reload();
                    },
                    failure : function(result) {
                        Ext.Msg.alert("提示", "重新授权失败!");
                    }
                });
            }
        });
    }
};

function stopRaUser(){
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if(!recode){
        Ext.Msg.alert("提示", "请选择一条记录!");
    }else{
        Ext.Msg.confirm("提示", "确认停止授权吗？", function(sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url : "../../CaUserAction_stopRaUser.action",
                    timeout: 20*60*1000,
                    method : "POST",
                    params:{id:recode.get("id")},
                    success : function(form, action) {
                        Ext.Msg.alert("提示", "停止授权成功!");
                        grid_panel.getStore().reload();
                    },
                    failure : function(result) {
                        Ext.Msg.alert("提示", "停止授权失败!");
                    }
                });
            }
        });
    }
};

function addRaUser(grid_panel, store){
    Ext.apply(Ext.form.VTypes, {
        repetition: function(val, field) {     //返回true，则验证通过，否则验证失败
            if (field.repetition) {               //如果表单有使用repetition配置，repetition配置是一个JSON对象，该对象提供了一个名为targetCmpId的字段，该字段指定了需要进行比较的另一个组件ID。
                var cmp = Ext.getCmp(field.repetition.targetCmpId);   //通过targetCmpId的字段查找组件
                if (Ext.isEmpty(cmp)) {      //如果组件（表单）不存在，提示错误
                    Ext.MessageBox.show({
                        title: '错误',
                        msg: '发生异常错误，指定的组件未找到',
                        icon: Ext.Msg.ERROR,
                        buttons: Ext.Msg.OK
                    });
                    return false;
                }
                if (val == cmp.getValue()) {  //取得目标组件（表单）的值，与宿主表单的值进行比较。
                    return true;
                } else {
                    return false;
                }
            }
        },
        repetitionText: '两次输入密码不一致!'
    }) ;

    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.TextField({
                fieldLabel : '用户名',
                name : 'caUser.cn',
                regex:/^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                regexText:'只能输入数字,字母,中文,下划线,不能以下划线开头和结尾!',
                id:  'raUser.adRaUser.username',
                allowBlank : false,
                emptyText:"请输入您的姓名",
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '用户密码',
                regex : /^[a-zA-Z0-9]+$/,
                regexText:'不能含有特殊字符,只能输入字母数字',
                name : 'caUser.hzihpassword',
                minLength:4,
                maxLength:20,
                inputType:'password',
                emptyText:"请输入4-20个字符",
                id:"raUser.adRaUser.password",
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '确认密码',
                regex : /^[a-zA-Z0-9]+$/,
                regexText:'不能含有特殊字符,只能输入字母数字',
                minLength:4,
                maxLength:20,
                emptyText:"请输入4-20个字符",
                id:"raUser.repassword",
                vtype: 'repetition',  //指定repetition验证类型
                repetition: { targetCmpId: 'raUser.adRaUser.password' } , //配置repetition验证，提供目标组件（表单）ID
                inputType:'password',
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '身份证号码',
                regex:/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([0-9xX])$/,
                regexText:'请输入有效的身份证号',
                allowBlank : false,
                emptyText:"请输入您的身份证号码",
                blankText : "请填写数字 ，不能为空，请正确填写",
                name : 'caUser.hzihid'
            }),
            new Ext.form.TextField({
                fieldLabel : '联系电话',
                name : 'caUser.hzihphone',
                regex:/^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/,
                regexText:'请输入正确的电话号或手机号',
                emptyText:"请输入您的手机号码",
                allowBlank : false,
                blankText : "联系电话"
            }),
            new Ext.form.TextField({
                fieldLabel : '联系地址',
                name : 'caUser.hzihaddress',
                allowBlank : false,
                emptyText:"请输入您具体联系地址",
                blankText : "联系地址"
            }),
            new Ext.form.TextField({
                fieldLabel : '电子邮件',
                regex:/^[0-9a-zA-Z_\-\.]+@[0-9a-zA-Z_\-]+(\.[0-9a-zA-Z_\-]+)*$/,
                regexText:'请输入有效的邮件地址',
                name : 'caUser.hzihemail',
                emptyText:"请输入您有效的邮件地址",
                allowBlank : false,
                blankText : "电子邮件"
            }),
            new Ext.form.TextField({
                fieldLabel : '工号',
                name : 'caUser.hzihjobnumber',
                emptyText:"请输入您的工号",
                allowBlank : false,
                blankText : "工号"
            }),
            new Ext.form.TextField({
                fieldLabel : '终端编号',
                emptyText:"请输入您手机终端编号",
                name : 'caUser.terminalid',
                allowBlank : false,
                blankText : "终端编号"
            }),
            new Ext.form.TextField({
                fieldLabel : '手机入网ID',
                emptyText:"请输入您手机入网ID",
                name : 'caUser.phonenetid',
                allowBlank : false,
                blankText : "手机入网ID"
            }),
            new Ext.form.TextField({
                fieldLabel : '证书序列号',
                emptyText:"请输入您所有证书序列号",
                name : 'caUser.hzihcaserialNumber',
                allowBlank : false,
                blankText : "证书序列号"
            })
        ]
    });
    var win = new Ext.Window({
        title:"新增授权用户",
        width:500,
        layout:'fit',
        height:340,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'insert_win.info',
                text:'新增',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../CaUserAction_add.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'新增成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        store.reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'保存失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            },{
                text:'重置',
                width:50,
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
};







