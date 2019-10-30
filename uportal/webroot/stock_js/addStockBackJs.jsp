<%@ page contentType="text/html;charset=UTF-8" language="java"%>

//当前的焦点对象
var oo;

var ids = '';
var amous = '';
var tsts;
var messk = '';
var locationId = '${currentLocationId}';
var currentLocationId = '${currentLocationId}';

var showJSON = JSON.parse('${showJSON}');

function total()
{
    var obj = document.getElementsByName("value");

    var total = 0;
    for (var i = 1; i < obj.length; i++)
    {
        if (obj[i].value != '')
        {
            total = add(total, parseFloat(obj[i].value));
        }
    }

    var ss =  document.getElementById("total");
    tsts = formatNum(total, 2);
    ss.innerHTML = '(总计:' + tsts + ')';
}

function titleChange()
{
    if ($O('outType'))
    {
        removeOption($O('outType'), 0);
        removeOption($O('outType'), 4);
        removeOption($O('outType'), 5);
    }
}




function load()
{
    titleChange();
    
    loadForm();
    
    managerChange();
    
    forceBuyTypeChange();
}

function check()
{

    if (!formCheck())
    {
        return false;
    }
    

    ids = '';
    amous = '';
    
    $O('priceList').value = '';
    $O('totalList').value = '';
    $O('nameList').value = '';
    $O('unitList').value = '';
    
    $O('otherList').value = '';
    $O('showIdList').value = '';
    $O('showNameList').value = '';
    $O('desList').value = '';
    $O('virtualPriceList').value = '';
    

    if ($$('outType') == '')
    {
        alert('请选择库单类型');
        return false;
    }

    var proNames = document.getElementsByName('productName');
    var units = document.getElementsByName('unit');
    var amounts = document.getElementsByName('amount');
    var prices = document.getElementsByName('price');
    var values = document.getElementsByName('value');
    var outProductNames = document.getElementsByName('outProductName');
    
    var productIds = document.getElementsByName('productId');
    
    var depots = document.getElementsByName('depot');
    var depotparts = document.getElementsByName('depotpart');
    var depotpartIds = document.getElementsByName('depotpartId');
    
    var providerIds = document.getElementsByName('providerId');
    var dutyIds = document.getElementsByName('dutyId');
    var invoiceIds = document.getElementsByName('invoiceId');
    
    var backTypes = document.getElementsByName('backType');
    
    //检查发货信息
    var needSendFlag = 0; //是否存在已入库退货
    for (var i = 1; i < backTypes.length; i++)
    {
      if(backTypes[i].value == '1' && depots[i].value !='生产作业库'){
        needSendFlag = 1;
      }
    }    
    
    if(needSendFlag == 1){
      var receiver = document.getElementById("receiver").value;
      var address = document.getElementById("address").value;
      var mobile = document.getElementById("mobile").value;
      if(receiver == '' || address == '' || mobile == ''){
        alert("已入库退货，必须填写发货信息！");
        return false;
      }
    }

    var tmpMap = {};
    //isNumbers
    for (var i = 1; i < proNames.length; i++)
    {
    
        if (proNames[i].value== '')
        {
            alert('数据不完整,请选择产品名称!');
            
            return false;
        }
        
        if (providerIds[i].value== '')
        {
            alert('数据不完整,请选择供应商!');
            
            return false;
        }
        
        if (dutyIds[i].value== '')
        {
            alert('数据不完整,请选择纳税实体!');
            
            return false;
        }
        
        if (invoiceIds[i].value== '')
        {
            alert('数据不完整,请选择发票类型!');
            
            return false;
        }                

        ids = ids + productIds[i].value + '~';

        $O('nameList').value = $O('nameList').value +  proNames[i].value + '~';
        
        //ele.productid + '-' + ele.price + '-' + ele.stafferid + '-' + ele.depotpartid;
        var product = {};
        product.productid = productIds[i].value;
        product.price = prices[i].value;
        product.stafferid = 0;
        product.depotpartid = depotpartIds[i].value;
        
        var ikey = toUnqueStr2(product);
        
        //alert(ikey);
        
        if (tmpMap[ikey])
        {
            alert('选择的产品重复[仓区+产品+职员+价格],请核实!');
            
            return false;
        }
        
        tmpMap[ikey] = ikey;
        
        //库存重要的标识
        $O('otherList').value = $O('otherList').value + ikey + '~';

        $O('idsList').value = ids;
        
        //检查产品 供应商和价格是否匹配
	    if(backTypes[i].value == '2'){
	      var productProviderVSpriceMap = $O('productProviderVSpriceMap').value;
	      var keyValueMap = new Map();
	      var keyValueMapArr = productProviderVSpriceMap.split(";");
	      for (var k = 0; k < keyValueMapArr.length; k++)
	      {
	        var keyvalue = keyValueMapArr[k];
	        if(keyvalue!=""){
	          var arr = keyvalue.split(":");
	          keyValueMap.set(arr[0], arr[1]);
	        }
	        
	      }
	      
	      var productProviderKey = productIds[i].value+"_"+providerIds[i].value;
	      var f1 = parseFloat(keyValueMap.get(productProviderKey));
	      var f2 = parseFloat(prices[i].value);
	      if(f1 != f2){
	        alert("产品、供应商和价格不匹配！"+f1+" vs "+f2);
	        return false;
	      }
	    }
    }

    for (var i = 1; i < amounts.length; i++)
    {
        if (trim(amounts[i].value) == '')
        {
            alert('数据不完整,请填写产品数量!');
            amounts[i].focus();
            return false;
        }

        if (!isNumbers(amounts[i].value))
        {
            alert('数据错误,产品数量 只能是整数!');
            amounts[i].focus();
            return false;
        }
        
        if (parseInt(amounts[i].value) == 0)
        {
            alert('数据错误,产品数量 不能为0!');
            amounts[i].focus();
            return false;
        }

        amous = amous + amounts[i].value + '~';

        $O('amontList').value = amous;
    }
    
    for (var i = 1; i < outProductNames.length; i++)
    {
        if (trim(outProductNames[i].value) == '')
        {
            alert('数据不完整,请选择!');
            outProductNames[i].focus();
            return false;
        }

        amous = amous + amounts[i].value + '~';

        $O('showIdList').value =  $O('showIdList').value + outProductNames[i].value + '~';
        
        $O('showNameList').value =  $O('showNameList').value + getOptionText(outProductNames[i]) + '~';
    }

    for (var i = 1; i < prices.length; i++)
    {
        if (trim(prices[i].value) == '')
        {
            alert('数据不完整,请填写产品价格!');
            prices[i].focus();
            return false;
        }
        
        if (!isFloat(prices[i].value))
        {
            alert('数据错误,产品数量只能是浮点数!');
            prices[i].focus();
            return false;
        }

        $O('priceList').value = $O('priceList').value + prices[i].value + '~';
    }

    var desList = document.getElementsByName('desciprt');
    //#545 虚料金额
    var virtualPriceList = document.getElementsByName('virtualPrice');

    for (var i = 1; i < desList.length; i++)
    {
        if (trim(desList[i].value) == '')
        {
            alert('成本是必填!');
            desList[i].focus();
            return false;
        }
        
        if (!isFloat(desList[i].value))
        {
            alert('格式错误,成本只能是浮点数!');
            desList[i].focus();
            return false;
        }
        
        if ($$('outType') != 1 && parseFloat(trim(desList[i].value)) == 0)
        {
            alert('入库成本价格不能为0!');
            desList[i].focus();
            return false;
        }
    }
    
    for (var i = 1; i < values.length; i++)
    {
        $O('totalList').value = $O('totalList').value + values[i].value + '~';
        $O('desList').value = $O('desList').value + desList[i].value + '~';
        var virtualPriceEle = virtualPriceList[i];
        if (virtualPriceEle){
            $O('virtualPriceList').value = $O('virtualPriceList').value + virtualPriceEle.value + '~';
        } else{
            $O('virtualPriceList').value = $O('virtualPriceList').value + '0' + '~';
        }
    }

    for (var i = 1; i < units.length; i++)
    {
        $O('unitList').value = $O('unitList').value + units[i].value + '~';
    }

    $O('totalss').value = tsts;

    return true;
}

