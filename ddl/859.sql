
delete from `t_center_auth` where id in ('9039');

INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('9039', '90', '批量导入退票申请', 0, 1, 1);

delete from `t_center_oamenuitem` where id in ('9039');

INSERT INTO `t_center_oamenuitem` VALUES ('9039', '批量导入退票申请', '../invoiceins/batchBackInvoiceins.jsp', '90', '1', '9039', '99', '');