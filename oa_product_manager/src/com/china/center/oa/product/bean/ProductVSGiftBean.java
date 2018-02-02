package com.china.center.oa.product.bean;

import java.io.Serializable;

import com.china.center.jdbc.annotation.*;
import com.china.center.jdbc.annotation.enums.Element;
import com.china.center.jdbc.annotation.enums.JoinType;

@SuppressWarnings("serial")
@Entity(name = "中信产品对应赠品")
@Table(name = "T_CENTER_VS_GIFT")
public class ProductVSGiftBean implements Serializable
{
	@Id(autoIncrement = true)
	private String id = "";
	
	@FK
    @Html(title = "销售商品品名", type = Element.INPUT, name = "productName", readonly = true)
	@Join(tagClass = ProductBean.class, type = JoinType.LEFT, alias = "P1")
	private String productId = "";

    @Html(title = "赠送商品品名", type = Element.INPUT, name = "giftProductName", readonly = true)
	@Join(tagClass = ProductBean.class, type = JoinType.LEFT, alias = "P2")
	private String giftProductId = "";

    @Html(title = "赠送商品数量", must = true, maxLength = 100)
	private int amount = 0;

    @Html(title = "赠送商品品名2", type = Element.INPUT, name = "giftProductName2", readonly = true)
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT, alias = "P3")
    private String giftProductId2 = "";

    @Html(title = "赠送商品数量2", maxLength = 100)
    private int amount2 = 0;

    @Html(title = "赠送商品品名3", type = Element.INPUT, name = "giftProductName3", readonly = true)
    @Join(tagClass = ProductBean.class, type = JoinType.LEFT, alias = "P4")
    private String giftProductId3 = "";

    @Html(title = "赠送商品数量3", maxLength = 100)
    private int amount3 = 0;

    @Html(title = "销售商品数量", must = true, maxLength = 100)
    private int sailAmount = 0;

    /**
     * 活动描述
     */
    @Html(title = "活动描述", must = true, maxLength = 100)
    private String activity = "";

    /**
     * 适用银行
     */
    @Html(title = "适用银行", must = true, maxLength = 100)
    private String bank = "";

    /**
     * 不适用银行
     */
    @Html(title = "不适用银行", must = false, maxLength = 100)
    private String excludeBank = "";

    /**
     * 开始日期
     */
    @Html(title = "开始日期", must = true, type= Element.DATE)
    private String beginDate = "";

    /**
     * 结束日期
     */
    @Html(title = "结束日期", must = true,type=Element.DATE)
    private String endDate = "";

    /**
     * 备注
     */
    @Html(title = "备注", type = Element.TEXTAREA, maxLength = 255)
    private String description = "";

    /**
     * 2016/1/5 #156 增加事业部，大区，部门，人员，城市的选择字段
     * 多选，用分号;分割
     */


    @Html(title = "事业部", maxLength = 100)
    private String industryName = "";

    @Html(title = "大区", maxLength = 100)
    private String industryName2 = "";

    @Html(title = "部门", maxLength = 100)
    private String industryName3 = "";


    /**
     * 城市
     */
    @Html(title = "城市", maxLength = 100)
    private String city = "";

    /**
     * 省份
     */
    @Html(title = "省份", maxLength = 100)
    private String province = "";

    @Html(title = "人员", maxLength = 100)
    private String stafferName = "";


    /**
     * #127 增加不包含字段
     */
    @Html(title = "不包含事业部", maxLength = 100)
    private String excludeIndustryName = "";

    @Html(title = "不包含大区", maxLength = 100)
    private String excludeIndustryName2 = "";

    @Html(title = "不包含部门", maxLength = 100)
    private String excludeIndustryName3 = "";

    @Html(title = "不包含城市", maxLength = 100)
    private String excludeCity = "";

    @Html(title = "公司承担比例", maxLength = 10)
    private int companyShare = 0;

    @Html(title = "个人承担比例", maxLength = 10)
    private int stafferShare = 0;


    /**
     * #135 增加承担比例
     */
    @Html(title = "不包含省份", maxLength = 100)
    private String excludeProvince = "";

    @Html(title = "不包含人员", maxLength = 100)
    private String excludeStafferName = "";

    @Html(title = "分行", maxLength = 100)
    private String branchName ="";

    @Html(title = "不包含分行", maxLength = 100)
    private String excludeBranchName ="";

    @Html(title = "支行", maxLength = 100)
    private String customerName = "";

    @Html(title = "不包含支行", maxLength = 100)
    private String excludeCustomerName = "";

    @Html(title = "渠道", maxLength = 10)
    private String channel = "";

    @Html(title = "不包含渠道", maxLength = 10)
    private String excludeChannel = "";

    private String createTime = "";

    private String creator = "";

    public ProductVSGiftBean()
	{
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getProductId()
	{
		return productId;
	}

	public void setProductId(String productId)
	{
		this.productId = productId;
	}

	public String getGiftProductId()
	{
		return giftProductId;
	}

	public void setGiftProductId(String giftProductId)
	{
		this.giftProductId = giftProductId;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getSailAmount() {
        return sailAmount;
    }

    public void setSailAmount(int sailAmount) {
        this.sailAmount = sailAmount;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getIndustryName2() {
        return industryName2;
    }

    public void setIndustryName2(String industryName2) {
        this.industryName2 = industryName2;
    }

    public String getIndustryName3() {
        return industryName3;
    }

    public void setIndustryName3(String industryName3) {
        this.industryName3 = industryName3;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStafferName() {
        return stafferName;
    }

    public void setStafferName(String stafferName) {
        this.stafferName = stafferName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getGiftProductId2() {
        return giftProductId2;
    }

    public void setGiftProductId2(String giftProductId2) {
        this.giftProductId2 = giftProductId2;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }

    public String getGiftProductId3() {
        return giftProductId3;
    }

    public void setGiftProductId3(String giftProductId3) {
        this.giftProductId3 = giftProductId3;
    }

    public int getAmount3() {
        return amount3;
    }

    public void setAmount3(int amount3) {
        this.amount3 = amount3;
    }

    public String getExcludeBank() {
        return excludeBank;
    }

    public void setExcludeBank(String excludeBank) {
        this.excludeBank = excludeBank;
    }

    public String getExcludeIndustryName() {
        return excludeIndustryName;
    }

    public void setExcludeIndustryName(String excludeIndustryName) {
        this.excludeIndustryName = excludeIndustryName;
    }

    public String getExcludeIndustryName2() {
        return excludeIndustryName2;
    }

    public void setExcludeIndustryName2(String excludeIndustryName2) {
        this.excludeIndustryName2 = excludeIndustryName2;
    }

    public String getExcludeIndustryName3() {
        return excludeIndustryName3;
    }

    public void setExcludeIndustryName3(String excludeIndustryName3) {
        this.excludeIndustryName3 = excludeIndustryName3;
    }

    public String getExcludeCity() {
        return excludeCity;
    }

    public void setExcludeCity(String excludeCity) {
        this.excludeCity = excludeCity;
    }

    public String getExcludeProvince() {
        return excludeProvince;
    }

    public void setExcludeProvince(String excludeProvince) {
        this.excludeProvince = excludeProvince;
    }

    public String getExcludeStafferName() {
        return excludeStafferName;
    }

    public void setExcludeStafferName(String excludeStafferName) {
        this.excludeStafferName = excludeStafferName;
    }

    public int getCompanyShare() {
        return companyShare;
    }

    public void setCompanyShare(int companyShare) {
        this.companyShare = companyShare;
    }

    public int getStafferShare() {
        return stafferShare;
    }

    public void setStafferShare(int stafferShare) {
        this.stafferShare = stafferShare;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getExcludeBranchName() {
        return excludeBranchName;
    }

    public void setExcludeBranchName(String excludeBranchName) {
        this.excludeBranchName = excludeBranchName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getExcludeCustomerName() {
        return excludeCustomerName;
    }

    public void setExcludeCustomerName(String excludeCustomerName) {
        this.excludeCustomerName = excludeCustomerName;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getExcludeChannel() {
        return excludeChannel;
    }

    public void setExcludeChannel(String excludeChannel) {
        this.excludeChannel = excludeChannel;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "ProductVSGiftBean{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", giftProductId='" + giftProductId + '\'' +
                ", amount=" + amount +
                ", giftProductId2='" + giftProductId2 + '\'' +
                ", amount2=" + amount2 +
                ", giftProductId3='" + giftProductId3 + '\'' +
                ", amount3=" + amount3 +
                ", sailAmount=" + sailAmount +
                ", activity='" + activity + '\'' +
                ", bank='" + bank + '\'' +
                ", excludeBank='" + excludeBank + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", description='" + description + '\'' +
                ", industryName='" + industryName + '\'' +
                ", industryName2='" + industryName2 + '\'' +
                ", industryName3='" + industryName3 + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", stafferName='" + stafferName + '\'' +
                ", excludeIndustryName='" + excludeIndustryName + '\'' +
                ", excludeIndustryName2='" + excludeIndustryName2 + '\'' +
                ", excludeIndustryName3='" + excludeIndustryName3 + '\'' +
                ", excludeCity='" + excludeCity + '\'' +
                ", companyShare=" + companyShare +
                ", stafferShare=" + stafferShare +
                ", excludeProvince='" + excludeProvince + '\'' +
                ", excludeStafferName='" + excludeStafferName + '\'' +
                '}';
    }
}
