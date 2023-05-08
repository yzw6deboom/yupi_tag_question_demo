package com.sans.bean.response;

import com.sans.bean.Choices;
import com.sans.bean.Usage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * chatgpt返回参数
 * @author sansyuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptParameterResponse {
    String id;
    String object;
    String created;
    String model;
    Usage usage;
    List<Choices> choices;
}
