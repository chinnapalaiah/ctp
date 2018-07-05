package com.hp.vtms.email;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.hp.vtms.util.CommonUtils;
import com.hp.vtms.vcloud.model.WebStats;

public class EmailNotificationService {

    private static final Logger IMPLLOG = LoggerFactory.getLogger(EmailNotificationService.class);
    private String NA = "N/A";

    public void emailNotification(List<String> contacts, Map<String, Object> model) {

        WebStats webStats = (WebStats) model.get("webStats");
        String subject = "Website Test Result ";

        String format = "MM/dd/yyyy HH:mm:ss";
        String templatePath = "/successfulEmailNotification.vm";
        Map<String, Object> valueMap = new HashMap<String, Object>();
        String currdateUTC = CommonUtils.getUTCTime();
        String eventTimezone = (String) model.get("eventTimezone");
        String supportEmail = (String) model.get("supportEmail");
        String connectedIP = (String) model.get("connectedIP");
        this.putValueMap(valueMap, "currdate", currdateUTC);
        String connTestComment = StringUtils.isEmpty(webStats.getConnTestComment()) ? "" : "("
            + webStats.getConnTestComment() + ")";
        String latTestComment = StringUtils.isEmpty(webStats.getLatTestComment()) ? "" : "("
            + webStats.getLatTestComment() + "ms)";

        Long connStartTime = (Long) model.get("connstarted");
        String connstarted = CommonUtils.formatDate(new Date(connStartTime), format);
        this.putValueMap(valueMap, "supportEmail", supportEmail);
        this.putValueMap(valueMap, "connstarted", connstarted);
        this.putValueMap(valueMap, "connended", "");
        this.putValueMap(valueMap, "connTestComment", connTestComment);
        this.putValueMap(valueMap, "latTestComment", latTestComment);
        this.putValueMap(valueMap, "webstats_fname", webStats.getWebstats_fname());
        this.putValueMap(valueMap, "webstats_lname", webStats.getWebstats_lname());
        this.putValueMap(valueMap, "webstats_username", webStats.getWebstats_username());
        this.putValueMap(valueMap, "webstats_email", webStats.getWebstats_email());
        String isDemouser = "1".equals(webStats.getWebstats_is_demouser()) ? "Yes" : "No";
        this.putValueMap(valueMap, "webstats_is_demouser", isDemouser);
        String eventName = (String) model.get("userEventName");
        this.putValueMap(valueMap, "webstats_event", eventName);
        String eventId = (String) model.get("userEventID");
        this.putValueMap(valueMap, "webstats_event_id", eventId);
        Long eventStart = webStats.getWebstats_event_start();
        String eventStartTime = "1".equals(webStats.getWebstats_is_demouser()) ? "" : CommonUtils.formatDate(new Date(
            eventStart), format)
            + " "
            + eventTimezone;
        this.putValueMap(valueMap, "webstats_event_start", eventStartTime);
        String eventEndTime = "1".equals(webStats.getWebstats_is_demouser()) ? "" : CommonUtils.formatDate(new Date(
            webStats.getWebstats_event_end()), format)
            + " " + eventTimezone;
        this.putValueMap(valueMap, "webstats_event_end", eventEndTime);
        this.putValueMap(valueMap, "connectedIP", connectedIP);
        this.putValueMap(valueMap, "webstats_conn_start", webStats.getWebstats_conn_start());
        this.putValueMap(valueMap, "webstats_conn_end", webStats.getWebstats_conn_end());
        this.putValueMap(valueMap, "webstats_conn_test_result",
            CommonUtils.uppcaseFirstLetter(webStats.getWebstats_conn_test_result()));
        this.putValueMap(valueMap, "webstats_lat_test_result",
            CommonUtils.uppcaseFirstLetter(webStats.getWebstats_lat_test_result()));
        this.putValueMap(valueMap, "webstats_rdp_test_result",
            CommonUtils.uppcaseFirstLetter(webStats.getWebstats_rdp_test_result()));

        String decoratedPath = "/emailDecorator.vm";
        Map<String, Object> decoratedValueMap = new HashMap<String, Object>();
        decoratedValueMap.put("contentTitle", subject);
        DecoratedEmailEntity decoratedEmailEntity = new DecoratedEmailEntity();
        decoratedEmailEntity.setDecoratorTemplatePath(decoratedPath);
        decoratedEmailEntity.setDecoratorValueMap(decoratedValueMap);
        decoratedEmailEntity.setTemplatePath(templatePath);
        decoratedEmailEntity.setValueMap(valueMap);
        decoratedEmailEntity.setSubject(subject);

        if (contacts != null) {
            String[] strs = (String[]) contacts.toArray(new String[contacts.size()]);
            decoratedEmailEntity.setTo(strs);
            decoratedEmailEntity.setSubject(subject);
            decoratedEmailEntity.setDecoratedText();
            EmailSender.getInstance().sendEmail(decoratedEmailEntity, true);
        }
    }
    private void putValueMap(Map<String, Object> valueMap, String paramName, Object paramValue) {
        if (paramValue != null) {
            valueMap.put(paramName, paramValue);
        } else {
            valueMap.put(paramName, NA);
        }
    }

    public static void main(String args[]) {
        EmailNotificationService email = new EmailNotificationService();
        List<String> contacts = new ArrayList<String>();
        contacts.add("yun-tao.xu@hp.com");
        WebStats webStats = new WebStats();
        webStats.setWebstats_fname("Beck");
        webStats.setWebstats_lname("XU");
        webStats.setWebstats_event_start(new Date().getTime());
        webStats.setWebstats_event_end(new Date().getTime());
        webStats.setWebstats_conn_start(new Date().getTime());
        webStats.setWebstats_conn_end(new Date().getTime());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("webStats", webStats);
        map.put("eventTimezone", "USA-EasternTime");
        email.emailNotification(contacts, map);
        // 1、取得本地时间：
        Calendar gc = GregorianCalendar.getInstance();
        String utc = DateFormatUtils.formatUTC(gc.getTime(), "MM/dd/yyyy HH:mm:ss", Locale.US);
        System.out.println(utc);
    }
}
