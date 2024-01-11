package com.rollingstone.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity(name = "PRODUCT")
@Table
public class Product {

	//@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String productCode;

	@Column(nullable = false)
	private String productName;

	@Column(name = "SHORT_DESCRIPTION", nullable = false)
	private String shortDescription;

	@Column(name = "LONG_DESCRIPTION", nullable = false)
	private String longDescription;

	@Column(name = "CANDISPLAY", nullable = false)
	private boolean canDisplay;

	@Column(name = "CANDELETED", nullable = false)
	private boolean canDeleted;

	@Column(name = "CANAUTOMOTIVE", nullable = false)
	private boolean canAutomotive;

	@Column(name = "CANINTERNATIONAL", nullable = false)
	private boolean canInternational;

	@OneToOne()
	@JoinColumn(name = "parent_category_id")
	private Category parentCategory;

	@OneToOne
	@JoinColumn(name = "category_id")
	private Category category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public boolean isCanDisplay() {
		return canDisplay;
	}

	public void setCanDisplay(boolean canDisplay) {
		this.canDisplay = canDisplay;
	}

	public boolean isCanDeleted() {
		return canDeleted;
	}

	public void setCanDeleted(boolean canDeleted) {
		this.canDeleted = canDeleted;
	}

	public boolean isCanAutomotive() {
		return canAutomotive;
	}

	public void setCanAutomotive(boolean canAutomotive) {
		this.canAutomotive = canAutomotive;
	}

	public boolean isCanInternational() {
		return canInternational;
	}

	public void setCanInternational(boolean canInternational) {
		this.canInternational = canInternational;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Product() {
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productCode=" + productCode + ", productName=" + productName
				+ ", shortDescription=" + shortDescription + ", longDescription=" + longDescription + ", canDisplay="
				+ canDisplay + ", canDeleted=" + canDeleted + ", canAutomotive=" + canAutomotive + ", canInternational="
				+ canInternational + ", parentCategory=" + parentCategory + ", category=" + category + "]";
	}

}
