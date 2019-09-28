package com.parser.avito.controller;

import com.parser.avito.model.SearchItemsResult;
import com.parser.avito.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1/avito")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping({"/{city}", "/{city}/{category}"})
    public ResponseEntity<SearchItemsResult> searchItems(@PathVariable String city,
                                                         @PathVariable(required = false) String category,
                                                         @RequestParam MultiValueMap<String, String> params) {
        SearchItemsResult result = itemService.searchItems(city, category, params);
        HttpStatus status = result.isSuccess() ? OK : result.getTotal() == null ? BAD_REQUEST : NOT_FOUND;
        return ResponseEntity.status(status).body(result);
    }
}
