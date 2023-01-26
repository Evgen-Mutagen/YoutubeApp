package com.github.youtubeAPI.service.imp;

import com.github.youtubeAPI.model.*;
import com.github.youtubeAPI.service.SearchService;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class YouTubeSearchService extends AbstractYouTube implements SearchService {
    private static final Logger log = LoggerFactory.getLogger(YouTubeSearchService.class);
    private static final String searchFieldBySearch = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/high/url,snippet/channelId )";
    private static final List<String> part = Collections.singletonList("id,snippet");
    private static final List<String> partByVideo = Collections.singletonList("snippet,contentDetails,statistics");
    private static final String searchFieldByCategoryId = "items,pageInfo";
    private static final String searchFieldByVideo = "items(kind,id,snippet/title,snippet/description,snippet/publishedAt," +
            "snippet/thumbnails/maxres/url,snippet/channelId,snippet/categoryId,contentDetails/duration,statistics/viewCount,statistics/likeCount)";

    @Override
    public List<YouTubeSearchModel> fetchVideosByQuery(String queryTerm, Long maxSearch) {
        List<YouTubeSearchModel> videos = new ArrayList<>();
        try {
            search = youtube.search().list(part);

            search.setKey(getApiKey());
            search.setQ(queryTerm);
            search.setType(Collections.singletonList("video"));
            search.setFields(searchFieldBySearch);
            search.setMaxResults(maxSearch);

            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                for (SearchResult result : searchResultList) {
                    YouTubeSearchModel video = new YouTubeSearchModel();
                    video.setUrl(YOUTUBE_WATCH_URL + result.getId().getVideoId());
                    video.setTitle(result.getSnippet().getTitle());
                    video.setDescription(result.getSnippet().getDescription());
                    DateTime dateTime = result.getSnippet().getPublishedAt();

                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    video.setPublishedAt(dateString);
                    video.setThumbnailUrl(result.getSnippet().getThumbnails().getHigh().getUrl());

                    videos.add(video);
                }
            } else {
                log.info("No search results for pages  got from YouTube API");
            }
        } catch (GoogleJsonResponseException e) {
            log.warn("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            log.warn("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }

        return videos;
    }

    @Override
    public List<YouTubePageModel> fetchVideosByCategoryId(String categoryId, Long maxResult) {
        YouTubePageModel categoryVideo = new YouTubePageModel();
        try {
            search = youtube.search().list(part);
            search.setKey(getApiKey());
            search.setVideoCategoryId(categoryId);
            search.setType(Collections.singletonList("video"));
            search.setMaxResults(maxResult);
            search.setFields(searchFieldByCategoryId);

            SearchListResponse searchResponse = search.execute();

            PageInfo searchResultForPage = searchResponse.getPageInfo();
            if (searchResultForPage != null) {
                categoryVideo.setPageNumber(searchResultForPage.getResultsPerPage());
                categoryVideo.setPageTotal(searchResultForPage.getTotalResults());
            } else {
                log.info("No search results for pages  got from YouTube API");
            }

            List<SearchResult> searchResultList = searchResponse.getItems();
            List<YouTubeSearchModel> searchForPage = new ArrayList<>();
            if (searchResultList != null) {
                for (SearchResult result : searchResultList) {
                    YouTubeSearchModel video = new YouTubeSearchModel();
                    video.setUrl(YOUTUBE_WATCH_URL + result.getId().getVideoId());
                    video.setTitle(result.getSnippet().getTitle());
                    video.setDescription(result.getSnippet().getDescription());
                    DateTime dateTime = result.getSnippet().getPublishedAt();

                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    video.setPublishedAt(dateString);
                    video.setThumbnailUrl(result.getSnippet().getThumbnails().getHigh().getUrl());

                    searchForPage.add(video);
                }
            } else {
                log.info("No search item got from YouTube API");
            }
            categoryVideo.setMedia(searchForPage);

        } catch (GoogleJsonResponseException e) {
            log.warn("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            log.warn("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
        return Collections.singletonList(categoryVideo);
    }

    @Override
    public YouTubeDetailModel fetchVideoById(String videoId) {
        YouTubeDetailModel videoById = new YouTubeDetailModel();
        try {
            YouTube.Videos.List video = youtube.videos().list(partByVideo);
            video.setKey(getApiKey());
            video.setId(Collections.singletonList(videoId));
            video.setFields(searchFieldByVideo);

            VideoListResponse videoResponse = video.execute();
            List<Video> videoResult = videoResponse.getItems();
            if (videoResult != null) {
                for (Video result : videoResult) {
                    videoById.setUrl(YOUTUBE_WATCH_URL + result.getId());
                    videoById.setTitle(result.getSnippet().getTitle());
                    videoById.setDescription(result.getSnippet().getDescription());

                    DateTime dateTime = result.getSnippet().getPublishedAt();
                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    videoById.setPublishedAt(dateString);

                    videoById.setThumbnailUrl(result.getSnippet().getThumbnails().getMaxres().getUrl());
                    videoById.setChannelTitle(result.getSnippet().getChannelId());
                    videoById.setDuration(result.getContentDetails().getDuration());
                    videoById.setViewCount(result.getStatistics().getViewCount().toString());
                    videoById.setLikeCount(result.getStatistics().getLikeCount().toString());
                }

            } else {
                log.info("No search results got from YouTube API");
            }
        } catch (GoogleJsonResponseException e) {
            log.warn("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            log.warn("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
        return videoById;
    }

    @Override
    public List<YouTubeCategoryMainPageModel> fetchVideoForMainPage(String name, String categoryId, String region) {
        List<YouTubeCategoryMainPageModel> listOfCategoryVideos = new ArrayList<>();
        try {
            YouTube.Videos.List video = youtube.videos().list(partByVideo);
            video.setKey(getApiKey());
            video.setVideoCategoryId(categoryId);
            video.setFields(searchFieldByVideo);
            video.setRegionCode(region);
            video.setMaxResults(20L);

            VideoListResponse videoResponse = video.setChart("mostPopular").execute();
            List<Video> videoResult = videoResponse.getItems();

            if (videoResult != null) {
                for (Video result : videoResult) {
                    YouTubeCategoryMainPageModel categoryVideo = new YouTubeCategoryMainPageModel();
                    categoryVideo.setCategoryId(result.getSnippet().getCategoryId());
                    categoryVideo.setCategoryName(name);

                    YouTubeSearchModel media = new YouTubeSearchModel();
                    media.setUrl(YOUTUBE_WATCH_URL + result.getId());
                    media.setTitle(result.getSnippet().getTitle());
                    media.setDescription(result.getSnippet().getDescription());
                    DateTime dateTime = result.getSnippet().getPublishedAt();

                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    media.setPublishedAt(dateString);

                    if (result.getSnippet().getThumbnails().getMaxres() == null) {
                        System.out.println("Видео без картинки - " + YOUTUBE_WATCH_URL + result.getId());
                    } else {
                        media.setThumbnailUrl(result.getSnippet().getThumbnails().getMaxres().getUrl());
                    }

                    categoryVideo.setMedia(media);

                    listOfCategoryVideos.add(categoryVideo);
                }
            } else {
                log.info("No search item got from YouTube API");
            }
        } catch (GoogleJsonResponseException e) {
            log.warn("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());

        } catch (IOException e) {
            log.warn("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        }
        return listOfCategoryVideos;
    }

    public List<YouTubeResultMainPage> getMainPage() {
        List<YouTubeResultMainPage> list = new ArrayList<>();
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Фильмы и анимация", "1", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Транспорт", "2", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Музыка", "10", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Животные", "15", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Спорт", "17", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Видеоигры", "20", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Люди и блоги", "22", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Юмор", "23", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Развлечения", "24", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Новости и политика", "25", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Хобби и стиль", "26", "RU")));
        list.add(new YouTubeResultMainPage(fetchVideoForMainPage("Наука и техника", "28", "RU")));
        return list;
    }
}