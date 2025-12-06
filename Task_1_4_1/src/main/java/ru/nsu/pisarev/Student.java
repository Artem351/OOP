package ru.nsu.pisarev;

import java.util.List;


public class Student {

    private final boolean isPaidEducation;
    private final GradeBook gradeBook;

    public Student(boolean isPaidEducation) {
        this.isPaidEducation = isPaidEducation;
        this.gradeBook = new GradeBook();
    }

    public GradeBook getGradeBook() {
        return gradeBook;
    }

    public boolean isPaidEducation() {
        return isPaidEducation;
    }


    public double getCurrentAverageScore() {
        return gradeBook.getAverageScore();
    }


    public boolean canTransferToBudget() {
        if (!isPaidEducation)
            return false;

        List<Integer> semesters = gradeBook.getRecords().stream()
                .map(GradeRecord::getSemester)
                .distinct()
                .sorted()
                .toList();

        if (semesters.size() < 2)
            return false;

        int last = semesters.get(semesters.size() - 1);
        int prev = semesters.get(semesters.size() - 2);

        List<GradeRecord> lastTwoSessions = gradeBook.getRecords().stream()
                .filter(r -> r.getSemester() == last || r.getSemester() == prev)
                .filter(r -> r.getType() == ControlType.EXAM)
                .toList();

        return lastTwoSessions.stream()
                .noneMatch(r -> r.getGrade().isSatisfactory());
    }

    public boolean canReceiveRedDiploma() {
        List<GradeRecord> all = gradeBook.getRecords();

        long total = all.stream()
                .filter(r -> r.getGrade().getScore() > 0)
                .count();

        long excellent = all.stream()
                .filter(r -> r.getGrade().isExcellent())
                .count();


        boolean enoughExcellent = total > 0 && ((double) excellent / total) >= 0.75;


        boolean noBad = all.stream()
                .filter(r -> r.getType() == ControlType.EXAM
                        || r.getType() == ControlType.DIFF_CREDIT)
                .noneMatch(r -> r.getGrade().isSatisfactory());


        boolean diplomaExcellent = all.stream()
                .filter(r -> r.getType() == ControlType.DIPLOMA_DEFENSE)
                .allMatch(r -> r.getGrade().isExcellent());

        return enoughExcellent && noBad && diplomaExcellent;
    }


    public boolean canGetIncreasedScholarship(int semester) {
        List<GradeRecord> sem = gradeBook.getRecordsForSemester(semester);


        return sem.stream()
                .filter(r -> r.getType() == ControlType.EXAM || r.getType() == ControlType.DIFF_CREDIT)
                .allMatch(r -> r.getGrade().isExcellent());
    }
}
