<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="集中付款管理" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var payOrderType ='${queryMap.payOrderType}';
	var payOrderStatus = '${payOrderStatus}';
	if(payOrderType != 4)
	{
		$("#trdatetype").attr("style","display:none");
	}
	else
	{
		if(payOrderStatus == 1)
		{
			$("#trdatetype").attr("style","display:blocked");
		}
		else
		{
			$("#trdatetype").attr("style","display:none");
		}
	}
});

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
	$("#trdatetype").attr("style","display:none");
	$O('approveName').value = '';
	$('#datetype').val(0);
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
	window.location.href='../payorder/queryPayOrder.do?method=modifyPayOrder&outid=' + outid +"&outbillid=" + outbillid;
}
function changeselect(sel)
{
	var paystatus = $("#payOrderStatus").val();
	if(sel.value==4)
	{
		if(paystatus == 1)
		{
			$("#trdatetype").attr("style","display:blocked");
		}
	}
	else
	{
		$("#trdatetype").attr("style","display:none");
	}
}
function changestatus(sel)
{
	var payOrderType = $("#payOrderType").val();
	if(sel.value == 1)
	{
		if(payOrderType == 4)
		{
			$("#trdatetype").attr("style","display:blocked");
		}
	}
	else
	{
		$("#trdatetype").attr("style","display:none");
	}
}
</script>
</head>
<body>
<p:navigation height="22">
    <td width="550" class="navigation">资金管理 &gt;&gt; 集中付款</td>
    <td width="85"></td>
</p:navigation>
<form action="../payorder/queryPayOrder.do" id="adminForm">
<input type="hidden" value="queryPayOrder" name="method"></input>
<input type="hidden" value="" name="billNoArrString" id="billNoArrString"></input>

