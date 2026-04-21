package ru.nsu.pisarev.dto;

import ru.nsu.pisarev.DataTransferObject;

public class Result  extends BaseDTO {
    /**
     * number to check
     */
    private int number;
    private Boolean isNonPrime;

    public Result(String taskId, int workerId, boolean isNonPrime, int number) {
        super(taskId, workerId, isNonPrime, number);
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

}
