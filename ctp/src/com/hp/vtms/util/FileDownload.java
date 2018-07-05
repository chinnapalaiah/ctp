package com.hp.vtms.util;

import com.hp.vtms.service.impl.FTPServiceImpl;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA. User: meij Date: 13-12-3 Time: 2:47 To change
 * this template use File | Settings | File Templates.
 */
public class FileDownload {

    private static Logger _LOG = LoggerFactory.getLogger(FTPServiceImpl.class);

//    public static ChannelSftp connect(String host, int port, String username, String password) {
//        ChannelSftp sftp = new ChannelSftp();
//        try {
//            JSch jsch = new JSch();
//            jsch.getSession(username, host, port);
//            Session sshSession = jsch.getSession(username, host, port);
//            // System.out.println("Session created.");
//            sshSession.setPassword(password);
//            Properties sshConfig = new Properties();
//            sshConfig.put("StrictHostKeyChecking", "no");
//            sshSession.setConfig(sshConfig);
//            sshSession.connect();
//            // System.out.println("Session connected.");
//            // System.out.println("Opening Channel.");
//            Channel channel = sshSession.openChannel("sftp");
//            channel.connect();
//            sftp = (ChannelSftp) channel;
//
//            System.out.println("Connected to " + host + ".");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sftp;
//    }

    public void download(String directory, String downloadFile, String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file = new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	if(sftp!=null){
                sftp.quit();
                sftp.disconnect();
            	}
        }
    }

    public static String changeSystemStr(String str) {
        //windows
        if ("\\".equals(File.separator)) {
            str = str.replace("/", "\\");
        }
        //linux
        else if ("/".equals(File.separator)) {
            str = str.replace("\\", "/");
        }
        return str;
    }

    public static InputStream downFile(String url, int port, String sftpUsername, String sftpPassword,
                                       String remotePath, String fileName, String downloadPath) {
        ChannelSftp sftp = new ChannelSftp();
        InputStream inStream = null;
        Session sshSession = null;
        Channel channel = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(sftpUsername, url, port);
            sshSession = jsch.getSession(sftpUsername, url, port);
            // System.out.println("Session created.");
            sshSession.setPassword(sftpPassword);
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

//            sftp = connect(url, port, sftpUsername, sftpPassword);
            sftp.cd(remotePath);
            Vector v = sftp.ls(remotePath);
            for (int i = 0; i < v.size(); i++) {
                ChannelSftp.LsEntry a = (ChannelSftp.LsEntry) v.get(i);
                if (a.getFilename().equals(fileName)) {
//					inStream = sftp.get(a.getFilename());
                    new File(changeSystemStr(downloadPath + remotePath)).mkdirs();
                    File file = new File(changeSystemStr(downloadPath + remotePath) + File.separator + a.getFilename());
                    sftp.get(a.getFilename(), new FileOutputStream(file));
                    inStream = new FileInputStream(changeSystemStr(downloadPath + remotePath) + File.separator + a.getFilename());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	if(channel!=null){
            _LOG.info("exit-status: " + channel.getExitStatus());
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
        return inStream;
    }
    // public static InputStream downFile(String url, int port, String username,
    // String password, String remotePath,
    // String fileName) throws IOException {
    // boolean success = false;
    // FTPClient ftp = new FTPClient();
    // InputStream inStream = null;
    // try {
    // int reply;
    // ftp.connect(url, port);
    // ftp.login(username, password);// login
    // reply = ftp.getReplyCode(); // 230 is successful
    // if (!FTPReply.isPositiveCompletion(reply)) {
    // ftp.disconnect();
    // _LOG.info("connection failed");
    // }
    // ftp.changeWorkingDirectory(remotePath);// turn to the ftp server
    // // path
    //
    // String[] ftpnames = ftp.listNames();
    // for (int i = 0; i < ftpnames.length; i++) {
    // _LOG.info(ftpnames[i]);
    // }
    //
    // FTPFile[] fs = ftp.listFiles(remotePath);
    // _LOG.info("get ftp file size is---" + fs.length);
    // for (FTPFile ff : fs) {
    // if (ff.getName().equals(fileName)) {
    // inStream = ftp.retrieveFileStream(ff.getName());
    // }
    // }
    //
    // ftp.logout();
    // success = true;
    // } catch (IOException e) {
    // e.printStackTrace();
    // } finally {
    // if (ftp.isConnected()) {
    // try {
    // ftp.disconnect();
    // } catch (IOException ioe) {
    // }
    // }
    // }
    // return inStream;
    // }

}
