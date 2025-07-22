package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Dto.BusinessStatItemDto;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.BusinessStatsService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/api/stats")
public class BusinessStatsController {

    @Autowired
    private BusinessStatsService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApisResponse createService(@RequestParam("title") String title,
                                      @RequestParam("value") String value,
                                      @RequestPart(value = "images", required = false) MultipartFile file) {
        try {
            BusinessStatItemDto dto = new BusinessStatItemDto();
            dto.setTitle(title);
            dto.setValue(value);

            BusinessStatItemDto savedDto = service.createStat(dto, file);
            return new ApisResponse(ApiStatus.OK, "Business stat created successfully", savedDto);

        } catch (Exception e) {
            return new ApisResponse(ApiStatus.BAD_REQUEST, "Failed to create stat: " + e.getMessage(), null);
        }
    }

    @GetMapping
    public ApisResponse getAll() {
        try {
            List<BusinessStatItemDto> list = service.getAllStats();
            return new ApisResponse(ApiStatus.OK, "Data fetched successfully", list);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Failed to fetch data", null);
        }
    }

    @GetMapping("/{id}")
    public ApisResponse getById(@PathVariable Long id) {
        try {
            BusinessStatItemDto dto = service.getStatById(id);
            return new ApisResponse(ApiStatus.OK, "Data fetched", dto);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Not found: " + e.getMessage(), null);
        }
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApisResponse update(@PathVariable Long id,
                               @RequestParam("title") String title,
                               @RequestParam("value") String value,
                               @RequestPart(value = "images", required = false) MultipartFile file) {
        try {
            BusinessStatItemDto dto = new BusinessStatItemDto();
            dto.setId(id);
            dto.setTitle(title);
            dto.setValue(value);

            BusinessStatItemDto updated = service.updateStat(dto, file);
            return new ApisResponse(ApiStatus.OK, "Updated successfully", updated);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.BAD_REQUEST, "Update failed: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ApisResponse delete(@PathVariable Long id) {
        try {
            service.deleteStat(id);
            return new ApisResponse(ApiStatus.OK, "Deleted successfully", null);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Delete failed: " + e.getMessage(), null);
        }
    }
}