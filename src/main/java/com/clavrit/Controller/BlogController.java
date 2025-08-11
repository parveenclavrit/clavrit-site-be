package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.clavrit.Dto.BlogDto;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.BlogService;
import com.clavrit.response.ApisResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/clavrit/blogs")
public class BlogController {
	
	@Autowired
    private BlogService blogService;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ApisResponse createBlog(MultipartHttpServletRequest request,
						@RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage	) {
	    try {
	        String blogJson = request.getParameter("blog");
	        ObjectMapper mapper = new ObjectMapper();
	        BlogDto blogDto = mapper.readValue(blogJson, BlogDto.class);

	        BlogDto createdBlog = blogService.createBlog(blogDto, bannerImage);
	        return new ApisResponse(ApiStatus.CREATED, "Blog created successfully", createdBlog);
	    }catch (JsonProcessingException e) {
	        return new ApisResponse(ApiStatus.BAD_REQUEST, "Invalid blog JSON", e.getMessage());
	    }
	    catch (Exception e) {
	        return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error creating blog", e.getMessage());
	    }
	}

	
	@PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApisResponse updateBlog(
            @PathVariable Long id,
            @RequestPart("blog") String blogJson,
            @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage) {
        try {
        	ObjectMapper mapper = new ObjectMapper();
        	BlogDto blogDto = mapper.readValue(blogJson, BlogDto.class);
        	
            BlogDto updatedBlog = blogService.updateBlog(id, blogDto, bannerImage);
            return new ApisResponse(ApiStatus.OK, "Blog updated successfully", updatedBlog);
        }catch (JsonProcessingException e) {
            return new ApisResponse(ApiStatus.BAD_REQUEST, "Invalid blog JSON", e.getMessage());
        } 
        catch (Exception e) {
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
