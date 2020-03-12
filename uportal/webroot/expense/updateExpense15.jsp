<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="中收报销" guid="true"/>
<script language="JavaScript" src="../js/string.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../tcp_js/expense.js"></script>
<script language="javascript">

function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    if ($O('processer'))
    {
	    if ("0" == opr)
	    {
	        $O('processer').oncheck = '';
	    }
	    else
	    {
	        $O('processer').oncheck = 'notNone';
	    }
    }
    
    if ("0" == opr)
    {
        submit('确定修改中收报销?', null, checks2);
    }
    
    if ("1" == opr)
    {
        submit('确定提交中收报销?', null, checks2);
    }
    
    if ("2" == opr)
    {
        submit('稽核修改费用,每项费用只能减少.确定提交稽核修改中收报销?', null, checks2);
    }
}

function load()
{
	$v('tr_att_more', false);
	
	loadForm();
	
	payTypeChange();

    loadBudget();
}

function del(id)
{
    $O('span_' + id).innerHTML = '';
    
    $O('attacmentIds').value = $O('attacmentIds').value.delSubString(id + ';')
}

function getTravelApply(oos)
{
    var oo = oos[0];
    
    $("#refMoney").val(oo.pbtotal);
    
    $("#refId").val(oo.value);
}
function selectProvince(obj)
{
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryProvince&load=1&selectMode=1');
}
function getProvinces(oos)
{
    var obj = oos[0];
    cityObj.value = obj.pname;
    $O('provinceId').value=obj.value;
}
function selectOpeningBank(obj)
{
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryOpeningBank&load=1&selectMode=1');
}

function getOpeningBank(oos)
{
    var obj = oos[0];
    cityObj.value = obj.pname;
}
</script>
</head>

