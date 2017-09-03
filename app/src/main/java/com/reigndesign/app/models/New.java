package com.reigndesign.app.models;


import java.util.Date;

public class New {
    private final int idDb;
    private final boolean visible;
    private final long createdAt;
    private final String title;
    private final String storyId;
    private final String storyUrl;
    private final String author;
    private final String comment;

    public New(long createdAt, String storyId, int idDb, String title,
               String storyUrl, String author, String comment, Boolean visible) {
        this.createdAt = createdAt;
        this.title = title;
        this.storyId = storyId;
        this.idDb = idDb;
        this.storyUrl = storyUrl;
        this.author = author;
        this.comment = comment;
        this.visible = visible;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getTitle() {
        return title;
    }

    public String getStoryId() {
        return storyId;
    }


    public String getStoryUrl() {
        return storyUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getComment() {
        return comment;
    }

    public int getIdDb() {
        return idDb;
    }

    public boolean isVisible() {
        return visible;
    }

    @Override
    public String toString() {
        return "New{" +
                "title='" + title + '\'' +
                ", idDb=" + idDb +
                ", author=" + author +
                ", storyId=" + storyId +
                '}';
    }
}
