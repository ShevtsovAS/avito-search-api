package com.parser.avito.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.avito.config.ParserProperties;
import com.parser.avito.model.Item;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ElementMapperTest {

    private static final String TEST_BASE_URI = "https://www.avito.ru:443/sankt-peterburg?q=ps4&user=1&pmax=10000&s=104";
    private static final String TEST_ELEMENT_FILE = "test-element.html";

    @Test
    public void getItem() throws IOException {
        Element element = Jsoup.parse(new ClassPathResource(TEST_ELEMENT_FILE).getFile(), StandardCharsets.UTF_8.name());
        element.setBaseUri(TEST_BASE_URI);

        ObjectMapper mapper = new ObjectMapper();
        ParserProperties parserProperties = ParserProperties.builder()
                .tableClass("item_table")
                .linkClass("item-description-title-link")
                .dateClass("js-item-date")
                .dateAttr("data-absolute-date")
                .priceClass("price")
                .dataClass("data")
                .vipListClass("vas-list-container")
                .vipListAttr("data-config")
                .isUppedProp("isUpped")
                .nextPageClass("js-pagination-next")
                .countClass("page-title-count")
                .build();

        ElementMapper.setMapper(mapper);
        ElementMapper.setParserProperties(parserProperties);

        Item item = ElementMapper.getItem(element);
        assertNotNull(item);
        assertTrue(StringUtils.isNotBlank(item.getTitle()));
        assertTrue(StringUtils.isNotBlank(item.getLink()));
        assertTrue(StringUtils.isNotBlank(item.getCategory()));
        assertTrue(StringUtils.isNotBlank(item.getDateTime()));
        assertTrue(StringUtils.isNotBlank(item.getLocation()));
        assertTrue(StringUtils.isNotBlank(item.getPrice()));
        assertTrue(item.isUpped());
        assertNull(item.getAbsDateTime());
        assertThat(item.getLink(), is("https://www.avito.ru:443/sankt-peterburg/igry_pristavki_i_programmy/igry_ps3_i_dopolnitelnye_aksessuary_1771234397"));
    }
}