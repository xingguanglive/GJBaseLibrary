package tv.guojiang.core.util;

import android.util.Base64;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * SSLUtils
 * <p>
 * <p>Created by ChenTao(chentao7v@gmail.com) on 2017/3/6 14:29
 */
public class SSLUtils {

    public static final String PASSWORD_PUBLIC_KEY =
        "public key";

    /**
     * 获取经过公钥、Base64加密后的字符串
     */
    public static String getEncryptPassword(String sourcePassword) {
        return encryptContentWithPublicKey(PASSWORD_PUBLIC_KEY, sourcePassword.getBytes());
    }

    private static String encryptContentWithPublicKey(String publicKeyStr, byte[] sourceBytes) {
        try {
            PublicKey publicKey = getPublicKey(publicKeyStr);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] cipherData = cipher.doFinal(sourceBytes);
            return Base64.encodeToString(cipherData, Base64.NO_WRAP).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static PublicKey getPublicKey(String publicKey)
        throws NoSuchAlgorithmException, InvalidKeySpecException {

        // 公钥加密算法采用RSA算法
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(
            new X509EncodedKeySpec(Base64.decode(publicKey, Base64.NO_WRAP)));
    }
}
