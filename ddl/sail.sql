-- #647
alter table T_CENTER_SAILCONF add column iprice double default 0;

-- #691
alter table t_center_frdb add column description varchar(500) default '';

-- #770
ALTER TABLE t_center_out_import ADD COLUMN (gfmc VARCHAR(64),gfsh VARCHAR(64), gfyh VARCHAR(64),gfdz VARCHAR(64));


-- #779
alter table t_center_olbase add column settleprice double(10,2) default 0;

-- #798
create trigger trigger_tw_outimport
  after INSERT
  on t_center_twout_import
  for each row
  BEGIN
INSERT INTO uportal2.t_center_twout_import(id, fullId,batchId,type,customerId,branchName,secondBranch,
comunicationBranch,comunicatonBranchName,productId,productCode,productName,amount,price,style,value,midValue,
arriveDate,storageType,citicNo,invoiceNature,invoiceHead,invoiceCondition,bindNo,invoiceType,invoiceName,invoiceMoney,provinceId,cityId,address,receiver,handPhone,status,OANo,reason,logTime,firstName,citicOrderDate,transport1,
itype,outType,depotId,depotpartId,stafferId,description,shipping,transport2,expressPay,transportPay,preUse,reday,presentFlag,
ibMoney,motivationMoney,customerName,nbyhNo,result,importFromMail,lhwd,telephone,direct,channel,cash,
grossProfit,ibMoney2,motivationMoney2,delivery,posPayer,recommendation,platformFee,productImportId,ykibMoney,
cash2,virtualStatus,gfmc,gfsh,gfyh,gfdz,refTnFullId)
  values(NEW.id, NEW.fullId,NEW.batchId,NEW.type,NEW.customerId,NEW.branchName,NEW.secondBranch,
  NEW.comunicationBranch,NEW.comunicatonBranchName,NEW.productId,NEW.productCode,NEW.productName,NEW.amount,NEW.price,NEW.style,NEW.value,NEW.midValue,
  NEW.arriveDate,NEW.storageType,NEW.citicNo,NEW.invoiceNature,NEW.invoiceHead,NEW.invoiceCondition,NEW.bindNo,NEW.invoiceType
,NEW.invoiceName,NEW.invoiceMoney,NEW.provinceId,NEW.cityId,NEW.address,NEW.receiver,NEW.handPhone,NEW.status,NEW.OANo,NEW.reason,NEW.logTime,
 NEW.firstName,NEW.citicOrderDate,NEW.transport1,
NEW.itype,NEW.outType,NEW.depotId,NEW.depotpartId,NEW.stafferId,NEW.description,NEW.shipping,NEW.transport2,NEW.expressPay,NEW.transportPay,NEW.preUse,NEW.reday,NEW.presentFlag,
NEW.ibMoney,NEW.motivationMoney,NEW.customerName,NEW.nbyhNo,NEW.result,NEW.importFromMail,NEW.lhwd,NEW.telephone,NEW.direct,NEW.channel,NEW.cash,
NEW.grossProfit,NEW.ibMoney2,NEW.motivationMoney2,NEW.delivery,NEW.posPayer,NEW.recommendation,NEW.platformFee,NEW.productImportId,NEW.ykibMoney,
NEW.cash2,NEW.virtualStatus,NEW.gfmc,NEW.gfsh,NEW.gfyh,NEW.gfdz,NEW.refTnFullId);
END;

-- #818
alter table t_center_product add column kpslid varchar(19) default '';

-- #854
create table uportal.t_center_invoice_kp
(
ID int auto_increment
primary key,
name varchar(255) null,
kpslid int(20) null
) DEFAULT CHARSET=utf8
;

-- #867
alter table t_center_olout add column virtualStatus varchar(5) default null;

-- #925
alter table t_center_base add column sn varchar(64) default '';
alter table t_center_out_import add column sn varchar(64) default '';
alter table T_CENTER_STORAGERALATION add column sn varchar(64) default '';
alter table T_CENTER_COMPOSE add column sn varchar(64) default '';
alter table T_CENTER_DECOMPOSE add column sn varchar(64) default '';
alter table T_CENTER_COMPOSE_ITEM add column sn varchar(64) default '';
alter table T_CENTER_STORAGELOG add column sn varchar(64) default '';


-- #930
create table T_CENTER_PREPACKAGE
(
ID int auto_increment
primary key,
type int(8) default 0,
citicNo varchar(64) null,
transport int(8) default 0,
transportNo varchar(64) null,
status int(8) default 0,
UNIQUE KEY `IDX_CITIC` (`citicNo`)
) DEFAULT CHARSET=utf8
;
alter table t_center_package add column type int(8) default 0;

-- #931
INSERT INTO t_center_schedule_job (id, createTime, updateTime, jobName, jobGroup, jobStatus, cronExpression, description, beanClass, isConcurrent, springId, methodName, type) VALUES (568, sysdate(), sysdate(), 'statsBlackOutJob', 'Default', '0', '0 0/1 * * * ?', '黑名单统计', 'com.china.center.oa.commission.manager.impl.BlackManagerImpl', '0', 'blackManager', 'statsBlackOutJob', 0);

-- #950
alter table T_CENTER_VS_GIFT add column mzje decimal(10,2) default 0,add column giftProductId4 varchar(32) default null,add column amount4 int(11) default 0;

alter table t_center_out_import add column price2 double(10,2) default 0;
alter table t_center_base add column price2 double(10,2) default 0;
alter table T_CENTER_OLBASE add column price2 double(10,2) default 0;

alter table T_CENTER_TCPIBREPORT_ITEM add column baseId varchar(64) default '';