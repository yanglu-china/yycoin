delete from `t_center_auth` where id in ('1522');

INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1522', '15', '入库-待财务登记入账', 0, 1, 1);

delete from `t_center_oamenuitem` where id in ('1522');

INSERT INTO `t_center_oamenuitem` VALUES ('1522', '入库-待财务登记入账', '../sail/out.do?method=queryBuy&load=1&menu=1&queryType=11', '15', '1', '1522', '99', '');

#如果状态是10变到11
