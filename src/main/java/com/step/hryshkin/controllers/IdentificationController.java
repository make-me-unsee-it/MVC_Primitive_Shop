package com.step.hryshkin.controllers;

import com.step.hryshkin.utils.UtilsForOnlineShop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alekx
 */

@Controller
public class IdentificationController {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        UtilsForOnlineShop.stopShopping(request);
        return "identification/login";
    }

    @GetMapping()
    public String indexPage() {
        return "redirect:/login";
    }
}
