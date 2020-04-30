<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="打印客户出库" link="false" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<link href="../css/self.css" type=text/css rel=stylesheet>
<script language="javascript">
function pagePrint()
{
	document.getElementById('ptr').style.display = 'none';
	window.print();
	document.getElementById('ptr').style.display = 'block';

	if ($$('printMode') == 0)
	{
		var pickupId = $O('pickupId').value;
		var index_pos = $O('index_pos').value;
		var printSmode = $O('printSmode').value;
		var compose = $O('compose').value;
		
		// 链到客户出库单打印界面
		$l("../sail/ship.do?method=findNextPackage&printMode=0&printSmode="+printSmode+"&pickupId="+pickupId+"&index_pos="+index_pos + "&compose=" + compose);
	}
}

</script>
	<style type="text/css">
		.underline {text-decoration: underline}	
	</style>
</head>
<body>
<input type="hidden" name="pickupId" value="${bean.pickupId}">
<input type="hidden" name="index_pos" value="${index_pos}">
<input type="hidden" name="printMode" value="${printMode}">
<input type="hidden" name="printSmode" value="${printSmode}">
<input type="hidden" name="compose" value="${compose}">

<div id="ck" style="page-break-after: always;">
<table width="100%" border="0" cellpadding="0" cellspacing="0" id="na">
	<tr>
		<td height="6" >
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td align="center">
				<font size="6"><b>
				</b>
				</font></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table width="90%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>

			</tr>
			<tr>
				<td align="center">
				<table width="100%" border="0" cellspacing="2">
					<tr>
						<td style="height: 27px" align="center">
                            <font size=5>
								${customerName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                <b>
									客&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;出&nbsp;&nbsp;&nbsp;库&nbsp;&nbsp;&nbsp;单${tw}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<c:if test="${bean.emergency == 1}">紧急订单</c:if>&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${bean.direct == 1}">直邮</c:if>
                                    <img src="${qrcode}"/>
                                </b>
                            </font>
                        </td>
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
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"   class="border_table">
					<tr class="content2">
						<td colspan="4" class="border_cell"><table><tr><td>编号：${bean.pickupId}--${bean.index_pos} / ${bean.id}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td class="border_cell"><table><tr><td>制单时间：${year} / ${month} / ${day}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>申请人：${bean.stafferName}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>事业部：${bean.industryName}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>销售部门：${bean.departName}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2" class="border_cell"><table><tr><td>收货人：${bean.receiver}</td></tr></table></td>
						<td colspan="2" class="border_cell"><table><tr><td>联系电话：${bean.mobile}</td></tr></table></td>
					</tr>					
					<tr class="content2">
						<td colspan="4" class="border_cell"><table><tr><td>发货地址：${bean.address}</td></tr></table></td>
					</tr>
					
					<tr class="content2">
						<td class="border_cell"><table><tr><td>发货方式：${my:get('outShipment',bean.shipping)}</td></tr></table></td>
						<td colspan="2" class="border_cell"><table><tr><td>发货公司：${bean.transportName1}&nbsp;${bean.transportName2}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>支付方式：${my:get('deliverPay',bean.expressPay)}&nbsp;${my:get('deliverPay',bean.transportPay)}</td></tr></table></td>
					</tr>					
					
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border2">
					<tr class="content2">
						<td width="8%" class="border_cell"><table><tr><td align="center">序号</td></tr></table></td>
						<td width="15%" class="border_cell"><table><tr><td align="center">单号</td></tr></table></td>
						<td width="15%" class="border_cell"><table><tr><td align="center">银行订单号</td></tr></table></td>
						<td width="40%" class="border_cell"><table><tr><td align="center">销售备注</td></tr></table></td>
					</tr>
					
					<c:forEach items="${bean.wrapList}" var="item1" varStatus="vs1">
					<tr class="content2">
						<td class="border_cell"><table><tr><td align="center">${vs1.index + 1}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item1.outId}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item1.citicNo}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item1.description}</td></tr></table></td>
					</tr>
					</c:forEach>
					
				</table>
				</td>
			</tr>			
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border2">
					<tr class="content2">
						<td width="5%" class="border_cell"><table><tr><td align="center">序号</td></tr></table></td>
						<td width="30%" class="border_cell"><table><tr><td align="center">品名</td></tr></table></td>
						<td width="52%" class="border_cell"><table><tr><td align="center">配件名</td></tr></table></td>
						<td width="8%" class="border_cell"><table><tr><td align="center">发货数量</td></tr></table></td>
					</tr>
					
					<c:forEach items="${bean.itemList}" var="item" varStatus="vs">
					<tr class="content2">
						<td class="border_cell"><table><tr><td align="center">${vs.index + 1}</td></tr></table></td>
						<td class="border_cell"><table><tr><td><p style='font-weight: bold;font-size:18px;'>${item.productName}</p></td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item.showSubProductName}</td></tr></table></td>
						<td class="border_cell"><table><tr><td align="center"><p style='font-weight: bold;font-size:18px;'>${item.amount}</p></td></tr></table></td>
					</tr>
					</c:forEach>
					
					<c:forEach varStatus="vs" begin="1" end="${(2 - my:length(baseList)) > 0 ? (2 - my:length(baseList)) : 0}">
					<tr class="content2">
						<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
						<td class="border_cell"><table><tr><td></td></tr></table></td>
						<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
						<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
					</tr>
					</c:forEach>
				</table>
				</td>
			</tr>
			
			<tr>
				<td height="15"></td>
			</tr>


			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0">
					<tr>
						<td colspan="2" align="left">
						备货人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td colspan="2" align="left">发货人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>

					<tr>
						<td colspan="4" align="left">
						检验人员：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>

		</td>
	</tr>
	
	<tr id="ptr">
		<td width="92%">
		<div align="right"><input type="button" name="pr"
			class="button_class" onclick="pagePrint()"
			value="&nbsp;&nbsp;打 印&nbsp;&nbsp;"></div>
		</td>
	</tr>
