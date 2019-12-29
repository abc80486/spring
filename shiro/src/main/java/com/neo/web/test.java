package com.neo.web;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class test {
    public static void main(String[] args) {
        
            Object result = new SimpleHash("Sha256","123456","admin8d78869f470951332959580424d4bf4f",2);
            System.out.println(":"+result);

            String str = "hello";  
            String salt = "123";  
            String sha1 = new Sha256Hash(str, salt,2).toString();   
            System.out.println(sha1);

        
    }
}
