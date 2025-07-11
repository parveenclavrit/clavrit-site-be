package com.clavrit.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.Dto.PartnerRequestDto;
import com.clavrit.Enums.ApiStatus;
import com.clavrit.Service.PartnerRequestService;
import com.clavrit.response.ApisResponse;

@RestController
@RequestMapping("/clavrit/partner")
public class PartnerRequestController {
	
	@Autowired
    private PartnerRequestService partnerRequestService;

    @PostMapping
    public ApisResponse createPartnerRequest(@RequestBody PartnerRequestDto dto) {
        try {
            PartnerRequestDto created = partnerRequestService.createRequest(dto);
            return new ApisResponse(ApiStatus.CREATED, "Partner request send successfully", created);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to create partner request", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ApisResponse getRequestById(@PathVariable Long id) {
        try {
            PartnerRequestDto result = partnerRequestService.getRequestById(id);
            return new ApisResponse(ApiStatus.OK, "Partner request fetched successfully", result);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Partner request not found", e.getMessage());
        }
    }

    @GetMapping
    public ApisResponse getAllRequests() {
        try {
            List<PartnerRequestDto> all = partnerRequestService.getAllRequests();
            return new ApisResponse(ApiStatus.OK, "All partner requests fetched", all);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to fetch partner requests", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ApisResponse updateRequest(@PathVariable Long id, @RequestBody PartnerRequestDto dto) {
        try {
            PartnerRequestDto updated = partnerRequestService.updateRequest(id, dto);
            return new ApisResponse(ApiStatus.OK, "Partner request updated successfully", updated);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.INTERNAL_ERROR, "Failed to update partner request", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ApisResponse deleteRequest(@PathVariable Long id) {
        try {
            String result = partnerRequestService.deleteRequest(id);
            return new ApisResponse(ApiStatus.OK, result, null);
        } catch (Exception e) {
            return new ApisResponse(ApiStatus.NOT_FOUND, "Failed to delete partner request", e.getMessage());
        }
    }

}
