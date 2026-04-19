package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class Ping  extends DataTransferObject {
    public Ping(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public Ping(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }
}
