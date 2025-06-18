package com.bbek.BbekServiceA.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {

    private static String getPropertyValue(String propName) {
        Properties prop = new Properties();
        String returnValue = "";
        try {
            prop.load(new FileInputStream("C:\\BBEK\\CONF\\config.properties"));
            returnValue = prop.getProperty(propName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
    public static String getMinistryImagePath() {
        return getPropertyValue("system.ministry_filePath");
    }



//    public static String getApiUsername() {
//        return getPropertyValue("api.username");
//    }
//    public static String getApiPassword() {
//        return getPropertyValue("api.password");
//    }
//    public static String getDepositSlipFilepath() {
//        return getPropertyValue("system.deposit_slip__filepath");
//    }
//
//    public static String getUserSupportingDocsFilePath() {
//        return getPropertyValue( "system.user_supporting_docs__filepath");
//    }
//
//    public static String getUserSelfieFilepath() {
//        return getPropertyValue("system.user_selfie__filepath");
//    }
}