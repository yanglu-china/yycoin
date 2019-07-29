<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="通用费用报销" guid="true"/>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/compatible.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/json.js"></script>
<script language="JavaScript" src="../tcp_js/jquery.js"></script>
<script language="JavaScript" src="../tcp_js/expense.js"></script>
<script language="javascript">

function addBean(opr)
{
    $("input[name='oprType']").val(opr);
    
    if ("0" == opr)
    {
        $O('processer').oncheck = '';
    }
    
    submit('确定通用费用报销?', null, checks2);
}

$(document).ready(function (){
    $("#sub_main_tr").hide();
});

function load()
{
	addTr();
	
	addPayTr();
	
	addShareTr();
	
	$v('tr_att_more', false);
	
	payTypeChange();

    loadBudget();
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
<input type="hidden" name="addOrUpdate" value="0"> 
<input type="hidden" name="processId" value=""> 
<input type="hidden" name="type" value="14"> 
<input type="hidden" name="stafferId" value="${g_stafferBean.id}"> 
<input type="hidden" name="departmentId" value="${g_stafferBean.principalshipId}"> 
<input type="hidden" name="stype" value="${g_stafferBean.otype}">
<input type="hidden" name="provinceId" value="">
<p:navigation height="22">
	<td width="550" class="navigation">通用费用报销</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="100%">

	<p:title>
		<td class="caption">
		 <strong>通用费用报销</strong>
		</td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="98%">
	
	    <p:class value="com.china.center.oa.tcp.bean.ExpenseApplyBean" />
	    
		<p:table cells="2">
            <p:pro field="stafferId" value="${g_stafferBean.name}"/>
            <p:pro field="departmentId" value="${g_stafferBean.principalshipName}"/>

            <p:pro field="specialType" cell="0">
                <p:option type="expenseSpecialType"/>
            </p:pro>
            
            <p:pro field="name" cell="0" innerString="size=60"/>
            
            <p:pro field="beginDate"/>
            <p:pro field="endDate"/>
            
            <p:pro field="payType" innerString="onchange='payTypeChange()'">
                <p:option type="expensePayType"></p:option>
            </p:pro>
            
            <p:pro field="lastMoney"/>
            
            <p:pro field="ticikCount" cell="0"/>

            <p:pro field="description" cell="0" innerString="rows=4 cols=55" />
            
            <p:cell title="附件" width="8" end="true"><input type="file" name="atts" size="70" >  
            <font color="blue"><span style="cursor: pointer;" onclick="showMoreAtt()" >【更多附件】 </span><b>如果是模板报销只能上传通用报销模板</b></font>
            </p:cell>
            
            <p:cell title="附件N" width="8" id="att_more" end="true">
            <input type="file" name="atts0" size="70" > <br> 
            <input type="file" name="atts1" size="70" > <br> 
            <input type="file" name="atts2" size="70" > <br> 
            <input type="file" name="atts3" size="70" > <br> 
            </p:cell>

        </p:table>
	</p:subBody>
	
    <p:title>
        <td class="caption">
         <strong>通用费用明细</strong>
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
                        <td width="10%" align="center">报销金额</td>
                        <td width="25%" align="center">备注</td>
                        <td width="5%" align="left"><input type="button" accesskey="A"
                            value="增加" class="button_class" onclick="addTr()"></td>
                    </tr>
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
                            name="allTotal" class="input_class" readonly="readonly" oncheck="notNone">
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
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>

<c:if test="${!imp}">
     <p:title>
        <td class="caption">
         <strong>费用分担</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    <tr>
        <td colspan='2' align='center'>
            <label for="budget">月度预算：</label>
            <select name="budget" id="budget">
                <option value="0"></option>
            </select>
            <label for="bearType">承担方式：</label>
            <select name="bearType" id="bearType" onchange="changeType(this)">
                <option value="0"></option>
                <option value="1">其他部门</option>
                <option value="2">省级经理下属承担</option>
                <option value="3">区域总监下属承担</option>
                <option value="4">大区总经理下属承担</option>
                <option value="5">专员承担</option>
            </select>
            <label for="manager">承担经理：</label>
            <select name="manager" id="manager" onchange="changeManager(this)">
                <option value="0"></option>
            </select>
        </td>
    </tr>

    <tr>
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_share">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">承担人</td>
                        <td width="15%" align="center">分担金额</td>
                        <td width="5%" align="left"><input type="button" accesskey="B"
                            value="增加" class="button_class" onclick="addShareTr()"></td>
                    </tr>
                </table>
                </td>
            </tr>
        </table>

        </td>
    </tr>
</c:if>

    <c:if test="${imp}">
        <%@include file="share_update.jsp"%>
    </c:if>

    <p:title>
        <td class="caption">
         <strong>提交/审核</strong>
        </td>
    </p:title>

    <p:line flag="0" />
    
    <tr id="sub_main_tr">
        <td colspan='2' align='center'>
        <table width="98%" border="0" cellpadding="0" cellspacing="0"
            class="border">
            <tr>
                <td>
                <table width="100%" border="0" cellspacing='1' id="tables_pay">
                    <tr align="center" class="content0">
                        <td width="15%" align="center">提交到</td>
                        <td align="left">
                        <input type="text" name="processer" readonly="readonly"  head="下环处理人"/>&nbsp;
                        <font color=red>*</font>
                        <input type="button" value="&nbsp;...&nbsp;" name="qout" id="qout"
                            class="button_class" onclick="initSelectNext()">&nbsp;&nbsp;
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
		<input type="button" class="button_class" id="sub_b1"
            value="&nbsp;&nbsp;保存为草稿&nbsp;&nbsp;" onclick="addBean(0)">
		  &nbsp;&nbsp;
		  <input type="button" class="button_class" id="sub_b2"
            value="&nbsp;&nbsp;提 交&nbsp;&nbsp;" onclick="addBean(1)">
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
         <select name="p_receiveType" class="select_class" style="width: 100%;" oncheck="notNone">
            <p:option type="travelApplyReceiveType" empty="true"></p:option>
         </select>
         </td>
         
         <td align="left"><input type="text" style="width: 100%"
                    name="p_bank" value="" id="p_bank" onclick='selectOpeningBank(this)' readonly=readonly oncheck="notNone;" style='cursor: pointer;'>
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
         	<input type=text name='bankprovince' head='开户省份'  id ='bankprovince' onclick='selectProvince(this)' style='cursor: pointer;'  readonly=readonly    oncheck="notNone;"  maxlength="100" > 
         </td>
         
		 <td align="left" >
		 	<input type=text name='bankcity' head='开户城市'  id ='bankcity' onclick='selectCity(this)' style='cursor: pointer;'  readonly=readonly    oncheck="notNone;"  maxlength="100" > 
		 </td>
         
         <td align="left">
         <input type="text" style="width: 100%"
                    name="p_moneys" value="" oncheck="notNone;isFloat3">
         </td>
         
         <td align="left">
         <textarea name="p_description" rows="3" style="width: 100%"></textarea>
         </td>
        <td width="5%" align="center"><input type=button name="pay_del_bu"
            value="&nbsp;删 除&nbsp;" class=button_class onclick="removeTr(this)"></td>
    </tr>
    
    <%@include file="share_tr.jsp"%>
    
</table>
</body>
</html>

