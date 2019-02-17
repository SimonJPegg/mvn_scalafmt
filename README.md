[![licenseimg]][licenselink]  [![Build Status](https://travis-ci.com/SimonJPegg/mvn_scoozie.svg?branch=master)](https://travis-ci.com/SimonJPegg/mvn_scoozie)

[![releasebadge]][releaselink] [![Maven][mavenimg]][mavenlink]

## Synopsis

A wrapper that allows the use of the [Scoozie](https://github.com/SimonJPegg/scoozie) generation tool in Maven.

## Versioning 

The version of this plugin should track the latest version of Scoozie

## Usage

Add the following snippet to your pom.

```xml
<build>
    ...
    <plugins>
        <plugin>
            <groupId>org.antipathy</groupId>
            <artifactId>mvn-scoozie</artifactId>
            <version>${scoozie.version}</version>
            <executions>
                <execution>
                    <id>scoozie-generate</id>
                    <phase>prepare-package</phase>
                    <goals>
                        <goal>scoozie</goal>
                    </goals>
                </execution>
            </executions>
            <configuration>
                <artefacts>
                    <artefact>
                        <configFile>/path/to/some.conf</configFile>
                        <saveAsZip>true</saveAsZip>
                    </artefact>
                    <artefact>
                        <configFile>/path/to/someOther.conf</configFile>
                        <saveAsZip>false</saveAsZip>
                    </artefact>
                </artefacts>
            </configuration>
        </plugin>
    </plugins>
</build>
```

See the [Scoozie Hocon documentation](https://github.com/SimonJPegg/scoozie/blob/master/HoconAPI.md) for more information on configuring conf files/

[licenseimg]: https://img.shields.io/badge/Licence-Apache%202.0-blue.svg
[licenselink]: ./LICENSE


[mavenimg]: https://maven-badges.herokuapp.com/maven-central/org.antipathy/mvn-scoozie/badge.svg
[mavenlink]: https://search.maven.org/search?q=org.antipathy.mvn-scoozie

[releasebadge]: https://img.shields.io/github/release/simonjpegg/mvn_scoozie.svg?style=flat
[releaselink]: https://github.com/SimonJPegg/mvn_scalafmt/releases