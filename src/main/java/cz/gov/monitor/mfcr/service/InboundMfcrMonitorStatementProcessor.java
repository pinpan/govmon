package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.model.BalanceStatement;
import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("inboundProcessor")
public class InboundMfcrMonitorStatementProcessor implements MfcrMonitorStatementProcessor {

    @Autowired
    private MfcrMonitorDBService mfcrMonitorDBService;

    @Autowired
    private MfcrMonitorRESTService mfcrMonitorRESTService;

    @Override
    public void processReports(List<FinancialReport> financialReports, String ico, String period) {

        Organization organization = mfcrMonitorDBService.fetchOrganizationByICO(ico);
        if (organization == null) {
            organization = mfcrMonitorRESTService.fetchOrganizationByICO(ico);
        }

        for (FinancialReport financialReport : financialReports) {
            processReport(financialReport, ico, period);
        }
    }

    private void processReport(FinancialReport financialReport, String ico, String period, Organization organization) {
        Optional<FinancialReport> optional = mfcrMonitorDBService.findReportByQuery(ico, period);
        FinancialReport fr = (optional.isPresent()) ? optional.get() : null;
        if (fr == null) {
            fr = financialReport;

            fr.setOrganization(organization);
            fr.setPeriod(period);
            mfcrMonitorDBService.saveReport(fr);

            for (BalanceStatement statement: fr.getExpenses()) {
                statement.setReport(fr);
                mfcrMonitorDBService.saveStatement(statement);
            }

            for (BalanceStatement statement: fr.getRevenues()) {
                statement.setReport(fr);
                mfcrMonitorDBService.saveStatement(statement);
            }
        }
    }

    public void processReport(FinancialReport financialReport, String ico, String period) {
        Organization organization = mfcrMonitorDBService.fetchOrganizationByICO(ico);

        processReport(financialReport, ico, period, organization);
    }

    @Override
    public void processOrganization(Organization organization) {

    }
}