function checkTotal()
{
	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false;
	}

    messk = '';
    var gh =  document.getElementsByName('productName');
    var ghk =  document.getElementsByName('amount');
    var description =  document.getElementsByName('description');
    
    var amountLimit =  document.getElementsByName('amountLimit');
    

    messk += '\r\n';
    for(var i = 0 ; i < gh.length; i++)
    {
        if(gh[i].value == ''){
          continue;
        }
        if(description[i].value == ''){
            alert("退货备注为必填项!");
            return false;        
        }
        
        //check amount
        var real = parseInt(ghk[i].value);
        if(isNaN(real)){
            continue;
        } else if(real >= 0){
            alert("退货数量必须为负数!");
            return false;
        }
                
        var realAbs = Math.abs(parseInt(ghk[i].value));
        var limit = parseInt(amountLimit[i].value);
        if(realAbs > limit){
            alert("数量不能超出上限!");
            return false;
        }
        
        messk += '\r\n' + '产品【' + gh[i].value + '】   数量:' + ghk[i].value;
    }
    
    if ($O('saves').value == 'save')
    {
         if (window.confirm("退货数量为负数,确定提交库单?" + messk))
         {
            disableAllButton();
            outForm.submit();
         }

         return;
    }


//    ccv = $$('location');

//    if (ccv == '')
//    {
//        alert('产品仓库为空，请核实');
//        return false;
//    } 


    if (window.confirm("退货数量为负数,确定提交库单?" + messk))
    {
        disableAllButton();
        outForm.submit();
    }
}

