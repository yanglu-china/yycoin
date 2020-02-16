package com.china.center.oa.sail.manager;

import java.util.List;

import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.oa.sail.bean.*;
import com.china.center.oa.sail.vo.BaseVO;

public interface OutImportManager
{

	String addBean(List<OutImportBean> list)
		throws MYException;
	
	String addPufaBean(List<OutImportBean> list)
	throws MYException;
	
	boolean process(List<? extends OutImportBean> list)
		throws MYException;
	
	boolean processAsyn(List<? extends OutImportBean> list);
	
	List<ReplenishmentBean> queryReplenishmentBean() throws MYException;

	public String batchDrop(User user, List<BatchDropBean> beans) throws MYException;
	
	String batchApproveImport(List<BatchApproveBean> list, int type)
		throws MYException;
	
	boolean batchApprove(User user,String batchId)
		throws MYException;
	
	String batchSwatchImport(List<BatchSwatchBean> list)
	throws MYException;

	boolean batchSwatch(User user,String batchId)
	throws MYException;
	
	boolean batchUpdateConsign(List<ConsignBean> list)
	throws MYException;
	
	boolean batchUpdateDistAddr(List<DistributionBean> list)
	throws MYException;
	
	boolean batchUpdateEmergency(List<ConsignBean> list)
			throws MYException;

	List<OutImportBean> preUseAmountCheck(String batchId);
	
	void processSplitOut(String batchId);

	/**
	 * 2015/9/18 batch import split out
	 * @param list
	 */
	void batchProcessSplitOut(List<OutBean> list);
	
	boolean batchUpdateRedate(List<OutBean> list)
	throws MYException;
	
	String addBankSail(User user, List<BankSailBean> list)
	throws MYException;
	
	boolean deleteBankSail(User user,String batchId)
	throws MYException;
	
	boolean addEstimateProfit(User user, List<EstimateProfitBean> list)
	throws MYException;
	
	boolean deleteEstimateProfit(User user,String batchId)
	throws MYException;
	
	boolean batchUpdateDepot(List<BaseBean> list)
			throws MYException;

	boolean batchUpdateProductName(User user,List<BaseVO> list) throws MYException;

	/**
	 * 2016/4/14 #222
	 * download attachment from mail and create OA out
	 */
	void downloadOrderFromMailAttachment();

    /**
     * #281 2016/7/27
     * 线下订单转换为OA单JOB
     */
    void offlineOrderJob();

    /**
     * #283 线下入库单
     */
    void offlineStorageInJob();

	/**
	 * #798 同步生成体外销售订单
	 */
	void twOrderJob();
}
