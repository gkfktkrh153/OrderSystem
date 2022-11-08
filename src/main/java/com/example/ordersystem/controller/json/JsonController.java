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
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

@Controller
public class JsonController {
    @Autowired
    ReservationRepository reservationRepository;


    @GetMapping(value = "/json")
    @ResponseBody
    public String fullCalendar(Principal principal, Model model, Authentication authentication)
    {

        List<Reservation> reservations = reservationRepository.findAllApprovalReservation();


        JsonArray ja = new JsonArray();
        HashMap<String, Object> hash = new HashMap<>();
        for(Reservation re: reservations){
            JsonObject obj = new JsonObject();
            obj.addProperty("title", re.getReservationName());
//            obj.addProperty("start", re.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getStartTime());
//            obj.addProperty("end", re.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd ")) + re.getEndTime());
            obj.addProperty("start", "2022-11-08 08:00");
            obj.addProperty("end", "2022-11-08 09:00");

            obj.addProperty("allDay", false);//
            // 하나의 예약정보라도 널값이면 에러남
            ja.add(obj);
        }
        return ja.toString();
    }
}
