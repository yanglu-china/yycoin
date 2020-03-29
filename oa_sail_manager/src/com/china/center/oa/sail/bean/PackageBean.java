package com.china.center.oa.sail.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.FK;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Join;
import com.china.center.jdbc.annotation.Table;
import com.china.center.jdbc.annotation.enums.JoinType;
import com.china.center.oa.client.bean.CustomerBean;
import com.china.center.oa.publics.bean.PrincipalshipBean;
import com.china.center.oa.sail.constanst.ShipConstant;
import com.china.center.tools.ListTools;

@SuppressWarnings("serial")
@Entity(name = "发货单（包）")
@Table(name = "T_CENTER_PACKAGE")
public class PackageBean implements Serializable
{
    /**
     * ID
     */
    @Id
    private String id = "";

    /**
     * 类型:1代表招商银行直邮，2代表京东，3代表天猫，其他后续再更新
     */
    private int type = 0;

    @Join(tagClass = CustomerBean.class, type = JoinType.LEFT)
    private String customerId = "";

    /**
     * 发货方式
     */
    private int shipping = 0;

    /**
     * 运输方式1 - 快递使用
     */
    @Join(tagClass = ExpressBean.class, type = JoinType.LEFT, alias = "e1")
    private int transport1 = 0;

    /**
     * 快递运费支付方式 - deliverPay
     */
    private int expressPay = -1;

    /**
     * 货运
     */
    @Join(tagClass = ExpressBean.class, type = JoinType.LEFT, alias = "e2")
    private int transport2 = 0;

    /**
     * 货运运费支付方式 - deliverPay
     */
    private int transportPay = -1;

    /**
     * 完整的收货地址
     */
    private String address = "";

    /**
     * 收货人
     */
    private String receiver = "";

    /**
     * 移动电话
     */
    private String mobile = "";

    private String telephone = "";

    /**
     * 数量合计
     */
    private int amount = 0;

    /**
     * 商品种类数
     */
    private int productCount = 0;

    /**
     * 金额合计
     */
    private double total = 0.0d;

    /**
     * 状态 - 0:初始; 1:已拣配， 99:已发货
     */
    private int status = ShipConstant.SHIP_STATUS_INIT;

    /**
     * 申请人
     */
    private String stafferName = "";

    /**
     * 事业部
     */
    private String industryName = "";

    /**
     * 部门
     */
    private String departName = "";

    /**
     * 地点
     */
    @Join(tagClass = PrincipalshipBean.class, type = JoinType.LEFT)
    private String locationId = "";

    /**
     * #408 生成时间
     */
    private String createTime = "";

    private String logTime = "";

    /**
     * 拣配
     */
    @FK
    private String pickupId = "";

    private String pickupTime = "";

    /**
     * #414 回执单打印时间
     */
    private String printTime = "";

    private int index_pos = 0;

    /**
     * 发货时间
     */
    private String shipTime = "";

    private String cityId = "";

    /**
     * 销售单 紧急 标识 1:紧急
     */
    private int emergency = 0;

    /**
     *发送邮件标识 1:已发送
     */
    private int sendMailFlag = 0;

    /**#228
     * 发送邮件给销售人员标志
     */
    private int sendMailFlagSails = 0;

    /**
     * 2015/12/22 宁波银行邮件
     *发送邮件标识 1:已发送
     */
    private int sendMailFlagNbyh = 0;

    /**2015/3/18 取CK单中距当前时间最长的单据创建时间
     *单据时间
     */
    private String billsTime = "";

    /**
     * 2015/3/22 发票单发指CK单中只有A或FP开头的单号，没有其他类型的订单
     * 此字段由后台Job生成
     */
    private int insFollowOut = ShipConstant.INVOICE_SHIP_FOLLOW_OUT;

    /**
     * #300 赠品单发
     */
    private int zsFollowOut = ShipConstant.ZS_SHIP_FOLLOW_OUT;

    //2015/6/27 顺丰发货单号
    private String transportNo = "";
    //2015/6/27 顺丰收货日期
    private String sfReceiveDate = "";

    /**#102
     * 直邮标识
     */
    private int direct = 0;

    @Ignore
    List<PackageItemBean> itemList = null;

    /**
     * #639
     */
    @Ignore
    List<PackageItemBean> dgnsItemList = null;

    /**
     * #626
     */
    @Ignore
    Map<String,List<PackageItemBean>> itemMap = null;

    public PackageBean()
    {
    }

    public int getZsFollowOut() {
        return zsFollowOut;
    }

    public void setZsFollowOut(int zsFollowOut) {
        this.zsFollowOut = zsFollowOut;
    }

    public String getTransportNo() {
        return transportNo;
    }

    public void setTransportNo(String transportNo) {
        this.transportNo = transportNo;
    }

    public String getSfReceiveDate() {
        return sfReceiveDate;
    }

    public void setSfReceiveDate(String sfReceiveDate) {
        this.sfReceiveDate = sfReceiveDate;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }

    public int getShipping()
    {
        return shipping;
    }

    public void setShipping(int shipping)
    {
        this.shipping = shipping;
    }

    public int getTransport1()
    {
        return transport1;
    }

