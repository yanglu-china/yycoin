CREATE or REPLACE VIEW v_center_tcpstatus
AS
SELECT id, `type`, `status`, `status` AS name, 'apply' AS tablename FROM t_center_tcpapply
union
SELECT id, `type`, `status`, `status` AS name, 'expense' AS tablename FROM t_center_tcpexpense;

CREATE or REPLACE VIEW v_center_tcphandlehis
AS
SELECT his.*,st.`status`,st.tablename
FROM t_center_tcphandlehis his
LEFT JOIN v_center_tcpstatus st on (st.id=his.refId and st.type=his.type);