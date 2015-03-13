package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Verify {
  
    private String keyFileName;
    private Pair pair;
    
    public Verify(String publicKeyFile, Pair pair){
        this.keyFileName = publicKeyFile;
        this.pair = pair;
        
    }
    
    public boolean isVerified() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
       
            ObjectInputStream key = new ObjectInputStream(new FileInputStream(new File(keyFileName)));
            PublicKey publicKey = (PublicKey) key.readObject();
            key.close();
            
            Signature signature = Signature.getInstance("DSA");
            signature.initVerify(publicKey);
            
            signature.update(pair.getMessage().getBytes());
            if(signature.verify(pair.getSignature()))
                return true;
            else
                return false;       
    }
}
