package org.antipathy.scalafmtmvn;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.util.List;

/**
 * Get the location of the config file and pass to Formatter
 */
@Mojo(name = "format")
public class FormatMojo extends AbstractMojo {

    @Parameter(property = "format.configLocation")
    private String configLocation;
    @Parameter(property = "format.parameters")
    private String parameters;
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        if (StringUtils.isBlank(configLocation)) {
            throw new MojoExecutionException("No configuration file specified");
        }
        getLog().info("Formatting with config: " + configLocation);

        if (!StringUtils.isBlank(parameters)) {
            getLog().info(" and options: " + parameters);
        } else {
            parameters = "";
        }

        List<String> sourceRoots = project.getCompileSourceRoots();
        String[] sources = (sourceRoots == null) ? new String[0] : sourceRoots.toArray(new String[sourceRoots.size()]);
        getLog().info(" sources: " + sources);

        try {
            Formatter.format(configLocation, parameters, sources);
        } catch (Exception e) {
            throw new MojoExecutionException("Error formatting Scala files", e);
        }
    }
}
