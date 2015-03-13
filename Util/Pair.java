package Util;

import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author amd
 */
public class Pair implements Serializable{
    private byte [] signature;
    private String message;

    public Pair(){
        
    }
    public Pair(byte [] sign , String messege){
        this.signature = new byte[sign.length];
        for(int i = 0 ; i < sign.length; i++)
            this.signature[i]=sign[i];
        this.message =messege;
    }
    /**
     * @return the signature
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * @param signature the signature to set
     */
    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
