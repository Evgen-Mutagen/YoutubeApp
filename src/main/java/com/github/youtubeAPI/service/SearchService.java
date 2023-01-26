package com.github.youtubeAPI.service;

import com.github.youtubeAPI.model.YouTubeCategoryMainPageModel;
import com.github.youtubeAPI.model.YouTubeDetailModel;
import com.github.youtubeAPI.model.YouTubePageModel;
import com.github.youtubeAPI.model.YouTubeSearchModel;

import java.util.List;

public interface SearchService {

    List<YouTubeSearchModel> fetchVideosByQuery(String queryTerm, Long maxSearch);

    List<YouTubePageModel> fetchVideosByCategoryId(String categoryId, Long maxResult);

    YouTubeDetailModel fetchVideoById(String videoId);

    List<YouTubeCategoryMainPageModel> fetchVideoForMainPage(String name, String categoryId, String region);
}
