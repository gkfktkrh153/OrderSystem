package com.example.ordersystem.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

import java.time.LocalTime;


@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String reservationName;

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lectureRoom_id")
    private LectureRoom lectureRoom;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

}
