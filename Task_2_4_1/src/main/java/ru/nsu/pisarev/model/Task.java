package ru.nsu.pisarev.model;

import java.time.LocalDate;

public record Task(String id, String name, int maxPoints, LocalDate softDeadline, LocalDate hardDeadline) {

}
