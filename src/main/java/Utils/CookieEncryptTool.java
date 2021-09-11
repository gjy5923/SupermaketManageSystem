package Utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;


public  class CookieEncryptTool {
    public CookieEncryptTool(){}
    /**
     * Base64加密
     *
     * @param cleartext
     * @return
     */
    public static String encodeBase64(String cleartext) {

        try {
            cleartext = new String(Base64.getEncoder().encode(cleartext.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cleartext;
    }

    /**
     * Base64解密
     *
     * @param ciphertext
     * @return
     */
    public static String decodeBase64(String ciphertext) {
        try {
            ciphertext = new String(Base64.getDecoder().decode(ciphertext.getBytes()),
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ciphertext;
    }

}
