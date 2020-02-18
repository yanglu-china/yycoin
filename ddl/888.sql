
delete from `t_center_auth` where id in ('1521');

INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1521', '15', '批量增加报废单', 0, 1, 1);

delete from `t_center_oamenuitem` where id in ('1521');

INSERT INTO `t_center_oamenuitem` VALUES ('1521', '批量增加报废单', '../sailImport/batchDropImport.jsp', '15', '1', '1521', '99', '');


ALTER TABLE T_CENTER_OUT ADD COLUMN (dropType int(11) COMMENT '报废类型' DEFAULT 0);


#以下SQL有助于准备测试数据
select sr.*, depot.`name` depotname, dp.`name` depotpartname,st.`name` storagename,prod.`name` prodname,prod.cost
from t_center_storageralation sr
LEFT JOIN t_center_depot depot on depot.id=sr.locationId
LEFT JOIN t_center_depotpart dp on dp.id=sr.depotpartId
LEFT JOIN t_center_storage st on st.id=sr.storageId
LEFT JOIN t_center_product prod on prod.id=sr.productId
WHERE sr.id='1199935';