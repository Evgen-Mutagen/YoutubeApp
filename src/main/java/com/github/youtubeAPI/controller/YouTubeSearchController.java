package com.github.youtubeAPI.controller;

import com.github.youtubeAPI.service.imp.YouTubeSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.github.youtubeAPI.model.YouTubeDetailModel;
import com.github.youtubeAPI.model.YouTubePageModel;
import com.github.youtubeAPI.model.YouTubeResultMainPage;
import com.github.youtubeAPI.model.YouTubeSearchModel;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(
        name = "YouTube - поиск",
        description = "Все методы для работы с поиском YouTube Data api"
)
@RequestMapping(value = "/youtube-service")
public class YouTubeSearchController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private final YouTubeSearchService youtubeSearchService;

    @GetMapping(value = "/search")
    @Operation(summary = "Поиск видео по ключевым словам")
    @ResponseStatus(HttpStatus.OK)
    public List<YouTubeSearchModel> searchYouTube(
            @RequestParam(value = "q", required = false) String search,
            @RequestParam(value = "items", required = false, defaultValue = "7") String items) {
        LOG.info("searchYouTube with search={}", search);
        Long max = (long) Integer.parseInt(items);
        return youtubeSearchService.fetchVideosByQuery(search, max);
    }

    @GetMapping(value = "/video-details/{videoId}")
    @Operation(summary = "Поиск видео по его id и вывод полной информации")
    @ResponseStatus(HttpStatus.OK)
    public YouTubeDetailModel videoYouTube(
            @PathVariable String videoId) {
        LOG.info("videoYouTube with videoId={}", videoId);
        return youtubeSearchService.fetchVideoById(videoId);
    }

    @GetMapping(value = "/categoryId/{categoryId}")
    @Operation(summary = "Поиск видео по категориям")
    @ResponseStatus(HttpStatus.OK)
    public List<YouTubePageModel> videoCategoryYouTube(
            @PathVariable String categoryId,
            @RequestParam(value = "items", required = false, defaultValue = "7") String items) {
        LOG.info("searchYouTube with search={}", categoryId);
        Long max = (long) Integer.parseInt(items);
        return youtubeSearchService.fetchVideosByCategoryId(categoryId, max);
    }

    @GetMapping(value = "/main")
    @Operation(summary = "Вывод видео по категориям для главной страницы")
    @ResponseStatus(HttpStatus.OK)
    public List<YouTubeResultMainPage> mainPageYouTube() {
        return youtubeSearchService.getMainPage();
    }
}