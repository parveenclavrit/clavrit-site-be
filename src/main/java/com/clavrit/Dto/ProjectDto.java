package com.clavrit.Dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
	
	private Long id;
	
    private String title;
    
    private String summary;
    
    private List<String> imageUrl;
    
    private List<String> technologies;
    
    private Map<String, String> keyPoints;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

}
