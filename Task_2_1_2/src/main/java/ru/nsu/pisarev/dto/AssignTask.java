package ru.nsu.pisarev.dto;


public class AssignTask extends BaseDTO {

    private int number;
    private int sequenceNumber;

    public AssignTask(String taskId, int workerId, int number, int sequenceNumber) {
        super(taskId, workerId);
        this.number = number;
        this.sequenceNumber = sequenceNumber;
    }
    public AssignTask(String taskId, int workerId) { super(taskId,workerId); }

    public int getNumber() { return number; }
    public void setNumber(int number) { this.number = number; }

    public int getSequenceNumber() { return sequenceNumber; }
    public void setSequenceNumber(int sequenceNumber) { this.sequenceNumber = sequenceNumber; }
}