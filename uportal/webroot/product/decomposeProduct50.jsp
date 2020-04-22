<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="拆分产品" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="JavaScript" src="../js/lodash.min.js"></script>
<script language="javascript">

function addBean()
{
    submit('确定拆分产品?', null, checks);
}


function getPrices(){
    var srs = document.getElementsByName('srcProductId');
    var stypes = document.getElementsByName('stype');
    var srcDepots = document.getElementsByName('srcDepot');
    var srcDepotparts = document.getElementsByName('srcDepotpart');
    var srcAmounts = document.getElementsByName('srcAmount');
    var srcPrices = document.getElementsByName('srcPrice');
    var vPrices = document.getElementsByName('vPrice');

    var parts = 0;
    var vPriceTotal = 0;

    for (var i = 0; i < srs.length - 1; i++)
    {
        // 库存类型
        if (stypes[i].value == '0')
        {
            if (srcDepots[i].value == '' || srcDepotparts[i].value == '')
            {
                alert('库存类型时,请选择仓库及仓区');
                return false;
            }
        }else{
            srcDepots[i].value = '';
            srcDepotparts[i].value = '';
        }

        //#747
        if (srcAmounts[i].value == '0')
        {
            alert('配件数量不能为0!');
            return false;
        }

        parts += parseFloat(srcAmounts[i].value) * parseFloat(srcPrices[i].value);
        // console.log(parseFloat(srcAmounts[i].value) * parseFloat(srcPrices[i].value));
        // console.log(parts);
        vPriceTotal += parseFloat(srcAmounts[i].value) * parseFloat(vPrices[i].value);
    }

    var finishedProduct = parseFloat($$('amount')) * parseFloat($$('price'));
    var finishedProductVirtualTotal = parseFloat($$('amount')) * parseFloat($$('virtualPrice'));

    // return [parts, finishedProduct, vPriceTotal, finishedProductVirtualTotal]

    return [_.round(parts, 4), _.round(finishedProduct, 4), _.round(vPriceTotal, 4), _.round(finishedProductVirtualTotal, 4)]
}


function checks()
{
	var srs = document.getElementsByName('srcProductId');
	
	var ret = $duplicate(srs);
	
	if (ret)
	{
		alert('相同产品不能在同一个储位');

		return !ret;
	}

    var prices = getPrices();
	if (prices){
        var parts = prices[0];
        var finishedProduct = prices[1];
        var vPriceTotal = prices[2];
        var finishedProductVirtualTotal = prices[3];
        var precision = 0.03;
        if (compareDouble(parts, finishedProduct) != 0 && Math.abs(parts-finishedProduct) >=precision )
        {
            alert('配件成本之和:'+parts+'要与成品成本一致:'+finishedProduct);
            return false;
        } else if (compareDouble(vPriceTotal, finishedProductVirtualTotal) != 0 && Math.abs(parts-finishedProduct) >=precision)
        {
            alert('配件虚料金额之和:'+vPriceTotal+'要与成品虚料金额一致:'+finishedProductVirtualTotal);
            return false;
        }

        return true;
    } else{
	    return false;
    }
}


function adjustPrice(){
    var prices = getPrices();

    //自动调整配件成本
    var parts = prices[0];
    var finishedProduct = prices[1];
    var diff = finishedProduct-parts;
    // console.log('diff***'+diff);
    var srcAmount = document.querySelectorAll('input[name="srcAmount"]');
    var srcPrice = document.querySelectorAll('input[name="srcPrice"]');
    //auto adjust the first part product's price
    var price0 = srcPrice[0];
    var price = parseFloat(price0.value)+diff/parseInt(srcAmount[0].value);
    // console.log(price);
    price0.value = _.round(price, 6);

    //自动调整虚料金额
    //TODO 需要自动调整到虚拟产品上
    var vPriceTotal = prices[2];
    var finishedProductVirtualTotal = prices[3];
    var diff2 = finishedProductVirtualTotal-vPriceTotal;
    // console.log(diff);

    var vPriceElements = document.querySelectorAll('input[name="vPrice"]');
    //auto adjust the first part product's price
    var vprice0 = vPriceElements[0];
    var vPrice = parseFloat(vprice0.value)+diff2/parseInt(srcAmount[0].value);
    // console.log(price);
    vprice0.value = _.round(vPrice, 6);
}

var current;

