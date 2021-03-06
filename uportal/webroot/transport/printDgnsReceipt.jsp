<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
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

	var pickupId = $O('pickupId').value;
	var index_pos = $O('index_pos').value;

	var packageId = $O('packageId').value;
	var subindex_pos = $O('subindex_pos').value;

	var compose = $O('compose').value;
    var batchPrint = $O('batchPrint').value;
    var directFlag = $O('directFlag').value;
//    console.log("directFlag:"+directFlag);
//    console.log("batchPrint:"+batchPrint);
//    console.log("allPackages:"+$$('allPackages'));
//    console.log("index_pos:"+index_pos);

    //连打模式下，并且回执单都已经打印完毕就跳转到交接单打印
//    if ($$('allPackages') == index_pos && batchPrint == '0' && $$('stafferName') == '叶百韬')
    //2015/3/26 最后打印的回执单可能不是叶百韬的单子，这个判断到打印交接单时做
    if ($$('allPackages') == index_pos && batchPrint == '0' && directFlag != '1')
    {
        var pickupId = $O('pickupId').value;
        var index_pos = $O('index_pos').value;
        var compose = $O('compose').value;

        // 链到交接清单打印界面
        $l("../sail/ship.do?method=printHandover&pickupId="+pickupId+"&index_pos=0" + "&compose=" + compose);
    } else{
		if ((!pickupId || 0 === pickupId.length)){
			alert("已打印!");
		}else{
//            $l("../sail/ship.do?method=findOutForReceipt&pickupId="
//                    +pickupId+"&index_pos="+index_pos +"&packageId=" + packageId + "&subindex_pos=" + subindex_pos
//                    + "&compose=" + compose+ "&batchPrint=" + batchPrint);


//            console.log(window.location.href);
            if (directFlag === '1'){
//                console.log("OK***");
				var url = window.location.href+"&directFlag="+directFlag;
				$l(url);
//                $l("../sail/ship.do?method=findOutForReceipt&pickupId="
//                    +pickupId+"&index_pos="+index_pos +"&packageId=" + packageId + "&subindex_pos=" + subindex_pos
//                    + "&compose=" + compose+ "&batchPrint=" + batchPrint+"&directFlag="+directFlag);
			} else{
                // 链到客户出库单打印界面
                $l("../sail/ship.do?method=findOutForReceipt&pickupId="
                    +pickupId+"&index_pos="+index_pos +"&packageId=" + packageId + "&subindex_pos=" + subindex_pos
                    + "&compose=" + compose+ "&batchPrint=" + batchPrint);
			}
		}
    }
}

function callBackPrintFun()
{
	//
}

</script>
	<style type="text/css">
		.underline {text-decoration: underline}
	</style>
