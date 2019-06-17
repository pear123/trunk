package com.arvato.cc.util;

import org.apache.commons.codec.binary.StringUtils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码工具类。
 */
public class PasswordUtils {
    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    public static String encrypt(String rawPass) {
        String eccryptedPass = "";
        try {
            byte[] bArray = DigestUtils.md5(rawPass.getBytes("UTF-16LE"));
            StringBuffer sb = new StringBuffer(bArray.length);
            String result;
            for (int i = 0; i < bArray.length; i++) {
                result = Integer.toHexString(0xFF & bArray[i]);
                if (result.length() == 1) {
                    result = "0" + result;
                }

                sb.append(result.toUpperCase());
            }
            eccryptedPass = sb.toString();
        } catch (UnsupportedEncodingException e) {
             logger.error(e.getMessage(), e);
        }

        return eccryptedPass;
    }
}
