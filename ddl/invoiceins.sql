-- #753
alter table t_center_invoiceins_item add column spmc varchar(100) default '';

-- #767
ALTER TABLE T_CENTER_INVOICEINS ADD COLUMN (gfmc VARCHAR(64),gfsh VARCHAR(64), gfyh VARCHAR(64),gfdz VARCHAR(64));
ALTER TABLE T_CENTER_INVOICEINS_IMPORT ADD COLUMN (gfmc VARCHAR(64),gfsh VARCHAR(64), gfyh VARCHAR(64),gfdz VARCHAR(64));