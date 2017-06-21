package com.gint.app.bisis4.client.retrofit;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.http.*;

import java.io.IOException;

/**
 * Created by Petar on 6/15/2017.
 */
public class AuthenticationInterceptor implements Interceptor {

    private String authToken;

    public AuthenticationInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder = original.newBuilder()
                .header("x-auth-token", authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}