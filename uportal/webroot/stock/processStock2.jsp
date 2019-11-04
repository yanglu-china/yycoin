<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="采购单" />
<link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
<script language="JavaScript" src="../js/title_div.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script src="../js/jquery/jquery.js"></script>
<script src="../js/plugin/dialog/jquery.dialog.js"></script>
<script src='../js/adapter.js'></script>
<script language="javascript">

var g_id;
var to_be_warehouse;
var xqqrBeans;

<%--var jmap = new Object();--%>
<%--<c:forEach items="${bean.itemVO}" var="item">--%>
<%--jmap['${item.id}'] = "${divMap[item.id]}";--%>
<%--</c:forEach>--%>
//console.log(jmap);

//function showDiv(id)
//{
////    console.log("ID*******"+id);
////    console.log("jamp*****"+jmap[id]);
//    tooltip.showTable(jmap[id]);
//}

function fech(id,productId, amount,totalWarehouseNum)
{
    g_id = id;
    to_be_warehouse = parseInt(amount)-parseInt(totalWarehouseNum);
    
    if(to_be_warehouse <= 0){
    	alert("可拿货数量不足！");
    	return;
    }
   console.log("id:"+id);
//    console.log("amount:"+amount);
//    console.log("totalWarehouseNum:"+totalWarehouseNum);
//    console.log("to_be_warehouse:"+to_be_warehouse);
	resetNhNum();
	var bjNo = $O('bjNo').value;
    $ajax('../stock/stock.do?method=queryXqqr&bjNo=' + bjNo+"&productId="+productId, function(data){
        console.log(data);
        xqqrBeans = data.msg;
        var xqqrElement = document.querySelector('#xqqr');
        removeAllItem(xqqrElement);
        setOption(xqqrElement,'-','-');
        for (var i=0;i<xqqrBeans.length;i++){
            var obj = xqqrBeans[i];
            console.log(obj);
            setOption(xqqrElement,obj.demandQRId,obj.demandQRId);
		}
	});
	$('#dlg').dialog({closed:false});
}

function xqqrChange(){
    //TODO
    var xqqrElement = document.querySelector('#xqqr');
    var xqqr = xqqrElement.value;
    console.log(xqqr);
    for (var i=0;i<xqqrBeans.length;i++){
        var obj = xqqrBeans[i];
        console.log(obj);
        if (obj.demandQRId === xqqr){
            var purchaseAmountElement = document.querySelector('#purchaseAmount');
            purchaseAmountElement.value = obj.purchaseAmount;
            var nhNumElement = document.querySelector('#nhNum');
            nhNumElement.value = obj.nhNum;
		}
    }
}

function resetNhNum(){
    var purchaseAmountElement = document.querySelector('#purchaseAmount');
    purchaseAmountElement.value = '';
    var nhNumElement = document.querySelector('#nhNum');
    nhNumElement.value = '';
}

function showBatchDlg()
{
//    console.log("id:"+id);
//    console.log("amount:"+amount);
//    console.log("totalWarehouseNum:"+totalWarehouseNum);
//    console.log("to_be_warehouse:"+to_be_warehouse);

    var amountElements = document.querySelectorAll('input[name="batchWarehouseNum"]')
    for (var i=0;i<=amountElements.length-1;i++){
        var element = amountElements[i];
        if(element && element.value){
            continue;
		} else{
            alert("批量拿货数量字段必填!");
            return false;
		}
    }
    $('#dlg2').dialog({closed:false});
}

function load()
{
    tooltip.init();

     $('#dlg').dialog({
                modal:true,
                closed:true,
                buttons:{
                    '确 定':function(){
                        updatePrice();
                    },
                    '取 消':function(){
                        $('#dlg').dialog({closed:true});
                    }
                }
     });
     
     $ESC('dlg');

    $('#dlg2').dialog({
        modal:true,
        closed:true,
        buttons:{
            '确 定':function(){
                batchFetchProduct();
            },
            '取 消':function(){
                $('#dlg2').dialog({closed:true});
            }
        }
    });

    $ESC('dlg2');
}

