package com.sans.controller;

import org.springframework.stereotype.Controller;

/**
 * 获取网页文章内容的接口
 * @author sansyuan
 */
@Controller
public interface IssueContentApi {

    /**
     * 根据页面地址，获取文章内容
     */
    String getContentByURL(String url);

}
