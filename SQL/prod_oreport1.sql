CREATE DEFINER=`root`@`localhost` PROCEDURE `prod_oreport`(IN store_id VARCHAR(50),tid VARCHAR(50), pricingdate_start VARCHAR(50) , pricingdate_end VARCHAR(50), alipay_start VARCHAR(50) , alipay_end VARCHAR(50))
BEGIN

DECLARE done INT DEFAULT 0;
/*定义动态查询SQL，并初始化*/
DECLARE _v_column VARCHAR(1000);
DECLARE _v_column_l VARCHAR(1000);

DECLARE _v_where VARCHAR(1000);
DECLARE _sql_base VARCHAR(5000);

DECLARE _flag    int;

DECLARE _total_fee    DOUBLE;
DECLARE _refund_fee   DOUBLE;
DECLARE _refund_payment   DOUBLE;
DECLARE _good_status  VARCHAR(200);
DECLARE _status  VARCHAR(200);
DECLARE _has_good_return  VARCHAR(50);
DECLARE _need_delete_id VARCHAR(1000);
DECLARE _pay_fee      DOUBLE;/*开票金额*/
DECLARE _line_pay_fee DOUBLE;/*开票金额*/
DECLARE _coupon_price DOUBLE;/*优惠券总金额*/
DECLARE _point_price DOUBLE;/*积分总金额*/
DECLARE _line_quantity DOUBLE;/*汇总行数量*/
DECLARE _line_coupon DOUBLE;/*汇总行coupon*/

DECLARE _is_delete_flag varchar(20);
DECLARE _tid_count int;


DECLARE _id                  VARCHAR(100);
DECLARE _seller_memo         VARCHAR(1000);
DECLARE _invoice_name        VARCHAR(200);
DECLARE _receiver_name       VARCHAR(200);
DECLARE _item_title          VARCHAR(200);
DECLARE _item_type           VARCHAR(200);
DECLARE _order_pay           DOUBLE;
DECLARE _order_payment       DOUBLE;
DECLARE _item_payment        DOUBLE;
DECLARE _end_time            timestamp;
DECLARE _original_point_fee  DOUBLE;
DECLARE _original_coupon     DOUBLE;
DECLARE _salesorg            VARCHAR(20);
DECLARE _distrchannel        VARCHAR(20);
DECLARE _salesdoctype        VARCHAR(20);
DECLARE _office_name         VARCHAR(50);
DECLARE _po_number           VARCHAR(200);
DECLARE _pricingdate         timestamp;
DECLARE _partnerfunctn       VARCHAR(30);
DECLARE _name                VARCHAR(200);
DECLARE _street              VARCHAR(200);
DECLARE _city                VARCHAR(200);
DECLARE _postal_code         VARCHAR(200);
DECLARE _material            VARCHAR(200);
DECLARE _quantity            INT;
DECLARE _tmall_price         VARCHAR(200);
DECLARE _condition_amount    DOUBLE;
DECLARE _tmall_points        VARCHAR(200);
DECLARE _point_amout         DOUBLE;
DECLARE _tmall_coupon        VARCHAR(50);
DECLARE _coupon_amount       DOUBLE;
DECLARE _poachiveno          VARCHAR(200);
DECLARE _internalnote        VARCHAR(200);
DECLARE _billingtext         VARCHAR(200);
DECLARE _text1               VARCHAR(1000);
DECLARE _text2               VARCHAR(1000);
DECLARE _text3               VARCHAR(1000);
DECLARE _store_id            VARCHAR(100);
DECLARE _trade_status        VARCHAR(100);
DECLARE _tid                 VARCHAR(100);
DECLARE _point_fee_remark    VARCHAR(45);
DECLARE _coupon_remark       VARCHAR(45);
DECLARE _address_remark      VARCHAR(45);
DECLARE _count_remark        VARCHAR(200);
DECLARE _payment             DOUBLE;
DECLARE _alipay_no           VARCHAR(100);
DECLARE _refund_id           VARCHAR(50);
DECLARE _create_time         timestamp;
DECLARE _in_fee              DOUBLE;
DECLARE _condition_amount_remark VARCHAR(200);

