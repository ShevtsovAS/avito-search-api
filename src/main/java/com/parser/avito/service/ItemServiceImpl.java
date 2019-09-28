package com.parser.avito.service;

import com.parser.avito.config.ParserProperties;
import com.parser.avito.config.SearchServiceProperties;
import com.parser.avito.exception.ItemServiceException;
import com.parser.avito.model.Item;
import com.parser.avito.model.SearchItemsResult;
import com.parser.avito.util.ElementMapper;
import com.parser.avito.util.NumberUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private static final String[] PATH_SEGMENTS = new String[]{"{city}", "{category}"};
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.75 Safari/537.36";
    private static final String REFERER = "http://www.google.com";
    private static final String QUERY_PARAM = "q";
    private static final String NOT_FOUND_MESSAGE = "Ничего не найденно!";

    private final SearchServiceProperties searchServiceProperties;
    private final ParserProperties parserProperties;

    @Override
    public SearchItemsResult searchItems(String city, String category, MultiValueMap<String, String> params) {
        String url = getSearchUrl(city, category, params);
        String query = getQuery(params);

        try {
            Document document = getDocument(url);
            List<Item> items = getItems(document);
            return SearchItemsResult.builder()
                    .success(true)
                    .searchUrl(url)
                    .query(query)
                    .total(getResultCount(document))
                    .count(items.size())
                    .items(items)
                    .build();
        } catch (ItemServiceException e) {
            return getBadRequestResult(url, query, e.getLocalizedMessage());
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 404) {
                return getNotFoundResult(url, query);
            } else {
                return getBadRequestResult(url, query, e.getLocalizedMessage());
            }
        }
    }

    private SearchItemsResult getNotFoundResult(String url, String query) {
        return SearchItemsResult.builder()
                .searchUrl(url)
                .success(false)
                .errorMessage(NOT_FOUND_MESSAGE)
                .query(query)
                .total(0)
                .count(0)
                .build();
    }

    private SearchItemsResult getBadRequestResult(String url, String query, String errorMessage) {
        return SearchItemsResult.builder()
                .searchUrl(url)
                .query(query)
                .errorMessage(errorMessage)
                .build();
    }

    private String getQuery(MultiValueMap<String, String> params) {
        return Optional.ofNullable(params.get(QUERY_PARAM))
                .map(values -> String.join(" ", values))
                .orElse(EMPTY);
    }

    private int getResultCount(Document document) {
        return NumberUtils.ParseInt(document.getElementsByClass(parserProperties.getItemCountClass()).text());
    }

    private List<Item> getItems(Document document) {
        return document
                .getElementsByClass(parserProperties.getItemTableClass())
                .stream()
                .map(ElementMapper::getItem)
                .filter(item -> !item.isUpped())
                .collect(toList());
    }

    private Document getDocument(String url) throws HttpStatusException {
        try {
            return Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .referrer(REFERER)
                    .timeout(parserProperties.getTimeOut())
                    .get();
        } catch (HttpStatusException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new ItemServiceException(e.getMessage());
        }
    }

    private String getSearchUrl(String city, String category, MultiValueMap<String, String> params) {
        return UriComponentsBuilder.newInstance()
                .scheme(searchServiceProperties.getScheme())
                .host(searchServiceProperties.getHost())
                .port(searchServiceProperties.getPort())
                .pathSegment(PATH_SEGMENTS)
                .queryParams(params)
                .buildAndExpand(city, category)
                .encode(searchServiceProperties.getEncode())
                .toString();
    }
}
