package com.clavrit.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "services")
public class ClavritService {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String title;         // e.g., "Salesforce"
	    private String img;           // e.g., "./assets/img/sf.jpg"
	    private String subheading;    // e.g., "Supercharge Your CRM..."

	    @Lob
	    @Column(columnDefinition = "TEXT")
	    private String description;

	    @Lob
	    @Column(columnDefinition = "TEXT")
	    private String content;

	    @ElementCollection
	    @CollectionTable(name = "service_images", joinColumns = @JoinColumn(name = "service_id"))
	    @Column(name = "image_url")
	    private List<String> imageUrls = new ArrayList<>();

	    // Getters and setters
	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public String getImg() {
	        return img;
	    }

	    public void setImg(String img) {
	        this.img = img;
	    }

	    public String getSubheading() {
	        return subheading;
	    }

	    public void setSubheading(String subheading) {
	        this.subheading = subheading;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public List<String> getImageUrls() {
	        return imageUrls;
	    }

	    public void setImageUrls(List<String> imageUrls) {
	        this.imageUrls = imageUrls;
	    }

		public ClavritService() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	}