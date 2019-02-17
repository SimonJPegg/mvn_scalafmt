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

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.antipathy.scoozie.builder.HoconConstants;

/**
 * Model class for config to the plugin
 */
public class Artefact {

    private File configFile;
    private boolean saveAsZip;
    private File outputDirectory;
    private Config config = null;

    public Artefact() {}


    public Artefact(File configFile, boolean saveAsZip, File outputDirectory) {
        this.configFile = configFile;
        this.saveAsZip = saveAsZip;
        this.outputDirectory = outputDirectory;
    }

    /**
     * getter method for configFile
     */
    protected File getConfigFile() {
        return configFile;
    }

    /**
     * getter method for saveAsZip
     */
    protected boolean isSaveAsZip() {
        return saveAsZip;
    }

    /**
     * private method for loading the config file for an artefact
     */
    private Config getConfig() {
        if (this.config == null) {
            this.config = ConfigFactory.parseFile(this.getConfigFile()).resolve();
        }
        return this.config;
    }

    /**
     * getter method for outputDirectory
     */
    protected File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * setter method for outputDirectory
     */
    protected void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    /**
     * setter method for outputDirectory
     */
    protected Path buildOutputDirectory() {
        StringBuilder outputDir = new StringBuilder();

        Config c = getConfig();

        String coordinatorPath = HoconConstants.coordinator() +"." + HoconConstants.name();
        String workflowPath = HoconConstants.workflow() +"." + HoconConstants.name();

        outputDir.append(this.outputDirectory.toString()).append(File.separator);

        if (c.hasPath(coordinatorPath)) {
            outputDir.append(c.getString(coordinatorPath)).append(File.separator);
        } else {
            outputDir.append(c.getString(workflowPath)).append(File.separator);
        }
        return Paths.get(outputDir.toString());
    }
}
