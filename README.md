## Synopsis

A wrapper that allows the use of the [Scalafmt](https://github.com/olafurpg/scalafmt/) formatter in Maven;
The Current Scalafmt version is 0.6.5

## Usage

Add the following snippet to your pom.

```xml
<plugin>
  <groupId>org.antipathy</groupId>
  <artifactId>mvn-scalafmt</artifactId>
  <version>0.1</version>
  <configuration>
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
