package com.github.youtubeAPI.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "Информация о полях поиска категорий")
public class YouTubePageModel {

    @Schema(description = "Медиа")
    private List<YouTubeSearchModel> media;

    @Schema(description = "Номер страницы")
    private Integer pageNumber;

    @Schema(description = "Всего найдено страниц")
    private Integer pageTotal;
}
