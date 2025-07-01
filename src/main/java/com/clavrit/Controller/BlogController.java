package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.BlogDto;
import com.clavrit.Entity.ApiResponse;
import com.clavrit.Service.BlogService;

@RestController
@RequestMapping("/clavrit/blogs")
public class BlogController {
	
	@Autowired
    private BlogService blogService;
	
	@PostMapping
    public ApiResponse createBlog(
            @RequestPart("blog") BlogDto blogDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
			BlogDto createdBlog = blogService.createBlog(blogDto, images);
            return new ApiResponse(true, 201, "Blog created successfully", createdBlog);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Error creating blog: " + e.getMessage(), null);
        }
    }
	
	@PutMapping("/{id}")
    public ApiResponse updateBlog(
            @PathVariable Long id,
            @RequestPart("blog") BlogDto blogDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            BlogDto updatedBlog = blogService.updateBlog(id, blogDto, images);
            return new ApiResponse(true, 200, "Blog updated successfully", updatedBlog);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Error updating blog: " + e.getMessage(), null);
        }
    }
	
	@GetMapping("/{id}")
    public ApiResponse getBlogById(@PathVariable Long id) {
        try {
            BlogDto blog = blogService.getBlogById(id);
            return new ApiResponse(true, 200, "Blog fetched successfully", blog);
        } catch (Exception e) {
            return new ApiResponse(false, 404, "Error fetching blog: " + e.getMessage(), null);
        }
    }

    @GetMapping
    public ApiResponse getAllBlogs() {
        try {
            List<BlogDto> blogs = blogService.getAllBlogs();
            return new ApiResponse(true, 200, "Blogs fetched successfully", blogs);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Error fetching blogs: " + e.getMessage(), null);
        }
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse deleteBlog(@PathVariable Long id) {
        try {
            String result = blogService.deleteBlog(id);
            return new ApiResponse(true, 200, result, null);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Error deleting blog: " + e.getMessage(), null);
        }
    }

}
