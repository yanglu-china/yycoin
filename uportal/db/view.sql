CREATE or REPLACE VIEW v_center_tcpstatus
AS
SELECT id, `type`, `status`, `status` AS name, 'apply' AS tablename FROM t_center_tcpapply
union
SELECT id, `type`, `status`, `status` AS name, 'expense' AS tablename FROM t_center_tcpexpense;

CREATE or REPLACE VIEW v_center_tcphandlehis
AS
SELECT his.*,
CASE 
WHEN (his.type<=10) THEN app.status
WHEN (his.type>10 AND his.type<=20) THEN exp.status
WHEN (his.type=21) THEN reb.status
WHEN (his.type=22) THEN pre.status
ELSE bac.status
END AS status
FROM t_center_tcphandlehis his
LEFT JOIN T_CENTER_TRAVELAPPLY app on (app.id=his.refId and app.type=his.type)
LEFT JOIN T_CENTER_TCPEXPENSE exp on (exp.id=his.refId and exp.type=his.type)
LEFT JOIN T_CENTER_REBATE reb on (reb.id=his.refId and reb.type=his.type)
LEFT JOIN T_CENTER_PREINVOICE pre on (pre.id=his.refId and pre.type=his.type)
LEFT JOIN T_CENTER_BACKPREPAY_APPLY bac on (bac.id=his.refId and bac.type=his.type);