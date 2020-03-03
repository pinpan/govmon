package cz.gov.monitor.mfcr.dao;

import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.service.MfcrMonitorRESTService;
import cz.gov.monitor.mfcr.utils.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class GovMonServiceTest {

    @Autowired
    private MfcrMonitorRESTService monitorService;

    @Test
    public void testFetch() {
        Date dateFrom = new Date();
        Date theDayAfterTommorow = DateUtils.getTwoDaysLater(new Date());
        FinancialReport report = monitorService.fetchReport("44992785", "1909");

        assertNotNull(report);
        assertNotNull(report.getExpenses());
        assertNotNull(report.getExpenses().size() > 10);

    }
}
