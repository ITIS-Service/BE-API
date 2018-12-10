package com.itis.service.service.impl;

import com.itis.service.dto.ProfileDto;
import com.itis.service.dto.RegisterDto;
import com.itis.service.entity.Group;
import com.itis.service.entity.Student;
import com.itis.service.entity.User;
import com.itis.service.entity.enums.UserRole;
import com.itis.service.exception.InitializeException;
import com.itis.service.exception.RegistrationException;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.mapper.StudentMapper;
import com.itis.service.repository.GroupRepository;
import com.itis.service.repository.StudentRepository;
import com.itis.service.repository.UserRepository;
import com.itis.service.security.JWTProvider;
import com.itis.service.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTProvider jwtProvider;

    private final StudentMapper studentMapper;

    @Autowired
    public UserServiceImpl(GroupRepository groupRepository,
                           StudentRepository studentRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JWTProvider jwtProvider,
                           StudentMapper studentMapper) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.studentMapper = studentMapper;
    }

    public String register(RegisterDto registerDto) {
        Student student = studentRepository.findByEmail(registerDto.getEmail());
        if (student == null) {
            throw new ResourceNotFoundException("Пользователь с данным e-mail не найден");
        }
        if (student.getPassword() != null) {
            throw new RegistrationException();
        }

        student.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        studentRepository.save(student);

        return jwtProvider.createToken(student.getRole().toString(), student.getEmail());
    }

    public ProfileDto fetchProfile(String email) {
        Student student = studentRepository.findByEmail(email);
        if (student == null) {
            throw new ResourceNotFoundException("Пользователь с e-mail" + email + " не найден");
        }

        return studentMapper.profileDto(student);
    }

    public void updateStudentList() {
        if (studentRepository.count() != 0 && groupRepository.count() != 0) {
            LOG.info("Students and Groups initialized");
            return;
        }

        LOG.info("Start fetching students and groups from kpfu.ru...");

        List<Group> groups = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("https://kpfu.ru/studentu/main_page")
                    .data("p_id", "6895")
                    .data("p_page", "1")
                    .data("p_rec_count", "1000")
                    .data("p_sub", "23861")
                    .userAgent("Mozilla")
                    .timeout(20000)
                    .post();
            Elements rows = doc.select("tr.konf_tr");
            for (Element row : rows) {
                Elements columns = row.select("td");
                for (Element column : columns) {
                    String[] name = column.select("a").first().text().split(" ");
                    String link = column.select("a").attr("href");
                    String course = column.select("font").text();

                    Pattern patternGroup = Pattern.compile("(\\d{2}-\\d{3}).*(\\d)");
                    Pattern patternLink = Pattern.compile("student/(.*)");
                    Matcher matcher = patternGroup.matcher(course);
                    Matcher matcherLink = patternLink.matcher(link);
                    if (matcher.find() && matcherLink.find()) {
                        String groupName = matcher.group(1);
                        String courseNumber = matcher.group(2);
                        String email = matcherLink.group(1) + "@stud.kpfu.ru";

                        Group group = new Group(groupName, Integer.parseInt(courseNumber));
                        if (groups.contains(group)) {
                            group = groups.get(groups.indexOf(group));
                        } else {
                            groups.add(group);
                        }

                        Student student = new Student(email, null, name[0], name[1], group);
                        students.add(student);
                    }
                }
            }

            studentRepository.saveAll(students);
            studentRepository.flush();

            LOG.info("Students and groups fetched successfully!");
        } catch (IOException e) {
            throw new InitializeException();
        }
    }

    public void createAdmin() {
        User admin = userRepository.findByRole(UserRole.ADMIN);
        if (admin == null) {
            LOG.info("Creating new admin...");
            admin = new User(
                    "admin@itis.kpfu.ru",
                    passwordEncoder.encode("qwe123"),
                    "ITIS",
                    "Admin",
                    UserRole.ADMIN
            );

            userRepository.saveAndFlush(admin);
            LOG.info("Admin created successfully");
        } else {
            LOG.info("Admin already exists");
        }
    }

    public void createTestStudents() {
        if (studentRepository.count() != 0 && groupRepository.count() != 0) {
            LOG.info("Students and Groups initialized");
            return;
        }

        LOG.info("Start creating test students and groups");

        String kpfuDomen = "stud.kpfu.ru";

        Group group504 = new Group("11-504", 4);
        Group group604 = new Group("11-604", 3);
        Group group704 = new Group("11-704", 2);
        Group group804 = new Group("11-804", 1);

        Student student504 = new Student("11-504@" + kpfuDomen, passwordEncoder.encode("qwe123"), "Student", "11-504", group504);
        Student student604 = new Student("11-604@" + kpfuDomen, passwordEncoder.encode("qwe123"), "Student", "11-604", group604);
        Student student704 = new Student("11-704@" + kpfuDomen, passwordEncoder.encode("qwe123"), "Student", "11-704", group704);
        Student student804 = new Student("11-804@" + kpfuDomen, passwordEncoder.encode("qwe123"), "Student", "11-804", group804);

        studentRepository.saveAll(Arrays.asList(student504, student604, student704, student804));
        studentRepository.flush();

        LOG.info("Test students and group created");
    }

}
