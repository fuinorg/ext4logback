package org.fuin.ext4logback.example;

import org.fuin.ext4logback.LogbackStandalone;
import org.fuin.ext4logback.NewLogConfigFileParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Minimalistic example app.
 */
public class ExampleApp {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleApp.class);
    
    /**
     * Starts the application.
     * 
     * @param args
     *            Only optional argument is the 'logback.xml' file path and name. If no argument is provided it's assumed that the file name
     *            is 'logback.xml' and it's in the current directory.
     */
    public static void main(final String[] args) {
        try {
            // Initializes Logback by reading the XML config file.
            // If the file does not exist, it will be created with some defaults.
            // This is a convenience method that directly uses the main method's arguments.
            new LogbackStandalone().init(args, new NewLogConfigFileParams("org.fuin.ext4logback.example", "ext4logback-example-app"));
            LOG.info("Application running...");
            // Your code goes here...
            System.out.println("Here we go!");
            System.exit(0);
        } catch (RuntimeException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

}
