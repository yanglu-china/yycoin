<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="未回款订单激励申请管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../tcp/apply.do?method=';
var addUrl = '../tcp/addTravelApply7.jsp';
var ukey = 'TravelApply';

var allDef = getAllDef();
var guidMap;
var thisObj;
function load()
{
     preload();
     
     guidMap = {
         title: '未回款订单激励申请列表',
         url: gurl + 'querySelfTravelApply&type=17',
         colModel : [
             {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lstatus={status}>', width : 40, align: 'center'},
             {display: '标识', name : 'id', width : '15%'},
             {display: '目的', name : 'name', width : '15%'},
             {display: '申请人', name : 'stafferName', width : '8%'},
             {display: '处理人', name : 'processer', width : '8%'},
             {display: '系列', name : 'stype', cc: 'tcpStype', width : '5%'},
             {display: '状态', name : 'status', cc: 'tcpStatus', width : '10%'},
             {display: '是否借款', name : 'borrow', cc: 'travelApplyBorrow', width : '5%'},
             {display: '报销', name : 'feedback', cc: 'tcpApplyFeedback', width : '8%'},
             {display: '费用/借款', name : 'showTotal', sortable: true, content: '{showTotal}/{showBorrowTotal}', cname: 'total', width : '10%'},
             {display: '合规标识', name : 'compliance', cc: 'tcpComplianceType', width : '5%'},
             {display: '时间', name : 'logTime', sortable: true, width : 'auto'}
             ],
         extAtt: {
             id : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'update', bclass: 'update', onpress : updateBean, auth: '0000'},
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '0000'},
             {id: 'search', bclass: 'search', onpress : doSearch}
             ],
        <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
    highlights($("#mainTable").get(0), ['结束'], 'blue');
    highlights($("#mainTable").get(0), ['驳回', '未关联报销'], 'red');
}

function delBean(opr, grid)
{
    if (getRadio('checkb') && (getRadio('checkb').lstatus == 0 || getRadio('checkb').lstatus == 1))
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作');
}

function updateBean()
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{	
		$l(gurl + 'find' + ukey + '&update=1&id=' + getRadioValue('checkb'));
	}
	else
	$error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=tcp.querySelfTravelApply');
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px"/>
</body>