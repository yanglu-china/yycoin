<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="产品库存列表" />
<base target="_self">
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/tableSort.js"></script>
<script language="javascript">

function querys()
{
	formEntry.submit();
}

function sures()
{
	add();
    
    closes();
}

function add()
{
    var opener = window.common.opener();
    
    var oo = getCheckBox("beans");
    
    if (oo && oo.length == 0)
    {
        alert('请选择产品库存');
        return;
    }
    
    if (oo)
    opener.getProductRelation(oo);
}

function closes()
{
	opener = null;
	window.close();
}

function load()
{
	loadForm();
}

function press()
{
    window.common.enter(querys);
} 

function closesd()
{
    var opener = window.common.opener();
    
    opener = null;
    window.close();
}

function resets()
{
    $O('name').value = '';
    $O('code').value = '';
    setSelectIndex($O('depotpartId'), 0);
    setSelectIndex($O('stafferId'), 0);
}


</script>

</head>
<body class="body_class" onload="load()">

<form name="formEntry" action="../depot/storage.do" method="post"><input
	type="hidden" name="method" value="rptQueryStorageRelation4StockBack"><input

<p:navigation
	height="22">
	<td width="550" class="navigation">产品库存管理</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">
	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr class="content1">
				<td width="15%" align="center">产品名称</td>
				<td align="center">
                    <select name="productId"  values="${productId}">
                        <option value="">-</option>
                        <c:forEach items="${productList}" var="productItem">
                            <option value="${productItem.id}">${productItem.name}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="stockId" value="${stockId}">
                    <input type="hidden" name="backType" value="${backType}">
                </td>
				<td width="15%" align="center"></td>
				<td align="center"></td>
			</tr>

			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					onclick="querys()" class="button_class"
					value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"></td>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>库存列表：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="90%">
		<table width="100%" align="center" cellspacing='1' class="table0"
			id="result">
			<tr align=center class="content0">
				<td align="center">选择</td>
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>仓库</strong></td>
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>仓区</strong></td>		
				<td align="center" onclick="tableSort(this)" class="td_class"><strong>产品</strong></td>
				<td align="center" onclick="tableSort(this, true)" class="td_class"><strong>库存数量</strong></td>
				<td align="center" onclick="tableSort(this, true)" class="td_class"><strong>价格</strong></td>
				<td align="center" onclick="tableSort(this, true)" class="td_class"><strong>虚料金额</strong></td>
			</tr>

			<c:forEach items="${beanList}" var="item" varStatus="vs">
				<tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
					<td align="center"><input type='radio' name="beans" ppid="${item.productId}"
					pstaffername="${item.stafferName}"
					pstafferid="${item.stafferId}"
					pdepotpartname="${item.depotpartName}"
					pdepotpartid="${item.depotpartId}"
					pname="${item.productName}" 
					pmtype="${item.productMtype}" 
					pcode="${item.productCode}" 
					pshowjosn='${item.showJOSNStr}' 
					pproducttype="${item.productType}" 
					pproductsailtype="${item.productSailType}" 
					pprice="${my:formatNum(item.addPrice)}" 
					prealprice="${my:formatNum(item.price)}" 
					pbatchprice="${my:formatNum(item.batchPrice)}" 
					pcostprice="${my:formatNum(item.costPrice)}"
					pinputprice="${my:formatNum(item.inputPrice)}"
					poldgoods="${item.pconsumeInDay}"
					pinputrate="${my:formatNum(item.inputRate)}"
					pamount="${item.amount}" value="${item.productId}"
					pstockammount="${my:formatNum(item.stockAmount)}"
					ptotalwarehousenum="${my:formatNum(item.totalWarehouseNum)}"				
				    pvirtualprice="${my:formatNum(item.virtualPrice)}" 
				    pvirtualpricekey="${my:formatNum(item.virtualPriceKey)}" 
				    pstorageid="${item.storageId}"
				    pstoragename="${item.storageName}"
				    pdutyid="${item.dutyId}"
				    pdutyname="${item.dutyName}"
				    plocationid="${item.locationId}"
				    plocationname="${item.locationName}"
				    pproviderid="${item.providerId}"
				    pprovidername="${item.providerName}"
				    pinvoicetype="${item.invoiceType}"
				    pinvoicetypename="${item.invoiceTypeName}"
				    /></td>
				    <td align="center" onclick="hrefAndSelect(this)">${item.locationName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.depotpartName}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.productName}(${item.productCode})</td>
					<td align="center" onclick="hrefAndSelect(this)">${item.amount}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.price)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${my:formatNum(item.virtualPrice)}</td>
				</tr>
			</c:forEach>
		</table>

	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
		<c:if test="${selectMode != 1}">
		<input type="button" class="button_class" id="adds"
            value="&nbsp;&nbsp;选 择&nbsp;&nbsp;" onClick="add()">&nbsp;&nbsp;
        </c:if>
        <input type="button" class="button_class" id="sure1"
            value="&nbsp;&nbsp;确 定&nbsp;&nbsp;" onClick="sures()" id="sures">&nbsp;&nbsp;<input type="button" class="button_class"
            value="&nbsp;&nbsp;关 闭&nbsp;&nbsp;" onClick="closesd()" id="clo">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>
</body>
</html>

