package org.csource.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class CommonUtil {
    public static boolean isEmpty(String str) {
        if (null == str || str.length() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        if (null != str && str.trim().length() > 0) {
            return true;
        }
        return false;
    }

    public static final String classpathFlag = "classpath:";
    public static InputStream loadFile(String filePath) throws Exception {
        if (filePath.startsWith(classpathFlag)) {
            return CommonUtil.class.getClassLoader().getResourceAsStream(filePath.replace(classpathFlag, ""));
        } else {
            return new FileInputStream(filePath);
        }
    }



}
