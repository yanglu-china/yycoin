-- #753
alter table t_center_invoiceins_item add column spmc varchar(100) default '';

-- #767
ALTER TABLE T_CENTER_INVOICEINS ADD COLUMN (gfmc VARCHAR(64),gfsh VARCHAR(64), gfyh VARCHAR(64),gfdz VARCHAR(64));
ALTER TABLE T_CENTER_INVOICEINS_IMPORT ADD COLUMN (gfmc VARCHAR(64),gfsh VARCHAR(64), gfyh VARCHAR(64),gfdz VARCHAR(64));

-- #776
alter table T_CENTER_INVOICEINS add column fpgg varchar(40) default '', add column fpdw varchar(8) default '';
alter table t_center_invoiceins_import add column fpgg varchar(40) default '', add column fpdw varchar(8) default '';

-- #812
alter table t_center_backprepay_apply add column sfDescription varchar(200) default '';
INSERT INTO t_center_schedule_job VALUES (562,SYSDATE(),NULL,'yscfJob','Default','0','0 0/1 * * * ?','预收拆分','com.china.center.oa.finance.manager.impl.FinanceManagerImpl',0,'financeManager','yscfJob',0);
