package com.itis.service.service.impl;

import com.itis.service.dto.RegisterDto;
import com.itis.service.entity.Group;
import com.itis.service.entity.Student;
import com.itis.service.exception.InitializeException;
import com.itis.service.exception.ResourceNotFoundException;
import com.itis.service.repository.GroupRepository;
import com.itis.service.repository.StudentRepository;
import com.itis.service.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public UserServiceImpl(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    public void register(RegisterDto registerDto) {
        Student student = studentRepository.findByEmail(registerDto.getEmail());
        if (student == null) {
            throw new ResourceNotFoundException("Пользователь с данным e-mail не найден");
        }
        student.setPassword(registerDto.getPassword());
        studentRepository.save(student);
    }

    public void updateStudentList() {
        studentRepository.deleteAll();
        groupRepository.deleteAll();

        Set<Group> groups = new HashSet<>();
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
                        groups.add(group);

                        Student student = new Student(email, null, name[0], name[1], group);
                        students.add(student);
                    }
                }
            }

            groupRepository.saveAll(groups);
            groupRepository.flush();
            studentRepository.saveAll(students);
            studentRepository.flush();
        } catch (IOException e) {
            throw new InitializeException();
        }
    }

}
