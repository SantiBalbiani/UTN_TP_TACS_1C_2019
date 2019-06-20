package findYourPlace.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

 
public class Encrypt {

 
    public static String encrypt(String str) {
    	
    	byte[] salt = "tp".getBytes(StandardCharsets.UTF_8);
    
        MessageDigest md;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
            
            md.update(salt);

            byte[] hashedPassword = md.digest(str.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword)
                sb.append(String.format("%02x", b));

            return(sb.toString());
            
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        
		return null;
    
    	
    }

    public static Boolean checkPsw(String givenPass, String encryptedExpectedPass)
    {
    	return encrypt(givenPass).equals(encryptedExpectedPass);
    }
    
}