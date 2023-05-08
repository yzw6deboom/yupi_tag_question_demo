package com.sans.service;

import com.sans.bean.request.ChatGptRequest;
import com.sans.controller.ChatGptApi;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 获取gpt回答实现类
 * @author sansyuan
 */

@Service
public class ChatGptApiImpl implements ChatGptApi {
    @Override
    public String getAnswerByGpt3_5(String apiKey, String question) throws IOException {
        String proxyIp =  "127.0.0.1";
        int proxyPort = 7890;
        HttpHost httpHost =  new HttpHost(proxyIp,proxyPort);
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setProxy(httpHost);
        CloseableHttpClient httpClient = httpClientBuilder.build();
        ChatGptRequest customChatGpt = new ChatGptRequest(apiKey);
        customChatGpt.setResponseTimeout(20000);
        String answer;
        try {
            answer =  customChatGpt.getAnswer(httpClient, question);
            httpClient.close();
        } finally {
            httpClient.close();
        }
        return answer;
    }
}
