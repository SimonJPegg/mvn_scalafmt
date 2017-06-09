## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/olafurpg/scalafmt/) formatter in Maven;
The Current Scalafmt version is 0.6.8

## Usage

Add the following snippet to your pom; anything in <parameters> will be
passed through to the CLI as is.

In the dependency section you can specify a scalafmt version.

```xml
<plugin>
  <groupId>org.antipathy</groupId>
  <artifactId>mvn-scalafmt</artifactId>
  <version>0.2</version>
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
  <dependencies>
    <!-- https://mvnrepository.com/artifact/com.geirsson/scalafmt-core_2.11 -->
    <dependency>
        <groupId>com.geirsson</groupId>
        <artifactId>scalafmt-core_2.11</artifactId>
        <version>0.6.8</version> <!-- or use a variable such as ${scalaFMTVersion}
        to define the version in the properties section of the pom -->
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.geirsson/scalafmt-cli_2.11 -->
    <dependency>
        <groupId>com.geirsson</groupId>
        <artifactId>scalafmt-cli_2.11</artifactId>
        <version>0.6.8</version>
    </dependency>
</dependencies>
</plugin>
```
