Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var record = new Ext.data.Record.create([
        {name:'stop_url',mapping:'stop_url'} ,
        {name:'allow_url',mapping:'allow_url'} ,
        {name:'allow_process',mapping:'allow_process'} ,
        {name:'stop_process',mapping:'stop_process'} ,
        {name:'disable_wifi',mapping:'disable_wifi'},
        {name:'disable_booth',mapping:'disable_booth'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../AndroidConfigAction_findConfig.action"
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"totalCount",
        root:"root"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load();
    store.on('load',function(){
        var stop_url = store.getAt(0).get('stop_url');
        var allow_url = store.getAt(0).get('allow_url');
        var allow_process = store.getAt(0).get('allow_process');
        var stop_process = store.getAt(0).get('stop_process');
        var disable_wifi = store.getAt(0).get('disable_wifi');
        var disable_booth = store.getAt(0).get('disable_booth');
        Ext.getCmp('android.stop_url').setValue(stop_url);
        Ext.getCmp('android.allow_url').setValue(allow_url);
        Ext.getCmp('android.stop_process').setValue(stop_process);
        Ext.getCmp('android.allow_process').setValue(allow_process);
        Ext.getCmp('android.disable_wifi').setValue(disable_wifi);
        Ext.getCmp('android.disable_booth').setValue(disable_booth);
    });

    var formPanel = new Ext.form.FormPanel({
        plain:true,
        width:500,
        labelAlign:'right',
        labelWidth:200,
        defaultType:'textfield',
        defaults:{
            width:250,
            allowBlank:false,
            blankText:'该项不能为空!'
        },
        items:[
            new Ext.form.Checkbox({
                fieldLabel : '启用网址黑名单策略',
                id:"android.stop_url",
                regexText:'启用网址黑名单策略',
                name : 'stop_url',
                blankText : "启用网址黑名单策略"
            }),
            new Ext.form.Checkbox({
                fieldLabel : '启用网址白名单策略',
                id:"android.allow_url",
                regexText:'启用网址白名单策略',
                name : 'allow_url',
                blankText : "启用网址白名单策略"
            }),
            new Ext.form.Checkbox({
                fieldLabel : '启用进程黑名单策略',
                id:"android.stop_process",
                regexText:'启用进程黑名单策略',
                name : 'stop_process',
                blankText : "启用进程黑名单策略"
            }),
            new Ext.form.Checkbox({
                fieldLabel : '启用进程白名单策略',
                id:"android.allow_process",
                regexText:'启用进程白名单策略',
                name : 'allow_process',
                blankText : "启用进程白名单策略"
            }),
            new Ext.form.Checkbox({
                fieldLabel : '启用禁用wifi策略',
                id:"android.disable_wifi",
                regexText:'启用禁用wifi策略',
                name : 'disable_wifi',
                blankText : "启用禁用wifi策略"
            }),
            new Ext.form.Checkbox({
                fieldLabel : '启用禁用蓝牙策略',
                id:"android.disable_booth",
                regexText:'启用禁用蓝牙策略',
                name : 'disable_booth',
                blankText : "启用禁用蓝牙策略"
            })
        ],
        buttons:[
             '->',
            {
                id:'insert_win.info',
                text:'保存配置',
                handler:function () {
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url:"../../AndroidConfigAction_strategy.action",
                            method:'POST',
                            waitTitle:'系统提示',
                            waitMsg:'正在连接...',
                            success:function () {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'保存成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false
                                });
                            },
                            failure:function () {
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
            }
        ]
    });

    var panel = new Ext.Panel({
        plain:true,
        width:600,
        border:false,
        items:[{
            id:'panel.info',
            xtype:'fieldset',
            title:'终端策略配置',
            width:530,
            items:[formPanel]
        }]
    });
    new Ext.Viewport({
        layout :'fit',
        renderTo:Ext.getBody(),
        autoScroll:true,
        items:[{
            frame:true,
            autoScroll:true,
            items:[panel]
        }]
    });

});


