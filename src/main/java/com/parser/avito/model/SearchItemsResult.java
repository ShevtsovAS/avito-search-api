package com.parser.avito.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchItemsResult {
    private String searchUrl;
    private boolean success;
    private String query;
    private String errorMessage;
    private Integer total;
    private Integer count;
    private List<Item> items;
}
