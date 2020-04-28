<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="库存管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/plugin/highlight/jquery.highlight.js"></script>
<script type="text/javascript">

var gurl = '../depot/storage.do?method=';
var ukey = 'StorageRelation';

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var priceDisplay = containInList($auth(), '1419');

function load()
{
     preload();
     
     var myColModel = [
                 {display: '选择', name : 'check', content : 
                 '<input type=radio name=checkb value={id} lamount={amount} ldepotpartId={depotpartId} lproductId={productId} llocationId={locationId} lpriceKey={priceKey}>', 
                 		width : 40, align: 'center'},
                 {display: '产品', name : 'productName', width : '15%', cname: 'StorageRelationBean.productId', sortable : true},
                 {display: '编码', name : 'productCode', width : '15%'},
                 {display: '实际/预占/在途', name : 'amount', width : '15%', content: '{amount}/{preassignAmount}/{inwayAmount}', sortable : true}
                 ];
     
     var myColModel1 = [
                 {display: '价格', name : 'price', toFixed: 2, sortable : true, width : '10%'}
                 ];
     
     var myColModel2 = [
                 {display: '进项税', name : 'inputRate', toFixed: 2, width : '8%'},
                 {display: '储位', name : 'storageName', width : '10%'},
                 {display: '仓区', name : 'depotpartName', width : '10%'},
                 {display: '仓库', name : 'locationName', width : '10%'},
                 {display: '职员', name : 'stafferName', width : 'auto'}
                 ];
     
     if(priceDisplay){
    	 myColModel = myColModel.concat(myColModel1);
     }
     
     myColModel = myColModel.concat(myColModel2);  
     
     guidMap = {
         title: '库存列表',
         url: gurl + 'query' + ukey,
         colModel : myColModel,
         extAtt: {
             //name : {begin : '<a href=' + gurl + 'find' + ukey + '&id={id}>', end : '</a>'}
         },
         buttons : [
             {id: 'del', bclass: 'del',  onpress : delBean, auth: '1107'},
             {id: 'update', bclass: 'update', caption: '更新库存KEY', onpress : initPriceKey, auth: '1107'},
             {id: 'update2', bclass: 'update', caption: '库存体检', onpress : checkStorageLog, auth: '1107'},
             {id: 'search', bclass: 'search', onpress : doSearch},
             {id: 'search1', bclass: 'search', caption: '仓区下异动历史', onpress : depotpartLog},
             {id: 'search2', bclass: 'search', caption: '仓区下异动(价格)', onpress : depotpartLog2},
             {id: 'search2', bclass: 'search', caption: '仓库下异动历史', onpress : depotLog},
             {id: 'search2', bclass: 'search', caption: '仓库下异动(价格)', onpress : depotLog2}
             ],
         <p:conf/>
     };
     
     $("#mainTable").flexigrid(guidMap, thisObj);
}
 
function $callBack()
{
    loadForm();
}


function delBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb') &&getRadio('checkb').lamount == 0)
    {    
        if(window.confirm('确定删除?'))    
        $ajax(gurl + 'delete' + ukey + '&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    $error('不能操作,只能删除数量为0的库存');
}

function initPriceKey(opr, grid)
{
    if(window.confirm('只有在库存存在BUG下才使用此功能,确定更新库存KEY?'))    
        $ajax(gurl + 'initPriceKey', callBackFun);
}

function checkStorageLog(opr, grid)
{
    if(window.confirm('确定库存体检?'))    
    $l(gurl + 'checkStorageLog');
}

function depotpartLog(opr, grid)
{
//    console.log(getRadio('checkb'));
//    console.log(getRadio('checkb').lproductId);
//    console.log(getRadio('checkb').ldepotpartId);

//    console.log(checkElement.getAttribute("lproductId"));
//    console.log(checkElement.getAttribute("ldepotpartId"));
	if (getRadio('checkb') && getRadioValue('checkb'))
	{
        var checkElement = getRadio("checkb");
        var productId = checkElement.getAttribute("lproductId");
        var depotpartId = checkElement.getAttribute("ldepotpartId");
		$l(gurl + 'queryStorageLog&queryType=1&productId=' 
			+ productId + '&depotpartId=' + depotpartId);
	}
	else
	$error('不能操作');
}

function depotpartLog2(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{
        var checkElement = getRadio("checkb");
        var productId = checkElement.getAttribute("lproductId");
        var depotpartId = checkElement.getAttribute("ldepotpartId");
        var lpriceKey = checkElement.getAttribute("lpriceKey");
        $l(gurl + 'queryStorageLog&queryType=1&productId='
                + productId + '&depotpartId=' + depotpartId
                + '&priceKey=' + lpriceKey);
//		$l(gurl + 'queryStorageLog&queryType=1&productId='
//			+ getRadio('checkb').lproductId + '&depotpartId=' + getRadio('checkb').ldepotpartId
//			+ '&priceKey=' + getRadio('checkb').lpriceKey);
	}
	else
	$error('不能操作');
}

function depotLog(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{
        var checkElement = getRadio("checkb");
        var productId = checkElement.getAttribute("lproductId");
        var llocationId = checkElement.getAttribute("llocationId");
        $l(gurl + 'queryStorageLog&queryType=2&productId='
                + productId + '&locationId=' + llocationId);

//		$l(gurl + 'queryStorageLog&queryType=2&productId='
//			+ getRadio('checkb').lproductId + '&locationId=' + getRadio('checkb').llocationId);
	}
	else
	$error('不能操作');
}

function depotLog2(opr, grid)
{
	if (getRadio('checkb') && getRadioValue('checkb'))
	{
        var checkElement = getRadio("checkb");
        var productId = checkElement.getAttribute("lproductId");
        var llocationId = checkElement.getAttribute("llocationId");
        var lpriceKey = checkElement.getAttribute("lpriceKey");
        $l(gurl + 'queryStorageLog&queryType=2&productId='
                + productId + '&locationId=' + llocationId
                + '&priceKey=' + lpriceKey);
//		$l(gurl + 'queryStorageLog&queryType=2&productId='
//			+ getRadio('checkb').lproductId + '&locationId=' + getRadio('checkb').llocationId
//			+ '&priceKey=' + getRadio('checkb').lpriceKey);
	}
	else
	$error('不能操作');
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=query' + ukey);
}

</script>
</head>
<body onload="load()" class="body_class">
<form name="mainForm" method="post">
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query height="300px" width="500px"/>
</body>