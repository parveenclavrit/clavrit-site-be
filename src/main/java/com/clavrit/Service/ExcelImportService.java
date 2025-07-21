package com.clavrit.Service;

import com.clavrit.Entity.*;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.List;

public interface ExcelImportService {

    List<ClavritService> readServices(Sheet sheet);
    //void saveServices(List<ClavritService> services);

    List<Blog> readBlogs(Sheet sheet);
    //void saveBlogs(List<Blog> blogs);
    List<Client> readClients(Sheet sheet);
    List<Project> readProjects(Sheet sheet);
    //void saveProjects(List<Project> projects);
    List<MailRecord> readMailRecords(Sheet sheet);
    List<PartnerRequest> readPartnerRequests(Sheet sheet);
    List<JobDetail> readJobs(Sheet sheet);
    List<Subscriber> readSubscribers(Sheet sheet);
    //void saveJobs(List<JobDetail> jobs);

    List<JobApplication> readApplications(Sheet sheet);
   // void saveApplications(List<JobApplication> applications);

}