var flag = 0;
function selectProduct(obj)
{
    current = obj;
	flag = 0;
	
    if ($$('depot') == '')
    {
        alert('请选择仓区');
        return;
    }

//    if ($O('cbom').checked == true) {
//
//        window.common.modal('../product/product.do?method=rptQueryProductBom&firstLoad=1&selectMode=1&stock=stock');
//    } else{
//        //查询拆分产品列表
//        window.common.modal('../depot/storage.do?method=rptQueryStorageRelationInDepot&load=1&selectMode=1&depotId='
//            + $$('depot') + '&depotpartId=' + $$('depotpart') + '&ctype=1' + '&init=1');
//    }
    var depotpartId = $$('depotpart')
    // console.log(depotpartId);
   //查询拆分产品列表
    window.common.modal('../product/product.do?method=rptQueryLatestComposeProduct&load=1&selectMode=1&depotpartId='+depotpartId);
}

var rateList = [];
// var srcPriceList = [];


function getProductBom(oos)
{
    try {
        //从BOM表中选择之后，每次明细的第一行都是默认空白，将空白行删除
        var table = $O("tables");
        // console.log(table);
        //删除原有两行,没删除一行后index会变化
        table.deleteRow(1);
        table.deleteRow(1);
//    var table = document.getElementById("tables");
//    while(table.rows.length > 0) {
//        table.deleteRow(0);
//    }
//    table.deleteRow(2);

        // console.log(oos);
        var oo = oos[0];
        console.log(oo);
        $O('productName').value = oo.pname;
        $O('productId').value = oo.value;


        var url = "../product/product.do?method=findCompose&id=" + oo.id;
//    console.log(url);
        var html = "<strong>最近合成:</strong>" + "<a href='" + url + "'>" + oo.id + "</a>";
//    console.log(html);
        $O('composeId').innerHTML = html;


        // console.log(bomjson);
        var amount = oo.pamount;
        var price = oo.pprice;
        var html2 = "<strong>最近合成成本:</strong>" + price;
        $O('composePrice').innerHTML = html2;

        var storagePrice = oo.sprice;
        var virtualPrice = oo.vprice;
        // console.log(virtualPrice);
        // console.log(amount);
        // console.log(price);
        // console.log(storagePrice);
        document.getElementById("amount").value = amount;
        document.getElementById("price").value = _.round(storagePrice, 4);
        document.getElementById("virtualPrice").value = _.round(virtualPrice, 4);
        var bomJson = oo.pbomjson;
        // console.log(bomJson);
        if (bomJson){
            var bomElements = JSON.parse(bomJson);
            // console.log(bomElements);
            var srcPriceElements = document.querySelectorAll('input[name="srcPrice"]');
            for (var j = 0; j < bomElements.length; j++) {
                var item = bomElements[j];
                // console.log(item);
                var trow = addTrInner();

                var stype = getEle(trow.getElementsByTagName('select'), "stype");
                setSelect(stype, "0");
                var srcDe1 = getEle(trow.getElementsByTagName('select'), "srcDepot");
                setSelect(srcDe1, "A1201606211663545335");
                setInputValueInTr(trow, 'srcProductName', item.productName);
                setInputValueInTr(trow, 'srcProductId', item.productId);
                //成品数量*配件装配率
               setInputValueInTr(trow, 'srcAmount', Math.round(parseInt(amount)*parseFloat(item.assemblyRate)));
               setInputValueInTr(trow, 'srcPrice', item.price);
               //#956 读取虚料金额XL
               setInputValueInTr(trow, 'vPrice', item.virtualPrice);
                //配件使用率
//            rateList.push(item.amount / amount);
                rateList.push(item.assemblyRate);
                // srcPriceList.push(item.price);

                //set part product's price
                // var oo2 = srcPriceElements[j];
                // if(oo2){
                //     oo2.value = parseFloat(item.price);
                // }

                var srcDepotpart = getEle(trow.getElementsByTagName('select'), "srcDepotpart");
                //add new option
                for (var k = 0; k < dList.length; k++) {
                    if (dList[k].locationId == "A1201606211663545335") {
                        setOption(srcDepotpart, dList[k].id, dList[k].name);
                    }
                }
                setSelect(srcDepotpart, "A1201606211663545389");
            }
        }
    }catch(error){
        // console.log(error);
    }
}


function amountChange(){
    var srcAmount = document.querySelectorAll('input[name="srcAmount"]');
    var srcPrice = document.querySelectorAll('input[name="srcPrice"]');
//    console.log(srcAmount);
    var amount = document.querySelector('input[name="amount"]');
    // console.log(amount.value);
    // console.log(rateList);
    // var total = 0;
    for (var i = 0 ; i < srcAmount.length; i++)
    {
        var oo = srcAmount[i];
        //成品数量*配件装配率
        oo.value = parseInt(amount.value)*parseInt(rateList[i]);

        // var oo2 = srcPrice[i];
        // oo2.value = parseFloat(srcPriceList[i]);
        // var temp = parseInt(amount.value)*parseInt(rateList[i])*parseFloat(srcPriceList[i]);
        // if (!isNaN(temp)){
        //     total += temp;
        //     // console.log(total);
        // }
    }
}

