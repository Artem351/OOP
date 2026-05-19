package ru.nsu.pisarev.model;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public record StudentResult(Student student, List<TaskResult> results) {
    public double totalPoints() {
        return results.stream()
                .mapToDouble(TaskResult::earnedPoints)
                .sum();
    }

    public String grade(Settings settings) {
        return settings.gradingScale().entrySet().stream()
                .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .filter(e -> totalPoints() >= e.getKey())
                .map(Map.Entry::getValue)
                .findFirst().orElse("F");
    }
}
