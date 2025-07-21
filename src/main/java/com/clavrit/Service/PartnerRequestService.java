package com.clavrit.Service;

import java.util.List;

import com.clavrit.Dto.PartnerRequestDto;
import com.clavrit.Entity.PartnerRequest;

public interface PartnerRequestService {
	
	PartnerRequestDto createRequest(PartnerRequestDto dto);

	PartnerRequestDto getRequestById(Long id);

	List<PartnerRequestDto> getAllRequests();

	PartnerRequestDto updateRequest(Long id, PartnerRequestDto dto);

	String deleteRequest(Long id);
	List<PartnerRequest> saveAllPartnerRequests(List<PartnerRequest> requests);
}
