ERP built with Spring,Java7.

Build tool:
gradle(2.2)

>gradle clean backup undeploy war deploy

http://localhost:8080/uportal/admin/index.jsp  


Logstash with log4j input plugin  
logstash -f logstash.conf --configtest  
logstash -f logstash.conf  
default logstash will create one index feed to elasticsearch every day.   


Elastic  
1. View all index  
http://localhost:9200/_cat/indices?v  
2. View index data  
http://localhost:9200/logstash-2016.09.29/_search?q=*&pretty  
设置环境变量ES_HEAP_SIZE=10g  


Kibana  
http://localhost:5601/  
https://www.elastic.co/guide/en/kibana/3.0/queries.html  


后台JOB:
**updateOutbackStatusJob:更新入库单对应退单状态**  
**updateCustomerReserve2Job:更新客户线上/线下状态**  
**synReferJob:**  
**synNewJob:**
**synJob:**  
**staxSynJob:**    
**statsPersonalSwatchJob:领样金额统计**  
statsBlackJob12:黑名单统计12点     
**statsBlackJob:黑名单统计**  
**statsBlackDetailJob:黑名单明细统计**    
statOutJob:客户信用统计    
statBankJob:银行月结统计    
statBankBalanceJob:银行余额统计  
**sortPackages:CK单中距当前时间最长的单据创建时间统计**  
**sendShippingMailToSails:发送发货邮件给销售员**      
**sendMailForShipping:发送发货邮件给客户**    
**sendMailForNbShipping:宁波银行发货邮件**    
planJob:已废弃    
passPaymentApplyJob:坏帐金额为0的收款审核申请自动审批通过  
passPaymentApply2Job:已废弃    
**offlineStorageInJob:自助入库单**    
monitorImplJob:已废弃  
**initCarryStatusJob:预算的执行状态变更**  
**ibReportJobMonthlyJob:中收激励申请统计导出**  
ibReportJob:中收激励统计  
**ibReportJob2：中收激励统计晚上**  
hotLoadingParameterJob:系统参数初始化  
handleCheckPayJob：已废弃  
**fixMonthIndexJob:更新财务凭证表monthIndex字段**  
**financeTagJob:T_CENTER_FINANCE_TAG统计**  
**financeOutPayTagJob：销售单回款标识统计**  
exportAllStorageRelationJob:库存导出  
**exportAllStafferCreditJob:职员信用导出**  
exportAllCurrentBankStatJob:银行统计导出  
everyDayCarryWithOutTransactionalJob:数据库备份  
everyHourCarryWithOutTransactionalJob:更新客户信息核对申请状态  
**downloadOrderFromMailAttachmentJob:邮件订单**    
deleteHisJob:清理客户信用日志  
createPackageJob:生成CK单  
createOAOrderFromAppJob:已废弃  
composeProductJob:预合成提交申请  
clearRejectInvoiceinsBeanJob:清除被驳回的发票  
**cleanMailJob:清除邮件**  
checkStorageLogJob:体检库存异动  
checkOrderWithoutCKJob:检查状态为 “已出库”且发货方式不是“空发”的但没有生成CK单的订单，列入preconsign表  
**backupBlackJob:黑名单备份**  
autoRefInbillToSailJob:自动勾款  
autoProcessPaymentToPreJob:暂记户-预收自动认款  
autoCreatePriceConfigJob:自动生成结算价  
autoApproveOutJob:已废弃   
autoApproveJob:自动库管审批通过  
**createCustomerJob:创建客户**  








