package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class AssignTask extends DataTransferObject {
    private int sequenceNumber;

    public AssignTask(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
}
