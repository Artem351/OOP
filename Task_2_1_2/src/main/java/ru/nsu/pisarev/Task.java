package ru.nsu.pisarev;

public class Task {
    private final String id;
    private final int number;
    private final int index;
    private int assignedWorker = -1;
    private long assignedTime;
    private int attempts = 0;

    public Task(String id, int number, int index) {
        this.id = id;
        this.number = number;
        this.index = index;
    }


    public String getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getIndex() {
        return index;
    }

    public int getAssignedWorker() {
        return assignedWorker;
    }

    public void setAssignedWorker(int assignedWorker) {
        this.assignedWorker = assignedWorker;
    }

    public long getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(long assignedTime) {
        this.assignedTime = assignedTime;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}