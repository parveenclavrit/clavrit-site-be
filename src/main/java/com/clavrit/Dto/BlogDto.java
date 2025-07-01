package com.clavrit.Dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogDto {
	
	private Long id;

    private String title;

    private String subtitle;

    private String authorName;

    private String summary;

    private String content;

    private String advantages;

    private String disadvantages;

    private String conclusion;

    private List<String> imageUrl;

    private List<String> tags;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
