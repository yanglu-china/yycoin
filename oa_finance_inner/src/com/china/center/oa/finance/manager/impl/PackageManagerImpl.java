package com.china.center.oa.finance.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.china.center.oa.finance.bean.InvoiceinsItemBean;
import com.china.center.oa.sail.bean.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.center.china.osgi.config.ConfigLoader;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.bean.InsVSInvoiceNumBean;
import com.china.center.oa.finance.bean.InvoiceinsBean;
import com.china.center.oa.finance.constant.FinanceConstant;
import com.china.center.oa.finance.dao.InsVSInvoiceNumDAO;
import com.china.center.oa.finance.dao.InvoiceinsDAO;
import com.china.center.oa.finance.dao.InvoiceinsItemDAO;
import com.china.center.oa.finance.dao.PreInvoiceApplyDAO;
import com.china.center.oa.finance.dao.TaoBaoTokenDAO;
import com.china.center.oa.finance.manager.PackageManager;
import com.china.center.oa.finance.vo.InvoiceinsItemVO;
import com.china.center.oa.finance.vo.InvoiceinsVO;
import com.china.center.oa.finance.vo.PreInvoiceApplyVO;
import com.china.center.oa.finance.vs.TaobaoLogisticsBean;
import com.china.center.oa.finance.vs.TaobaoTokenBean;
import com.china.center.oa.product.bean.DepotBean;
import com.china.center.oa.product.constant.DepotConstant;
import com.china.center.oa.product.dao.DepotDAO;
import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.dao.StafferDAO;
import com.china.center.oa.publics.vo.StafferVO;
import com.china.center.oa.sail.constanst.OutConstant;
import com.china.center.oa.sail.constanst.ShipConstant;
import com.china.center.oa.sail.dao.BaseDAO;
import com.china.center.oa.sail.dao.DistributionDAO;
import com.china.center.oa.sail.dao.OutDAO;
import com.china.center.oa.sail.dao.OutImportDAO;
import com.china.center.oa.sail.dao.PackageDAO;
import com.china.center.oa.sail.dao.PackageItemDAO;
import com.china.center.oa.sail.dao.PackageVSCustomerDAO;
import com.china.center.oa.sail.dao.PreConsignDAO;
import com.china.center.oa.sail.dao.ProductExchangeConfigDAO;
import com.china.center.oa.sail.dao.TwBaseDAO;
import com.china.center.oa.sail.dao.TwDistributionDAO;
import com.china.center.oa.sail.dao.TwOutDAO;
import com.china.center.oa.sail.manager.OutManager;
import com.china.center.oa.sail.vo.DistributionInterface;
import com.china.center.oa.sail.vo.DistributionVO;
import com.china.center.oa.sail.vo.DistributionVOInterface;
import com.china.center.oa.sail.vo.OutVO;
import com.china.center.oa.sail.vo.OutVOInterface;
import com.china.center.oa.sail.vo.PackageVO;
import com.china.center.oa.sail.vo.ProductExchangeConfigVO;
import com.china.center.oa.sail.vo.TwDistributionVO;
import com.china.center.oa.sail.vo.TwOutVO;
import com.china.center.tools.ListTools;
import com.china.center.tools.StringTools;
import com.china.center.tools.TimeTools;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.LogisticsOnlineConfirmRequest;
import com.taobao.api.request.LogisticsOnlineSendRequest;
import com.taobao.api.response.LogisticsOnlineConfirmResponse;
import com.taobao.api.response.LogisticsOnlineSendResponse;

