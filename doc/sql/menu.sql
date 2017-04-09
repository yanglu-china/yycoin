delete from t_center_oamenuitem where MENUITEMNAME in('职员信用','结算价格管理','告警管理','销售审批规则','外部品名配置','商品转换配置');
delete from t_center_oamenuitem where MENUITEMNAME in('产品申请','产品审核','产品调价');
delete from t_center_oamenuitem where MENUITEMNAME in('采购入库预确认');
delete from t_center_oamenuitem where PARENTID=20 or MENUITEMNAME in('项目管理');
delete from t_center_oamenuitem where PARENTID=21 or MENUITEMNAME in('回访对账');
delete from t_center_oamenuitem where MENUITEMNAME='流程管理' or PARENTID=07;