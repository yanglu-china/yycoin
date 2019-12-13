<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="出库单列表" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script src="../js/title_div.js"></script>
<script src="../js/public.js"></script>
<script src="../js/JCheck.js"></script>
<script src="../js/common.js"></script>
<script src="../js/tableSort.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script src="../js/adapter.js"></script>
<script language="javascript">
function process()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}
	
	$l('../sail/ship.do?method=findConsign&fullId=' + getRadioValue('pickupId') + '&gid=' + getRadio('pickupId').pgid);
}

function batchPagePrint()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	$l('../sail/ship.do?method=findPickup&printMode=0&printSmode=0&compose=2&pickupId=' + getRadioValue("pickupId") + '&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
}

function pickupPagePrint()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	$l('../sail/ship.do?method=findPickup&printMode=1&printSmode=1&compose=2&pickupId=' + getRadioValue("pickupId") + '&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
}

function packagePagePrint()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

    var clis = getCheckBox('packageIds');
    if (clis.length ==1){
        //打印第一个选中的CK单
        var packageId = clis[0].value;
        $l('../sail/ship.do?method=findNextPackage&printMode=0&printSmode=1&compose=2&pickupId=' + getRadioValue("pickupId") +'&packageId='+packageId+ '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
    }else{
        if (clis.length >1){
            alert('一次只能选择一个出库单!');
            return;
        } else{
            //打印批次
            $l('../sail/ship.do?method=findNextPackage&printMode=0&printSmode=1&compose=2&pickupId=' + getRadioValue("pickupId") + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
        }
    }
	}

function receiptPagePrint(flag)
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	if (flag == '1')
		$l('../sail/ship.do?method=findOutForReceipt&compose=1&pickupId=' + getRadioValue("pickupId") + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
	else if (flag == '3') {
		//直邮打印批次
		$l('../sail/ship.do?method=findOutForReceipt&compose=2&direct=1&pickupId=' + getRadioValue("pickupId") + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
	}else {
		var clis = getCheckBox('packageIds');
		if (clis.length ==1){
			//打印第一个选中的CK单
			var packageId = clis[0].value;
			$l('../sail/ship.do?method=findOutForSingleReceipt&compose=2&packageId=' + packageId + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
		}else{
			if (clis.length >1){
				alert('选择CK单时一次只能打印一个出库单!');
				return;
			} else{
				//打印批次
				$l('../sail/ship.do?method=findOutForReceipt&compose=2&pickupId=' + getRadioValue("pickupId") + '&index_pos=0&CENTER_COMMON_CENTER_COMMON=' + new Date().getTime());
			}
		}
	}
}

function sub()
{
    var clis = getCheckBox('packageIds');
    var pickupId = getRadioValue("pickupId");

    if (clis.length > 0)
    {
        var packageIds = "";
        for (var i = 0; i < clis.length; i++)
        {
            packageIds += clis[i].value + '~';
        }
        if (window.confirm('确认发货选中的出库单?')){
            $l('../sail/ship.do?method=mUpdateStatus&packageIds='+packageIds);
        }
    } else {
        if (window.confirm('确认发货选中批次下的所有出库单?')){
            $l('../sail/ship.do?method=mUpdateStatus&pickupId='+pickupId);
        }
    }
}

function cancelPickup()
{
    var clis = getCheckBox('packageIds');
    var pickupId = getRadioValue("pickupId");

    if (clis.length > 0)
    {
        var packageIds = "";
        for (var i = 0; i < clis.length; i++)
        {
            packageIds += clis[i].value + '~';
        }
        if (window.confirm('确定撤销拣配选中的出库单?')){
            $l('../sail/ship.do?method=cancelPickup&packageIds='+packageIds);
        }
    } else {
        if (window.confirm('确定撤销拣配选中批次下的所有出库单?')){
            $l('../sail/ship.do?method=cancelPickup&pickupId='+pickupId);
        }
    }
}

function printHandover()
{
    var clis = getCheckBox('packageIds');
    var pickupId = getRadioValue("pickupId");

    if (getRadioValue('pickupId') == '')
    {
        alert('请选择出库单');
        return;
    }

    $l('../sail/ship.do?method=printHandover&pickupId=' + getRadioValue("pickupId"));

//    if (clis.length > 0)
//    {
//        var packageIds = "";
//        for (var i = 0; i < clis.length; i++)
//        {
//            packageIds += clis[i].value + '~';
//        }
//        if (window.confirm('确认发货选中的出库单?')){
//            $l('../sail/ship.do?method=mUpdateStatus&packageIds='+packageIds);
//        }
//    } else {
//        if (window.confirm('确认发货选中批次下的所有出库单?')){
//            $l('../sail/ship.do?method=mUpdateStatus&pickupId='+pickupId);
//        }
//    }
}

function printBatch()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}

	$l('../sail/ship.do?method=printBatch&pickupId=' + getRadioValue("pickupId"));
}


function query()
{
	var transport1 = $$('transport1');
	var transport2 = $$('transport2');

	if (transport1 != '' || transport2 != '')
	{
		if ($$('shipment') != '')
		{
			if ($$('shipment') == 0 || $$('shipment') == 1)
			{
				alert('选择快递或货运时发货方式不能为自提与公司');

				return false;
			} 
		}
	}

	formEntry.submit();
}

function selectPrincipalship()
{
    window.common.modal('../admin/pop.do?method=rptQueryPrincipalship&load=1&selectMode=1');
}

function getPrincipalship(oos)
{
    var oo = oos[0];

    $O('industryId').value = oo.value;
    $O('industryName').value = oo.pname;   
}

function clears()
{
	$O('industryId').value = '';
	$O('industryName').value = '';
}

function sfPickup()
{
	if (getRadioValue('pickupId') == '')
	{
		alert('请选择出库单');
		return;
	}
	var clis = getCheckBox('packageIds');
	if (clis.length == 0){
		alert('请选择一条出库单!');
		return;
	}
    var packageIds = "";
    for (var i = 0; i < clis.length; i++)
    {
        packageIds += clis[i].value + '~';
    }
	$l('../sail/ship.do?method=showSfPrintPage&packageIds='+packageIds);
}

function selectCK(pcId)
{
	var pickupObj = $("input[type='radio']");
	
	$.each(pickupObj,function(){
		var pickUpIdVal = $(this).val();
		if(pcId == pickUpIdVal)
		{
			var name="pc_" +  pickUpIdVal;
			var pcAll = $("input[name^='" + name + "']");
			$.each(pcAll,function(i){
			    var val = $(this).val();
			    var valArray = val.split('_');
			    var vpcId = valArray[0];
			    var vckId = valArray[1];
				if(pcId == vpcId)
				{
					var checkBoxAll = $("input[name='packageIds']");
					$.each(checkBoxAll,function(){
						var itemval = $(this).val();
						if(itemval == vckId)
						{
							$(this).attr("checked",true);
						}
					});
				}
			});
		}
		else
		{
			var name="pc_" +  pickUpIdVal;
			var pcAll = $("input[name^='" + name + "']");
			$.each(pcAll,function(i){
			    var val = $(this).val();
			    var valArray = val.split('_');
			    var vpcId = valArray[0];
			    var vckId = valArray[1];
				var checkBoxAll = $("input[name='packageIds']");
				$.each(checkBoxAll,function(){
					var itemval = $(this).val();
					if(itemval == vckId)
					{
						$(this).attr("checked",false);
					}
				});
			});
		}
	});
	
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../sail/ship.do"><input
	type="hidden" name="method" value="queryPickup"> 
	<input type="hidden" value="1" name="firstLoad">
	<input type="hidden" value="" name="pickupId">
	<input type="hidden" value="${ppmap.industryId}"
	name="industryId">
	
	<p:navigation
	height="22">
	<td width="550" class="navigation">出库单管理 &gt;&gt; 出库单列表</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr class="content1">
				<td width="15%" align="center">批次号</td>
				<td align="left">
				<input type="text" name="batchId" maxlength="40" size="30" value="${ppmap.batchId}">
				</td>
				<td width="15%" align="center">发货方式</td>
				<td align="left">
					<select name="shipment"	class="select_class" style="width:50%" values="${ppmap.shipment}">
					<option value="">--</option>
					<option value="0">自提</option>
					<option value="1">公司</option>
					<option value="2">第三方快递</option>
					<option value="3">第三方货运</option>
					<option value="4">第三方快递+货运</option>
				</select>
				</td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">快递</td>
				<td align="left">
				<select name="transport1" quick=true class="select_class" style="width:50%" values="${ppmap.transport1}">
					<option value="">--</option>
					<c:forEach items="${expressList}" var="item">
						<c:if test="${item.type==0 || item.type == 99}">
							<option value="${item.id}">${item.name}</option>
						</c:if>
					</c:forEach>
				</select>
				</td>
				<td width="15%" align="center">货运</td>
				<td align="left">
				<select name="transport2" quick=true class="select_class" style="width:50%" values="${ppmap.transport2}">
					<option value="">--</option>
					<c:forEach items="${expressList}" var="item">
						<c:if test="${item.type==1 || item.type == 99}">
							<option value="${item.id}">${item.name}</option>
						</c:if>
					</c:forEach>
				</select>
				</td>
			</tr>

			<tr class="content1">
				<td width="15%" align="center">收货人</td>
				<td align="left">
				<input name="receiver" size="30"  value="${ppmap.receiver}"/>
				</td>
				<td width="15%" align="center">联系电话</td>
				<td align="left">
				<input name="mobile" size="30"  value="${ppmap.mobile}"/>
				</td>
			</tr>

			<tr class="content2">
				<td width="15%" align="center">出库单号</td>
				<td align="left">
				<input name="packageId" size="30"  value="${ppmap.packageId}"/>
				</td>
				<td width="15%" align="center">状态</td>
				<td align="left">
				<select name="currentStatus" class="select_class" style="width:50%" values="${ppmap.currentStatus}">
					<option value="">--</option>
					<option value="1">已拣配</option>
					<option value="3">已打印</option>
					<option value="2">已发货</option>
					<option value="4">已拣配/打印/打印发票</option>
					<option value="5">打印发票</option>
					<option value="13">签收</option>
					<option value="14">退签</option>
					<option value="10">在途</option>
				</select>
				</td>
			</tr>
			
			<tr class="content1">
				<td width="15%" align="center">仓库地点</td>
				<td align="left">
				<select name="location" class="select_class" style="width:50%" values="${ppmap.location}">
					<p:option type="$adminindustryList" empty="true"></p:option>
				</select>
				</td>
				
				<td width="15%" align="center">事业部</td>
                <td align="left">
                        <input type="text" name="industryName" value="${ppmap.industryName}" readonly="readonly" onClick="selectPrincipalship()">
                        <input
								type="button" value="清空" name="qout" id="qout"
								class="button_class" onclick="clears()">&nbsp;&nbsp;
                </td>
			</tr>
			
			<tr class="content2">
				<td width="15%" align="center">销售单号</td>
				<td align="left"><input type="text" name="outId" value="${ppmap.outId}" style="width:50%"></td>
				
				<td width="15%" align="center">紧急</td>
                <td align="left">
                <select name="emergency" class="select_class" style="width:50%" values="${ppmap.emergency}">
					<option value="">--</option>
					<option value="0">不紧急</option>
					<option value="1">紧急</option>
				</select>
                </td>
			</tr>

            <tr class="content2">
                <td width="15%" align="center">客户名称</td>
                <td align="left">
                    <input type="text" name="customerName" value="${ppmap.customerName}" style="width:50%">
                </td>

                <td width="15%" align="center">产品名称</td>
                <td align="left">
                    <input type="text" name="productName" value="${ppmap.productName}" style="width:50%">
                </td>
            </tr>
			
			<tr class="content1">
				<td colspan="4" align="right"><input type="button"
					class="button_class" onclick="query()" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
					type="reset" class="button_class"
					value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
			</tr>
		</table>

	</p:subBody>

	<p:title>
		<td class="caption"><strong>出库单列表</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
		<table width="100%" align="center" cellspacing='1' class="table0">
			<tr align=center class="content0">
				<td align="center" class="td_class">选择</td>
				<td align="center" class="td_class" ><strong>批次号</strong></td>
				<td align="center" class="td_class" ><strong>系列号</strong></td>
                <td align="center" class="td_class" ><strong>选择2</strong></td>
				<td align="center" class="td_class" ><strong>出库单</strong></td>
				<td align="center" class="td_class" ><strong>状态</strong></td>
				<td align="center" class="td_class" ><strong>事业部</strong></td>
				<td align="center" class="td_class" ><strong>发货方式</strong></td>
				<td align="center" class="td_class" ><strong>快递公司</strong></td>
				<td align="center" class="td_class" ><strong>货运公司</strong></td>
				<td align="center" class="td_class" ><strong>收货人</strong></td>
				<td align="center" class="td_class" ><strong>收货电话</strong></td>
				<td align="center" class="td_class" ><strong>仓库地点</strong></td>
			</tr>
			
			<c:forEach items="${itemList}" var="item" varStatus="vs">
                <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center"><input type="radio" name="pickupId" onclick="selectCK('${item.pickupId}')"
                        value="${item.pickupId}"
                        ${vs.index== 0 ? "checked" : ""}/></td>
                    <td align="center" onclick="hrefAndSelect(this)">${item.pickupId}</td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                    <td align="center" onclick="hrefAndSelect(this)"></td>
                </tr>
                <c:forEach items="${item.packageList}" var="item2" varStatus="vs2">
                    <tr class="${vs2.index % 2 == 0 ? 'content1' : 'content2'}">
                    <td align="center"><input type="hidden" name="pc_${item.pickupId}_${item2.id}" id="pc_${item.pickupId}_${item2.id}" value="${item.pickupId}_${item2.id}"></td>
                    <td align="center">--</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.index_pos}</td>
                    <td align="center"><input type="checkbox" name="packageIds" value="${item2.id}"></td>
                    <td align="center" onclick="hrefAndSelect(this)">
                    <a
                        href="../sail/ship.do?method=findPackage&packageId=${item2.id}"
                        >${item2.id}</a></td>
                    <td align="center" onclick="hrefAndSelect(this)">${my:get('shipStatus', item2.status)}</td>
					<td align="center" onclick="hrefAndSelect(this)">${item2.industryName}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${my:get('outShipment',item2.shipping)}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.transportName1}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.transportName2}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.receiver}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.mobile}</td>
                    <td align="center" onclick="hrefAndSelect(this)">${item2.locationName}</td>
                </tr>
                </c:forEach>
                
            </c:forEach>
            
		</table>

		<p:formTurning form="formEntry" method="queryPickup"></p:formTurning>
		
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="98%" rightWidth="2%">
		
		<div align="right">
			<input type="button"
				   class="button_class" onclick="printBatch()"
				   value="&nbsp;&nbsp;打印批次发票&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="pickupPagePrint()"
			value="&nbsp;&nbsp;打印批次出库单&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="packagePagePrint()"
			value="&nbsp;&nbsp;打印客户出库单&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="receiptPagePrint(2)"
			value="&nbsp;&nbsp;打印回执单&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" 
			class="button_class" onclick="receiptPagePrint(1)"
			value="&nbsp;&nbsp;打印回执单(含配件)&nbsp;&nbsp;">&nbsp;&nbsp;
            <input type="button"
                   class="button_class" onclick="printHandover()"
                   value="&nbsp;&nbsp;打印交接清单&nbsp;&nbsp;">&nbsp;&nbsp;
            <input type="button"
                   class="button_class" onclick="batchPagePrint()"
                   value="&nbsp;&nbsp;批量打印&nbsp;&nbsp;">&nbsp;&nbsp;
			<%--<input type="button"--%>
				   <%--class="button_class" onclick="receiptPagePrint(3)"--%>
				   <%--value="&nbsp;&nbsp;直邮打印&nbsp;&nbsp;">&nbsp;&nbsp;--%>
			<input type="button" 
			class="button_class" onclick="sub()"
			value="&nbsp;&nbsp;确认发货&nbsp;&nbsp;">&nbsp;&nbsp;
            <input type="button"
                   class="button_class" onclick="cancelPickup()"
                   value="&nbsp;&nbsp;撤销拣配&nbsp;&nbsp;">&nbsp;&nbsp;
			<input type="button" id="sfprint" name="sfprint"
                   class="button_class" onclick="sfPickup()"
                   value="&nbsp;&nbsp;顺丰面单打印&nbsp;&nbsp;">&nbsp;&nbsp;
		</div>
	</p:button>

	<p:message2 />

</p:body></form>

</body>
</html>

