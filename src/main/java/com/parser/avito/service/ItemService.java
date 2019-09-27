package com.parser.avito.service;

import com.parser.avito.model.Item;

import java.util.List;

public interface ItemService {
    List<Item> searchItems(String url);
}
