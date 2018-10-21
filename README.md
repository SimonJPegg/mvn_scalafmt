## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/scalameta/scalafmt/) formatter in Maven.


## Versioning 

The versioning of this plugin follows the following format:

```
<artifactId>mvn-scalafmt_${scala.version}</artifactId>
<version>${plugin-version}-${scalafmt-version}</version>
```
 
Current supported versions (of Scalafmt) are 1.1.0 - 1.5.1. For example, to use the latest version 
of the plugin with the latest version of scala-fmt you should set the version to 0.7_1.5.1 in your pom.
Note `scala.version` refers to binary versions of scala i.e. `2.11` or `2.12`.

## Usage

Add the following snippet to your pom; anything in \<parameters\> will be
passed through to the CLI as is.

```xml
<plugin>
  <groupId>org.antipathy</groupId>
  <artifactId>mvn-scalafmt_${scala.version}</artifactId>
  <version>0.7_${scalafmt.version}</version>
  <configuration>
    <parameters>--diff</parameters> <!-- (Optional) Additional command line arguments -->
    <skip>false</skip> <!-- (Optional) skip formatting -->
    <skiptest>false</skip> <!-- (Optional) Skip formatting test sources -->
    <skipmain>false</skip> <!-- (Optional) Skip formatting main sources -->
    <configLocation>${project.basedir}/path/to/scalafmt.conf</configLocation> <!-- (Optional) config locataion -->
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

Make sure your source paths are setup correctly, for example:

```xml
<build>
    <sourceDirectory>src/main/scala</sourceDirectory>
    <testSourceDirectory>src/test/scala</testSourceDirectory>
    ...
</build>
```
