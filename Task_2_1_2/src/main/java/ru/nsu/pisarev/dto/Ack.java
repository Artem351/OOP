package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class Ack extends DataTransferObject {

    public Ack(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public Ack(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }

}
