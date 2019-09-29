package com.parser.avito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.avito.model.SearchItemsResult;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerTestIT {

    private static final String TEST_URI_1 = "/api/v1/avito/sankt-peterburg?q=игры&user=1&pmax=10000";
    private static final String TEST_URI_2 = "/api/v1/avito/sankt-peterburg/igry_pristavki_i_programmy?q=игры&user=1&pmax=10000";
    private static final String TEST_URI_3 = "/api/v1/avito/sankt-peterburg?q=blablablaforemtyresult&user=1&pmax=10000";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void searchItems() throws Exception {
        MvcResult result = mvc.perform(get(TEST_URI_1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        checkResult(result);
    }

    @Test
    public void searchItemsByCategory() throws Exception {
        MvcResult result = mvc.perform(get(TEST_URI_2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();

        checkResult(result);
    }

    @Test
    public void searchItemsNotFound() throws Exception {
        mvc.perform(get(TEST_URI_3))
                .andExpect(status().isNotFound());
    }

    private void checkResult(MvcResult result) throws Exception {
        String content = result.getResponse().getContentAsString();
        assertTrue(StringUtils.isNotBlank(content));

        SearchItemsResult searchItemsResult = mapper.readValue(content, SearchItemsResult.class);
        assertNotNull(searchItemsResult);

        assertTrue(StringUtils.isNotBlank(searchItemsResult.getSearchUrl()));
        assertTrue(searchItemsResult.isSuccess());
        assertTrue(StringUtils.isNotBlank(searchItemsResult.getQuery()));
        assertNull(searchItemsResult.getErrorMessage());
        assertNotNull(searchItemsResult.getTotal());
        assertTrue(searchItemsResult.getTotal() > 0);
        assertNotNull(searchItemsResult.getCount());
        assertTrue(searchItemsResult.getCount() > 0);
        assertNotNull(searchItemsResult.getItems());
        assertFalse(searchItemsResult.getItems().isEmpty());
    }
}