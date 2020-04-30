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
						<td colspan="2" width="50%"><table><tr><td>招行供应商：${bean.customerName}</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2" width="50%"><table><tr><td>业务电话：4006518859</td></tr></table></td>
						<td colspan="2" width="50%"><table><tr><td></td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="2" width="50%"><table><tr><td>收货人：${bean.receiver}</td></tr></table></td>
						<td colspan="2" width="50%"><table><tr><td>联系电话：${bean.mobile}</td></tr></table></td>
					</tr>
				</table>
				</td>
			</tr>
			
			<tr>
				<td>
				<table width="100%" cellspacing='0' cellpadding="0"  class="border_table">
					<tr class="content2">
						<td width="24%" class="border_cell"><table><tr><td align="center">产品名称</td></tr></table></td>
                        <td width="16%" class="border_cell"><table><tr><td align="center">代码</td></tr></table></td>
						<td width="8%" class="border_cell"><table><tr><td align="center">数量</td></tr></table></td>
						<td width="8%" class="border_cell"><table><tr><td align="center">订单性质</td></tr></table></td>
						<td width="16%" class="border_cell"><table><tr><td align="center">订单时间</td></tr></table></td>
						<td width="12%" class="border_cell"><table><tr><td align="center">分行名称</td></tr></table></td>
					</tr>
					
					<c:forEach items="${bean.itemList}" var="item" varStatus="vs">
					<tr class="content2">
						<td class="border_cell"><table><tr><td>${item.productName}</td></tr></table></td>
                        <td class="border_cell"><table><tr><td align="center">${item.productCode}</td></tr></table></td>
						<td class="border_cell"><table><tr><td align="center">${item.amount}</td></tr></table></td>
						<td class="border_cell"><table><tr><td></td></tr></table></td>
						<td class="border_cell"><table><tr><td>${item.description}${item.printText}</td></tr></table></td>
                        <td class="border_cell"><table><tr><td>${branchName}</td></tr></table></td>
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
						<td colspan="3" width="50%"><table><tr><td>发货单位盖章：</td></tr></table></td>
						<td width="50%"><table><tr><td>收货人签字确认：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>备注：</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>1.本产品由“上海金币投资有限公司”供应，由“永银文化”承接发货、运输和办理入库；</td></tr></table></td>
					</tr>
					<tr class="content2">
						<td colspan="4"><table><tr><td>2.若您收到产品有问题，可直接与永银文化的业务人员联系。</td></tr></table></td>
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
</body>
</html>
