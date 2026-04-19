package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class Shutdown  extends DataTransferObject {
    public Shutdown(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public Shutdown(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }
}
