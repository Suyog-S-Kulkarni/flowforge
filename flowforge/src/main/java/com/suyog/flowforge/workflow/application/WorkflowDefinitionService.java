package com.suyog.flowforge.workflow.application;

import com.suyog.flowforge.workflow.domain.WorkflowDefinition;
import com.suyog.flowforge.workflow.domain.WorkflowValidator;
import com.suyog.flowforge.workflow.domain.WorkflowVersion;
import com.suyog.flowforge.workflow.domain.WorkflowVersionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkflowDefinitionService {

    private final WorkflowValidator validator;
    private final WorkflowVersionRepository repository;

    public WorkflowDefinitionService(
            WorkflowValidator validator,
            WorkflowVersionRepository repository
    ) {
        this.validator = validator;
        this.repository = repository;
    }

    @Transactional
    public WorkflowVersion create(
            UUID tenantId,
            WorkflowDefinition definition
    ) {
        Objects.requireNonNull(tenantId, "Tenant ID must not be null");
        Objects.requireNonNull(definition, "Definition must not be null");

        validator.validateAndGetExecutionOrder(definition);

        WorkflowVersion version = new WorkflowVersion(
                UUID.randomUUID(),
                tenantId,
                UUID.randomUUID(),
                1,
                definition,
                Instant.now().truncatedTo(ChronoUnit.MICROS)
        );

        repository.insert(version);

        return version;
    }

    @Transactional(readOnly = true)
    public Optional<WorkflowVersion> findVersion(
            UUID tenantId,
            UUID workflowId,
            int version
    ) {
        return repository.findVersion(
                tenantId,
                workflowId,
                version
        );
    }
}