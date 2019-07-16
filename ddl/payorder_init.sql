CREATE TABLE t_center_paylistlog (
  id VARCHAR (48) COMMENT '流水号',
  Outid VARCHAR (48) COMMENT '单据号',
  TYPE VARCHAR (8) COMMENT '单据类型',
  Bankname VARCHAR (24) COMMENT '收款银行',
  username VARCHAR (128) COMMENT '收款户名',
  bankno VARCHAR (128) COMMENT '收款账号',
  Money NUMERIC (12, 2) COMMENT '收款金额',
  province VARCHAR(48) COMMENT '收款账户所在省',
  city VARCHAR(48) COMMENT '收款账户所在市',
  description VARCHAR (256) COMMENT '付款备注',
  outidtime VARCHAR (24) COMMENT '单据日期',
  STATUS VARCHAR (2) COMMENT '付款状态',
  outbillid VARCHAR (128) COMMENT '付款单号',
  operator VARCHAR (32) COMMENT '付款操作人',
  paytime VARCHAR (24) COMMENT '付款时间',
  Payaccount VARCHAR (48) COMMENT '付款账户',
  Paybank VARCHAR (128) COMMENT '付款银行',	
  bankstatus VARCHAR (8) COMMENT '银行确认状态',
  bankpaytime VARCHAR (24) COMMENT '银行确认时间'
);

//菜单
INSERT INTO t_center_oamenuitem VALUES (1660,'集中付款','../payorder/querypayorder.jsp',16,1,1660,99,'资金平台集中付款');
//权限
INSERT INTO t_center_auth VALUES (1660,16,'集中支付',0,1,1);



INSERT INTO t_center_schedule_job VALUES (563,SYSDATE(),NULL,'queryPayOrderStatus','Default','0','0 1/10 * * * ?',
'宁波银行查询付款状态','com.china.center.oa.job.manager.impl.NbBankPayOrderQueryImpl',0,'nbBankPayOrderQueryJobManager','run',0);


ALTER TABLE T_CENTER_TCPEXPENSE ADD COLUMN (bankprovince VARCHAR(64),bankcity VARCHAR(64));

ALTER TABLE T_CENTER_TRAVELAPPLYPAY ADD COLUMN (bankprovince VARCHAR(64),bankcity VARCHAR(64));

ALTER TABLE T_CENTER_BANK ADD COLUMN (bankprovince VARCHAR(64),bankcity VARCHAR(64));








