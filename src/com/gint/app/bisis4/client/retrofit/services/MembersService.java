package com.gint.app.bisis4.client.retrofit.services;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import com.gint.app.bisis4.client.retrofit.model.UserCredentials;

import okhttp3.ResponseBody;
import retrofit2.Call;

public interface MembersService {
	
	@Headers({"ContentType: application/json"})
	@POST("/auth")
	Call<ResponseBody> getToken(@Body UserCredentials creds);

	@GET("/members")
	Call<ResponseBody> getMembers();
	
	@Headers({"ContentType: application/json"})
    @GET("/libraries")
    Call<ResponseBody> getLibs();

}
