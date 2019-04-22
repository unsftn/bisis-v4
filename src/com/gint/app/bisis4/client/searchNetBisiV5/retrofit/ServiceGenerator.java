package com.gint.app.bisis4.client.searchNetBisiV5.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
	private static final String BASE_URL = "https://app.bisis.rs/bisisWS/";
	private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();


    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();
    public static void addAuthInterceptor(String authToken){
        httpClient.addInterceptor(new AuthInterceptor(authToken));
        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public static <S> S createService(
        Class<S> serviceClass) {
            builder.client(httpClient.build());
            retrofit = builder.build();

        return retrofit.create(serviceClass);
    }

}
