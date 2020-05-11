/**
 * 
 */
package com.china.center.oa.sail.manager;


import java.util.List;


import com.china.center.common.MYException;

import com.china.center.oa.sail.wrap.Sample2OrderWrap;

import com.center.china.osgi.publics.User;


/**
 * 
 * @author GLQ
 *
 */
public interface OutSample2OrderManager
{
    
	boolean batchHandle(List<Sample2OrderWrap> list, User user) throws MYException;
	
}