function getEle(eles, name)
{
	for (var i = 0; i < eles.length; i++)
	{
		if (eles[i].name == name)
		{
			return eles[i];
		}
	}

	return null;
}

function getProductRelation(oos)
{
	var oo = oos[0];

	$O('depotpart').value = oo.pdepotpartid;
	$O('productName').value = oo.pname;
	$O('productId').value = oo.ppid;
	$O('amount').value = oo.pamount;
	$O('mayAmount').value = oo.pamount;
	$O('price').value = oo.prealprice;
}

function selectDepotpartProduct(obj)
{
    current = obj;

    if ($$("productName") == '')
    {
        alert("请选择拆分产品");
        return;
    }

 	//查询配件产品列表
   //window.common.modal(RPT_PRODUCT);
   window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&abstractType=0&status=0');
}

function getProduct(oos)
{
	var oo = oos[0];

	current.value = oo.pname;

	var tr = getTrObject(current);

	var eles = tr.getElementsByTagName('input');

    var hobj = getEle(eles, "srcProductId");

    hobj.value = oo.value;
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}

var dList = JSON.parse('${depotpartListStr}');

function getDepartmentId(obj)
{
    var tr = getTrObject(obj);

    if (tr != null)
    {
    	return tr.getElementsByTagName('select')[2];
    }
}

function getDepotId(obj)
{
    var tr = getTrObject(obj);

    if (tr != null)
    {
    	return tr.getElementsByTagName('select')[1];
    }
}

function depotpartChange(obj)
{
    var tr = getTrObject(obj);

    var inputs = tr.getElementsByTagName('input');

    for (var i = 0 ; i < inputs.length; i++)
    {
        var oo = inputs[i];

        if (oo.type.toLowerCase() != 'button')
        {
            oo.value = '';
        }
    }
  	//add by zhangxian 2019-06-17
	//change cascade srcdepotpart item
	var srcDepotparts = document.getElementsByName("srcDepotpart");
  	console.log(srcDepotparts.length);
   	//add new option
   	for(var j=0;j<srcDepotparts.length;j++)
   	{
   		var oneselect = srcDepotparts[j];
		for(i=0;i<oneselect.length;i++){
		    if(oneselect[i].value==obj.value)
		    {
		    	oneselect[i].selected = true;
		    	break;
		    }
		}
   	}
    //end add
}

function depotChange()
{
	var newsrcDepot = $$('depot');

	console.log(newsrcDepot);

	removeAllItem($O('depotpart'));

	//add new option
	for (var j = 0; j < dList.length; j++)
	{
		if (dList[j].locationId == newsrcDepot)
		{
			setOption($O('depotpart'), dList[j].id, dList[j].name);
		}
	}

	depotpartChange($O('depotpart'));

	//add by zhangxian 2019-06-17
	//change cascade srcdepotpart item
	var srcDepots = document.getElementsByName("srcDepot");
	console.log(srcDepots.length);
   	//add new option
   	for(var j=0;j<srcDepots.length;j++)
   	{
   		var oneselect = srcDepots[j];
		for(i=0;i<oneselect.length;i++){
		    if(oneselect[i].value==newsrcDepot)
		    {
		    	oneselect[i].selected = true;
		    	srcDepotChange(oneselect);
		    	break;
		    }
		}
   	}
    //end add

}

function srcDepotChange(obj)
{
	var newsrcDepot = obj.value;

	var tr = getTrObject(obj);

    var selects = tr.getElementsByTagName('select');

    for (var i = 0 ; i < selects.length; i++)
    {
        var oo = selects[i];

        if (oo.name == 'srcDepotpart')
        {
        	removeAllItem(oo);

        	//add new option
        	for (var j = 0; j < dList.length; j++)
        	{
        		if (dList[j].locationId == newsrcDepot)
        		{
        			setOption(oo, dList[j].id, dList[j].name);
        		}
        	}

        	break;
        }
    }
}



function selectSrcProduct()
{
	var productId = $$('productId');

	if (productId == '')
	{
		alert('请选择 要拆分的产品');
		return;
	}

	//
	window.common.modal('../product/product.do?method=rptQueryComposeProduct&load=1&selectMode=0&productId=' + productId);
}