</head>
<body>
<input type="hidden" name="pickupId" value="${pickupId}">
<input type="hidden" name="index_pos" value="${index_pos}">
<input type="hidden" name="packageId" value="${packageId}">
<input type="hidden" name="subindex_pos" value="${subindex_pos}">
<input type="hidden" name="compose" value="${compose}">
<input type="hidden" name="batchPrint" value="${batchPrint}">
<input type="hidden" name="allPackages" value="${allPackages}">
<input type="hidden" name="printMode" value="${printMode}">
<input type="hidden" name="printSmode" value="${printSmode}">
<input type="hidden" name="stafferName" value="${stafferName}">
<input type="hidden" name="directFlag" value="${directFlag}">
<div id="ck" style="page-break-after: always;">
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
                            <td align="left" style="height: 27px"><img src="${qrcode}"/></td>
                            <td align="right"><img src="${qrcode}"/></td>
                        </tr>
                        <tr>
                            <td style="height: 27px" align="center" colspan="2">
                                <font size=5>
                                    <b>
                            ${title}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${bean.emergency == 1}">紧急订单</c:if>&nbsp;&nbsp;&nbsp;&nbsp;<c:if test="${bean.direct == 1}">直邮</c:if>
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
						<td colspan="2" width="50%"><table><tr><td>出库时间：${year}-${month}-${day}</td></tr></table></td>
						<td colspan="2" width="50%"><table><tr><td>收货客户：${bean.customerName}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2" width="50%"><table><tr><td>业务联系：${stafferName}</td></tr></table></td>
						<td colspan="2" width="50%"><table><tr><td>客服电话：4006518859</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2" width="50%"><table><tr><td>业务电话：${phone}</td></tr></table></td>
						<td colspan="2" width="50%"><table><tr><td>客服传真：025-51885923</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2" width="50%"><table><tr><td>收货人员：${bean.receiver}</td></tr></table></td>
						<td colspan="2" width="50%"><table><tr><td>联系电话：${bean.mobile}</td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class=""border_table"">
					<tr class="content2">
						<td width="24%" class="border_cell"><table><tr><td align="center">产品名称</td></tr></table></td>
                        <td width="16%" class="border_cell"><table><tr><td align="center">代码</td></tr></table></td>
						<td width="8%" class="border_cell"><table><tr><td align="center">数量</td></tr></table></td>
						<td width="8%" class="border_cell"><table><tr><td align="center">订单性质</td></tr></table></td>
						<td width="16%" class="border_cell"><table><tr><td align="center">客户单号</td></tr></table></td>
						<td width="16%" class="border_cell"><table><tr><td align="center">订单日期</td></tr></table></td>
                        <td width="8%" class="border_cell"><table><tr><td align="center">顾客姓名</td></tr></table></td>
						<td width="12%" class="border_cell"><table><tr><td align="center">客户备注</td></tr></table></td>
					</tr>
					
					<c:forEach items="${bean.itemList}" var="item" varStatus="vs">
					<tr class="content2">
						<td class="border_cell"><table><tr><td>${item.productName}</td></tr></table></td>
                        <td class="border_cell"><table><tr><td align="center">${item.productCode}</td></tr></table></td>
						<td class="border_cell"><table><tr><td align="center">${item.amount}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item.itemType}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item.refId}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item.poDate}</td></tr></table></td>
                        <td class="border_cell"><table><tr><td>${item.customerName}</td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item.customerDescription}</td></tr></table></td>
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
						<td colspan="3" width="50%"><table><tr><td>发货单位盖章：</td></tr></table></td>
						<td width="50%"><table><tr><td>收货人签字确认：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>${address}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>备注：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>1.此发货清单仅作为收货确认使用。请依据本清单清点货品，请特别留意：如有发票，发票与发货清单一起放置在透明塑封袋中。</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>2.约定有配套赠品的，本次如未配送，将随后补发，请留意收货。</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>3.如遇产品质量问题，经确认需要换货的，请留存照片等说明情况的依据，在收货后三日内联系我方人员，提供需换货的订单号及将换货寄回的快递单号，需换货的货品，烦请保留所有配件完整，原样（含发票）完成打包后寄回。</td></tr></table></td>
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
							<table width="100%" cellspacing='0' cellpadding="0"  class="border_table">
								<tr class="content2">
									<td width="16%" class="border_cell"><table><tr><td align="center">代码</td></tr></table></td>
									<td width="24%" class="border_cell"><table><tr><td align="center">产品名称</td></tr></table></td>
									<td width="8%" class="border_cell"><table><tr><td align="center">类型</td></tr></table></td>
									<td width="8%" class="border_cell"><table><tr><td align="center">供应商</td></tr></table></td>
									<td width="16%" class="border_cell"><table><tr><td align="center">库存状态</td></tr></table></td>
									<td width="16%" class="border_cell"><table><tr><td align="center">调动数量</td></tr></table></td>
								</tr>

								<c:forEach items="${bean.itemList}" var="item" varStatus="vs">
									<tr class="content2">
										<td class="border_cell"><table><tr><td align="center">${item.productCode}</td></tr></table></td>
										<td class="border_cell"><table><tr><td>${item.productName}</td></tr></table></td>
										<td class="border_cell"><table><tr><td>代销产品</td></tr></table></td>
										<td class="border_cell"><table><tr><td>永银文化</td></tr></table></td>
										<td class="border_cell"><table><tr><td>代提</td></tr></table></td>
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
