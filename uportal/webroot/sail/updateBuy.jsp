<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
<p:link title="填写入库单" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/cnchina.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../sail_js/addBuy.js"></script>
<script language="javascript">
<%@include file="../sail_js/buy_stock_in.jsp"%>

var duesMap = {};
var duesTypeMap = {};
<c:forEach items="${dutyList}" var="item">
duesMap['${item.id}'] = '${item.dues}';
duesTypeMap['${item.id}'] = '${item.mtype}';
</c:forEach>

/**
 * 查询库存
 */
function opens(obj)
{

	var ret = checkCurrentUser();

	if (!ret)
	{
		window.parent.location.reload();

		return false
	}
	
    oo = obj;
    
    if ($$('dutyId') == '')
    {
        alert('请选择纳税实体');
        
        return false;
    }
    
    //只能选择永银和经纬 90201008080000000001/A1201112260004531364
    if ($$('outType') == 1 || true)
    {
        if ($$('dutyId') != '90201008080000000001' && $$('dutyId') != 'A1201112260004531364')
        {
            alert('入库单的时候纳税实体只能选择永银收藏品或者经纬公司');
            return false;
        }
    }
    
    var mtype = duesTypeMap[$$('dutyId')];
    
    if (g_url_query == 0)
    window.common.modal('../depot/storage.do?method=rptQueryStorageRelationInDepot&costMode=1&queryType=1&showAbs=1&load=1&depotId='+ $$('location') + '&code=' + obj.productcode + '&mtype=' + mtype+ '&tmpdutyid=' + $("#dutyId").val());
    else
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1&abstractType=0&status=0' + '&mtype=' + mtype+  '&tmpdutyid=' + $("#dutyId").val());
}

</script>
</head>
<body class="body_class" onload="load()">
<form name="outForm" method=post action="../sail/out.do"><input
	type=hidden name="method" value="addOut" /><input type=hidden
	name="nameList" /> <input type=hidden name="idsList" /> <input
	type=hidden name="unitList" /> <input type=hidden name="amontList" />
<input type=hidden name="priceList" /> <input type=hidden
	name="totalList" /> <input type=hidden name="totalss" /> <input
	type=hidden name="customerId" value="${bean.customerId}"/> 
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
<input type=hidden name="inputPriceList" value="" />
<input type=hidden name="customercreditlevel" value="" />
<input type=hidden name="showCostList" value="" />
<input type=hidden name="id" value="${bean.id}" />
<input type=hidden name="fullId" value="${bean.fullId}" />
<input type="hidden" name="reserve9" value="${bean.reserve9}"/>	

<input type=hidden name="oprType" value="1">
<p:navigation
	height="22">
	<td width="550" class="navigation">库单管理 &gt;&gt; 填写入库单(入库单都是正数增加库存,负数减少库存)</td>
				<td width="85"></td>
</p:navigation> <br>

