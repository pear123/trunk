package com.arvato.cc.constant;

/**
 * Created with IntelliJ IDEA.
 * User: caol005
 * Date: 15-7-14
 * Time: 上午9:37
 * To change this template use File | Settings | File Templates.
 */
public interface Constants {

    public final static String SYSTEM_OPERATOR = "SYSTEM";

    public static enum LockedTypeOfLogin {
        Account,
        IP
    }

    public static enum PasswordSaveExceptionStatus {
        ExistInRecentRecord,//最近使用过的密码
        NotEqualsOriginalPasscode,//修改密码时，原密码验证错误
        NotEqualsNewPasscode //新密码和确认密码不一致
    }

    public  static  enum EmailExceptionStatus{
        EmailIsEmpty,//邮箱为空
        IlegalEmail,//非法的邮箱
    }

    public static enum CredentialCode {
        CredentialExpiredTime("90"),
        AvailableLoginTime("9"),//超出错误登录次数
        AutoTimeOfUnlockAccount("30"),
        RequiredInitCredential("1"),
        RecentCredentialRecord("10");//修改密码验证保留近10次
        private String val;

        private CredentialCode(String val) {
            this.val = val;
        }

        public String getVal() {
            return this.val;
        }
    }


    public static String LOG_TEMPLATE_DETAIL = "\r\n" +
            "PROJECT:     BSH CC\r\n" +
            "APP NAME:    %s\r\n" +
            "METHOD:      %s\r\n" +
            "TIME:        %s\r\n" +
            "DESCRIPTION: %s\r\n" +
            "DETAILED INFO:      %s";

    public static String LOG_TEMPLATE = "\r\n" +
            "PROJECT:     BSH CC\r\n" +
            "APP NAME:    %s\r\n" +
            "METHOD:      %s\r\n" +
            "TIME:        %s\r\n" +
            "DESCRIPTION: %s\r\n";


    static enum Sequence {
        ORDER_ITEM_SEQ,
        ALIPAY_RETURN_BATCH_NO
    }

    static enum ComparisonType {
        COMPARISON_TYPE_BOOLEAN("boolean"),
        COMPARISON_TYPE_NUMERIC("numberic"),
        COMPARISON_TYPE_LIST("list"),
        COMPARISON_LT("lt"),
        COMPARISON_GT("gt");
        private String value;

        private ComparisonType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    static enum ExceptionMsg{
        DATA_IS_NULL("数据为空"),
        FORMAT_ERROR("模版错误"),
        ORDER_NO_IS_NUL("订单号为空"),
        DATE_FORMAT_ERROR("日期格式异常"),
        NUMBER_FORMAT_ERROR("数字格式异常"),
        PRICE_FORMAT_ERROR("金额格式异常"),
        FUNCTION_IS_BUSY("此功能正在使用，请稍后操作")
        ;

        private String value;

        private ExceptionMsg(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    static String upload_error_msg = "line[%s] has error,error type is {%s}";

    static String upload_success_msg = "success";
    static String save_success_msg="success";
    static String modify_success_msg="success";
    static String delete_success_msg="success";

    static String null_error_msg = "%s 为空";
    static String format_error_msg = "%s 格式不正确";


    /**
     * log history
     */
    public static enum ModelType {
        UPLOAD,//上传
        DOWNLOAD,//下载
        SYSTEM //系统
    }

    /**
     * log history
     */
    public static enum OperationType {
        UPLOAD_ALIPAY,//
        UPLOAD_SKU,
        UPLOAD_GIFT,
        UPLOAD_CPD,
        UPLOAD_INVOICE,
        UPLOAD_BILL,
        DOWNLOAD_ORDER,
        DOWNLOAD_BILL,
        DOWNLOAD_FINANCE,
        DOWNLOAD_REST,
        DOWNLOAD_AREA_SALE,
        DOWNLOAD_SALE_STRUCTURE,
        ADD_SKU,
        DEL_SKU,
        MOD_SKU,
        ADD_GIFT,
        DEL_GIFT,
        MOD_GIFT
    }
    /**
     * upload status
     */
    public static enum UploadStatus{
        ACTIVE,
        INACTIVE
    }

    /**
     * control different user operate same model
     */
    public static enum Model {
        Alipay,
        Sku,
        Gift,
        CPD,
        Sap,
        Bill
    }

    public static enum Function {
        upload,
        download,
        save,
        delete,
        modify
    }

    public static enum JobStatus {
        process,
        done
    }

    public static enum JobName {
        orderReportJob,
        financeReportJob,
        orderDataJob,
        deliveryReportJob,
        cleanTempDataJob,
        syncJstTradeJob,
        syncJstTradeReSendJob,
        syncJstTradeBySellerJob,
        syncJstRefundJob
    }

//    static enum StoreName{
//        SIEMENS("西门子家电官方旗舰店"),
//        BOSCH("博世家电官方旗舰店");
//        private String value;
//
//        private StoreName(String value) {
//            this.value = value;
//        }
//
//        public String getValue() {
//            return this.value;
//        }
//    }

//    static enum StoreCode{
//        SIEMENS("22511900"),
//        BOSCH("22511920"),
//        SIEMENSCH("西门子"),
//        BOSCHCH("博世");
//        private String value;
//
//        private StoreCode(String value) {
//            this.value = value;
//        }
//
//        public String getValue() {
//            return this.value;
//        }
//    }

//    static enum AlipayStore{
//        SIEMENS("20887011960011000156"),
//        BOSCH("20882116502245910156") ;

//        private String value;
//
//        private AlipayStore(String value) {
//            this.value = value;
//        }
//
//        public String getValue() {
//            return this.value;
//        }
//    }



    static enum CouponType{
        ITEM("itemCoupon"),
        SHOP("shopbonus");

        private String value;

        private CouponType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    static enum TradeStatus{
        WAIT_BUYER_PAY,//等待买家付款
        TRADE_CLOSED_BY_TAOBAO,//付款以前，卖家或买家主动关闭交易
        WAIT_SELLER_SEND_GOODS,//等待卖家发货,即:买家已付款
        WAIT_BUYER_CONFIRM_GOODS,//等待买家确认收货,即:卖家已发货
        TRADE_FINISHED,//交易成功
        TRADE_CLOSED//付款以后用户退款成功，交易自动关闭

    }

    static enum ShippingType{
        virtual,
        express
    }

    static enum OrderReportItemType {
        item,
        order
    }
}
