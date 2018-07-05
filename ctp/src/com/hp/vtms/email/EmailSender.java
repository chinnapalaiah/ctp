package com.hp.vtms.email;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.hp.vtms.util.CommonUtils;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeUtility;

public final class EmailSender {

    private static final String EMAIL_MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String EMAIL_MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    private static final String EMAIL_MAIL_SMTP_SENDPARTIAL = "mail.smtp.sendpartial";
    private static Logger logger = LoggerFactory.getLogger(EmailSender.class);

    /**
     * mailSenderImpl
     */
    private static JavaMailSenderImpl javaMailSenderImpl;

    /**
     * emailSendHandle
     */
    private static EmailSender emailSender;

    private static Map<String, String> emailConf;
    
    private static String emailHost;

    /**
     * EmailSender constructor
     */
    private EmailSender() {
    }

    /**
     * getInstance
     * 
     * @return EmailSender
     */
    public static EmailSender getInstance() {

        if (emailConf == null) {
            try {
                emailConf = CommonUtils.getProperty("emailconf.properties");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                logger.error(e1.getMessage());
            }
        }

        String host = emailHost;
      
     
       // String host = emailConf.get("email_host");
        String mailSmtpAuth = emailConf.get("mail_smtp_auth");
        String mailSmtpTimeout = emailConf.get("mail_smtp_timeout");
        String mailSmtpSendPartial = emailConf.get("mail_smtp_sendpartial");
        String defaultEncoding = emailConf.get("email_default_encoding");
        String username = emailConf.get("email_username");
        String password = emailConf.get("email_password");
        if (emailSender == null) {
            emailSender = new EmailSender();

            try {
                javaMailSenderImpl = new JavaMailSenderImpl();

                Properties p = new Properties();
                p.put(EMAIL_MAIL_SMTP_AUTH, mailSmtpAuth);
                p.put(EMAIL_MAIL_SMTP_TIMEOUT, mailSmtpTimeout);
                p.put(EMAIL_MAIL_SMTP_SENDPARTIAL, mailSmtpSendPartial);

                javaMailSenderImpl.setHost(host);
                javaMailSenderImpl.setJavaMailProperties(p);
                javaMailSenderImpl.setDefaultEncoding(defaultEncoding);
                javaMailSenderImpl.setUsername(username);
                javaMailSenderImpl.setPassword(password);

            } catch (Exception e) {
                logger.error("exceptions happen when init the email sender instance", e);
            }
        }

        return emailSender;
    }

    /**
     * sendEmail
     * 
     * @param emailEntity
     */
    public void sendEmail(EmailEntity emailEntity) {
        this.sendEmail(emailEntity, false);
    }

    /**
     * sendEmail
     * 
     * @param emailEntity
     *            emailEntity
     * @param html
     *            html
     */
    public void sendEmail(final EmailEntity emailEntity, final boolean html) {
        ThreadPoolService.getInstance().execute(new Runnable() {

            public void run() {
                sendEmailAsync(emailEntity, html);
            }

        });
        // sendEmailAsync(emailEntity, html);
    }

    /**
     * sendEmailAsync
     * 
     * @param emailEntity
     *            emailEntity
     * @param html
     *            html
     */
    private void sendEmailAsync(final EmailEntity emailEntity, final boolean html) {


        if (emailEntity == null) {
            logger.error("parameters are wrong, please check");
            return;
        }

        logger.info("----------------start sending email thread[" + Arrays.deepToString(emailEntity.getTo()) + "]");

        if (emailEntity.getTo() == null || emailEntity.getTo().length == 0) {
            logger.error("the to email address is empty, please check");
            return;
        }

        MimeMessage msg = null;

        try {
            msg = javaMailSenderImpl.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
            String[] emails = emailEntity.getTo();
            helper.setTo(emails);
            helper.setFrom("no-reply@hp.com");
            if (emailEntity.getCc() != null && emailEntity.getCc().length > 0) {
                helper.setCc(emailEntity.getCc());
            }
            // bcc
            if (emailEntity.getBcc() != null && emailEntity.getBcc().length > 0) {
                helper.setBcc(emailEntity.getBcc());
            }

            // subject
            if (emailEntity.getSubject() != null && !emailEntity.getSubject().equals("")) {
                helper.setSubject(emailEntity.getSubject());
            }

            // text
            if (emailEntity.getText() != null && !emailEntity.getText().equals("")) {
                helper.setText(emailEntity.getText(), html);
            }

            // addInline, support image
            processEmailAddlineImage(emailEntity, helper, html);

            // attachment
            processEmailAttachment(emailEntity, helper);

            // send emial
            javaMailSenderImpl.send(msg);

        } catch (MailSendException e) {
            logger.warn("exceptions happen when sending email", e);
        } catch (MessagingException e1) {
            logger.warn("exceptions happen when sending email", e1);
        } catch (UnsupportedEncodingException e2) {
            logger.warn("exceptions happen when sending email", e2);
        } catch (IOException e3) {
            logger.warn("exceptions happen when sending email", e3);
        } catch (Exception e4) {
            logger.warn("exceptions happen when sending email", e4);
        }
        logger.info("-----------------------------end sending email thread ");

    }

