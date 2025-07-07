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
        dto.setSubtitle(blog.getSubtitle());
        dto.setAuthorName(blog.getAuthorName());
        dto.setSummary(blog.getSummary());
        dto.setContent(blog.getContent());
        dto.setAdvantages(blog.getAdvantages());
        dto.setDisadvantages(blog.getDisadvantages());
        dto.setConclusion(blog.getConclusion());
        dto.setImageUrl(blog.getImageUrl());
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
        blog.setSubtitle(dto.getSubtitle());
        blog.setAuthorName(dto.getAuthorName());
        blog.setSummary(dto.getSummary());
        blog.setContent(dto.getContent());
        blog.setAdvantages(dto.getAdvantages());
        blog.setDisadvantages(dto.getDisadvantages());
        blog.setConclusion(dto.getConclusion());
        blog.setImageUrl(dto.getImageUrl());
        blog.setTags(dto.getTags());

        return blog;
    }
}

