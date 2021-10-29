package com.mycompany.mszczepienia.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VisitStatus {

    PENDING("PENDING"),
    FINISHED("FINISHED"),
    MISSED("MISSED"),
    CANCELLED("CANCELLED");

    public final String status;
}
