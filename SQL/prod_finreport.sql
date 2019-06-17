CREATE DEFINER=`root`@`localhost` PROCEDURE `prod_finreport`(IN store_id VARCHAR(50),tid VARCHAR(50), pricingdate_start VARCHAR(50) , pricingdate_end VARCHAR(50), alipay_start VARCHAR(50) , alipay_end VARCHAR(50))
BEGIN


DECLARE done INT DEFAULT 0;
/*定义动态查询SQL，并初始化*/
DECLARE _v_column VARCHAR(1000);
DECLARE _v_column_l VARCHAR(1000);

DECLARE v_sqlcounts VARCHAR(500);

DECLARE _v_where VARCHAR(1000);
DECLARE _sql_base VARCHAR(5000);

declare _id int;
declare _bldat timestamp;
declare _budat timestamp;
declare _bkpf varchar(200);
declare _monat varchar(200);
declare _bukrs varchar(200);
declare _orderStatus varchar(200);
declare _waers varchar(200);
declare _newbs varchar(200);
declare _newko varchar(200);
declare _wrbtr double;
declare _zuonr varchar(200);
declare _sgtxt varchar(200);
declare _newbs1 varchar(200);
declare _newko1 varchar(200);
declare _name1 varchar(200);
declare _ort01 varchar(200);
declare _wrbtr1 double;
declare _zuonr1 varchar(200);
declare _sgtxt1 varchar(200);
declare _newbs2 varchar(200);
declare _wrbtr2 double;
declare _zuonr2 varchar(200);
declare _sgtxt2 varchar(200);
declare _createTime timestamp;
declare _serviceSerialNum varchar(200);
declare _invoiceTitle varchar(200);
declare _inFee double;
declare _pointFee double;
declare _storeId varchar(200);

declare _refund_payment double;
declare _refund_fee double;
declare _total_fee double;
declare _flag int;
declare _count int;

DECLARE rs_cur CURSOR FOR select id,bldat,budat,bkpf,monat,bukrs,orderStatus,waers,newbs,newko,wrbtr,zuonr,sgtxt,newbs1,newko1,name1,ort01,wrbtr1,zuonr1,sgtxt1,newbs2,wrbtr2,zuonr2,sgtxt2 ,createTime,serviceSerialNum,invoiceTitle,inFee,pointFee,storeId from tmp_finance_report ;
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
/********************* 创建报表临时表 start *****************************/
DROP TEMPORARY TABLE IF EXISTS tmp_finance_report;
CREATE TEMPORARY TABLE tmp_finance_report (
	id    int,
	bldat timestamp,
	budat timestamp,
	bkpf  VARCHAR(200),
	monat VARCHAR(200),
	bukrs VARCHAR(200),
	orderStatus	 VARCHAR(200),
	waers VARCHAR(200),
	newbs VARCHAR(200),
	newko VARCHAR(200),
    wrbtr double,
	zuonr VARCHAR(200),
    sgtxt varchar(200),
    newbs1 varchar(200),
    newko1 varchar(200),
    name1 varchar(200),
    ort01 varchar(200),
    wrbtr1 double,
    zuonr1 varchar(200),
    sgtxt1 varchar(200),
    newbs2 varchar(200),
    wrbtr2 double,
    zuonr2 varchar(200),
    sgtxt2 varchar(200),
    createTime timestamp,
    serviceSerialNum varchar(200),
    invoiceTitle varchar(200) ,
    inFee double,
    pointFee double,
    storeId varchar(200)
);
/********************* 创建报表临时表 end *****************************/

/********************* 构建查询语句 start *****************************/
set _v_column = ' ef.id,ef.bldat,ef.budat,ef.bkpf,ef.monat,ef.bukrs,ef.order_Status,ef.waers,ef.newbs,ef.newko,ef.wrbtr,ef.zuonr,ef.sgtxt,ef.newbs1,ef.newko1,ef.name1,ef.ort01,ef.wrbtr1,ef.zuonr1,ef.sgtxt1,ef.newbs2,ef.wrbtr2,ef.zuonr2,ef.sgtxt2,ali.create_Time,ali.service_Serial_Num,ef.invoice_Title,ali.in_Fee,ef.point_Fee,ef.store_Id';
set _v_column_l = ' id,bldat,budat,bkpf,monat,bukrs,orderStatus,waers,newbs,newko,wrbtr,zuonr,sgtxt,newbs1,newko1,name1,ort01,wrbtr1,zuonr1,sgtxt1,newbs2,wrbtr2,zuonr2,sgtxt2 ,createTime,serviceSerialNum,invoiceTitle,inFee,pointFee,storeId';

set _v_where = ' where 1=1 ';
IF store_id  <> '' and  store_id is not null THEN
	set _v_where = concat(_v_where,' and ef.store_Id = ','\'',store_id,'\'');
END IF;
IF tid <> '' and  tid is not null THEN
	set _v_where = concat(_v_where,' and ef.tid =  ','\'',tid,'\'');
END IF;
IF pricingdate_start  <> '' and  pricingdate_start is not null THEN
	set _v_where = concat(_v_where,' and ef.pricingDate >= ','\'',pricingdate_start,'\'');
END IF;
IF pricingdate_end <> '' and  pricingdate_end is not null THEN
	set _v_where = concat(_v_where,' and ef.pricingDate <= ','\'',pricingdate_end,'\'');
