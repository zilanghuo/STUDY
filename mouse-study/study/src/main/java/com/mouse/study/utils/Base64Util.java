package com.mouse.study.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by 00245337 on 2018/1/4.
 */
public class Base64Util {

    private Base64Util(){}

    static Base64 base64 = new Base64();

    public static Object encode(Object obj) throws EncoderException {
        return base64.encode(obj);
    }

    public static String encodeToString(byte[] pArray) {
        return base64.encodeToString(pArray);
    }

    public static String encodeAsString(byte[] pArray) {
        return base64.encodeAsString(pArray);
    }

    public static Object decode(Object obj) throws DecoderException {
        return base64.decode(obj);
    }

    public static byte[] decode(String pArray) {
        return base64.decode(pArray);
    }

    public byte[] decode(byte[] pArray) {
        return base64.decode(pArray);
    }

    public byte[] encode(byte[] pArray) {
        return base64.encode(pArray);
    }

    public byte[] encode(byte[] pArray, int offset, int length) {
        return base64.encode(pArray, offset, length);
    }

    public boolean isInAlphabet(byte[] arrayOctet, boolean allowWSPad) {
        return base64.isInAlphabet(arrayOctet, allowWSPad);
    }

    public boolean isInAlphabet(String basen) {
        return base64.isInAlphabet(basen);
    }

    public long getEncodedLength(byte[] pArray) {
        return base64.getEncodedLength(pArray);
    }
}
