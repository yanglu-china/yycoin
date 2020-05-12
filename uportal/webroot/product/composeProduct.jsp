<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="合成产品" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script language="JavaScript" src="../product_js/composeProduct.js"></script>
<script language="javascript">

function saveBean()
{
    //保存
    $O('save').value = '0';
    submit('确定保存合成产品?', null, checks);
}

function addBean()
{
    $O('save').value = '1';
    submit('确定合成产品?', null, checks);
}

function checks()
{
	var srs = document.getElementsByName('srcRelation');
	var ret = $duplicate(srs);
	
	if (ret)
	{
		alert('相同产品不能在同一个储位');
	}

	//用量不能超过可用数量
    var useAmountElements = document.querySelectorAll('input[name="useAmount"]');
    var srcAmountElements = document.querySelectorAll('input[name="srcAmount"]');
    var targerNameElements = document.querySelectorAll('input[name="targerName"]');
    for (var i=0;i<=useAmountElements.length-1;i++){
        var useAmountElement = useAmountElements[i];
        var srcAmountElement = srcAmountElements[i];
        var targerNameElement = targerNameElements[i];
        if(useAmountElement && srcAmountElement && targerNameElement) {
            var useAmount = parseInt(useAmountElement.value);
            if (useAmount == 0){
                alert(targerNameElement.value + "使用数量不能为0!");
                return false;
            } else if (useAmount > parseInt(srcAmountElement.value)) {
                alert(targerNameElement.value + "使用数量:" + useAmountElement.value + "不能超过可用数量:" + srcAmountElement.value);
                return false;
            }
        }
    }
    return !ret;
}

var current;

function selectProduct(obj)
{
    current = obj;
    var dirDepotpart = document.querySelector('[name="dirDepotpart"]');
    // 从bom中取
    if ($O('cbom').checked == true) {
    	
    	window.common.modal('../product/product.do?method=rptQueryProductBom&firstLoad=1&selectMode=1&stock=stock&dirDepotpart='+dirDepotpart.value);
    } else {
    	var pobj = getDepartmentId(current);
        
        if (pobj.value == '')
        {
            alert('请选择仓区');
            return;
        }
        
        //查询合成产品列表
        window.common.modal(RPT_PRODUCT + '&ctype=1');
    }
}

//选择合成产品后的回调函数
function getProductBom(oos)
{
//    document.getElementById('productTable').deleteRow(1);
    //#139 从BOM表中选择之后，每次明细的第一行都是默认空白，将空白行删除
//    var table = $O("tables");
//    table.deleteRow(1);
    var tb = document.getElementById("tables");
    var rnum=tb.rows.length;
    if (rnum > 1)
    {
        for(var i=rnum -1; i>0; i--)
        {
            tb.deleteRow(i);
        }
    }
	var oo = oos[0];

    current.value = oo.pname;

    $O("mtype").value = oo.pmtype;
    $O("oldproduct").value = oo.poldproduct;
    $O("dirProductId").value = oo.value;
    $O("high").value = oo.high;
    $O("low").value = oo.low;
	
	var bomjson = JSON.parse(oo.pbomjson);
	for (var j = 0; j < bomjson.length; j++)
    {
        var item = bomjson[j];
		var trow = addTrInner();
        //console.log(item);
		setInputValueInTr(trow, 'srcProductId', item.subProductId);
		setInputValueInTr(trow, 'targerName', item.subProductName);
		setInputValueInTr(trow, 'srcProductCode', item.code);
        setInputValueInTr(trow, 'srcPrice', item.price);
        setInputValueInTr(trow, 'srcAmount', item.pamount);
        setInputValueInTr(trow, 'srcRelation', item.srcRelation);
        setInputValueInTr(trow, 'bomAmount', item.bomAmount);
        setInputValueInTr(trow, 'attritionRate', item.attritionRate);
        setInputValueInTr(trow, 'virtualPrice', item.virtualPrice);
        setInputValueInTr(trow, 'sn', item.sn);
        var srcDe1 = getEle(trow.getElementsByTagName('select'), "srcDepotpart");
        //#966 与合成产品源仓区一致
        var dirDepotpart = document.querySelector('[name="dirDepotpart"]');
        setSelect(srcDe1, dirDepotpart.value);
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
	current.value = oo.pname;
	
	var tr = getTrObject(current);
	
	var eles = tr.getElementsByTagName('input');
    
    var hobj = getEle(eles, "srcProductId");
    
    hobj.value = oo.ppid;

    var hobj1 = getEle(eles, "srcInputRate");
    
    hobj1.value = oo.pinputrate;
    
    var pobj = getEle(eles, "srcPrice");
    
    pobj.value = oo.pprice;
    
    var aobj = getEle(eles, "srcAmount");
    
    aobj.value = oo.pamount;
    
    var reobj = getEle(eles, "srcRelation");
    
    reobj.value = oo.value;

    var vobj = getEle(eles, "virtualPrice");

    vobj.value = oo.pvirtualprice;

    var sobj = getEle(eles, "sn");

    sobj.value = oo.psn;
    
    var srcDe = getEle(tr.getElementsByTagName('select'), "srcDepotpart");
    
    setSelect(srcDe, oo.pdepotpart);

    if (oos.length > 1 ) {

    	for(var i = 1; i < oos.length; i++)
    	{
    		var trow = addTrInner();
            //console.log(oos[i]);
    		setInputValueInTr(trow, 'srcProductId', oos[i].ppid);
    		setInputValueInTr(trow, 'targerName', oos[i].pname);
    		setInputValueInTr(trow, 'srcInputrowate', oos[i].pinputrowate);
    		setInputValueInTr(trow, 'srcPrice', oos[i].pprice);
    		setInputValueInTr(trow, 'srcAmount', oos[i].pamount);
    		setInputValueInTr(trow, 'srcRelation', oos[i].value);
            setInputValueInTr(trow, 'virtualPrice', oos[i].pvirtualprice);
            setInputValueInTr(trow, 'sn', oos[i].psn);
    	    var srcDe1 = getEle(trow.getElementsByTagName('select'), "srcDepotpart");
    	    
    	    setSelect(srcDe1, oos[i].pdepotpart);
    	}
    }
}

function selectDepotpartProduct(obj)
{
    current = obj;
    
    var pobj = getDepartmentId(current);
    
    if (pobj.value == '')
    {
        alert('请选择仓区');
        return;
    }

    if ($$("dirTargerName") == '')
    {
        alert("请选择合成产品");
        return;
    }
    
    var tr = getTrObject(obj);
    
    var srcProductCode = getInputInTr(tr, "srcProductCode").value;
    
   	window.common.modal('../depot/storage.do?method=rptQueryProductInDepot&load=1&compose=1&stafferId=0&selectMode=0&locationId='+ $$('srcDepot')
		   + "&code="+ srcProductCode +"&mtype=" + $$('mtype' + "&oldproduct=" + $$('oldproduct')));
}

function getProduct(oos)
{
	var oo = oos[0];
	
    current.value = oo.pname;

    $O("mtype").value = oo.pmtype;
    $O("oldproduct").value = oo.poldproduct;
    
    var hobj = getNextInput(current.nextSibling);
    
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
        return tr.getElementsByTagName('select')[0];
    }
}

