package com.sans.controller;

/**
 * 获取网页文章内容的接口
 * @author sansyuan
 */
public interface IssueContentApi {

    /**
     * 根据页面地址，获取文章内容
     */
    String getContentByURL();

}
