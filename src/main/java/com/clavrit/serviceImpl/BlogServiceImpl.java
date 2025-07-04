package com.clavrit.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.BlogDto;
import com.clavrit.Entity.Blog;
import com.clavrit.Repository.BlogRepository;
import com.clavrit.Service.BlogService;
import com.clavrit.mapper.BlogMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService{
	
	Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

    @Autowired
    private BlogRepository blogRepository;
    
    @Autowired
    private BlogMapper blogMapper;

    @Value("${file.upload.BlogImage}")
    private String basePath;

	@Override
	public BlogDto createBlog(BlogDto blogDto, List<MultipartFile> images) {
		try {
            Blog blog = blogMapper.toEntity(blogDto);
            List<String> imageUrls = saveImages(images);
            blog.setImageUrl(imageUrls);
            blog.setCreatedAt(LocalDateTime.now());
            blog.setUpdatedAt(LocalDateTime.now());

            Blog saved = blogRepository.save(blog);
            return blogMapper.toDto(saved);
        } catch (Exception e) {
            logger.error("Error while creating blog: {}", e.getMessage());
            throw new RuntimeException("Failed to create blog: " + e.getMessage());
        }
	}
    

	@Override
	public BlogDto updateBlog(Long id, BlogDto dto, List<MultipartFile> images) {
	    try {
	        Optional<Blog> optionalBlog = blogRepository.findById(id);
	        if (!optionalBlog.isPresent()) {
	            throw new RuntimeException("Blog not found with ID: " + id);
	        }

	        Blog blog = optionalBlog.get();

	        blog.setTitle(dto.getTitle() != null ? dto.getTitle() : blog.getTitle());
	        blog.setSubtitle(dto.getSubtitle() != null ? dto.getSubtitle() : blog.getSubtitle());
	        blog.setAuthorName(dto.getAuthorName() != null ? dto.getAuthorName() : blog.getAuthorName());
	        blog.setSummary(dto.getSummary() != null ? dto.getSummary() : blog.getSummary());
	        blog.setContent(dto.getContent() != null ? dto.getContent() : blog.getContent());
	        blog.setAdvantages(dto.getAdvantages() != null ? dto.getAdvantages() : blog.getAdvantages());
	        blog.setDisadvantages(dto.getDisadvantages() != null ? dto.getDisadvantages() : blog.getDisadvantages());
	        blog.setConclusion(dto.getConclusion() != null ? dto.getConclusion() : blog.getConclusion());
	        blog.setTags(dto.getTags() != null ? dto.getTags() : blog.getTags());


	        if (images != null && !images.isEmpty()) {
	            List<String> existingImages = blog.getImageUrl();
	            if (existingImages != null && !existingImages.isEmpty()) {
	                for (String path : existingImages) {
	                    try {
	                        File file = new File(path);
	                        if (file.exists()) {
	                            file.delete();
	                            logger.info("Deleted old image: {}", path);
	                        }
	                    } catch (Exception e) {
	                        logger.warn("Failed to delete image: {}", path);
	                    }
	                }
	            }

	            List<String> imageUrls = saveImages(images);
	            blog.setImageUrl(imageUrls);
	        }

	        blog.setUpdatedAt(LocalDateTime.now());

	        Blog updated = blogRepository.save(blog);
	        logger.info("Blog with ID {} updated successfully.", id);

	        return blogMapper.toDto(updated);

	    } catch (Exception e) {
	        logger.error("Error updating blog with ID {}: {}", id, e.getMessage());
	        throw new RuntimeException("Failed to update blog: " + e.getMessage());
	    }
	}


	@Override
	public BlogDto getBlogById(Long id) {
	    try {
	        Optional<Blog> optionalBlog = blogRepository.findById(id);
	        if (!optionalBlog.isPresent()) {
	            throw new RuntimeException("Blog not found with ID: " + id);
	        }
	        Blog blog = optionalBlog.get();
	        return blogMapper.toDto(blog);
	    } catch (Exception e) {
	        logger.error("Error fetching blog with ID {}: {}", id, e.getMessage());
	        throw new RuntimeException("Failed to fetch blog: " + e.getMessage());
	    }
	}

	@Override
	public List<BlogDto> getAllBlogs() {
	    try {
	        List<Blog> blogs = blogRepository.findAll();

	        if (blogs == null || blogs.isEmpty()) {
	            logger.warn("No blogs found.");
	            return new ArrayList<>();
	        }

	        List<BlogDto> blogDtos = new ArrayList<>();
	        for (Blog blog : blogs) {
	            blogDtos.add(blogMapper.toDto(blog));
	        }

	        return blogDtos;
	    } catch (Exception e) {
	        logger.error("Error fetching all blogs: {}", e.getMessage());
	        throw new RuntimeException("Failed to fetch blogs: " + e.getMessage());
	    }
	}

	@Override
	public String deleteBlog(Long id) {
	    try {
	        Optional<Blog> optionalBlog = blogRepository.findById(id);
	        if (!optionalBlog.isPresent()) {
	            return "Blog not found with ID: " + id;
	        }

	        blogRepository.delete(optionalBlog.get());

	        logger.info("Blog with ID {} deleted successfully.", id);
	        return "Blog with ID " + id + " deleted successfully.";
	    } catch (Exception e) {
	        logger.error("Error deleting blog with ID {}: {}", id, e.getMessage());
	        throw new RuntimeException("Failed to delete blog: " + e.getMessage());
	    }
	}

    private List<String> saveImages(List<MultipartFile> images) {
    	
        List<String> imagePaths = new ArrayList<>();
        if (images == null || images.isEmpty()) return imagePaths;

        File dir = new File(basePath);
        if (!dir.exists()) dir.mkdirs();

        for (MultipartFile file : images) {
            if (file != null && !file.isEmpty()) {
                try {
                    String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                    String fullPath = basePath + uniqueFileName;

                    File dest = new File(fullPath);
                    file.transferTo(dest);
                    logger.info("Saved blog image to: {}", fullPath);

                    imagePaths.add(fullPath);
                } catch (Exception e) {
                    logger.error("Image saving failed: {}", e.getMessage());
                    throw new RuntimeException("Error saving image: " + e.getMessage());
                }
            }
        }
        return imagePaths;    
    }
}
