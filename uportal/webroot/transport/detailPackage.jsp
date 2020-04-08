<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="出库单明细" guid="true" dialog="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/plugin/highlight/jquery.highlight.js"></script>
<script language="javascript">

function load()
{
    loadForm();
    
    //loadShow(true);
}


</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/ship.do">
<div id="na">
<p:navigation
	height="22">
	<td width="550" class="navigation">出库单明细</td>
				<td width="85"></td>
</p:navigation> <br>
</div>

<table width="98%" border="0" cellpadding="0" cellspacing="0" id="viewTable"
	align="center">
	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" id="mainTable"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%" align="right">出库单：</td>
						<td width="35%">${bean.id}</td>
						<td width="15%" align="right">批次号：</td>
						<td width="35%">${bean.pickupId}</td>
					</tr>

					<tr class="content1">
						<td width="15%" align="right">事业部：</td>
						<td width="35%">${bean.industryName}</td>
						<td width="15%" align="right">客户：</td>
						<td width="35%">${bean.customerName}</td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="right">状态：</td>
						<td width="35%">${my:get('shipStatus',bean.status)}</td>					
						<td width="15%" align="right">发货方式：</td>
						<td width="35%">${my:get('outShipment',bean.shipping)}</td>
					</tr>

					<tr class="content2">
						<td width="15%" align="right">运输公司：</td>
						<td width="35%">${bean.transportName1}/${bean.transportName2}</td>
						<td width="15%" align="right">支付方式：</td>
						<td width="35%">${my:get('deliverPay',bean.expressPay)}//${my:get('deliverPay',bean.transportPay)}</td>
					</tr>
					
					<tr class="content1">
						<td width="15%" align="right">发货单号：</td>
						<td width="35%">${bean.transportNo}</td>
						<td width="15%" align="right">地址：</td>
						<td width="35%" colspan="3">${bean.address}</td>
                    </tr>
					
					<tr class="content2">
						<td width="15%" align="right">收货人：</td>
						<td width="35%">${bean.receiver}</td>
						<td width="15%" align="right">收货电话/固定电话：</td>
						<td width="35%">${bean.mobile}/${bean.telephone}</td>
                    </tr>					

					<tr class="content1">
						<td width="15%" align="right">数量：</td>
						<td width="35%">${bean.amount}</td>
						<td width="15%" align="right">金额：</td>
						<td width="35%">${my:formatNum(bean.total)}</td>
                    </tr>

					<tr class="content2">
						<td width="15%" align="right" >发货类型：</td>
						<td width="35%" colspan="3">${my:get('preShipType',bean.type)}</td>
					</tr>

				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
        <td colspan='2' align='center'>
        <div id="desc1" style="display: block;">
        <table width="100%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">销售单</td>
                        <td width="25%" align="center">产品</td>
                        <td width="10%" align="center">数量</td>
                    </tr>

                    <c:forEach items="${bean.itemList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
	                        <c:if test="${fn:substring(item.outId, 0, 1)== 'A'}">
	                            <td align="center"><a href="../finance/invoiceins.do?method=findInvoiceins&id=${item.outId}">${item.outId}</a></td>
	                        </c:if>
                            <c:if test="${fn:substring(item.outId, 0, 2)== 'FP'}">
                                <td align="center"><a href="../tcp/preinvoice.do?method=findPreInvoice&id=${item.outId}">${item.outId}</a></td>
                            </c:if>
	                        <c:if test="${fn:substring(item.outId, 0, 1)!= 'A' && fn:substring(item.outId, 0, 2)!= 'FP'}">
	                            <td align="center"><a href="../sail/out.do?method=findOut&flow=99&outId=${item.outId}">${item.outId}</a></td>
	                        </c:if>

                            <td  align="center">${item.productName}</td>

                            <td  align="center">${item.amount}</td>

                        </tr>
                    </c:forEach>
                    
                </table>
                </td>
            </tr>
        </table>
        </div>
        </td>
    </tr>
    
    <tr>
        <td height="10" colspan='2'></td>
    </tr>

    <tr>
        <td background="../images/dot_line.gif" colspan='2'></td>
    </tr>

    <tr>
        <td height="10" colspan='2'></td>
    </tr>


	<tr>
		<td colspan='2' align='center'>
			<div style="display: block;">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					   class="border">
					<tr>
						<td>
							<table width="100%" border="0" cellspacing='1'>
								<tr align="center" class="content0">
									<td width="10%" align="center">审批人</td>
									<td width="10%" align="center">前状态</td>
									<td width="10%" align="center">后状态</td>
									<td width="15%" align="center">备注</td>
									<td width="15%" align="center">时间</td>
								</tr>

								<c:forEach items="${logList}" var="item" varStatus="vs">
									<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
										<td align="center">${item.actor}</td>

										<td  align="center">${item.preStatusName}</td>

										<td  align="center">${item.afterStatusName}</td>

										<td  align="center">${item.description}</td>

										<td  align="center">${item.logTime}</td>

									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>


	<tr>
        <td width="100%">
        <div align="right">
        
            <input
            type="button" name="ba" class="button_class"
            onclick="javascript:history.go(-1)"
            value="&nbsp;&nbsp;返 回&nbsp;&nbsp;"></div>
        </td>
        <td width="0%"></td>
    </tr>

</table>
</form>
</body>
</html>

