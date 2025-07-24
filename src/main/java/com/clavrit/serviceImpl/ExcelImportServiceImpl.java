package com.clavrit.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Value;
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
import com.clavrit.Service.ExcelImportService;

@Service
public class ExcelImportServiceImpl implements ExcelImportService {
	
	
	 @Value("${PUBLIC_URL_BASE}")
	    private String PUBLIC_URL_BASE;

    @Override
    public List<ClavritService> readServices(Sheet sheet) {
        List<ClavritService> services = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null || isRowEmpty(row)) {
                continue;
            }
            ClavritService service = new ClavritService();
            service.setName(getCellValue(row, 0));
            service.setType(getCellValue(row, 1));
            service.setDescription(getCellValue(row, 2));
            String imageUrlsRaw = getCellValue(row, 3); 
            List<String> imageUrls = buildFullImageUrls(imageUrlsRaw);
            service.setImageUrl(imageUrls);
            services.add(service);
        }
        return services;
    }

    @Override
    public List<Blog> readBlogs(Sheet sheet) {
        List<Blog> blogs = new ArrayList<>();

        // Assuming first row (0) is header
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Blog blog = new Blog();

            blog.setTitle(getCellValue(row, 0));
            blog.setSubtitle(getCellValue(row, 1));
            blog.setAuthorName(getCellValue(row, 2));
            blog.setSummary(getCellValue(row, 3));
            blog.setContent(getCellValue(row, 4));
            blog.setAdvantages(getCellValue(row, 5));
            blog.setDisadvantages(getCellValue(row, 6));
            blog.setConclusion(getCellValue(row, 7));
            
            String imageUrlsRaw = getCellValue(row, 8); 
            List<String> imageUrls = buildFullImageUrls(imageUrlsRaw);
            blog.setImageUrl(imageUrls);
            
            String tags = row.getCell(9).getStringCellValue();                         
            List<String> tagsList = new ArrayList<>(Arrays.asList(tags.split("\\s*,\\s*")));
            blog.setTags(tagsList);
            
            // Optional: You can allow overriding createdAt and updatedAt via Excel (cols 10 & 11), or just default to now:
            blog.setCreatedAt(LocalDateTime.now());
            blog.setUpdatedAt(LocalDateTime.now());

            blogs.add(blog);
        }

        return blogs;
    }


    @Override
    public List<Project> readProjects(Sheet sheet) {
        List<Project> projects = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Project project = new Project();
            project.setTitle(row.getCell(0).getStringCellValue());
            project.setSummary(row.getCell(1).getStringCellValue());
           

            // Read imageUrl as comma-separated
            String imageUrlStr = row.getCell(4).getStringCellValue();
            List<String> imageUrls = buildFullImageUrls(imageUrlStr);
            project.setImageUrl(imageUrls);

            // Read technologies as comma-separated
            String techStr = row.getCell(2).getStringCellValue();
            List<String> technologies = new ArrayList<>(Arrays.asList(techStr.split("\\s*,\\s*")));
            project.setTechnologies(technologies);

            // Read keyPoints as comma-separated
            String keyPointsStr = row.getCell(3).getStringCellValue();
            List<String> keyPoints = new ArrayList<>(Arrays.asList(keyPointsStr.split("\\s*,\\s*")));
            project.setKeyPoints(keyPoints);
            project.setCreatedAt(LocalDateTime.now());
            projects.add(project);
        }
        return projects;
    }


    @Override
    public List<JobDetail> readJobs(Sheet sheet) {
        List<JobDetail> jobs = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            JobDetail job = new JobDetail();
            //job.setTitle(row.getCell(0).getStringCellValue());
            job.setJobDesignation(row.getCell(0).getStringCellValue());

            // Comma-separated values for list fields
            String JobResponsibilities = row.getCell(1).getStringCellValue();                         
            List<String> JobResponsibilityList = new ArrayList<>(Arrays.asList(JobResponsibilities.split("\\s*,\\s*")));
            job.setJobResponsibility(JobResponsibilityList);
            
            String jobQualifications = row.getCell(2).getStringCellValue();                         
            List<String> jobQualificationList = new ArrayList<>(Arrays.asList(jobQualifications.split("\\s*,\\s*")));
            job.setJobQualification(jobQualificationList);
            
            String competencies = row.getCell(3).getStringCellValue();                         
            List<String> competenciesList = new ArrayList<>(Arrays.asList(competencies.split("\\s*,\\s*")));
            job.setCompetencies(competenciesList);
            
            job.setJobCategory(row.getCell(4).getStringCellValue());
            job.setJobType(row.getCell(5).getStringCellValue());
            job.setJobLocation(row.getCell(6).getStringCellValue());
            job.setIndustry(row.getCell(7).getStringCellValue());
            job.setCreateAt(LocalDateTime.now());

            jobs.add(job);
        }
        return jobs;
    }

    @Override
    public List<JobApplication> readApplications(Sheet sheet) {
        List<JobApplication> applications = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            JobApplication application = new JobApplication();

            application.setFullName(getCellValue(row, 0));
            application.setEmail(getCellValue(row, 1));
            application.setPhone(getCellValue(row, 2));
            application.setJobAppliedFor(getCellValue(row, 3));
            application.setQualification(getCellValue(row, 4));
            application.setTotalYOE(getCellValue(row, 5));
            application.setRelevantExperience(getCellValue(row, 6));
            application.setCurrentCompany(getCellValue(row, 7));
            application.setCurrentCTC(getCellValue(row, 8));
            application.setNoticePeriod(getCellValue(row, 9));
            application.setCoverLetter(getCellValue(row, 10));
            String imageUrlStr = getCellValue(row, 11);
            List<String> resume = buildFullImageUrls(imageUrlStr);
            application.setResumeFilePath(resume.get(0));
            application.setResumeFileName(getCellValue(row, 12));
            application.setResumeFileType(getCellValue(row, 13));
            applications.add(application);
        }

        return applications;
    }
    
    @Override
    public List<MailRecord> readMailRecords(Sheet sheet) {
        List<MailRecord> records = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            MailRecord record = new MailRecord();
            record.setEmail(getCellValue(row, 0));
            record.setName(getCellValue(row, 1));
            record.setSubject(getCellValue(row, 2));
            record.setMessage(getCellValue(row, 3));
            record.setDestination(getCellValue(row, 4));
            record.setCompany(getCellValue(row, 5));
            record.setPhone(getCellValue(row, 6));
            record.setCountry(getCellValue(row, 7));
            record.setSentAt(LocalDateTime.now());

            records.add(record);
        }

        return records;
    }

    
    @Override
    public List<Client> readClients(Sheet sheet) {
        List<Client> clients = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            Client client = new Client();
            client.setName(getCellValue(row, 0));
            client.setCompany(getCellValue(row, 1));
            client.setEmail(getCellValue(row, 2));
            client.setPhone(getCellValue(row, 3));
            String imageUrlStr = row.getCell(4).getStringCellValue();
            List<String> imageUrls = buildFullImageUrls(imageUrlStr);
           
            client.setLogoImage(imageUrls.get(0));

            clients.add(client);
        }

        return clients;
    }
    @Override
    public List<Subscriber> readSubscribers(Sheet sheet) {
        List<Subscriber> subscribers = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            String email = getCellValue(row, 0);
            if (email != null && !email.isEmpty()) {
                Subscriber subscriber = new Subscriber();
                subscriber.setEmail(email.trim());
                subscriber.setSubscribedAt(LocalDateTime.now());
                
                subscribers.add(subscriber);
            }
        }

        return subscribers;
    }

    
    @Override
    public List<PartnerRequest> readPartnerRequests(Sheet sheet) {
        List<PartnerRequest> requests = new ArrayList<>();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            PartnerRequest request = new PartnerRequest();
            request.setFullName(getCellValue(row, 0));
            request.setCompanyName(getCellValue(row, 1));
            request.setEmail(getCellValue(row, 2));
            request.setPhone(getCellValue(row, 3));
            request.setMessage(getCellValue(row, 4));
            request.setSubmittedAt(LocalDateTime.now());
            requests.add(request);
        }

        return requests;
    }


    private String getCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return getCellValue(cell);
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    
    private List<String> buildFullImageUrls(String cellValue) {
        if (cellValue == null || cellValue.trim().isEmpty()) {
        	return Collections.emptyList();
        }

        return new ArrayList<>(Arrays.stream(cellValue.split("\\s*,\\s*"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> PUBLIC_URL_BASE + "/upload/" + s)
                .collect(Collectors.toList()));
    }
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }

        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                String value = getCellValue(cell);
                if (value != null && !value.trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }



}
