package com.hp.vtms.service.impl;

import com.hp.vtms.service.LatencyTestService;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-12-25 Time: 2:45 To change
 * this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class LatencyTestServiceImpl implements LatencyTestService {
	private static Logger _LOG = LoggerFactory.getLogger(LatencyTestServiceImpl.class);
	@Value("#{envConfig.ftp_url}")
	private String ftp_url;
	@Value("#{envConfig.vcloud_ip}")
	private String vcloud_ip;
	private static final int timeout = 50000;

	public long getCurrentTimeMillis(String ipAddress) {

		try {
			long connectStart = System.currentTimeMillis();

			InetAddress address = InetAddress.getByName(ipAddress);
			if (address.isReachable(timeout)) {
				long connectFinish = System.currentTimeMillis();
				// System.out.println(Long.toString(connectFinish -
				// connectStart) + "ms");
				return (connectFinish - connectStart);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public String getTotalTime() {
		if (getCurrentTimeMillis(ftp_url) != -1 && getCurrentTimeMillis(vcloud_ip) != -1) {
			return Long.toString(getCurrentTimeMillis(ftp_url) + getCurrentTimeMillis(vcloud_ip));
		} else if (getCurrentTimeMillis(ftp_url) == -1) {
			return "ftp connection error";
		} else if (getCurrentTimeMillis(vcloud_ip) == -1) {
			return "vcloud connection error";
		}
		return "connection error";
	}

	@Override
	public String getConnectionTime(long ftpTime, long appvcloudTime, long connectFinish, long appvcloudFinish) {
		if (connectFinish != -1 && appvcloudFinish != -1) {
			return Long.toString(ftpTime + appvcloudTime);
		} else if (connectFinish == -1 && appvcloudFinish != -1) {
			return "getFTPFiles error";
		} else if (connectFinish != -1 && appvcloudFinish == -1) {
			return "getVcloudFiles error";
		}
		return "error";
	}
}
