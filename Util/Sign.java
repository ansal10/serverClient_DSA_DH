package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Sign {

    private String keyFileName;
    private String message;
    
    public Sign(String privateKeyfile , String message){
        this.keyFileName = privateKeyfile;
        this.message = message;
    }
    
    public Pair sign() throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException{
        
        ObjectInputStream key = new ObjectInputStream(new FileInputStream(new File(keyFileName)));
        PrivateKey privateKey = (PrivateKey) key.readObject();
        key.close();
        
        Signature signature = Signature.getInstance("DSA");
        signature.initSign(privateKey);
        
        signature.update(message.getBytes());
        byte[] signit = signature.sign();
        
        Pair retVal = new Pair(signit, message);
        return retVal;
        
        
    }
}
