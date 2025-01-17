package com.hyujikoh.ShortUrl;

import com.hyujikoh.ShortUrl.domain.url.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ShortUrlProjectApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Service service;

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
    }

    @Test
    @DisplayName("서버 테스트")
    public void serverHealthCheck() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/url")
                        .characterEncoding("UTF-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


    @Test
    @DisplayName("8자리 랜덤 url 리턴")
    public void startApi() throws Exception {
        //given
        String url = "https://youtube.com";

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:"+port+"/url/shortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url) // URL을 쿼리 파라미터로 전달
                ).andExpect(status().isOk())
                .andReturn();

        //then
        // 길이가 8자리 가 맞는지
        assertEquals(mvcResult.getResponse().getContentAsString().length(),8);
    }


    @Test
    @DisplayName("정상적인 URL 이 요청에 안왔을때를 대비한 오류 처리")
    public void notAllowURLApi() throws Exception {
        //given
        String url = "youtube.com";

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:"+port+"/url/shortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url) // URL을 쿼리 파라미터로 전달
                ).andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("랜덤 키값으로 등록한 url 이 리턴이 되는지")
    public void redirectApi() throws Exception {
        //given
        String url = "https://youtube.com";

        String url2 = "https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation/3.2.4";

        //then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:"+port+"/url/shortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url) // URL을 쿼리 파라미터로 전달
                ).andExpect(status().isOk())
                .andReturn();

        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:"+port+"/url/shortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url2) // URL을 쿼리 파라미터로 전달
                ).andExpect(status().isOk())
                .andReturn();

        String urlFromKey = this.service.getUrlFromKey(mvcResult.getResponse().getContentAsString());

        String urlFromKey2 = this.service.getUrlFromKey(mvcResult2.getResponse().getContentAsString());


        //then
        assertEquals(url,urlFromKey);

        assertEquals(url2,urlFromKey2);
    }

    @Test
    @DisplayName("등록된 url이 얼마나 count 가 됐는지 수량 확인 api")
    public void countUrlApi() throws Exception {
        //given
        String url = "https://google.com";

        String url2 = "https://naver.com";

        for(int i = 0 ; i<29; i++){
            this.service.createRandomUrl(url);
        }

        for(int i = 0 ; i<17; i++){
            this.service.createRandomUrl(url2);
        }

        //when
        // 수량 조회 하는 api

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/url/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url))
                .andExpect(status().isOk());

        ResultActions resultActions1 = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:" + port + "/url/count")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("url", url2))
                .andExpect(status().isOk());

        //then
        Long aLong = Long.parseLong(resultActions.andReturn().getResponse().getContentAsString());
        Long aLong1 = Long.parseLong(resultActions1.andReturn().getResponse().getContentAsString());
        assertEquals(aLong, 29);
        assertEquals(aLong1, 17);
    }

}
