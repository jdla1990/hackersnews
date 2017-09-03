package com.reigndesign.app.network;


import com.reigndesign.app.network.models.NewsEnvelope;

import retrofit2.Call;
import retrofit2.http.GET;

public interface News {

    @GET("search_by_date?query=android")
    Call<NewsEnvelope> getNewsByDate();
}
