package com.example.ordersystem.repository;

import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.domain.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByReservationName(String resourceName);

    @Query("select r from Reservation r join fetch r.account order by r.id desc")
    List<Reservation> findAllReservation();

    @Query("select r from Reservation r join fetch r.account where r.status = 'WAITING'  order by r.id desc")
    List<Reservation> findAllWaitingReservation();

    @Query("select r from Reservation r join fetch r.account where r.status = 'APPROVAL'  order by r.id desc")
    List<Reservation> findAllApprovalReservation();


    List<Reservation> findAllReservationByAccountId(long id);

    Reservation findReservationById(long id);
}
