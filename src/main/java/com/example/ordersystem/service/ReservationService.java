package com.example.ordersystem.service;


import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.domain.entity.Resources;

import java.util.List;

public interface ReservationService {
    Reservation getReservation(long id);

    List<Reservation> getReservations();

    void createReservation(Reservation reservation);

    void deleteReservation(long id);

    void approvalReservation(long reservationId);

    void denialReservation(long reservationId);
}
