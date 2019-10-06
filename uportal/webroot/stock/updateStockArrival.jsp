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
    <script language="JavaScript" src="../stock_js/jquery-1.11.3.min.js"></script>
    <%--<script language="JavaScript" src="../stock_js/polyfiller.js"></script>--%>
    <script language="JavaScript" src="../stock_js/lodash3.10.1.min.js"></script>
    <script language="JavaScript" src="../stock_js/addStockArrival.js"></script>

    <script language="javascript">
//        webshims.setOptions('waitReady', false);
//        webshims.setOptions('forms-ext', {types: 'date'});
//        webshims.polyfill('forms forms-ext');

        function load()
        {
            loadForm();
//            showDate();
        }

        var jmap = {};
        <c:forEach items="${map}" var="item">
        jmap['${item.key}'] = "${item.value}";
        </c:forEach>

        var productMap = {};
        <c:forEach items="${bean.itemVO}" var="item">
            productMap['${item.productId}'] = parseInt("${item.amount}");
        </c:forEach>

//        console.log(productMap);

        function showDiv(id)
        {
            if (jmap[id] != null && jmap[id] != '')
                tooltip.showTable(jmap[id]);
        }

        function calDateInner(obj, name)
        {
            var tr = getTrObject(obj);

            var el = getInputInTr(tr, name);

            return calDate(el)
        }

    </script>

</head>
<body class="body_class" onload="load()" onkeydown="tooltip.bingEsc(event)">
<form action="../stock/stock.do" name="formEntry">
    <input type="hidden" name="method" value="updateStockArrival">
    <input type="hidden" name="stockId" value="${bean.id}">
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

	           <p:cells celspan="2" title="关联采购退货">
	           <c:forEach items="${stockBackOutList}" var="item">
	            <a href="../sail/out.do?method=findOut&fow=99&outId=${item.fullId}">${item.fullId}</a>
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

        <p:subBody width="100%">
            <table width="100%" border="0" cellspacing='1' id="tables1">
                <tr align="center" class="content0">
                    <td width="10%" align="center">采购产品</td>
                    <td width="5%" align="center">采购数量</td>
                    <td width="5%" align="center">已入库数量</td>
                    <td width="5%" align="center">当前库存</td>
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

                        <td align="center">${item.totalWarehouseNum}</td>

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
                    <td width="35%" align="center">采购产品</td>
                    <td width="10%" align="center">数量</td>
                    <td width="20%" align="center">出货日期</td>
                    <td width="20%" align="center">预计到货日期</td>
                    <td width="10%" align="center">是否拿货</td>
                    <td width="5%" align="left">
                        <input type="button" accesskey="A" value="增加" class="button_class" onclick="addRow()">
                    </td>
                </tr>

                <tr id="trCopy" class="content1" style="display: none;">
                    <td align="center">
                        <select name="productId">
                            <option value="">-</option>
                            <c:forEach items="${bean.itemVO}" var="item2" varStatus="vs">
                                <option value="${item2.productId}">${item2.productName}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="itemId" value="">
                    </td>

                    <td align="center"><input type="number" name="amount" required></td>

                    <td align="center"><input type="text" name="deliveryDate" ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "deliveryDate");' height='20px' width='20px'/></td>

                    <td align="center"><input type="text" name="arrivalDate" ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "arrivalDate");' height='20px' width='20px'/></td>

                    <td align="left"><input type="button" value="删除"  class="button_class" onclick="removeTr(this)"></td>
                </tr>

                <c:forEach items="${bean.stockItemArrivalVOs}" var="item" varStatus="vs">
                    <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                        <td align="center">
                            <input type="string" name="productName" value="${item.productName}" readonly>
                            <input type="hidden" name="productId" value="${item.productId}">
                            <input type="hidden" name="itemId" value="${item.id}">
                        </td>

                        <c:if test="${item.fechProduct == 0 ||item.fechProduct == 2}">
                            <td align="center"><input type="number" name="amount" value="${item.amount}" required></td>

                            <td align="center"><input type="text" name="deliveryDate" value="${item.deliveryDate}" ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "deliveryDate");' height='20px' width='20px'/></td>

                            <td align="center"><input type="text" name="arrivalDate" value="${item.arrivalDate}" ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "arrivalDate");' height='20px' width='20px'/></td>

                            <td align="center">${my:get('stockItemFech', item.fechProduct)}</td>

                            <td align="left"><input type="button" value="删除"  class="button_class" onclick="removeTr(this)"></td>
                        </c:if>

                        <c:if test="${item.fechProduct == 1}">
                            <td align="center"><input type="number" name="amount" value="${item.amount}" readonly></td>

                            <td align="center"><input type="date" name="deliveryDate" value="${item.deliveryDate}" readonly></td>

                            <td align="center"><input type="date" name="arrivalDate" value="${item.arrivalDate}" readonly></td>

                            <td align="center">${my:get('stockItemFech', item.fechProduct)}</td>

                            <td align="left"></td>
                        </c:if>
                    </tr>
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
                       name="b_submit" style="cursor: pointer"
                       value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">&nbsp;&nbsp;
            </div>
        </p:button>

        <p:message2></p:message2>
    </p:body></form>
</body>
</html>

