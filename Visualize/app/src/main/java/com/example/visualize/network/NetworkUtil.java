package com.example.visualize.network;

import java.io.IOException;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkUtil {

    private static final MediaType JSON;
    private static final OkHttpClient CLIENT;
    private static final Gson GSON;
    private static final String SERVER_URL;
    private static final int SERVER_PORT;

    static {
        JSON = MediaType.parse("application/json; charset=utf-8");
        CLIENT = new OkHttpClient();
        GSON = new Gson();
        SERVER_URL = "192.168.1.109";
        SERVER_PORT = 1234;
    }

    private NetworkUtil() {
    }


    public static String get(String uri) {
        String url = String.format("http://%s:%d/%s", SERVER_URL, SERVER_PORT, uri);
        Request request = new Request.Builder().url(url).build();

        try {
            Response response = CLIENT.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String post(String uri, String json) {
        String url = String.format("http://%s:%d/%s", SERVER_URL, SERVER_PORT, uri);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();

        try {
            Response response = CLIENT.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Gson getGson() {
        return GSON;
    }

    public static String hash(String s) {
        return s;
    }

}
