package com.china.center.oa.sail.helper;

import com.china.center.oa.publics.StringUtils;
import com.china.center.oa.sail.constanst.OutImportConstant;
import com.china.center.oa.sail.manager.OutImportManager;

import java.util.ArrayList;
import java.util.List;

public class OutImportHelper
{
	public static int getStorageType(String name)
	{
		if (name.equals("订货代销"))
		{
			return 1;
		}
		else if (name.equals("铺货库存"))
		{
			return 2;
		}
		else if (name.equals("在途库存"))
		{
			return 3;
		}
		else
		{
			return -1;
		}
			
	}
	
	public static int getInvoiceNature(String name)
	{
		if (OutImportConstant.SINGLE_INVOICEINS.equals(name))
		{
			return 1;
		}
		else if (OutImportConstant.ONE_INVOICEINS.equals(name))
		{
			return 2;
		}
		else
		{
			return -1;
		}
	}

	public static String getInvoiceNature(){
		List<String> list = new ArrayList<String>();
		list.add(OutImportConstant.SINGLE_INVOICEINS);
		list.add(OutImportConstant.ONE_INVOICEINS);
		return StringUtils.listToString(list,",");
	}
	
	public static int getInvoiceType(String name)
	{
		if (name.equals("增值可抵扣票"))
		{
			return 1;
		}
		else if (name.equals("增值普票"))
		{
			return 2;
		}
		else if (name.equals("普票"))
		{
			return 3;
		}
		else if (name.equals("旧货票"))
		{
			return 4;
		}
		else if (name.equals("零税率票"))
		{
			return 5;
		}
		else
		{
			return -1;
		}
	}
}
