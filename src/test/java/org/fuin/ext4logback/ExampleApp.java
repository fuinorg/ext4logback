/**
 * Copyright (C) 2015 Michael Schnell. All rights reserved. 
 * http://www.fuin.org/
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library. If not, see http://www.gnu.org/licenses/.
 */
package org.fuin.ext4logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example application that initializes the logging.
 */
public final class ExampleApp {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleApp.class);

    /**
     * Starts the application.
     * 
     * @param args
     *            Only optional argument is the 'logback.xml' file path and
     *            name. If no argument is provided it's assumed that the file
     *            name is 'logback.xml' and it's in the current directory.
     */
    public static void main(final String[] args) {
        try {
            // Initializes Logback by reading the XML config file. If the file
            // does not exist, it will be created with some defaults. This is a
            // convenience method that directly uses the main method's
            // arguments.
            new LogbackStandalone().init(args, new NewLogConfigFileParams("your.app.package", "myapp"));
            LOG.info("Application running...");
            // Your code goes here...
            System.exit(0);
        } catch (RuntimeException ex) {
            ex.printStackTrace(System.err);
            System.exit(1);
        }
    }

}
