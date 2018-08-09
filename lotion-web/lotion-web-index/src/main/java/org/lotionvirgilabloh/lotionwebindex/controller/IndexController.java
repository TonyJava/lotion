package org.lotionvirgilabloh.lotionwebindex.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    /**
     * 静态资源地址
     */
    @Value("${custom.web.static.url}")
    private String webStaticUrl;
    /**
     * host地址
     */
    @Value("${custom.web.host.url}")
    private String webHostUrl;

    @RequestMapping({"/", "/index"})
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "index";
    }

    @RequestMapping("/indexframe")
    public String indexframe(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "indexframe";
    }
}
