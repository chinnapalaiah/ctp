package com.hp.vtms.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.hp.vtms.service.TraceTestService;
import com.hp.vtms.util.CommonUtils;
import com.hp.vtms.vcloud.model.WebStats;

public class TraceTestServiceImpl implements TraceTestService {

    private static final Logger log = LoggerFactory.getLogger(TraceTestServiceImpl.class);

    public TraceTestServiceImpl() {
    }

    @Override
    public Long addTraceRecord(WebStats webStats) {
        Long keyId = null;
        Connection conn = null;
        PreparedStatement pst = null;
        Boolean result = true;
        try {

            String sql = "INSERT INTO webstats "
                + " (webstats_fname,webstats_lname,webstats_username,webstats_email,webstats_is_demouser,"
                + " webstats_event,webstats_event_id,webstats_event_start,webstats_event_end,webstats_conn_ip,"
                + " webstats_conn_start,webstats_conn_end,webstats_conn_test_result,webstats_lat_test_result,webstats_rdp_test_result)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = getJdbcConnection();
            conn.setAutoCommit(true);
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, webStats.getWebstats_fname());
            pst.setString(2, webStats.getWebstats_lname());
            pst.setString(3, webStats.getWebstats_username());
            pst.setString(4, webStats.getWebstats_email());
            pst.setString(5, webStats.getWebstats_is_demouser());
            pst.setString(6, webStats.getWebstats_event());
            pst.setString(7, webStats.getWebstats_event_id());
            Timestamp webstats_event_start = null;
            if (webStats.getWebstats_event_start() != null) {
                webstats_event_start = new Timestamp(webStats.getWebstats_event_start());
            }
            pst.setTimestamp(8, webstats_event_start);
            Timestamp webstats_event_end = null;
            if (webStats.getWebstats_event_end() != null) {
                webstats_event_end = new Timestamp(webStats.getWebstats_event_end());
            }
            pst.setTimestamp(9, webstats_event_end);
            pst.setString(10, webStats.getWebstats_conn_ip());
            Timestamp webstats_conn_start = null;
            if (webStats.getWebstats_conn_start() != null) {
                webstats_conn_start = new Timestamp(webStats.getWebstats_conn_start());
            }
            pst.setTimestamp(11, webstats_conn_start);
            Timestamp webstats_conn_end = null;
            if (webStats.getWebstats_conn_end() != null) {
                webstats_conn_end = new Timestamp(webStats.getWebstats_conn_end());
            }
            pst.setTimestamp(12, webstats_conn_end);
            pst.setString(13, webStats.getWebstats_conn_test_result());
            pst.setString(14, webStats.getWebstats_lat_test_result());
            pst.setString(15, webStats.getWebstats_rdp_test_result());
            result = pst.execute();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                keyId = rs.getLong(1);
            }
            conn.commit();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            rollBack(conn);
            result = false;
            log.info("----------------traceerror: " + e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("----------------database connection close after login.");
            closeConn(conn, pst);
        }
        return keyId;
    }

    @Override
    public Long updateTraceRecord(Long webstats_id, String webstats_fname, String webstats_lname,
        String webstats_event, String webstats_email, String webstats_conn_test_result, String connTestComment,
        String webstats_lat_test_result, String latTestComment, String webstats_rdp_test_result, String userEventName) {
        String sql = "update webstats set webstats_fname =?,webstats_lname =?,webstats_event =?,webstats_email =?,"
            + "webstats_conn_test_result =?,webstats_lat_test_result =?,webstats_rdp_test_result =?  where webstats_id=?";

        Long keyId = null;
        Connection conn = null;
        PreparedStatement pst = null;
        Boolean result = true;
        try {
            // conn = dataSource.getConnection();
            conn = getJdbcConnection();
            conn.setAutoCommit(true);

            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, webstats_fname);
            pst.setString(2, webstats_lname);
            pst.setString(3, webstats_event);
            pst.setString(4, webstats_email);
            pst.setString(5, webstats_conn_test_result);
            pst.setString(6, webstats_lat_test_result);
            pst.setString(7, webstats_rdp_test_result);
            pst.setLong(8, webstats_id);
            result = pst.execute();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                keyId = rs.getLong(1);
            }
            conn.commit();
        } catch (Exception e) {
            rollBack(conn);
            result = false;
            log.info("----------------traceerror: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConn(conn, pst);
            log.info("----------------database connection close after send mail.");
        }
        return keyId;

    }

    public Long updateTraceRecord(Long webstats_id, Long connendTime) {
        String sql = "update webstats set webstats_conn_end =? where webstats_id=?";

        Long keyId = null;
        Connection conn = null;
        PreparedStatement pst = null;
        Boolean result = true;
        try {

            conn = getJdbcConnection();
            conn.setAutoCommit(true);
            pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Timestamp webstats_conn_end = null;
            if (connendTime != null) {
                webstats_conn_end = new Timestamp(connendTime);
            }
            pst.setTimestamp(1, webstats_conn_end);
            pst.setLong(2, webstats_id);
            result = pst.execute();
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                keyId = rs.getLong(1);
            }
            conn.commit();
        } catch (Exception e) {
            rollBack(conn);
            result = false;
            log.info("----------------traceerror: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeConn(conn, pst);
            log.info("----------------database connection close after loginout.");
        }
        return keyId;
    }

    private void closeConn(Connection conn, Statement pst) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.info("----------------traceerror sql server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void rollBack(Connection conn) {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.info("----------------traceerror: sql server" + e.getMessage());
        }
    }

    private Connection getJdbcConnection() throws SQLException, ClassNotFoundException {
        Map<String, String> map = new HashMap<String, String>();
        String driverClassName = null;
        String urluag = null;
        String usernameuag = null;
        String passworduag = null;
        try {
            map = CommonUtils.getProperty("jdbc.properties");
            driverClassName = map.get("jdbc.driverClassName");
            urluag = map.get("jdbc.urluag");
            usernameuag = map.get("jdbc.usernameuag");
            passworduag = map.get("jdbc.passworduag");
            Class.forName(driverClassName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.info("----------------traceerror: " + e.getMessage());
            e.printStackTrace();
        }

        Connection dbConn = DriverManager.getConnection(urluag, usernameuag, passworduag);
        return dbConn;
    }
}
