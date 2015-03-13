package Util;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;


public class temp {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidKeyException, NoSuchAlgorithmException, SignatureException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

//        Random rand = new SecureRandom();
//        BigInteger p = BigInteger.probablePrime(512,rand);
//        System.out.println(p);
//        BigInteger g = BigInteger.probablePrime(256,rand);
//        while (!g.gcd(p).equals(BigInteger.ONE)){
//         g = g.nextProbablePrime();
//        }
//
//        BigInteger keyA = BigInteger.probablePrime(16,new SecureRandom());
//        BigInteger keyB = BigInteger.probablePrime(16,new SecureRandom());
//
//        keyA = g.modPow(keyA,p);
//        keyB = g.modPow(keyB,p);
//
//        System.out.println(keyA);
//        System.out.println(keyB);
//
//        System.out.println(keyA.modPow(keyB,p));

    Encdec enc = new Encdec("4350411538063763360898231216991657553038915618003351872727890924164497825783245607100056526656717683597485113847370252284237188296425967064752339382447318");
        String cipher = enc.encrypt("This messege encode it");

        System.out.println(cipher);

        String messege = enc.decrypt(cipher);

        System.out.println(messege);






        
    }
}
