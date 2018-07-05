package com.hp.vtms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.map.ObjectMapper;

public class CommonUtils {

    public static <T> T jsonToJava(String json, Class<T> objClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        T result = null;
        try {
            result = objectMapper.readValue(json, objClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Map<String, String> getProperty(String path) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        InputStream ins = CommonUtils.class.getClassLoader().getResourceAsStream(path);
       try{
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] namepro = line.split("=", 2);
            if (namepro.length == 2) {
                map.put(namepro[0], namepro[1]);
            } else {
                map.put(namepro[0], "");
            }
        }
        }finally{
        if(ins!=null){
        	ins.close();
        }
        }
        return map;
    }

    public static String getUTCTime() {
        Calendar gc = GregorianCalendar.getInstance();
        String utc = DateFormatUtils.formatUTC(gc.getTime(), "MM/dd/yyyy HH:mm:ss", Locale.US);
        return utc;
    }

    public static Long getUTCTIimeInMillisecond() {
        Long time = null;
        Calendar gc = GregorianCalendar.getInstance();
        String format = "MM/dd/yyyy HH:mm:ss";
        String utc = DateFormatUtils.formatUTC(gc.getTime(), format, Locale.US);
        Date utcTime = parseDate(utc, format);
        if (utcTime != null) {
            time = utcTime.getTime();
        }
        return time;
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        // SimpleDateFormat simpleFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return simpleFormat.format(date);
    }

    
    public static Date parseDate(String date, String format) {
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        Date date1 = null;
        try {
            date1 = simpleFormat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date1;
    }
    
    public static String uppcaseFirstLetter(String str) {
        if (str == null || "".equals(str.trim()))
            return null;
        String regex = "[^a-zA-Z]*([a-zA-Z]).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String modifyLetter = matcher.group(1);
            int modifyIndex = matcher.start(1);
            str = str.replaceFirst(modifyLetter, modifyLetter.toUpperCase());
        }

        return str;
    }

    public static void main(String arg[]) throws IOException {
        // System.out.println(getProperty("emailconf.properties"));
        // System.out.println(uppcaseFirstLetter(" feafe979"));
        // System.out.println(parseDate("2015-03-23 09:00:00.0000000",
        // "yyyy-MM-dd HH:mm:ss.S").toLocaleString());
        //
        // System.out.println(new Date(1404176400000L));

        System.out.println(getUTCTime());
        System.out.println(new Date(getUTCTIimeInMillisecond()).toLocaleString());
    }

}