<table width="95%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td valign="top" colspan='2'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<!--DWLayoutTable-->
			<tr>
				<td width="784" height="6"></td>
			</tr>
			<tr>
				<td align="center" valign="top">
				<div align="left">
				<table width="90%" border="0" cellspacing="2">
					<tr>
						<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="10">
							<tr>
								<td width="35">&nbsp;</td>
								<td width="6"><img src="../images/dot_r.gif" width="6"
									height="6"></td>
								<td class="caption"><strong>填写入库单信息:</strong>
								<font color="blue">源仓库：</font>
								<select name="location" class="select_class" values="${bean.location}" onchange="clearsAll()">
									<c:forEach items='${locationList}' var="item">
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								</select>
								</td>
							</tr>
						</table>
						</td>
					</tr>


				</table>
				</div>
				</td>
			</tr>
		</table>
		</td>
	</tr>


	<tr>
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1'>
					<tr class="content2">
						<td width="15%" align="right">入库日期：</td>

						<td width="35%"><input type="text" name="outTime"
							value="${bean.outTime}" maxlength="20" size="20"
							readonly="readonly"><font color="#FF0000">*</font></td>

							<td width="15%" align="right">入库类型：</td>
							<td width="35%"><select name="outType" class="select_class" onchange="managerChange()" readonly=true values="${bean.outType}">
								<p:option type="outType_in"></p:option>
							</select><font color="#FF0000">*</font></td>
						
					</tr>
					
					<tr class="content1" id = "forceBuy_tr">
						<td align="right" id="outd">入库事由：</td>
						<td colspan="3">
						<select name="forceBuyType" class="select_class" onchange="forceBuyTypeChange()" readonly=true values="${bean.forceBuyType}">
							<p:option type="forceBuyTypes"></p:option>
						</select><font color="#FF0000">*</font></td>

					</tr>

					<tr class="content2" id = "refOutFullId_tr">
						<td align="right" id="outd">原销售单号：</td>
						<td colspan="3"><input type="text" name="refOutFullId" readonly=true value="${bean.refOutFullId}" 
							maxlength="40" size="40"
							><font color="#FF0000">*</font>						
						</td>
						
					</tr>
					
					<tr class="content1" id = "staffer_tr">
						<td align="right" id="outd">业务员/职员：</td>
						<td colspan="3">
						<input type="text" name="stafferName1" maxlength="14"  readonly=true value="${bean.stafferName1}"  size="20" 
                            onclick="selectStaffer()" style="cursor: pointer;"
                            readonly="readonly">
                            <font color="#FF0000">*</font>
                        </td>
												
					</tr>
					
					<tr class="content2" id="customer_tr">
                        <td align="right" id="outd">客户：</td>
                        <td colspan="3">
                        <input type="text" name="customerName1" maxlength="14" readonly=true value="${bean.customerName}" size="60" 
                            onclick="selectCustomer1()" style="cursor: pointer;"
                            readonly="readonly">
                            <font color="#FF0000">*</font>
                        </td>
                    </tr>

					<tr class="content1" id="dir_tr">
						<td align="right" id="outd">目的仓库：</td>
						<td colspan="3">
						<select name="destinationId" class="select_class" values="${bean.destinationId}" oncheck="notNone;">
                                    <c:forEach items='${dirLocationList}' var="item">
                                        <option value="${item.id}">${item.name}</option>
                                    </c:forEach>
                                </select>
						<font color="#FF0000">*</font></td>
					</tr>
					
					<tr class="content2" id="pro_tr">
                        <td align="right" id="outd">供应商：</td>
                        <td colspan="3">
                        <input type="text" name="customerName" maxlength="14" value="${bean.customerName}" size="60"
                            onclick="selectCustomer()" style="cursor: pointer;"
                            readonly="readonly">
                        </td>
                    </tr>

					<tr class="content2" id="duty_tr">
					    <td align="right">纳税实体：</td>
                        <td colspan="3">
                        <select name="dutyId" id="dutyId" class="select_class" style="width: 240px" values="${bean.dutyId}" oncheck="notNone;" readonly=true>
                            <option value="">--</option>
                            <c:forEach items="${dutyList}" var="item">
                            <option value="${item.id}">${item.name}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                       
                    </tr>
                    
                    <tr class="content1" id="invoice_tr">
                        <td align="right">发票类型：</td>
                        <td colspan="3">
                        <select name="invoiceId" class="select_class" head="发票类型" style="width: 400px" values="${bean.invoiceId}">
                           <option value="">没有发票</option>
                            <c:forEach items="${invoiceList}" var="item">
                            <option value="${item.id}">${item.fullName}</option>
                            </c:forEach>
                        </select>
                        <font color="#FF0000">*</font></td>
                    </tr>
                    

					<tr class="content2">
						<td align="right">入库单备注：</td>
						<td colspan="3"><textarea rows="3" cols="55" oncheck="notNone;"
							name="description"><c:out value="${bean.description}"/></textarea>
							<font color="#FF0000">*</font>
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
		<td background="../images/dot_line.gif" colspan='2'></td>
	</tr>

	<tr>
		<td height="10" colspan='2'></td>
	</tr>

	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border">
			<tr>
				<td>
				<table width="100%" border="0" cellspacing='1' id="tables">
					<tr align="center" class="content0">
						<td width="20%" align="center">品名</td>
						<td width="5%" align="center">单位</td>
						<td width="5%" align="center">数量</td>
						<td width="10%" align="center">单价</td>
						<td width="10%" align="left">金额<span id="total"></span></td>
						<td width="10%" align="center">成本</td>
						<td width="15%" align="center">类型</td>
						<td width="10%" align="center">虚料金额</td>
						<td width="5%" align="left"><input type="button" accesskey="A"
							value="增加" class="button_class" onclick="addTr()"></td>
					</tr>

					<tr class="content1" id="trCopy" style="display: none;">
						<td>
						<input type="text" name="productName"
							onclick="opens(this)"
							productid="" 
							productcode="" 
							price=""
							virtualprice=""
							stafferid=""
							depotpartid=""
							readonly="readonly"
							style="width: 100%; cursor: hand">
						</td>

						<td><select name="unit" style="WIDTH: 50px;">
							<option value="套">套</option>
							<option value="枚">枚</option>
							<option value="个">个</option>
							<option value="本">本</option>
							<option value="批">批</option>
						</select></td>

						<td align="center"><input type="text"
							style="width: 100%" maxlength="6" onkeyup="cc(this)"
							name="amount"></td>

						<td align="center"><input type="text" readonly="readonly"
							style="width: 100%" maxlength="8" onkeyup="cc(this)"
							onblur="blu(this)" name="price"></td>

						<td align="center"><input type="text"
							value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

						<td align="center"><input type="text" readonly="readonly"
							style="width: 100%" name="desciprt"></td>
							
						<td align="center"><input type="text" readonly="readonly"
							style="width: 100%" name="rstafferName"></td>

						<td align="center"><input type="text"
												  value="0.00" readonly="readonly" style="width: 100%" name="virtualPrice"></td>
						<td align="center"></td>
					</tr>

					<tr class="content2">
                        <td><input type="text" name="productName" id="unProductName"
                            onclick="opens(this)" 
                            productid="${fristBase.productId}" 
                            productcode="" 
                            price="${my:formatNum(fristBase.costPrice)}"
                            stafferid="${fristBase.owner}"
                            depotpartid="${fristBase.depotpartId}"
                            readonly="readonly"
                            style="width: 100%; cursor: pointer"
                            value="${fristBase.productName}"></td>

                        <td><select name="unit" style="WIDTH: 50px;" values="${fristBase.unit}">
                            <option value="套">套</option>
                            <option value="枚">枚</option>
                            <option value="个">个</option>
                            <option value="本">本</option>
                            <option value="批">批</option>
                        </select></td>

                        <td align="center"><input type="text" style="width: 100%" id="unAmount" value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>

                        <td align="center"><input type="text" style="width: 100%" id="unPrice" readonly="readonly" value="${my:formatNum(fristBase.price)}" 
                            maxlength="8" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text" value="${fristBase.value}"
                            value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

                        <td align="center"><input type="text" id="unDesciprt" readonly="readonly" value="${my:formatNum(fristBase.costPrice)}"
                            style="width: 100%" name="desciprt"></td>
                            
                        <td align="center"><input type="text" id="unRstafferName" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
                            style="width: 100%" name="rstafferName"></td>

						<td align="center"><input type="text" readonly="readonly" value="${my:formatNum(fristBase.virtualPrice)}"
												  style="width: 100%" name="virtualPrice"></td>
                        <td align="center"><input type=button value="清空"  class="button_class" onclick="clears()"></td>
                    </tr>
                    
                    <c:forEach items="${lastBaseList}" var="fristBase" varStatus="vs">
                    <tr class="content2">
                        <td><input type="text" name="productName"
                            onclick="opens(this)" 
                            productid="${fristBase.productId}" 
                            productcode="" 
                            price="${my:formatNum(fristBase.costPrice)}"
                            stafferid="${fristBase.owner}"
                            depotpartid="${fristBase.depotpartId}"
                            readonly="readonly"
                            style="width: 100%; cursor: pointer"
                            value="${fristBase.productName}"></td>

                        <td><select name="unit" style="WIDTH: 50px;" values="${fristBase.unit}">
                            <option value="套">套</option>
                            <option value="枚">枚</option>
                            <option value="个">个</option>
                            <option value="本">本</option>
                            <option value="批">批</option>
                        </select></td>

                        <td align="center"><input type="text" style="width: 100%"  value="${fristBase.amount}"
                            maxlength="6" onkeyup="cc(this)" name="amount"></td>

                        <td align="center"><input type="text" style="width: 100%"  readonly="readonly" value="${my:formatNum(fristBase.price)}"
                            maxlength="11" onkeyup="cc(this)" onblur="blu(this)" name="price"></td>

                        <td align="center"><input type="text" value="${my:formatNum(fristBase.value)}"
                            value="0.00" readonly="readonly" style="width: 100%" name="value"></td>

                        <td align="center"><input type="text"  readonly="readonly" value="${my:formatNum(fristBase.costPrice)}"
                            style="width: 100%" name="desciprt"></td>
                            
                        <td align="center"><input type="text" readonly="readonly" value="${fristBase.depotpartName}-->${fristBase.ownerName}"
                            style="width: 100%" name="rstafferName"></td>

                        <td align="center"><input type=button value="删除" class=button_class onclick="removeTr(this)"></td>
                    </tr>
                    </c:forEach>
                    
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
		<td width="100%">
		<div align="right"><input type="button" class="button_class"
			value="&nbsp;&nbsp;保 存&nbsp;&nbsp;" onClick="save()" />
			&nbsp;&nbsp;<input
			type="button" class="button_class" id="sub_b"
			value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onClick="sub()" /></div>
		</td>
		<td width="0%"></td>
	</tr>

</table>
</form>
</body>
</html>

