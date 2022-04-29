package com.example.se2project.controller;

import com.example.se2project.controller.user.MyUserDetails;
import com.example.se2project.entity.User;
import com.example.se2project.entity.dto.LoginRequestDto;
import com.example.se2project.service.AuthService;
import com.example.se2project.service.UserService;
import com.example.se2project.util.LogFactory;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping({"/login"})
@SessionAttributes({"email", "userId"})
public class LoginController {
    //    private final TokenProvider tokenProvider;
    @Autowired
    private AuthService authService;

    @Autowired
    private MyUserDetails myUserDetails;
    @Autowired
    UserService userService;
    private final Logger LOGGER = LogFactory.getLogger();

    @GetMapping
    public String login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginPage";
        }
        return "redirect:/";
//        model.addAttribute("loginRequestDto", new LoginRequestDto());
//        return "loginPage";
    }
    @PostMapping
    public String checkLogin(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             Model model) {
        User user = userService.findUserByEmailAndPassword(email, password);
        System.out.println("============================================================");
        System.out.println("author = " + myUserDetails.getAuthorities());
        if (Objects.isNull(user)) {
//            ObjectError objectError = new ObjectError("error", "Invalid email or password!");
//            bindingResult.addError(objectError);
            model.addAttribute("errorMessage", "Username or password is incorrect!!!");
            return "loginPage";
        } else {

            model.addAttribute("userId", user.getUserId());
            model.addAttribute("email", user.getEmail());

            LOGGER.info("Logged successfully!");

            return "redirect:/";
        }
    }
//    @PostMapping
//    public String checkLogin(@ModelAttribute @Valid LoginRequestDto loginRequestDto,
//                             BindingResult bindingResult,
//                             Model model){
//        if(bindingResult.hasErrors()){
//            return "loginPage";
//        }
//
//        User user = authService.existedUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
//        if(Objects.isNull(user)){
//            ObjectError objectError = new ObjectError("error", "Invalid email or password!");
//            bindingResult.addError(objectError);
//            return "loginPage";
//        }
//
//        model.addAttribute("userId", user.getUserId());
//        model.addAttribute("email", user.getEmail());
//
//        LOGGER.info("Logged successfully!");
//
//        return "redirect:/";
//    }

}