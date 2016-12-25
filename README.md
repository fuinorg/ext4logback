#ext4logback
Extensions for Logback

[![Build Status](https://fuin-org.ci.cloudbees.com/job/ext4logback/badge/icon)](https://fuin-org.ci.cloudbees.com/job/ext4logback/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.fuin/ext4logback/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.fuin/ext4logback/)
[![Javadocs](https://www.javadoc.io/badge/org.fuin/ext4logback.svg)](https://www.javadoc.io/doc/org.fuin/ext4logback)
[![LGPLv3 License](http://img.shields.io/badge/license-LGPLv3-blue.svg)](https://www.gnu.org/licenses/lgpl.html)
[![Java Development Kit 1.8](https://img.shields.io/badge/JDK-1.8-green.svg)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

<a href="https://fuin-org.ci.cloudbees.com/job/ext4logback"><img src="http://www.fuin.org/images/Button-Built-on-CB-1.png" width="213" height="72" border="0" alt="Built on CloudBees"/></a>

##Features

Configuring Logback for a standalone application is easy:
```java
new LogbackStandalone("org.your.app", "myapp").init(new File("./logback.xml"));
```
For more options, see [JavaDoc](https://www.javadoc.io/doc/org.fuin/ext4logback).

Here is an example of a main method:

```java
/**
 * Starts the application.
 * 
 * @param args Only optional argument is the 'logback.xml' file path and name.
 */
public static void main(String[] args) {
    final File logbackXmlFile;
    if ((args == null) || (args.length == 0)) {
        // Default name if no config file is provided
        logbackXmlFile = new File("./logback.xml");
    } else {
        logbackXmlFile = new File(args[0]);
    }
    try {
        // Initializes Logback by reading the XML config file.
        // If the file does not exist, it will be created with some defaults.
        new LogbackStandalone("org.your.app", "myapp").init(logbackXmlFile);
        // Launch your application here
        System.exit(0);
    } catch (RuntimeException ex ) {
        ex.printStackTrace(System.err);
        System.exit(1);
    }
}
```


##Snapshots

Snapshots can be found on the [OSS Sonatype Snapshots Repository](http://oss.sonatype.org/content/repositories/snapshots/org/fuin "Snapshot Repository"). 

Add the following to your .m2/settings.xml to enable snapshots in your Maven build:

```xml
<pluginRepository>
    <id>sonatype.oss.snapshots</id>
    <name>Sonatype OSS Snapshot Repository</name>
    <url>http://oss.sonatype.org/content/repositories/snapshots</url>
    <releases>
        <enabled>false</enabled>
    </releases>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</pluginRepository>
```
