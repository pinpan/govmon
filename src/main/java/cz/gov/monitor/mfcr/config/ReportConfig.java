package cz.gov.monitor.mfcr.config;


import lombok.Data;

@Data
public class ReportConfig {
    String path;
    String[] filterCodes;
}
