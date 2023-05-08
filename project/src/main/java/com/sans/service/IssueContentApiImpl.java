package com.sans.service;


import com.sans.bean.response.IssueContentResponse;
import com.sans.controller.IssueContentApi;
import com.sans.util.ContentHandleUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 获取文章内容实现类
 * @author sansyuan
 */
@Service
public class IssueContentApiImpl implements IssueContentApi {

    @Override
    public String getContentByURL(String url) {
        //获取url的返回值
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url,String.class);
        IssueContentResponse responseContent = restTemplate.getForObject(url,IssueContentResponse.class);

        //获取能够发送到gpt的文章内容
        assert responseContent != null;

        return ContentHandleUtils.getLimitContent(responseContent.getData().getContent());
    }
}
