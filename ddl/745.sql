ALTER TABLE T_CENTER_OUT ADD COLUMN (buyReturnFlag CHAR(1) COMMENT '是否新流程采购退货:0-不是;1:是' DEFAULT 0);
ALTER TABLE T_CENTER_OUT ADD COLUMN (buyReturnType CHAR(1) COMMENT '采购退货类型，1：已入库退货， 2：未入库退货' DEFAULT 0);

delete from `t_center_oamenuitem` where id in ('1351','1352','1353','1354'，'1355');

INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1351', '13', '我的采购退货', 0, 1, 1);
INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1352', '13', '采购退货-生产采购经理', 0, 1, 1);
INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1353', '13', '采购退货-运营总监', 0, 1, 1);
INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1354', '13', '采购退货-分管副总裁', 0, 1, 1);
INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1355', '13', '所有采购退货查询', 0, 1, 1);

delete from `t_center_oamenuitem` where id in ('1341','1342','1343','1344'，'1345');

INSERT INTO `t_center_oamenuitem` VALUES ('1341', '我的采购退货', '../sail/out.do?method=querySelfBuy&load=1&menu=1&queryType=0&buyReturnFlag=1', '13', '1', '1351', '31', '');
INSERT INTO `t_center_oamenuitem` VALUES ('1342', '采购退货-生产采购经理', '../sail/out.do?method=queryBuy&load=1&menu=1&queryType=1&buyReturnFlag=1', '13', '1', '1352', '32', '');
INSERT INTO `t_center_oamenuitem` VALUES ('1343', '采购退货-运营总监', '../sail/out.do?method=queryBuy&load=1&menu=1&queryType=2&buyReturnFlag=1', '13', '1', '1353', '33', '');
INSERT INTO `t_center_oamenuitem` VALUES ('1344', '采购退货-分管副总裁', '../sail/out.do?method=queryBuy&load=1&menu=1&queryType=3&buyReturnFlag=1', '13', '1', '1354', '34', '');

INSERT INTO `t_center_oamenuitem` VALUES ('1345', '所有采购退货查询', '../sail/out.do?method=queryBuy&load=1&menu=1&queryType=6&buyReturnFlag=1', '13', '1', '1355', '30', '');
