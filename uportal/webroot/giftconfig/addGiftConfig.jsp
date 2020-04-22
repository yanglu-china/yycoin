<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="增加赠品配置" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/math.js"></script>
<script language="JavaScript" src="../admin_js/giftConfig.js"></script>
<script language="javascript">
    var index = -1;


function addBean()
{
	submit('确定增加赠品配置?', null, checkBean());
}

function selectProduct(idx)
{
//    console.log(idx);
    index = idx;
    window.common.modal('../product/product.do?method=rptQueryProduct&load=1&selectMode=1');
}

function getProduct(oos)
{
//    console.log("getProduct now");
    var obj = oos[0];
//    console.log(obj);
//    console.log(index);
    if (index === 1){
        $O('productName').value = obj.pname;
        $O('productId').value = obj.value;
    } else if (index === 2){
        $O('giftProductName').value = obj.pname;
        $O('giftProductId').value = obj.value;
    }  else if (index === 22){
        $O('giftProductName2').value = obj.pname;
        $O('giftProductId2').value = obj.value;
    }   else if (index === 3){
        $O('giftProductName3').value = obj.pname;
        $O('giftProductId3').value = obj.value;
    } else if (index === 4){
        $O('giftProductName4').value = obj.pname;
        $O('giftProductId4').value = obj.value;
    }
}

</script>

</head>
<body class="body_class">
<form name="formEntry" action="../sail/giftconfig.do" method="post">
<input type="hidden" name="method" value="addGiftConfig">
<input type="hidden" name="productId" value="0">
<input type="hidden" name="giftProductId" value="0">
<input type="hidden" name="giftProductId2" value="0">
<input type="hidden" name="giftProductId3" value="0">
<input type="hidden" name="giftProductId4" value="0">


