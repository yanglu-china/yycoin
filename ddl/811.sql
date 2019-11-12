UPDATE `t_center_auth` set NAME='查看所有中收激励结果' WHERE id='1902';
UPDATE `t_center_oamenuitem` set MENUITEMNAME='查看所有中收激励结果' WHERE id='1923';

delete from `t_center_auth` where id in ('1903');

INSERT INTO `t_center_auth` (`id`, `PARENTID`, `NAME`, `type`, `LEVEL`, `bottomFlag`) VALUES ('1903', '19', '中收激励申请统计', 0, 1, 1);

delete from `t_center_oamenuitem` where id in ('1926');

INSERT INTO `t_center_oamenuitem` VALUES ('1926', '中收激励申请统计', '../tcp/apply.do?method=queryIbReport&queryType=1', '19', '1', '1903', '20', '');
