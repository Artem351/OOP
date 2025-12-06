package ru.nsu.pisarev;

public enum Grade {
    EXCELLENT(5),
    GOOD(4),
    SATISFACTORY(3),
    PASSED(0),
    FAILED(0);

    private final int score;

    Grade(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public boolean isExcellent() {
        return this == EXCELLENT;
    }

    public boolean isSatisfactory() {
        return this == SATISFACTORY;
    }
}
