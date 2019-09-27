package com.parser.avito.service;

import com.parser.avito.model.Item;
import com.parser.avito.util.ElementMapper;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public List<Item> searchItems(String url) {

        try {
            return Jsoup.connect(url).get()
                    .getElementsByClass("item_table").stream()
                    .map(ElementMapper::getItem)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            // TODO...
            return Collections.emptyList();
        }
    }
}
