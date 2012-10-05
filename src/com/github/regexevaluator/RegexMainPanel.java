package com.github.regexevaluator;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


/**
 * Implement the UI for the application.
 * 
 * @author mkbaldwin
 * @since  May 14, 2011
 */
public class RegexMainPanel extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    
    private SpringLayout mainLayout;
    
    private JTextField regexInput;
    private JLabel regexLabel;
    private JTextArea sourceInput;
    private JLabel sourceLabel;
    private JLabel outputLabel;
    private JTextArea outputField;
    
    private JPanel buttonBar;
    private JButton matchesButton;
    private JButton splitButton;
    
    
    /**
     * Initialize the window
     */
    public RegexMainPanel(final String appTitle)
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(appTitle);
        this.setPreferredSize(new Dimension(500,300));
        this.setResizable(false);
        
        Container contentPane = this.getContentPane();
        
        //Setup the layout manager
        mainLayout = new SpringLayout();
        this.setLayout(mainLayout);
        
        //Setup the regex input field and label
        regexLabel = new JLabel("Regular Expression:");
        contentPane.add(regexLabel);
        mainLayout.putConstraint(SpringLayout.WEST, regexLabel, 10, SpringLayout.WEST, contentPane);
        mainLayout.putConstraint(SpringLayout.NORTH, regexLabel, 14, SpringLayout.NORTH, contentPane);
        
        regexInput = new JTextField();
        regexInput.setPreferredSize(new Dimension(350, (int)regexInput.getPreferredSize().getHeight()));
        contentPane.add(regexInput);
        mainLayout.putConstraint(SpringLayout.WEST, regexInput, 5, SpringLayout.EAST, regexLabel);
        mainLayout.putConstraint(SpringLayout.NORTH, regexInput, 10, SpringLayout.NORTH, contentPane);
        
        //Setup the source input field and label
        sourceLabel = new JLabel("Source String:");
        contentPane.add(sourceLabel);
        mainLayout.putConstraint(SpringLayout.EAST, sourceLabel, 0, SpringLayout.EAST, regexLabel);
        mainLayout.putConstraint(SpringLayout.NORTH, sourceLabel, 10, SpringLayout.SOUTH, regexInput);
        
        sourceInput = new JTextArea();
        sourceInput.setLineWrap(true);
        JScrollPane scrollableInput = new JScrollPane(sourceInput, 
                                                      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollableInput.setPreferredSize(new Dimension(344,50));
        contentPane.add(scrollableInput);
        mainLayout.putConstraint(SpringLayout.WEST, scrollableInput, 3, SpringLayout.WEST, regexInput);
        mainLayout.putConstraint(SpringLayout.NORTH, scrollableInput, 10, SpringLayout.SOUTH, regexInput);
        
        //Setup the buttons
        buttonBar = new JPanel();
        buttonBar.setLayout(new FlowLayout());
        contentPane.add(buttonBar);
        
        mainLayout.putConstraint(SpringLayout.WEST, buttonBar, 10, SpringLayout.WEST, contentPane);
        mainLayout.putConstraint(SpringLayout.NORTH, buttonBar, 10, SpringLayout.SOUTH, scrollableInput);
        
        matchesButton = new JButton("Matches");
        matchesButton.addActionListener(new MatchesButtonListener());
        buttonBar.add(matchesButton);
        splitButton = new JButton("Split");
        splitButton.addActionListener(new SplitButtonListener());
        buttonBar.add(splitButton);
        
        //Setup the output area
        outputLabel = new JLabel("Output:");
        contentPane.add(outputLabel);
        mainLayout.putConstraint(SpringLayout.EAST, outputLabel, 0, SpringLayout.EAST, regexLabel);
        mainLayout.putConstraint(SpringLayout.NORTH, outputLabel, 10, SpringLayout.SOUTH, buttonBar);
        outputField = new JTextArea();
        outputField.setLineWrap(false);
        JScrollPane scrollableOutput = new JScrollPane(outputField,
                                                       JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                                       JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollableOutput.setPreferredSize(new Dimension(344,100));
        contentPane.add(scrollableOutput);
        mainLayout.putConstraint(SpringLayout.WEST, scrollableOutput, 3, SpringLayout.WEST, regexInput);
        mainLayout.putConstraint(SpringLayout.NORTH, scrollableOutput, 10, SpringLayout.SOUTH, buttonBar);
        
        this.pack();
        this.setVisible(true);
    }
    
    
    /**
     * Handle click events on the split button.
     */
    private class SplitButtonListener extends ButtonListenerBase implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            if(validateInputFields())
            {
                try
                {
                    String[] parts = RegexEvaluatorService.split(sourceInput.getText(), regexInput.getText());
                    
                    String outputText = "";
                    for(int i = 0; i < parts.length; i++)
                    {
                        outputText += String.format("[%s]: %s\n",
                                                    i,
                                                    parts[i]);
                    }
                    outputField.setText(outputText);
                }
                catch(Exception ex)
                {
                    outputField.setText("Error:\n" + ex.getMessage());
                }
            }
        }
    }
    
    
    /**
     * Handle click events on the matches button.
     */
    private class MatchesButtonListener extends ButtonListenerBase implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent arg0)
        {
            if(validateInputFields())
            {
                try
                {
                    boolean didMatch = RegexEvaluatorService.matches(sourceInput.getText(), regexInput.getText());
                    outputField.setText("" + didMatch);
                }
                catch(Exception ex)
                {
                    outputField.setText("Error:\n" + ex.getMessage());
                }
            }
        }
    }
    
    
    /**
     * Base class for all button listeners.
     */
    private abstract class ButtonListenerBase
    {
        /**
         * Determine if the String value is empty
         * @param value String to test
         * @return boolean true/false
         */
        public boolean isEmpty(final String value)
        {
            if(value == null || "".equals(value))
            {
                return true;
            }
            return false;
        }
        
        
        /**
         * Validate the input fields and set the error messages.
         * @return Boolean - indicating whether or not the fields were valid.
         */
        public boolean validateInputFields()
        {
            String errorMessage = "Validation Errors:\n";
            boolean isValid = true;
            
            if(isEmpty(regexInput.getText()))
            {
                errorMessage += String.format("  * Provide a response for '%s'.\n",
                                              regexLabel.getText().replace(":", ""));
                isValid = false;
            }
            
            if(!isValid)
            {
                outputField.setText(errorMessage);
            }
            else
            {
                outputField.setText("");
            }
            
            return isValid;
        }
    }
}
