package com.suyog.flowforge.api;

import com.suyog.flowforge.api.dto.WorkflowExecutionResponse;
import com.suyog.flowforge.api.dto.WorkflowValidationRequest;
import com.suyog.flowforge.execution.application.WorkflowExecutionService;
import com.suyog.flowforge.execution.domain.WorkflowExecutionReport;
import com.suyog.flowforge.workflow.domain.TaskDefinition;
import com.suyog.flowforge.workflow.domain.WorkflowDefinition;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/workflows")
public class WorkflowExecutionController {

    private final WorkflowExecutionService service;

    public WorkflowExecutionController(
            WorkflowExecutionService service
    ) {
        this.service = service;
    }

    @PostMapping("/execute-preview")
    public WorkflowExecutionResponse executePreview(
            @Valid @RequestBody WorkflowValidationRequest request
    ) throws InterruptedException {

        List<TaskDefinition> tasks = request.tasks().stream()
                .map(task -> new TaskDefinition(
                        task.id(),
                        task.type(),
                        task.dependsOn()
                ))
                .toList();

        WorkflowDefinition workflow = new WorkflowDefinition(
                request.name(),
                tasks
        );

        WorkflowExecutionReport report =
                service.executePreview(workflow);

        return new WorkflowExecutionResponse(
                report.workflowName(),
                report.succeeded(),
                report.taskStatuses(),
                report.attemptCounts()
        );
    }
}