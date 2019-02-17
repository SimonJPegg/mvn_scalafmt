/**
 *    Copyright (C) 2019 Antipathy.org <support@antipathy.org>
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.antipathy.scoozie.maven;

import org.antipathy.scoozie.GeneratedArtefacts;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.List;
import org.antipathy.scoozie.Scoozie;

/**
 * Build oozie artefacts
 */
@Mojo(name = "scoozie")
public class ScoozieMojo extends AbstractMojo {

    @Parameter(property = "scoozie.artefacts", required = true)
    private List<Artefact> artefacts;

    @Parameter(defaultValue = "${project.build.directory}", required = true)
    private File buildDirectory;

    /**
     * Run the Mojo
     */
    public void execute() throws MojoExecutionException {
        try {
            artefacts.forEach(this::processArtefact);
        } catch (Exception e) {
            throw new MojoExecutionException("Error generating oozie artefacts", e);
        }
    }

    /**
     * Process an artefact and save it to disc
     * @param artefact the artefact to process
     */
    private void processArtefact(Artefact artefact) {
        GeneratedArtefacts generatedArtefact = Scoozie.fromConfig(artefact.getConfigFile().toPath());
        if (artefact.getOutputDirectory() == null) {
            artefact.setOutputDirectory(buildDirectory);
        }
        generatedArtefact.save(artefact.buildOutputDirectory(),artefact.isSaveAsZip());
    }

}
