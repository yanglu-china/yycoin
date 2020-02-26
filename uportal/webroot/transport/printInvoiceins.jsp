<%@ page contentType="text/html;charset=UTF-8" language="java"
         errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>

<html>
<head>
    <p:link title="打印发票" />
    <link href="../js/plugin/dialog/css/dialog.css" type="text/css" rel="stylesheet"/>
    <script src="../js/title_div.js"></script>
    <script src="../js/public.js"></script>
    <script src="../js/JCheck.js"></script>
    <script src="../js/common.js"></script>
    <script src="../js/tableSort.js"></script>
    <script src="../js/jquery/jquery.js"></script>
    <script src="../js/plugin/dialog/jquery.dialog.js"></script>
    <script src="../js/plugin/highlight/jquery.highlight.js"></script>
    <script src="../js/adapter.js"></script>
    <script src="../transport_js/print_invoiceins.js"></script>
    <script src="../js/json.js"></script>
    <script language="javascript">
        //开票
        function callbackGenerateInvoice(data)
        {
            // console.log(data);
            if (data.retMsg.toLowerCase() === "ok") {
                OpenCard();
                var dataList = data.obj;
                // console.log(dataList);
                for (var j = 0; j < dataList.length; j++) {
                    //查询打印机状态
                    $.ajax({
                        type: "POST",
                        url: '../finance/invoiceins.do?method=queryPrintStatus',
                        data: [], // serializes the form's elements.
                        async: false,
                        success: function(data2)
                        {
                            var json = JSON.parse(data2);
                            if (json.retCode == 0){
                                var key = dataList[j].invoiceId;
                                // alert(key);
                                var xml = dataList[j].payload;
                                // console.log(xml);
                                var response =  a.JsaeroKP(xml);
                                // alert(response);
                                //test code
                                // var response = "<Result>0<fphm>111</fphm><fpdm>222</fpdm><fpzl>0</fpzl></Result>";
                                var oDOM = null;
                                var xmlDoc = null;
                                if (typeof DOMParser != "undefined"){
                                    var oParser = new DOMParser();
                                    oDOM = oParser.parseFromString(response, "text/xml");
                                    xmlDoc = oParser.parseFromString(xml,"text/xml");
                                }else if (typeof ActiveXObject != "undefined") {
                                    //IE8
                                    oDOM = new ActiveXObject("Microsoft.XMLDOM");
                                    oDOM.async = false;
                                    oDOM.loadXML(response);

                                    xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
                                    xmlDoc.async="false";
                                    xmlDoc.loadXML(xml);
                                    if (oDOM.parseError != 0) {
                                        throw new Error("XML parsing error: " + oDOM.parseError.reason);
                                    }
                                }else {
                                    alert("No XML parser available.");
                                }

                                var result = oDOM.getElementsByTagName("Result")[0].childNodes[0].nodeValue;
                                if (result === '0'){
                                    var fphm = oDOM.getElementsByTagName("fphm")[0].childNodes[0].nodeValue;
                                    var fpdm = oDOM.getElementsByTagName("fpdm")[0].childNodes[0].nodeValue;
                                    //打印发票
                                    //发票种类
                                     var fpzl = xmlDoc.getElementsByTagName("fpzl")[0].childNodes[0].nodeValue;
                                    //打印标志（DYBZ）：0-打印发票；1-打印销货清单
                                    var dybz = "0";
                                    //打印模式（DYMS）：0-不弹框打印；1-弹框打印
                                    var dyms = "0";
                                     var response2 = a.JsaeroDY(fpzl,fpdm,fphm,dybz,dyms);
                                    // alert(result);
                                    // var response2 = "<invinterface><Result>1</Result><ErrMsg>error!</ErrMsg></invinterface>";
                                    var oDom2 = parseXml(response2);
                                    var result2 = oDom2.getElementsByTagName("Result")[0].childNodes[0].nodeValue;
                                    if (result2 === '0'){
                                        //更新发票号码
                                        var packageId = $O('packageId').value;
                                        $ajax('../finance/invoiceins.do?method=generateInvoiceins&insId='+key+'&fphm='+fphm+"&packageId="+packageId+"&fpdm="+fpdm, callbackUpdateInsNum);
                                    } else{
                                        var msg = oDom2.getElementsByTagName("ErrMsg")[0].childNodes[0].nodeValue;
                                        alert(msg);
                                    }
                                }else{
                                    var msg = oDOM.getElementsByTagName("ErrMsg")[0].childNodes[0].nodeValue;
                                    alert(msg);
                                }
                            } else{
                                alert("打印机未就绪!");
                            }
                        }
                    });

                }
                CloseCard();
            }
        }


        function load()
        {
            loadForm();
        }

        function querys()
        {
            formEntry.submit();
        }
    </script>

