package com.example.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "blog5", type = "article")
public class Article implements Serializable {
    @Id
    @Field(store = true,index = FieldIndex.not_analyzed)
    private Integer id;
    @Field(index = FieldIndex.analyzed,store = true,analyzer = "ik",searchAnalyzer = "ik",type = FieldType.String)
    private String title;
    @Field(index = FieldIndex.analyzed,store = true,analyzer = "ik",searchAnalyzer = "ik",type = FieldType.String)
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