DECLARE rs_cur CURSOR FOR SELECT id,seller_memo,invoice_name,receiver_name,item_title,item_type,order_pay,order_payment,item_payment,end_time,original_point_fee,original_coupon,salesorg,distrchannel,salesdoctype,po_number,pricingdate,partnerfunctn,name,street,city,postal_code,material,quantity,tmall_price,condition_amount,tmall_points,point_amout,tmall_coupon,coupon_amount,poachiveno,internalnote,billingtext,text1,text2,text3,store_id,trade_status,tidi,point_fee_remark,coupon_remark,address_remark,count_remark,payment,alipay_no,refund_id,create_time,in_fee,OFFICE_NAME,OFFICE_NAME1,refund_fee,condition_amount_remark FROM tmp_order_report order by tidi,item_type;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
/********************* 创建报表临时表 start *****************************/
DROP TEMPORARY TABLE IF EXISTS tmp_order_report;
CREATE TEMPORARY TABLE tmp_order_report (
	id               int,
	seller_memo      VARCHAR(1000),
	invoice_name     VARCHAR(200),
	receiver_name    VARCHAR(200),
	item_title       VARCHAR(200),
	item_type        VARCHAR(200),
	order_pay        DOUBLE,
	order_payment    DOUBLE,
	item_payment     DOUBLE,
	end_time         timestamp,
	original_point_fee DOUBLE,
	original_coupon DOUBLE,
	SALESORG	 VARCHAR(20),
	DISTRCHANNEL	 VARCHAR(20),
	SALESDOCTYPE	 VARCHAR(20),
	OFFICE_NAME	 VARCHAR(50),
	PO_NUMBER	 VARCHAR(200),
	CREATE_TIME	 timestamp,
	PRICINGDATE	 timestamp,
	PARTNERFUNCTN	 VARCHAR(30),
	OFFICE_NAME1	 VARCHAR(20),
	NAME	 VARCHAR(200),
	STREET	 VARCHAR(200),
	CITY	 VARCHAR(200),
	POSTAL_CODE	 VARCHAR(200),
	MATERIAL	 VARCHAR(200),
	QUANTITY	 INT,
	TMALL_PRICE	 VARCHAR(200),
	CONDITION_AMOUNT DOUBLE,
	TMALL_POINTS	 VARCHAR(200),
	POINT_AMOUT	 DOUBLE,
	TMALL_COUPON	 VARCHAR(50),
	COUPON_AMOUNT	 DOUBLE,
	POACHIVENO	 VARCHAR(200),
	INTERNALNOTE	 VARCHAR(200),
	BILLINGTEXT	 VARCHAR(200),
	TEXT1	 VARCHAR(1000),
	TEXT2	 VARCHAR(1000),
	TEXT3	 VARCHAR(1000),
	STORE_ID	 VARCHAR(100),
	TRADE_STATUS	 VARCHAR(100),
	TIDi	VARCHAR(100),
	POINT_FEE_REMARK VARCHAR(45),
	COUPON_REMARK	 VARCHAR(45),
	ADDRESS_REMARK	 VARCHAR(45),
	COUNT_REMARK	 VARCHAR(200),
	IN_FEE	 DOUBLE,
	PAYMENT	 DOUBLE,
    ALIPAY_NO VARCHAR(500),
    refund_id VARCHAR(200),
    refund_fee DOUBLE,
    condition_amount_remark VARCHAR(200)
);

/*将需要标记为组合装的订单暂存临时表*/
DROP TEMPORARY TABLE IF EXISTS tmp_sku_remark;
CREATE TEMPORARY TABLE tmp_sku_remark(
	tid VARCHAR(200),
    UNIQUE KEY (tid)
);

/*将积分记录到临时表*/
DROP TEMPORARY TABLE IF EXISTS tmp_point;
CREATE TEMPORARY TABLE tmp_point(
	id int,
	tid VARCHAR(200),
    original_point_fee double,
    quantity int,
    UNIQUE KEY (tid)
);
/********************* 创建报表临时表 end *****************************/

