package com.budgetwise.budgetwise.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * PasswordUtil component.
 */
public class PasswordUtil {
    /**
     * hashPassword operation.
     * @param password parameter value
     * @return result value
     */
    public static String hashPassword(String password) {
        int logRounds = 12;

        String salt = BCrypt.gensalt(logRounds);

        return BCrypt.hashpw(password, salt);
    }

    /**
     * verify operation.
     * @param password parameter value
     * @param hashedPassword parameter value
     * @return result value
     */
    public static boolean verify(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
