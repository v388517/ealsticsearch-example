package com.example.elasticsearch.test;

import com.example.pojo.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Iterator;

public class SecondTest {
    private TransportClient client;

    /**
     * 获取链接对象
     */
    @Before
    public void init() throws Exception {
        System.out.println("init.....");
        client = TransportClient.builder().build().
                addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    /**
     * 释放资源
     */
    @After
    public void destory() {
        System.out.println("destory.....");
        if (client != null) {
            client.close();
        }
    }

    /**
     * 索引和文档的操作
     * 注： blog4 必须先经过mapping映射（ik分词器分词）
     */
    @Test
    public void documentAndIndex() throws JsonProcessingException {
        Article article = new Article();
        article.setId(4);
        article.setTitle("五一黄金周");
        article.setContent("五一黄金周，黄金五一，欢迎来旅游....");
        //转json
        ObjectMapper objectMapper = new ObjectMapper();
        //添加文档
        client.prepareIndex("blog4", "article", article.getId().toString()).setSource(objectMapper.writeValueAsString(article)).get();
        //修改文档 方式一
        // client.prepareUpdate("blog2","article",article.getId().toString()).setDoc(mapper.writeValueAsString(article)).get();
        //修改文档2
        //  client.update(new UpdateRequest("blog2","article",article.getId().toString()).doc(mapper.writeValueAsString(article))).get();
        //删除文档
        //方式一：client.prepareDelete();
        //方式二 client.delete(new DeleteRequest("blog2","article",article.getId().toString())).get();

    }

    /**
     * 查询
     */
    @Test
    public void search() {
        SearchResponse searchResponse = client.prepareSearch("blog4").setTypes("article")
                .setQuery(QueryBuilders.termQuery("content", "五一")).get();
        printResult(searchResponse);
    }

    /**
     * 打印结果
     *
     * @param searchResponse
     */
    private void printResult(SearchResponse searchResponse) {
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()) {
            SearchHit next = iterator.next();
            System.out.println(next.getSource().get("content"));
        }
    }


}
