package com.china.center.oa.sail.bean;


import java.io.Serializable;
import java.util.List;

import com.china.center.jdbc.annotation.Column;
import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.bean.DepotpartBean;
import com.china.center.oa.publics.bean.AttachmentBean;
import com.china.center.oa.publics.bean.DutyBean;
import com.china.center.oa.publics.bean.InvoiceBean;
import com.china.center.oa.publics.bean.LocationBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.publics.bean.StafferBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.vo.OutInterface;
import com.china.center.tools.StringTools;


/**
 * 库单(销售单、入库单) CORE
 * 
 * @author ZHUZHU
 * @version 2007-3-25
 * @see OutBean
 * @since 1.0
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_CENTER_OUT")
public class OutBean implements Serializable, OutInterface
{
    @Id
    private String fullId = "";

    /**
     * 排序ID
     */
    private String id = "";

    /**
     * 流程ID
     */
    private String flowId = "";

    private String outTime = "";

    /**
     * 总经理通过时间
     */
    private String managerTime = "";

    /**
     * 库存移动时间
     */
    private String changeTime = "";

    /**
     * 付款时间
     */
    private String payTime = "";

    /**
     * 库管通过时间
     */
    private String flowTime = "";

    /**
     * 库单类型
     */
    private int outType = OutConstant.OUTTYPE_OUT_COMMON;


    /**
     * 报废类型
     */
    private int dropType = OutConstant.DROPTYPE_INVENTORY_SHORTAGE;

    /**
     * 0:销售单 1:入库单
     */
    private int type = OutConstant.OUT_TYPE_OUTBILL;

    /**
     * 0:defalut 1:abs
     */
    private int vtype = OutConstant.VTYPE_DEFAULT;

    /**
     * mtype
     */
    private int mtype = PublicConstant.MANAGER_TYPE_MANAGER;

    /**
     * 产品销售类型
     */
    private int pmtype = PublicConstant.MANAGER_TYPE_MANAGER;

    /**
     * 是否开票
     */
    private int hasInvoice = OutConstant.HASINVOICE_NO;

    /**
     * 发票ID
     */
    @Join(tagClass = InvoiceBean.class, type = JoinType.LEFT)
    private String invoiceId = "";

    @Join(tagClass = DutyBean.class, type = JoinType.LEFT)
    private String dutyId = "";

    /**
     * 0:保存 1:提交 2:驳回 3:通过 4:会计审核通过 6:总经理审核通过 7:物流管理员通过<br>
     * (一般此通过即是销售单已经OK status in (3, 4))<br>
     * 预占库存 status in (1, 6, 7)
     */
    private int status = OutConstant.STATUS_SAVE;

    /**
     * 开发票状态(0:没有开票 1:全部开票)
     */
    private int invoiceStatus = OutConstant.INVOICESTATUS_INIT;

    /**
     * 开发票的金额(已经开票的金额)
     */
    private double invoiceMoney = 0.0d;

    private String department = "";

    @Join(tagClass = UnitViewBean.class, type = JoinType.LEFT)
    private String customerId = "";

    private String customerName = "";

    /**
     * 库单所在区域
     */
    @Join(tagClass = LocationBean.class, type = JoinType.LEFT)
    private String locationId = "";

    /**
     * 进出产品所在的仓库
     */
    @Join(tagClass = DepotBean.class, type = JoinType.LEFT)
    private String location = "";

    /**
     * 销售行业范围(4级组织)
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String industryId = "";

    /**
     * 5级组织
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT, alias = "PRI2")
    private String industryId2 = "";

    /**
     * 六级组织
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT, alias = "PRI3")
    private String industryId3 = "";

    private String connector = "";

    private String phone = "";

    private String stafferName = "";

    private String stafferId = "";

    /**
     * 担保人id
     */
    private String managerId = "";

    private double total = 0.0d;

    private String description = "";

    /**
     * #352 客户备注
     */
    private String customerDescription = "";

    /**
     * 总部核对信息
     */
    private String checks = "";

    private int checkStatus = PublicConstant.CHECK_STATUS_INIT;

    private int reday = 0;

    /**
     * 回款日期
     */
    private String redate = "";

    /**
     * 入库单的入库仓区
     */
    @Join(tagClass = DepotpartBean.class, type = JoinType.LEFT)
    private String depotpartId = "";

    @Column(name = "mark")
    private int marks = 0;

    @Ignore
    private boolean mark = false;

    @Ignore
    private int consign = 0;

    /**
     * #630 调拨JOB自动生成CK单
     */
    @Ignore
    private boolean frFlag = false;

    /**
     * 0:没有付款 1:付款
     */
    private int pay = OutConstant.PAY_NOT;


    /**#775
     * 退货付款类型
     */
    private int backPay = 0;

    /**
     * 0：非在途 1：在途（入库单使用 调出）
     */
    private int inway = OutConstant.IN_WAY_NO;

    /**
     * 超期天数 如果未超期就是0
     */
    private int tempType = 0;

    /**
     * 已经支付的金额
     */
    private double hadPay = 0.0d;

    /**
     * 坏账金额
     */
    private double badDebts = 0.0d;

    /**
     * 坏账和付款关联
     */
    private int badDebtsCheckStatus = OutConstant.BADDEBTSCHECKSTATUS_NO;

    /**
     * 到货日期
     */
    private String arriveDate = "";

    /**
     * 目的仓库（入库单使用）
     */
    @Join(tagClass = DepotBean.class, alias = "des", type = JoinType.LEFT)
    private String destinationId = "0";

    /**
     * 调出的相关OUT(或者是采购的原始单据),个人领样的的销售单
     */
    private String refOutFullId = "";

    /**
     * vtype相互关联的两个单据
     */
    private String vtypeFullId = "";

    /**
     * 调出的时候强制需要运单号
     */
    private String tranNo = "";

    /**
     * 入库单的时候是调出还是调入/销售的时候是信用未处理
     */
    private int reserve1 = 0;

    /**
     * 客户是否超支(0:没有 1:超支)
     */
    private int reserve2 = OutConstant.OUT_CREDIT_COMMON;

    /**
     * 0:货到收款 1:款到发货(黑名单) 2:使用业务员信用额度 3:使用了事业部经理的信用
     */
    private int reserve3 = OutConstant.OUT_SAIL_TYPE_COMMON;

    /**
     * 上次已经扣除了一部分了
     */
    private String reserve4 = "";

    /**
     * 当前已经延期级别
     */
    private String reserve5 = "";

    /**
     * 信用超支日志
     */
    private String reserve6 = "";

    /**
     * 应收的描述,当销售单结束的时候会填写
     */
    private String reserve7 = "";

    /**
     * 是否是对冲单据
     */
    private String reserve8 = "";

    /**
     * 其它入库时，用于存放申请人，即为操作人，stafferid 用于存放经手人，即业务员。
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "Staff1")
    private String reserve9 = "";

    /**
     * 改造后增加了销售类型
     */
    private String sailType = "";

    /**
     * 销售的产品类型
     */
    private String productType = "";

    /**
     * 税率
     */
    private String ratio = "";

    /**
     * 预占客户信用等级金额(优先使用客户的)
     */
    private double curcredit = 0.0d;

    /**
     * 预占职员信用等级金额
     */
    private double staffcredit = 0.0d;

    /**
     * 预占分公司经理信用等级金额
     */
    private double managercredit = 0.0d;

    /**
     * 其它入库（也称强制入库）事由类型
     */
    private int forceBuyType = -1;
    
    /**
     * 促销活动ID
     */   
    @Join(tagClass = PromotionBean.class, type = JoinType.LEFT)
    private String eventId = "";
    
    /**
     * 优惠金额
     */
    private double promValue = 0.0d;
    
    /**
     * 是否执行促销活动
     */
    private int promStatus = -1;
    
    /**
     * 促销绑定订单
     */
    private String refBindOutId = "";
    
    /**
     * 最后更改的时间
     */
    private String lastModified = "";
    
    /**
     * 商务模式下，经办人
     */
    private String operator = "";
    
    /**
     * 商务模式下，经办人名称
     */
    private String operatorName = "";
    
    /**
     * 开单时间  yyyy-MM-dd hh:mm:ss
     */
    private String logTime = "";
    
    /** 回访标志 */
    private int feedBackVisit = OutConstant.OUT_FEEDBACK_VISIT_NO;
    
    /** 对账标志 */
    private int feedBackCheck = OutConstant.OUT_FEEDBACK_CHECK_NO;
    
    /**
     * 是否参与返利
     */
    private int hasRebate = OutConstant.OUT_REBATE_NO;
    
    /**
     * 信用担保人
     */
    @Join(tagClass = StafferBean.class, type = JoinType.LEFT, alias = "Staff2")
    private String guarantor = "";
    
    /**
     * 税额
     */
    private double taxTotal = 0.0d;
    
    /**
     * 勾款与开标纳税实体与管理属性,票款一致的保证
     */
    /**
     * pi: Pay & invoice 勾款或开票最终确定的纳税实体
     */
    private String piDutyId = "";
    
    /**
     * 勾款或开票的最终管理属性 PublicConstant.MANAGER_TYPE_COMMON
     */
    private int piMtype = -1;
    
    /**
     * 勾款或开票 OutConstant.OUT_PAYINS_TYPE_PAY
     */
    private int piType = -1;
    
    /**
     * 勾款或开票是否结束
     */
    private int piStatus = -1;
    
    /**
     * 核销发票金额
     */
    private double hasConfirmInsMoney = 0.0d;
    
    /**
     * 是否已核销完全
     */
    private int hasConfirm = 0;
    
    /**
     * 销售单 紧急 标识 1:紧急
     */
    private int emergency = 0;
    
    /**
     * 赠送类型
     */
    private int presentFlag = 0;

    //2015/2/25 默认为空
    private String podate = "";

    //2015/4/11 中收激励标志位
    //0: 未申请 1：已申请
    private int ibFlag = 0;

    private String ibApplyId = "";

    //0: 未申请 1：已申请
    private int motivationFlag = 0;

    private String motivationApplyId = "";

    private int ibFlag2 = 0;

    private String ibApplyId2 = "";

    //0: 未申请 1：已申请
    private int motivationFlag2 = 0;

    private String motivationApplyId2 = "";

    /**
     * 平台手续费标记
     */
    private int platformFlag = 0;

    private String platformApplyId = "";

    //2015/8/5 异地调拨标志: 1-需异地调拨 0-否
    private int remoteAllocate = 0;

    /**
     * 2016/2/14 #171：未审批原因
     */
    private String reason = "";

    /**
     * 2016/3/18 #202: 销售退单时必填
     */
    private String transportNo = "";

    private String swbz = "";

    private String outbackStatus = "";

    private boolean customerCreated = false;

    /**
     * #135 赠品规则ID
     */
    private String refGiftId = "";

    /**
     * #162
     * 渠道
     */
    private String channel = "";

    private int cashFlag = 0;

    private int profigFlag = 0;

    /**#355
     * 交货方式
     */
    private String delivery  = "";

    /**
     * pos付款方
     */
    private String posPayer = "";

    /**
     * 推荐标示
     */
    private String recommendation = "";

    /**
     * #629 虚拟订单
     */
    private int virtualStatus = 0;

    @Ignore
    private List<BaseBean> baseList = null;

    @Ignore
    private DistributionBean distributeBean = null;
    
    @Ignore
    private List<DistributionBean> distList = null;
    
    /**
     * 附件列表
     */
    @Ignore
    protected List<AttachmentBean> attachmentList = null;
    
    //#745
    private int buyReturnFlag = 0;
    private int buyReturnType = 0;
    
    /**
     * Copy Constructor(不拷贝baseList)
     * 
     * @param outBean
     *            a <code>OutBean</code> object
     */
    public OutBean(OutBean outBean)
    {
        this.fullId = outBean.fullId;
        this.id = outBean.id;
        this.flowId = outBean.flowId;
        this.outTime = outBean.outTime;
        this.managerTime = outBean.managerTime;
        this.outType = outBean.outType;
        this.dropType = outBean.dropType;
        this.type = outBean.type;
        this.hasInvoice = outBean.hasInvoice;
        this.invoiceId = outBean.invoiceId;
        this.dutyId = outBean.dutyId;
        this.status = outBean.status;
        this.invoiceStatus = outBean.invoiceStatus;
        this.invoiceMoney = outBean.invoiceMoney;
        this.department = outBean.department;
        this.customerId = outBean.customerId;
        this.customerName = outBean.customerName;
        this.locationId = outBean.locationId;
        this.location = outBean.location;
        this.connector = outBean.connector;
        this.phone = outBean.phone;
        this.stafferName = outBean.stafferName;
        this.stafferId = outBean.stafferId;
        this.managerId = outBean.managerId;
        this.total = outBean.total;
        this.description = outBean.description;
        this.checks = outBean.checks;
        this.reday = outBean.reday;
        this.redate = outBean.redate;
        this.depotpartId = outBean.depotpartId;
        this.marks = outBean.marks;
        this.mark = outBean.mark;
        this.consign = outBean.consign;
        this.pay = outBean.pay;
        this.inway = outBean.inway;
        this.tempType = outBean.tempType;
        this.hadPay = outBean.hadPay;
        this.arriveDate = outBean.arriveDate;
        this.destinationId = outBean.destinationId;
        this.refOutFullId = outBean.refOutFullId;
        this.tranNo = outBean.tranNo;
        this.reserve1 = outBean.reserve1;
        this.reserve2 = outBean.reserve2;
        this.reserve3 = outBean.reserve3;
        this.reserve4 = outBean.reserve4;
        this.reserve5 = outBean.reserve5;
        this.reserve6 = outBean.reserve6;
        this.reserve7 = outBean.reserve7;
        this.reserve8 = outBean.reserve8;
        this.reserve9 = outBean.reserve9;
        this.curcredit = outBean.curcredit;
        this.staffcredit = outBean.staffcredit;
        this.managercredit = outBean.managercredit;
    }

    /**
     * default constructor
     */
    public OutBean()
    {
    }

    public String getSwbz() {
        return swbz;
    }

    public void setSwbz(String swbz) {
        this.swbz = swbz;
    }

    public int getRemoteAllocate() {
        return remoteAllocate;
    }

    public void setRemoteAllocate(int remoteAllocate) {
        this.remoteAllocate = remoteAllocate;
    }

    /**
     * @return the baseList
     */
    public List<BaseBean> getBaseList()
    {
        return baseList;
    }

    /**
     * @param baseList
     *            the baseList to set
     */
    public void setBaseList(List<BaseBean> baseList)
    {
        this.baseList = baseList;
    }

    /**
     * @return the department
     */
    public String getDepartment()
    {
        return department;
    }

    /**
     * @param department
     *            the department to set
     */
    public void setDepartment(String department)
    {
        this.department = department;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the fullId
     */
    public String getFullId()
    {
        return fullId;
    }

    /**
     * @param fullId
     *            the fullId to set
     */
    public void setFullId(String fullId)
    {
        this.fullId = fullId;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the total
     */
    public double getTotal()
    {
        return total;
    }

    /**
     * @param total
     *            the total to set
     */
    public void setTotal(double total)
    {
        this.total = total;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName()
    {
        return customerName;
    }

    /**
     * @param customerName
     *            the customerName to set
     */
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    /**
     * @return the connector
     */
    public String getConnector()
    {
        return connector;
    }

    /**
     * @param connector
     *            the connector to set
     */
    public void setConnector(String connector)
    {
        this.connector = connector;
    }

    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            the customerId to set
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    /**
     * @return the outTime
     */
    public String getOutTime()
    {
        return outTime;
    }

    /**
     * @param outTime
     *            the outTime to set
     */
    public void setOutTime(String outTime)
    {
        this.outTime = outTime;
    }

    /**
     * @return the stafferName
     */
    public String getStafferName()
    {
        return stafferName;
    }

    /**
     * @param stafferName
     *            the stafferName to set
     */
    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
    }

    /**
     * @return the outType
     */
    public int getOutType()
    {
        return outType;
    }

    /**
     * @param outType
     *            the outType to set
     */
    public void setOutType(int outType)
    {
        this.outType = outType;
    }

    public int getDropType() {
        return dropType;
    }

    public void setDropType(int dropType) {
        this.dropType = dropType;
    }

    /**
     * @return the status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * @return the phone
     */
    public String getPhone()
    {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    /**
     * @return the type
     */
    public int getType()
    {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(int type)
    {
        this.type = type;
    }

    /**
     * @return the checks
     */
    public String getChecks()
    {
        return checks;
    }

    /**
     * @param checks
     *            the checks to set
     */
    public void setChecks(String checks)
    {
        if (checks != null)
        {
            this.checks = checks;
        }
    }

    public boolean isCustomerCreated() {
        return customerCreated;
    }

    public void setCustomerCreated(boolean customerCreated) {
        this.customerCreated = customerCreated;
    }

    /**
     * @return the reday
     */
    public int getReday()
    {
        return reday;
    }

    /**
     * @param reday
     *            the reday to set
     */
    public void setReday(int reday)
    {
        this.reday = reday;
    }

    /**
     * @return the redate
     */
    public String getRedate()
    {
        return redate;
    }

    /**
     * @param redate
     *            the redate to set
     */
    public void setRedate(String redate)
    {
        this.redate = redate;
    }

    /**
     * @return the pay
     */
    public int getPay()
    {
        return pay;
    }

    /**
     * @param pay
     *            the pay to set
     */
    public void setPay(int pay)
    {
        this.pay = pay;
    }

    /**
     * @return the tempType
     */
    public int getTempType()
    {
        return tempType;
    }

    /**
     * @param tempType
     *            the tempType to set
     */
    public void setTempType(int tempType)
    {
        this.tempType = tempType;
    }

    /**
     * @return the hadPay
     */
    public double getHadPay()
    {
        return hadPay;
    }

    /**
     * @param hadPay
     *            the hadPay to set
     */
    public void setHadPay(double hadPay)
    {
        this.hadPay = hadPay;
    }

    /**
     * @param flowId
     *            the flowId to set
     */
    public void setFlowId(String flowId)
    {
        this.flowId = flowId;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId
     *            the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the marks
     */
    public int getMarks()
    {
        return marks;
    }

    /**
     * @param marks
     *            the marks to set
     */
    public void setMarks(int marks)
    {
        if (marks == 0)
        {
            this.setMark(false);
        }
        else
        {
            this.setMark(true);
        }

        this.marks = marks;
    }

    /**
     * @return the mark
     */
    public boolean isMark()
    {
        return mark;
    }

    /**
     * @param mark
     *            the mark to set
     */
    public void setMark(boolean mark)
    {
        this.mark = mark;
    }

    /**
     * @return 返回 arriveDate
     */
    public String getArriveDate()
    {
        return arriveDate;
    }

    /**
     * @param arriveDate
     */
    public void setArriveDate(String arriveDate)
    {
        this.arriveDate = arriveDate;
    }

    /**
     * @return 返回 location
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * @param location
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * @return the depotpartId
     */
    public String getDepotpartId()
    {
        return depotpartId;
    }

    /**
     * @param depotpartId
     *            the depotpartId to set
     */
    public void setDepotpartId(String depotpartId)
    {
        this.depotpartId = depotpartId;
    }

    /**
     * @return the destinationId
     */
    public String getDestinationId()
    {
        return destinationId;
    }

    /**
     * @param destinationId
     *            the destinationId to set
     */
    public void setDestinationId(String destinationId)
    {
        this.destinationId = destinationId;
    }

    /**
     * @return the refOutFullId
     */
    public String getRefOutFullId()
    {
        return refOutFullId;
    }

    /**
     * @param refOutFullId
     *            the refOutFullId to set
     */
    public void setRefOutFullId(String refOutFullId)
    {
        this.refOutFullId = refOutFullId;
    }

    /**
     * @return the inway
     */
    public int getInway()
    {
        return inway;
    }

    /**
     * @param inway
     *            the inway to set
     */
    public void setInway(int inway)
    {
        this.inway = inway;
    }

    /**
     * @return the consign
     */
    public int getConsign()
    {
        return consign;
    }

    /**
     * @param consign
     *            the consign to set
     */
    public void setConsign(int consign)
    {
        this.consign = consign;
    }

    /**
     * @return the tranNo
     */
    public String getTranNo()
    {
        return tranNo;
    }

    /**
     * @param tranNo
     *            the tranNo to set
     */
    public void setTranNo(String tranNo)
    {
        this.tranNo = tranNo;
    }

    /**
     * @return the stafferId
     */
    public String getStafferId()
    {
        return stafferId;
    }

    /**
     * @param stafferId
     *            the stafferId to set
     */
    public void setStafferId(String stafferId)
    {
        this.stafferId = stafferId;
    }

    /**
     * @return the reserve1
     */
    public int getReserve1()
    {
        return reserve1;
    }

    /**
     * @param reserve1
     *            the reserve1 to set
     */
    public void setReserve1(int reserve1)
    {
        this.reserve1 = reserve1;
    }

    /**
     * @return the reserve2
     */
    public int getReserve2()
    {
        return reserve2;
    }

    /**
     * @param reserve2
     *            the reserve2 to set
     */
    public void setReserve2(int reserve2)
    {
        this.reserve2 = reserve2;
    }

    /**
     * @return the reserve3
     */
    public int getReserve3()
    {
        return reserve3;
    }

    /**
     * @param reserve3
     *            the reserve3 to set
     */
    public void setReserve3(int reserve3)
    {
        this.reserve3 = reserve3;
    }

    /**
     * @return the reserve4
     */
    public String getReserve4()
    {
        return reserve4;
    }

    /**
     * @param reserve4
     *            the reserve4 to set
     */
    public void setReserve4(String reserve4)
    {
        this.reserve4 = reserve4;
    }

    /**
     * @return the reserve5
     */
    public String getReserve5()
    {
        return reserve5;
    }

    /**
     * @param reserve5
     *            the reserve5 to set
     */
    public void setReserve5(String reserve5)
    {
        this.reserve5 = reserve5;
    }

    /**
     * @return the reserve6
     */
    public String getReserve6()
    {
        return reserve6;
    }

    /**
     * @param reserve6
     *            the reserve6 to set
     */
    public void setReserve6(String reserve6)
    {
        this.reserve6 = reserve6;
    }

    /**
     * @return the curcredit
     */
    public double getCurcredit()
    {
        return curcredit;
    }

    /**
     * @param curcredit
     *            the curcredit to set
     */
    public void setCurcredit(double curcredit)
    {
        this.curcredit = curcredit;
    }

    /**
     * @return the staffcredit
     */
    public double getStaffcredit()
    {
        return staffcredit;
    }

    /**
     * @param staffcredit
     *            the staffcredit to set
     */
    public void setStaffcredit(double staffcredit)
    {
        this.staffcredit = staffcredit;
    }

    /**
     * @return the managerTime
     */
    public String getManagerTime()
    {
        return managerTime;
    }

    /**
     * @param managerTime
     *            the managerTime to set
     */
    public void setManagerTime(String managerTime)
    {
        this.managerTime = managerTime;
    }

    /**
     * @return the reserve7
     */
    public String getReserve7()
    {
        return reserve7;
    }

    /**
     * @param reserve7
     *            the reserve7 to set
     */
    public void setReserve7(String reserve7)
    {
        this.reserve7 = reserve7;
    }

    /**
     * @return the reserve8
     */
    public String getReserve8()
    {
        return reserve8;
    }

    /**
     * @param reserve8
     *            the reserve8 to set
     */
    public void setReserve8(String reserve8)
    {
        this.reserve8 = reserve8;
    }

    /**
     * @return the reserve9
     */
    public String getReserve9()
    {
        return reserve9;
    }

    /**
     * @param reserve9
     *            the reserve9 to set
     */
    public void setReserve9(String reserve9)
    {
        this.reserve9 = reserve9;
    }

    /**
     * @return the managercredit
     */
    public double getManagercredit()
    {
        return managercredit;
    }

    /**
     * @param managercredit
     *            the managercredit to set
     */
    public void setManagercredit(double managercredit)
    {
        this.managercredit = managercredit;
    }

    /**
     * @return the hasInvoice
     */
    public int getHasInvoice()
    {
        return hasInvoice;
    }

    /**
     * @param hasInvoice
     *            the hasInvoice to set
     */
    public void setHasInvoice(int hasInvoice)
    {
        this.hasInvoice = hasInvoice;
    }

    /**
     * @return the invoiceId
     */
    public String getInvoiceId()
    {
        return invoiceId;
    }

    /**
     * @param invoiceId
     *            the invoiceId to set
     */
    public void setInvoiceId(String invoiceId)
    {
        this.invoiceId = invoiceId;
    }

    /**
     * @return the dutyId
     */
    public String getDutyId()
    {
        return dutyId;
    }

    /**
     * @param dutyId
     *            the dutyId to set
     */
    public void setDutyId(String dutyId)
    {
        this.dutyId = dutyId;
    }

    /**
     * @return the managerId
     */
    public String getManagerId()
    {
        return managerId;
    }

    /**
     * @param managerId
     *            the managerId to set
     */
    public void setManagerId(String managerId)
    {
        this.managerId = managerId;
    }

    /**
     * @return the flowId
     */
    public String getFlowId()
    {
        return flowId;
    }

    /**
     * @return the invoiceStatus
     */
    public int getInvoiceStatus()
    {
        return invoiceStatus;
    }

    /**
     * @param invoiceStatus
     *            the invoiceStatus to set
     */
    public void setInvoiceStatus(int invoiceStatus)
    {
        this.invoiceStatus = invoiceStatus;
    }

    /**
     * @return the invoiceMoney
     */
    public double getInvoiceMoney()
    {
        return invoiceMoney;
    }

    /**
     * @param invoiceMoney
     *            the invoiceMoney to set
     */
    public void setInvoiceMoney(double invoiceMoney)
    {
        this.invoiceMoney = invoiceMoney;
    }

    /**
     * @return the badDebts
     */
    public double getBadDebts()
    {
        return badDebts;
    }

    /**
     * @param badDebts
     *            the badDebts to set
     */
    public void setBadDebts(double badDebts)
    {
        this.badDebts = badDebts;
    }

    /**
     * @return the industryId
     */
    public String getIndustryId()
    {
        return industryId;
    }

    /**
     * @param industryId
     *            the industryId to set
     */
    public void setIndustryId(String industryId)
    {
        this.industryId = industryId;
    }

    /**
     * @return the industryId2
     */
    public String getIndustryId2()
    {
        return industryId2;
    }

    /**
     * @param industryId2
     *            the industryId2 to set
     */
    public void setIndustryId2(String industryId2)
    {
        this.industryId2 = industryId2;
    }

    /**
     * @return the checkStatus
     */
    public int getCheckStatus()
    {
        return checkStatus;
    }

    /**
     * @param checkStatus
     *            the checkStatus to set
     */
    public void setCheckStatus(int checkStatus)
    {
        this.checkStatus = checkStatus;
    }

    /**
     * @return the badDebtsCheckStatus
     */
    public int getBadDebtsCheckStatus()
    {
        return badDebtsCheckStatus;
    }

    /**
     * @param badDebtsCheckStatus
     *            the badDebtsCheckStatus to set
     */
    public void setBadDebtsCheckStatus(int badDebtsCheckStatus)
    {
        this.badDebtsCheckStatus = badDebtsCheckStatus;
    }

    /**
     * @return the changeTime
     */
    public String getChangeTime()
    {
        return changeTime;
    }

    /**
     * @param changeTime
     *            the changeTime to set
     */
    public void setChangeTime(String changeTime)
    {
        this.changeTime = changeTime;
    }

    /**
     * @return the mtype
     */
    public int getMtype()
    {
        return mtype;
    }

    /**
     * @param mtype
     *            the mtype to set
     */
    public void setMtype(int mtype)
    {
        this.mtype = mtype;
    }

    /**
     * @return the sailType
     */
    public String getSailType()
    {
        return sailType;
    }

    /**
     * @param sailType
     *            the sailType to set
     */
    public void setSailType(String sailType)
    {
        this.sailType = sailType;
    }

    /**
     * @return the productType
     */
    public String getProductType()
    {
        return productType;
    }

    /**
     * @param productType
     *            the productType to set
     */
    public void setProductType(String productType)
    {
        this.productType = productType;
    }

    /**
     * @return the ratio
     */
    public String getRatio()
    {
        return ratio;
    }

    /**
     * @param ratio
     *            the ratio to set
     */
    public void setRatio(String ratio)
    {
        this.ratio = ratio;
    }

    /**
     * @return the vtype
     */
    public int getVtype()
    {
        return vtype;
    }

    /**
     * @param vtype
     *            the vtype to set
     */
    public void setVtype(int vtype)
    {
        this.vtype = vtype;
    }

    /**
     * @return the vtypeFullId
     */
    public String getVtypeFullId()
    {
        return vtypeFullId;
    }

    /**
     * @param vtypeFullId
     *            the vtypeFullId to set
     */
    public void setVtypeFullId(String vtypeFullId)
    {
        this.vtypeFullId = vtypeFullId;
    }

    /**
     * @return the pmtype
     */
    public int getPmtype()
    {
        return pmtype;
    }

    /**
     * @param pmtype
     *            the pmtype to set
     */
    public void setPmtype(int pmtype)
    {
        this.pmtype = pmtype;
    }

    /**
     * @return the industryId3
     */
    public String getIndustryId3()
    {
        return industryId3;
    }

    /**
     * @param industryId3
     *            the industryId3 to set
     */
    public void setIndustryId3(String industryId3)
    {
        this.industryId3 = industryId3;
    }

    /**
     * @return the payTime
     */
    public String getPayTime()
    {
        return payTime;
    }

    /**
     * @param payTime
     *            the payTime to set
     */
    public void setPayTime(String payTime)
    {
        this.payTime = payTime;
    }

    public int getForceBuyType() {
        return forceBuyType;
    }

    public void setForceBuyType(int forceBuyType) {
        this.forceBuyType = forceBuyType;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public double getPromValue() {
        return promValue;
    }

    public void setPromValue(double promValue) {
        this.promValue = promValue;
    }

    public int getPromStatus() {
        return promStatus;
    }

    public void setPromStatus(int promStatus) {
        this.promStatus = promStatus;
    }

    public String getRefBindOutId() {
        return refBindOutId;
    }

    public void setRefBindOutId(String refBindOutId) {
        this.refBindOutId = refBindOutId;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public String getOperator()
	{
		return operator;
	}

	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	public String getOperatorName()
	{
		return operatorName;
	}

	public void setOperatorName(String operatorName)
	{
		this.operatorName = operatorName;
	}

	public DistributionBean getDistributeBean()
	{
		return distributeBean;
	}

	public void setDistributeBean(DistributionBean distributeBean)
	{
		this.distributeBean = distributeBean;
	}

	public String getLogTime()
	{
		return logTime;
	}

	public void setLogTime(String logTime)
	{
		this.logTime = logTime;
	}

	public List<AttachmentBean> getAttachmentList()
	{
		return attachmentList;
	}

	public void setAttachmentList(List<AttachmentBean> attachmentList)
	{
		this.attachmentList = attachmentList;
	}

	public String getGuarantor()
	{
		return guarantor;
	}

	public void setGuarantor(String guarantor)
	{
		this.guarantor = guarantor;
	}

	public int getFeedBackVisit()
	{
		return feedBackVisit;
	}

	public void setFeedBackVisit(int feedBackVisit)
	{
		this.feedBackVisit = feedBackVisit;
	}

	public int getFeedBackCheck()
	{
		return feedBackCheck;
	}

	public void setFeedBackCheck(int feedBackCheck)
	{
		this.feedBackCheck = feedBackCheck;
	}

	public double getTaxTotal()
	{
		return taxTotal;
	}

	public void setTaxTotal(double taxTotal)
	{
		this.taxTotal = taxTotal;
	}

	/**
	 * @return the hasRebate
	 */
	public int getHasRebate()
	{
		return hasRebate;
	}

	/**
	 * @return the piDutyId
	 */
	public String getPiDutyId()
	{
		return piDutyId;
	}

	/**
	 * @param piDutyId the piDutyId to set
	 */
	public void setPiDutyId(String piDutyId)
	{
		this.piDutyId = piDutyId;
	}

	/**
	 * @return the piMtype
	 */
	public int getPiMtype()
	{
		return piMtype;
	}

	/**
	 * @param piMtype the piMtype to set
	 */
	public void setPiMtype(int piMtype)
	{
		this.piMtype = piMtype;
	}

	/**
	 * @return the piType
	 */
	public int getPiType()
	{
		return piType;
	}

	/**
	 * @param piType the piType to set
	 */
	public void setPiType(int piType)
	{
		this.piType = piType;
	}

	/**
	 * @return the piStatus
	 */
	public int getPiStatus()
	{
		return piStatus;
	}

	/**
	 * @param piStatus the piStatus to set
	 */
	public void setPiStatus(int piStatus)
	{
		this.piStatus = piStatus;
	}

	/**
	 * @return the hasConfirmInsMoney
	 */
	public double getHasConfirmInsMoney()
	{
		return hasConfirmInsMoney;
	}

	/**
	 * @param hasConfirmInsMoney the hasConfirmInsMoney to set
	 */
	public void setHasConfirmInsMoney(double hasConfirmInsMoney)
	{
		this.hasConfirmInsMoney = hasConfirmInsMoney;
	}

	/**
	 * @return the hasConfirm
	 */
	public int getHasConfirm()
	{
		return hasConfirm;
	}

	/**
	 * @param hasConfirm the hasConfirm to set
	 */
	public void setHasConfirm(int hasConfirm)
	{
		this.hasConfirm = hasConfirm;
	}

	/**
	 * @param hasRebate the hasRebate to set
	 */
	public void setHasRebate(int hasRebate)
	{
		this.hasRebate = hasRebate;
	}

	/**
	 * @return the emergency
	 */
	public int getEmergency() {
		return emergency;
	}

	/**
	 * @param emergency the emergency to set
	 */
	public void setEmergency(int emergency) {
		this.emergency = emergency;
	}

	/**
	 * @return the presentFlag
	 */
	public int getPresentFlag() {
		return presentFlag;
	}

	/**
	 * @param presentFlag the presentFlag to set
	 */
	public void setPresentFlag(int presentFlag) {
		this.presentFlag = presentFlag;
	}

    /**
     * @return the podate
     */
    public String getPodate() {
        return podate;
    }

    /**
     * @param podate the podate to set
     */
    public void setPodate(String podate) {
        this.podate = podate;
    }

    /**
	 * @return the distList
	 */
	public List<DistributionBean> getDistList()
	{
		return distList;
	}

	/**
	 * @param distList the distList to set
	 */
	public void setDistList(List<DistributionBean> distList)
	{
		this.distList = distList;
	}

    public int getIbFlag() {
        return ibFlag;
    }

    public void setIbFlag(int ibFlag) {
        this.ibFlag = ibFlag;
    }

    public int getMotivationFlag() {
        return motivationFlag;
    }

    public void setMotivationFlag(int motivationFlag) {
        this.motivationFlag = motivationFlag;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * get ZJRC fullId from description
     * @return
     */
    public String getZjrcFullId(){
        String zjrcFullId = "";
        if (!StringTools.isNullOrNone(description)){
            String[] temp1 = description.split("\\.");
            if (temp1.length>=2){
                zjrcFullId = temp1[1].trim();
            }
        }
        return zjrcFullId;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public String getOutbackStatus() {
        return outbackStatus;
    }

    public void setOutbackStatus(String outbackStatus) {
        this.outbackStatus = outbackStatus;
    }

    public String getFlowTime() {
        return flowTime;
    }

    public void setFlowTime(String flowTime) {
        this.flowTime = flowTime;
    }

    public String getIbApplyId() {
        return ibApplyId;
    }

    public void setIbApplyId(String ibApplyId) {
        this.ibApplyId = ibApplyId;
    }

    public String getMotivationApplyId() {
        return motivationApplyId;
    }

    public void setMotivationApplyId(String motivationApplyId) {
        this.motivationApplyId = motivationApplyId;
    }

    public String getRefGiftId() {
        return refGiftId;
    }

    public void setRefGiftId(String refGiftId) {
        this.refGiftId = refGiftId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getCashFlag() {
        return cashFlag;
    }

    public void setCashFlag(int cashFlag) {
        this.cashFlag = cashFlag;
    }

    public int getProfigFlag() {
        return profigFlag;
    }

    public void setProfigFlag(int profigFlag) {
        this.profigFlag = profigFlag;
    }

    public int getIbFlag2() {
        return ibFlag2;
    }

    public void setIbFlag2(int ibFlag2) {
        this.ibFlag2 = ibFlag2;
    }

    public String getIbApplyId2() {
        return ibApplyId2;
    }

    public void setIbApplyId2(String ibApplyId2) {
        this.ibApplyId2 = ibApplyId2;
    }

    public int getMotivationFlag2() {
        return motivationFlag2;
    }

    public void setMotivationFlag2(int motivationFlag2) {
        this.motivationFlag2 = motivationFlag2;
    }

    public String getMotivationApplyId2() {
        return motivationApplyId2;
    }

    public void setMotivationApplyId2(String motivationApplyId2) {
        this.motivationApplyId2 = motivationApplyId2;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }

    public void setCustomerDescription(String customerDescription) {
        this.customerDescription = customerDescription;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getPosPayer() {
        return posPayer;
    }

    public void setPosPayer(String posPayer) {
        this.posPayer = posPayer;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public int getPlatformFlag() {
        return platformFlag;
    }

    public void setPlatformFlag(int platformFlag) {
        this.platformFlag = platformFlag;
    }

    public String getPlatformApplyId() {
        return platformApplyId;
    }

    public void setPlatformApplyId(String platformApplyId) {
        this.platformApplyId = platformApplyId;
    }

    public boolean isFrFlag() {
        return frFlag;
    }

    public void setFrFlag(boolean frFlag) {
        this.frFlag = frFlag;
    }

    public int getVirtualStatus() {
        return virtualStatus;
    }

    public void setVirtualStatus(int virtualStatus) {
        this.virtualStatus = virtualStatus;
    }
    

    public int getBuyReturnFlag() {
		return buyReturnFlag;
	}

	public void setBuyReturnFlag(int buyReturnFlag) {
		this.buyReturnFlag = buyReturnFlag;
	}

	public int getBuyReturnType() {
		return buyReturnType;
	}

	public void setBuyReturnType(int buyReturnType) {
		this.buyReturnType = buyReturnType;
	}

    public int getBackPay() {
        return backPay;
    }

    public void setBackPay(int backPay) {
        this.backPay = backPay;
    }

    /**
     * Constructs a <code>String</code> with all attributes in name = value format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        return "OutBean{" +
                "fullId='" + fullId + '\'' +
                ", id='" + id + '\'' +
                ", flowId='" + flowId + '\'' +
                ", outTime='" + outTime + '\'' +
                ", managerTime='" + managerTime + '\'' +
                ", changeTime='" + changeTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", flowTime='" + flowTime + '\'' +
                ", outType=" + outType +
                ", type=" + type +
                ", vtype=" + vtype +
                ", mtype=" + mtype +
                ", pmtype=" + pmtype +
                ", hasInvoice=" + hasInvoice +
                ", invoiceId='" + invoiceId + '\'' +
                ", dutyId='" + dutyId + '\'' +
                ", status=" + status +
                ", invoiceStatus=" + invoiceStatus +
                ", invoiceMoney=" + invoiceMoney +
                ", department='" + department + '\'' +
                ", customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", locationId='" + locationId + '\'' +
                ", location='" + location + '\'' +
                ", industryId='" + industryId + '\'' +
                ", industryId2='" + industryId2 + '\'' +
                ", industryId3='" + industryId3 + '\'' +
                ", connector='" + connector + '\'' +
                ", phone='" + phone + '\'' +
                ", stafferName='" + stafferName + '\'' +
                ", stafferId='" + stafferId + '\'' +
                ", managerId='" + managerId + '\'' +
                ", total=" + total +
                ", description='" + description + '\'' +
                ", customerDescription='" + customerDescription + '\'' +
                ", checks='" + checks + '\'' +
                ", checkStatus=" + checkStatus +
                ", reday=" + reday +
                ", redate='" + redate + '\'' +
                ", depotpartId='" + depotpartId + '\'' +
                ", marks=" + marks +
                ", mark=" + mark +
                ", consign=" + consign +
                ", frFlag=" + frFlag +
                ", pay=" + pay +
                ", backPay=" + backPay +
                ", inway=" + inway +
                ", tempType=" + tempType +
                ", hadPay=" + hadPay +
                ", badDebts=" + badDebts +
                ", badDebtsCheckStatus=" + badDebtsCheckStatus +
                ", arriveDate='" + arriveDate + '\'' +
                ", destinationId='" + destinationId + '\'' +
                ", refOutFullId='" + refOutFullId + '\'' +
                ", vtypeFullId='" + vtypeFullId + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", reserve1=" + reserve1 +
                ", reserve2=" + reserve2 +
                ", reserve3=" + reserve3 +
                ", reserve4='" + reserve4 + '\'' +
                ", reserve5='" + reserve5 + '\'' +
                ", reserve6='" + reserve6 + '\'' +
                ", reserve7='" + reserve7 + '\'' +
                ", reserve8='" + reserve8 + '\'' +
                ", reserve9='" + reserve9 + '\'' +
                ", sailType='" + sailType + '\'' +
                ", productType='" + productType + '\'' +
                ", ratio='" + ratio + '\'' +
                ", curcredit=" + curcredit +
                ", staffcredit=" + staffcredit +
                ", managercredit=" + managercredit +
                ", forceBuyType=" + forceBuyType +
                ", eventId='" + eventId + '\'' +
                ", promValue=" + promValue +
                ", promStatus=" + promStatus +
                ", refBindOutId='" + refBindOutId + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", operator='" + operator + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", logTime='" + logTime + '\'' +
                ", feedBackVisit=" + feedBackVisit +
                ", feedBackCheck=" + feedBackCheck +
                ", hasRebate=" + hasRebate +
                ", guarantor='" + guarantor + '\'' +
                ", taxTotal=" + taxTotal +
                ", piDutyId='" + piDutyId + '\'' +
                ", piMtype=" + piMtype +
                ", piType=" + piType +
                ", piStatus=" + piStatus +
                ", hasConfirmInsMoney=" + hasConfirmInsMoney +
                ", hasConfirm=" + hasConfirm +
                ", emergency=" + emergency +
                ", presentFlag=" + presentFlag +
                ", podate='" + podate + '\'' +
                ", ibFlag=" + ibFlag +
                ", ibApplyId='" + ibApplyId + '\'' +
                ", motivationFlag=" + motivationFlag +
                ", motivationApplyId='" + motivationApplyId + '\'' +
                ", ibFlag2=" + ibFlag2 +
                ", ibApplyId2='" + ibApplyId2 + '\'' +
                ", motivationFlag2=" + motivationFlag2 +
                ", motivationApplyId2='" + motivationApplyId2 + '\'' +
                ", platformFlag=" + platformFlag +
                ", platformApplyId='" + platformApplyId + '\'' +
                ", remoteAllocate=" + remoteAllocate +
                ", reason='" + reason + '\'' +
                ", transportNo='" + transportNo + '\'' +
                ", swbz='" + swbz + '\'' +
                ", outbackStatus='" + outbackStatus + '\'' +
                ", customerCreated=" + customerCreated +
                ", refGiftId='" + refGiftId + '\'' +
                ", channel='" + channel + '\'' +
                ", cashFlag=" + cashFlag +
                ", profigFlag=" + profigFlag +
                ", delivery='" + delivery + '\'' +
                ", posPayer='" + posPayer + '\'' +
                ", recommendation='" + recommendation + '\'' +
                ", virtualStatus=" + virtualStatus +
                ", buyReturnFlag=" + buyReturnFlag +
                ", buyReturnType=" + buyReturnType +
                '}';
    }
}
