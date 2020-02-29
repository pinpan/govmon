package cz.gov.monitor.mfcr.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Data
@Configuration
@ComponentScan({"cz.gov.monitor"})
@ConfigurationProperties(prefix = "monitor.government.mfcr")
public class GovMonitorServerConfig {

    //@Autowired
    /*
        private Server server;

        private String scheme;
        private String host;
        private String port;
        private String path;
    */

    private HashMap<String, String> server;
    private HashMap<String, String> paths;

    public String serviceUrl(String service) {
        StringBuilder sb = new StringBuilder(serverUrl());
        sb.append(getPaths().get(service));

        return sb.toString();
    }

    public String serverUrl() {
        StringBuilder builder = new StringBuilder();
            builder.append(server.get("scheme"))
                .append("://")
                .append(server.get("host"));
            String port = server.get("port");
            if ((port != null) && !port.trim().isEmpty()){
                builder.append(":")
                       .append(server.get("port"));
            }
            builder.append(server.get("path"));

            return builder.toString();
    }
}
