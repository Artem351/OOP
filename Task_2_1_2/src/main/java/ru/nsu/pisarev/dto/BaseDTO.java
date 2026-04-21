package ru.nsu.pisarev;

import java.io.Serializable;
import java.util.UUID;

public class BaseDTO implements Serializable {
    private String taskId;
    private int workerId;

    private long timestamp;
    private String messageId;
    private String ackForMessageId;
    private int retryCount;

    /**
     * Constructor for maintenance messages
     */
    public BaseDTO(String taskId, int workerId) {
        this.taskId = taskId;
        this.workerId = workerId;
        this.timestamp = System.currentTimeMillis();
        this.messageId = UUID.randomUUID().toString();
        this.retryCount = 0;
    }

    /**
     * Constructor for results
     */
    public BaseDTO(String taskId, int workerId, boolean isNonPrime, int number) {
        this.taskId = taskId;
        this.workerId = workerId;
        this.isNonPrime = isNonPrime;
        this.number = number;
        this.timestamp = System.currentTimeMillis();
        this.messageId = UUID.randomUUID().toString();
        this.retryCount = 0;
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

    public Boolean getNonPrime() {
        return isNonPrime;
    }
    public void setNonPrime(Boolean nonPrime) {
        isNonPrime = nonPrime;
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
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getAckForMessageId() {
        return ackForMessageId;
    }
    public void setAckForMessageId(String ackForMessageId) {
        this.ackForMessageId = ackForMessageId;
    }

    public int getRetryCount() {
        return retryCount;
    }
    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public DataTransferObject withRetry() {
        this.retryCount++;
        return this;
    }
}