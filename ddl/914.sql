create view v_center_ct_approvetime
AS
select a.fullId, a.logTime 
from T_CENTER_APPROVELOG a
where a.afterStatus=3
and a.fullId like 'CT%';