<%@ page contentType="text/html;charset=UTF-8" language="java"
    errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="打印顺丰面单" link="false" guid="true" cal="true" dialog="true" />
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<link href="../css/self.css" type=text/css rel=stylesheet>
<style   type="text/css">
	/* @media print 为打印时适应的样式表*/
  @media   print   {  
  body {
	padding-top: 0px;padding-right: 0px;padding-bottom: 0px;padding-left: 0px;
	}	
  }
  </style>  
<script language="javascript">
function pagePrint()
{
	document.getElementById('ptr').style.display = 'none';
	window.print();
	document.getElementById('ptr').style.display = 'block';
	$l('../sail/ship.do?method=toSfPrintNextPage');
}
function ret()
{
	document.getElementById('ptr').style.display = 'none';
	$l('../sail/ship.do?method=queryPickup&load=1&menu=1');
}
</script>
</head>
<body>
<form name="formEntry" action="../sail/ship.do">
<input type="hidden" name="packageId" value="${packageId}">
<input type="hidden" name="method" value="toSfPrintNextPage">
<table width="90%" border="0" cellpadding="0" cellspacing="0"
	align="center">
	<tr>
		<td colspan='2' align='center'>
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
			<tr>
				<td>
					<img style="height:35%;padding-top: 0px;padding-right: 0px;padding-bottom: 0px;padding-left: 0px;" src="../sail/ship.do?method=downLoadSfPicUrl&packageId=${packageId}"/>
				</td>
			</tr>
			
		</table>

		</td>
	</tr>
	
	<tr id="ptr">
		<td width="92%">
			<div align="right">
				<input type="button" name="pr" class="button_class" onclick="pagePrint()" value="&nbsp;&nbsp;打 印&nbsp;&nbsp;">
				<input type="button" name="rt" class="button_class" onclick="ret()" value="&nbsp;&nbsp;返 回&nbsp;&nbsp;">
			</div>
		</td>
	</tr>
</table>
</form>
</body>
</html>
