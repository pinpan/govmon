package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.client.RestClient;
import cz.gov.monitor.mfcr.config.GovMonitorServerConfig;
import cz.gov.monitor.mfcr.model.FinancialReport;
import cz.gov.monitor.mfcr.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MfcrMonitorRESTService {

    private Locale czechLocale = new Locale("CZ");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ParameterizedTypeReference<FinancialReport[]> financialReportsTypeRef = new ParameterizedTypeReference<FinancialReport[]>() {};
    private static ParameterizedTypeReference<FinancialReport> financialReportTypeRef = new ParameterizedTypeReference<FinancialReport>() {};
    private static ParameterizedTypeReference<Organization> organizationTypeRef = new ParameterizedTypeReference<Organization>() {};

    // ucetni-zaverka/2 obdobi=1909&ic=44992785"
    // private static Map<Type, ServiceDefinition> services = new HashMap();

    @Autowired
    @Qualifier("inboundProcessor")
    private MfcrMonitorStatementProcessor inboundProcessor;

    @Autowired
    private RestClient restClient;

    @Autowired
    private GovMonitorServerConfig govMonitorServerConfig;

    public FinancialReport fetchReport(String ico, String period) {

        StringBuilder sb = new StringBuilder(govMonitorServerConfig.serviceUrl("all-statements"));

        Map<String, String> queryParamsMap = new HashMap();
        queryParamsMap.put("obdobi", period);
        queryParamsMap.put("ic", ico);
        sb = addQueryParams(sb, queryParamsMap);
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

    public Organization fetchOrganization(String ico) {

        StringBuilder sb = new StringBuilder(govMonitorServerConfig.serviceUrl("organization"));

        //Map<String, String> queryParamsMap = new HashMap();
        //queryParamsMap.put("ic", ico);
        //sb = addQueryParams(sb, queryParamsMap);
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

    private static StringBuilder addQueryParams(StringBuilder sb, Map<String, String> queryParamsMap) {
        if (!queryParamsMap.isEmpty()) {
            sb.append("?");

            Iterator<Map.Entry<String, String>> it = queryParamsMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                sb.append(entry.getKey()).append("=").append(entry.getValue());
                if (it.hasNext()) {
                    sb.append("&");
                }
            }
        }
        return sb;
    }
}
