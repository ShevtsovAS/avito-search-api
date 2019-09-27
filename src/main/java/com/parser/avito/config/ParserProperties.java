package com.parser.avito.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "search-service.parser")
public class ParserProperties {
    private String itemTableClass;
    private String itemLinkClass;
    private String itemLinkAttr;
    private String itemPriceClass;
    private String itemDateClass;
    private String itemDateAttr;
    private String itemDataClass;
    private String itemVipListClass;
    private String itemVipListAttr;
    private String itemIsUppedProp;
}
