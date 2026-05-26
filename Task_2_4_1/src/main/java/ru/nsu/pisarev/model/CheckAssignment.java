package ru.nsu.pisarev.model;

import java.util.List;

public record CheckAssignment(String groupName, List<String> studentNicks, List<String> taskIds) {

}