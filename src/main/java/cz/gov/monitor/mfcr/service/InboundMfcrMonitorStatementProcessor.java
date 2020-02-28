package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.dao.MfcrMonitorDao;
import cz.gov.monitor.mfcr.model.FinancialReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InboundMfcrMonitorStatementProcessor implements MfcrMonitorStatementProcessor {

    @Autowired
    private MfcrMonitorDao mfcrMonitorDao;

    @Override
    public void processReports(List<FinancialReport> financialReports) {
        for (FinancialReport financialReport : financialReports) {
            Optional<FinancialReport> optional = mfcrMonitorDao.findById(financialReport.getId());
            FinancialReport fr = (optional.isPresent()) ? optional.get():null;
            if (fr == null) {
                mfcrMonitorDao.save(fr);
            }
        }
    }

}
