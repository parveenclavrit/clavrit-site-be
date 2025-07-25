package com.clavrit.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.BlogDto;
import com.clavrit.Entity.Blog;

public interface BlogService {
	
	BlogDto createBlog(BlogDto blogDto, List<MultipartFile> images);

    BlogDto updateBlog(Long id, BlogDto blogDto, List<MultipartFile> images);

    BlogDto getBlogById(Long id);

    List<BlogDto> getAllBlogs();

    String deleteBlog(Long id);
    List<Blog> createBlogList( List<Blog> blogs);

}
