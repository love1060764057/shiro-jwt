package com.tongchenggo.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
/*
* 密码加密类
* @author jiang
* @date 2021.6.11
* */
public class MD5Utils {
    public static String encryptionPassword(String password,String salt){
        return new SimpleHash("MD5",
                password, ByteSource.Util.bytes(salt),
                10).toString();
    }
}
