/**
 *
 */
package com.china.center.oa.stock.vo;


import java.util.List;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Ignore;
import com.china.center.jdbc.annotation.Relationship;
//import com.china.center.oa.sail.bean.BaseBean;
import com.china.center.oa.stock.bean.StockItemBean;


/**
 * @author Administrator
 */
@Entity(inherit = true)
public class StockItemVO extends StockItemBean
{
    @Relationship(relationField = "productId", tagField = "name")
    private String productName = "";

    @Relationship(relationField = "productId", tagField = "code")
    private String productCode = "";
    
    @Relationship(relationField = "productId", tagField = "cost")
    private String productCost = "";
    
    @Relationship(relationField = "depotpartId", tagField = "name")
    private String depotpartName = "";
    
    @Relationship(relationField = "invoiceType", tagField = "name")
    private String invoiceTypeName = "";

    @Relationship(relationField = "providerId", tagField = "name")
    private String providerName = "";

    @Relationship(relationField = "stockId", tagField = "logTime")
    private String stockTime = "";

    @Relationship(relationField = "stafferId")
    private String stafferName = "";

    @Relationship(relationField = "dutyId")
    private String dutyName = "";

    @Ignore
    private List<PriceAskProviderBeanVO> asksVo = null;
    
    /**
     * 已勾稽金额
     */
    @Ignore
    protected double totalGj = 0.0d;

    /**
     * 2014/12/15 拿货功能增强显示拿货历史记录
     */
//    @Ignore
//    private List<BaseBean> baseBeans = null;

    /**
     *
     */
    public StockItemVO()
    {
    }

    /**
     * @return the productName
     */
    public String getProductName()
    {
        return productName;
    }

    /**
     * @param productName
     *            the productName to set
     */
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    /**
     * @return the asksVo
     */
    public List<PriceAskProviderBeanVO> getAsksVo()
    {
        return asksVo;
    }

    /**
     * @param asksVo
     *            the asksVo to set
     */
    public void setAsksVo(List<PriceAskProviderBeanVO> asksVo)
    {
        this.asksVo = asksVo;
    }

    /**
     * @return the productCode
     */
    public String getProductCode()
    {
        return productCode;
    }

    /**
     * @param productCode
     *            the productCode to set
     */
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }

	public String getProductCost() {
		return productCost;
	}

	public void setProductCost(String productCost) {
		this.productCost = productCost;
	}

	public String getDepotpartName() {
		return depotpartName;
	}

	public void setDepotpartName(String depotpartName) {
		this.depotpartName = depotpartName;
	}

	public String getInvoiceTypeName() {
		return invoiceTypeName;
	}

	public void setInvoiceTypeName(String invoiceTypeName) {
		this.invoiceTypeName = invoiceTypeName;
	}

	/**
     * @return the providerName
     */
    public String getProviderName()
    {
        return providerName;
    }

    /**
     * @param providerName
     *            the providerName to set
     */
    public void setProviderName(String providerName)
    {
        this.providerName = providerName;
    }

    /**
     * @return the stockTime
     */
    public String getStockTime()
    {
        return stockTime;
    }

    /**
     * @param stockTime
     *            the stockTime to set
     */
    public void setStockTime(String stockTime)
    {
        this.stockTime = stockTime;
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
     * @return the dutyName
     */
    public String getDutyName()
    {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName)
    {
        this.dutyName = dutyName;
    }

//    public List<BaseBean> getBaseBeans() {
//        return baseBeans;
//    }
//
//    public void setBaseBeans(List<BaseBean> baseBeans) {
//        this.baseBeans = baseBeans;
//    }

    public double getTotalGj() {
		return totalGj;
	}

	public void setTotalGj(double totalGj) {
		this.totalGj = totalGj;
	}

	/**
     * Constructs a <code>String</code> with all attributes in name = value format.
     * 
     * @return a <code>String</code> representation of this object.
     */
    public String toString()
    {
        final String TAB = ",";

        StringBuilder retValue = new StringBuilder();

        retValue
            .append("StockItemVO ( ")
            .append(super.toString())
            .append(TAB)
            .append("productName = ")
            .append(this.productName)
            .append("productId = ")
            .append(this.productId)
            .append(TAB)
            .append("productCode = ")
            .append(this.productCode)
            .append(TAB)
            .append("providerName = ")
            .append(this.providerName)
            .append(TAB)
            .append("stockTime = ")
            .append(this.stockTime)
            .append(TAB)
            .append("stafferName = ")
            .append(this.stafferName)
            .append(TAB)
            .append("dutyName = ")
            .append(this.dutyName)
            .append(TAB)
            .append("asksVo = ")
            .append(this.asksVo)
            .append(TAB)
            .append(" )");

        return retValue.toString();
    }
}
