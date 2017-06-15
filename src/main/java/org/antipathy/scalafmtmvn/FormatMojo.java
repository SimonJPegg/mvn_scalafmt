package org.antipathy.scalafmtmvn;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Get the location of the config file and pass to Formatter
 */
@Mojo(name = "format")
public class FormatMojo extends AbstractMojo {

    @Parameter(property="format.configLocation",defaultValue="")
    private String configLocation;
    @Parameter(property="format.parameters",defaultValue="")
    private String parameters;

    public void execute() throws MojoExecutionException {
        if(StringUtils.isEmpty(configLocation)) {
            throw new MojoExecutionException("No configuration file specified");
        } else {
            getLog().info("Formatting with config: " + configLocation);
            if (parameters != null) {
                getLog().info(" and options: " + parameters);
            }
            try {
                Formatter.format(configLocation, parameters);
            } catch (Exception e) {
                throw new MojoExecutionException("Error formatting Scala files", e);
            }
        }
    }
}
