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

import org.slf4j.event.Level;

/**
 * Parameters for creating a new logback.xml configuration file.
 */
public final class NewLogConfigFileParams {

    private final Level rootLevel;

    private final String pkgName;

    private final Level pkgLevel;

    private final String layoutPattern;

    private final String logFilename;

    /**
     * Constructor with mandatory values.
     * 
     * @param pkgName
     *            Main package name (like "org.your.name").
     * @param logFilename
     *            Name of the log file without extension.
     */
    public NewLogConfigFileParams(final String pkgName, final String logFilename) {
        this(Level.WARN, pkgName, Level.INFO,
                "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n", logFilename);
    }

    /**
     * Constructor with all configuration options.
     * 
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
     */
    public NewLogConfigFileParams(final Level rootLevel, final String pkgName, final Level pkgLevel,
            final String layoutPattern, final String logFilename) {
        super();
        this.rootLevel = rootLevel;
        this.pkgName = pkgName;
        this.pkgLevel = pkgLevel;
        this.layoutPattern = layoutPattern;
        this.logFilename = logFilename;
    }

    /**
     * Returns the level of the root logger.
     * 
     * @return Level.
     */
    public final Level getRootLevel() {
        return rootLevel;
    }

    /**
     * Returns the main package name.
     * 
     * @return Package name (like "org.your.name").
     */
    public final String getPkgName() {
        return pkgName;
    }

    /**
     * Returns the main package log level.
     * 
     * @return Log level.
     */
    public final Level getPkgLevel() {
        return pkgLevel;
    }

    /**
     * Returns the layout pattern.
     * 
     * @return Pattern.
     */
    public final String getLayoutPattern() {
        return layoutPattern;
    }

    /**
     * Returns the name of the log file without extension.
     * 
     * @return Log file name (name only - no extension)
     */
    public final String getLogFilename() {
        return logFilename;
    }

    @Override
    public final String toString() {
        return "NewLogConfigFileParams [rootLevel=" + rootLevel + ", pkgName=" + pkgName + ", pkgLevel="
                + pkgLevel + ", layoutPattern=" + layoutPattern + ", logFilename=" + logFilename + "]";
    }

}
