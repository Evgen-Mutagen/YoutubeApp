package ru.astondevs.youtubeAPI.service.imp;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import ru.astondevs.youtubeAPI.model.YouTubeDetailModel;
import ru.astondevs.youtubeAPI.model.YouTubePageModel;
import ru.astondevs.youtubeAPI.model.YouTubeSearchModel;
import ru.astondevs.youtubeAPI.service.SearchService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public abstract class AbstractYouTube implements SearchService {
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

    @Override
    public abstract List<YouTubeSearchModel> fetchVideosByQuery(String queryTerm, Long maxSearch);

    @Override
    public abstract YouTubeDetailModel fetchVideoById(String videoId);

    @Override
    public abstract List<YouTubePageModel> fetchVideosByCategoryId(String videoId, Long maxResult);
}
