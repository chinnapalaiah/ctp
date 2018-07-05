package com.hp.vtms.service.impl;

import com.hp.vtms.model.RDPFileInformation;
import com.hp.vtms.service.FTPService;
import com.hp.vtms.util.FileDownload;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import javax.servlet.http.HttpServletResponse;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-12-9 Time: 2:04 To change
 * this template use File | Settings | File Templates.
 */
@Service
@Transactional
public class FTPServiceImpl implements FTPService {

    private static Logger _LOG = LoggerFactory.getLogger(FTPServiceImpl.class);

    @Value("#{envConfig.ftp_url}")
    private String url;
    @Value("#{envConfig.ftp_username}")
    private String FTPusername;
    @Value("#{envConfig.ftp_password}")
    private String FTPpassword;
    @Value("#{envConfig.ftp_port}")
    private Integer port;
    @Value("#{envConfig.root_directory}")
    private String folder;
//	@Override
//	public List FTPFolderList(String remotePath) {
//
//		remotePath = "/" + remotePath;
//		boolean success = false;
//		FTPClient ftp = new FTPClient();
//		InputStream inStream = null;
//		List ftpnameList = new ArrayList();
//		try {
//			int reply;
//			ftp.connect(url, port);
//			ftp.login(FTPusername, FTPpassword);// login
//			reply = ftp.getReplyCode(); // 230 is successful
//			if (!FTPReply.isPositiveCompletion(reply)) {
//				ftp.disconnect();
//				_LOG.info("connection failed");
//			}
//
//			if (ftp.changeWorkingDirectory(remotePath)) {
//				String[] ftpnames = ftp.listNames();
//				for (int i = 0; i < ftpnames.length; i++) {
//					RDPFileInformation rdpFileInformation = new RDPFileInformation();
//					rdpFileInformation.setRdpfileRealName(ftpnames[i].split("\\.")[0]);
//					rdpFileInformation.setRdpfilename(ftpnames[i]);
//					rdpFileInformation.setRdpfilePath(remotePath);
//					ftpnameList.add(rdpFileInformation);
//				}
//			}
//
//			ftp.logout();
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (ftp.isConnected()) {
//				try {
//					ftp.disconnect();
//				} catch (IOException ioe) {
//				}
//			}
//		}
//		return ftpnameList;
//	}

    @Override
    public List FTPFolderList(String directory) {
        directory = "/" + folder + "/" + directory;
        List ftpnameList = new ArrayList();
        ChannelSftp sftp = new ChannelSftp();
        InputStream inStream = null;
        Session sshSession = null;
        Channel channel = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(FTPusername, url, port);
            sshSession = jsch.getSession(FTPusername, url, port);
            // System.out.println("Session created.");
            sshSession.setPassword(FTPpassword);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            // System.out.println("Session connected.");
            // System.out.println("Opening Channel.");
            channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            System.out.println("Connected to " + url + ".");
//            sftp = FileDownload.connect(url, port, FTPusername, FTPpassword);
            if (sftp.isConnected() != true) {
                sftp.disconnect();
                _LOG.info("--------------------------------------sftp connection failed");
            } else {
                _LOG.info("--------------------------------------sftp folder is---" + directory);
                sftp.cd(directory);

                Vector v = sftp.ls(directory);
                for (int i = 0; i < v.size(); i++) {
                    ChannelSftp.LsEntry a = (ChannelSftp.LsEntry) v.get(i);
                    if (!a.getFilename().equals(".") && !a.getFilename().equals("..")) {
                        RDPFileInformation rdpFileInformation = new RDPFileInformation();
                        rdpFileInformation.setRdpfileRealName(a.getFilename().split("\\.")[0]);
                        rdpFileInformation.setRdpfilename(a.getFilename());
                        rdpFileInformation.setRdpfilePath(directory);
                        ftpnameList.add(rdpFileInformation);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
        	   if(channel!=null){
                channel.disconnect();
            	}
            	if(sshSession!=null){
                sshSession.disconnect();
            	}
            	if(sftp!=null){
                sftp.quit();
                sftp.disconnect();
            	}
        }
        return ftpnameList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void downLoadOnline(String filePath, String filename, HttpServletResponse response, boolean isOnLine)
            throws IOException {
        BufferedInputStream br = null;
        OutputStream out = null;
        String downloadPath = this.getClass().getClassLoader().getResource("").getPath();
        _LOG.info("the download files path is ----------------" + downloadPath);
        try {
            if (filename == "") {
                response.sendError(404, "File not found!");
                return;
            }

            String type = "";
            if (filename.toLowerCase().indexOf(".rdp") > 0) {
                type = "application/x-rdp";
            }
            //ftp
//			br = new BufferedInputStream(FileDownload.downFile(url, port, FTPusername, FTPpassword, "/" + filePath,
//					filename));
            br = new BufferedInputStream(FileDownload.downFile(url, port, FTPusername, FTPpassword, "/" + filePath, filename, downloadPath));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset();
            if (isOnLine) {
                response.setContentType(type);
                response.setHeader("Content-Disposition", "inline; filename=" + filename);

            } else {
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            }
            out = response.getOutputStream();
            while ((len = br.read(buf)) > 0)
                out.write(buf, 0, len);
        } catch (IOException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        } finally {
        	if(out!=null){
                out.flush();
                out.close();
            	}
            	if(br!=null){
                br.close();
            	}
        }
    }

}