import net.sf.json.JSONObject;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 *
 * @author smart
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class PackageManagerImpl implements PackageManager {
	private final Log triggerLog = LogFactory.getLog("trigger");

	private final Log blockedLog = LogFactory.getLog("blocked");

	private final Log taobaoLog = LogFactory.getLog("taobao");

	private final Log _logger = LogFactory.getLog(getClass());

	private PreConsignDAO preConsignDAO = null;

	private PackageDAO packageDAO = null;

	private PackageItemDAO packageItemDAO = null;

	private OutDAO outDAO = null;

	private BaseDAO baseDAO = null;

	private DistributionDAO distributionDAO = null;

	private TwOutDAO twOutDAO = null;

	private TwBaseDAO twBaseDAO = null;

	private TwDistributionDAO twDistributionDAO = null;

	private CommonDAO commonDAO = null;

	private DepotDAO depotDAO = null;

	private PackageVSCustomerDAO packageVSCustomerDAO = null;

	private InvoiceinsDAO invoiceinsDAO = null;

	private InsVSInvoiceNumDAO insVSInvoiceNumDAO = null;

	private StafferDAO stafferDAO = null;

	private InvoiceinsItemDAO invoiceinsItemDAO = null;

	private OutImportDAO outImportDAO = null;

	private OutManager outManager = null;

	private PlatformTransactionManager transactionManager = null;

	private Object lock = new Object();

	private PreInvoiceApplyDAO preInvoiceApplyDAO = null;

	private ProductExchangeConfigDAO productExchangeConfigDAO = null;

	private FlowLogDAO flowLogDAO = null;

	private TaoBaoTokenDAO taoBaoTokenDAO;

	public PackageManagerImpl() {
	}

	@Override
	public void checkOrderWithoutCKJob() {

		String msg = "*******************checkOrderWithoutCKJob running***********************";
		_logger.info(msg);

		long statsStar = System.currentTimeMillis();

		TransactionTemplate tran = new TransactionTemplate(transactionManager);

		try {
			tran.execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus arg0) {
					try {
						processZsOrderWithoutCK();
					} catch (MYException e) {
						e.printStackTrace();
						_logger.error(e);
						throw new RuntimeException(e);
					}

					return Boolean.TRUE;
				}
			});
		} catch (Exception e) {
			triggerLog.error(e, e);
		}

		_logger.info("checkOrderWithoutCKJob 统计结束... ,共耗时：" + (System.currentTimeMillis() - statsStar));

		return;
	}

	private void processZsOrderWithoutCK() throws MYException {
		ConditionParse conditionParse = new ConditionParse();
		conditionParse.addWhereStr();
		// 销售单
		conditionParse.addCondition("OutBean.type", "=", OutConstant.OUT_TYPE_OUTBILL);
		// 只针对出问题的赠送单
//		conditionParse.addCondition("OutBean.outType","=", OutConstant.OUTTYPE_OUT_PRESENT);
		// 状态为 “已出库”
		conditionParse.addCondition("OutBean.status", "=", OutConstant.STATUS_PASS);
		String outTime = ConfigLoader.getProperty("checkOrderWithoutCKBeginTime");
		if (StringTools.isNullOrNone(outTime)) {
			outTime = "2018-02-01";
		}
		conditionParse.addCondition("OutBean.outTime", ">", outTime);
		// 没有生成CK单的订单
		conditionParse.addCondition(
				"and not exists(select p.id from T_CENTER_PACKAGE_ITEM p where p.outId = OutBean.fullId)");

		List<OutVO> outBeans = this.outDAO.queryEntityVOsByCondition(conditionParse);
		if (!ListTools.isEmptyOrNull(outBeans)) {
			_logger.info("processZsOrderWithoutCK with out size:" + outBeans.size());
			for (OutVO out : outBeans) {
				// 发货方式不是“空发”
				List<DistributionVO> distList = distributionDAO.queryEntityVOsByFK(out.getFullId());

				if (ListTools.isEmptyOrNull(distList)
						|| distList.get(0).getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
					_logger.info("no distribution bean or 99:" + out.getFullId());
					continue;
				} else {
					// check duplicate preconsign
					ConditionParse con = new ConditionParse();
					con.addWhereStr();
					con.addCondition("outId", "=", out.getFullId());
					List<PreConsignBean> preConsignBeans = this.preConsignDAO.queryEntityBeansByCondition(con);
					if (ListTools.isEmptyOrNull(preConsignBeans)) {
						PreConsignBean preConsign = new PreConsignBean();

						preConsign.setOutId(out.getFullId());

						preConsignDAO.saveEntityBean(preConsign);
						this.logPreconsign(preConsign);
					}
				}
			}
		} else {
			_logger.info("processZsOrderWithoutCK with out size 0!");
		}
	}

	private void logPreconsign(PreConsignBean preConsignBean) {
		String message = String.format("生成preconsign表:%s", preConsignBean.getOutId());
		_logger.info(message);
	}

	/**
	 * 生成发货单 (根据收货打包)
	 */
	@Override
	public void createPackage() {
		synchronized (this.lock) {

			long statsStar = System.currentTimeMillis();

			TransactionTemplate tran = new TransactionTemplate(transactionManager);

			try {
				tran.execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						try {
							processOut();
						} catch (MYException e) {
							throw new RuntimeException(e);
						}

						return Boolean.TRUE;
					}
				});
			} catch (Exception e) {
				triggerLog.error(e, e);
			}

			triggerLog.info("createPackage 统计结束... ,共耗时：" + (System.currentTimeMillis() - statsStar));

			return;
		}
	}

	/**
	 * 生成发货单核心
	 * 
	 * @throws MYException
	 */
	private void processOut() throws MYException {
		ConditionParse conditionParse = new ConditionParse();
		conditionParse.addWhereStr();
		conditionParse.addCondition(" order by logTime asc");

		int batchSize = 1;

		List<PreConsignBean> list = preConsignDAO.queryEntityBeansByLimit(conditionParse, batchSize);
		if (ListTools.isEmptyOrNull(list)) {
			String msg = "******createPackage without preconsign to do******";
			System.out.println(msg);
			_logger.info(msg);
			return;
		} else {
			PreConsignBean first = list.get(0);
			String msg = first.getId() + "******createPackage with outId " + first.getOutId();
			System.out.println(msg);
			_logger.info(msg);
		}

		for (PreConsignBean each : list) {
			boolean isPackaged = this.isPackaged(each);
			if (isPackaged) {
				continue;
			}

			OutVO outBean = outDAO.findVO(each.getOutId().trim());

			if (null != outBean) {
				_logger.info("======is out======" + each.getOutId() + "===" + outBean.getOutType());
				createPackage(each, outBean);
			} else {
				_logger.info("=========is not out==========" + each.getOutId());
				// #267 系统外TW单据
				if (each.getOutId().startsWith("TW")) {
					TwOutVO twOutBean = this.twOutDAO.findVO(each.getOutId());
					if (twOutBean != null) {
						this.createTwPackage(each, twOutBean);
					}
				} else {
					InvoiceinsBean insBean = invoiceinsDAO.find(each.getOutId());

					if (null != insBean) {
						_logger.info("======is invoiceins======" + each.getOutId());
						createInsPackage(each, insBean.getId());
					} else {
						// 2015/3/1 预开票申请也需要进入CK单
						PreInvoiceApplyVO applyBean = this.preInvoiceApplyDAO.findVO(each.getOutId());

						if (applyBean != null) {
							_logger.info("======is PreInvoiceApplyBean======" + each.getOutId());
							this.createPreInsPackage(each, applyBean);
						} else {
							triggerLog.warn("======is other, direct delete, handle nothing======" + each.getOutId());
							preConsignDAO.deleteEntityBean(each.getId());

							continue;
						}
					}
				}
			}
		}
	}

	private boolean isPackaged(PreConsignBean pre) {
		String fullId = pre.getOutId();
		ConditionParse conditionParse = new ConditionParse();
		conditionParse.addCondition("outId", "=", fullId);
		List<PackageItemBean> itemBeanList = this.packageItemDAO.queryEntityBeansByCondition(conditionParse);
		if (ListTools.isEmptyOrNull(itemBeanList)) {
			return false;
		} else {
			_logger.warn(fullId + " is already packaged in CK***" + itemBeanList.get(0).getPackageId());
			preConsignDAO.deleteEntityBean(pre.getId());
			return true;
		}
	}

	private void createNewPackage(OutVOInterface outBean, List<? extends BaseInterface> baseList,
			DistributionInterface distVO, String fullAddress, String location,
								  PrePackageBean prePackageBean) {
		_logger.info("**************createNewPackage for Out now " + outBean.getFullId());
		String id = commonDAO.getSquenceString20("CK");

		int allAmount = 0;

		PackageBean packBean = new PackageBean();

		packBean.setId(id);
		packBean.setCustomerId(outBean.getCustomerId());
		packBean.setShipping(distVO.getShipping());
		if (prePackageBean == null){
			packBean.setTransport1(distVO.getTransport1());
		} else{
			packBean.setTransport1(prePackageBean.getTransport());
			packBean.setTransportNo(prePackageBean.getTransportNo());
			packBean.setType(prePackageBean.getType());
		}

		packBean.setExpressPay(distVO.getExpressPay());
		packBean.setTransport2(distVO.getTransport2());
		packBean.setTransportPay(distVO.getTransportPay());
		packBean.setAddress(fullAddress);
		packBean.setReceiver(distVO.getReceiver());
		packBean.setMobile(distVO.getMobile());
		packBean.setTelephone(distVO.getTelephone());
		packBean.setLocationId(location);
		packBean.setCityId(distVO.getCityId());

		packBean.setStafferName(outBean.getStafferName());
		packBean.setIndustryName(outBean.getIndustryName());
		packBean.setDepartName(outBean.getIndustryName3());

		packBean.setTotal(outBean.getTotal());
		packBean.setStatus(0);
		String now = TimeTools.now();
		packBean.setCreateTime(now);
		packBean.setLogTime(now);

		List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();

		boolean isEmergency = false;
		Map<String, List<BaseInterface>> pmap = new HashMap<String, List<BaseInterface>>();
		for (BaseInterface base : baseList) {
			PackageItemBean item = new PackageItemBean();

			item.setPackageId(id);
			item.setOutId(outBean.getFullId());
			item.setBaseId(base.getId());
			// #632
			item.setProductImportId(base.getProductImportId());
			item.setProductId(base.getProductId());
			item.setProductName(base.getProductName());

			// 2015/8/12 调拨单产生的CK单的数量应该取绝对值，因为调拨出库是负的数量
			item.setAmount(Math.abs(base.getAmount()));
			item.setPrice(Math.abs(base.getPrice()));

			item.setValue(base.getValue());
			item.setOutTime(outBean.getOutTime());
			item.setDescription(outBean.getDescription());
			item.setCustomerId(outBean.getCustomerId());
			item.setEmergency(outBean.getEmergency());

			if (item.getEmergency() == 1) {
				isEmergency = true;
			}
			// #120 2015/11/1 商品转换功能
			if (!outBean.getFullId().startsWith("TW") && !outBean.getFullId().startsWith("A")) {
				ConditionParse condition = new ConditionParse();
				condition.addWhereStr();
				condition.addCondition("ProductExchangeConfigBean.srcProductId", "=", item.getProductId());
				List<ProductExchangeConfigVO> list = this.productExchangeConfigDAO.queryEntityVOsByCondition(condition);
				if (!ListTools.isEmptyOrNull(list)) {
					ProductExchangeConfigVO vo = list.get(0);

					if (item.getAmount() * vo.getDestAmount() % vo.getSrcAmount() == 0) {
						item.setAmount(item.getAmount() * vo.getDestAmount() / vo.getSrcAmount());
						item.setProductId(vo.getDestProductId());
						item.setProductName(vo.getDestProductName());
						_logger.info(item + " create package bean for product exchange:" + vo);
					} else {
						_logger.warn(item + " does not match product exchange:" + vo);
					}
				} else {
					_logger.info("no ProductExchangeConfigVO found:" + item.getOutId());
				}
			}

			itemList.add(item);

			allAmount += item.getAmount();

			if (!pmap.containsKey(base.getProductId())) {
				List<BaseInterface> blist = new ArrayList<BaseInterface>();

				blist.add(base);

				pmap.put(base.getProductId(), blist);
			} else {
				List<BaseInterface> blist = pmap.get(base.getProductId());

				blist.add(base);
			}
		}

		packBean.setAmount(allAmount);

		if (isEmergency) {
			packBean.setEmergency(OutConstant.OUT_EMERGENCY_YES);
		}
		packBean.setProductCount(pmap.values().size());

		PackageVSCustomerBean vsBean = new PackageVSCustomerBean();

		vsBean.setPackageId(id);
		vsBean.setCustomerId(outBean.getCustomerId());
		vsBean.setCustomerName(outBean.getCustomerName());
		vsBean.setIndexPos(1);

		packBean.setPrintInvoiceinsStatus(itemList);
		this.savePackage(packBean, itemList);

		packageItemDAO.saveAllEntityBeans(itemList);

		packageVSCustomerDAO.saveEntityBean(vsBean);
	}

	/**
	 * OA生成CK单前,先到中间表确认此销售单有无对应的状态为0外部单号（根据fullid到out_import表取oano对应的CITICNO），
	 * 如有，则在生成CK单时将快递公司与快递单号带入,并将状态更新为1
	 * @param fullId
	 */
	private PrePackageBean getPrePackageBean(String fullId, boolean ignoreStatus){
		if (!fullId.startsWith("A")){
			String citicNo = this.outImportDAO.getCiticNo(fullId);
			if (!StringTools.isNullOrNone(citicNo)){
				PrePackageBean prePackageBean = null;
				if (ignoreStatus){
					//当开票申请查关联销售单的时候忽略状态,因为对应销售单可能已经进入CK单并把中间表状态改掉了
					prePackageBean = this.packageDAO.queryPrePackageIgnoreStatus(citicNo);
				} else{
					//第一次查销售单的时候必须状态是0
					prePackageBean = this.packageDAO.queryPrePackage(citicNo, 0);
				}

				if(prePackageBean!= null){
				    // 如果发票先进入CK单时不更新状态
				    if (!ignoreStatus){
                        this.packageDAO.updatePrePackageStatus(citicNo, 1);
                    }
					return prePackageBean;
				}
			}
		}
		return null;
	}

	/**
	 * 根据开票申请，查询对应的销售单是否在中间表中
	 * @param insId
	 * @return
	 */
    private PrePackageBean getPrePackageBeanForIns(String insId){
        if (insId.startsWith("A")){
            List<InvoiceinsItemBean> invoiceinsItemBeans = this.invoiceinsItemDAO.queryEntityBeansByFK(insId);
            if (!ListTools.isEmptyOrNull(invoiceinsItemBeans)){
            	for (InvoiceinsItemBean item: invoiceinsItemBeans){
            		//该开票申请关联的销售单,只要有一个存在于中间表中就返回
            		String outId = item.getOutId();
            		if (!StringTools.isNullOrNone(outId)){
						PrePackageBean prePackageBean = this.getPrePackageBean(outId, true);
						if (prePackageBean!= null){
							return prePackageBean;
						}
					}
				}
			}
        }
        return null;
    }


	private void addLog(final String packageId, int preStatus, int afterStatus, String description) {
		FlowLogBean log = new FlowLogBean();
		log.setActor("系统");
		log.setFullId(packageId);
		log.setOprMode(PublicConstant.OPRMODE_PASS);
		log.setLogTime(TimeTools.now());
		log.setPreStatus(preStatus);
		log.setAfterStatus(afterStatus);
		log.setDescription(description);

		flowLogDAO.saveEntityBean(log);

		_logger.info(packageId + " update SF package status to " + afterStatus);
	}

	/**
	 * for invoiceins
	 * 
	 * @param ins
	 * @param distVO
	 * @param fullAddress
	 * @param location
	 */
	private void createNewInsPackage(InvoiceinsVO ins, List<InsVSInvoiceNumBean> numList, DistributionVO distVO,
			String fullAddress, String location, PrePackageBean prePackageBean) {
		_logger.info("****createNewInsPackage now****");
		String id = commonDAO.getSquenceString20("CK");

		int allAmount = 0;

		PackageBean packBean = new PackageBean();

		packBean.setId(id);
		packBean.setCustomerId(ins.getCustomerId());
		packBean.setShipping(distVO.getShipping());

        if (prePackageBean == null){
            packBean.setTransport1(distVO.getTransport1());
        } else{
            packBean.setTransport1(prePackageBean.getTransport());
            packBean.setTransportNo(prePackageBean.getTransportNo());
            packBean.setType(prePackageBean.getType());
        }

		packBean.setExpressPay(distVO.getExpressPay());
		packBean.setTransport2(distVO.getTransport2());
		packBean.setTransportPay(distVO.getTransportPay());
		packBean.setAddress(fullAddress);
		packBean.setReceiver(distVO.getReceiver());
		packBean.setMobile(distVO.getMobile());
		packBean.setTelephone(distVO.getTelephone());
		packBean.setLocationId(location);
		packBean.setCityId(distVO.getCityId());

		packBean.setStafferName(ins.getStafferName());

		StafferVO staff = stafferDAO.findVO(ins.getStafferId());

		if (null != staff) {
			packBean.setIndustryName(staff.getIndustryName());
			packBean.setDepartName(staff.getIndustryName3());
		}

		packBean.setTotal(ins.getMoneys());
		packBean.setStatus(0);
		String now = TimeTools.now();
		packBean.setCreateTime(now);
		packBean.setLogTime(now);

		String sb = getPrintTextForIns(ins);

		List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();

		boolean first = false;

		for (InsVSInvoiceNumBean base : numList) {
			PackageItemBean item = new PackageItemBean();

			item.setPackageId(id);
			item.setOutId(ins.getId());
			item.setBaseId(base.getId());
			item.setProductId(base.getInvoiceNum());
			item.setProductName("发票号：" + base.getInvoiceNum());
			item.setAmount(1);
			item.setPrice(base.getMoneys());
			item.setValue(base.getMoneys());
			item.setOutTime(TimeTools.changeFormat(ins.getLogTime(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
			item.setDescription(ins.getDescription());
			item.setCustomerId(ins.getCustomerId());
			if (!first) {
				item.setPrintText(sb);
			}

			first = true;

			itemList.add(item);

			allAmount += item.getAmount();
		}

		packBean.setAmount(allAmount);

		packBean.setProductCount(numList.size());

		PackageVSCustomerBean vsBean = new PackageVSCustomerBean();

		vsBean.setPackageId(id);
		vsBean.setCustomerId(ins.getCustomerId());
		vsBean.setCustomerName(ins.getCustomerName());
		vsBean.setIndexPos(1);

		packBean.setPrintInvoiceinsStatus(itemList);

		this.savePackage(packBean, itemList);

		packageItemDAO.saveAllEntityBeans(itemList);

		packageVSCustomerDAO.saveEntityBean(vsBean);
	}

	/**
	 * 功能描述: <br>
	 * 〈功能详细描述〉
	 *
	 * @param ins
	 * @return
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	private String getPrintTextForIns(InvoiceinsVO ins) {
		StringBuilder sb = new StringBuilder();

		List<InvoiceinsItemVO> insVOList = invoiceinsItemDAO.queryEntityVOsByFK(ins.getId());

		Set<String> uniqueSO = new HashSet<String>();

//		sb.append(ins.getCustomerName()).append(";商品明细：");

		for (InvoiceinsItemVO insVO : insVOList) {
//			sb.append(insVO.getProductName()).append(";");

			if (!uniqueSO.contains(insVO.getOutId()) && insVO.getType() == FinanceConstant.INSVSOUT_TYPE_OUT) {
				uniqueSO.add(insVO.getOutId());
			}
		}

		sb.append("银行订单号：");
		for (String outid : uniqueSO) {
			List<OutImportBean> outiList = outImportDAO.queryEntityBeansByFK(outid, AnoConstant.FK_FIRST);

			if (!ListTools.isEmptyOrNull(outiList)) {
				if (!StringTools.isNullOrNone(outiList.get(0).getCiticNo())) {
					sb.append(outiList.get(0).getCiticNo()).append(";");
				}
			}
		}
		String result = sb.toString();
		if (result.length() > 2000) {
			return StringUtils.subString(result, 1995) + "...";
		} else {
			return result;
		}
	}

	// 2015/1/13 update
	private void setInnerCondition(DistributionVOInterface distVO, String location, ConditionParse con,
			String customerId, String industryName) {
		int shipping = distVO.getShipping();
		if (shipping == 0) {
			// 发货方式也必须一致
			con.addIntCondition("PackageBean.shipping", "=", distVO.getShipping());

			// 自提：收货人，电话一致，才合并
			con.addCondition("PackageBean.receiver", "=", distVO.getReceiver());

			con.addCondition("PackageBean.mobile", "=", distVO.getMobile());

			// #225
			con.addCondition("PackageBean.locationId", "=", location);
//			this.checkZjgh(customerId, con);
			this.addIndustryCondition(industryName, customerId, con);
			con.addCondition(" and (PackageBean.pickupId ='' or PackageBean.pickupId IS NULL)");
			con.addCondition(" and PackageBean.status in(0,5)");
		} else if (shipping == 2) {
			// 第三方快递：地址、收货人、电话完全一致，才合并.能不能判断地址后6个字符一致，电话，收货人一致，就合并
			String fullAddress = distVO.getProvinceName() + distVO.getCityName() + distVO.getAddress();
			String temp = fullAddress.trim();

			// #25 包含特殊字符\,过滤掉
			if (temp.contains("\\")) {
				String[] arrays = temp.split("\\\\");
				_logger.info(arrays);
				int length = arrays.length;
				if (length == 1) {
					String temp2 = arrays[0];
					con.addCondition("PackageBean.address", "like", "%" + temp2 + "%");
				} else {
					String temp2 = arrays[length - 1];
					con.addCondition("PackageBean.address", "like", "%" + temp2 + "%");
				}
			} else if (temp.length() >= 6) {
				con.addCondition("PackageBean.address", "like", "%" + temp.substring(temp.length() - 6));
			} else {
				con.addCondition("PackageBean.address", "like", "%" + temp);
			}
			con.addIntCondition("PackageBean.shipping", "=", distVO.getShipping());

			con.addCondition("PackageBean.receiver", "=", distVO.getReceiver());

			con.addCondition("PackageBean.mobile", "=", distVO.getMobile());
			// #225
			con.addCondition("PackageBean.locationId", "=", location);
//			this.checkZjgh(customerId, con);
			this.addIndustryCondition(industryName, customerId, con);
			con.addCondition(" and (PackageBean.pickupId ='' or PackageBean.pickupId IS NULL)");
			con.addCondition(" and PackageBean.status in(0,5)");
		} else {
			// Keep default behavior
			// con.addCondition("PackageBean.customerId", "=", outBean.getCustomerId());
			con.addCondition("PackageBean.cityId", "=", distVO.getCityId()); // 借用outId 用于存储城市。生成出库单增加 城市 维度
			con.addIntCondition("PackageBean.shipping", "=", distVO.getShipping());

			con.addIntCondition("PackageBean.transport1", "=", distVO.getTransport1());

			con.addIntCondition("PackageBean.expressPay", "=", distVO.getExpressPay());

			con.addIntCondition("PackageBean.transport2", "=", distVO.getTransport2());

			con.addIntCondition("PackageBean.transportPay", "=", distVO.getTransportPay());

			con.addCondition("PackageBean.locationId", "=", location);

//			this.checkZjgh(customerId, con);
			this.addIndustryCondition(industryName, customerId, con);
			con.addCondition("PackageBean.receiver", "=", distVO.getReceiver());

			con.addCondition("PackageBean.mobile", "=", distVO.getMobile());

			con.addCondition(" and (PackageBean.pickupId ='' or PackageBean.pickupId IS NULL)");
			con.addCondition(" and PackageBean.status in(0,5)");
		}
	}

    private void setInnerConditionForPre(DistributionVOInterface distVO, String location, ConditionParse con,
                                   String customerId, String industryName, PrePackageBean prePackageBean) {
        // 第三方快递：地址、收货人、电话完全一致，才合并.能不能判断地址后6个字符一致，电话，收货人一致，就合并
        String fullAddress = distVO.getProvinceName() + distVO.getCityName() + distVO.getAddress();
        String temp = fullAddress.trim();

        // #25 包含特殊字符\,过滤掉
        if (temp.contains("\\")) {
            String[] arrays = temp.split("\\\\");
            _logger.info(arrays);
            int length = arrays.length;
            if (length == 1) {
                String temp2 = arrays[0];
                con.addCondition("PackageBean.address", "like", "%" + temp2 + "%");
            } else {
                String temp2 = arrays[length - 1];
                con.addCondition("PackageBean.address", "like", "%" + temp2 + "%");
            }
        } else if (temp.length() >= 6) {
            con.addCondition("PackageBean.address", "like", "%" + temp.substring(temp.length() - 6));
        } else {
            con.addCondition("PackageBean.address", "like", "%" + temp);
        }
        con.addIntCondition("PackageBean.type", "=", prePackageBean.getType());
        con.addCondition("PackageBean.transportNo", "=", prePackageBean.getTransportNo());

        con.addIntCondition("PackageBean.shipping", "=", distVO.getShipping());
        con.addCondition("PackageBean.receiver", "=", distVO.getReceiver());

        con.addCondition("PackageBean.mobile", "=", distVO.getMobile());
        // #225
        con.addCondition("PackageBean.locationId", "=", location);
        this.addIndustryCondition(industryName, customerId, con);
        con.addCondition(" and (PackageBean.pickupId ='' or PackageBean.pickupId IS NULL)");
        con.addCondition(" and PackageBean.status in(0,5)");
    }

	private void addIndustryCondition(String industryName, String customerId, ConditionParse con){
		_logger.info("***industryName***"+industryName);
		//#320 中金国华
		if ("575852694".equals(customerId) ||
		//#713 钱币事业部
		(!StringTools.isNullOrNone(industryName) && industryName.contains("钱币事业部"))){
			con.addCondition("PackageBean.customerId", "=", customerId);
		}
	}

	/**
	 * #302 hard code 中金国华customerId
	 * 
	 * @param customerId
	 * @param con
	 */
	private void checkZjgh(String customerId, ConditionParse con) {
		if ("575852694".equals(customerId)) {
			con.addCondition("PackageBean.customerId", "=", customerId);
		}
	}

	/**
	 * 
	 */
	@Override
	public void createPackage(PreConsignBean pre, OutVO out) throws MYException {
		String location = "";
		String fullId = out.getFullId();

		// 通过仓库获取 仓库地点
		DepotBean depot = depotDAO.find(out.getLocation());

		if (depot != null)
			location = depot.getIndustryId2();

		List<BaseBean> baseList = baseDAO.queryEntityBeansByFK(fullId);

		List<DistributionVO> distList = distributionDAO.queryEntityVOsByFK(fullId);

		if (ListTools.isEmptyOrNull(distList)) {
			triggerLog.warn("======createPackage== (distList is null or empty)====" + fullId);
			preConsignDAO.deleteEntityBean(pre.getId());

			return;
		}

		DistributionVO distVO = distList.get(0);

		// 如果是空发,则不处理
		if (distVO.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
			triggerLog.warn("======createPackage== (shipping is OUT_SHIPPING_NOTSHIPPING)====" + fullId);
			preConsignDAO.deleteEntityBean(pre.getId());

			return;
		}

		// 地址不全,不发
		if (distVO.getAddress().trim().equals("0") && distVO.getReceiver().trim().equals("0")
				&& distVO.getMobile().trim().equals("0")) {
			_logger.warn("======address not complete==" + fullId);
			preConsignDAO.deleteEntityBean(pre.getId());
			return;
		}

		String fullAddress = distVO.getProvinceName() + distVO.getCityName() + distVO.getAddress();
		String fullAddressTrim = fullAddress.trim();

		//#942
		boolean needNotMerge = this.needNotMerge(out.getCustomerName());
		_logger.info(out.getCustomerName()+"***"+needNotMerge);
		if (needNotMerge){
			_logger.info("***need not merge***"+fullId);
			createNewPackage(out, baseList, distVO, fullAddressTrim, location, null);
			preConsignDAO.deleteEntityBean(pre.getId());
			return;
		}

		// 此客户是否存在同一个发货包裹,且未拣配
		ConditionParse con = new ConditionParse();
		con.addWhereStr();

        //#930 检查是否预先分配快递单号
        PrePackageBean prePackageBean = this.getPrePackageBean(fullId, false);
        if (prePackageBean == null){
            setInnerCondition(distVO, location, con, out.getCustomerId(), out.getIndustryName());
        } else{
			_logger.info(fullId + "****is in prePackageBean***"+prePackageBean);
            setInnerConditionForPre(distVO, location, con, out.getCustomerId(), out.getIndustryName(),prePackageBean);
        }
		List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);

		if (ListTools.isEmptyOrNull(packageList)) {
			_logger.info("****create new package now***" + fullId);
			createNewPackage(out, baseList, distVO, fullAddressTrim, location, prePackageBean);
		} else {
			_logger.info(location + "****package already exist***" + fullId);
			String id = packageList.get(0).getId();

			PackageBean packBean = packageDAO.find(id);

			// 不存在或已不是初始状态(可能已被拣配)
			if (null == packBean || (packBean.getStatus() != 0
					&& packBean.getStatus() != ShipConstant.SHIP_STATUS_PRINT_INVOICEINS)) {
				_logger.info(fullId + "****added to new package***");
				createNewPackage(out, baseList, distVO, fullAddressTrim, location, null);
			} else {
				// #18 2015/2/5 同一个CK单中的所有SO单必须location一致才能合并
				List<PackageItemBean> currentItems = this.packageItemDAO.queryEntityBeansByFK(packBean.getId());
				if (!ListTools.isEmptyOrNull(currentItems)) {
					_logger.info(fullId + "****add SO to existent package " + packBean.getId() + " current size:"
							+ currentItems.size());
					PackageItemBean first = currentItems.get(0);
					if (fullId.contains("DB") && first.getOutId().contains("SO")) {
						_logger.warn("***not merge with different out type***" + first.getOutId());
						createNewPackage(out, baseList, distVO, fullAddressTrim, location, null);
						// #539
						return;
					} else {
						OutVO outBean = outDAO.findVO(first.getOutId());
						if (outBean != null) {
							String lo = outBean.getLocation();
							// #18 2016/6/15 如果销售单对应depot表中industryId2不一致，就不能合并。而不管仓库是否一致
							DepotBean depot2 = this.depotDAO.find(lo);
							if (depot2 != null && !location.equals(depot2.getIndustryId2())) {
								String msg = first.getOutId() + "****industryId2 is not same****" + out.getFullId();
								_logger.warn(msg);

								createNewPackage(out, baseList, distVO, fullAddressTrim, location, null);
								return;
							}
						}

						// 2015/2/15 检查重复SO单
						for (PackageItemBean p : currentItems) {
							if (out.getFullId().equals(p.getOutId())) {
								_logger.warn("****duplicate package item***" + fullId);
								preConsignDAO.deleteEntityBean(pre.getId());
								return;
							}
						}
					}
				} else {
					_logger.warn("***no package items exist***" + packBean.getId());
				}
				List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();

				int allAmount = 0;
				double total = 0;

				Map<String, List<BaseBean>> pmap = new HashMap<String, List<BaseBean>>();
				boolean isEmergency = false;
				for (BaseBean base : baseList) {
					PackageItemBean item = new PackageItemBean();

					item.setPackageId(id);
					item.setOutId(out.getFullId());
					item.setBaseId(base.getId());
					item.setProductImportId(base.getProductImportId());
					item.setProductId(base.getProductId());
					item.setProductName(base.getProductName());
					item.setAmount(Math.abs(base.getAmount()));
					item.setPrice(Math.abs(base.getPrice()));
					item.setValue(base.getValue());
					item.setOutTime(out.getOutTime());
					item.setDescription(out.getDescription());
					item.setCustomerId(out.getCustomerId());
					item.setEmergency(out.getEmergency());

					if (item.getEmergency() == 1) {
						isEmergency = true;
					}

					itemList.add(item);

					allAmount += item.getAmount();
					total += base.getValue();

					if (!pmap.containsKey(base.getProductId())) {
						List<BaseBean> blist = new ArrayList<BaseBean>();

						blist.add(base);

						pmap.put(base.getProductId(), blist);
					} else {
						List<BaseBean> blist = pmap.get(base.getProductId());

						blist.add(base);
					}
				}

				packBean.setAmount(packBean.getAmount() + allAmount);
				packBean.setTotal(packBean.getTotal() + total);
				packBean.setProductCount(packBean.getProductCount() + pmap.values().size());

				if (isEmergency) {
					packBean.setEmergency(OutConstant.OUT_EMERGENCY_YES);
				}
				if (this.isDirectShipped(itemList)) {
					packBean.setDirect(1);
				}
				packageDAO.updateEntityBean(packBean);

				packageItemDAO.saveAllEntityBeans(itemList);

				// 包与客户关系
				PackageVSCustomerBean vsBean = packageVSCustomerDAO.findByUnique(id, out.getCustomerId());

				if (null == vsBean) {
					int count = packageVSCustomerDAO.countByCondition("where packageId = ?", id);

					PackageVSCustomerBean newvsBean = new PackageVSCustomerBean();

					newvsBean.setPackageId(id);
					newvsBean.setCustomerId(out.getCustomerId());
					newvsBean.setCustomerName(out.getCustomerName());
					newvsBean.setIndexPos(count + 1);

					packageVSCustomerDAO.saveEntityBean(newvsBean);
				}
			}
		}

		preConsignDAO.deleteEntityBean(pre.getId());
	}

	/**
	 * 以下客户的销售单不需要合并CK单 #942
	 * @param customerName
	 * @return
	 */
	private boolean needNotMerge(String customerName){
		return "拍卖客户".equals(customerName)
				|| "国华拍卖".equals(customerName)
				|| "永银天猫旗舰店-有赞".equals(customerName)
				|| "国华天猫旗舰店-有赞".equals(customerName)
				|| "国华天猫旗舰店-零售".equals(customerName)
				|| "永银京东旗舰店-零售".equals(customerName)
				|| "永银京东旗舰店-线上".equals(customerName)
				|| "国华京东旗舰店-线上".equals(customerName)
				|| "中金国华-京东".equals(customerName)
				|| "中金国华-天猫".equals(customerName)
				|| "永银天猫旗舰店-线上".equals(customerName)
				|| "永银京东旗舰店-有赞".equals(customerName)
				|| "钱币交易部-京东".equals(customerName)
				|| "国华天猫旗舰店-线上".equals(customerName)
				|| "国华京东旗舰店-有赞".equals(customerName)
				|| "国华京东旗舰店-零售".equals(customerName);
	}

	private boolean isDirectShipped(List<PackageItemBean> items) {
		boolean result = false;
		if (!ListTools.isEmptyOrNull(items)) {
			for (PackageItemBean item : items) {
				String outId = item.getOutId();
				List<OutImportBean> importBeans = outImportDAO.queryEntityBeansByFK(outId, AnoConstant.FK_FIRST);
				if (!ListTools.isEmptyOrNull(importBeans)) {
					for (OutImportBean importBean : importBeans) {
						if (importBean.getDirect() == 1) {
							return true;
						}
					}
				}
			}
		}
		return result;
	}

	public void createTwPackage(PreConsignBean pre, TwOutVO out) throws MYException {
		_logger.info("createTwPackage***" + pre.getOutId());
		String location = "";
		String fullId = out.getFullId();

		// 通过仓库获取 仓库地点
		DepotBean depot = depotDAO.find(out.getLocation());

		if (depot != null)
			location = depot.getIndustryId2();

		List<TwBaseBean> baseList = this.twBaseDAO.queryEntityBeansByFK(fullId);

		List<TwDistributionVO> distList = this.twDistributionDAO.queryEntityVOsByFK(fullId);

		if (ListTools.isEmptyOrNull(distList)) {
			triggerLog.warn("======createPackage== (distList is null or empty)====" + fullId);
			preConsignDAO.deleteEntityBean(pre.getId());

			return;
		}

		TwDistributionVO distVO = distList.get(0);

		// 如果是空发,则不处理
		if (distVO.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
			triggerLog.warn("======createPackage== (shipping is OUT_SHIPPING_NOTSHIPPING)====" + fullId);
			preConsignDAO.deleteEntityBean(pre.getId());

			return;
		}

		// 地址不全,不发
		if (distVO.getAddress().trim().equals("0") && distVO.getReceiver().trim().equals("0")
				&& distVO.getMobile().trim().equals("0")) {
			_logger.warn("======address not complete==" + fullId);
			preConsignDAO.deleteEntityBean(pre.getId());
			return;
		}

		String fullAddress = distVO.getProvinceName() + distVO.getCityName() + distVO.getAddress();
		String fullAddressTrim = fullAddress.trim();
		// 此客户是否存在同一个发货包裹,且未拣配
		ConditionParse con = new ConditionParse();

		con.addWhereStr();

		setInnerCondition(distVO, location, con, out.getCustomerId(), out.getIndustryName());

		List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);

		if (ListTools.isEmptyOrNull(packageList)) {
			_logger.info("****create new package now***" + fullId);
			createNewPackage(out, baseList, distVO, fullAddressTrim, location, null);
		} else {
			_logger.info("****package already exist***" + fullId);
			String id = packageList.get(0).getId();

			PackageBean packBean = packageDAO.find(id);

			// 不存在或已不是初始状态(可能已被拣配)
			if (null == packBean || (packBean.getStatus() != 0
					&& packBean.getStatus() != ShipConstant.SHIP_STATUS_PRINT_INVOICEINS)) {
				_logger.info(fullId + "****added to new package***");
				createNewPackage(out, baseList, distVO, fullAddressTrim, location,null);
			} else {
				// #18 2015/2/5 同一个CK单中的所有SO单必须location一致才能合并
				List<PackageItemBean> currentItems = this.packageItemDAO.queryEntityBeansByFK(packBean.getId());
				if (!ListTools.isEmptyOrNull(currentItems)) {
					_logger.info(fullId + "****add SO to existent package " + packBean.getId() + " current size:"
							+ currentItems.size());
					PackageItemBean first = currentItems.get(0);
					if (fullId.contains("DB") && first.getOutId().contains("SO")) {
						_logger.warn("***DB type not merge with SO***" + first.getOutId());
						createNewPackage(out, baseList, distVO, fullAddressTrim, location,null);
					} else {
						OutVO outBean = outDAO.findVO(first.getOutId());
						if (outBean != null) {
							String lo = outBean.getLocation();
							// #18 2016/6/15 如果销售单对应depot表中industryId2不一致，就不能合并。而不管仓库是否一致
							DepotBean depot2 = this.depotDAO.find(lo);
							if (depot2 != null && !location.equals(depot2.getIndustryId2())) {
								String msg = first.getOutId() + "****industryId2 is not same****" + out.getFullId();
								_logger.warn(msg);

								createNewPackage(out, baseList, distVO, fullAddressTrim, location,null);
//								preConsignDAO.deleteEntityBean(pre.getId());
								return;
							}
						}

						// 2015/2/15 检查重复SO单
						for (PackageItemBean p : currentItems) {
							if (out.getFullId().equals(p.getOutId())) {
								_logger.warn("****duplicate package item***" + fullId);
								preConsignDAO.deleteEntityBean(pre.getId());
								return;
							}
						}
					}
				} else {
					_logger.warn("***no package items exist***" + packBean.getId());
				}
				List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();

				int allAmount = 0;
				double total = 0;

				Map<String, List<BaseInterface>> pmap = new HashMap<String, List<BaseInterface>>();
				boolean isEmergency = false;
				for (BaseInterface base : baseList) {
					PackageItemBean item = new PackageItemBean();

					item.setPackageId(id);
					item.setOutId(out.getFullId());
					item.setBaseId(base.getId());
					item.setProductId(base.getProductId());
					item.setProductName(base.getProductName());
					item.setAmount(Math.abs(base.getAmount()));
					item.setPrice(Math.abs(base.getPrice()));
					item.setValue(base.getValue());
					item.setOutTime(out.getOutTime());
					item.setDescription(out.getDescription());
					item.setCustomerId(out.getCustomerId());
					item.setEmergency(out.getEmergency());

					if (item.getEmergency() == 1) {
						isEmergency = true;
					}

					itemList.add(item);

					allAmount += item.getAmount();
					total += base.getValue();

					if (!pmap.containsKey(base.getProductId())) {
						List<BaseInterface> blist = new ArrayList<BaseInterface>();

						blist.add(base);

						pmap.put(base.getProductId(), blist);
					} else {
						List<BaseInterface> blist = pmap.get(base.getProductId());

						blist.add(base);
					}
				}

				packBean.setAmount(packBean.getAmount() + allAmount);
				packBean.setTotal(packBean.getTotal() + total);
				packBean.setProductCount(packBean.getProductCount() + pmap.values().size());

				if (isEmergency) {
					packBean.setEmergency(OutConstant.OUT_EMERGENCY_YES);
				}
				if (this.isDirectShipped(itemList)) {
					packBean.setDirect(1);
				}
				packageDAO.updateEntityBean(packBean);

				packageItemDAO.saveAllEntityBeans(itemList);

				// 包与客户关系
				PackageVSCustomerBean vsBean = packageVSCustomerDAO.findByUnique(id, out.getCustomerId());

				if (null == vsBean) {
					int count = packageVSCustomerDAO.countByCondition("where packageId = ?", id);

					PackageVSCustomerBean newvsBean = new PackageVSCustomerBean();

					newvsBean.setPackageId(id);
					newvsBean.setCustomerId(out.getCustomerId());
					newvsBean.setCustomerName(out.getCustomerName());
					newvsBean.setIndexPos(count + 1);

					packageVSCustomerDAO.saveEntityBean(newvsBean);
				}
			}
		}

		preConsignDAO.deleteEntityBean(pre.getId());
	}

	/**
	 * 
	 */
	@Override
	public void createInsPackage(PreConsignBean pre, String insId) throws MYException {
		String location = "";

		// 通过仓库获取 仓库地点
		DepotBean depot = depotDAO.find(DepotConstant.CENTER_DEPOT_ID);

		if (depot != null)
			location = depot.getIndustryId2();

		InvoiceinsVO ins = invoiceinsDAO.findVO(insId);

		if (null == ins) {
			_logger.error("ins not found***" + insId);
			preConsignDAO.deleteEntityBean(pre.getId());
			return;
		}

		List<InsVSInvoiceNumBean> numList = insVSInvoiceNumDAO.queryEntityBeansByFK(insId);

		if (ListTools.isEmptyOrNull(numList)) {
			_logger.warn("======createInsPackage== (numList is null or empty)====" + insId);
			return;
		}

		List<DistributionVO> distList = distributionDAO.queryEntityVOsByFK(ins.getId());

		if (ListTools.isEmptyOrNull(distList)) {
			_logger.warn("======createInsPackage==(distList is null or empty)====" + insId);
			preConsignDAO.deleteEntityBean(pre.getId());
			return;
		}

		DistributionVO distVO = distList.get(0);

		// 如果是空发,则不处理
		if (distVO.getShipping() == OutConstant.OUT_SHIPPING_NOTSHIPPING) {
			_logger.warn("======createInsPackage==(shipping is OUT_SHIPPING_NOTSHIPPING)====" + insId);
			preConsignDAO.deleteEntityBean(pre.getId());
			return;
		}

		// 历史数据无配送信息，pass
		if (StringTools.isNullOrNone(distVO.getAddress()) && StringTools.isNullOrNone(distVO.getReceiver())
				&& StringTools.isNullOrNone(distVO.getMobile())) {
			_logger.warn("======createInsPackage==(distList detail is null or empty)====" + insId);
			preConsignDAO.deleteEntityBean(pre.getId());
			return;
		}

		// 地址不全,不发
		if (distVO.getAddress().trim().equals("0") && distVO.getReceiver().trim().equals("0")
				&& distVO.getMobile().trim().equals("0")) {
			_logger.warn("***wrong address***");
			return;
		}

		String fullAddress = distVO.getProvinceName() + distVO.getCityName() + distVO.getAddress();

		// 此客户是否存在同一个发货包裹,且未拣配
		ConditionParse con = new ConditionParse();
		con.addWhereStr();

		//#930 检查是否开票申请关联了预先分配快递单号
		PrePackageBean prePackageBean = this.getPrePackageBeanForIns(insId);
		if (prePackageBean == null){
			setInnerCondition(distVO, location, con, ins.getCustomerId(), null);
		} else{
			_logger.info(insId + "**** has orders in prePackageBean***"+prePackageBean);
			setInnerConditionForPre(distVO, location, con, ins.getCustomerId(), null, prePackageBean);
		}

		List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);

		if (ListTools.isEmptyOrNull(packageList)) {
			createNewInsPackage(ins, numList, distVO, fullAddress, location, prePackageBean);
		} else {
			String id = packageList.get(0).getId();

			PackageBean packBean = packageDAO.find(id);

			// 不存在或已不是初始状态(可能已被拣配)
			if (null == packBean || (packBean.getStatus() != 0
					&& packBean.getStatus() != ShipConstant.SHIP_STATUS_PRINT_INVOICEINS)) {
				createNewInsPackage(ins, numList, distVO, fullAddress, location, null);
			} else {
				_logger.info("***package already exists***" + id);
				// #200 合并入现有CK单时检查是否有重复outId
				List<PackageItemBean> itemList = new ArrayList<>();

				int allAmount = 0;
				double total = 0;

				String sb = getPrintTextForIns(ins);

				boolean first = false;

				_logger.info("***loop1111***" + id);
				for (InsVSInvoiceNumBean base : numList) {
					if (this.contains(itemList, insId)) {
						_logger.warn(insId + "***insId already packaged***" + packBean.getId());
						continue;
					}
					PackageItemBean item = new PackageItemBean();

					item.setPackageId(id);
					item.setOutId(ins.getId());
					item.setBaseId(base.getId());
					item.setProductId(base.getInvoiceNum());
					item.setProductName("发票号：" + base.getInvoiceNum());
					item.setAmount(1);
					item.setPrice(base.getMoneys());
					item.setValue(base.getMoneys());
					item.setOutTime(
							TimeTools.changeFormat(ins.getLogTime(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
					item.setDescription(ins.getDescription());
					item.setCustomerId(ins.getCustomerId());
					if (!first) {
						item.setPrintText(sb);
					}

					first = true;

					itemList.add(item);
					_logger.info("***create package item***" + item);
					allAmount += item.getAmount();
					total += base.getMoneys();
				}

				packBean.setAmount(packBean.getAmount() + allAmount);
				packBean.setTotal(packBean.getTotal() + total);
				packBean.setProductCount(packBean.getProductCount() + numList.size());
				_logger.info("***loop***222222" + itemList);
				if (this.isDirectShipped(itemList)) {
					packBean.setDirect(1);
				}
				try {
					packageDAO.updateEntityBean(packBean);
				} catch (Exception e) {
					_logger.error(e);
				}
				_logger.info(insId + "***merge to exist package***" + packBean.getId());

				if (!ListTools.isEmptyOrNull(itemList)) {
					try {
						packageItemDAO.saveAllEntityBeans(itemList);
					} catch (Exception e) {
						_logger.error(e);
					}
				}

				// 包与客户关系
				PackageVSCustomerBean vsBean = packageVSCustomerDAO.findByUnique(id, ins.getCustomerId());

				if (null == vsBean) {
					int count = packageVSCustomerDAO.countByCondition("where packageId = ?", id);

					PackageVSCustomerBean newvsBean = new PackageVSCustomerBean();

					newvsBean.setPackageId(id);
					newvsBean.setCustomerId(ins.getCustomerId());
					newvsBean.setCustomerName(ins.getCustomerName());
					newvsBean.setIndexPos(count + 1);
					try {
						packageVSCustomerDAO.saveEntityBean(newvsBean);
					} catch (Exception e) {
						_logger.error(e);
					}
				}
			}
		}

		_logger.info("***delete preconsign***" + pre.getOutId());
		preConsignDAO.deleteEntityBean(pre.getId());
	}

	private boolean contains(List<PackageItemBean> items, String insId) {
		for (PackageItemBean item : items) {
			if (item.getOutId().equals(insId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 2015/3/1 预开票也需要进入CK单
	 * 
	 * @param pre
	 * @param bean
	 * @throws MYException
	 */
	public void createPreInsPackage(PreConsignBean pre, PreInvoiceApplyVO bean) throws MYException {
		// 此客户是否存在同一个发货包裹,且未拣配
		ConditionParse con = new ConditionParse();
		con.addWhereStr();

		String fullAddress = bean.getAddress();
		String temp = fullAddress.trim();

		if (temp.length() >= 6) {
			con.addCondition("PackageBean.address", "like", "%" + temp.substring(temp.length() - 6));
		} else {
			con.addCondition("PackageBean.address", "like", "%" + temp);
		}

		con.addCondition("PackageBean.receiver", "=", bean.getReceiver());

		con.addCondition("PackageBean.mobile", "=", bean.getMobile());

		con.addIntCondition("PackageBean.status", "=", 0);

		List<PackageVO> packageList = packageDAO.queryVOsByCondition(con);

//		if (packageList.size() > 1){
//			throw new MYException("数据异常,生成发货单出错.");
//		}

		if (ListTools.isEmptyOrNull(packageList)) {
			// TODO
			createNewPreInsPackage(bean);
		} else {
			String id = packageList.get(0).getId();

			PackageBean packBean = packageDAO.find(id);

			// 不存在或已不是初始状态(可能已被拣配)
			if (null == packBean || (packBean.getStatus() != 0
					&& packBean.getStatus() != ShipConstant.SHIP_STATUS_PRINT_INVOICEINS)) {
				createNewPreInsPackage(bean);
			} else {
				List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();
				PackageItemBean item = new PackageItemBean();

				item.setPackageId(id);
				item.setOutId(bean.getId());
				item.setBaseId(bean.getId());
				// 2015/12/8 发票号作为productId
				item.setProductId(bean.getInvoiceNumber());
				item.setProductName("发票号：" + bean.getInvoiceNumber());
				item.setAmount(1);
				item.setPrice(bean.getInvoiceMoney());
				item.setValue(bean.getInvoiceMoney());
				item.setOutTime(
						TimeTools.changeFormat(bean.getLogTime(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
				item.setDescription(bean.getDescription());
				item.setCustomerId(bean.getCustomerId());
				item.setPrintText("test text");

				itemList.add(item);

				packBean.setAmount(packBean.getAmount() + 1);
				packBean.setTotal(packBean.getTotal() + bean.getInvoiceMoney());
				packBean.setProductCount(packBean.getProductCount() + 1);

				if (this.isDirectShipped(itemList)) {
					packBean.setDirect(1);
				}
				packageDAO.updateEntityBean(packBean);

				packageItemDAO.saveAllEntityBeans(itemList);

				// 包与客户关系
				PackageVSCustomerBean vsBean = packageVSCustomerDAO.findByUnique(id, bean.getCustomerId());

				if (null == vsBean) {
					int count = packageVSCustomerDAO.countByCondition("where packageId = ?", id);

					PackageVSCustomerBean newvsBean = new PackageVSCustomerBean();

					newvsBean.setPackageId(id);
					newvsBean.setCustomerId(bean.getCustomerId());
					newvsBean.setCustomerName(bean.getCustomerName());
					newvsBean.setIndexPos(count + 1);

					packageVSCustomerDAO.saveEntityBean(newvsBean);
				}
			}
		}

		preConsignDAO.deleteEntityBean(pre.getId());
	}

	private void createNewPreInsPackage(PreInvoiceApplyVO ins) {
		String id = commonDAO.getSquenceString20("CK");

		PackageBean packBean = new PackageBean();

		packBean.setId(id);
		packBean.setCustomerId(ins.getCustomerId());

		packBean.setShipping(ins.getShipping());
		packBean.setTransport1(ins.getTransport1());
		packBean.setExpressPay(ins.getExpressPay());
		packBean.setTransport2(ins.getTransport2());
		packBean.setTransportPay(ins.getTransportPay());
		packBean.setCityId(ins.getCityId());

		// #410
		packBean.setLocationId("99");
		// #411
		String fullAddress = ins.getProvinceName() + ins.getCityName() + ins.getAddress();
		packBean.setAddress(fullAddress.trim());

		packBean.setReceiver(ins.getReceiver());
		packBean.setMobile(ins.getMobile());

		packBean.setStafferName(ins.getStafferName());

		StafferVO staff = stafferDAO.findVO(ins.getStafferId());

		if (null != staff) {
			packBean.setIndustryName(staff.getIndustryName());
			packBean.setDepartName(staff.getIndustryName3());
		}

		packBean.setTotal(ins.getTotal());
		packBean.setStatus(0);
		String now = TimeTools.now();
		packBean.setCreateTime(now);
		packBean.setLogTime(now);

//        StringBuilder sb = getPrintTextForIns(ins);

		List<PackageItemBean> itemList = new ArrayList<PackageItemBean>();

		PackageItemBean item = new PackageItemBean();

		item.setPackageId(id);
		item.setOutId(ins.getId());

		item.setProductName("发票号：" + ins.getInvoiceNumber());
		// 2015/12/8 预开票申请已发票号作为productId
		item.setProductId(ins.getInvoiceNumber());

		item.setAmount(1);
		item.setPrice(ins.getInvoiceMoney());
		item.setValue(ins.getInvoiceMoney());
		item.setOutTime(TimeTools.changeFormat(ins.getLogTime(), TimeTools.LONG_FORMAT, TimeTools.SHORT_FORMAT));
		item.setDescription(ins.getDescription());
		item.setCustomerId(ins.getCustomerId());
		// TODO
		item.setPrintText("test print text");

		itemList.add(item);

		packBean.setAmount(1);

		packBean.setProductCount(1);

		PackageVSCustomerBean vsBean = new PackageVSCustomerBean();

		vsBean.setPackageId(id);
		vsBean.setCustomerId(ins.getCustomerId());
		vsBean.setCustomerName(ins.getCustomerName());
		vsBean.setIndexPos(1);

		packBean.setPrintInvoiceinsStatus(itemList);
		this.savePackage(packBean, itemList);
		packageItemDAO.saveAllEntityBeans(itemList);

		packageVSCustomerDAO.saveEntityBean(vsBean);
	}

	private void savePackage(PackageBean packageBean, List<PackageItemBean> itemList) {
		if (this.isDirectShipped(itemList)) {
			packageBean.setDirect(1);
		}

		packageDAO.saveEntityBean(packageBean);
		this.addLog(packageBean.getId(), ShipConstant.SHIP_STATUS_INIT, ShipConstant.SHIP_STATUS_INIT, "生成CK单");
		_logger.info(String.format("生成CK单:%s direct:%s", packageBean.getId(), String.valueOf(packageBean.getDirect())));
	}

	/**
	 * 2015/2/3 票随货发合并订单及发票
	 * 
	 * @param outIdList
	 * @throws MYException
	 */
	@Override
	@Deprecated
	public void createPackage(final List<String> outIdList) throws MYException {
		// To change body of implemented methods use File | Settings | File Templates.
		if (!ListTools.isEmptyOrNull(outIdList)) {
			long statsStar = System.currentTimeMillis();

			TransactionTemplate tran = new TransactionTemplate(transactionManager);

			try {
				tran.execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						try {
							processOut2(outIdList);
						} catch (MYException e) {
							throw new RuntimeException(e);
						}

						return Boolean.TRUE;
					}
				});
			} catch (Exception e) {
				triggerLog.error(e, e);
			}

			triggerLog.info("createPackage 票随货发合并订单及发票统计结束... ,共耗时：" + (System.currentTimeMillis() - statsStar));
		}
	}

	private void processOut2(List<String> outIdList) throws MYException {
		for (int i = 0; i < outIdList.size(); i++) {
			String outId = outIdList.get(i);
			InvoiceinsBean insBean = invoiceinsDAO.find(outId);
			if (insBean != null) {
				insBean.setPackaged(1);
				this.invoiceinsDAO.updateEntityBean(insBean);
				_logger.info("InsVSOutBean updated to packaged***" + outId);
			}
		}
	}

	// add by zhangxian 2019-05-20
	public void queryPackageinfo2taobao() {
		List<TaobaoLogisticsBean> logisticsList = taoBaoTokenDAO.queryTaobaoLogisticsList();

		for (TaobaoLogisticsBean logistics : logisticsList) {
			String customerId = logistics.getCustomerId();
			// 获取sessionkey
			List<TaobaoTokenBean> tokenList = taoBaoTokenDAO.queryLastToken(customerId);
			if (tokenList.size() == 0) {
				taobaoLog.info("customerid:" + customerId + " taobao token is null");
				continue;
			}
			TaobaoTokenBean tokenBean = tokenList.get(0);
			String sessionKey = tokenBean.getToken();
			TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "27432470",
					"bf3d1a704f406e23235679a9cf1a877c");
			LogisticsOnlineSendRequest req = new LogisticsOnlineSendRequest();
			req.setTid(Long.valueOf(logistics.getCiticNo().trim()));
			req.setOutSid(logistics.getTransportNo().trim());
			req.setCompanyCode(logistics.getLogosticsCode().trim());
			LogisticsOnlineSendResponse rsp;
			try {
				taobaoLog.info("taobao logistics request:tid:" + logistics.getCiticNo() + "||outid:"
						+ logistics.getTransportNo() + "||companycode:" + logistics.getLogosticsCode());
				rsp = client.execute(req, sessionKey);
				taobaoLog.info("taobao logistics response:" + rsp.getBody());
				JSONObject resultJson = JSONObject.fromObject(rsp.getBody());
				Object errorObj = resultJson.get("error_response");
				if (errorObj == null) {
					// 表示成功
					Object successObj = resultJson.get("logistics_online_send_response");
					if (successObj != null) {
						//调用confirm接口
						LogisticsOnlineConfirmRequest confirmreq = new LogisticsOnlineConfirmRequest();
						confirmreq.setTid(Long.valueOf(logistics.getCiticNo().trim()));
						confirmreq.setOutSid(logistics.getTransportNo().trim());
						LogisticsOnlineConfirmResponse confirmrsp = client.execute(confirmreq, sessionKey);
						taobaoLog.info("taobao logistics confirm response:" + confirmrsp.getBody());
						JSONObject confirmresultJson = JSONObject.fromObject(confirmrsp.getBody());
						Object confirmerrorObj = confirmresultJson.get("error_response");
						if (confirmerrorObj == null) {
							taoBaoTokenDAO.updatePackageStatus(logistics.getId());
						}
						
					}
				} else {
					JSONObject resp = JSONObject.fromObject(errorObj);
					String code = resp.getString("code");
					String message = resp.getString("sub_msg");
					taobaoLog.info("taobao logistics error code:" + code + ";error message:" + message);
				}

			} catch (ApiException e) {
				taobaoLog.error("taobao logistics request:tid:" + logistics.getCiticNo() + " error", e);
			}
		}

	}

	// end add

	/**
	 * @return the preConsignDAO
	 */
	public PreConsignDAO getPreConsignDAO() {
		return preConsignDAO;
	}

	/**
	 * @param preConsignDAO the preConsignDAO to set
	 */
	public void setPreConsignDAO(PreConsignDAO preConsignDAO) {
		this.preConsignDAO = preConsignDAO;
	}

	/**
	 * @return the packageDAO
	 */
	public PackageDAO getPackageDAO() {
		return packageDAO;
	}

	/**
	 * @param packageDAO the packageDAO to set
	 */
	public void setPackageDAO(PackageDAO packageDAO) {
		this.packageDAO = packageDAO;
	}

	/**
	 * @return the packageItemDAO
	 */
	public PackageItemDAO getPackageItemDAO() {
		return packageItemDAO;
	}

	/**
	 * @param packageItemDAO the packageItemDAO to set
	 */
	public void setPackageItemDAO(PackageItemDAO packageItemDAO) {
		this.packageItemDAO = packageItemDAO;
	}

	/**
	 * @return the outDAO
	 */
	public OutDAO getOutDAO() {
		return outDAO;
	}

	/**
	 * @param outDAO the outDAO to set
	 */
	public void setOutDAO(OutDAO outDAO) {
		this.outDAO = outDAO;
	}

	/**
	 * @return the baseDAO
	 */
	public BaseDAO getBaseDAO() {
		return baseDAO;
	}

	/**
	 * @param baseDAO the baseDAO to set
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * @return the distributionDAO
	 */
	public DistributionDAO getDistributionDAO() {
		return distributionDAO;
	}

	/**
	 * @param distributionDAO the distributionDAO to set
	 */
	public void setDistributionDAO(DistributionDAO distributionDAO) {
		this.distributionDAO = distributionDAO;
	}

	/**
	 * @return the commonDAO
	 */
	public CommonDAO getCommonDAO() {
		return commonDAO;
	}

	/**
	 * @param commonDAO the commonDAO to set
	 */
	public void setCommonDAO(CommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	/**
	 * @return the transactionManager
	 */
	public PlatformTransactionManager getTransactionManager() {
		return transactionManager;
	}

	/**
	 * @param transactionManager the transactionManager to set
	 */
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	/**
	 * @return the depotDAO
	 */
	public DepotDAO getDepotDAO() {
		return depotDAO;
	}

	/**
	 * @param depotDAO the depotDAO to set
	 */
	public void setDepotDAO(DepotDAO depotDAO) {
		this.depotDAO = depotDAO;
	}

	/**
	 * @return the packageVSCustomerDAO
	 */
	public PackageVSCustomerDAO getPackageVSCustomerDAO() {
		return packageVSCustomerDAO;
	}

	/**
	 * @param packageVSCustomerDAO the packageVSCustomerDAO to set
	 */
	public void setPackageVSCustomerDAO(PackageVSCustomerDAO packageVSCustomerDAO) {
		this.packageVSCustomerDAO = packageVSCustomerDAO;
	}

	public InvoiceinsDAO getInvoiceinsDAO() {
		return invoiceinsDAO;
	}

	public void setInvoiceinsDAO(InvoiceinsDAO invoiceinsDAO) {
		this.invoiceinsDAO = invoiceinsDAO;
	}

	public InsVSInvoiceNumDAO getInsVSInvoiceNumDAO() {
		return insVSInvoiceNumDAO;
	}

	public void setInsVSInvoiceNumDAO(InsVSInvoiceNumDAO insVSInvoiceNumDAO) {
		this.insVSInvoiceNumDAO = insVSInvoiceNumDAO;
	}

	public StafferDAO getStafferDAO() {
		return stafferDAO;
	}

	public void setStafferDAO(StafferDAO stafferDAO) {
		this.stafferDAO = stafferDAO;
	}

	/**
	 * @return the invoiceinsItemDAO
	 */
	public InvoiceinsItemDAO getInvoiceinsItemDAO() {
		return invoiceinsItemDAO;
	}

	/**
	 * @param invoiceinsItemDAO the invoiceinsItemDAO to set
	 */
	public void setInvoiceinsItemDAO(InvoiceinsItemDAO invoiceinsItemDAO) {
		this.invoiceinsItemDAO = invoiceinsItemDAO;
	}

	/**
	 * @return the outImportDAO
	 */
	public OutImportDAO getOutImportDAO() {
		return outImportDAO;
	}

	/**
	 * @param outImportDAO the outImportDAO to set
	 */
	public void setOutImportDAO(OutImportDAO outImportDAO) {
		this.outImportDAO = outImportDAO;
	}

	public OutManager getOutManager() {
		return outManager;
	}

	public void setOutManager(OutManager outManager) {
		this.outManager = outManager;
	}

	/**
	 * @return the preInvoiceApplyDAO
	 */
	public PreInvoiceApplyDAO getPreInvoiceApplyDAO() {
		return preInvoiceApplyDAO;
	}

	/**
	 * @param preInvoiceApplyDAO the preInvoiceApplyDAO to set
	 */
	public void setPreInvoiceApplyDAO(PreInvoiceApplyDAO preInvoiceApplyDAO) {
		this.preInvoiceApplyDAO = preInvoiceApplyDAO;
	}

	public ProductExchangeConfigDAO getProductExchangeConfigDAO() {
		return productExchangeConfigDAO;
	}

	public void setProductExchangeConfigDAO(ProductExchangeConfigDAO productExchangeConfigDAO) {
		this.productExchangeConfigDAO = productExchangeConfigDAO;
	}

	public FlowLogDAO getFlowLogDAO() {
		return flowLogDAO;
	}

	public void setFlowLogDAO(FlowLogDAO flowLogDAO) {
		this.flowLogDAO = flowLogDAO;
	}

	public TwOutDAO getTwOutDAO() {
		return twOutDAO;
	}

	public void setTwOutDAO(TwOutDAO twOutDAO) {
		this.twOutDAO = twOutDAO;
	}

	public TwBaseDAO getTwBaseDAO() {
		return twBaseDAO;
	}

	public void setTwBaseDAO(TwBaseDAO twBaseDAO) {
		this.twBaseDAO = twBaseDAO;
	}

	public TwDistributionDAO getTwDistributionDAO() {
		return twDistributionDAO;
	}

	public void setTwDistributionDAO(TwDistributionDAO twDistributionDAO) {
		this.twDistributionDAO = twDistributionDAO;
	}

	public TaoBaoTokenDAO getTaoBaoTokenDAO() {
		return taoBaoTokenDAO;
	}

	public void setTaoBaoTokenDAO(TaoBaoTokenDAO taoBaoTokenDAO) {
		this.taoBaoTokenDAO = taoBaoTokenDAO;
	}

}
