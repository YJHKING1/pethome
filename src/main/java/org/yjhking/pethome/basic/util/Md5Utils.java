package org.yjhking.pethome.basic.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 密码加密工具
 *
 * @author YJH
 */
public class Md5Utils {
    
    /**
     * MD5加密
     *
     * @param context 要加密的字符串
     * @return 加密后的字符串
     */
    public static String encrypByMd5(String context) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // update处理
            md.update(context.getBytes());
            // 调用该方法完成计算
            byte[] encryContext = md.digest();
            int i;
            StringBuilder stringBuilder = new StringBuilder("");
            // 做相应的转化（十六进制）
            for (byte b : encryContext) {
                i = b;
                if (i < 0) i += 256;
                if (i < 16) stringBuilder.append("0");
                stringBuilder.append(Integer.toHexString(i));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
