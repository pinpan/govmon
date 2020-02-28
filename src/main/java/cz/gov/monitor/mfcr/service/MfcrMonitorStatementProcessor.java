package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.model.FinancialReport;

import java.util.List;

public interface MfcrMonitorStatementProcessor {
    /**
     * Default processing of a List of reports
     */
    public void processReports(List<FinancialReport> reports);
}
