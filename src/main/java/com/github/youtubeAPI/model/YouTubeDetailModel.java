package com.github.youtubeAPI.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "Информация о полях детального описания видео по его id")
public class YouTubeDetailModel {

    @Schema(description = "Ссылка на видео")
    private String url;

    @Schema(description = "Название")
    private String title;

    @Schema(description = "Описание видео")
    private String description;

    @Schema(description = "Дата публикации")
    private String publishedAt;

    @Schema(description = "Ссылка на картинку")
    private String thumbnailUrl;

    @Schema(description = "Название канала")
    private String channelTitle;

    @Schema(description = "Продолжительность")
    private String duration;

    @Schema(description = "Количество просмотров")
    private String viewCount;

    @Schema(description = "Количество лайков")
    private String likeCount;
}