function save()
{
    $O('saves').value = 'save';
    if (check())
    {
        checkTotal();
    }
}

function sub()
{
    $O('saves').value = 'submit';
    if (check())
    {
        checkTotal();
    }
}

var modifyPage = ('${bean.id}' != '');

var g_url_query = 0;

function managerChange()
{
        showTr('dir_tr', false);
        showTr('allocate', false);
        showTr('distribution1', false);
        showTr('distribution2', false);
        showTr('distribution3', false);
        showTr('distribution4', false);
        showTr('distribution5', false);
        showTr('forceBuy_tr', false);
        showTr('refOutFullId_tr', false);
        showTr('staffer_tr', false);
        showTr('customer_tr', false);

}

function showTr(id, show)
{
    $v(id, show);
    $d(id, !show);
}

var flag;
function selectCustomer()
{
	flag = 0;
    window.common.modal('../finance/finance.do?method=rptQueryUnit&load=1');
}

function getUnit(oo)
{
	if (flag == 0)
	{
	    $O('customerId').value = oo.value;
    	$O('customerName').value = oo.pname;
	}else
	{
		$O('customerId').value = oo.value;
    	$O('customerName1').value = oo.pname;
	}

}

function selectCustomer1()
{
	flag = 1;
    window.common.modal('../finance/finance.do?method=rptQueryUnit&load=1');
}

function selectStaffer()
{
    window.common.modal('../admin/pop.do?method=rptQueryStaffer&load=1&selectMode=1');
}

function getStaffers(oos)
{
    var oo = oos[0];
    
    $O('stafferName1').value = oo.pname;
    // reserve9 存放业务员
    $O('reserve9').value = oo.value;
}

