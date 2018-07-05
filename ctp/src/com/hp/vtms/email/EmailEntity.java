package com.hp.vtms.email;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * EmailEntity
 * 
 * @author yue
 */
public class EmailEntity {
  
    // private String from;
    /**
     * mail to
     */
    private String[] to;

    /**
     * mail cc to
     */
    private String[] cc;

    /**
     * mail bcc to
     */
    private String[] bcc;

    /**
     * mail subject
     */
    private String subject;

    /**
     * mail body
     */
    private String text;

    /**
     * mail image
     */
    private Map<String, String> imageMap;

    /**
     * attachmentList support file path and Resource
     */
    private List<Object> attachmentList;

    /**
     * isExcpetion
     */
    private boolean fromExcpetion;

    /**
     * EmailEntity
     */
    public EmailEntity() {
    }

    /**
     * EmailEntity
     * 
     * @param to
     *            to
     * @param subject
     *            subject
     * @param text
     *            text
     */
    public EmailEntity(String to, String subject, String text) {
        this(to, null, subject, text);
    }

    /**
     * EmailEntity
     * 
     * @param to
     *            to
     * @param cc
     *            cc
     * @param subject
     *            subject
     * @param text
     *            text
     */
    public EmailEntity(String to, String cc, String subject, String text) {
        this(to, cc, null, subject, text);
    }

    /**
     * EmailEntity
     * 
     * @param to
     *            to
     * @param cc
     *            cc
     * @param bcc
     *            bcc
     * @param subject
     *            subject
     * @param text
     *            text
     */
    public EmailEntity(String to, String cc, String bcc, String subject, String text) {
        this(to != null ? new String[] { to } : null, cc != null ? new String[] { cc } : null,
            bcc != null ? new String[] { bcc } : null, subject, text);
    }

    public EmailEntity(String[] to, String[] cc, String[] bcc, String subject, String text) {
        // this.from = from;
        this.subject = subject;
        this.text = text;
        if (to != null) {
            this.to = Arrays.copyOf(to, to.length);
        }
        if (cc != null) {
            this.cc = Arrays.copyOf(cc, cc.length);
        }
        if (bcc != null) {
            this.bcc = Arrays.copyOf(bcc, bcc.length);
        }
    }

    // public String getFrom() {
    // return from;
    // }
    //
    // public void setFrom(String from) {
    // this.from = from;
    // }

    /**
     * getTo
     */
    public final String[] getTo() {
        if (to != null) {
            String[] too = Arrays.copyOf(to, to.length);
            return too;
        } else {
            return null;
        }

    }

    /**
     * setTo
     * 
     * @param to
     *            to
     */
    public final void setTo(String[] to) {
        if (to != null) {
            this.to = Arrays.copyOf(to, to.length);
        }
    }

    /**
     * setTo
     * 
     * @param to
     *            to
     */
    public final void setTo(String to) {
        this.to = new String[] { to };
    }

    /**
     * getCc
     * 
     * @return String[]
     */
    public final String[] getCc() {
        if (cc != null) {
            String[] ccc = Arrays.copyOf(cc, cc.length);
            return ccc;
        } else {
            return null;
        }
    }

    /**
     * setCc
     * 
     * @param cc
     *            cc
     */
    public final void setCc(String[] cc) {
        if (cc != null) {
            this.cc = Arrays.copyOf(cc, cc.length);
        }
    }

    /**
     * getBcc
     * 
     * @return String[]
     */
    public final String[] getBcc() {
        if (bcc != null) {
            String[] bccc = Arrays.copyOf(bcc, bcc.length);
            return bccc;
        } else {
            return null;
        }
    }

    /**
     * setBcc
     * 
     * @param bcc
     *            bcc
     */
    public final void setBcc(String[] bcc) {
        if (bcc != null) {
            this.bcc = Arrays.copyOf(bcc, bcc.length);
        }
    }

    /**
     * setCc
     * 
     * @param cc
     *            cc
     */
    public final void setCc(String cc) {
        this.cc = new String[] { cc };
    }

    /**
     * getSubject
     * 
     * @return String
     */
    public final String getSubject() {
        return subject;
    }

    /**
     * setSubject
     * 
     * @param subject
     *            subject
     */
    public final void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * getText
     * 
     * @return String
     */
    public final String getText() {
        return text;
    }

    /**
     * setText
     * 
     * @param text
     *            text
     */
    public final void setText(String text) {
        this.text = text;
    }

    /**
     * getImageMap
     * 
     * @return Map<String, String>
     */
    public final Map<String, String> getImageMap() {
        return imageMap;
    }

    /**
     * setImageMap
     * 
     * @param imageMap
     *            imageMap
     */
    public final void setImageMap(Map<String, String> imageMap) {
        this.imageMap = imageMap;
    }

    /**
     * getAttachmentList
     * 
     * @return List<Object>
     */
    public final List<Object> getAttachmentList() {
        return attachmentList;
    }

    /**
     * setAttachmentList
     * 
     * @param attachmentList
     *            attachmentList
     */
    public final void setAttachmentList(List<Object> attachmentList) {
        this.attachmentList = attachmentList;
    }

    /**
     * isFromExcpetion
     * 
     * @return boolean
     */
    public final boolean isFromExcpetion() {
        return fromExcpetion;
    }

    /**
     * setFromExcpetion
     * 
     * @param fromExcpetion
     *            fromExcpetion
     */
    public final void setFromExcpetion(boolean fromExcpetion) {
        this.fromExcpetion = fromExcpetion;
    }

}
