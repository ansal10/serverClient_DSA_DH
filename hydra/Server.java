package hydra;
import Util.Encdec;
import Util.Verification;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Date;
public class Server {
    
    
    public static void main(String []args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        int SERVER_PORT=-1;
        if(args.length!=1){
            System.out.println("Error! Usage is java class port");
            System.exit(1);
        }
        else
            SERVER_PORT=Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
        while(true){
            Socket clientSocket = serverSocket.accept();
            ObjectOutputStream outObj = new ObjectOutputStream( clientSocket.getOutputStream() );
            ObjectInputStream inObj = new ObjectInputStream( clientSocket.getInputStream() );
            Verification v = new Verification();
            if(v.isVerified(outObj, inObj)){
                System.out.println("Client was Verified by Server");
                if(v.sendVerification(outObj, inObj, "hydra")){
                    System.out.println("Successfully 2 way connection made");

                    BigInteger p = (BigInteger) inObj.readObject();
                    BigInteger g = (BigInteger) inObj.readObject();
                    BigInteger keyA = (BigInteger) inObj.readObject();
                    BigInteger keyB = g.modPow(BigInteger.probablePrime(16, new SecureRandom()), p);
                    outObj.writeObject(keyB);
                    String sharedSecretKey = keyA.modPow(keyB,p).toString();
                    Encdec e = new Encdec(sharedSecretKey);
                    String str = (String)inObj.readObject();
                    while(!e.decrypt(str).equals("quit")){
                        System.out.println("Actual Messege : "+e.decrypt(str));
                        str = (String)inObj.readObject();
                    }
                }
            }else{
                System.out.println("Client is Not Verified");
            }
        }
    }
}

