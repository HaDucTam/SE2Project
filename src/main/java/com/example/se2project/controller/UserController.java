package com.example.se2project.controller;

import com.example.se2project.controller.user.MyUserDetails;
import com.example.se2project.entity.Category;
import com.example.se2project.entity.Order;
import com.example.se2project.entity.User;
import com.example.se2project.repository.UserRepository;
import com.example.se2project.service.OrderService;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderService orderService;
    @GetMapping
    public String viewPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
        model.addAttribute("userDetail", user);
        return "accountPages/userDashboard";
    }
    @GetMapping("/uploadImage")
    public String uploadImage(@RequestParam(value = "userImage", required = false) MultipartFile userImage) {
        String fileName = StringUtils.cleanPath(userImage.getOriginalFilename());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
        user.setImage(fileName);
        return "/adminPages/categories/categoryAdd";
    }
    @GetMapping("/updatePage")
    public String viewUpdatePage(@AuthenticationPrincipal MyUserDetails loggedUser, Model model) {
            String email = loggedUser.getUsername();
            User user = userService.getUserByEmail(email);
            model.addAttribute("user", user);
            return "accountPages/profileUpdate";
    }
    @GetMapping("/order")
    public String viewOrderPage(@AuthenticationPrincipal MyUserDetails loggedUser, Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
//        String username = user1.getUsername();
//        User user = userService.getUserByEmail(username);
//        model.addAttribute("userDetail", user);
        User user = getUserFromSession();
        List<Order> order = (List<Order>) orderService.getOrderByUser(user);

        model.addAttribute("order", order);
        return "accountPages/orderPage";
    }
    public User getUserFromSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
        return user;
    }

    @PostMapping("/updateProfileByUser")
    public String updateProfile(User user, RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal MyUserDetails logedUser
                                ) throws IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
//        String username = user1.getUsername();
//        User u = userService.getUserByEmail(username);

//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String rawPassword = user.getPassword();
//        String encode = encoder.encode(rawPassword);
//        user.setPassword(encode);
        user.setPassword(logedUser.getPassword());
        userRepository.save(user);

        logedUser.setFirstName(user.getFirstName());
        logedUser.setLastName(user.getLastName());
        logedUser.setPhoneNumber(user.getPhoneNumber());
        logedUser.setAddress(user.getAddress());
//        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "Your account have been updated !!!");

        return "redirect:/user/updatePage";
    }

}