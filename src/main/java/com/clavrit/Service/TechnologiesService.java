package com.clavrit.Service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.TechnologiesResponse;

public interface TechnologiesService {
	
	TechnologiesResponse save(MultipartFile file);
    TechnologiesResponse update(Long id, MultipartFile file);
    String delete(Long id);
    TechnologiesResponse getById(Long id);
    List<TechnologiesResponse> getAll();

}
