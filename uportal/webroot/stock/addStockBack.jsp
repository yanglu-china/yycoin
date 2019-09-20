<%@ page contentType="text/html;charset=UTF-8" language="java"
         errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
    <p:link title="采购单" />
    <script language="JavaScript" src="../js/common.js"></script>
    <script language="JavaScript" src="../js/JCheck.js"></script>
    <script language="JavaScript" src="../js/public.js"></script>
    <script language="JavaScript" src="../js/key.js"></script>
    <script language="JavaScript" src="../js/title_div.js"></script>
    <script language="JavaScript" src="../stock_js/jquery-1.7.1.min.js"></script>
    <script language="JavaScript" src="../stock_js/picker.js"></script>
    <script language="JavaScript" src="../stock_js/picker.date.js"></script>
    
    <script language="JavaScript" src="../stock_js/addStockBack.js"></script>
    
    <link rel="stylesheet" href="../stock_js/classic.css" />
    <link rel="stylesheet" href="../stock_js/classic.date.css" />

<script language="javascript">

<%@include file="../stock_js/addStockBackJs.jsp"%>

		/**
		 * 查询库存
		 */
		function opens(obj)
		{
			var ret = checkCurrentUser();
		
			if (!ret)
			{
				window.parent.location.reload();
		
				return false;
			}
		
			var os = obj.parentNode.parentNode;
			
        	var trow = obj.parentNode.parentNode;
        	
        	var backType = getSelectInTr(trow, "backType").value;
        	
        	if(backType == ""){
        		alert("请选择退货类型！");
        		return;
        	}  
		    
		    var mtype = "";
		    oo = obj;
		    // 配件
		    window.common.modal('../depot/storage.do?method=rptQueryStorageRelation4StockBack&stockId=${bean.id}&backType='+backType);
		}

        function load()
        {
            loadForm();
            showDate();
        }

        var jmap = {};
        <c:forEach items="${map}" var="item">
        jmap['${item.key}'] = "${item.value}";
        </c:forEach>

        var productMap = {};
        
        var productObj = {};
        var productInfoMap = {};
        
        <c:forEach items="${bean.itemVO}" var="item" varStatus="status">
            productMap['${item.productId}'] = parseInt("${item.amount}");
            	
            productObj["cost"] = "${item.productCost}";
            productObj["price"] = "${item.price}";
            productObj["depotpartId"] = "${item.depotpartId}";
            productObj["depot"] = "${item.depotpartName}";
            productObj["providerId"] = "${item.providerId}";
            productObj["provider"] = "${item.providerName}";
            productObj["dutyId"] = "${item.dutyId}";
            productObj["duty"] = "${item.dutyName}";
            productObj["invoiceId"] = "${item.invoiceType}"; 
            productObj["invoice"] = "${item.invoiceTypeName}";  
            productObj["productName"] = "${item.productName}";  
            
            productInfoMap['${ status.index + 1}'] = productObj;
        </c:forEach>
