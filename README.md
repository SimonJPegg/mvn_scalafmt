## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/olafurpg/scalafmt/) formatter in Maven;

Note: The version of the plugin should match the version of scalafmt you wish to use.  Current supported versions are 0.6.0 - 0.6.8.  All versions are currently compiled against Scala 2.11.8.  If you require anything else, please open an issue.

## Usage

Add the following snippet to your pom; anything in <parameters> will be
passed through to the CLI as is.


```xml
<plugin>
  <groupId>org.antipathy</groupId>
  <artifactId>mvn-scalafmt</artifactId>
  <version>${scalafmt.version}</version>
  <configuration>
    <parameters>--diff</parameters> <!-- Additional command line arguments-->
    <configLocation>${project.basedir}/path/to/scalafmt.conf</configLocation>
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
