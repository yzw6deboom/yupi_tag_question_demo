# yupi_tag_question_demo
获取网站文章并根据内容打标签  
# **问题背景：**  
你是编程导航平台（https://www.code-nav.cn）的文章创作者，现在你想要把自己在这个平台上发布的所有文章导出到自己的网站，并且根据文章的内容给每篇文章打上对应的多个标签（比如 Java、Dubbo），然后将这些文章按照标签分层级地进行归档，从而便于之后的阅读和搜索。  
  
问题：原平台不提供导出、打标签、分层级归档、搜索等功能，请问如何自主设计实现上述系统。请尽量清晰详细地阐述思路，能具体实现更加分。  
  
# **问题提炼：**  
由问题可以总结出需求实现流程：  
1. 获得文章内容  
2. 根据文章内容打上标签  
3. 将文章分类归档至自己网站  
   以上三点最重要的是第二点：**如何从一篇没有任何额外标注的文章得到内容标签**  
   第一点主要考虑网站是否有**反爬**措施以及爬取文章的形式，如果是网页动态生成html内容那需要对内容**进行一定的格式化**  
   至于第三点，完成了一二两点，第三点是最简单实现的，不多做赘述  
  
# 方案设计  
## 方案1：使用chatgpt的方案  
### 1. 将网站的文章爬下来  
- 该网站的文章显示接口直接是把文章包裹在content属性中，还是md格式的，直接下载下来就行了  
  
  
### 2. 将文章发送给chatgpt，让它来判断这个文章主要说明的是什么，如果是java就打上java，细分的spring，doubo等  
你需要扮演一个精通计算机软件领域的专家，你对任何一篇计算机领域知识的文章都能清楚的知道这篇文章所讲述的技术是什么并且能够给文章打上准确的技术分类标签，标签的生成规则如下：  
1. 首先，你得到文章所写的技术应用的领域是什么，例如spring是java下的框架，所以你得出得第一级分类标签就是Java，第一级分类必须是该文章涉及到的技术的超类，是该技术使用的语言，如python，java，c++  
2. 其次，你会归纳文章总体是在具体描写哪类技术，例如文章通篇大部分在讲述springboot，所以你得出得第二级分类标签就是springboot  
3. 这条规则必须严格执行，你根据上述规则最多生成深度为3的分类标签，并且你给我得回答只能是这样的格式：第一级标签/第二级标签/第三级标签,不允许其它回答  
5. 这条规则必须严格执行，只需要一条最符合文章内容的分类标签回答  
6. 这条规则必须严格执行，回答必须用中文回答   
7. 这条规则必须严格执行，不需要一定回答深度为3的分类标签，可以为1或2
8. 这条规则必须严格执行，标签一定是文章总体上的内容，不要出现对文章某一小节的概括，例如文章通篇在讲spring家族，虽然有小节介绍springboot和springCloud，但标签必须为能起到概括性的spring框架
     
You need to act as an expert in the field of computer software. You can clearly know what technology this article describes for any article on computer field knowledge, and you can label the article with accurate technical classification labels and label generation rules. as follows:  
1. First of all, you get the field of technology application written in the article. For example, spring is a framework under java, so you can get the first-level classification label to be Java, and the first-level classification must be the technology involved in the article The superclass of the language used by the technology, such as python, java, c++
2. Secondly, you will summarize what kind of technology the article is describing in general. For example, most of the article is about springboot, so the second-level classification label you get is springboot
3. This rule must be strictly enforced. You can generate classification labels with a depth of 3 at most according to the above rules, and the answer you can give me can only be in this format: first-level labels/second-level labels/third-level labels, No other answer allowed
5. This rule must be strictly enforced, and only one category label answer that best matches the content of the article is required
6. This rule must be strictly enforced, and the answer must be in Chinese
7. This rule must be strictly enforced, and there is no need to answer the classification label with a depth of 3, which can be 1 or 2
8. This rule must be strictly enforced. The label must be the overall content of the article. Do not summarize a section of the article. For example, the entire article talks about the spring family. Although there are sections introducing springboot and springCloud, the label must be able to Play a general spring framework

### 3. 得到文章标签  
得到了这样的回答：计算机基础/网络协议/RPC
![[Pasted image 20230508231143.png]]
  
### 4.处理文章标签  
得到了标签分级后，那么给文章打标签就很方便了，就有很多方式了，给文章数据表加个tag的事情。这样就可以在自己的网站上得到分类详实的文章了  
  
  
## 方案2：不使用chatgpt的方案  
如果不使用chatgpt来智能识别文章标签的话，那么可以用这几种方案来实现对文章内容总结标签的方式  
1. 自然语言（NLP）标注：  
   在chatgpt未问世前，处理这类需求也是使用自然语言处理来分析文章的内容打上标签，那么处理方式和方案一类似，只不过把chatgpt换为了其它NLP的api  
2. 使用关键词标注：  
   1. 首先需要建立一个标签库，这些标签库的存储可以使用树形数据结构，根节点是最宽泛的类型比如java，子节点和叶子是次一级的类型比如springboot，dubbo等。  
   2. 以此建立多个不同领域的标签库。  
   3. 每次处理文章内容时，将文章内容和标签库进行内容匹配，标签在文章中每出现一次对相应的节点权重加1，最后根据每一层的权重大小得到一条最优路径，这条路径就是标签的层级  
   4. 因为有多个不同领域的标签库，所以可以使用多线程来加速匹配速度  
3. ~~人工标注~~  
  
  
# 代码设计  
- controller层里面两个方法接口，一个是获取网页文章内容，一个是调用chatgpt  
- service层进行文章内容调用chatgpt接口获取返回值  
  
  
  
  
# 总结  
1. 在这个需求中我只用了chatgpt来进行方案1的第二步操作，其余代码和设计均为自己设想  
2. chatgpt确实好用，能提升不少效率  
3. 有些文章过长导致chatgpt无法回答  
   1. 可以用chatpdf之类的开源压缩token技术  
   2. 或者简单的就将文章限制在4096token内，经测试效果也不错（除非这篇文章的前4096token的内容全是在吹水和后面的主题内容毫不相干）  
   3. 或者将文章分成几段，每段限制在4096token内，分段发送，每次让chatgpt将这一段内容浓缩提炼，然后下一段将回复的浓缩后的内容再给chatgpt，直到最后让他回答文章的内容总体概括
