package ru.astondevs.youtubeAPI.service;

import java.util.List;

public interface SearchService {

    List<?> fetchVideosByQuery(String queryTerm, Long maxSearch);

    Object fetchVideoById(String videoId);

    List<?> fetchVideosByCategoryId(String videoId, Long maxResult);
}
