
delete from `t_center_auth` where id in ('1453');

INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1453', '14', '批量领样转订单', 0, 1, 1);

delete from `t_center_oamenuitem` where id in ('1453');

INSERT INTO `t_center_oamenuitem` VALUES ('1453', '批量领样转订单', '../sail/batchSample2Order.jsp', '14', '1', '1453', '99', '');



