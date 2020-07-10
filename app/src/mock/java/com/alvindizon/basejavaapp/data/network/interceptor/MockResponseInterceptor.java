package com.alvindizon.basejavaapp.data.network.interceptor;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

@Singleton
public class MockResponseInterceptor implements Interceptor {

    private final Context context;

    @Inject
    public MockResponseInterceptor(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();

        String mockResponse;
        String fileName;

        // TODO add mock JSON responses in your mock's assets folders
        if(url.contains("someQuery")) {
            fileName = "sample_response.json";
        } else if(url.contains("Login")) {
            fileName = "login_response.json";
        } else {
            throw new ConnectException();
        }

        try {
            mockResponse = getStringFromFile(fileName);
        } catch (Exception e) {
            throw new IOException(e);
        }

        return new Response.Builder()
                .code(200)
                .message(mockResponse)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), mockResponse.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
    }

    private String getStringFromFile(String fileName) throws Exception {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = context.getAssets().open(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))){
            String line;
            while((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return sb.toString();
    }
}
