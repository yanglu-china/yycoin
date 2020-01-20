var a=new ActiveXObject("JSTAXS.Tax");

/*开启金税盘*/
function OpenCard(){
    var result = a.JsaeroOpen();
    var oDOM = null;
    if (typeof DOMParser != "undefined"){
        var oParser = new DOMParser();
        var oDOM = oParser.parseFromString(result, "text/xml");
    }else if (typeof ActiveXObject != "undefined") {
        //IE8
        oDOM = new ActiveXObject("Microsoft.XMLDOM");
        oDOM.async = false;
        oDOM.loadXML(result);
        if (oDOM.parseError != 0) {
            throw new Error("XML parsing error: " + oDOM.parseError.reason);
        }
    }else {
        alert("No XML parser available.");
    }

    var result = oDOM.getElementsByTagName("Result")[0].childNodes[0].nodeValue;
    if (result === '1') {
        var msg = oDOM.getElementsByTagName("ErrMsg")[0].childNodes[0].nodeValue;
        alert(msg);
    } else{
        alert("成功开启!");
    }
}


/**
 * 关闭金税盘
 *
 */
function CloseCard(){
    var result = a.JsaeroClose();
    alert(result);
}


//生成开票接口需要的XML数据
function Invoice(){
    var packageId = $O('packageId').value;
    var batchId = $O('batchId').value;
    $ajax('../sail/ship.do?method=generateInvoiceinsXml&packageId='+packageId+'&batchId='+batchId, callbackGenerateInvoice);
}

function parseXml(response){
    var oDOM = null;
    if (typeof DOMParser != "undefined"){
        var oParser = new DOMParser();
        var oDOM = oParser.parseFromString(response, "text/xml");
    }else if (typeof ActiveXObject != "undefined") {
        //IE8
        oDOM = new ActiveXObject("Microsoft.XMLDOM");
        oDOM.async = false;
        oDOM.loadXML(response);
        if (oDOM.parseError != 0) {
            throw new Error("XML parsing error: " + oDOM.parseError.reason);
        }
    }else {
        alert("No XML parser available.");
    }
    return oDOM;
}

//把发票号码显示在页面上
function callbackUpdateInsNum(data){
    var insDiv = $O(data.extraObj);
    // console.log(insDiv);
    if (insDiv){
        insDiv.value=data.obj.invoiceNum;
    }
}