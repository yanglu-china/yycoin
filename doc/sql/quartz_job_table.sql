-- --------------------------------------------------------
-- 主机:                           localhost
-- 服务器版本:                        5.6.30 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.1.0.4867
-- --------------------------------------------------------

-- 定时任务管理表
DROP TABLE IF EXISTS `t_center_schedule_job`;
CREATE TABLE IF NOT EXISTS `t_center_schedule_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NULL DEFAULT NULL,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `jobName` varchar(255) DEFAULT NULL,
  `jobGroup` varchar(255) DEFAULT NULL,
  `jobStatus` varchar(255) DEFAULT NULL,
  `cronExpression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `beanClass` varchar(255) DEFAULT NULL,
  `isConcurrent` varchar(255) DEFAULT NULL COMMENT '0',
  `springId` varchar(255) DEFAULT NULL,
  `methodName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_group` (`jobName`,`jobGroup`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=500 DEFAULT CHARSET=utf8;

-- 定时任务管理表默认数据
INSERT INTO `t_center_schedule_job` (`createTime`, `updateTime`, `jobName`, `jobGroup`, `jobStatus`, `cronExpression`, `description`, `beanClass`, `isConcurrent`, `springId`, `methodName`) VALUES
	( '2016-10-15 16:52:17', '2016-12-08 14:32:23', 'cleanMailJob', 'Default', '1', '0 0 2 * * ?', '清除邮件', 'com.china.center.oa.mail.manager.impl.MailCleanManagerImpl', '0', 'mailCleanManager', 'cleanMail'),
	( '2016-10-15 16:52:17', '2016-12-08 14:32:23', 'initCarryStatusJob', 'Default', '1', '0 10 0 * * ?', '预算的执行状态变更', 'com.china.center.oa.budget.manager.impl.BudgetManagerImpl', '0', 'budgetManager', 'initCarryStatus'),
	( '2016-10-15 16:52:17', '2016-12-08 14:32:23', 'backupBlackJob', 'Default', '1', '0 30 4 * * ?', '黑名单备份', 'com.china.center.oa.commission.dao.impl.BlackDAOImpl', '0', 'blackDAO', 'backup'),
	( '2016-10-15 16:52:17', '2016-12-08 14:32:23', 'statsBlackJob12', 'Default', '1', '0 0 12 * * ?', '黑名单统计12点', 'com.china.center.oa.commission.manager.impl.BlackManagerImpl', '0', 'blackManager', 'statsBlack'),
	( '2016-10-15 16:52:17', '2016-12-08 14:32:23', 'statsBlackJob', 'Default', '1', '0 10 17 * * ?', '黑名单统计', 'com.china.center.oa.commission.manager.impl.BlackManagerImpl', '0', 'blackManager', 'statsBlack'),
	( '2016-10-15 16:52:17', '2016-12-08 14:32:23', 'statsBlackDetailJob', 'Default', '1', '0 30 3 * * ?', '黑名单明细统计', 'com.china.center.oa.commission.manager.impl.BlackManagerImpl', '0', 'blackManager', 'statsBlackOutDetail'),
	( '2016-10-15 16:52:17', '2016-12-08 14:32:23', 'updateCustomerReserve2Job', 'Default', '1', '0 0/60 * * * ?', '更新客户线上/线下状态', 'com.china.center.oa.client.dao.impl.CustomerMainDAOImpl', '0', 'customerMainDAO', 'updateCustomerReserve2Job'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'statOutJob', 'Default', '1', '0 30 3 * * ?', '客户信用统计', 'com.china.center.oa.customervssail.manager.impl.CurOutManagerImpl', '0', 'curOutManager', 'statOut'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'deleteHisJob', 'Default', '1', '0 0 4 * * ?', '清理客户信用日志', 'com.china.center.oa.customervssail.manager.impl.CurOutManagerImpl', '0', 'curOutManager', 'deleteHis'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'statBankJob', 'Default', '1', '0 30 2 * * ?', '银行月结统计', 'com.china.center.oa.finance.manager.impl.StatBankManagerImpl', '0', 'statBankManager', 'statBank'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'clearRejectInvoiceinsBeanJob', 'Default', '1', '0 0 3 * * ?', '清除被驳回的发票', 'com.china.center.oa.finance.manager.impl.InvoiceinsManagerImpl', '0', 'invoiceinsManager', 'clearRejectInvoiceinsBean'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'exportAllCurrentBankStatJob', 'Default', '1', '0 58 23 * * ?', '银行统计导出', 'com.china.center.oa.finance.manager.impl.BankManagerImpl', '0', 'bankManager', 'exportAllCurrentBankStat'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'autoProcessPaymentToPreJob', 'Default', '1', '0 0 * * * ?', '暂记户-预收自动认款', 'com.china.center.oa.finance.manager.impl.AutoPayManagerImpl', '0', 'autoPayManager', 'autoProcessPaymentToPre'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'autoRefInbillToSailJob', 'Default', '1', '0 10 0/2 * * ?', '自动勾款', 'com.china.center.oa.finance.manager.impl.AutoPayManagerImpl', '0', 'autoPayManager', 'autoRefInbillToSail'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'createPackageJob', 'Default', '1', '0/3 * * * * ?', '生成CK单', 'com.china.center.oa.finance.manager.impl.PackageManagerImpl', '0', 'packageManager', 'createPackage'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'passPaymentApplyJob', 'Default', '1', '0 0/5 * * * ?', '坏帐金额为0的收款审核申请自动审批通过', 'com.china.center.oa.finance.manager.impl.PaymentApplyManagerImpl', '0', 'paymentApplyManager', 'passPaymentApplyJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'autoApproveJob', 'Default', '1', '0 0/5 * * * ?', '自动库管审批通过', 'com.china.center.oa.finance.manager.impl.InvoiceinsManagerImpl', '0', 'invoiceinsManager', 'autoApproveJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'checkOrderWithoutCKJob', 'Default', '1', '0 0/40 * * * ?', '检查状态为 “已出库”且发货方式不是“空发”的但没有生成CK单的订单，列入preconsign表', 'com.china.center.oa.finance.manager.impl.PackageManagerImpl', '0', 'packageManager', 'checkOrderWithoutCKJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'statBankBalanceJob', 'Default', '1', '0 0 1 * * ?', '银行余额统计', 'com.china.center.oa.finance.manager.impl.StatBankManagerImpl', '0', 'statBankManager', 'statBankBalance'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'exportAllStorageRelationJob', 'Default', '1', '0 59 23 * * ?', '库存导出', 'com.china.center.oa.product.manager.impl.StorageRelationManagerImpl', '0', 'storageRelationManager', 'exportAllStorageRelation'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'checkStorageLogJob', 'Default', '1', '0 50 3 * * ?', '体检库存异动', 'com.china.center.oa.product.manager.impl.StorageRelationManagerImpl', '0', 'storageRelationManager', 'checkStorageLog'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'composeProductJob', 'Default', '1', '0 0/60 * * * ?', '预合成提交申请', 'com.china.center.oa.product.manager.impl.ComposeProductManagerImpl', '0', 'composeProductManager', 'composeProductJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'autoCreatePriceConfigJob', 'Default', '1', '0 0/60 * * * ?', '自动生成结算价', 'com.china.center.oa.product.manager.impl.ProductManagerImpl', '0', 'productManager', 'autoCreatePriceConfigJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'everyDayCarryWithOutTransactionalJob', 'Default', '1', '0 0 1 * * ?', '数据库备份', 'com.china.center.oa.publics.trigger.PublicTriggerHander', '0', 'publicTriggerHander', 'everyDayCarryWithOutTransactional'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'everyHourCarryWithOutTransactionalJob', 'Default', '1', '0 0 * * * ?', '更新客户信息核对申请状态', 'com.china.center.oa.publics.trigger.PublicTriggerHander', '0', 'publicTriggerHander', 'everyHourCarryWithOutTransactional'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'hotLoadingParameterJob', 'Default', '1', '0 0 * * * ?', '系统参数初始化', 'com.china.center.oa.publics.dao.impl.ParameterDAOImpl', '0', 'parameterDAO', 'init'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'synJob', 'Default', '1', '0 10 23 * * ?', '', 'com.china.center.oa.sail.dao.impl.UnitViewDAOImpl', '0', 'unitViewDAO', 'syn'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'synNewJob', 'Default', '1', '0 0 0/1 * * ?', '', 'com.china.center.oa.sail.dao.impl.UnitViewDAOImpl', '0', 'unitViewDAO', 'synNew'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'exportAllStafferCreditJob', 'Default', '1', '0 58 23 * * ?', '职员信用导出', 'com.china.center.oa.sail.manager.impl.OutManagerImpl', '0', 'outManager', 'exportAllStafferCredit'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'statsPersonalSwatchJob', 'Default', '1', '0 40 23 * * ?', '领样金额统计', 'com.china.center.oa.sail.manager.impl.OutStatManagerImpl', '0', 'outStatManager', 'statsPersonalSwatch'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'sendMailForShipping', 'Default', '1', '0 0 10 * * ?', '发送发货邮件给客户', 'com.china.center.oa.sail.manager.impl.ShipManagerImpl', '0', 'shipManager', 'sendMailForShipping'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'sendShippingMailToSails', 'Default', '1', '0 45 9 * * ?', '发送发货邮件给销售员', 'com.china.center.oa.sail.manager.impl.ShipManagerImpl', '0', 'shipManager', 'sendShippingMailToSails'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'sendMailForNbShipping', 'Default', '1', '0 0/5 * * * ?', '宁波银行发货邮件', 'com.china.center.oa.sail.manager.impl.ShipManagerImpl', '0', 'shipManager', 'sendMailForNbShipping'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'sortPackages', 'Default', '1', '0 0/1 * * * ?', 'CK单中距当前时间最长的单据创建时间统计', 'com.china.center.oa.sail.manager.impl.ShipManagerImpl', '0', 'shipManager', 'sortPackagesJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'downloadOrderFromMailAttachmentJob', 'Default', '1', '0 0/16 * * * ?', '邮件订单', 'com.china.center.oa.sail.manager.impl.OutImportManagerImpl', '0', 'outImportManager', 'downloadOrderFromMailAttachment'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'offlineStorageInJob', 'Default', '1', '0 0/15 * * * ?', '自助入库单', 'com.china.center.oa.sail.manager.impl.OutImportManagerImpl', '0', 'outImportManager', 'offlineStorageInJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'updateOutbackStatusJob', 'Default', '1', '0 0/15 * * * ?', '更新入库单对应退单状态', 'com.china.center.oa.sail.manager.impl.OutManagerImpl', '0', 'outManager', 'updateOutbackStatusJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'staxSynJob', 'Default', '1', '0 0 0/1 * * ?', '', 'com.china.center.oa.tax.dao.impl.CheckViewDAOImpl', '0', 'checkViewDAO', 'syn'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'synReferJob', 'Default', '1', '0 45 2 * * ?', '', 'com.china.center.oa.tax.dao.impl.FinanceReferDAOImpl', '0', 'financeReferDAO', 'syn'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'fixMonthIndexJob', 'Default', '1', '0 10 0 * * ?', '更新财务凭证表monthIndex字段', 'com.china.center.oa.tax.manager.impl.FinanceManagerImpl', '0', 'financeManager', 'fixMonthIndex'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'financeTagJob', 'Default', '1', '0 10 23 * * ?', 'T_CENTER_FINANCE_TAG统计', 'com.china.center.oa.tax.manager.impl.FinanceTagManagerImpl', '0', 'financeTagManager', 'processInvoiceinsTagData'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'financeOutPayTagJob', 'Default', '1', '0 0 23 * * ?', '销售单回款标识统计', 'com.china.center.oa.tax.manager.impl.FinanceTagManagerImpl', '0', 'financeTagManager', 'processOutPayTagData'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'ibReportJob', 'Default', '1', '0 59 23 * * ?', '中收激励统计', 'com.china.center.oa.tcp.manager.impl.TravelApplyManagerImpl', '0', 'travelApplyManager', 'ibReportJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'ibReportJob2', 'Default', '1', '0 0 12 * * ?', '中收激励统计12点', 'com.china.center.oa.tcp.manager.impl.TravelApplyManagerImpl', '0', 'travelApplyManager', 'ibReportJob'),
	('2016-10-15 16:52:17', '2016-12-08 14:32:23', 'ibReportJobMonthlyJob', 'Default', '1', '0 0 23 * * ?', '中收激励申请统计导出', 'com.china.center.oa.tcp.manager.impl.TravelApplyManagerImpl', '0', 'travelApplyManager', 'ibReportJobMonthly');
	
-- 插入菜单
-- INSERT INTO `t_center_oamenuitem` (`id`, `MENUITEMNAME`, `URL`, `PARENTID`, `BottomFlag`, `AUTH`, `indexPos`, `description`) VALUES ('100', '定时任务管理', '', '0', 0, '0000', 100, '定时任务管理模块');
-- INSERT INTO `t_center_oamenuitem` (`id`, `MENUITEMNAME`, `URL`, `PARENTID`, `BottomFlag`, `AUTH`, `indexPos`, `description`) VALUES ('10001', '定时任务', '../schedulejob/queryAllJob.jsp', '100', 1, '0000', 100, '定时任务');

-- 定时任务日志表
DROP TABLE IF EXISTS `t_center_schedule_job_log`;
CREATE TABLE IF NOT EXISTS `t_center_schedule_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobId` varchar(20) NOT NULL,
  `jobName` varchar(64) DEFAULT NULL,
  `jobGroup` varchar(64) DEFAULT NULL,
  `fireTime` varchar(20) DEFAULT NULL,
  `nextFireTime` varchar(20) DEFAULT NULL,
  `refireCount` int DEFAULT NULL,
  `result` int DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

