package com.china.center.oa.publics.manager.impl;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by aaronhuang on 2017/4/24.
 */
public class TestJob implements Job  {
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+ "★★★★★★★★★★★");
    }
}
