package com.example.ordersystem.security.listener;

import com.example.ordersystem.domain.entity.*;
import com.example.ordersystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.ordersystem.domain.entity.ReservationStatus.*;

@Component
public class  SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private LectureRoomRepository lectureRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourcesRepository resourcesRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static AtomicInteger count = new AtomicInteger(0);

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();

        //setupAccessIpData();

        alreadySetup = true;
    }

    private void setupSecurityResources() {

        Set<Role> rolesAdmin = new HashSet<>();
        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        rolesAdmin.add(adminRole);
        Account admin = createUserIfNotFound("admin", "admin@admin.com", "1234", rolesAdmin);
        createResourceIfNotFound("/admin/**", "", rolesAdmin, "url");

        Set<Role> rolesUser = new HashSet<>();
        Role userRole = createRoleIfNotFound("ROLE_USER", "사용자권한");
        rolesUser.add(userRole);
        Account user = createUserIfNotFound("user", "user@admin.com", "1234", rolesUser);

        createResourceIfNotFound("/user/**", "", rolesUser, "url");

        LectureRoom lectureRoom1 = createLectureRoomIfNotFound("이공관 101호");
        LectureRoom lectureRoom2 = createLectureRoomIfNotFound("이공관 103호");
        LectureRoom lectureRoom3 = createLectureRoomIfNotFound("이공관 302호");
        LectureRoom lectureRoom4 = createLectureRoomIfNotFound("이공관 310호");
        LectureRoom lectureRoom5 = createLectureRoomIfNotFound("이공관 311호");
        LectureRoom lectureRoom6 = createLectureRoomIfNotFound("이공관 312호");


        createReservationIfNotFound("컴퓨터프로그래밍(21)",LocalDate.parse("2022-11-07"), LocalTime.parse("09:00"), LocalTime.parse("11:40"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("컴퓨터프로그래밍(19)",LocalDate.parse("2022-11-07"), LocalTime.parse("11:50"), LocalTime.parse("14:30"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("프로그래밍언어(02)",LocalDate.parse("2022-11-07"), LocalTime.parse("14:40"), LocalTime.parse("17:20"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("컴퓨터프로그래밍(13)",LocalDate.parse("2022-11-08"), LocalTime.parse("09:00"), LocalTime.parse("11:40"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("영상콘텐츠제작실습(02)",LocalDate.parse("2022-11-08"), LocalTime.parse("14:40"), LocalTime.parse("17:20"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("프로그래밍언어(02)",LocalDate.parse("2022-11-09"), LocalTime.parse("09:00"), LocalTime.parse("12:40"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("특수아초등컴퓨터(00)",LocalDate.parse("2022-11-10"), LocalTime.parse("09:00"), LocalTime.parse("11:40"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("프로그래밍언어(02)",LocalDate.parse("2022-11-10"), LocalTime.parse("11:50"), LocalTime.parse("14:30"),admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("파이썬응용(02)",LocalDate.parse("2022-11-11"), LocalTime.parse("09:00"), LocalTime.parse("11:40"),admin ,lectureRoom1, LECTURE);

        createReservationIfNotFound("디자인콘텐츠기획및제작(00)",LocalDate.parse("2022-11-07"), LocalTime.parse("11:50"), LocalTime.parse("14:30"),admin ,lectureRoom2, LECTURE);
        createReservationIfNotFound("프로그래밍언어(01)",LocalDate.parse("2022-11-07"), LocalTime.parse("14:30"), LocalTime.parse("17:20"),admin ,lectureRoom2, LECTURE);
        createReservationIfNotFound("데이터베이스(02)",LocalDate.parse("2022-11-08"), LocalTime.parse("09:00"), LocalTime.parse("12:40"),admin ,lectureRoom2, LECTURE);
        createReservationIfNotFound("C프로그래밍(03)",LocalDate.parse("2022-11-09"), LocalTime.parse("09:00"), LocalTime.parse("12:40"),admin ,lectureRoom2, LECTURE);
        createReservationIfNotFound("데이터베이스(03)",LocalDate.parse("2022-11-09"), LocalTime.parse("13:40"), LocalTime.parse("17:20"),admin ,lectureRoom2, LECTURE);

        createReservationIfNotFound("파이썬응용(03)",LocalDate.parse("2022-11-09"), LocalTime.parse("09:00"), LocalTime.parse("11:40"),admin ,lectureRoom3, LECTURE);

        createReservationIfNotFound("도시설계(00)",LocalDate.parse("2022-11-09"), LocalTime.parse("11:50"), LocalTime.parse("16:30"),admin ,lectureRoom4, LECTURE);

        createReservationIfNotFound("C프로그래밍(02)",LocalDate.parse("2022-11-07"), LocalTime.parse("13:40"), LocalTime.parse("17:20"),admin ,lectureRoom5, LECTURE);
        createReservationIfNotFound("가상현실응용(00)",LocalDate.parse("2022-11-08"), LocalTime.parse("14:40"), LocalTime.parse("17:20"),admin ,lectureRoom5, LECTURE);
        createReservationIfNotFound("운영체제(02)",LocalDate.parse("2022-11-09"), LocalTime.parse("09:00"), LocalTime.parse("12:40"),admin ,lectureRoom5, LECTURE);
        createReservationIfNotFound("기계학습(00)",LocalDate.parse("2022-11-11"), LocalTime.parse("13:40"), LocalTime.parse("17:20"),admin ,lectureRoom5, LECTURE);

        createReservationIfNotFound("운영체제(03)",LocalDate.parse("2022-11-07"), LocalTime.parse("09:00"), LocalTime.parse("12:40"),admin ,lectureRoom6, LECTURE);
        createReservationIfNotFound("객체지향프로그래밍(05)",LocalDate.parse("2022-11-07"), LocalTime.parse("14:40"), LocalTime.parse("17:20"),admin ,lectureRoom6, LECTURE);
        createReservationIfNotFound("네트워크(01)",LocalDate.parse("2022-11-08"), LocalTime.parse("09:00"), LocalTime.parse("12:40"),admin ,lectureRoom6, LECTURE);
        createReservationIfNotFound("네트워크(02)",LocalDate.parse("2022-11-08"), LocalTime.parse("13:40"), LocalTime.parse("17:20"),admin ,lectureRoom6, LECTURE);
        createReservationIfNotFound("파이썬응용(01)",LocalDate.parse("2022-11-09"), LocalTime.parse("09:00"), LocalTime.parse("11:40"),admin ,lectureRoom6, LECTURE);
        createReservationIfNotFound("VR파노라마(00)",LocalDate.parse("2022-11-09"), LocalTime.parse("11:50"), LocalTime.parse("14:30"),admin ,lectureRoom6, LECTURE);
        createReservationIfNotFound("캡스톤디자인(VR)",LocalDate.parse("2022-11-10"), LocalTime.parse("09:00"), LocalTime.parse("11:40"),admin ,lectureRoom6, LECTURE);
        createReservationIfNotFound("정보보호관리및정책(02)",LocalDate.parse("2022-11-11"), LocalTime.parse("11:50"), LocalTime.parse("14:30"),admin ,lectureRoom6, LECTURE);

    }


    @Transactional
    public Role createRoleIfNotFound(String roleName, String roleDesc) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = Role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Account createUserIfNotFound(final String userName, final String email, final String password, Set<Role> roleSet) {

        Account account = userRepository.findByUsername(userName);

        if (account == null) {
            account = Account.builder()
                    .username(userName)
                    .email(email)
                    .password(passwordEncoder.encode(password))
                    .userRoles(roleSet)
                    .build();
        }
        return userRepository.save(account);
    }

    @Transactional
    public Reservation createReservationIfNotFound(final String reservationName, final LocalDate date, final LocalTime startTime, final LocalTime endTime, Account account, final LectureRoom lectureRoom, final ReservationStatus status) {

        Reservation reservation = reservationRepository.findByReservationName(reservationName);

        if (reservation == null) {
            reservation = Reservation.builder()
                    .reservationName(reservationName)
                    .date(date)
                    .startTime(startTime)
                    .endTime(endTime)
                    .account(account)
                    .lectureRoom(lectureRoom)
                    .status(status)
                    .build();
        }
        return reservationRepository.save(reservation);
    }

    @Transactional
    public LectureRoom createLectureRoomIfNotFound(final String lectureRoomName) {

        LectureRoom lectureRoom = lectureRoomRepository.findByLectureRoomName(lectureRoomName);

        if (lectureRoom == null) {
            lectureRoom = lectureRoom.builder()
                    .lectureRoomName(lectureRoomName)
                    .build();
        }
        return lectureRoomRepository.save(lectureRoom);
    }


    @Transactional
    public Resources createResourceIfNotFound(String resourceName, String httpMethod, Set<Role> roleSet, String resourceType) {
        Resources resources = resourcesRepository.findByResourceNameAndHttpMethod(resourceName, httpMethod);

        if (resources == null) {
            resources = Resources.builder()
                    .resourceName(resourceName)
                    .roleSet(roleSet)
                    .httpMethod(httpMethod)
                    .resourceType(resourceType)
                    .orderNum(count.incrementAndGet())
                    .build();
        }
        return resourcesRepository.save(resources);
    }

}