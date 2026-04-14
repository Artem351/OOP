package ru.nsu.pisarev;

import java.io.Serializable;
import java.util.UUID;

public class DataTransferObject implements Serializable {

    public enum Command {
        PING, PONG, ASSIGN_TASK, RESULT, HEARTBEAT, SHUTDOWN, ERROR, ACK
    }

    private Command command;
    private String taskId;
    private int workerId;

    /**
     * number to check
     */
    private int number;
    private Boolean isNonPrime;
    private long timestamp;
    private int sequenceNumber;
    private String messageId;
    private String ackForMessageId;
    private int retryCount;

    /**
     * Constructor for maintenance messages
     */
    public DataTransferObject(Command command, String taskId, int workerId) {
        this.command = command;
        this.taskId = taskId;
        this.workerId = workerId;
        this.timestamp = System.currentTimeMillis();
        this.messageId = UUID.randomUUID().toString();
        this.retryCount = 0;
    }

    /**
     * Constructor for results
     */
    public DataTransferObject(Command command, String taskId, int workerId, boolean isNonPrime, int number) {
        this.command = command;
        this.taskId = taskId;
        this.workerId = workerId;
        this.isNonPrime = isNonPrime;
        this.number = number;
        this.timestamp = System.currentTimeMillis();
        this.messageId = UUID.randomUUID().toString();
        this.retryCount = 0;
    }

    public Command getCommand() {
        return command;
    }
    public void setCommand(Command command) {
        this.command = command;
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

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
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

    public int getSequenceNumber() {
        return sequenceNumber;
    }
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
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