function getComposeProduct(oos)
{
	for(var i = 0; i < oos.length; i++)
	{
		var trow = addTrInner();

		setInputValueInTr(trow, 'srcProductId', oos[i].value);
		setInputValueInTr(trow, 'srcProductName', oos[i].pname);
	}
}

function load()
{
	addTr1();

	depotChange();
}

function addTr1()
{
    for (var k = 0; k < 2; k++)
    {
        var trow = addTrInner();

        var selects = trow.getElementsByTagName('select');

        for (var i = 0 ; i < selects.length; i++)
        {
            var oo = selects[i];

            if (oo.name == 'srcDepotpart')
            {
            	removeAllItem(oo);

            	//add new option
            	for (var j = 0; j < dList.length; j++)
            	{
            		if (dList[j].locationId == 'A1201606211663545335')
            		{
            			setOption(oo, dList[j].id, dList[j].name);
            		}
            	}

            	break;
            }
        }
    }

    loadForm();
}

</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/product.do" method="post"><input
	type="hidden" name="method" value="deComposeProduct">

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 产品拆分</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
			<strong>成品</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			成品仓库：
			<select name="depot" class="select_class" style="width: 15%;" onchange="depotChange()" oncheck="notNone">
		         <c:forEach var="item" items="${depotList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	     	</select>
			成品仓区：
			<select name="depotpart" class="select_class" style="width: 20%;" onchange="depotpartChange(this)" oncheck="notNone">
		         <option value="">--</option>
		         <c:forEach var="item" items="${depotpartList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	         </select>
			拆分产品：<input type="text" style="width: 20%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="productName"
			onclick="selectProduct(this)">
                <%--<strong>从BOM中选择:</strong><input type="checkbox" name='cbom' id ='cbom' />--%>
         <input type="hidden" name="productId" value="">
         	数量：<input type="text" style="width: 5%" name="amount" id="amount" value="" oncheck="notNone;isNumber;" onblur="amountChange();">
                    <input type="hidden" name="mayAmount" value=""/>
			成本：<input type="text" style="width: 6%"  name="price" id="price" value="1" oncheck="notNone;isFloat" readonly>
                虚料金额：<input type="text" style="width: 6%"  name="virtualPrice" id="virtualPrice" value="1" oncheck="notNone;isFloat" readonly>
			</p:tr>
            <%--<p:tr align="right">--%>
                <%--<input type="button" class="button_class" id="ref_b"--%>
                       <%--value="&nbsp;配件产品查询&nbsp;" onclick="selectSrcProduct()">--%>
            <%--</p:tr>--%>
		</p:table>
	</p:subBody>

	<p:title>
        <td class="caption">
         <strong>配件产品</strong>
        </td>
        <td>
            <div id="composeId"></div>
        </td>
        <td>
            <div id="composePrice"></div>
        </td>
    </p:title>

	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="3%" align="center">类型</td>
                        <td width="15%" align="center">仓库</td>
                        <td width="15%" align="center">仓区</td>
                        <td width="42%" align="center">产品</td>
                        <td width="3%" align="center">数量</td>
                        <td width="3%" align="center">成本</td>
                        <td width="3%" align="center">XL</td>
                        <td width="3%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr1()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
            <input type="button" class="button_class"
                   value="&nbsp;&nbsp;自动调整配件成本&nbsp;&nbsp;" onclick="adjustPrice()">
		  <input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>

	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="100%" align="center">
         <select name="stype" class="select_class" style="width: 100%;" onchange="stypeChange(this)" oncheck="notNone" values="0">
         <option value="">--</option>
         <option value="0">库存</option>
         <option value="1">费用</option>
         </select>
         </td>
         <td width="95%" align="center">
         <select name="srcDepot" id="srcDepot" class="select_class" style="width: 100%;" onchange="srcDepotChange(this)">
         <option value="">--</option>
         <c:forEach var="item" items="${depotList}">
             <option value="${item.id}">${item.name}</option>
         </c:forEach>
         </select>
         </td>
         <td width="95%" align="center">
         <select name="srcDepotpart" id="srcDepotpart" class="select_class" style="width: 100%;">
         <option value="">--</option>
         </select>
         </td>
         <td width="30%" align="center"><input type="text"
         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="srcProductName" onclick="selectDepotpartProduct(this)">
         <input type="hidden" name="srcProductId" value="">
         </td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="srcAmount" value="" oncheck="notNone;isNumber"></td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="srcPrice" value="" oncheck="notNone;isFloat">
         </td>
        <td width="15%" align="center">
            <input type="text" style="width: 100%"
                                              name="vPrice" value="0" oncheck="notNone;isFloat">
        </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>

