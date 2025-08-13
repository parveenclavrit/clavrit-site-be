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

	    private String title;
	    
	    private String metaTitle;
	    
	    private String slug;
	    
	    private String metaDescription;
	    
	    private String subheading;
	    
	    private String category;

	    @Lob
	    private String description;

	    @Lob
	    private String content;

	    @ElementCollection
	    @CollectionTable(name = "service_images", joinColumns = @JoinColumn(name = "service_id"))
	    @Column(name = "image_url")
	    private List<String> imageUrls = new ArrayList<>();

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

		public String getSubheading() {
			return subheading;
		}

		public void setSubheading(String subheading) {
			this.subheading = subheading;
		}

		public String getCategory() {
			return category;
		}

		public void setCategory(String category) {
			this.category = category;
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

		public String getMetaTitle() {
			return metaTitle;
		}

		public void setMetaTitle(String metaTitlte) {
			this.metaTitle = metaTitlte;
		}

		public String getSlug() {
			return slug;
		}

		public void setSlug(String slug) {
			this.slug = slug;
		}

		public String getMetaDescription() {
			return metaDescription;
		}

		public void setMetaDescription(String metaDescription) {
			this.metaDescription = metaDescription;
		}

	   
	    
	}