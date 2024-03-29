package com.example.ordersystem.controller.json;

import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.repository.ReservationRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Controller
public class JsonController {
    @Autowired
    ReservationRepository reservationRepository;


    @GetMapping(value = "/json/mypage")
    @ResponseBody
    public String fullCalendar1(Principal principal, Model model, Authentication authentication)
    { // 월 캘린더

        List<Reservation> reservations = reservationRepository.findAllApprovalReservation();


        JsonArray ja = new JsonArray();
        for(Reservation re: reservations){
            JsonObject obj = new JsonObject();
            obj.addProperty("title", re.getLectureRoom().getLectureRoomName());
            obj.addProperty("start", re.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getStartTime());
            obj.addProperty("end", re.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getEndTime());
            obj.addProperty("allDay", false);//
            // 하나의 예약정보라도 널값이면 에러남
            ja.add(obj);
        }

        return ja.toString();
    }

    @GetMapping(value = "/json/timetable/{id}")
    @ResponseBody
    public String fullCalendar2(Principal principal, Model model, Authentication authentication, @PathVariable("id") String lectureRoomId)
    {
        long id = Long.parseLong(lectureRoomId);
        List<Reservation> reservations = reservationRepository.findAllApprovalReservationByLectureRoomId(id);
        List<Reservation> lectureReservations = reservationRepository.findAllLectureReservationByLectureRoomId(id);




        JsonArray ja = new JsonArray();
        HashMap<String, Object> hash = new HashMap<>();



        for(Reservation re: reservations){
            long round = Math.round(Math.random() * 0xffffff);
            String color = Long.toString(round,16);
            color = color.toUpperCase();
            while(color == "#3788d8")
            {
                round = Math.round(Math.random() * 0xffffff);
                color = Long.toString(round,16).toUpperCase(Locale.ROOT);
            }


            JsonObject obj = new JsonObject();
            obj.addProperty("title", re.getReservationName());
            obj.addProperty("start", re.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getStartTime());
            obj.addProperty("end", re.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getEndTime());
            obj.addProperty("color", "#" +color);
            obj.addProperty("allDay", false);//

            // 하나의 예약정보라도 널값이면 에러남
            ja.add(obj);
        }


        for(Reservation re: lectureReservations){

            for(int i = 0; i < 8; i++) {
                JsonObject obj = new JsonObject();
                obj.addProperty("title", re.getReservationName());
                obj.addProperty("start", re.getDate().plusWeeks(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getStartTime());
                obj.addProperty("end", re.getDate().plusWeeks(i).format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getEndTime());
                obj.addProperty("description","test");
                obj.addProperty("allDay", false);//
                // 하나의 예약정보라도 널값이면 에러남
                ja.add(obj);
            }
        }

        return ja.toString();
    }
}
