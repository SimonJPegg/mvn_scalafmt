[![licenseimg]][licenselink]  [![Codacy][codacyimg]][codacylink] ![Coverage][covimg] ![Build Status][buildimg] [![Total alerts][lgtmimg]][lgtmlink]

[![releasebadge]][releaselink] [![Maven][mavenimg]][mavenlink]

## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/scalameta/scalafmt/) formatter in Maven.

## Usage

Add the following snippet to your pom, and it will be invoked as part of your build during the
selected lifecycle phase (default `validate`).

Note: `version.scala.binary` refers to major releases of scala ie. 2.11, 2.12 or 2.13.  
mvn_scalafmt_2.11 will soon be deprecated and may not receive future releases

You can also invoke the plugin directly via `mvn scalafmt:format`.

## Versioning 

This plugin follows the following versioning convention:

`mvn_scalafmt_(scalaversion)-(major).(minor).(commitepoch).(commithash)`

The latest release should be visible at the top of this readme.

## Minimal Working POM XML:
```xml
    <plugin>
        <groupId>org.antipathy</groupId>
        <!-- The scala binary here doesn't need to match the project version -->
        <artifactId>mvn-scalafmt_${version.scala.binary}</artifactId>
        <!-- This represents the desired version of the plugin, whould be in the form:
             (major).(minor).(commitepoch).(commithash), which can be found here:
             https://github.com/simonjpegg/mvn_scalafmt/releases
             e.g. <version>1.0.1589620826.41b214a</version>
             Note: The SCALA version is OMITTED from this value
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
    <!-- The scala binary here doesn't need to match the project version -->
    <artifactId>mvn-scalafmt_${version.scala.binary}</artifactId>
    <!-- This represents the desired version of the plugin, whould be in the form:
         (major).(minor).(commitepoch).(commithash), which can be found here:
         https://github.com/simonjpegg/mvn_scalafmt/releases
         e.g. <version>1.0.1589620826.41b214a</version>
         Note: The SCALA version is OMITTED from this value
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
        <!-- The git branch to check against
             If branch.startsWith(": ") the value in <branch> tag is used as a command to run
             and the output will be used as the actual branch-->
        <branch>: git rev-parse --abbrev-ref HEAD</branch> <!-- the current branch-->
        <!-- <branch>master</branch>-->
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

`configLocation` Can either be a local path (e.g. `${project.basedir}/.scalafmt.conf`) or a HTTP url (e.g `https://raw.githubusercontent.com/jozic/scalafmt-config/master/.scalafmt.conf`)

make sure you have set a version in your scalafmt.conf 
```yaml
version = "2.6.2"
```
[lgtmimg]: https://img.shields.io/lgtm/alerts/g/SimonJPegg/mvn_scalafmt.svg?logo=lgtm&logoWidth=18
[lgtmlink]: https://lgtm.com/projects/g/SimonJPegg/mvn_scalafmt/alerts/
[licenseimg]: https://img.shields.io/badge/Licence-Apache%202.0-blue.svg
[licenselink]: ./LICENSE
[buildimg]: https://github.com/SimonJPegg/mvn_scalafmt/workflows/Build213/badge.svg
[covimg]: https://app.codacy.com/project/badge/Coverage/15b50622fcf349cc89301b6c3d40fc4e
[codacyimg]: https://api.codacy.com/project/badge/Grade/15b50622fcf349cc89301b6c3d40fc4e
[codacylink]: https://app.codacy.com/project/Antipathy_org/mvn_scalafmt/dashboard?branchId=11175791
[mavenimg]: https://maven-badges.herokuapp.com/maven-central/org.antipathy/mvn-scalafmt_2.11/badge.svg
[mavenlink]: https://search.maven.org/search?q=org.antipathy.mvn-scalafmt
[releasebadge]: https://img.shields.io/github/release/simonjpegg/mvn_scalafmt.svg?style=flat
[releaselink]: https://github.com/SimonJPegg/mvn_scalafmt/releases