/********************* 构建查询语句 start *****************************/
set _v_column = ' distinct eo.id,eo.seller_memo,eo.invoice_name,eo.receiver_name,eo.item_title,eo.item_type,eo.order_pay,eo.order_payment,eo.item_payment,eo.end_time,eo.original_point_fee,eo.original_coupon,eo.salesorg,eo.distrchannel,eo.salesdoctype,eo.po_number,eo.pricingdate,eo.partnerfunctn,eo.name,eo.street,eo.city,eo.postal_code,eo.material,eo.quantity,eo.tmall_price,eo.condition_amount,eo.tmall_points,eo.point_amout,eo.tmall_coupon,eo.coupon_amount,eo.poachiveno,eo.internalnote,eo.billingtext,eo.text1,eo.text2,eo.text3,eo.store_id,eo.trade_status,eo.tid,eo.point_fee_remark,eo.coupon_remark,eo.address_remark,eo.count_remark,eo.payment,eo.alipay_no,eo.refund_id,trans.create_time,trans.in_fee,eo.customer,eo.customer,eo.condition_amount_remark';
set _v_column_l = ' id,seller_memo,invoice_name,receiver_name,item_title,item_type,order_pay,order_payment,item_payment,end_time,original_point_fee,original_coupon,salesorg,distrchannel,salesdoctype,po_number,pricingdate,partnerfunctn,name,street,city,postal_code,material,quantity,tmall_price,condition_amount,tmall_points,point_amout,tmall_coupon,coupon_amount,poachiveno,internalnote,billingtext,text1,text2,text3,store_id,trade_status,tidi,point_fee_remark,coupon_remark,address_remark,count_remark,payment,alipay_no,refund_id,create_time,in_fee,OFFICE_NAME,OFFICE_NAME1,condition_amount_remark';

set _v_where = ' where eo.trade_status in (\'TRADE_CLOSED\',\'TRADE_FINISHED\') ';/*and alipay_no in(\'2015060121001001640216833798\',\'2015080921001001350272934911\')*/
IF store_id  <> '' and  store_id is not null THEN
	set _v_where = concat(_v_where,' and eo.STORE_ID = ','\'',store_id,'\'');
END IF;
IF tid <> '' and  tid is not null THEN
	set _v_where = concat(_v_where,' and eo.TID =  ','\'',tid,'\'');
END IF;
IF pricingdate_start  <> '' and  pricingdate_start is not null THEN
	set _v_where = concat(_v_where,' and eo.PRICINGDATE >=  ','\'',pricingdate_start,'\'');
END IF;
IF pricingdate_end <> '' and  pricingdate_end is not null THEN
	set _v_where = concat(_v_where,' and eo.PRICINGDATE <=  ','\'',pricingdate_end,'\'');
END IF;
IF alipay_start <> '' and  alipay_start is not null THEN
	set _v_where = concat(_v_where,' and trans.CREATE_TIME >=  ','\'',alipay_start,'\'');
END IF;
IF alipay_end <> '' and  alipay_end is not null THEN
	set _v_where = concat(_v_where,' and trans.CREATE_TIME <=  ','\'',alipay_end,'\'');
