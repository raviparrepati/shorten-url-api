package com.neueda.assignment.shortenurlapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neueda.assignment.shortenurlapi.dto.UrlInputRequest;
import com.neueda.assignment.shortenurlapi.service.ShortenUrlService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UrlControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ShortenUrlService shortenUrlService;


    @Test
    public void test_FullUrl_StatusOk() throws Exception {
        UrlInputRequest fullUrl = new UrlInputRequest("https://fullurl.com/foo");

        mvc.perform(post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk());
    }

    @Test
    public void test_FullUrl_shortUrl() throws Exception {
        UrlInputRequest fullUrlObj = new UrlInputRequest("https://fullurl.com/foo");

        mvc.perform(post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrlObj)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").exists());
    }


    @Test
    public void test_2SameFullUrl_Exception() throws Exception {
        UrlInputRequest fullUrl = new UrlInputRequest("https://fullurl.com/foo");

        String shortUrl1 = mvc.perform(post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        Assert.assertEquals(shortUrl1, shortUrl2);
    }

    @Test
    public void test_2DifferentFullUrl_Success() throws Exception {
        UrlInputRequest fullUrl1 = new UrlInputRequest("https://fullurl.com/foo1");
        UrlInputRequest fullUrl2 = new UrlInputRequest("https://fullurl.com/foo2");

        String shortUrl1 = mvc.perform(post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        String shortUrl2 = mvc.perform(post("/shortenUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(fullUrl2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl", startsWith("http"))).andReturn().getResponse().getContentAsString();

        Assert.assertNotEquals(shortUrl1, shortUrl2);
    }

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(obj);
    }

}