/*
 *Simple password encode and decode text plus dummy salt, not meant for real world
 *applicatoin.
 * Code referenced from 
 * http://www.java2s.com/Code/Java/Security/EncryptsthestringalongwithsaltDecryptsthestringandremovesthesalt.htm
 */
package services.implementation;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import services.interfaces.SecurityService;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Ya
 */
@Primary
@Service
public class SecurityServiceImpl implements SecurityService{
    private static Random random = new Random((new Date()).getTime());
      
      //Encrypts plain text and adding dummy salt
    @Override
    public String encrypt(String plainText){
       BASE64Encoder encoder = new BASE64Encoder();         
       try{
        // adding dummy salt
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return encoder.encode(salt) + encoder.encode(plainText.getBytes());
       }
        catch(NullPointerException | IllegalArgumentException e){        
       }    
        return "n/a"; //if exception occurs, will not return null     
    }
    
    /*Removes dummy salt and decrypts text, won't be possible if using MD5 hashing
    * If using MD5 hasing, the salt used in encryption must be stored with the cipher text, so it can be
    * retrieved later to re-encrypt plain text for hash comparison, as different salt would generate different
    * hash.
    */
    @Override
    public String decrypt(String encryptString){    
      //bypass salt knowing the length of the encyrpted version
    if (encryptString.length() > 12) {
       String cipher = encryptString.substring(12);
       BASE64Decoder decoder = new BASE64Decoder();
      try {
       return new String (decoder.decodeBuffer(cipher));
      } catch (IOException| IllegalArgumentException e) {
        }
    }         
      return "n/a"; //if exception occurs, will not return null
  }    
}
