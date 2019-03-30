package com.example.elasticsearch.test;

import com.example.pojo.Article;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;

/**
 * 高级查询
 */
public class ThereTest {

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
     * 插入100条记录
     */
    @Test
    public void insertDoc() throws JsonProcessingException {
        //转json
        ObjectMapper objectMapper = new ObjectMapper();

        for (int i = 1; i <= 100; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitle("【"+i+"】五一黄金周");
            article.setContent("【"+i+"】五一黄金周，黄金五一，欢迎来旅游....");
            //添加文档
            client.prepareIndex("blog4", "article", article.getId().toString()).setSource(objectMapper.writeValueAsString(article)).get();
        }

    }
    /**
     * 分页查询
     *   默认分页为10条
     */

    @Test
    public  void testPage() throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        SearchRequestBuilder builder = client.prepareSearch("blog4")
                .setTypes("article")
                .setQuery(QueryBuilders.wildcardQuery("content", "五一"));
        //设置分页
        builder.setFrom(0);
        builder.setSize(20);
        SearchResponse searchResponse = builder.get();
        //解析结果
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit next = iterator.next();
            Article article = objectMapper.readValue(next.getSourceAsString(), Article.class);
            System.out.println(article);
        }


    }

    /**
     * 分页查询 +高亮显示
     *   默认分页为10条
     */

    @Test
    public  void testPageAndHigh() throws IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        SearchRequestBuilder builder = client.prepareSearch("blog4")
                .setTypes("article")
                .setQuery(QueryBuilders.wildcardQuery("content", "五一"));
        //设置分页
        builder.setFrom(0);
        builder.setSize(20);
        //设置高亮显示
        builder.addHighlightedField("content");//高亮显示的字段
        builder.setHighlighterPreTags("<font color='red'>");//前缀
        builder.setHighlighterPostTags("</font>");//后缀
        SearchResponse searchResponse = builder.get();
        //解析结果
        SearchHits hits = searchResponse.getHits();
        Iterator<SearchHit> iterator = hits.iterator();
        while (iterator.hasNext()){
            SearchHit next = iterator.next();
            //解析高亮结果
            HighlightField content = next.getHighlightFields().get("content");
            Text[] texts = content.getFragments();
            String contentStr="";
            for (Text text : texts) {
                contentStr+=text;
            }

            Article article = objectMapper.readValue(next.getSourceAsString(), Article.class);
            //替换
            article.setContent(contentStr);
            System.out.println(article);
        }


    }





}
