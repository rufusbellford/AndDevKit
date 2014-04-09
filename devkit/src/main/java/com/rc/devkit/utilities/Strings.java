package com.rc.devkit.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Strings
{
    public static boolean isEmpty(CharSequence string)
    {
        if (string == null || string.length() == 0) {
            return true;
        }

        return false;
    }

    public static String getStringFromInputStream(InputStream is)
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
    
    public static boolean areStringsNotEmpty(CharSequence ... args)
    {
    	for (CharSequence string : args) {
			if (isEmpty(string)) {
				return false;
			}
		}
    	
		return true;
    }
}