    public void setTransport1(int transport1)
    {
        this.transport1 = transport1;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getReceiver()
    {
        return receiver;
    }

    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public int getAmount()
    {
        return amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public double getTotal()
    {
        return total;
    }

    public void setTotal(double total)
    {
        this.total = total;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getLogTime()
    {
        return logTime;
    }

    public void setLogTime(String logTime)
    {
        this.logTime = logTime;
    }

    public String getPickupId()
    {
        return pickupId;
    }

    public void setPickupId(String pickupId)
    {
        this.pickupId = pickupId;
    }

    public int getIndex_pos()
    {
        return index_pos;
    }

    public void setIndex_pos(int index_pos)
    {
        this.index_pos = index_pos;
    }

    public List<PackageItemBean> getItemList()
    {
        return itemList;
    }

    public void setItemList(List<PackageItemBean> itemList)
    {
        this.itemList = itemList;
    }

    public String getStafferName()
    {
        return stafferName;
    }

    public void setStafferName(String stafferName)
    {
        this.stafferName = stafferName;
    }

    public String getIndustryName()
    {
        return industryName;
    }

    public void setIndustryName(String industryName)
    {
        this.industryName = industryName;
    }

    public String getDepartName()
    {
        return departName;
    }

    public void setDepartName(String departName)
    {
        this.departName = departName;
    }

    /**
     * @return the expressPay
     */
    public int getExpressPay()
    {
        return expressPay;
    }

    /**
     * @param expressPay the expressPay to set
     */
    public void setExpressPay(int expressPay)
    {
        this.expressPay = expressPay;
    }

    /**
     * @return the transport2
     */
    public int getTransport2()
    {
        return transport2;
    }

    /**
     * @param transport2 the transport2 to set
     */
    public void setTransport2(int transport2)
    {
        this.transport2 = transport2;
    }

    /**
     * @return the transportPay
     */
    public int getTransportPay()
    {
        return transportPay;
    }

    /**
     * @param transportPay the transportPay to set
     */
    public void setTransportPay(int transportPay)
    {
        this.transportPay = transportPay;
    }

    /**
     * @return the productCount
     */
    public int getProductCount()
    {
        return productCount;
    }

    /**
     * @param productCount the productCount to set
     */
    public void setProductCount(int productCount)
    {
        this.productCount = productCount;
    }

    /**
     * @return the locationId
     */
    public String getLocationId()
    {
        return locationId;
    }

    /**
     * @param locationId the locationId to set
     */
    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }

    /**
     * @return the shipTime
     */
    public String getShipTime()
    {
        return shipTime;
    }

    /**
     * @param shipTime the shipTime to set
     */
    public void setShipTime(String shipTime)
    {
        this.shipTime = shipTime;
    }

    /**
     * @return the cityId
     */
    public String getCityId()
    {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(String cityId)
    {
        this.cityId = cityId;
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
     * @return the sendMailFlag
     */
    public int getSendMailFlag() {
        return sendMailFlag;
    }

    /**
     * @param sendMailFlag the sendMailFlag to set
     */
    public void setSendMailFlag(int sendMailFlag) {
        this.sendMailFlag = sendMailFlag;
    }


    /**
     * @return the billsTime
     */
    public String getBillsTime() {
        return billsTime;
    }


    /**
     * @param billsTime the billsTime to set
     */
    public void setBillsTime(String billsTime) {
        this.billsTime = billsTime;
    }

    /**
     * @return the insFollowOut
     */
    public int getInsFollowOut() {
        return insFollowOut;
    }


    /**
     * @param insFollowOut the insFollowOut to set
     */
    public void setInsFollowOut(int insFollowOut) {
        this.insFollowOut = insFollowOut;
    }

    public int getSendMailFlagNbyh() {
        return sendMailFlagNbyh;
    }

    public void setSendMailFlagNbyh(int sendMailFlagNbyh) {
        this.sendMailFlagNbyh = sendMailFlagNbyh;
    }

    public int getSendMailFlagSails() {
        return sendMailFlagSails;
    }

    public void setSendMailFlagSails(int sendMailFlagSails) {
        this.sendMailFlagSails = sendMailFlagSails;
    }

    /**
     * #328
     */
    public void setPrintInvoiceinsStatus(List<PackageItemBean> items){
        if (!ListTools.isEmptyOrNull(items)){
            for (PackageItemBean item: items){
                String productName = item.getProductName();
                if (productName!= null && productName.startsWith("发票号：XN")){
                    this.status = ShipConstant.SHIP_STATUS_PRINT_INVOICEINS;
                    break;
                }
            }
        }
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<PackageItemBean> getDgnsItemList() {
        return dgnsItemList;
    }

    public void setDgnsItemList(List<PackageItemBean> dgnsItemList) {
        this.dgnsItemList = dgnsItemList;
    }

    public Map<String, List<PackageItemBean>> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, List<PackageItemBean>> itemMap) {
        this.itemMap = itemMap;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "PackageBean{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", shipping=" + shipping +
                ", transport1=" + transport1 +
                ", expressPay=" + expressPay +
                ", transport2=" + transport2 +
                ", transportPay=" + transportPay +
                ", address='" + address + '\'' +
                ", receiver='" + receiver + '\'' +
                ", mobile='" + mobile + '\'' +
                ", amount=" + amount +
                ", productCount=" + productCount +
                ", total=" + total +
                ", status=" + status +
                ", stafferName='" + stafferName + '\'' +
                ", industryName='" + industryName + '\'' +
                ", departName='" + departName + '\'' +
                ", locationId='" + locationId + '\'' +
                ", logTime='" + logTime + '\'' +
                ", pickupId='" + pickupId + '\'' +
                ", index_pos=" + index_pos +
                ", shipTime='" + shipTime + '\'' +
                ", cityId='" + cityId + '\'' +
                ", emergency=" + emergency +
                ", sendMailFlag=" + sendMailFlag +
                ", sendMailFlagSails=" + sendMailFlagSails +
                ", sendMailFlagNbyh=" + sendMailFlagNbyh +
                ", billsTime='" + billsTime + '\'' +
                ", insFollowOut=" + insFollowOut +
                ", zsFollowOut=" + zsFollowOut +
                ", transportNo='" + transportNo + '\'' +
                ", sfReceiveDate='" + sfReceiveDate + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}