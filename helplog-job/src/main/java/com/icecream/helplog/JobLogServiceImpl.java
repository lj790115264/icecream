package com.icecream.helplog;

import com.icecream.helplog.spi.JobLogService;
import com.xxl.job.core.context.XxlJobHelper;

/**
 * @author andre.lan
 */
public class JobLogServiceImpl implements JobLogService {

    public void log(String appendLogPattern, Object... appendLogArguments) {
        XxlJobHelper.log(appendLogPattern, appendLogArguments);
    }
}
