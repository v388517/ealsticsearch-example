package com.example.elasticsearch.test;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;

/**
 * ElasticSearch基本测试
 */
public class BasicTest {

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
     * 创建文档
     */
    @Test
    public void createContent() throws IOException {
        //构建文档
        XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                .startObject()
                .field("id", 3)
                .field("title", "五一黄金周")
                .field("content", "欢迎来到五一黄金周，五一黄金周欢迎您")
                .endObject();
        /**
         * 创建索引
         *  index:索引
         *  type：文档类型
         *  id:
         * get()方法：相当于执行
         */
        client.prepareIndex("blog3", "article", "3").setSource(contentBuilder).get();
    }

    /**
     * 搜索文档
     * prepareSearch:查询结果
     * QueryBuilders.matchAllQuery()：匹配所有
     */
    @Test
    public void search() {
        SearchResponse searchResponse = client.prepareSearch("blog3").setTypes("article").setQuery(QueryBuilders.matchAllQuery()).get();
        //解析结果
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
            System.out.println(next.getSource().get("title"));
        }
    }

    /**
     * 匹配字段:匹配所有字段
     * 匹配规则 ： elasticSearch分词规则：一个中文是一个词
     * queryStringQuery("五周")方法是将"五周"这个词先进行分词，然后查询
     * 结论：可以看出，在blog3文档中没有"五周"，但也可以查询出来数据
     */
    @Test
    public void searchItem() {
        SearchResponse searchResponse = client.prepareSearch("blog3").setTypes("article").
                setQuery(QueryBuilders.queryStringQuery("五周")).get();
        printResult(searchResponse);

    }

    /**
     * 分词查询title
     * wildcardQuery()是一个模糊查询 支持通配符：*五一*
     * 结论：无法查询出结果  将五一看成一个词
     */
    @Test
    public void searchTitle() {
        SearchResponse searchResponse = client.prepareSearch("blog3").setTypes("article").
                setQuery(QueryBuilders.wildcardQuery("title", "*五一*")).get();
        printResult(searchResponse);
    }


    /**
     * 词条查询 title
     * termQuery()查询词条
     * 结论：无法查询出结果  将五一看成一个词
     *
     * @throws Exception
     */
    @Test
    public void demo05() throws Exception {
        SearchResponse searchResponse = client.prepareSearch("blog1").setTypes("article")
                .setQuery(QueryBuilders.termQuery("title", "五一")).get();
        printResult(searchResponse);
    }

    /**
     * 索引的操作
     * 创建索引
     */
    @Test
    public void createIndex() {
        //创建索引  此创建不会创建mapping映射
        client.admin().indices().prepareCreate("blog4").get();
    }

    /**
     * 索引的操作
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        //删除索引
        client.admin().indices().prepareDelete("blog4").get();
    }

    /**
     * 添加映射
     * 自定义添加映射：主要是在创建分词的时候使用ik分词器
     * <p>
     * 注：首先要创建索引
     */
    @Test
    public void addMapping() throws Exception {
        //创建索引
        client.admin().indices().prepareCreate("blog4").get();
        /**
         * 映射创建
         */
        XContentBuilder contentBuilder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("article")
                .startObject("properties")
                .startObject("id").field("type", "integer").field("store", "yes").endObject()
                .startObject("title").field("type", "string").field("store", "yes").field("analyzer", "ik").endObject()
                .startObject("content").field("type", "string").field("store", "yes").field("analyzer", "ik").endObject()
                .endObject()
                .endObject()
                .endObject();
        //添加Mapping
        PutMappingRequest mappingRequest = Requests.putMappingRequest("blog4").type("article").source(contentBuilder);
        client.admin().indices().putMapping(mappingRequest).get();
    }


}
