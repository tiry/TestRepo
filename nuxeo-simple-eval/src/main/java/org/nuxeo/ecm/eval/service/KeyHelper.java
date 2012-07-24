package org.nuxeo.ecm.eval.service;

import org.nuxeo.common.utils.Base64;

public class KeyHelper {

    protected static byte[] translate(byte[] keyb) {
        byte[] xkey = KeyHelper.class.getCanonicalName().getBytes();
        byte[] rkey = new byte[keyb.length];
        for (int i = 0; i < keyb.length; i++) {
            rkey[i] = (byte) (keyb[i] ^ xkey[(i % xkey.length)]);
        }
        return rkey;
    }

    public static String encryptKey(String key) {
        return Base64.encodeBytes(translate(key.getBytes())).replace("=", "*");
    }

    public static String decryptKey(String key) {
        return new String(translate(Base64.decode(key.replace("*", "="))));
    }
}
