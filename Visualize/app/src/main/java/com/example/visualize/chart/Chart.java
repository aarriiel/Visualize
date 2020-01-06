package com.example.visualize.chart;

import com.example.visualize.network.Response;
import com.example.visualize.user.User;
import com.example.visualize.network.NetworkUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Chart {

    private final String name;
    private final String createTime;
    private final String xAxis;
    private final String yAxis;

    public Chart(String name, String createTime, String xAxis, String yAxis) {
        this.name = name;
        this.createTime = createTime;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
    }

    public static Response create(String userName, String chartName, String xAxis, String yAxis) {
        String uri = String.format("user/%s/charts/create", userName);

        JsonObject json = new JsonObject();
        json.addProperty("chartName", chartName);
        json.addProperty("xAxis", xAxis);
        json.addProperty("yAxis", yAxis);

        return new Response(NetworkUtil.post(uri, json.toString()));
    }

    public static Response get(String username) {
        String uri = String.format("user/%s/charts", username);
        return new Response(NetworkUtil.get(uri));
    }

    public static List<Chart> getCharts(User user) {
        List<Chart> charts = new ArrayList<>();

        try {
            Response query = Chart.get(user.getUsername());
            if (query.success()) {
                String raw = NetworkUtil.getGson().toJson(query.getContent().get("charts"));
                JsonArray json = NetworkUtil.getGson().fromJson(raw, JsonArray.class);

                for (JsonElement e: json) {
                    String name = e.getAsJsonObject().get("chartName").getAsString();
                    String createTime = e.getAsJsonObject().get("createTime").getAsString();
                    String xAxis = e.getAsJsonObject().get("xAxis").getAsString();
                    String yAxis = e.getAsJsonObject().get("yAxis").getAsString();
                    charts.add(new Chart(name, createTime, xAxis, yAxis));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return charts;
    }

    public String getName() {
        return name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getxAxis() {
        return xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

}