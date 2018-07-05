package com.hp.vtms.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hp.vtms.VCloudExceptionsHandler;
import com.hp.vtms.model.Event;
import com.hp.vtms.model.Info;
import com.hp.vtms.model.RDPFileInformation;
import com.hp.vtms.model.User;
import com.hp.vtms.service.BizService;
import com.hp.vtms.service.EventService;
import com.hp.vtms.service.FTPService;
import com.hp.vtms.service.InfoService;
import com.hp.vtms.service.LatencyTestService;
import com.hp.vtms.util.ApplicationContainer;
import com.hp.vtms.vcloud.AppVCloudClient;
import com.hp.vtms.vcloud.model.SupportedVersions;

@Controller
@RequestMapping("event")
public class EventController {

    private static final Logger _LOG = LoggerFactory.getLogger(EventController.class);

    @Autowired
    private VCloudExceptionsHandler vCloudExceptionsHandler;
    @Autowired
    private FTPService ftpService;
    @Autowired
    private EventService eventService;
    @Autowired
    private LatencyTestService latencyTestService;
    @Autowired
    private AppVCloudClient vCloudClient;
    @Autowired
    private BizService bizService;
    @Autowired
    private InfoService infoService;

    @Value("#{envConfig.vcloud_versionUrl}")
    private String vcloudVersionUrl;

    @Value("#{envConfig.vcloud_ip}")
    private String vcloud_address;

    @Value("#{envConfig.ftp_url}")
    private String ftp_url;

    @Value("#{envConfig.connection_threshold}")
    private String connectionThreshold;

    @RequestMapping()
    public String index(ModelMap map, HttpServletRequest request) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Event> events = eventService.getEventsByUser(user);
        String supportEmail = null;
        String eventTimezone = null;
        _LOG.info(" " + events);
        map.put("events", events);
        if (user.getIsDemo() == null || user.getIsDemo() == false) {
            if (events.size() > 0) {
                String bizAbbr = events.get(0).getBusiness();
                supportEmail = bizService.getSupportEmailByBizAbbr(bizAbbr);
                map.put("getVCloudUrl", eventService.getEvent(user).getCellLocation());
                Info info = infoService.getInfo("connection_server");
                map.put("connectionUrl", info.getInfoValue());
                eventTimezone = events.get(0).getEventTimezone();
            } else {
                vCloudExceptionsHandler.customVcloudExceptions("603");
            }

        } else {
            Info info = infoService.getInfo("connection_server");
            map.put("connectionUrl", info.getInfoValue());
            info = infoService.getInfo("test_user_connection");
            map.put("rdpTest", info);
            supportEmail = user.getUserEmail();
        }
        Info info_latency = infoService.getInfo("latency_max");
        Info info_latency_offset = infoService.getInfo("latency_offset");
        if (info_latency != null) {
            map.put("latencyThreshold", info_latency.getInfoValue());
        }
        if (info_latency_offset != null) {
            map.put("infoLatencyOffsetDivide", info_latency_offset.getInfoValue());
            map.put("infoLatencyOffsetSubtract", info_latency_offset.getInfoValue2());
        }
        map.put("connectionThreshold", connectionThreshold);
        ApplicationContainer applicationContainer = ApplicationContainer.getInstance();
        String participantUrl = (String) applicationContainer.getObject(user.getEventId());
        user.setParticipantUrl(participantUrl);
        session.setAttribute("supportEmail", supportEmail);
        session.setAttribute("eventTimezone", eventTimezone);
        return "myEvents";
    }

    @RequestMapping("contection")
    public String contection(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        try {
            PrintWriter out = response.getWriter();
            HttpSession session = request.getSession();
            long ftpTime = -1, connectFinish = -1;
            long connectStart = System.currentTimeMillis();
            List<RDPFileInformation> ftpnameList = ftpService.FTPFolderList((String) session.getAttribute("username"));
            if (ftpnameList != null) {
                connectFinish = System.currentTimeMillis();
                ftpTime = connectFinish - connectStart;
            }

            long appvcloudStart = System.currentTimeMillis();
            long appvcloudTime = -1, appvcloudFinish = -1;
            SupportedVersions supportedVersions = vCloudClient.doGetXmlToModel(SupportedVersions.class,
                vcloudVersionUrl);
            if (supportedVersions != null) {
                appvcloudFinish = System.currentTimeMillis();
                appvcloudTime = appvcloudFinish - appvcloudStart;
            }

            out.print(latencyTestService.getConnectionTime(ftpTime, appvcloudTime, connectFinish, appvcloudFinish));
            out.close();
        } catch (IOException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
        return null;
    }

    private String pingIp(String ip) {
        String result = null;
        Runtime runtime = Runtime.getRuntime();
        Process process = null;
        InputStream is = null;
        BufferedReader br = null;
        Boolean res = false;
        String line = null;
        int i = 0;
        String time = null;
        float sumtime = 0.0f;
        float t = 0.0f;
        float averageTime = 0.0f;
        String ping = "";
        try {
            if (System.getProperties().getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
                ping = "ping " + ip;
                process = runtime.exec("ping " + ip);
            } else {
                ping = "ping -c 4 " + ip;

            }
            process = runtime.exec(ping);
            _LOG.info(ping);
            is = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                _LOG.info(line);
                if (line.contains("TTL") || line.contains("ttl")) {
                    int startIndex = line.indexOf("time=");
                    if (startIndex == -1) {
                        startIndex = line.indexOf("time<");
                    }
                    if (startIndex == -1) {
                        continue;
                    }
                    time = line.substring(startIndex + 5, line.indexOf("ms"));
                    t = Float.parseFloat(time);
                    sumtime += t;
                    i++;
                    _LOG.info(line);
                }
                if (i >= 4) {
                    res = true;
                    break;
                }
            }
            averageTime = sumtime / 4;
            is.close();
            br.close();
            if (res) {
                result = String.valueOf(averageTime);
                _LOG.info("ping " + ip + "averageTime is :" + averageTime);
            } else {
                result = "failed";
                _LOG.info("ping " + ip + " failed ");
            }
        } catch (Exception e) {
            _LOG.info(e.getMessage());
        }
        return result;
    }

    @RequestMapping("pingLatency")
    @ResponseBody
    public String pingLatency(HttpServletRequest request, HttpServletResponse response) {
        String latency = null;
        try {
            HttpSession session = request.getSession();
            String connectedIP = (String) session.getAttribute("clientIP");
            latency = pingIp(connectedIP);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latency;
    }

    public static void main(String args[]) {
        EventController ec = new EventController();
        String ip = "162.155.220.13";
        System.out.println(ec.pingIp(ip));
    }

}
