package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class Pong  extends DataTransferObject {
    public Pong(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public Pong(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }
}
