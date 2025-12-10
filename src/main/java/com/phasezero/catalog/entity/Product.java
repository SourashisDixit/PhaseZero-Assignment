package com.phasezero.catalog.entity;

import java.util.Date;

import com.phasezero.catalog.dto.ProductDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "part_number")
	private String partNumber;

	@Column(name = "part_name")
	private String partName;

	private String category;

	private Double price;

	private Integer stock;

	private String brand;

	@Column(name = "created_at")
	private Date createdAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Product(Long id, String partNumber, String partName, String category, Double price, Integer stock,
			String brand, Date createdAt) {
		super();
		this.id = id;
		this.partNumber = partNumber;
		this.partName = partName;
		this.category = category;
		this.price = price;
		this.stock = stock;
		this.brand = brand;
		this.createdAt = createdAt;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDto convertToDto() {
		return new ProductDto(this.getId() != null ? this.getId() : null,
				this.getPartNumber() != null ? this.getPartNumber() : null,
				this.getPartName() != null ? this.getPartName() : null,
				this.getCategory() != null ? this.getCategory() : null,
				this.getPrice() != null ? this.getPrice() : null, this.getStock() != null ? this.getStock() : null,
				this.getBrand() != null ? this.getBrand() : null);
	}

}
