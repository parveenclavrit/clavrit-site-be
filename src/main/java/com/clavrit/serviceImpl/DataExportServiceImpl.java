package com.clavrit.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.clavrit.Entity.Blog;
import com.clavrit.Entity.ClavritService;
import com.clavrit.Entity.Client;
import com.clavrit.Entity.JobApplication;
import com.clavrit.Entity.JobDetail;
import com.clavrit.Entity.MailRecord;
import com.clavrit.Entity.PartnerRequest;
import com.clavrit.Entity.Project;
import com.clavrit.Entity.Subscriber;
import com.clavrit.Repository.BlogRepository;
import com.clavrit.Repository.ClientRepository;
import com.clavrit.Repository.JobApplicationRepository;
import com.clavrit.Repository.JobDetailRepository;
import com.clavrit.Repository.MailRecordRepository;
import com.clavrit.Repository.PartnerRequestRepository;
import com.clavrit.Repository.ProjectRepository;
import com.clavrit.Repository.ServiceRepository;
import com.clavrit.Repository.SubscriberRepository;

@Service
public class DataExportServiceImpl {
	
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	BlogRepository blogRepository;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	JobApplicationRepository jobApplicationRepository;
	
	@Autowired
	JobDetailRepository jobDetailRepository;
	
	@Autowired
	MailRecordRepository mailRecordRepository;
	
	@Autowired
	PartnerRequestRepository partnerRequestRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	SubscriberRepository subscriberRepository;
	
	
	
	public void exportAllDataToTemplate(HttpServletResponse response) {
	    try {
	        // Load your predefined Excel template
	    	InputStream templateStream = new ClassPathResource("templates/sample_import_template_with_guide.xlsx").getInputStream();

	        Workbook workbook = new XSSFWorkbook(templateStream);

	        // Fetch all data from DB
	        List<ClavritService> services = serviceRepository.findAll();
	        List<Blog> blogs = blogRepository.findAll();
	        List<Project> projects = projectRepository.findAll();
	        List<JobDetail> jobDetails = jobDetailRepository.findAll();
	        List<JobApplication> jobApplications = jobApplicationRepository.findAll();
	        List<Client> clients = clientRepository.findAll();
	        List<MailRecord> mailRecords = mailRecordRepository.findAll();
	        List<PartnerRequest> partnerRequests = partnerRequestRepository.findAll();
	        List<Subscriber> subscribers = subscriberRepository.findAll();

	        // Populate all sheets
	        populateServicesSheet(workbook, services);
	        populateBlogsSheet(workbook, blogs);
	        populateProjectsSheet(workbook, projects);
	        populateJobDetailsSheet(workbook, jobDetails);
	        populateJobApplicationsSheet(workbook, jobApplications);
	        populateClientsSheet(workbook, clients);
	        populateMailRecordsSheet(workbook, mailRecords);
	        populatePartnerRequestsSheet(workbook, partnerRequests);
	        populateSubscribersSheet(workbook, subscribers);

	        // Set response headers
	        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
	        response.setHeader("Content-Disposition", "attachment; filename=\"clavrit_export.xlsx\"");

	        // Write the workbook to output stream
	        OutputStream out = response.getOutputStream();
	        workbook.write(out);
	        workbook.close();
	        out.flush();
	        out.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Failed to export data to Excel template", e);
	    }
	}

	
	private void populateServicesSheet(Workbook workbook, List<ClavritService> services) {
	    Sheet sheet = workbook.getSheet("Services");
	    
	    Row header = sheet.createRow(0);
	    int col = 0;
	    header.createCell(col++).setCellValue("title");
	    header.createCell(col++).setCellValue("subheading");
	    header.createCell(col++).setCellValue("category");
	    header.createCell(col++).setCellValue("description");
	    header.createCell(col++).setCellValue("content");
	    header.createCell(col++).setCellValue("image_urls");
	    
	    int rowNum = 1;

	    for (ClavritService service : services) {
	        Row row = sheet.createRow(rowNum++);
	        col = 0;

	        row.createCell(col++).setCellValue(service.getTitle());
	        row.createCell(col++).setCellValue(service.getSubheading());
	        row.createCell(col++).setCellValue(service.getCategory()); 
	        row.createCell(col++).setCellValue(service.getDescription());
	        row.createCell(col++).setCellValue(service.getContent());
	        row.createCell(col++).setCellValue(String.join(", ", service.getImageUrls()));
	    }
	}


