package com.hp.vtms.util;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class SessioinAttributeListener implements HttpSessionAttributeListener {

    TraceRecord traceRecord = new TraceRecord();
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        if ("user".equals(attributeName)) {
            Long loginTime = CommonUtils.getUTCTIimeInMillisecond();
            event.getSession().setAttribute("connstartTime", loginTime);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        if ("webStatsID".equals(attributeName)) {
            Long logoutTime = CommonUtils.getUTCTIimeInMillisecond();
            Long webstats_id = (Long) event.getValue();
            traceRecord.updateTraceRecord(webstats_id, logoutTime);
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

    }
}
