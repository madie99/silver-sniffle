package com.example.BlogApplication.Controller;

import com.example.BlogApplication.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.BlogApplication.Service.UserService;


@Controller
public class LoginController {
    private final UserService userService;
    @Autowired
    public LoginController(UserService userService) {
        this.userService=userService;
    }
    @GetMapping("/showMyLoginPage")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/access_denied")
    public String showAccessDenied() {
        return "access_denied";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute("user") User user,Model model) {
        userService.save(user);
        return "login";
    }

    @GetMapping("/open_signup")
    public String open_signup(Model model){
        User user=new User();
        model.addAttribute("user",user);
        return "signup";
    }
}
