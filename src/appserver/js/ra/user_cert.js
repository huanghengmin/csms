Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var typeData = [
        /* ['jks','生成jks文件'],*/['pfx','生成pfx文件'],['crt','生成crt文件']
    ];
    var typeDataStore = new Ext.data.SimpleStore({
        fields:['id','name'],
        data:typeData
    });

//    var record = new Ext.data.Record.create([
//        {name:'cn',mapping:'cn'},
//        {name:'hzihdatadn',mapping:'hzihdatadn'},
//        {name:'hzihprovince',mapping:'hzihprovince'},
//        {name:'hzihcity',mapping:'hzihcity'},
//        {name:'hzihsubdn',mapping:'hzihsubdn'},
//        {name:'hzihorganization',mapping:'hzihorganization'},
//        {name:'hzihinstitutions',mapping:'hzihinstitutions'},
//        {name:'hzihcastatus',mapping:'hzihcastatus'}
//    ]);
//
//    var proxy = new Ext.data.HttpProxy({
//        url:'../../Ca_selectCa.action',
//        method:"POST"
//    });
//
//    var reader = new Ext.data.JsonReader({
//        totalProperty:"totalCount",
//        root:"root"
//    }, record);
//
//    var caStore = new Ext.data.GroupingStore({
//        id:"store.info",
//        proxy:proxy,
//        reader:reader
//    });
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
        //title:'新增用户',
        plain:true,
        labelAlign:'right',
        labelWidth:150,
        defaultType:'textfield',
        defaults:{
            width:250,
            allowBlank:false,
            blankText:'该项不能为空!'
        },
        items:[
//            new Ext.form.ComboBox({
//                emptyText :'--请选择--',
//                editable : false,
//                typeAhead:true,
//                forceSelection: true,
//                triggerAction:'all',
//                fieldLabel : '签发CA',
//                hiddenName : 'hzihUser.hzihparentca',
//                id:'hzihUser.addEntity.hzihparentcadn',
//                //name : 'hzihUser.hzihparentcadn',
//                store : caStore,
//                displayField : "hzihsubdn",// 关联某一个逻辑列名作为显示值
//                valueField : "hzihdatadn" ,
//                listeners:{
//                    select:function(){
//                        Ext.getCmp("hzihUser.hzihusername").reset();
//                    }
//                }
//            }),
            new Ext.form.TextField({
                fieldLabel : '用户名',
                name : 'hzihUser.cn',
                emptyText:'请输入用户名称,只能输入数字,字母,中文,下划线,不能以下划线开头和结尾!',
                id:  'hzihUser.hzihusername',
                allowBlank : false,
                regex : /^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/,
                regexText:'只能输入数字,字母,中文,下划线,不能以下划线开头和结尾!',
                blankText : "不能为空，请正确填写"/*,
                *//*   validationEvent : 'blur',
                 validator : function() {*//*
                listeners:{
                    blur:function(){
                        //var isNameOK;
                        var thisCommon = Ext.getCmp("hzihUser.hzihusername");
                        var cn = thisCommon.getValue();
//                        var parentCa = Ext.getCmp("hzihUser.addEntity.hzihparentcadn").getValue();
//                        if(parentCa!=null&&parentCa!=""){
                        if(cn!=''){
                            Ext.Ajax.request({
                                url : '../../CertOprationAction_existUserName.action',
                                method : 'post',
                                params : {
                                    cn : cn*//* ,parentCa:parentCa*//*
                                },
                                success : function(r,o) {
                                    var respText = Ext.util.JSON.decode(r.responseText);
                                    var msg = respText.msg;
                                    if (msg == 'false') {
                                        *//*   isNameOK=false;
                                         thisCommon.markInvalid('CA名称已被使用');
                                         thisCommon.setValue('');*//*
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            width:250,
                                            msg:'用户名称已被使用,请更换！',
                                            buttons:Ext.MessageBox.OK,
                                            buttons:{'ok':'确定'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                if(e=='ok'){
                                                    Ext.getCmp('hzihUser.hzihusername').setValue('');
                                                }
                                            }
                                        });
                                    }*//*else{
                                     isNameOK=true;
                                     thisCommon.clearInvalid();
                                     }*//*
                                }
                            });
                        }
//                        }else{
//                            Ext.MessageBox.show({
//                                title:'警告',
//                                width:200,
//                                msg:'请先选择签发CA!',
//                                buttons:Ext.MessageBox.OK,
//                                buttons:{'ok':'确定'},
//                                icon:Ext.MessageBox.WARNING,
//                                closable:false
//                            });
//
//                        }
                        //return isNameOK;
                    }
                }*/
            }),
            new Ext.form.TextField({
                fieldLabel : '用户密码',
                emptyText:'请输入用户密码',
                name : 'hzihUser.hzihpassword',
                regex : /^[a-zA-Z0-9]+$/,
                regexText:'不能含有特殊字符,只能输入字母数字',
                minLength:4,
                maxLength:20,
                inputType:'password',
                id:"hzihUser.hzihpassword",
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '确认密码',
                emptyText:'确认密码',
                minLength:4,
                maxLength:20,
                id:"hzihUser.repassword",
                vtype: 'repetition',  //指定repetition验证类型
                repetition: { targetCmpId: 'hzihUser.hzihpassword' } , //配置repetition验证，提供目标组件（表单）ID
                inputType:'password',
                allowBlank : false,
                blankText : "不能为空，请正确填写"
            }),
            new Ext.form.TextField({
                fieldLabel : '身份证号码',
                emptyText:'请输入身份证号码',
                id:'hzihUser.hzihid',
                //minLength:15,
                //maxLength:18,
                regex:/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})([0-9xX])$/,
                regexText:'请输入有效的身份证号',
                allowBlank : false,
                blankText : "请填写数字 ，不能为空，请正确填写",
                name : 'hzihUser.hzihid'
            }),
            new Ext.form.TextField({
                fieldLabel : '联系电话',
                emptyText:'请输入联系电话',
                name : 'hzihUser.hzihphone',
                regex:/^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/,
                regexText:'请输入正确的电话号或手机号',
                allowBlank : false,
                blankText : "联系电话"
            }),
            new Ext.form.TextField({
                fieldLabel : '联系地址',
                emptyText:'请输入联系地址',
                name : 'hzihUser.hzihaddress',
                allowBlank : false,
                blankText : "联系地址"
            }),
            new Ext.form.TextField({
                fieldLabel : '电子邮件',
                emptyText:'请输入电子邮件',
                regex:/^[0-9a-zA-Z_\-\.]+@[0-9a-zA-Z_\-]+(\.[0-9a-zA-Z_\-]+)*$/,
                regexText:'请输入有效的邮件地址',
                name : 'hzihUser.hzihemail',
                allowBlank : false,
                blankText : "电子邮件"
            }),
            new Ext.form.TextField({
                fieldLabel : '警号',
                emptyText:'请输入警号',
                name : 'hzihUser.hzihjobnumber',
                // regex:/^d+$/,
                allowBlank : false,
                blankText : "警号"
            }),
            new Ext.form.DateField({
                fieldLabel : '证书截止日期',
                name : 'hzihUser.hzihcavalidity',
                id:'ca.caValidity',
                format:'Y-m-d',
                minValue:new Date(new Date().getTime()-24*60*60*1000),
//                allowBlank : false,
//                allowDecimals : false, // 允许小数点？
//                allowNegative : false, // 允许负数？
//                maxValue: 36500,
//                value:'3650',
//                minValue: 1,
                blankText : "不能为空，按天计算",
                listeners:{
                    select:function(){
                        var validateComment = Ext.getCmp("ca.caValidity");
                        var selectDate = validateComment.getValue();
                        Ext.Ajax.request({
                            url : '../../CertOprationAction_getParentCaValidate.action',
                            method : 'post',
                            params : {
                                /*DN : DN,*/selectDate:selectDate
                            },
                            success : function(r,o) {
                                var respText = Ext.util.JSON.decode(r.responseText);
                                var msg = respText.msg;
                                var parentValidate = respText.validate;
                                if (msg == 'false') {
                                    Ext.MessageBox.show({
                                        title:'信息',
                                        width:250,
                                        msg:'证书有效期不能超过上级CA截止日期'+parentValidate,
                                        buttons:Ext.MessageBox.OK,
                                        buttons:{'ok':'确定'},
                                        icon:Ext.MessageBox.INFO,
                                        closable:false,
                                        fn:function(e){
                                            if(e=='ok'){
                                                Ext.getCmp('ca.caValidity').setValue('');
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
                /*     listeners:{
                 focus:function(){
                 var validateComment = Ext.getCmp("ca.caValidity");
                 Ext.Ajax.request({
                 url : '../../CertOprationAction_getParentCaValidate.action',
                 method : 'post',
                 *//*   params : {
                 DN : DN
                 },*//*
                 success : function(r,o) {
                 var respText = Ext.util.JSON.decode(r.responseText);
                 var parentValidate = respText.validate;
                 validateComment.setMaxValue(new Date(parentValidate))
                 }
                 });
                 }
                 }*/
                /*   validationEvent : 'blur',
                 validator : function() {
                 var isNameOK;*/
                /*    listeners:{
                 blur:function(){
                 var validateComment = Ext.getCmp("ca.caValidity");
                 var date = validateComment.getValue();
                 var parentCa = Ext.getCmp("hzihUser.addEntity.hzihparentcadn").getValue();
                 Ext.Ajax.request({
                 url : '../../CertOprationAction_getParentCaValidate.action',
                 method : 'post',
                 params : {
                 DN : parentCa,date:date
                 },
                 success : function(r,o) {
                 var respText = Ext.util.JSON.decode(r.responseText);
                 var msg = respText.msg;
                 var parentValidate = respText.validate;
                 if (msg == 'false') {
                 Ext.MessageBox.show({
                 title:'信息',
                 width:250,
                 msg:'证书有效期不能超过上级CA有效期'+parentValidate+'天!',
                 buttons:Ext.MessageBox.OK,
                 buttons:{'ok':'确定'},
                 icon:Ext.MessageBox.INFO,
                 closable:false,
                 fn:function(e){
                 if(e=='ok'){
                 Ext.getCmp('ca.caValidity').setValue('');
                 }
                 }
                 });
                 }*//*else{
                 isNameOK = true;
                 validateComment.clearInvalid();
                 }*//*
                 }
                 });
                 //return isNameOK;
                 }
                 }*/
            }),
            new Ext.form.ComboBox({
                fieldLabel : '证书类型',
                typeAhead: true,
                triggerAction: 'all',
                forceSelection: true,
                mode: 'local',
                hiddenName:"hzihUser.hzihcertificatetype",
                store: typeDataStore,
                valueField: 'id',   //下拉框具体的值（例如值为SM，则显示的内容即为‘短信’）
                displayField: 'name'//下拉框显示内容
            })

        ] ,
        buttons:[{
            id:'insert_win.info',
            text:'申请证书',
            handler:function(){
                if (formPanel.getForm().isValid()) {
                    var user_name = Ext.getCmp("hzihUser.hzihusername").getValue();
                    var user_id = Ext.getCmp("hzihUser.hzihid").getValue();
                    var cn = user_name + "_" + user_id;
                    Ext.Ajax.request({
                        url : '../../CertOprationAction_existUserName.action',
                        timeout:20 * 60 * 1000,
                        method:'post',
                        params:{
                            cn:cn /*,parentCa:parentCa*/
                        },
                        success:function (r, o) {
                            var respText = Ext.util.JSON.decode(r.responseText);
                            var msg = respText.msg;
                            if (msg == 'false') {
                                /*   isNameOK=false;
                                 thisCommon.markInvalid('用户名称已被使用');*/
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'用户名称对应身份证书已被使用,请更换！',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function (e) {
                                        if (e == 'ok') {
                                            Ext.getCmp('hzihUser.hzihusername').setValue('');
                                            Ext.getCmp("hzihUser.hzihid").setValue('');
                                        }
                                    }
                                });
                            } else {
                                formPanel.getForm().submit({
                                    url :'../../CertOprationAction_addPublicUser.action',
                                    method :'POST',
                                    waitTitle :'系统提示',
                                    timeout:20*60*1000,
                                    waitMsg :'正在连接...',
                                    success : function() {
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            width:250,
                                            msg:'申请成功,待审批发证后再来下载!',
                                            buttons:Ext.MessageBox.OK,
                                            buttons:{'ok':'确定'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                formPanel.getForm().reset();
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
                            }
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
    });


    var panel = new Ext.Panel({
        plain:true,
        width:600,
        border:false,
        items:[{
            id:'panel.info',
            xtype:'fieldset',
            title:'终端用户申请',
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
            items:[ panel]
        }]
    });
    /*   var win = new Ext.Window({
     align:'center',
     title:'新增用户请求',
     region:'center',
     width:600,
     autoScroll:true,
     modal:true,
     layout:"form",
     //            renderTo:port,
     frame:true,
     items:formPanel
     }).show();*/

    /*     var port = new Ext.Viewport({
     layout:'fit',
     region:'center',
     align:'center',
     renderTo:Ext.getBody(),
     items:[panels]
     });*/
});