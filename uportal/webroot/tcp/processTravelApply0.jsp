<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<%@ taglib prefix="attach" tagdir="/WEB-INF/tags/attach" %>

<html>
<head>
<p:link title="差旅费申请" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/key.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../tcp_js/travelApply.js"></script>
<script language="javascript">
function load()
{
    showFlowLogTr();
    
    <c:if test="${bean.status == 22 && bean.borrow == 1}">
    addTrInner("tables_realPay", "trCopy");
    </c:if>
}

function addTr()
{
	addTrInner("tables_realPay", "trCopy");
}

function processBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    var msg = '';
    
    var checkFun = null;
    
    if ("0" == opr)
    {
        msg = '确定通过差旅费申请?';
    }
    
    if ("1" == opr)
    {
        msg = '确定驳回到差旅费申请到初始?';
    }
    
    if ("2" == opr)
    {
        msg = '确定驳回到差旅费申请到上一步?';
    }
    
    if ($O('processer'))
    {
	    if ("0" != opr)
	    {
	        $O('processer').oncheck = '';
	    }
	    else
	    {
	        $O('processer').oncheck = 'notNone';
	    }
    }
    
    
    <c:if test="${bean.status == 22}">
    if ("0" == opr)
    checkFun = checkMoney;
    else
    removePay();
    </c:if>
    
    submit(msg, null, checkFun);
}

function removePay()
{
    //remove tr
    var list = formEntry.elements;
    
    if (list)
    {
        for (var i = 0; i < list.length; i++)
        {
            if (list[i].name== 'pay_del_bu')
            list[i].onclick.apply(list[i]);
        }
    }
}

var showTr = false;

function showFlowLogTr()
{
    $v('flowLogTr', showTr);
    
    showTr = !showTr;
}

function getBank(obj)
{
    g_obj.value = obj.pname;
    
    var hobj = getNextInput(g_obj.nextSibling);
    
    hobj.value = obj.value;
}

function getNextInput(el)
{
    if (el.tagName && el.tagName.toLowerCase() == 'input')
    {
        return el;
    }
    else
    {
        return getNextInput(el.nextSibling);
    }
}

function checkMoney()
{
    var total = parseFloat('${bean.showBorrowTotal}');
    
    var mels = document.getElementsByName('money');
    
    var addMoney = 0.0;
    
    for (var i = 0; i < mels.length; i++)
    {
        if (mels[i].value != '')
        {
            addMoney += parseFloat(mels[i].value);
        }
    }
    
    if (compareDouble(addMoney, total) != 0)
    {
        alert('付款金额必须是${bean.showBorrowTotal}');
        
        return false;
    }
    
    return true;
}

</script>
</head>

<body class="body_class" onload="load()">
<c:if test="${bean.status == 11  || bean.status == 22}">
<form name="formEntry" action="../tcp/apply.do?method=processTravelApplyBean"   enctype="multipart/form-data" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="type" value="${bean.type}"> 
</c:if>
<c:if test="${bean.status != 11}">
<form name="formEntry" action="../tcp/apply.do"  method="post">
<input type="hidden" name="method" value="processTravelApplyBean"> 
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="type" value="${bean.type}"> 
<input type="hidden" name="status" value="${bean.status}"> 
</c:if>

