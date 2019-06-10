alter table T_CENTER_INVOICEINS_IMPORT add column invoiceType varchar(20) default '纸质发票';
alter table T_CENTER_VS_INVOICENUM add column invoiceType varchar(20) default '纸质发票';