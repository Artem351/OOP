package ru.nsu.pisarev.config.dsl;


import ru.nsu.pisarev.model.Student;

import java.util.List;
import java.util.Map;

public record StudentDSL(List<Student> students) {

    public void student(Map<String, String> args) {
        students.add(new Student(args.get("nick"), args.get("fullName"), args.get("repo")));
    }
}
