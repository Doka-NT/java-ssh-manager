package com.skobka.sshmanager.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Soshnikov Artem <213036@skobka.com>
 */

public class Loader {
    private String configFile = File.separator + ".sshManager.config.json";
    @SuppressWarnings("FieldCanBeLocal")
    private String configFileDist = "/config-dist.json";

    public Config load() throws IOException {
        String dir = System.getProperty("user.home");
        File f = new File(dir + this.configFile);
        if (!f.exists()) {
            InputStream inputStream = getClass().getResourceAsStream(this.configFileDist);
            File newConfigFile = new File(dir + this.configFile);
            //noinspection ResultOfMethodCallIgnored
            newConfigFile.createNewFile();
            FileUtils.writeStringToFile(newConfigFile, IOUtils.toString(inputStream));
        }

        Config config = getConfiguration(new File(dir + configFile));
        config.setConfigFile(this.configFile);
        config.setLoader(this);

        return config;
    }

    public String getConfigFile() {
        return configFile;
    }

    private Config getConfiguration(File configFile) throws IOException {
        String jsonString = FileUtils.readFileToString(configFile);
        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(jsonString, Config.class);
    }
}
