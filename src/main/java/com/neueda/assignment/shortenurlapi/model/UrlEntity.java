package com.neueda.assignment.shortenurlapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "url")
@Table(name = "url")
public class UrlEntity {

    private Long id;

    @Column(name = "full_url")
    private String fullUrl;

    @Column(name = "short_url")
    private String shortUrl;
    
    @Column(nullable = false)
    private Date createdDate;

    private Date expiresDate;

    public UrlEntity() {
    }

    public UrlEntity(Long id, String fullUrl, String shortUrl) {
        this.id = id;
        this.fullUrl = fullUrl;
        this.shortUrl = shortUrl;
    }

    public UrlEntity(String fullUrl, Date createdDate, Date expiresDate) {
        this.fullUrl = fullUrl;
        this.createdDate = createdDate;
        this.expiresDate = expiresDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullUrl() {
        return fullUrl;
    }

    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getExpiresDate() {
		return expiresDate;
	}

	public void setExpiresDate(Date expiresDate) {
		this.expiresDate = expiresDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UrlEntity [id=");
		builder.append(id);
		builder.append(", fullUrl=");
		builder.append(fullUrl);
		builder.append(", shortUrl=");
		builder.append(shortUrl);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", expiresDate=");
		builder.append(expiresDate);
		builder.append("]");
		return builder.toString();
	}
}
