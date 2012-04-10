package laba08;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 *
 * @author 3D-GRAF
 */
public class Account {
    private int id;
    private String name;
    private String password;
    private Float money;
    
    public Account(int id, String name, String password, Float money) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.money = money;
    }
  
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPassword() {
        return this.password;
    }
        
    public Float getMoney() {
        return this.money;
    }
    
    public void setMoney(Float money) {
        this.money = money;
    }
    
    public static String cryptPassword(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return sha1(source);
    }
    
    private static String md5(String source) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        md.update(source.getBytes(), 0, source.length());
        String output = new BigInteger(1, md.digest()).toString(16);

        return output;
    }
    
    private static String sha1(String source) throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        md.update(source.getBytes(), 0, source.length());
        String output = new BigInteger(1, md.digest()).toString(16);
        
        return output;
    } 
}
