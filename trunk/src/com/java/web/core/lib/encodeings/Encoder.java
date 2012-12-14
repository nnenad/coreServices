/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.java.web.core.lib.encodeings;

import java.io.UnsupportedEncodingException;

/**
 *
 * @author Vuk
 */
public class Encoder {
    public static String encodeToUtf8(String source) throws UnsupportedEncodingException{
        byte[] arr = source.getBytes("Cp1252");
        String s = new String(arr, "UTF8");
        return s;
    }
    public static String encodeToLatin(String source) throws UnsupportedEncodingException{
        byte[] arr = source.getBytes("UTF8");
        String s = new String(arr, "Cp1252");
        return s;
    }
}
