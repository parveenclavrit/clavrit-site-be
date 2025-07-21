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

	    private String name;
	    private String type;

	    @Lob
	    private String description;

	    @ElementCollection
	    @CollectionTable(name = "service_images", joinColumns =  @JoinColumn(name = "service_id"))
	    @Column(name = "image_url")
	    private List<String> imageUrl = new ArrayList<>();

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public List<String> getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(List<String> imageUrl) {
			this.imageUrl = imageUrl;
		}


	public ClavritService() {
		super();
	}
	
	

}
