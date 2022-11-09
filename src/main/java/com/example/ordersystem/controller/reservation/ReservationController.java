package com.example.ordersystem.controller.reservation;

import com.example.ordersystem.domain.dto.ReservationDto;
import com.example.ordersystem.domain.entity.Account;
import com.example.ordersystem.domain.entity.LectureRoom;
import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.domain.entity.ReservationStatus;
import com.example.ordersystem.repository.LectureRoomRepository;
import com.example.ordersystem.repository.ReservationRepository;
import com.example.ordersystem.repository.UserRepository;
import com.example.ordersystem.security.service.AccountContext;
import com.example.ordersystem.service.ReservationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReservationController {
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ReservationService reservationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LectureRoomRepository lectureRoomRepository;


    @GetMapping(value = "/admin/reservation/management") // 예약관리
    public String adminManagementReservation(Model model) {
        List<Reservation> reservations = reservationRepository.findAllApprovalReservation();
        model.addAttribute("reservations",reservations);
        return "admin/reservation/management";
    }
    @PostMapping(value = "/admin/reservation/management/{reservationId}/cancel") // 예약삭제
    public String cancelReservation1(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return "redirect:/admin/reservation/management";
    }

    @PostMapping(value = "/admin/reservation/requestList/{reservationId}/approval") // 예약승인
    public String approvalReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.approvalReservation(reservationId);
        return "redirect:/admin/reservation/requestList";
    }


    @PostMapping(value = "/admin/reservation/requestList/{reservationId}/denial") // 예약거부
    public String denialReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.denialReservation(reservationId);
        return "redirect:/admin/reservation/requestList";
    }


    @GetMapping(value = "/admin/reservation/resist") // 예약등록 폼
    public String adminResistReservation(Model model, Authentication authentication) {
        Reservation reservation = new Reservation();
        Account account = (Account) authentication.getPrincipal();
        String userName = account.getUsername();
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllLectureRoom();
        for (LectureRoom lectureRoom : lectureRooms)
        {
            System.out.println(lectureRoom.getLectureRoomName());
        }
        model.addAttribute("lectureRooms",lectureRooms);
        model.addAttribute("userName", userName);
        model.addAttribute(reservation);


        return "admin/reservation/resist";
    }

    @PostMapping(value = "/admin/reservation/resist") // 예약등록
    public String adminResistReservation(ReservationDto reservationDto, Authentication authentication, @RequestParam("lectureRoomId") Long lectureRoomId) {
        Account account= (Account) authentication.getPrincipal();
        //System.out.println(account);

        LectureRoom lectureRoom = lectureRoomRepository.findLectureRoomById(lectureRoomId);

        ModelMapper modelMapper = new ModelMapper();
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        reservation.setStatus(ReservationStatus.APPROVAL);
        reservation.setAccount(account);
        reservation.setLectureRoom(lectureRoom);

        reservationService.createReservation(reservation);
        return "redirect:/admin/reservation/management";
    }

    @GetMapping(value = "/admin/reservation/requestList") // 모든 USER들의 요청 목록
    public String adminRequestList(Model model) {
        List<Reservation> reservations = reservationRepository.findAllWaitingReservation();
        model.addAttribute("reservations",reservations);
        return "admin/reservation/requestList";
    }


    @GetMapping(value = "/user/reservation/list") // USER의 신청목록
    public String userReservationList(Authentication authentication, Model model) {
        Account account= (Account)authentication.getPrincipal();
        Long id = account.getId();
        List<Reservation> reservations = reservationRepository.findAllReservationByAccountId(id);
        model.addAttribute("reservations",reservations);
        return "user/reservation/list";
    }

    @PostMapping(value = "/user/reservation/list/{reservationId}/cancel") // 예약신청삭제
    public String cancelReservation2(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return "redirect:/user/reservation/list";
    }



    @GetMapping(value = "/user/reservation/request") // 예약신청 폼
    public String userRequestReservation(Model model, Authentication authentication) {

        Reservation reservation = new Reservation();
        model.addAttribute(reservation);
        Account account = (Account) authentication.getPrincipal();
        String userName = account.getUsername();
        model.addAttribute("userName", userName);

        return "user/reservation/request";
    }
    @PostMapping(value = "/user/reservation/request") // 예약신청
    public String userRequestReservation(ReservationDto reservationDto, Authentication authentication) {
        Account account= (Account) authentication.getPrincipal();
        System.out.println(account);

        ModelMapper modelMapper = new ModelMapper();
        Reservation reservation = modelMapper.map(reservationDto, Reservation.class);
        reservation.setStatus(ReservationStatus.WAITING);
        reservation.setAccount(account);

        reservationService.createReservation(reservation);
        return "redirect:/user/reservation/list";
    }
}
