package com.parser.avito.service;

import com.parser.avito.model.SearchItemsResult;
import org.springframework.util.MultiValueMap;

public interface ItemService {
    SearchItemsResult searchItems(String city, String category, MultiValueMap<String, String> params);
}
