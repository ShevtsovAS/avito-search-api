package com.parser.avito.controller;

import com.parser.avito.config.SearchServiceProperties;
import com.parser.avito.model.Item;
import com.parser.avito.model.SearchItemsResult;
import com.parser.avito.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/v1/avito")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final SearchServiceProperties searchServiceProperties;

    @GetMapping({"/{city}", "/{city}/{category}"})
    public ResponseEntity<SearchItemsResult> searchItems(@PathVariable String city,
                                                         @PathVariable(required = false) String category,
                                                         @RequestParam MultiValueMap<String, String> params) {

        String url = UriComponentsBuilder.newInstance()
                .scheme(searchServiceProperties.getScheme())
                .host(searchServiceProperties.getHost())
                .port(searchServiceProperties.getPort())
                .pathSegment("{city}", "{category}")
                .queryParams(params)
                .buildAndExpand(city, category)
                .encode(searchServiceProperties.getEncode())
                .toString();

        List<Item> items = itemService.searchItems(url);

        SearchItemsResult result = SearchItemsResult.builder()
                .searchUrl(url)
                .items(items)
                .total(items.size())
                .success(true)
                .build();

        return ResponseEntity.ok(result);
    }
}
