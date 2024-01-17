package com.app.e_business_easytask.security;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    // Diese Methode hasht das Passwort
    public static String hashPassword(String password) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        return hashedPassword;
    }

    // Diese Methode überprüft das eingegebene Passwort mit dem gehashten Passwort
    public static boolean checkPassword(String password, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
        return result.verified;
    }
}