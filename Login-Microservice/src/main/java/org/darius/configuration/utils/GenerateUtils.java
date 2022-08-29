package org.darius.configuration.utils;

import java.util.Random;

public class GenerateUtils {
    private static final Random random = new Random();
    public static String generateRandomString(int numberOfChars){
        String alphabet = "abcdefghijklmnopqrtuvyz";
        StringBuilder resultingString = new StringBuilder();
        resultingString.append(Character.toUpperCase(alphabet.charAt(random.nextInt(alphabet.length()))));
        for (int i = 0; i < numberOfChars; i++) {
            resultingString.append(Character.toLowerCase(alphabet.charAt(random.nextInt(alphabet.length()))));
        }
        return resultingString.toString();
    }

}
