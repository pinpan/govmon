package cz.gov.monitor.mfcr.utils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class BuildInfo {

    public void logBuildInfo() {
        BuildInfoDetails buildInfo = getBuildInfoDetails();
        log.info("Service {} version {} #{} on {} by {} commit {}", buildInfo.getArtifact(), buildInfo.getVersion(), buildInfo.getNumber(),
            buildInfo.getTime(), buildInfo.getMachine(), buildInfo.getCommitId());
    }

    public BuildInfoDetails getBuildInfoDetails() {
        Properties build = getProperties("META-INF/build-info.properties");
        Properties git = getProperties("META-INF/git.properties");
        return new BuildInfoDetails(build, git);
    }

    private Properties getProperties(String path) {
        // Create the Properties
        Properties props = new Properties();

        // Create the input streams
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(path)) {
            if (input == null) {
                return props;
            }

            props.load(input);
        } catch (IOException ioe) {
            log.error("Proprties were not found on provided path: %s", path, ioe.toString());
        }

        return props;
    }
}
