package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.client.RestClient;
import cz.gov.monitor.mfcr.config.GovMonitorServerConfig;
import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.model.FiscalPeriod;
import cz.gov.monitor.mfcr.model.Organization;
import cz.gov.monitor.mfcr.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MfcrMonitorRESTService {

    private static ParameterizedTypeReference<FinancialReport[]> financialReportsTypeRef = new ParameterizedTypeReference<FinancialReport[]>() {};
    private static ParameterizedTypeReference<FinancialReport> financialReportTypeRef = new ParameterizedTypeReference<FinancialReport>() {};
    private static ParameterizedTypeReference<Organization> organizationTypeRef = new ParameterizedTypeReference<Organization>() {};
    private static ParameterizedTypeReference<FiscalPeriod> fiscalPeriodTypeRef = new ParameterizedTypeReference<FiscalPeriod>() {};
    private static ParameterizedTypeReference<List<FiscalPeriod>> fiscalPeriodsListTypeRef = new ParameterizedTypeReference<List<FiscalPeriod>>() {};

    @Autowired
    @Qualifier("inboundProcessor")
    private MfcrMonitorStatementProcessor inboundProcessor;

    @Autowired
    private RestClient restClient;

    @Autowired
    private GovMonitorServerConfig govMonitorServerConfig;

    public FinancialReport fetchReport(String ico, String period) {

        StringBuilder sb = new StringBuilder(govMonitorServerConfig.serviceUrl("all_statements"));

        Map<String, String> queryParamsMap = new HashMap();
        queryParamsMap.put("obdobi", period);
        queryParamsMap.put("ic", ico);
        sb = StringUtils.addParams(sb, queryParamsMap);
        String serviceUrl = sb.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "cs");
        headers.set("Accept", "application/json, text/plain, */*");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity(null, headers);

        FinancialReport financialReport = restClient.fetchResource(serviceUrl, httpEntity, financialReportTypeRef);
        // Here we expect just one report

        if (financialReport != null) {
            inboundProcessor.processReport(financialReport, ico, period);
        }

        return financialReport;
    }

    public Organization fetchOrganizationByICO(String ico) {
        StringBuilder sb = new StringBuilder(govMonitorServerConfig.serviceUrl("organization"));

        sb.append("/").append(ico);
        String serviceUrl = sb.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "cs");
        headers.set("Accept", "application/json, text/plain, */*");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity(null, headers);

        Organization organization = restClient.fetchResource(serviceUrl, httpEntity, organizationTypeRef);
        // Here we expect just one report

        if (organization != null) {
            inboundProcessor.processOrganization(organization);
        }

        return organization;
    }

    public Map<Integer, FinancialReport> fetchOrganizationReports(String ico) {


        // 1. Fetch all fiscal periods and forEach fetch corresponding financial report
        String serviceUrl = govMonitorServerConfig.serviceUrl("fiscal_period");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "cs");
        headers.set("Accept", "application/json, text/plain, */*");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity(null, headers);

        List<FiscalPeriod> fiscalPeriods = restClient.fetchResourcesList(serviceUrl, httpEntity, fiscalPeriodsListTypeRef);
        Map<Integer, FinancialReport> financialReports = new HashMap<Integer, FinancialReport>();
        if ((fiscalPeriods != null) && !fiscalPeriods.isEmpty()) {


            for (FiscalPeriod period : fiscalPeriods) {
                FinancialReport repoet = fetchReport(ico, period.getLoadID().toString());
                // If a report is null or empty, skip it or upon configuration dispaly it as empry json,
                // so it will be obvious that it is missing
                financialReports.put(period.getLoadID(), repoet);
            }
        }
        return financialReports;
   }

    /*public Map<Integer, FinancialReport> fetchOrganizatzionReports(String ico) {
        return null;
    }*/
}
