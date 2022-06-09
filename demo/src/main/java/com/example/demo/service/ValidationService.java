package com.example.demo.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationService {

    private static Pattern pattern;
    private static Matcher matcher;

    public static void checkMarca(String marca) throws IllegalStateException {
        boolean valid;
        valid = 
            marca.equalsIgnoreCase("Seat")
            || marca.equalsIgnoreCase("Renault")
            || marca.equalsIgnoreCase("Citroen");

        if (!valid) throw new IllegalStateException("La marca debe ser Seat, Renault o Citroen");
    }

    public static void checkRegex(String regex, String string,
        String message) throws IllegalStateException {
    
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(string);
        
            if (!matcher.find()) throw new IllegalStateException(message);

    }

}
