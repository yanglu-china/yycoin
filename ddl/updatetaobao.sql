INSERT INTO t_center_schedule_job VALUES (562,SYSDATE(),NULL,'sendTaobaoJob','Default','0','0 1/10 * * * ?','淘宝平台自动更新物流订单号','com.china.center.oa.finance.manager.impl.PackageManagerImpl',0,'packageManager','queryPackageinfo2taobao',0);



ALTER TABLE t_center_package ADD  taobaoflag CHAR(1) NOT NULL DEFAULT 0;

UPDATE t_center_package SET taobaoflag=1;


UPDATE t_center_express SET tbcode='SF' WHERE id=1;
UPDATE t_center_express SET tbcode='FEDEX' WHERE id=2;
UPDATE t_center_express SET tbcode='EMS' WHERE id=4;
UPDATE t_center_express SET tbcode='FEDEX' WHERE id=5;
UPDATE t_center_express SET tbcode='DBKD' WHERE id=6;
UPDATE t_center_express SET tbcode='YUNDA' WHERE id=10;
UPDATE t_center_express SET tbcode='STO' WHERE id=11;
UPDATE t_center_express SET tbcode='EYB' WHERE id=12;
UPDATE t_center_express SET tbcode='DBL' WHERE id=100;



CREATE TABLE `t_center_taobao_token` (
  `token` varchar(128) DEFAULT NULL,
  `createtime` varchar(48) DEFAULT NULL
);

insert  into `t_center_taobao_token`(`token`,`createtime`) values 
('6100d2077352c378485f7704783319c7b738e5390fe127d2835588043','2019-06-03 12:59:43');