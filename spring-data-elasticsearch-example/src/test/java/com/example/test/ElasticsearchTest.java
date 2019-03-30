package com.example.test;

import com.example.pojo.Article;
import com.example.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 索引的使用
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ElasticsearchTest {

    @Autowired
    private ArticleService articleServcie;
    @Autowired

    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 新建索引和映射
     */
    @Test
    public void createIndex(){
        elasticsearchTemplate.createIndex(Article.class);
        elasticsearchTemplate.putMapping(Article.class);
    }

    /**
     * 删除索引
     */
    @Test
    public  void deleteIndex(){
         elasticsearchTemplate.deleteIndex(Article.class);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndexs(){
       elasticsearchTemplate.deleteIndex("blog5");
    }

}
