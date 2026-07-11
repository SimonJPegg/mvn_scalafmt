[![licenseimg]][licenselink] [![Codacy][codacyimg]][codacylink] ![Coverage][covimg] ![Build Status][buildimg]

[![releasebadge]][releaselink] [![Maven][mavenimg]][mavenlink]

## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/scalameta/scalafmt/) formatter in Maven.

## Usage

Add the following snippet to your pom, and it will be invoked as part of your build during the
selected lifecycle phase (default `validate`).

Note: `version.scala.binary` refers to the major Scala release — currently only `2.13` is supported.

You can also invoke the plugin directly via `mvn scalafmt:format`.

## Versioning

This plugin follows the following versioning convention:

`mvn_scalafmt_(scalaversion)-(major).(minor).(commitepoch).(commithash)`

The latest release should be visible at the top of this readme.

## Minimal Working POM XML:
```xml
    <plugin>
        <groupId>org.antipathy</groupId>
        <artifactId>mvn-scalafmt_${version.scala.binary}</artifactId>
        <!-- Version is in the form: (major).(minor).(commitepoch).(commithash)
             The Scala version is OMITTED from this value. Find releases at:
             https://github.com/simonjpegg/mvn_scalafmt/releases
             e.g. <version>1.0.1589620826.41b214a</version>
        -->
        <version>__DESIRED_MVN_SCALAFMT_VERSION__</version>
        <configuration>
            <configLocation>${project.basedir}/.scalafmt.conf</configLocation> <!-- path to config -->
        </configuration>
        <executions>
            <execution>
                <phase>validate</phase>
                <goals>
                    <goal>format</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
```

## FULL SNIPPET
```xml
<plugin>
    <groupId>org.antipathy</groupId>
    <artifactId>mvn-scalafmt_${version.scala.binary}</artifactId>
    <!-- Version is in the form: (major).(minor).(commitepoch).(commithash)
         The Scala version is OMITTED from this value. Find releases at:
         https://github.com/simonjpegg/mvn_scalafmt/releases
         e.g. <version>1.0.1589620826.41b214a</version>
    -->
    <version>__DESIRED_MVN_SCALAFMT_VERSION__</version>
    <configuration>
        <configLocation>${project.basedir}/.scalafmt.conf</configLocation> <!-- path to config -->
        <skipTestSources>false</skipTestSources> <!-- (Optional) skip formatting test sources -->
        <skipSources>false</skipSources> <!-- (Optional) skip formatting main sources -->
        <sourceDirectories> <!-- (Optional) Paths to source-directories. Overrides ${project.build.sourceDirectory} -->
          <param>${project.basedir}/src/main/scala</param>
        </sourceDirectories>
        <testSourceDirectories> <!-- (Optional) Paths to test-source-directories. Overrides ${project.build.testSourceDirectory} -->
          <param>${project.basedir}/src/test/scala</param>
        </testSourceDirectories>
        <validateOnly>false</validateOnly> <!-- check formatting without changing files -->
        <onlyChangedFiles>true</onlyChangedFiles> <!-- only format (staged) files that have been changed from the specified git branch -->
        <showReformattedOnly>false</showReformattedOnly> <!-- log only modified files -->
        <!-- The git branch to check against.
             If branch.startsWith(": "), the value is treated as a command to run
             and the output is used as the actual branch name. -->
        <branch>: git rev-parse --abbrev-ref HEAD</branch> <!-- the current branch -->
        <!-- <branch>master</branch> -->
        <useSpecifiedRepositories>false</useSpecifiedRepositories> <!-- use project repositories configuration for scalafmt dynamic loading -->
    </configuration>
    <executions>
        <execution>
            <phase>validate</phase> <!-- default -->
            <goals>
                <goal>format</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

`configLocation` can either be a local path (e.g. `${project.basedir}/.scalafmt.conf`) or an HTTP URL (e.g. `https://raw.githubusercontent.com/jozic/scalafmt-config/master/.scalafmt.conf`)

Make sure you have set a version in your `.scalafmt.conf`:
```yaml
version = "3.9.6"
```

[licenseimg]: https://img.shields.io/badge/Licence-Apache%202.0-blue.svg
[licenselink]: ./LICENSE
[buildimg]: https://github.com/SimonJPegg/mvn_scalafmt/workflows/Build213/badge.svg
[covimg]: https://app.codacy.com/project/badge/Coverage/f7d89aaf1a05436b86043168b7b26715
[codacyimg]: https://app.codacy.com/project/badge/Grade/f7d89aaf1a05436b86043168b7b26715
[codacylink]: https://www.codacy.com/gh/SimonJPegg/mvn_scalafmt/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=SimonJPegg/mvn_scalafmt&amp;utm_campaign=Badge_Grade
[mavenimg]: https://img.shields.io/maven-central/v/org.antipathy/mvn-scalafmt_2.13.svg
[mavenlink]: https://central.sonatype.com/artifact/org.antipathy/mvn-scalafmt_2.13
[releasebadge]: https://img.shields.io/github/release/simonjpegg/mvn_scalafmt.svg?style=flat
[releaselink]: https://github.com/SimonJPegg/mvn_scalafmt/releases
