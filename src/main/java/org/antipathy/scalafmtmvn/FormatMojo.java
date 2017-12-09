package org.antipathy.scalafmtmvn;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
    @Parameter(defaultValue = "false")
    private boolean skip;
    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Skip flag set, skipping formatting");
            return;
        }
        if (StringUtils.isBlank(configLocation)) {
            throw new MojoExecutionException("No configuration file specified");
        }
        getLog().info("Formatting with config: " + configLocation);

        if (!StringUtils.isBlank(parameters)) {
            getLog().info(" and options: " + parameters);
        } else {
            parameters = "";
        }


        String[] sourceRoots = getSourceRoots();
        getLog().info(" sources: " + Arrays.toString(sourceRoots));
        if (sourceRoots.length == 0) {
            getLog().info("No sources, skipping formatting");
            return;
        }

        try {
            Formatter.format(configLocation, parameters, sourceRoots);
        } catch (Exception e) {
            throw new MojoExecutionException("Error formatting Scala files", e);
        }
    }

    private String[] getSourceRoots() {
        List<String> sourceRoots = new ArrayList<>();
        if (project.getCompileSourceRoots() != null) {
            //noinspection unchecked
            sourceRoots.addAll(project.getCompileSourceRoots());
        }
        if (project.getTestCompileSourceRoots() != null) {
            //noinspection unchecked
            sourceRoots.addAll(project.getTestCompileSourceRoots());
        }

        // Remove non-existing paths or Scalafmt will fail
        removeNonExistingPaths(sourceRoots);

        return sourceRoots.toArray(new String[sourceRoots.size()]);
    }

    private static void removeNonExistingPaths(List<String> paths) {
        Iterator<String> ite = paths.iterator();
        while (ite.hasNext()) {
            if (!Files.exists(Paths.get(ite.next()))) {
                ite.remove();
            }
        }
    }
}
