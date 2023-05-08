package com.sans.project;

import com.sans.service.ChatGptApiImpl;
import com.sans.service.IssueContentApiImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class ProjectApplicationTests {
    @Autowired
    IssueContentApiImpl issueContentService;
    @Autowired
    ChatGptApiImpl chatGptService;

    @Test
    void getIssueContent() throws IOException {
        String content = issueContentService.getContentByURL("https://www.code-nav.cn/api/post/get/vo?id=1654376152560611329");
        //调用chatGpt
        String apiKey = "apiKey";
        String answer = chatGptService.getAnswerByGpt3_5(apiKey,content);
        System.out.println(answer);
    }

}
