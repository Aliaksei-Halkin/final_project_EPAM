package com.epam.flyingdutchman.controller.commands.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    private static final int POSITIVE_SIGN = 1;
    private static final int HEX_RADIX = 16;
    private static final String SHA_1_ALGORITHM = "SHA-1";

    public PasswordEncryptor() {
    }

    public static String encryptPassword(String originalPassword) {
        Logger logger = LogManager.getLogger();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA_1_ALGORITHM);
            messageDigest.update(originalPassword.getBytes(StandardCharsets.UTF_8));
            byte[] encruptedPassword = messageDigest.digest();
            BigInteger passwordEncryptedNumber = new BigInteger(POSITIVE_SIGN, encruptedPassword);
            return passwordEncryptedNumber.toString(HEX_RADIX);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error encrypting the password", e);
        }
        return null;
    }
}
