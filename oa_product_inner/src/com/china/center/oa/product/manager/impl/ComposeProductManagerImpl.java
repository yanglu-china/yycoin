/**
 * File Name: ComposeProductManagerImpl.java<br>
 * CopyRight: Copyright by www.center.china<br>
 * Description:<br>
 * CREATER: ZHUACHEN<br>
 * CreateTime: 2010-10-2<br>
 * Grant: open source to everybody
 */
package com.china.center.oa.product.manager.impl;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.center.china.osgi.config.ConfigLoader;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.product.bean.*;
import com.china.center.oa.product.dao.*;
import com.china.center.oa.product.manager.PriceConfigManager;
import com.china.center.oa.product.vs.StorageRelationBean;
import com.china.center.oa.publics.NumberUtils;
import com.china.center.oa.publics.bean.LogBean;
import com.china.center.oa.publics.constant.AppConstant;
import com.china.center.oa.publics.constant.ModuleConstant;
import com.china.center.oa.publics.constant.OperationConstant;
import com.china.center.oa.publics.dao.LogDAO;
import com.china.center.tools.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.china.center.spring.ex.annotation.Exceptional;
import org.springframework.transaction.annotation.Transactional;

import com.center.china.osgi.publics.AbstractListenerManager;
import com.center.china.osgi.publics.User;
import com.china.center.common.MYException;
import com.china.center.jdbc.annosql.constant.AnoConstant;
import com.china.center.jdbc.expression.Expression;
import com.china.center.oa.product.constant.ComposeConstant;
import com.china.center.oa.product.constant.ProductApplyConstant;
import com.china.center.oa.product.constant.ProductConstant;
import com.china.center.oa.product.constant.StorageConstant;
import com.china.center.oa.product.listener.ComposeProductListener;
import com.china.center.oa.product.manager.ComposeProductManager;
import com.china.center.oa.product.manager.StorageRelationManager;
import com.china.center.oa.product.vo.ComposeFeeDefinedVO;
import com.china.center.oa.product.vo.ComposeProductVO;
import com.china.center.oa.product.wrap.ProductChangeWrap;
import com.china.center.oa.publics.bean.FlowLogBean;
import com.china.center.oa.publics.constant.PublicConstant;
import com.china.center.oa.publics.dao.CommonDAO;
import com.china.center.oa.publics.dao.FlowLogDAO;
import com.china.center.oa.publics.helper.OATools;
import com.china.center.oa.publics.message.MessageConstant;
import com.china.center.oa.publics.message.PublishMessage;


/**
 * ComposeProductManagerImpl
 * 
 * @author ZHUZHU
 * @version 2010-10-2
 * @see ComposeProductManagerImpl
 * @since 1.0
 */
@Exceptional
public class ComposeProductManagerImpl extends AbstractListenerManager<ComposeProductListener> implements ComposeProductManager
{
    private final Log _logger = LogFactory.getLog(getClass());

    private final Log oprLogger = LogFactory.getLog("opr");

    private ComposeProductDAO composeProductDAO = null;

    private ComposeItemDAO composeItemDAO = null;

    private ComposeFeeDAO composeFeeDAO = null;

    private ProductDAO productDAO = null;

    private ComposeFeeDefinedDAO composeFeeDefinedDAO = null;
    
    private DecomposeProductDAO decomposeProductDAO = null;

    private StorageRelationManager storageRelationManager = null;

    private PublishMessage publishMessage = null;

    private CommonDAO commonDAO = null;
    
    private FlowLogDAO flowLogDAO = null;

    private StorageRelationDAO storageRelationDAO = null;

    private PriceConfigDAO priceConfigDAO = null;

    private PriceConfigManager priceConfigManager = null;

    private LogDAO logDAO = null;

