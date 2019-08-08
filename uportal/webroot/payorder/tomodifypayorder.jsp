<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="集中付款管理"/>
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript" src="../stockapply_js/detailStockPayApply.js"></script>
<script type="text/javascript">
function saveorder()
{
	if($("#bankName").val() == '')
	{
		alert("请选择收款银行！");
		return;
	}
	if($("#userName").val() == '')
	{
		alert("请填写收款户名");
		return;
	}
	if($("#bankNo").val() == '')
	{
		alert("请填写收款账号");
		return;
	}
	
	if($("#bankprovince").val() == '')
	{
		alert("请填写开户省份");
		return;
	}
	
	if($("#bankcity").val() == '')
	{
		alert("请填写开户城市");
		return;
	}
	
	if (window.confirm('确定修改?'))
	{
		var formData = $("#adminForm").serialize();
		$.ajax({
	        type: "POST",
	        url: '../payorder/queryPayOrder.do?method=saveModifyPayOrder',
	        data: formData, // serializes the form's elements.
	        success: function(data)
	        {
	            alert(data);
	            window.location.href='../payorder/queryPayOrder.do?method=queryPayOrder';
	        }
	    });
	}
}

var cityObj;

function selectCity(obj)
{
	var provinceId = $O('provinceId').value;
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryCity&load=1&selectMode=1&provinceId=' + provinceId);
}
function getCitys(oos)
{
    var obj = oos[0];
    
    cityObj.value = obj.pname;
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
function retquery()
{
	$("#retform").submit();
}
function show(url)
{
	 window.common.modal(url,'');
}
function selectOpeningBank(obj)
{
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryOpeningBank&load=1&selectMode=1');
}

function getOpeningBank(oos)
{
    var obj = oos[0];
    cityObj.value = obj.pname;
}
function selectProvince(obj)
{
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryProvince&load=1&selectMode=1');
}

function getProvinces(oos)
{
    var obj = oos[0];
    cityObj.value = obj.pname;
    $O('provinceId').value=obj.value;
}
</script>
</head>
<body class="body_class">
<form action="../payorder/queryPayOrder.do" id="adminForm">
<input type="hidden" name="provinceId" value="">
<p:navigation height="22">
    <td width="550" class="navigation">资金管理 &gt;&gt; 修改付款单</td>
    <td width="85"></td>
</p:navigation>
<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing='1' id="mainTable">
						<tr align="center" class="content0">
							<td align="center" onclick="tableSort(this)" class="td_class">单据号</td>
							<td align="center" onclick="tableSort(this)" class="td_class">单据类型</td>
							<td align="center" onclick="tableSort(this)" class="td_class">单据日期</td>
							<td align="center" onclick="tableSort(this)" class="td_class">收款金额(元)</td>
							<td align="center" onclick="tableSort(this)" class="td_class">收款银行</td>
							<td align="center" onclick="tableSort(this)" class="td_class">收款户名</td>
							<td align="center" onclick="tableSort(this)" class="td_class">收款帐号</td>
							<td align="center" onclick="tableSort(this)" class="td_class">开户省份</td>
                        	<td align="center" onclick="tableSort(this)" class="td_class">开户城市</td>
						</tr>
						<tbody id="tbdata">
						<c:forEach items="${payOrderLogVoList}" var="item" varStatus="vs">
							<tr class='content1'>
								<td align="center">
									<c:if test="${item.type == 1}">
									<a onclick="show('../finance/stock.do?method=findStockPayApply&id=${item.outid}')" href="javascript:void(0);">${item.outid}</a>
									</c:if>
									<c:if test="${item.type == 2}">
									<a onclick="show('../finance/stock.do?method=findStockPrePayApply&id=${item.outid}')" href="javascript:void(0);">${item.outid}</a>
									</c:if>
									<c:if test="${item.type == 3}">
									<a onclick="show('../tcp/apply.do?method=findTravelApply&id=${item.outid}')" href="javascript:void(0);">${item.outid}</a>
									</c:if>
									<c:if test="${item.type == 4}">
									<a onclick="show('../tcp/expense.do?method=findExpense&id=${item.outid}')" href="javascript:void(0);">${item.outid}</a>
									</c:if>
									<c:if test="${item.type == 5}">
									<a onclick="show('../tcp/backprepay.do?method=findBackPrePay&id=${item.outid}')" href="javascript:void(0);">${item.outid}</a>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${item.type == 1}">
										采购预付款
									</c:if>
									<c:if test="${item.type == 2}">
										采购付款
									</c:if>
									<c:if test="${item.type == 3}">
										借款申请付款
									</c:if>
									<c:if test="${item.type == 4}">
										报销申请付款
									</c:if>
									<c:if test="${item.type == 5}">
										预收退款
									</c:if>
								</td>
								<td align="center">${item.outidtime}</td>
								<td align="center">${my:formatNum(item.money)}</td>
								<td align="center">
									<input type="text" name="bankName" id="bankName" onclick="selectOpeningBank(this)" value="${item.bankName}" readonly="readonly" style="width: 300px"/>
								</td>
								<td align="center"><input type="text" name="userName" id="userName" value="${item.userName}"/></td>
								<td align="center"><input type="text" name="bankNo" id="bankNo" value="${item.bankNo}"/></td>
								<td align="left">
						         	<input type="text" name="bankprovince" id ='bankprovince' onclick='selectProvince(this)' style='cursor: pointer;'  readonly=readonly value="${bankProvince}"    oncheck="notNone;"  maxlength="100">
						         </td>
						         
								 <td align="left" >
								 	<input type="text" name='bankcity' head='开户城市'  id ='bankcity' onclick='selectCity(this)' style='cursor: pointer;'  readonly=readonly value="${bankCity }"   oncheck="notNone;"  maxlength="100" > 
								 </td>
								<td align="center">
									<input type="hidden" id="outId" name="outId" value="${item.outid}">
									<input type="hidden" id="outBillId" name="outBillId" value="${item.outbillid}">
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center">
			<input type="button" id="save" onclick="saveorder()" class="button_class" value="&nbsp;&nbsp;保存&nbsp;&nbsp;"/>
			<input type="button" id="ret" onclick="retquery()" class="button_class" value="&nbsp;&nbsp;返回&nbsp;&nbsp;"/>
		</td>
	</tr>
</table>
</form>
<form id="retform" name="retform" action="../payorder/queryPayOrder.do">
<input type="hidden" value="queryPayOrder" name="method"/>
<input type="hidden" value="4" name="payOrderStatus"/>
</form>
</body>
</html>