function depotpartChange(obj)
{
    var tr = getTrObject(obj);
    
    var inputs = tr.getElementsByTagName('input');
    
    for (var i = 0 ; i < inputs.length; i++)
    {
        var oo = inputs[i];

        if (oo.name == 'goldPrice' || oo.name == 'silverPrice')
            continue;
        
        if (oo.type.toLowerCase() != 'button')
        {
            oo.value = '';
        }
    }
}

function depotChange()
{
	var elements = new Array();
	
	var sels = document.getElementsByName('srcDepotpart');
	
	for (var i = 0; i < sels.length; i++)
	{
		elements.push(sels[i]);
	}
	
	elements.push($O('dirDepotpart'));
	
	var newsrcDepot = $$('srcDepot');
    var defaultValue = '';
	for (var i = 0; i < elements.length; i++)
	{
		removeAllItem(elements[i]);
		
		//add new option
		for (var j = 0; j < dList.length; j++)
		{
			if (dList[j].locationId == newsrcDepot)
			{
				setOption(elements[i], dList[j].id, dList[j].name);
                if (dList[j].id == 'A1201606211663545389')
                    defaultValue = "A1201606211663545389";
			}
		}
		
		depotpartChange(elements[i]);
	}
	//默认仓区设为 备货仓
    if (defaultValue == 'A1201606211663545389')
    {
        var dirDepotpart = document.querySelector('[name="dirDepotpart"]');
//        console.log(dirDepotpart);
        setSelect(dirDepotpart,"A1201606211663545389");
        for(var i=0;i<sels.length;i++){
            setSelect(sels[i],"A1201606211663545389");
        }
    }
}

function bomClick(){
//	var tb = document.getElementById("tables");
//
//	var rnum=tb.rows.length;
//
//	if (rnum > 1)
//	{
//		 for(var i=rnum -1; i>0; i--)
//		 {
//			 tb.deleteRow(i);
//		 }
//	}
//
//	addTr();
	
	$O("mtype").value = '';
    $O("oldproduct").value = "";
    $O("dirProductId").value = '';
    $O("dirTargerName").value = '';
}

