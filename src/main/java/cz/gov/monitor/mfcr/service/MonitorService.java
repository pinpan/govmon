package cz.gov.monitor.mfcr.service;

import cz.gov.monitor.mfcr.client.RestClient;
import cz.gov.monitor.mfcr.config.GovMonitorServerConfig;
import cz.gov.monitor.mfcr.model.FinancialReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonitorService {

    private Locale czechLocale = new Locale("CZ");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static ParameterizedTypeReference<FinancialReport[]> financialReportsTypeRef = new ParameterizedTypeReference<FinancialReport[]>() {};
    private static ParameterizedTypeReference<FinancialReport> financialReportTypeRef = new ParameterizedTypeReference<FinancialReport>() {};

    // ucetni-zaverka/2 obdobi=1909&ic=44992785"
    // private static Map<Type, ServiceDefinition> services = new HashMap();


    @Autowired
    private RestClient restClient;

    @Autowired
    private GovMonitorServerConfig govMonitorServerConfig;

    public FinancialReport fetchReport(String ico, String period) {

        StringBuilder sb = new StringBuilder(govMonitorServerConfig.serviceUrl("report"));

        Map<String, String> queryParamsMap = new HashMap();
        queryParamsMap.put("obdobi", period);
        queryParamsMap.put("ic", ico);
        sb = addQueryParams(sb, queryParamsMap);
        String serviceUrl = sb.toString();

        List<FinancialReport> financialReports = restClient.fetchFinancialReports(serviceUrl, financialReportTypeRef);
        // Here we expect just one report

        return ((financialReports != null) && (0 < financialReports.size()))
                ? financialReports.get(0) : null;
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
