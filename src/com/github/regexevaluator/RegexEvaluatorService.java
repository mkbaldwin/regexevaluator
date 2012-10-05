package com.github.regexevaluator;

import java.util.regex.Pattern;


/**
 * Simple static class to abstract out the regular expression processing work.
 * 
 * @author mkbaldwin
 * @since  May 14, 2011
 */
public final class RegexEvaluatorService
{
    /**
     * Private constructor to prevent the class from being instantiated.
     */
    private RegexEvaluatorService(){}
    
    
    /**
     * Split the string based on the provided regular expression.
     * 
     * @param source String - Source data
     * @param regex String - Regular Expression
     * @return Array Of Strings 
     */
    public static String[] split(final String source,
                                 final String regex)
    {
        Pattern p = Pattern.compile(regex);
        return p.split(source);
    }
    
    
    /**
     * Determine if the string matches the provided regular expression.
     * 
     * @param source String - Source data
     * @param regex String - Regular Expression
     * @return Boolean
     */
    public static boolean matches(final String source,
                                  final String regex)
    {
    Pattern p = Pattern.compile(regex);
    return p.matcher(source).matches();
    }
}
