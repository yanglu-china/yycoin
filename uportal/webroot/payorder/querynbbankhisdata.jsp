<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="宁波银行账户明细" link="true" guid="true" cal="false" dialog="true"/>
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">
var guidMap;
var thisObj;

var allDef = window.top.topFrame.allDef;

function load()
{
     preload();
    
	 guidMap = {
		 title: '明细列表',
		 url: '../payorder/queryHisData.do?method=queryHisData',
		 colModel : [
		     {display: '账号', name : 'bankAcc', width : '5%', sortable : false, align: 'left'},
		     {display: '开户银行', name : 'bankName', width : '5%', sortable : false, align: 'left'},
		     {display: '户名', name : 'accName', width : '8%', sortable : false, align: 'left'},
		     {display: '对方账号', name : 'oppAccNo', width : '15%', sortable : true, align: 'left'},
		     {display: '对方户名', name : 'oppAccName', width : '15%', sortable : false, align: 'left'},
		     {display: '对方开户行', name : 'oppAccBank', width : '10%', sortable : false, align: 'left'},
		     {display: '收支方向', name : 'cdSign', width : '5%', sortable : false, align: 'left'},
		     {display: '交易金额', name : 'amt', width : '6%'},
		     {display: '明细余额', name : 'bal', width : '6%',  toFixed: 2},
		     {display: '凭证号号', name : 'voucherNo', width : '8%', sortable : false},
		     {display: '交易日期', name : 'transDate', width : '5%', sortable : false},
		     {display: '用途', name : 'uses', width : '8%', sortable : false},
		     {display: '备注', name : 'remark', width : '5%', sortable : false}
		     ],
		 buttons : [
		     {id: 'search', bclass: 'search', onpress : doSearch}
		     ]
		 <p:conf/>
	 };
	 
	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function $callBack()
{
    loadForm();
    
    highlights($("#mainTable").get(0), ['设置', '正常'], 'blue');
    
    highlights($("#mainTable").get(0), ['未设置', '废弃'], 'red');
}
 
function doSearch()
{
    //$modalQuery('../admin/query.do?method=popCommonQuery2&key=queryStaffer');
}
</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" action="../payorder/queryHisData.do" method="post">
<input type="hidden" name="method" value="queryHisData"/>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>