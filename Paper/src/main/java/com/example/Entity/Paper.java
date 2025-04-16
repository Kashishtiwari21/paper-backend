package com.example.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "papers")
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    private int paperId;

    private int authorId;
    private String title;
    private String content;
    private String coAuthor;
    private boolean acceptance;
    private String details;
    private String topic;

    public Paper() {
    }

    public Paper(int authorId, String title, String details, String content, String coAuthor,
                 boolean acceptance, String topic) {
        this.authorId = authorId;
        this.title = title;
        this.details = details;
        this.content = content;
        this.coAuthor = coAuthor;
        this.acceptance = acceptance;
        this.topic = topic;
    }

    // Getters and Setters

    public int getPaperId() {
        return paperId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoAuthor() {
        return coAuthor;
    }

    public void setCoAuthor(String coAuthor) {
        this.coAuthor = coAuthor;
    }

    public boolean isAcceptance() {
        return acceptance;
    }

    public void setAcceptance(boolean acceptance) {
        this.acceptance = acceptance;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Paper [paperId=" + paperId + ", authorId=" + authorId + ", title=" + title +
                ", details=" + details + ", content=" + content +
                ", coAuthor=" + coAuthor + ", acceptance=" + acceptance + ", topic=" + topic + "]";
    }
}
