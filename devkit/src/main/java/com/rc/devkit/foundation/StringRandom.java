package com.rc.devkit.foundation;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class StringRandom
{
    //================================================================================
    // Private Variables
    //================================================================================
    private SecureRandom random = new SecureRandom();
    private Random normalRandom = new Random();
    private final int DEFAULT_WORD_SIZE_MAX = 20;

    //================================================================================
    // Private Methods
    //================================================================================
    private String generateStringForNumber()
    {
        return new BigInteger(130, random).toString(32);
    }

    //================================================================================
    // Public Methods
    //================================================================================
    public String nextWord()
    {
        int wordSize = Math.abs(normalRandom.nextInt() % DEFAULT_WORD_SIZE_MAX);
        return nextWordWithLength(wordSize);
    }

    public String nextWordWithLength(int numberOfCharacters)
    {
        String string = generateStringForNumber();
        return string.substring(0, Math.min(numberOfCharacters, string.length() - 1));
    }
}
