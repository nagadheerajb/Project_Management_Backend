package fs19.java.backend.config;

import fs19.java.backend.presentation.shared.exception.InvalidInvitationFoundException;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class URLParameterExtractor {
    public static Map<String, String> extractParameters(String url) {
        Map<String, String> paramMap = new HashMap<>();
        try {
            String query = url.split("\\?")[1]; // Get the query string
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                String key = URLDecoder.decode(keyValue[0], "UTF-8");
                String value = URLDecoder.decode(keyValue[1], "UTF-8");
                paramMap.put(key, value);
            }
        } catch (Exception e) {
            throw new InvalidInvitationFoundException(e.getMessage() + ": Invalid Invitation URL");
        }
        return paramMap;
    }
}
