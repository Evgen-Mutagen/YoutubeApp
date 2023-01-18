package ru.astondevs.youtubeAPI.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class YouTubeResultMainPage {

    @Schema(description = "Популярные видео")
    List<YouTubeCategoryMainPageModel> items;
}
