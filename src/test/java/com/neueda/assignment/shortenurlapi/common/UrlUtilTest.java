package com.neueda.assignment.shortenurlapi.common;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.net.MalformedURLException;

@RunWith(MockitoJUnitRunner.class)
public class UrlUtilTest {

    @Test(expected = MalformedURLException.class)
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithoutProtocol() throws MalformedURLException {
        BaseUrlExractor.extract("malformed url dummy text");
    }

    @Test(expected = MalformedURLException.class)
    public void shouldThrowExceptionWhenMalformedUrlSuppliedWithIllegalChars() throws MalformedURLException {
        BaseUrlExractor.extract("malformed://example.com/foo");
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithoutPort() throws MalformedURLException {
        Assert.assertEquals(BaseUrlExractor.extract("http://example.com/foo"), "http://example.com/");
    }

    @Test
    public void shouldReturnBaseUrlWhenValidUrlSuppliedWithPort() throws MalformedURLException {
        Assert.assertEquals(BaseUrlExractor.extract("http://example.com:8080/foo"), "http://example.com:8080/");
    }
}