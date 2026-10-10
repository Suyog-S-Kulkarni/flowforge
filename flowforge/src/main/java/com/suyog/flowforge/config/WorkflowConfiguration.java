package com.suyog.flowforge.config;

import com.suyog.flowforge.workflow.domain.WorkflowValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class WorkflowConfiguration {

    @Bean
    public WorkflowValidator workflowValidator() {
        return new WorkflowValidator();
    }
}