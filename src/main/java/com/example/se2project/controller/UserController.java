package com.example.se2project.controller;

import com.example.se2project.controller.user.MyUserDetails;
import com.example.se2project.entity.Order;
import com.example.se2project.entity.Payment;
import com.example.se2project.entity.User;
import com.example.se2project.repository.PaymentRepository;
import com.example.se2project.repository.UserRepository;
import com.example.se2project.service.OrderService;
import com.example.se2project.service.PaymentService;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
@RequestMapping({"/user"})
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderService orderService;
    @Autowired
    PaymentRepository paymentRepository;
    @GetMapping
    public String viewPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
        model.addAttribute("userDetail", user);
        return "accountPages/userDashboard";
    }
    @GetMapping("/update-profile")
    public String viewUpdatePage(@AuthenticationPrincipal MyUserDetails loggedUser, Model model) {
        String email = loggedUser.getUsername();
        User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);
        return "accountPages/profileUpdate";
    }
    @GetMapping("/my-order")
    public String viewOrderPage(@AuthenticationPrincipal MyUserDetails loggedUser, Model model) {
        User user = getUserFromSession();
        List<Order> order = orderService.getOrderByUser(user);

        model.addAttribute("myOrder", order);
        return "accountPages/orderList";
    }
    public User getUserFromSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user1 = (MyUserDetails) authentication.getPrincipal();
        String username = user1.getUsername();
        User user = userService.getUserByEmail(username);
        return user;
    }
    @GetMapping("/addPayment")
    public String addPaymentMethod(Model model) {
        model.addAttribute("payment", new Payment());
        return "accountPages/paymentAdd";
    }
    @PostMapping("/addPayment/save")
    public String savePaymentMethod(@RequestParam(value = "bank", defaultValue = "empty bank") String bank,
                                    @RequestParam(value = "cardNumber", defaultValue = "empty cardNumber") String cardNumber,
                                    @RequestParam(value = "userName", defaultValue = "empty userName") String userName
                                    ) {
        Payment payment = Payment.builder().bank(bank).cardNumber(cardNumber).userName(userName).build();
        Payment savedPayment = paymentRepository.save(payment);
        User u = getUserFromSession();
        u.setPayment(savedPayment);
        userRepository.save(u);
        return "redirect:/user";
    }


    @PostMapping("/updateProfileByUser")
    public String updateProfile(User user, RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal MyUserDetails logedUser,
                                @RequestParam(value = "userImage", required = false) MultipartFile userImage
    ) throws IOException {
        String fileName = StringUtils.cleanPath(userImage.getOriginalFilename());
        user.setPassword(logedUser.getPassword());
        user.setImage(fileName);
        String uploadDir = "user-image/" + user.getUserId();
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try {
            InputStream inputStream = userImage.getInputStream();
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e) {
            throw new IOException("could not save uploaded file: " + fileName);
        }
        userRepository.save(user);

        logedUser.setFirstName(user.getFirstName());
        logedUser.setLastName(user.getLastName());
        logedUser.setPhoneNumber(user.getPhoneNumber());
        logedUser.setAddress(user.getAddress());
//        userRepository.save(user);
        redirectAttributes.addFlashAttribute("message", "Your account have been updated !!!");

        return "redirect:/user/update-profile";
    }

}