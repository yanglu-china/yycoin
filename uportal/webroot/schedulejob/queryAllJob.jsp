<%@page contentType="text/html; charset=UTF-8"%>
<%@include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<p:link title="定时任务管理" link="true" guid="true" cal="true" dialog="true" />
<script src="../js/common.js"></script>
<script src="../js/jquery.blockUI.js"></script>
<script src="../js/public.js"></script>
<script src="../js/pop.js"></script>
<script src="../js/json.js"></script>
<script type="text/javascript">

var allDef = window.top.topFrame.allDef;
var guidMap;
var thisObj;

var updatFlag = window.top.topFrame.containAuth('0202') ? '1' : '0';
function load()
{
     preload();

	 guidMap = {
		 title: '定时任务列表',
		 url: '../schedulejob/jobs.do?method=queryAllJobs',
		 colModel : [
		     {display: '选择', name : 'check', content : '<input type=radio name=checkb value={id} lname={jobName}>', width : 40, sortable : false, align: 'center'},
		     {display: '任务名称', name : 'jobName', width : '20%', sortable : true, align: 'left', cname: 'id'},
		     {display: '描述', name : 'description', width : '20%',sortable : true},
		     {display: '状态', name : 'jobStatus', cc:'jobStatus', width : '5%', sortable : true, align: 'left'},
		     {display: '下次执行时间', name : 'nextFireTime',  width : '12%', sortable : false, align: 'left'},
		     {display: '触发器表达式', name : 'cronExpression', width : '8%', sortable : false, align: 'left', cc: 101},
		     {display: '执行类', name : 'beanClass', width : '20%', sortable : false, align: 'left'},
		     {display: '执行方法', name : 'methodName', width : 'auto', sortable : true, align: 'left'}
		     ],
		 extAtt: {
		     name : {begin : '<a href=.../schedulejob/jobs.do?method=findJob&id={id}&update=1>', end : '</a>'}
		 },
		 buttons : [
			 {id: 'update', bclass: 'update', onpress : updateBean, auth: '0202'},
		     {id: 'start',caption: '启动', bclass: 'update', onpress : startJob, auth: '0202'},
		     // {id: 'pause', caption: '暂停',bclass: 'update', onpress : pauseJob, auth: '0202'},
		     // {id: 'resume', caption: '继续', bclass: 'update', onpress : resumeJob, auth: '0202'},
		     {id: 'stop', caption: '停止',bclass: 'update', onpress : stopJob, auth: '0202'},
		     {id: 'viewLog', caption: '查看日志',bclass: 'search', onpress : viewLog, auth: '0202'},
		     {id: 'search', caption: '检索',bclass: 'search', onpress : doSearch, auth: '0202'}
		     ],
		 <p:conf callBack="loadForm" queryMode="0"/>
	 };

	 $("#mainTable").flexigrid(guidMap, thisObj);
 }

function updateBean(opr, grid)
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
       $l('../schedulejob/jobs.do?method=findJob&update=1&id=' + getRadioValue('checkb'));
    }
}

function viewLog()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $l('../schedulejob/jobLog.jsp?targerId=' + getRadioValue('checkb') + '&menu=1');
    }
    else
    {
        $error();
    }
}

function doSearch()
{
    $modalQuery('../admin/query.do?method=popCommonQuery2&key=queryScheduleJob');
}

function startJob()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        $ajax('../schedulejob/jobs.do?method=startJob&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    {
        $error();
    }
}

function pauseJob()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
         $ajax('../schedulejob/jobs.do?method=pauseJob&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    {
        $error();
    }
}

function resumeJob()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
          $ajax('../schedulejob/jobs.do?method=resumeJob&id=' + getRadioValue('checkb'), callBackFun);
    }
    else
    {
        $error();
    }
}

function stopJob()
{
    if (getRadio('checkb') && getRadioValue('checkb'))
    {
        if (window.confirm('确定停止任务--' + getRadio('checkb').lname))
        {
             $ajax('../schedulejob/jobs.do?method=stopJob&id=' + getRadioValue('checkb'), callBackFun);
        }
    }
}

function callBackFun(data)
{
    $.unblockUI();
    reloadTip(data.msg, data.ret == 0);
	commonQuery();

}

function commonQuery(par)
{
    gobal_guid.p.queryCondition = par;
    gobal_guid.grid.populate(true);
}


</script>
</head>
<body onload="load()" class="body_class">
<form>
<p:cache></p:cache>
</form>
<p:message></p:message>
<table id="mainTable" style="display: none"></table>
<p:query/>
</body>