    /**
     * default constructor
     */
    public ComposeProductManagerImpl()
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.ComposeProductManager#addComposeProduct(com.center.china.osgi.publics.User,
     *      com.china.center.oa.product.bean.ComposeProductBean)
     */
    @Transactional(rollbackFor = MYException.class)
    public boolean addComposeProduct(User user, ComposeProductBean bean)
        throws MYException
    {
        _logger.info("***addComposeProduct***"+bean);
        JudgeTools.judgeParameterIsNull(user, bean);

        // 校验
        checkCompose(bean);

        saveInner(bean, false);

        return true;
    }


    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean updateComposeProduct(User user, ComposeProductBean composeProductBean) throws MYException {
        _logger.info("***updateComposeProduct***"+composeProductBean);
        String id = composeProductBean.getId();
        //Remove old data
        composeItemDAO.deleteEntityBeansByFK(id);
        composeFeeDAO.deleteEntityBeansByFK(id);

        this.composeProductDAO.updateEntityBean(composeProductBean);
        List<ComposeItemBean> itemList = composeProductBean.getItemList();
        for(ComposeItemBean item :itemList){
            item.setParentId(id);
        }
        this.composeItemDAO.saveAllEntityBeans(itemList);
        List<ComposeFeeBean> feeList = composeProductBean.getFeeList();
        for (ComposeFeeBean item: feeList){
            item.setParentId(id);
        }
        this.composeFeeDAO.saveAllEntityBeans(feeList);
        return true;
    }

    /**
     * checkAddCompose
     * 
     * @param bean
     * @throws MYException
     */
    private void checkCompose(ComposeProductBean bean)
        throws MYException
    {
        // 检查合成逻辑
        String productId = bean.getProductId();
        
        ProductBean compose = productDAO.find(productId);

        if (compose == null)
        {
            if (!StringTools.isNullOrNone(bean.getDirTargerName())){
                compose = this.productDAO.findByName(bean.getDirTargerName());
                if (compose == null){
                    throw new MYException("产品不存在："+productId);
                } else{
                    bean.setProductId(compose.getId());
                }
            } else{
                throw new MYException("产品不存在："+productId);
            }
        }

        // #457
//        if (compose.getCtype() != ProductConstant.CTYPE_YES)
//        {
//            throw new MYException("目的产品不是合成产品,请确认操作");
//        }

        // MANAGER 合成产品增加的管理逻辑
        /*if (OATools.isCommon(compose.getReserve4()))
        {
            // 必须都是普通产品才能合成
        	 List<ComposeItemBean> itemList = bean.getItemList();

            for (ComposeItemBean composeItemBean : itemList)
            {
                ProductBean each = productDAO.find(composeItemBean.getProductId());

                if (each == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }
                
                // 会有什么影响？？ 130725
                if (OATools.getManagerType(each.getReserve4()) != PublicConstant.MANAGER_TYPE_COMMON)
                {
                    throw new MYException("合成的源产品的管理类型必须都是普通,错误产品:%s", each.getName());
                }
            }

            bean.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
        }
        else
        {
            bean.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
        }*/
        
        int counter = 0;
        
        List<ComposeItemBean> itemList = bean.getItemList();

        for (ComposeItemBean composeItemBean : itemList)
        {
            ProductBean each = productDAO.find(composeItemBean.getProductId());

            if (each == null)
            {
                throw new MYException("数据错误,请确认操作");
            }
            
            composeItemBean.setMtype(MathTools.parseInt(each.getReserve4()));
            
            counter += composeItemBean.getMtype();
        }
        
        // 全部管理 或 普通 ， 合成产品的管理属性须与源产品的管理属性一致
        if (counter == 0 || counter == itemList.size())
        {
        	if (bean.getMtype() != itemList.get(0).getMtype())
        	{
        		throw new MYException("合成的源产品的管理属性全为普通或管理时，合成产品管理属性须与源产品一致");
        	}
        }
    }

    /**
     * 2015/7/27 预合成JOB检查
     * @param bean
     * @throws MYException
     */
    private void preCheckCompose(ComposeProductBean bean)
            throws MYException
    {
        // 检查合成逻辑
        String productId = bean.getProductId();

        ProductBean compose = productDAO.find(productId);

        if (compose == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        // #457
/*        if (compose.getCtype() != ProductConstant.CTYPE_YES)
        {
            throw new MYException("目的产品不是合成产品,请确认操作");
        }*/


        int counter = 0;

        double total = 0.0d;

        //TODO
//        List<ComposeFeeBean> feeList = new ArrayList<ComposeFeeBean>();
//        for (int i = 0; i < feeItems.length; i++ )
//        {
//            if ( !MathTools.equal(0.0, CommonTools.parseFloat(feeItems[i])))
//            {
//                ComposeFeeBean each = new ComposeFeeBean();
//                each.setFeeItemId(feeItemIds[i]);
//                each.setPrice(CommonTools.parseFloat(feeItems[i]));
//                each.setLogTime(bean.getLogTime());
//                each.setDescription(idescriptions[i]);
//                feeList.add(each);
//
//                total += each.getPrice();
//            }
//        }

        List<ComposeItemBean> itemList = bean.getItemList();

        for (ComposeItemBean composeItemBean : itemList)
        {
            ProductBean each = productDAO.find(composeItemBean.getProductId());

            if (each == null)
            {
                throw new MYException("数据错误,请确认操作");
            }

            //检查对应库区中是否有符合合成条件的产品及数量
            ConditionParse con = new ConditionParse();

            con.addWhereStr();

            con.addCondition("StorageRelationBean.depotpartId", "=", composeItemBean.getDepotpartId());

            con.addCondition("StorageRelationBean.productId", "=", composeItemBean.getProductId());

            con.addIntCondition("StorageRelationBean.amount", ">=", composeItemBean.getAmount());

            List<StorageRelationBean> relationBeanList = this.storageRelationDAO.queryEntityBeansByCondition(con);
            if (ListTools.isEmptyOrNull(relationBeanList)){
                String template = "仓区:%s中源产品:%s库存%d不足无法合成!";
                String msg = String.format(template, composeItemBean.getDepotpartId(), composeItemBean.getProductId(),
                        composeItemBean.getAmount());
                _logger.warn(msg);
                throw new MYException(msg);
            } else {
                StorageRelationBean storageRelationBean = relationBeanList.get(0);
                composeItemBean.setPrice(storageRelationBean.getPrice());
                _logger.info(bean+" set price to:"+storageRelationBean.getPrice());
            }

            composeItemBean.setMtype(MathTools.parseInt(each.getReserve4()));

            counter += composeItemBean.getMtype();

            total += composeItemBean.getPrice() * composeItemBean.getAmount();
        }
        bean.setPrice(total);

        // 全部管理 或 普通 ， 合成产品的管理属性须与源产品的管理属性一致
        if (counter == 0 || counter == itemList.size())
        {
            if (bean.getMtype() != itemList.get(0).getMtype())
            {
                throw new MYException("合成的源产品的管理属性全为普通或管理时，合成产品管理属性须与源产品一致");
            }
        }
    }

    @Transactional(rollbackFor = MYException.class)
    @Override
    public boolean preComposeProduct(User user, ComposeProductBean composeProductBean) throws MYException {
        JudgeTools.judgeParameterIsNull(user, composeProductBean);

        saveInner(composeProductBean, true);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    @Override
    public void composeProductJob() throws MYException {
        //To change body of implemented methods use File | Settings | File Templates.
        _logger.info("************composeProductJob running***********");
        ConditionParse condition = new ConditionParse();
//        condition.addCondition("status", "=", ComposeConstant.STATUS_PRE_SUBMIT);
        condition.addCondition("status", "=", ComposeConstant.STATUS_PRE_COMPOSE);
//        System.out.println("**************condition********"+condition);
//        _logger.info("**************condition********"+condition);
        List<ComposeProductBean> beans = this.composeProductDAO.queryEntityBeansByCondition(condition);
        if (ListTools.isEmptyOrNull(beans)){
            _logger.info("No pre-ComposeProductBean");
        } else{
            _logger.info("composeProductJob with size:"+beans.size());
            for (ComposeProductBean bean : beans){
                try{
                    List<ComposeItemBean> items = this.composeItemDAO.queryEntityBeansByFK(bean.getId());
                    bean.setItemList(items);

                    this.preCheckCompose(bean);

                    //update status to SUBMIT
                    ComposeProductBean beanInDb = composeProductDAO.find(bean.getId());
                    if (beanInDb == null){
                       _logger.error("ComposeProductBean not found:"+bean);
                    } else{
//                        beanInDb.setStatus(ComposeConstant.STATUS_SUBMIT);
                        beanInDb.setStatus(ComposeConstant.STATUS_SUBMIT);
                        beanInDb.setPrice(bean.getPrice());
                        this.composeProductDAO.updateEntityBean(beanInDb);
                        _logger.info("ComposeProductBean is submitted:"+bean);

                        //update ComposeItemBean price
                        if (!ListTools.isEmptyOrNull(items)){
                            for (ComposeItemBean composeItemBean : items){
                                this.composeItemDAO.updateEntityBean(composeItemBean);
                                _logger.info("ComposeItemBean update price:" + composeItemBean);
                            }
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    _logger.error("Fail to compose product:"+bean, e);
                }
            }
        }
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rollbackComposeProduct(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        ComposeProductBean bean = findBeanById(id);

        if (bean == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if ( !bean.getStafferId().equals(user.getStafferId()))
        {
            throw new MYException("只能操作自己的单据,请确认操作");
        }

        if (bean.getType() != ComposeConstant.COMPOSE_TYPE_COMPOSE
                && bean.getType() != ComposeConstant.COMPOSE_TYPE_PRE_COMPOSE)
        {
            throw new MYException("数据错误,请确认操作");
        }

        int count = composeProductDAO.countByFK(id);

        if (count > 0)
        {
            throw new MYException("此合成单存在回滚申请,请先结束此申请或者删除(或者分解已经成功)");
        }

        bean.setRefId(id);

        bean.setType(ComposeConstant.COMPOSE_TYPE_DECOMPOSE);

        bean.setLogTime(TimeTools.now());

        List<ComposeFeeBean> feeList = bean.getFeeList();

        if ( !ListTools.isEmptyOrNull(feeList))
        {
            for (ComposeFeeBean composeFeeBean : feeList)
            {
                composeFeeBean.setParentId(bean.getId());

                // 费用是负数
                composeFeeBean.setPrice( -composeFeeBean.getPrice());
            }
        }

        // 校验
        checkCompose(bean);

        saveInner(bean, false);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean lastPassComposeProduct(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        ComposeProductBean bean = findBeanById(id);

        if (bean == null)
        {
            throw new MYException("合成单不存在");
        }

        if (bean.getStatus() != ComposeConstant.STATUS_MANAGER_PASS)
        {
            throw new MYException("合成单状态不正确,不能最终合成产品");
        }

        
        //composeProductDAO.updateStatus(id, ComposeConstant.STATUS_CRO_PASS);
        bean.setStatus(ComposeConstant.STATUS_CRO_PASS);

        // 结束时间
        //composeProductDAO.updateLogTime(id, TimeTools.now());
        bean.setLogTime(TimeTools.now());
        
        if (bean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
		{
        	bean.setTag("HCP1");
		}else{
			bean.setTag("HCG1");
		}
        
        composeProductDAO.updateEntityBean(bean);

        // 修改库存(合成)
        if (bean.getType() == ComposeConstant.COMPOSE_TYPE_COMPOSE
                //2015/8/3 预合成类型
                || bean.getType() == ComposeConstant.COMPOSE_TYPE_PRE_COMPOSE)
        {
            processCompose(user, bean);
        }
        else
        {
            // 分解
            processDecompose(user, bean);
        }

        // 根据源产品管理/普通组成情况，如果是普通+管理，则需一拆为三
        boolean hybrid = false;
        
        hybrid = checkIfHybrid(bean);
        
        // TAX_ADD 产品合成/分解-运营总监通过
        Collection<ComposeProductListener> listenerMapValues = this.listenerMapValues();
        
        // 拆分
        if (hybrid)
        {
        	// 原合成申请混合标记
        	composeProductDAO.updateHybrid(id, 1);
        	
        	List<ComposeProductBean> list = splitComposeProduct(bean);
        	
			for (ComposeProductListener composeProductListener : listenerMapValues)
			{
				 for (ComposeProductBean each : list)
				{
					composeProductListener.onConfirmCompose(user, each);
				}
			}
        	
        }else{
            for (ComposeProductListener composeProductListener : listenerMapValues)
            {
                composeProductListener.onConfirmCompose(user, bean);
            }
        }

        return true;
    }
    
    /**
     * 
     * @param bean
     */
    private List<ComposeProductBean> splitComposeProduct(ComposeProductBean bean) throws MYException
    {
    	List<ComposeProductBean> list = new ArrayList<ComposeProductBean>();
    	
    	// 分组
    	Map<Integer, List<ComposeItemBean>> map = new HashMap<Integer, List<ComposeItemBean>>();
    	
    	for (ComposeItemBean each : bean.getItemList())
    	{
    		if (!map.containsKey(each.getMtype()))
    		{
    			List<ComposeItemBean> innerList = new ArrayList<ComposeItemBean>();
    			
    			innerList.add(each);
    			
    			map.put(each.getMtype(), innerList);
    		}else
    		{
    			List<ComposeItemBean> innerList = map.get(each.getMtype());
    			
    			innerList.add(each);
    		}
    	}
    	
    	for (Map.Entry<Integer, List<ComposeItemBean>> entry : map.entrySet())
    	{
    		ComposeProductBean newBean = createSemiComposeProduct(bean, entry.getKey(), entry.getValue());
    		
    		list.add(newBean);
    	}
    	
    	// 管理、普通半成品作为源产品，原合成产品为合成产品，重新生成合成品
    	createComposeProduct(bean, list);
    	
    	return list;
    }
    
    /**
     * 创建半成品的合成单
     * @param bean
     * @param itemList
     * @return
     */
    private ComposeProductBean createSemiComposeProduct(ComposeProductBean bean, int mtype, List<ComposeItemBean> itemList)
    	throws MYException
    {
    	ComposeProductBean newBean = new ComposeProductBean();
    	
    	List<ComposeFeeBean> newFeeList = new ArrayList<ComposeFeeBean>();
    	
    	List<ComposeItemBean> newItemList = new ArrayList<ComposeItemBean>();
    	
    	BeanUtil.copyProperties(newBean, bean);
    	
    	newBean.setFeeList(newFeeList);
    	newBean.setItemList(newItemList);
    	
    	newBean.setId(commonDAO.getSquenceString20());
    	
    	// 创建一个半成品商品
    	ComposeItemBean oneItem = itemList.get(0);
    	String newProductId = createSemiProduct(oneItem.getProductId());
    	
    	newBean.setProductId(newProductId);
    	
    	newBean.setStatus(ComposeConstant.STATUS_CRO_PASS);
    	newBean.setParentId(bean.getId());
    	newBean.setMtype(oneItem.getMtype());
    	
    	double total = 0.0d;
    	
    	if (mtype == PublicConstant.MANAGER_TYPE_COMMON)
    	{
    		List<ComposeFeeBean> feeList = bean.getFeeList();
        	
        	for (ComposeFeeBean each : feeList)
        	{
        		ComposeFeeBean fee = new ComposeFeeBean();
        		
        		BeanUtil.copyProperties(fee, each);
        		
        		fee.setParentId(newBean.getId());
        		
        		newFeeList.add(fee);
        		
        		total += each.getPrice();
        	}
    	}
    	
    	if (mtype == PublicConstant.MANAGER_TYPE_COMMON)
		{
			newBean.setTag("HCP1");
		}else{
			newBean.setTag("HCG1");
		}
    	
    	for (ComposeItemBean each : itemList)
    	{
    		ComposeItemBean item = new ComposeItemBean();
    		
    		BeanUtil.copyProperties(item, each);
    		
    		item.setParentId(newBean.getId());
    		
    		newItemList.add(item);
    		
    		total += each.getPrice() * each.getAmount();
    	}
    	
    	 // 计算新产品的成本价
        double price = total / newBean.getAmount();

        newBean.setPrice(price);
        
        composeProductDAO.saveEntityBean(newBean);
        
        composeItemDAO.saveAllEntityBeans(newItemList);
        
        if (!ListTools.isEmptyOrNull(newFeeList))
        	composeFeeDAO.saveAllEntityBeans(newFeeList);
        
    	return newBean;
    }
    
    /**
     * 
     * @param productId
     * @return
     */
    private String createSemiProduct(String productId) throws MYException
    {
    	ProductBean product = productDAO.find(productId);
    	
    	if (null == product)
    	{
    		throw new MYException("源产品不存在");
    	}
    	
    	String newProductName = product.getName() + "-半成品";
    	
    	ProductBean newProduct = productDAO.findByName(newProductName);
    	
    	if (null == newProduct)
    	{
    		ProductBean newPB = new ProductBean();
    		
    		BeanUtil.copyProperties(newPB, product);
    		
    		newPB.setName(newProductName);

            String appName = ConfigLoader.getProperty("appName");

            String code = commonDAO.getSquenceString();
            if (AppConstant.APP_NAME_TW.equals(appName)){
                code = "TW"+code;
            } else if(AppConstant.APP_NAME_ZJGH.equals(appName)){
                code = AppConstant.PREFIX_GH+code;
            }
    		newPB.setCode(code);

            String id = commonDAO.getSquenceString();
            if (AppConstant.APP_NAME_TW.equals(appName)){
                id = "TW"+id;
            } else if(AppConstant.APP_NAME_ZJGH.equals(appName)){
                id = AppConstant.PREFIX_GH+id;
            }
    		newPB.setId(id);

    		newPB.setFullspell("");
    		newPB.setShortspell("");
    		
    		productDAO.saveEntityBean(newPB);
    		
    		return newPB.getId();
    	}
    	
    	return newProduct.getId();
    }
    
    /**
     * 将两个半成品生成新的合成品
     * @param bean
     * @param list 
     */
    private void createComposeProduct(ComposeProductBean bean, List<ComposeProductBean> list) throws MYException
    {
    	if (list.size() != 2)
    	{
    		throw new MYException("数据错误");
    	}
    	
    	ComposeProductBean newBean = new ComposeProductBean();
    	
    	List<ComposeFeeBean> newFeeList = new ArrayList<ComposeFeeBean>();
    	
    	List<ComposeItemBean> newItemList = new ArrayList<ComposeItemBean>();
    	
    	BeanUtil.copyProperties(newBean, bean);
    	
    	newBean.setFeeList(newFeeList);
    	newBean.setItemList(newItemList);
    	
    	newBean.setId(commonDAO.getSquenceString20());
    	
    	newBean.setStatus(ComposeConstant.STATUS_CRO_PASS);
    	newBean.setParentId(bean.getId());
    	
    	List<ComposeFeeBean> feeList = bean.getFeeList();
    	
    	double total = 0.0d;
    	
    	for (ComposeFeeBean each : feeList)
    	{
    		ComposeFeeBean fee = new ComposeFeeBean();
    		
    		BeanUtil.copyProperties(fee, each);
    		
    		fee.setParentId(newBean.getId());
    		
    		newFeeList.add(fee);
    		
    		total += each.getPrice();
    	}
    	
    	for (ComposeProductBean each : list)
    	{
    		ComposeItemBean semiItem = each.getItemList().get(0);
    		
    		ComposeItemBean item = new ComposeItemBean();
    		
    		BeanUtil.copyProperties(item, semiItem);
    		
    		item.setParentId(newBean.getId());
    		
    		item.setProductId(each.getProductId());
    		item.setAmount(each.getAmount());
    		item.setPrice(each.getPrice());
    		
    		newItemList.add(item);
    		
    		total += each.getPrice() * each.getAmount();
    	}
    	
    	 // 计算新产品的成本价
        double price = total / newBean.getAmount();

        newBean.setPrice(price);
        
        newBean.setTag("HCH1");
        
        composeProductDAO.saveEntityBean(newBean);
        
        composeItemDAO.saveAllEntityBeans(newItemList);
        
        composeFeeDAO.saveAllEntityBeans(newFeeList);
        
        list.add(newBean);
    }
    
    private boolean checkIfHybrid(ComposeProductBean bean)
    {
    	boolean hybrid = false;
    	
    	List<ComposeItemBean> itemList = bean.getItemList();
    	
    	int count = 0;
    	
    	for (ComposeItemBean each : itemList)
    	{
    		count += each.getMtype();
    	}
    	
    	if (count >0 && count < itemList.size())
    	{
    		hybrid = true;
    	}
    	
    	return hybrid;
    }
    

    @Transactional(rollbackFor = MYException.class)
    public boolean passComposeProduct(User user, String id)
        throws MYException
    {
        //#246 2016/5/30 合成审批流程调整
//        JudgeTools.judgeParameterIsNull(user, id);
//
//        composeProductDAO.updateStatus(id, ComposeConstant.STATUS_MANAGER_PASS);
//
//        return true;

        JudgeTools.judgeParameterIsNull(user, id);

        ComposeProductBean bean = findBeanById(id);

        if (bean == null)
        {
            throw new MYException("合成单不存在");
        }

        bean.setStatus(ComposeConstant.STATUS_MANAGER_PASS);

        // 结束时间
        bean.setLogTime(TimeTools.now());

        if (bean.getMtype() == PublicConstant.MANAGER_TYPE_COMMON)
        {
            bean.setTag("HCP1");
        }else{
            bean.setTag("HCG1");
        }

        composeProductDAO.updateEntityBean(bean);

        // 修改库存(合成)
        if (bean.getType() == ComposeConstant.COMPOSE_TYPE_COMPOSE
                //2015/8/3 预合成类型
                || bean.getType() == ComposeConstant.COMPOSE_TYPE_PRE_COMPOSE)
        {
            processCompose(user, bean);
        }
        else
        {
            // 分解
            processDecompose(user, bean);
        }

        // 根据源产品管理/普通组成情况，如果是普通+管理，则需一拆为三
        boolean hybrid = false;

        hybrid = checkIfHybrid(bean);

        // TAX_ADD 产品合成/分解-运营总监通过
        Collection<ComposeProductListener> listenerMapValues = this.listenerMapValues();

        // 拆分
        if (hybrid)
        {
            // 原合成申请混合标记
            composeProductDAO.updateHybrid(id, 1);

            List<ComposeProductBean> list = splitComposeProduct(bean);

            for (ComposeProductListener composeProductListener : listenerMapValues)
            {
                for (ComposeProductBean each : list)
                {
                    composeProductListener.onConfirmCompose(user, each);
                }
            }

        }else{
            for (ComposeProductListener composeProductListener : listenerMapValues)
            {
                composeProductListener.onConfirmCompose(user, bean);
            }
        }

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean rejectComposeProduct(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        // 判断是否可以删除
        ComposeProductBean compose = composeProductDAO.find(id);

        if (compose == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (compose.getStatus() == ComposeConstant.STATUS_CRO_PASS)
        {
            throw new MYException("已经合成不能删除,请确认操作");
        }

        compose.setStatus(ComposeConstant.STATUS_REJECT);
        compose.setLogTime(TimeTools.now());

        composeProductDAO.updateEntityBean(compose);

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("");
        log.setFullId(id);
        log.setOprMode(ProductApplyConstant.OPRMODE_REJECT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(compose.getStatus());

        log.setAfterStatus(compose.getStatus());

        flowLogDAO.saveEntityBean(log);

        return true;
    }

    @Override
    @Transactional(rollbackFor = MYException.class)
    public boolean deleteComposeProduct(User user, String id) throws MYException {
        JudgeTools.judgeParameterIsNull(user, id);

        // 判断是否可以删除
        ComposeProductBean compose = composeProductDAO.find(id);

        if (compose == null)
        {
            throw new MYException("数据错误,请确认操作");
        }

        if (compose.getStatus() == ComposeConstant.STATUS_CRO_PASS)
        {
            throw new MYException("已经合成不能删除,请确认操作");
        }

        composeProductDAO.deleteEntityBean(id);

        composeItemDAO.deleteEntityBeansByFK(id);

        composeFeeDAO.deleteEntityBeansByFK(id);

        oprLogger.info(user.getStafferName() + "驳回删除了合成单:" + compose);

        return true;
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addComposeFeeDefinedBean(User user, ComposeFeeDefinedBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        bean.setId(commonDAO.getSquenceString());

        // TEMPLATE DAO校验表达式
        Expression exp = new Expression(bean, this);

        exp.check("#name &unique @composeFeeDefinedDAO", "名称已经存在");

        return composeFeeDefinedDAO.saveEntityBean(bean);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean deleteComposeFeeDefinedBean(User user, String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

        int count = composeFeeDAO.countByFK(id, AnoConstant.FK_FIRST);

        if (count > 0)
        {
            throw new MYException("费用项已经被使用,不能删除");
        }

        return composeFeeDefinedDAO.deleteEntityBean(id);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean updateComposeFeeDefinedBean(User user, ComposeFeeDefinedBean bean)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, bean);

        Expression exp = new Expression(bean, this);

        exp.check("#name &unique2 @composeFeeDefinedDAO", "名称已经存在");

        return composeFeeDefinedDAO.updateEntityBean(bean);
    }

    public ComposeFeeDefinedVO findComposeFeeDefinedVO(String id)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(id);

        ComposeFeeDefinedVO vo = composeFeeDefinedDAO.findVO(id);

        if (vo == null)
        {
            return vo;
        }

        if ( !StringTools.isNullOrNone(vo.getTaxId()))
        {
            Map<String, String> result = publishMessage.publicP2PMessage(
                MessageConstant.FINDCOMPOSEFEEDEFINEDVO, vo.getTaxId());

            if (result.size() > 0)
            {
                vo.setTaxName(result.get(MessageConstant.RESULT));
            }
        }

        return vo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.china.center.oa.product.manager.ComposeProductManager#findById(java.lang.String)
     */
    public ComposeProductVO findById(String id)
    {
        ComposeProductVO vo = composeProductDAO.findVO(id);

        if (vo == null)
        {
            return null;
        }

        vo.setItemVOList(composeItemDAO.queryEntityVOsByFK(id));

        vo.setFeeVOList(composeFeeDAO.queryEntityVOsByFK(id));

        return vo;
    }

    private ComposeProductBean findBeanById(String id)
    {
        ComposeProductBean bean = composeProductDAO.find(id);

        if (bean == null)
        {
            return null;
        }

        bean.setItemList(composeItemDAO.queryEntityBeansByFK(id));

        bean.setFeeList(composeFeeDAO.queryEntityBeansByFK(id));

        return bean;
    }

    /**
     * processCompose
     * 
     * @param user
     * @param bean
     * @throws MYException
     */
    private void processCompose(User user, ComposeProductBean bean)
        throws MYException
    {
        String sid = commonDAO.getSquenceString();
        
        List<ComposeItemBean> itemList = bean.getItemList();

        ProductChangeWrap wrap = new ProductChangeWrap();

        wrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
        wrap.setChange(bean.getAmount());
        //#525 在物流作业库 下合成的成品，成品合成后默认放置 物流作业库-组配仓
        if ("A1201606211663545335".equals(bean.getDeportId())){
            wrap.setDepotpartId("A1201605230938242216");
        } else{
            wrap.setDepotpartId(bean.getDepotpartId());
        }

        wrap.setDescription("合成产品异动(合成后增加):" + bean.getId());
        wrap.setPrice(bean.getPrice());
        wrap.setProductId(bean.getProductId());
        wrap.setType(StorageConstant.OPR_STORAGE_COMPOSE);
        wrap.setSerializeId(sid);
        wrap.setRefId(sid);

        //#545
        double virtualPrice = this.getVirtualPrice(bean);
        wrap.setVirtualPrice(virtualPrice);
        
        // 根据子产品的税率计算合成产品的税率
        // inputtax = ((a.tax/17%)*a.cost + (b.tax/17%)*b.cost+(c.tax/17%)*c.cost + …)/(a+b+c+…)
        double total = 0.0d;
        double totalTax = 0.0d;
        
        for (ComposeItemBean eachItem : itemList)
        {
        	totalTax +=  (eachItem.getInputRate()/0.17) * eachItem.getPrice();
        	total += eachItem.getPrice();
        }
        
        wrap.setInputRate(totalTax/total);

        storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);

        double sailPrice = bean.getPrice() - virtualPrice;
        _logger.info(virtualPrice+"***sailPrice***"+sailPrice);
        String productId = bean.getProductId();
        ProductBean productBean = this.productDAO.find(productId);
        _logger.info("***productBean***"+productBean);
        if (productBean!= null && productBean.getSailPriceFlag() ==  1
                && !NumberUtils.equals(productBean.getSailPrice(),sailPrice,0.001)){
            _logger.info("*****1111");
            List<PriceConfigBean> list1 = priceConfigDAO.querySailPricebyProductId(productId);
            if (ListTools.isEmptyOrNull(list1)){
                // 日志
                StringBuilder sb = new StringBuilder();
                sb.append("取自合成单:"+bean.getId()+".修改人:"+user.getName())
                        .append(".原产品结算价:").append(productBean.getSailPrice())
                        .append(".更新为:").append(sailPrice);
                this.log(user,productId, OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);
                _logger.info("*****2222");
                //如果没有config配置项，直接更新product的sailPrice
                productBean.setSailPrice(sailPrice);
                productDAO.updateEntityBean(productBean);
            } else {
                _logger.info("*****3333");
                PriceConfigBean first = list1.get(0);
                //有配置项，先更新配置项的辅料字段，然后通过公式算出结算价，然后更新回product的sailPrice
                this.priceConfigDAO.updateGsPriceUp(productId, sailPrice);
                //log
                StringBuilder sb2 = new StringBuilder();
                sb2.append("取自合成单:"+bean.getId()+".修改人:"+user.getName())
                        .append(".原产品辅料价:").append(first.getGsPriceUp())
                        .append(".更新为:").append(sailPrice);
                this.log(user, bean.getProductId(), OperationConstant.OPERATION_UPDATE, sb2.toString(), ModuleConstant.MODULE_PRICE_CONFIG2);
                first.setGsPriceUp(sailPrice);
                PriceConfigBean cb = this.priceConfigManager.calcSailPrice(first);
                if (cb!= null){
                    _logger.info("*****4444");
                    // 日志
                    StringBuilder sb = new StringBuilder();
                    sb.append("取自合成单:"+bean.getId()+".修改人:"+user.getName())
                            .append(".原产品结算价:").append(productBean.getSailPrice())
                            .append(".更新为:").append(cb.getSailPrice());
                    this.log(user, bean.getProductId(), OperationConstant.OPERATION_UPDATE, sb.toString(), ModuleConstant.MODULE_PRICE_CONFIG);

                    productBean.setSailPrice(cb.getSailPrice());
                    productDAO.updateEntityBean(productBean);
                }
            }
        }

        for (ComposeItemBean composeItemBean : itemList)
        {
            ProductChangeWrap eachWrap = new ProductChangeWrap();

            eachWrap.setStorageId(composeItemBean.getStorageId());
            eachWrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
            eachWrap.setChange( -composeItemBean.getAmount());
            eachWrap.setDepotpartId(composeItemBean.getDepotpartId());
            eachWrap.setDescription("合成产品移动(合成项减少):" + bean.getId());
            eachWrap.setPrice(composeItemBean.getPrice());
            eachWrap.setVirtualPrice(composeItemBean.getVirtualPrice());
            eachWrap.setProductId(composeItemBean.getProductId());
            eachWrap.setType(StorageConstant.OPR_STORAGE_COMPOSE);
            eachWrap.setRelationId(eachWrap.getRelationId());
            eachWrap.setSerializeId(sid);
            eachWrap.setRefId(sid);
            eachWrap.setInputRate(composeItemBean.getInputRate());

            storageRelationManager.changeStorageRelationWithoutTransaction(user, eachWrap, true);
        }
    }

    private double getVirtualPrice(ComposeProductBean bean){
        if (this.isVirtualProduct(bean.getProductId())){
            return bean.getPrice();
        } else{
            double virtualPrice = 0.0;
            List<ComposeItemBean> itemList = bean.getItemList();
            for (ComposeItemBean itemBean: itemList){
//            if (this.isVirtualProduct(itemBean.getProductId())){
//                virtualPrice += ((double)itemBean.getAmount()/bean.getAmount())*itemBean.getPrice();
//            }
                virtualPrice += ((double)itemBean.getAmount()/bean.getAmount())*itemBean.getVirtualPrice();
            }
            return virtualPrice;
        }
    }

    private boolean isVirtualProduct(String productId){
        ProductBean productBean = this.productDAO.find(productId);
        if (productBean!= null && productBean.getVirtualFlag() == 1){
            return true;
        }

        return false;
    }

    private void log(User user, String id,String operation, String reason, String module) {
        // 记录审批日志
        LogBean log = new LogBean();

        log.setFkId(id);
        if (user == null){
            log.setLocationId("系统");
            log.setStafferId("系统");
        } else{
            log.setLocationId(user.getLocationId());
            log.setStafferId(user.getStafferId());
        }

        log.setLogTime(TimeTools.now());
//        log.setModule(ModuleConstant.MODULE_PRICE_CONFIG);
        log.setModule(module);
        log.setOperation(operation);
        log.setLog(reason);

        logDAO.saveEntityBean(log);
    }

    private void processDecompose(User user, ComposeProductBean bean)
        throws MYException
    {
        String sid = commonDAO.getSquenceString();

        ProductChangeWrap wrap = new ProductChangeWrap();

        wrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
        wrap.setChange( -bean.getAmount());
        wrap.setDepotpartId(bean.getDepotpartId());
        wrap.setDescription("分解产品异动(分解后减少):" + bean.getId());
        wrap.setPrice(bean.getPrice());
        wrap.setProductId(bean.getProductId());
        wrap.setType(StorageConstant.OPR_STORAGE_COMPOSE);
        wrap.setSerializeId(sid);
        wrap.setRefId(sid);
        wrap.setInputRate(0.0d);

        storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);

        List<ComposeItemBean> itemList = bean.getItemList();

        for (ComposeItemBean composeItemBean : itemList)
        {
            ProductChangeWrap eachWrap = new ProductChangeWrap();

            eachWrap.setStorageId(composeItemBean.getStorageId());
            eachWrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
            eachWrap.setChange(composeItemBean.getAmount());
            eachWrap.setDepotpartId(composeItemBean.getDepotpartId());
            eachWrap.setDescription("分解产品(合成项增加):" + bean.getId());
            eachWrap.setPrice(composeItemBean.getPrice());
            eachWrap.setProductId(composeItemBean.getProductId());
            eachWrap.setType(StorageConstant.OPR_STORAGE_COMPOSE);
            eachWrap.setRelationId(eachWrap.getRelationId());
            eachWrap.setSerializeId(sid);
            eachWrap.setRefId(sid);
            eachWrap.setInputRate(composeItemBean.getInputRate());

            storageRelationManager.changeStorageRelationWithoutTransaction(user, eachWrap, true);
        }
    }

    private void saveInner(ComposeProductBean bean, boolean preCompose)
    {
        bean.setId(commonDAO.getSquenceString20());

        if (preCompose){
            bean.setStatus(ComposeConstant.STATUS_PRE_COMPOSE);
//            bean.setStatus(-1);
        } else{
            //#431
            if (bean.getStatus() == ComposeConstant.STATUS_SAVE){
                bean.setStatus(ComposeConstant.STATUS_SAVE);
            } else{
                bean.setStatus(ComposeConstant.STATUS_SUBMIT);
            }
        }

        composeProductDAO.saveEntityBean(bean);

        List<ComposeItemBean> itemList = bean.getItemList();

        if ( !ListTools.isEmptyOrNull(itemList))
        {
            for (ComposeItemBean composeItemBean : itemList)
            {
                composeItemBean.setParentId(bean.getId());
                //composeItemBean.setMtype(bean.getMtype());
            }
        }

        composeItemDAO.saveAllEntityBeans(itemList);

        List<ComposeFeeBean> feeList = bean.getFeeList();

        if ( !ListTools.isEmptyOrNull(feeList))
        {
            for (ComposeFeeBean composeFeeBean : feeList)
            {
                composeFeeBean.setParentId(bean.getId());
                composeFeeBean.setMtype(bean.getMtype());
            }
        }

        composeFeeDAO.saveAllEntityBeans(feeList);
    }

    @Transactional(rollbackFor = MYException.class)
    public boolean addDecomposeProduct(User user, DecomposeProductBean bean)
        throws MYException
    {
        _logger.info("***addDecomposeProduct***"+bean);
        JudgeTools.judgeParameterIsNull(user, bean);

        // 检查合成逻辑
        String productId = bean.getProductId();

        ProductBean compose = productDAO.find(productId);

        if (compose == null)
        {
            throw new MYException("产品不存在："+productId);
        }

        // #433 产品分拆配件不能拆分的控制去掉
//        if (compose.getCtype() != ProductConstant.CTYPE_YES)
//        {
//            throw new MYException("目的产品不是合成产品,请确认操作");
//        }

        // MANAGER 合成产品增加的管理逻辑
        if (OATools.isCommon(compose.getReserve4()))
        {
            // 必须都是普通产品才能合成
            List<ComposeItemBean> itemList = bean.getItemList();

            for (ComposeItemBean composeItemBean : itemList)
            {
                ProductBean each = productDAO.find(composeItemBean.getProductId());

                if (each == null)
                {
                    throw new MYException("数据错误,请确认操作");
                }

                // 会有什么影响？？ 130725
/*                if (OATools.getManagerType(each.getReserve4()) != PublicConstant.MANAGER_TYPE_COMMON)
                {
                    throw new MYException("合成的源产品的管理类型必须都是普通,错误产品:%s", each.getName());
                }*/
            }

            bean.setMtype(PublicConstant.MANAGER_TYPE_COMMON);
        }
        else
        {
            bean.setMtype(PublicConstant.MANAGER_TYPE_MANAGER);
        }

        bean.setId(commonDAO.getSquenceString20());

        bean.setStatus(ComposeConstant.STATUS_INDUSTRY_PASS);
        _logger.info("****addDecomposeProduct***"+bean);
        decomposeProductDAO.saveEntityBean(bean);

        List<ComposeItemBean> itemList = bean.getItemList();

        if ( !ListTools.isEmptyOrNull(itemList))
        {
            for (ComposeItemBean composeItemBean : itemList)
            {
                composeItemBean.setParentId(bean.getId());
                composeItemBean.setMtype(bean.getMtype());
            }
        }

        System.out.println(bean.getId()+"****************itemList size***********"+itemList.size());
        composeItemDAO.saveAllEntityBeans(itemList);
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription("提交");
        log.setFullId(bean.getId());
        log.setOprMode(ProductApplyConstant.OPRMODE_SUBMIT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(bean.getStatus());

        log.setAfterStatus(ComposeConstant.STATUS_INDUSTRY_PASS);

        flowLogDAO.saveEntityBean(log);
        
        return true;
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean passDecomposeProduct(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

         DecomposeProductBean bean = decomposeProductDAO.find(id);

         if (bean == null)
         {
             throw new MYException("单据不存在");
         }
         
        bean.setItemList(composeItemDAO.queryEntityBeansByFK(id));

        if (bean.getStatus() != ComposeConstant.STATUS_INDUSTRY_PASS)
        {
            throw new MYException("单据状态不正确，请确认");
        }
        
        bean.setStatus(ComposeConstant.STATUS_OK);
        bean.setLogTime(TimeTools.now());
        
        decomposeProductDAO.updateEntityBean(bean);

        // 修改库存
        processDecompose(user, bean);

        // TAX_ADD 产品合成/分解-运营总监通过
        Collection<ComposeProductListener> listenerMapValues = this.listenerMapValues();

        for (ComposeProductListener composeProductListener : listenerMapValues)
        {
            composeProductListener.onConfirmDecompose(user, bean);
        }
        
        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(reason);
        log.setFullId(id);
        log.setOprMode(ProductApplyConstant.OPRMODE_PASS);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(bean.getStatus());

        log.setAfterStatus(ComposeConstant.STATUS_OK);

        flowLogDAO.saveEntityBean(log);

        return true;
    }
    
    @Transactional(rollbackFor = MYException.class)
    public boolean rejectDecomposeProduct(User user, String id, String reason)
        throws MYException
    {
        JudgeTools.judgeParameterIsNull(user, id);

         DecomposeProductBean bean = decomposeProductDAO.find(id);

         if (bean == null)
         {
             throw new MYException("单据不存在");
         }
         
        if (bean.getStatus() != ComposeConstant.STATUS_INDUSTRY_PASS)
        {
            throw new MYException("单据状态不正确，请确认");
        }
        
        bean.setStatus(ComposeConstant.STATUS_REJECT);
        bean.setLogTime(TimeTools.now());
        
        decomposeProductDAO.updateEntityBean(bean);

        FlowLogBean log = new FlowLogBean();

        log.setActor(user.getStafferName());

        log.setDescription(reason);
        log.setFullId(id);
        log.setOprMode(ProductApplyConstant.OPRMODE_REJECT);
        log.setLogTime(TimeTools.now());

        log.setPreStatus(bean.getStatus());

        log.setAfterStatus(bean.getStatus());

        flowLogDAO.saveEntityBean(log);

        return true;
    }
    
    /**
     * 单独拆分产品
     */
    private void processDecompose(User user, DecomposeProductBean bean)
    throws MYException
	{
	    String sid = commonDAO.getSquenceString();
	
	    ProductChangeWrap wrap = new ProductChangeWrap();
	
	    wrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
	    wrap.setChange( -bean.getAmount());
	    wrap.setDepotpartId(bean.getDepotpartId());
	    wrap.setDescription("拆分产品异动(拆分后成品减少):" + bean.getId());
	    wrap.setPrice(bean.getPrice());
	    wrap.setProductId(bean.getProductId());
	    wrap.setType(StorageConstant.OPR_STORAGE_DECOMPOSE);
	    wrap.setSerializeId(sid);
	    wrap.setRefId(sid);
	    wrap.setInputRate(0.0d);
	
	    storageRelationManager.changeStorageRelationWithoutTransaction(user, wrap, false);
	
	    List<ComposeItemBean> itemList = bean.getItemList();
	
	    for (ComposeItemBean composeItemBean : itemList)
	    {
	    	if (composeItemBean.getStype() == ProductConstant.DECOMPOSE_PRODUCT_FEE)
	    		continue;
	    	
	        ProductChangeWrap eachWrap = new ProductChangeWrap();
	
	        eachWrap.setStorageId(composeItemBean.getStorageId());
	        eachWrap.setStafferId(StorageConstant.PUBLIC_STAFFER);
	        eachWrap.setChange(composeItemBean.getAmount());
	        eachWrap.setDepotpartId(composeItemBean.getDepotpartId());
	        eachWrap.setDescription("拆分产品(配件产品增加):" + bean.getId());
	        eachWrap.setPrice(composeItemBean.getPrice());
	        eachWrap.setProductId(composeItemBean.getProductId());
	        eachWrap.setType(StorageConstant.OPR_STORAGE_DECOMPOSE);
	        eachWrap.setSerializeId(sid);
	        eachWrap.setRefId(sid);
	        eachWrap.setInputRate(composeItemBean.getInputRate());
	
	        storageRelationManager.changeStorageRelationWithoutTransaction(user, eachWrap, true);
	    }
	}
    
    /**
     * @return the composeProductDAO
     */
    public ComposeProductDAO getComposeProductDAO()
    {
        return composeProductDAO;
    }

    /**
     * @param composeProductDAO
     *            the composeProductDAO to set
     */
    public void setComposeProductDAO(ComposeProductDAO composeProductDAO)
    {
        this.composeProductDAO = composeProductDAO;
    }

    /**
     * @return the composeItemDAO
     */
    public ComposeItemDAO getComposeItemDAO()
    {
        return composeItemDAO;
    }

    /**
     * @param composeItemDAO
     *            the composeItemDAO to set
     */
    public void setComposeItemDAO(ComposeItemDAO composeItemDAO)
    {
        this.composeItemDAO = composeItemDAO;
    }

    /**
     * @return the composeFeeDAO
     */
    public ComposeFeeDAO getComposeFeeDAO()
    {
        return composeFeeDAO;
    }

    /**
     * @param composeFeeDAO
     *            the composeFeeDAO to set
     */
    public void setComposeFeeDAO(ComposeFeeDAO composeFeeDAO)
    {
        this.composeFeeDAO = composeFeeDAO;
    }

    /**
     * @return the commonDAO
     */
    public CommonDAO getCommonDAO()
    {
        return commonDAO;
    }

    /**
     * @param commonDAO
     *            the commonDAO to set
     */
    public void setCommonDAO(CommonDAO commonDAO)
    {
        this.commonDAO = commonDAO;
    }

    /**
     * @return the storageRelationManager
     */
    public StorageRelationManager getStorageRelationManager()
    {
        return storageRelationManager;
    }

    /**
     * @param storageRelationManager
     *            the storageRelationManager to set
     */
    public void setStorageRelationManager(StorageRelationManager storageRelationManager)
    {
        this.storageRelationManager = storageRelationManager;
    }

    /**
     * @return the composeFeeDefinedDAO
     */
    public ComposeFeeDefinedDAO getComposeFeeDefinedDAO()
    {
        return composeFeeDefinedDAO;
    }

    /**
     * @param composeFeeDefinedDAO
     *            the composeFeeDefinedDAO to set
     */
    public void setComposeFeeDefinedDAO(ComposeFeeDefinedDAO composeFeeDefinedDAO)
    {
        this.composeFeeDefinedDAO = composeFeeDefinedDAO;
    }

    /**
     * @return the publishMessage
     */
    public PublishMessage getPublishMessage()
    {
        return publishMessage;
    }

    /**
     * @param publishMessage
     *            the publishMessage to set
     */
    public void setPublishMessage(PublishMessage publishMessage)
    {
        this.publishMessage = publishMessage;
    }

    /**
     * @return the productDAO
     */
    public ProductDAO getProductDAO()
    {
        return productDAO;
    }

    /**
     * @param productDAO
     *            the productDAO to set
     */
    public void setProductDAO(ProductDAO productDAO)
    {
        this.productDAO = productDAO;
    }

	/**
	 * @return the decomposeProductDAO
	 */
	public DecomposeProductDAO getDecomposeProductDAO()
	{
		return decomposeProductDAO;
	}

	/**
	 * @param decomposeProductDAO the decomposeProductDAO to set
	 */
	public void setDecomposeProductDAO(DecomposeProductDAO decomposeProductDAO)
	{
		this.decomposeProductDAO = decomposeProductDAO;
	}

	/**
	 * @return the flowLogDAO
	 */
	public FlowLogDAO getFlowLogDAO()
	{
		return flowLogDAO;
	}

	/**
	 * @param flowLogDAO the flowLogDAO to set
	 */
	public void setFlowLogDAO(FlowLogDAO flowLogDAO)
	{
		this.flowLogDAO = flowLogDAO;
	}

    public StorageRelationDAO getStorageRelationDAO() {
        return storageRelationDAO;
    }

    public void setStorageRelationDAO(StorageRelationDAO storageRelationDAO) {
        this.storageRelationDAO = storageRelationDAO;
    }

    public LogDAO getLogDAO() {
        return logDAO;
    }

    public void setLogDAO(LogDAO logDAO) {
        this.logDAO = logDAO;
    }

    public PriceConfigDAO getPriceConfigDAO() {
        return priceConfigDAO;
    }

    public void setPriceConfigDAO(PriceConfigDAO priceConfigDAO) {
        this.priceConfigDAO = priceConfigDAO;
    }

    public PriceConfigManager getPriceConfigManager() {
        return priceConfigManager;
    }

    public void setPriceConfigManager(PriceConfigManager priceConfigManager) {
        this.priceConfigManager = priceConfigManager;
    }
}
