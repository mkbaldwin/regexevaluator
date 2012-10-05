package com.github.regexevaluator;


/**
 * Driver to start and configure the application
 * 
 * @author mkbaldwin
 * @since  May 14, 2011
 */
public class RegexEvaluatorDriver
{
    private static final String APP_NAME = "Regular Expression Evaluator";
    
    /**
     * Launch the application
     */
    public static void main(String args[])
    {
        //OS X specific property to set the title in the menu bar
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", APP_NAME);
        
        //Create the GUI
        new RegexMainPanel(APP_NAME);
    }
}
