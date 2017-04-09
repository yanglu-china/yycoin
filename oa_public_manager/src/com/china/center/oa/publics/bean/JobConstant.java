package com.china.center.oa.publics.bean;

import com.china.center.jdbc.annotation.Defined;

/**
 * Created by Simon on 2016/10/23.
 */
public interface JobConstant {

    @Defined(key = "jobStatus", value = "已停止")
    int STATUS_NOT_RUNNING = 0;

    @Defined(key = "jobStatus", value = "运行中")
    int STATUS_RUNNING = 1;

    @Defined(key = "jobStatus", value = "暂停")
    int STATUS_PAUSED = 2;
}
