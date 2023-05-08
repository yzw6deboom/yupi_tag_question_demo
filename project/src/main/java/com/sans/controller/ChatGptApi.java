package com.sans.controller;

import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * 调用chatgpt模型接口
 * @author sansyuan
 */
@Controller
public interface ChatGptApi {

    /**
     * 调用GPT-3.5模型获取回答
     */
    String getAnswerByGpt3_5(String apiKey, String question) throws IOException;

}
