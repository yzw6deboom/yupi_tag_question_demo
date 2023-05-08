package com.sans.util;

/**
 * 处理内容工具类
 * @author sansyuan
 */
public class ContentHandleUtils {

    /**
     * 将文章内容限制在1000字内
     */
    public static String getLimitContent(String content){

        return content.substring(0,Math.min(content.length(),1000));
    }

    /**
     * 获取gpt系统设置内容
     */
    public static String getGptSystemContent(){
        return ("You need to act as an expert in the field of computer software. You can clearly know what technology this article describes for any article on computer field knowledge, and you can label the article with accurate technical classification labels and label generation rules. as follows:  \n" +
                "1. First of all, you get the field of technology application written in the article. For example, spring is a framework under java, so you can get the first-level classification label to be Java, and the first-level classification must be the technology involved in the article The superclass of the language used by the technology, such as python, java, c++\n" +
                "2. Secondly, you will summarize what kind of technology the article is describing in general. For example, most of the article is about springboot, so the second-level classification label you get is springboot\n" +
                "3. This rule must be strictly enforced. You can generate classification labels with a depth of 3 at most according to the above rules, and the answer you can give me can only be in this format: first-level labels/second-level labels/third-level labels, No other answer allowed\n" +
                "5. This rule must be strictly enforced, and only one category label answer that best matches the content of the article is required\n" +
                "6. This rule must be strictly enforced, and the answer must be in Chinese\n" +
                "7. This rule must be strictly enforced, and there is no need to answer the classification label with a depth of 3, which can be 1 or 2\n" +
                "8. This rule must be strictly enforced. The label must be the overall content of the article. Do not summarize a section of the article. For example, the entire article talks about the spring family. Although there are sections introducing springboot and springCloud, the label must be able to Play a general spring framework");
    }

}
