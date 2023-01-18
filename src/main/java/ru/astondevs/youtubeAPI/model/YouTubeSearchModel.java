package ru.astondevs.youtubeAPI.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@Setter
@ToString
@Schema(description = "Информация о полях поиска")
public class YouTubeSearchModel {

    @Schema(description = "Ссылка на видео")
    private String url;

    @Schema(description = "Название")
    private String title;

    @Schema(description = "Описание видео")
    private String description;

    @Schema(description = "Дата публикации")
    private String publishedAt;

    @Schema(description = "Ссылка на картинку")
    @Nullable
    private String thumbnailUrl;
}
