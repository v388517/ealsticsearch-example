package com.example.test;


import com.example.pojo.Article;
import com.example.service.ArticleService;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ElasticsearchDemoTest {
    @Autowired
    private Client client;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ArticleService articleService;

    /**
     * 添加1条记录
     */
    @Test
    public void test() {
        Article article = new Article();
        article.setId(1);
        article.setTitle("五一旅游网");
        article.setContent("五一旅游平台，主要是旅游资讯...");
        elasticsearchTemplate.createIndex(Article.class);
        elasticsearchTemplate.putMapping(Article.class);
        articleService.save(article);
        System.out.println(client);
    }

    /**
     * 插入100条数据
     */
    @Test
    public void testInsertAll() {
        List<Article> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitle("【" + i + "】五一旅游网");
            article.setContent("【" + i + "】五一旅游平台，主要是旅游资讯...");
            list.add(article);
        }
        articleService.saveAll(list);

    }

    /**
     * 单个查询
     */
    @Test
    public void testFindOne() {
        Article article = articleService.findOne(3);
        System.out.println(article);
    }

    /**
     * 查询所有
     */
    @Test
    public void testFindAll() {
        Iterable<Article> articles = articleService.findAll();
        articles.forEach(System.out::println);
    }

    /**
     * 分页查询
     */
    @Test
    public void testFindPage() {
        Pageable pageable = new PageRequest(1, 5);
        Page<Article> page = articleService.findPage(pageable);
        List<Article> content = page.getContent();
        content.forEach(System.out::println);
    }
    /**
     * 排序查询
     */
    @Test
    public void testFindSort(){
        Iterable<Article> sort = articleService.findSort();
        sort.forEach(System.out::println);
    }




    /**
     * 条件查询
     */
    @Test
    public void testFindByTitle() {
        List<Article> title = articleService.findByTitle("【10");
        title.forEach(System.out::println);

    }
    @Test
    public void testFindByTitleLikeAndPage(){
        Pageable pageable=new PageRequest(2,3);
        Page<Article> page = articleService.findByTitleLikeAndPage("1*", pageable);
        List<Article> content = page.getContent();
        content.forEach(System.out::println);
    }



}





