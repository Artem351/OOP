package ru.nsu.pisarev.dto;

import java.io.Serializable;
import java.util.UUID;

public class BaseDTO implements Serializable {

    private String taskId;
    private int workerId;
    private long timestamp;
    private String messageId;

    public BaseDTO(String taskId, int workerId) {
        this.taskId = taskId;
        this.workerId = workerId;
        this.timestamp = System.currentTimeMillis();
        this.messageId = UUID.randomUUID().toString();
    }

    protected BaseDTO() {
        this.messageId = UUID.randomUUID().toString();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getWorkerId() {
        return workerId;
    }

    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }
}