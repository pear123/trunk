<#import "spring.ftl" as s>
<#assign sec=JspTaglibs["http://www.springframework.org/security/tags"] />
 <style>
     /*     .header{
                    background:url("${base}/images/header.jpg") repeat-x;
           }*/
 </style>
<!--topnav start-->
<div class="repx topnav">
    <div class="l icn cWhite" style="color:#ffffff;padding-top:inherit;margin-left: 40px">B/S/H CHANNEL CONNECTOR</div>
    <div class="r cf cWhite tvcell"><span>Hi，<@sec.authentication property="name"/> <em
            class="ml20"></em> | </span><span>
       <#-- <a href="${base}/user/index" class="icn idsetting">用户管理</a>-->
        <a href="#" onclick="updatePassword()" class="icn idsetting" style="cursor:auto">修改密码</a>
        <a href="${base}/bsh/j_spring_security_logout" class="icn logout">退出</a></span></div>
    <!--/r-->
</div>
<!--topnav end-->
<!--header start-->
<#--<div class="rel header">-->
<#--<h1 class="l logo"><a href="#" class="ti_">arvato systems,arvato TC</a></h1>-->

<#--<div class="r"><em class="ti_ l tel">arvato system:4006705818</em></div>-->
    <!--/r-->
    <!--msgs start-->
    <#--<i class="icn abs speaker"></i>
    <a href="#" class="abs dn cRed msgcenter" id="msgAll" onclick="return showMsgIn()">消息中心[<span class="cRed">20</span>]</a>
    <div class="msgInner" id="msgIn">
    <i class="icn abs tipA"></i>
    <div class="w280 abs msgs">
      <div class="round color1">
      <b class="b1"></b><b class="b2"></b><b class="b3"></b><b class="b4"></b>
      <div class="brd">
      <ul class="p0p10 w240">
  &lt;#&ndash;        <li class="dbMsg"><a href="#">库存报警数：[6]</a></li>
          <li class="orderMsg"><a href="#">未处理订单：[2]</a></li>
          <li class="errMsg"><a href="#">您的误操作：[3]</a></li>&ndash;&gt;
        </ul>
      <a href="#" class="icn tdn abs close" onclick="return showMsgAll()">&nbsp;</a>
      </div><!--/bd&ndash;&gt;
      <b class="b5"></b><b class="b6"></b><b class="b7"></b><b class="b8"></b>
      </div><!--/round&ndash;&gt;

    </div>
    </div>--><!--/dn-->
    <!--msgs end-->

<#--</div>-->
<!--header end-->

