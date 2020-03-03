package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.model.Organization;

import java.util.List;

public interface MfcrMonitorStatementProcessor {
    /**
     * Default processing of a List of reports
     */
    public void processReports(List<FinancialReport> reports, String ico, String period);

    /**
     * Default processing of a List of reports
     */
    public void processReport(FinancialReport report, String ico, String period);


    void processOrganization(Organization organization);
}
