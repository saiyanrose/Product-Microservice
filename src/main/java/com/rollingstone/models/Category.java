package com.rollingstone.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "CATEGORY")
@Table
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "CATEGORY_NAME", nullable = false)
	private String categoryName;

	@Column(name = "CATEGORY_DESCRIPTION", nullable = false)
	private String categoryDescription;
	
	public Category(Long id, String categoryName, String categoryDescription) {		
		this.id = id;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}

	public Category() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}	
	
}