//        console.log(productMap);

        function showDiv(id)
        {
            if (jmap[id] != null && jmap[id] != '')
                tooltip.showTable(jmap[id]);
        }
        
      //选择产品后的回调函数
        function getProductBom(oos)
        {
            var tb = document.getElementById("tables");
            
            var rnum=tb.rows.length;
            if (rnum > 1)
            {
                for(var i=rnum -1; i>0; i--)
                {
                    tb.deleteRow(i);
                }
            }
        	var oo = oos[0];

            current.value = oo.pname;

            $O("mtype").value = oo.pmtype;
            $O("oldproduct").value = oo.poldproduct;
            $O("dirProductId").value = oo.value;
            $O("high").value = oo.high;
            $O("low").value = oo.low;
        	
        	var bomjson = JSON.parse(oo.pbomjson);
        	for (var j = 0; j < bomjson.length; j++)
            {
                var item = bomjson[j];
        		var trow = addTrInner();
                //console.log(item);
        		setInputValueInTr(trow, 'srcProductId', item.subProductId);
        		setInputValueInTr(trow, 'targerName', item.subProductName);
        		setInputValueInTr(trow, 'srcProductCode', item.code);
                setInputValueInTr(trow, 'srcPrice', item.price);
                setInputValueInTr(trow, 'srcAmount', item.pamount);
                setInputValueInTr(trow, 'srcRelation', item.srcRelation);
                setInputValueInTr(trow, 'bomAmount', item.bomAmount);
                setInputValueInTr(trow, 'attritionRate', item.attritionRate);
                setInputValueInTr(trow, 'virtualPrice', item.virtualPrice);
                var srcDe1 = getEle(trow.getElementsByTagName('select'), "srcDepotpart");
                setSelect(srcDe1, "A1201606211663545389");
            }
        } 
      
      
        //选中产品
        function selectProduct(obj)
        {
        	
        	var index = obj.selectedIndex;
        	
        	var trow = obj.parentNode.parentNode;
        	
        	var backType = getSelectInTr(trow, "backType").value;
        	
        	if(backType == "1"){
        		setInputValueInTr(trow, 'price', productInfoMap[index].cost);
        	}else{
        		setInputValueInTr(trow, 'price', productInfoMap[index].price);
        	}
        	setInputValueInTr(trow, 'depotpartId', productInfoMap[index].depotpartId);
        	setInputValueInTr(trow, 'depot', productInfoMap[index].depot);
        	setInputValueInTr(trow, 'providerId', productInfoMap[index].providerId);
        	setInputValueInTr(trow, 'provider', productInfoMap[index].provider);
        	setInputValueInTr(trow, 'dutyId', productInfoMap[index].dutyId);
        	setInputValueInTr(trow, 'duty', productInfoMap[index].duty);
        	setInputValueInTr(trow, 'invoiceId', productInfoMap[index].invoiceId);
        	setInputValueInTr(trow, 'invoiceType', productInfoMap[index].invoice);
        	setInputValueInTr(trow, 'productName', productInfoMap[index].productName);

        }  
        
        function checkBackType(obj){
        	var trow = obj.parentNode.parentNode;
        	
        	var backType = getSelectInTr(trow, "backType").value;
        	
        	if(backType == ""){
        		alert("请选择退货类型！");
        		return;
        	}        	
        }
        
        function toggleSendInfo(obj)
        {
            //console.log(obj.value);
            if (obj.value == '1')
            {
                showTr('distribution1', true);
                showTr('distribution2', true);
                showTr('distribution3', true);
                showTr('distribution4', true);
                showTr('distribution5', true);
            }
            else if (obj.value == '0')
            {
                showTr('distribution1', false);
                showTr('distribution2', false);
                showTr('distribution3', false);
                showTr('distribution4', false);
                showTr('distribution5', false);
            }
        }  
        
        function radio_click(obj)
        {
        	if (obj.value == '2')
        	{
        		$O('transport1').disabled = false;
        		removeAllItem($O('transport1'));
        		setOption($O('transport1'), "", "--");
        		<c:forEach items="${expressList}" var="item">
        		if ("${item.type}" == 0 || "${item.type}" == 99)
        		{
        			setOption($O('transport1'), "${item.id}", "${item.name}");
        		}
        		</c:forEach>
        		removeAllItem($O('transport2'));
        		setOption($O('transport2'), "", "--");
        		$O('transport2').disabled = true;
        		var expressPay = $O('expressPay');
        		var transportPay = $O('transportPay');
        		removeAllItem(expressPay);
        		setOption(expressPay, '1', '业务员支付');
        		setOption(expressPay, '2', '公司支付');
        		setOption(expressPay, '3', '客户支付');
        		removeAllItem(transportPay);
        		setOption(transportPay, '', '--');
        	}
        	else if (obj.value == '3')
        	{
        		$O('transport2').disabled = false;
        		removeAllItem($O('transport2'));
        		setOption($O('transport2'), "", "--");
        		<c:forEach items="${expressList}" var="item">
        		if ("${item.type}" == 1 || "${item.type}" == 99)
        		{
        			setOption($O('transport2'), "${item.id}", "${item.name}");
        		}
        		</c:forEach>
        		removeAllItem($O('transport1'));
        		setOption($O('transport1'), "", "--");
        		$O('transport1').disabled = true;
        		var expressPay = $O('expressPay');
        		var transportPay = $O('transportPay');
        		removeAllItem(expressPay);
        		setOption(expressPay, '', '--');
        		removeAllItem(transportPay);
        		setOption(transportPay, '1', '业务员支付');
        		setOption(transportPay, '2', '公司支付');
        		setOption(transportPay, '3', '客户支付');
        	}
        	else if (obj.value == '4')
        	{
        		$O('transport1').disabled = false;
        		$O('transport2').disabled = false;
        		removeAllItem($O('transport1'));
        		setOption($O('transport1'), "", "--");
        		removeAllItem($O('transport2'));
        		setOption($O('transport2'), "", "--");
        		<c:forEach items="${expressList}" var="item">
        		if ("${item.type}" == 0 || "${item.type}" == 99)
        		{
        			setOption($O('transport1'), "${item.id}", "${item.name}");
        		}
        		</c:forEach>
        		<c:forEach items="${expressList}" var="item">
        		if ("${item.type}" == 1 || "${item.type}" == 99)
        		{
        			setOption($O('transport2'), "${item.id}", "${item.name}");
        		}
        		</c:forEach>
        		var expressPay = $O('expressPay');
        		var transportPay = $O('transportPay');
        		removeAllItem(expressPay);
        		setOption(expressPay, '1', '业务员支付');
        		setOption(expressPay, '2', '公司支付');
        		setOption(expressPay, '3', '客户支付');
        		removeAllItem(transportPay);
        		setOption(transportPay, '1', '业务员支付');
        		setOption(transportPay, '2', '公司支付');
        		setOption(transportPay, '3', '客户支付');
        	}
        	else
        	{
        		$O('transport1').disabled = true;
        		$O('transport2').disabled = true;
        		removeAllItem($O('transport1'));
        		setOption($O('transport1'), "", "--");
        		removeAllItem($O('transport2'));
        		setOption($O('transport2'), "", "--");
        		var expressPay = $O('expressPay');
        		var transportPay = $O('transportPay');
        		removeAllItem(expressPay);
        		setOption(expressPay, '', '--');
        		removeAllItem(transportPay);
        		setOption(transportPay, '', '--');
        	}
        }        


    </script>

