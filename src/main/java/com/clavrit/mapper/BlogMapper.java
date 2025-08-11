package com.clavrit.mapper;

import org.springframework.stereotype.Component;

import com.clavrit.Dto.BlogDto;
import com.clavrit.Entity.Blog;

@Component
public class BlogMapper {

    public BlogDto toDto(Blog blog) {
        if (blog == null) return null;

        BlogDto dto = new BlogDto();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setSlug(blog.getSlug());
        dto.setPublish(blog.getPublish());
        dto.setAuthorName(blog.getAuthorName());
        dto.setBannerUrl(blog.getBannerUrl());
        dto.setContent(blog.getContent());
        dto.setSerpTitle(blog.getSerpTitle());
        dto.setSerpMetaDescription(blog.getSerpMetaDescription());
        dto.setTags(blog.getTags());
        dto.setCreatedAt(blog.getCreatedAt());
        dto.setUpdatedAt(blog.getUpdatedAt());

        return dto;
    }

    public Blog toEntity(BlogDto dto) {
        if (dto == null) return null;

        Blog blog = new Blog();
        blog.setId(dto.getId());
        blog.setTitle(dto.getTitle());
        blog.setSlug(dto.getSlug());
        blog.setPublish(dto.getPublish());
        blog.setAuthorName(dto.getAuthorName());
        blog.setBannerUrl(dto.getBannerUrl());
        blog.setContent(dto.getContent());
        blog.setSerpTitle(dto.getSerpTitle());
        blog.setSerpMetaDescription(dto.getSerpMetaDescription());
        blog.setTags(dto.getTags());
        blog.setCreatedAt(dto.getCreatedAt());
        blog.setUpdatedAt(dto.getUpdatedAt());

        return blog;
    }
}

