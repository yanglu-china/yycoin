-- #647
alter table T_CENTER_SAILCONF add column iprice double default 0;

-- #691
alter table t_center_frdb add column description varchar(500) default '';

-- #770
ALTER TABLE t_center_out_import ADD COLUMN (gfmc VARCHAR(64),gfsh VARCHAR(64), gfyh VARCHAR(64),gfdz VARCHAR(64));


-- #779
alter table t_center_olbase add column settleprice double(10,2) default 0;