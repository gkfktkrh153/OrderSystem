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

import java.util.List;
import java.util.Optional;

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

}
