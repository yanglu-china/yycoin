package com.china.center.oa.tcp.bean;

import com.china.center.jdbc.annotation.Entity;
import com.china.center.jdbc.annotation.Id;
import com.china.center.jdbc.annotation.Table;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/** #231
 * 银行业务部层级表
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 16-4-30
 * Time: 上午8:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "T_CENTER_BANKBU_LEVEL")
public class BankBuLevelBean implements Serializable {
    /**
     * 专员编码
     */
    @Id
    private String id;

    /**
     * 专员姓名
     */
    private String name;

    /**
     * 省级经理编码
     */
    private String provinceManagerId;

    /**
     * 省级经理姓名
     */
    private String provinceManager;

    /**
     * 省级团队
     */
    private String provinceName;

    /**
     * 区域经理编码
     */
    private String regionalManagerId;

    /**
     * 区域经理姓名
     */
    private String regionalManager;

    /**
     * 二级区域
     */
    private String regionalName;

    /**
     * 大区总编码
     */
    private String regionalDirectorId;

    /**
     * 大区总姓名
     */
    private String regionalDirector;

    /**
     * 大区
     */
    private String dqName;

    /** #273
     * 事业部总经理
     */
    private String manager;

    private String managerId;
    
    private String sybmanager;

    private String sybmanagerId;
    
    private String sybname;
    
    private String zc;

    private String zcId;

    /**
     * 业务部
     */
    private String ywbName;

    private String changer;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceManagerId() {
        return provinceManagerId;
    }

    public void setProvinceManagerId(String provinceManagerId) {
        this.provinceManagerId = provinceManagerId;
    }

    public String getProvinceManager() {
        return provinceManager;
    }

    public void setProvinceManager(String provinceManager) {
        this.provinceManager = provinceManager;
    }

    public String getRegionalManagerId() {
        return regionalManagerId;
    }

    public void setRegionalManagerId(String regionalManagerId) {
        this.regionalManagerId = regionalManagerId;
    }

    public String getRegionalManager() {
        return regionalManager;
    }

    public void setRegionalManager(String regionalManager) {
        this.regionalManager = regionalManager;
    }

    public String getRegionalDirectorId() {
        return regionalDirectorId;
    }

    public void setRegionalDirectorId(String regionalDirectorId) {
        this.regionalDirectorId = regionalDirectorId;
    }

    public String getRegionalDirector() {
        return regionalDirector;
    }

    public void setRegionalDirector(String regionalDirector) {
        this.regionalDirector = regionalDirector;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getRegionalName() {
        return regionalName;
    }

    public void setRegionalName(String regionalName) {
        this.regionalName = regionalName;
    }

    public String getDqName() {
        return dqName;
    }

    public void setDqName(String dqName) {
        this.dqName = dqName;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }    

    public String getSybmanager() {
		return sybmanager;
	}

	public void setSybmanager(String sybmanager) {
		this.sybmanager = sybmanager;
	}

	public String getSybmanagerId() {
		return sybmanagerId;
	}

	public void setSybmanagerId(String sybmanagerId) {
		this.sybmanagerId = sybmanagerId;
	}

	public String getSybname() {
		return sybname;
	}

	public void setSybname(String sybname) {
		this.sybname = sybname;
	}

	public String getZc() {
		return zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	public String getZcId() {
		return zcId;
	}

	public void setZcId(String zcId) {
		this.zcId = zcId;
	}

	public String getYwbName() {
        return ywbName;
    }

    public void setYwbName(String ywbName) {
        this.ywbName = ywbName;
    }

    public String getChanger() {
        return changer;
    }

    public void setChanger(String changer) {
        this.changer = changer;
    }
    
    public boolean isLevelsEntire(){
    	boolean rst = true;
    	if(StringUtils.isEmpty(this.zcId) 
    	|| StringUtils.isEmpty(this.sybmanagerId)
    	|| StringUtils.isEmpty(this.managerId)
    	|| StringUtils.isEmpty(this.regionalManagerId)){
    		rst = false;
    	}
    	return rst;
    }

    @Override
    public String toString() {
        return "BankBuLevelBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", provinceManagerId='" + provinceManagerId + '\'' +
                ", provinceManager='" + provinceManager + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", regionalManagerId='" + regionalManagerId + '\'' +
                ", regionalManager='" + regionalManager + '\'' +
                ", regionalName='" + regionalName + '\'' +
                ", regionalDirectorId='" + regionalDirectorId + '\'' +
                ", regionalDirector='" + regionalDirector + '\'' +
                ", dqName='" + dqName + '\'' +
                ", manager='" + manager + '\'' +
                ", managerId='" + managerId + '\'' +
                ", sybmanager='" + sybmanager + '\'' +
                ", sybmanagerId='" + sybmanagerId + '\'' +
                ", sybname='" + sybname + '\'' +
                ", zc='" + zc + '\'' +
                ", zcId='" + zcId + '\'' +                
                '}';
    }
}
