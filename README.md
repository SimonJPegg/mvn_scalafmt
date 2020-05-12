[![licenseimg]][licenselink]  [![Codacy][codacyimg]][codacylink] ![Build Status][buildimg]

[![releasebadge]][releaselink] [![Maven][mavenimg]][mavenlink]

## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/scalameta/scalafmt/) formatter in Maven.

## Usage

Add the following snippet to your pom.

Note: `version.scala.binary` refers to major releases of scala ie. 2.11, 2.12 or 2.13.  
mvn_scalafmt_2.11 will soon be deprecated and may not receive future releases

## Versioning 

This plugin follows the following versioning convention:

`mvn_scalafmt_(scalaversion)-(major).(minor).(commitepoch).(commithash)`

The latest release should be visible at the top of this readme.

```xml
<plugin>
    <groupId>org.antipathy</groupId>
    <!-- The scala binary here doesn't need to match the project version -->
    <artifactId>mvn-scalafmt_${version.scala.binary}</artifactId>
    <!-- <artifactId>mvn-scalafmt_${version.scala.binary}</artifactId> -->
    <version>1.0.somerelease</version>
    <configuration>
        <configLocation>${project.basedir}/.scalafmt.conf</configLocation> <!-- path to config -->
        <skipTestSources>false</skipTestSources> <!-- (Optional) skip formatting test sources -->
        <skipSources>false</skipSources> <!-- (Optional) skip formatting main sources -->
        <respectVersion>false</respectVersion> <!-- (Optional) fail if no version set in scalafmt.conf -->
        <sourceDirectories> <!-- (Optional) Paths to source-directories. Overrides ${project.build.sourceDirectory} -->
          <param>${project.basedir}/src/main/scala</param>
        </sourceDirectories>
        <testSourceDirectories> <!-- (Optional) Paths to test-source-directories. Overrides ${project.build.testSourceDirectory} -->
          <param>${project.basedir}/src/test/scala</param>
        </testSourceDirectories>
        <validateOnly>false</validateOnly> <!-- check formatting without changing files -->
        <onlyChangedFiles>true</onlyChangedFiles> <!-- only format (staged) files that have been changed from the specified git branch -->
        <!-- The git branch to check against
             If branch.startsWith(": ") the value in <branch> tag is used as a command to run
             and the output will be used as the actual branch-->
        <branch>: git rev-parse --abbrev-ref HEAD</branch> <!-- the current branch-->
        <!-- <branch>master</branch>-->
        <useSpecifiedRepositories>false</useSpecifiedRepositories> <!-- use project repositories configuration for scalafmt dynamic loading -->
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

`configLocation` Can either be a local path (e.g. `${project.basedir}/.scalafmt.conf`) or a HTTP url (e.g `https://raw.githubusercontent.com/jozic/scalafmt-config/master/.scalafmt.conf`)

make sure you have set a version in your scalafmt.conf 
```yaml
version = "2.5.2"
```

[licenseimg]: https://img.shields.io/badge/Licence-Apache%202.0-blue.svg
[licenselink]: ./LICENSE

[buildimg]: https://github.com/SimonJPegg/mvn_scalafmt/workflows/Release/badge.svg

[codacyimg]: https://api.codacy.com/project/badge/Grade/15b50622fcf349cc89301b6c3d40fc4e
[codacylink]: https://app.codacy.com/project/Antipathy_org/mvn_scalafmt/dashboard?branchId=11175791

[mavenimg]: https://maven-badges.herokuapp.com/maven-central/org.antipathy/mvn-scalafmt_2.11/badge.svg
[mavenlink]: https://search.maven.org/search?q=org.antipathy.mvn-scalafmt

[releasebadge]: https://img.shields.io/github/release/simonjpegg/mvn_scalafmt.svg?style=flat
[releaselink]: https://github.com/SimonJPegg/mvn_scalafmt/releases
