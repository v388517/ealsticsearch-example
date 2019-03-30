package com.example.service;

import com.example.pojo.Article;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    /**
     * 保存
     * @param article
     */
    void save(Article article);

    /**
     * 批量插入
     * @param articles
     */
    void saveAll(List<Article> articles);


    /**
     * 删除
     * @param article
     */
    void delete(Article article);

    /**
     * 查询单个
     * @param id
     * @return
     */
    Article findOne(Integer id);

    /**
     * 查询所有
     * @return
     */
    Iterable<Article>findAll();

    /**
     * 分页
     * @param pageable
     * @return
     */
    Page<Article> findPage(Pageable pageable);


    /**
     * 排序
     */
    Iterable<Article> findSort();


    /**
     * 条件查询
     */
    List<Article>findByTitle(String title);

    /**
     * 条件 + 分页
     * @param title
     * @param pageable
     * @return
     */

    Page<Article>findByTitleLikeAndPage(String title, Pageable pageable);

}
