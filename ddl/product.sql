alter table t_product_apply add column refProduct varchar(200) default '';

--#885
alter table t_product_apply add column codeGenerated int(11) default 1;
INSERT INTO t_center_schedule_job VALUES (563,SYSDATE(),NULL,'generateCodeJob','Default','0','0 0/1 * * * ?','产品编码生成','com.china.center.oa.product.manager.impl.ProductApplyManagerImpl',0,'productApplyManager','generateCodeJob',0);
