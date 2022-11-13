package com.example.ordersystem.security.listener;

import com.example.ordersystem.domain.entity.*;
import com.example.ordersystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.ordersystem.domain.entity.ReservationStatus.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

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

        LectureRoom lectureRoom1 = createLectureRoomIfNotFound("샬롬관 201호");
        LectureRoom lectureRoom2 = createLectureRoomIfNotFound("샬롬관 202호");
        LectureRoom lectureRoom3 = createLectureRoomIfNotFound("샬롬관 301호");
        LectureRoom lectureRoom4 = createLectureRoomIfNotFound("샬롬관 401호");

        createReservationIfNotFound("소프트웨어공학",LocalDate.parse("2022-11-08"), "09:00", "12:00",admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("자료구조", LocalDate.parse("2022-11-09"), "12:00", "15:00",admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("알고리즘", LocalDate.parse("2022-11-09"), "09:00", "10:30",admin ,lectureRoom1, LECTURE);
        createReservationIfNotFound("캡스톤디자인", LocalDate.parse("2022-11-10"), "12:00", "15:00",admin ,lectureRoom1, LECTURE);

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
    public Reservation createReservationIfNotFound(final String reservationName, final LocalDate date, final String startTime, final String endTime, Account account, final LectureRoom lectureRoom, final ReservationStatus status) {

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