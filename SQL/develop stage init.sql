truncate table role_resource;
truncate table user_role;

SET FOREIGN_KEY_CHECKS=0;

truncate table user;
truncate table role;
truncate table resource;

insert into user values(1,'admin','admin@1234.com','25d55ad283aa400af464c76d713c07ad',0,1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','admin',null);
insert into role values(1,'ROLE_ANONYMOUS','匿名','匿名',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system',0);
insert into role values(2,'ROLE_SUPER','超级管理员','超级管理员',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system',0);
insert into role values(3,'ROLE_DOWNLOAD_REPORT','报表下载员','报表下载员',1,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system',0);

insert into user_role values(1,1,2,CURRENT_TIMESTAMP,'system',CURRENT_TIMESTAMP,'system',0);

insert into resource values(1,'登陆','/login',1,'登陆',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',0,'all',0);

insert into resource values(2,'上传管理','#',1,'上传管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',0,'uploadmanage',0);
insert into resource values(3,'上传alipay数据','/upload/alipay',1,'上传alipay数据',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',2,'uploadalipay',0);
insert into resource values(4,'上传sku数据','/upload/sku',1,'上传sku数据',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',2,'uplocadsku',0);
insert into resource values(5,'上传物料数据','/upload/gift',1,'上传物料数据',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',2,'uploadgift',0);
insert into resource values(6,'上传CPD数据','/upload/cpd',1,'上传CPD数据',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',2,'uploadcpd',0);
insert into resource values(7,'上传发货单数据','/upload/bill',1,'上传发货单数据',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',2,'uploadbill',0);
insert into resource values(8,'上传发票数据','/upload/invoice',1,'上传发票数据',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',2,'uploadinvoice',0);

insert into resource values(9,'报表下载','#',1,'报表下载',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,null,null,'sync',0,'download',0);
insert into resource values(10,'订单报表','/download/order',1,'订单报表',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',9,'downloadorder',0);
insert into resource values(11,'发货单报表','/download/stkout',1,'发货单报表',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',9,'downloaddelivery',0);
insert into resource values(12,'财务报表','/download/finance',1,'财务报表',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',9,'downloadfinacial',0);
insert into resource values(13,'区域销售报表','/download/areaSale',1,'区域销售报表',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',9,'downloadarea',0);
insert into resource values(14,'财务反查报表','/download/restFinance',1,'财务反查报表',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',9,'downloadresetFin',0);
insert into resource values(15,'销售汇总报表','/download/saleStructure',1,'销售汇总报表',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',9,'downloadall',0);

-- insert into resource values(16,'订单','#',1,'订单',CURRENT_TIMESTAMP,null,null,null,'sync',0,'order',0);
-- insert into resource values(17,'订单管理','/order/index',1,'订单管理',CURRENT_TIMESTAMP,null,null,null,'sync',16,'ordermanage',0);

insert into resource values(16,'SKU&物料管理','#',1,'sku&物料管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',0,'sku&gift',0);
insert into resource values(17,'SKU管理','/sku/index',1,'sku管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',16,'areaSale',0);
insert into resource values(18,'物料管理','/matrn/index',1,'物料管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',16,'giftmanage',0);

insert into resource values(19,'销售报表','#',1,'销售报表',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',0,'sale',0);
insert into resource values(20,'区域销售查询','/sale/areaSale',1,'区域销售查询',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',19,'areasalequery',0);
insert into resource values(21,'销售汇总查询','/sale/saleStructure',1,'销售汇总查询',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',19,'saleStructurequery',0);

insert into resource values(22,'系统管理','#',1,'系统管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',0,'system',0);
insert into resource values(23,'用户管理','/user/index',1,'用户管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',22,'usermanage',0);
insert into resource values(24,'用户角色管理','/userRole/index',1,'用户角色管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',22,'userRoleManage',0);
insert into resource values(25,'用户权限管理','/userRoleResource/index',1,'用户权限管理',CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,'system','system','sync',22,'useroperationmanage',0);

insert into role_resource values(1,1,1,1);
insert into role_resource values(2,2,1,1);
insert into role_resource values(3,2,2,1);
insert into role_resource values(4,2,3,1);
insert into role_resource values(5,2,4,1);
insert into role_resource values(6,2,5,1);
insert into role_resource values(7,2,6,1);
insert into role_resource values(8,2,7,1);
insert into role_resource values(9,2,8,1);
insert into role_resource values(10,2,9,1);
insert into role_resource values(11,2,10,1);
insert into role_resource values(12,2,11,1);
insert into role_resource values(13,2,12,1);
insert into role_resource values(14,2,13,1);
insert into role_resource values(15,2,14,1);
insert into role_resource values(16,2,15,1);
insert into role_resource values(17,2,16,1);
insert into role_resource values(18,2,17,1);
insert into role_resource values(19,2,18,1);
insert into role_resource values(20,2,19,1);
insert into role_resource values(21,2,20,1);
insert into role_resource values(22,2,21,1);
insert into role_resource values(23,2,22,1);
insert into role_resource values(24,2,23,1);
insert into role_resource values(25,2,24,1);
insert into role_resource values(26,2,25,1);
insert into role_resource values(27,3,9,1);
insert into role_resource values(28,3,10,1);
insert into role_resource values(29,3,11,1);
insert into role_resource values(30,3,12,1);
insert into role_resource values(31,3,13,1);
insert into role_resource values(32,3,14,1);
insert into role_resource values(33,3,15,1);


-- *****************************************************************************************************************************


insert into area values(100001,'浙江','','PZJ',0,0);
insert into area values(100002,'北京','','PBJ',0,0);
insert into area values(100003,'广东','','PGD',0,0);
insert into area values(100004,'湖北','','PHB',0,0);
insert into area values(100005,'四川','','PSC',0,0);


insert into area values(100006,'成都','QDHEWL-0011','CD',100005,1);
insert into area values(100007,'西安','QDHEWL-0001','XA',100005,0);
insert into area values(100008,'兰州','QDHEWL-0029','LZ',100005,0);
insert into area values(100009,'昆明','QDHEWL-0023','KM',100005,0);
insert into area values(100010,'贵阳','QDHEWL-0024','GY',100005,0);
insert into area values(100011,'重庆','QDHEWL-0022','CQ',100005,0);

insert into area values(100012,'武汉','QDHEWL-0036','WH',100004,1);
insert into area values(100013,'南昌','QDHEWL-0020','NC',100004,0);
insert into area values(100014,'郑州','QDHEWL-0081','ZZ',100004,0);
insert into area values(100015,'长沙','QDHEWL-0010','CS',100004,0);

insert into area values(100016,'北京','QDHEWL-0007','BJ',100002,1);
insert into area values(100017,'太原','QDHEWL-0014','TY',100002,0);
insert into area values(100018,'济南','QDHEWL-0080','JN',100002,0);
insert into area values(100019,'石家庄','QDHEWL-0016','SJZ',100002,0);
insert into area values(100020,'沈阳','QDHEWL-0008','SY',100002,0);
insert into area values(100021,'长春','QDHEWL-0027','CC',100002,0);
insert into area values(100022,'哈尔滨','QDHEWL-0025','HEB',100002,0);

insert into area values(100023,'杭州','QDHEWL-0002','HZ',100001,1);
insert into area values(100024,'合肥','QDHEWL-0003','HF',100001,0);
insert into area values(100025,'上海','QDHEWL-0082','SH',100001,0);
insert into area values(100026,'南京','QDHEWL-0004','NJ',100001,0);
insert into area values(100027,'无锡','QDHEWL-0012','WX',100001,0);
insert into area values(100028,'宁波','QDHEWL-0019','NB',100001,0);

insert into area values(100029,'佛山','QDHEWL-0009','FS',100003,1);
insert into area values(100030,'南宁','QDHEWL-0021','NN',100003,0);
insert into area values(100031,'福州','QDHEWL-0006','FZ',100003,0);
insert into area values(100032,'深圳','QDHEWL-0035','SZ',100003,0);



insert into store values(100001,'VISUAL_STORE_525635','QDHEWL-0001');
insert into store values(100002,'VISUAL_STORE_525624','QDHEWL-0002');
insert into store values(100003,'VISUAL_STORE_525644','QDHEWL-0003');
insert into store values(100004,'VISUAL_STORE_525640','QDHEWL-0004');
insert into store values(100005,'VISUAL_STORE_525637','QDHEWL-0006');
insert into store values(100006,'VISUAL_STORE_525621','QDHEWL-0007');
insert into store values(100007,'VISUAL_STORE_525628','QDHEWL-0008');
insert into store values(100008,'VISUAL_STORE_525623','QDHEWL-0009');
insert into store values(100009,'VISUAL_STORE_525646','QDHEWL-0010');
insert into store values(100010,'VISUAL_STORE_525622','QDHEWL-0011');
insert into store values(100011,'VISUAL_STORE_525643','QDHEWL-0012');
insert into store values(100012,'VISUAL_STORE_525630','QDHEWL-0014');
insert into store values(100013,'VISUAL_STORE_525629','QDHEWL-0016');
insert into store values(100014,'VISUAL_STORE_525641','QDHEWL-0019');
insert into store values(100015,'VISUAL_STORE_525645','QDHEWL-0020');
insert into store values(100016,'VISUAL_STORE_525638','QDHEWL-0021');
insert into store values(100017,'VISUAL_STORE_525636','QDHEWL-0022');
insert into store values(100018,'VISUAL_STORE_525633','QDHEWL-0023');
insert into store values(100019,'VISUAL_STORE_525632','QDHEWL-0024');
insert into store values(100020,'VISUAL_STORE_525626','QDHEWL-0025');
insert into store values(100021,'VISUAL_STORE_525631','QDHEWL-0027');
insert into store values(100022,'VISUAL_STORE_525634','QDHEWL-0029');
insert into store values(100023,'VISUAL_STORE_525639','QDHEWL-0035');
insert into store values(100024,'VISUAL_STORE_525625','QDHEWL-0036');
insert into store values(100025,'VISUAL_STORE_525627','QDHEWL-0080');
insert into store values(100026,'VISUAL_STORE_525647','QDHEWL-0081');
insert into store values(100027,'VISUAL_STORE_525642','QDHEWL-0082');


insert into store values(100028,'ONLOAD_STORE_141001','QDHEWL-0019');
insert into store values(100029,'ONLOAD_STORE_141002','QDHEWL-0082');
insert into store values(100030,'ONLOAD_STORE_141003','QDHEWL-0035');
insert into store values(100031,'ONLOAD_STORE_155575','QDHEWL-0006');
insert into store values(100032,'ONLOAD_STORE_155576','QDHEWL-0008');
insert into store values(100033,'ONLOAD_STORE_155596','QDHEWL-0080');
insert into store values(100034,'ONLOAD_STORE_155709','QDHEWL-0004');
insert into store values(100035,'ONLOAD_STORE_155777','QDHEWL-0012');
insert into store values(100036,'ONLOAD_STORE_155778','QDHEWL-0021');
insert into store values(100037,'ONLOAD_STORE_155779','QDHEWL-0014');
insert into store values(100038,'ONLOAD_STORE_155780','QDHEWL-0016');
insert into store values(100039,'ONLOAD_STORE_155757','QDHEWL-0027');
insert into store values(100040,'ONLOAD_STORE_155758','QDHEWL-0025');
insert into store values(100041,'ONLOAD_STORE_156091','QDHEWL-0003');
insert into store values(100042,'ONLOAD_STORE_608361','QDHEWL-0001');
insert into store values(100043,'ONLOAD_STORE_608362','QDHEWL-0029');
insert into store values(100044,'ONLOAD_STORE_608363','QDHEWL-0023');
insert into store values(100045,'ONLOAD_STORE_608364','QDHEWL-0024');
insert into store values(100046,'ONLOAD_STORE_608365','QDHEWL-0022');
insert into store values(100047,'ONLOAD_STORE_608366','QDHEWL-0020');
insert into store values(100048,'ONLOAD_STORE_608367','QDHEWL-0081');
insert into store values(100049,'ONLOAD_STORE_608368','QDHEWL-0010');

insert into store values(100050,'QDHEWL-0011','QDHEWL-0011');
insert into store values(100051,'QDHEWL-0001','QDHEWL-0001');
insert into store values(100052,'QDHEWL-0029','QDHEWL-0029');
insert into store values(100053,'QDHEWL-0023','QDHEWL-0023');
insert into store values(100054,'QDHEWL-0024','QDHEWL-0024');
insert into store values(100055,'QDHEWL-0022','QDHEWL-0022');
insert into store values(100056,'QDHEWL-0036','QDHEWL-0036');
insert into store values(100057,'QDHEWL-0020','QDHEWL-0020');
insert into store values(100058,'QDHEWL-0081','QDHEWL-0081');
insert into store values(100059,'QDHEWL-0010','QDHEWL-0010');
insert into store values(100060,'QDHEWL-0007','QDHEWL-0007');
insert into store values(100061,'QDHEWL-0014','QDHEWL-0014');
insert into store values(100062,'QDHEWL-0080','QDHEWL-0080');
insert into store values(100063,'QDHEWL-0016','QDHEWL-0016');
insert into store values(100064,'QDHEWL-0008','QDHEWL-0008');
insert into store values(100065,'QDHEWL-0027','QDHEWL-0027');
insert into store values(100066,'QDHEWL-0025','QDHEWL-0025');
insert into store values(100067,'QDHEWL-0002','QDHEWL-0002');
insert into store values(100068,'QDHEWL-0003','QDHEWL-0003');
insert into store values(100069,'QDHEWL-0082','QDHEWL-0082');
insert into store values(100070,'QDHEWL-0004','QDHEWL-0004');
insert into store values(100072,'QDHEWL-0012','QDHEWL-0012');
insert into store values(100073,'QDHEWL-0019','QDHEWL-0019');
insert into store values(100074,'QDHEWL-0009','QDHEWL-0009');
insert into store values(100075,'QDHEWL-0021','QDHEWL-0021');
insert into store values(100076,'QDHEWL-0006','QDHEWL-0006');
insert into store values(100077,'QDHEWL-0035','QDHEWL-0035');


insert into TAOBAO_STORE values(100001,'22511900','西门子家电官方旗舰店','SIEMENS','20887011960011000156');
insert into TAOBAO_STORE values(100002,'22511920','博世家电官方旗舰店','BOSCH','20882116502245910156');
