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
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.BlogService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/clavrit/blogs")
public class BlogController {
	
	@Autowired
    private BlogService blogService;
	
	@PostMapping
    public ApisResponse createBlog(
            @RequestPart("blog") BlogDto blogDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
			BlogDto createdBlog = blogService.createBlog(blogDto, images);
			return new ApisResponse(ApiStatus.CREATED, "Blog created successfully", createdBlog);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error creating blog", e.getMessage());
        }
    }
	
	@PutMapping("/{id}")
    public ApisResponse updateBlog(
            @PathVariable Long id,
            @RequestPart("blog") BlogDto blogDto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            BlogDto updatedBlog = blogService.updateBlog(id, blogDto, images);
            return new ApisResponse(ApiStatus.OK, "Blog updated successfully", updatedBlog);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error updating blog", e.getMessage());
        }
    }
	
	@GetMapping("/{id}")
    public ApisResponse getBlogById(@PathVariable Long id) {
        try {
            BlogDto blog = blogService.getBlogById(id);
            return new ApisResponse(ApiStatus.OK, "Blog fetched successfully", blog);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Error fetching blog", e.getMessage());
        }
    }

    @GetMapping
    public ApisResponse getAllBlogs() {
        try {
            List<BlogDto> blogs = blogService.getAllBlogs();
            return new ApisResponse(ApiStatus.OK, "Blogs fetched successfully", blogs);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error fetching blogs", e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ApisResponse deleteBlog(@PathVariable Long id) {
        try {
            String result = blogService.deleteBlog(id);
            return new ApisResponse(ApiStatus.OK, result, null);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error deleting blog", e.getMessage());
        }
    }

}
