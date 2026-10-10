package com.suyog.flowforge.handler;

import com.suyog.flowforge.workflow.domain.TaskDefinition;

@FunctionalInterface
public interface TaskHandler {

    void execute(TaskDefinition task) throws Exception;

    default void execute(
            TaskDefinition task,
            int attempt
    ) throws Exception {
        execute(task);
    }
}