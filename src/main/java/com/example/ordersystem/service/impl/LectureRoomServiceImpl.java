package com.example.ordersystem.service.impl;

import com.example.ordersystem.domain.entity.LectureRoom;
import com.example.ordersystem.repository.LectureRoomRepository;
import com.example.ordersystem.service.LectureRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class LectureRoomServiceImpl implements LectureRoomService {
    @Autowired
    private LectureRoomRepository lectureRoomRepository;


    @Transactional
    public LectureRoom getLectureRoom(long id) {
        return lectureRoomRepository.findById(id).orElse(new LectureRoom()); // orElse -> 값이 없을경우 new LectureRoom

    }

    @Transactional
    public List<LectureRoom> getLectureRooms() {
        return lectureRoomRepository.findAll(Sort.by(Sort.Order.asc("id")));
    }

    @Transactional
    public void createLectureRoom(LectureRoom lectureRoom){
        lectureRoomRepository.save(lectureRoom);
    }

    @Transactional
    public void deleteLectureRoom(long id) {
        lectureRoomRepository.deleteById(id);
    }

}
