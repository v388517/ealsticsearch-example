package com.example.dao;

import com.example.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleDao extends ElasticsearchRepository<Article,Integer> {
    List<Article>findByTitle(String title);

    Page<Article>findByTitleLike(String title, Pageable pageable);




}
