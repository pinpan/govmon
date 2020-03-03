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

        Organization organization = mfcrMonitorDBService.getOrganizationByICO(ico);
        if (organization == null) {
            organization = mfcrMonitorRESTService.fetchOrganization(ico);
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
        }
        fr.setOrganization(organization);
        fr.setPeriod(period);
        for (BalanceStatement expense: fr.getExpenses()) {
            expense.setReport(fr);
        }

        mfcrMonitorDBService.saveReport(fr);
    }

    public void processReport(FinancialReport financialReport, String ico, String period) {
        Organization organization = mfcrMonitorDBService.getOrganizationByICO(ico);

        processReport(financialReport, ico, period, organization);
    }

    @Override
    public void processOrganization(Organization organization) {

    }

}