END IF;
IF alipay_start <> '' and  alipay_start is not null THEN
	set _v_where = concat(_v_where,' and ali.create_time >= ','\'',alipay_start,'\'');
END IF;
IF alipay_end <> '' and  alipay_end is not null THEN
	set _v_where = concat(_v_where,' and ali.create_time <= ','\'',alipay_end,'\'');
END IF;
set _sql_base = concat( 'INSERT INTO tmp_finance_report(', _v_column_l , ') select ', _v_column , ' from exp_Finacial ef inner join alipay_trans ali on ef.alipay_no = ali.service_serial_num ',_v_where);/*CREATE_TIME,IN_FEE,OFFICE_NAME,OFFICE_NAME1,*/
set @ms=_sql_base;
PREPARE s1 from @ms;/*预处理需要执行的动态SQL，其中ms是一个变量*/
EXECUTE s1;/*执行SQL语句*/
deallocate prepare s1;/*释放掉预处理段*/
/********************* 构建查询语句 end *****************************/

set _flag = 0;
set _refund_fee = 0;
set _count = 0;

OPEN rs_cur;
loop_label:loop
	FETCH rs_cur INTO _id,_bldat,_budat,_bkpf,_monat,_bukrs,_orderStatus,_waers,_newbs,_newko,_wrbtr,_zuonr,_sgtxt,_newbs1,_newko1,_name1,_ort01,_wrbtr1,_zuonr1,_sgtxt1,_newbs2,_wrbtr2,_zuonr2,_sgtxt2,_createTime,_serviceSerialNum,_invoiceTitle,_inFee,_pointFee,_storeId;
    /*循环终止，跳出*/
    if done = 1 THEN
		leave loop_label;
	end if;
    
     /*删除临时表数据*/
    if _flag = 0 then 
		truncate table tmp_finance_report;
        set _flag = 1;
    end if;

	/*设置SGTXT的值*/
    set _name1 = left(_invoiceTitle,4);
	set _sgtxt = concat(if(_createTime is not null,date_format(_createTime,'%Y%m%d'),''),'收tmall销售款',if(_ort01 is not null,_ort01,''),_name1);    
    set _sgtxt1 = concat(_sgtxt,_name1);
    if _storeId = '22511920' then
        /*set _sgtxt2 = concat(_sgtxt,_invoiceTitle,'/消费积分博世');*/
        set _sgtxt2 = concat(_sgtxt1,'/消费积分博世');
	else
		/*set _sgtxt2 = concat(_sgtxt,_invoiceTitle,'/消费积分');*/
        set _sgtxt2 = concat(_sgtxt1,'/消费积分博世');
    end if;
    
    set _refund_fee = 0;
    set _refund_payment = 0;
    set _count = 0;
    set v_sqlcounts = concat('select count(tid) into @count from trade_refund where tid = \'',_ort01,'\' and status = \'','SUCCESS','\''); 
	set @sqlcounts = v_sqlcounts;  
	prepare stmt from @sqlcounts;  
	execute stmt;  
	deallocate prepare stmt;
    set _count = @count;
    if _count is not null and _count !=0 then 
		set v_sqlcounts = concat('select sum(payment) as payment,sum(total_fee) as total_fee,sum(refund_fee) as refund_fee into @refund_payment,@total_fee,@refund_fee from (select distinct payment,total_fee,refund_fee,tid,status from trade_refund where tid = \'',_ort01,'\' and status = \'','SUCCESS','\' ) a  group by a.tid');  
		set @sqlcounts = v_sqlcounts;  
		prepare stmt from @sqlcounts;  
		execute stmt;  
		deallocate prepare stmt;   
		set _refund_fee = @refund_fee;
		set _refund_payment = @refund_payment;
		set _total_fee = @total_fee;

		/*发生退款*/
		if _refund_fee is not null and _refund_fee !=0 then 
			if _refund_fee = _infee then 
				set _wrbtr1 = 0;
			else 
				set _wrbtr1 = _wrbtr1 - _refund_fee;
			end if;
		end if;  
    end if;
   
    /*插入数据到临时表*/
    INSERT INTO tmp_finance_report(
		id,bldat,budat,bkpf,monat,bukrs,orderStatus,waers,newbs,newko,wrbtr,zuonr,sgtxt,newbs1,newko1,name1,ort01,wrbtr1,zuonr1,sgtxt1,newbs2,wrbtr2,zuonr2,sgtxt2 ,createTime,serviceSerialNum,invoiceTitle,inFee,pointFee,storeId
	) VALUES (
		_id,_bldat,_budat,_bkpf,_monat,_bukrs,_orderStatus,_waers,_newbs,_newko,_wrbtr,_zuonr,_sgtxt,_newbs1,_newko1,_name1,_ort01,round(_wrbtr1,2),_zuonr1,_sgtxt1,_newbs2,_wrbtr2,_zuonr2,_sgtxt2,_createTime,_serviceSerialNum,_invoiceTitle,_inFee,_pointFee,_storeId);
    
    commit;
    
    set done = 0;
    

end loop loop_label;
CLOSE rs_cur;

set _sql_base = 'SELECT * FROM tmp_finance_report ';
set @ms=_sql_base;
PREPARE s1 from @ms;
EXECUTE s1;
deallocate prepare s1;

END