<body class="body_class" onload="load()">
<form name="formEntry" action="../tcp/expense.do?method=addOrUpdateExpense" enctype="multipart/form-data" method="post">
<input type="hidden" name="oprType" value="0"> 
<input type="hidden" name="addOrUpdate" value="1"> 
<input type="hidden" name="id" value="${bean.id}"> 
<input type="hidden" name="type" value="${bean.type}"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input
    type="hidden" name="attacmentIds" value="${attacmentIds}">
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" name="provinceId" value="">
<p:navigation height="22">
	<td width="550" class="navigation">中收报销</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>中收报销</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.tcp.bean.ExpenseApplyBean" opr="1"/>
	    
	    <div id="main_div">
		<p:table cells="2" id="main_t">

            <p:pro field="stafferId" value="${bean.stafferName}"/>
            <p:pro field="departmentId" value="${bean.departmentName}"/>
            
            <p:pro field="specialType" cell="0">
                <p:option type="expenseSpecialType"/>
            </p:pro>
            
            <p:pro field="name" cell="0" innerString="size=60"/>
            
            <p:pro field="beginDate"/>
            <p:pro field="endDate"/>
            
            <p:pro field="refId">
                  <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                    class="button_class" onclick="selectTravelApply()">&nbsp;
            </p:pro>
            
            <p:pro field="refMoney" innerString="readonly=true" value="${my:formatNum(bean.refMoney / 100.0)}"/>
            
            <p:pro field="payType" innerString="onchange='payTypeChange()'">
                <p:option type="expensePayType"></p:option>
            </p:pro>
            
            <p:pro field="lastMoney" value="${my:formatNum(bean.lastMoney / 100.0)}"/>
            
            <p:pro field="ticikCount"/>
            
            <p:pro field="remark"/>

            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
            <p:cell title="原附件" width="8" end="true">
            <c:forEach items="${bean.attachmentList}" var="item" varStatus="vs">
            <span id="span_${item.id}"><img src=../images/oa/attachment.gif>
            <a target="_blank" href="../tcp/apply.do?method=downAttachmentFile&id=${item.id}">${item.name}</a>&nbsp;
            <c:if test="${update == 1}">
            <a title="删除附件" href="javascript:del('${item.id}')"> <img
                        src="../images/oa/del.gif" border="0" height="15" width="15"></a>
            </c:if>
            </span>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <c:if test="${!vs.last}">
            <br>
            </c:if>
            </c:forEach>
            </p:cell>
            
            <p:cell title="附件" width="8" end="true"><input type="file" name="atts" size="70" class="button_class">  
            <font color="blue"><span style="cursor: pointer;" onclick="showMoreAtt()" >【更多附件】 </span><b>如果是模板报销只能上传通用报销模板</b></font>
            </p:cell>
            
            <p:cell title="附件N" width="8" id="att_more" end="true">
            <input type="file" name="atts0" size="70" class="button_class"> <br> 
            <input type="file" name="atts1" size="70" class="button_class"> <br> 
            <input type="file" name="atts2" size="70" class="button_class"> <br> 
            <input type="file" name="atts3" size="70" class="button_class"> <br> 
            </p:cell>

        </p:table>
        </div>
	</p:subBody>
	
	
    <p:title>
        <td class="caption">
         <strong>中收明细</strong>
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
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
                    
                    <c:forEach items="${bean.itemVOList}" var="itemEach" varStatus="vs">
                    <tr class="content1">
			         <td align="left">
			         <input type=text name = 'i_beginDate'  id = 'i_beginDate'  
			         oncheck = ""  value = '${itemEach.beginDate}'  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_beginDate");' height='20px' width='20px'/>
			         </td>
			         <td align="left">
			         <input type=text name = 'i_endDate'  id = 'i_endDate'  
			         oncheck = ""  value = '${itemEach.endDate}'  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_endDate");' height='20px' width='20px'/>
			         </td>
			         <td align="left">
			         <select name="i_feeItem" class="select_class" style="width: 100%;" oncheck="notNone" values="${itemEach.feeItemId}">
			             <option value="">--</option>
			             <c:forEach var="item" items="${feeItemList}">
			                 <option value="${item.id}">${item.name}</option>
			             </c:forEach>
			         </select>
			         </td>
			         
			         <td align="left"><input type="text" style="width: 100%"
			                    name="i_moneys" value="${itemEach.showMoneys}" oncheck="notNone;isFloat3"></td>
			         <td align="left">
			         <textarea name="i_description" rows="3" style="width: 100%"><c:out value="${itemEach.description}"/></textarea>
			         </td>
			        <td width="5%" align="center"><input type=button
			            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
			        </tr>
			        </c:forEach>
			        
                </table>
                </td>
            </tr>
        </table>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="totalTables">
                    <tr align="left" class="content0">
                        <td align="left" width="20%" align="center">
                        <input type="button" class="button_class" id="sum_b"
                        value="&nbsp;合计金额&nbsp;" onclick="sumTotal()">
                        </td>
                        <td width="90%" align="left">
                        <input type="text" 
                            name="allTotal" class="input_class" readonly="readonly" oncheck="notNone" value="${bean.showTotal}">
                            <font color="#FF0000">*</font>
                        </td>
                    </tr>
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
                        <td width="25%" align="center">备注</td>
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addPayTr()"></td>
                    </tr>
                    
                     <c:forEach items="${bean.payList}" var="itemEach" varStatus="vs">
                     <tr class="content1">
			         <td align="left">
			         <select name="p_receiveType" class="select_class" style="width: 100%;" oncheck="notNone" values="${itemEach.receiveType}">
			            <p:option type="travelApplyReceiveType" empty="true"></p:option>
			         </select>
			         </td>
			         
			         <td align="left"><input type="text" style="width: 100%"
			                    name="p_bank" value="${itemEach.bankName}" onclick='selectOpeningBank(this)' readonly=readonly oncheck="notNone;" style='cursor: pointer;'>
			         </td>
			         
			         <td align="left">
			         <input type="text" style="width: 100%"
			                    name="p_userName" value="${itemEach.userName}" oncheck="notNone;">
			         </td>
			         
			         <td align="left">
			         <input type="text" style="width: 100%"
			                    name="p_bankNo" value="${itemEach.bankNo}" oncheck="notNone;">
			         </td>
			         
			         <td align="left">
			         	<input type="text" name="bankprovince" value="${itemEach.bankprovince}" id ='bankprovince' onclick='selectProvince(this)' style='cursor: pointer;'  readonly=readonly    oncheck="notNone;"  maxlength="100">
			         </td>
			         
					 <td align="left" >
					 	<input type=text name='bankcity' value="${itemEach.bankcity}" head='开户城市'  id ='bankcity' onclick='selectCity(this)' style='cursor: pointer;'  readonly=readonly    oncheck="notNone;"  maxlength="100" > 
					 </td>
			         
			         <td align="left">
			         <input type="text" style="width: 100%"
			                    name="p_moneys" value="${my:formatNum(itemEach.moneys / 100.0)}" oncheck="notNone;isFloat3">
			         </td>
			         
			         <td align="left">
			         <textarea name="p_description" rows="3" style="width: 100%"><c:out value="${itemEach.description}"/></textarea>
			         </td>
			        <td width="5%" align="center"><input type=button
			            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
			         </tr>
			         </c:forEach>
			         
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
    
    <%@include file="share_update_with_budget.jsp"%>
    
    
	    <p:title>
	        <td class="caption">
	         <strong>提交/审核</strong>
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
	                        <td width="15%" align="center">提交到</td>
	                        <td align="left">
	                        <input type="text" name="processer" readonly="readonly" oncheck="notNone" head="下环处理人"/>&nbsp;
	                        <font color=red>*</font>
	                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
	                            class="button_class" onclick="initSelectNext15()">&nbsp;&nbsp;
	                        </td>
	                    </tr>
	                </table>
	                </td>
	            </tr>
	        </table>
	
	        </td>
	    </tr>
    
    <p:line flag="1" />
    
	<p:button leftWidth="98%" rightWidth="0%">
		<div align="right">
		<c:if test="${update == 1}">
		<input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;保存为草稿&nbsp;&nbsp;" onclick="addBean(0)">
		  &nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
         </c:if>
         
         <c:if test="${update == 3}">
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;稽核修改&nbsp;&nbsp;" onclick="addBean(2)">
         </c:if>
        </div>
	</p:button>
	
	<p:message2/>
