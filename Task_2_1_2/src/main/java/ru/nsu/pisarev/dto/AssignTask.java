package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class AssignTask extends DataTransferObject {
    public AssignTask(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public AssignTask(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }
}