</head>
<body class="body_class" onload="load()" onkeydown="tooltip.bingEsc(event)">
<form action="../stock/stock.do" name="outForm">

<input type=hidden name="method" value="addOuts" />

	<input type=hidden name="nameList" />
	<input type=hidden name="idsList" />
	<input type=hidden name="unitList" />
	<input type=hidden name="amontList" />
	<input type=hidden name="priceList" />
	<input type=hidden name="totalList" />
	<input type=hidden name="totalss" />
	<input type=hidden name="customerId" />
<input type="hidden" name="reserve9" value=""/>	
<input type=hidden name="type" value='1' /> 
<input type=hidden name="saves" value="" />
<input type=hidden name="desList" value="" />
<input type=hidden name="virtualPriceList" value="" />
<input type=hidden name="otherList" value="" />

<input type=hidden name="depotList" value="" />
<input type=hidden name="mtypeList" value="" />
<input type=hidden name="oldGoodsList" value="" />
<input type=hidden name="taxList" value="" />
<input type=hidden name="taxrateList" value="" />
<input type=hidden name="inputRateList" value="" />

<input type=hidden name="showIdList" value="" />
<input type=hidden name="showNameList" value="" />
<input type=hidden name="customercreditlevel" value="" />
<input type=hidden name="inputPriceList" value="" />
<input type=hidden name="id" value="" />
<input type=hidden name="showCostList" value="" />

<input type=hidden name="oprType" value="1">

