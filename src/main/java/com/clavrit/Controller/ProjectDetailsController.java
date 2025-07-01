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
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.ProjectDetailsService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/clavrit/projects")
public class ProjectDetailsController {
	
	@Autowired
    private ProjectDetailsService projectService;
	
	@PostMapping
	public ApisResponse createProjectWithImages(
	        @RequestPart("project") ProjectDto projectDto,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images) {
	    try {
	        ProjectDto createdProject = projectService.createProjectDetails(projectDto, images);
	        return new ApisResponse(ApiStatus.CREATED, "Project details saved successfully", createdProject);
	    } catch (Exception e) {
	    	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error while adding project", e.getMessage());
	    }
	}


    @GetMapping("/{id}")
    public ApisResponse getProjectById(@PathVariable Long id) {
        try {
            ProjectDto project = projectService.getProjectDetailsById(id);
            return new ApisResponse(ApiStatus.OK, "Project fetched successfully", project);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Project not found", e.getMessage());
        }
    }

    @GetMapping
    public ApisResponse getAllProjects() {
        try {
            List<ProjectDto> projects = projectService.getAllProjectsDetails();
            return new ApisResponse(ApiStatus.OK, "Projects fetched successfully", projects);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error fetching projects", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApisResponse updateProject(@PathVariable Long id, @RequestPart("project") ProjectDto projectDto,
	        @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            ProjectDto updated = projectService.updateProjectDetails(id, projectDto,images);
            return new ApisResponse(ApiStatus.OK, "Project updated successfully", updated);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Error updating project", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApisResponse deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProjectDetails(id);
            return new ApisResponse(ApiStatus.OK, "Project deleted successfully", null);
        } catch (Exception e) {
        	return new ApisResponse(ApiStatus.NOT_FOUND, "Error deleting project", e.getMessage());
        }
    }

}
