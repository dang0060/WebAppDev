/*
 * Service for encrypting/decrypting password and email
 */
package services.interfaces;

/**
 *
 * @author Ya
 */
public interface SecurityService {

    //Encrypts plain text and adding salt
    public String encrypt(String plainText);
    
    //removes salt and decrypts text
    public String decrypt(String encryptKey);
}