    /**
     * processEmailAddlineImage
     * 
     * @param emailEntity
     *            emailEntity
     * @param helper
     *            helper
     * @param html
     *            html
     * @throws MessagingException
     *             MessagingException
     * @throws IOException
     *             IOException
     * @throws javax.mail.MessagingException
     */
    private void processEmailAddlineImage(EmailEntity emailEntity, MimeMessageHelper helper, boolean html)
        throws MessagingException, IOException, javax.mail.MessagingException {

        if (html && emailEntity.getImageMap() != null && emailEntity.getImageMap().size() > 0) {
            Map<String, String> imageMap = emailEntity.getImageMap();
            Set<String> contentIdSet = imageMap.keySet();
            for (String contentId : contentIdSet) {
                Resource resource = null;
                try {
                    resource = new ClassPathResource(imageMap.get(contentId));
                    resource.getURL();
                } catch (FileNotFoundException f) {
                    resource = new FileSystemResource(new File(imageMap.get(contentId)));
                }

                if (resource != null) {
                    helper.addInline(contentId, resource);
                }
            }
        }

    }

    /**
     * @param emailEntity
     *            emailEntity
     * @param helper
     *            helper
     * @throws MessagingException
     *             MessagingException
     * @throws IOException
     *             IOException
     * @throws javax.mail.MessagingException
     */
    private void processEmailAttachment(EmailEntity emailEntity, MimeMessageHelper helper) throws MessagingException,
        IOException, javax.mail.MessagingException {
        if (emailEntity.getAttachmentList() != null && emailEntity.getAttachmentList().size() > 0) {
            for (Object attachment : emailEntity.getAttachmentList()) {

                if (attachment != null) {

                    if (attachment instanceof String) {
                        Resource resource = null;
                        try {
                            resource = new ClassPathResource(String.valueOf(attachment));
                            resource.getURL();
                        } catch (FileNotFoundException f) {
                            resource = new FileSystemResource(new File(String.valueOf(attachment)));
                        }
                        if (resource != null) {
                            helper.addAttachment(MimeUtility.encodeWord(resource.getFilename()), resource);
                        }
                    } else if (attachment instanceof Resource) {
                        String attachmentName = ((Resource) attachment).getFilename();
                        if (attachmentName == null || attachmentName.isEmpty()) {
                            if (attachment instanceof ByteArrayResource) {
                                attachmentName = ((ByteArrayResource) attachment).getDescription();
                            }
                        }
                        helper.addAttachment(MimeUtility.encodeWord(attachmentName), (Resource) attachment);
                    }

                }
            }
        }
    }

    /**
     * processAssignedEmailAddress
     * 
     * @param assignedEmailAddress
     * @param emailEntity
     * @param helper
     * @throws MessagingException
     * @throws javax.mail.MessagingException
     */
    private void processAssignedEmailAddress(String assignedEmailAddress, EmailEntity emailEntity,
        MimeMessageHelper helper) throws MessagingException, javax.mail.MessagingException {
        logger.info("send the email to the assigned user :" + assignedEmailAddress);

        String[] emailAddress = assignedEmailAddress.trim().split(",");

        List<String> toAddressList = new ArrayList<String>();

        if (emailAddress != null && emailAddress.length > 0) {
            for (int i = 0; i < emailAddress.length; i++) {
                if (emailAddress[i] != null && !emailAddress[i].trim().equals("")) {
                    toAddressList.add(emailAddress[i].trim());
                }
            }
        }

        // String[] toArray = this.filterEmailAddress(emailEntity.getTo());
        // if (toArray != null && toArray.length > 0) {
        // for (String address : toArray) {
        // toAddressList.add(address);
        // }
        // }

        helper.setTo(toAddressList.toArray(new String[toAddressList.size()]));

        // String[] ccArray = this.filterEmailAddress(emailEntity.getCc());
        // if (ccArray != null && ccArray.length > 0) {
        // helper.setCc(ccArray);
        // }
        //
        // String[] bccArray = this.filterEmailAddress(emailEntity.getBcc());
        // if (bccArray != null && bccArray.length > 0) {
        // helper.setBcc(bccArray);
        // }
    }

    /**
     * filterEmailAddress
     * 
     * @param addreassArray
     * @return
     */
    private String[] filterEmailAddress(String[] addreassArray) {

        String filterCondition = "@hp.com";

        if (addreassArray == null || addreassArray.length == 0) {
            return null;
        }

        List<String> addressList = new ArrayList<String>();

        for (String address : addreassArray) {
            if (address != null && address.endsWith(filterCondition)) {
                logger.debug("valid address:" + address.trim());
                addressList.add(address.trim());
            } else {
                logger.debug("filtered out address:" + address.trim());
            }
        }

        return addressList.toArray(new String[addressList.size()]);
    }
	public static void setEmailHost(String host){
		emailHost = host;
		logger.info("setted email host value -------> "+emailHost);
	}

}
