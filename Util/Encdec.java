package Util;


import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * Created by amd on 3/10/15.
 */
public class Encdec {
    private SecretKey key ;
    private Cipher cipher;

    public Encdec(String k) throws NoSuchPaddingException, NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, InvalidKeySpecException {
        KeySpec keySpec = new DESedeKeySpec(k.getBytes("UTF8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        this.key = keyFactory.generateSecret(keySpec);
        this.cipher = Cipher.getInstance("DESede");

    }

    @SuppressWarnings("all")
    public String encrypt(String messege) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {

        cipher.init(Cipher.ENCRYPT_MODE ,key);
        byte[] plainText = messege.getBytes("UTF8");
        byte[] cipherText = cipher.doFinal(plainText);
        return Base64.getEncoder().encodeToString(cipherText);
    }

    @SuppressWarnings("all")
    public String decrypt(String cipherText) throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, IOException {
        cipher.init(Cipher.DECRYPT_MODE, this.key);
        byte[] cip = Base64.getDecoder().decode(cipherText);
        byte[] plain = cipher.doFinal(cip);
        return new String(plain, "UTF8");
    }


}