function updatePrice()
{
    if ($$('depotpartId') == '')
    {
        alert('请选择仓区');
        return false;
    }

//    console.log(to_be_warehouse);
//    console.log(parseInt($$('warehouseNum')));
    if (parseInt($$('warehouseNum'))>to_be_warehouse){
        alert('此次入库数量不能大于待入库数量');
        return false;
    }

    var xqqrElement = document.querySelector('#xqqr');
    var demandQRId = xqqrElement.value;
    document.location.href = '../stock/stock.do?method=fechProduct&id=${bean.id}&itemId='
            + g_id + '&depotpartId=' + $$('depotpartId')+ '&warehouseNum=' + $$('warehouseNum')
            + '&to_be_warehouse=' + to_be_warehouse+'&demandQRId='+demandQRId;
}

function batchFetchProduct()
{
    if ($$('depotpartId') == '')
    {
        alert('请选择仓区');
        return false;
    }

    formEntry.method.value = 'batchFetchProduct';
    formEntry.submit();
}


</script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" id="formEntry" action="../stock/stock.do" method="post">
<input type="hidden" name="method" value="updateStockStatus">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="bjNo" value="${bean.bjNo}">
<input type="hidden" name="pass" value="1">
<input type="hidden" name="nearlyPayDate" value="">
<input type="hidden" name="reject" value="">
<p:navigation height="22">
	<td width="550" class="navigation">采购单明细</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>采购单信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:table cells="2">
			<p:class value="com.china.center.oa.stock.bean.StockBean" opr="2"/>
			
			<p:cell title="单号">
            ${bean.id}
            </p:cell>

            <p:cell title="采购人">
            ${bean.userName}
            </p:cell>

			<p:cell title="比价标示">
				${bean.bjNo}
			</p:cell>
            
            <p:cell title="库存人">
            ${bean.owerName}
            </p:cell>

			<p:cell title="区域">
			${bean.locationName}
			</p:cell>

			<p:cell title="状态">
			${my:get('stockStatus', bean.status)}
			</p:cell>
			
			<p:pro field="stockType">
				<option value="">--</option>
               <p:option type="stockSailType"></p:option>
            </p:pro>
            
             <p:pro field="stype">
				<option value="">--</option>
               <p:option type="stockStype"></p:option>
            </p:pro>
			
            <p:pro field="invoiceType" innerString="style='width: 300px'">
                <option value="">没有发票</option>
                <c:forEach items="${invoiceList}" var="item">
				<option value="${item.id}">${item.fullName}</option>
				</c:forEach>
            </p:pro>

			<p:cell title="录入时间">
			${bean.logTime}
			</p:cell>

			<p:cell title="需到货时间">
			${bean.needTime}
			</p:cell>

			<p:cell title="物流">
			${bean.flow}
			</p:cell>

			<p:cell title="总计金额">
			${my:formatNum(bean.total)}
			</p:cell>

			<p:cells celspan="1" title="异常状态">
			${my:get('stockExceptStatus', bean.exceptStatus)}
			</p:cells>
			
			<p:cells celspan="1" title="询价类型">
            ${my:get('priceAskType', bean.type)}
            </p:cells>
            
            <p:pro field="willDate" cell="2"/>
            
             <p:cells celspan="2" title="纳税实体">
            ${bean.dutyName}
            </p:cells>

			<p:cells celspan="2" title="备注">
			${bean.description}
			</p:cells>
			
		</p:table>
	</p:subBody>

	<p:tr />

	<p:subBody width="100%">
		<table width="100%" border="0" cellspacing='1' id="tables">
			<tr align="center" class="content0">
				<td width="10%" align="center">采购产品</td>
				<td width="5%" align="center">采量</td>
				<td width="10%" align="center">待入库数量</td>
                <td width="10%" align="center">入库数量</td>
				<%--<td width="10%" align="center">是否询价</td>--%>
				<td width="10%" align="center">参考/实际价格</td>
				<td width="10%" align="center">付款时间</td>
				<td width="15%" align="center">供应商</td>
				<td width="5%" align="center">合计金额</td>
				<td width="10%" align="center">描述</td>
				<td width="10%" align="center">拿货数量</td>
				<td width="10%" align="center">拿货人</td>
				<td width="10%" align="center">拿 货</td>
			</tr>

			<c:forEach items="${bean.stockItemArrivalVOs}" var="item" varStatus="vs">
				<c:if test="${bean.stafferId == user.stafferId || item.stafferId == user.stafferId}">
					<tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
						<td align="center" >${item.productName}</td>

						<td align="center">${item.amount}</td>

						<td align="center">${item.amount-item.totalWarehouseNum}</td>
						<td align="center">
							<a href="javascript:void(0);">${item.totalWarehouseNum}</a>
						</td>

						<td align="center">${my:formatNum(item.prePrice)}/${my:formatNum(item.price)}</td>

						<td align="center">${item.nearlyPayDate}</td>

						<td align="center">${item.providerName}</td>

						<td align="center">${my:formatNum(item.amount*item.price)}</td>

						<td align="center">${item.description}</td>

						<td align="center">
							<input type="number" name="batchWarehouseNum" placeholder="批量拿货数量">
						</td>

						<td align="center">${item.stafferName}</td>
						<input type="hidden" name="itemId" value="${item.id}">
						<input type="hidden" name="to_be_warehouse" value="${item.amount-item.totalWarehouseNum}">
						<td align="center">
							<c:if test="${item.fechProduct == 0 || item.fechProduct == 2}">
								<a title="拿货"
								   href="javascript:fech('${item.id}','${item.productId}','${item.amount}','${item.totalWarehouseNum}')">
									<img src="../images/opr/change.gif" border="0" height="15" width="15"></a>
							</c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right">
			<input type="button" class="button_class"
				   onclick="showBatchDlg()"
				   value="&nbsp;&nbsp;批量拿货&nbsp;&nbsp;">
			<input type="button" class="button_class"
			onclick="javascript:history.go(-1)"
			value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
		</div>
	</p:button>

	<p:message2/>
	
