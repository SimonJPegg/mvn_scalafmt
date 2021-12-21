package org.antipathy.mvn_scalafmt;

import org.antipathy.mvn_scalafmt.model.Summary;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
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
@Mojo(name = "format", defaultPhase = LifecyclePhase.VALIDATE)
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
    @Parameter(property = "format.validateOnly", defaultValue = "false")
    private boolean validateOnly;
    @Parameter(property = "format.onlyChangedFiles", defaultValue = "false")
    private boolean onlyChangedFiles;
    @Parameter(property = "format.showReformattedOnly", defaultValue = "false")
    private boolean showReformattedOnly;
    /** if branch.startsWith(": "), ex set in pom.xml:
      * <pre>{@code
      * <!-- the current branch-->
      * <branch>: git rev-parse --abbrev-ref HEAD</branch>
      * }</pre>
      * then we consider the value in {@code <branch>} tag as a command to run and the output will be used as the actual branch */
    @Parameter(property = "format.branch", defaultValue = "master")
    private String branch;
    @Parameter(readonly = true, defaultValue = "${project}")
    private MavenProject project;
    @Parameter(property = "format.useSpecifiedRepositories", defaultValue = "false")
    private boolean useSpecifiedRepositories;
    @Parameter(readonly = true, defaultValue = "${project.repositories}")
    private List<Repository> mavenRepositories;

    private List<String> getRepositoriesUrls(List<Repository> repositories) {
        ArrayList<String> urls = new ArrayList<>();
        for (Repository repository : repositories) {
            urls.add(repository.getUrl());
        }
        return urls;
    }

    public void execute() throws MojoExecutionException {

        List<File> sources = new ArrayList<>();

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
        if (!sources.isEmpty()) {
            try {

                Summary result = ScalaFormatter.apply(
                        configLocation,
                        getLog(),
                        validateOnly,
                        onlyChangedFiles,
                        showReformattedOnly,
                        branch,
                        project.getBasedir(),
                        useSpecifiedRepositories ? getRepositoriesUrls(mavenRepositories) : new ArrayList<String>()
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
