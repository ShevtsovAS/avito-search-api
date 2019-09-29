package com.parser.avito.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "search-service.parser")
public class ParserProperties {
    private String nextPageClass;
    private String tableClass;
    private String linkClass;
    private String priceClass;
    private String dateClass;
    private String dateAttr;
    private String dataClass;
    private String vipListClass;
    private String vipListAttr;
    private String isUppedProp;
    private String countClass;
}
