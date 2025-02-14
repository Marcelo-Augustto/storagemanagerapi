package com.example.storagemanagerapi.exception.report;

public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(Long reportId) {
        super("Report with ID " + reportId + " not found");
    }
}
