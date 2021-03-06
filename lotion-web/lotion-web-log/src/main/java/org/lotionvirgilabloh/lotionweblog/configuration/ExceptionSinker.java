package org.lotionvirgilabloh.lotionweblog.configuration;

import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.lotionvirgilabloh.lotionweblog.event.ExceptionEvent;
import org.lotionvirgilabloh.lotionweblog.service.ExceptionDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ExceptionSinker {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExceptionDaoService exceptionDaoService;

    @EventListener
    public void exceptionEventHandler(ExceptionEvent exceptionEvent) {
        Map<String, Object> addtionalProperties = new LinkedHashMap<>(exceptionEvent.getCustomProperties());
        //project不属于addtionalProperties
        addtionalProperties.remove("project");
        addtionalProperties.put("loggerName", exceptionEvent.getLog4jLogEvent().getLoggerName());
        addtionalProperties.put("threadID", exceptionEvent.getLog4jLogEvent().getThreadId());
        addtionalProperties.put("threadName", exceptionEvent.getLog4jLogEvent().getThreadName());

        //构造FormattedException
        String project;
        if (exceptionEvent.getCustomProperties().containsKey("project"))
            project = exceptionEvent.getCustomProperties().get("project").toString();
        else
            project = "unknown";
        String eventMsg;
        try {
            eventMsg = exceptionEvent.getLog4jLogEvent().getMessage().toString();
        } catch (Exception e) {
            eventMsg = "";
        }
        FormattedException formattedException = new FormattedException(exceptionEvent.getLog4jLogEvent().getTimeMillis(), eventMsg, project, addtionalProperties);

        //设置ID
        formattedException.setExceptionID(formattedException.hashCode());
        logger.info(Integer.toString(formattedException.getExceptionID()));

        //插入FormattedException至数据库
        FormattedException feReturn = exceptionDaoService.insert(formattedException);

        //判断是否插入成功
        if (feReturn != null) {
            if (feReturn.getExceptionID() == formattedException.getExceptionID()) {
                logger.info("FE:" + formattedException.getExceptionID() + "插入MongoDB成功");
            } else {
                logger.error("FE:" + formattedException.getExceptionID() + "插入MongoDB失败，返回实体与插入实体不相同");
            }
        } else {
            logger.error("FE:" + formattedException.getExceptionID() + "插入MongoDB失败");
        }

    }
}
