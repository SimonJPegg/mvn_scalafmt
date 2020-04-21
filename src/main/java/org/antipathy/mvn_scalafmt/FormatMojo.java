package org.antipathy.mvn_scalafmt;

import org.antipathy.mvn_scalafmt.model.Summary;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.apache.maven.model.Repository;

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
    @Parameter(property = "format.validateOnly", defaultValue = "false")
    private boolean validateOnly;
    @Parameter(property = "format.onlyChangedFiles", defaultValue = "false")
    private boolean onlyChangedFiles;
    @Parameter(property = "format.branch", defaultValue = "master")
    private String branch;
    @Parameter(readonly = true, defaultValue = "${project}")
    private MavenProject project;
    @Parameter(property = "format.use.specified.repositories", defaultValue = "false", required = false)
    private boolean useSpecifiedRepositories;
    @Parameter(property = "format.maven.repositories", defaultValue = "${project.repositories}", required = false)
    private List<Repository> mavenRepositories;


    public void execute() throws MojoExecutionException {

        List<File> sources = new ArrayList<>();

        List<String> mavenRepositoriesUrls = new ArrayList<>(mavenRepositories.size());

        for (Repository r: mavenRepositories) {
            mavenRepositoriesUrls.add(r.getUrl());
        }

        if (!skipSources) {
            sources.addAll(sourceDirectories);
        } else {
            getLog().warn("format.skipSources set, ignoring main directories");
        }

        if (!skipTestSources) {
            sources.addAll(testSourceDirectories);
        } else {
            getLog().warn("format.skipTestSources set, ignoring validateOnly directories");
        }
        if (sources.size() > 0) {
            try {

                Summary result = ScalaFormatter.apply(
                        configLocation,
                        getLog(),
                        respectVersion,
                        validateOnly,
                        onlyChangedFiles,
                        branch,
                        project.getBasedir(),
                        useSpecifiedRepositories,
                        mavenRepositoriesUrls
                ).format(sources);
                getLog().info(result.toString());
                if (validateOnly && result.unformattedFiles() != 0) {
                    throw new MojoExecutionException("Scalafmt: Unformatted files found");
                }
            } catch (Exception e) {
                getLog().error(e);
                throw new MojoExecutionException("Error formatting Scala files: " + e.getMessage(), e);
            }
        } else {
            getLog().warn("No sources specified, skipping formatting");
        }
    }
}
