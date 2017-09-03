package com.reigndesign.app.network;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    protected static final String BASE_SERVICE = "https://hn.algolia.com/api/v1/";
    private static HttpLoggingInterceptor httpLoggingInterceptor =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);


    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_SERVICE)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, String authToken) {
        if (!httpClient.interceptors().contains(httpLoggingInterceptor)) {
            httpClient.addInterceptor(httpLoggingInterceptor);

            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
