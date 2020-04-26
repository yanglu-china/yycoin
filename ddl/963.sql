
delete from `t_center_auth` where id in ('1904');

INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1904', '19', '查看中收激励明细', 0, 1, 1);

delete from `t_center_oamenuitem` where id in ('1926');

INSERT INTO `t_center_oamenuitem` VALUES ('1926', '中收激励申请统计', '../tcp/apply.do?method=queryIbReport', '19', '1', '1904', '20', '');



delete from t_center_oamenuitem where MENUITEMNAME = '银行业务部层级关系导入';



