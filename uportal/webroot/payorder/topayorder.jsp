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
<script language="JavaScript" src="../stockapply_js/detailStockPayApply.js"></script>
<script language="javascript">
function dopay()
{
	if($("#bankName").val() == '' || $("#bankId").val()=='')
	{
		alert("请选择付款银行！");
		return;
	}
	if($("#billNoString").val() == '')
	{
		alert("请返回上一级页面重新选择付款单！");
		return;
	}
	if (window.confirm('确定付款?'))
	{
		
		var formData = $("#adminForm").serialize();
		$.ajax({
	        type: "POST",
	        url: '../payorder/queryPayOrder.do?method=doPayOrder',
	        data: formData, // serializes the form's elements.
	        success: function(data)
	        {
	            alert(data);
	            window.location.href='../payorder/queryPayOrder.do?method=queryPayOrder';
	        }
	    });
	}
}

var g_obj;
function selectBank(obj)
{
    g_obj = obj;
    
    //单选
    window.common.modal('../finance/bank.do?method=rptQueryBank&load=1&invoiceId=${bean.invoiceId}&payType=1');
}

function getBank(obj)
{
    g_obj.value = obj.pname;
    
    var hobj = getNextInput(g_obj.nextSibling);
    
    hobj.value = obj.value;
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
</script>
</head>
<body class="body_class">
<form action="../payorder/queryPayOrder.do" id="adminForm">
<input type="hidden" value="doPayOrder" name="method"></input>
<input type="hidden" value="" name="billNoArrString" id="billNoArrString"></input>
<p:navigation height="22">
    <td width="550" class="navigation">资金管理 &gt;&gt; 提取付款单</td>
    <td width="85"></td>
</p:navigation>
<p:title>
		<td class="caption"><strong>选择付款银行：</strong></td>
</p:title>
<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr>
		<td align='center' colspan='2'>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border">
				<tr>
			        <td colspan='2' align='center'>
				        <table width="100%" border="0" cellpadding="0" cellspacing="0"
				            class="border" id="inner_table">
				            <tr>
				                <td>
				                	<table width="100%" border="0" cellspacing='1' id="tables">
					                    <tr align="center" class="content0">
					                        <td width="30%" align="center">银行</td>
					                        <td width="15%" align="center">金额</td>
			                   			 </tr>
			                   			 <tr class="content1" id="trCopy" style="">
									         <td width="50%" align="center">
									         	<input name="bankName" id="bankName" type="text" readonly="readonly" style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectBank(this)"/>
									         	<input type="hidden" id="bankId" name="bankId" value=""/> 
									         </td>
									         <td width="20%" align="center">${my:formatNum(totalAmount)}</td>
								    	</tr>
		                			</table>
               					</td>
		            		</tr>
				        </table>
			        </td>
		    	</tr>
			</table>
		</td>
	</tr>
	
	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border">
			<tr>
				<td align="center">
					<input type="button" id="todopay" onclick="dopay()" class="button_class" value="&nbsp;&nbsp;付款&nbsp;&nbsp;"/>
					<input type="button" id="ret" onclick="retquery()" class="button_class" value="&nbsp;&nbsp;返回&nbsp;&nbsp;"/>
				</td>
			</tr>
		</table>
	</tr>

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
							<td align="center" onclick="tableSort(this)" class="td_class">单据状态</td>
							<td align="center" onclick="tableSort(this)" class="td_class">付款备注</td>
						</tr>
						<tbody id="tbdata">
						<c:forEach items="${payOrderList}" var="item" varStatus="vs">
							<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
								<td align="center">
									<c:if test="${item.billType == 1}">
									<a onclick="show('../finance/stock.do?method=findStockPayApply&id=${item.billNo}')" href="javascript:void(0);">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 2}">
									<a onclick="show('../finance/stock.do?method=findStockPrePayApply&id=${item.billNo}')" href="javascript:void(0);">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 3}">
									<a onclick="show('../tcp/apply.do?method=findTravelApply&id=${item.billNo}')" href="javascript:void(0);">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 4}">
									<a onclick="show('../tcp/expense.do?method=findExpense&id=${item.billNo}')" href="javascript:void(0);">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 5}">
									<a onclick="show('../tcp/backprepay.do?method=findBackPrePay&id=${item.billNo}')" href="javascript:void(0);">${item.billNo}</a>
									</c:if>
								</td>
								<td align="center">${item.billTypeDesc}
								</td>
								<td align="center">${item.billDate}</td>
								<td align="center">${my:formatNum(item.payeeAmount)}</td>
								<td align="center">${item.payeeBank}</td>
								<td align="center">${item.payeeBankAccName}</td>
								<td align="center">${item.payeeBankAcc}</td>
								<c:if test="${item.billStatus == 1}">
								<td align="center">待付款</td>
								</c:if>
								<c:if test="${item.billStatus == 2}">
								<td align="center">待确认</td>
								</c:if>
								<c:if test="${item.billStatus == 3}">
								<td align="center">已付款</td>
								</c:if>
								<c:if test="${item.billStatus == 4}">
								<td align="center">未成功待付款</td>
								</c:if>
								<td align="center"></td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<input type="hidden" value="doPayOrder" name="method"/>
<input type="hidden" value="${billNoString}" name="billNoString" id="billNoString"/>
</form>
<form id="retform" name="retform" action="../payorder/queryPayOrder.do">
<input type="hidden" value="queryPayOrder" name="method"/>
<input type="hidden" value="${queryMap.payeeBank}" name="payeeBank" id="payeeBank"/>
<input type="hidden" value="${queryMap.payeeAccName }" name="payeeAccName" id="payeeAccName"/>
<input type="hidden" value="${queryMap.payeeAcc }" name="payeeAcc" id="payeeAcc"/>
<input type="hidden" value="${queryMap.payeeAmount }" name="payeeAmount" id="payeeAmount"/>
<input type="hidden" value="${queryMap.billTime }" name="billTime" id="billTime"/>
<input type="hidden" value="${queryMap.payOrderType }" name="payOrderType" id="payOrderType"/>
<input type="hidden" value="${queryMap.payOrderStatus }" name="payOrderStatus" id="payOrderStatus"/>
</form>
</body>
</html>