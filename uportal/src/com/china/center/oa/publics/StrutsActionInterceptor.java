package com.china.center.oa.publics;

/**
 * Created by Simon on 2016/10/18.
 */
import com.china.center.actionhelper.common.KeyConstant;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionMapping;
import javax.servlet.http.HttpServletRequest;

public class StrutsActionInterceptor implements MethodInterceptor {
    private final Log _logger = LogFactory.getLog(getClass());


    public Object invoke(MethodInvocation invocation) throws Throwable {
        _logger.info("*******before: invocation=[" + invocation + "]");
        Object target = invocation.getThis();
        if (target instanceof  Action){
            try {
                Object rval = invocation.proceed();
                _logger.info(invocation.getThis() + "*****after Invocation returned***" + invocation.getClass());
                return rval;
            }catch (Exception e){
                _logger.error(e, e);
                Object[] parameters = invocation.getArguments();
                HttpServletRequest request = (HttpServletRequest)parameters[2];
                ActionMapping mapping = (ActionMapping)parameters[0];
                request.setAttribute(KeyConstant.ERROR_MESSAGE, "后台异常,请联系管理员:" + e.toString());

                return mapping.findForward("error");
            }
        } else{
            return invocation.proceed();
        }
    }
}