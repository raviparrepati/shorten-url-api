package com.neueda.assignment.shortenurlapi;

import com.neueda.assignment.shortenurlapi.service.ShortenUrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class shortenurlapiApplicationTests {

	@Autowired
	private ShortenUrlService urlService;

	@Test
	void contextLoads() {
	}

}
