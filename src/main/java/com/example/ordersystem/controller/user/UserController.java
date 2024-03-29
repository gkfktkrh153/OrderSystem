package com.example.ordersystem.controller.user;

import com.example.ordersystem.domain.entity.Account;
import com.example.ordersystem.domain.dto.AccountDto;
import com.example.ordersystem.domain.entity.LectureRoom;
import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.repository.LectureRoomRepository;
import com.example.ordersystem.repository.ReservationRepository;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.service.UserService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LectureRoomRepository lectureRoomRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/user")
    public String userPage(Principal principal, Model model, Authentication authentication)
    {

        Account account= (Account)authentication.getPrincipal();
        Long id = account.getId();
        List<Reservation> reservations = reservationRepository.findAllReservationByAccountId(id);
        model.addAttribute("reservations",reservations);

        return "user/mypage";
    }
    @GetMapping(value = "/user/timetable")
    public String timeTablePage1(Principal principal, Model model, Authentication authentication, @RequestParam(defaultValue = "7") String lectureRoomId)
    {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllLectureRoom();
        model.addAttribute("lectureRooms",lectureRooms);

        Long id = Long.parseLong(lectureRoomId);
        LectureRoom lectureRoom = lectureRoomRepository.findLectureRoomById(id);
        model.addAttribute("name",lectureRoom.getLectureRoomName());
        model.addAttribute("id",id);
        return "user/timetable";
    }
    @PostMapping(value = "/user/timetable")
    public String timeTablePage2(Principal principal, Model model, Authentication authentication, @RequestParam(defaultValue = "7") String lectureRoomId)
    {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllLectureRoom();
        model.addAttribute("lectureRooms",lectureRooms);

        Long id = Long.parseLong(lectureRoomId);
        LectureRoom lectureRoom = lectureRoomRepository.findLectureRoomById(id);
        model.addAttribute("name",lectureRoom.getLectureRoomName());
        model.addAttribute("id",id);
        return "user/timetable";
    }

    @GetMapping(value = "/resister")
    public String createUser(){
        return "resister";
    }

    @PostMapping(value = "/resister")
    public String createUser(AccountDto accountDto){
        ModelMapper modelMapper = new ModelMapper();
        Account account = modelMapper.map(accountDto, Account.class);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        userService.createUser(account);

        return "redirect:/";
    }
}
