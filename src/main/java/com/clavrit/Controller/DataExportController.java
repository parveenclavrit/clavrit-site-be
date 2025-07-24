package com.clavrit.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clavrit.serviceImpl.DataExportServiceImpl;

@RestController
@RequestMapping("/api/export")
public class DataExportController {
	
	@Autowired
	DataExportServiceImpl dataExportServiceImpl;

	
	@GetMapping
	public void exportExcel(HttpServletResponse response) {
		dataExportServiceImpl.exportAllDataToTemplate(response);
	}

}
