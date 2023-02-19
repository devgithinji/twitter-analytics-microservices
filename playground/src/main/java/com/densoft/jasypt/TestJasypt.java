package com.densoft.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.iv.RandomIvGenerator;

/**
 * Hello world!
 */
public class TestJasypt {
    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setPassword("secret1234!");
        standardPBEStringEncryptor.setAlgorithm("PBEWithHMACSHA512AndAES_256");
        standardPBEStringEncryptor.setIvGenerator(new RandomIvGenerator());
        String result = standardPBEStringEncryptor.encrypt("spring_cloud_pwd");
        System.out.println(result);
        System.out.println(standardPBEStringEncryptor.decrypt(result));
    }
}
