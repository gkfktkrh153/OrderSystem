package com.example.ordersystem.controller.admin;

import com.example.ordersystem.domain.entity.Account;
import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.repository.ReservationRepository;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping(value = "/admin")
    public String adminPage() {
        return "admin/mypage";
    }

    @GetMapping(value = "/admin/timetable")
    public String timeTablePage(Principal principal, Model model, Authentication authentication)
    {
        Account account= (Account)authentication.getPrincipal();
        Long id = account.getId();
        List<Reservation> reservations = reservationRepository.findAllReservationByAccountId(id);
        model.addAttribute("reservations",reservations);
        return "admin/timetable";
    }
}
