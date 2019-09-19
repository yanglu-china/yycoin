<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="上传银行回单附件" />
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">
function upload()
{
	var fileName = $O('myFile').value;
    if ("" == fileName)
    {
        alert("请选择上传的文件");
        return false;
    }
   	submit('上传附件？', null, null);
}
function show(url)
{
	 window.common.modal(url,'');
}
function retquery()
{
	$("#retform").submit();
}
var delArr = new Array();
function del(attaId)
{
	delArr.push(attaId);
	$("#delAttaId").val(delArr.join(","));
	$("#span_" +attaId).hide();
}
function load()
{
	loadForm();
}
</script>

</head>
<body class="body_class" onload="load()">
<form name="adminForm" action="../payorder/queryPayOrder.do?method=uploadAttachement" enctype="multipart/form-data" method="post">
<input type="hidden" value="${outid}" name="outid" id="outid"/>
<input type="hidden" value="${outbillid}" name="outbillid" id="outbillid"/>
<input type="hidden" value="" name="delAttaId" id="delAttaId"/>
<p:navigation
	height="22">
	<td width="550" class="navigation"><span>上传银行回单</span></td>
	<td width="85"></td>
</p:navigation> <br>
<table width="98%" border="0" cellpadding="0" cellspacing="0" align="center">
	<c:forEach items="${payOrderLogVoList}" var="item" varStatus="vs">
	<tr>
		<td align='center' colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border">
			<tr>
				<td>
					<table width="100%" border="0" cellspacing='1' id="mainTable">
						<tbody id="tbdata">
						
							<tr class='content1'>
								<td align="center" class="td_class">单据号</td>
								<td align="left">
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
							</tr>
							<tr class='content1'>
								<td align="center" class="td_class">单据类型</td>
								<td align="left">
									<c:if test="${item.billType == 1}">
										采购预付款
									</c:if>
									<c:if test="${item.billType == 2}">
										采购付款
									</c:if>
									<c:if test="${item.billType == 3}">
										借款申请付款
									</c:if>
									<c:if test="${item.billType == 4}">
										报销申请付款
									</c:if>
									<c:if test="${item.billType == 5}">
										预收退款
									</c:if>
								</td>
							</tr>
							<tr class='content1'>
								<td align="center" class="td_class">单据日期</td>
								<td align="left">${item.logTime}</td>
							</tr>
							<tr class='content1'>
								<td align="center" class="td_class">收款金额(元)</td>
								<td align="left">${my:formatNum(item.payeeAmount)}</td>
							</tr>
							<tr class='content1'>
								<td align="center" class="td_class">收款银行</td>
								<td align="left">${item.payeeBank}</td>
							</tr>
							<tr class='content1'>
								<td align="center" class="td_class">收款户名</td>
								<td align="left">${item.payeeBankAccName}</td>
							</tr>
							<tr class='content1'>
								<td align="center" class="td_class">收款帐号</td>
								<td align="left">${item.payeeBankAcc}</td>
							</tr>
							<tr class='content1'>
								<td align="center">上传附件</td>
								<td>
									<input type="file" name="myFile" id="myFile" size="200" class="button_class"><font color="blue"><b>建议压缩后上传,最大支持20M</b></font>
								</td>
							</tr>
							<tr class='content1'>
								<td align="center" class="td_class">原附件</td>   
								<td align="left">
									<c:forEach items="${attachmentList}" var="attachitem" varStatus="vs">
							            <span id="span_${attachitem.id}">
							            	<img src="../images/oa/attachment.gif"/>
							            	<a target="_blank" href="../payorder/queryPayOrder.do?method=downAttachmentFile&id=${attachitem.id}">${attachitem.name}</a>
							            	<img src="../images/oa/del.gif" border="0" height="15" width="15" onclick="javascript:del('${attachitem.id}')">
							            </span>
							            &nbsp;&nbsp;&nbsp;&nbsp;
							            <c:if test="${!vs.last}">
							            <br>
							            </c:if>
						            </c:forEach>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	</c:forEach>
	<tr class="content1">
		<td colspan="4" align="center">
			<input type="button" id="query_b" onclick="upload()" class="button_class" value="&nbsp;&nbsp;上传&nbsp;&nbsp;"/>&nbsp;&nbsp;
			<input type="button" onclick="retquery()" class="button_class" value="&nbsp;&nbsp;返回&nbsp;&nbsp;"/>
		</td>
	</tr>
</table>
<p:message2 />
</form>
<form id="retform" name="retform" action="../payorder/queryPayOrder.do">
<input type="hidden" value="queryAttachement" name="method"/>
<input type="hidden" value="3" name="payOrderStatus"/>
</form>
</body>
</html>

