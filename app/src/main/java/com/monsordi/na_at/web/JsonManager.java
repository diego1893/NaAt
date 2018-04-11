package com.monsordi.na_at.web;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by diego on 10/04/18.
 */

public class JsonManager {

    private static final String BASE_URL = "http://picasaweb.google.com/data/entry/api/user/";
    private static final String JSON_EXTENSION = "?alt=json";

    private static String getEmailId(String email){
        if(email.contains("@")) {
            return email.substring(0, email.lastIndexOf('@'));
        }
        return null;
    }

    public static String getImageRequestingUrl(String email){
        String userId = getEmailId(email);
        return BASE_URL + userId + JSON_EXTENSION;
    }

    public static String getImageUrl(JSONObject response) throws JSONException {
        JSONObject jsonObject = response.getJSONObject("entry");
        JSONObject photo = jsonObject.getJSONObject("gphoto$thumbnail");
        String url64 = photo.getString("$t");
        String url = url64.replace("s64","s500");
        return url;
    }

}