function load()
{
	addTr();

    //默认仓库"物流中心-作业库"
    var srcDepot = document.querySelector('[name="srcDepot"]');
    setSelect(srcDepot,"A1201606211663545335");

	depotChange();
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../product/product.do" method="post" id="formEntry">
    <input type="hidden" name="method" value="composeProduct">
	<input type=hidden name="mtype" value="">
	<input type=hidden name="oldproduct" value="">
    <input type="hidden" name="save" value="">

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javaScript:window.history.go(-1);">产品管理</span> &gt;&gt; 合成产品</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption"><strong>选择仓库</strong>
		<select name="srcDepot" class="select_class" style="width: 25%;" onchange="depotChange()" oncheck="notNone">
		         <c:forEach var="item" items="${depotList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	     </select> (产品合成只能在仓库内进行) <strong>如果需要增加合成费用项,请到 公共资源-->配置管理</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<p:table cells="1">
			<p:tr align="left">
			源仓区：
			<select name="dirDepotpart" class="select_class" style="width: 25%;" onchange="depotpartChange(this)" oncheck="notNone">
		         <option value="">--</option>
		         <c:forEach var="item" items="${depotpartList}">
		             <option value="${item.id}">${item.name}</option>
		         </c:forEach>
	         </select>
			合成产品：<input type="text" style="width: 20%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="dirTargerName" 
			onclick="selectProduct(this)">
         <input type="hidden" name="dirProductId" value=""><strong>从BOM中选择:</strong><input type="checkbox" name='cbom' id ='cbom' onclick="bomClick()"/>
         合成数量：<input type="text" style="width: 10%"
                    name="dirAmount" value="" oncheck="notNone;isNumber;range(1)" onblur="amountChange();">
	金价：<input type="text" style="width: 5%"
                    name="goldPrice" value="0.0" oncheck="isFloat">
	银价：<input type="text" style="width: 5%"
                    name="silverPrice" value="0.0" oncheck="isFloat">
			</p:tr>
            <p:tr align="left">
                备注：<textarea name="description" rows="3" cols="60"></textarea>
                最近一个月合成最低价：<input type="text" style="width: 10%" id="low" value="" disabled>
                最高价：<input type="text" style="width: 10%" id="high" value="" disabled>
            </p:tr>
		</p:table>
	</p:subBody>
	
	<p:tr></p:tr>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables2">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">合成费用项</td>
                        <td width="20%" align="center">费用</td>
                        <td width="50%" align="center">备注</td>
                    </tr>
                    
                    <c:forEach items="${feeList}" var="item">
                    <tr align="center" class="content1">
                        <td width="30%" align="center">${item.name}</td>
                        <td width="30%" align="center">
                        <input type="hidden" 
                    name="feeItemId" value="${item.id}">
                        <input type="text" style="width: 100%"
                    name="feeItem" value="0.0" oncheck="notNone;isFloat">
                        </td>
                        <td width="30%" align="center">
                        <input type="text" style="width: 100%"
                    name="idescription" value="" maxlength="200">
                        </td>
                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:tr></p:tr>
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0" class="border" id="productTable">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="18%" align="center">源仓区</td>
                        <td width="45%" align="center">源产品</td>
                        <td width="3%" align="center">使用数量</td>
                        <td width="3%" align="center">可用数量</td>
                        <td width="3%" align="center">价格</td>
                        <td width="3%" align="center">组成用量</td>
                        <td width="3%" align="center">损耗率(‰)</td>
                        <td width="3%" align="center">XL</td>
                        <td width="3%" align="center">单品码</td>
                        <td width="3%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
        <div align="left" id="price"></div>
		<div align="right">
            <input type="button" class="button_class"
                   value="&nbsp;&nbsp;毛利率试算&nbsp;&nbsp;" onclick="computePrice()">&nbsp;&nbsp;
            <input type="button" class="button_class"
                   value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onclick="saveBean()">&nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean()">
        </div>
	</p:button>
	<p:message2/>
</p:body>
</form>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="95%" align="center">
         <select name="srcDepotpart" class="select_class" style="width: 100%;" onchange="depotpartChange(this)" oncheck="notNone">
         <option value="">--</option>
         <c:forEach var="item" items="${depotpartList}">
             <option value="${item.id}">${item.name}</option>
         </c:forEach>
         </select>
         </td>
         <td width="30%" align="center">
             <input type="text"
         style="width: 100%;cursor: pointer;" readonly="readonly" value="" oncheck="notNone" name="targerName" onclick="selectDepotpartProduct(this)">
         <input type="hidden" name="srcProductId" value="">
         <input type="hidden" name="srcInputRate" value="">
         <input type="hidden" name="srcProductCode" value="">
         </td>
         <td width="15%" align="center"><input type="text" style="width: 100%"
                    name="useAmount" value="" oncheck="notNone;isNumber"></td>
         <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                    name="srcAmount" value="" oncheck="notNone;isNumber"></td>
         <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                    name="srcPrice" value="0" oncheck="isFloat">
        <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                                              name="bomAmount" value="0" >
        <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                                              name="attritionRate" value="0" >
         <input type="hidden" name="srcRelation" value="">
         </td>
        <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                                              name="virtualPrice" value="" oncheck="isFloat"></td>
        <td width="15%" align="center"><input type="text" style="width: 100%" readonly="readonly"
                                              name="sn" value="" ></td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>
</body>
</html>

