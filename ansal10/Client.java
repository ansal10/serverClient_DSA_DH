package ansal10;
import Util.Encdec;
import Util.Verification;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
public class Client {
    
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        String LOCAL_HOST=null;
        int SERVER_PORT = -1;
        String userid = null;
        if(args.length!=3){
            System.out.println("Error ! Usage is java class host port userid");
            System.exit(1);
        }else{
            LOCAL_HOST = args[0];
            SERVER_PORT = Integer.parseInt(args[1]);
            userid = args[2];
        }
        Socket socket=null;
        try {
            socket = new Socket(LOCAL_HOST, SERVER_PORT);
        }catch (ConnectException e){
            System.err.println("Server is not running on this port");
            System.exit(1);
        }
        ObjectOutputStream outObj = new ObjectOutputStream( socket.getOutputStream() );
        ObjectInputStream inObj = new ObjectInputStream( socket.getInputStream() );
        Verification v = new Verification();
        if(v.sendVerification(outObj, inObj, userid)){
            System.out.println("Client Was authenticated");
            if(v.isVerified(outObj, inObj)){
                System.out.println("Successfully 2 way Verification made");
                        Random rand = new SecureRandom();
                        BigInteger p = BigInteger.probablePrime(512,rand);
                        BigInteger g = BigInteger.probablePrime(256,rand);
                        while (!g.gcd(p).equals(BigInteger.ONE))
                            g = g.nextProbablePrime();

                        BigInteger keyA = g.modPow(BigInteger.probablePrime(16, new SecureRandom()), p);
                        outObj.writeObject(p);
                        outObj.writeObject(g);
                        outObj.writeObject(keyA);
                        BigInteger keyB = (BigInteger) inObj.readObject();
                        String sharedSecretKey = keyA.modPow(keyB,p).toString();
                        System.out.println("Shared Secret Key with Server is Generated ");
                    Scanner s = new Scanner(System.in);
                    Encdec e = new Encdec(sharedSecretKey);
                    while (true){
                        try {
                            System.out.print("Write Messege  (quit to Quit Connection) \t>  ");
                            String m = s.nextLine();
                            String str = e.encrypt(m);
                            System.out.println("Sending Encoded as : " + str);
                            outObj.writeObject(str);
                            if(m.equals("quit"))
                                break;
                        }catch (Exception ex){
                            outObj.writeObject("quit");
                            break;
                        }
                    }
            }
            else{
                System.out.println("Server was not verified");
            }
        }else{
            System.out.println("Error Verifying");
        }
    }
}
