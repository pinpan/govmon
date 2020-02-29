package cz.gov.monitor.mfcr.client;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.*;

import cz.gov.monitor.mfcr.config.ServiceDefinition;
import cz.gov.monitor.mfcr.model.FinancialReport;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;

@Service
public class RestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private static RestTemplate restTemplate = new RestTemplate();
    static {
        restTemplate.setRequestFactory(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
                if (connection instanceof HttpsURLConnection) {
                    ((HttpsURLConnection) connection).setHostnameVerifier(new NoopHostnameVerifier());
                }
                super.prepareConnection(connection, httpMethod);
            }
        });
    }

    /**
     * Find FinancialReport
     *
     * @param serviceBaseUrl
     * @return
     */
    public List<FinancialReport> fetchFinancialReports(String serviceBaseUrl, ParameterizedTypeReference typeRef) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "cs");
        headers.set("Accept", "application/json, text/plain, */*");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity(null, headers);


        List<FinancialReport> response = fetchFinancialReports(serviceBaseUrl, httpEntity, typeRef);

        return response;
    }


    public List fetchFinancialReports(String searchUrl, HttpEntity<HttpHeaders> httpEntity, ParameterizedTypeReference typeRef) {
        LOGGER.info("Fetch Financial Reports from MFCR endpoint: %s", searchUrl);

        ResponseEntity response = restTemplate.exchange(searchUrl, HttpMethod.GET, httpEntity, typeRef);

        List reports = null;
        if (HttpStatus.OK.equals(response.getStatusCode())) {

            if (response.getBody() != null) {

                // Copy into new list to avoid access issues
                reports = Arrays.asList(response.getBody());
                LOGGER.info("Reports found: " + reports.size());
            } else {
                LOGGER.info("No reports found.");
            }
        } else {
            LOGGER.info("Couldn't find place data. Geonames /search service returned Http status code: " + response.getStatusCode());
        }

        if (reports == null) {
            reports = new ArrayList();
        }

        return reports;
    }

    protected static String getHttpQueryStringFromParams(Map<String, String> queryParams) {
        if ((queryParams == null) || (queryParams.size() == 0)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Iterator keyIt = queryParams.keySet().iterator();

        while (keyIt.hasNext()) {
            String key = (String) keyIt.next();
            String value = queryParams.get(key);
            sb.append(key).append("=").append(value);
            if (keyIt.hasNext()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    protected static void setHeadersFromMap(HttpHeaders headers, Map<String, String> headerParams) {
        Iterator keyIt = headerParams.keySet().iterator();
        while (keyIt.hasNext()) {
            String key = (String) keyIt.next();
            String value = headerParams.get(key);
            headers.add(key, value);
        }
    }
}
