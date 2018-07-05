package com.hp.vtms.email;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DecoratedEmailEntity
 * 
 * @author yue
 */
public class DecoratedEmailEntity extends TemplateEmailEntity {

    /**
     * 
     */
    private Logger logger = LoggerFactory.getLogger(DecoratedEmailEntity.class);

    /**
     * 
     */
    private static final String REPLACE = "_replace_";

    /**
     * 
     */
    private String decoratorTemplatePath;

    /**
     * 
     */
    private Map<String, Object> decoratorValueMap = new HashMap<String, Object>();

    /**
     * DecoratedEmailEntity
     */
    public DecoratedEmailEntity() {
    }

    /**
     * DecoratedEmailEntity
     * 
     * @param decoratorTemplatePath
     *            decoratorTemplatePath
     * @param decoratorValueMap
     *            decoratorValueMap
     * @param templatePath
     *            templatePath
     * @param valueMap
     *            valueMap
     * @param to
     *            to
     * @param subject
     *            subject
     */
    public DecoratedEmailEntity(String decoratorTemplatePath, Map<String, Object> decoratorValueMap,
        String templatePath, Map<String, Object> valueMap, String to, String subject) {
        super(templatePath, valueMap, to, subject);

        this.decoratorTemplatePath = decoratorTemplatePath;
        this.decoratorValueMap = decoratorValueMap;

        // setText
        processDecoratedText();
    }

    /**
     * DecoratedEmailEntity
     * 
     * @param decoratorTemplatePath
     *            decoratorTemplatePath
     * @param decoratorValueMap
     *            decoratorValueMap
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
    public DecoratedEmailEntity(String decoratorTemplatePath, Map<String, Object> decoratorValueMap,
        String templatePath, Map<String, Object> valueMap, String to, String cc, String subject) {
        super(templatePath, valueMap, to, cc, subject);

        this.decoratorTemplatePath = decoratorTemplatePath;
        this.decoratorValueMap = decoratorValueMap;

        // setText
        processDecoratedText();
    }

    /**
     * DecoratedEmailEntity
     * 
     * @param decoratorTemplatePath
     *            decoratorTemplatePath
     * @param decoratorValueMap
     *            decoratorValueMap
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
    public DecoratedEmailEntity(String decoratorTemplatePath, Map<String, Object> decoratorValueMap,
        String templatePath, Map<String, Object> valueMap, String to, String cc, String bcc, String subject) {
        super(templatePath, valueMap, to, cc, bcc, subject);

        this.decoratorTemplatePath = decoratorTemplatePath;
        this.decoratorValueMap = decoratorValueMap;

        // setText
        processDecoratedText();
    }

    /**
     * DecoratedEmailEntity
     * 
     * @param decoratorTemplatePath
     *            decoratorTemplatePath
     * @param decoratorValueMap
     *            decoratorValueMap
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
    public DecoratedEmailEntity(String decoratorTemplatePath, Map<String, Object> decoratorValueMap,
        String templatePath, Map<String, Object> valueMap, String[] to, String[] cc, String[] bcc, String subject) {
        super(templatePath, valueMap, to, cc, bcc, subject);

        this.decoratorTemplatePath = decoratorTemplatePath;
        this.decoratorValueMap = decoratorValueMap;

        // setText
        processDecoratedText();
    }

    /**
     * getDecoratedText
     * 
     * @return String
     */
    public final String getDecoratedText() {
        String decoratedMessage = "";

        if (this.decoratorTemplatePath != null && !this.decoratorTemplatePath.equals("")
            && this.getTemplatePath() != null && !this.getTemplatePath().equals("")) {

            String templateMessage = VelocityUtils.generateMessage(this.getValueMap(), this.getTemplatePath());
            if (templateMessage != null) {

                decoratedMessage = VelocityUtils.generateMessage(decoratorValueMap, decoratorTemplatePath);
                if (decoratedMessage != null) {
                    decoratedMessage = decoratedMessage.replace(REPLACE, templateMessage);
                }
            }
        }

        return decoratedMessage;

    }

    /**
     * setDecoratedText
     */
    public final void setDecoratedText() {
        if (this.decoratorTemplatePath != null && !this.decoratorTemplatePath.equals("")
            && this.getTemplatePath() != null && !this.getTemplatePath().equals("")) {
            String templateMessage = VelocityUtils.generateMessage(this.getValueMap(), this.getTemplatePath());
            if (templateMessage != null) {

                String decoratedMessage = VelocityUtils.generateMessage(decoratorValueMap, decoratorTemplatePath);
                if (decoratedMessage != null) {
                    decoratedMessage = decoratedMessage.replace(REPLACE, templateMessage);
                    logger.debug("the final content of email is:\n" + decoratedMessage);
                    super.setText(decoratedMessage);
                }
            }
        }
    }

    /**
     * processDecoratedText
     */
    private void processDecoratedText() {

        if (this.decoratorTemplatePath != null && !this.decoratorTemplatePath.equals("")) {

            String text = this.getText();
            if (text != null) {

                String decoratedMessage = VelocityUtils.generateMessage(decoratorValueMap, decoratorTemplatePath);
                if (decoratedMessage != null) {
                    decoratedMessage = decoratedMessage.replace(REPLACE, text);
                    logger.debug("the final content of email is:\n" + decoratedMessage);
                    super.setText(decoratedMessage);

                }
            }
        }
    }

    /**
     * getDecoratorTemplatePath
     * 
     * @return String
     */
    public final String getDecoratorTemplatePath() {
        return decoratorTemplatePath;
    }

    /**
     * setDecoratorTemplatePath
     * 
     * @param decoratorTemplatePath
     *            decoratorTemplatePath
     */
    public final void setDecoratorTemplatePath(String decoratorTemplatePath) {
        this.decoratorTemplatePath = decoratorTemplatePath;
    }

    /**
     * getDecoratorValueMap
     * 
     * @return Map<String, Object>
     */
    public final Map<String, Object> getDecoratorValueMap() {
        return decoratorValueMap;
    }

    /**
     * setDecoratorValueMap
     * 
     * @param decoratorValueMap
     *            decoratorValueMap
     */
    public final void setDecoratorValueMap(Map<String, Object> decoratorValueMap) {
        this.decoratorValueMap = decoratorValueMap;
    }

}
