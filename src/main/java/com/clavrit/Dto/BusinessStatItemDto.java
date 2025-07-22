package com.clavrit.Dto;

public class BusinessStatItemDto {
	private long id;
	private String title;
    private String value;
    private String iconUrl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String geticonUrl() {
		return iconUrl;
	}
	public void seticonUrl(String iconUrl) {
		this.iconUrl = iconUrl;
		
		
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public BusinessStatItemDto()
	{	}
	public BusinessStatItemDto(String title, String value, String iconUrl) {
		super();
		this.title = title;
		this.value = value;
		this.iconUrl = iconUrl;
	}
	public BusinessStatItemDto(long id, String title, String value, String iconUrl) {
		super();
		this.id = id;
		this.title = title;
		this.value = value;
		this.iconUrl = iconUrl;
	}
    
}
