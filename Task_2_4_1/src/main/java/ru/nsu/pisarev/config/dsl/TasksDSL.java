package ru.nsu.pisarev.config.dsl;

import ru.nsu.pisarev.config.ConfigParser;
import ru.nsu.pisarev.model.Task;

import java.time.LocalDate;

public record TasksDSL(ConfigParser parent) {

    public void task(String id, String name, int max, String soft, String hard) {
        parent.addTask(new Task(id, name, max, LocalDate.parse(soft), LocalDate.parse(hard)));
    }
}