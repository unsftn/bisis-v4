package com.gint.app.bisis4.client.retrofit;

import java.io.IOException;
import com.gint.app.bisis4.client.retrofit.model.UserCredentials;
import com.gint.app.bisis4.client.retrofit.services.MembersService;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitAPI {

	public RetrofitAPI() {}
	
	
		public static void main(String[] args) throws IOException{
			
			
		        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
		        String token = acquireToken();
				
		        okHttpClient.addInterceptor(new Interceptor() {
		            @Override
		            public okhttp3.Response intercept(Chain chain) throws IOException {

		                Request req = chain.request();
		                Request.Builder newRequest = req.newBuilder().header("Authorization", token);



		                return chain.proceed(newRequest.build());
		            }
		        });

		        Retrofit.Builder builder = new Retrofit.Builder()
		                .baseUrl("http://localhost:8080")
		                .client(okHttpClient.build())
		                .addConverterFactory(GsonConverterFactory.create());
		        Retrofit mainRetrofit = builder.build();
		        
		        MembersService bisisClient = mainRetrofit.create(MembersService.class);
		        Call<ResponseBody> mems = bisisClient.getMembers();

		        String m = mems.execute().body().string();
		        System.out.println(m);

		        Call<ResponseBody> libs = bisisClient.getLibs();
		        libs.enqueue(new Callback<ResponseBody>() {
		        	
		           
		            @Override
		            public void onFailure(Call<ResponseBody> call, Throwable t) {
		                    System.out.println("Nije dobio response!");
		            }

					@Override
					public void onResponse(Call<ResponseBody> arg0, retrofit2.Response<ResponseBody> response) {
						if (response.isSuccessful()){
		                    System.out.println("Uspesan response!");
		                    try {
		                        System.out.println(response.body().string());
		                    } catch (IOException e) {
		                        e.printStackTrace();
		                    }
		                    System.out.println("Server returns code: " + response.code());
		                }
		                else{
		                    System.out.println("Nesto je poslo po zlu!");
		                    System.out.println("Server returns code: " + response.code());
						
					}
					}
					});
		        
		        
			
		}
		
		private static String acquireToken() throws IOException {
			/*OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
			HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
			okHttpClient.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS));*/
			Retrofit rf = new Retrofit.Builder()
	                //.client(okHttpClient.build())
	                .baseUrl("http://localhost:8080")
	                .addConverterFactory(GsonConverterFactory.create())
	                .build();
	        
	        MembersService bs = rf.create(MembersService.class);


	        Call<ResponseBody> ans = bs.getToken(new UserCredentials("admin","admin"));
	        final String[] token = new String[1];
	        token[0] = ans.execute().body().string();
	        token[0] = token[0].split(":")[1];
	        token[0] = token[0].substring(1, token[0].length()-2);

	        return token[0];
	    }
	

}
