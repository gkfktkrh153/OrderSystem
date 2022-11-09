package com.example.ordersystem.service;


import com.example.ordersystem.domain.entity.LectureRoom;
import com.example.ordersystem.domain.entity.Reservation;

import java.util.List;

public interface LectureRoomService {
    LectureRoom getLectureRoom(long id);

    List<LectureRoom> getLectureRooms();

    void createLectureRoom(LectureRoom lectureRoom);

    void deleteLectureRoom(long id);


}
