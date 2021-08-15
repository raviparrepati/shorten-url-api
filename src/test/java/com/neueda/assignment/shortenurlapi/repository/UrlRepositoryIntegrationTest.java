package com.neueda.assignment.shortenurlapi.repository;

import com.neueda.assignment.shortenurlapi.model.UrlEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UrlRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UrlRepository urlRepository;

    @Test
    public void shouldInsertAndGetFullurl() {
    	Date currentDate = new Date();
    	//Setting expire date as 24 hours
    	Date expireDate =  new Date(currentDate.getTime() + 86400000);
        UrlEntity urlEntity = new UrlEntity("http://fullurl.com",currentDate, expireDate);
        urlRepository.save(urlEntity);

        assertThat(urlEntity.getId(), notNullValue());

        UrlEntity urlEntityFromDb = urlRepository.findById(urlEntity.getId()).get();
        assertThat(urlEntityFromDb.getId(), equalTo(urlEntity.getId()));
    }

}