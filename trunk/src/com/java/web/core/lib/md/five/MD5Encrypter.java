/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.java.web.core.lib.md.five;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 *
 * @author Vuk
 */
public class MD5Encrypter {

    public static String encryptPassword(String password) throws Exception{
        String encryptedPassword = "";
        MessageDigest mdEnc = MessageDigest.getInstance("MD5");
        mdEnc.update(password.getBytes(), 0, password.length());
        encryptedPassword = new BigInteger(1, mdEnc.digest()).toString(16);
        // 007de96adfa8b36dc2c8dd268d039129
        int cnt = 0;
        while (encryptedPassword.length() < 32) {
            encryptedPassword = "0" + encryptedPassword;
            cnt++;
            if (cnt>33) break;
        }
        return encryptedPassword;
    }
}
