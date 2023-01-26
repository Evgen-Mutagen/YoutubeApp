package com.github.youtubeAPI.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "Информация о полях для видео по одной категории")
public class YouTubeCategoryMainPageModel {

    @Schema(description = "Айди категории")
    private String categoryId;

    @Schema(description = "Имя категории")
    private String categoryName;

    @Schema(description = "Медиа")
    private YouTubeSearchModel media;
}
