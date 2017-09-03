package com.reigndesign.app.network.models;


import com.google.gson.annotations.SerializedName;


public class New {

    @SerializedName("created_at_i")
    private long createdAtEpoch;
    @SerializedName("title")
    private String title;
    @SerializedName("story_id")
    private String storyId;
    @SerializedName("story_title")
    private String storyTitle;
    @SerializedName("story_url")
    private String storyUrl;
    @SerializedName("author")
    private String author;
    @SerializedName("comment_text")
    private String comment;


    public String getTitle() {
        return title;
    }

    public String getStoryId() {
        return storyId;
    }

    public String getStoryTitle() {
        return storyTitle;
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

    public long getCreatedAtEpoch() {
        return createdAtEpoch;
    }
}