END IF;
set _sql_base = concat( 'INSERT INTO tmp_order_report(', _v_column_l , ') select ', _v_column , ' from exp_order eo inner join alipay_trans trans on eo.alipay_no = trans.service_serial_num',_v_where);/*CREATE_TIME,IN_FEE,OFFICE_NAME,OFFICE_NAME1,*/
set @ms=_sql_base;
PREPARE s1 from @ms;/*预处理需要执行的动态SQL，其中ms是一个变量*/
EXECUTE s1;/*执行SQL语句*/
deallocate prepare s1;/*释放掉预处理段*/
/********************* 构建查询语句 end *****************************/
set _need_delete_id = '';
set _line_pay_fee = 0;
set _line_quantity = 0;
set _line_coupon = 0;
set _flag = 0;
OPEN rs_cur;
loop_label:loop
	FETCH rs_cur INTO _id,_seller_memo,_invoice_name,_receiver_name,_item_title,_item_type,_order_pay,_order_payment,_item_payment,_end_time,_original_point_fee,_original_coupon,_salesorg,_distrchannel,_salesdoctype,_po_number,_pricingdate,_partnerfunctn,_name,_street,_city,_postal_code,_material,_quantity,_tmall_price,_condition_amount,_tmall_points,_point_amout,_tmall_coupon,_coupon_amount,_poachiveno,_internalnote,_billingtext,_text1,_text2,_text3,_store_id,_trade_status,_tid,_point_fee_remark,_coupon_remark,_address_remark,_count_remark,_payment,_alipay_no,_refund_id,_create_time,_in_fee,_OFFICE_NAME,_OFFICE_NAME,_refund_fee,_condition_amount_remark;
	/*循环终止，跳出*/
    if done = 1 THEN
		leave loop_label;
	end if;
    
    /*删除临时表数据*/
    if _flag = 0 then 
		truncate table tmp_order_report;
        set _flag = 1;
    end if;
    
    
	set _is_delete_flag = '0'; /*标记此记录是否被删除*/
	set _tid_count = 0;
    
    /*注意广东 和广东深圳*/
	set _office_name = null;
	select b.code into _office_name from upd_cpd b where instr(substring_index(_street,' ',2),b.office_name) > 0 and b.office_name = '深圳';
	if _office_name is null then
		select b.code into _office_name from upd_cpd b where instr(substring_index(_street,' ',2),b.office_name) > 0 ;
    end if;
    
    
	set _total_fee = null;
	set _refund_fee = null;
    /*若未发生退款，行金额为原逻辑金额*/
    set _pay_fee = _condition_amount;
    
    /*若订单剩余金额为0，则为全退，无需在报表中显示*/
    if _item_payment = 0 and _item_type = 'item' then
		set _need_delete_id = concat(_need_delete_id,'\'',_id,'\',');
        set _pay_fee = 0;
        set _is_delete_flag = '1'; 
    end if;
    /*根据退款单号查询退款单数据*/
    if _refund_id is not null and _refund_id <> '' then 
		select payment,total_fee,refund_fee,good_status,has_good_return,status into _refund_payment,_total_fee,_refund_fee,_good_status,_has_good_return,_status from trade_refund where refund_id = _refund_id order by modified desc limit 1;
        
        /*退款单状态为SUCCESS为全退货*/
        if _status = 'SUCCESS' and _refund_payment = 0 then
			set _need_delete_id = concat(_need_delete_id,'\'',_id,'\',');
            set _pay_fee = 0;
			set _is_delete_flag = '1'; 
        /*部分退款*/    
		elseif _status = 'SUCCESS' and _refund_payment > 0 then
			
            
            /*计算积分金额*/
            if _point_fee_remark = '1' then
				set _point_price = _point_amout;
            elseif _point_fee_remark = '0' then
				set _point_price = _point_amout * _quantity * 100;
            end if;
            
            /*开票金额计算公式 = ( 剩下的payment - 优惠券金额 ) / 数量 - 积分 / 数量 / 100 
			  若( 剩下的payment - 优惠券金额 ) / 数量 除不尽，则需要标记
            */
           if mod(( _refund_payment - _coupon_price ) * 100 , _quantity) > 0  then /* 进行整数比较*/
				set _condition_amount_remark = '1';
                set _pay_fee =  _refund_payment ;
            else
				set _condition_amount_remark = '0';
                set _pay_fee = _refund_payment  / _quantity - _point_price / _quantity / 100;
            end if;
            
            set _line_quantity = _line_quantity + _quantity;
			if _coupon_remark = '0' then
				set _line_coupon = _line_coupon + _coupon_amount * _quantity;
			else
				set _line_coupon = _line_coupon + _coupon_amount;
			end if;
        else 
        	set _refund_fee = null;
            set _line_quantity = _line_quantity + _quantity;
			if _coupon_remark = '0' then
				set _line_coupon = _line_coupon + _coupon_amount * _quantity;
			else
				set _line_coupon = _line_coupon + _coupon_amount;
			end if;
        end if;
    else
    	if _item_type = 'item' then
    		set _line_quantity = _line_quantity + _quantity;	
			if _coupon_remark = '0' then
				set _line_coupon = _line_coupon + _coupon_amount * _quantity;
			else
				set _line_coupon = _line_coupon + _coupon_amount;
			end if;
        end if;
    end if;
    
    /*
    select _id,_seller_memo,_invoice_name,_receiver_name,_item_title,_item_type,_order_pay,_order_payment,_item_payment,_end_time,_original_point_fee,_original_coupon,_salesorg,_distrchannel,_salesdoctype,_po_number,_pricingdate,_partnerfunctn,_name,_street,_city,_postal_code,_material,_quantity,_tmall_price,
			_pay_fee,_tmall_points,_point_amout,_tmall_coupon,_coupon_amount,_poachiveno,_internalnote,_billingtext,_text1,_text2,_text3,_store_id,_trade_status,_tid,_point_fee_remark,_coupon_remark,_address_remark,_count_remark,_payment,_alipay_no,_refund_id,_create_time,_in_fee,_office_name,_office_name ,_refund_fee;
    */
    /*计算order行金额*/
    if _item_type = 'item' then
		set _line_pay_fee = _line_pay_fee + _pay_fee;	
        
        if _address_remark = '1' then 
			insert into tmp_sku_remark values (_tid);
        end if;
        
        select count(1) into _tid_count from tmp_point where tid = _tid;
        if _is_delete_flag = '0' and _tid_count = 0 then /*订单项不被移除，且从未出现过此订单的记录，则视为第一行，重新计算积分*/
			insert into tmp_point values(_id,_tid,_original_point_fee,_quantity);
            
            set _point_amout = _original_point_fee / _quantity;
            if mod(_original_point_fee * 100 , _quantity) > 0  then
				set _point_fee_remark = '1';
            end if;
		else
			set _point_amout = 0;
            set _point_fee_remark = '1';
		end if;
	elseif _item_type = 'order' then	
		set _pay_fee = _line_pay_fee;
		set _line_pay_fee = 0;
        set _quantity = _line_quantity; /*给汇总行数量重新赋值*/
        /*set _original_coupon = _line_coupon;给汇总行coupon重新赋值*/
        set _line_quantity = 0;
        set _line_coupon = 0;
        
        
	end if;
    
    /*插入数据到临时表*/
    INSERT INTO tmp_order_report(id,seller_memo,invoice_name,receiver_name,item_title,item_type,order_pay,order_payment,item_payment,end_time,original_point_fee,original_coupon,
		SALESORG,DISTRCHANNEL,SALESDOCTYPE,PO_NUMBER,PRICINGDATE,PARTNERFUNCTN,NAME,STREET,CITY,POSTAL_CODE,MATERIAL,QUANTITY,TMALL_PRICE,
        CONDITION_AMOUNT,TMALL_POINTS,POINT_AMOUT,
		TMALL_COUPON,COUPON_AMOUNT,POACHIVENO,INTERNALNOTE,BILLINGTEXT,TEXT1,TEXT2,TEXT3,STORE_ID,TRADE_STATUS,TIDi,POINT_FEE_REMARK,COUPON_REMARK,ADDRESS_REMARK,COUNT_REMARK,PAYMENT,
		ALIPAY_NO,CREATE_TIME,IN_FEE,OFFICE_NAME,OFFICE_NAME1,refund_fee,refund_id
		) VALUES (_id,_seller_memo,_invoice_name,_receiver_name,_item_title,_item_type,_order_pay,_order_payment,_item_payment,_end_time,_original_point_fee,round(_original_coupon,2),
		_salesorg,_distrchannel,_salesdoctype,_po_number,_pricingdate,_partnerfunctn,_name,_street,_city,_postal_code,_material,_quantity,_tmall_price,
        round(_pay_fee,5),_tmall_points,_point_amout,
		_tmall_coupon,_coupon_amount,_poachiveno,_internalnote,_billingtext,_text1,_text2,_text3,_store_id,_trade_status,_tid,_point_fee_remark,_coupon_remark,_address_remark,_count_remark,_payment,
		_alipay_no,_create_time,_in_fee,_office_name,_office_name,_refund_fee,_refund_id);
    
    commit;
    
    /*防止_need_delete_id 超长，分批删除*/
	if length(_need_delete_id) > 900 then 
		set _need_delete_id = concat('delete from tmp_order_report where id in (',substring(_need_delete_id,1,length(_need_delete_id)-1),')');
		set @ms=_need_delete_id;
		PREPARE s1 from @ms;
		EXECUTE s1;
		deallocate prepare s1;
        
		set _need_delete_id = '';
    end if;
    
    set done = 0;
    
end loop loop_label;
CLOSE rs_cur;

if length(_need_delete_id) > 0 then 
	set _need_delete_id = concat('delete from tmp_order_report where id in (',substring(_need_delete_id,1,length(_need_delete_id)-1),')');
	set @ms=_need_delete_id;
	PREPARE s1 from @ms;
	EXECUTE s1;
	deallocate prepare s1;
end if;

/*若item的payment都被删掉后，order也删除*/
DROP TEMPORARY TABLE IF EXISTS tmp1;
create TEMPORARY TABLE tmp1 as select id from tmp_order_report group by tidi having count(tidi) = 1;
delete from tmp_order_report where id in (select id from tmp1);
commit;

/*更新商品积分标记*/
update tmp_order_report set address_remark = '1' where tid in (select tid from tmp_sku_remark);

set _sql_base = 'SELECT * FROM tmp_order_report ';
set @ms=_sql_base;
PREPARE s1 from @ms;
EXECUTE s1;
deallocate prepare s1;

END