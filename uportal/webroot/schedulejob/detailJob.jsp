<%@ page contentType="text/html;charset=UTF-8" language="java"
	errorPage="../common/error.jsp"%>
<%@include file="../common/common.jsp"%>
<html>
<head>
<p:link title="修改定时任务" />
<script language="JavaScript" src="../js/JCheck.js"></script>
<script language="JavaScript" src="../js/common.js"></script>
<script language="JavaScript" src="../js/public.js"></script>
<script language="javascript">
function addBean()
{
	submit('确定修改任务?');
}

</script>

</head>
<body class="body_class">
<form name="addApply" action="../schedulejob/jobs.do" method="post">
<input type="hidden" name="method" value="updateJob">
<input type="hidden" name="id" value="${bean.id}">
<p:navigation
	height="22">
	<td width="550" class="navigation"><span style="cursor: pointer;"
		onclick="javascript:history.go(-1)">定时任务管理</span> &gt;&gt; 修改定时任务</td>
	<td width="85"></td>
</p:navigation> <br>

<p:body width="98%">

	<p:title>
		<td class="caption"><strong>定时任务基本信息：</strong></td>
	</p:title>

	<p:line flag="0" />

	<p:subBody width="100%">
		<p:class value="com.china.center.oa.publics.bean.ScheduleJobBean" opr="1"/>

		<p:table cells="1">

			<p:pro field="jobName" innerString="readonly=true"/>

			<p:pro field="jobGroup" innerString="readonly=true"/>

			<p:pro field="jobStatus" innerString="readonly=true"/>

			<p:pro field="cronExpression" />

			<p:pro field="description" cell="0" innerString="rows=3 cols=72" />

			<p:pro field="beanClass" innerString="readonly=true size=70"/>

			<p:pro field="methodName" innerString="readonly=true size=70"/>

		</p:table>
	</p:subBody>

	<p:line flag="1" />

	<p:button leftWidth="100%" rightWidth="0%">
		<div align="right"><input type="button" class="button_class" id="ok_b"
			style="cursor: pointer" value="&nbsp;&nbsp;确 定&nbsp;&nbsp;"
			onclick="addBean()"></div>
	</p:button>

	<p:message/>
</p:body></form>
</body>
</html>

