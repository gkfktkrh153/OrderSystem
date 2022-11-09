package com.example.ordersystem.repository;

import com.example.ordersystem.domain.entity.LectureRoom;
import com.example.ordersystem.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRoomRepository extends JpaRepository<LectureRoom, Long> {

    LectureRoom findByLectureRoomName(String LectureRoomName);
    LectureRoom findLectureRoomById(long id);
    @Query("select l from LectureRoom l")
    List<LectureRoom> findAllLectureRoom();
}
