<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="批量回款认领" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定批量回款认领?', null, checkValue);
}

function checkValue()
{
    var fileName = $O('myFile').value;
        
    if ("" == fileName)
    {
        alert("请输入要导入的文件名");
        return false;
    }
    
    if (fileName.indexOf('xls') == -1)
    {
        alert("只支持XLS文件格式!");
        return false;
    }
    
    return true;
}


</script>

</head>
<body class="body_class">
<form name="batchDrawPayment"
	action="../finance/bank.do?method=batchDrawPayment" enctype="multipart/form-data" method="post">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span>批量回款认领</span></td>
	<td width="85"></td>
</p:navigation> <br>
<p:body width="100%">

	<p:title>
		<td class="caption"><strong>选择文件：(数据一定少于2000条,否则系统容易错误)</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="80%">

		<p:table cells="1">
			<p:cell title="导入模板">
				<a target="_blank"
					href="../admin/down.do?method=downTemplateFileByName&fileName=batchDrawPayment.xls">
					下载批量回款认领模板</a>
			</p:cell>
		</p:table>

		<p:table cells="1">
			<p:cell title="导入文件">
				<input type="file" name="myFile" size="60"/>
			</p:cell>
		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message2 />
	
</p:body></form>
</body>
</html>

