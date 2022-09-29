package com.step.hryshkin.controllers;

import com.step.hryshkin.model.User;
import com.step.hryshkin.service.OrderService;
import com.step.hryshkin.service.UserService;
import com.step.hryshkin.utils.UtilsForOnlineShop;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Alekx
 */

@Controller
public class IdentificationController {

    @Inject
    private UserService userService;


    //TODO ВЫПИЛИТЬ
    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        UtilsForOnlineShop.stopShopping(request);
        return "redirect:/login";
    }

    @GetMapping()
    public String indexPage(HttpServletRequest request) {
        return "redirect:/homepage";
    }

    @GetMapping("/homepage")
    public String homePage(HttpServletRequest request) {
        return "identification/homepage";
    }

    @GetMapping("/new")
    public String accountCreatingPage() {
        return "identification/new";
    }

    @GetMapping("/new_success")
    public String creatingNewUserAccount(Model model, ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        checkUser((HttpServletRequest) servletRequest);
        if (!checkFlag(request)) return "errors/403";
        model.addAttribute("user", request.getParameter("username"));

        return "identification/new_success";
    }

    private void checkUser(HttpServletRequest request) {
        String login = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(login, password);
        if ((login != null) & (password != null)) {
            if (userService.getUserByName(login).isEmpty()) {
                userService.createNewUser(user);
            }
        }
    }

    private boolean checkFlag(HttpServletRequest request) {
        return request.getParameter("check") != null;
    }
}
