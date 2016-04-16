Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
//        {name:'id', mapping:'id'} ,
        {name:'hzihphone', mapping:'hzihphone'} ,
        {name:'hzihaddress', mapping:'hzihaddress'},
        {name:'hzihemail', mapping:'hzihemail'},
        {name:'hzihjobnumber', mapping:'hzihjobnumber'},
        {name:'phonenetid', mapping:'phonenetid'},
        {name:'hzihcaserialNumber', mapping:'hzihcaserialNumber'},
        {name:'cn', mapping:'cn'} ,
        {name:'hzihpassword', mapping:'hzihpassword'} ,
        {name:'hzihid', mapping:'hzihid'} ,
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
        {name:'hzihcertificatetype', mapping:'hzihcertificatetype'} ,
//        {name:'locationstr', mapping:'locationstr'},
        {name:'viewFlag', mapping:'viewFlag'},
        {name:'location',mapping:'location'} ,
        {name:'status', mapping:'status'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../TerminalAction_onLineUser.action" ,
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

    var rowNumber = new Ext.grid.RowNumberer();         //自动编号

    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"用户名", dataIndex:"cn",align:'center', sortable:true, menuDisabled:true,sort:true} ,
        {header:"身份证", dataIndex:"hzihid",align:'center', sortable:true, menuDisabled:true} ,
        {header:"手机串号", dataIndex:"phonenetid",align:'center', sortable:true, menuDisabled:true} ,
        {header:"证书序列号",dataIndex:"hzihcaserialNumber",align:'center', sortable:true, menuDisabled:true,width:250},
        {header:"地理位置", dataIndex:"location",align:'center', sortable:true, menuDisabled:true,width:300} ,
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
        bbar:page_toolbar
    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[grid_panel]
    });
});

