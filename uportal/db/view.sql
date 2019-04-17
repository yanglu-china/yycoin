CREATE or REPLACE VIEW v_center_tcpstatus
AS
SELECT id, `type`, `status`, `status` AS name, 'apply' AS tablename FROM t_center_tcpapply
union
SELECT id, `type`, `status`, `status` AS name, 'expense' AS tablename FROM t_center_tcpexpense;

CREATE or REPLACE VIEW v_center_tcphandlehis
AS
SELECT his.*,
CASE 
WHEN (his.type>10 AND his.type<=20 AND NOT(exp.status is NULL)) THEN exp.status
ELSE app.status
END AS status
FROM t_center_tcphandlehis his
LEFT JOIN t_center_tcpapply app on (app.id=his.refId and app.type=his.type)
LEFT JOIN t_center_tcpexpense exp on (exp.id=his.refId and exp.type=his.type);