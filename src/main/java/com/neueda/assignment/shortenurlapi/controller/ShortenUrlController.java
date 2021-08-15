package com.neueda.assignment.shortenurlapi.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.neueda.assignment.shortenurlapi.common.BaseUrlExractor;
import com.neueda.assignment.shortenurlapi.dto.UrlInputRequest;
import com.neueda.assignment.shortenurlapi.service.ShortenUrlService;

@RestController
public class ShortenUrlController {

    Logger logger = LoggerFactory.getLogger(ShortenUrlController.class);

    protected final ShortenUrlService shortenUrlService;

    @Autowired
    public ShortenUrlController(ShortenUrlService urlService) {
        this.shortenUrlService = urlService;
    }

    @PostMapping("/shortenUrl")
    public ResponseEntity<Object> convertAndSaveShortUrl(@RequestBody UrlInputRequest fullUrl, HttpServletRequest request) {

        UrlValidator validator = new UrlValidator(
                new String[]{"http", "https"}
        );
        
        String url = fullUrl.getFullUrl();
        if (!validator.isValid(url)) {
            logger.error("Malformed Url provided");
            return ResponseEntity.badRequest().body(new RuntimeException("url : " + fullUrl.getFullUrl() + " is Invalid URL"));
        }
        String baseUrl = null;

        try {
            baseUrl = BaseUrlExractor.extract(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request url is invalid", e);
        }
        String shortUrl = shortenUrlService.getShortUrl(fullUrl);
        logger.debug(String.format("ShortUrl for FullUrl %s is %s", fullUrl.getFullUrl(), shortUrl));

        return new ResponseEntity<>(baseUrl + shortUrl, HttpStatus.OK);
    }

    @GetMapping("/{shortenUrl}")
    public void callFullUrl(HttpServletResponse response, @PathVariable String shortenUrl) {
        try {
            UrlInputRequest fullUrl = shortenUrlService.getFullUrl(shortenUrl);
            logger.info(String.format("Redirecting to %s", fullUrl.getFullUrl()));
            response.sendRedirect(fullUrl.getFullUrl());
        } catch (NoSuchElementException e) {
            logger.error(String.format("No URL found for %s in the db", shortenUrl));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Url not found", e);
        } catch (IOException e) {
            logger.error("Could not redirect to the full url");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not redirect to the full url", e);
        }
    }

}
