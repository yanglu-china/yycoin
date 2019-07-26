package com.china.center.oa.payorder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.china.center.actionhelper.common.ActionTools;
import com.china.center.actionhelper.common.JSONTools;
import com.china.center.jdbc.util.ConditionParse;
import com.china.center.oa.finance.dao.NbBankHisDataDAO;

public class NbBankQueryHisDataAction extends DispatchAction {
	
	private final String QUERYNBBANKHISDATA = "queryNbBankHisData";
	
	private NbBankHisDataDAO nbBankHisDataDao;
	
	public ActionForward queryHisData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		
		ConditionParse condtion = new ConditionParse();

		condtion.addWhereStr();

		ActionTools.processJSONQueryCondition(QUERYNBBANKHISDATA, request, condtion);

		String jsonstr = ActionTools.queryVOByJSONAndToString(QUERYNBBANKHISDATA,
				request, condtion, this.nbBankHisDataDao);

		return JSONTools.writeResponse(response, jsonstr);
		
	}

	public NbBankHisDataDAO getNbBankHisDataDao() {
		return nbBankHisDataDao;
	}

	public void setNbBankHisDataDao(NbBankHisDataDAO nbBankHisDataDao) {
		this.nbBankHisDataDao = nbBankHisDataDao;
	}

}