</p:body>
<div id="dlg" title="选择到货的仓区" style="width:320px;">
    <div style="padding:20px;height:200px;" id="dia_inner1" title="">
        <c:forEach items="${depotpartList}" var="item" varStatus="vs">
         <input type="radio" name="depotpartId" value="${item.id}"  ${vs.index == 0 ? 'checked=checked' : '' }>${item.locationName}-${item.name}<br>
        </c:forEach>
		<label for="xqqr">
			需求确认单号
			<select id="xqqr" name="xqqr" onchange="xqqrChange()">
				<option value="-">-</option>
			</select>
		</label><br>
		<label for="purchaseAmount">
			此需求确认单采购数量
			<input type="text" id="purchaseAmount" name="purchaseAmount" placeholder="采购数量" disabled>
		</label><br>
		<label for="nhNum">
			此需求确认单已拿货数量
			<input type="text" id="nhNum" name="nhNum" placeholder="已拿货数量" disabled>
		</label><br>
        <label for="warehouseNum">
			此次入库数量
            <input type="text" id="warehouseNum" name="warehouseNum" placeholder="此次入库数量" required>
        </label>
   </div>
</div>
<div id="dlg2" title="选择到货的仓区" style="width:320px;">
	<div style="padding:20px;height:200px;" id="dia_inner2" title="">
		<c:forEach items="${depotpartList}" var="item" varStatus="vs">
			<input type="radio" name="depotpartId" value="${item.id}"  ${vs.index == 0 ? 'checked=checked' : '' }>${item.locationName}-${item.name}<br>
		</c:forEach>
	</div>
</div>
</form>
</body>
</html>

