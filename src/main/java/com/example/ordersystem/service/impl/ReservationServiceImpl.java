package com.example.ordersystem.service.impl;

import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.domain.entity.ReservationStatus;
import com.example.ordersystem.repository.ReservationRepository;
import com.example.ordersystem.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    public Reservation getReservation(long id) {
        return reservationRepository.findById(id).orElse(new Reservation());

    }

    @Transactional
    public List<Reservation> getReservations() {
        return reservationRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    @Transactional
    public void createReservation(Reservation reservation){
        reservationRepository.save(reservation);
    }

    @Transactional
    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }


    @Transactional
    public void approvalReservation(long id) {
        Reservation reservation = reservationRepository.findReservationById(id);
        reservation.setStatus(ReservationStatus.APPROVAL);

    }

    @Transactional
    public void denialReservation(long id) {
        Reservation reservation = reservationRepository.findReservationById(id);
        reservation.setStatus(ReservationStatus.DENIAL);
    }

    @Transactional
    public boolean isDuplicate(LocalDate date, Long lectureRoomId, LocalTime s2, LocalTime e2) {
        List<Reservation> allReservations = reservationRepository.findAllReservationByLectureRoomId(lectureRoomId);

        List<Reservation> reservations = allReservations.stream()
                .filter(a -> a.getDate().isEqual(date))
                .collect(Collectors.toList());

        for (Reservation reservation : reservations){ // 기존시간대 V1(s1,e1) 입력시간대 V2(s2,e2)
            LocalTime e1 = reservation.getEndTime();
            LocalTime s1 = reservation.getStartTime();

            if (s2.isBefore(s1) && e2.isAfter(s1) && (e2.isBefore(e1) || (e2.isAfter(e1)) )) // s2 < s1 | s1 < e2 < e1
            {// 기존 예약시간 이전에 시작해서 기존 예약시간 내에서 끝나는 경우
                System.out.println("case1");
                return true;
            }
            if ( (s2.isAfter(s1) || s2.equals(s1)) && s2.isBefore(e1)  && e2.isAfter(e1))
            {// 기존 예약시간 내에 시작하고 예약 외 시간대에 끝나는 경우()
                System.out.println("case2");
                return true;
            }
            if ((s2.isAfter(s1) || s2.equals(s1)) && (s2.isBefore(e1) || s2.equals(s1)) )
            {// 기존 예약시간에 내에 시작하고 기존 예약시간 내에 끝나는 경우(일치 or 내부)
                System.out.println("case3");
                return true;
            }
            if (s2.isBefore(s1) && e2.isAfter(e1))
            {// 기존 예약시간 이후에 시작하고 기존 예약시간 내에 끝나는 경우
                System.out.println("case4");
                return true;
            }
        }


        return false;
    }

}
