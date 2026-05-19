package ru.nsu.pisarev.model;

import java.util.Map;

public record Settings(Map<Integer, String> gradingScale, int timeoutSec, String strategy) {

}