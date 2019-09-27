package com.parser.avito.service;

import com.parser.avito.exception.ItemServiceException;
import com.parser.avito.model.Item;
import com.parser.avito.util.ElementMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public List<Item> searchItems(String url) {

        try {
            return Jsoup.connect(url).get()
                    .getElementsByClass("item_table").stream()
                    .map(ElementMapper::getItem)
                    .collect(toList());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ItemServiceException(e.getMessage());
        }
    }
}
