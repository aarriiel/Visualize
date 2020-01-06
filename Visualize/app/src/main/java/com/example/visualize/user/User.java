package com.example.visualize.user;

import com.example.visualize.network.NetworkUtil;
import com.example.visualize.network.Response;
import com.google.gson.JsonObject;


public class User {

    private UserProfile profile;

    public User(String username) {
        initProfile(username);
    }

    private void initProfile(String username) {
        String uri = String.format("user/%s", username);
        Response response = new Response(NetworkUtil.get(uri));

        if (response.success()) {
            this.profile = new UserProfile(username);
        }
    }


    public static Response register(String username, String password) {
        String uri = String.format("user/register");

        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("password", password);
        return new Response(NetworkUtil.post(uri, json.toString()));
    }

    public static Response login(String username, String password) {
        String uri = "user/login";

        JsonObject json = new JsonObject();
        json.addProperty("username", username);
        json.addProperty("password", NetworkUtil.hash(password));
        return new Response(NetworkUtil.post(uri, json.toString()));
    }


    public Response update(String password) {
        String uri = "user/update";

        JsonObject json = new JsonObject();
        json.addProperty("username", getUsername());
        json.addProperty("password", password);

        return new Response(NetworkUtil.post(uri, json.toString()));
    }


    public String getUsername() {
        return profile.getUsername();
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;

        User that = (User) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash += 37 * hash + getUsername().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return profile.toString();
    }

}
