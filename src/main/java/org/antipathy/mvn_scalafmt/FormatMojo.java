package org.antipathy.mvn_scalafmt;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import java.util.ArrayList;

/**
 * Get the location of the config file and pass to Formatter
 */
@Mojo(name = "format")
public class FormatMojo extends AbstractMojo {

    @Parameter(property = "format.configLocation")
    private String configLocation;
    @Parameter(property = "format.parameters")
    private String parameters;
    @Parameter(property = "format.skip", defaultValue = "false")
    private boolean skip;
    @Parameter(property = "format.skiptest", defaultValue = "false")
    private boolean skipTestSources;
    @Parameter(property = "format.skipmain", defaultValue = "false")
    private boolean skipSources;
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {

        ArrayList<Object> sources = new ArrayList<>();
        ArrayList<Object> testSources = new ArrayList<>();

        if (!skipSources) {
            sources.addAll(project.getCompileSourceRoots());
        }

        if (!skipTestSources) {
            testSources.addAll(project.getTestCompileSourceRoots());
        }

        if(!skip) {
            try {
                ScalaFormatter.format(
                        configLocation,
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
}
