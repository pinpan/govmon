package cz.gov.monitor.mfcr.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ComponentScan({"cz.gov.monitor"})
@ConfigurationProperties(prefix = "monitor.government.mfcr.server")
public class GovMonitorServerConfig {

    @Autowired
    private Server server;

    private String scheme;
    private String host;
    private String port;
    private String path;

    public String getUrl() {
        StringBuilder builder = new StringBuilder();
            builder.append(server.getScheme())
                .append("://")
                .append(server.getHost());
            if (server.getPort() != null) {
                builder.append(":")
                       .append(server.getPort());
            }
            builder.append("/")
                   .append(server.getPath());

            return builder.toString();
    }
}
