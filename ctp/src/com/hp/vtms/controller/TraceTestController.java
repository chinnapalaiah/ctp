package com.hp.vtms.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hp.vtms.email.EmailNotificationService;
import com.hp.vtms.email.EmailSender;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.User;
import com.hp.vtms.service.EmailHostService;
import com.hp.vtms.service.EventService;
import com.hp.vtms.service.TraceTestService;
import com.hp.vtms.service.impl.TraceTestServiceImpl;
import com.hp.vtms.util.CommonUtils;
import com.hp.vtms.vcloud.model.WebStats;

@Controller
@RequestMapping("trace")
public class TraceTestController {

    private static Logger _LOG = LoggerFactory.getLogger(TraceTestController.class);
    @Autowired
    private EventService eventService;

    @Autowired
    private EmailHostService emailHostService;

    private EmailNotificationService emailNotificationService = new EmailNotificationService();
    private TraceTestService traceTestService = new TraceTestServiceImpl();

    @RequestMapping("sendEmail")
    @ResponseBody
    public String updateTraceRecord(@RequestBody WebStats webStats, HttpServletRequest request) {
        String result = "success";
        try {
            HttpSession session = request.getSession(false);
            User user = (User) session.getAttribute("user");
            Long webstats_id = (Long) (session == null ? null : session.getAttribute("webStatsID"));
            String webstats_fname = null;
            String webstats_lname = null;
            String userEventName = null;
            String userEventID = user.getEventId();
            if (Boolean.TRUE.equals(user.getIsDemo()) && "0".equals(user.getEventId())) {
                webstats_fname = webStats.getWebstats_fname();
                webstats_lname = webStats.getWebstats_lname();
                userEventName = webStats.getWebstats_event();
            } else {
                List<Event> events = eventService.getEventsByUser(user);
                if (events != null && events.size() > 0) {
                    Event event = events.get(0);
                    userEventName = event.getName();
                    String eventStart = event.getStartDate() + " " + event.getStartTime();
                    String format = "yyyy-MM-dd HH:mm:ss.S";
                    Date eventStartTime = CommonUtils.parseDate(eventStart, format);
                    Long webstats_event_start = eventStartTime.getTime();
                    String eventEnd = event.getEndDate() + " " + event.getEndTime();
                    Date eventEndTime = CommonUtils.parseDate(eventEnd, format);
                    Long webstats_event_end = eventEndTime.getTime();
                    webStats.setWebstats_event_start(webstats_event_start);
                    webStats.setWebstats_event_end(webstats_event_end);
                }
                webstats_fname = user.getFirstName();
                webstats_lname = user.getLastName();
            }
            String webstats_event = webStats.getWebstats_event();
            String webstats_email = webStats.getWebstats_email();
            String webstats_conn_test_result = webStats.getWebstats_conn_test_result();
            String connTestComment = webStats.getConnTestComment();
            String webstats_lat_test_result = webStats.getWebstats_lat_test_result();
            String latTestComment = webStats.getLatTestComment();
            String webstats_rdp_test_result = webStats.getWebstats_rdp_test_result();


            Map<String, Object> map = new HashMap<String, Object>();
            map.put("webStats", webStats);
            String supportEmail = (String) session.getAttribute("supportEmail");
            String eventTimezone = (String) session.getAttribute("eventTimezone");
            map.put("eventTimezone", eventTimezone);
            map.put("supportEmail", supportEmail);
            Long connstarted = (Long) session.getAttribute("connstartTime");
            map.put("connstarted", connstarted);
            map.put("userEventName", userEventName);
            map.put("userEventID", userEventID);
            String connectedIP = (String) session.getAttribute("clientIP");
            map.put("connectedIP", connectedIP);
            List<String> contacts = new ArrayList<String>();
            _LOG.info("---------------------supportEmail: " + supportEmail);
            contacts.add(supportEmail);

            _LOG.info("notification email start");
            // try {
            String emailHost = emailHostService.getEmailHost("smtp_server");
        
        	EmailSender.setEmailHost(emailHost);
                emailNotificationService.emailNotification(contacts, map);
            // } catch (Exception e) {
            // // TODO Auto-generated catch block
            //
            // _LOG.info("-------------------send email error:" + e);
            // }
            _LOG.info("notification email end");
            traceTestService.updateTraceRecord(webstats_id, webstats_fname, webstats_lname, webstats_event,
                webstats_email, webstats_conn_test_result, connTestComment, webstats_lat_test_result, latTestComment,
                webstats_rdp_test_result, userEventName);

            // emailNotificationService.emailNotification(contacts, map);
        } catch (Exception e) {
            result = "fail";
            _LOG.info(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
