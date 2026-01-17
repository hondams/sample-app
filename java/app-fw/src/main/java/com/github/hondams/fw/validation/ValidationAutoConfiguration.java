package com.github.hondams.fw.validation;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

@AutoConfiguration
@RequiredArgsConstructor
public class ValidationAutoConfiguration {

    private final ResourcePatternResolver resourcePatternResolver;

    @PostConstruct
    public void init() throws IOException {

        Resource[] resources = this.resourcePatternResolver
                .getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + "/ValidationMessages.properties");

        switch (resources.length) {
            case 0:
                throw new IllegalStateException(
                        "ValidationMessages.properties not found in classpath.");
            case 1:
                // Only one resource found, no action needed.
                break;
            default:
                throw new IllegalStateException(
                        "Multiple ValidationMessages.properties found in classpath. "//
                                + List.of(resources));
        }
    }
}
