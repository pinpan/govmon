package cz.gov.monitor.mfcr.dao;

import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.service.MonitorService;
import cz.gov.monitor.mfcr.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class GovMonServiceTest {

    @Autowired
    private MonitorService monitorService;

    @Test
    public void testFetch() {
        Date dateFrom = new Date();
        Date theDayAfterTommorow = DateUtils.getTwoDaysLater(new Date());
        List<FinancialReport> reports = monitorService.fetchReports("44992785", "1909");

    }
}