function show_flag(value, p, r){
    if( r.get("location") == true  && r.get("terminal_pwd_audit")=="1"&&parseInt(r.get("hzihcastatus"))==3) {
        return String.format(
            '<a id="revokeCa.info" href="javascript:void(0);" onclick="revokeCa();return false;"style="color: green;">吊销</a>&nbsp;&nbsp;&nbsp;'  +
               '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="allowPwd.info" href="javascript:void(0);" onclick="allowPwd();return false;" style="color: green;">允许重置密码</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="closeLocation();return false;" style="color: green;">关闭定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }
    if( r.get("location") == true  && r.get("terminal_pwd_audit")=="1"&&parseInt(r.get("hzihcastatus"))!=3) {
        return String.format(
            '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="allowPwd.info" href="javascript:void(0);" onclick="allowPwd();return false;" style="color: green;">允许重置密码</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="closeLocation();return false;" style="color: green;">关闭定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }

    if( r.get("location") != true && r.get("terminal_pwd_audit")=="1"&&parseInt(r.get("hzihcastatus"))==3) {
        return String.format(
            '<a id="revokeCa.info" href="javascript:void(0);" onclick="revokeCa();return false;"style="color: green;">吊销</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="allowPwd.info" href="javascript:void(0);" onclick="allowPwd();return false;" style="color: green;">允许重置密码</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="uploadLocation();return false;" style="color: green;">开启定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }
    if( r.get("location") != true && r.get("terminal_pwd_audit")=="1"&&parseInt(r.get("hzihcastatus"))!=3) {
        return String.format(
            '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="allowPwd.info" href="javascript:void(0);" onclick="allowPwd();return false;" style="color: green;">允许重置密码</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="uploadLocation();return false;" style="color: green;">开启定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }

    if( r.get("location") == true  && r.get("terminal_pwd_audit")!="1"&&parseInt(r.get("hzihcastatus"))==3) {
        return String.format(
            '<a id="revokeCa.info" href="javascript:void(0);" onclick="revokeCa();return false;"style="color: green;">吊销</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="closeLocation();return false;" style="color: green;">关闭定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }
    if( r.get("location") == true  && r.get("terminal_pwd_audit")!="1"&&parseInt(r.get("hzihcastatus"))!=3) {
        return String.format(
            '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="closeLocation();return false;" style="color: green;">关闭定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }
    if( r.get("location") != true  && r.get("terminal_pwd_audit")!="1"&&parseInt(r.get("hzihcastatus"))==3) {
        return String.format(
            '<a id="revokeCa.info" href="javascript:void(0);" onclick="revokeCa();return false;"style="color: green;">吊销</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="uploadLocation();return false;" style="color: green;">开启定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }
    if( r.get("location") != true  && r.get("terminal_pwd_audit")!="1"&&parseInt(r.get("hzihcastatus"))!=3) {
        return String.format(
            '<a id="show_process.info" href="javascript:void(0);" onclick="showProcess();return false;" style="color: green;">查看终端进程</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="viewPhoto.info" href="javascript:void(0);" onclick="viewPhoto();return false;" style="color: green;">查看图片</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="view.info" href="javascript:void(0);" onclick="viewProcess();return false;" style="color: green;">截屏</a>&nbsp;&nbsp;&nbsp;' +
                '<a id="location.info" href="javascript:void(0);" onclick="uploadLocation();return false;" style="color: green;">开启定位</a>&nbsp;&nbsp;&nbsp;'
        );
    }
};

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

};                  //吊销证书

function viewPhoto(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var serialnumber  = recode.get("hzihcaserialNumber");
    Ext.Ajax.request({
        url : "../../TerminalAction_viewPhoto.action",
        timeout: 5*60*1000,
        method : "POST",
        params:{serialnumber:serialnumber},
        success : function(r,o) {
            var respText = Ext.util.JSON.decode(r.responseText);
            var url = respText.url;
            var str=  "../.."+url;
            var s="<img src='"+str+"'>";
            var win = new Ext.Window({
                layout : 'fit',
                width : 500,
                height : 680,
                items:[{
                    html:s,
                    align:'center'
                }]
            }).show();
        },
        failure : function(r,o) {
            Ext.Msg.alert("提示", "文件未上传!");
        }
    });
};

function allowPwd(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var serialnumber  = recode.get("hzihcaserialNumber");
    Ext.Ajax.request({
        url : "../../TerminalAction_updateAuditPwdStatus.action",
        timeout: 5*60*1000,
        method : "POST",
        params:{serialnumber:serialnumber},
        success : function(form, action) {
            Ext.Msg.alert("提示", "允许重置密码成功!");
            grid.getStore().reload();
        },
        failure : function(result) {
            Ext.Msg.alert("提示", "允许重置密码失败!");
        }
    });
};

function uploadLocation(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var serialnumber  = recode.get("hzihcaserialNumber");
    Ext.Ajax.request({
        url : "../../TerminalAction_location.action",
        timeout: 5*60*1000,
        method : "POST",
        params:{serialnumber:serialnumber},
        success : function(form, action) {
            Ext.Msg.alert("提示", "开启定位成功!");
            grid.getStore().reload();
        },
        failure : function(result) {
            Ext.Msg.alert("提示", "开启定位失败!");
        }
    });
};

function closeLocation(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var serialnumber  = recode.get("hzihcaserialNumber");
    Ext.Ajax.request({
        url : "../../TerminalAction_closeLocation.action",
        timeout: 5*60*1000,
        method : "POST",
        params:{serialnumber:serialnumber},
        success : function(form, action) {
            Ext.Msg.alert("提示", "关闭定位成功!");
            grid.getStore().reload();
        },
        failure : function(result) {
            Ext.Msg.alert("提示", "关闭定位失败!");
        }
    });
};

function viewProcess(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var serialnumber  = recode.get("hzihcaserialNumber");
    Ext.Ajax.request({
        url : "../../TerminalAction_updateView.action",
        timeout: 5*60*1000,
        method : "POST",
        params:{serialnumber:serialnumber},
        success : function(form, action) {
            Ext.Msg.alert("提示", "更新截屏状态成功!");
            grid.getStore().reload();
        },
        failure : function(result) {
            Ext.Msg.alert("提示", "更新截屏状态失败!");
        }
    });
};

function showProcess(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var serialnumber  = recode.get("hzihcaserialNumber");

    var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:true});   //复选框单选

    var rowNumber = new Ext.grid.RowNumberer();         //自动编号

    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"进程ID", dataIndex:"processId", sortable:true, menuDisabled:true} ,
        {header:"进程名称", dataIndex:"processName", sortable:true, menuDisabled:true} ,
        {header:"操作标记",dataIndex:'flag', sortable:true, menuDisabled:true, renderer:add_flag}
    ]);

    function  add_flag(value, p, r){
        return String.format(
            '<a id="stop_list.info" href="javascript:void(0);" onclick="stop_list();return false;" style="color: green;">加入进程黑名单</a>&nbsp;&nbsp;&nbsp;'+
                '<a id="allow_list.info" href="javascript:void(0);" onclick="allow_list();return false;" style="color: green;">加入允许安装应用</a>&nbsp;&nbsp;&nbsp;'
        );
    };

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'processId', mapping:'processId'},
        {name:'processName', mapping:'processName'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:'../../TerminalAction_getProcessList.action?serialnumber='+serialnumber,
        method:'POST'
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, record);
    var store = new Ext.data.GroupingStore({
        id:"store.processList.info",
        proxy:proxy,
        reader:reader
    });

    store.load({
        params:{
            start:start, limit:pageSize
        }
    });
    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });
    var list_grid_panel = new Ext.grid.GridPanel({
        id:'grid.processList.info',
        plain:true,
        height:300,
        width:500,
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        bodyStyle:'width:100%',
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:true,
        cm:colM,
        sm:boxM,
        store:store,
        bbar:page_toolbar
    });

    var win = new Ext.Window({
        title:"终端访问进程",
        width:800,
        layout:'fit',
        height:450,
        modal:true,
        items:list_grid_panel
    }).show();

};

function stop_list(){
    var grid = Ext.getCmp('grid.processList.info');
    var recode = grid.getSelectionModel().getSelected();
    var processId  = recode.get("processId");
    var processName  = recode.get("processName");
    Ext.Msg.confirm("提示", "确认将此进程加入黑名单吗？", function(sid) {
        if (sid == "yes") {
            Ext.Ajax.request({
                url : "../../StopListAction_add.action",
                timeout: 5*60*1000,
                method : "POST",
                params:{processId:processId,processName:processName},
                success : function(form, action) {
                    Ext.Msg.alert("提示", "加入黑名单成功!");
                },
                failure : function(result) {
                    Ext.Msg.alert("提示", "加入黑名单失败!");
                }
            });
        }
    });
};

function allow_list(){
    var grid = Ext.getCmp('grid.processList.info');
    var recode = grid.getSelectionModel().getSelected();
    var processId  = recode.get("processId");
    var processName  = recode.get("processName");
    Ext.Msg.confirm("提示", "确认将此进程加入允许安装应用吗？", function(sid) {
        if (sid == "yes") {
            Ext.Ajax.request({
                url : "../../AllowListAction_add.action",
                timeout: 5*60*1000,
                method : "POST",
                params:{processId:processId,processName:processName},
                success : function(form, action) {
                    Ext.Msg.alert("提示", "加入成功!");
                },
                failure : function(result) {
                    Ext.Msg.alert("提示", "加入失败!");
                }
            });
        }
    });
};