</table>
</div>


<table>
	<div id="dgns">
		<tr>
			<td colspan='2'>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
					</tr>
					<tr>
						<td align="center">
							<table width="100%" border="0" cellspacing="2">
								<tr>
									<td align="left" style="height: 27px"></td>
									<td align="right"></td>
								</tr>
								<tr>
									<td style="height: 27px" align="center" colspan="2">
										<font size=5>
											<b>
												实物贵金属库存管理需求表<br>
												（代销供应商专用）
											</b>
										</font>
									</td>
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
			<td colspan='2' align='center'>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" cellspacing='0' cellpadding="0">
								<tr class="content2">
									<td colspan="4" width="100%"><table><tr><td>调出机构：永银文化</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4" width="100%"><table><tr><td>接收机构：${yjzh}</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4" width="100%"><table><tr><td>库存管理操作：<span class="underline">入库</span>(入库/下放/上缴/出库)</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4" width="100%"><table><tr><td>交接方式：<span class="underline">快递寄送</span>(临柜交接/现金押运调拨/快递寄送)</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="2" width="50%"><table><tr><td>交接委托说明：（临柜交接需填写）</td></tr></table></td>
									<td colspan="2" width="50%"><table><tr><td></td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4" width="100%"><table><tr><td>&nbsp;&nbsp;&nbsp;&nbsp;我公司委托<span class="underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										(身份证件号码<span class="underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										联系方式<span class="underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>)和
										<span class="underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										(身份证件号码<span class="underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
										联系方式<span class="underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>)
										两名人员到（机构）<span class="underline">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>办理下述产品或包装盒的库存调动。</td></tr></table></td>
								</tr>
							</table>
						</td>
					</tr>

					<tr>
						<td>
							<table width="100%" cellspacing='0' cellpadding="0"   class="border_table">
								<tr class="content2">
									<td width="16%" class="border_cell"><table><tr><td align="center">代码</td></tr></table></td>
									<td width="24%" class="border_cell"><table><tr><td align="center">产品名称</td></tr></table></td>
									<td width="8%" class="border_cell"><table><tr><td align="center">类型</td></tr></table></td>
									<td width="8%" class="border_cell"><table><tr><td align="center">供应商</td></tr></table></td>
									<td width="16%" class="border_cell"><table><tr><td align="center">库存状态</td></tr></table></td>
									<td width="16%" class="border_cell"><table><tr><td align="center">调动数量</td></tr></table></td>
								</tr>

								<c:forEach items="${bean.dgnsItemList}" var="item" varStatus="vs">
									<tr class="content2">
										<td class="border_cell"><table><tr><td align="center">${item.productCode}</td></tr></table></td>
										<td class="border_cell"><table><tr><td>${item.productName}</td></tr></table></td>
										<td class="border_cell"><table><tr><td>代销产品</td></tr></table></td>
										<td class="border_cell"><table><tr><td>永银文化</td></tr></table></td>
										<td class="border_cell"><table><tr><td>待提</td></tr></table></td>
										<td class="border_cell"><table><tr><td align="center">${item.amount}</td></tr></table></td>
									</tr>
								</c:forEach>

								<c:forEach varStatus="vs" begin="1" end="${(2 - my:length(vo.itemList)) > 0 ? (2 - my:length(vo.itemList)) : 0}">
									<tr class="content2">
										<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
										<td class="border_cell"><table><tr><td></td></tr></table></td>
										<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
										<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
										<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
										<td class="border_cell"><table><tr><td align="center"></td></tr></table></td>
									</tr>
								</c:forEach>
							</table>
						</td>
					</tr>

					<tr>
						<td height="20"></td>
					</tr>

					<tr>
						<td>
							<table width="100%" cellspacing='0' cellpadding="0">
								<tr class="content2">
									<td colspan="3" width="50%"><table><tr><td>调出机构（签章）：</td></tr></table></td>
									<td width="50%"><table><tr><td>接收机构（签章）：</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="3" width="50%"><table><tr><td>经办人1：</td></tr></table></td>
									<td width="50%"><table><tr><td>经办人1：</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="3" width="50%"><table><tr><td>经办人2：</td></tr></table></td>
									<td width="50%"><table><tr><td>经办人2：</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="3" width="50%"><table><tr><td>日期：${year}年${month}月${day}日</td></tr></table></td>
									<td width="50%"><table><tr><td>日期：</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr><td>填表说明：</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr><td>1.此表一式两联，调出机构和接收机构各存一联。</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr><td>2.名称指产品名称，需填写全称；</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr><td>3.类型可填写：代销产品；</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr><td>4.供应商可填写产品的供货商，如金叶珠宝、南方裕银、百泰国礼、国富黄金、永银文化等；</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr><td>5.库存状态可选“可售”、“待提”，待提状态用于柜面预付款模式的产品调动；</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr><td>6.调动数量指本栏产品本次申请库存调动的数量；</td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="3"><table><tr><td></td></tr></table></td>
									<td><table><tr><td></td></tr></table></td>
								</tr>
								<tr class="content2">
									<td colspan="4"><table><tr>
										<td>
										</td>
									</tr></table></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</div>
</table>
</body>
</html>
