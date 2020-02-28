package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.dao.MfcrMonitorDao;
import cz.gov.monitor.mfcr.model.FinancialReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MfcrMonitorService {

    @Autowired
    private MfcrMonitorDao mfcrMonitorDao;

    public Iterable<FinancialReport> fetchAll() {

        return mfcrMonitorDao.findAll();
    }

    public void saveReports(List<FinancialReport> reports) {
        mfcrMonitorDao.saveAll(reports);
    }
}