<div class="navwrap" id="navWrap">
    <!--globalnav start-->
    <ul class="f12 tdn globalnav" id="globalNav">

    <#--<li class="master_nav cur">-->
    <#--<span><a href="javascript:;"><em><@s.message "oms.bill.module.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/download/index"><@s.message "oms.bill.menu.download"/></a></li>-->
    <#--<li><a href="${base}/upload/index"><@s.message "oms.bill.menu.upload"/></a></li>-->
    <#--<li><a href="${base}/upload/alipayView"><@s.message "oms.bill.menu.alipay"/></a></li>-->
    <#--<li><a href="${base}/upload/shippingFeeReport"><@s.message "oms.bill.menu.sku"/></a></li>-->
    <#--<li><a href="${base}/upload/shippingFeeReport"><@s.message "oms.bill.menu.materiel"/></a></li>-->
    <#--<li><a href="${base}/upload/shippingFeeReport"><@s.message "oms.bill.menu.customerId"/></a></li>-->
    <#--<li><a href="${base}/upload/shippingFeeReport"><@s.message "oms.bill.menu.stkout"/></a></li>-->
    <#--<li><a href="${base}/upload/shippingFeeReport"><@s.message "oms.bill.menu.invoice"/></a></li>-->
    <#--</ul>-->
    <#--</li>-->
   <#if Session["menus"]?exists>
       <#assign menus = Session["menus"]>
       <#list menus as menu>
           <#if menu_index == 0>
                    <li class="master_nav cur"><span><a href="javascript:;"><em>${menu.resourceName!''}</em></a></span>
           <#else>
                    <li class="master_nav"><span><a href="javascript:;"><em>${menu.resourceName!''}</em></a></span>
           </#if>
                <ul class="content_nav">
                    <#list menu.subResourceNodes as subMenuNodes>
                        <li><a href="${base}${subMenuNodes.resourcePattern!''}">${subMenuNodes.resourceName!''}</a></li>
                    </#list>
                </ul>
            </li>
       </#list>
   </#if>

    <#--<li class="master_nav"><span><a href="javascript:;"><em><@s.message "oms.upload.module.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/upload/alipay"><@s.message "oms.upload.menu.alipay"/></a></li>-->
    <#--<li><a href="${base}/upload/sku"><@s.message "oms.upload.menu.sku"/></a></li>-->
    <#--<li><a href="${base}/upload/gift"><@s.message "oms.upload.menu.gift"/></a>-->
    <#--<li><a href="${base}/upload/cpd"><@s.message "oms.upload.menu.cpd"/></a>-->
    <#--<li><a href="${base}/upload/bill"><@s.message "oms.upload.menu.bill"/></a>-->
    <#--<li><a href="${base}/upload/invoice"><@s.message "oms.upload.menu.invoice"/></a>-->
    <#--</li>-->
    <#--</ul>-->
    <#--</li>-->

    <#--<li class="master_nav"><span><a href="javascript:;"><em><@s.message "oms.download.module.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/download/order"><@s.message "oms.download.menu.order"/></a></li>-->
    <#--<li><a href="${base}/download/stkout"><@s.message "oms.download.menu.stkout"/></a></li>-->
    <#--<li><a href="${base}/download/finance"><@s.message "oms.download.menu.finance"/></a>-->
    <#--<li><a href="${base}/download/areaSale"><@s.message "oms.download.menu.areaSale"/></a>-->
    <#--<li><a href="${base}/download/restFinance"><@s.message "oms.download.menu.restFinance"/></a>-->
    <#--<li><a href="${base}/download/saleStructure"><@s.message "oms.download.menu.saleStructure"/></a>-->
    <#--</li>-->
    <#--</ul>-->
    <#--</li>-->

    <#--<li class="master_nav"><span><a href="javascript:;"><em><@s.message "oms.order.module.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/order/index"><@s.message "oms.order.menu.orderManager"/></a></li>-->
    <#--&lt;#&ndash;<li><a href="${base}/orderException/index"><@s.message "oms.order.menu.orderExceptionManager"/></a></li>&ndash;&gt;-->
    <#--&lt;#&ndash;<li><a href="${base}/orderReturn/omsOrderReturnList"><@s.message "oms.order.menu.orderReturnManager"/></a></li>&ndash;&gt;-->
    <#--&lt;#&ndash;&lt;#&ndash;<li><a href="${base}/stkinHd/list"><@s.message "oms.order.menu.stkinMananger"/></a></li>&ndash;&gt;&ndash;&gt;-->
    <#--&lt;#&ndash;<li><a href="${base}/orderRefund/list"><@s.message "oms.order.menu.refundMananger"/></a></li>&ndash;&gt;-->
    <#--&lt;#&ndash;<li><a href="${base}/orderRefund/unlockList"><@s.message "oms.order.menu.refundUnlock"/></a></li>&ndash;&gt;-->
    <#--&lt;#&ndash;<li><a href="${base}/refund/list"><@s.message "oms.order.menu.refund"/></a></li>&ndash;&gt;-->
    <#--</ul>-->
    <#--</li>-->
    <#--<li class="master_nav"><span><a-->
    <#--href="javascript:;"><em><@s.message "oms.inventory.module.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/inventory/index"><@s.message "oms.inventory.menu.inventoryManager"/></a></li>-->
    <#--</ul>-->
    <#--</li>-->

    <#--        <li class="master_nav"><span><a href="javascript:;"><em><@s.message "oms.ui.module.name"/></em></a></span>
                <ul class="content_nav">
                    <li><a href="${base}/sku/index"><@s.message "oms.ui.menu.sku"/></a></li>
                    <li><a href="${base}/matrn/index"><@s.message "oms.ui.menu.matrn"/></a></li>
                    </li>
                </ul>
            </li>-->

    <#--<li class="master_nav"><span><a href="javascript:;"><em><@s.message "oms.saleReport.module.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/saleReport/areaSale"><@s.message "oms.saleReport.menu.areaSale"/></a></li>-->
    <#--<li><a href="${base}/saleReport/saleStructure"><@s.message "oms.saleReport.menu.saleStructure"/></a></li>-->
    <#--</li>-->
    <#--</ul>-->
    <#--</li>-->
    <#--
          <li class="master_nav"><span><a href="javascript:;"><em><@s.message "oms.sys.module.name"/></em></a></span>
                <ul class="content_nav">
                    <li><a href="${base}/user/index"><@s.message "oms.sys.menu.sysUserManager"/></a></li>
                    <li><a href="${base}/userRole/index"><@s.message "oms.sys.menu.sysUserRoleManager"/></a></li>
                    <li><a href="${base}/userRoleResource/index"><@s.message "oms.sys.menu.sysUserOperationManager"/></a>
                    </li>
                </ul>
            </li>-->
    <#--<li class="master_nav"><span><a-->
    <#--href="javascript:;"><em><@s.message "oms.financeSearch.model.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/finance/paymentGatewayQuery"><@s.message "oms.financeSearch.menu.alipayQuery"/></a>-->
    <#--</li>-->
    <#--<li><a href="${base}/finance/promotionReport"><@s.message "oms.financeSearch.menu.promotionQuery"/></a>-->
    <#--</li>-->
    <#--<li><a href="${base}/finance/order"><@s.message "oms.financeSearch.menu.orderQuery"/></a></li>-->
    <#--<li><a href="${base}/finance/orderReturn"><@s.message "oms.financeSearch.menu.orderReturnQuery"/></a>-->
    <#--</li>-->
    <#--</ul>-->
    <#--</li>-->
    <#--<li class="master_nav"><span><a-->
    <#--href="javascript:;"><em><@s.message "oms.saleReport.model.name"/></em></a></span>-->
    <#--<ul class="content_nav">-->
    <#--<li><a href="${base}/sale/main"><@s.message "oms.saleReport.menu.saleReport"/></a></li>-->
    <#--<li><a href="${base}/sale/topProdudct"><@s.message "oms.saleReport.menu.hotProductReport"/></a></li>-->
    <#--<li><a href="${base}/sale/orderDetail"><@s.message "oms.saleReport.menu.orderQuery"/></a></li>-->
    <#--<li><a href="${base}/sale/singleProduct"><@s.message "oms.saleReport.menu.singleProduct"/></a></li>-->
    <#--<li><a href="${base}/sale/bundleProduct"><@s.message "oms.saleReport.menu.bundleProduct"/></a></li>-->
    <#--</ul>-->
    <#--</li>-->
    </ul>
