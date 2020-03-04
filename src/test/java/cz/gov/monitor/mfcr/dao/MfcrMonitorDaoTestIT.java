package cz.gov.monitor.mfcr.dao;

import java.util.Optional;

import cz.gov.monitor.mfcr.model.FinancialReport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Attila Cseh on 25/01/2020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
public class MfcrMonitorDaoTestIT {

	@Autowired
	private FinancialReportDao mfcrMonitorDao;

	@Test
	public void testFindById() {
		final Optional<FinancialReport> byId = mfcrMonitorDao.findById(1L);
		System.out.println(byId);
	}
}
