package com.example.service.impl;

import com.example.dao.ArticleDao;
import com.example.pojo.Article;
import com.example.service.ArticleService;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;
    @Override
    public void save(Article article) {
        articleDao.save(article);
    }

    @Override
    public void saveAll(List<Article> articles) {
       articleDao.save(articles);
    }

    @Override
    public void delete(Article article) {
        articleDao.delete(article);
    }

    @Override
    public Article findOne(Integer id) {
        return articleDao.findOne(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleDao.findAll();
    }

    @Override
    public Page<Article> findPage(Pageable pageable) {
        return articleDao.findAll(pageable);
    }

    @Override
    public Iterable<Article> findSort() {
        return articleDao.findAll(new Sort(Sort.Direction.DESC,"id"));
    }

    @Override
    public List<Article> findByTitle(String title) {
        return articleDao.findByTitle(title);
    }

    @Override
    public Page<Article> findByTitleLikeAndPage(String title, Pageable pageable) {
        return articleDao.findByTitleLike(title,pageable);
    }


}
