package com.sans.bean.request;

import com.sans.bean.ChatGptMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 模型参数类
 * @author sansyuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptParameterRequest {

    String model = "gpt-3.5-turbo";

    List<ChatGptMessage> messages = new ArrayList<>();

    public void addMessages(ChatGptMessage message) {
        this.messages.add(message);
    }
}
