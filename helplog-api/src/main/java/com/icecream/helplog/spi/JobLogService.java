package com.icecream.helplog.spi;

/**
 * @author andre.lan
 */
public interface JobLogService {

    void log(String appendLogPattern, Object ... appendLogArguments);
}