<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">赠品配置</span> &gt;&gt; 增加赠品配置</td>
	<td width="85"></td>
	
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>赠品配置基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.product.bean.ProductVSGiftBean" />

		<p:table cells="1">

            <p:cell title="活动描述">
                <input type="text" name="activity" id="activity">
            </p:cell>

            <p:cell title="适用银行">
                <input type="text" name="bank" id="bank" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不适用银行">
                <input type="text" name="excludeBank" id="excludeBank" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="适用分行">
                <input type="text" name="branchName" id="branchName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不适用分行">
                <input type="text" name="excludeBranchName" id="excludeBranchName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="适用支行">
                <input type="text" name="customerName" id="customerName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不适用支行">
                <input type="text" name="excludeCustomerName" id="excludeCustomerName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="适用渠道">
                <input type="text" name="channel" id="channel" placeholder="可多选，以分号;分割" size="10">
            </p:cell>

            <p:cell title="不适用渠道">
                <input type="text" name="excludeChannel" id="excludeChannel" placeholder="可多选，以分号;分割" size="10">
            </p:cell>

            <p:cell title="包含事业部">
                <input type="text" name="industryName" id="industryName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不包含事业部">
                <input type="text" name="excludeIndustryName" id="excludeIndustryName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="大区">
                <input type="text" name="industryName2" id="industryName2" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不包含大区">
                <input type="text" name="excludeIndustryName2" id="excludeIndustryName2" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="部门">
                <input type="text" name="industryName3" id="industryName3" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不包含部门">
                <input type="text" name="excludeIndustryName3" id="excludeIndustryName3" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="人员">
                <input type="text" name="stafferName" id="stafferName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不包含人员">
                <input type="text" name="excludeStafferName" id="excludeStafferName" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="省份">
                <input type="text" name="province" id="province" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不包含省份">
                <input type="text" name="excludeProvince" id="excludeProvince" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="城市">
                <input type="text" name="city" id="city" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:cell title="不包含城市">
                <input type="text" name="excludeCity" id="excludeCity" placeholder="可多选，以分号;分割" size="100">
            </p:cell>

            <p:pro field="companyShare" innerString="size=10 oncheck='isMathNumber'"/>
            <p:cell title="事业部">
            	<select name="sybid" class="select_class" oncheck="notNone;">
                    <c:forEach items='${sybList}' var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
                &nbsp;&nbsp;&nbsp;
                <p:pro field="sybShare" innerString="size=10 oncheck='isMathNumber'"/>
            </p:cell>
             <p:cell title="业务部">
            	<select name="ywbid" class="select_class">
                    <c:forEach items='${ywbList}' var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
                &nbsp;&nbsp;&nbsp;
                <p:pro field="ywbShare" innerString="size=10 oncheck='isMathNumber'"/>
            </p:cell>
             <p:cell title="大区">
            	<select name="dqid" class="select_class">
                    <c:forEach items='${dqList}' var="item">
                        <option value="${item.id}">${item.name}</option>
                    </c:forEach>
                </select>
                &nbsp;&nbsp;&nbsp;
                <p:pro field="dqShare" innerString="size=10 oncheck='isMathNumber'"/>
            </p:cell>
            <p:pro field="publicfunds" innerString="size=10 oncheck='isMathNumber'"/>

            <p:pro field="stafferShare" innerString="size=10 oncheck='isMathNumber'"/>

            <p:pro field="beginDate" />

            <p:pro field="endDate" />

			<p:pro field="productId" value="销售商品品名" innerString="size=60">
			     <input type="button" value="&nbsp;选择产品&nbsp;" name="qout1" id="qout1"
                    class="button_class" onclick="selectProduct(1)">&nbsp;
                 <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                        class="button_class" onclick="clears(1)">&nbsp;&nbsp;
			</p:pro>

            <p:pro field="sailAmount" value="0" innerString="oncheck='isMathNumber'"/>

            <p:pro field="giftProductId" value="赠送商品品名1" innerString="size=60">
                <input type="button" value="&nbsp;选择产品&nbsp;" name="qout1" id="qout1"
                       class="button_class" onclick="selectProduct(2)">&nbsp;
                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                       class="button_class" onclick="clears(2)">&nbsp;&nbsp;
            </p:pro>

            <p:pro field="amount" value="0" innerString="oncheck='isMathNumber'"/>

            <p:pro field="giftProductId2" value="赠送商品品名2" innerString="size=60">
                <input type="button" value="&nbsp;选择产品&nbsp;" name="qout1" id="qout1"
                       class="button_class" onclick="selectProduct(22)">&nbsp;
                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                       class="button_class" onclick="clears(22)">&nbsp;&nbsp;
            </p:pro>

            <p:pro field="amount2" value="0" innerString="oncheck='isMathNumber'"/>

            <p:pro field="giftProductId3" value="赠送商品品名3" innerString="size=60">
                <input type="button" value="&nbsp;选择产品&nbsp;" name="qout1" id="qout1"
                       class="button_class" onclick="selectProduct(3)">&nbsp;
                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                       class="button_class" onclick="clears(3)">&nbsp;&nbsp;
            </p:pro>

            <p:pro field="amount3" value="0" innerString="oncheck='isMathNumber'"/>

            <p:pro field="mzje" value="0" innerString="oncheck='isFloat'"/>

            <p:pro field="giftProductId4" value="满增商品品名" innerString="size=60">
                <input type="button" value="&nbsp;选择产品&nbsp;" name="qout1" id="qout1"
                       class="button_class" onclick="selectProduct(4)">&nbsp;
                <input type="button" value="&nbsp;清 空&nbsp;" name="qout" id="qout"
                       class="button_class" onclick="clears(4)">&nbsp;&nbsp;
            </p:pro>

            <p:pro field="amount4" value="0" innerString="oncheck='isMathNumber'"/>
			
			<p:pro field="description" cell="0" innerString="rows=3 cols=55" />

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2/>
</p:body></form>
</body>
</html>

