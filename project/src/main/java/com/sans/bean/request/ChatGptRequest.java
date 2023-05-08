package com.sans.bean.request;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sans.bean.ChatGptMessage;
import com.sans.bean.Choices;
import com.sans.bean.response.ChatGptParameterResponse;
import com.sans.util.ContentHandleUtils;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * gpt请求参数
 * @author sansyuan
 */
public class ChatGptRequest {
    /**
     * ApiKey
     */
    private String apiKey;
    /**
     * 使用的模型
     */
    private String model = "gpt-3.5-turbo-0301";
    /**
     * 对应的请求接口
     */
    private String url = "https://api.openai.com/v1/chat/completions";
    /**
     * 默认编码
     */
    private Charset charset = StandardCharsets.UTF_8;


    /**
     * 创建一个ChatGptRequestParameter，用于携带请求参数
     */
    private ChatGptParameterRequest chatGptRequestParameter = new ChatGptParameterRequest();

    /**
     * 相应超时时间，毫秒
     */
    private int responseTimeout = 1000;

    public void setResponseTimeout(int responseTimeout) {
        this.responseTimeout = responseTimeout;
    }

    public ChatGptRequest(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAnswer(CloseableHttpClient client, String question) {

        // 创建一个HttpPost
        HttpPost httpPost = new HttpPost(url);
        // 创建一个ObjectMapper，用于解析和创建json
        ObjectMapper objectMapper = new ObjectMapper();

        //设置gpt系统参数
        chatGptRequestParameter.addMessages(new ChatGptMessage("system", ContentHandleUtils.getGptSystemContent()));

        // 设置请求参数
        chatGptRequestParameter.addMessages(new ChatGptMessage("user", question));
        HttpEntity httpEntity = null;
        try {
            // 对象转换为json字符串
            httpEntity = new StringEntity(objectMapper.writeValueAsString(chatGptRequestParameter), charset);
        } catch (JsonProcessingException e) {
            System.out.println(question + "->json转换异常");
            return null;
        }
        httpPost.setEntity(httpEntity);


        // 设置请求头
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        // 设置登录凭证
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey);

        // 用于设置超时时间
        RequestConfig config = RequestConfig
                .custom()
                .setConnectionRequestTimeout(2, TimeUnit.SECONDS)
                .setResponseTimeout(responseTimeout, TimeUnit.MILLISECONDS)
                .build();
        httpPost.setConfig(config);
        try {
            // 提交请求
            return client.execute(httpPost, response -> {
                // 得到返回的内容
                String resStr = EntityUtils.toString(response.getEntity(), charset);
                // 转换为对象
                ChatGptParameterResponse responseParameter = objectMapper.readValue(resStr, ChatGptParameterResponse.class);
                String ans = "";
                // 遍历所有的Choices
                for (Choices choice : responseParameter.getChoices()) {
                    ChatGptMessage message = choice.getMessage();
                    chatGptRequestParameter.addMessages(new ChatGptMessage(message.getRole(), message.getContent()));
                    String s = message.getContent().replaceAll("\n+", "\n");
                    ans += s;
                }
                // 返回信息
                return ans;
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 发生异常，移除刚刚添加的ChatGptMessage
        chatGptRequestParameter.getMessages().remove(chatGptRequestParameter.getMessages().size()-1);
        return "网络异常";
    }
}
