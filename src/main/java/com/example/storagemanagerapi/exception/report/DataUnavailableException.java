package com.example.storagemanagerapi.exception.report;

public class DataUnavailableException extends RuntimeException {
    public DataUnavailableException(String reportType) {
        super("No data available to generate the " + reportType);
    }
}
