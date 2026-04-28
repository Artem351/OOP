package ru.nsu.pisarev.dto;


public class Result extends BaseDTO {

    private int number;
    private Boolean isNonPrime;
    private int sequenceNumber;


    public Result(String taskId, int workerId, int number, boolean isNonPrime) {
        super(taskId, workerId);
        this.number = number;
        this.isNonPrime = isNonPrime;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Boolean getNonPrime() {
        return isNonPrime;
    }

    public void setNonPrime(Boolean nonPrime) {
        isNonPrime = nonPrime;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

}
