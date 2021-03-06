package org.lotionvirgilabloh.lotionwebindex.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    /**
     * 静态资源地址
     */
    @Value("${lotion.web.static.url}")
    private String webStaticUrl;
    /**
     * host地址
     */
    @Value("${lotion.web.host.url}")
    private String webHostUrl;

    @Value("${lotion.web.this.url}")
    private String thisUrl;

    @RequestMapping({"/", "/index"})
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "index";
    }

    @RequestMapping("/idx")
    public String indexframe(ModelMap modelMap) {
        modelMap.addAttribute("webStaticUrl", webStaticUrl);
        modelMap.addAttribute("webHostUrl", webHostUrl);
        return "indexframe";
    }

    @RequestMapping(value = "/getWebStaticUrl", method = RequestMethod.POST)
    public @ResponseBody String requestWebStaticUrl() {
        return webStaticUrl;
    }

    @RequestMapping(value = "/getWebHostUrl", method = RequestMethod.POST)
    public @ResponseBody String requestWebHostUrl() {
        return webHostUrl;
    }


}