	private void populateBlogsSheet(Workbook workbook, List<Blog> blogs) {
	    Sheet sheet = workbook.getSheet("Blogs");
	    int rowNum = 1;
	    for (Blog blog : blogs) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(blog.getTitle());
	        row.createCell(col++).setCellValue(blog.getSubtitle());
	        row.createCell(col++).setCellValue(blog.getAuthorName());
	        row.createCell(col++).setCellValue(blog.getSummary());
	        row.createCell(col++).setCellValue(blog.getContent());
	        row.createCell(col++).setCellValue(blog.getAdvantages());
	        row.createCell(col++).setCellValue(blog.getDisadvantages());
	        row.createCell(col++).setCellValue(blog.getConclusion());
	        row.createCell(col++).setCellValue(String.join(", ", blog.getImageUrl()));
	        row.createCell(col++).setCellValue(String.join(", ", blog.getTags()));
	    }
	}

	private void populateProjectsSheet(Workbook workbook, List<Project> projects) {
	    Sheet sheet = workbook.getSheet("Projects");
	    int rowNum = 1;
	    for (Project project : projects) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(project.getTitle());
	        row.createCell(col++).setCellValue(project.getSummary());
	        row.createCell(col++).setCellValue(String.join(", ", project.getTechnologies()));
	        row.createCell(col++).setCellValue(String.join(", ", project.getKeyPoints()));
	        row.createCell(col++).setCellValue(String.join(", ", project.getImageUrl()));
	    }
	}

	private void populateJobDetailsSheet(Workbook workbook, List<JobDetail> jobs) {
	    Sheet sheet = workbook.getSheet("JobDetails");
	    int rowNum = 1;
	    for (JobDetail job : jobs) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(job.getJobDesignation());
	        row.createCell(col++).setCellValue(String.join(", ", job.getJobResponsibility()));
	        row.createCell(col++).setCellValue(String.join(", ", job.getJobQualification()));
	        row.createCell(col++).setCellValue(String.join(", ", job.getCompetencies()));
	        row.createCell(col++).setCellValue(job.getJobCategory());
	        row.createCell(col++).setCellValue(job.getJobType());
	        row.createCell(col++).setCellValue(job.getJobLocation());
	        row.createCell(col++).setCellValue(job.getIndustry());
	    }
	}

	private void populateJobApplicationsSheet(Workbook workbook, List<JobApplication> applications) {
	    Sheet sheet = workbook.getSheet("JobApplications");
	    int rowNum = 1;
	    for (JobApplication app : applications) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(app.getFullName());
	        row.createCell(col++).setCellValue(app.getEmail());
	        row.createCell(col++).setCellValue(app.getPhone());
	        row.createCell(col++).setCellValue(app.getJobAppliedFor());
	        row.createCell(col++).setCellValue(app.getQualification());
	        row.createCell(col++).setCellValue(app.getTotalYOE());
	        row.createCell(col++).setCellValue(app.getRelevantExperience());
	        row.createCell(col++).setCellValue(app.getCurrentCompany());
	        row.createCell(col++).setCellValue(app.getCurrentCTC());
	        row.createCell(col++).setCellValue(app.getNoticePeriod());
	        row.createCell(col++).setCellValue(app.getCoverLetter());
	        row.createCell(col++).setCellValue(app.getResumeFilePath());
	        row.createCell(col++).setCellValue(app.getResumeFileName());
	        row.createCell(col++).setCellValue(app.getResumeFileType());
	    }
	}

	private void populateClientsSheet(Workbook workbook, List<Client> clients) {
	    Sheet sheet = workbook.getSheet("Clients");
	    int rowNum = 1;
	    for (Client client : clients) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(client.getName());
	        row.createCell(col++).setCellValue(client.getCompany());
	        row.createCell(col++).setCellValue(client.getEmail());
	        row.createCell(col++).setCellValue(client.getPhone());
	        row.createCell(col++).setCellValue(client.getLogoImage());
	    }
	}

	private void populateMailRecordsSheet(Workbook workbook, List<MailRecord> records) {
	    Sheet sheet = workbook.getSheet("MailRecords");
	    int rowNum = 1;
	    for (MailRecord mail : records) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(mail.getEmail());
	        row.createCell(col++).setCellValue(mail.getName());
	        row.createCell(col++).setCellValue(mail.getSubject());
	        row.createCell(col++).setCellValue(mail.getMessage());
	        row.createCell(col++).setCellValue(mail.getDestination());
	        row.createCell(col++).setCellValue(mail.getCompany());
	        row.createCell(col++).setCellValue(mail.getPhone());
	        row.createCell(col++).setCellValue(mail.getCountry());
	    }
	}

	private void populatePartnerRequestsSheet(Workbook workbook, List<PartnerRequest> requests) {
	    Sheet sheet = workbook.getSheet("PartnerRequests");
	    int rowNum = 1;
	    for (PartnerRequest request : requests) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(request.getFullName());
	        row.createCell(col++).setCellValue(request.getCompanyName());
	        row.createCell(col++).setCellValue(request.getEmail());
	        row.createCell(col++).setCellValue(request.getPhone());
	        row.createCell(col++).setCellValue(request.getMessage());
	    }
	}

	private void populateSubscribersSheet(Workbook workbook, List<Subscriber> subscribers) {
	    Sheet sheet = workbook.getSheet("Subscribers");
	    int rowNum = 1;
	    for (Subscriber sub : subscribers) {
	        Row row = sheet.createRow(rowNum++);
	        int col = 0;
	        row.createCell(col++).setCellValue(sub.getEmail());
	    }
	}


}
