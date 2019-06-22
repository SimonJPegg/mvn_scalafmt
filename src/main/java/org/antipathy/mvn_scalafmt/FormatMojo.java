package org.antipathy.mvn_scalafmt;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Get the location of the config file and pass to Formatter
 */
@Mojo(name = "format")
public class FormatMojo extends AbstractMojo {

    @Parameter(property = "format.configLocation")
    private String configLocation;
    @Parameter(property = "format.configRequired")
    private boolean configRequired;
    @Parameter(property = "format.parameters")
    private String parameters;
    @Parameter(property = "format.skip", defaultValue = "false")
    private boolean skip;
    @Parameter(property = "format.skiptest", defaultValue = "false")
    private boolean skipTestSources;
    @Parameter(property = "format.skipmain", defaultValue = "false")
    private boolean skipSources;
    @Parameter(defaultValue = "${project.build.sourceDirectory}/../scala", required = true)
    private List<File> sourceDirectories;
    @Parameter(defaultValue = "${project.build.testSourceDirectory}/../scala", required = true)
    private List<File> testSourceDirectories;

    public void execute() throws MojoExecutionException {

        List<Object> sources = prepareSources(skipSources, sourceDirectories);
        List<Object> testSources = prepareSources(skipTestSources, testSourceDirectories);

        if(!skip) {
            try {
                ScalaFormatter.format(
                        configLocation,
                        configRequired,
                        parameters,
                        sources,
                        testSources,
                        getLog());
            } catch (Exception e) {
                throw new MojoExecutionException("Error formatting Scala files", e);
            }
        } else {
            getLog().info("Skip flag set, skipping formatting");
        }
    }

    private List<Object> prepareSources(boolean skip, List<File> sources) throws MojoExecutionException {
        ArrayList<Object> prepared = new ArrayList<>();
        if(!skip) {
            for (File source : sources) {
                try {
                    prepared.add(source.getCanonicalFile());
                } catch (IOException e) {
                    throw new MojoExecutionException("Can't get canonical file for " + source.toString(), e);
                }
            }
        }
        return Collections.unmodifiableList(prepared);
    }
}