<input type=hidden name="customercreditlevel" value="" />
<input type=hidden name="id" value="" />
<input type=hidden name="inputPriceList">

    <input type="hidden" name="stockId" value="${bean.id}">
    <input type="hidden" name="buyReturnFlag" value="1">
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

                <p:cell title="库存人">
                    ${bean.owerName}
                </p:cell>

                <p:cell title="事业部">
                    ${bean.industryName}
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

                <p:pro field="areaId" innerString="style='width: 300px'">
                    <option value="">--</option>
                    <p:option type="123"></p:option>
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
                    外网/卢工/马甸询价
                </p:cells>

                <p:pro field="willDate" cell="1"/>

                <p:pro field="mode" cell="1">
                    <p:option type="stockMode"></p:option>
                </p:pro>

                <p:pro field="mtype" cell="1">
                    <p:option type="stockManagerType"></p:option>
                </p:pro>

                <p:pro field="ptype" cell="1">
                    <p:option type="natureType"></p:option>
                </p:pro>

                <p:cells celspan="2" title="备注">
                    ${bean.target}
                </p:cells>

                <p:cells celspan="2" title="备注">
                    ${bean.description}
                </p:cells>

                <p:cells celspan="2" title="发货说明">
                    ${bean.consign}
                </p:cells>

                <p:cell title="核对状态" end="true">
                    ${my:get('pubCheckStatus', bean.checkStatus)}
                </p:cell>

                <p:cells celspan="2" title="核对">
                    ${bean.checks}
                </p:cells>

                <p:cells celspan="2" title="关联凭证">
                    <c:forEach items="${financeBeanList}" var="item">
                        <a href="../finance/finance.do?method=findFinance&id=${item.id}">${item.id}</a>
                        &nbsp;
                    </c:forEach>
                </p:cells>

                <p:cells celspan="2" title="关联跟单">
                    <c:forEach items="${stockWorkBeanList}" var="item">
                        <a href="../stock/work.do?method=findStockWork&id=${item.id}">${item.id}</a>
                        &nbsp;
                    </c:forEach>
                </p:cells>                   

            </p:table>
        </p:subBody>

        <p:tr />
        
        <p:subBody width="100%;align:left">
        <table style="width:100%">
			<tr class="content1" id="allocate" style=" align:left">
				<td align="right" width="150px">是否显示发货信息：</td>
				<td colspan="3">
				    <label><input type="radio" name="sendInfo" value="1" onClick="toggleSendInfo(this)">显示</label>
					<label><input type="radio" name="sendInfo" value="0" checked="checked" onClick="toggleSendInfo(this)">隐藏</label>								
				</td>
			</tr>

			<tr class="content1" id="distribution1" style="display: none;">
				<td align="right">发货方式：</td>
				<td colspan="3">
					<label><input type="radio" name="shipping" value="0" onClick="radio_click(this)">自提</label>
					<label><input type="radio" name="shipping" value="1" onClick="radio_click(this)">公司</label>
					<label><input type="radio" name="shipping" value="2" onClick="radio_click(this)">第三方快递</label>
					<label><input type="radio" name="shipping" value="3" onClick="radio_click(this)">第三方货运</label>
					<label><input type="radio" name="shipping" value="4" onClick="radio_click(this)">第三方快递+货运</label>
				</td>
			</tr>
			<tr class="content1" id="distribution2" style="display: none;">
				<td align="right">运输方式：</td>
				<td colspan="3">
					<select name="transport1" id="transport1" quick=true class="select_class" style="width:20%" >
					</select>&nbsp;&nbsp;
					<select name="transport2" id="transport2" quick=true class="select_class" style="width:20%" >
					</select>
				</td>
			</tr>
			<tr class="content1" id="distribution3" style="display: none;">
				<td align="right">运费支付方式：</td>
				<td colspan="3">
					<select name="expressPay" quick=true class="select_class" style="width:20%">
						<p:option type="deliverPay"></p:option>
					</select>&nbsp;&nbsp;
					<select name="transportPay" quick=true class="select_class" style="width:20%">
						<p:option type="deliverPay"></p:option>
					</select>
				</td>
			</tr>
			<tr class="content1" id="distribution4" style="display: none;">
				<td width="15%" align="right">送货地址：</td>
				<td width="35%">
					<select name="provinceId" quick=true onchange="change_city(this)" class="select_class" >
						<option>-</option>
						<c:forEach items="${provinceList}" var="province">
							<option value="${province.id}">${province.name}</option>
						</c:forEach>
						</select>&nbsp;&nbsp;
					<select name="cityId" quick=true class="select_class" >
						<option>-</option>
					</select>&nbsp;&nbsp;
				</td>
                         <td width="15%" align="right">收货人：</td>
                         <td width="35%">
                             <input type="text" name='receiver' id ='receiver' maxlength="10" required="required" /><font color="#FF0000">*</font>
                         </td>
			</tr>
			<tr class="content2" id="distribution5" style="display: none;">
				<td width="15%" align="right">地址：</td>

				<td width="35%">
					<input type="text" name="address" id="address" maxlength="60" required="required" /><font color="#FF0000">*</font>
				</td>

				<td width="15%" align="right">电话：</td>
				<td width="35%">
                             <input type="text" name="mobile" id ="mobile" maxlength="13" required="required"/><font color="#FF0000">*</font>
				</td>
			</tr>
     
        </table>
        </p:subBody>
        
        <p:tr />

        <p:subBody width="100%">
            <table width="100%" border="0" cellspacing='1' id="tables1">
                <tr align="center" class="content0">
                    <td width="10%" align="center">采购产品</td>
                    <td width="5%" align="center">采购数量</td>
                    <td width="5%" align="center">当前数量</td>
                    <td width="5%" align="center">是否询价</td>
                    <td width="5%" align="center">是否拿货</td>
                    <td width="10%" align="center">参考/实际价格</td>
                    <td width="10%" align="center">付款时间</td>
                    <td width="15%" align="center">供应商</td>
                    <td width="5%" align="center">付款</td>
                    <td width="5%" align="center">是否入库</td>
                    <td width="10%" align="center">合计金额</td>
                    <td width="10%" align="center">描述</td>

                </tr>

                <c:forEach items="${bean.itemVO}" var="item" varStatus="vs">
                    <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                        <td align="center" style="cursor: pointer;"
                            onMouseOver="showDiv('${item.id}')" onmousemove="tooltip.move()" onmouseout="tooltip.hide()"
                                ><a href="../product/product.do?method=findProduct&id=${item.productId}&detail=1">${item.productName}</a></td>

                        <td align="center">${item.amount}</td>

                        <td align="center">${item.productNum}</td>

                        <td align="center">${item.status == 0 ? "否" : "是"}</td>

                        <td align="center">${my:get('stockItemFech', item.fechProduct)}</td>

                        <td align="center">${my:formatNum(item.prePrice)}/${my:formatNum(item.price)}</td>


                        <td align="center">${item.nearlyPayDate}</td>

                        <td align="center">${item.providerName}</td>

                        <td align="center">${item.pay == 0 ? "未付款" : "已付款"}</td>

                        <c:if test="${item.hasRef == 0}">
                            <td align="center">
                                <font color=red>否</font>
                            </td>
                        </c:if>

                        <c:if test="${item.hasRef == 1}">
                            <td align="center">
                                <a href=../sail/out.do?method=findOut&fow=99&outId=${item.refOutId}>是</a>
                            </td>
                        </c:if>
						
                        <c:if test="${item.hasRef == 2}">
                            <td align="center">
                                <font color=red>部分入库</font>
                            </td>
                        </c:if>						

                        <td align="center">${my:formatNum(item.total)}</td>

                        <td align="center">${item.description}</td>

                    </tr>
                </c:forEach>
            </table>
        </p:subBody>

        <p:tr />

        <p:subBody width="100%">
            <table width="100%" border="0" cellspacing='1' id="tables">
                <tr align="center" class="content0">
                    <td width="10%" align="center">退货类型</td>
                    <td width="17%" align="center">产品名</td>
                    <td width="10%" align="center">退货数量</td>
                    <td width="8%" align="center">成本价格</td>
                    <td width="10%" align="center">源仓库</td>
                    <td width="10%" align="center">供应商</td>
                    <td width="10%" align="center">纳税实体</td>
                    <td width="10%" align="center">发票类型</td>
                    <td width="10%" align="center">退货备注</td>
                    <td width="5%" align="left">
                        <input type="button" accesskey="A" value="增加" class="button_class" onclick="addRow()">
                    </td>
                </tr>                                 
 
                <tr id="trCopy" style="display: none;">
                    <td align="center">
                        <select name="backType">
                            <option value="">-</option>
                            <option value="1">已入库退货</option>
                            <option value="2">未入库退货</option>
                        </select>
                    </td>
                    <td align="center">
                        <input type="text" name="productName"
							onclick="opens(this)"
							readonly="readonly"
							style="width: 200px; cursor: hand">
						<input type="hidden" name="productId" value="">	
                    </td>

                    <td align="center"><input type="number" name="amount" required><input type="hidden" name="amountLimit"></td>
                    <td align="center"><input type="number" name="price" required></td>
                    <td align="center"><input type="text" name="depot" required><input type="hidden" name="depotpartId">
                    <input type="hidden" name="storageId" value="${itemBase.storageId }">
                    </td>
                    <td align="center">
                        <select name="providerId">
                            <option value="">-</option>
                            <c:forEach items="${providerList}" var="providerItem">
                                <option value="${providerItem.id}">${providerItem.name}</option>
                            </c:forEach>
                        </select>                    
                    
                    </td>
                    <td align="center">
                        <select name="dutyId">
                            <option value="">-</option>
                            <c:forEach items="${dutyList}" var="dutyItem">
                                <option value="${dutyItem.id}">${dutyItem.name}</option>
                            </c:forEach>
                        </select>                    
                    </td>
                    <td align="center">
                        <select name="invoiceId">
                            <option value="">-</option>
                            <c:forEach items="${invoiceList}" var="invoiceItem">
                                <option value="${invoiceItem.id}">${invoiceItem.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td align="center">
                    <input type="text" name="description" required>
                    <!--  <input type="hidden" name="locationId">-->
                    </td>
                    <td align="left"><input type="button" value="删除"  class="button_class" onclick="removeTr(this)"></td>
                </tr>
  
                
                <c:forEach items="${baseList}" var="itemBase" varStatus="vs">
                  <c:choose>
                    <c:when test="${itemBase.outStatus eq '1' or itemBase.outStatus eq '3' }">
                    <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>  
	                    <td align="center">
	                        <select name="backType">
	                            <option value="" "${''==itemBase.buyReturnType?'selected':''}">-</option>
	                            <option value="1" "${'1'==itemBase.buyReturnType?'selected':''}">已入库退货</option>
	                            <option value="2" "${'2'==itemBase.buyReturnType?'selected':''}">未入库退货</option>
	                        </select>
	                    </td>
	
	                    <td align="center">
	                        
	                        <input type="text" name="productName"
								onclick="opens(this)"
								readonly="readonly"
								value="${itemBase.productName }"
								style="width: 200px; cursor: hand">
							<input type="hidden" name="productId" value="${itemBase.productId }">	                        
	                    </td>
	
	                    <td align="center"><input type="number" name="amount" value="${itemBase.amount }" required></td>
	                    <td align="center"><input type="number" name="price" value="${itemBase.price }" required></td>
	                    <td align="center"><input type="text" name="depot" value="${itemBase.depot }" required><input type="hidden" name="depotpartId" value="${itemBase.depotpartId }">
	                    <input type="hidden" name="storageId" value="${itemBase.storageId }">
	                    </td>
                        <td align="center">
                        <select name="providerId">
                            <option value="">-</option>
                            <c:forEach items="${providerList}" var="providerItem">
                                <option value="${providerItem.id}" "${providerItem.id==itemBase.providerId?'selected':''}">${providerItem.name}</option>
                            </c:forEach>
                        </select>

                        </td>
                        <td align="center">
                        <select name="dutyId">
                            <option value="">-</option>
                            <c:forEach items="${dutyList}" var="dutyItem">
                                <option value="${dutyItem.id}" "${dutyItem.id==itemBase.dutyId?'selected':''}">${dutyItem.name}</option>
                            </c:forEach>
                        </select>
                        </td>
                        <td align="center">
                        <select name="invoiceId">
                            <option value="">-</option>
                            <c:forEach items="${invoiceList}" var="invoiceItem">
                                <option value="${invoiceItem.id}" "${invoiceItem.id==itemBase.invoiceId?'selected':''}">${invoiceItem.name}</option>
                            </c:forEach>
                        </select>
                        </td>
	                    <td align="center">
	                    <input type="text" name="description" value="${itemBase.description }"  required>
	                    <!--  <input type="hidden" name="locationId">-->
	                    </td>
	                    <td align="left"><input type="button" value="删除"  class="button_class" onclick="removeTr(this)"></td>                    
                    </tr>                    
                    </c:when>
                    <c:otherwise>
                    <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>  
	                    <td align="center">
	                        <c:choose>
	                          <c:when test="${itemBase.buyReturnType eq '1' }">已入库退货</c:when>
	                          <c:when test="${itemBase.buyReturnType eq '2' }">未入库退货</c:when>
	                          <c:otherwise></c:otherwise>
	                        </c:choose>
	                    </td>
	
	                    <td align="center">
	                        ${itemBase.productName }
	                    </td>
	
	                    <td align="center">${itemBase.amount }</td>
	                    <td align="center">${itemBase.price }</td>
	                    <td align="center">${itemBase.depot }</td>
	                    <td align="center">${itemBase.provider }</td>
	                    <td align="center">${itemBase.duty }</td>
	                    <td align="center">${itemBase.invoiceType }</td>
	                    <td align="center">${itemBase.description }</td>
	                    <td align="left"></td>                    
                    </tr>                    
                    </c:otherwise>
                  </c:choose>

                </c:forEach>                 
                
            </table>
        </p:subBody>

        <p:tr />


        <p:line flag="1" />

        <p:button leftWidth="100%" rightWidth="0%">
            <div align="right">
                <input type="button" class="button_class"
                                      onclick="javascript:history.go(-1)"
                                      value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
                       
			    <input type="button" class="button_class"
						value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onClick="save()" />&nbsp;&nbsp;<input
						type="button" class="button_class" id="sub_b"
						value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onClick="sub()" />                       
            </div>
        </p:button>

        <p:message2></p:message2>
    </p:body></form>
</body>
</html>