<p:navigation height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
        onclick="javascript:history.go(-1)">待我处理</span> &gt;&gt; 差旅费申请处理</td>
    <td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>出差申请及借款-${my:get('tcpStatus', bean.status)}</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.tcp.bean.TravelApplyBean" opr="2"/>
	    
		<p:table cells="2">
		    <p:cell title="处理流程" width="8" end="true">
            ${bean.flowDescription}
            </p:cell>
            
            <p:pro field="id" cell="0"/>

            <p:pro field="stafferId" value="${bean.stafferName}"/>
            <p:pro field="departmentId" value="${bean.departmentName}"/>


            <p:pro field="stype" cell="0">
                <p:option type="tcpStype" empty="true"/>
            </p:pro>
            
            <p:pro field="name"/>
            
            <p:pro field="status">
                <p:option type="tcpStatus"></p:option>
            </p:pro>
            
            <p:pro field="beginDate"/>
            <p:pro field="endDate"/>
            
            <p:pro field="srcCity" innerString="onclick='selectCity(this)' style='cursor: pointer;'"/>
            <p:pro field="destCity" innerString="onclick='selectCity(this)' style='cursor: pointer;'"/>
            
            <p:pro field="borrow" innerString="onchange='borrowChange()'">
                <p:option type="travelApplyBorrow"></p:option>
            </p:pro>
            
            <p:cell title="借款人">
            ${bean.borrowStafferName}
            </p:cell>
            
            <p:pro field="showTotal"/>
            <p:pro field="showBorrowTotal"/>

            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
            <p:cell title="附件" width="8" end="true">
            <c:forEach items="${bean.attachmentList}" var="item">
            <a href="../tcp/apply.do?method=downAttachmentFile&id=${item.id}" title="点击下载附件">${item.name}</a>
            <br>
            <br>
            </c:forEach>
            </p:cell>
            
    
            
            <p:cell title="处理人" width="8" end="true">
            ${bean.processer}
            </p:cell>
            
            <p:cell title="关联收付款单" width="8" end="true">
                <c:forEach items="${billList}" var="item">
                <a href="../finance/bill.do?method=findBill&id=${item.id}">${item.id}</a>&nbsp;&nbsp;
                </c:forEach>
            </p:cell>
            
            <p:cell title="关联凭证" width="8" end="true">
                <c:forEach items="${financeList}" var="item">
                <a href="../finance/finance.do?method=findFinance&id=${item.id}">${item.id}</a>&nbsp;&nbsp;
                </c:forEach>
            </p:cell>

			<c:if test="${bean.status == 200}">
			<p:cell title="是否合规" width="8" end="true">
				<select name="compliance" class="select_class" style="width: 240px" onCheck="notNone" >
					<option value="">--</option>
					<option value="0">合规</option>
					<option value="1">不合规</option>
				</select><font color="#FF0000">*</font> 
			</p:cell>
			</c:if>

        </p:table>
	</p:subBody>
	
	<p:title>
        <td class="caption">
         <strong>差旅费明细</strong>
        </td>
    </p:title>

    <p:line flag="0" />

    <p:subBody width="98%">
    
        <p:table cells="2" id="traTable">

            <p:pro field="showAirplaneCharges"/>
            <p:pro field="showTrainCharges"/>
            
            <p:pro field="showBusCharges"/>
            <p:pro field="showHotelCharges"/>
            
            <p:pro field="showEntertainCharges"/>
            <p:pro field="showAllowanceCharges"/>
            
            <p:pro field="showOther1Charges"/>
            <p:pro field="showOther2Charges"/>

        </p:table>
    </p:subBody>
	
    <p:title>
        <td class="caption">
         <strong>其他申请费用明细(包含差旅费)</strong>
        </td>
    </p:title>

    <p:line flag="0" />
	
	<tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">开始日期</td>
                        <td width="15%" align="center">结束日期</td>
                        <td width="15%" align="center">预算项</td>
                        <td width="10%" align="center">申请金额</td>
                        <td width="40%" align="center">备注</td>
                    </tr>
                    <c:forEach items="${bean.itemVOList}" var="item">
                    <tr align="center" class="content1">
                        <td width="15%" align="center">${item.beginDate}</td>
                        <td width="15%" align="center">${item.endDate}</td>
                        <td width="15%" align="center">${item.feeItemName}</td>
                        <td width="10%" align="center">${my:formatNum(item.moneys / 100.0)}</td>
                        <td width="40%" align="center"><c:out value="${item.description}"/></td>
                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>
        </td>
    </tr>

	<p:title>
        <td class="caption">
         <strong>收款明细</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="pay_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">收款方式</td>
                        <td width="15%" align="center">开户银行</td>
                        <td width="15%" align="center">户名</td>
                        <td width="20%" align="center">收款帐号</td>
                        <td width="5%" align="center">开户省份</td>
                        <td width="5%" align="center">开户城市</td>
                        <td width="10%" align="center">收款金额</td>
                        <c:if test="${bean.status == 20 && bean.borrow == 1}">
                        <td width="10%" align="center">稽核金额<font color="#FF0000">*</font></td>
                        <td width="25%" align="center">稽核备注<font color="#FF0000">*</font></td>
                        </c:if>
                        <c:if test="${bean.status != 20}">
                        <td width="10%" align="center">稽核金额</td>
                        <td width="25%" align="center">稽核备注</td>
                        </c:if>
                    </tr>
                    <c:forEach items="${bean.payList}" var="item">
                    <tr align="center" class="content1">
                        <td align="center">${my:get('travelApplyReceiveType', item.receiveType)}</td>
                        <td align="center">${item.bankName}</td>
                        <td align="center">${item.userName}</td>
                        <td align="center">${item.bankNo}</td>
                        <td align="center">${item.bankprovince}</td>
						<td align="center">${item.bankcity}</td>
                        <td align="center" title='<c:out value="${item.description}"/>'>${my:formatNum(item.moneys / 100.0)}</td>
                        <c:if test="${bean.status == 20 && bean.borrow == 1}">
                        <td align="center">
                         <input type="text" style="width: 100%"
                    		name="p_cmoneys" value="${my:formatNum(item.moneys / 100.0)}" oncheck="notNone;isFloat3">
                    	<input type="hidden" name="p_cid" value="${item.id}">
                    	</td>
                        <td align="center"><textarea name="p_cdescription" rows="3" style="width: 100%" oncheck="notNone;maxLength(600)">ok</textarea></td>
                        </c:if>
                        <c:if test="${bean.status != 20}">
                        <td align="center"><font color="red">${my:formatNum(item.cmoneys / 100.0)}</font></td>
                        <td align="center"><c:out value="${item.cdescription}"/></td>
                        </c:if>
                    </tr>
                    </c:forEach>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <%@include file="share_process0.jsp"%>
    
    <p:title>
        <td class="caption">
         <strong><span style="cursor: pointer;" onclick="showFlowLogTr()">流程日志(点击查看)</span></strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="flowLogTr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables">
                    <tr align="center" class="content0">
                        <td width="10%" align="center">审批人</td>
                        <td width="10%" align="center">审批动作</td>
                        <td width="10%" align="center">前状态</td>
                        <td width="10%" align="center">后状态</td>
                        <td width="45%" align="center">意见</td>
                        <td width="15%" align="center">时间</td>
                    </tr>

                    <c:forEach items="${logList}" var="item" varStatus="vs">
                        <tr class='${vs.index % 2 == 0 ? "content1" : "content2"}'>
                            <td align="center">${item.actor}</td>

                            <td  align="center">${item.oprModeName}</td>

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

        </td>
    </tr>
    
    <c:if test="${bean.status == 22}">
	    <p:title>
	        <td class="caption">
	         <strong>财务支付-支付金额:${bean.showBorrowTotal}</strong>
	        </td>
	    </p:title>
	
	    <p:line flag="0" />
	    
	    <tr>
	        <td colspan='2' align='center'>
	        <table width="98%" border="0" cellpadding="0" cellspacing="0"
                class="border">
                <tr>
                    <td>
                    <table width="100%" border="0" cellspacing='1'>
                    <tr align="center" class="content0">
                        <td width="20%" align="center">纳税实体</td>
                        <td align="left" colspan="3">
                        <select name="dutyId" style="width: 240px" oncheck="notNone">
			                <p:option type="$dutyList" empty="true"/>
			            </select>
                        <font color=red>*</font></td>
                    </tr>
                    </table>
                    </td>
                </tr>
            </table>
            <c:if test="${bean.status == 22 && bean.borrow == 1}">
	        <table width="98%" border="0" cellpadding="0" cellspacing="0"
	            class="border">
	            <tr>
	                <td>
	                <table width="100%" border="0" cellspacing='1' id="tables_realPay">
                    <tr align="center" class="content0">
                        <td width="30%" align="center">银行</td>
                        <td width="30%" align="center">付款类型</td>
                        <td width="15%" align="center">金额</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
               		</table>
	                </td>
	            </tr>
	        </table>
	        </c:if>
	
	        </td>
	    </tr>
	    
	    <p:line flag="0" />
    </c:if>
    
    <p:title>
        <td class="caption">
         <strong>审核-${my:get('tcpStatus', bean.status)}</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">审批意见</td>
                        <td align="left">
                        <textarea rows="3" cols="55" oncheck="notNone;maxLength(200);" name="reason"></textarea>
                        <font color="red">*</font>
                        </td>
                    </tr>
                    <c:if test="${bean.status == '22'}">
                        <attach:attachmentsTag/>
                    </c:if>
                    <c:if test="${requestScope.pluginType == 'group'}">
                    <tr align="center" class="content1">
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="selectNext('${pluginType}', '${pluginValue}')">&nbsp;&nbsp;
                        </td>
                    </tr>
                    </c:if>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
        <div align="right">
        <input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;通 过&nbsp;&nbsp;" onclick="processBean(0)">
          &nbsp;&nbsp;
          <c:if test="${token.reject == 1}">
          <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;驳回到初始&nbsp;&nbsp;" onclick="processBean(1)">
          </c:if>
          <c:if test="${token.rejectToPre == 1}">
          <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;驳回到上一步&nbsp;&nbsp;" onclick="processBean(2)">
          </c:if>
           &nbsp;&nbsp;<input type="button" name="pr"
            class="button_class" onclick="pagePrint()"
            value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">
        </div>
    </p:button>
	
	<p:message2/>
</p:body>
</form>

<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td width="50%" align="center">
         <input name="bankName" type="text" readonly="readonly" style="width: 100%;cursor: pointer;" oncheck="notNone" onclick="selectBank(this)">
         <input type="hidden" name="bankId" value=""> 
         </td>
         <td width="25%" align="center">
         <select name="payType" style="width: 100%" class="select_class" oncheck="notNone">
                <p:option type="outbillPayType"></p:option>
                </select>
         </td>
         <td width="20%" align="center"><input type="text" style="width: 100%"
                    name="money" value="" oncheck="notNone;isFloat">
         </td>
         <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
</table>

</body>
</html>

