package cz.gov.monitor.mfcr.client;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.*;

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
    public List<FinancialReport> fetchResourcesList(String serviceBaseUrl, ParameterizedTypeReference typeRef) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept-Language", "cs");
        headers.set("Accept", "application/json, text/plain, */*");
        HttpEntity<HttpHeaders> httpEntity = new HttpEntity(null, headers);


        List<FinancialReport> response = fetchResourcesList(serviceBaseUrl, httpEntity, typeRef);

        return response;
    }


    public <T> List<T> fetchResourcesList(String searchUrl, HttpEntity<HttpHeaders> httpEntity, ParameterizedTypeReference<List<T>> typeRef) {
        LOGGER.info("Fetch Resources of type %s from endpoint: %s", typeRef.toString(), searchUrl);

        ResponseEntity<List<T>> response = restTemplate.exchange(searchUrl, HttpMethod.GET, httpEntity, typeRef);

        List<T> resources = null;
        if (HttpStatus.OK.equals(response.getStatusCode())) {

            if (response.getBody() != null) {

                // Copy into new list to avoid access issues
                resources = response.getBody();
                LOGGER.info("Reports found: " + resources.size());
            } else {
                LOGGER.info("No reports found.");
            }
        } else {
            LOGGER.info("Couldn't find data. REST service returned Http status code: " + response.getStatusCode());
        }

        if (resources == null) {
            resources = new ArrayList();
        }

        return resources;
    }

    public <T> T fetchResource(String searchUrl, HttpEntity<HttpHeaders> httpEntity, ParameterizedTypeReference<T> typeRef) {
        LOGGER.info("Fetch Resource of type %s from endpoint: %s", typeRef.toString(), searchUrl);

        ResponseEntity<T> response = restTemplate.exchange(searchUrl, HttpMethod.GET, httpEntity, typeRef);

        if (HttpStatus.OK.equals(response.getStatusCode())) {

            if (response.getBody() != null) {

                // Copy into new list to avoid access issues
                LOGGER.info("Resource found: " + response.getBody());
            } else {
                LOGGER.info("No resource of type %s found.", typeRef.toString());
            }
        } else {
            LOGGER.info("Couldn't find data. REST service returned Http status code: " + response.getStatusCode());
        }

        return response.getBody();
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
