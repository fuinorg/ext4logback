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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Configures Logback in a standalone environment.
 */
public final class LogbackStandalone {

    private static final Logger LOG = LoggerFactory.getLogger(LogbackStandalone.class);
    
    /**
     * Initializes the Logback system using an existing configuration file.
     * Fails with a runtime exception if the file does not exist. This is a
     * convenience method for using it in a main method if the first argument is
     * the logfile path and name.
     * 
     * @param args
     *            Arguments passed through from the main method.
     */
    public final void init(final String[] args) {
        if ((args == null) || (args.length == 0)) {
            init(new File("logback.xml"));
        } else {
            init(new File(args[0]));
        }
    }

    /**
     * Initializes the Logback system using an existing configuration file.
     * Fails with a runtime exception if the file does not exist. This is a
     * convenience method for using it in a main method if the first argument is
     * the logfile path and name.
     * 
     * @param args
     *            Arguments passed through from the main method.
     * @param params
     *            Parameters to use for creation of the 'logback.xml' file.
     */
    public final void init(final String[] args, final NewLogConfigFileParams params) {
        if ((args == null) || (args.length == 0)) {
            init(new File("logback.xml"), params);
        } else {
            init(new File(args[0]), params);
        }
    }

    /**
     * Initializes the Logback system using an existing configuration file.
     * Fails with a runtime exception if the file does not exist.
     * 
     * @param logbackXmlFile
     *            File to load - Cannot be <code>null</code>.
     */
    public final void init(final File logbackXmlFile) {
        if (logbackXmlFile == null) {
            throw new IllegalArgumentException("The arg 'logbackXmlFile' cannot be null");
        }
        try {
            final File configFile = logbackXmlFile.getAbsoluteFile();
            if (!configFile.exists()) {
                throw new FileNotFoundException(
                        "Logback XML configuration file not found: " + configFile.toString());
            }
            System.setProperty("log_path", configFile.getParentFile().getCanonicalPath());
            loadLogfile(configFile);
        } catch (final IOException ex) {
            throw new RuntimeException("Error initializing Logback", ex);
        }
    }

    /**
     * Initializes the Logback system using a configuration file. The file will
     * be created if it does not exist.
     * 
     * @param logbackXmlFile
     *            File to load - Cannot be <code>null</code>.
     * @param params
     *            Parameters to use for creation of the 'logback.xml' file - Cannot be <code>null</code>.
     */
    public final void init(final File logbackXmlFile, final NewLogConfigFileParams params) {
        if (logbackXmlFile == null) {
            throw new IllegalArgumentException("The arg 'logbackXmlFile' cannot be null");
        }
        if (params == null) {
            throw new IllegalArgumentException("The arg 'params' cannot be null");
        }
        try {
            final File configFile = logbackXmlFile.getAbsoluteFile();
            if (!configFile.exists()) {
                writeInitialLogbackXml(configFile, params.getRootLevel(), params.getPkgName(),
                        params.getPkgLevel(), params.getLayoutPattern(), params.getLogFilename());
            }
            System.setProperty("log_path", configFile.getParentFile().getCanonicalPath());
            loadLogfile(configFile);
        } catch (final IOException ex) {
            throw new RuntimeException("Error initializing Logback", ex);
        }
    }

    /**
     * Creates an initial Logback configuration.
     * 
     * @param logbackXmlFile
     *            XML logback configuration file to write.
     * @param rootLevel
     *            Level of the root logger.
     * @param pkgName
     *            Main package name (like "org.your.name").
     * @param pkgLevel
     *            Main package log level.
     * @param layoutPattern
     *            Layout pattern.
     * @param logFilename
     *            Name of the log file without extension.
     * 
     * @throws IOException
     *             Error writing the configuration file.
     */
    public static void writeInitialLogbackXml(final File logbackXmlFile, final Level rootLevel,
            final String pkgName, final Level pkgLevel, final String layoutPattern, final String logFilename)
            throws IOException {
        // @formatter:off
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
                + "<configuration>\n"
                + "    <appender name=\"FILE\" class=\"ch.qos.logback.core.rolling.RollingFileAppender\">\n"
                + "        <file>${log_path}/" + logFilename + ".log</file>\n"
                + "        <rollingPolicy class=\"ch.qos.logback.core.rolling.TimeBasedRollingPolicy\">\n"
                + "            <fileNamePattern>" + logFilename + ".%d{yyyy-MM-dd}.log</fileNamePattern>\n"
                + "            <maxHistory>30</maxHistory>\n" 
                + "        </rollingPolicy>\n"
                + "        <append>true</append>\n"
                + "        <layout class=\"ch.qos.logback.classic.PatternLayout\">\n"
                + "            <Pattern>" + layoutPattern + "</Pattern>\n" 
                + "        </layout>\n"
                + "    </appender>\n" 
                + "    <root level=\"" + rootLevel + "\">\n"
                + "        <appender-ref ref=\"FILE\" />\n" 
                + "    </root>\n" 
                + "    <logger name=\"" + pkgName + "\" level=\"" + pkgLevel + "\" />\n" 
                + "</configuration>\n";
        // @formatter:on
        try (final Writer fw = new OutputStreamWriter(new FileOutputStream(logbackXmlFile),
                Charset.forName("UTF-8"))) {
            fw.write(xml);
        }
    }

    /**
     * Loads the Logback configuration.
     * 
     * @param logbackXmlFile
     *            XML Logback configuration file to load.
     */
    private void loadLogfile(final File logbackXmlFile) {
        final LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        final JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        context.reset();
        try {
            configurator.doConfigure(logbackXmlFile);
        } catch (final JoranException ex) {
            throw new RuntimeException(ex);
        }
        LOG.info("Logging initialized: {}", logbackXmlFile);
    }

}
