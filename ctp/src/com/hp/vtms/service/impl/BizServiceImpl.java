package com.hp.vtms.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.hp.vtms.service.BizService;

@Service
public class BizServiceImpl implements BizService {


    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(BizServiceImpl.class);

    @Override
    public String getSupportEmailByBizAbbr(String bizAbbr) {
        String supportEmail = null;
        Connection conn = null;
        PreparedStatement pst=null;
        try {
            conn = dataSource.getConnection();
            String sql = "select biz_id,biz_name,biz_abbr,biz_location,biz_contact,biz_email1,biz_email2 from biz where biz_abbr=?";
            pst = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE);
            pst.setString(1, bizAbbr);
            ResultSet rest = pst.executeQuery();
            if (rest.next()) {
                supportEmail = rest.getString(6);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (pst != null) {
                    pst.close();
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                log.error(e.toString());
                e.printStackTrace();
            }
        }
        return supportEmail;
    }

}
