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
public class NewLogbackConfigFileParamsTest {

    @Test
    public void testConstructionMinimalArgs() {

        // PREPARE
        final String pkgName = "org.fuin.ext4logback";
        final String logFilename = "ext4logback";

        // TEST
        final NewLogConfigFileParams testee = new NewLogConfigFileParams(pkgName, logFilename);

        // VERIFY
        assertThat(testee.getRootLevel()).isNotNull();
        assertThat(testee.getPkgName()).isEqualTo(pkgName);
        assertThat(testee.getPkgLevel()).isNotNull();
        assertThat(testee.getLayoutPattern()).isNotEmpty();
        assertThat(testee.getLogFilename()).isEqualTo(logFilename);

    }

    @Test
    public void testConstructionAllArgs() {

        // PREPARE
        final Level rootLevel = Level.ERROR;
        final String pkgName = "org.fuin.ext4logback";
        final Level pkgLevel = Level.DEBUG;
        final String layoutPattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n";
        final String logFilename = "ext4logback";
        
        // TEST
        final NewLogConfigFileParams testee = new NewLogConfigFileParams(rootLevel, pkgName, pkgLevel, layoutPattern, logFilename);

        // VERIFY
        assertThat(testee.getRootLevel()).isEqualTo(rootLevel);
        assertThat(testee.getPkgName()).isEqualTo(pkgName);
        assertThat(testee.getPkgLevel()).isEqualTo(pkgLevel);
        assertThat(testee.getLayoutPattern()).isEqualTo(layoutPattern);
        assertThat(testee.getLogFilename()).isEqualTo(logFilename);
        

    }
    
}
// CHECKSTYLE:ON
