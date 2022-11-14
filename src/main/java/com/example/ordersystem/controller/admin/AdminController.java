package com.example.ordersystem.controller.admin;

import com.example.ordersystem.domain.entity.Account;
import com.example.ordersystem.domain.entity.LectureRoom;
import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.repository.LectureRoomRepository;
import com.example.ordersystem.repository.ReservationRepository;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LectureRoomRepository lectureRoomRepository;
    @Autowired
    private UserService userService;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping(value = "/admin")
    public String adminPage() {
        return "admin/mypage";
    }

    @GetMapping(value = "/admin/timetable")
    public String timeTablePage1(Principal principal, Model model, Authentication authentication, @RequestParam(defaultValue = "7") String lectureRoomId)
    {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllLectureRoom();
        model.addAttribute("lectureRooms",lectureRooms);

        Long id = Long.parseLong(lectureRoomId);
        LectureRoom lectureRoom = lectureRoomRepository.findLectureRoomById(id);
        model.addAttribute("name",lectureRoom.getLectureRoomName());
        model.addAttribute("id",id);
        return "admin/timetable";
    }
    @PostMapping(value = "/admin/timetable")
    public String timeTablePage2(Principal principal, Model model, Authentication authentication, @RequestParam(defaultValue = "7") String lectureRoomId)
    {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllLectureRoom();
        model.addAttribute("lectureRooms",lectureRooms);

        Long id = Long.parseLong(lectureRoomId);
        LectureRoom lectureRoom = lectureRoomRepository.findLectureRoomById(id);
        model.addAttribute("name",lectureRoom.getLectureRoomName());
        model.addAttribute("id",id);
        return "admin/timetable";
    }
}
