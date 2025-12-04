package ru.nsu.pisarev;


public class GradeRecord {

    private final int semester;
    private final ControlType type;
    private final Grade grade;

    public GradeRecord(int semester, ControlType type, Grade grade) {
        this.semester = semester;
        this.type = type;
        this.grade = grade;
    }

    public int getSemester() {
        return semester;
    }

    public ControlType getType() {
        return type;
    }

    public Grade getGrade() {
        return grade;
    }
}
