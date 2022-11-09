package com.example.ordersystem.domain.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class LectureRoom {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String lectureRoomName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lectureRoom")
    private Set<Reservation> reservationSet = new HashSet<>();

}
