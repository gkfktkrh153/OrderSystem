package com.example.ordersystem.repository;

import com.example.ordersystem.domain.entity.Reservation;
import com.example.ordersystem.domain.entity.Resources;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    Reservation findByReservationName(String resourceName);


    @Query("select r from Reservation r join fetch r.account where r.status = 'WAITING'  order by r.id desc")
    List<Reservation> findAllWaitingReservation();

    @Query("select r from Reservation r join fetch r.account where r.status = 'APPROVAL'  order by r.id desc")
    List<Reservation> findAllApprovalReservation();

    @Query("select r from Reservation r join fetch r.account where r.status = 'LECTURE'  order by r.id desc")
    List<Reservation> findAllLectureReservation();

    //@Query("select r from Reservation r join fetch r.lectureRoom l where l.id = :id  order by r.id desc")
    //List<Reservation> findAllReservationByLectureRoomId(@Param("id") Long id);

    @Query("select r from Reservation r join fetch r.lectureRoom l where l.id = :id and r.status = 'APPROVAL' order by r.id desc")
    List<Reservation> findAllApprovalReservationByLectureRoomId(@Param("id") Long id);

    @Query("select r from Reservation r join fetch r.lectureRoom l where l.id = :id and r.status = 'LECTURE'  order by r.id desc")
    List<Reservation> findAllLectureReservationByLectureRoomId(@Param("id") Long id);

    List<Reservation> findAllReservationByAccountId(long id);

    Reservation findReservationById(long id);
}
