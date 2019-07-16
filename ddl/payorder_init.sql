CREATE TABLE t_center_paylistlog (
  id VARCHAR (48) COMMENT '��ˮ��',
  Outid VARCHAR (48) COMMENT '���ݺ�',
  TYPE VARCHAR (8) COMMENT '��������',
  Bankname VARCHAR (24) COMMENT '�տ�����',
  username VARCHAR (128) COMMENT '�տ��',
  bankno VARCHAR (128) COMMENT '�տ��˺�',
  Money NUMERIC (12, 2) COMMENT '�տ���',
  province VARCHAR(48) COMMENT '�տ��˻�����ʡ',
  city VARCHAR(48) COMMENT '�տ��˻�������',
  description VARCHAR (256) COMMENT '���ע',
  outidtime VARCHAR (24) COMMENT '��������',
  STATUS VARCHAR (2) COMMENT '����״̬',
  outbillid VARCHAR (128) COMMENT '�����',
  operator VARCHAR (32) COMMENT '���������',
  paytime VARCHAR (24) COMMENT '����ʱ��',
  Payaccount VARCHAR (48) COMMENT '�����˻�',
  Paybank VARCHAR (128) COMMENT '��������',	
  bankstatus VARCHAR (8) COMMENT '����ȷ��״̬',
  bankpaytime VARCHAR (24) COMMENT '����ȷ��ʱ��'
);

//�˵�
INSERT INTO t_center_oamenuitem VALUES (1660,'���и���','../payorder/querypayorder.jsp',16,1,1660,99,'�ʽ�ƽ̨���и���');
//Ȩ��
INSERT INTO t_center_auth VALUES (1660,16,'����֧��',0,1,1);



INSERT INTO t_center_schedule_job VALUES (563,SYSDATE(),NULL,'queryPayOrderStatus','Default','0','0 1/10 * * * ?',
'�������в�ѯ����״̬','com.china.center.oa.job.manager.impl.NbBankPayOrderQueryImpl',0,'nbBankPayOrderQueryJobManager','run',0);


ALTER TABLE T_CENTER_TCPEXPENSE ADD COLUMN (bankprovince VARCHAR(64),bankcity VARCHAR(64));

ALTER TABLE T_CENTER_TRAVELAPPLYPAY ADD COLUMN (bankprovince VARCHAR(64),bankcity VARCHAR(64));

ALTER TABLE T_CENTER_BANK ADD COLUMN (bankprovince VARCHAR(64),bankcity VARCHAR(64));








