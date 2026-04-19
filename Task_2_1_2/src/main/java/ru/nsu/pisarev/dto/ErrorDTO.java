package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class ErrorDTO extends DataTransferObject {
    public ErrorDTO(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public ErrorDTO(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }
}
