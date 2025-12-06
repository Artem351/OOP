package ru.nsu.pisarev;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GradeBook {

    private final List<GradeRecord> records = new ArrayList<>();

    public void addRecord(GradeRecord record) {
        records.add(record);
    }

    public List<GradeRecord> getRecords() {
        return records;
    }

    public double getAverageScore() {
        List<Grade> marks = records.stream()
                .map(GradeRecord::getGrade) // зачёты не считаем
                .filter(grade -> grade.getScore() > 0)
                .toList();

        if (marks.isEmpty()) {
            return 0;
        }

        double sum = marks.stream().mapToInt(Grade::getScore).sum();
        return sum / marks.size();
    }

    public List<GradeRecord> getRecordsForSemester(int semester) {
        return records.stream()
                .filter(r -> r.getSemester() == semester)
                .collect(Collectors.toList());
    }
}
