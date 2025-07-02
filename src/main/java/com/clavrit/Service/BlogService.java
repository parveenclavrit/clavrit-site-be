package com.clavrit.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.BlogDto;

public interface BlogService {
	
	BlogDto createBlog(BlogDto blogDto, List<MultipartFile> images);

    BlogDto updateBlog(Long id, BlogDto blogDto, List<MultipartFile> images);

    BlogDto getBlogById(Long id);

    List<BlogDto> getAllBlogs();

    String deleteBlog(Long id);

}