function forceBuyTypeChange()
{
	
	// 其它入库
	if ($$('outType') == 99)
	{
	 if ($$('forceBuyType') == 0 || $$('forceBuyType') == 1 || $$('forceBuyType') == 3 )
	    {		
	    	showTr('table_tr', false);
	        showTr('button_tr', false);
	    
	        showTr('refOutFullId_tr', true);
	        showTr('staffer_tr', false);
	        showTr('customer_tr', false);
	        $O('customerId').value = '';
	        $O('customerName').value = '';
	        $O('stafferName').value = '';	        
	        $O('reserve9').value = '';	        

	    }

	 if ($$('forceBuyType') == 4 || $$('forceBuyType') == 5 || $$('forceBuyType') == 7 )
	    {
	       	showTr('table_tr', true);
	        showTr('button_tr', true);
	        
	        showTr('refOutFullId_tr', false);
	        showTr('staffer_tr', false);
	        showTr('customer_tr', false);
	        $O('customerId').value = '';
	        $O('customerName').value = '';
	        $O('stafferName').value = '';	        
	        $O('reserve9').value = '';
	        $O('refOutFullId').value = '';
	        
	    }

	 if ($$('forceBuyType') == 2 )
	    {	    
	       	showTr('table_tr', true);
	        showTr('button_tr', true);
	        
	        showTr('refOutFullId_tr', false);
	        showTr('staffer_tr', true);
	        showTr('customer_tr', true);
	        $O('refOutFullId').value = '';
	        
	    }  

	 if ($$('forceBuyType') == 6 )
	    {
	       	showTr('table_tr', true);
	        showTr('button_tr', true);	  
	        
	        showTr('refOutFullId_tr', false);
	        showTr('staffer_tr', true);
	        showTr('customer_tr', false);
	        $O('refOutFullId').value = '';
	        $O('customerId').value = '';
	        $O('customerName').value = '';
	              
	    }
	    
	 }
	 
	 // 报废
	 if ($$('outType')== 2)
	 {
	 	//
	 }   
}

function titleChange2()
{
	if ($O('fullId'))
	{
	 // do noting
	}else
	{	
		removeAllItem($O('forceBuyType'));
		
	    if ($$('outType') == 99)
	    {
	    	setOption($O('forceBuyType'), 0, '20110401前销售单退货');
	        setOption($O('forceBuyType'), 1, '纳税实体错误');
	        setOption($O('forceBuyType'), 2, '原单找不到');
	        setOption($O('forceBuyType'), 3, '转客户或业务员');
	        setOption($O('forceBuyType'), 4, '调品名');
	        setOption($O('forceBuyType'), 5, '不良品拆解');
	        setOption($O('forceBuyType'), 6, '私库转公库');
	        setOption($O('forceBuyType'), 7, '调整库存属性');
	    }
	    
	    if ($$('outType') == 2)
	    {
	        setOption($O('forceBuyType'), 50, '物流报废');
	        setOption($O('forceBuyType'), 51, '事业部报废' );
	        setOption($O('forceBuyType'), 52, '与事业部共同报废');
	        setOption($O('forceBuyType'), 53, '总部报废');
	        setOption($O('forceBuyType'), 54, '业务员报废');
	        setOption($O('forceBuyType'), 55, '配件报废');
	    }
    }
}

function checkCurrentUser()
{	
     // check
     var elogin = "${g_elogin}";

 	 //  商务登陆
     //if (elogin == '1')
     //{
          var top = window.top.topFrame.document;
          //var allDef = window.top.topFrame.allDef;
          var srcStafferId = top.getElementById('srcStafferId').value;
         
          var currentStafferId = "${g_stafferBean.id}";

          var currentStafferName = "${g_stafferBean.name}";
         
          if (srcStafferId && srcStafferId != currentStafferId)
          {

               alert('请不要同时打开多个OA连接，且当前登陆者不同，当前登陆者应为：' + currentStafferName);
               
               return false;
          }
     //}

	return true;
}
