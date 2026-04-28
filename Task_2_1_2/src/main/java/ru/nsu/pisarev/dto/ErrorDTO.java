package ru.nsu.pisarev.dto;


public class ErrorDTO extends BaseDTO {

    private String errorMessage;
    public ErrorDTO(String taskId, int workerId) {
        super(taskId, workerId);
    }

    public ErrorDTO(String taskId, int workerId, String errorMessage) {
        super(taskId, workerId);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}