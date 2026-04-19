package com.wizteco.configuration;

import com.wizteco.helloworld.service.BaiduSearchService;
import com.wizteco.helloworld.service.BaiduSearchService.BaiduSearchResult;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Redirects requests to the configured Baidu search endpoint.
 */
@Controller
public class BaiduController {

    private final BaiduSearchService baiduSearchService;

    public BaiduController(BaiduSearchService baiduSearchService) {
        this.baiduSearchService = baiduSearchService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/baidu?key=baidu";
    }

    @GetMapping("/baidu")
    public ResponseEntity<String> redirectToBaidu(
            @RequestParam(name = "key", required = false, defaultValue = "baidu") String key,
            @RequestParam(name = "keywords", required = false) String keywords) throws IOException {
        BaiduSearchResult result = baiduSearchService.prepareSearch(key, keywords);
        if (!result.isReady()) {
            return ResponseEntity.status(result.getStatus())
                    .body(result.getMessage());
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, result.getSearchUrl());
        return new ResponseEntity<String>(headers, HttpStatus.FOUND);
    }
}
