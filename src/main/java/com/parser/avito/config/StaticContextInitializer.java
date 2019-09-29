package com.parser.avito.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.avito.util.ElementMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@RequiredArgsConstructor
public class StaticContextInitializer {

    private final ParserProperties parserProperties;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        ElementMapper.setParserProperties(parserProperties);
        ElementMapper.setMapper(objectMapper);
    }
}
