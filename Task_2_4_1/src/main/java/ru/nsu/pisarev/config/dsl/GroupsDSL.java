package ru.nsu.pisarev.config.dsl;

import groovy.lang.Closure;
import ru.nsu.pisarev.config.ConfigParser;
import ru.nsu.pisarev.model.Group;
import ru.nsu.pisarev.model.Student;

import java.util.ArrayList;
import java.util.List;

public record GroupsDSL(ConfigParser parent) {

    public void group(String name, Closure<?> closure) {
        List<Student> students = new ArrayList<>();
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.setDelegate(new StudentDSL(students));
        closure.call();
        parent.addGroup(new Group(name, students));
    }
}
