<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="绑定银行回单" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">
var billNoArr = new Array();
function query()
{
	$("#adminForm").submit();
}
function dopay()
{
	if(billNoArr.length==0)
	{
		alert("请至少选择一笔款项！");
		return;
	}
	$("#billNoString").val(billNoArr.toString());
	$("#topayform").submit();
}
function res()
{
	$O('payOrderNo').value = '';
	$O("payeeAccName").value = '';
	$O("payeeAcc").value = '';
	$O("payeeAmount").value = '';
	$O("billTime").value = '';
	$O("billEndTime").value = '';
	$O('payeeBank').value = '';
	$('#payOrderType').val(0);
	$('#payOrderStatus').val(0);
}
function checkall(){
	billNoArr = new Array();
    if ($("#ckall").attr("checked")) {
        $("#tbdata :checkbox").attr("checked", true);
        $("#tbdata :checkbox").each(function(){
        	billNoArr.push($(this).val())
        })
    } else {
        $("#tbdata :checkbox").attr("checked", false);
        $("#tbdata :checkbox").each(function(){
        	billNoArr.pop($(this).val())
        })
    }
    $("#billNoArrString").val(billNoArr);
}
function singleck(obj)
{
	if($(obj).attr("checked"))
	{
		billNoArr.push($(obj).val())
	}
	else{
		billNoArr.pop($(obj).val())
	}
	$("#billNoArrString").val(billNoArr);
}
function change(outid,outbillid,billtype)
{
	window.location.href='../payorder/queryPayOrder.do?method=toUploadAttachement&outid=' + outid +"&outbillid=" + outbillid;
}
</script>
</head>
<body>
<p:navigation height="22">
    <td width="550" class="navigation">资金管理 &gt;&gt; 绑定银行回单</td>
    <td width="85"></td>
</p:navigation>
<form action="../payorder/queryPayOrder.do" id="adminForm">
<input type="hidden" value="queryAttachement" name="method"></input>

<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr class="content1">
        <%-- <td style="width: 15%" align="center">单据类型:</td>
        <td align="center" style="width: 35%">
        	<select name="payOrderType" id="payOrderType" class="select_class" values="${queryMap.payOrderType}">
				<option value="">--</option>
				<!-- <option value="1">采购付款</option> -->
				<!-- <option value="2">采购预付款</option> -->
				<option value="3">借款申请付款</option>
				<option value="4">报销申请付款</option>
				<option value="5">预收退款</option>
			</select>
        </td> --%>
        <td align="center" style="width: 15%">单据号:</td>
        <td align="center"><input type="text" name="payOrderNo" value="${queryMap.payOrderNo }"/>
        </td>
    </tr>

	<tr class="content2">
		<td style="width: 15%" align="center">收款银行:</td>
		<td align="center"><input type="text" name="payeeBank" value="${queryMap.payeeBank }"/></td>
		<td style="width: 15%" align="center">收款户名：</td>
		<td align="center"><input type="text" name="payeeAccName" value="${queryMap.payeeAccName }"/></td>
	</tr>

	<tr class="content1">
		<td width="15%" align="center">收款帐号:</td>
		<td align="center"><input type="text" name="payeeAcc" value="${queryMap.payeeAcc }"/></td>
		<td width="15%" align="center">收款金额（元）:</td>
		<td align="center"><input type="text" name="payeeAmount" value="${queryMap.payeeAmount }"/></td>
	</tr>

	<tr class="content2">
		<td width="15%" align="center">开始日期:</td>
		<td align="center" colspan="1"><p:plugin name="billTime" type="0" size="20" value="${queryMap.billTime }"/></td>
		
		<td width="15%" align="center">结束日期:</td>
		<td align="center" colspan="1"><p:plugin name="billEndTime" type="0" size="20" value="${queryMap.billEndTime }"/></td>
	</tr>
	<tr class="content1">
		<td colspan="4" align="right">
			<input type="button" id="query_b" onclick="query()" class="button_class" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;"/>&nbsp;&nbsp;
			<input type="button" onclick="res()" class="button_class" value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"/>
		</td>
	</tr>
	
	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
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
							<td align="center" onclick="tableSort(this)" class="td_class">操作</td>
						</tr>
						<tbody id="tbdata">
						<c:forEach items="${payOrderLogList}" var="item" varStatus="vs">
							<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
								<td align="center">
									<c:if test="${item.billType == 1}">
									<a onclick="" href="../finance/stock.do?method=findStockPayApply&id=${item.billNo}">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 2}">
									<a onclick="" href="../finance/stock.do?method=findStockPrePayApply&id=${item.billNo}">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 3}">
									<a onclick="" href="../tcp/apply.do?method=findTravelApply&id=${item.billNo}">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 4}">
									<a onclick="" href="../tcp/expense.do?method=findExpense&id=${item.billNo}">${item.billNo}</a>
									</c:if>
									<c:if test="${item.billType == 5}">
									<a onclick="" href="../tcp/backprepay.do?method=findBackPrePay&id=${item.billNo}">${item.billNo}</a>
									</c:if>
								</td>
								<td align="center">
									<c:if test="${item.billType == 1}">采购付款</c:if>
									<c:if test="${item.billType == 2}">采购预付款</c:if>
									<c:if test="${item.billType == 3}">借款申请付款</c:if>
									<c:if test="${item.billType == 4}">报销申请付款</c:if>
									<c:if test="${item.billType == 5}">预收退款</c:if>
								</td>
								<td align="center">${item.logTime}</td>
								<td align="center">${my:formatNum(item.payeeAmount)}</td>
								<td align="center">${item.payeeBank}</td>
								<td align="center">${item.payeeBankAccName}</td>
								<td align="center">${item.payeeBankAcc}</td>
								<td align="center">${item.billStatus}</td>
								<td align="center">${item.description}</td>
								<c:if test="${hasAuth == 1}">
									<td align="center"><input type="button" onclick="change('${item.billNo}','${item.billNo}')" class="button_class" value="&nbsp;&nbsp;附件&nbsp;&nbsp;"/></td>
								</c:if>
							</tr>
						</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</tr>
</table>
</form>
</body>
</html>