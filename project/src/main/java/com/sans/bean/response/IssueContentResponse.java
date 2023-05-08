package com.sans.bean.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 网页curl返回值映射
 *
 * @author sansyuan
 */
@Data
public class IssueContentResponse {
    @JsonProperty("data")
    private DataResponse data;


    @Data
    public static class DataResponse{
        @JsonProperty("content")
        private String content;
    }
}
