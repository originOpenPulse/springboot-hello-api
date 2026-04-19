package com.wizteco.configuration;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Redirect the project root to Baidu.
 */
@Controller
public class BaiduController {

    @RequestMapping(value = "/")
    public String index() {
        return "redirect:https://www.baidu.com";
    }
}
