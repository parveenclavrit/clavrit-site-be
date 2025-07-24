package com.clavrit.Controller;

import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.clavrit.Entity.Blog;
import com.clavrit.Entity.ClavritService;
import com.clavrit.Entity.Client;
import com.clavrit.Entity.JobApplication;
import com.clavrit.Entity.JobDetail;
import com.clavrit.Entity.MailRecord;
import com.clavrit.Entity.PartnerRequest;
import com.clavrit.Entity.Project;
import com.clavrit.Entity.Subscriber;
import com.clavrit.Service.BlogService;
import com.clavrit.Service.ClientService;
import com.clavrit.Service.ContactService;
import com.clavrit.Service.ExcelImportService;
import com.clavrit.Service.JobApplicationService;
import com.clavrit.Service.JobDetailService;
import com.clavrit.Service.PartnerRequestService;
import com.clavrit.Service.ProjectDetailsService;
import com.clavrit.Service.ServiceManagementService;
import com.clavrit.Service.SubscriptionService;

@RestController
@RequestMapping("/api/import")
public class DataImportController {

	@Autowired
	ServiceManagementService managementService;

	@Autowired
	BlogService blogService;
	
	@Autowired
	ContactService mailService;

	@Autowired
	ClientService clientService;

	@Autowired
	JobApplicationService applicationService;

	@Autowired
	JobDetailService detailService;

	@Autowired
	ContactService mailRecordService;

	@Autowired
	ProjectDetailsService detailsService;

	@Autowired
	PartnerRequestService partnerRequestService;

	@Autowired
	SubscriptionService subService;
	@Autowired
	ExcelImportService excelImportService;

	@PostMapping("/upload")
	public ResponseEntity<Map<String, Object>> uploadExcelAndSaveToDb(@RequestParam("file") MultipartFile file) {
		Map<String, Object> resultMap = new LinkedHashMap<>();
		try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {

			Sheet servicesSheet = workbook.getSheet("Services");
			Sheet blogsSheet = workbook.getSheet("Blogs");
			Sheet clientsSheet = workbook.getSheet("Clients");
			Sheet projectsSheet = workbook.getSheet("Projects");
			Sheet mailRecordsSheet = workbook.getSheet("MailRecords");
			Sheet partnerRequestsSheet = workbook.getSheet("PartnerRequests");
			Sheet jobsSheet = workbook.getSheet("JobDetails");
			Sheet subscribersSheet = workbook.getSheet("Subscribers");
			Sheet applicationsSheet = workbook.getSheet("JobApplications");

			if (servicesSheet != null) {
				List<ClavritService> services = excelImportService.readServices(servicesSheet);
				
				resultMap.put("services", services);
				managementService.createServiceList(services);
			}

			if (blogsSheet != null) {
				List<Blog> blogs = excelImportService.readBlogs(blogsSheet);
				resultMap.put("blogs", blogs);
				blogService.createBlogList(blogs);
			}

			if (clientsSheet != null) {
				List<Client> clients = excelImportService.readClients(clientsSheet);
				resultMap.put("clients", clients);
				clientService.createClientList(clients);
			}

			if (projectsSheet != null) {
				List<Project> projects = excelImportService.readProjects(projectsSheet);
				resultMap.put("projects", projects);
				detailsService.saveAllProjects(projects);
			}

			if (mailRecordsSheet != null) {
				List<MailRecord> mails = excelImportService.readMailRecords(mailRecordsSheet);
	            resultMap.put("mailRecords", mails);
	            mailRecordService.SaveRecordList(mails);
	        }

			if (partnerRequestsSheet != null) {
				List<PartnerRequest> partnerRequests = excelImportService.readPartnerRequests(partnerRequestsSheet);
				resultMap.put("partnerRequests", partnerRequests);
				partnerRequestService.saveAllPartnerRequests(partnerRequests);
			}

			if (jobsSheet != null) {
				List<JobDetail> jobs = excelImportService.readJobs(jobsSheet);
				resultMap.put("jobs", jobs);
				detailService.saveAllJobs(jobs);
			}

			if (subscribersSheet != null) {
				List<Subscriber> subscribers = excelImportService.readSubscribers(subscribersSheet);
				resultMap.put("subscribers", subscribers);
				subService.saveAllSubscribers(subscribers);
			}

			if (applicationsSheet != null) {
				List<JobApplication> applications = excelImportService.readApplications(applicationsSheet);
				resultMap.put("applications", applications);
				applicationService.saveAllJobApplications(applications);
			}

			return ResponseEntity.ok(resultMap);

		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
		}
	}

	@PostMapping("/preview")
	public ResponseEntity<Map<String, Object>> previewExcelData(@RequestParam("file") MultipartFile file) {
		Map<String, Object> resultMap = new LinkedHashMap<>();
		try (InputStream inputStream = file.getInputStream(); Workbook workbook = WorkbookFactory.create(inputStream)) {

			Sheet servicesSheet = workbook.getSheet("Services");
			Sheet blogsSheet = workbook.getSheet("Blogs");
			Sheet clientsSheet = workbook.getSheet("Clients");
			Sheet projectsSheet = workbook.getSheet("Projects");
			Sheet mailRecordsSheet = workbook.getSheet("MailRecords");
			Sheet partnerRequestsSheet = workbook.getSheet("PartnerRequests");
			Sheet jobsSheet = workbook.getSheet("Jobs");
			Sheet subscribersSheet = workbook.getSheet("Subscribers");
			Sheet applicationsSheet = workbook.getSheet("JobApplications");

			if (servicesSheet != null)
				resultMap.put("services", excelImportService.readServices(servicesSheet));
			if (blogsSheet != null)
				resultMap.put("blogs", excelImportService.readBlogs(blogsSheet));
			if (clientsSheet != null)
				resultMap.put("clients", excelImportService.readClients(clientsSheet));
			if (projectsSheet != null)
				resultMap.put("projects", excelImportService.readProjects(projectsSheet));
			if (mailRecordsSheet != null)
				resultMap.put("mailRecords", excelImportService.readMailRecords(mailRecordsSheet));
			if (partnerRequestsSheet != null)
				resultMap.put("partnerRequests", excelImportService.readPartnerRequests(partnerRequestsSheet));
			if (jobsSheet != null)
				resultMap.put("jobs", excelImportService.readJobs(jobsSheet));
			if (subscribersSheet != null)
				resultMap.put("subscribers", excelImportService.readSubscribers(subscribersSheet));
			if (applicationsSheet != null)
				resultMap.put("applications", excelImportService.readApplications(applicationsSheet));

			return ResponseEntity.ok(resultMap);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
		}
	}

}
