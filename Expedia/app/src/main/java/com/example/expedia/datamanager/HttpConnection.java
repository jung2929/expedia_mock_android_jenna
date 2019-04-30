package com.example.expedia.datamanager;

import android.content.Context;

import com.example.expedia.MyApplication;
import com.example.expedia.R;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpConnection {
    private OkHttpClient client;
    private Context context;
    private String url, type, json;
    private Request request;

    public HttpConnection(Context context, String url, String type){
        this.client = new OkHttpClient();
        this.context = context;
        this.url = url;
        this.type = type;
    }

    public HttpConnection(Context context, String url, String type, String json){
        this.client = new OkHttpClient();
        this.context = context;
        this.url = url;
        this.type = type;
        this.json = json;
    }

    public void requestWebServer(Callback callback){
        String baseUrl = context.getResources().getString(R.string.base_url);
        String fullUrl =  baseUrl + url;

        if(MyApplication.isLogInStatus()) {
            switch (type) {
                case "get":
                    request = new Request.Builder()
                            .addHeader("x-access-token", MyApplication.getToken())
                            .addHeader("Content-Type", "application/json")
                            .url(fullUrl)
                            .build();
                    break;

                case "post":
                    request = new Request.Builder()
                            .header("x-access-token", MyApplication.getToken())
                            .url(fullUrl)
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    break;

                case "delete":
                    if (json == null){
                        request = new Request.Builder()
                                .addHeader("x-access-token", MyApplication.getToken())
                                .url(fullUrl)
                                .delete()
                                .build();
                        break;
                    }
                    request = new Request.Builder()
                            .addHeader("x-access-token", MyApplication.getToken())
                            .url(fullUrl)
                            .delete(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    break;

            }
        }else {
            switch (type) {
                case "get":
                    request = new Request.Builder()
                            .addHeader("Content-Type", "application/json")
                            .url(fullUrl)
                            .build();
                    break;

                case "post":
                    request = new Request.Builder()
                            .url(fullUrl)
                            .post(RequestBody.create(MediaType.parse("application/json"), json))
                            .build();
                    break;
            }
        }
        client.newCall(request).enqueue(callback);
    }
}
