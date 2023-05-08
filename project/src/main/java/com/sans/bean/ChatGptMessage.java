package com.sans.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 消息类
 * @author sansyuan
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGptMessage {
    String role;
    String content;
}
