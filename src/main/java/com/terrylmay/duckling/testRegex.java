package com.terrylmay.duckling;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testRegex {

	public static void main(String[] args) {
		
		String sentence = "un million cent un";
		System.out.println(sentence);
		
		Pattern p = Pattern.compile("[undeuxtroisquatrecinqsixsepthuitneuf123456789]million[undeuxtroisquatrecinqsixsepthuitneuf123456789](?!(mille|cent|dix))");
        Matcher m = p.matcher(sentence);
        boolean result = m.find();
        while (result) {
            String group = m.group();
            System.out.println(group);
            result = m.find();
        }
		
	}
	
}
