package com.parser.avito.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private String title;
    private String link;
    private String price;
    private String dateTime;
    private String category;
    private String location;
}
