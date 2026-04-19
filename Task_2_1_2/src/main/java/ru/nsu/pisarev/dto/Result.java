package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class Result  extends DataTransferObject {
    public Result(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public Result(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }
}