</div>
<script>
    Ext.onReady(function () {
        new GlobalNav();
    })

    function updatePassword() {
        Ext.apply(Ext.form.VTypes, {
            password: function (val, field) {//val指这里的文本框值，field指这个文本框组件
                if (field.confirmTo) {//confirmTo是自定义的配置参数，一般用来保存另外的组件的id值
                    var pwd = Ext.getCmp(field.confirmTo);//取得confirmTo的那个id的值
                    return (val == pwd.getValue());
                }
                return true;
            }
        });
        Ext.create('Ext.window.Window', {
            id: 'winUpdatePassword',
            title: '修改密码',
            height: 200,
            width: 400,
            layout: 'fit',
            modal: true,
            items: [
                Ext.create('Ext.form.FormPanel', {
                    id: 'credentialForm',
                    bodyPadding: 20,
                    autoScroll: true,
                    tbar: [
                        {
                            xtype: 'button',
                            text: '修改',
                            handler: function () {
                                var credentialForm = Ext.getCmp('credentialForm').getForm();
                                if (!credentialForm.isValid()) {
                                    Ext.Msg.alert(messages['ext.tips.tip'], '您的相关信息不符合要求，请重新输入');
                                    return;
                                }

                                var oldPasswordWord = credentialForm.findField('oldPasswordWord').getValue();
                                var omsUserPassWord = credentialForm.findField('omsUserPassword').getValue();
                                var omsUserPassWordConfirm = credentialForm.findField('omsUserPassWordConfirm').getValue();

                                Ext.Ajax.request({
                                    url: path('/user/updatePassword'),
                                    params: {
                                        omsUserId: '<@sec.authentication property="name" htmlEscape="false"/>',
                                        oldPasswordWord: oldPasswordWord,
                                        omsUserPassWord: omsUserPassWord,
                                        omsUserPassWordConfirm: omsUserPassWordConfirm
                                    },
                                    success: function (json) {
                                        var data = Ext.decode(json.responseText);
                                        if (data.result == "NotEqualsOriginalPasscode") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '原密码输入错误');
                                        } else if (data.result === "NotEqualsNewPasscode") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '您输入的新旧密码不一致');
                                        } else if (data.result == "ExistInRecentRecord") {
                                            Ext.Msg.alert(messages['ext.tips.error'], '您的新密码最近使用过，请更换其他密码');
                                        } else if (data.result == "success") {
                                            Ext.Msg.alert(messages['ext.tips.tip'], '修改密码成功，请重新登录', function (btn) {
                                                window.location.href = "${base}/bsh/j_spring_security_logout";
                                            });
                                        }
                                    },
                                    failure: function () {
                                        Ext.Msg.alert(messages['ext.tips.error'], messages['ext.tips.errorMsg']);
                                    }
                                });
                            }
                        },
                        {
                            xtype: 'button',
                            text: '取消',
                            handler: function () {
                                Ext.getCmp('winUpdatePassword').close();
                            }
                        }
                    ],
                    defaults: {
                        labelAlign: 'right'
                    },
                    items: [
                        {
                            fieldLabel: '原&nbsp;密&nbsp;码<span style=color:red>*</span>',
                            xtype: 'textfield',
                            id: 'oldPasswordWord',
                            name: 'oldPasswordWord',
                            readOnlyCls: 'disable',
                            inputType: 'password',
                            allowBlank: false, // 不允许为空
                            blankText: "不能为空",
                            width: 300,
                            maxLength: 50,
                            labelAlign: 'right',
                            maxLengthText: '最大长度不能超过50个字符!'
                        },
                        {
                            fieldLabel: '用户密码<span style=color:red>*</span>',
                            xtype: 'textfield',
                            id: 'omsUserPassword',
                            name: 'omsUserPassword',
                            inputType: 'password',
                            allowBlank: false,
                            blankText: "不能为空",
                            width: 300,
                            maxLength: 16,
                            maxLengthText: '最大长度不能超过16个字符',
                            minLength: 6,
                            minLengthText: '最小长度不能小于6个字符',
//                            regex: new RegExp("(?![^a-zA-Z]+$)(?!\D+$)(?![a-zA-Z0-9]+$).{6,16}"),
                            regex: new RegExp("^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9])).{6,16}$"),
                            invalidText: '密码必须包含大小写字母、数字以及特殊字符且长度为6-16位'
                        },
                        {
                            fieldLabel: '确认密码<span style=color:red>*</span>',
                            xtype: 'textfield',
                            id: 'omsUserPassWordConfirm',
                            name: 'omsUserPassWordConfirm',
                            inputType: 'password',
                            width: 300,
                            vtype: "password", //自定义的验证类型
                            vtypeText: "两次密码不一致！",
                            confirmTo: "omsUserPassword"//要比较的另外一个的组件的id
                        }
                    ]
                })
            ]
        }).show();
    }
</script>

