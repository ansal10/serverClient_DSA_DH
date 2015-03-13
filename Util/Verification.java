/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;

/**
 *
 * @author amd
 */
public class Verification {
    public static final String SRC=".";
    
    public boolean sendVerification(ObjectOutputStream out, ObjectInputStream in , String userid) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException{
        
        Sign sign = new Sign(SRC+"/"+userid+"/"+userid+".prv", userid+","+new Date().getTime());
        Pair pair = sign.sign();

        out.writeObject(pair);
        
        String response = (String)in.readObject();

        return response.equals("Verified");
    }
    
    public boolean isVerified(ObjectOutputStream out, ObjectInputStream in) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Pair pair = (Pair)in.readObject();

        String messege = pair.getMessage();
        String time = messege.split(",")[1].trim();
        String userid = messege.split(",")[0].trim();
        long timeRecieved = 0;
        try {
             timeRecieved = Long.valueOf(time);
        }catch (Exception e){
            e.printStackTrace();
        }

        Verify verify = new Verify(SRC+"/"+userid+"/"+userid+".pub", pair);

        String response = "Not Verified ! Closing Connection";
        if (verify.isVerified() && timeConsistent(timeRecieved, new Date().getTime()))
            response = "Verified";
        out.writeObject(response);
        return response.equals("Verified");
    }
    public boolean timeConsistent(long prev , long now){
        if(now-prev > 60*1000)
            return false;
        else return true;
    }
}
