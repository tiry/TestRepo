package org.nuxeo.ecm.eval.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.common.utils.Base64;

/**
 * Simple implementation of the {@link MessageService}.
 * 
 * This implementation uses a simple encryption system so that reading the
 * message without reading the code is not that simple :)
 * 
 * @author Tiry (tdelprat@nuxeo.com)
 * 
 */
public class MessageServiceImpl implements MessageService {

    protected static final Log log = LogFactory.getLog(MessageServiceImpl.class);

    protected Map<String, String> messages = new HashMap<String, String>();

    protected static MessageServiceImpl instance = null;

    static MessageService instance() {
        if (instance == null) {

            instance = new MessageServiceImpl();

            try {
                instance.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private void load() throws IOException {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(
                "messages-eval.properties");
        ResourceBundle rb = new PropertyResourceBundle(is);
        Enumeration<String> keys = rb.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            String message = rb.getString(key);
            messages.put(KeyHelper.decryptKey(key), message);
        }
    }

    @Override
    public String getMessage(String key, String secret) {
        String message = getMessage(key);
        if (secret == null || message == null) {
            return message;
        }

        try {
            message = decypher(secret, message);
            return message;
        } catch (Exception e) {
            String errMessage = "Error while decrypting message, ";
            errMessage += "\n secret=" + secret + "*";
            errMessage += "\n message=" + message + "*";
            log.error(errMessage, e);
            return null;
        }
    }

    public void addMessage(String key, String secret, String message)
            throws Exception {
        messages.put(key, cypher(secret, message));
    }

    public String getMessage(String key) {
        return messages.get(key);
    }

    protected SecretKeySpec buildKey(String key) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.toString(), e);
        }
        byte[] bytes = key.getBytes();
        digest.update(bytes, 0, bytes.length);

        byte[] bkey = new byte[16];

        try {
            digest.digest(bkey, 0, 16);
        } catch (DigestException e) {
            throw new RuntimeException(e.toString(), e);
        }

        return new SecretKeySpec(bkey, "AES");
    }

    @Override
    public String cypher(String key, String input) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
        SecretKeySpec aesKey = buildKey(key);

        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        byte[] cipherText = cipher.doFinal(input.getBytes());

        return Base64.encodeBytes(cipherText);
    }

    @Override
    public String decypher(String key, String input) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
        SecretKeySpec aesKey = buildKey(key);
        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        byte[] encodedBytes = Base64.decode(input);

        byte[] decryptedBytes = cipher.doFinal(encodedBytes);

        return new String(decryptedBytes);
    }

    public String dump() {
        StringBuffer sb = new StringBuffer();

        for (String key : messages.keySet()) {
            sb.append(KeyHelper.encryptKey(key));
            sb.append(" = ");
            sb.append(messages.get(key).replace("\n", "").replace("\r", ""));
            sb.append("\n");
        }

        return sb.toString();
    }
}
