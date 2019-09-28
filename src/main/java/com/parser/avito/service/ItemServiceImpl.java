package com.parser.avito.service;

import com.parser.avito.exception.ItemServiceException;
import com.parser.avito.model.Item;
import com.parser.avito.util.ElementMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.parser.avito.util.ElementMapper.ABS_HREF_ATTR;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    @Value("${search-service.parser.item-table-class}")
    private String itemTable;

    @Value("${search-service.parser.next-page-class}")
    private String nextPageClass;

    @Value("${search-service.parser.max-pages:1}")
    private int maxPages;

    @Override
    public List<Item> searchItems(String url) {

        try {
            List<Item> result = new ArrayList<>();

            int pages = 0;
            do {
                Document document = Jsoup.connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.75 Safari/537.36")
                        .referrer("http://www.google.com")
                        .timeout(10000)
                        .get();

                result.addAll(document
                        .getElementsByClass(itemTable).stream()
                        .map(ElementMapper::getItem)
                        .filter(item -> !item.isUpped())
                        .collect(toList()));

                url = document.getElementsByClass(nextPageClass).attr(ABS_HREF_ATTR);
                pages++;
            } while (pages < maxPages && !StringUtils.isEmpty(url));

            return result;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ItemServiceException(e.getMessage());
        }
    }
}
