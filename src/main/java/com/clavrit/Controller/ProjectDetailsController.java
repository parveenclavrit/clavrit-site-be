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

import com.clavrit.Dto.ProjectDto;
import com.clavrit.Entity.ApiResponse;
import com.clavrit.Service.ProjectDetailsService;

@RestController
@RequestMapping("/clavrit/projects")
public class ProjectDetailsController {
	
	@Autowired
    private ProjectDetailsService projectService;
	
	@PostMapping
	public ApiResponse createProjectWithImages(
	        @RequestPart("project") ProjectDto projectDto,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images) {
	    try {
	        ProjectDto createdProject = projectService.createProjectDetails(projectDto, images);
	        return new ApiResponse(true, 201, "Project details saved successfully", createdProject);
	    } catch (Exception e) {
	        return new ApiResponse(false, 500, "Error while adding project with images: " + e.getMessage(), null);
	    }
	}


    @GetMapping("/{id}")
    public ApiResponse getProjectById(@PathVariable Long id) {
        try {
            ProjectDto project = projectService.getProjectDetailsById(id);
            return new ApiResponse(true, 200, "Project fetched successfully", project);
        } catch (Exception e) {
            return new ApiResponse(false, 404, "Project not found: " + e.getMessage(), null);
        }
    }

    @GetMapping
    public ApiResponse getAllProjects() {
        try {
            List<ProjectDto> projects = projectService.getAllProjectsDetails();
            return new ApiResponse(true, 200, "Projects fetched successfully", projects);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Error fetching projects: " + e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ApiResponse updateProject(@PathVariable Long id, @RequestPart("project") ProjectDto projectDto,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            ProjectDto updated = projectService.updateProjectDetails(id, projectDto,images);
            return new ApiResponse(true, 200, "Project updated successfully", updated);
        } catch (Exception e) {
            return new ApiResponse(false, 500, "Error updating project: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProjectDetails(id);
            return new ApiResponse(true, 200, "Project deleted successfully", null);
        } catch (Exception e) {
            return new ApiResponse(false, 404, "Error deleting project: " + e.getMessage(), null);
        }
    }

}
