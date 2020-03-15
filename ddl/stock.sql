--#851
alter table t_center_provide add khm varchar(100) default '';
alter table T_CENTER_PROVIDE_HIS add khm varchar(100) default '';

--#909
INSERT INTO uportal.t_center_schedule_job (id, createTime, updateTime, jobName, jobGroup, jobStatus, cronExpression, description, beanClass, isConcurrent, springId, methodName, type) VALUES (567, sysdate(), sysdate(), 'dhDiaoboJob2', 'Default', '0', '0 0/1 * * * ?', '到货调拨2', 'com.china.center.oa.stock.manager.impl.StockManagerImpl', '0', 'stockManager', 'dhDiaoboJob2', 0);
