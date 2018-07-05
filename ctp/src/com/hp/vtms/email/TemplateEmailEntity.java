package com.hp.vtms.email;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TemplateEmailEntity
 * 
 * @author yue
 */
public class TemplateEmailEntity extends EmailEntity {

    /**
     * 
     */
    private Logger logger = LoggerFactory.getLogger(TemplateEmailEntity.class);

    /**
     * 
     */
    private String templatePath;

    /**
     * 
     */
    private Map<String, Object> valueMap = new HashMap<String, Object>();

    /**
     * TemplateEmailEntity
     */
    public TemplateEmailEntity() {
    }

    /**
     * TemplateEmailEntity
     * 
     * @param templatePath
     *            templatePath
     * @param valueMap
     *            valueMap
     * @param to
     *            to
     * @param subject
     *            subject
     */
    public TemplateEmailEntity(String templatePath, Map<String, Object> valueMap, String to, String subject) {
        super(to, subject, null);
        this.templatePath = templatePath;
        this.valueMap = valueMap;

        // setText
        processTemplateText();

    }

    /**
     * TemplateEmailEntity
     * 
     * @param templatePath
     *            templatePath
     * @param valueMap
     *            valueMap
     * @param to
     *            to
     * @param cc
     *            cc
     * @param subject
     *            subject
     */
    public TemplateEmailEntity(String templatePath, Map<String, Object> valueMap, String to, String cc, String subject) {
        super(to, cc, subject, null);
        this.templatePath = templatePath;
        this.valueMap = valueMap;

        // setText
        processTemplateText();
    }

    /**
     * TemplateEmailEntity
     * 
     * @param templatePath
     *            templatePath
     * @param valueMap
     *            valueMap
     * @param to
     *            to
     * @param cc
     *            cc
     * @param bcc
     *            bcc
     * @param subject
     *            subject
     */
    public TemplateEmailEntity(String templatePath, Map<String, Object> valueMap, String to, String cc, String bcc,
        String subject) {
        super(to, cc, bcc, subject, null);
        this.templatePath = templatePath;
        this.valueMap = valueMap;

        // setText
        processTemplateText();
    }

    /**
     * TemplateEmailEntity
     * 
     * @param templatePath
     *            templatePath
     * @param valueMap
     *            valueMap
     * @param to
     *            to
     * @param cc
     *            cc
     * @param bcc
     *            bcc
     * @param subject
     *            subject
     */
    public TemplateEmailEntity(String templatePath, Map<String, Object> valueMap, String[] to, String[] cc,
        String[] bcc, String subject) {
        super(to, cc, bcc, subject, null);
        this.templatePath = templatePath;
        this.valueMap = valueMap;

        // setText
        processTemplateText();
    }

    /**
     * getMessage
     * 
     * @return String
     */
    public final String getTemplateText() {
        String templateMessage = "";

        if (this.templatePath != null && !this.templatePath.equals("")) {
            templateMessage = VelocityUtils.generateMessage(valueMap, templatePath);
        }

        return templateMessage;
    }

    /**
     * setMessage
     */
    public final void setTemplateText() {

        if (this.templatePath != null && !this.templatePath.equals("")) {
            String templateMessage = VelocityUtils.generateMessage(valueMap, templatePath);

            logger.debug("the content of email is:\n" + templateMessage);

            super.setText(templateMessage);
        }

    }

    /**
     * processTemplateText
     */
    private void processTemplateText() {
        if (this.templatePath != null && !this.templatePath.equals("")) {
            String templateMessage = VelocityUtils.generateMessage(valueMap, templatePath);

            logger.debug("the content of email is:\n" + templateMessage);

            super.setText(templateMessage);
        }
    }

    /**
     * getTemplatePath
     * 
     * @return String
     */
    public final String getTemplatePath() {
        return templatePath;
    }

    /**
     * setTemplatePath
     * 
     * @param templatePath
     *            templatePath
     */
    public final void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * getValueMap
     * 
     * @return Map<String, Object>
     */
    public final Map<String, Object> getValueMap() {
        return valueMap;
    }

    /**
     * setValueMap
     * 
     * @param valueMap
     *            valueMap
     */
    public final void setValueMap(Map<String, Object> valueMap) {
        this.valueMap = valueMap;
    }
}
