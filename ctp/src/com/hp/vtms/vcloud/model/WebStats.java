package com.hp.vtms.vcloud.model;


public class WebStats {

    private String webstats_id;
    private String webstats_fname;
    private String webstats_lname;
    private String webstats_username;
    private String webstats_email;
    private String webstats_is_demouser;
    private String webstats_event;
    private String webstats_event_id;
    private Long webstats_event_start;
    private Long webstats_event_end;
    private String webstats_conn_ip;
    private Long webstats_conn_start;
    private Long webstats_conn_end;
    private String webstats_conn_test_result;
    private String connTestComment;
    private String webstats_lat_test_result;
    private String latTestComment;
    private String webstats_rdp_test_result;

    

    public WebStats() {
        super();
    }

    public WebStats(String webstats_fname, String webstats_lname, String webstats_username, String webstats_email,
        String webstats_event, String webstats_event_id, String webstats_conn_ip,
        String webstats_conn_test_result, String webstats_lat_test_result, String webstats_rdp_test_result) {
        super();
        this.webstats_fname = webstats_fname;
        this.webstats_lname = webstats_lname;
        this.webstats_username = webstats_username;
        this.webstats_email = webstats_email;
        this.webstats_event = webstats_event;
        this.webstats_event_id = webstats_event_id;
        this.webstats_conn_ip = webstats_conn_ip;
        this.webstats_conn_test_result = webstats_conn_test_result;
        this.webstats_lat_test_result = webstats_lat_test_result;
        this.webstats_rdp_test_result = webstats_rdp_test_result;
    }

    public String getWebstats_id() {
        return webstats_id;
    }

    public void setWebstats_id(String webstats_id) {
        this.webstats_id = webstats_id;
    }

    public String getWebstats_fname() {
        return webstats_fname;
    }

    public void setWebstats_fname(String webstats_fname) {
        this.webstats_fname = webstats_fname;
    }

    public String getWebstats_lname() {
        return webstats_lname;
    }

    public void setWebstats_lname(String webstats_lname) {
        this.webstats_lname = webstats_lname;
    }

    public String getWebstats_username() {
        return webstats_username;
    }

    public void setWebstats_username(String webstats_username) {
        this.webstats_username = webstats_username;
    }

    public String getWebstats_email() {
        return webstats_email;
    }

    public void setWebstats_email(String webstats_email) {
        this.webstats_email = webstats_email;
    }

    public String getWebstats_is_demouser() {
        return webstats_is_demouser;
    }

    public void setWebstats_is_demouser(String webstats_is_demouser) {
        this.webstats_is_demouser = webstats_is_demouser;
    }

    public String getWebstats_event() {
        return webstats_event;
    }

    public void setWebstats_event(String webstats_event) {
        this.webstats_event = webstats_event;
    }

    public String getWebstats_event_id() {
        return webstats_event_id;
    }

    public void setWebstats_event_id(String webstats_event_id) {
        this.webstats_event_id = webstats_event_id;
    }

    public String getWebstats_conn_ip() {
        return webstats_conn_ip;
    }

    public void setWebstats_conn_ip(String webstats_conn_ip) {
        this.webstats_conn_ip = webstats_conn_ip;
    }

    public String getWebstats_conn_test_result() {
        return webstats_conn_test_result;
    }

    public void setWebstats_conn_test_result(String webstats_conn_test_result) {
        this.webstats_conn_test_result = webstats_conn_test_result;
    }

    public String getWebstats_lat_test_result() {
        return webstats_lat_test_result;
    }

    public void setWebstats_lat_test_result(String webstats_lat_test_result) {
        this.webstats_lat_test_result = webstats_lat_test_result;
    }

    public String getWebstats_rdp_test_result() {
        return webstats_rdp_test_result;
    }

    public void setWebstats_rdp_test_result(String webstats_rdp_test_result) {
        this.webstats_rdp_test_result = webstats_rdp_test_result;
    }

    public Long getWebstats_event_start() {
        return webstats_event_start;
    }

    public void setWebstats_event_start(Long webstats_event_start) {
        this.webstats_event_start = webstats_event_start;
    }

    public Long getWebstats_event_end() {
        return webstats_event_end;
    }

    public void setWebstats_event_end(Long webstats_event_end) {
        this.webstats_event_end = webstats_event_end;
    }

    public Long getWebstats_conn_start() {
        return webstats_conn_start;
    }

    public void setWebstats_conn_start(Long webstats_conn_start) {
        this.webstats_conn_start = webstats_conn_start;
    }

    public Long getWebstats_conn_end() {
        return webstats_conn_end;
    }

    public void setWebstats_conn_end(Long webstats_conn_end) {
        this.webstats_conn_end = webstats_conn_end;
    }

    public String getConnTestComment() {
        return connTestComment;
    }

    public void setConnTestComment(String connTestComment) {
        this.connTestComment = connTestComment;
    }

    public String getLatTestComment() {
        return latTestComment;
    }

    public void setLatTestComment(String latTestComment) {
        this.latTestComment = latTestComment;
    }

}

