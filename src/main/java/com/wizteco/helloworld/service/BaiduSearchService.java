package com.wizteco.helloworld.service;

import com.wizteco.helloworld.config.BaiduSearchProperties;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.io.UnsupportedEncodingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Handles Baidu availability checks and builds keyword-based search requests.
 */
@Service
public class BaiduSearchService {

    private final BaiduSearchProperties properties;

    public BaiduSearchService(BaiduSearchProperties properties) {
        this.properties = properties;
    }

    public BaiduSearchResult prepareSearch(String key, String keywords) throws IOException {
        if (!matchesProvider(key)) {
            return BaiduSearchResult.failure(
                    HttpStatus.BAD_REQUEST,
                    "Unsupported search key: " + key + ". Expected key: " + properties.getKey());
        }

        int statusCode = probeBaidu();
        if (!isAccessible(statusCode)) {
            return BaiduSearchResult.failure(
                    HttpStatus.BAD_GATEWAY,
                    "Baidu is unavailable, upstream status code: " + statusCode);
        }

        String searchUrl = buildSearchUrl(keywords);
        return BaiduSearchResult.success(statusCode, searchUrl);
    }

    private boolean matchesProvider(String key) {
        return StringUtils.hasText(key)
                && key.toLowerCase().contains(properties.getKey().toLowerCase());
    }

    private int probeBaidu() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(properties.getHomeUrl()).openConnection();
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(properties.getConnectTimeoutMs());
        connection.setReadTimeout(properties.getReadTimeoutMs());
        connection.setRequestProperty("User-Agent", "springboot-hello-api/1.0");
        try {
            return connection.getResponseCode();
        } finally {
            connection.disconnect();
        }
    }

    private boolean isAccessible(int statusCode) {
        return (statusCode >= 200 && statusCode < 400);
    }

    private String buildSearchUrl(String keywords) {
        if (!StringUtils.hasText(keywords)) {
            return properties.getHomeUrl();
        }

        String encodedKeywords = encodeKeywords(keywords);
        return properties.getSearchUrl() + "?" + properties.getQueryParam() + "=" + encodedKeywords;
    }

    private String encodeKeywords(String keywords) {
        try {
            return URLEncoder.encode(keywords, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalStateException("UTF-8 encoding is not supported.", ex);
        }
    }

    public static class BaiduSearchResult {

        private final boolean ready;
        private final HttpStatus status;
        private final String message;
        private final String searchUrl;

        private BaiduSearchResult(boolean ready, HttpStatus status, String message, String searchUrl) {
            this.ready = ready;
            this.status = status;
            this.message = message;
            this.searchUrl = searchUrl;
        }

        public static BaiduSearchResult success(int upstreamStatus, String searchUrl) {
            return new BaiduSearchResult(
                    true,
                    HttpStatus.resolve(upstreamStatus) == null ? HttpStatus.OK : HttpStatus.resolve(upstreamStatus),
                    "Baidu is reachable.",
                    searchUrl);
        }

        public static BaiduSearchResult failure(HttpStatus status, String message) {
            return new BaiduSearchResult(false, status, message, null);
        }

        public boolean isReady() {
            return ready;
        }

        public HttpStatus getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }

        public String getSearchUrl() {
            return searchUrl;
        }
    }
}