</head>
<body class="body_class" onload="load()">
<form name="formEntry" action="../sail/ship.do">
    <input type="hidden" name="method" value="printInvoiceins">
    <input type="hidden" value="1" name="firstLoad">

    <p:navigation
            height="22">
        <td width="550" class="navigation">打印发票 &gt;&gt; </td>
        <td width="85"></td>
    </p:navigation> <br>

    <p:body width="100%">

        <p:subBody width="98%">
            <table width="100%" align="center" cellspacing='1' class="table0">
                <tr class="content1">
                    <td width="15%" align="center">批次号：</td>
                    <td align="left">
                        <input name="batchId" size="20" value="${batchId}"  />
                    </td>
                </tr>
                <tr class="content2">
                    <td width="15%" align="center">CK单号：</td>
                    <td align="left">
                        <input name="packageId" size="20" value="${packageId}"  />
                    </td>
                </tr>

                <tr class="content1">
                    <td colspan="4" align="right">
                        <input type="button" class="button_class" onclick="querys()" value="&nbsp;&nbsp;查 询&nbsp;&nbsp;">&nbsp;&nbsp;<input
                            type="reset" class="button_class"
                            value="&nbsp;&nbsp;重 置&nbsp;&nbsp;"></td>
                </tr>
            </table>

        </p:subBody>

        <p:title>
            <td class="caption"><strong>待打印发票列表</strong></td>
        </p:title>

        <p:line flag="0" />

        <p:subBody width="98%">
            <table width="100%" align="center" cellspacing='1' class="table0">
                <tr align=center class="content0">
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>开票申请</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>开票抬头</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>开票品名</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>数量</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>单价</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>金额</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>税率</strong></td>
                    <td align="center" class="td_class" onclick="tableSort(this)"><strong>发票号码</strong></td>
                </tr>

                <c:forEach items="${invoiceList}" var="item" varStatus="vs">
                    <tr class="${vs.index % 2 == 0 ? 'content1' : 'content2'}">
                        <td align="center" onclick="hrefAndSelect(this)">
                            <a href="../finance/invoiceins.do?method=findInvoiceins&id=${item.id}">
                                    ${item.id}</a></td>
                        <td align="center">${item.headContent}</td>
                        <td align="center">${item.spmc}</td>
                        <td align="center">${item.itemAmount}</td>
                        <td align="center">${item.price}</td>
                        <td align="center">${item.moneys}</td>
                        <td align="center">${item.sl}</td>
                        <td align="center"><input type="text" readonly id="${item.id}"></td>
                    </tr>
                </c:forEach>
            </table>

        </p:subBody>

        <p:line flag="1"/>

        <br>

        <p:button>
            <div align="right">
                <input type="button" class="button_class"
                       value="&nbsp;&nbsp;打印发票&nbsp;&nbsp;" onclick="Invoice()">&nbsp;&nbsp;
            </div>
        </p:button>
        <p:message2 />

    </p:body></form>

</body>
</html>

