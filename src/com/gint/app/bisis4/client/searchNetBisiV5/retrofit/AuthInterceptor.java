package com.gint.app.bisis4.client.searchNetBisiV5.retrofit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthInterceptor implements Interceptor {
	private String authToken;
	public AuthInterceptor(String token){
		this.authToken = token;
	}
	public Response intercept(Chain chain) throws IOException {
		Request original = chain.request();
        Request request = original.newBuilder()
            .header("Authorization", this.authToken)
            .method(original.method(), original.body())
            .build();

        return chain.proceed(request);
		
	}
	
}
