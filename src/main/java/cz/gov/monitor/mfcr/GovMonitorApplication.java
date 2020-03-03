package cz.gov.monitor.mfcr;

import cz.gov.monitor.mfcr.config.GovMonitorServerConfig;
import cz.gov.monitor.mfcr.utils.BuildInfo;
import cz.gov.monitor.mfcr.dao.ReportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties(GovMonitorServerConfig.class)
@EnableJpaRepositories(basePackageClasses={ReportDao.class})
public class GovMonitorApplication implements ApplicationListener<ApplicationReadyEvent> {
    private static final Logger logger = LoggerFactory.getLogger(GovMonitorApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(GovMonitorApplication.class);
        app.setLogStartupInfo(false);
        new BuildInfo().logBuildInfo();
        app.run(args);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onContextRefreshedEventEvent() {
        logger.info("onContextRefreshedEventEvent called...");
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        logger.info("onApplicationEvent called...");
    }
}
