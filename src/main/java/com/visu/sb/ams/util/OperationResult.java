package com.visu.sb.ams.util;

public class OperationResult {

    private static final String OPERATION_PROCESSED_SUCCESSFULLY = "Operation processed successfully";

    private Status status;
    private String description;

    public OperationResult(Status status) {
        this(status, OPERATION_PROCESSED_SUCCESSFULLY);
    }

    public OperationResult(Status status, String description) {
        this.status = status;
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
