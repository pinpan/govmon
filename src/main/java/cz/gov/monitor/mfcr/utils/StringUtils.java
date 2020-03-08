package cz.gov.monitor.mfcr.utils;

import java.util.Iterator;
import java.util.Map;

public class StringUtils {

    public static final String[] EMPTY_STRING_ARRAY = new String[]{};

    public static StringBuilder addParams(StringBuilder sb, Map<String, String> paramsMap) {
        if (!paramsMap.isEmpty()) {
            sb.append("?");

            Iterator<Map.Entry<String, String>> it = paramsMap.entrySet().iterator();
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
