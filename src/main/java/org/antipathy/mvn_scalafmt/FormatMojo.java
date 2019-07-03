package org.antipathy.mvn_scalafmt;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Get the location of the config file and pass to Formatter
 */
@Mojo(name = "format")
public class FormatMojo extends AbstractMojo {

    @Parameter(property = "format.configLocation")
    private String configLocation;
    @Parameter(property = "format.skipTestSources", defaultValue = "false")
    private boolean skipTestSources;
    @Parameter(property = "format.skipSources", defaultValue = "false")
    private boolean skipSources;
    @Parameter(defaultValue = "${project.build.sourceDirectory}/../scala", required = true)
    private List<File> sourceDirectories;
    @Parameter(defaultValue = "${project.build.testSourceDirectory}/../scala", required = true)
    private List<File> testSourceDirectories;
    @Parameter(property = "format.respectVersion", defaultValue = "false", required = true)
    private boolean respectVersion;

    public void execute() throws MojoExecutionException {

        ArrayList<Object> sources = new ArrayList<>();

        if (!skipSources) {
            sources.addAll(sourceDirectories);
        } else {
            getLog().warn("format.skipSources set, ignoring main directories");
        }

        if (!skipTestSources) {
            sources.addAll(testSourceDirectories);
        } else {
            getLog().warn("format.skipTestSources set, ignoring test directories");
        }
        if (sources.size() > 0) {
            try {
                String result = ScalaFormatter.apply(configLocation,getLog(),respectVersion).format(sources);
                getLog().info(result);
            } catch (Exception e) {
                getLog().error(e);
                throw new MojoExecutionException("Error formatting Scala files: " + e.getMessage(), e);
            }
        } else {
            getLog().warn("No sources specified, skipping formatting");
        }
    }
}
