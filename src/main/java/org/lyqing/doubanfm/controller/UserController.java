package org.lyqing.doubanfm.controller;

import org.lyqing.doubanfm.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    private Map<String, User> users = new HashMap<>();

    @GetMapping ("/sign")
    public String signPage(Model model) {
        return "/sign";
    }

    @PostMapping("/register")
    @ResponseBody
    public Map registerAction(
            @RequestParam("userId") String userId, @RequestParam("password") String password, @RequestParam("mobile") String mobile,
            HttpServletRequest request, HttpServletResponse response) {

        User user = new User(userId, password, mobile);
        Map result = new HashMap();
        result.put("userId", userId);
        result.put("password", password);
        result.put("mobile", mobile);
        result.put("message", "Sign successful");

        users.put(userId, user);

        return result;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @PostMapping("/authenticate")
    @ResponseBody
    public Map loginAction(
            @RequestParam("userId") String userId, @RequestParam("password") String password,
            HttpServletRequest request, HttpServletResponse response) {

        Map result = new HashMap();

        if (password.equals(users.get(userId).getPassword())) {
            result.put("userId", userId);
            result.put("message", "Login successful");
        } else {
            result.put("message", "Login error");
        }

        return result;
    }

}
