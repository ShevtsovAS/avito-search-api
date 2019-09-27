package com.parser.avito.controller;

import com.parser.avito.model.Item;
import com.parser.avito.model.SearchItemsResult;
import com.parser.avito.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/api/v1/avito")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{city}")
    public ResponseEntity<SearchItemsResult> searchItems(@PathVariable String city,
                                                         @RequestParam MultiValueMap<String, String> params) {

        String url = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("www.avito.ru")
                .pathSegment("{city}")
                .queryParams(params)
                .buildAndExpand(city)
                .encode(UTF_8)
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
