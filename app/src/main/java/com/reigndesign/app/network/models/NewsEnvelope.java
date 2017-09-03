package com.reigndesign.app.network.models;

import java.util.List;

public class NewsEnvelope {
    private List<New> hits;
    private String query;
    private String params;

    public List<New> getHits() {
        return hits;
    }

    public void setHits(List<New> hits) {
        this.hits = hits;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
