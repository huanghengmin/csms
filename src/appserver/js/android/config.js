Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';
    var record = new Ext.data.Record.create([
        {name:'wifi_num', mapping:'wifi_num'},
        {name:'booth_num', mapping:'booth_num'} ,
        {name:'up_time', mapping:'up_time'} ,
        {name:'clear_time',mapping:'clear_time'}
       /* {name:'disable_wifi',mapping:'disable_wifi'},
        {name:'disable_booth',mapping:'disable_booth'}*/
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
        var wifi_num = store.getAt(0).get('wifi_num');
        var booth_num = store.getAt(0).get('booth_num');
        var up_time = store.getAt(0).get('up_time');
        var clear_time = store.getAt(0).get('clear_time');
      /*  var disable_wifi = store.getAt(0).get('disable_wifi');
        var disable_booth = store.getAt(0).get('disable_booth');*/
        Ext.getCmp('android.wifi_num').setValue(wifi_num);
        Ext.getCmp('android.booth_num').setValue(booth_num);
        Ext.getCmp('android.up_time').setValue(up_time);
        Ext.getCmp('android.clear_time').setValue(clear_time);
       /* Ext.getCmp('android.disable_wifi').setValue(disable_wifi);
        Ext.getCmp('android.disable_booth').setValue(disable_booth);*/
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
            new Ext.form.TextField({
                fieldLabel:'无线wifi违规界定次数',
                name:'wifi_num',
                regex:/^[1-9]*[1-9][0-9]*$/,
                regexText:'请输入蓝牙违规界定次数',
                emptyText:'请输入蓝牙违规界定次数(>=1)',
                id:"android.wifi_num",
                allowBlank:false,
                blankText:"无线wifi违规界定次数"
            }),
            new Ext.form.TextField({
                fieldLabel:'蓝牙违规界定次数',
                name:'booth_num',
                id:"android.booth_num",
                regex:/^[1-9]*[1-9][0-9]*$/,
                emptyText:'请输入蓝牙违规界定次数(>=1)',
                regexText:'请输入蓝牙违规界定次数',
                allowBlank:false,
                blankText:"蓝牙违规界定次数"
            }),
            new Ext.form.TextField({
                fieldLabel : '终端上报时间间隔(秒)',
                name : 'up_time',
                regex:/^[1-9]*[1-9][0-9]*$/,
                regexText:'请输入终端上报时间间隔(秒)',
                id:"android.up_time",
                emptyText:'请输入终端上报时间间隔(秒>=1)',
                allowBlank : false,
                blankText : "终端上报时间间隔(秒)"
            }),
            new Ext.form.TextField({
                fieldLabel : '终端清空缓存时间间隔(秒)',
                id:"android.clear_time",
                regex:/^[1-9]*[1-9][0-9]*$/,
                emptyText:'终端清空缓存时间间隔(秒>=1)',
                regexText:'请输入终端清空缓存时间间隔(秒)',
                name : 'clear_time',
                allowBlank : false,
                blankText : "终端清空缓存时间间隔(秒)"
            })
          /*  new Ext.form.Checkbox({
                fieldLabel : '启用禁用wifi功能',
                id:"android.disable_wifi",
                regexText:'启用禁用wifi功能',
                name : 'disable_wifi',
                blankText : "启用禁用wifi功能"
            }),
            new Ext.form.Checkbox({
                fieldLabel : '启用禁用蓝牙功能',
                id:"android.disable_booth",
                regexText:'启用禁用蓝牙功能',
                name : 'disable_booth',
                blankText : "启用禁用蓝牙功能"
            })*/
        ],
        buttons:[
             '->',
            {
                id:'insert_win.info',
                text:'保存配置',
                handler:function () {
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url:"../../AndroidConfigAction_config.action",
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
            title:'终端上报参数配置',
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


