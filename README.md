## Synopsis

This is a wrapper that allows you to use the [Scalafmt](https://github.com/olafurpg/scalafmt/) formatter in Mvn.
current Scalafmt version is 0.6.5

## Usage

To include it into your pom.xml, just specify some lines as below  in the phase you want to format your code:

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

##Debug
If you are finding an strange behaviour with the plugin you can check the exit from scalafmt using -X mvn option which provides Debug log.
Also you can remove from your ~/.scalafmt the jar that is copied in order to use the plugin from maven.