package com.suyog.flowforge.execution.domain;

public enum TaskStatus {
    BLOCKED,
    READY,
    RUNNING,
    RETRY_WAIT,
    SUCCEEDED,
    FAILED,
    SKIPPED
}