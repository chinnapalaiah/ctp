package com.hp.vtms.email;

import java.util.Map;
import java.util.Properties;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * VelocityUtils
 * 
 * @author yue
 */
public final class VelocityUtils {

    /**
     * 
     */
    private static Logger logger = LoggerFactory.getLogger(VelocityUtils.class);

    /**
     * 
     */
    private static VelocityEngine velocityEngine = null;

    /**
     * VelocityUtils
     */
    private VelocityUtils() {
    }

    static {
        if (velocityEngine == null) {
            velocityEngine = new VelocityEngine();
            Properties p = new Properties();
            // p.setProperty("input.encoding", "UTF-8");
            // p.setProperty("output.encoding", "UTF-8");
            p.setProperty("resource.loader", "class");
            p.setProperty("class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            // force velocity to write log to tomcat folder..
            p.put("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
            p.put("runtime.log.logsystem.log4j.category", "velocity");
            p.put("runtime.log.logsystem.log4j.logger", "velocity");
            try {
                velocityEngine.init(p);
            } catch (Exception e) {
                logger.error("exceptions happened when init velocityEngine", e);
            }

        }
    }

    /**
     * generateMessage
     * 
     * @param model
     *            model
     * @param templateLocation
     *            templateLocation
     * @return String
     */
    public static String generateMessage(Map<String, Object> model, String templateLocation) {
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", model);
    }

}
