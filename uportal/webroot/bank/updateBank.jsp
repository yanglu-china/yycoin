<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改帐户" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改帐户?');
}
var cityObj;
function selectProvince(obj)
{
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryProvince&load=1&selectMode=1');
}

function getProvinces(oos)
{
    var obj = oos[0];
    cityObj.value = obj.pname;
}
function getCitys(oos)
{
    var obj = oos[0];
    
    cityObj.value = obj.pname;
}
function selectCity(obj)
{
	cityObj = obj;
    window.common.modal('../admin/pop.do?method=rptQueryCity&load=1&selectMode=1');
}
</script>

</head>
<body class="body_class">
<form name="formEntry" action="../finance/bank.do" method="post">
<input type="hidden" name="method" value="updateBank">
<input type="hidden" name="id" value="${bean.id}">
<input type="hidden" name="mtype" value="${bean.mtype}">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">帐户管理</span> &gt;&gt; 修改帐户</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>帐户基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.finance.bean.BankBean" opr="1"/>

		<p:table cells="1">

			<p:pro field="name" innerString="size=60"/>
			
			<p:pro field="bankNo" innerString="size=60"/>
            <p:pro field="bankprovince" innerString="onclick='selectProvince(this)' style='cursor: pointer;'" cell="0"/>
            <p:pro field="bankcity" innerString="onclick='selectCity(this)' style='cursor: pointer;'" cell="0" />
            <p:pro field="dutyId">
                <p:option type="dutyList"/>
            </p:pro>

            <p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body>
</form>
</body>
</html>

