package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class Heartbeat  extends DataTransferObject {
    public Heartbeat(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public Heartbeat(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }
}