<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center">
	<tr class="content1">
        <td style="width: 15%" align="center">单据类型:</td>
        <td align="center" style="width: 35%">
        	<select name="payOrderType" id="payOrderType" class="select_class" values="${queryMap.payOrderType}" onchange="changeselect(this)">
				<option value="">--</option>
				<option value="1">采购付款</option>
				<!-- <option value="2">采购预付款</option> -->
				<option value="3">借款申请付款</option>
				<option value="4">报销申请付款</option>
				<option value="5">预收退款</option>
			</select>
        </td>
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
	<tr class="content2">
		<td width="15%" align="center">单据状态:</td>
        <td align="center">
	         <select name="payOrderStatus" id="payOrderStatus" class="select_class" values="${queryMap.payOrderStatus}" onchange="changestatus(this)">
	              <option value="1">待付款</option>
	              <option value="2">待确认</option>
	              <option value="3">已付款</option>
	              <option value="4">未成功待付款</option>
	         </select>
         </td>
	</tr>
	<tr class="content1" id="trdatetype">
		<td style="width: 15%;" align="center">日期类型:</td>
        <td align="center" style="width: 35%;">
        	<select name="datetype" id="datetype" class="select_class" values="${queryMap.datetype}" onchange="changedatetype(this)">
				<option value="1">单据日期</option>
				<option value="2">财务审批日期</option>
			</select>
        </td>
		<td width="15%" align="center">财务审批人:</td>
		<td align="center" colspan="1"><input type="text" name="approveName" type="0" size="20" value="${queryMap.approveName }"/></td>
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
				<td align="center">
				<c:if test="${payOrderStatus == 1}">
					<input type="button" id="todopay" onclick="dopay()" class="button_class" value="&nbsp;&nbsp;提取付款单&nbsp;&nbsp;"/>
				</c:if>
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
						<c:if test="${payOrderStatus == 1}">
							<td align="center" width="5%" align="center"><input type="checkbox" name="chall" id="ckall" onclick="checkall(this)"></input></td>
						</c:if>
							<td align="center" onclick="tableSort(this)" class="td_class">单据号</td>
							<td align="center" onclick="tableSort(this)" class="td_class">单据类型</td>
							<c:choose>
								<c:when test="${queryMap.datetype == 2}">
								<td align="center" onclick="tableSort(this)" class="td_class">财务审批日期</td>
								<td align="center" onclick="tableSort(this)" class="td_class">财务审批人</td>
								</c:when>
								<c:otherwise>
									<td align="center" onclick="tableSort(this)" class="td_class">单据日期</td>
								</c:otherwise>
							</c:choose>
							
							<td align="center" onclick="tableSort(this)" class="td_class">收款金额(元)</td>
							<td align="center" onclick="tableSort(this)" class="td_class">收款银行</td>
							<td align="center" onclick="tableSort(this)" class="td_class">收款户名</td>
							<td align="center" onclick="tableSort(this)" class="td_class">收款帐号</td>
							<td align="center" onclick="tableSort(this)" class="td_class">单据状态</td>
							<td align="center" onclick="tableSort(this)" class="td_class">付款备注</td>
							<c:if test="${payOrderStatus != 1}">
							<td align="center" onclick="tableSort(this)" class="td_class">付款结果</td>
							</c:if>
							<c:if test="${payOrderStatus == 4}">
							<td align="center" onclick="tableSort(this)" class="td_class">操作</td>
							</c:if>
						</tr>
						<tbody id="tbdata">
						<c:if test="${payOrderStatus == 1}">
							<c:forEach items="${payOrderList}" var="item" varStatus="vs">
								<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
									<td align="center"><input type="checkbox" name="ckbillid" onclick="singleck(this)" value="${item.billNo}_${item.billType}"/></td>
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
									<td align="center">${item.billTypeDesc}
									</td>
									<td align="center">${item.billDate}</td>
									<c:if test="${not empty item.approveName}">
									<td align="center">${item.approveName}</td>
									</c:if>
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
						</c:if>
							<c:if test="${payOrderStatus != 1}">
								<c:forEach items="${payOrderLogList}" var="item" varStatus="vs">
									<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
										
										<td align="center">
											<c:if test="${item.type == 1}">
											<a onclick="" href="../finance/stock.do?method=findStockPayApply&id=${item.outid}">${item.outid}</a>
											</c:if>
											<c:if test="${item.type == 2}">
											<a onclick="" href="../finance/stock.do?method=findStockPrePayApply&id=${item.outid}">${item.outid}</a>
											</c:if>
											<c:if test="${item.type == 3}">
											<a onclick="" href="../tcp/apply.do?method=findTravelApply&id=${item.outid}">${item.outid}</a>
											</c:if>
											<c:if test="${item.type == 4}">
											<a onclick="" href="../tcp/expense.do?method=findExpense&id=${item.outid}">${item.outid}</a>
											</c:if>
											<c:if test="${item.type == 5}">
											<a onclick="" href="../tcp/backprepay.do?method=findBackPrePay&id=${item.outid}">${item.outid}</a>
											</c:if>
										</td>
										<td align="center">
											<c:if test="${item.type == 1}">采购付款</c:if>
											<c:if test="${item.type == 2}">采购预付款</c:if>
											<c:if test="${item.type == 3}">借款申请付款</c:if>
											<c:if test="${item.type == 4}">报销申请付款</c:if>
											<c:if test="${item.type == 5}">预收退款</c:if>
										</td>
										<td align="center">${item.outidtime}</td>
										<td align="center">${my:formatNum(item.money)}</td>
										<td align="center">${item.bankName}</td>
										<td align="center">${item.userName}</td>
										<td align="center">${item.bankNo}</td>
										<c:if test="${item.status == 2}">
										<td align="center">待确认</td>
										</c:if>
										<c:if test="${item.status == 3}">
										<td align="center">已付款</td>
										</c:if>
										<c:if test="${item.status == 4}">
										<td align="center">未成功待付款</td>
										</c:if>
										<td align="center">${item.description}</td>
										<td align="center">${item.message}</td>
										<c:if test="${payOrderStatus == 4}">
											<c:if test="${hasAuth == 1}">
												<td align="center"><input type="button" onclick="change('${item.outid}','${item.outbillid}')" class="button_class" value="&nbsp;&nbsp;修改&nbsp;&nbsp;"/></td>
											</c:if>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>
						
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>

</form>
<form id="topayform" action="../payorder/queryPayOrder.do">
<input type="hidden" value="toPayOrder" name="method"/>
<input type="hidden" value="" name="billNoString" id="billNoString"/>
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