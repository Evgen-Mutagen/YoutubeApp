package ru.astondevs.youtubeAPI.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class YouTubeSearchControllerTest extends AbstractControllerTest {
   private static final String REST_URL = "/youtube-service";

    @Test
    void searchYouTube() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/search?q=" + "Sting"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void videoYouTube() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/video-details/" + "xxzBTdZJm9Q"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void videoCategoryYouTube() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/categoryId" + "/20"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}