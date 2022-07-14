package org.yjhking.pethome.basic.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 生成随机字符串工具类
 *
 * @author YJH
 */
public class StrUtils {
    /**
     * 把逗号分隔的字符串转换字符串数组
     *
     * @param str   字符串
     * @param split 分割符
     * @return 字符串数组
     */
    public static String[] splitStr2StrArr(String str, String split) {
        if (str != null && !str.equals("")) {
            return str.split(split);
        }
        return null;
    }
    
    
    /**
     * 把逗号分隔字符串转换List的Long
     *
     * @param str 字符串
     * @return List<Long>
     */
    public static List<Long> splitStr2LongArr(String str) {
        String[] strings = splitStr2StrArr(str, ",");
        if (strings == null) return null;
        
        List<Long> result = new ArrayList<>();
        for (String string : strings) {
            result.add(Long.parseLong(string));
        }
        
        return result;
    }
    
    /**
     * 把逗号分隔字符串转换List的Long
     *
     * @param str   字符串
     * @param split 分割符
     * @return List<Long>
     */
    public static List<Long> splitStr2LongArr(String str, String split) {
        String[] strings = splitStr2StrArr(str, split);
        if (strings == null) return null;
        
        List<Long> result = new ArrayList<>();
        for (String string : strings) {
            result.add(Long.parseLong(string));
        }
        
        return result;
    }
    
    /**
     * 生成随机数字字符串
     *
     * @param length 字符串长度
     * @return 随机数字字符串
     */
    public static String getRandomString(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
        
    }
    
    /**
     * 生成随机字母、数字字符串
     *
     * @param length 字符串长度
     * @return 随机字母、数字字符串
     */
    public static String getComplexRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    
    /**
     * 将properties转换为HTML
     *
     * @param properties properties
     * @return HTML
     */
    public static String convertPropertiesToHtml(String properties) {
        //1:容量:6:32GB_4:样式:12:塑料壳
        StringBuilder sBuilder = new StringBuilder();
        String[] propArr = properties.split("_");
        for (String props : propArr) {
            String[] valueArr = props.split(":");
            sBuilder.append(valueArr[1]).append(":").append(valueArr[3]).append("<br>");
        }
        return sBuilder.toString();
    }
    
}
