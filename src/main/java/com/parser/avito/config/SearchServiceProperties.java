package com.parser.avito.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Data
@Component
@ConfigurationProperties(prefix = "search-service.config")
public class SearchServiceProperties {
    private String scheme;
    private String host;
    private int port = 443;
    private Charset encode = StandardCharsets.UTF_8;
}
