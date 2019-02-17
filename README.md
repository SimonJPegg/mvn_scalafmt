
[![licenseimg]][licenselink]  [![Codacy][codacyimg]][codacylink] [![Build Status](https://travis-ci.com/SimonJPegg/mvn_scalafmt.svg?branch=master)](https://travis-ci.com/SimonJPegg/mvn_scalafmt)

[![releasebadge]][releaselink] [![Maven][mavenimg]][mavenlink]

## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/scalameta/scalafmt/) formatter in Maven.


## Versioning 

The versioning of this plugin follows the following format:

```
<artifactId>mvn-scalafmt_${scala.version}</artifactId>
<version>${plugin-version}-${scalafmt-version}</version>
```
 
Current supported versions (of Scalafmt) are 1.1.0 - 1.5.1. For example, to use the latest version 
of the plugin with the latest version of scala-fmt you should set the version to 0.10_1.5.1 in your pom.
Note `scala.version` refers to binary versions of scala i.e. `2.11` or `2.12`.

## Usage

Add the following snippet to your pom; anything in \<parameters\> will be
passed through to the CLI as is.

```xml
<plugin>
  <groupId>org.antipathy</groupId>
  <artifactId>mvn-scalafmt_${scala.version}</artifactId>
  <version>0.10_${scalafmt.version}</version>
  <configuration>
    <parameters>--diff</parameters> <!-- (Optional) Additional command line arguments -->
    <skip>false</skip> <!-- (Optional) skip formatting -->
    <skiptest>false</skiptest> <!-- (Optional) Skip formatting test sources -->
    <skipmain>false</skipmain> <!-- (Optional) Skip formatting main sources -->
    <configLocation>${project.basedir}/path/to/scalafmt.conf</configLocation> <!-- (Optional) config location -->
    <configRequired>false</configRequired><!-- (Optional) raise an error if configLocation is missing or invalid (otherwise use Scalafmt defaults) -->
    <sourceDirectories> <!-- (Optional) Paths to source-directories. Overrides ${project.build.sourceDirectory} -->
      <param>${project.basedir}/src/main/scala</param>
    </sourceDirectories>
    <testSourceDirectories> <!-- (Optional) Paths to test-source-directories. Overrides ${project.build.testSourceDirectory} -->
      <param>${project.basedir}/src/test/scala</param>
    </testSourceDirectories>
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


[licenseimg]: https://img.shields.io/badge/Licence-Apache%202.0-blue.svg
[licenselink]: ./LICENSE

[codacyimg]: https://api.codacy.com/project/badge/Grade/15b50622fcf349cc89301b6c3d40fc4e
[codacylink]: https://app.codacy.com/project/Antipathy_org/mvn_scalafmt/dashboard?branchId=11175791

[mavenimg]: https://maven-badges.herokuapp.com/maven-central/org.antipathy/mvn-scalafmt_2.11/badge.svg
[mavenlink]: https://search.maven.org/search?q=org.antipathy.mvn-scalafmt


[releasebadge]: https://img.shields.io/github/release/simonjpegg/mvn_scalafmt.svg?style=flat
[releaselink]: https://github.com/SimonJPegg/mvn_scalafmt/releases
