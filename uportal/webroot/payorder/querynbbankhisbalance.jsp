<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="宁波银行账户余额" link="true" guid="true" cal="true" dialog="true"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/jquery.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">
var guidMap;
var thisObj;

var allDef = window.top.topFrame.allDef;

function load()
{
	 preload();
	 guidMap = {
		 title: '余额列表',
		 url: '../payorder/queryHisData.do?method=queryHisBalance',
		 colModel : [
			 {display: '日期', name : 'business_date', width : '10%', sortable : false, align: 'left'},
		     {display: '账号', name : 'bankAcc', width : '12%', sortable : false, align: 'left'},
		     {display: '银行', name : 'bankName', width : '20%', sortable : false, align: 'left'},
		     {display: '公司', name : 'corpName', width : '20%', sortable : false, align: 'left'},
		     {display: '户名', name : 'accName', width : '20%', sortable : false, align: 'left'},
		     {display: '账户属性', name : 'accAttribute', width : '10%', sortable : false, align: 'left'},
		     {display: '余额', name : 'balance', width : '10%',  toFixed: 2},
		     ],
		 buttons : [
		     {id: 'search', bclass: 'search', onpress : doSearch},
		     {id: 'export', bclass: 'replied',  caption: '导出', onpress : exports}
		     ],
		 <p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function $callBack()
{
    loadForm();
    
    //highlights($("#mainTable").get(0), ['设置', '正常'], 'blue');
    
    //highlights($("#mainTable").get(0), ['未设置', '废弃'], 'red');
}
 
function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryNbBankHisBalance');
}

function exports()
{
	var business_date = "";
	var bankAcc = "";
	var bankName = "";
	var accName = "";
	if(typeof($("#business_date").val()) != 'undefined')
	{
		business_date = $O("business_date").value;
	}
	if(typeof($("#bankAcc").val()) != 'undefined')
	{
		bankAcc = $O("bankAcc").value;
	}
	if(typeof($("#bankName").val()) != 'undefined')
	{
		bankName = $O("bankName").value;
	}
	if(typeof($("#accName").val()) != 'undefined')
	{
		accName = $O("accName").value;
	}
	
    document.location.href = '../payorder/queryHisData.do?method=exportHisBalanceData';
}
</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../payorder/queryHisData.do" method="post">
<input type="hidden" name="method" value="queryHisBalance"/>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="40px" />

<p:query />
</body>
</html>