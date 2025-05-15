package springBoot.study.blog.util;

import javax.crypto.Cipher;
import java.security.*;

public class PasswordGeneratorEncoder {
    public static void main(String[] args) throws Exception {
        RsaGenrator passwordEncoder = new RsaGenrator();
        byte[] encrypted = passwordEncoder.encrypt("moaaz");
        System.out.println(new String(encrypted));
        String decrypted = passwordEncoder.decrypt(encrypted);
        System.out.println(decrypted);
    }

    public static class RsaGenrator {

        public RsaGenrator() throws Exception {
        }

        public static KeyPair generateRsaKeyPair() throws Exception {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            // define key size
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        }

        public static byte[] encrypt(String password) throws Exception {
            PublicKey publicKey = generateRsaKeyPair().getPublic();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(password.getBytes());

        }

        public static String decrypt(byte[] ciphertext) throws Exception {
            PrivateKey privateKey = generateRsaKeyPair().getPrivate();
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(ciphertext));
        }
    }
}
