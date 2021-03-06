package com.neueda.assignment.shortenurlapi.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neueda.assignment.shortenurlapi.common.ConversionUtils;
import com.neueda.assignment.shortenurlapi.dto.UrlInputRequest;
import com.neueda.assignment.shortenurlapi.model.UrlEntity;
import com.neueda.assignment.shortenurlapi.repository.UrlRepository;

@Service
public class ShortenUrlService {

    Logger logger = LoggerFactory.getLogger(ShortenUrlService.class);

    private final UrlRepository urlRepository;

    @Autowired
    public ShortenUrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private UrlEntity get(Long id) {
        logger.info(String.format("Fetching Url from database for Id %d", id));
        UrlEntity urlEntity = urlRepository.findById(id).get();
        return urlEntity;
    }

    public UrlInputRequest getFullUrl(String shortenString) {
        Long id = ConversionUtils.encode(shortenString);
        return new UrlInputRequest(this.get(id).getFullUrl());
    }

    private UrlEntity save(UrlInputRequest fullUrl) {
    	Date currentDate = new Date();
    	//Setting expire date as 24 hours
    	Date expireDate =  new Date(currentDate.getTime() + 86400000);
        return urlRepository.save(new UrlEntity(fullUrl.getFullUrl(), currentDate, expireDate));
    }

    /**
     * It saves the full url to database and uses the autogenerated id to convert to Base62 string
     */
    public String getShortUrl(UrlInputRequest fullUrl) {

         List<UrlEntity> savedUrls = checkFullUrlAlreadyExists(fullUrl);

        UrlEntity savedUrl = null;

        if (savedUrls.isEmpty()) {
            savedUrl = this.save(fullUrl);
        }
        else {
            savedUrl = savedUrls.get(0);
            logger.info(String.format("url: %s already exists in the database. skipped insert", savedUrl));
        }

        String shortUrlText = ConversionUtils.decode(savedUrl.getId());
        logger.info(String.format("Converted Base 10 %d to Base 62 string %s", savedUrl.getId(), shortUrlText));

        return shortUrlText;
    }

    private List<UrlEntity> checkFullUrlAlreadyExists(UrlInputRequest fullUrl) {
        return urlRepository.findUrlByFullUrl(fullUrl.getFullUrl());
    }
}
