package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.dao.BalanceStatementDao;
import cz.gov.monitor.mfcr.dao.FinancialReportDao;
import cz.gov.monitor.mfcr.dao.FiscalPeriodDao;
import cz.gov.monitor.mfcr.dao.OrganizationDao;
import cz.gov.monitor.mfcr.model.BalanceStatement;
import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.model.FiscalPeriod;
import cz.gov.monitor.mfcr.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MfcrMonitorDBService {

    @Autowired
    private FinancialReportDao reportDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private BalanceStatementDao balanceStatementDao;


    @Autowired
    private FiscalPeriodDao fiscalPeriodDao;


    public Optional<FinancialReport> findReportByQuery(String ico, String period) {
        FinancialReport report = reportDao.findReportByQuery(ico, period);
        return Optional.ofNullable(report);
    }

    public Iterable<FiscalPeriod> fetchAlFiscalPeriods() {
        return fiscalPeriodDao.findAll();
    }


    public Iterable<FinancialReport> fetchAllReports() {
        return reportDao.findAll();
    }

    public List<FinancialReport> fetchAllOrganizationReports(Organization organization) {
        return reportDao.getAllOrganizationReports(organization);
    }

    public void saveReports(List<FinancialReport> reports) {
        reportDao.saveAll(reports);
    }

    public void saveReport(FinancialReport report) {
        reportDao.save(report);
    }


    public Organization fetchOrganizationByICO(String ico) {
        return organizationDao.fetchOrganizationByICO(ico);
    }

    public void saveOrganization(Organization organization) {
        organizationDao.save(organization);
    }

    public void saveStatement(BalanceStatement statement) {
        balanceStatementDao.save(statement);
    }

    public void saFiscalPEriods(Iterable<FiscalPeriod> fiscalPeriods) {

    }
}
