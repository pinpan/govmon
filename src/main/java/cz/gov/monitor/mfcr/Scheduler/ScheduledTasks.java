package cz.gov.monitor.mfcr.Scheduler;

import cz.gov.monitor.mfcr.service.MfcrMonitorRESTService;
import cz.gov.monitor.mfcr.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static Date BEGINNING_OF_TIME = null;
    {

        try {
            BEGINNING_OF_TIME = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2019");;
        } catch (ParseException pe) {
            BEGINNING_OF_TIME = new Date();
        }
    }


    @Autowired
    MfcrMonitorRESTService service;


    @Autowired
    MfcrMonitorRESTService monitoringService;

    @PostConstruct
    public void onStartup() {
        cleanExpiredAtStartUp();
    }

    private void cleanExpiredAtStartUp() {

        Date dateFrom = BEGINNING_OF_TIME;
        Date dateTo = DateUtils.getFewDaysLater(new Date(), 30);
        log.info("Start-up clean check of expired reports. From %s, To: %s", dateFrom.toString(), dateTo.toString());

        //List<FinancialReport> vns = service.fetchReport(dateFrom, dateTo);
        //inboundReportsProcessor.processReports(vns);
    }
}