</p:body>
</form>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<table>
    <tr class="content1" id="trCopy" style="display: none;">
         <td align="left">
         <input type=text name = 'i_beginDate'  id = 'i_beginDate'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_beginDate");' height='20px' width='20px'/>
         </td>
         <td align="left">
         <input type=text name = 'i_endDate'  id = 'i_endDate'  oncheck = ""  value = ''  readonly=readonly class='input_class' size = '20' ><img src='../images/calendar.gif' style='cursor: pointer' title='请选择时间' align='top' onclick='return calDateInner(this, "i_endDate");' height='20px' width='20px'/>
         </td>
         <td align="left">
         <select name="i_feeItem" class="select_class" style="width: 100%;" oncheck="notNone">
	         <option value="">--</option>
	         <c:forEach var="item" items="${feeItemList}">
	             <option value="${item.id}">${item.name}</option>
	         </c:forEach>
         </select>
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="i_moneys" value="" oncheck="notNone;isFloat3"></td>
         <td align="left">
         <textarea name="i_description" rows="3" style="width: 100%"></textarea>
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <tr class="content1" id="trCopy_pay" style="display: none;">
         <td align="left">
         <select name="p_receiveType" id="p_receiveType"  class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="travelApplyReceiveType" empty="true"></p:option>
         </select>
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="p_bank" value="" id="p_bank" onclick='selectOpeningBank(this)' readonly=readonly style='cursor: pointer;'>
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_userName" value="" >
         </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_bankNo" value="" >
         </td>
         
         <td align="left">
         	<input type="text" name="bankprovince" id ='bankprovince' onclick='selectProvince(this)' style='cursor: pointer;'  readonly=readonly   maxlength="100">
         </td>
         
		 <td align="left" >
		 	<input type="text" name='bankcity' head='开户城市'  id ='bankcity' onclick='selectCity(this)' style='cursor: pointer;'  readonly=readonly  maxlength="100" > 
		 </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_moneys" value="" oncheck="notNone;isFloat3">
         </td>
         
         <td align="left">
         <textarea name="p_description" rows="3" style="width: 100%"></textarea>
         </td>
        <td width="5%" align="center"><input type=button
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <%@include file="share_tr.jsp"%>
    
</table>
</body>
</html>

