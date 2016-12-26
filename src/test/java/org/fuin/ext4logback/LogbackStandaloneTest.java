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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.fuin.utils4j.Utils4J;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

//CHECKSTYLE:OFF
public class LogbackStandaloneTest {

    private static final Logger LOG = LoggerFactory.getLogger(LogbackStandaloneTest.class);

    @Test
    public void testInitNonExistingLogConfigFile() throws IOException {

        // PREPARE
        final String logname = "ext4logback-1";
        final File logFile = new File(Utils4J.getTempDir(), logname + ".log");
        if (logFile.exists()) {
            assertThat(logFile.delete()).isTrue();
        }
        final File logbackXmlFile = new File(Utils4J.getTempDir(), "testInitNonExistingLogConfigFile.xml");
        if (logbackXmlFile.exists()) {
            assertThat(logbackXmlFile.delete()).isTrue();
        }

        final LogbackStandalone testee = new LogbackStandalone();

        // TEST
        testee.init(logbackXmlFile,
                new NewLogConfigFileParams(this.getClass().getPackage().getName(), logname));
        LOG.info("logbackXmlFile={}", logbackXmlFile);

        // VERIFY
        final String expectedXml = IOUtils.toString(Utils4J.url("classpath:logback-expected-1.xml"),
                Charset.forName("utf-8"));
        assertThat(logbackXmlFile).hasContent(expectedXml);
        final String expectedLog = FileUtils.readFileToString(logFile, Charset.forName("utf-8"));
        assertThat(expectedLog).contains("LogbackStandaloneTest - logbackXmlFile=");
        assertThat(System.getProperty("log_path")).isEqualTo(Utils4J.getTempDir().getCanonicalPath());

    }

    @Test
    public void testwriteInitialLogbackXml() throws IOException {

        // PREPARE
        final File logbackXmlFile = File
                .createTempFile(this.getClass().getSimpleName() + "-testwriteInitialLogbackXml-", ".xml");

        // TEST
        LogbackStandalone.writeInitialLogbackXml(logbackXmlFile, Level.WARN,
                this.getClass().getPackage().getName(), Level.INFO,
                "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", "ext4logback-2");

        // VERIFY
        final String expectedXml = IOUtils.toString(Utils4J.url("classpath:logback-expected-2.xml"),
                Charset.forName("utf-8"));
        assertThat(logbackXmlFile).hasContent(expectedXml);

    }

}
// CHECKSTYLE:ON
