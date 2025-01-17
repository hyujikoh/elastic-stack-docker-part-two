package com.hyujikoh.ShortUrl.domain.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.tinylog.Logger;

/**
 * Author : hyujikoh.
 * CreatedAt : 2024-04-16.
 * Desc :
 */
@Controller
public class HomeController {
    @GetMapping("")
    public String homeMethod() {
        Logger.debug("homeMethod");
        return "index.html";
    }
}
