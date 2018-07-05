package com.hp.vtms.util;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hp.vtms.email.ThreadPoolService;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.User;
import com.hp.vtms.service.TraceTestService;
import com.hp.vtms.service.impl.TraceTestServiceImpl;
import com.hp.vtms.vcloud.model.WebStats;

public class TraceRecord {

    private static Logger _LOG = LoggerFactory.getLogger(TraceRecord.class);

    Boolean booleantest = Boolean.TRUE;

    private TraceTestService traceTestService;


    public TraceRecord() {
        traceTestService = new TraceTestServiceImpl();
    }

    public void addTraceRecord(final User user, final List<Event> events, final HttpSession session) {

        _LOG.info("addTraceRecord main method start");
        ThreadPoolService.getInstance().execute(new Runnable() {

            public void run() {
                addTraceRecordAsync(user, events, session);
            }

        });
        _LOG.info("addTraceRecord main method end");
    }

    private void addTraceRecordAsync(final User user, final List<Event> events, final HttpSession session) {
        _LOG.info("addTraceRecord main thread start");
        // List<Event> events = eventService.getEventsByUser(user);
        WebStats webStats = new WebStats();
        String webstats_conn_ip = (String) session.getAttribute("clientIP");
        webStats.setWebstats_conn_ip(webstats_conn_ip);
        webStats.setWebstats_conn_start((Long) session.getAttribute("connstartTime"));
        webStats.setWebstats_username(user.getUserName());
        webStats.setWebstats_fname(user.getFirstName());
        webStats.setWebstats_is_demouser(booleantest.equals(user.getIsDemo()) ? "1" : "0");
        webStats.setWebstats_lname(user.getLastName());

        String webstats_event = null;
        Long webstats_event_id = Long.valueOf(user.getEventId());
        Long webstats_event_start = null;
        Long webstats_event_end = null;
        if (events != null && events.size() > 0) {
            Event myEvent = events.get(0);
            webstats_event = myEvent.getName();
            webstats_event_id = myEvent.getId();
            String eventStart = myEvent.getStartDate() + " " + myEvent.getStartTime();
            String format = "yyyy-MM-dd HH:mm:ss.S";
            Date eventStartTime = CommonUtils.parseDate(eventStart, format);
            webstats_event_start = eventStartTime.getTime();
            String eventEnd = myEvent.getEndDate() + " " + myEvent.getEndTime();
            Date eventEndTime = CommonUtils.parseDate(eventEnd, format);
            webstats_event_end = eventEndTime.getTime();
        }
        webStats.setWebstats_event(webstats_event);
        webStats.setWebstats_event_id(webstats_event_id == null ? null : webstats_event_id.toString());
        webStats.setWebstats_event_start(webstats_event_start);
        webStats.setWebstats_event_end(webstats_event_end);
        try {
            Long webStatsID = traceTestService.addTraceRecord(webStats);
            session.setAttribute("webStatsID", webStatsID);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            _LOG.info(e.getMessage());
            e.printStackTrace();
        }
        _LOG.info("addTraceRecord main thread end");
    }

    public void updateTraceRecord(final Long webstats_id, final Long logoutTime) {
        _LOG.info("updateTraceRecord main method start");
        ThreadPoolService.getInstance().execute(new Runnable() {

            public void run() {
                updateTraceRecordAsync(webstats_id, logoutTime);
            }

        });
        _LOG.info("updateTraceRecord main method end");
    }

    private void updateTraceRecordAsync(final Long webstats_id, final Long logoutTime) {
        _LOG.info("updateTraceRecord main thread start");
        traceTestService.updateTraceRecord(webstats_id, logoutTime);
        _LOG.info("updateTraceRecord main thread end");
    }

}
