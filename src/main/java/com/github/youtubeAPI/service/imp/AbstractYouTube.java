package com.github.youtubeAPI.service.imp;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public abstract class AbstractYouTube {
    static final String YOUTUBE_WATCH_URL = "https://www.youtube.com/watch?v=";
    static final YouTube youtube;
    static YouTube.Search.List search;
    static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

    static {
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                (request) -> {
                }).setApplicationName("youtubeAPI").build();
    }

    String getApiKey() {
        String propertiesName = "youtube";
        ResourceBundle propertiesBundle = ResourceBundle.getBundle(propertiesName);
        return propertiesBundle.getString("youtube.apikey3");
    }
}
